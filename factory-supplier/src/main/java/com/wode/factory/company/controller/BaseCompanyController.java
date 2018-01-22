package com.wode.factory.company.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wode.common.constant.UserConstant;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

/**
 * @author zhengxiongwu
 */
public class BaseCompanyController extends BaseSpringController {

    @Autowired
    @Qualifier("supplierService")
    private SupplierService supplierService;

	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	@Autowired
	private EnterpriseService enterpriseService;
	/**
	 * 记录用户登录后企业session值
	 */
	public final static String ENTERPRISE_SESSION = "enterpriseSession";

	/**
	 * 在session中获取usermodel
	 * @param request
	 * @return com.wode.model.User
	 */
	protected com.wode.factory.model.UserFactory getSessionUserModel(HttpServletRequest request) {

		UserFactory uf =  UserInterceptor.getSessionUser(request,shopService);

		if(uf != null && uf.getSupplierId() == null) {
	        Supplier supplier = supplierService.getById(uf.getSupplierId());
	        if (supplier != null) {
	            uf.setSupplierId(supplier.getId());
	        }
		}
        
		return uf;
        
	}


	/**
	 * 在session中获取usermodel
	 * @param request
	 * @return com.wode.model.User
	 */
	protected Long getSupplierId(HttpServletRequest request) {
		EnterpriseVo ent = getEnterpriseInfo(request);
		if(ent!= null && ent.getId() != null) return ent.getId();
		
		UserFactory uf = getSessionUserModel(request);
		if(uf != null) return uf.getSupplierId();
		
		return null;
	}

	/**
	 * 在session中获取usermodel
	 * @param request
	 * @return com.wode.model.User
	 */
	protected String getCurrentUserName(HttpServletRequest request) {
		UserFactory uf = getSessionUserModel(request);
		if(uf != null) return uf.getUserName();
		
		return null;
	}
	protected EnterpriseVo getEnterpriseInfo(HttpServletRequest request) {
		 HttpSession session = request.getSession();
		 EnterpriseVo vo = (EnterpriseVo)session.getAttribute(ENTERPRISE_SESSION);
		 if(vo == null) {
			UserFactory uf = getSessionUserModel(request);
			vo = this.enterpriseService.selectById(uf.getSupplierId());
			if(vo == null)
				vo = new EnterpriseVo();
			session.setAttribute(ENTERPRISE_SESSION, vo);
		 }
		 return vo;
	}
}
