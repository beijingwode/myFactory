package com.wode.factory.user.util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.user.service.UserService;
import com.wode.model.CommUser;

/**
 * Created by Bing King on 2015/3/3.
 */
public class UserInterceptor implements HandlerInterceptor {
	@Qualifier("redis")
	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	private UserService userService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

		//注解 排除方法  NoCheckLong
		if (!(o instanceof HandlerMethod))
			return true;
		HandlerMethod handlerMethod = (HandlerMethod) o;
		Method method = handlerMethod.getMethod();
		//fixed by zhangtong ,that when user logout do not rerecord session and cookie;
		NoCheckLogin nocheck = method.getAnnotation(NoCheckLogin.class);
		if (nocheck != null) {
			return true;
		}
		
		String uuid = CookieUtils.getUUID(request, response);

		//session 中取
		if(SessonRedisUtil.getLoginUser(uuid, redisUtil,userService) != null) return true;
		
		//通过userId取
		String uid = SessonRedisUtil.getLoginId(request, response, redisUtil);
		if(!StringUtils.isEmpty(uid)) return true;

		String user_ticket= CookieUtils.getTicket(request) ;//获取cookie中ticket
		if(!StringUtils.isEmpty(user_ticket)) {
			//共通用户中查询
			ActResult<CommUser> ar = us.hasLogin(user_ticket);
			if(ar.isSuccess()){
				return true;
			}
		}

        String url=request.getRequestURI()+"?"+request.getQueryString();
        
        String from= org.apache.commons.codec.binary.Base64.encodeBase64String(url.getBytes());
        response.sendRedirect(Constant.COMM_USER_URL+"?from=myFactory&domain="+Constant.SYSTEM_DOMAIN+"&returnUrl="+from);        
        
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    	
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
