package com.wode.factory.facade.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.SaleDurationUtils;
import com.wode.common.util.SeasonUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.facade.ProductExchangeFacade;
import com.wode.factory.facade.SpecialSaleFacade;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.model.CommissionRefund;
import com.wode.factory.model.CommissionRefundCash;
import com.wode.factory.model.CommissionRefundDetail;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.service.CommissionRefundCashService;
import com.wode.factory.service.CommissionRefundDetailService;
import com.wode.factory.service.CommissionRefundService;
import com.wode.factory.service.SaleBillService;
import com.wode.factory.service.SaleDetailService;
import com.wode.factory.service.SaleDurationParamService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierCloseCmdService;
import com.wode.factory.service.SupplierDurationVoService;

/**
 * Created by gaoyj on 2015/11/09.
 */
@Service("supplierCloseFacade")
@Transactional("tm_factory")
public class SupplierCloseFacadeImpl implements SupplierCloseFacade {

	@Autowired
    private SupplierCloseCmdService supplierCloseCmdService;
	@Autowired
    private SupplierDurationVoService sds;
	@Autowired
	private EntBenefitFacade entBenefitFacade;
	@Autowired
    private SaleDetailService saleDetailService;
	@Autowired
    private SaleBillService saleBillService;
	@Autowired
	private CommissionRefundDetailService commissionRefundDetailService;
	@Autowired
	private CommissionRefundService commissionRefundService;
	@Autowired
	DBUtils dbUtils;
	@Autowired
	SubOrderService subOrderService;
	@Autowired
	SpecialSaleFacade specialSaleFacade;
	@Autowired 
	CommissionRefundCashService commissionRefundCashService;
	@Autowired
	ProductExchangeFacade productExchangeFacade;
	//账期规则表
	@Autowired
	SaleDurationParamService saleDurationParamService;

	private Date saleStart =TimeUtil.strToDate("2017-04-28 00:00:00");
	private Date saleEnd =TimeUtil.strToDate("2017-04-28 23:59:59");
	private String saleSupplierIds = ",1019589081269290,2036300385781233,1877214328161663,1869536462833520,1848930747942757,2115187423708250,2215068720104860,";
	
	@Override
	public ActResult<Long> execClose(SupplierCloseCmd entity,String supplierName,String billId) {
		
		////////////////////////////////////////////////////////////
		//根据账期生成结算单
		Date createTime = new Date();
		//********************做成对账单明细数据*************************

		//结算表id
		Long saleBillId = dbUtils.CreateID();
		ActResult<Long> rtn = ActResult.success(saleBillId);

		SupplierDuration sd = sds.getById(entity.getSupplierId());
		List<SaleDetail> lst = null;
		List<SaleDetail> specilSales = new ArrayList<SaleDetail>();
		List<SaleDetail> exchangeSales = new ArrayList<SaleDetail>();
		
		// 先款日结 2X1
		boolean payFirst=sds.isPayFirst(sd);
		if(payFirst) {
			 lst = saleDetailService.countByOrderEx(entity);
		} else {
			 lst = saleDetailService.countByOrder(entity);
		}
		Map<String,String> mapSubId = new HashMap<String,String>();
		//_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_
		//没有明细时不生成账单
		if(lst!=null && lst.size() > 0) {
		//_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_
			// 判断是否为满金额结算的商家
			if (sd.getSaleDurationKey().startsWith("3")) {
				Integer fixedAmount = Integer.MAX_VALUE;//固定金额结算，这里默认值给最大
				Boolean fixedAmountPeriod = false;//是否为固定金额账期
				// 这里计算是否已经超过账期，如果超过账期必须结算 不用满足金额
				long a = (entity.getCloseEnd().getTime() - entity.getCloseStart().getTime()) / (24 * 60 * 60 * 1000) + 1;
				if (a >= SaleDurationUtils.getDayBySaleDurationKey(sd.getSaleDurationKey())) {
					fixedAmountPeriod = true;
				}else{
					// 如果小于账期则进行金额判断
					// 取出对应账期的金额
					SaleDurationParam saleDurationParam = saleDurationParamService.selectByKey(sd.getSaleDurationKey());
					if (null != saleDurationParam ) {
							fixedAmount = Integer.parseInt(saleDurationParam.getValue());// 这里取出账期满足的金额条件
					}
				}
				
				//如果是账期到了就可以结算
				if(fixedAmountPeriod){
					dealSaleDetail(sd,entity.getEnterpriseId(), lst, specilSales, exchangeSales, mapSubId, payFirst, createTime, saleBillId);
				}else{
				    BigDecimal count = new BigDecimal(0);
					for (SaleDetail saleDetail : lst) {
						if(saleDetail.getStatus() == -1){
							count = count.subtract(saleDetail.getRealPrice());
						}else{
							count = count.add(saleDetail.getRealPrice());
						}
					}
					if(count.compareTo(new BigDecimal(fixedAmount)) >= 0){
						dealSaleDetail(sd,entity.getEnterpriseId(), lst, specilSales, exchangeSales, mapSubId, payFirst, createTime, saleBillId);
					}else{
						rtn.setSuccess(false);
						rtn.setMsg("该商户本期未满足结算条件，商家id：" + entity.getSupplierId());
						//特定错误码，为固定期限结算而准备
						rtn.setData(3333L);
					}
				}
			}else{
				//不是3系列账期走原有逻辑
				dealSaleDetail(sd,entity.getEnterpriseId(), lst, specilSales, exchangeSales, mapSubId, payFirst, createTime, saleBillId);
			}
	
		} else {
			rtn.setSuccess(false);
			rtn.setMsg("该商户本期没有订单数据，商家id：" + entity.getSupplierId());
		}
	
		//*********************************************************
		
		//********************做成对账单数据****************************
		SaleBill sb = null;
		if(rtn.isSuccess()) {
			sb =saleBillService.sumBydetail(saleBillId);
			//id
			sb.setId(saleBillId);
			//对账单ID
			sb.setBillId(billId);
			//商户id
			sb.setSupplierId(entity.getSupplierId());
			//对账单标题
			sb.setTitle(getBillTitle(entity.getCloseStart(),entity.getCloseEnd()));
			//商户名称（商户的公司名称）
			sb.setName(supplierName);
			//本期需支付的总金额 = 实付金额-佣金费用(已减过)
			//sb.setPayPrice(sb.getPayPrice().subtract(sb.getCommissionPrice()));
			//账单开始日期
			sb.setStartTime(entity.getCloseStart());
			//账单结束日期
			sb.setEndTime(entity.getCloseEnd());
			//创建时间
			sb.setCreateTime(createTime);
			
		}
		//*********************************************************
		////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////
		//根据账期生成佣金返还信息
		if(rtn.isSuccess()) {
			//主表id
			Long refundillId = dbUtils.CreateID();
			BigDecimal half = BigDecimal.ZERO;
			List<CommissionRefundDetail> lst2 = commissionRefundDetailService.countByOrder(entity);

			//_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_
			//没有明细时不生成账单
			if(lst2!=null && lst2.size() > 0) {
			//_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_
				//********************做成对账单明细数据*************************
				//计算佣金
				for (CommissionRefundDetail detail : lst2) {
//					SupplierCategoryVo cv = scMap.get(saleDetail.getCategoryId());
//					if(cv == null) {
//						result=false;
//						errMsg = "数据异常，订单中商品品类（"+ saleDetail.getCategoryId() +"）在商家品类信息中不存在。请与检查 表 t_supplier_category中商家id：" + entity.getSupplierId() + "的数据。";
//						break;					
//					}
//					
					detail.setId(dbUtils.CreateID());
					//
					detail.setCommissionRefundId(refundillId);
					detail.setCreateTime(createTime);
					//数据插入
					commissionRefundDetailService.insert(detail);
				}
				//*********************************************************
				
				
				//********************做成佣金返还记录****************************
				EntSeasonAct act = entBenefitFacade.getEntSeasonAct(entity.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), "佣金返还计算");
				
				CommissionRefund cr =commissionRefundService.sumBydetail(refundillId);
				//id
				cr.setId(refundillId);
				// 佣金返还ID
				cr.setRefundId(billId + "R");
				// 商户id
				cr.setSupplierId(entity.getSupplierId());
				// 本期销售货款
				cr.setSaleAmount(sb.getReceivePrice());
				// 本期销售佣金
				cr.setCurrentCommission(sb.getCommissionPrice());
				// 现金券发放总额
				cr.setGiveCashSum(act.getGiveCashSum());
				
				//-----------------计算现金券抵扣 及现金券佣金返还-------------------------------------------------
				// 未抵扣发放现金券
				BigDecimal dedCash = act.getGiveCashSum();
				CommissionRefundCash crc = commissionRefundCashService.getById(entity.getSupplierId());
				if(crc != null) {
					dedCash = dedCash.subtract(crc.getAmount());
					if(dedCash.compareTo(BigDecimal.ZERO)<=0) {
						dedCash = BigDecimal.ZERO;
					}
				}
				
				// 现金券额度抵用
				if(dedCash.compareTo(cr.getSaleAmount())>0) {
					dedCash = cr.getSaleAmount();
				}

				// 现金券额度抵用
				cr.setDedCash(dedCash);
				// 现金抵用计算佣金
				if(dedCash.compareTo(BigDecimal.ZERO)==0) {
					cr.setDedCashCommission(BigDecimal.ZERO);
				} else {
					cr.setDedCashCommission(cr.getDedCash().multiply(cr.getCurrentCommission()).divide(cr.getSaleAmount(), 2, BigDecimal.ROUND_DOWN));
					
					// 记录累计 返佣抵扣现金券总额
					if(crc == null) {
						crc = new CommissionRefundCash();
						crc.setId(entity.getSupplierId());
						crc.setAmount(dedCash);
						crc.setUpdateTime(createTime);
						
						commissionRefundCashService.save(crc);
					} else {
						crc.setAmount(crc.getAmount().add(dedCash));
						crc.setUpdateTime(createTime);
						
						commissionRefundCashService.update(crc);
					}
				}

				// 返佣抵扣现金券总额
				if(crc == null) {
					cr.setDedCashSum(BigDecimal.ZERO);
				} else {
					cr.setDedCashSum(crc.getAmount());
				}
				//-------------------------------------------------------------------------------------
				
				//-----------------计算员工购买佣金返还-------------------------------------------------------
				if(!isRefound(entity.getSupplierId())) {
					//不返佣
					cr.setBuyCommission(BigDecimal.ZERO);
				} else {
					// 购买总金额-本次现金抵扣
					cr.setBuyCommission(cr.getAmount().subtract(dedCash));
					if(cr.getBuyCommission().compareTo(BigDecimal.ZERO)<=0) {
						// 购买已近全部抵扣
						cr.setBuyCommission(BigDecimal.ZERO);
					} else {
						// 购买计算佣金 = (实付总金额-本次现金抵扣)*总佣金/购买总金额
						cr.setBuyCommission(cr.getBuyCommission().multiply(cr.getCommissionTotal()).divide(cr.getAmount(), 2, BigDecimal.ROUND_DOWN));
						// 所购买商品的佣金点位的百分之五十以现金储值形式返还
						cr.setBuyCommission(cr.getBuyCommission().divide(new BigDecimal(2),2,BigDecimal.ROUND_DOWN));
					}
				}
				//-------------------------------------------------------------------------------------

				//-----------------计算实际返还佣金---------------------------------------------------------
				//注：佣金返还额度达到甲方实缴佣金的，则不再进行佣金返还。 佣金返还总额合计不超过商家实缴佣金的一半
				if(NumberUtil.isGreaterZero(cr.getCurrentCommission())) {
					cr.setCommissionAmount(cr.getCurrentCommission().divide(new BigDecimal(2),2,BigDecimal.ROUND_DOWN));
					if(cr.getDedCashCommission().compareTo(cr.getCommissionAmount())>=0) {
						// 现金券抵扣 已超商家实缴佣金的一半时，按现金抵扣返还
						cr.setCommissionAmount(cr.getDedCashCommission());
					} else {
						if(cr.getDedCashCommission().add(cr.getBuyCommission()).compareTo(cr.getCommissionAmount())>=0) {
							// 现金券抵扣 + 购买佣金返还   已超商家实缴佣金的一半时,按实付佣金一半返还
						} else {
							// 现金券抵扣 + 购买佣金返还  不足商家实缴佣金的一半时,现金券抵扣 + 购买佣金返还 返还
							cr.setCommissionAmount(cr.getDedCashCommission().add(cr.getBuyCommission()));
						}
					}
				} else {
					cr.setCommissionAmount(BigDecimal.ZERO);
				}
				//-------------------------------------------------------------------------------------

				half = cr.getCommissionAmount();
				cr.setCreateTime(createTime);
				commissionRefundService.insert(cr);

				//*********************************************************
				
			} else {
				EntSeasonAct act = entBenefitFacade.getEntSeasonAct(entity.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), "佣金返还计算");
				
				CommissionRefund cr = new CommissionRefund();
				
				//id
				cr.setId(refundillId);
				// 佣金返还ID
				cr.setRefundId(billId + "R");
				// 商户id
				cr.setSupplierId(entity.getSupplierId());
				// amount
				cr.setAmount(BigDecimal.ZERO);
				// freight
				cr.setFreight(BigDecimal.ZERO);
				// commission_amount
				cr.setCommissionAmount(BigDecimal.ZERO);
				// commissionTotal
				cr.setCommissionTotal(BigDecimal.ZERO);
				// fuli
				cr.setFuli(BigDecimal.ZERO);
				// 本期销售货款
				cr.setSaleAmount(sb.getReceivePrice());
				// 本期销售佣金
				cr.setCurrentCommission(sb.getCommissionPrice());
				// 现金券发放总额
				cr.setGiveCashSum(act.getGiveCashSum());
				
				//-----------------计算现金券抵扣 及现金券佣金返还-------------------------------------------------
				// 未抵扣发放现金券
				BigDecimal dedCash = act.getGiveCashSum();
				CommissionRefundCash crc = commissionRefundCashService.getById(entity.getSupplierId());
				if(crc != null) {
					dedCash = dedCash.subtract(crc.getAmount());
					if(dedCash.compareTo(BigDecimal.ZERO)<=0) {
						dedCash = BigDecimal.ZERO;
					}
				}
				
				// 现金券额度抵用
				if(NumberUtil.isGreaterZero(cr.getSaleAmount()) && NumberUtil.isGreaterZero(cr.getCurrentCommission()) ) {
					if(dedCash.compareTo(cr.getSaleAmount())>0) {
						dedCash = cr.getSaleAmount();
					} 
				} else {
					dedCash = BigDecimal.ZERO;
				}

				// 现金券额度抵用
				cr.setDedCash(dedCash);
				// 现金抵用计算佣金
				if(dedCash.compareTo(BigDecimal.ZERO)==0) {
					cr.setDedCashCommission(BigDecimal.ZERO);

					rtn.setMsg("该商户本期没有需要返还的订单数据，商家id：" + entity.getSupplierId());
				} else {
					cr.setDedCashCommission(cr.getDedCash().multiply(cr.getCurrentCommission()).divide(cr.getSaleAmount(), 2, BigDecimal.ROUND_DOWN));
					
					// 记录累计 返佣抵扣现金券总额
					if(crc == null) {
						crc = new CommissionRefundCash();
						crc.setId(entity.getSupplierId());
						crc.setAmount(dedCash);
						crc.setUpdateTime(createTime);
						
						commissionRefundCashService.save(crc);
					} else {
						crc.setAmount(crc.getAmount().add(dedCash));
						crc.setUpdateTime(createTime);
						
						commissionRefundCashService.update(crc);
					}

					// 返佣抵扣现金券总额
					cr.setDedCashSum(crc.getAmount());
					// 计算员工购买佣金返还
					cr.setBuyCommission(BigDecimal.ZERO);
					// 按现金抵扣返还
					cr.setCommissionAmount(cr.getDedCashCommission());

					half = cr.getCommissionAmount();
					cr.setCreateTime(createTime);
					commissionRefundService.insert(cr);
				}
			}
			
			//佣金返还部分计入账单表
			sb.setRefundAmount(half);
			sb.setRefundId(half.compareTo(BigDecimal.ZERO)==0?null:refundillId);
			
			//实付金额 已包含运费及佣金内容
			if("211".equals(sd.getSaleDurationKey())) {
				//先款日结佣金单收
				
				//结算内容不含佣金
				//本期需支付的总金额 =不含佣金 （实付金额+佣金总额(还原)，不减佣金返还）
				sb.setPayPrice(sb.getPayPrice().add(sb.getCommissionPrice()==null?BigDecimal.ZERO:sb.getCommissionPrice()));
				sb.setCloseType(6);		//货款+运费
				sb.setCloseNote("账期内货款及运费结算，佣金另行结算");
				
			} else if("221".equals(sd.getSaleDurationKey())) {
				//先款日结运费单收
				
				//结算内容不含运费
				//本期需支付的总金额 = 实付金额-运费+佣金返还
				sb.setPayPrice(sb.getPayPrice().subtract(sb.getCarriagePrice()==null?BigDecimal.ZERO:sb.getCarriagePrice()).add(half));
				sb.setCloseType(5);		//货款+佣金
				sb.setCloseNote("账期内货款及佣金结算，运费另行结算");
			} else {
				//本期需支付的总金额 = 实付金额+佣金返还
				sb.setPayPrice(sb.getPayPrice().add(half));
				sb.setCloseType(7);		//货款+运费+佣金
				sb.setCloseNote("账期内货款、运费、佣金结算");
			}

			sb.setRelationType(0);			//0:未关联
			sb.setSaleDurationKey(sd.getSaleDurationKey());
			sb.setPayType(1);				//结算到商家现金账户
			saleBillService.insert(sb);
			
			for (String key : mapSubId.keySet()) {
				subOrderService.updateToClose(key, saleBillId);
			}
		}
		//*********************************************************
		////////////////////////////////////////////////////////////

		if(!exchangeSales.isEmpty()) {
			productExchangeFacade.ExchangeOrderCount(entity.getEnterpriseId(), exchangeSales);
		}
		return rtn;
	}
	
	public boolean isOnSale(Date date,Long sid) {
		if(date==null) return false;
		
		if(saleStart==null || saleEnd == null) return false;
		
		if((date.compareTo(saleStart) >=0) && (date.compareTo(saleEnd)<=0)) {
			return saleSupplierIds.contains(","+sid+",");
		} else {
			return false;
		}
	}

	public boolean isRefound(Long sid) {
		if(924144373204600L==sid) {
			//厦门极色电子商务有限公司
			return false;
		} else if(940404163806607L ==sid) {
			//云浮市乐佰世界贸易有限公司
			return false;
		}
		return true;
	}
	
	@Override
	public void makeFistDuration(Long supplierId, Date execDate) {
		////////////////////////////////////////////////////////////
		//计算并生成下一账期的时间记录
		Date[] dates = SaleDurationUtils.getFirstDuration(execDate);
		SupplierCloseCmd next = new SupplierCloseCmd();
		next.setId(dbUtils.CreateID());
		next.setSupplierId(supplierId);				//供应商id
		next.setExecDate(dates[2]);					//执行日期
		next.setCloseStart(dates[0]);				//账期开始时刻
		next.setCloseEnd(dates[1]);					//账期结束时刻
		next.setExecStatus("0");					//执行状态0:未执行
		next.setExecResult("0");					//执行结果 0:未知
		next.setCreateTime(new Date());
		next.setCreateUser("结算自动处理");
		
		supplierCloseCmdService.insert(next);
		////////////////////////////////////////////////////////////
	}

	/**
	 * 账单名称生成
	 * @param start
	 * @param end
	 * @return
	 */
	private String getBillTitle(Date start,Date end) {
		SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
		return f.format(start) + "至" + f.format(end) + "对账单";
	}

	@Override
	public void dealCloseCmd(SupplierCloseCmd entity, boolean result,
			Long saleBillId, String errMsg) {

		String key = "";

		////////////////////////////////////////////////////////////
		// 取得商家账期设置信息
		SupplierDuration sd = sds.getById(entity.getSupplierId());
		if (sd == null) {
			result = false;
			errMsg = "商家账期信息没有设置。请与检查 表 t_supplier_duration中商家id：" + entity.getSupplierId() + "的数据。";
		} else {
			key = sd.getSaleDurationKey();
		}
		////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////
		// 如果为固定结算期限失败则不添加新的账期，只修改本账期时间
		if (saleBillId == 3333L) {
			// 更新执行状态
			entity.setExecStatus("0"); // 设置为0:未执行
			entity.setSaleBillId(saleBillId); // 结算单ID
			entity.setErrMsg(errMsg); // 错误信息
			Calendar c = Calendar.getInstance();
			c.setTime(entity.getCloseEnd());
			c.add(Calendar.DATE, 1); // +1天在处理
			entity.setCloseEnd(c.getTime());

			c.setTime(entity.getExecDate());
			c.add(Calendar.DATE, 1); // +1天在处理
			entity.setExecDate(c.getTime());

		} else {

			// 计算并生成下一账期的时间记录
			Date[] dates = SaleDurationUtils.getDuration(key, entity.getExecDate());
			SupplierCloseCmd next = new SupplierCloseCmd();
			next.setId(dbUtils.CreateID());
			next.setSupplierId(entity.getSupplierId()); // 供应商id
			next.setExecDate(dates[2]); // 执行日期
			next.setCloseStart(dates[0]); // 账期开始时刻
			next.setCloseEnd(dates[1]); // 账期结束时刻
			next.setExecStatus("0"); // 执行状态0:未执行
			next.setExecResult("0"); // 执行结果 0:未知
			next.setCreateTime(new Date());
			next.setCreateUser("结算自动处理");

			supplierCloseCmdService.insert(next);
			////////////////////////////////////////////////////////////

			////////////////////////////////////////////////////////////
			// 更新执行状态
			entity.setExecStatus("2"); // 设置为2:已执行状态
			if (result) {
				// 执行成功
				entity.setExecResult("2"); // 2:成功
				entity.setSaleBillId(saleBillId); // 结算单ID
				entity.setErrMsg(errMsg); // 错误信息
			} else {
				// 执行失败
				entity.setExecResult("1"); // 1:失败
				entity.setErrMsg(errMsg); // 错误信息
			}
			////////////////////////////////////////////////////////////
		}
		entity.setUpdateTime(new Date());
		entity.setUpdateUser("结算自动处理");
		supplierCloseCmdService.update(entity);
	}

	@Override
	public ActResult<SaleBill> execRelation(Integer relationType,String title,String note, Long supplierId, String supplierName,
			String billId, List<SaleBill> list,Long createUserid,String updUser,Date startTime,Date endTime,Integer payType,String payNote) {

		// 获取商家账期信息
		SupplierDuration sd = sds.getById(supplierId);
		if("211".equals(sd.getSaleDurationKey()) && relationType!=1) {
			 return ActResult.fail("参数错误");
		} else if("221".equals(sd.getSaleDurationKey()) && relationType!=2) {
			 return ActResult.fail("参数错误");
		}
		
		if(list == null || list.isEmpty())
			 return ActResult.fail("参数错误");

		//结算表id
		Long saleBillId = dbUtils.CreateID();
		Date now = new Date();
		SaleBill entity = new SaleBill();
		entity.setId(saleBillId);
		entity.setSupplierId(supplierId);					//商户id
		entity.setTitle(title);								//对账单标题：如： 2015-11-01至2015-11-31对账单
		entity.setName(supplierName);						//商户名称（商户的公司名称）
		entity.setStartTime(startTime);						//账单开始日期
		entity.setEndTime(endTime);							//账单结束日期
		entity.setReceivePrice(BigDecimal.ZERO);			//代收总货款
		entity.setCarriagePrice(BigDecimal.ZERO);			//运费
		entity.setCommissionPrice(BigDecimal.ZERO);			//佣金费用
		entity.setDeductPrice(BigDecimal.ZERO);				//扣除费用
		entity.setPayStatus(0);								//支付结算状态 0:未审核  1:对账审核通过 2:运营审核通过 3:财务审核通过 4:已付款 -1:对账审核未通过 -2:运营审核未通过 3:财务审核未通过
		entity.setPayPrice(BigDecimal.ZERO);				//本期需支付的总金额
		entity.setPayTime(null);							//付款日期
		entity.setFuCoin(BigDecimal.ZERO);					//福利币使用数量
		entity.setConfirmStatus(0);							//商家确认状态 0：未确认  1：已确认',
		entity.setConfirmTime(null);						//商家确认时间',
		entity.setReceiptStatus(0);							//发票开具状态 0：未开发票  1：已申请  2：已开发票',
		entity.setReceiptTime(null);						//开票时间',
		entity.setPrintStatus(0);							//打印状态：0 :未打印   1：已打印',
		entity.setPrintTime(null);							//开票时间',
		entity.setCreateTime(now);							//创建时间',
		entity.setCreateUserid(createUserid);				//创建人id',
		entity.setIsDelete(0L);								//是否删除  0：未删除   1：已删除',
		entity.setBillId(billId);							//对账单ID',
		entity.setReceiptCode(null);						//发票号码',
		entity.setReceiptId(null);							//发票记录id',
		entity.setRefundId(null);							//返佣表id',
		entity.setRefundAmount(BigDecimal.ZERO);			//返还佣金',
		entity.setCloseType(relationType);					//结算内容 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金',
		entity.setCloseNote(note);							//备注、结算内容',
		entity.setSaleDurationKey(sd.getSaleDurationKey());
		entity.setRelationType(0);							//关联内容0:未关联 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金',
		entity.setPayType(payType);							//1:现金账户 2:其他方式
		entity.setPayNote(payNote);							//支付信息
		for (SaleBill saleBill : list) {
			// 货款总额
			entity.setReceivePrice(entity.getReceivePrice().add(saleBill.getReceivePrice()));
			// 运费总额
			entity.setCarriagePrice(entity.getCarriagePrice().add(saleBill.getCarriagePrice()));
			// 佣金总额
			entity.setCommissionPrice(entity.getCommissionPrice().add(saleBill.getCommissionPrice()));
			// 返还佣金总额
			entity.setRefundAmount(entity.getRefundAmount().add(saleBill.getRefundAmount()));
			
			saleBill.setRelationType(relationType);
			saleBill.setRelationKey(saleBillId);
			saleBill.setRelationDate(now);
			saleBill.setRelationUser(updUser);
			
			saleBillService.update(saleBill);
		}
		
		//结算内容 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金',
		if(relationType == 2) {
			//本期需支付的总金额 = 运费总额
			entity.setPayPrice(entity.getCarriagePrice());
		} else if(relationType == 1) {
			//本期需支付的总金额 = 返佣-佣金
			entity.setPayPrice(entity.getRefundAmount().subtract(entity.getCommissionPrice()));
		}
		saleBillService.insert(entity);
		
		return ActResult.success(entity);
	}
	/**
	 * 将原有逻辑抽成方法
	 * @param lst
	 * @param supplierId 
	 * @param specilSales
	 * @param exchangeSales
	 * @param mapSubId
	 * @param payFirst
	 * @param createTime
	 * @param saleBillId
	 */
	private void dealSaleDetail(SupplierDuration sd, Long supplierId, List<SaleDetail> lst,
			List<SaleDetail> specilSales, List<SaleDetail> exchangeSales, Map<String, String> mapSubId,
			Boolean payFirst, Date createTime, Long saleBillId) {

		//退款订单
		Map<String, SaleDetail> mapReturn=new HashMap<String, SaleDetail>();
		
		//计算佣金
		for (SaleDetail saleDetail : lst) {
//				SupplierCategoryVo cv = scMap.get(saleDetail.getCategoryId());
//				if(cv == null) {
//					result=false;
//					errMsg = "数据异常，订单中商品品类（"+ saleDetail.getCategoryId() +"）在商家品类信息中不存在。请与检查 表 t_supplier_category中商家id：" + entity.getSupplierId() + "的数据。";
//					break;					
//				}
//				
			//换领商品
			if(saleDetail.getSaleKbn() != null) {
				if(saleDetail.getSaleKbn().equals(2)) {
					if(payFirst) {
						try {
							exchangeSales.add((SaleDetail)saleDetail.clone());
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					}
					//换领  实收金额为0
					saleDetail.setHaveCheap(3);
					saleDetail.setRealPrice(BigDecimal.ZERO);
					// 先款商家 无需等到确认收货即可激活换领币
//					if(payFirst) {
//						exchangeSales.add(saleDetail);
//					}
				} else if(saleDetail.getSaleKbn().equals(4)) {
					specilSales.add(saleDetail);
				} else if(saleDetail.getSaleKbn().equals(5)) {
					//试用  实收金额为0
					saleDetail.setHaveCheap(5);
					saleDetail.setRealPrice(saleDetail.getRealPrice().subtract(saleDetail.getBenefitAmount()));
				}	
			}
			saleDetail.setId(dbUtils.CreateID());
			saleDetail.setSaleBillId(saleBillId);
			if(saleDetail.getOwn() == 0) {
				//本企业员工不收佣金
				saleDetail.setCommission(BigDecimal.ZERO);
			} else if(saleDetail.getSaleKbn().equals(2)) {
				//换领不收佣金
				saleDetail.setCommission(BigDecimal.ZERO);
			} else if(saleDetail.getSaleKbn().equals(5)) {
				//试用不收佣金
				saleDetail.setCommission(BigDecimal.ZERO);
			} else if(isOnSale(saleDetail.getPayTime(),supplierId)){
				//活动期间免佣金
				saleDetail.setCommission(BigDecimal.ZERO);
			} else {
				//佣金 = 实付金额 * 佣金比例 /100
				saleDetail.setCommission(saleDetail.getRealPrice().multiply(BigDecimal.valueOf(saleDetail.getCommissionRatio())).divide(BigDecimal.valueOf(100),sd.getScale(),sd.getRoundMode()));
			}
			
			//退款金额处理，退款订单中如果有多个商品，则首个商品的实付金额=实际退款金额，其他商品实付金额=0
			//状态 -1:已退货
			if(saleDetail.getStatus() == -1)
			{
				//如果订单非首次出现
				if(mapReturn.containsKey(saleDetail.getSubOrderId())) {
					//则实付金额清零，退款金额已反映到首次出现该订单的项目中
					saleDetail.setRealPrice(BigDecimal.ZERO);
				} else {
					//实付金额=退款金额
					saleDetail.setRealPrice(saleDetail.getPayPrice());
					mapReturn.put(saleDetail.getSubOrderId(), saleDetail);
				}
			}
			
			if(saleDetail.getStatus() == -1) {
				//退款单
				//实付金额=商退款金额 - 佣金
				if(NumberUtil.isGreaterZero(saleDetail.getCommission())) {
					saleDetail.setPayPrice(saleDetail.getRealPrice().subtract(saleDetail.getCommission()));
				} else {
					saleDetail.setPayPrice(saleDetail.getRealPrice());
				}
			} else {
				//实付金额=商品实付金额+运费-佣金 
				saleDetail.setPayPrice(saleDetail.getRealPrice().add(saleDetail.getCarriagePrice()).subtract(saleDetail.getCommission()));
			}
			//福利清零
			if(saleDetail.getFuCoin() == null) {
				saleDetail.setFuCoin(BigDecimal.ZERO);
			}
			saleDetail.setCreateTime(createTime);
			//数据插入
			saleDetailService.insert(saleDetail);
			
			if(!mapSubId.containsKey(saleDetail.getSubOrderId())){
				mapSubId.put(saleDetail.getSubOrderId(), saleDetail.getSubOrderId());
			}
		}
	}
}
