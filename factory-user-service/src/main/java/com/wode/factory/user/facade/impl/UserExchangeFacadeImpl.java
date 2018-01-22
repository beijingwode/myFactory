package com.wode.factory.user.facade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.user.facade.UserExchangeTicketFacade;
import com.wode.factory.user.model.UseExchangeTicketVo;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserTicketHisService;

@Service("userTicketFacade")
public class UserExchangeFacadeImpl implements UserExchangeTicketFacade {

	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private UserTicketHisService userTicketHisService;
	
    @Autowired
    DBUtils dbUtils;

	@Override
	@Transactional
	public int cancelOrderTicket(UserFactory user,Map<Long,UseExchangeTicketVo> mapExchange, String updUser, Long key,Date createTime) {

		////////////////////////////////////////////////////////////////////////////////////////
		// 获取用户换领币
		List<UserExchangeTicket> tickets= userExchangeTicketService.usingTicket(user.getId(),createTime);
		////////////////////////////////////////////////////////////////////////////////////////
		
		Map<Long,UserExchangeTicket> dealTickets= new HashMap<Long,UserExchangeTicket>();
		Map<Long,UseExchangeTicketVo> mapTicket = new HashMap<Long,UseExchangeTicketVo>();
		// 优先处理本企业商品换领币
		for (UserExchangeTicket userExchangeTicket : tickets) {
			userExchangeTicket.setLeftCnt(userExchangeTicket.getUsedAmount());	//临时存放 用于计算本次使用
			UseExchangeTicketVo vo = mapExchange.get(userExchangeTicket.getProductId());
			if(vo != null) {
				// 记录换领试用金额
				dealTickets.put(userExchangeTicket.getId(), userExchangeTicket);
				
				// 使用金额大于剩余金额
				if(vo.getSelf().compareTo(userExchangeTicket.getUsedAmount())>0) {
					return -1;
				} else {
					// 记录消费金额
					userExchangeTicket.setUsedAmount(userExchangeTicket.getUsedAmount().subtract(vo.getSelf()));
					// 清空临时
					vo.setTicket(vo.getTicket().subtract(vo.getSelf()));
					vo.setCash(vo.getCash().subtract(vo.getSelf()));
					vo.setSelf(BigDecimal.ZERO);
				}
				
				mapTicket.put(userExchangeTicket.getId(), vo);
			}
		}

		// 处理其他换领
		for (UseExchangeTicketVo vo : mapExchange.values()) {
			if(NumberUtil.isGreaterZero(vo.getSelf())) {
				return -1;
			}
			if(NumberUtil.isGreaterZero(vo.getTicket())) {
				for(int i=tickets.size()-1;i>=0;i--) {
					UserExchangeTicket userExchangeTicket = tickets.get(i);
					
					if(!NumberUtil.isGreaterZero(userExchangeTicket.getUsedAmount())){
						continue;
					}
					// 记录换领试用金额
					dealTickets.put(userExchangeTicket.getId(), userExchangeTicket);

					// 使用金额大于剩余金额
					if(vo.getTicket().compareTo(userExchangeTicket.getUsedAmount())>0) {
						// 清空临时
						vo.setTicket(vo.getTicket().subtract(userExchangeTicket.getUsedAmount()));
						if(vo.getCash().compareTo(userExchangeTicket.getUsedAmount())>0) {
							vo.setCash(vo.getCash().subtract(userExchangeTicket.getUsedAmount()));
							// 临时记录使用共享金额
							userExchangeTicket.setActiveAmount(userExchangeTicket.getUsedAmount());
						} else {
							// 临时记录使用共享金额
							userExchangeTicket.setActiveAmount(vo.getCash());
							vo.setCash(BigDecimal.ZERO);
						}
						userExchangeTicket.setUsedAmount(BigDecimal.ZERO);
						userExchangeTicket.setPrepayAmount(BigDecimal.ZERO);
						
					} else {
						// 记录消费金额
						userExchangeTicket.setUsedAmount(userExchangeTicket.getUsedAmount().subtract(vo.getTicket()));						
						userExchangeTicket.setPrepayAmount(userExchangeTicket.getPrepayAmount().subtract(vo.getTicket().subtract(vo.getCash())));
						// 临时记录使用共享金额
						userExchangeTicket.setActiveAmount(vo.getCash());
						// 清空临时
						vo.setTicket(BigDecimal.ZERO);
						vo.setCash(BigDecimal.ZERO);
						
						mapTicket.put(userExchangeTicket.getId(), vo);
						
						break;
					}
					
					mapTicket.put(userExchangeTicket.getId(), vo);
				}
				
				if(NumberUtil.isGreaterZero(vo.getTicket())) {
					return -1; 		// 换领币余额不足
				}
			}
		}
		

		for (UserExchangeTicket userExchangeTicket : dealTickets.values()) {
//			if(NumberUtil.isGreaterZero(userExchangeTicket.getActiveAmount())) {
//				SupplierExchangeProduct sep = supplierExchangeProductService.getById(userExchangeTicket.getExchangeProductId());
//
//				sep.setShareAmount(sep.getShareAmount().add(userExchangeTicket.getActiveAmount()));
//				this.saveSupplierTicketFlow(sep, "202", userExchangeTicket.getActiveAmount(), sep.getShareAmount(), "订单取消返还激活换领币，订单编号："+key, key, updUser);
//				// 记录共享金额使用
//				userExchangeTicket.setUsedActive(userExchangeTicket.getUsedActive().subtract(userExchangeTicket.getActiveAmount()));
//				// 清空临时共享
//				userExchangeTicket.setActiveAmount(BigDecimal.ZERO);
//				
//				supplierExchangeProductService.update(sep);
//			} 
			
			// 本季度额度生成 流水记录
			////////////////////////////////////////////////////////////////////////////////////////
			// 本季度额度生成 流水记录
			UserTicketHis uhis = new UserTicketHis();
			uhis.setId(dbUtils.CreateID());
			uhis.setKeyId(key);
			UseExchangeTicketVo vo = mapTicket.get(userExchangeTicket.getId());
			uhis.setNote(vo.getNote());
			uhis.setOpCode("202");
			uhis.setOpDate(new Date());
			uhis.setSupplierId(userExchangeTicket.getExchangeProductId());
			uhis.setTicket(userExchangeTicket.getLeftCnt().subtract(userExchangeTicket.getUsedAmount()));
			userExchangeTicket.setLeftCnt(BigDecimal.ZERO);
			uhis.setTicketBalance(getLeft(userExchangeTicket));
			uhis.setTicketId(userExchangeTicket.getId());
			uhis.setUserId(userExchangeTicket.getUserId());
			uhis.setUserName(updUser);
			
			// DB更新
			userTicketHisService.save(uhis);
			////////////////////////////////////////////////////////////////////////////////////////

			if(userExchangeTicket.getUsedAmount().compareTo(userExchangeTicket.getEmpAvgAmount())==0) {
				userExchangeTicket.setStatus(2);	//2:已用完
			} else {
				if(userExchangeTicket.getStatus() < 3) {
					userExchangeTicket.setStatus(1);	//1:部分使用
				}
			}
			userExchangeTicketService.update(userExchangeTicket);
		}
		
		return 1;
	}


	@Override
	@Transactional
	public int orderUserTicket(Long userId,Map<Long,UseExchangeTicketVo> mapExchange, String updUser, Long key) {

		////////////////////////////////////////////////////////////////////////////////////////
		// 获取用户换领币
		List<UserExchangeTicket> tickets= userExchangeTicketService.usingTicket(userId);
		////////////////////////////////////////////////////////////////////////////////////////
		
		Map<Long,UserExchangeTicket> dealTickets= new HashMap<Long,UserExchangeTicket>();
		Map<Long,UseExchangeTicketVo> mapTicket = new HashMap<Long,UseExchangeTicketVo>();
		// 优先处理本企业商品换领币
		for (UserExchangeTicket userExchangeTicket : tickets) {
			userExchangeTicket.setLeftCnt(userExchangeTicket.getUsedAmount());	//临时存放 用于计算本次使用
			UseExchangeTicketVo vo = mapExchange.get(userExchangeTicket.getProductId());
			if(vo != null) {
				dealTickets.put(userExchangeTicket.getId(), userExchangeTicket);
				// 记录换领试用金额
				BigDecimal left = this.getLeft(userExchangeTicket);
				// 使用金额大于剩余金额
				if(vo.getSelf().compareTo(left)>0) {
					return -1; 		// 换领币余额不足
					
				} else {
					// 记录消费金额
					userExchangeTicket.setUsedAmount(userExchangeTicket.getUsedAmount().add(vo.getSelf()));
					// 清空临时
					vo.setTicket(vo.getTicket().subtract(vo.getSelf()));
					vo.setCash(vo.getCash().subtract(vo.getSelf()));
					vo.setSelf(BigDecimal.ZERO);
				}
				
				String note = userExchangeTicket.getUsedNote()==null?"":userExchangeTicket.getUsedNote();
				if(!note.contains("领取自家商品")) {
					userExchangeTicket.setUsedNote(note+"领取自家商品,");
				}
				
				mapTicket.put(userExchangeTicket.getId(), vo);
			}
		}
		
		// 处理其他换领
		for (UseExchangeTicketVo vo : mapExchange.values()) {
			if(NumberUtil.isGreaterZero(vo.getSelf())) {
				return -1; 		// 换领币余额不足
			}
			
			if(NumberUtil.isGreaterZero(vo.getTicket())) {
				for (UserExchangeTicket userExchangeTicket : tickets) {
					BigDecimal left = this.getLeft(userExchangeTicket);
					if(!NumberUtil.isGreaterZero(left)){
						continue;
					}

					// 使用金额大于剩余金额
					if(vo.getTicket().compareTo(left)>0) {
						// 记录换领试用金额
						dealTickets.put(userExchangeTicket.getId(), userExchangeTicket);
						// 记录消费金额
						userExchangeTicket.setUsedAmount(userExchangeTicket.getUsedAmount().add(left));
						// 清空临时
						vo.setTicket(vo.getTicket().subtract(left));
//						if(vo.getCash().compareTo(left)>0) {
//							vo.setCash(vo.getCash().subtract(left));
//							// 临时记录使用共享金额
//							userExchangeTicket.setActiveAmount(left);
//						} else {
//							userExchangeTicket.setPrepayAmount(left.subtract(vo.getCash()));
//							// 临时记录使用共享金额
//							userExchangeTicket.setActiveAmount(vo.getCash());
//							vo.setCash(BigDecimal.ZERO);
//						}
//						
					} else {
						// 记录换领试用金额
						dealTickets.put(userExchangeTicket.getId(), userExchangeTicket);
						// 记录消费金额
						userExchangeTicket.setUsedAmount(userExchangeTicket.getUsedAmount().add(vo.getTicket()));
						
//						userExchangeTicket.setPrepayAmount(vo.getTicket().subtract(vo.getCash()));
//						// 临时记录使用共享金额
//						userExchangeTicket.setActiveAmount(vo.getCash());
						// 清空临时
						vo.setTicket(BigDecimal.ZERO);
						vo.setCash(BigDecimal.ZERO);

						String note = userExchangeTicket.getUsedNote()==null?"":userExchangeTicket.getUsedNote();
						if(!note.contains("领取他家商品")) {
							userExchangeTicket.setTicketNote(note+"领取他家商品,");
						}
						
						mapTicket.put(userExchangeTicket.getId(), vo);
						
						break;
					}
					
					String note = userExchangeTicket.getTicketNote()+"";
					if(!note.contains("领取他家商品")) {
						userExchangeTicket.setTicketNote(note+"领取他家商品,");
					}
					
					mapTicket.put(userExchangeTicket.getId(), vo);
				}
				
				if(NumberUtil.isGreaterZero(vo.getTicket())) {
					return -1; 		// 换领币余额不足
				}
			}
		}
		
		for (UserExchangeTicket userExchangeTicket : dealTickets.values()) {
			
//			if(NumberUtil.isGreaterZero(userExchangeTicket.getActiveAmount())) {
//				SupplierExchangeProduct sep = supplierExchangeProductService.getById(userExchangeTicket.getExchangeProductId());
//				if(sep.getShareAmount().compareTo(userExchangeTicket.getActiveAmount())<0) {
//					return -2;		// 企业共享券余额不足
//				} else {
//					sep.setShareAmount(sep.getShareAmount().subtract(userExchangeTicket.getActiveAmount()));
//					this.saveSupplierTicketFlow(sep, "203", userExchangeTicket.getActiveAmount(), sep.getShareAmount(), "使用激活换领币支付订单支付订单，订单编号："+key, key, updUser);
//					// 记录共享金额使用
//					userExchangeTicket.setUsedActive(userExchangeTicket.getUsedActive().add(userExchangeTicket.getActiveAmount()));
//					// 清空临时共享
//					userExchangeTicket.setActiveAmount(BigDecimal.ZERO);
//					
//					supplierExchangeProductService.update(sep);
//				}
//			} 
//			
			// 本季度额度生成 流水记录
			////////////////////////////////////////////////////////////////////////////////////////
			// 本季度额度生成 流水记录
			UserTicketHis uhis = new UserTicketHis();
			uhis.setId(dbUtils.CreateID());
			uhis.setKeyId(key);
			UseExchangeTicketVo vo = mapTicket.get(userExchangeTicket.getId());
			uhis.setNote(vo.getNote());
			uhis.setOpCode("203");
			uhis.setOpDate(new Date());
			uhis.setSupplierId(userExchangeTicket.getExchangeProductId());
			uhis.setTicket(userExchangeTicket.getUsedAmount().subtract(userExchangeTicket.getLeftCnt()));
			userExchangeTicket.setLeftCnt(BigDecimal.ZERO);
			uhis.setTicketBalance(getLeft(userExchangeTicket));
			uhis.setTicketId(userExchangeTicket.getId());
			uhis.setUserId(userExchangeTicket.getUserId());
			uhis.setUserName(updUser);
			
			// DB更新
			userTicketHisService.save(uhis);
			////////////////////////////////////////////////////////////////////////////////////////
			
			if(userExchangeTicket.getUsedAmount().compareTo(userExchangeTicket.getEmpAvgAmount())==0) {
				userExchangeTicket.setStatus(2);	//2:已用完
			} else {
				if(userExchangeTicket.getStatus() < 3) {
					userExchangeTicket.setStatus(1);	//1:部分使用
				}
			}
			userExchangeTicketService.update(userExchangeTicket);
		}
		
		return 1;
	}	

	private BigDecimal getLeft(UserExchangeTicket t) {
		return t.getEmpAvgAmount().subtract(t.getUsedAmount());
	}
}
