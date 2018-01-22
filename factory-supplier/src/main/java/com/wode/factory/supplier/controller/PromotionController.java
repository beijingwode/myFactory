/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.wode.common.stereotype.Token;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.PromotionQuery;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.PromotionProductService;
import com.wode.factory.supplier.service.PromotionService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("promotion")
public class PromotionController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("promotionService")
	private PromotionService promotionService;
	
	@Autowired
	@Qualifier("promotionProductService")
	private PromotionProductService promotionProductService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	public PromotionController() {
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
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,PromotionQuery query) {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("product/activity/seckill-activity");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=20;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="20";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 1);
			Integer total = promotionService.findAllCount(map);
			Integer startnum=(page-1)*size;
			if(total>0){
			
				if(total<startnum){
					startnum=total-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				
				map.put("startnum", startnum);
				map.put("size",size);
				List<Promotion> promotionList = promotionService.findByMap(map);
				
				Map<String,Object> parame = new HashMap<String,Object>();
				parame.put("type", 1);
				parame.put("startnum", 0);
				parame.put("size",1000);
				parame.put("userId", us.getId());
				parame.put("supplierId", us.getSupplierId());
				parame.put("status2", 0);
				List<PromotionProduct> promotionProductList = promotionService.findProductByMap(parame);
				mv.addObject("promotionProductList", promotionProductList);
				
				mv.addObject("promotionList", promotionList);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
			}else{
				result.setErrorCode("1000");
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DAY_OF_MONTH, 3);
	        mv.addObject("begin",sf.format(c.getTime()));
	        c.add(Calendar.DAY_OF_MONTH, 33);
	        mv.addObject("end",sf.format(c.getTime()));
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);
			
		}
		
		return mv;
	}
	
	/**
	 * 进入详情页面
	 **/
	@RequestMapping(value="toPromotionInfo",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		ModelAndView mv =new ModelAndView();
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		mv.setViewName("product/activity/seckill-info");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Promotion promotion = (Promotion)promotionService.getById(id);
			
			Map<String,Object> parame = new HashMap<String,Object>();
			parame.put("startnum", 0);
			parame.put("size",1000);
			parame.put("userId", us.getId());
			parame.put("status2", 0);
			List<PromotionProduct> promotionProductList = promotionService.findProductByMap(parame);
			mv.addObject("promotionProductList", promotionProductList);
			
			mv.addObject("promotion",promotion);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DAY_OF_MONTH, 3);
	        mv.addObject("begin",sf.format(c.getTime()));
	        c.add(Calendar.DAY_OF_MONTH, 33);
	        mv.addObject("end",sf.format(c.getTime()));
		}
		
		return mv;
	}
	
	
	/** 
	 * 申请中的活动 
	 **/
	@RequestMapping(value="mylist")
	public ModelAndView mylist(HttpServletRequest request,HttpServletResponse response,PromotionQuery query) {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		String status = request.getParameter("status");
		Date now = new Date();
		mv.setViewName("product/activity/seckill-activity-all");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			//获得管理员的userId
			Long userId=null;
			Long supplierId=null;
			UserFactory user = new UserFactory();
			user.setSupplierId(us.getSupplierId());
			user.setType(2);//卖家
			List<UserFactory> userList = this.userService.getUserList(user);
			if(userList.isEmpty()){
				mv.setViewName("redirect:/user/login.html");
				logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			}else{
				userId=userList.get(0).getId();
				supplierId = userList.get(0).getSupplierId();
			}
			
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=20;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="20";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 1);
			map.put("userId", userId);
			map.put("supplierId", supplierId);
			if(!"".equals(status)&&status!=null){
				map.put("status", status);
			}
			map.put("now", now);
			Integer total = promotionService.findAllProductCount(map);
			Integer startnum=(page-1)*size;
			if(total>0){
			
				if(total<startnum){
					startnum=total-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				
				map.put("startnum", startnum);
				map.put("size",size);
				List<PromotionProduct> promotionList = promotionService.findProductByMap(map);
				mv.addObject("promotionList", promotionList);
				
				Map<String,Object> parame = new HashMap<String,Object>();
				parame.put("type", 1);
				parame.put("startnum", 0);
				parame.put("size",1000);
				parame.put("userId", userId);
				parame.put("supplierId",supplierId);
				parame.put("status2", 0);
				List<PromotionProduct> promotionProductList = promotionService.findProductByMap(parame);
				mv.addObject("promotionProductList", promotionProductList);
				
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
			}else{
				result.setErrorCode("1000");
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DAY_OF_MONTH, 3);
	        mv.addObject("begin",sf.format(c.getTime()));
	        c.add(Calendar.DAY_OF_MONTH, 33);
	        mv.addObject("end",sf.format(c.getTime()));
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);
			mv.addObject("status",status);
			mv.addObject("now",now);
		}
		
		return mv;
	}
	
	/**
	 * ajax修改
	 **/
	@RequestMapping(value="ajaxUpdatePromotionProduct",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView ajaxUpdatePromotionProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String id =  request.getParameter("id");
			String status =  request.getParameter("status");//状态：-1：关闭
			PromotionProduct promotionProduct= promotionProductService.getById(!"".equals(id)?new java.lang.Long(id):0);
			if(!StringUtils.isEmpty(status)){
				promotionProduct.setStatus(new Integer(status));
			}
			promotionProductService.saveOrUpdate(promotionProduct);
			result.setErrorCode("0");
		}
		mv.addObject("result", result);
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************fdxu********************************/
	/** 
	 * 声明页面
	 **/
	@RequestMapping(value="toConfirm")
	public ModelAndView toConfirm(HttpServletRequest request,HttpServletResponse response,PromotionQuery query) {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		String bmTime = request.getParameter("bmTime");
		mv.setViewName("product/activity/seckill-confirm");
		String promotionId =request.getParameter("promotionId");
		mv.addObject("bmTime",bmTime);
		mv.addObject("promotionId", promotionId);
		return mv;
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="productlist")
	public ModelAndView productlist(HttpServletRequest request,HttpServletResponse response) {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("product/activity/seckill-productlist");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String name = request.getParameter("name");
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			String bmTime = request.getParameter("bmTime");
			String promotionId =request.getParameter("promotionId");
			String categoryid = request.getParameter("categoryid");
			Integer page=1;
			Integer size=10;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="10";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", name);
			map.put("categoryid", categoryid);
			Supplier supplier = supplierService.getById(us.getSupplierId());
			Long supplierId = supplier.getId();
			map.put("supplierId",supplierId);
			Integer total = productSpecificationsService.getAllCount(map);
			Integer startnum=(page-1)*size;
			if(total>0){
			
				if(total<startnum){
					startnum=total-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				
				map.put("startnum", startnum);
				map.put("size",size);
				List<ProductSpecifications> list = productSpecificationsService.getPagelist(map);
				for(ProductSpecifications pss :list){
					String itemValues = pss.getItemValues();
					if(itemValues!=null&&!itemValues.trim().equals("")){
						String itemValuesNew = itemValues.substring(2, itemValues.length()-2).replace("\":\"", ":").replace("\",\"", " ");
						pss.setItemValues(itemValuesNew);
					}
				}
				result.setMsgBody(list);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
			}else{
				result.setErrorCode("1000");
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
			}
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("bmTime",bmTime);
			mv.addObject("promotionId", promotionId);
			mv.addObject("result",result);
			mv.addObject("categoryid",categoryid);
			mv.addObject("name",name);
		}
		
		return mv;
	}
	
	/** 
	 * 设置页面
	 **/
	@RequestMapping(value="productSet")
	public ModelAndView productSet(HttpServletRequest request,HttpServletResponse response) {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("product/activity/seckill-productset"); 
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String id = request.getParameter("id");
			String bmTime = request.getParameter("bmTime");
			String promotionId =request.getParameter("promotionId");
			String promotionProductId = request.getParameter("promotionProductId");//活动商品id
			ProductSpecifications ps = productSpecificationsService.getProductSpecificationsById(new Long(id));
			PromotionProduct pp = new PromotionProduct();
			if(promotionProductId!=null&&!promotionProductId.equals("")){
				pp = promotionProductService.getById(new Long(promotionProductId));
			}
			mv.addObject("productSpecifications",ps);
			mv.addObject("bmTime",bmTime);
			mv.addObject("promotionId", promotionId);
			mv.addObject("promotionProduct", pp);
		}
		
		return mv;
	}
	
	/** 
	 * set 
	 **/
	@RequestMapping(value="promotionSkuSet")
	public ModelAndView promotionSkuSet(HttpServletRequest request,HttpServletResponse response,Long id) {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		Supplier supplier = supplierService.getById(us.getSupplierId());
		
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("product/activity/seckill-ban");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String pid = request.getParameter("promotionProductId");
			String promotionId =request.getParameter("promotionId");
			String bmTime = request.getParameter("bmTime");
			String preferentialNum = request.getParameter("preferentialNum");
			String preferentialType = request.getParameter("preferentialType");
			String joinQuantity = request.getParameter("joinQuantity");
			String maxQuantity = request.getParameter("maxQuantity");
			String afterService = request.getParameter("afterService");
			String productId = request.getParameter("productId");
			String skuId = request.getParameter("skuId");
			String oldPrice = request.getParameter("oldPrice");
			
			PromotionProduct pp;
			if(pid!=null&&!pid.equals("")){
				pp = promotionProductService.getById(new Long(pid));
				pp.setPreferentialNum(new Double(preferentialNum));
				pp.setPreferentialType(new Integer(preferentialType));
				pp.setJoinQuantity(new Integer(joinQuantity));
				pp.setMaxQuantity(new Integer(maxQuantity));
				pp.setCanReturn(0);
				pp.setCanRepair(0);
				pp.setCanReplace(0);
				if(afterService.equals("1")){
					pp.setCanRepair(1);
					pp.setCanReplace(1);
				}else if(afterService.equals("2")){
					pp.setCanRepair(1);
				}else if(afterService.equals("3")){
					pp.setCanReplace(1);
				}
				if(preferentialType.equals("1")){
					pp.setPrice(new BigDecimal(preferentialNum));
				}else if(preferentialType.equals("2")){
					pp.setPrice(new BigDecimal(oldPrice).multiply(new BigDecimal(preferentialNum)).divide(new BigDecimal(10)));
				}
				pp.setModifyDate(new Date());
			}else{
				pp = new PromotionProduct();
				pp.setPreferentialNum(new Double(preferentialNum));
				pp.setPreferentialType(new Integer(preferentialType));
				pp.setJoinStart(TimeUtil.strToDate(bmTime +" 00:00:00"));
				pp.setJoinEnd(TimeUtil.strToDate(bmTime +" 00:00:00"));
				pp.setJoinQuantity(new Integer(joinQuantity));
				pp.setMaxQuantity(new Integer(maxQuantity));
				pp.setPromotionId(new Long(promotionId));
				pp.setProductId(new Long(productId));
				pp.setSkuId(new Long(skuId));
				pp.setCanReturn(0);
				pp.setCanRepair(0);
				pp.setCanReplace(0);
				if(afterService.equals("1")){
					pp.setCanRepair(1);
					pp.setCanReplace(1);
				}else if(afterService.equals("2")){
					pp.setCanRepair(1);
				}else if(afterService.equals("3")){
					pp.setCanReplace(1);
				}
				if(preferentialType.equals("1")){
					pp.setPrice(new BigDecimal(preferentialNum));
				}else if(preferentialType.equals("2")){
					pp.setPrice(new BigDecimal(oldPrice).multiply(new BigDecimal(preferentialNum)).divide(new BigDecimal(10)));
				}
				pp.setStatus(0);
				pp.setCreateDate(new Date());
				pp.setModifyDate(new Date());
				Shop p = new Shop();
				p.setSupplierId(supplier.getId());
				Shop ss = shopService.selectByModel(p).get(0);
				pp.setShopId(ss.getId());
				ProductSpecifications ps = productSpecificationsService.getProductSpecificationsById(new Long(skuId));
				if(ps != null) {
					pp.setBigImage(ps.getProductImage());
					pp.setSmallImage(ps.getProductImage());
				}
			}
			
			promotionProductService.saveOrUpdate(pp);
			mv.addObject("promotionProduct",pp);
			
		}
		return mv;
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="promotionSkuBanSet")
	public ModelAndView promotionSkuBanSet(HttpServletRequest request,HttpServletResponse response) {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);

		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("product/activity/seckill-success");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String id = request.getParameter("promotionProductId");
			String bigImage = request.getParameter("bigImage");
			String smallImage = request.getParameter("smallImage");
			
			PromotionProduct pp = promotionProductService.getById(new Long(id));
			pp.setBigImage(bigImage);
			pp.setSmallImage(smallImage);
			pp.setModifyDate(new Date());
			promotionProductService.saveOrUpdate(pp);
		}
		return mv;
	}
	
	
	/** 
	 * 商家发起抢购活动列表 
	 **/
	@RequestMapping(value="myqglist")
	public ModelAndView myqglist(HttpServletRequest request,HttpServletResponse response,PromotionQuery query) {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		String status = request.getParameter("status");
		mv.setViewName("product/activity/seckill-activity-all");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=20;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="20";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 2);
			map.put("userId", us.getId());
			if(!"".equals(status)&&status!=null){
				map.put("status", status);
			}
			Integer total = promotionService.findAllProductCount(map);
			Integer startnum=(page-1)*size;
			if(total>0){
			
				if(total<startnum){
					startnum=total-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				
				map.put("startnum", startnum);
				map.put("size",size);
				List<PromotionProduct> promotionList = promotionService.findProductByMap(map);
				mv.addObject("promotionList", promotionList);
				
				Map<String,Object> parame = new HashMap<String,Object>();
				parame.put("type", 2);
				parame.put("startnum", 0);
				parame.put("size",1000);
				parame.put("userId", us.getId());
				parame.put("status2", 0);
				List<PromotionProduct> promotionProductList = promotionService.findProductByMap(parame);
				mv.addObject("promotionProductList", promotionProductList);
				
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
			}else{
				result.setErrorCode("1000");
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DAY_OF_MONTH, 3);
	        mv.addObject("begin",sf.format(c.getTime()));
	        c.add(Calendar.DAY_OF_MONTH, 33);
	        mv.addObject("end",sf.format(c.getTime()));
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);
			mv.addObject("status",status);
			mv.addObject("now",TimeUtil.addDay(new Date(), -1));
		}
		
		return mv;
	}
	

	/**
	 * 限时抢购--第二步选择商品--列表及搜索
	 * @param mv
	 * @return
	 */
	@RequestMapping("timeLimitProductSearch")
	public ModelAndView timeLimitProductSearch(ModelAndView mv, HttpServletRequest request) {
		// 创建返回结果类实体
		Result result = new Result();
		// 获取用户登录信息
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		// 验证用户是否登录
		if(us == null) {
			// 用户未登录，页面跳转到登录页面
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			// 商品名称
			String name = request.getParameter("name");
			// 当前页数
			String pages = request.getParameter("pages");
			Integer page = null;
			// 每页20个商品
			Integer size = 20;
			// 商品类型
			String categoryid = request.getParameter("categoryid");
			// 判断当前页数是否为空
			if(pages == null || pages.trim().equals("")) {
				// 当前页数设置为1
				page = 1;
			} else {
				page = Integer.valueOf(pages.trim());
			}
			// 供应商
			Supplier supplier = supplierService.getById(us.getSupplierId());
			// 供应商ID
			Long supplierId = supplier.getId();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("categoryid", categoryid);
			map.put("supplierId", supplierId);
			// 获取总的商品个数
			Integer total = productSpecificationsService.getAllCount(map);
			// 判断总的商品个数
			if(total != null && total > 0) {
				// 商品起始索引
				Integer startnum = (page - 1) * size;
				// 当最后一页 + 1
				if(total < startnum){
					startnum = total - size;
				}
				// 当第一页 - 1
				if(startnum < 0){
					startnum = 0;
				}
				map.put("startnum", startnum);
				map.put("size",size);
				List<ProductSpecifications> list = productSpecificationsService.getPagelist(map);
				// 循环SKU列表
				for(ProductSpecifications pss : list){
					// 根据商品SKU查询正在参加活动的商品列表（包括待审核、审核中、已通过）
					List<PromotionProduct> promotionProductList = promotionProductService.findPromotionProductBySKU(pss.getId());
					// 判断该SKU商品当前是否正在参加某活动
					if(promotionProductList != null && promotionProductList.size() > 0) {
						// 该SKU商品当前正在参加其他活动
						pss.setPromotionStatus(1);
					} else {
						// 该SKU商品当前没有参加任何活动
						pss.setPromotionStatus(0);
					}
				}
				result.setMsgBody(list);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
			} else {
				// 商品个数为0时
				result.setErrorCode("1000");
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
			}
			// 向页面传递参数
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);
			mv.addObject("categoryid",categoryid);
			mv.addObject("name",name);
			// 返回页面
			mv.setViewName("");
		}
		return mv;
	}
}

