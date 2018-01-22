package com.wode.api.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.wode.api.facade.LoginFacade;
import com.wode.api.util.Constant;
import com.wode.api.util.LoginUserManage;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;

public class BaseController {

	protected UserFactory loginUser;
	@Autowired
	private LoginFacade loginFacade;

	@ModelAttribute
	public UserFactory getLoginUser(HttpServletRequest request) {
		loginUser = LoginUserManage.getUser();
		if (loginUser != null) {
			return loginUser;
		}

		String ticket = request.getParameter(Constant.Ticket);

		if (ticket != null) {
			ActResult<UserFactory> act = loginFacade.hasLogin(ticket);
			if (act.isSuccess()) {
				loginUser = act.getData();
			}
		} else {
			String uid = request.getParameter(Constant.UserId);
			if (StringUtils.isEmpty(uid)) {
				String userId = request.getParameter("userId");
				if(!StringUtils.isEmpty(userId)){
					uid = userId;
				}
			}
			if (StringUtils.isEmpty(uid)) {
				Cookie[] cookies = request.getCookies();
				if(cookies!=null){
					for (Cookie ck : cookies) {
						if("uid".equals(ck.getName())) {
							uid=ck.getValue();
							break;
						}
					}
				}
			}
			if (!StringUtils.isEmpty(uid)) {
				ActResult<UserFactory> act = loginFacade.hasLogin(Long.valueOf(uid));
				if (act.isSuccess()) {
					loginUser = act.getData();
				}
			}
		}
		return loginUser;
	}
}
