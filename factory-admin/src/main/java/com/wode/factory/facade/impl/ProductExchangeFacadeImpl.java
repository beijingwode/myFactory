package com.wode.factory.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.SeasonUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.facade.ProductExchangeFacade;
import com.wode.factory.facade.ProductFacadeOff;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.SupplierTicketFlow;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.service.EmpBenefitFlowService;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.OrderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierTicketFlowService;
import com.wode.factory.service.UserBalanceService;
import com.wode.factory.service.UserExchangeTicketService;
import com.wode.factory.service.UserTicketHisService;

/**
 * Created by gaoyj on 2015/11/09.
 */
@Service("productExchangeFacade")
@Transactional("tm_factory")
public class ProductExchangeFacadeImpl implements ProductExchangeFacade {
	
	@Resource
	private ProductService productService;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private UserTicketHisService userTicketHisService;
	@Autowired
	private OrderService orderService;
	@Autowired
	DBUtils dbUtils;
	@Autowired
	UserBalanceService userBalanceService;
	@Autowired
	EmpBenefitFlowService empBenefitFlowService;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private SupplierTicketFlowService supplierTicketFlowService;
	@Autowired
    private ProductFacadeOff productFacade;
	@Autowired
    private EntBenefitFacade entBenefitFacade;
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private ProductDao productDao;
	
	@Override
	@Transactional
	public ActResult<List<UserTicketHis>> ExchangeProuctCheck(Product pro,String updName) {

		Calendar c = Calendar.getInstance();
		
		////////////////////////////////////////////////////////////////////////////
		//商品锁定
//		pro.setLocked(1);
//		pro.setLockReason("换领商品自动锁定");
//		productService.updateByBusiness(pro);
		////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////
		// 更新换领商品记录
		// 获取换领商品记录
//		SupplierExchangeProduct query = new SupplierExchangeProduct();
//		query.setSupplierId(pro.getSupplierId());
//		query.setProductId(pro.getId());
//		query.setStatus(1);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("supplierId", pro.getSupplierId());
		param.put("productId", pro.getId());
		param.put("status", "1,2");
		List<SupplierExchangeProduct> ls= supplierExchangeProductService.findListByMap(param);
		List<ProductSpecifications> list = productSpecificationsService.findlistByProductid(pro.getId());
		SupplierExchangeProduct exchangeProduct = ls.get(0);
		BigDecimal subtract = BigDecimal.ZERO;
		if(list != null && list.size() >0) {
			subtract = list.get(0).getInternalPurchasePrice();
			exchangeProduct.setProductPrice(subtract);
		}
		exchangeProduct.setProductCnt(pro.getAllnum());
		exchangeProduct.setEmpAvgCnt((pro.getEmpPrice().divide(subtract,2, BigDecimal.ROUND_HALF_EVEN)).setScale(1, BigDecimal.ROUND_HALF_UP));
		exchangeProduct.setEmpAvgAmount(pro.getEmpPrice());
		exchangeProduct.setStatus(2);					//状态=2:换领中
		exchangeProduct.setLimitStart(c.getTime());		//使用期限开始
		if(exchangeProduct.getLimitType() == 5) {
			c.add(Calendar.DAY_OF_MONTH, 15);	// 半个月  15天 
		} else if(exchangeProduct.getLimitType() == 9) {
			c.add(Calendar.YEAR, 5);			// 售完为止 5年
		} else {
			c.add(Calendar.MONTH, exchangeProduct.getLimitType());	// 半个月  15天 
		}
		exchangeProduct.setLimitEnd(c.getTime());		//使用期限开始
		supplierExchangeProductService.update(exchangeProduct);
		////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////
		//换领币分配
//		EnterpriseUser query2 = new EnterpriseUser();
//		query2.setEnterpriseId(pro.getSupplierId());
//		if(pro.getDivLevel() !=null && pro.getDivLevel()!=-1) {
//			query2.setWelfareLevel(pro.getDivLevel());
//		}
//		List<EnterpriseUser> emplyees = enterpriseUserService.selectByModel(query2);
//		if(emplyees ==null || emplyees.isEmpty()) {
//			//处理完成
//			return ActResult.successSetMsg("该商家没有员工，没有进行分配");
//		} 
//		List<UserTicketHis> rtn = new ArrayList<UserTicketHis>();
//		for (int i = 0; i < emplyees.size() && i<exchangeProduct.getEmpCnt(); i++) {
//			UserExchangeTicket userTicket = getUserTicket(exchangeProduct, emplyees.get(i));
//			rtn.add(saveUserTicketHis(exchangeProduct, userTicket, "216",
//					"公司向您发放换领币" + exchangeProduct.getEmpAvgAmount() + "元，限"
//							+ TimeUtil.dateToStr(exchangeProduct.getLimitEnd()) + "前使用",
//					emplyees.get(i).getUserName()));
//
//		}
		////////////////////////////////////////////////////////////////////////////
		
		return ActResult.success(null);
	}


	@Override
	@Transactional
	public ActResult<List<EmpBenefitFlow>> ClearLimitTicket(SupplierExchangeProduct supplierTicket, String updName) {

		////////////////////////////////////////////////////////////////////////////
		// 判断订单是否已结算
		if(NumberUtil.isGreaterZero(orderService.getNoClearOrderByProduct(supplierTicket.getProductId(), supplierTicket.getLimitStart(),supplierTicket.getLimitEnd()))) {
			return ActResult.fail("尚有未结算订单，暂时不能清算");
		}
		////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////
		// 根据记录获取未使用的换领币
		UserExchangeTicket query = new UserExchangeTicket();
		query.setExchangeProductId(supplierTicket.getId());
		List<UserExchangeTicket> emps = userExchangeTicketService.selectLeft(query);
		////////////////////////////////////////////////////////////////////////////
		
		return ActResult.success(this.recoveryTicket(supplierTicket, emps, updName));
	}

	@Override
	@Transactional
	public void ExchangeOrderCount(Long supplierId, List<SaleDetail> exchangeSales) {
		Map<Long,SupplierExchangeProduct> sepmap = new HashMap<Long,SupplierExchangeProduct>();
		Map<Long,SupplierTicketFlow> flows = new HashMap<Long,SupplierTicketFlow>();
		
		////////////////////////////////////////////////////////////////////////////////////////////
		// 销售数据更新
		for (SaleDetail saleDetail : exchangeSales) {
			SupplierExchangeProduct entity = getSEP(sepmap,supplierId,saleDetail.getProductId());
			if(entity !=null) {
				int buyCnt = 0;
				int distributeCnt = 0;
				int orderCnt = saleDetail.getNumber()*saleDetail.getStatus();
				// 使用券
				if(NumberUtil.isGreaterZero(saleDetail.getBenefitSelf())){
					distributeCnt =  saleDetail.getBenefitSelf().divide(saleDetail.getBenefitTicket(), 2, BigDecimal.ROUND_DOWN).multiply(NumberUtil.toBigDecimal(orderCnt)).intValue();
					buyCnt = orderCnt-distributeCnt;
				} else {
					buyCnt = orderCnt;
				}
				// 销售数++
				entity.setSellCnt(entity.getSellCnt()+ buyCnt);
				// 领取数++
				entity.setDistributeCnt(entity.getDistributeCnt() + distributeCnt);
				
				//BigDecimal cash = saleDetail.getBenefitTicket().subtract(saleDetail.getBenefitSelf()).multiply(NumberUtil.toBigDecimal(saleDetail.getStatus()));
				
				//换领币++
				BigDecimal sampleAmount = saleDetail.getBenefitTicket().multiply(NumberUtil.toBigDecimal(saleDetail.getStatus()));
				//现金++
				BigDecimal cashAmount = saleDetail.getRealPrice().multiply(NumberUtil.toBigDecimal(saleDetail.getStatus()));
				
				if(BigDecimal.ZERO.compareTo(sampleAmount) != 0 || BigDecimal.ZERO.compareTo(cashAmount) != 0) {
					if(entity.getSampleAmount() == null){
						entity.setSampleAmount(BigDecimal.ZERO);
					}
					if(entity.getCashAmount() == null){
						entity.setCashAmount(BigDecimal.ZERO);
					}
					entity.setSampleAmount(entity.getSampleAmount().add(sampleAmount));
					entity.setCashAmount(entity.getCashAmount().add(cashAmount));
					//cash=cash.multiply(NumberUtil.toBigDecimal(saleDetail.getStatus()));
					//累计现金++、可用现金++
//					entity.setAllSaleAmount(entity.getAllSaleAmount().add(cash));
//					entity.setShareAmount(entity.getShareAmount().add(cash));
					SupplierTicketFlow flw= flows.get(entity.getId());
					if(flw == null) {
						flw = this.makeSupplierTicketFlow(entity, "120", sampleAmount,entity.getSampleAmount(), "换领账单结算，货款入账。结算单号："+saleDetail.getSaleBillId(), saleDetail.getSaleBillId(), "system");
						flw.setCashAmount(cashAmount);
						flw.setCashBalance(entity.getCashAmount());
						flows.put(entity.getId(), flw);
					} else {
						flw.setAmount(flw.getAmount().add(sampleAmount));
						flw.setBalance(entity.getSampleAmount());
						flw.setCashAmount(flw.getCashAmount().add(cashAmount));
						flw.setCashBalance(entity.getCashAmount());
					}
				}
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////
		
		// 处理流水
		for (SupplierTicketFlow flw : flows.values()) {
			supplierTicketFlowService.save(flw);
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);
		Date now = new Date();
		////////////////////////////////////////////////////////////////////////////////////////////
		// 换领商品记录更新
		for (SupplierExchangeProduct sep : sepmap.values()) {
			if(sep.getProductCnt() == sep.getSellCnt()+sep.getDistributeCnt()) {
				boolean updEnds = false;
				//总数=领取+销售
				if(sep.getStatus() == 2) {
					// 领完
					sep.setStatus(3);	//3:已结束（领完）
					
					if(sep.getLimitType()==9) {
						//9:售完为止
						// 期限修改至 D+7 以便激活其他商家换领
						sep.setLimitEnd(c.getTime());
						
						updEnds=true;
					}
				}
				sep.setUpdateDate(now);
				
				// 更新换领币使用期限
				if(updEnds) {
					UserExchangeTicket query = new UserExchangeTicket();
					query.setExchangeProductId(sep.getId());
					query.setLimitEnd(sep.getLimitEnd());
					query.setUpdateDate(now);
					userExchangeTicketService.updateEnds(query);
				}
			}
			supplierExchangeProductService.update(sep);
		}
		////////////////////////////////////////////////////////////////////////////////////////////
	}


	@Override
	@Transactional
	public List<EmpBenefitFlow> stopExchangeAndShare(SupplierExchangeProduct sep) {
		Date now = new Date();
		List<EmpBenefitFlow> rtn = new ArrayList<EmpBenefitFlow>();
		
		boolean upd=false;

		////////////////////////////////////////////////////////////////////////////
		// 更新状态
		////////////////////////////////////////////////////////////////////////////			
		if(sep.getStatus()==4) {
			// 提前结束
			sep.setStatus(5);		//状态=5:已结束（终止）
			upd=true;
		} else if(sep.getStatus()==2) {
			if(sep.getLimitEnd().compareTo(now) <=0) {
				// 到达使用期限
				// 提前结束
				sep.setStatus(7);		//7:已结束（到期）
				upd=true;
			}
		}
		
		////////////////////////////////////////////////////////////////////////////
		// 返还预付金额
		////////////////////////////////////////////////////////////////////////////
		BigDecimal shareAmount = sep.getShareAmount();
		// 分配可用现金
//		if(NumberUtil.isGreaterZero(shareAmount)) {
//			
//			UserExchangeTicket userTicket = new UserExchangeTicket();
//			userTicket.setExchangeProductId(sep.getId());
//			userTicket.setPrepayAmount(BigDecimal.ZERO);
//			List<UserExchangeTicket> uets = userExchangeTicketService.selectByModel(userTicket);
//			if(!uets.isEmpty()) {
//				for (UserExchangeTicket uet : uets) {
//					BigDecimal amount = BigDecimal.ZERO;
//					if(uet.getPrepayAmount().compareTo(shareAmount)>0) {
//						amount = shareAmount;
//						shareAmount = BigDecimal.ZERO;
//					} else {
//						amount=uet.getPrepayAmount();
//						shareAmount = shareAmount.subtract(amount);
//					}
//					// 抵扣预付金额
//					uet.setPrepayAmount(uet.getPrepayAmount().subtract(amount));
//					// 记录共享金额使用
//					uet.setUsedActive(uet.getUsedActive().add(amount));
//					
//					// 记录现金流水
//					EmpBenefitFlow flw = new EmpBenefitFlow();
//					flw.setCash(amount);
//					flw.setEmpId(uet.getUserId());
//					flw.setKeyId(uet.getId()+"");
//					flw.setOpCode("216");
//					flw.setOpDate(now);
//					flw.setNote("换领商品换得现金券再次分配给员工");
//					flw.setTicket(BigDecimal.ZERO);
//					flw.setUserName("system");
//					
//					dealEmpBenefit(flw);					
//					rtn.add(flw);
//					userExchangeTicketService.update(uet);
//					
//
//					SupplierTicketFlow tflw = this.makeSupplierTicketFlow(sep, "107", amount,shareAmount, "换领商品换得现金券返还预付金额", flw.getId(), "system");
//					supplierTicketFlowService.save(tflw);
//					if(!NumberUtil.isGreaterZero(shareAmount)) {
//						break;
//					}
//				}
//				
//				upd=true;
//				sep.setShareAmount(shareAmount);
//			}
//		}
		

		////////////////////////////////////////////////////////////////////////////
		// 激活通知=1
		////////////////////////////////////////////////////////////////////////////
		String notify = "0";
		if(NumberUtil.isGreaterZero(sep.getShareAmount())) {
			if(sep.getLimitEnd().compareTo(now)>0) {
				notify ="1";		// 有共享金额且未超出又有效期 通知
			} else {
				notify ="0";		// 不通知
			}
		} else {
			notify ="0";		// 不通知
		}
		if(!sep.getNotifyFlg().equals(notify)) {
			upd=true;
			sep.setNotifyFlg(notify);
		}

		////////////////////////////////////////////////////////////////////////////
		// DB更新
		////////////////////////////////////////////////////////////////////////////
		if(upd) {
			sep.setUpdateDate(now);
			supplierExchangeProductService.update(sep);
		}
		
		////////////////////////////////////////////////////////////////////////////
		// 商品下架
		////////////////////////////////////////////////////////////////////////////
		if(sep.getStatus() == 3|| sep.getStatus() ==7) {
			// 换领结束 商品自动下架
			Product pro = productService.getById(sep.getProductId());
			// 解锁
			productDao.unlockExchangeProduct(pro);
			// 下架
			productFacade.forceselloff(pro);
		}
	
		
		return rtn;
	}

	@Override
	public List<UserExchangeTicket> ExchangeShareNotify(SupplierExchangeProduct sep) {
		Date now = new Date();
		List<UserExchangeTicket> rtn = new ArrayList<UserExchangeTicket>();
		
		if(sep.getLimitEnd().compareTo(now)>0) {

			UserExchangeTicket userTicket = new UserExchangeTicket();
			userTicket.setExchangeProductId(sep.getId());
			List<UserExchangeTicket> uets = userExchangeTicketService.selectLeft(userTicket);
			for (UserExchangeTicket uet : uets) {
				BigDecimal left = this.getLeft(uet);
				if(NumberUtil.isGreaterZero(left)) {
					if(sep.getShareAmount().compareTo(left)>0) {
						uet.setActiveAmount(left);
					} else {
						uet.setActiveAmount(sep.getShareAmount());
					}
					
					rtn.add(uet);
				}
			}
		}
		
		sep.setNotifyFlg("0");
		supplierExchangeProductService.update(sep);
		
		return rtn;
	}
	
	private SupplierExchangeProduct getSEP(Map<Long,SupplierExchangeProduct> sepmap,Long supplierId,Long pid) {
		if(!sepmap.containsKey(pid)) {
			////////////////////////////////////////////////////////////////////////////
			// 更新换领商品记录
			// 获取换领商品记录
			SupplierExchangeProduct query = new SupplierExchangeProduct();
			query.setSupplierId(supplierId);
			query.setProductId(pid);
			query.setClearStatus(0);	// 0:未清算
			List<SupplierExchangeProduct> ls= supplierExchangeProductService.selectByModel(query);
			for (SupplierExchangeProduct supplierExchangeProduct : ls) {
				sepmap.put(pid, supplierExchangeProduct);
				break;
			}
			////////////////////////////////////////////////////////////////////////////
		}
		return sepmap.get(pid);
	}
	/**
	 * 保存用户换领币使用记录
	 * @param pro
	 * @param supplierTicket
	 * @param div
	 * @param enterpriseUser
	 * @param userTicket
	 * @param opCode
	 * @return
	 */
	private UserTicketHis saveUserTicketHis(SupplierExchangeProduct supplierTicket,UserExchangeTicket userTicket,String opCode,String note,String userName) {
		UserTicketHis uhis= new UserTicketHis();
		uhis.setUserId(userTicket.getUserId());
		uhis.setOpDate(new Date());
		uhis.setOpCode(opCode);
		uhis.setTicketId(userTicket.getId());
		uhis.setTicket(userTicket.getEmpAvgAmount());
		uhis.setTicketBalance(userTicket.getEmpAvgAmount());
		uhis.setNote(note);
		uhis.setKeyId(supplierTicket.getId());
		uhis.setUserName(userName);
		uhis.setSupplierId(supplierTicket.getSupplierId());
		
		userTicketHisService.save(uhis);
		
		return uhis;
	}

	/**
	 * 获取用户换领币记录，没有时自动插入
	 * @param pro
	 * @param supplierTicket
	 * @param div
	 * @param enterpriseUser
	 * @return
	 */
	private UserExchangeTicket getUserTicket(SupplierExchangeProduct supplierTicket, EnterpriseUser enterpriseUser) {

		UserExchangeTicket userTicket = new UserExchangeTicket();
		userTicket.setExchangeProductId(supplierTicket.getId());
		userTicket.setUserId(enterpriseUser.getId());
		userTicket.setNickname(enterpriseUser.getName());
		userTicket.setDuty(enterpriseUser.getDuty());
		userTicket.setEmpAvgCnt(supplierTicket.getEmpAvgCnt());
		userTicket.setEmpAvgAmount(supplierTicket.getEmpAvgAmount());
		userTicket.setLimitStart(supplierTicket.getLimitStart());
		userTicket.setLimitEnd(supplierTicket.getLimitEnd());
		userTicket.setStatus(0);
		userTicket.setCreateDate(supplierTicket.getClearDate());
		userTicket.setActiveAmount(BigDecimal.ZERO);
		userTicket.setUsedAmount(BigDecimal.ZERO);
		userTicket.setUsedActive(BigDecimal.ZERO);
		userTicket.setLeftCnt(supplierTicket.getEmpAvgCnt());
		userTicket.setPrepayAmount(BigDecimal.ZERO);
		userTicket.setTicketNote("限购买换领商品");
		userTicket.setProductId(supplierTicket.getProductId());
		userTicket.setProductName(supplierTicket.getProductName());
		
		userExchangeTicketService.save(userTicket);
		
		return userTicket;
	}

	/**
	 * 回收员工手中换领币
	 * @param supplierTicket
	 * @param emps
	 * @param updName
	 */
	private List<EmpBenefitFlow> recoveryTicket(SupplierExchangeProduct supplierTicket,List<UserExchangeTicket> emps,String updName) {
		List<EmpBenefitFlow> rnt = new ArrayList<EmpBenefitFlow>();
		Date now = new Date();
		BigDecimal leftAll = BigDecimal.ZERO;

		BigDecimal shareAmount = supplierTicket.getShareAmount();
		for (UserExchangeTicket userTicket : emps) {
			// 分配可用现金
			if(NumberUtil.isGreaterZero(shareAmount)) {
				// 已激活金额=未使用金额
				BigDecimal leftTicket =this.getLeft(userTicket);
				if(leftTicket.compareTo(shareAmount)>=0) {
					userTicket.setActiveAmount(shareAmount);
					shareAmount = BigDecimal.ZERO;
				} else {
					userTicket.setActiveAmount(leftTicket);
					shareAmount = shareAmount.subtract(leftTicket);
				}
			}
			
			// 预付返还已消费
			// 已消费=已消费-预付，预付=0
			if(NumberUtil.isGreaterZero(userTicket.getPrepayAmount())) {
				
				saveUserTicketHis(supplierTicket, userTicket, "216",
						"换领币清算，预付金额返还换领币：" + userTicket.getPrepayAmount() + "元，",updName);
				
				userTicket.setUsedAmount(userTicket.getUsedAmount().subtract(userTicket.getPrepayAmount()));
				userTicket.setPrepayAmount(BigDecimal.ZERO);
			}
			
			//this.saveUserTicketHis(supplierTicket, userTicket, "217", "换领币到达使用期限自动回收，回收金额"+div+"元",updName);
			// 更新可领取数量
			// 可领取数量 =（换领币总数-已消费-现金）/内购价
			userTicket.setLeftCnt(userTicket.getEmpAvgAmount().subtract(userTicket.getUsedAmount())
					.subtract(userTicket.getActiveAmount())
					.divide(supplierTicket.getProductPrice(), 2, BigDecimal.ROUND_DOWN));
			
			if(NumberUtil.isGreaterZero(userTicket.getActiveAmount())) {
				EmpBenefitFlow flw = new EmpBenefitFlow();
				flw.setCash(userTicket.getActiveAmount());
				flw.setEmpId(userTicket.getUserId());
				flw.setKeyId(userTicket.getId()+"");
				flw.setOpCode("216");
				flw.setOpDate(now);
				flw.setNote("换领商品换得现金券再次分配给员工");
				flw.setTicket(BigDecimal.ZERO);
				flw.setUserName(updName);
				
				dealEmpBenefit(flw);
				
				String note = userTicket.getUsedNote()==null?"":userTicket.getUsedNote();
				
				if(!NumberUtil.isGreaterZero(userTicket.getLeftCnt())) {
					userTicket.setStatus(4);	// 4:已折现
					if(!note.contains("折现")) {
						userTicket.setUsedNote(note+"折现,");
					}
				} else {
					if(!note.contains("折现")) {
						userTicket.setUsedNote(note+"部分折现,");
					}
				}
			}
			
			leftAll = leftAll.add(userTicket.getLeftCnt());
			userExchangeTicketService.update(userTicket);
		}

		// 更新换领商品记录
		// 可分配金额
		BigDecimal amount =supplierTicket.getShareAmount().subtract(shareAmount);
		if(amount.compareTo(BigDecimal.ZERO) != 0) {
			SupplierTicketFlow flw = this.makeSupplierTicketFlow(supplierTicket, "107", amount,shareAmount, "换领商品换得现金券返还预付金额", supplierTicket.getId(), "system");
			supplierTicketFlowService.save(flw);
			supplierTicket.setShareAmount(shareAmount);
		}

		// 剩余共享现金回归到企业账户 货款
		if(NumberUtil.isGreaterZero(shareAmount)) {
			save2EntCash(supplierTicket.getSupplierId(), shareAmount,supplierTicket.getProductName(),supplierTicket.getId());
		}
		supplierTicket.setClearStatus(1);	//1:已清算
		supplierTicket.setClearDate(now);
		// 强制发放=1（发放，有剩余）、2（无剩余不需发放）
		if(NumberUtil.isGreaterZero(leftAll)) {
			supplierTicket.setOfflineFlg(1);
		} else {
			supplierTicket.setOfflineFlg(2);
		}
		supplierExchangeProductService.update(supplierTicket);
		return rnt;
	}


	/**
	 * 换领商品剩余可分配额结算给商家现金账户
	 * @param entId
	 * @param shareAmount
	 * @param productName
	 * @param key
	 */
	private void save2EntCash(Long entId, BigDecimal shareAmount, String productName, Long key) {
		//if(!NumberUtil.isGreaterZero(shareAmount)) return;
		
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(entId, SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),
				"system");

		String desrc = "换领商品换得现金券分给员工后有剩余，计入商家货款.商品：" + productName;
		if (desrc.length() > 200) {
			desrc = desrc.substring(0, 200);
		}
		entBenefitFacade.dealEntBenefit(entId, "120", esa, shareAmount, BigDecimal.ZERO, key, dbUtils.CreateID(), desrc,
				"system", false);
	}
	
	/**
	 * 保存用户换领币使用记录
	 * @param pro
	 * @param supplierTicket
	 * @param div
	 * @param enterpriseUser
	 * @param userTicket
	 * @param opCode
	 * @return
	 */
	private SupplierTicketFlow makeSupplierTicketFlow(SupplierExchangeProduct supplierTicket,String opCode,BigDecimal amount,BigDecimal balance, String note,Long keyId,String userName) {
		SupplierTicketFlow flw= new SupplierTicketFlow();
		flw.setSupplierId(supplierTicket.getSupplierId());
		flw.setTicketId(supplierTicket.getId());
		flw.setTicketType(3);
		flw.setOpDate(new Date());
		flw.setOpCode(opCode);
		flw.setAmount(amount);
		flw.setBalance(balance);
		flw.setNote(note);
		flw.setKeyId(keyId);
		flw.setUpdName(userName);
		
		//supplierTicketFlowService.save(flw);
		
		return flw;
	}
	
	/**
	 * 向员工现金券以抵扣回收换领币
	 * @param flw
	 * @return
	 */
	private int dealEmpBenefit(EmpBenefitFlow flw) {
		
		////////////////////////////////////////////////////////////////////////////////////////
		//计算余额
		//现金余额
		UserBalance ubCash = userBalanceService.findByUserAndCurrencyId(flw.getEmpId(), 0L);
		//现金余额
		UserBalance ubTicket = userBalanceService.findByUserAndCurrencyId(flw.getEmpId(), 1L);
		
		if(ubCash==null||ubTicket==null) return 0;
		//现金余额 = 现金余额+本次操作现金*操作符号sasdd
		BigDecimal cashB = ubCash.getBalance().add(flw.getCash());
		//内购券余额 = 内购券余额+本次操作内购券*操作符号
		BigDecimal ticketB = ubTicket.getBalance();
		////////////////////////////////////////////////////////////////////////////////////////
		

		////////////////////////////////////////////////////////////////////////////////////////
		//本季度额度生成 流水记录
		flw.setId(dbUtils.CreateID());				//id
		flw.setTicketBalance(ticketB);				//内购券余额= 上季度 剩余内购券总额
		flw.setCashBalance(cashB);					//现金余额	=上季度剩余现金总额

		//DB更新
		empBenefitFlowService.insert(flw);
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//余额处理
		//现金
		ubCash.setBalance(cashB);
		userBalanceService.saveOrUpdate(ubCash);
		////////////////////////////////////////////////////////////////////////////////////////

		//正常处理
		return 1;
	}

	private BigDecimal getLeft(UserExchangeTicket uet) {
		return uet.getEmpAvgAmount().subtract(uet.getUsedAmount());
	}
}
