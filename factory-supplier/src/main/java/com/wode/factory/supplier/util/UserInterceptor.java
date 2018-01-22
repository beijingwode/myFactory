package com.wode.factory.supplier.util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.constant.UserConstant;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.service.ApprSupplierService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;

/**
 * Created by Bing King on 2015/3/3.
 */
public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	@Autowired
	private ApprSupplierService apprSupplierService;

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
		} else {
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute(UserConstant.USER_SESSION) != null) {
				com.wode.factory.model.UserFactory userModel = getSessionUserModel(request);
				Supplier supplier = supplierService.getById(userModel.getSupplierId());
				if (supplier == null) {
					ApprSupplier appr = apprSupplierService.getSupplierApprIng(userModel.getSupplierId());
					
					if(appr == null) {
						String curPath = request.getRequestURL().toString();
						if (curPath.indexOf("supplier/tocreatesupplierinfo") >= 0 || curPath.indexOf("supplier/torecruitmenttype") >= 0
								|| curPath.indexOf("supplier/torecruitmentnewbrand") >= 0 || curPath.indexOf("supplier/torecruitmentstore") >= 0
								|| curPath.indexOf("supplier/torecruitmentcontract") >= 0 || curPath.indexOf("supplier/tocreatebrand") >= 0
								|| curPath.indexOf("supplier/enterMain") >= 0) {
							return true;
						}
						response.sendRedirect(request.getContextPath() + "/supplier/enterMain.html");
						return false;
					} else {

						if (appr.getEnterType() < 5) {
							String curPath = request.getRequestURL().toString();
							if (curPath.indexOf("supplier/tocreatesupplierinfo") >= 0 || curPath.indexOf("supplier/torecruitmenttype") >= 0
									|| curPath.indexOf("supplier/torecruitmentnewbrand") >= 0 || curPath.indexOf("supplier/torecruitmentstore") >= 0
									|| curPath.indexOf("supplier/torecruitmentcontract") >= 0 || curPath.indexOf("supplier/tocreatebrand") >= 0
									|| curPath.indexOf("supplier/enterMain") >= 0) {
								return true;
							}
							response.sendRedirect(request.getContextPath() + "/supplier/enterMain.html");
							return false;
						}else if(appr.getEnterType()==5&&appr.getStatus()==-1){
							response.sendRedirect(request.getContextPath() + "/supplier/enterMain.html");
							return false;
						}else{
							response.sendRedirect(request.getContextPath() + "/product/toNotthroughaudit.html");
							return false;
							
						}
					}
					
				} else {

					if (supplier.getStatus() >= 2) {
						String curPath = request.getRequestURL().toString();
//						// 比如登录、退出、首页等页面无需登录，即此处要放行
//						if (curPath.indexOf("supplier/tocreatesupplierinfo") >= 0 || curPath.indexOf("supplier/torecruitmenttype") >= 0
//								|| curPath.indexOf("supplier/torecruitmentnewbrand") >= 0 || curPath.indexOf("supplier/torecruitmentstore") >= 0
//								|| curPath.indexOf("supplier/torecruitmentcontract") >= 0 ) { //|| curPath.indexOf("supplier/tocreatebrand") >= 0 不需拦截
//							response.sendRedirect(request.getContextPath() + "/user/tosuppliermain.html");
//							return false;
//						} else {
							return true;
//						}
					} else {
						response.sendRedirect(request.getContextPath() + "/product/toNotthroughaudit.html");
						return false;
					}
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/user/login.html");
				return false;
			}

		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

	/**
	 * 在session中获取usermodel
	 *
	 * @param request
	 * @return com.wode.factory.model.UserFactory
	 */
	private com.wode.factory.model.UserFactory getSessionUserModel(HttpServletRequest request) {
		return UserInterceptor.getSessionUser(request,shopService);
	}
	
	public static com.wode.factory.model.UserFactory getSessionUser(HttpServletRequest request,ShopService shopService) {
		HttpSession session = request.getSession(false);
		if (session != null) {
//			logger.info("\n*************************在session中获取CookieUserModel		，会话id为：			" + session.getId());
		}
		
		com.wode.factory.model.UserFactory user_ = (com.wode.factory.model.UserFactory)(session != null ? session.getAttribute(UserConstant.USER_SESSION) : null);
		
		if(user_!=null && (user_.getShopCount()==null || user_.getShopCount()<1)) {
			//重新设定店铺个数
			Shop p = new Shop();
			p.setSupplierId(user_.getSupplierId());
			user_.setShopCount(shopService.selectByModel(p).size());
			session.setAttribute(UserConstant.USER_SESSION, user_);			
		}
		
		return user_;
	}
}
