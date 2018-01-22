package com.wode.factory.user.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;

public class LoginUtil {

	public static void setLoginInfoForWeb(HttpServletRequest request, HttpServletResponse response, UserFactory login,
			String user_ticket, String remenber, RedisUtil redisUtil) throws UnsupportedEncodingException {

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

		// ticket
		Cookie cookie_ticket=null;
		if(!StringUtils.isEmpty(user_ticket)) {
			cookie_ticket = new Cookie("user_ticket", user_ticket);
			cookie_ticket.setPath("/");
		}

		// time_stamp
		Cookie cookie_time_stamp = new Cookie("time_stamp", (new Date()).getTime() + "");
		cookie_time_stamp.setPath("/");

		// 1年免登
		if ("1".equals(remenber)) {
			cookie_uid.setMaxAge(365 * (24 * 60 * 60 * 1000));
			cookie_nic.setMaxAge(365 * (24 * 60 * 60 * 1000));
			cookie_phone.setMaxAge(365 * (24 * 60 * 60 * 1000));
			if(cookie_ticket!=null)	cookie_ticket.setMaxAge(365 * (24 * 60 * 60 * 1000));
			cookie_time_stamp.setMaxAge(365 * (24 * 60 * 60 * 1000));
			cookie_nic.setMaxAge(365 * (24 * 60 * 60 * 1000));
		} else if("0".equals(remenber)) {	
		} else {
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for (int i = 0; i < cookies.length; i++) {
					String name = cookies[i].getName();
					//用户名及uuid保留
					if("user_ticket".equals(name)) return;
				}
			}
		}

		response.addCookie(cookie_uid);
		response.addCookie(cookie_nic);
		response.addCookie(cookie_phone);
		if(cookie_ticket!=null)	response.addCookie(cookie_ticket);
		response.addCookie(cookie_time_stamp);		
	}
	
	public static void clearLoginInfoForWeb(HttpServletRequest request,
			HttpServletResponse response, RedisUtil redisUtil) {
		// 清除cookid
		Cookie[] cookies = request.getCookies();
		try {

			//String user_ticket = CookieUtils.getTicket(request);

			String uuid = CookieUtils.getUUID(request, response);
			// 删除缓存中会话信息
			SessonRedisUtil.clearSession(uuid,redisUtil);
			//sesson.delete(Constant.SESSION_KEY + user_ticket);
			if(cookies!=null){
				for (int i = 0; i < cookies.length; i++) {
					String name = cookies[i].getName();
					
					//用户名及uuid保留
					if("Factory_userName".equals(name) || "uuid".equals(name)) continue;
					
					Cookie cookie = new Cookie(name, null);
					cookie.setMaxAge(0);
					cookie.setPath("/");// 根据你创建cookie的路径进行填写
					response.addCookie(cookie);
				}
			}

		} catch (Exception ex) {
			System.out.println("清空Cookies发生异常！");
		}
	}
}
