package com.wode.factory.company.facade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.facade.EmpBenefitFacade;
import com.wode.factory.company.service.EmpBalanceService;
import com.wode.factory.company.service.EmpBenefitFlowService;
import com.wode.factory.company.service.EmpSeasonActService;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.EmpSeasonAct;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserBalance;

@Service("empBenefitFacade")
public class EmpBenefitFacadeImpl  implements EmpBenefitFacade{

	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private EmpBenefitFlowService empBenefitFlowService;
	@Autowired
	private EmpSeasonActService empSeasonActService;
	@Autowired
	private EmpBalanceService empBalanceService;


	@Autowired
	private DBUtils dbUtils;
	
	@Override
	@Transactional
	public int dealEmpBenefit(Long empId, String opCode, BigDecimal absCash,
			BigDecimal absTicket, String updUser, String key, Long flowId,
			Long entId, String empName, String desrc,int year, int season,String exBenefitType) {
		
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
		//现金余额
		UserBalance ubCash = empBalanceService.fetchByUserAndName(empId, "balance");
		//现金余额
		UserBalance ubTicket = empBalanceService.fetchByUserAndName(empId, "companyTicket");
		
		//符号
		BigDecimal signed = new BigDecimal(op.getValue());
		//现金余额 = 现金余额+本次操作现金*操作符号
		BigDecimal cashB = ubCash.getBalance().add(absCash.multiply(signed));
		//内购券余额 = 内购券余额+本次操作内购券*操作符号
		BigDecimal ticketB = ubTicket.getBalance().add(absTicket.multiply(signed));
		
		int rtn = 0;
		//操作后 现金余额小于0 处理终止 提示现金余额不足
		if(cashB.compareTo(BigDecimal.ZERO) == -1) rtn = -1;  //现金余额不足
		
		//操作后 内购券余额小于0 处理终止 提示内购券余额不足 (rtn=-3 表示两方不做)
		if(ticketB.compareTo(BigDecimal.ZERO) == -1) rtn += -2;  //现金余额不足  
		
		if(rtn < 0) return rtn;
		////////////////////////////////////////////////////////////////////////////////////////
		

		////////////////////////////////////////////////////////////////////////////////////////
		//本季度额度生成 流水记录
		EmpBenefitFlow istF = new EmpBenefitFlow();
		istF.setId(flowId);				//id
		istF.setEmpId(empId);						//员工（用户）id
		istF.setOpDate(new Date());					//日期
		istF.setOpCode(op.getCode());				//操作代码 
		istF.setTicket(absTicket);		 			//内购券= 上季度 剩余内购券总额
		istF.setTicketBalance(ticketB);				//内购券余额= 上季度 剩余内购券总额
		istF.setCash(absCash);						//现金=上季度剩余现金总额
		istF.setCashBalance(cashB);					//现金余额	=上季度剩余现金总额
		if(StringUtils.isEmpty(desrc)) {
			//订单相关
			//if("203".equals(op.getCode()) || "202".equals(op.getCode())) {
				istF.setNote(op.getDescr() + key);
			//} 
		} else {
			istF.setNote(desrc);					//简要说明
		}
		istF.setKeyId(key);							//关键字
		istF.setUserName(updUser);					//操作员名
		istF.setEnterpriseId(entId);				//企业 id
		istF.setEmpName(empName);;					//企业 id
		istF.setExBenefitType(exBenefitType);		//福利科目 1:生日礼金/2:过节费/空:其他

		//DB更新
		empBenefitFlowService.save(istF);
		////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////////////////////////
		//余额处理
		//现金
		if(absCash.compareTo(BigDecimal.ZERO) > 0) {
			//ubCash.setCurrencyId(1L);
			ubCash.setBalance(cashB);
			empBalanceService.update(ubCash);
		}

		//内购券
		if(absTicket.compareTo(BigDecimal.ZERO) > 0) {
			ubTicket.setBalance(ticketB);
			empBalanceService.update(ubTicket);
		}
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////
		//季度福利发放记录处理
		if("216".equals(op.getCode())) {
			//发放福利
			EmpSeasonAct esa = empSeasonActService.getEmpSeasonAct(empId, year, season,entId);
			esa.setGiveCashSum(esa.getGiveCashSum().add(absCash));
			esa.setGiveTicketSum(esa.getGiveTicketSum().add(absTicket));
			
			empSeasonActService.saveSeasonAct(esa, updUser);
		} else if("217".equals(op.getCode())) {
			//回收福利
			EmpSeasonAct esa = empSeasonActService.getEmpSeasonAct(empId, year, season,entId);
			esa.setGiveCashSum(esa.getGiveCashSum().subtract(absCash));
			esa.setGiveTicketSum(esa.getGiveTicketSum().subtract(absTicket));
			
			//回零处理
			if(esa.getGiveCashSum().compareTo(BigDecimal.ZERO) < 0) {
				esa.setGiveCashSum(BigDecimal.ZERO);
			}
			if(esa.getGiveTicketSum().compareTo(BigDecimal.ZERO) < 0) {
				esa.setGiveTicketSum(BigDecimal.ZERO);
			}
			empSeasonActService.saveSeasonAct(esa, updUser);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		//正常处理
		return 1;
	}
	
//	@Override
//	@Transactional
//	public int dealEmpBenefits(Long empId, String opCode, List<BigDecimal> absCashs,
//			List<BigDecimal> absTickets, List<String> keys, List<Long> flowIds,List<String> desrcs,
//			String updUser, Long entId, String empName, int year, int season,String exBenefitType) {
//		
//		////////////////////////////////////////////////////////////////////////////////////////
//		//根据操作代码取得操作属性
//		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
//		EntParamCode op = null;
//		if(mapCode != null) {
//			op = mapCode.get(opCode);
//		}
//		
//		//系统异常 该操作不存在
//		if(op==null) return 0;
//		
//		//系统异常 该操作已废止
//		if("1".equals(op.getStopFlg())) return 0;
//		////////////////////////////////////////////////////////////////////////////////////////
//
//		////////////////////////////////////////////////////////////////////////////////////////
//		//计算余额
//		//现金余额
//		UserBalance ubCash = empBalanceService.fetchByUserAndName(empId, "balance");
//		//现金余额
//		UserBalance ubTicket = empBalanceService.fetchByUserAndName(empId, "companyTicket");
//		//现金余额 = 现金余额+本次操作现金*操作符号
//		BigDecimal cashB = ubCash.getBalance();
//		//内购券余额 = 内购券余额+本次操作内购券*操作符号
//		BigDecimal ticketB=ubTicket.getBalance();
//		
//		//符号
//		BigDecimal signed = new BigDecimal(op.getValue());
//		
//
//		////////////////////////////////////////////////////////////////////////////////////////
//		//本季度额度生成 流水记录
//		List<EmpBenefitFlow> ists = new ArrayList<EmpBenefitFlow>();
//		for (int i=0;i<absCashs.size();i++) {
//			//现金余额 = 现金余额+本次操作现金*操作符号
//			cashB = cashB.add(absCashs.get(i).multiply(signed));
//			//内购券余额 = 内购券余额+本次操作内购券*操作符号
//			ticketB = ticketB.add(absTickets.get(i).multiply(signed));
//
//			EmpBenefitFlow istF = new EmpBenefitFlow();
//			istF.setId(flowIds.get(i));					//id
//			istF.setEmpId(empId);						//员工（用户）id
//			istF.setOpDate(new Date());					//日期
//			istF.setOpCode(op.getCode());				//操作代码 
//			istF.setTicket(absTickets.get(i));			//内购券= 上季度 剩余内购券总额
//			istF.setTicketBalance(ticketB);				//内购券余额= 上季度 剩余内购券总额
//			istF.setCash(absCashs.get(i));				//现金=上季度剩余现金总额
//			istF.setCashBalance(cashB);					//现金余额	=上季度剩余现金总额
//			if(StringUtils.isEmpty(desrcs.get(i))) {
//				istF.setNote(op.getDescr());			//简要说明
//				//订单相关
//				if("203".equals(op.getCode()) || "202".equals(op.getCode())) {
//					istF.setNote(op.getDescr() + keys.get(i));
//				} 
//			} else {
//				istF.setNote(desrcs.get(i));			//简要说明
//			}
//			istF.setKeyId(keys.get(i));					//关键字
//			istF.setUserName(updUser);					//操作员名
//			istF.setEnterpriseId(entId);				//企业 id
//			istF.setEmpName(empName);;					//企业 id
//			istF.setExBenefitType(exBenefitType);		//福利科目 1:生日礼金/2:过节费/空:其他
//			ists.add(istF);
//		}
//		
//		int rtn = 0;
//		//操作后 现金余额小于0 处理终止 提示现金余额不足
//		if(cashB.compareTo(BigDecimal.ZERO) == -1) rtn = -1;  //现金余额不足
//		
//		//操作后 内购券余额小于0 处理终止 提示内购券余额不足 (rtn=-3 表示两方不做)
//		if(ticketB.compareTo(BigDecimal.ZERO) == -1) rtn += -2;  //现金余额不足  
//		
//		if(rtn < 0) return rtn;
//		////////////////////////////////////////////////////////////////////////////////////////
//		
//		////////////////////////////////////////////////////////////////////////////////////////
//		//本季度额度生成 流水记录
//		//DB更新
//		for (EmpBenefitFlow empBenefitFlow : ists) {
//			empBenefitFlowService.save(empBenefitFlow);
//		}
//		////////////////////////////////////////////////////////////////////////////////////////
//		
//		////////////////////////////////////////////////////////////////////////////////////////
//		//余额处理
//		//现金
//		//if(cashB.compareTo(ubCash.getBalance()) > 0) {
//			//ubCash.setCurrencyId(1L);
//			ubCash.setBalance(cashB);
//			empBalanceService.update(ubCash);
//		//}
//
//		//内购券
//		//if(ticketB.compareTo(ubTicket.getBalance()) > 0) {
//			ubTicket.setBalance(ticketB);
//			empBalanceService.update(ubTicket);
//		//}
//		////////////////////////////////////////////////////////////////////////////////////////
//
//		//正常处理
//		return 1;
//	}

	@Override
	@Transactional
	public int giftEmpBenefit(Long empId, Long userId, BigDecimal absCash, BigDecimal absTicket, String empName,
			String userName, Long flowId, Long entId, String updUser, String desrc1, String desrc2) {

		int resut=0;
		////////////////////////////////////////////////////////////////////////////////////////
		//员工账号扣款 205 馈赠亲友
		resut = this.dealEmpBenefit(empId, "205", absCash, absTicket, updUser, userName, flowId, entId, empName, desrc1, 0, 0,null);
		////////////////////////////////////////////////////////////////////////////////////////
				
		////////////////////////////////////////////////////////////////////////////////////////
		//亲友账户增加 206 获得馈赠
		if(resut==1) {
			this.dealEmpBenefit(userId, "206", absCash, absTicket, updUser, empName, dbUtils.CreateID(), entId, userName, desrc2, 0, 0,null);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		return resut;
	}


	@Override
	@Transactional
	public int envelopeEmpBenefit(Long fromId, Long toId, BigDecimal absCash, BigDecimal absTicket, String fromName,
			String toName, Long flowId, Long entId, String updUser,String key, String desrc1, String desrc2) {
		int resut=0;
		////////////////////////////////////////////////////////////////////////////////////////
		//员工账号扣款 205 馈赠亲友
		resut = this.dealEmpBenefit(fromId, "221", absCash, absTicket, fromName,key, flowId, entId, updUser, desrc1, 0, 0,null);
		////////////////////////////////////////////////////////////////////////////////////////
				
		////////////////////////////////////////////////////////////////////////////////////////
		//亲友账户增加 206 获得馈赠
		if(resut==1) {
			this.dealEmpBenefit(toId, "220", absCash, absTicket, toName,key, dbUtils.CreateID(), entId, updUser, desrc2, 0, 0,null);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		return resut;
	}
	
	@Override
	@Transactional
	public int recoveryEmpGift(Long empId, Long userId, BigDecimal absCash, BigDecimal absTicket, String empName,
			String userName, Long flowId, Long entId, String updUser, String desrc1, String desrc2) {
		
		int resut=0;
		////////////////////////////////////////////////////////////////////////////////////////
		//亲友账号扣款 207 馈赠亲友
		resut =this.dealEmpBenefit(userId, "207", absCash, absTicket, updUser, empName, flowId, entId, userName, desrc2, 0, 0,null);
		////////////////////////////////////////////////////////////////////////////////////////
				
		////////////////////////////////////////////////////////////////////////////////////////
		//员工账户增加 204收回馈赠
		if(resut==1) {
			this.dealEmpBenefit(empId, "204", absCash, absTicket, updUser, userName, dbUtils.CreateID(), entId, empName, desrc1, 0, 0,null);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		return resut;
	}

	@Override
	@Transactional
	public void invitBonuses(EnterpriseUser from,EnterpriseUser to,EntParamCode fistPrize, String note) {
		this.dealEmpBenefit(to.getId(), "298", BigDecimal.ZERO, NumberUtil.toBigDecimal(fistPrize.getValue()), 
				to.getName(), to.getId()+"", dbUtils.CreateID(), to.getEnterpriseId(), 
				to.getName(), note, SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),null);
		
		this.dealEmpBenefit(from.getId(), "298", BigDecimal.ZERO, NumberUtil.toBigDecimal(fistPrize.getValue()), 
					from.getName(), from.getId()+"", dbUtils.CreateID(), from.getEnterpriseId(), 
					from.getName(), note, SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),null);
			
	}
}