
package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.mapper.SaleBillDao;
import com.wode.factory.mapper.SaleDetailDao;
import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.ServiceReceipt;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.service.SaleBillService;
import com.wode.factory.service.ServiceReceiptService;
import com.wode.factory.service.SupplierDurationVoService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.ReceiptVo;
import com.wode.factory.vo.SaleBillDetailVo;

@Service("SaleBillService")
public class SaleBillServiceImpl extends EntityServiceImpl<SaleBill,Long> implements SaleBillService {
	
	@Autowired
	private SaleBillDao dao;
	@Autowired
	private SaleDetailDao saleDetailDao;
	@Autowired
	private ServiceReceiptService serviceReceiptService;
	@Autowired
	DBUtils dbUtils;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	SupplierDurationVoService supplierDurationVoService;

	
	@Override
	public SaleBillDao getDao() {
		return dao;
	}

	@Override
	public void insert(SaleBill entity) {
		dao.insert(entity);
	}

	@Override
	public SaleBill sumBydetail(Long id) {
		return getDao().sumBydetail(id);
	}

	@Override
	public PageInfo<SaleBill> getPage(Map<String, Object> params) {
		//结算周期
		String endTime = (String)params.get("endTime");
		if(!StringUtils.isNullOrEmpty(endTime)){
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
				//日期加一天  因为数据库中时间都是年-月-日 时:分:秒 格式的 查询条件是 年-月-日 格式的。
				cal.add(Calendar.DAY_OF_MONTH, 1);
				params.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//付款时间
		String endPayTime = (String)params.get("endPayTime");
		if(!StringUtils.isNullOrEmpty(endPayTime)){
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endPayTime));
				//日期加一天  因为数据库中时间都是年-月-日 时:分:秒 格式的 查询条件是 年-月-日 格式的。
				cal.add(Calendar.DAY_OF_MONTH, 1);
				params.put("endPayTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		PageHelper.startPage(params);
		List<SaleBill> listSaleBill = this.dao.findPageSaleBill(params);
		return new PageInfo(listSaleBill);
	}

	@Override
	@Transactional
	public ActResult<Object> updateSaleBillStatus(List<SaleBill> saleBill,BigDecimal amount,String code,Long updUser) {
		ActResult<Object> act = new ActResult<Object>();
		if(saleBill.isEmpty()){
			act.setSuccess(false);
			act.setMsg("参数为空");
			return act;
		}
		
		List<SaleBill> updateInfo = new ArrayList<SaleBill>();

		ServiceReceipt receipt=null;
		for(SaleBill salebill : saleBill){
			//查询账单详情
			SaleBill sb = this.dao.getById(salebill.getId());

			if(receipt==null) {
				if(!StringUtils.isNullOrEmpty(saleBill.get(0).getReceiptStatus())){
					if(2==saleBill.get(0).getReceiptStatus()){
						Supplier s = supplierService.findByid(sb.getSupplierId());
						SupplierDuration sd =supplierDurationVoService.getById(sb.getSupplierId());
						receipt=new ServiceReceipt();
						receipt.setId(dbUtils.CreateID());
						receipt.setType("技术服务费");
						receipt.setSupplierId(sb.getSupplierId());
						receipt.setCode(code);
						receipt.setTitle(s.getComName());
						receipt.setFinanceCode(sd.getFinanceCode());
						receipt.setAmount(amount);
						receipt.setCreateDate(new Date());
						receipt.setCreateUser(updUser);
						receipt.setStatus("2");
						receipt.setSaleBillIds("");
						Calendar firstc = Calendar.getInstance();
						firstc.set(Calendar.DAY_OF_MONTH, 1);
						firstc.set(Calendar.HOUR_OF_DAY, 0);
						firstc.set(Calendar.MINUTE, 0);
						firstc.set(Calendar.SECOND, 0);
						firstc.add(Calendar.MONTH, 1);
						firstc.add(Calendar.SECOND, -1);
						receipt.setReturnLimit(firstc.getTime());
					}
				}
			}
			if(StringUtils.isNullOrEmpty(sb)){
				act.setSuccess(false);
				act.setMsg("id参数错误");
				return act;
			}
			//结算状态 -1审核不通过 0：待审核  1：审核成功（待付款） 2：已付款
			if(!StringUtils.isNullOrEmpty(salebill.getPayStatus())){
				//已付款
				if(4==salebill.getPayStatus()){
					//支付，直接进入商家记账流程，不在此处更新状态
//					sb.setPayStatus(2);
//					sb.setPayTime(new Date());
				} else {
					sb.setPayStatus(salebill.getPayStatus());
				}
			
			//发票开具状态 0：未开发票  1：已开发票
			}else if(!StringUtils.isNullOrEmpty(salebill.getReceiptStatus())){
				//开具发票
				if(2==salebill.getReceiptStatus()){
					sb.setReceiptStatus(2);
					sb.setReceiptTime(new Date());
					sb.setReceiptCode(receipt.getCode());
					sb.setReceiptId(receipt.getId());
					
					receipt.setSaleBillIds(receipt.getSaleBillIds()+sb.getId()+",");
				}
				
			}else{
				act.setSuccess(false);
				act.setMsg("参数错误");
				return act;
			}			
			updateInfo.add(sb);
		}

		if(receipt!=null) {
			//保存发票信息
			serviceReceiptService.insert(receipt);
		}
		//更新结算单状态
		for(SaleBill sb: updateInfo){
			switch (sb.getPayStatus()) {
			case 1:
				sb.setLastUpdateTime1(new Date());
				break;
			case 2:
				sb.setLastUpdateTime2(new Date());
				break;
			case 3:
				sb.setLastUpdateTime3(new Date());
				break;
			case 4:
				sb.setLastUpdateTime4(new Date());
				break;
			case -1:
				sb.setLastUpdateTime1(new Date());
				break;
			case -2:
				sb.setLastUpdateTime2(new Date());
				break;
			case -3:
				sb.setLastUpdateTime3(new Date());
				break;

			default:
				break;
			}
			this.dao.update(sb);
		}
		
		act.setSuccess(true);
		return act;
	}

	/* 根据对账单id查询发票信息
	 * @see com.wode.factory.service.SaleBillService#getReceiptInfo(java.util.List)
	 */
	@Override
	public ActResult<ReceiptVo> getReceiptInfo(List<Long> id) {
		// TODO Auto-generated method stub
		ActResult<ReceiptVo> act = new ActResult<ReceiptVo>();
		if(id.isEmpty()){
			act.setSuccess(false);
			act.setMsg("参数为空");
			return act;
		}
		//发票信息实体类
		ReceiptVo receipt = new ReceiptVo();
		//发票的价钱
		List<SaleBill> price = new ArrayList<SaleBill>();
		//发票的总价钱
		Double sumPrice =0.0;
		//商户的id
		Long supplierId = 0l;
		for(int i =0;i<id.size();i++){
			//根据对账单id查询对账单信息
			SaleBill saleBill = this.dao.getSaleBillDetail(id.get(i));
			if(i==0){
				receipt.setSupplier(saleBill.getName());
				
				supplierId=saleBill.getSupplierId();
			}else{
				//判断方法参数id所对应的商家是否是同一家
				if(supplierId.equals(saleBill.getSupplierId())){
					//id相同，表示是同一家商家
				}else{
					act.setSuccess(false);
					act.setMsg("不能开具不同商家的发票");
					return act;
				}
			}
			
			price.add(saleBill);
			
			sumPrice+=saleBill.getCommissionPrice().subtract(saleBill.getRefundAmount()).doubleValue();
		}
		receipt.setPrice(price);
		receipt.setSumPrice(sumPrice);
		
		act.setSuccess(true);
		act.setData(receipt);
		return act;
	}

	@Override
	public ActResult<SaleBill> makeSailBill(Integer relationType, List<Long> id) {

		ActResult<SaleBill> act = new ActResult<SaleBill>();
		if(id.isEmpty()){
			act.setSuccess(false);
			act.setMsg("参数为空");
			return act;
		}
		Date startTime=new Date();
		Date endTime=TimeUtil.strToDate("2016-12-07");
		
		//发票信息实体类
		SaleBill relation = new SaleBill();
		//发票的价钱
		List<SaleBill> price = new ArrayList<SaleBill>();
		//发票的总价钱
		Double sumPrice =0.0;
		for(int i =0;i<id.size();i++){
			//根据对账单id查询对账单信息
			SaleBill saleBill = this.dao.getSaleBillDetail(id.get(i));
			if(i==0){
				relation.setName(saleBill.getName());
				relation.setSupplierId(saleBill.getSupplierId());
			}else{
				//判断方法参数id所对应的商家是否是同一家
				if(relation.getSupplierId().equals(saleBill.getSupplierId())){
					//id相同，表示是同一家商家
				}else{
					act.setSuccess(false);
					act.setMsg("不能同时生成不同商家的结算单");
					return act;
				}
			}
			
			price.add(saleBill);

			//结算内容 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金',
			if(relationType == 2) {
				//本期需支付的总金额 = 运费总额
				sumPrice+=saleBill.getCarriagePrice().doubleValue();
			} else if(relationType == 1) {
				//本期需支付的总金额 = 返佣-佣金
				sumPrice+=saleBill.getRefundAmount().subtract(saleBill.getCommissionPrice()).doubleValue();
			}
			
			//获取最小开始日期
			if(startTime.compareTo(saleBill.getStartTime())>0) {
				startTime= saleBill.getStartTime();
			}
			
			//获取最大结束日期
			if(endTime.compareTo(saleBill.getEndTime())<0) {
				endTime= saleBill.getEndTime();
			}
		}
		//应付总额
		relation.setPayPrice(NumberUtil.toBigDecimal(sumPrice));
		relation.setStartTime(startTime);
		relation.setEndTime(endTime);
		relation.setTitle(getBillTitle(startTime, endTime, relationType));
		relation.setCloseNote(getBillNote(startTime, endTime, relationType));
		relation.setCloseType(relationType);
		relation.setPayType(1);		//1:现金账户 2:其他方式
		
		act.setSuccess(true);
		act.setData(relation);
		return act;
	}	

	/**
	 * 账单名称生成
	 * @param start
	 * @param end
	 * @return
	 */
	private String getBillTitle(Date start,Date end,Integer relationType) {
		SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
		String title = f.format(start) + "至" + f.format(end);
		//结算内容 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金',
		if(relationType == 2) {
			//本期需支付的总金额 = 运费总额
			title += "运费对账单";
		} else if(relationType == 1) {
			//本期需支付的总金额 = 返佣-佣金
			title += "佣金对账单";
		}
		return title;
	}

	/**
	 * 账单名称生成
	 * @param start
	 * @param end
	 * @return
	 */
	private String getBillNote(Date start,Date end,Integer relationType) {
		String title = "";
		if(relationType == 2) {
			//本期需支付的总金额 = 运费总额
			title += "运费独立结算，结算周期：";
		} else if(relationType == 1) {
			//本期需支付的总金额 = 返佣-佣金
			title += "佣金独立结算，结算周期：";
		}
		SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
		title += f.format(start) + "至" + f.format(end);
		return title;
	}
	
	/* 查询对账单及订单详情
	 * @see com.wode.factory.service.SaleBillService#getSaleBillDetail(java.lang.Long)
	 */
	@Override
	public SaleBillDetailVo getSaleBillDetail(Long id) {
		// TODO Auto-generated method stub
		if(StringUtils.isNullOrEmpty(id)){
			return new SaleBillDetailVo();
		}else{
			SaleBillDetailVo detail = new SaleBillDetailVo();
			//查询对账单详情
			SaleBill saleBill = this.dao.getSaleBillDetail(id);
			detail.setSaleBill(saleBill);
			//查询对账单对应的订单详情
			List<SaleDetail> listSaleDetail = this.saleDetailDao.getBySaleBillId(id);
			detail.setSaleDetail(listSaleDetail);
			
			return detail;
		}
	}

	@Override
	public List<SaleBill> getByIds(String ids) {
		return this.dao.getByIds(ids);
	}
}
