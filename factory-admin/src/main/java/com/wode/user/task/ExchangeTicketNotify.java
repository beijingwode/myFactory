package com.wode.user.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.ProductExchangeFacade;
import com.wode.factory.facade.SpecialSaleFacade;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.service.ExchangeSuborderService;
import com.wode.factory.service.SaleDetailService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.task.OrderTask;


@Service
public class ExchangeTicketNotify {
	@Autowired
	private ProductExchangeFacade productExchangeFacade;
	@Autowired
	SpecialSaleFacade specialSaleFacade;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	@Autowired
    private SaleDetailService saleDetailService;
	
	private final Logger logger = LoggerFactory.getLogger(ExchangeTicketNotify.class);

	/**
	 * 前一天 确认收货订单 累计金额
	 */
	public void beforeShare() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.add(Calendar.SECOND, -1);
		Date closeEnd = now.getTime();		// 前一天的23:59:59

		now.add(Calendar.SECOND, 1);
		now.add(Calendar.DAY_OF_MONTH, -1);
		Date closeStart = now.getTime();	// 前一天的00:00:00

		logger.debug("换领币共享金额计算开始：" + closeStart + "--" + closeEnd);
		List<SaleDetail> ls= saleDetailService.countExchangeOrder(closeStart, closeEnd);
		
		// 按商家拆分
		Map<Long,List<SaleDetail>> map= new HashMap<Long,List<SaleDetail>>();
		for (SaleDetail saleDetail : ls) {
			List<SaleDetail> mv=map.get(saleDetail.getId());
			if(mv==null) {
				mv = new ArrayList<SaleDetail>();
				map.put(saleDetail.getId(), mv);
			}
			mv.add(saleDetail);
		}
		
		for (Long sid : map.keySet()) {
			productExchangeFacade.ExchangeOrderCount(sid, map.get(sid));
		}
		////////////////////////////////////////////////////////////////////
		logger.info("ExchangeTicketNotify beforeShare() end " + ls.size() + " 条数据。成功");
	}
	/**
	 * 判断换领结束，并返还预付金额（如有）
	 */
	public void stopExchange() {
		////////////////////////////////////////////////////////////////////////////
		// 获取未完结的换领商品记录，并循环处理
		SupplierExchangeProduct query = new SupplierExchangeProduct();
		query.setClearStatus(0);	// 0:未清算
		List<SupplierExchangeProduct> ls= supplierExchangeProductService.selectByModel(query);
		////////////////////////////////////////////////////////////////////////////

		List<EmpBenefitFlow> pushLs = new ArrayList<EmpBenefitFlow>();
		
		logger.debug("换领币预付金额返还开始");

		int s_cnt = 0;
		int e_cnt = 0;
		int skip = 0;
		
		logger.info("ExchangeTicketNotify share() start,"+ls.size()+" to share");
		
		for (SupplierExchangeProduct sep : ls) {
			List<EmpBenefitFlow> flws = productExchangeFacade.stopExchangeAndShare(sep);
			pushLs.addAll(flws);
		}
		

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", "换领币预付金额返还");
		String msg = "贵公司换领商品热卖，此前预付换领商品金额返还至现金券：";
		
		Map<String,Object> paramWX = new HashMap<String,Object>();
		paramWX.put("type", "balace");
		paramWX.put("cId", "0");
		paramWX.put("date", TimeUtil.getStringDateShort());
		paramWX.put("note", "贵公司换领商品热卖，此前预付换领商品金额返还至现金券");
		
		for (EmpBenefitFlow flw : pushLs) {			
			try{
				//app 推送
				paramPush.put("userId", flw.getEmpId());
				paramPush.put("msg", msg+flw.getCash());
				HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);

				//微信推送
				paramWX.put("userId", flw.getEmpId());
				paramWX.put("amount",flw.getCash());
				HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
				
				s_cnt++;
			} catch(Exception ex) {
				e_cnt++;
			}
		}
		
		////////////////////////////////////////////////////////////////////
		logger.info("ExchangeTicketNotify share() end " + ls.size() + " 条数据。成功" + s_cnt + "条，失败" + e_cnt + "条。跳过" + skip + "条。");
	}
	
	/**
	 * 换领币激活通知
	 */
	public void notifyShare() {

		////////////////////////////////////////////////////////////////////////////
		// 获取未完结的换领商品记录，并循环处理
		////////////////////////////////////////////////////////////////////////////
		SupplierExchangeProduct query = new SupplierExchangeProduct();
		query.setNotifyFlg("1");	// 0:未清算
		List<SupplierExchangeProduct> ls= supplierExchangeProductService.selectByModel(query);

		logger.debug("换领币激活通知开始");

		int s_cnt = 0;
		int e_cnt = 0;
		int skip = 0;
		
		logger.info("ExchangeTicketNotify notifyShare() start,"+ls.size()+" to share");

		List<UserExchangeTicket> pushLs = new ArrayList<UserExchangeTicket>();
		////////////////////////////////////////////////////////////////////////////
		// 获取未完结的换领商品记录，并循环处理
		////////////////////////////////////////////////////////////////////////////
		for (SupplierExchangeProduct sep : ls) {
			
			List<UserExchangeTicket> uets = productExchangeFacade.ExchangeShareNotify(sep);
			pushLs.addAll(uets);
		}
		
		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", "换领币激活");
		String msg = "贵公司换领商品热卖，所得金额激活换领币：";
		
		Map<String,Object> paramWX = new HashMap<String,Object>();
		paramWX.put("type", "special_sale_ticket");
		paramWX.put("specialSaleId", -1L);
		paramWX.put("first", "贵公司换领商品热卖,为员工储值并激活换领币");
		paramWX.put("remark", "激活换领币为员工共有，先用先得，手慢就没了，赶快抢吧！");
		paramWX.put("date", TimeUtil.getCurrentTime());
		
		for (UserExchangeTicket uts : pushLs) {	
			try{
				//app 推送
				paramPush.put("userId", uts.getUserId());
				paramPush.put("msg", msg+uts.getActiveAmount()+",该金额为员工共有，先用先得，手慢就没了，赶快抢吧！");
				HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);

				//微信推送
				paramWX.put("userId", uts.getUserId());
				paramWX.put("amount",msg+uts.getActiveAmount());
				paramWX.put("detail", "商品信息："+uts.getProductName());
				HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
				
				s_cnt++;
			} catch(Exception ex) {
				e_cnt++;
			}
			
		}
		////////////////////////////////////////////////////////////////////
		logger.info("ExchangeTicketNotify share() end " + ls.size() + " 条数据。成功" + s_cnt + "条，失败" + e_cnt + "条。跳过" + skip + "条。");
	}
	

	/**
	 * 3天未获得评价的试用商品
	 */
	public void clear() {
		////////////////////////////////////////////////////////////////////////////
		// 获取未完结的换领商品记录，并循环处理
		List<SupplierExchangeProduct> ls = supplierExchangeProductService.selectForClear();
		////////////////////////////////////////////////////////////////////////////

		List<EmpBenefitFlow> pushLs = new ArrayList<EmpBenefitFlow>();
		
		logger.debug("换领币预付金额返还开始");

		int s_cnt = 0;
		int e_cnt = 0;
		int skip = 0;
		
		logger.info("ExchangeTicketNotify share() start,"+ls.size()+" to share");
		
		////////////////////////////////////////////////////////////////////
		// 循环执行任务
		for (SupplierExchangeProduct ticket : ls) {
			try {

				////////////////////////////////////////////////////////////
				// 更新执行状态
				////////////////////////////////////////////////////////////
				ActResult<List<EmpBenefitFlow>> act = productExchangeFacade.ClearLimitTicket(ticket, "system");

				if (act.getData() != null && !act.getData().isEmpty()) {
					pushLs.addAll(act.getData());
				} else {
					e_cnt++;
				}
				s_cnt++;
				////////////////////////////////////////////////////////////
			} catch (Exception e) {
				e.printStackTrace();
				e_cnt++;
			}
		}

		Map<String,Object> paramPush=new HashMap<String,Object>();
		paramPush.put("title", "换领币预付金额返还");
		String msg = "贵公司换领商品热卖，此前预付换领商品金额返还至现金券：";
		
		Map<String,Object> paramWX = new HashMap<String,Object>();
		paramWX.put("type", "balace");
		paramWX.put("cId", "0");
		paramWX.put("date", TimeUtil.getStringDateShort());
		paramWX.put("note", "贵公司换领商品热卖，此前预付换领商品金额返还至现金券");
		
		for (EmpBenefitFlow flw : pushLs) {			
			try{
				//app 推送
				paramPush.put("userId", flw.getEmpId());
				paramPush.put("msg", msg+flw.getCash());
				HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);

				//微信推送
				paramWX.put("userId", flw.getEmpId());
				paramWX.put("amount",flw.getCash());
				HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
				
				s_cnt++;
			} catch(Exception ex) {
				e_cnt++;
			}
		}
		
		////////////////////////////////////////////////////////////////////
		logger.info("ExchangeTicketNotify share() end " + ls.size() + " 条数据。成功" + s_cnt + "条，失败" + e_cnt + "条。跳过" + skip + "条。");
	}
	
	public void autoCancelOrder() {
		/**
		 * 未支付订单自动取消(还原库存)
		 * 
		 */
        ExchangeSuborder q = new ExchangeSuborder();
        q.setCreateTime(TimeUtil.addDay(new Date(), OrderTask.cancleDay));
        q.setStatus(0);
        
		Map<String,Object> paramPush=new HashMap<String,Object>();
        List<ExchangeSuborder> gss=  exchangeSuborderService.selectByModel(q);
		String api=apiUrl+"exchangeOrder/autoCancelOrder.tj";
		if (null != gss && gss.size() > 0) {
			for (ExchangeSuborder gs : gss) {
				// 到时自动开团
				paramPush.put("userId",gs.getUserId());
				paramPush.put("subOrderId", gs.getSubOrderId());
				paramPush.put("closeReason", "因超时未付款，交易自动关闭");
				paramPush.put("updateBy", "system");
				paramPush.put("exchangeStatus", "-1");
				try{
					HttpClientUtil.sendHttpRequest("post",api, paramPush);
				} catch(Exception ex) {
				}
			}
		}
	}
}
