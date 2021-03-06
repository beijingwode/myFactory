package com.wode.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wode.api.facade.LoginFacade;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;



public class LoginCheckInteceptor implements HandlerInterceptor {

//	private static Logger logger= LoggerFactory.getLogger(LoginCheckInteceptor.class);
		
	@Autowired
	private LoginFacade loginFacade;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String ticket=request.getParameter(Constant.Ticket);
		 
		 if(!StringUtils.isEmpty(ticket)){
			 ActResult<UserFactory> act = loginFacade.hasLogin(ticket);
			 if(act.isSuccess()){
				 LoginUserManage.setUser(act.getData());
				 return true;
			 }else{
				 act.setMsg("请登录后操作");
				 response.setCharacterEncoding("utf-8");   
				 response.setContentType("text/html; charset=utf-8");
				 response.getWriter().write(JsonUtil.toJsonString(act));
			 }
		 }else{
			 String uid=request.getParameter(Constant.UserId);
			 if(!StringUtils.isEmpty(uid) && !"true".equals(uid)) {
				 ActResult<UserFactory> act = loginFacade.hasLogin(Long.valueOf(uid));
				 if(act.isSuccess()){
					 LoginUserManage.setUser(act.getData());
					 return true;
				 } else {
					 ActResult act1=ActResult.fail("请登录后操作");
					 response.setCharacterEncoding("utf-8"); 
					 response.setContentType("text/html; charset=utf-8");
					 response.getWriter().write(JsonUtil.toJsonString(act1));
				 }
			 } else {
				 ActResult act=ActResult.fail("请登录后操作");
				 response.setCharacterEncoding("utf-8"); 
				 response.setContentType("text/html; charset=utf-8");
				 response.getWriter().write(JsonUtil.toJsonString(act));
			 }
		 }
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LoginUserManage.removeUser();
	}

}
