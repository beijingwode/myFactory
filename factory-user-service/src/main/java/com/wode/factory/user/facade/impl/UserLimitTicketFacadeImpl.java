package com.wode.factory.user.facade.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.user.facade.UserLimitTicketFacade;
import com.wode.factory.user.service.UserLimitTicketService;

@Service("userLimitTicketFacade")
public class UserLimitTicketFacadeImpl implements UserLimitTicketFacade {

	@Autowired
	private UserLimitTicketService userLimitTicketService;
	
	@Override
	public List<UserLimitTicket> getUsableTickits(Long userId, String limitTicketIds) {
		List<UserLimitTicket> rtn = new ArrayList<UserLimitTicket>();
		Date now =new Date();
		if(!StringUtils.isEmpty(limitTicketIds)) {
			String[] ary = limitTicketIds.split(",");
			for (String string : ary) {
				Long id= NumberUtil.toLong(string);
				if(id!=null) {
					UserLimitTicket t = userLimitTicketService.getById(id);
					if(t!= null && t.getStatus()!=2 && t.getStatus()!=3 && userId.equals(t.getUserId())) {						
						if(now.before(t.getLimitEnd()) && 
								(NumberUtil.isGreaterZero(t.getCashBalance()) || NumberUtil.isGreaterZero(t.getTicketBalance()))) {
							rtn.add(t);
						}
					}
				}
			}
		}
		return sortList(rtn);
	}
	
	@Override
	public List<UserLimitTicket> getUsableTickits(Long userId, List<Long> skuIds, boolean hasLimit4) {
		List<UserLimitTicket> rtn = new ArrayList<UserLimitTicket>();
		StringBuilder sb = new StringBuilder();
		sb.append(skuIds.get(0));
		for (int i=1;i<skuIds.size();i++) {
			sb.append(","+skuIds.get(i));
		}
		rtn = userLimitTicketService.getAvailableTicketMap(userId, sb.toString(),null);

		// 专享商品
		if(hasLimit4) {
			rtn.addAll(userLimitTicketService.getAvailableTicketMap(userId,null,4));
		}
		
		return sortList(rtn);
	}
	
	private List<UserLimitTicket> sortList(List<UserLimitTicket> rnt){
		rnt.sort(new Comparator<UserLimitTicket>() {

			@Override
			public int compare(UserLimitTicket o1, UserLimitTicket o2) {
				// 先比较类型
				if(o1.getTicketType() != o2.getTicketType()) {
					
					// 专用现金券 最优先
					if(o1.getTicketType() == 4) return -1;
					if(o2.getTicketType() == 4) return 1;
					
					// 通用现金券
					if(o1.getTicketType() == 3) return -1;
					if(o2.getTicketType() == 3) return 1;
					
					// 体验券（免品）
					if(o1.getTicketType() == 2) return -1;
					if(o2.getTicketType() == 2) return 1;
					
					return 0;
				}
				
				// 再比较 限制类型
				if(o1.getLimitType() != o2.getLimitType()) {
					// 商品（单品/活动页）
					if(o1.getLimitType() == 2) return -1;
					if(o2.getLimitType() == 2) return 1;
					
					// 品牌
					if(o1.getLimitType() == 6) return -1;
					if(o2.getLimitType() == 6) return 1;
					
					// 商家
					if(o1.getLimitType() == 7) return -1;
					if(o2.getLimitType() == 7) return 1;
					
					// 品类
					if(o1.getLimitType() == 3) return -1;
					if(o2.getLimitType() == 3) return 1;
					
					// 频道
					if(o1.getLimitType() == 5) return -1;
					if(o2.getLimitType() == 5) return 1;
					return 0;
				}
				
				// 在比较 是否一次使用 
				if(o1.getOneceFlag() != o2.getOneceFlag()) {
					// 一次使用 
					if(o1.getOneceFlag() == 1) return -1;
					if(o2.getOneceFlag() == 1) return 1;
					
				}
				//最后比较 时间
				return o1.getLimitEnd().compareTo(o2.getLimitEnd());
			}
		});
		return rnt;
	}
}
