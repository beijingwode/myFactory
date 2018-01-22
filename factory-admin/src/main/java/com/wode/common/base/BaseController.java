package com.wode.common.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.wode.common.constant.Constant;
import com.wode.sys.model.SysUser;


public class BaseController {
	
	protected SysUser loginUser;
	
	 @ModelAttribute  
     public void getLoginUser(HttpServletRequest request) {  
		 Object obj=request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		 if(obj!=null){
			 loginUser=(SysUser) obj;
		 }
     }  
}
