/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.supplier.service.ApprShopService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("productCategory")
public class ProductCategoryController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("productCategoryService")
	private ProductCategoryService productCategoryService;
	
	@Autowired
	@Qualifier("supplierCategoryService")
	private SupplierCategoryService supplierCategoryService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	private ApprShopService apprShopService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	
	public ProductCategoryController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,ProductCategory productCategory) throws Exception {
		return new ModelAndView("product/productCategory/create","productCategory",productCategory);
	}
	
	
	/**
	 * 获取商品类型的子节点
	 */
	@RequestMapping(value="getProductCategoryList",method=RequestMethod.GET)
	@Token(save=true)
	@NoCheckLogin
	public ModelAndView getProductCategoryList(HttpServletRequest request,HttpServletResponse response,Long shopId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		String pids = request.getParameter("pids");
		String order = request.getParameter("order");
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		List<ProductCategory> productCategoryListNew = new ArrayList<ProductCategory>();
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			
			
			//Supplier supplier = supplierService.getById(userModel.getSupplierId());
			
			if(pids!=null){
				for(int i=0;i<pids.split(",").length;i++){
					productCategoryList.addAll(productCategoryService.findSub(new ProductCategory(new Long(pids.split(",")[i]))));
				}
			}
			Map<Long,ProductCategory> map = new HashMap<Long,ProductCategory>();
			for(ProductCategory pc : productCategoryList){
				map.put(pc.getId(),pc);
			}

			//获取商家信息
	        if(StringUtils.isEmpty(shopId)) {
				ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());
				shopId = appr.getShopId();
	        }
			List<SupplierCategory> supplierCategorylist = supplierCategoryService.getBySupplierId(userModel.getSupplierId(),shopId);
			Map mapids = new HashMap();
			List<Long> ids = new ArrayList<Long>();
			if(supplierCategorylist!=null&&supplierCategorylist.size()>0){
				for(SupplierCategory s : supplierCategorylist){
					if(order!=null){
						if(order.equals("2")){
							ids.add(s.getCategoryParentid());
						}else if(order.equals("3")){
							ids.add(s.getCategoryId());
						}
					}
				}
			}
			if(ids.size()==0){
				productCategoryListNew = productCategoryList;
			}else{
				HashSet h = new HashSet(ids);
				  ids.clear();  
				  ids.addAll(h);  
				for(Long l :ids){
					if(map.get(l)!=null){
						productCategoryListNew.add(map.get(l));
					}
				}
			}
			  
		}
		
		mv.addObject("productCategoryList", productCategoryListNew);
		result.setErrorCode("0");
		result.setMsgBody(productCategoryListNew);
		return new ModelAndView("","result",result);
	}

	/**
	 * 获取商品类型的子节点
	 */
	@RequestMapping(value="getBrandCategoryList",method=RequestMethod.GET)
	@Token(save=true)
	@NoCheckLogin
	public ModelAndView getBrandCategoryList(HttpServletRequest request,HttpServletResponse response,Long shopId,Long brandId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		String pids = request.getParameter("pids");
		String order = request.getParameter("order");
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		List<ProductCategory> productCategoryListNew = new ArrayList<ProductCategory>();
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			
			
			//Supplier supplier = supplierService.getById(userModel.getSupplierId());
			
			if(pids!=null){
				for(int i=0;i<pids.split(",").length;i++){
					productCategoryList.addAll(productCategoryService.findSub(new ProductCategory(new Long(pids.split(",")[i]))));
				}
			}
			Map<Long,ProductCategory> map = new HashMap<Long,ProductCategory>();
			for(ProductCategory pc : productCategoryList){
				map.put(pc.getId(),pc);
			}

			//获取商家信息
	        if(StringUtils.isEmpty(shopId)) {
				ApprShop appr= apprShopService.getShopApprIng(userModel.getSupplierId());
				shopId = appr.getShopId();
	        }
			List<SupplierCategory> supplierCategorylist = supplierCategoryService.getByShopAndBrand(userModel.getSupplierId(),shopId,brandId);
			Map mapids = new HashMap();
			List<Long> ids = new ArrayList<Long>();
			if(supplierCategorylist!=null&&supplierCategorylist.size()>0){
				for(SupplierCategory s : supplierCategorylist){
					if(order!=null){
						if(order.equals("2")){
							ids.add(s.getCategoryParentid());
						}else if(order.equals("3")){
							ids.add(s.getCategoryId());
						}
					}
				}
			}
			if(ids.size()==0){
				productCategoryListNew = productCategoryList;
			}else{
				HashSet h = new HashSet(ids);
				  ids.clear();  
				  ids.addAll(h);  
				for(Long l :ids){
					if(map.get(l)!=null){
						productCategoryListNew.add(map.get(l));
					}
				}
			}
			  
		}
		
		mv.addObject("productCategoryList", productCategoryListNew);
		result.setErrorCode("0");
		result.setMsgBody(productCategoryListNew);
		return new ModelAndView("","result",result);
	}
	
	/**
	 * 获取商品类型的子节点
	 */
	@RequestMapping(value="ajaxGetCategoryListByids",method=RequestMethod.GET)
	@Token(save=true)
	@NoCheckLogin
	public ModelAndView ajaxGetCategoryListByids(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		Long supplierId = null;
		if(userModel!=null){
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			if(supplier!=null){
				supplierId = supplier.getId();
			}
		}
		if(supplierId==null){
			supplierId = -1l;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplierId);
		List<SupplierCategory> list = supplierCategoryService.findAll(map);
		List<Long> idslist = new ArrayList<Long>();
		
		if(list!=null&&list.size()>0){
			for(SupplierCategory s:list){
				idslist.add(s.getCategoryId());//三级目录
			}
		}
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList = productCategoryService.findIds(idslist);
		mv.addObject("productCategoryList", productCategoryList);
		Result result = new Result();
		result.setErrorCode("0");
		result.setMsgBody(productCategoryList);
		return new ModelAndView("","result",result);
	}
}

