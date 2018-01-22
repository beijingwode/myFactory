package com.wode.factory.supplier.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.facade.LoginFacade;
import com.wode.factory.supplier.util.LoginUserManage;


public class BaseController {
	
	protected UserFactory loginUser;
	@Autowired
	private LoginFacade loginFacade;
		
	 @ModelAttribute  
     public UserFactory getLoginUser(HttpServletRequest request) {
		 loginUser=LoginUserManage.getUser();
		 if(loginUser!=null){
			 return loginUser;
		 }
		 
		 String ticket=request.getParameter("ticket");
		 
		 if(ticket!=null){
			 ActResult<UserFactory> act=loginFacade.hasLogin(ticket);
			 if(act.isSuccess()){
				 loginUser=act.getData();
			 }
		 } else {
			 String uid = request.getParameter("uid");
			 if (!StringUtils.isEmpty(uid)) {
				 ActResult<UserFactory> act=loginFacade.hasLogin(Long.valueOf(uid));
				 if (act.isSuccess()) {
					loginUser = act.getData();
				}
			}
		 }
		 return loginUser;
     }  
}
