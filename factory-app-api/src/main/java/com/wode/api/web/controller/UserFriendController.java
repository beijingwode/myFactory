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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wode.api.facade.LoginFacade;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.Currency;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.model.UserRedEnvelope;
import com.wode.factory.model.UserRedEnvelopeItem;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.SmsService;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.model.UserContactsAppr;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserContactsApprService;
import com.wode.factory.user.service.UserContactsService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserImService;
import com.wode.factory.user.service.UserRedEnvelopeItemService;
import com.wode.factory.user.service.UserRedEnvelopeService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.vo.UserContactsApprVo;
import com.wode.factory.user.vo.UserContactsVo;
import com.wode.model.CommUser;

/**
 * 2015-8-20
 * @author 谷子夜
 *
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/friend")
@ResponseBody
public class UserFriendController extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private UserImService userImService;
	@Autowired
	private UserImGroupService userImGroupService;
	
	@Autowired
	private UserBalanceService userBalanceService;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	DBUtils dbUtils;
	@Autowired
	private LoginFacade loginFacade;
	@Autowired
	private UserRedEnvelopeService userRedEnvelopeService;
	@Autowired
	private UserRedEnvelopeItemService userRedEnvelopeItemService;
	@Autowired
	private UserContactsService userContactsService;
	@Autowired
	private UserContactsApprService userContactsApprService;
	
	@Autowired
	private RedisUtil redisUtil;

	static SmsService sms = ServiceFactory.getSmsService(Constant.OUTSIDE_SERVICE_URL);
	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	/**删除亲友
	 * @param request
	 * @param empId 亲友userId
	 * @return
	 */
	@RequestMapping("deleteUserFriend.user")
	public ActResult<String> deleteUserFriend(HttpServletRequest request,Long id){
		if(StringUtils.isEmpty(id)){
			return ActResult.fail("参数为空");
		}else{
			UserContacts uc = userContactsService.getById(id);
			if(uc!=null) {
				uc.setFirendType("0");
				userContactsService.update(uc);
			}
			return ActResult.successSetMsg("亲友删除成功");
		}
	}
	
	/**
	 * 员工用户添加普通用户为亲友
	 * 	好友上线5人
	 * @param request
	 * @param phoneNumber 普通用户手机号(亲友)
	 * @param note 普通用户备注(亲友)
	 * @param name 员工姓名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("empUserToNormalUser.user")
	public ActResult<String> empUserToNormalUser(HttpServletRequest request,String phoneNumber,String note,String nickname){
		if(StringUtils.isEmpty(phoneNumber)){
			return ActResult.fail("手机号为空");   
		}else if(StringUtils.isEmpty(note)){
			return ActResult.fail("备注为空");
		}else if(StringUtils.isEmpty(nickname)){
			return ActResult.fail("昵称为空");
		}else{
			//查询phoneNumber手机号信息
			////////////////////////////////////////////////////////////////////////
			ActResult<UserFactory> ar = userService.findByPhone(phoneNumber);
			UserFactory userFactory = ar.getData();
			if(!ar.isSuccess()) {
				//向共通注册
				ActResult<CommUser> actUser = this.registerCommonByPhone(phoneNumber, 1, request);
				if(actUser.isSuccess()) {
					UserFactory uf = new UserFactory();
					uf.setId(actUser.getData().getUserId());
					uf.setUserName(actUser.getData().getUserName());
					uf.setEmail(actUser.getData().getUserEmail());
					uf.setEnabled(actUser.getData().getEnabled());
					uf.setUsable(actUser.getData().getUsable());
					uf.setCreatTime(new Date());
					uf.setNickName(actUser.getData().getNickName());
					uf.setEnabled(1);
					uf.setType(1);
					uf.setPhone(actUser.getData().getUserPhone());
					uf.setGender("m");
					uf.setLoginTime(new Date());
					userFactory=uf;
					userService.specialSave(uf);
					
					sms.sendSms(phoneNumber, Constant.SMS_SIGNATURE, nickname+"预赠送内购券给您，请用手机号登陆查看祥情。更多内容猛戳 http://wd-w.com/app.htm", "factoryAPI", true, null);
				} else {
					return ActResult.fail("您输入的手机号还没有注册，无法添加为亲友");
				}
				
			}
//			
//			if(userFactory.getEmployeeType()!=0)
//				return ActResult.fail("对方不是普通用户,不能添加");
			
			////////////////////////////////////////////////////////////////////////
			//查询用户信息
			////////////////////////////////////////////////////////////////////////
			UserFactory user = userService.getById(loginUser.getId());
			if(StringUtils.isEmpty(user))
				return ActResult.fail("empId参数有误");
			
			if(user.getEmployeeType()!=1)
				return ActResult.fail("您不是员工用户,不能进行添加操作");
			////////////////////////////////////////////////////////////////////////
			
			UserContacts query = new UserContacts();
			query.setUserId(loginUser.getId());
			query.setContactsId(userFactory.getId());
			query.setFirendType("1");
			UserContacts uc1 = userContactsService.selectOneByModel(query);
			
			query.setUserId(userFactory.getId());
			query.setContactsId(loginUser.getId());
			query.setFirendType("1");
			UserContacts uc2 = userContactsService.selectOneByModel(query);
			
			if(uc1!=null && uc2!=null){
				return ActResult.fail("你们已经是好友了，请刷新联系人列表");//账户已成为亲友账户
			}
			
			UserContactsAppr aq=new UserContactsAppr();
			aq.setUserId(loginUser.getId());
			aq.setFirendId(userFactory.getId());
			List<UserContactsAppr> apprs= userContactsApprService.selectByModel(aq);
			if(apprs!=null && !apprs.isEmpty()) {
				ActResult ret = new ActResult<>(); 
				ret.setData(userFactory.getId());
				ret.setMsg("申请成功，请等待对方审核");
				return ret;
			}
			
			//查询phoneNumber账号的亲友信息
			query = new UserContacts();
			query.setUserId(loginUser.getId());
			query.setFirendType("1");
			List<UserContacts> list = userContactsService.selectByModel(query);
			//员工账户亲友上限5人
			if(list.size()>=50){
				return ActResult.fail("亲友数已达上限（50人），不能再添加了");
			}
			//普通用户的亲友 uq = new UserFriendQuery();
			query.setUserId(userFactory.getId());
			query.setFirendType("1");
			list = userContactsService.selectByModel(query);
			if(list.size()>=50) {
				return ActResult.fail("对方账号亲友已达上限");
			}
			
			UserContactsAppr appr = new UserContactsAppr();
			appr.setUserId(loginUser.getId());
			appr.setUserKey(StringUtils.isEmpty(loginUser.getPhone())?loginUser.getEmail():loginUser.getPhone());
			appr.setUserNickname(nickname);
			appr.setApprText("我是"+nickname);
			appr.setFriendMemo(note);
			appr.setFirendId(userFactory.getId());
			appr.setFriendKey(StringUtils.isEmpty(userFactory.getPhone())?userFactory.getEmail():userFactory.getPhone());
			appr.setFriendNickname(userFactory.getNickName());
			appr.setStatus("1");
			appr.setCreateTime(new Date());
			appr.setApprFrom("app");

			userContactsApprService.saveOrUpdate(appr);
			
			loginFacade.doPushNotify(userFactory.getId(), null, "亲友邀请",nickname+"("+appr.getUserKey()+"),想成为您的亲友，赶快给个回复吧。");
			//添加到备注表中
			query = new UserContacts();
			query.setUserId(loginUser.getId());
			query.setContactsId(userFactory.getId());
			uc1 = userContactsService.selectOneByModel(query);
			if (uc1 == null) {//没有 添加
				query.setContactsMemo(note);
				UserIm q = new UserIm();
				q.setUserId(query.getContactsId());
				List<UserIm> lst = userImService.selectByModel(q);
				if(lst!=null && !lst.isEmpty()) {
					query.setContactsImId(lst.get(0).getOpenimId());
				}
				query.setFirendType("0");	//非好友
				query.setApprFrom("app");
				query.setCreateTime(new Date());
				userContactsService.save(query);
			}else{//有 修改
				uc1.setContactsMemo(note);
				userContactsService.update(uc1);
			}
			ActResult ret = new ActResult<>();
			ret.setData(userFactory.getId());
			ret.setMsg("申请添加亲友成功");
			return ret;
			//return ActResult.successSetMsg("申请添加亲友成功");
			////////////////////////////////////////////////////////////////////////
		}
	}
	/**
	 * 普通用户的亲友列表
	 * 普通账户亲友上限1人
	 * @param request
	 * @return   
	 */
	@RequestMapping("normalUserFriendsList.user")
	public ActResult<List<UserContactsVo>> normalUserFriendsList(HttpServletRequest request){
		return this.userContactsService.normalUserFriendsList(loginUser.getId());
	}
	
	/**
	 * 功能：员工用户亲友列表
	 * 员工账户亲友上限5人
	 * @return
	 */
	@RequestMapping("list.user")
	public ActResult<List<UserContactsVo>> findAll(HttpServletRequest request){
//		List<UserFriend> list = userFriendService.findByemployee(loginUser.getId());
		return userContactsService.empUserFriendsList(loginUser.getId());
	}
	
	/**
	 * 功能：普通申请成为员工亲友账户
	 * @param request
	 * @param memo
	 * @param phoneNumber
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("apply.user")
	public ActResult<String> applyFriend(HttpServletRequest request, String nickname, String phoneNumber,String note){
		if (StringUtils.isEmpty(note)) {
			return ActResult.fail("备注为空或请更新版本!");
		}

		//普通用户只能成为工账户的亲友		
		if(StringUtils.isPhoneNumber(phoneNumber)){
			ActResult<UserFactory> ar = userService.findByPhone(phoneNumber);
			if(ar.isSuccess()){
				UserFactory uf = ar.getData();
								
				if(uf.getEmployeeType()==1){
					UserContacts query = new UserContacts();
					query.setUserId(loginUser.getId());
					query.setContactsId(uf.getId());
					query.setFirendType("1");
					UserContacts uc1 = userContactsService.selectOneByModel(query);
					
					query.setUserId(uf.getId());
					query.setContactsId(loginUser.getId());
					query.setFirendType("1");
					UserContacts uc2 = userContactsService.selectOneByModel(query);
					
					if(uc1!=null && uc2!=null){
						return ActResult.fail("你们已经是好友了，请刷新联系人列表");//账户已成为亲友账户
					}
					UserContactsAppr aq=new UserContactsAppr();
					aq.setUserId(loginUser.getId());
					aq.setFirendId(uf.getId());
					List<UserContactsAppr> apprs= userContactsApprService.selectByModel(aq);
					if(apprs!=null && !apprs.isEmpty()) {
						ActResult ret = new ActResult<>(); 
						ret.setData(uf.getId());
						ret.setMsg("申请成功，请等待对方审核");
						return ret;
					}
					
					query = new UserContacts();
					query.setUserId(loginUser.getId());
					query.setFirendType("1");
					List<UserContacts> list = userContactsService.selectByModel(query);
					//员工账户亲友上限5人
					if(list.size()>=50){
						return ActResult.fail("亲友数已达上限（50人），不能再添加了");
					}
					//普通用户的亲友 uq = new UserFriendQuery();
					query.setUserId(uf.getId());
					query.setFirendType("1");
					list = userContactsService.selectByModel(query);
					if(list.size()>=50) {
						return ActResult.fail("对方账号亲友已达上限");
					}
					
					UserContactsAppr appr = new UserContactsAppr();
					appr.setUserId(loginUser.getId());
					appr.setUserKey(StringUtils.isEmpty(loginUser.getPhone())?loginUser.getEmail():loginUser.getPhone());
					appr.setUserNickname(nickname);
					appr.setApprText("我是"+nickname);
					appr.setFriendMemo(note);
					appr.setFirendId(uf.getId());
					appr.setFriendKey(StringUtils.isEmpty(uf.getPhone())?uf.getEmail():uf.getPhone());
					appr.setFriendNickname(uf.getNickName());
					appr.setStatus("1");
					appr.setCreateTime(new Date());
					appr.setApprFrom("app");

					userContactsApprService.saveOrUpdate(appr);
					
					loginFacade.doPushNotify(uf.getId(), null, "亲友申请",nickname+"("+appr.getUserKey()+"),想成为您的亲友，赶快给个回复吧。");
					
					//添加到备注表中
					query = new UserContacts();
					query.setUserId(loginUser.getId());
					query.setContactsId(uf.getId());
					uc1 = userContactsService.selectOneByModel(query);
					if (uc1 == null) {//没有 添加
						query.setContactsMemo(note);
						UserIm q = new UserIm();
						q.setUserId(query.getContactsId());
						List<UserIm> lst = userImService.selectByModel(q);
						if(lst!=null && !lst.isEmpty()) {
							query.setContactsImId(lst.get(0).getOpenimId());
						}
						query.setFirendType("0");	//非好友
						query.setApprFrom("app");
						query.setCreateTime(new Date());
						userContactsService.save(query);
					}else{//有 修改
						uc1.setContactsMemo(note);
						userContactsService.update(uc1);
					}
					
					ActResult ret = new ActResult<>(); 
					ret.setData(uf.getId());
					ret.setMsg("申请成功，请等待对方审核");
					return ret;
					
				}else{
					return ActResult.fail("该手机号绑定用户不是员工账户");
				}
			}else
				return ActResult.fail("该手机号未绑定账户");
		}else
			return ActResult.fail("请输入正确手机号码");
	}
	
	/**
	 * 普通账号进行审核
	 * @param request
	 * @param result
	 * @param empId
	 * @return
	 */
	@RequestMapping("normalUserCheck.user")
	public ActResult<String> normalUserCheck(HttpServletRequest request, String status, Long id,String memo){
		UserContactsAppr appr= userContactsApprService.getById(id);
		if(StringUtils.isEmpty(appr)){
			return ActResult.fail("审核信息不存在");
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		UserContacts query = new UserContacts();
		query.setUserId(loginUser.getId());
		query.setFirendType("1");
		List<UserContacts> list = userContactsService.selectByModel(query);
		//员工账户亲友上限5人
		if(list.size()>50){
			return ActResult.fail("账户亲友已达上限");
		}
		//普通用户的亲友 uq = new UserFriendQuery();
		query.setUserId(appr.getUserId());
		query.setFirendType("1");
		list = userContactsService.selectByModel(query);
		if(list.size()>=50) {
			return ActResult.fail("对方账号亲友已达上限");
		}		

		UserFactory user = this.userService.getById(appr.getUserId());
		if(user.getEmployeeType()!=1)
			return ActResult.fail("很抱歉，您不能添加非员工用户作为亲友。");

		if(status.equals("true")){
			make2contachs(memo, appr);
			
			loginFacade.doPushNotify(appr.getUserId(), null, "亲友确认",(StringUtils.isEmpty(appr.getFriendMemo())?loginUser.getNickName():appr.getFriendMemo())+"("+appr.getFriendKey()+"),接受了您的亲友申请，给他点内购券增加友好度吧。");

			userContactsApprService.removeById(id);
			return ActResult.successSetMsg("审核成功");
			////////////////////////////////////
		} else if(status.equals("false")){
			userContactsApprService.removeById(id);
			//loginFacade.doPushNotify(checkUser.getUserId(), null, "亲友确认",checkUser.getMemo()+"("+loginUser.getPhone()+"),拒绝了您的亲友申请，再找其他人试试吧。");
			return ActResult.successSetMsg("拒绝成功");
		}else{
			return ActResult.fail("参数有误");
		}
	}
	/**
	 * 员工账户进行审核
	 * 功能：审核亲友申请
	 * @param request
	 * @param result
	 * @param friendId
	 * @return
	 */
	@RequestMapping("check.user")
	public ActResult<String> verify(HttpServletRequest request, String result, Long id,String memo){
		UserContactsAppr appr= userContactsApprService.getById(id);
		if(StringUtils.isEmpty(appr)){
			return ActResult.fail("审核信息不存在");
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		UserContacts query = new UserContacts();
		query.setUserId(loginUser.getId());
		query.setFirendType("1");
		List<UserContacts> list = userContactsService.selectByModel(query);
		//员工账户亲友上限5人
		if(list.size()>50){
			return ActResult.fail("账户亲友已达上限");
		}
		//普通用户的亲友 uq = new UserFriendQuery();
		query.setUserId(appr.getUserId());
		query.setFirendType("1");
		list = userContactsService.selectByModel(query);
		if(list.size()>=50) {
			return ActResult.fail("对方账号亲友已达上限");
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		//审核亲友申请
		String str="";
		if("true".equals(result)){
			make2contachs(memo, appr);
			////////////////////////////////
			loginFacade.doPushNotify(appr.getUserId(), null, "亲友确认",(StringUtils.isEmpty(appr.getFriendMemo())?loginUser.getNickName():appr.getFriendMemo())+"("+appr.getFriendKey()+"),接受了您的好友申请，要点内购券增加友好度吧。");

			userContactsApprService.removeById(id);
		}else if("false".equals(result)){
			userContactsApprService.removeById(id);
		}else{
			return ActResult.fail("参数错误");
		}
		return ActResult.success(str);
	}
	
	/**
	 * 功能：修改亲友昵称
	 * @return
	 */
	@RequestMapping("changeNick.user")
	public ActResult<String> changeNickName(HttpServletRequest request, Long userId, String memo){
		if(StringUtils.isEmpty(memo)){
			return ActResult.fail("亲友昵称不能为空");
		}
		UserContacts query = new UserContacts();
		query.setUserId(loginUser.getId());
		query.setContactsId(userId);
		UserContacts uc = userContactsService.selectOneByModel(query);

		if(uc==null){
			return ActResult.fail("好友关系不存在，请先添加为好友");
		}
		
		uc.setContactsMemo(memo);
		userContactsService.update(uc);
		return ActResult.success(null);
	}
	
	/**
	 * 功能：待审核列表
	 * @return
	 */
	@RequestMapping("checkList.user")
	public ActResult<Object> checkFriendList(HttpServletRequest request, ModelMap model, Integer pageSize, Integer pageNum){

		UserContactsAppr query =new UserContactsAppr();
		query.setFirendId(loginUser.getId());
		List<UserContactsApprVo> apprs= userContactsApprService.selectVoByModel(query);
		
		JSONObject rtn = new JSONObject();
		rtn.put("list", apprs);
		return ActResult.success(rtn);
	}
	
	/**
	 * 功能：员工为亲友分配员工内购券
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("giveFucoin.user")
	public ActResult<String> giveFucoin(HttpServletRequest request, Long userId, Integer num){
		UserFactory friendUser = userService.getById(userId);

		if(friendUser==null){
			return ActResult.fail("好友信息查不存在，请仔细确认！");
		}
		if(num==null){
			num=0;
			return ActResult.fail("请输入正确的内购券数量！");
		}else{
			//找出要修改的亲友
			UserContacts query = new UserContacts();
			query.setUserId(loginUser.getId());
			query.setContactsId(userId);
			query.setFirendType("1");
			UserContacts ec = userContactsService.selectOneByModel(query);
			if(ec==null){
				return ActResult.fail("好友关系不存在，请先添加为好友");
			}
			
			query.setUserId(userId);
			query.setContactsId(loginUser.getId());
			query.setFirendType("1");
			UserContacts uc = userContactsService.selectOneByModel(query);
			if(uc==null){
				return ActResult.fail("好友关系不存在，请先添加为好友");
			}
			
			Currency currency = currencyService.findByName("companyTicket");
			UserBalance ubtemp = userBalanceService.findByUserAndType(loginUser.getId(),currency.getId());
			if(ubtemp!=null&&ubtemp.getBalance()!=null){
				if(ubtemp.getBalance().compareTo(new BigDecimal(num))>-1){
					Map<String, Object> comMap = new HashMap<String, Object>();
					
					comMap.put("empId", loginUser.getId());
					comMap.put("userId", userId);
					comMap.put("absCash", 0);
					comMap.put("absTicket", num);
					comMap.put("empName", StringUtils.isEmpty(uc.getContactsMemo())?loginUser.getNickName():uc.getContactsMemo());
					comMap.put("userName",StringUtils.isEmpty(ec.getContactsMemo())?friendUser.getNickName():ec.getContactsMemo());
					comMap.put("updUser", loginUser.getUserName());
					
					String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/giftEmpBenefit", comMap);
					ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);

					if(acTicket.isSuccess()) {
						String note = (StringUtils.isEmpty(uc.getContactsMemo())?loginUser.getNickName():uc.getContactsMemo())+"("+loginUser.getPhone()+"),向您赠送了"+num+"内购券，快去享受员工级优惠吧。";
						loginFacade.doPushNotify(userId, null, "内购券赠送",note);
						
						loginFacade.doPushWxBanlanceMsg(userId, num+"", TimeUtil.getStringDateShort(), note, "1");
						return ActResult.success(null);
					} else {
						return ActResult.fail("分配内购券失败，内购券数量不足!");
					}
				}else{
					return ActResult.fail("分配内购券失败，内购券数量不足!");
				}
			}else{
				return ActResult.fail("分配内购券失败，内购券数量不足!");
			}
		}
	}
	

	/**
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
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("sendRedEnvelope.user")
	public ActResult<String> sendRedEnvelope(HttpServletRequest request,Long currencyId,Integer toType,String toId, Integer toCnt, BigDecimal amount,Integer allotType,BigDecimal price,String message) {

		if(StringUtils.isEmpty(toId)) return ActResult.fail("参数错误");
		if(toCnt==null || toCnt<1) return ActResult.fail("参数错误");
		if(amount==null || amount.compareTo(BigDecimal.ZERO) <=0 ) return ActResult.fail("参数错误");
		if(currencyId==null) currencyId=1L; 	//内购券
		
		Long realTo=null;
		String desrc = "";
		if(0L == toType) {
			//单个发送
			UserFactory friendUser = userService.getById(Long.parseLong(toId));
			if(friendUser==null){
				return ActResult.fail("对方信息不存在，请刷新后重试！");
			}

			realTo = friendUser.getId();
			if(allotType==null) allotType=0; 	//随机分配
			desrc="向"+friendUser.getNickName()+"发送"+amount+"元红包，"+message ;
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
			desrc="向 "+userImGroup.getGroupname()+" 群发送"+amount+"元红包，"+message ;
			
		} else {
			return ActResult.fail("参数错误");
		}
		

		UserRedEnvelope ure = new UserRedEnvelope();
		ure.setId(dbUtils.CreateID());
		ure.setUserId(loginUser.getId());
		ure.setUserNike(loginUser.getNickName());
		ure.setUserAvatar(loginUser.getAvatar());
		ure.setCurrencyId(currencyId);
		ure.setToType(toType);
		ure.setToId(realTo);
		ure.setToCnt(toCnt);
		ure.setAllotType(allotType);
		ure.setPrice(price);
		ure.setStatus(1);	//状态 1:已发送 3:部分领取 4:全部领取 6:全部取消',
		ure.setAmount(amount);
		ure.setCreateTime(new Date());
		ure.setMessage(message);

		List<UserRedEnvelopeItem> items = new ArrayList<UserRedEnvelopeItem>();
		BigDecimal sum = makeItems(ure,items);
		if(amount.compareTo(sum) != 0) ActResult.fail("参数错误");
		
		BigDecimal absCash =BigDecimal.ZERO;
		BigDecimal absTicket =BigDecimal.ZERO;
		if(currencyId==0L) absCash= amount;
		if(currencyId==1L) absTicket= amount;

		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("opCode", "221");
		comMap.put("empId", loginUser.getId());
		comMap.put("absCash", absCash);
		comMap.put("absTicket", absTicket);
		comMap.put("key", ure.getId());
		comMap.put("desrc",desrc);
		comMap.put("updUser", loginUser.getUserName());
		
		String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/dealEmpBenefit", comMap);
		ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);

		if(acTicket.isSuccess()) {
			ure.setFlowCd(acTicket.getData().toString());
			userRedEnvelopeService.save(ure);
			for (UserRedEnvelopeItem userRedEnvelopeItem : items) {
				userRedEnvelopeItemService.save(userRedEnvelopeItem);
			}
			return ActResult.success(ure.getId()+"");
		} else {
			return ActResult.fail("红包发送失败，账户余额不足!请刷新后重试");
		}
	}

	/**
	 * 功能：查看红包   /friend/getRedEnvelope.user
	 * 
	 * @param envelopeId
	 * @return 数据(data).status 2:当前用户已领取/4:红包已被抢完/6:红包过期自动取消/其他:可以打开；数据(data).items 红包领取记录 （未拆封前不能查看）
	 */
	@RequestMapping("getRedEnvelope.user")
	public ActResult<UserRedEnvelope> getRedEnvelope(HttpServletRequest request,Long envelopeId){
		
		UserRedEnvelope ure = userRedEnvelopeService.getById(envelopeId);
		
		List<UserRedEnvelopeItem> list= getEnvelopeItems(envelopeId);
		
		List<UserRedEnvelopeItem> opends= getOpenedItems(list);
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
	 * 功能：拆开红包   /friend/openRedEnvelope.user
	 * 
	 * @param envelopeId
	 * @return 数据(data).status 2:当前用户已领取/4:红包已被抢完/6:红包过期自动取消/其他:可以打开；数据(data).items 红包领取记录 （未拆封前不能查看）
	 */
	@RequestMapping("openRedEnvelope.user")
	public ActResult<UserRedEnvelope> openRedEnvelope(HttpServletRequest request,Long envelopeId){
		UserRedEnvelope ure = userRedEnvelopeService.getById(envelopeId);
		return open(envelopeId, ure);
		
	}

	@SuppressWarnings("rawtypes")
	private  ActResult<UserRedEnvelope> open(Long envelopeId, UserRedEnvelope ure) {
		synchronized(ure) {
			List<UserRedEnvelopeItem> list= getEnvelopeItems(envelopeId);
			if(ure.getStatus() == 6) {
				ure.setItems(getOpenedItems(list));
			} else if(isOpened(envelopeId,loginUser.getId())) {
				ure.setStatus(2);			//已经打开过
				ure.setItems(getOpenedItems(list));
			} else {
				List<UserRedEnvelopeItem> closes= getClosedItems(list);
				if(closes.size()==0) {
					ure.setStatus(4);
					ure.setItems(list);
				} else {
					UserRedEnvelopeItem item = null;
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
					
					item.setUserId(loginUser.getId());
					item.setStatus(2);
					item.setUpdateTime(new Date());
					item.setUserNike(loginUser.getNickName());
					item.setUserAvatar(loginUser.getAvatar());
					
					saveEnvelopeItems(item,list);
	
					Map<String, Object> comMap = new HashMap<String, Object>();
					BigDecimal absCash =BigDecimal.ZERO;
					BigDecimal absTicket =BigDecimal.ZERO;
					if(item.getCurrencyId()==0L) absCash= item.getPrice();
					if(item.getCurrencyId()==1L) absTicket= item.getPrice();
					
					comMap.put("opCode", "220");
					comMap.put("empId", loginUser.getId());
					comMap.put("absCash", absCash);
					comMap.put("absTicket", absTicket);
					comMap.put("key", ure.getId());
					comMap.put("desrc","收到"+ item.getFromNike() + "红包："+ item.getPrice() +"元");
					comMap.put("updUser", loginUser.getUserName());
					
					String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/dealEmpBenefit", comMap);
					ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);
	
					if(acTicket.isSuccess()) {
						item.setFlowCd(acTicket.getData().toString());
						userRedEnvelopeItemService.update(item);
						
						ure.setItems(getOpenedItems(list));
					} else {
						item.setStatus(0);
						saveEnvelopeItems(item,list);
						
						return ActResult.fail("系统异常");
					}		
					//记录开封
					redisUtil.setMapData("REDIS_ENVELOPE_OPEN_" + envelopeId,loginUser.getId()+"", "1");
					redisUtil.setTime("REDIS_ENVELOPE_OPEN_" + envelopeId,60*60*24);	
				}
			}
			return ActResult.success(ure);
		}
	}

	
	/**
	 * 功能：获取红包领取记录   /friend/getRedEnvelopeItems.user
	 * 
	 * @param envelopeId
	 * 
	 * @return 数据(data).items 红包领取记录 （未拆封前不能查看）
	 */
	@RequestMapping("getRedEnvelopeItems.user")
	public ActResult<UserRedEnvelope> getRedEnvelopeItems(HttpServletRequest request,Long envelopeId){
		UserRedEnvelope ure = userRedEnvelopeService.getById(envelopeId);
		ure.setItems(getOpenedItems(getEnvelopeItems(envelopeId)));
		return ActResult.success(ure);
	}
	/**
	 * 微信亲友
	 * @param request
	 * @param model
	 * @param employeeType
	 * @return
	 */
	@RequestMapping("page.user")
	public ModelAndView page(HttpServletRequest request,ModelAndView model,Integer employeeType){
		model.addObject("employeeType", employeeType);//用户类型
		model.setViewName("friend_circle");
		return model;
	}
	private boolean isOpened(Long envelopeId,Long userId) {
		String jsonS=redisUtil.getMapData("REDIS_ENVELOPE_OPEN_" + envelopeId,userId+"");
		return !StringUtils.isEmpty(jsonS);
	}

	private List<UserRedEnvelopeItem> getOpenedItems(List<UserRedEnvelopeItem> list) {
		List<UserRedEnvelopeItem> rtn = new ArrayList<UserRedEnvelopeItem>();
		for (UserRedEnvelopeItem userRedEnvelopeItem : list) {
			if(userRedEnvelopeItem.getStatus()==2) {
				rtn.add(userRedEnvelopeItem);
			}
		}
		return rtn;
	}

	private List<UserRedEnvelopeItem> getClosedItems(List<UserRedEnvelopeItem> list) {
		List<UserRedEnvelopeItem> rtn = new ArrayList<UserRedEnvelopeItem>();
		for (UserRedEnvelopeItem userRedEnvelopeItem : list) {
			if(userRedEnvelopeItem.getStatus()==0) {
				rtn.add(userRedEnvelopeItem);
			}
		}
		return rtn;
	}
	
	private List<UserRedEnvelopeItem> getEnvelopeItems(Long envelopeId) {
		String jsonS=redisUtil.getData("REDIS_ENVELOPE_ITEMS_" + envelopeId);
		if(StringUtils.isEmpty(jsonS)) {
			UserRedEnvelopeItem query = new UserRedEnvelopeItem();
			query.setEnvelopeId(envelopeId);
			List<UserRedEnvelopeItem> items = userRedEnvelopeItemService.selectByModel(query);
			
			redisUtil.setData("REDIS_ENVELOPE_ITEMS_" + envelopeId, JsonUtil.toJsonString(items), 60*60*6); //6小时缓存
			return items;
		} else {
			return JsonUtil.getList(jsonS, UserRedEnvelopeItem.class);
		}
	}

	private void saveEnvelopeItems(UserRedEnvelopeItem item,List<UserRedEnvelopeItem> list) {
		userRedEnvelopeItemService.update(item);
		redisUtil.setData("REDIS_ENVELOPE_ITEMS_" + item.getEnvelopeId(), JsonUtil.toJsonString(list), 60*60*6); //6小时缓存
	}
	
	
	private BigDecimal makeItems(UserRedEnvelope ure,List<UserRedEnvelopeItem> items) {
		List<BigDecimal> prices = new ArrayList<BigDecimal>();
		BigDecimal sum = breakAmount(ure.getAllotType(),ure.getPrice(),ure.getAmount(),ure.getToCnt(), prices);
		Collections.sort(prices, new Comparator<BigDecimal>(){
			@Override
			public int compare(BigDecimal arg0, BigDecimal arg1) {
				return arg1.compareTo(arg0);
			}});
		
		int index=0;
		for (BigDecimal p : prices) {
			UserRedEnvelopeItem item = new UserRedEnvelopeItem();
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
	
	/**注册共通的用户，用手机号注册
	 * @param phone
	 * @return
	 */
	private ActResult<CommUser> registerCommonByPhone(String phone,int userType, HttpServletRequest request){
		CommUser user = new CommUser();
		user.setUserName(phone);
		user.setNickName(phone);
		String nickName=phone;
		if(nickName.length()>4) {
			nickName="1***"+nickName.substring(nickName.length()-4);
			user.setNickName(nickName);
		}
		user.setPassword(phone);
		user.setUserPhone(phone);
		user.setUsable(1);
		user.setUserType(userType);
		user.setEnabled(1);//用户默认是被激活的
		user.setUserCom("company");
		user.setUserIp(getIpAddr(request));
		return us.insert(user);
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
	


	private void make2contachs(String memo, UserContactsAppr appr) {
		UserContacts query;
		Date now=new Date();

		////////////////////////////////
		// 增加自己的联系人
		query = new UserContacts();
		query.setUserId(loginUser.getId());
		query.setContactsId(appr.getUserId());
		UserContacts uc1 = userContactsService.selectOneByModel(query);
		
		if(!StringUtils.isEmpty(uc1)){
			if(!StringUtils.isEmpty(memo)) {
				uc1.setContactsMemo(memo);
			}
			if("0".equals(uc1.getFirendType())) {
				uc1.setFirendType("1");
				uc1.setCreateTime(now);
			}
			if(StringUtils.isEmpty(uc1.getContactsImId())) {
				UserIm q = new UserIm();
				q.setUserId(uc1.getContactsId());
				List<UserIm> lst = userImService.selectByModel(q);
				if(lst!=null && !lst.isEmpty()) {
					uc1.setContactsImId(lst.get(0).getOpenimId());
				}
			}
			
			userContactsService.update(uc1);
		} else {
			uc1 = new UserContacts();
			uc1.setUserId(loginUser.getId());
			uc1.setContactsId(appr.getUserId());
			if(!StringUtils.isEmpty(memo)) {
				uc1.setContactsMemo(memo);
			} else {
				UserFactory emp = this.userService.getById(uc1.getContactsId());
				if(emp!=null) {
					uc1.setContactsMemo(emp.getNickName());
				}
			}
			uc1.setCreateTime(now);
			uc1.setApprFrom("app");				
			UserIm q = new UserIm();
			q.setUserId(uc1.getContactsId());
			List<UserIm> lst = userImService.selectByModel(q);
			if(lst!=null && !lst.isEmpty()) {
				uc1.setContactsImId(lst.get(0).getOpenimId());
			}
			uc1.setFirendType("1");
			
			userContactsService.save(uc1);
		}
		////////////////////////////////

		////////////////////////////////
		// 增加对方的联系人
		query = new UserContacts();
		query.setUserId(appr.getUserId());
		query.setContactsId(loginUser.getId());
		UserContacts uc2 = userContactsService.selectOneByModel(query);
		
		if(!StringUtils.isEmpty(uc2)){
			if("0".equals(uc2.getFirendType())) {
				uc2.setFirendType("1");
				uc2.setCreateTime(now);
			}
			if(StringUtils.isEmpty(uc2.getContactsImId())) {
				UserIm q = new UserIm();
				q.setUserId(uc2.getContactsId());
				List<UserIm> lst = userImService.selectByModel(q);
				if(lst!=null && !lst.isEmpty()) {
					uc2.setContactsImId(lst.get(0).getOpenimId());
				}
			}
			
			userContactsService.update(uc2);
		} else {
			uc2 = new UserContacts();
			uc2.setUserId(appr.getUserId());
			uc2.setContactsId(loginUser.getId());				
			if(!StringUtils.isEmpty(appr.getFriendMemo())) {
				uc2.setContactsMemo(appr.getFriendMemo());
			} else {
				uc2.setContactsMemo(loginUser.getNickName());
			}
			uc2.setCreateTime(now);
			uc2.setApprFrom(appr.getApprFrom());				
			UserIm q = new UserIm();
			q.setUserId(uc2.getContactsId());
			List<UserIm> lst = userImService.selectByModel(q);
			if(lst!=null && !lst.isEmpty()) {
				uc2.setContactsImId(lst.get(0).getOpenimId());
			}
			uc2.setFirendType("1");
			
			userContactsService.save(uc2);
		}
	}
}
