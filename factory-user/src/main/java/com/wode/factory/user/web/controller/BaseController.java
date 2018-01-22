package com.wode.factory.user.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.CookieUtils;
import com.wode.factory.user.util.SessonRedisUtil;
import com.wode.model.CommUser;

/**
 * @author zhengxiongwu
 */
public class BaseController {
	@Qualifier("redis")
	@Autowired
	protected RedisUtil redisUtil;

	@Qualifier("userService")
	@Autowired
	protected UserService userService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	
	protected UserFactory getUser(HttpServletRequest request, HttpServletResponse response) {
		
		//已经取过
		UserFactory user=null;
		String uuid = CookieUtils.getUUID(request, response);
		//session 中取
		user = SessonRedisUtil.getLoginUser(uuid, redisUtil,userService);
		
		//session 中取不到
		if(user == null) {

			user=this.getRuntimeUser(request, response);
		}
		return user;
	}

	//通过ticket获取用户信息
	protected ActResult<UserFactory> getUserByTicket(String user_ticket) {
		if(!StringUtils.isEmpty(user_ticket)) {
			//共通用户中查询
			ActResult<CommUser> ar = us.hasLogin(user_ticket);
			if(ar.isSuccess()){
				CommUser cuser = ar.getData();
				//判断本地是否存在
				UserFactory user = userService.getById(cuser.getUserId());
				if(user==null){
					//不存在是copy到本地
					user = new UserFactory();
					user.setId(ar.getData().getUserId());
					user.setUserName(cuser.getUserName());
					user.setEmail(cuser.getUserEmail());
					user.setEnabled(cuser.getEnabled());
					user.setUsable(cuser.getUsable());
					user.setCreatTime(new Date());
					user.setNickName(cuser.getNickName());
					user.setEnabled(1);
					user.setType(1);
					user.setPhone(cuser.getUserPhone());
					userService.specialSave(user);
				}
				
				return ActResult.success(user);
			}
		}
		
		return null;
	}
	
	protected UserFactory getRuntimeUser(HttpServletRequest request, HttpServletResponse response) {
		UserFactory user=null;
		String uuid = CookieUtils.getUUID(request, response);
		
		//通过userId取
		String uid = SessonRedisUtil.getLoginId(request, response, redisUtil);
		if(!StringUtils.isEmpty(uid)) {
			user = userService.getById(NumberUtil.toLong(uid));
		}
		
		//通过ticket取
		if(user == null) {
			String user_ticket= CookieUtils.getTicket(request) ;//获取cookie中ticket
			ActResult<UserFactory> act = getUserByTicket(user_ticket);
			if(act!=null && act.isSuccess()) {
				user=act.getData();
			}
		}
		if(user!=null) {
			//存入session
			SessonRedisUtil.setLoginId(uuid, user, redisUtil);
		}
		
		return user;
	}
}
