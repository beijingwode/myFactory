package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SupplierEvent;
import com.wode.factory.model.SupplierPrize;
import com.wode.factory.model.Teatime;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserPrizeTake;
import com.wode.factory.model.UserSignRecord;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.TeaTimeService;
import com.wode.factory.user.service.SupplierEventService;
import com.wode.factory.user.service.SupplierPrizeService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserPrizeTakeService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserSignRecordService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.Constant;

@Controller
@RequestMapping("/acticity")
public class EnterpriseActicityController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SupplierPrizeService supplierPrizeService;
	
	@Autowired
	private UserPrizeTakeService userPrizeTakeService;
	
	@Autowired
	private TeaTimeService teaTimeService;
	
	@Autowired
	private SupplierEventService supplierEventService;
	
	@Autowired
	private UserSignRecordService userSignRecordService;
	
	@Autowired
	private UserWeixinService userWeixinService;
	
	/**
	 * 跳转企业奖品领取页面
	 * @return
	 */
	@RequestMapping("/toPrizeTakePage")
	@ResponseBody
	public ModelAndView toPrizeTakePage(Long prizeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", prizeId);
		ModelAndView model = null;
		SupplierPrize supplierPrize = supplierPrizeService.findPrizeByMap(map);
		if(("1").equals(supplierPrize.getType())) {
			model = new ModelAndView("acticity/prize_take_page");
			model.addObject("supplierPrize", supplierPrize);
		}
		if(("2").equals(supplierPrize.getType())) {
			model = new ModelAndView("acticity/get_ticket_page");
			model.addObject("prizeId", supplierPrize.getId());
			model.addObject("ticket", supplierPrize.getTicket());
		}
		if(("3").equals(supplierPrize.getType())) {
			model = new ModelAndView("acticity/get_exp_acticity_page");
			model.addObject("prizeId", supplierPrize.getId());
		}
		return model;
	}
	
	/**
	 * 
	 * 填写领取信息页面
	 * @return
	 */
	@RequestMapping("/toAddMsgPage")
	@ResponseBody
	public ModelAndView toAddMsgPage(Long supplierPrizeId) {
		ModelAndView model = new ModelAndView("acticity/prize_take_information");
		model.addObject("prizeId", supplierPrizeId);
		return model;
	}
	
	/**
	 * 领取奖品保存收货信息
	 * @return
	 */
	@RequestMapping("/takePrize")
	@ResponseBody
	public ActResult<Object> takePrize(UserPrizeTake userPrizeTake) {
		if(userPrizeTake.getPrizeId() == null) {
			return ActResult.fail("奖品信息领取错误");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		UserPrizeTake userPrize = new UserPrizeTake();
		if(userPrizeTake.getMobile() != null) {
			map.put("mobile", userPrizeTake.getMobile());
			map.put("prizeId", userPrizeTake.getPrizeId());
			userPrize = userPrizeTakeService.findUserPrizeByMap(map);
			if(userPrize != null) {
				return ActResult.success(userPrize.getPrizeId());
			}
		}
		if(userPrizeTake.getEmail() != null) {
			map.clear();
			map.put("prizeId", userPrizeTake.getPrizeId());
			map.put("email", userPrizeTake.getEmail());
			userPrize = userPrizeTakeService.findUserPrizeByMap(map);
			if(userPrize != null) {
				return ActResult.success(userPrize.getPrizeId());
			}
		}
	
		userPrizeTake.setCreateDate(new Date());
		userPrizeTake.setStatus("1");
		userPrizeTakeService.save(userPrizeTake);
		return ActResult.successSetMsg("success");
	}
	
	/**
	 * 跳转到领取成功页面
	 * @param prizeId
	 * @return
	 */
	@RequestMapping("/toTakeSuccessPage")
	@ResponseBody
	public ModelAndView toTakeSuccessPage(Long prizeId) {
		SupplierPrize supplierPrize = supplierPrizeService.getById(prizeId);
		ModelAndView model = null;
		if(("1").equals(supplierPrize.getType())) {
			model = new ModelAndView("acticity/prize_take_success_page");
			model.addObject("supplierPrize", supplierPrize);
		}
		if(("2").equals(supplierPrize.getType())) {
			model = new ModelAndView("acticity/ticket_take_success_page");
			model.addObject("ticket", supplierPrize.getTicket());
		}
		if(("3").equals(supplierPrize.getType())) {
			model = new ModelAndView("acticity/exp_acticity_success_page");
		}
		return model;
	}

	/**
	 * 跳转到活动页
	 * @return
	 */
	@RequestMapping("/toActicityPage")
	public ModelAndView toActicityPage() {
		ModelAndView model = new ModelAndView("acticity/push_message");
		return model;
	}
	
	/**
	 * 跳转到添加用户信息页
	 * @return
	 */
	@RequestMapping("/toPushMsgPage")
	public ModelAndView toPushMsgPage() {
		ModelAndView model = new ModelAndView("acticity/add_push_message");
		return model;
	}
	
	/**
	 * 添加用户
	 * @param name
	 * @param phone
	 * @return
	 */
	@RequestMapping("/addUserMessage")
	@ResponseBody
	public ActResult<Object> addUserMessage(String name,String phone){
		Teatime teatime = new Teatime();
		teatime.setUsername(name);
		teatime.setTel(phone);
		teatime.setCompany("1");
		teatime.setCreatetime(new Date());
		teaTimeService.insert(teatime);
		return ActResult.successSetMsg("");
	}
	
	
	//=====================================拜博口腔抽奖活动==============================================
	/**
	 * 跳转活动签到页面
	 * @return
	 */
	@RequestMapping("/toSignPage{activityId}")
	public ModelAndView toSignPage(@PathVariable Long activityId) {
		ModelAndView model =  new ModelAndView("acticity/to_sign_in");
		SupplierEvent supplierEvent = supplierEventService.getById(activityId);
		if(supplierEvent==null) {
			// 数据异常 直接跳到首页
			model.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
			return model;
		}
		model.addObject("activityId", activityId);
		return model;
	}
	
	/**
	 * 跳转活动签到页面
	 * @return
	 */
	@RequestMapping("/signPage{activityId}")
	@ResponseBody
	public ModelAndView signPage(@PathVariable Long activityId,String openId,Long userId) {
		ModelAndView model =  new ModelAndView("acticity/sign_in");
		SupplierEvent supplierEvent = supplierEventService.getById(activityId);
		if(supplierEvent==null) {
			// 数据异常 直接跳到首页
			model.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
			return model;
		}
		String name=null;
		String phone=null;
		UserFactory user = null;
		if(userId!=null) {
			user = userService.getById(userId);
			if(user == null || !supplierEvent.getEnterpriseId().equals(user.getSupplierId())) {
				// 数据异常 直接跳到首页
				model.setViewName("redirect:"+"http://"+Constant.SYSTEM_DOMAIN+"/index_m.htm");
				return model;
			}
		}
		if(user != null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", user.getId());
			UserSignRecord userSignRecord = userSignRecordService.getRecordByMap(map);
			if(userSignRecord == null) {
				name = user.getNickName();
				phone = user.getPhone();
			}else {
				name = userSignRecord.getName();
				phone = userSignRecord.getPhone();
			}
		}
		model.addObject("activityId", activityId);
		model.addObject("enterpriseId", supplierEvent.getEnterpriseId());
		model.addObject("wxBanner", supplierEvent.getWxPageBanner());
		model.addObject("userId", userId);
		model.addObject("openId", openId);
		model.addObject("phone", phone);
		model.addObject("name", name);
		return model;
	}
	/**
	 * 添加签到用户信息
	 * @param name
	 * @param phone
	 * @param enterpriseId
	 * @param activityId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addSignMsg")
	@ResponseBody
	public ActResult<Object> addSignMsg(String name,String phone,String enterpriseId,Long activityId,String openId,Long userId) {
		//注册部分
		if(userId!=null) {
			// 已有用户
			
		} else {
			// 没有用户
			Map<String, Object> comMap = new HashMap<String, Object>();
			comMap.put("userName", phone);
			comMap.put("phone", phone);
			comMap.put("name", name);
			comMap.put("email", null);
			comMap.put("seniority",null);
			comMap.put("enterpriseId",enterpriseId);
			comMap.put("fromId", null);
			comMap.put("unSafe", "1");

			String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/addEmployee", comMap);
			ActResult<String> art = JsonUtil.getObject(ticketResult, ActResult.class);
			if(art.isSuccess()) {
				userId=NumberUtil.toLong(art.getData());
			} else {
				if(art.getMsg().contains("该手机/邮箱已经绑定过")) {
					return ActResult.fail("该手机号已通过其他方式注册，请先登录后再签到");
				} else {
					return ActResult.fail("签到失败，请联系技术支持");
				}
			}
		}
		//wx号绑定
		if(!StringUtils.isEmpty(openId) && !"null".equals(openId)) {
			UserWeixin user = userWeixinService.getOneModelByOpenId(openId);
			if(user==null) {
				user = new UserWeixin();
				user.setAppId(WxOpenService.APP_ID);
				user.setOpenId(openId);
				user.setUserId(userId);
				userWeixinService.save(user);
			}
		}
		// 不推送500券
		
		//签到记录
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		UserSignRecord userSignRecord = userSignRecordService.getRecordByMap(map);
		int ticket = 0;
		if(userSignRecord == null) {
			ticket = getTicket(activityId);
			map.clear();
			map.put("acticityId", activityId);
			Integer maxLuckyNumber = userSignRecordService.findMaxLuckyNumber(map);
			if(maxLuckyNumber == null) {
				maxLuckyNumber = 0;
			}
			userSignRecord = new UserSignRecord();
			userSignRecord.setName(name);
			userSignRecord.setActicityId(activityId);
			userSignRecord.setPhone(phone);
			userSignRecord.setUserId(userId);
			userSignRecord.setCreateDate(new Date());
			userSignRecord.setType(0);
			userSignRecord.setLuckyNumber(maxLuckyNumber+1);
			userSignRecordService.save(userSignRecord);
		}else {
			userSignRecord.setName(name);
			userSignRecord.setPhone(phone);
			userSignRecordService.update(userSignRecord);
			if(userSignRecord.getTicket() == null) {
				ticket = getTicket(activityId);
			}else {
				ticket = userSignRecord.getTicket().intValue();
			}
		}
		map.clear();
		map.put("recordId", userSignRecord.getId());
		map.put("ticket", ticket);
		return ActResult.success(map);
	}
	
	//处理活动随即内购卷
	public int getTicket(Long acticityId) {
		int ticket = 0;
		SupplierEvent supplierEvent = supplierEventService.getById(acticityId);
		if(supplierEvent != null) {
			int Min = supplierEvent.getMaxTicket().multiply(new BigDecimal("0.1")).intValue();
			int Max = supplierEvent.getMaxTicket().intValue();
			ticket = Min + (int)(Math.random() * ((Max - Min) + 1));
		}
		return ticket;
	}
	
	/**
	 * 跳转到签成功页面
	 * @param recordId
	 * @return
	 */
	@RequestMapping("/toSignSuccessPage")
	public ModelAndView toSignSuccessPage(Long recordId,String ticket) {
		ModelAndView model = new ModelAndView("acticity/sign_in_success");
		UserSignRecord userSignRecord = userSignRecordService.getById(recordId);
		SupplierEvent supplierEvent = supplierEventService.getById(userSignRecord.getActicityId());
		model.addObject("companyName", supplierEvent.getEnterpriseName());
		model.addObject("luckyNumber", userSignRecord.getLuckyNumber());
		model.addObject("ticket", ticket);
		model.addObject("recordId", recordId);
		return model;
	}
	
	/**
	 * 更新用户内购卷信息（用户涂擦后）
	 * @param recordId
	 * @return
	 */
	@RequestMapping("/updateTicketMsg")
	public ActResult<Object> updateTicketMsg(Long recordId,String ticket) {
		//更新签到用户内购卷信息
		UserSignRecord userSignRecord = userSignRecordService.getById(recordId);
		if(userSignRecord.getTicket() == null) {
			userSignRecord.setTicket(new BigDecimal(ticket));
			userSignRecordService.update(userSignRecord);
			
			//更新用户内购卷
			Map<String, Object> comMap = new HashMap<String, Object>();
			comMap.put("empId", userSignRecord.getUserId());
			comMap.put("limitm", ticket);
			comMap.put("empName", userSignRecord.getName());
			comMap.put("desrc", "线下活动奖励");
			comMap.put("updName",userSignRecord.getPhone());
			comMap.put("key",userSignRecord.getId());

			String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/fisrtPrize", comMap);
		}
		return ActResult.success(null);
	}
}
