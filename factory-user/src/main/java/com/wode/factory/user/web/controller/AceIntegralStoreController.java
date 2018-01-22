package com.wode.factory.user.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wode.common.constant.UserConstant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.EncryptUtils;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.user.model.AceUserVo;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.SessonRedisUtil;

@Controller
@RequestMapping("/store")
public class AceIntegralStoreController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	private static final String ERROR_URL = "http://www.ace-elite.com/";
	
	@Autowired
	private UserBalanceService userBalanceService;
	
	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);

	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	@Value("#{configProperties['ace.url']}")
	private  String aceUrl;
	
	@RequestMapping("")
	@NoCheckLogin
	public String checkUserMsg(String ticket,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		if(ticket == null || "".equals(ticket)) {
			return "redirect:"+ERROR_URL;
		}
		Map paramPush=new HashMap();
		paramPush.put("ticket", ticket);
		Long time = System.currentTimeMillis();
		paramPush.put("M8", EncryptUtils.Md5Encode(ticket+time));
		paramPush.put("M9", time);
		String str = null;
		Map<String,Object> map = new HashMap<String,Object>();
		Integer code = 0;
		try {
			str = HttpClientUtil.sendHttpRequest("post", aceUrl + "aceserver/queryUserInfo.r", paramPush);
			map = JsonUtil.getMap4Json(str);
			code = (Integer) map.get("code");
		} catch (Exception e) {
			return "redirect:"+ERROR_URL;
		}
		if(code == 2) {
			return "redirect:"+ERROR_URL;
		}
		String msg = (String) map.get("msg");
		AceUserVo aceUser = JsonUtil.getObject(map.get("data").toString(), AceUserVo.class);
		String userid = aceUser.getUserid();
		if(aceUser.getPhone() == null) {
			return "redirect:"+ERROR_URL;
		}
		ActResult<UserFactory> userFa = userService.findByPhone(aceUser.getPhone());
		
		if(!userFa.isSuccess()) {//用户不存在的 则进行添加
			Map<String, Object> comMap = new HashMap<String, Object>();
			comMap.put("userName", aceUser.getPhone());
			comMap.put("phone", aceUser.getPhone());
			comMap.put("name", aceUser.getNickName());
			comMap.put("email", null);
			comMap.put("seniority",null);
			comMap.put("enterpriseId","201712221700825");
			comMap.put("fromId", null);
			comMap.put("empNumber", 10000);
			comMap.put("sectionName", aceUser.getCompany());
			comMap.put("unSafe", "0");

			String ticketResult = HttpClientUtil.sendHttpRequest("post", com.wode.factory.user.util.Constant.QIYE_API_URL + "api/addEmployee", comMap);
			ActResult<String> art = JsonUtil.getObject(ticketResult, ActResult.class);
			if(art.isSuccess()) {
				UserFactory user = userService.getById(Long.valueOf(art.getData()));
				boolean status = setLoginMsg(aceUser,user,userid,ticket,request,response);
				if(status) {
					return "redirect:"+ERROR_URL;
				}
				return "redirect:index.html";
			}
		}else {//用户已经存在直接保存session
			UserFactory user = userFa.getData();
			boolean status = setLoginMsg(aceUser,user,userid,ticket,request,response);
			if(status) {
				return "redirect:"+ERROR_URL;
			}
			return "redirect:index.html";
		}
		return "redirect:"+ERROR_URL;
	}
	
	public boolean setLoginMsg(AceUserVo aceUser,UserFactory user,String userid,String ticket,HttpServletRequest request,HttpServletResponse response) throws Exception {
		boolean flag = checkMsg(user,userid);
		if(flag) {
			return flag;
		}
		ActResult<String> active = getActive(user);
		//更新用户内购卷
		UserBalance userBalance = userBalanceService.findByUserAndType(user.getId(), Long.valueOf("1"));
		if(userBalance == null) {
			userBalance = new UserBalance();
			userBalance.setUserId(user.getId());
			userBalance.setCurrencyId(Long.valueOf("1"));
			userBalance.setBalance(aceUser.getAccount());
			userBalanceService.save(userBalance);
		}else {
			userBalance.setBalance(aceUser.getAccount());
			userBalanceService.update(userBalance);
		}
		// 计入cookie及缓存
		setLoginInfoForWebEx(request, response, user,active.getData() , ticket, redisUtil);//只设置cookie的值
		return false;
	}
	
	
	public boolean checkMsg(UserFactory user,String userid) {
		if(user.getAddress() != null && !user.getAddress().equals(userid)) {
			return true;
		}
		if(userid != null && !"".equals(userid)) {//更新ace用户id
			if(!userid.equals(user.getAddress())) {
				user.setAddress(userid);
				userService.update(user);
			}
		}
		return false;
	}
	
	public ActResult<String> getActive(UserFactory user){
		ActResult<String> active = us.active(user.getId(),UserConstant.COMFROM,"1", false, null);
		if (StringUtils.isEmpty(user.getNickName())) {
			user.setNickName(user.getUserName());
			if (user.getNickName().equals(user.getPhone())) {
				String nickName = user.getPhone();
				if (nickName.length() > 4) {
					nickName = "1***" + nickName.substring(nickName.length() - 4);
					user.setNickName(nickName);
				}
			}
		}
		return active;
	}
	
	public static void setLoginInfoForWebEx(HttpServletRequest request, HttpServletResponse response, UserFactory login,
			String user_ticket, String ticket, RedisUtil redisUtil) throws UnsupportedEncodingException {

		// 从cookie中那唯一标识
		String uuid = CookieUtils.getUUID(request, response);
		// 会话信息存入缓存20分钟
		SessonRedisUtil.setLoginId(uuid, login, redisUtil);
		
		// 用户ID
		Cookie cookie_uid = new Cookie("uid", login.getId() + "");
		cookie_uid.setPath("/");

		// 昵称
		Cookie cookie_nic = new Cookie("nickname", URLEncoder.encode(login.getNickName(), "utf-8"));
		cookie_nic.setPath("/");

		// 手机号
		Cookie cookie_phone = new Cookie("phone", login.getUserName());
		cookie_phone.setPath("/");
		
		// Ace用户ticket
		Cookie cookie_aceTicket = new Cookie("cookie_aceTicket", ticket);
		cookie_aceTicket.setPath("/");

		// ticket
		Cookie cookie_ticket=null;
		if(!StringUtils.isEmpty(user_ticket)) {
			cookie_ticket = new Cookie("user_ticket", user_ticket);
			cookie_ticket.setPath("/");
		}

		// time_stamp
		Cookie cookie_time_stamp = new Cookie("time_stamp", (new Date()).getTime() + "");
		cookie_time_stamp.setPath("/");


		response.addCookie(cookie_uid);
		response.addCookie(cookie_nic);
		response.addCookie(cookie_phone);
		response.addCookie(cookie_aceTicket);
		if(cookie_ticket!=null)	response.addCookie(cookie_ticket);
		response.addCookie(cookie_time_stamp);		
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
	
}
