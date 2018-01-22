package com.wode.factory.company.facade;

import java.math.BigDecimal;
import java.util.List;

import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EnterpriseUser;

public interface EmpBenefitFacade {

	/**
	 * 员工账务处理
	 * @param empId	员工id
	 * @param opCode 操作代码
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param updUser 当前用户
	 * @param key 关键字
	 * @param flowId 流水id
	 * @param entId 企业id
	 * @param empName 员工姓名
	 * @param desrc 备注
	 * @param year 年
	 * @param season 季度
	 * @param exBenefitType 福利科目 1:生日礼金/2:过节费/空:其他
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	public int dealEmpBenefit(Long empId, String opCode, BigDecimal absCash,
			BigDecimal absTicket, String updUser, String key, Long flowId,
			Long entId, String empName, String desrc,int year, int season,String exBenefitType);
	
	/**
	 * 员工向亲友馈赠
	 * @param empId	员工id
	 * @param userId 亲友账户Id
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param empName 员工姓名
	 * @param userName 亲友姓名
	 * @param flowId 流水id
	 * @param entId 企业id
	 * @param updUser 当前账号
	 * @param desrc1 备注
	 * @param desrc2 备注
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	public int giftEmpBenefit(Long empId,Long userId, BigDecimal absCash,
			BigDecimal absTicket, String empName, String userName, Long flowId,
			Long entId,String updUser,String desrc1,String desrc2);
	
	/**
	 * 黑包支付
	 * @param fromId from用户id
	 * @param toId to用户Id
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param fromName 员工姓名
	 * @param toName 亲友姓名
	 * @param flowId 流水id
	 * @param entId 企业id
	 * @param updUser 当前账号
	 * @param desrc1 备注
	 * @param desrc2 备注
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	public int envelopeEmpBenefit(Long fromId,Long toId, BigDecimal absCash,
			BigDecimal absTicket, String fromName, String toName, Long flowId,
			Long entId,String updUser,String key,String desrc1,String desrc2);

	/**
	 * 回收馈赠
	 * @param empId	员工id
	 * @param userId 亲友账户Id
	 * @param absCash 操作现金
	 * @param absTicket 操作内购券
	 * @param empName 员工姓名
	 * @param userName 亲友姓名
	 * @param flowId 流水id
	 * @param entId 企业id
	 * @param updUser 当前账号
	 * @param desrc1 备注
	 * @param desrc2 备注
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足
	 */
	public int recoveryEmpGift(Long empId,Long userId, BigDecimal absCash,
			BigDecimal absTicket, String empName, String userName, Long flowId,
			Long entId,String updUser,String desrc1,String desrc2);

	public void invitBonuses(EnterpriseUser from, EnterpriseUser to, EntParamCode fistPrize, String note);
}
