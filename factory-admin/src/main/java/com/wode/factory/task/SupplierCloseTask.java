package com.wode.factory.task;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.facade.SupplierSaleCodeManageFacade;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.CommissionRefundDetailService;
import com.wode.factory.service.SupplierCloseCmdService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.vo.SupplierSaleFuliVo;

/**
 * 功能说明: 结算定时任务类
 * 包括：
 * 		订单自动结算
 * 日期:	2015年11月9日
 * 开发者:	高永久
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年3月20日
 */
@Component
public class SupplierCloseTask {


	@Autowired
    private SupplierCloseCmdService supplierCloseCmdService;

	@Autowired
    private SupplierCloseFacade scf;	
	
    private final Logger logger = LoggerFactory.getLogger(SupplierCloseTask.class);

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
    private SupplierSaleCodeManageFacade sscmf;
	@Autowired
	private CommissionRefundDetailService commissionRefundDetailService;
	@Autowired
	private UserFactoryService userFactoryService;
	
	/**
	 * 结算，每天凌晨1点5跑
	 * 2015-11-09
	 */
	@Scheduled(cron = "0 5 1 * * ?")
	public void checkReceivesProduct() {
		logger.debug("自动结算开始");
		int s_cnt =0;
		int e_cnt =0;
		
		////////////////////////////////////////////////////////////////////
		//查询执行任务
		SupplierCloseCmd entity = new SupplierCloseCmd();
		entity.setExecDate(new Date()); 	//执行日期为当前日期
		entity.setExecStatus("0");			//未处理数据
		List<SupplierCloseCmd> lst = supplierCloseCmdService.find(entity);
		////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////
		//循环执行任务
		for (SupplierCloseCmd supplierCloseCmd : lst) {
			try{
				//循环执行，如遇异常跳过，执行下一个
				boolean result = true;
				String errMsg="";
				Long saleBillId = 0L;
				
				////////////////////////////////////////////////////////////
				//更新执行状态
				supplierCloseCmd.setExecStatus("1");		//设置为执行中状态
				supplierCloseCmdService.update(supplierCloseCmd);
				////////////////////////////////////////////////////////////
	
				String billId="";
	
				////////////////////////////////////////////////////////////
				//取得商家信息 
				Supplier s =supplierService.findByid(supplierCloseCmd.getSupplierId());
				if(s == null) {
					result=false;
					errMsg = "商家信息取得失败。请确认表 t_supplier，t_supplier_close_cmd中的数据，商家id：" + supplierCloseCmd.getSupplierId();
				} else {
					//用以识别本企业员工的id应为商家关联的企业id
					supplierCloseCmd.setEnterpriseId(s.getEnterpriseId()==null ? s.getId():s.getEnterpriseId());
				}
				////////////////////////////////////////////////////////////
	
				////////////////////////////////////////////////////////////
				//对账单ID 表示用
				if(result) {
					billId = sscmf.getSaleCode(supplierCloseCmd.getSupplierId());
	
					////////////////////////////////////////////////////////////
					//账务处理
					try{
						ActResult<Long> ar= scf.execClose(supplierCloseCmd,s.getComName(),billId);
						result = ar.isSuccess();
						errMsg = ar.getMsg();
						saleBillId = ar.getData();
					} catch(Exception e) {
						result = false;
						errMsg = "处理异常" + e.getMessage();
						logger.error(e.getMessage());
						saleBillId = 0L;
					}
					////////////////////////////////////////////////////////////
				}
				////////////////////////////////////////////////////////////
	
				////////////////////////////////////////////////////////////
				//账期处理
				scf.dealCloseCmd(supplierCloseCmd, result, saleBillId, errMsg);
				////////////////////////////////////////////////////////////
	
				if(result) {
					s_cnt++;
				} else {
					e_cnt++;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		////////////////////////////////////////////////////////////////////
		logger.info("定时任务：自动结算处理完成！共处理 " + lst.size() + " 条数据。成功" + s_cnt + "条，失败"+e_cnt +"条。");
	}
	

	/**
	 * 结算，每天凌晨8点5跑
	 * 2015-11-09
	 */
	@Scheduled(cron = "0 5 8 * * ?")
	public void returnFuli() {
		Calendar c = Calendar.getInstance();
		//昨天
		c.add(Calendar.DATE, -1);
		String date = c.get(Calendar.YEAR)+"-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
		String startTime=date+" 00:00:00";
		String endTime= date+" 23:59:59";
		List<SupplierSaleFuliVo>  ls =commissionRefundDetailService.countSupplierDaySaleFuli(startTime, endTime);
		for (SupplierSaleFuliVo supplierSaleFuliVo : ls) {

			UserFactory model = new UserFactory();
			model.setEnabled(1);
			model.setUsable(1);
			model.setType(1);
			model.setEmployeeType(-1); //全部数据
			model.setSupplierId(supplierSaleFuliVo.getSupplierId());

			List<UserFactory> lst= userFactoryService.selectByModel(model);
			
			if(lst.isEmpty()) continue;
			//小于平均小于1时 不发送
			if(supplierSaleFuliVo.getFuli().compareTo(NumberUtil.toBigDecimal(lst.size()))<0) continue; 
			BigDecimal value=supplierSaleFuliVo.getFuli().divide(NumberUtil.toBigDecimal(lst.size()),1,BigDecimal.ROUND_DOWN);
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("limitm", value);
			paramMap.put("desrc", "商品热卖，平台将商品优惠向员工平均回馈内购券："+value);
			paramMap.put("updName","System");

			Map<String,Object> paramPush=new HashMap<String,Object>();
			paramPush.put("title", "热卖销售奖励");
			paramPush.put("msg", "贵公司商品热卖，平台将昨日订单中累计优惠额度，平均回馈给公司员工，您共获得奖励内购券："+value);
			
			Map<String,Object> paramWX = new HashMap<String,Object>();
			paramWX.put("type", "balace");
			paramWX.put("cId", "1");
			paramWX.put("date", TimeUtil.getStringDateShort());
			paramWX.put("amount",value);
			paramWX.put("note", "贵公司商品热卖，平台将昨日订单中累计优惠额度，平均回馈给公司员工，您共获得奖励内购券："+value);
			
			for (UserFactory userFactory : lst) {			
				try{
					//派发福利
					paramMap.put("empId", userFactory.getId());
					paramMap.put("empName", userFactory.getUserName());
					HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_SUPPLIER_URL+"api/salePrize", paramMap);

					//app 推送
					paramPush.put("userId", userFactory.getId());
					HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL+"user/pushMsg", paramPush);

					//微信推送
					paramWX.put("userId", userFactory.getId());
					HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_API_URL + "wx/templateMsgSend", paramWX);
				} catch(Exception ex) {
					
				}
			}
		}
	}
}