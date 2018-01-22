package com.wode.factory.company.facade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.facade.EmpBenefitFacade;
import com.wode.factory.company.facade.EntBenefitFacade;
import com.wode.factory.company.service.EntBenefitApprService;
import com.wode.factory.company.service.EntBenefitFlowService;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.company.service.EntSeasonActService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.model.EntBenefitFlow;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.supplier.service.SaleBillService;
import com.wode.factory.supplier.service.SupplierTransferService;

@Service("entBenefitFacade")
public class EntBenefitFacadeImpl implements EntBenefitFacade{

	@Autowired
	private EntSeasonActService entSeasonActService;
	@Autowired
	private EntBenefitFlowService entBenefitFlowService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private EmpBenefitFacade empBenefitFacade;
	@Autowired
	private EntBenefitApprService entBenefitApprService;
	@Autowired
	private SaleBillService saleBillService;
	@Autowired
	private SupplierTransferService supplierTransferService;
	
	@Autowired
	DBUtils dbUtils;
	
	@Override
	@Transactional
	public int autoSeason(Long entId, int year, int season, String updUser) {
		////////////////////////////////////////////////////////////////////////////////////////
		//根据操作代码取得操作属性
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		////////////////////////////////////////////////////////////////////////////////////////
		
		//上一季度福利总额取得
		EntSeasonAct esa = new EntSeasonAct();
		esa.setEnterpriseId(entId); //企业id
		esa.setUseFlg("1");			//使用标志 1：使用中
		List<EntSeasonAct> rtn= entSeasonActService.selectByModel(esa);
		
		if(rtn==null || rtn.size() == 0) {
			//上季度企业福利记录不存在
			EntSeasonAct ist = new EntSeasonAct();
			ist.setId(dbUtils.CreateID());				//id
			ist.setEnterpriseId(entId);					//企业 id
			ist.setLastTicketSum(BigDecimal.ZERO);		//上季度剩余内购券总额
			ist.setCurrentTicketSum(BigDecimal.ZERO);	//本季度获取内购券总额
			ist.setCurrentTransferSum(BigDecimal.ZERO);	//本季度划入福利额度
			ist.setAllTicketSum(BigDecimal.ZERO); 		//内购券总额
			ist.setGiveTicketSum(BigDecimal.ZERO); 		//已发放内购券总额
			ist.setTransfeTicketSum(BigDecimal.ZERO); 	//已划拨内购券总额
			ist.setLastCashSum(BigDecimal.ZERO);		//上季度剩余现金总额
			ist.setCurrentCashSum(BigDecimal.ZERO);		//本季度获取现金总额
			ist.setAllCashSum(BigDecimal.ZERO);			//现金总额
			ist.setGiveCashSum(BigDecimal.ZERO);		//已发放现金总额
			ist.setCurYear(year+"");					//年度
			ist.setCurSeason(season+"");				//季度
			ist.setUseFlg("1");							//有效标志
			ist.setCreateDate(new Date());				//创建时间
			ist.setUpdateDate(new Date());				//更新时间
			ist.setUpdateUser(updUser);					//更新者
			
			//保存到DB
			entSeasonActService.save(ist);
			return 1;
		} else {
			//上季度企业福利记录存在
			EntSeasonAct lst = rtn.get(0);
			
			//当季度记录做成
			EntSeasonAct ist = new EntSeasonAct();
			ist.setId(dbUtils.CreateID());				//id
			ist.setEnterpriseId(entId);					//企业 id
			//上季度 剩余内购券总额=内购券总额-已发放内购券总额-已划拨内购券总额
			ist.setLastTicketSum(lst.getAllTicketSum().subtract(lst.getGiveTicketSum()).subtract(lst.getTransfeTicketSum()));
			ist.setCurrentTicketSum(BigDecimal.ZERO);	//本季度获取内购券总额
			ist.setCurrentTransferSum(BigDecimal.ZERO);	//本季度划入福利额度
			//内购券总额=上季度 剩余内购券总额
			ist.setAllTicketSum(ist.getLastTicketSum()); 		
			ist.setGiveTicketSum(BigDecimal.ZERO); 		//已发放内购券总额
			ist.setTransfeTicketSum(BigDecimal.ZERO); 	//已划拨内购券总额
			//上季度剩余现金总额 =上季度 现金总额 - 已发放现金总额
			ist.setLastCashSum(lst.getAllCashSum().subtract(lst.getGiveCashSum()));
			ist.setCurrentCashSum(BigDecimal.ZERO);		//本季度获取现金总额
			//现金总额=上季度剩余现金总额 +　本季度获取现金总额
			ist.setAllCashSum(ist.getLastCashSum());
			ist.setGiveCashSum(BigDecimal.ZERO);		//已发放现金总额
			
			////////////////////////////////////////////////////////////////////////////////////////
			//上季度企业福利记录失效
			lst.setUseFlg("0");
			lst.setUpdateDate(new Date());
			lst.setUpdateUser(updUser);
			
			//生成流水记录 
			//上季度额度作废 流水记录
			EntBenefitFlow lstF = new EntBenefitFlow();
			lstF.setId(dbUtils.CreateID());				//id
			lstF.setEnterpriseId(entId);				//企业 id
			lstF.setOpDate(new Date());					//日期
			lstF.setOpCode("101");						//操作代码  季度结算流出
			lstF.setTicket(ist.getLastTicketSum());		//内购券= 上季度 剩余内购券总额
			lstF.setTicketBalance(BigDecimal.ZERO);		//内购券余额	清零
			lstF.setCash(ist.getLastCashSum());			//现金=上季度剩余现金总额
			lstF.setCashBalance(BigDecimal.ZERO);		//现金余额		清零
			lstF.setNote(mapCode.get("101").getDescr());	//备注
			lstF.setKeyId(lst.getId());					//关键字
			lstF.setUserName(updUser);					//操作员名
			
			//DB更新
			entBenefitFlowService.save(lstF);
			entSeasonActService.update(lst);
			////////////////////////////////////////////////////////////////////////////////////////

			////////////////////////////////////////////////////////////////////////////////////////
			//本季度额度生成 流水记录
			EntBenefitFlow istF = new EntBenefitFlow();
			istF.setId(dbUtils.CreateID());				//id
			istF.setEnterpriseId(entId);				//企业 id
			istF.setOpDate(new Date());					//日期
			istF.setOpCode("100");						//操作代码  季度结算流入
			istF.setTicket(ist.getLastTicketSum());		 //内购券= 上季度 剩余内购券总额
			istF.setTicketBalance(ist.getLastTicketSum());//内购券余额= 上季度 剩余内购券总额
			istF.setCash(ist.getLastCashSum());			//现金=上季度剩余现金总额
			istF.setCashBalance(ist.getLastCashSum());	//现金余额	=上季度剩余现金总额
			istF.setNote(mapCode.get("100").getDescr());		//备注
			istF.setKeyId(ist.getId());					//关键字
			istF.setUserName(updUser);					//操作员名
			
			//DB更新
			entBenefitFlowService.save(istF);

			EntParamCode op = mapCode.get("102");
			if(op!=null && "0".equals(op.getStopFlg())) {
				//本季度额度现金累计 流水记录
				EntBenefitFlow istF2 = new EntBenefitFlow();
				istF2.setId(dbUtils.CreateID());			//id
				istF2.setEnterpriseId(entId);				//企业 id
				istF2.setOpDate(new Date());				//日期
				istF2.setOpCode("102");						//操作代码  季度结算 现金累计
				istF2.setTicket(BigDecimal.ZERO);			//内购券
				istF2.setTicketBalance(ist.getLastTicketSum());//内购券余额= 上季度 剩余内购券总额
				istF2.setCash(lst.getGiveCashSum());		 //现金=上季度已发放现金总额
				istF2.setCashBalance(ist.getLastCashSum());  //现金余额=上季度剩余现金总额
				istF2.setNote(mapCode.get("102").getDescr());	//备注
				istF2.setKeyId(lst.getId());				//关键字
				istF2.setUserName(updUser);					//操作员名
	
				//现金累计
				//上季度剩余现金总额 =上季度 现金总额
				ist.setLastCashSum(lst.getAllCashSum());
				ist.setAllCashSum(ist.getLastCashSum());
				//已发放现金总额=上季度 已发放现金总额
				ist.setGiveCashSum(lst.getGiveCashSum());	//已发放现金总额

				//DB更新
				entBenefitFlowService.save(istF2);
			}
			
			ist.setCurYear(year+"");					//年度
			ist.setCurSeason(season+"");				//季度
			ist.setUseFlg("1");							//有效标志
			ist.setCreateDate(new Date());				//创建时间
			ist.setUpdateDate(new Date());				//更新时间
			ist.setUpdateUser(updUser);					//更新者
			
			//DB更新
			entSeasonActService.save(ist);
			////////////////////////////////////////////////////////////////////////////////////////
		}
		return 0;
	}

	@Override
	@Transactional
	public int grantBenefit(Long apprId, Long entId, BigDecimal limit,
			Long flowId ,String updName) {

		////////////////////////////////////////////////////////////////////////////////////////
		//取得审批记录
		EntBenefitAppr appr = entBenefitApprService.getById(apprId);
		if(appr == null) return 0;  //系统异常
		
		if(1 != appr.getStatus()) return -1; //该记录已审批过
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//审批记录更新
		appr.setApplyLimit(limit);
		appr.setStatus(2);
		appr.setUpdateDate(new Date());
		appr.setUpdateUser(updName);
		entBenefitApprService.update(appr);
		////////////////////////////////////////////////////////////////////////////////////////
		
		//账目处理
		return this.grantBenefit(appr.getEnterpriseId(), Integer.parseInt(appr.getCurYear()), Integer.parseInt(appr.getCurSeason()), limit, flowId, updName);
	}
	
	@Override
	public int grantBenefit(Long entId, int year, int season,
			BigDecimal amount, Long flowId,String updUser) {

		////////////////////////////////////////////////////////////////////////////////////////
		//上一季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(entId, year, season, updUser);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//账务处理
		this.dealEntBenefit(entId, "110", esa, BigDecimal.ZERO, amount.abs(),
				esa.getId(), flowId, null, updUser,false);
		////////////////////////////////////////////////////////////////////////////////////////
		
		return 1;
	}

	@Override
	@Transactional
	public int bankCash(Long entId, int year, int season, BigDecimal amount,Long flowId, String desrc,
			String updUser) {
		////////////////////////////////////////////////////////////////////////////////////////
		//季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(entId, year, season, updUser);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//账务处理
		this.dealEntBenefit(entId, "112", esa, amount.abs(), BigDecimal.ZERO,
				esa.getId(), flowId, desrc, updUser,false);
		////////////////////////////////////////////////////////////////////////////////////////
		
		return 1;
	}

	@Override
	@Transactional
	public int giveBenefit(Long entId, int year, int season,
			 BigDecimal cashSum,BigDecimal ticketSum, List<Long> empIds, List<BigDecimal> absCashs,
			List<BigDecimal> absTickets, List<String> empNames, String updUser,List<String> exBenefitTypes) {

		////////////////////////////////////////////////////////////////////////////////////////
		//季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(entId, year, season, updUser);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////
		//企业账目
		Long flowId = dbUtils.CreateID();
		int rtn = this.dealEntBenefit(entId, "117", esa, cashSum.abs(),
				ticketSum.abs(), esa.getId(), flowId, null, updUser);

		//余额不足
		if(rtn <= 0) return rtn;
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//发放到员工
		for (int i = 0; i < empIds.size(); i++) {
			empBenefitFacade.dealEmpBenefit(empIds.get(i), "216",
					absCashs.get(i).abs(), absTickets.get(i).abs(), updUser,
					flowId+"", dbUtils.CreateID(), entId, empNames.get(i), null,year,season,exBenefitTypes.get(i));
		}
		////////////////////////////////////////////////////////////////////////////////////////
		return 1;
	}
	
	@Override
	@Transactional
	public int reCoverBenefit(Long entId, int year, int season,
			BigDecimal cashSum, BigDecimal ticketSum, List<Long> empIds,
			List<BigDecimal> absCashs, List<BigDecimal> absTickets,
			List<String> empNames, String updUser) throws BenefitLessException {

		////////////////////////////////////////////////////////////////////////////////////////
		//季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(entId, year, season, updUser);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////
		//企业账目
		Long flowId = dbUtils.CreateID();
		this.dealEntBenefit(entId, "116", esa, cashSum.abs(), ticketSum.abs(),
				esa.getId(), flowId, null, updUser,false);

		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		int rtn = 1;
		//回收福利
		for (int i = 0; i < empIds.size(); i++) {
			rtn = empBenefitFacade.dealEmpBenefit(empIds.get(i), "217",
					absCashs.get(i).abs(), absTickets.get(i).abs(), updUser,
					flowId+"", dbUtils.CreateID(), entId, empNames.get(i), null,year,season,"");
			
			if (rtn <= 0) {
				throw new BenefitLessException(empNames.get(i),empIds.get(i)+"");
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////
		return rtn;
	}

	@Override
	public int transferBenefit(Long entId, int year ,int season,
			BigDecimal cashSum,BigDecimal ticketSum, 
			List<Long> subEntIds, List<BigDecimal> absCashs,
			List<BigDecimal> absTickets,
			String opEntName, String updUser) {
		
		////////////////////////////////////////////////////////////////////////////////////////
		//季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(entId, year, season, updUser);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////
		//企业账目
		//检查可划拨的福利  可划拨的福利=本季度全部额度/2 - 已划拨额度
		if (esa.getAllTicketSum().divide(new BigDecimal(2))
				.subtract(esa.getTransfeTicketSum()).compareTo(ticketSum) < 0) {
			//可划拨的福利 < 本次划拨福利
			//处理终止，提示可划拨福利不足
			return -4;
		}
		Long flowId = dbUtils.CreateID();
		int rtn = this.dealEntBenefit(entId, "119", esa, cashSum.abs(), ticketSum.abs(),
				esa.getId(), flowId, null, updUser);
		//余额不足
		if(rtn <= 0) return rtn;
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////
		//划拨福利
		for (int i = 0; i < subEntIds.size(); i++) {
			EntSeasonAct sub_esa = this.getEntSeasonAct(subEntIds.get(i), year, season, updUser);
			this.dealEntBenefit(subEntIds.get(i), "118", sub_esa,
					absCashs.get(i), absTickets.get(i), flowId,
					dbUtils.CreateID(), opEntName + " 划拨来的福利。", updUser,false);

		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		return 1;
	}

	/**
	 * 企业账务处理
	 * @param entId	企业id
	 * @param opCode 操作代码
	 * @param esa 操作前企业账目（当季度）
	 * @param absCash 现金（不要符号）
	 * @param absTicket 内购券（不要符号）
	 * @param updUser 操作员
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@Override
	@Transactional
	public int dealEntBenefit(Long entId, String opCode, EntSeasonAct esa,
			BigDecimal absCash, BigDecimal absTicket, Long key,
			Long flowId, String desrc, String updUser) {
		return this.dealEntBenefit(entId, opCode, esa, absCash, absTicket, key, flowId, desrc, updUser, true);
	}
	/**
	 * 企业账务处理
	 * @param entId	企业id
	 * @param opCode 操作代码
	 * @param esa 操作前企业账目（当季度）
	 * @param absCash 现金（不要符号）
	 * @param absTicket 内购券（不要符号）
	 * @param updUser 操作员
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	@Override
	@Transactional
	public int dealEntBenefit(Long entId, String opCode, EntSeasonAct esa,
			BigDecimal absCash, BigDecimal absTicket, Long key,
			Long flowId, String desrc, String updUser,boolean banlanceCheck) {

		if(!"1".equals(esa.getUseFlg())) return -1001;
		
		////////////////////////////////////////////////////////////////////////////////////////
		//根据操作代码取得操作属性
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		EntParamCode op = null;
		if(mapCode != null) {
			op = mapCode.get(opCode);
		}
		
		//系统异常 该操作不存在
		if(op==null) return 0;
		
		//系统异常 该操作已废止
		if("1".equals(op.getStopFlg())) return 0;
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//计算余额
		//符号
		BigDecimal signed = new BigDecimal(op.getValue());
		//现金余额 = 当季全部现金（上季度残+本季度）-已下发现金+本次操作现金*操作符号
		BigDecimal cashB = esa.getAllCashSum().subtract(esa.getGiveCashSum()).add(absCash.multiply(signed));
		//内购券余额 = 当季全部内购券（上季度残+本季度）-已下内购券+本次操作内购券*操作符号
		BigDecimal ticketB = esa.getAllTicketSum().subtract(esa.getGiveTicketSum()).subtract(esa.getTransfeTicketSum()).add(absTicket.multiply(signed));
		
		int rtn = 0;
		//操作后 现金余额小于0 处理终止 提示现金余额不足
		if(banlanceCheck && cashB.compareTo(BigDecimal.ZERO) == -1) rtn = -1;  //现金余额不足
		
		//操作后 内购券余额小于0 处理终止 提示内购券余额不足 (rtn=-3 表示两方不做)
		if(banlanceCheck && ticketB.compareTo(BigDecimal.ZERO) == -1) rtn += -2;  //现金余额不足  
		
		if(rtn < 0) return rtn;
		////////////////////////////////////////////////////////////////////////////////////////
		

		////////////////////////////////////////////////////////////////////////////////////////
		//账目处理
		if(signed.compareTo(BigDecimal.ZERO) > 0) {
			if("116".equals(opCode)) {
				//回收福利
				
				//本季度下发现金=下发现金-本次操作现金
				esa.setGiveCashSum(esa.getGiveCashSum().subtract(absCash));

				//本季度下发内购券=下发内购券-本次操作内购券
				esa.setGiveTicketSum(esa.getGiveTicketSum().subtract(absTicket));				
			} else {
				//现金存入、批准申请额度或者上级划拨福利到账
				
				//本季度获得现金=本季度获得现金+本次操作现金
				esa.setCurrentCashSum(esa.getCurrentCashSum().add(absCash));
				//现金余额=本季度获得现金+上季度残
				esa.setAllCashSum(esa.getCurrentCashSum().add(esa.getLastCashSum()));

				if("118".equals(opCode)) {
					// 拨入福利
					// 本季度划入福利额度 = 本季度划入福利额度 +本次操作内购券
					esa.setCurrentTransferSum(esa.getCurrentTransferSum().add(absTicket));
				} else {
					//本季度获得内购券=本季度获得内购券+本次操作内购券
					esa.setCurrentTicketSum(esa.getCurrentTicketSum().add(absTicket));
				}
				//内购券余额=本季度获得内购券+本季度划入福利额度+上季度残
				esa.setAllTicketSum(esa.getCurrentTicketSum().add(esa.getCurrentTransferSum()).add(esa.getLastTicketSum()));
			}
		} else {
			if("117".equals(opCode)) {
				//发放福利
				
				//本季度下发现金=下发现金+本次操作现金
				esa.setGiveCashSum(esa.getGiveCashSum().add(absCash));

				//本季度下发内购券=下发内购券+本次操作内购券
				esa.setGiveTicketSum(esa.getGiveTicketSum().add(absTicket));	
			} else {
				//划拨福利
				//本季度获得现金=本季度获得现金+本次操作现金
				esa.setCurrentCashSum(esa.getCurrentCashSum().subtract(absCash));
				//现金余额=本季度获得现金+上季度残
				esa.setAllCashSum(esa.getCurrentCashSum().add(esa.getLastCashSum()));
				
				//本季度划拨内购券=划拨内购券+本次操作内购券
				esa.setTransfeTicketSum(esa.getTransfeTicketSum().add(absTicket));
			}
		}
		esa.setUpdateDate(new Date());
		esa.setUpdateUser(updUser);
		
		//本季度额度生成 流水记录
		EntBenefitFlow istF = new EntBenefitFlow();
		istF.setId(flowId);				//id
		istF.setEnterpriseId(entId);				//企业 id
		istF.setOpDate(new Date());					//日期
		istF.setOpCode(op.getCode());				//操作代码 
		istF.setTicket(absTicket);		 			//内购券= 上季度 剩余内购券总额
		istF.setTicketBalance(ticketB);				//内购券余额= 上季度 剩余内购券总额
		istF.setCash(absCash);						//现金=上季度剩余现金总额
		istF.setCashBalance(cashB);					//现金余额	=上季度剩余现金总额
		if(StringUtils.isEmpty(desrc)) {
			istF.setNote(op.getDescr());			//备注
		} else {
			istF.setNote(desrc);					//备注
		}
		istF.setKeyId(key);							//关键字
		istF.setUserName(updUser);					//操作员名

		//DB更新
		entBenefitFlowService.save(istF);
		entSeasonActService.update(esa);
		
		//正常处理
		return 1;
	}
	
	/**
	 * 取得企业当前季度福利账目
	 * @param entId
	 * @param year
	 * @param season
	 * @param updUser
	 * @return
	 */
	@Transactional
	public EntSeasonAct getEntSeasonAct(Long entId, int year, int season,String updUser) {
		EntSeasonAct esa = new EntSeasonAct();
		esa.setEnterpriseId(entId); //企业id
		esa.setCurYear(year+"");
		esa.setCurSeason(season+"");
		//查询企业某季度的福利信息 
		List<EntSeasonAct> rtn= entSeasonActService.selectByModel(esa);

		if(rtn==null || rtn.size() == 0) {
			//不存在当前季度企业福利账目 
			//当前账目自动生成
			this.autoSeason(entId, year, season, updUser);
			rtn= entSeasonActService.selectByModel(esa);
		}

		if(rtn==null || rtn.size() == 0) {
			return null;
		} else {
			return rtn.get(0);
		}
	}

	@Override
	@Transactional
	public int paySaleBill(Long saleBillId, String updName, Long flowId) {
		////////////////////////////////////////////////////////////////////////////////////////
		//取得审批记录
		SaleBill saleBill = saleBillService.getById(saleBillId);
		if(saleBill == null) return 0;  //系统异常
		
		if(3 != saleBill.getPayStatus()) return -1; //该记录已审批过
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//上一季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(saleBill.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), updName);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//对账单更新
		saleBill.setPayStatus(4);
		saleBill.setPayTime(new Date());
		saleBillService.update(saleBill);
		////////////////////////////////////////////////////////////////////////////////////////
		
		//账目处理
		return this.dealEntBenefit(saleBill.getSupplierId(), "120", esa, saleBill.getPayPrice(), BigDecimal.ZERO, saleBillId, flowId, "系统账单结算，货款入账。结算单号："+saleBill.getBillId(), updName,false);
	}

	@Override
	@Transactional
	public int cashTransfer(Long transferId, String flowCd, String updName, Long flowId) {
		
		////////////////////////////////////////////////////////////////////////////////////////
		//取得审批记录
		SupplierTransfer transfer = supplierTransferService.getById(transferId);
		if(transfer == null) return 0;  //系统异常
		
		if(2 != transfer.getStatus()) return -2; //该记录已审批过
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//上一季度福利总额取得
		EntSeasonAct esa = this.getEntSeasonAct(transfer.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), updName);
		if(esa == null) return 0;
		if(!"1".equals(esa.getUseFlg())) return -1001;
		////////////////////////////////////////////////////////////////////////////////////////

		//账目处理
		int rnt= this.dealEntBenefit(transfer.getSupplierId(), "123", esa, transfer.getAmount(), BigDecimal.ZERO, transferId, flowId, "提现转出，交易流水号："+flowCd, updName);
		
		////////////////////////////////////////////////////////////////////////////////////////
		//对账单更新
		if(rnt==1) {
			transfer.setStatus(3);
			transfer.setPayDate(new Date());
			transfer.setPayFlowCode(flowCd);
			transfer.setUpdateTime(new Date());
			supplierTransferService.update(transfer);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		return rnt;
	}
}