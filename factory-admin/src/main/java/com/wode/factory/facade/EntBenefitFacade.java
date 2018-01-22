package com.wode.factory.facade;

import java.math.BigDecimal;

import com.wode.factory.model.EntSeasonAct;

public interface EntBenefitFacade {

	/**
	 * 企业福利额度自动流转。
	 * @param entId 企业id
	 * @param year 当前年度
	 * @param season 当前季度
	 * @param updUser 当前用户
	 * @return
	 */
	public int autoSeason(Long entId,int year ,int season,String updUser);

	/**
	 * 批准企业申请，准许企业福利发放额度
	 * @param apprId 申请记录id
	 * @param entId 企业id
	 * @param limit 获准额度
	 * @param flowId 流水id
	 * @param updUser 当前用户
	 * @return 0:系统异常、-1：审批记录已被处理过、1：正常处理
	 */
	public int grantBenefit(Long apprId,Long entId,BigDecimal limit,Long flowId ,String updName);

	/**
	 * 货款结算到商家现金账户
	 * @param saleBillId 对账单ID
	 * @param updName 更新者
	 * @return 0:系统异常、-1：审批记录已被处理过，1：正常处理
	 */
	public int paySaleBill(Long saleBillId,Long userId,String updName,Long flowId);


	/**
	 * 批准商家账号提现
	 * @param transferId 提现单ID
	 * @param flowCd 转账流水号
	 * @param updName 更新者
	 * @return 0:系统异常、-1：处理失败，1：正常处理
	 */
	public int cashTransfer(Long transferId,String flowCd,String updName,Long flowId);
	
	/**
	 * 转账失败
	 * @param transferId 提现单ID
	 * @param note		更新者
	 * @param updName	
	 * @param flowId
	 * @return 0:系统异常、-1：处理失败，1：正常处理
	 */
	public int cashTransferFail(Long transferId,String note,String updName,Long flowId);
	
	/**
	 * 企业账务处理
	 * @param entId	企业id
	 * @param opCode 操作代码
	 * @param esa 操作前企业账目（当季度）
	 * @param absCash 现金（不要符号）
	 * @param absTicket 内购券（不要符号）
	 * @param key 关键字
	 * @param flowId 流水id
	 * @param desrc 备注
	 * @param updUser 操作员
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足/-1001：当前季度已结转不能进行该操作
	 */
	public int dealEntBenefit(Long entId, String opCode, EntSeasonAct esa,
			BigDecimal absCash, BigDecimal absTicket, Long key,
			Long flowId, String desrc, String updUser);


	/**
	 * 企业账务处理
	 * @param entId	企业id
	 * @param opCode 操作代码
	 * @param esa 操作前企业账目（当季度）
	 * @param absCash 现金（不要符号）
	 * @param absTicket 内购券（不要符号）
	 * @param key 关键字
	 * @param flowId 流水id
	 * @param desrc 备注
	 * @param updUser 操作员
	 * @param banlanceCheck 余额检查
	 * @return 1：正常处理/0：系统异常/-1：现金余额不足/-2：内购券余额不足/-3：现金、内购券余额都不足/-1001：当前季度已结转不能进行该操作
	 */
	public int dealEntBenefit(Long entId, String opCode, EntSeasonAct esa,
			BigDecimal absCash, BigDecimal absTicket, Long key,
			Long flowId, String desrc, String updUser,boolean banlanceCheck);
	
	/**
	 * 取得企业当前季度福利账目
	 * @param entId
	 * @param year
	 * @param season
	 * @param updUser
	 * @return
	 */
	public EntSeasonAct getEntSeasonAct(Long entId, int year, int season,String updUser);
}
