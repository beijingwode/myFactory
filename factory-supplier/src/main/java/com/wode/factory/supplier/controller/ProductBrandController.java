/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.constant.UserConstant;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.query.ProductBrandQuery;
import com.wode.factory.supplier.service.ApprShopService;
import com.wode.factory.supplier.service.BrandProducttypeService;
import com.wode.factory.supplier.service.ProductBrandImageService;
import com.wode.factory.supplier.service.ProductBrandService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

import cn.org.rapid_framework.page.Page;

@Controller
@RequestMapping("productBrand")
public class ProductBrandController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("productBrandService")
	private ProductBrandService productBrandService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	@Autowired
	@Qualifier("brandProducttypesService")
	private BrandProducttypeService brandProducttypesService;

	@Autowired
	private ProductBrandImageService productBrandImageService;
	@Autowired
	private ApprShopService apprShopService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	private final String LIST_ACTION = "redirect:/productBrand/list.html";
	
	public ProductBrandController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,ProductBrandQuery query) {
		Page page = this.productBrandService.findPage(query);
		
		ModelAndView result = new ModelAndView("product/productBrand/list");
		result.addAllObjects(toModelMap(page, query));
		return result;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		ProductBrand productBrand = (ProductBrand)productBrandService.getById(id);
		return new ModelAndView("product/productBrand/show","productBrand",productBrand);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,ProductBrand productBrand) throws Exception {
		return new ModelAndView("product/productBrand/create","productBrand",productBrand);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	@NoCheckLogin
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,ProductBrand productBrand) throws Exception {
		String categoryIds = request.getParameter("categoryIds");
		String flag = request.getParameter("flag");
		String returnUrl = request.getParameter("returnUrl");
		ModelAndView mv =new ModelAndView();
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Long productBrandId = null;
			String id = request.getParameter("id");
			//获取商家信息
			ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());
			if(StringUtils.isNullOrEmpty(id)){
				
				productBrand.setCreateDate(new Date());
				productBrand.setSupplierId(appr.getSupplierId());
				productBrand.setShopId(appr.getShopId());
				productBrand.setStatus(0);
				productBrand.setIsDelete(0);
				ProductBrand inproductBrand = productBrandService.save(productBrand);
				productBrandId = productBrand.getId();
				String[] categoryId = categoryIds.split(",");
				for (String cid : categoryId) {
					BrandProducttype brandProducttype = new BrandProducttype();
					brandProducttype.setBrandId(inproductBrand.getId());
					brandProducttype.setCategoryId(new Long(cid));
					brandProducttype.setSupplierId(appr.getSupplierId());
					brandProducttypesService.save(brandProducttype);
				}
				
			}else{
				productBrandId = new Long(id);
				
				ProductBrand reproductBrand = (ProductBrand)productBrandService.getById(productBrandId);
				bind(request,reproductBrand);
				productBrand.setCreateDate(reproductBrand.getCreateDate());
				productBrand.setStatus(0);
				productBrand.setIsDelete(0);
				productBrand.setShopId(appr.getShopId());
				productBrandService.update(productBrand);
				
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("brandId", new Long(id));
				map.put("supplierId",appr.getSupplierId());
				
				brandProducttypesService.removeByMap(map);
				
				String[] categoryId = categoryIds.split(",");
				for (String cid : categoryId) {
					BrandProducttype brandProducttype = new BrandProducttype();
					brandProducttype.setBrandId(new Long(id));
					brandProducttype.setCategoryId(new Long(cid));
					brandProducttype.setSupplierId(appr.getSupplierId());
					brandProducttypesService.save(brandProducttype);
				}
			}
			//保存商标授权
			String[] brandImg = request.getParameterValues("brandImg");

			ProductBrandImage record = new ProductBrandImage();
			record.setSupplierId(appr.getSupplierId());
			record.setBrandId(productBrandId);

			//旧数据删除
			List<ProductBrandImage> old = productBrandImageService.selectByModel(record);
			for (ProductBrandImage productBrandImage : old) {
				productBrandImageService.removeById(productBrandImage.getId());
			}
			
			//保存新数据
			if(brandImg != null) {
				for (int i = 0; i < brandImg.length; i++) {
					ProductBrandImage ins = new ProductBrandImage();
					ins.setSupplierId(appr.getSupplierId());
					ins.setBrandId(productBrandId);
					ins.setOrders(i);
					ins.setSource(brandImg[i]);
					ins.setCreateDate(new Date());
					productBrandImageService.save(ins);
				}
			}
			
			//返回前一画面
			if(StringUtils.isEmpty(returnUrl)) {
				mv.setViewName("redirect:/supplier/torecruitmentnewbrand.html");
			} else {
				mv.setViewName("redirect:" + returnUrl);
			}
			
		}
		
		return mv;
	}

	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		ProductBrand productBrand = (ProductBrand)productBrandService.getById(id);
		return new ModelAndView("product/productBrand/edit","productBrand",productBrand);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		ProductBrand productBrand = (ProductBrand)productBrandService.getById(id);
		bind(request,productBrand);
		productBrandService.update(productBrand);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@NoCheckLogin
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		ProductBrand productBrand = (ProductBrand)productBrandService.getById(id);
		productBrand.setIsDelete(1);
		productBrandService.update(productBrand);
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		Supplier supplier = supplierService.getById(userModel.getSupplierId());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("brandId", id);
		map.put("supplierId", supplier.getId());
		brandProducttypesService.removeByMap(map);
		return new ModelAndView("redirect:/supplier/torecruitmentnewbrand.html");
	}
	
}

