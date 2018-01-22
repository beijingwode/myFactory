package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Currency;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserBlackEnvelope;
import com.wode.factory.model.UserBlackEnvelopeItem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserBlackEnvelopeItemService;
import com.wode.factory.user.service.UserBlackEnvelopeService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserService;

/**
 * 2015-8-20
 * @author 谷子夜
 *
 */
@Controller
@RequestMapping("/blackEnvelope")
@ResponseBody
public class BlackEnvelopeController extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private UserImGroupService userImGroupService;
	
	@Autowired
	DBUtils dbUtils;
	@Autowired
	private UserBlackEnvelopeService userBlackEnvelopeService;
	@Autowired
	private UserBlackEnvelopeItemService userBlackEnvelopeItemService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	CurrencyService currencyService;
	@Autowired
	UserBalanceService userBalanceService;
	
	/**
	 * 
	 * 
	 * 功能：发放内购券红包   /friend/sendRedEnvelope.user
	 * @param currencyId 	0:现金券、1:内购券
	 * @param toType	  	目标类型 0:个人 1:群
	 * @param toId			对方id
	 * @param toCnt			数量
	 * @param amount		总金额
	 * @param allotType		分配方式 0:固定值 1:随机分配
	 * @param price			单笔金额
	 * @param message		留言
	 * @param reasonType	原因
	 * @param forUserId		为朋友
	 * @param expKey1		关联key1
	 * @param expKey2		关联key2
	 * @param expMsg1		关联信息1
	 * @param expMsg2		关联信息2
	 * @param expMsg3		关联信息3
	 * @param expImg1		关联图片1
	 * @param expImg2		关联图片2
	 * @return
	 */
	@RequestMapping("sendBlackEnvelope.user")
	public ActResult<String> sendBlackEnvelope(HttpServletRequest request, Long currencyId, Integer toType, String toId,
			Integer toCnt, BigDecimal amount, Integer allotType, BigDecimal price, String message, String reasonType,
			Long forUserId, Long expKey1, Long expKey2, String expMsg1, String expMsg2, String expMsg3, String expImg1,
			String expImg2) {

		if(StringUtils.isEmpty(toId)) return ActResult.fail("参数错误");
		if(toCnt==null || toCnt<1) return ActResult.fail("参数错误");
		if(amount==null || amount.compareTo(BigDecimal.ZERO) <=0 ) return ActResult.fail("参数错误");
		if(currencyId==null) currencyId=1L; 	//内购券
		
		Long realTo=null;
		if(0L == toType) {
			//单个发送
			UserFactory friendUser = userService.getById(Long.parseLong(toId));
			if(friendUser==null){
				return ActResult.fail("对方信息不存在，请刷新后重试！");
			}

			realTo = friendUser.getId();
			if(allotType==null) allotType=0; 	//随机分配
		} else if(1L == toType) {
			//向群发送
			if(allotType==null) allotType=1; 	//随机分配
			UserImGroup query = new UserImGroup();
			query.setImGroupId(toId);
			UserImGroup userImGroup = null;
			List<UserImGroup> ls =userImGroupService.selectByModel(query);
			if(ls!=null && !ls.isEmpty()) {
				userImGroup=ls.get(0);
			}
			if(userImGroup==null){
				return ActResult.fail("对方信息不存在，请刷新后重试！");
			}
			realTo=userImGroup.getId();
			
		} else {
			return ActResult.fail("参数错误");
		}
		UserFactory forUser = null;
		if(StringUtils.isEmpty(forUserId)) {
			forUser = loginUser;
		} else if(forUserId.equals(loginUser.getId())){
			forUser = loginUser;
		} else {
			forUser = userService.getById(forUserId);
			if(forUser==null){
				return ActResult.fail("对方信息不存在，请刷新后重试！");
			}
		}

		UserBlackEnvelope ube = new UserBlackEnvelope();
		ube.setId(dbUtils.CreateID());
		ube.setUserId(forUser.getId());
		ube.setUserNike(forUser.getNickName());
		ube.setUserAvatar(forUser.getAvatar());
		ube.setCurrencyId(currencyId);
		ube.setToType(toType);
		ube.setToId(realTo);
		ube.setToCnt(toCnt);
		ube.setAllotType(allotType);
		ube.setPrice(price);
		ube.setStatus(1);	//状态 1:已发送 3:部分领取 4:全部领取 6:全部取消',
		ube.setAmount(amount);
		ube.setGainAmount(BigDecimal.ZERO);
		ube.setCreateTime(new Date());
		ube.setMessage(message);
		ube.setFromUserId(loginUser.getId());
		ube.setFromUserNike(loginUser.getNickName());
		ube.setFromUserAvatar(loginUser.getAvatar());
		ube.setReasonType(StringUtils.isEmpty(reasonType)?1:NumberUtil.toInteger(reasonType));
		ube.setExpKey1(expKey1);
		ube.setExpKey2(expKey2);
		ube.setExpMsg1(expMsg1);
		ube.setExpMsg2(expMsg2);
		ube.setExpMsg3(expMsg3);
		ube.setExpImg1(expImg1);
		ube.setExpImg2(expImg2);
		
		List<UserBlackEnvelopeItem> items = new ArrayList<UserBlackEnvelopeItem>();
		BigDecimal sum = makeItems(ube,items);
		if(amount.compareTo(sum) != 0) ActResult.fail("参数错误");
		
		userBlackEnvelopeService.save(ube);
		for (UserBlackEnvelopeItem userBlackEnvelopeItem : items) {
			userBlackEnvelopeItemService.save(userBlackEnvelopeItem);
		}
		return ActResult.success(ube.getId()+"");
	
	}

	/**
	 * 功能：查看红包   /friend/getRedEnvelope.user
	 * 
	 * @param envelopeId
	 * @return 数据(data).status 2:当前用户已领取/4:红包已被抢完/6:红包过期自动取消/其他:可以打开；数据(data).items 红包领取记录 （未拆封前不能查看）
	 */
	@RequestMapping("getBlackEnvelope.user")
	public ActResult<UserBlackEnvelope> getBlackEnvelope(HttpServletRequest request,Long envelopeId){
		
		UserBlackEnvelope ure = userBlackEnvelopeService.getById(envelopeId);
		
		List<UserBlackEnvelopeItem> list= getEnvelopeItems(envelopeId);
		
		List<UserBlackEnvelopeItem> opends= getOpenedItems(list);
		if(isOpened(envelopeId,loginUser.getId())) {
			ure.setStatus(2);			//已经打开过
			ure.setItems(opends);
		} else if(opends.size() == list.size()) {
			ure.setStatus(4);
			ure.setItems(opends);
		} else if(ure.getStatus() == 6) {
			ure.setItems(opends);
		}
		
		return ActResult.success(ure);
	}

	/**
	 * 功能：拆开红包   /friend/openBlackEnvelope.user
	 * 
	 * @param envelopeId
	 * @return 数据(data).status 2:当前用户已领取/4:红包已被抢完/6:红包过期自动取消/其他:可以打开；数据(data).items 红包领取记录 （未拆封前不能查看）;数据(msg) itemId_金额
	 */
	@RequestMapping("openBlackEnvelope.user")
	public ActResult<UserBlackEnvelope> openBlackEnvelope(HttpServletRequest request,Long envelopeId,String again){
		UserBlackEnvelope ure = userBlackEnvelopeService.getById(envelopeId);
		return open(envelopeId, ure,again);
		
	}

	/**
	 * 功能：拆开红包   /friend/openBlackEnvelope.user
	 * 
	 * @param envelopeId
	 * @param itemId 
	 * @return 数据(data).status 2:当前用户已领取/4:红包已被抢完/6:红包过期自动取消/其他:可以打开；数据(data).items 红包领取记录 （未拆封前不能查看）
	 */
	@RequestMapping("payBlackEnvelope.user")
	public ActResult<UserBlackEnvelope> payBlackEnvelope(HttpServletRequest request,Long envelopeId,Long itemId,BigDecimal itemPrice){
		UserBlackEnvelope ure = userBlackEnvelopeService.getById(envelopeId);
		return realOpen(envelopeId, ure,itemId,itemPrice);
		
	}

	@RequestMapping("/page{envelopeId}")
	public ModelAndView page(HttpServletRequest request, @PathVariable Long envelopeId,String toChatUserName) {
		ModelAndView result = new ModelAndView("blackEnvelope1");

		UserBlackEnvelope ure = userBlackEnvelopeService.getById(envelopeId);
		List<UserBlackEnvelopeItem> list= getEnvelopeItems(envelopeId);
		
		List<UserBlackEnvelopeItem> opends= getOpenedItems(list);
		if(opends.size() == list.size()) {
			ure.setStatus(4);
			ure.setItems(opends);
			result.addObject("note", "来晚一步，此包已经被凑全啦，");
		} else if(ure.getStatus() == 6) {
			ure.setItems(opends);
			result.addObject("note", "当前凑券包已过期");
		} else {
			ure.setItems(opends);
			result.addObject("note", "总金额￥"+ure.getAmount()+"，还差￥"+(ure.getAmount().subtract(ure.getGainAmount()))+"凑够。");			
		}
		
		result.addObject("vo", ure);
		result.addObject("fuid", ure.getUserId());
		return result;
	}

	@RequestMapping("/openPage{envelopeId}.user")
	public ModelAndView openPage(HttpServletRequest request, @PathVariable Long envelopeId) {
		ModelAndView result = new ModelAndView("blackEnvelope2");
		UserBlackEnvelope ure = userBlackEnvelopeService.getById(envelopeId);
		List<UserBalance> lstUB = userBalanceService.findByUser(loginUser.getId());
		UserBalance ub = null;//userBalanceService.findByUserAndType(loginUser.getId(), currencyTicket.getId());
		Currency currencyTicket=null;
		if (ure.getCurrencyId()==1) {
			 currencyTicket = currencyService.findByName("companyTicket");
		}else if(ure.getCurrencyId()==0){
			currencyTicket = currencyService.findByName("balance");
		}
		for (UserBalance userBalance : lstUB) {
			if(userBalance.getCurrencyId().equals(currencyTicket.getId())) {
				ub=userBalance;
			} 
		}
		if(ub==null) {
			result.addObject("balance", 0);
		} else {
			result.addObject("balance", ub.getBalance());
		}
		result.addObject("ticket", request.getParameter("ticket"));
		result.addObject("uid", request.getParameter("uid"));
		
		result.addObject("vo", ure);
		return result;
	}
	
	/**
	 * 功能：获取红包领取记录   /friend/getBlackEnvelopeItems.user
	 * 
	 * @param envelopeId
	 * 
	 * @return 数据(data).items 红包领取记录 （未拆封前不能查看）
	 */
	@RequestMapping("getBlackEnvelopeItems.user")
	public ActResult<UserBlackEnvelope> getBlackEnvelopeItems(HttpServletRequest request,Long envelopeId){
		UserBlackEnvelope ure = userBlackEnvelopeService.getById(envelopeId);
		ure.setItems(getOpenedItems(getEnvelopeItems(envelopeId)));
		return ActResult.success(ure);
	}
	
	private boolean isOpened(Long envelopeId,Long userId) {
		String jsonS=redisUtil.getMapData("REDIS_ENVELOPE_OPEN_" + envelopeId,userId+"");
		return !StringUtils.isEmpty(jsonS);
	}

	private List<UserBlackEnvelopeItem> getOpenedItems(List<UserBlackEnvelopeItem> list) {
		List<UserBlackEnvelopeItem> rtn = new ArrayList<UserBlackEnvelopeItem>();
		for (UserBlackEnvelopeItem userBlackEnvelopeItem : list) {
			if(userBlackEnvelopeItem.getStatus()==2) {
				rtn.add(userBlackEnvelopeItem);
			}
		}
		return rtn;
	}

	private List<UserBlackEnvelopeItem> getClosedItems(List<UserBlackEnvelopeItem> list) {
		List<UserBlackEnvelopeItem> rtn = new ArrayList<UserBlackEnvelopeItem>();
		for (UserBlackEnvelopeItem userBlackEnvelopeItem : list) {
			if(userBlackEnvelopeItem.getStatus()==0) {
				rtn.add(userBlackEnvelopeItem);
			}
		}
		return rtn;
	}
	
	private List<UserBlackEnvelopeItem> getEnvelopeItems(Long envelopeId) {
		String jsonS=redisUtil.getData("REDIS_ENVELOPE_ITEMS_" + envelopeId);
		if(StringUtils.isEmpty(jsonS)) {
			UserBlackEnvelopeItem query = new UserBlackEnvelopeItem();
			query.setEnvelopeId(envelopeId);
			List<UserBlackEnvelopeItem> items = userBlackEnvelopeItemService.selectByModel(query);
			
			redisUtil.setData("REDIS_ENVELOPE_ITEMS_" + envelopeId, JsonUtil.toJsonString(items), 60*60*6); //6小时缓存
			return items;
		} else {
			return JsonUtil.getList(jsonS, UserBlackEnvelopeItem.class);
		}
	}

	private void saveEnvelopeItems(UserBlackEnvelopeItem item,List<UserBlackEnvelopeItem> list,UserBlackEnvelope ube,int add) {
		if(add==0) {
			userBlackEnvelopeItemService.update(item);
		} else {
			userBlackEnvelopeItemService.save(item);
			ube.setToCnt(ube.getToCnt()+1);
		}
		ube.setGainAmount(ube.getGainAmount().add(item.getPrice()));
		userBlackEnvelopeService.update(ube);
		
		redisUtil.setData("REDIS_ENVELOPE_" + ube.getId(), JsonUtil.toJsonString(ube), 60*60*6); //6小时缓存
		redisUtil.setData("REDIS_ENVELOPE_ITEMS_" + item.getEnvelopeId(), JsonUtil.toJsonString(list), 60*60*6); //6小时缓存
	}
	
	
	private BigDecimal makeItems(UserBlackEnvelope ure,List<UserBlackEnvelopeItem> items) {
		List<BigDecimal> prices = new ArrayList<BigDecimal>();
		BigDecimal sum = breakAmount(ure.getAllotType(),ure.getPrice(),ure.getAmount(),ure.getToCnt(), prices);
		Collections.sort(prices, new Comparator<BigDecimal>(){
			@Override
			public int compare(BigDecimal arg0, BigDecimal arg1) {
				return arg1.compareTo(arg0);
			}});
		
		int index=0;
		for (BigDecimal p : prices) {
			UserBlackEnvelopeItem item = new UserBlackEnvelopeItem();
			item.setId(dbUtils.CreateID());
			item.setEnvelopeId(ure.getId());
			item.setAllotType(ure.getAllotType());
			item.setCurrencyId(ure.getCurrencyId());
			item.setOrders(++index);
			item.setPrice(p);
			item.setFromId(ure.getUserId());
			item.setFromNike(ure.getUserNike());
			item.setStatus(0);					//状态 0:未领取 2:已领取 3:自动取消',
			
			items.add(item);
		}
		return sum;
	}
	
	private static BigDecimal breakAmount(Integer type, BigDecimal priceS,BigDecimal amount,Integer toCnt, List<BigDecimal> prices) {
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal max = amount.multiply(new BigDecimal(0.6)).setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal retio = new BigDecimal(5D/toCnt).setScale(2, BigDecimal.ROUND_DOWN);
		if(retio.compareTo(BigDecimal.ONE)>0) retio = new BigDecimal(0.6);
		if(type==0) {
			for (int i = 0; i < toCnt; i++) {
				prices.add(priceS);
				sum=sum.add(priceS);
			}
		} else {
			BigDecimal last = amount;
			for (int i = 0; i < toCnt-1; i++) {
				//没人最少 0.01元
				last = last.subtract(BigDecimal.valueOf((toCnt-1-i)*0.01));
				BigDecimal price = last.multiply(retio).multiply(BigDecimal.valueOf(Math.random())).setScale(2, BigDecimal.ROUND_DOWN);
				if(price.compareTo(BigDecimal.ZERO) <=0) {
					price= BigDecimal.valueOf(0.01d);
				} else if (price.compareTo(max)>0) {
					price = max;
				}
				prices.add(price);
				sum=sum.add(price);
				
				last = amount.subtract(sum);
			}

			prices.add(last);
			sum=sum.add(last);			
		}
		return sum;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	private  ActResult<UserBlackEnvelope> open(Long envelopeId, UserBlackEnvelope ure,String again) {
		synchronized(ure) {
			ActResult<UserBlackEnvelope> rtn =  ActResult.success(ure);
			List<UserBlackEnvelopeItem> list= getEnvelopeItems(envelopeId);
			if(ure.getStatus() == 6) {
				ure.setItems(getOpenedItems(list));
			} else if(StringUtils.isEmpty(again) && isOpened(envelopeId,loginUser.getId())) {
				ure.setStatus(2);			//已经打开过
				ure.setItems(getOpenedItems(list));
			} else {
				List<UserBlackEnvelopeItem> closes= getClosedItems(list);
				if(closes.size()==0) {
					ure.setStatus(4);
					ure.setItems(list);
				} else {
					UserBlackEnvelopeItem item = null;
					if(closes.size()==1) {
						item = closes.get(0);
					} else {
						int index = (int) (Math.random()*closes.size());
						if(index<0) index=0;
						if(index>=closes.size()) index=closes.size()-1;
						item = closes.get(index);
					}
					if(item==null) item=closes.get(closes.size()-1);
					
					if(ure.getToType()==0) {
						if(!loginUser.getId().equals(ure.getToId())) {
							return ActResult.fail("参数错误");
						}
					}

					ure.setItems(getOpenedItems(list));
					rtn.setMsg(item.getId()+"_"+item.getPrice());
				}
			}
			return rtn;
		}
	}
	
	private  ActResult<UserBlackEnvelope> realOpen(Long envelopeId, UserBlackEnvelope ure,Long itemId,BigDecimal itemPrice) {
		synchronized(ure) {
			UserBlackEnvelopeItem item = null;
			List<UserBlackEnvelopeItem> list= getEnvelopeItems(envelopeId);
			for (UserBlackEnvelopeItem userBlackEnvelopeItem : list) {
				if(userBlackEnvelopeItem.getId().equals(itemId)) {
					item=userBlackEnvelopeItem;
					break;
				}
			}
			if(item == null) {
				return ActResult.fail("参数错误");
			}
			if(itemPrice!=null){
				item.setPrice(itemPrice);//更改凑包金额	
			}
			Map<String, Object> comMap = new HashMap<String, Object>();
			BigDecimal absCash =BigDecimal.ZERO;
			BigDecimal absTicket =BigDecimal.ZERO;
			if(item.getCurrencyId()==0L) absCash= item.getPrice();
			if(item.getCurrencyId()==1L) absTicket= item.getPrice();
			
			item.setUserNike(loginUser.getNickName());
			item.setUserAvatar(loginUser.getAvatar());
			
			comMap.put("fromId", loginUser.getId());
			comMap.put("toId", ure.getUserId());
			comMap.put("absCash", absCash);
			comMap.put("absTicket", absTicket);
			comMap.put("fromName", item.getFromNike());
			comMap.put("toName", item.getUserNike());
			comMap.put("key", ure.getId());
			comMap.put("entId", loginUser.getSupplierId());
			comMap.put("desrc1","向"+ item.getFromNike() + "发送红包："+ item.getPrice() +"元" + (item.getCurrencyId()==0L?"现金券":"内购券"));
			comMap.put("desrc2","收到"+ item.getUserNike() + "红包："+ item.getPrice() +"元" + (item.getCurrencyId()==0L?"现金券":"内购券"));
			comMap.put("updUser", loginUser.getUserName());

			String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/payEnvelope", comMap);
			ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);

			if(acTicket.isSuccess()) {
				item.setFlowCd(acTicket.getData().toString());

				//判断是否已被别人打开
				if(item.getStatus() != null && item.getStatus()==2) {
					
					UserBlackEnvelopeItem newItem =new UserBlackEnvelopeItem(item);
					
					newItem.setId(dbUtils.CreateID());
					newItem.setUserId(loginUser.getId());
					newItem.setStatus(2);
					newItem.setUpdateTime(new Date());

					list.add(newItem);
					saveEnvelopeItems(newItem,list,ure,1);
				} else {

					item.setUserId(loginUser.getId());
					item.setStatus(2);
					item.setUpdateTime(new Date());
					
					saveEnvelopeItems(item,list,ure,0);
				}
				
				//记录开封
				redisUtil.setMapData("REDIS_ENVELOPE_OPEN_" + envelopeId,loginUser.getId()+"", "1");
				redisUtil.setTime("REDIS_ENVELOPE_OPEN_" + envelopeId,60*60*24);	
			} else {
				return ActResult.fail(acTicket.getMsg());
			}
		
			return ActResult.success(ure);
		}
	}
}
