/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.redis.RedisUtil;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.facade.DeleteApprRelationFacade;
import com.wode.factory.company.query.SupplierExchangeProductQuery;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Attribute;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.ParameterGroup;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Specification;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.supplier.facade.ProductFacade;
import com.wode.factory.supplier.facade.SupplierSpecificationFacade;
import com.wode.factory.supplier.model.SupplierQuestionnaire;
import com.wode.factory.supplier.service.ApprProductService;
import com.wode.factory.supplier.service.AttributeService;
import com.wode.factory.supplier.service.ExchangeSuborderService;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.ParameterGroupService;
import com.wode.factory.supplier.service.ProductAttributeService;
import com.wode.factory.supplier.service.ProductBrandService;
import com.wode.factory.supplier.service.ProductDetailListService;
import com.wode.factory.supplier.service.ProductLadderService;
import com.wode.factory.supplier.service.ProductParameterValueService;
import com.wode.factory.supplier.service.ProductQuestionnaireService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ProductShippingService;
import com.wode.factory.supplier.service.ProductSpecificationValueService;
import com.wode.factory.supplier.service.ProductSpecificationsImageService;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.ShippingFreeRuleService;
import com.wode.factory.supplier.service.ShippingTemplateRuleService;
import com.wode.factory.supplier.service.ShippingTemplateService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SpecificationService;
import com.wode.factory.supplier.service.SpecificationValueService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierExchangeProductService;
import com.wode.factory.supplier.service.SupplierLogService;
import com.wode.factory.supplier.service.SupplierQuestionnaireService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.SupplierSpecificationService;
import com.wode.factory.supplier.service.UserExchangeTicketService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.UserInterceptor;
@Controller
@RequestMapping("product")
public class ProductController extends BaseSpringController {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;

	@Autowired
	@Qualifier("productDetailListService")
	private ProductDetailListService productDetailListService;

	@Autowired
	@Qualifier("attributeService")
	private AttributeService attributeService;

	@Autowired
	@Qualifier("productAttributeService")
	private ProductAttributeService productAttributeService;

	@Autowired
	@Qualifier("parameterGroupService")
	private ParameterGroupService parameterGroupService;
	@Autowired
	@Qualifier("productParameterValueService")
	private ProductParameterValueService productParameterValueService;
	@Autowired
	@Qualifier("productCategoryService")
	private ProductCategoryService productCategoryService;

	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	@Qualifier("specificationService")
	private SpecificationService specificationService;
	@Autowired
	@Qualifier("specificationValueService")
	private SpecificationValueService specificationValueService;
	@Autowired
	@Qualifier("supplierSpecificationService")
	private SupplierSpecificationService supplierSpecificationService;
	@Autowired
	private SupplierSpecificationFacade supplierSpecificationFacade;
	@Autowired
	@Qualifier("productSpecificationValueService")
	private ProductSpecificationValueService productSpecificationValueService;
	@Autowired
	@Qualifier("productSpecificationsImageService")
	private ProductSpecificationsImageService productSpecificationsImageService;

	@Resource
	private SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	@Autowired
	@Qualifier("apprProductService")
	private ApprProductService apprProductService;
	
	@Autowired
	@Qualifier("productBrandService")
	private ProductBrandService productBrandService;

	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;

	@Autowired
	@Qualifier("supplierCategoryService")
	private SupplierCategoryService supplierCategoryService;

	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;

    @Autowired
    @Qualifier("shopService")
    private ShopService shopService;

	@Autowired
	private ProductShippingService productShippingService;

	@Autowired
	@Qualifier("shippingTemplateService")
	private ShippingTemplateService shippingTemplateService;
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;
	@Resource
	private RedisUtil redis;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private ProductFacade productFacade;
	@Autowired
	private DeleteApprRelationFacade deleteApprRelationFacade;
	@Autowired
	private ProductQuestionnaireService productQuestionnaireService;
	@Autowired
	private SupplierQuestionnaireService supplierQuestionnaireService;
	private final String LIST_ACTION = "redirect:/product/list.html";
	
	@Autowired
	@Qualifier("supplierShippingTemplateRuleService")
	private ShippingTemplateRuleService shippingTemplateRuleService;
	
	@Autowired
	@Qualifier("productLadderService")
	private ProductLadderService productLadderService;
	
	@Autowired
	private UserExchangeTicketService  userExchangeTicketService;
	
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	public ProductController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		Product product = (Product) productService.getById(id);
		return new ModelAndView("product/product/show", "product", product);
	}

	/**
	 * 进入新增页面
	 **/
	@RequestMapping(value = "create", method = RequestMethod.GET)
	@Token(save = true)
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, Product product) throws Exception {
		return new ModelAndView("product/product/create", "product", product);
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Token(remove = true)
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Product product) throws Exception {
		productService.save(product);
		return new ModelAndView(LIST_ACTION);
	}

	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	@Token(save = true)
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		Product product = (Product) productService.getById(id);
		return new ModelAndView("product/product/edit", "product", product);
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Token(remove = true)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));

		Product product = (Product) productService.getById(id);
		bind(request, product);
		productService.update(product);
		return new ModelAndView(LIST_ACTION);
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		productService.removeById(id);
		return new ModelAndView(LIST_ACTION);
		
/*		//删除多个
		String[] items = request.getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			
			java.lang.Long aid = new java.lang.Long((String)params.get("id"));
			
			productService.removeById(aid);
		}
		return new ModelAndView(LIST_ACTION);*/
	}

	/**
	 * 跳转到商户未通过审核的空白页
	 */
	@RequestMapping(value = "toNotthroughaudit", method = RequestMethod.GET)
	@Token(save = true)
	@NoCheckLogin
	public ModelAndView toNotthroughaudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("notthroughaudit");
		logger.error("商户入驻信息还未通过审核");
		return mv;
	}


	/**
	 * 跳转到商品添加页面
	 */
	@RequestMapping(value = "toSelectProducttype", method = RequestMethod.GET)
	@Token(save = true)
	public ModelAndView toSelectProducttype(HttpServletRequest request, HttpServletResponse response,Long shopId,Long brandId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			if (supplier.getStatus() == null || supplier.getStatus() < 2) {
				result.setErrorCode("10000");
				mv.setViewName("redirect:/product/toNotthroughaudit.html");
				logger.error("商户入驻信息还未通过审核   errorcode：10000");
			} else {
				mv.setViewName("product/product/selectproducttype");
				mv.addObject("selltype", "createproduct");//类型
				String productId = request.getParameter("productId");
				String specificationType = request.getParameter("specificationType");
				if (productId!=null&&productId!="") {
					Product product=null;
					product = productService.getById(new Long(productId));
					if(product ==null){//修改分类是临时表对象
					   product = apprProductService.getById(new Long(productId));
					}
					if (product != null) {
						mv.addObject("productId",productId);
						mv.addObject("productName",product.getName());
						
						ProductCategory productCategory3 = productCategoryService.findById(product.getCategoryId());
						List<ProductCategory> parents = productCategoryService.findParents(product.getCategoryId());
						
						mv.addObject("name1",parents==null?"":parents.get(0).getName());
						mv.addObject("name2",parents==null?"":parents.get(1).getName());
						mv.addObject("name3",parents==null?"":productCategory3.getName());
					}
				}
		        Shop record = new Shop();
		        record.setSupplierId(userModel.getSupplierId());
		        List<Shop> shopList = shopService.selectByModel(record);

		        Shop shop = null;
		        if(StringUtils.isEmpty(shopId)) {
		        	shop= shopList.get(0);
		        } else {
		        	for (Shop s : shopList) {
						if(s.getId().equals(shopId)) {
							shop =s;
							break;
						}
					}
		        }
		        mv.addObject("shopList", shopList);
		        mv.addObject("shopId", shop.getId());
		        
				Map map = new HashMap();
				map.put("supplierId", userModel.getSupplierId());
				map.put("shopId", shop.getId());
				map.put("isDelete",0);//正在使用
				List<ProductBrand> brandList = productBrandService.findAllBymap(map);
				if(brandList == null || brandList.isEmpty()) {
					mv.setViewName("redirect:/shopSetting/categoryBrand.html?id=" + shop.getId());
					logger.error("商品添加时，没有品牌信息！errorcode：10002");
					return mv;
				}
				
				for (ProductBrand productBrand : brandList) {
					if(StringUtils.isEmpty(productBrand.getName())) {
						productBrand.setName(productBrand.getNameEn());
					}
				}
				ProductBrand pb = null;
		        if(StringUtils.isEmpty(brandId)) {
		        	pb= brandList.get(0);
		        } else {
		        	for (ProductBrand s : brandList) {
						if(s.getId().equals(brandId)) {
							pb =s;
							break;
						}
					}
		        	
		        	if(pb==null && !brandList.isEmpty()) pb=brandList.get(0);
		        }
		        mv.addObject("brandList", brandList);
		        mv.addObject("brandId", pb.getId());
				
				List<SupplierCategory> supplierCategorylist = supplierCategoryService.getByShopAndBrand(supplier.getId(), shop.getId(), pb.getId());
				Map mapids = new HashMap();
				List<Long> ids = new ArrayList<Long>();
				if (supplierCategorylist != null && supplierCategorylist.size() > 0) {
					for (SupplierCategory s : supplierCategorylist) {
						ids.add(s.getCategoryParentParentid());
					}
				}
				HashSet h = new HashSet(ids);
				ids.clear();
				ids.addAll(h);
				List<ProductCategory> productCategoryList = productCategoryService.findIds(ids);
				mv.addObject("productCategoryList", productCategoryList);
			}
		}
		return mv;
	}

	/**
	 * 跳转到商品添加页面
	 */
	@RequestMapping(value = "toCreateProduct", method = RequestMethod.GET)
	@Token(save = true)
	public ModelAndView toCreateProduct(HttpServletRequest request, HttpServletResponse response,Long shopId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		Supplier supplier = supplierService.getById(userModel.getSupplierId());
		String selltype = request.getParameter("selltype");	
		String productcopy=request.getParameter("productcopy");
		if (selltype == null || selltype.equals("")) {
			selltype = "createproduct";
		}
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			mv.setViewName("product/product/createproduct");
			mv.addObject("selltype", selltype);//类型 从url参数中获取
			//商品id
			String productId = request.getParameter("productId"); //从url参数中获取
			String apprid = request.getParameter("apprid");//从url参数中获取
			Product product = null;
			Long proid=null;//用于审核信息（正式表id）
			Integer displayOrhide=0;//0:表示隐藏，1：表示显示
			Long questionnaireId = -1L;
			if(apprid !=null && !apprid.equals("")){
				//如果是从不是在售状态修改商品时的id为apprid
				product= apprProductService.getById(new Long(apprid));
				if (product != null) { //如果临时表中有要修改的商品，就把apprid赋给productId,方便下面的sku，产品属性，参数等使用临时表id关联他们
					proid = ((ApprProduct)product).getProductId(); //获取正式表id为下面的审核信息，第三方价格使用
					Product p =productService.getById(proid);
					if(product.getStatus() <2) {
						productId = apprid; 
						if(p!=null && p.getIsMarketable()==1){
							displayOrhide=1;
						}
					} else {
						if(p!=null) {
							product= apprProductService.selectProductIdAndStatus(p.getId());
							if(product !=null){ //在编辑状态有此商品
								apprid=product.getId()+"";//这句很关键，之前apprid传递的都是空值，会出现在修改商品时和弹出sku修改时，如果审核状态下有此商品时，在审核状态中会重新生成一个该商品的原因
								proid = ((ApprProduct)product).getProductId();
								productId = apprid; //这里解决当在售商品编辑发布后，生成待审核数据，用户修改待审核数据（例如添加了一条sku）发布或者保存后，在售商品中没有更新添加的sku信息（原因是在售的productid不能获取没有审核通过的规格值信息）
								displayOrhide=1;
								
							} else { //修改的是正式表
								product =p;
								proid = product.getId();
							}							
						}
					}
				}
				
			} else {
				if (productId != null && !productId.equals("")) {
					product= apprProductService.getById(new Long(productId)); //如果是从修改商品分类（通过url参数productId）传递过来的是productId（可能是临时表id）
					if (product != null && product.getStatus()<2) { //说明是从临时表修改商品分类传递的productId
						apprid =productId; //赋值给临时表id
						proid = ((ApprProduct)product).getProductId(); //同上
						Product p =productService.getById(proid);
						if(p!=null && p.getIsMarketable()==1){
							displayOrhide=1;
						}
					} else {
						//看在编辑状态中是否有此商品
						product= apprProductService.selectProductIdAndStatus(new Long(productId));
						if(product !=null){ //在编辑状态有此商品
							apprid=product.getId()+"";//这句很关键，之前apprid传递的都是空值，会出现在修改商品时和弹出sku修改时，如果审核状态下有此商品时，在审核状态中会重新生成一个该商品的原因
							proid = ((ApprProduct)product).getProductId();
							productId = apprid; //这里解决当在售商品编辑发布后，生成待审核数据，用户修改待审核数据（例如添加了一条sku）发布或者保存后，在售商品中没有更新添加的sku信息（原因是在售的productid不能获取没有审核通过的规格值信息）
							displayOrhide=1;
							
						} else { //修改的是正式表
							product =productService.getById(new Long(productId));
							proid = product.getId();
							
							
						}
					}
				}				
			}
			
			if (productId == null || productId.equals("")) { //当时新增商品时，apprid和productid都为null
				productId = "-1"; //等于-1是为了过滤不必要的查询
			}
			
			//店铺ID设置
			if (product != null) {
				if(shopId==null) shopId = product.getShopId();
				product.setShopId(shopId);
			}
			//店铺id取得
			Shop shop = shopService.getById(shopId);
            
			//// 商品附带清单list
			Map detailMap = new HashMap();
			detailMap.put("productId", productId);
			List<ProductDetailList> detaillist = productDetailListService.getProductdetaillistByProductid(detailMap);
			mv.addObject("detaillist", detaillist);// 商品附带清单list，编辑功能会用到
					
			// 商品类别id
			String categoryid = request.getParameter("categoryid");
			String brandId = request.getParameter("brandId");

			//编辑时
			if(product != null) {
				//直接进入编辑
				if(StringUtils.isEmpty(categoryid)) {
					categoryid = product.getCategoryId() + "";
					brandId = product.getBrandId() + "";
				} else {
					Long categoryId = new Long(categoryid);
					//修改分类进入
					if(!categoryId.equals(product.getCategoryId())) {
						supplierSpecificationFacade.copySupplierSpecification(userModel.getSupplierId(), product.getCategoryId(), categoryId,Long.valueOf(productId), 1);
						supplierSpecificationFacade.copySupplierSpecification(userModel.getSupplierId(), product.getCategoryId(), categoryId, Long.valueOf(productId), 2);
						product.setCategoryId(categoryId);// 设置成新的分类id
					}
					if(!StringUtils.isEmpty(brandId)) {
						product.setBrandId(new Long(brandId));// 设置成新的品牌id
					}
				}
				
				if(!"copy".equals(productcopy) && product.getQuestionnaireId()!=null) {
					questionnaireId = product.getQuestionnaireId();
				}
			}
			
			long id = Long.parseLong(categoryid);
			ProductCategory productCategory3 = productCategoryService.findById(id);
			List<ProductCategory> parents = productCategoryService.findParents(id);
			mv.addObject("name1",parents==null?"":parents.get(0).getName());
			mv.addObject("name2",parents==null?"":parents.get(1).getName());
			mv.addObject("name3",productCategory3==null?"":productCategory3.getName());
			
			mv.addObject("categoryId", categoryid);//product实体类
			if(brandId.equals("null")){ 
			   brandId="";
			   mv.addObject("brandId", brandId);//product实体类
			}else{
			   mv.addObject("brandId", brandId);//product实体类
			}
			////根据商品类型id获取该类型id所对应的属性
			Map categoryMap = new HashMap();
			categoryMap.put("categoryid", new Long(categoryid));
			categoryMap.put("productid", new Long(productId));

			List<Attribute> attributelist = attributeService.getAttributelistByCategoryid(categoryMap);
			if(attributelist!=null&&!attributelist.isEmpty()){
				mv.addObject("attributelist", attributelist);//类型所带的属性list
			}
			List<ProductBrand> brandList =productBrandService.getByShopAndCategory(userModel.getSupplierId(), shopId, new Long(categoryid));
			for (ProductBrand productBrand : brandList) {
				if(StringUtils.isEmpty(productBrand.getName())) {
					productBrand.setName(productBrand.getNameEn());
				}
			}
			mv.addObject("brandList", brandList);//product实体类
			////根据商品类型id获取该类型id所对应的参数
			List<ParameterGroup> parametergrouplist = parameterGroupService.getParameterGrouplistByCategoryid(categoryMap);
			if(parametergrouplist!=null&&!parametergrouplist.isEmpty()){
				mv.addObject("parametergrouplist", parametergrouplist);//类型所带的参数list
			}
			////根据商品类型id获取该类型id所对应的规格
			List<Specification> specificationlist = specificationService.getSpecificationlistByCategoryid(categoryMap);
			categoryMap.put("supplierId", supplier.getId());
			categoryMap.put("type", 1);
			List<SupplierSpecification> skuSpecificationlist = supplierSpecificationService.getSpecificationlistByCategoryid(categoryMap);
			categoryMap.put("type", 2);
			List<SupplierSpecification> supplierSpecificationlist = supplierSpecificationService.getSpecificationlistByCategoryid(categoryMap);
			mv.addObject("supplierSpecificationlist", supplierSpecificationlist);//类型所带的规格list（自定义规格回显）

			//标准规格
			//规格选择值为 list  为了统一，把已经选择的list规格拼装成了  属性、参数类似的结构    id_names
			if (specificationlist != null && specificationlist.size() > 0) {
				for (Specification s : specificationlist) {
					String names = "";
					if (s.getProductSpecificationValuelist() != null && s.getProductSpecificationValuelist().size() > 0) {
						for (ProductSpecificationValue psv : s.getProductSpecificationValuelist()) {
							names += psv.getSpecificationValue() + ",";//颜色和尺寸等规格值
						}
					}
					if (!names.equals("")) {
						names = names.substring(0, names.length() - 1);
						s.setSelectedValue(names);
					}
				}
			}
			mv.addObject("specificationlist", specificationlist);//类型所带的规格list（标准规格回显）

			//商家标准规格
			//规格选择值为 list  为了统一，把已经选择的list规格拼装成了  属性、参数类似的结构    id_names
			if (supplierSpecificationlist != null && supplierSpecificationlist.size() > 0) {
				for (SupplierSpecification s : supplierSpecificationlist) {
					String names = "";
					if (s.getProductSpecificationValuelist() != null && s.getProductSpecificationValuelist().size() > 0) {
						for (ProductSpecificationValue psv : s.getProductSpecificationValuelist()) {
							names += psv.getSpecificationValue() + ",";
						}
					}
					if (!names.equals("")) {
						names = names.substring(0, names.length() - 1);
						s.setSelectedValue(names);
					}
				}
			}
			mv.addObject("supplierSpecificationlist", supplierSpecificationlist);//类型所带的规格list（自定义规格回显）

			//简略sku
			//规格选择值为 list  为了统一，把已经选择的list规格拼装成了  属性、参数类似的结构    id_names
			if (skuSpecificationlist != null && skuSpecificationlist.size() > 0) {
				for (SupplierSpecification s : skuSpecificationlist) {
					String names = "";
					if (s.getProductSpecificationValuelist() != null && s.getProductSpecificationValuelist().size() > 0) {
						for (ProductSpecificationValue psv : s.getProductSpecificationValuelist()) {
							names += psv.getSpecificationValue() + ",";
						}
					}
					if (!names.equals("")) {
						names = names.substring(0, names.length() - 1);
						s.setSelectedValue(names);
					}
				}
			}
			
			//根据商品id查询商品的审核记录	
		    if(proid !=null){
		    	List<CheckOpinion> checkOptions = this.supplierService.getCheckOpinionListBySupplierId(Long.valueOf(proid));	
		    	mv.addObject("checkOptions", checkOptions);
		    	List<ProductThirdPrice> ptpList= this.productService.getProductThirdPriceByProductId(Long.valueOf(proid));
		    	if(ptpList!=null && !ptpList.isEmpty()){
		    		for (ProductThirdPrice ptp : ptpList) {
		    			if(ptp.getItemValues()!=null){
		    				ptp.setItemValues(getSpecificationList(ptp.getItemValues()));
		    			}
					}
		    	}
		    	mv.addObject("ptpList", ptpList);
		    	
		    	if(!"copy".equals(productcopy)){
			    	// 查看换领中记录
					SupplierExchangeProduct q = new SupplierExchangeProduct();
					q.setProductId(proid);
					q.setClearStatus(0);			// 未清算
					List<SupplierExchangeProduct> l =supplierExchangeProductService.selectByModel(q);
					for (SupplierExchangeProduct supplierExchangeProduct : l) {
						if(supplierExchangeProduct.getStatus() == 1) {
					    	mv.addObject("exchanging", "0");
					    	mv.addObject("limitType", supplierExchangeProduct.getLimitType());
						} else {
					    	mv.addObject("exchanging", "1");
					    	mv.addObject("limitType", supplierExchangeProduct.getLimitType());
					    	break;
						}
					}
		    	} else {
			    	mv.addObject("limitType", "3");
			    	mv.addObject("exchanging", "0");
		    	}
		    } else {
		    	mv.addObject("limitType", "3");
		    	mv.addObject("exchanging", "0");
		    }
//			mv.addObject("specificationType", specificationType);
//			mv.addObject("skuCnt", skuCnt);
//			mv.addObject("theadName1", theadName1);
//			mv.addObject("theadName2", theadName2);
//			mv.addObject("productSpecifications", psvlist1);//product实体类
			mv.addObject("product", product);//product实体类
			mv.addObject("shop", shop);//product实体类
			mv.addObject("supplier", supplier);//product实体类
			mv.addObject("proid", proid);
			mv.addObject("supplierId", supplier.getId());//product实体类
			//如果是修改商品的话，会传递临时表的id过来
			mv.addObject("apprid", apprid);	
			mv.addObject("displayOrhide", displayOrhide);
			mv.addObject("productcopy", productcopy);
			
			
			SupplierQuestionnaire q = new SupplierQuestionnaire();
			q.setSupplierId(userModel.getSupplierId());
			List<SupplierQuestionnaire> listQuestionnaire = supplierQuestionnaireService.selectByModel(q);
			if(!questionnaireId.equals(-1L)) {
				ProductQuestionnaire pq=  productQuestionnaireService.getById(questionnaireId);
				if(pq != null) {
					listQuestionnaire.add(new SupplierQuestionnaire(pq));
				}
			}
			mv.addObject("listQuestionnaire", listQuestionnaire);//根据商家id获取定义的运费模板一个或多个
			mv.addObject("questionnaireId", questionnaireId);//根据商家id获取定义的运费模板一个或多个
			

			selectShippingTemplate(mv, supplier);
		
			//日志
			SupplierLog log = new SupplierLog();
			log.setUserId(userModel.getId());
			log.setUsername(userModel.getUserName());
			if (productId.equals("-1")) {
				log.setAct("跳转到商品新增页面");
			} else {
				log.setAct("跳转到商品id=" + productId + " 的编辑页面");
			}
			log.setTime(new Date());
			log.setResult("success");
			supplierLogService.save(log);
		}
		return mv;
	}

	
	/**
	 * 将规格JSON数据转换成 String
	 *
	 * @param specificationJson
	 * @return
	 */
	private String getSpecificationList(String specificationJson) {
		specificationJson = specificationJson.replace("{", "").replace("}", "").replace("\"", "").replace("\\", "");
		String[] strs = specificationJson.split(",");
		String values="";
		for (int i = 0; i < strs.length; i++) {
			if(strs.length>1){
				values+=strs[i].substring(strs[i].indexOf(":")+1)+"/";
			}else{
				values+=strs[i].substring(strs[i].indexOf(":")+1);
			}
		}
		return values;
	}
	
	

	/**
	 * 商品详情
	 */
	@RequestMapping(value = "productView", method = RequestMethod.GET)
	@Token(save = true)
	@NoCheckLogin
	public ModelAndView productView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		boolean isTemp = false;
		Long proId = -1l;//直接通过正式表id来查看
		
		mv.setViewName("product/product/productview");
        Product ptemp=null;
		//商品id
        String productId = request.getParameter("productId");
        String apprid = request.getParameter("apprid");
        if(apprid !=null && !apprid.equals("")){ //首先判断是临时表传递的id
        	ptemp= apprProductService.getById(new Long(apprid));
        	proId = ((ApprProduct)ptemp).getProductId();
        	isTemp = true;
		} else { //只有在售 或者  在售在编辑状态都有  
			ptemp= apprProductService.selectProductIdAndStatus(new Long(productId)); 
			if(ptemp == null) { //只有在售
				ptemp = productService.getById(new Long(productId));
				proId = ptemp.getId();
				isTemp = false;
			} else { // 在售在编辑状态都有
	        	proId = ((ApprProduct)ptemp).getProductId();
            	isTemp = true;       	
			}
		}

		List<Product> prolist=null;
		////根据商品类型id获取该类型id所对应的规格
		Map map = new HashMap();
		if(isTemp){
			map.put("productId", ptemp.getId());
			map.put("categoryId", ptemp.getCategoryId());
			prolist=apprProductService.getProductByMap(map);
		} else {
			map.put("productId", ptemp.getId());
			map.put("categoryId", ptemp.getCategoryId());
			prolist = productService.getProductByMap(map);
		}
				
		Product pro = new Product(); //如果是临时表的预览，这里pro就是临时表的对象
		if (prolist != null) {
			pro = prolist.get(0);
		}
		Map<Long, String> specificationValueMap = new HashMap<Long, String>();
		if (pro != null && pro.getProductSpecificationValuelist() != null) {
			for (int i = 0; i < pro.getProductSpecificationValuelist().size(); i++) {
				specificationValueMap.put(pro.getProductSpecificationValuelist().get(i).getId(), pro.getProductSpecificationValuelist().get(i).getSpecificationValue());
			}
		}

		if (pro != null && pro.getProductSpecificationslist() != null) {
			for (int i = 0; i < pro.getProductSpecificationslist().size(); i++) {
				String itemids = pro.getProductSpecificationslist().get(i).getItemids();
				String[] idstemp = itemids.split(",");
				String itemnames = "";
				for (String idtemp : idstemp) {
					itemnames = itemnames + specificationValueMap.get(new Long(idtemp)) + "/";
				}
				if (!itemnames.equals("")) {
					itemnames = itemnames.substring(0, itemnames.length() - 1);
				}
				pro.getProductSpecificationslist().get(i).setItemnames(itemnames);
			}
		}

		if (!isTemp ||  pro.getStatus() == 2) {
			mv.setViewName("redirect:" +Constant.FACTORY_WEB_URL + proId + ".html");
		}
		mv.addObject("product", pro);//product实体类
		ProductShipping productShipping = new ProductShipping();
		productShipping.setProductId(pro.getId());
		List<ProductShipping> lst= productShippingService.selectByModel(productShipping);
		if(lst!=null && !lst.isEmpty()) {
			mv.addObject("shippingTemplate",shippingTemplateService.getById(lst.get(0).getTemplateId()));//product实体类
		}
		ShippingTemplate shippingTemplate = new ShippingTemplate();
		shippingTemplate.setSupplierId(pro.getSupplierId());
		shippingTemplate.setVersion(2);
		List<ShippingTemplate> shippingTemplateList = shippingTemplateService.selectByModel(shippingTemplate);
		if(null != shippingTemplateList && !shippingTemplateList.isEmpty() && !NumberUtil.isGreaterZero(pro.getCarriage())){
			mv.addObject("shippingTemplate",shippingTemplateList.get(0));//product实体类
		}
		//在session中获取userModel
		//com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		Supplier supplier = supplierService.getById(pro.getSupplierId());
		selectShippingTemplate(mv, supplier);
		
		SupplierQuestionnaire q = new SupplierQuestionnaire();
		q.setSupplierId(pro.getSupplierId());
		List<SupplierQuestionnaire> listQuestionnaire = supplierQuestionnaireService.selectByModel(q);
		mv.addObject("listQuestionnaire", listQuestionnaire);//根据商家id获取定义的运费模板一个或多个
		
		List<CheckOpinion> checkOptions = this.supplierService.getCheckOpinionListBySupplierId(Long.valueOf(proId));	
    	mv.addObject("checkOptions", checkOptions);
    	List<ProductThirdPrice> ptpList= this.productService.getProductThirdPriceByProductId(Long.valueOf(proId));
    	if(ptpList!=null && !ptpList.isEmpty()){
    		for (ProductThirdPrice ptp : ptpList) {
    			if(ptp.getItemValues()!=null){
    				ptp.setItemValues(getSpecificationList(ptp.getItemValues()));
    			}
			}
    		mv.addObject("ptpList", ptpList);
    	}
    	// 查看换领中记录
    	if(pro.getSaleKbn() ==2){
    		SupplierExchangeProduct sq = new SupplierExchangeProduct();
    		sq.setProductId(proId);
    		sq.setClearStatus(0);			// 未清算
    		List<SupplierExchangeProduct> l =supplierExchangeProductService.selectByModel(sq);
    		for (SupplierExchangeProduct supplierExchangeProduct : l) {
    			mv.addObject("limitType", supplierExchangeProduct.getLimitType());
    			mv.addObject("empAvgAmount", supplierExchangeProduct.getEmpAvgAmount());//平均金额
    			mv.addObject("empAvgCnt", supplierExchangeProduct.getEmpAvgCnt());//平均每个人多少
    			mv.addObject("productCnt", supplierExchangeProduct.getProductCnt());//商品总数
    		}
    	}
    	mv.addObject("proId", proId);
    	if (isTemp && pro.getStatus() != 2) {//非预览时
    	if(5!=pro.getSaleKbn() && 2!= pro.getSaleKbn()){//非换领和试用
    		List<ProductLadder> ls = productLadderService.getlistByProductid(pro.getId());
    		if(ls!=null && !ls.isEmpty()){
    			// 处理规格
    			String productLadder = makeProductLadder(ls,pro.getProductSpecificationslist());
    			if(ls.get(0).getType()==1){
    				productLadder = productLadder.replaceAll("内购价","").replaceAll("元/件", "折");
    				mv.addObject("productLadderType", 1);
    			}
    			mv.addObject("productLadderList",productLadder);
    		}
    	}
    	}
		logger.info("跳转到商品详情页面：product/toCreateProduct");
		return mv;
	}


	private String makeProductLadder(List<ProductLadder> ls, List<ProductSpecifications> productSpecificationslist) {
		StringBuilder sb= new StringBuilder();
		for (ProductLadder productLadder : ls) {
			sb.append("<tr>");
			sb.append("<td><input  name=\"ladder-num\" value ="+productLadder.getNum()+" style=\"width:50px;height:22px;\" readonly=\"readonly\" type=\"text\"/>件以上，内购价<input name=\"ladder-price\" readonly=\"readonly\" value = "+productLadder.getPrice()+" style=\"width:50px;height:22px;\" type=\"text\"/> 元/件</td>");
			sb.append("<td>");
			String[] skuIds = productLadder.getSkuids().split(",");
			String skuValues = "";
			for (String skuId : skuIds) {
				for (ProductSpecifications ps : productSpecificationslist) {
					if(ps.getId().toString().equals(skuId)){
						sb.append("<input  name=\"ladder-box\" type=\"checkbox\" disabled=\"disabled\" checked=\"checked\" ><label for=\"chk_qc\">"+ps.getItemnames()+"</label>&nbsp;&nbsp;");
					}
				}
			}
			sb.append("</td>");
			sb.append("<td><div><a href=\"javascript:;\">删除</a></div></td>");
			sb.append("</tr>");
		}
		return  sb.toString();
	}

	/**
	 * 跳转到商品添加页面
	 */
	@RequestMapping(value = "createProduct", method = RequestMethod.POST)
	@Token(save = true)
	public ModelAndView createProduct(HttpServletRequest request, HttpServletResponse response,Long shopId) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		String selltype = request.getParameter("selltype");
		String status = request.getParameter("status");
		String savestate =request.getParameter("savestate");
		String productcopy =request.getParameter("productcopy");
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			Map<String,Object> map = new HashMap<String,Object>();
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			////更新product基本信息
			
			String apprid = request.getParameter("apprid");
			String productid = request.getParameter("productid"); //这里使用productid（jsp隐藏域传递的值）为了区分url参数的productId（url参数传递的值）故意名字不同，因为他们的值可能不同，回显时443-458行代码查看
			if("copy".equals(productcopy)){ //商品复制			
				productid = dbUtils.CreateID()+"";
				apprid="0";
			}
			if(StringUtils.isEmpty(apprid)) {
				apprid = "0";  //说明是在售商品修改并且在编辑状态中没有此商品,或者是新增商品
			}
			
			if(StringUtils.isEmpty(productid)) {
				productid = dbUtils.CreateID()+"";
			}
			
			String name = (request.getParameter("name") != null && !request.getParameter("name").trim().equals("")) ? request.getParameter("name") : "";
			String fullName = (request.getParameter("fullName") != null && !request.getParameter("fullName").trim().equals("")) ? request.getParameter("fullName") : "";
			String promotion = (request.getParameter("promotion") != null && !request.getParameter("promotion").trim().equals("")) ? request.getParameter("promotion") : "";
			String marque = request.getParameter("marque");
			String roughWeight = request.getParameter("roughWeight");
			String length = request.getParameter("length");
			String height = request.getParameter("height");
			String barcode = (request.getParameter("barcode") != null && !request.getParameter("barcode").trim().equals("")) ? request.getParameter("barcode") : "";
			String netWeight = request.getParameter("netWeight");
			String width = request.getParameter("width");
			String bulk = request.getParameter("bulk");
			String province = request.getParameter("province");
			String town = request.getParameter("town");
			String county = request.getParameter("county");
			String produceaddress = request.getParameter("produceaddress");
			
			String introduction = request.getParameter("introduction");
			String introductionMobile = request.getParameter("introductionMobile");
			String afterService = request.getParameter("afterService");
			String stockLockType = request.getParameter("stockLockType");
			String categoryId = request.getParameter("categoryId");
			String sendProvince = request.getParameter("sendProvince");
			String sendTown = request.getParameter("sendTown");
			String sendAddress = request.getParameter("sendAddress");
			String brandId = request.getParameter("brandId");
			String specificationType = request.getParameter("specificationType");
			String rdFreightType = request.getParameter("rdFreightType");
			String rdUserFreight = request.getParameter("rdUserFreight");
			String carriage = request.getParameter("newCarriage");
			if(StringUtils.isNullOrEmpty(carriage)){
				carriage = "0";
			}
			
			String shippingTemplateId = request.getParameter("newShippingTemplateId");
			
			//如果模板id不为空并且商品运费不等于0（也就是不包邮）那么要判断模板是否为新版的运费模板
			if(!StringUtils.isNullOrEmpty(shippingTemplateId)  && NumberUtil.toDouble(carriage) != 0){
				ShippingTemplate  shippingTemplate  = shippingTemplateService.getById(NumberUtil.toLong(shippingTemplateId));
			    if(null != shippingTemplate && shippingTemplate.getVersion() != 2){
			    	result.setErrorCode("必须使用新的运费模板");
			    	mv.addObject(result);
			    	return mv;
			    }
			}
			//没有模板id而且不包邮
            if(StringUtils.isNullOrEmpty(shippingTemplateId) && NumberUtil.toDouble(carriage) != 0){
            	result.setErrorCode("必须使用新的运费模板或选择包邮");
		    	mv.addObject(result);
		    	return mv;
            }
			//商品限购类型
			String limitKbn = request.getParameter("limitKbn");
			map.put("limitKbn", limitKbn);
			String limitCnt = request.getParameter("limitCnt");
			String areasName = request.getParameter("areasName");
			String areasCode = request.getParameter("areasCode");
			String saleKbn = request.getParameter("saleKbn");
			String saleNote = request.getParameter("saleNote");
//			String divLevel = request.getParameter("divLevel");	//换领商品 -----员工范围级别
			String divLevel = "-1";	//换领商品 -----全体员工范围级别
			String empExPrice = request.getParameter("empExPrice");	
			String empPrice = request.getParameter("empPrice");	
			String trialPrice = request.getParameter("trialPrice");				
			String empCash = request.getParameter("empCash");	
			String empLevel = request.getParameter("empLevel");	
			String limitType = request.getParameter("limitType");
			String questionnaireId = request.getParameter("questionnaireId");
			if(StringUtils.isEmpty(questionnaireId) || "*".equals(questionnaireId)) {
				questionnaireId="-1";
			}
			empLevel = StringUtils.isEmpty(empLevel)?"0":empLevel;
			empPrice = StringUtils.isEmpty(empPrice)?"0":empPrice;
			trialPrice = StringUtils.isEmpty(empPrice)?"0":trialPrice;
			
			map.put("selltype", selltype);
			map.put("shopId", shopId);
			map.put("status", status);
			map.put("supplier", supplier);
			map.put("productid", productid);
			map.put("name", name);
			map.put("fullName", fullName);
			map.put("promotion", promotion);
			map.put("marque", marque);
			map.put("roughWeight", roughWeight);
			map.put("length", length);
			map.put("height", height);
			map.put("barcode", barcode);
			map.put("netWeight", netWeight);
			map.put("width", width);
			map.put("bulk", bulk);
			map.put("province", province);
			map.put("town", town);
			map.put("county", county);
			map.put("produceaddress", produceaddress);
			map.put("carriage", carriage);
			map.put("introduction", introduction);
			map.put("introductionMobile", introductionMobile);

			map.put("afterService", afterService);
			map.put("stockLockType", stockLockType);
			map.put("categoryId", categoryId);
			map.put("sendProvince", sendProvince);
			map.put("sendTown", sendTown);
			map.put("sendAddress", sendAddress);
			map.put("brandId", brandId);
			map.put("rdFreightType", rdFreightType);
			map.put("rdUserFreight", rdUserFreight);

			map.put("specificationType", specificationType);
			map.put("limitCnt", StringUtils.isEmpty(limitCnt)?0:NumberUtil.toInteger(limitCnt));
			map.put("areasName", StringUtils.isEmpty(areasName)?"全国":areasName);
			map.put("areasCode", StringUtils.isEmpty(areasCode)?"0":areasCode);
			map.put("saleKbn", StringUtils.isEmpty(saleKbn)?0:NumberUtil.toInteger(saleKbn));
			map.put("limitType", StringUtils.isEmpty(limitType)?0:NumberUtil.toInteger(limitType));
			map.put("saleNote", saleNote);
			map.put("divLevel", "2".equals(saleKbn)?divLevel:null);//换领商品---分配员工范围级别 TODO
			if("2".equals(saleKbn)) {
				map.put("empPrice", empExPrice);
			}else {
				map.put("empPrice", "4".equals(saleKbn)?empPrice:"0");
			}
			map.put("empCash", "4".equals(saleKbn)?empCash:"0");
			map.put("empLevel", "4".equals(saleKbn)?empLevel:"0");
			map.put("trialPrice", "5".equals(saleKbn)?trialPrice:"0");
			map.put("apprid", apprid);
			map.put("savestate", savestate);
			map.put("questionnaireId", questionnaireId);
			////产品属性
			String[] attribute_result = request.getParameterValues("attribute_result");
			map.put("attribute_result", attribute_result);

			////产品参数
			String[] parameter_result = request.getParameterValues("parameter_result");
			map.put("parameter_result", parameter_result);

			////产品规格
			String[] specification_result = request.getParameterValues("specification_result");
			map.put("specification_result", specification_result);
			////自定义规格
			String[] self_specification_result = request.getParameterValues("self_specification_result");
			map.put("self_specification_result", self_specification_result);

			////简略sku规格
			String[] skufSpecification = request.getParameterValues("color");//简略sku规格值
			map.put("skuSpecification", skufSpecification);
			String[] skufSpecificationId = request.getParameterValues("color_id");//简略sku规格id
			map.put("skuSpecificationId", skufSpecificationId);

			////商品清单
			String[] detaillist_result = request.getParameterValues("detaillist_result");
			map.put("detaillist_result", detaillist_result);

			////商品sku
			String[] specifications_result = request.getParameterValues("specifications_result");
			map.put("specifications_result", specifications_result);
			
			//sku的阶梯价--begin
			String chkActivityQicai = request.getParameter("chkActivityQicai");
			String chkActivityDiscount = request.getParameter("chkActivityDiscount");//表示折扣
			if("1".equals(chkActivityQicai)) {
				int activityQicaiRowCnt = NumberUtil.toInt(request.getParameter("activityQicaiRowCnt"));
				List<String> ladder_nums = new ArrayList<String>();
				List<String> ladder_prices = new ArrayList<String>();
				for (int i=1;i<=activityQicaiRowCnt;i++) {
					String ladder_num =request.getParameter("ladder-num-"+i);
					if(!StringUtils.isEmpty(ladder_num)) {
						ladder_nums.add(ladder_num.trim());
						ladder_prices.add(request.getParameter("ladder-price-"+i).trim());
						String[] ladder_box = request.getParameterValues("ladder-box-" + i);
						if(null == ladder_box || ladder_box.length == 0){
							result.setErrorCode("设置阶梯价格时，必须选择sku。");
					    	mv.addObject(result);
					    	return mv;
						}
						map.put("ladder_box_" + (ladder_nums.size()), ladder_box);
					}
				}
				if(StringUtils.isNullOrEmpty(chkActivityDiscount)){
					chkActivityDiscount="0";
				}
				map.put("ladder_type", chkActivityDiscount);
				map.put("ladder_num", ladder_nums.toArray());
				map.put("ladder_price", ladder_prices.toArray());
				
			}
			//sku的阶梯价--end
			
			
			String imageStr = "";//主图
			//sku图片
			String[] specifications_image_result = request.getParameterValues("specifications_image_result");
			map.put("specifications_image_result", specifications_image_result);
			
			//Product product = productService.saveOrUpdateProduct(map);
		    
			
			ApprProduct product=apprProductService.saveOrUpdateProduct(map);

			//日志
			SupplierLog log = new SupplierLog();
			log.setUserId(userModel.getId());
			log.setUsername(userModel.getUserName());
			if (productid.equals("0")) {
				log.setAct("商品新增");
			} else {
				log.setAct("商品(id=" + productid + ") 编辑");
			}
			log.setTime(new Date());
			log.setResult("success");
			supplierLogService.save(log);
			
			//需要自动做上架处理
			if(product.getIsMarketable() == 1) {
				// 更新缓存
				// 更新索引
				// 生成静态页
				this.refreshProduct(product.getProductId(),true);
			}

			if (selltype != null) {
				if (selltype.equals("waitsell")) {//如果为待售
						selltype = "waitsell";				
				} else if (selltype.equals("createproduct") || selltype.equals("selling")) { //如果为新增或者在售
					if (new Integer(status) == 0) { // 保存
						selltype = "waitsell";
					} else if (new Integer(status) == 1) {//发布
						if(product.getIsMarketable() == 1) {
							selltype = "selling";
						} else {
							selltype = "waitcheck";
						}
					}	
				}else if(selltype.equals("reject")){//问题商品
						selltype = "reject";		
				}else if(selltype.equals("waitcheck")){//待审核商品
						selltype = "waitcheck";
				}
			} else { //selltype==null
				if (new Integer(status) == 0) { // 保存
					selltype = "waitsell";
				} else if (new Integer(status) == 1) { //发布
					selltype = "waitcheck";
				}
			}
			mv.setViewName("redirect:/product/gotoProductlist.html?selltype=" + selltype);
			logger.info("商品新增：product/createProduct");
		}
		return mv;
	}

	/**
	 * 跳转到商品添加页面
	 */
	@RequestMapping(value = "productPreview", method = RequestMethod.POST)
	@Token(save = true)
	public ModelAndView productPreview(HttpServletRequest request, HttpServletResponse response,Long shopId) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());

			////更新product基本信息
			String productid = (request.getParameter("productid") != null && !request.getParameter("productid").equals("")) ? request.getParameter("productid") : "0";
			String status = request.getParameter("status");
			String name = request.getParameter("name");
			String promotion = request.getParameter("promotion");
			String marque = request.getParameter("marque");
			String roughWeight = request.getParameter("roughWeight");
			String length = request.getParameter("length");
			String height = request.getParameter("height");
			String barcode = request.getParameter("barcode");
			String netWeight = request.getParameter("netWeight");
			String width = request.getParameter("width");
			String bulk = request.getParameter("bulk");
			String province = request.getParameter("province");
			String town = request.getParameter("town");
			String county = request.getParameter("county");
			String produceaddress = request.getParameter("produceaddress");
			String carriage = request.getParameter("carriage");
			String introduction = request.getParameter("introduction");
			String afterService = request.getParameter("afterService");
			String stockLockType = request.getParameter("stockLockType");
			String categoryId = request.getParameter("categoryId");
			String sendProvince = request.getParameter("sendProvince");
			String sendTown = request.getParameter("sendTown");
			String sendAddress = request.getParameter("sendAddress");

			Product product = productService.getById(new Long(productid));
			if (product == null) {
				product = new Product();
				product.setShopId(shopId);
			}
			product.setStatus(new Integer(status));
			product.setName(name);
			product.setFullName(name);
			product.setPromotion(promotion);
			product.setMarque(marque);
			product.setRoughWeight(new BigDecimal(roughWeight));
			product.setLength(new BigDecimal(length));
			product.setHeight(new BigDecimal(height));
			product.setBarcode(barcode);
			product.setNetWeight(new BigDecimal(netWeight));
			product.setWidth(new BigDecimal(width));
			product.setBulk(new BigDecimal(bulk));
			product.setProvince(new Long(province));
			product.setTown(new Long(town));
			product.setCounty(new Long(county));
			product.setProduceaddress(produceaddress);
			product.setIntroduction(introduction);
			product.setStockLockType(new Integer(stockLockType));
			product.setAfterService(afterService);
			product.setCategoryId(new Long(categoryId));
			product.setSupplierId(supplier.getId());
			product.setCarriage(new BigDecimal(carriage));
			product.setCreateDate(new Date());
			product.setUpdateDate(new Date());
			product.setSendProvince(new Long(sendProvince));
			product.setSendTown(new Long(sendTown));
			product.setSendAddress(sendAddress);
			product.setIsMarketable(0);

			productService.saveOrUpdate(product);

			Map delMap = new HashMap();
			delMap.put("productid", productid);
			//删除已有的属性
			productAttributeService.removeAllByProductid(delMap);
			////产品属性
			String[] attribute_result = request.getParameterValues("attribute_result");
			if (attribute_result != null && attribute_result.length > 0) {
				for (int i = 0; i < attribute_result.length; i++) {
					String attribute = attribute_result[i];
					if (attribute != null && !attribute.trim().equals("")) {
						String[] attributes = attribute.split("_");
						ProductAttribute pa = new ProductAttribute();
						pa.setAttributeId(new Long(attributes[0]));
						pa.setProductId(product.getId());
						pa.setValue(attributes[2]);
						productAttributeService.save(pa);
					}
				}
			}

			//删除已有的属性
			productParameterValueService.removeAllByProductid(delMap);
			////产品参数
			String[] parameter_result = request.getParameterValues("parameter_result");
			if (parameter_result != null && parameter_result.length > 0) {
				for (int i = 0; i < parameter_result.length; i++) {
					String parameter = parameter_result[i];
					if (parameter != null && !parameter.trim().equals("")) {
						String[] parameters = parameter.split("_");
						ProductParameterValue pa = new ProductParameterValue();
						pa.setParameterGroupId(new Long(parameters[0]));
						pa.setProductId(product.getId());
						pa.setParameterValue(parameters[2]);
						productParameterValueService.save(pa);
					}
				}
			}

			Map<String, Long> specificationMap = new HashMap<String, Long>();
			//删除已有的规格
			productSpecificationValueService.removeAllByProductid(delMap);
			////产品规格
			String[] specification_result = request.getParameterValues("specification_result");
			if (specification_result != null && specification_result.length > 0) {
				for (int i = 0; i < specification_result.length; i++) {
					String specification = specification_result[i];
					if (specification != null && !specification.trim().equals("")) {
						String[] specifications = specification.split("_");
						Long specificationId = new Long(specifications[0]);
						String specificationValue = specifications[1];
						String[] specificationValues = specificationValue.split(",");
						for (int j = 0; j < specificationValues.length; j++) {
							ProductSpecificationValue pa = new ProductSpecificationValue();
							pa.setSpecificationId(specificationId);
							pa.setProductId(product.getId());
							pa.setSpecificationValue(specificationValues[j]);
							pa.setIsDelete(0);
							productSpecificationValueService.save(pa);
							specificationMap.put(specificationValues[j], pa.getId());
						}

					}
				}
			}
			//删除已有的清单
			productDetailListService.removeAllByProductid(delMap);
			////商品清单
			String[] detaillist_result = request.getParameterValues("detaillist_result");
			if (detaillist_result != null && detaillist_result.length > 0) {
				for (int i = 0; i < detaillist_result.length; i++) {
					String detaillist = detaillist_result[i];
					if (detaillist != null && !detaillist.trim().equals("")) {
						String[] detaillists = detaillist.split("_");
						ProductDetailList pa = new ProductDetailList();
						pa.setIsdelete(0);
						pa.setName(detaillists[0]);
						pa.setNum(new Integer(detaillists[1]));
						pa.setOrders(i);
						pa.setProductId(product.getId());
						productDetailListService.save(pa);
					}
				}
			}

			List<BigDecimal> pricelist = new ArrayList<BigDecimal>();//价格，冒泡排序使用
			Integer allnum = 0;//总库存
			//删除sku（set isDelete=1）
			delMap.put("isDelete", 1);
			productSpecificationsService.removeAllByProductid(delMap);

			Map<String, List<Long>> specificationsMap = new HashMap<String, List<Long>>();//Map<红色，[1,2]>
			////商品sku
			String[] specifications_result = request.getParameterValues("specifications_result");
			if (specifications_result != null && specifications_result.length > 0) {
				for (int i = 0; i < specifications_result.length; i++) {
					String specifications = specifications_result[i];
					if (specifications != null && !specifications.trim().equals("")) {
						String[] specificationss = specifications.split("_");
						ProductSpecifications pa = new ProductSpecifications();
						pa.setProductCode(specificationss[1]);
						pa.setPrice(new BigDecimal(specificationss[2]));
						//pa.setStock(new Integer(specificationss[3]));
						//pa.setWarnnum(new Integer(specificationss[4]));
						allnum = allnum + new Integer(specificationss[3]);
						String specificationss0 = specificationss[0];
						String[] s0 = specificationss0.split(",");
						List<Long> s0list = new ArrayList<Long>();
						String ids = "";
						for (int j = 0; j < s0.length; j++) {
							s0list.add(specificationMap.get(s0[j]));
/*							ids = ids+specificationMap.get(s0[j]);
							if(j<s0.length-1){
								ids = ids+",";
							}*/
						}
						Collections.sort(s0list, new Comparator<Long>() {
							public int compare(Long arg0, Long arg1) {
								return arg0.compareTo(arg1);
							}
						});
						for (Long ll : s0list) {
							ids = ids + ll + ",";
						}
						if (ids != null && !ids.equals("")) {
							ids = ids.substring(0, ids.length() - 1);
						}
						pa.setItemids(ids);
						pa.setProductId(product.getId());
						productSpecificationsService.save(pa);
						List<Long> idslist = specificationsMap.get(s0[0]);
						if (idslist == null) {
							idslist = new ArrayList<Long>();
						}
						pricelist.add(pa.getPrice());
						idslist.add(pa.getId());
						specificationsMap.put(s0[0], idslist);
					}
				}
			}

			product.setAllnum(allnum);

			bubbleSort(pricelist);//价格排序
			product.setMinprice(pricelist.get(0));
			product.setShowPrice(pricelist.get(0) + "");
			product.setMaxprice(pricelist.get(pricelist.size() - 1));

			String imageStr = "";//主图
			//sku图片
			String[] specifications_image_result = request.getParameterValues("specifications_image_result");
			if (specifications_image_result != null) {
				for (String s : specifications_image_result) {
					if (s != null && !s.equals("")) {
						String[] ss = s.split("_");
						List<Long> idslist = specificationsMap.get(ss[0]);
						String[] images = ss[1].split(",");
						for (Long l : idslist) {
							for (int i = 0; i < images.length; i++) {
								ProductSpecificationsImage psi = new ProductSpecificationsImage();
								psi.setCreateDate(new Date());
								psi.setOrders((i + 1));
								psi.setSupplyId(supplier.getId());
								psi.setUpdateDate(new Date());
								psi.setSpecificationsId(l);
								psi.setSource(images[i]);
								productSpecificationsImageService.save(psi);
								if (imageStr.equals("")) {
									imageStr = images[i];
								}
							}
						}
					}
				}
			}
			product.setImage(imageStr);//主图
			productService.saveOrUpdate(product);
		}

//		RedirectView redirectView = new RedirectView("redirect:/product/gotoProductlist.html");
//		//mv.setViewName("product/product/list");
//        redirectView.setExpandUriTemplateVariables(false);
//        redirectView.setExposeModelAttributes(false);
//        mv.setView(redirectView);

		mv.setViewName("redirect:/product/gotoProductlist.html?selltype=waitsell");
		return mv;
	}


	/**
	 * 商品新增页面ajax获取品牌
	 **/
	@RequestMapping(value = "ajaxGetBrand", method = RequestMethod.GET)
	@Token(remove = true)
	@NoCheckLogin
	public ModelAndView ajaxGetBrand(HttpServletRequest request, HttpServletResponse response,Long shopId) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			Map map = new HashMap();
			map.put("supplierId", userModel.getSupplierId());
			map.put("shopId", shopId);
			List<ProductBrand> brandlist = productBrandService.findAllBymap(map);
			result.setErrorCode("0");
			result.setMsgBody(brandlist);
		}

		//logger.info("商品新增页面ajax获取品牌" );
		return new ModelAndView("", "result", result);
	}

	/**
	 * 商品list
	 **/
	@RequestMapping(value = "exchageProduct")
	public ModelAndView exchageProduct(HttpServletRequest request, SupplierExchangeProductQuery vo) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		vo.setSupplierId(us.getSupplierId());
		vo.setPageSize(10);
		PageInfo<SupplierExchangeProduct> page =  supplierExchangeProductService.findPage(vo);
		List<SupplierExchangeProduct> list = page.getList();
		Map<String,Object> map = new HashMap<String,Object>();
		for (SupplierExchangeProduct supplierExchangeProduct : list) {
			map.put("productId", supplierExchangeProduct.getProductId());
			Integer reservedNum = exchangeSuborderService.findReservedNumByMap(map);
			Integer exchangeSuNum = suborderService.findExchangeSuNumByMap(map);
			supplierExchangeProduct.setReservedNum(reservedNum);
			supplierExchangeProduct.setExchangeSuNum(exchangeSuNum);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("product/product/exchageProduct");
		mv.addObject("page", page);
		mv.addObject("query", vo);
		return mv;
	}
	
	/**
	 * 商品list
	 **/
	@RequestMapping(value = "gotoProductlist", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView gotoProductlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return productlist(request, response);
	}

	/**
	 * 商品list
	 **/
	@RequestMapping(value = "findProductlistPage", method = {RequestMethod.POST,RequestMethod.GET})
	@Token(remove = true)
	public ModelAndView findProductlistPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return productlist(request, response);
	}


	/**
	 * 商品list
	 **/
	public ModelAndView productlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());

			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page = 1;
			Integer size = 10;
			mv.setViewName("product/product/productlist");
			if (pages == null || pages.equals("")) {
				pages = "1";
			}
			page = new Integer(pages);

			if (sizes == null || sizes.equals("")) {
				sizes = "10";
			}

			size = new Integer(sizes);

			if (size > 100) {
				size = 100;
			}

			String shopId = request.getParameter("shopId");
			String name = request.getParameter("name");
			String partnumber = request.getParameter("partnumber");
			String categoryid = request.getParameter("categoryid");
			String minprice = request.getParameter("minprice");
			String maxprice = request.getParameter("maxprice");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String status = request.getParameter("status");
			String isMarketable = request.getParameter("isMarketable");
			String selltype = request.getParameter("selltype");
			String barcode = request.getParameter("barcode");
			String stoId = request.getParameter("stoId");
			//排序字段
			String sortname = request.getParameter("sortname");
			String pricesort = request.getParameter("pricesort");
			String allnumsort = request.getParameter("allnumsort");
			String createDatesort = request.getParameter("createDatesort");
			String sort = request.getParameter("sort");
			
			Map map = new HashMap();
			map.put("name", name);
			map.put("partnumber", partnumber);			
			if (selltype == null || selltype.equals("")) {
				selltype = "waitcheck";
			}
			map.put("selltype", selltype);
			map.put("barcode", barcode);
			map.put("supplierId", supplier.getId());

			if (!StringUtils.isEmpty(shopId)) {
				map.put("shopId", new Long(shopId));
			}
			if (!StringUtils.isEmpty(categoryid)) {
				map.put("categoryid", new Long(categoryid));
			}
			if (!StringUtils.isEmpty(stoId)) {
				map.put("stoId", new Long(stoId));
			}
			if (!StringUtils.isEmpty(minprice)) {
				map.put("minprice", new Double(minprice));
			}
			if (!StringUtils.isEmpty(maxprice)) {
				map.put("maxprice", new Double(maxprice));
			}
			if (!StringUtils.isEmpty(starttime)) {
				map.put("starttime", starttime + " 00:00:00");
			}
			if (!StringUtils.isEmpty(endtime)) {
				map.put("endtime", endtime + " 23:59:59");
			}
			if (!StringUtils.isEmpty(status)) {
				map.put("status", new Integer(status));
			}
			if (!StringUtils.isEmpty(isMarketable)) {
				map.put("isMarketable", new Integer(isMarketable));
			}
			if (!StringUtils.isEmpty(pricesort)) {
				map.put("pricesort", new Integer(pricesort));
			}
			if (!StringUtils.isEmpty(allnumsort)) {
				map.put("allnumsort", new Integer(allnumsort));
			}
			if (!StringUtils.isEmpty(sort)) {
				map.put("sort", new Integer(sort));
			}
			if (StringUtils.isEmpty(createDatesort)) {
				createDatesort = "2";
			}
			map.put("createDatesort", new Integer(createDatesort));
			if (StringUtils.isEmpty(sortname)) {
				sortname = "createDatesort";
			}
			map.put("sortname", (sortname == null || sortname.equals("") ? "createDatesort" : sortname));
			mv.addObject("pages", page);
			mv.addObject("sizes", size);
			mv.addObject("name", name);
			mv.addObject("partnumber", partnumber);
			mv.addObject("categoryid", categoryid);
			mv.addObject("shopId", shopId);
			mv.addObject("stoId", stoId);
			mv.addObject("minprice", minprice);
			mv.addObject("maxprice", maxprice);
			mv.addObject("starttime", starttime);
			mv.addObject("endtime", endtime);
			mv.addObject("status", status);
			mv.addObject("isMarketable", isMarketable);
			mv.addObject("selltype", selltype);
			mv.addObject("pricesort", pricesort);
			mv.addObject("allnumsort", allnumsort);
			mv.addObject("createDatesort", createDatesort);
			mv.addObject("sort", sort);
			mv.addObject("barcode", barcode);
			mv.addObject("sortname", (sortname == null || sortname.equals("") ? "createDatesort" : sortname));
	        Shop record = new Shop();
	        record.setSupplierId(userModel.getSupplierId());
	        List<Shop> shopList = shopService.selectByModel(record);
	        mv.addObject("shopList", shopList);
//			// 排序记录该商家下所有在售商品的总的个数
//				Map<String, Object> mapSortTotal = new HashMap<String, Object>();
//				mapSortTotal.put("selltype","selling");// 在售
//				mapSortTotal.put("supplierId", supplier.getId());// 商家ID
//				Integer sortTotal = productService.findProductlistPageCount(map);
//			mv.addObject("sortTotal",sortTotal);
	        if(selltype.equals("reject") || selltype.equals("waitcheck") || selltype.equals("waitsell")){
	        	Integer total = apprProductService.findProductlistPageCount(map);
	        	Integer startnum = (page - 1) * size;
	        	if (total > 0) {
					if (total < startnum) {
						startnum = total - size;
					}
					if (startnum < 0) {
						startnum = 0;
					} 
					map.put("startnum", startnum);
					map.put("size", size);
					List<ApprProduct> list = apprProductService.findProductlistPage(map);
					result.setPage(page);
					result.setSize(size);
					result.setTotal(total);
					result.setErrorCode("0");
					result.setMsgBody(list);
	        	}else {
					result.setErrorCode("1000");
				}
			}else if(selltype.equals("selling")){
				Integer total = productService.findProductlistPageCount(map);
				Integer startnum = (page - 1) * size;
				if (total > 0) {
					if (total < startnum) {
						startnum = total - size;
					}
					if (startnum < 0) {
						startnum = 0;
					}
					map.put("startnum", startnum);
					map.put("size", size);
					List<Product> list = productService.findProductlistPage(map);
					result.setPage(page);
					result.setSize(size);
					result.setTotal(total);
					result.setErrorCode("0");
					result.setMsgBody(list);
				} else {
					result.setErrorCode("1000");
				}
			
			}
			mv.addObject("result", result);
		}

		return mv;
	}


	/**
	 * ajax上架商品（版本表和正式表产生后，上架操作只可能在版本表中数据）
	 **/
	@RequestMapping(value = "ajaxSellOn")
	@Token(remove = true)
	public ActResult ajaxSellOn(HttpServletRequest request) throws Exception {
		String[] ids = request.getParameterValues("ids[]");
		List<Long> idslist = new ArrayList<Long>();
		if (ids != null) {
			for (String s : ids) {
				idslist.add(new Long(s));
			}
		}
		UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		ActResult<List<Long>> ret = apprProductService.sellOn(idslist, userModel);
		if (ret.isSuccess()) {
			for(Long pid : ret.getData()){//这里直接返回没有修改过敏感数据的正式表对象的id，更合理
				this.refreshProduct(pid,true);//更新缓存，索引和静态页
			}
			//日志
			SupplierLog log = new SupplierLog();
			log.setUserId(userModel.getId());
			log.setUsername(userModel.getUserName());
			if (idslist != null && idslist.size() > 0) {
				log.setAct("商品 (ids=" + ids + ") 上架");
			}
			log.setTime(new Date());
			log.setResult("success");
			supplierLogService.save(log);
		}
		return ret;
	}


	/**
	 * ajax下架商品（版本表和正式表产生后，下架操作只可能在正式表中数据）
	 **/
	@RequestMapping(value = "ajaxSellOff")
	@Token(remove = true)
	public ActResult ajaxSellOff(HttpServletRequest request) throws Exception {
		String[] ids = request.getParameterValues("ids[]");
		List<Long> idslist = new ArrayList<Long>();
		if (ids != null) {
			for (String s : ids) {
				idslist.add(new Long(s));
			}
		}
		UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		logger.info("ajax下架商品");
		//日志
		SupplierLog log = new SupplierLog();
		log.setUserId(userModel.getId());
		log.setUsername(userModel.getUserName());
		if (idslist != null && idslist.size() > 0) {
			log.setAct("商品 (ids=" + idslist.toString() + ") 下架");
		}
		log.setTime(new Date());
		log.setResult("success");
		supplierLogService.save(log);
		
		if (ids != null) {
			for (String s : ids) {
				Product product =productService.getById(new Long(s));
				
				if (product != null) {
					//处理换领商品下架后改变换领商品状态
					if(product.getSaleKbn() == 2) {
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("productId", product.getId());
						param.put("status", "2");
						List<SupplierExchangeProduct> l =supplierExchangeProductService.findListByMap(param);
						if(l !=null && l.size() > 0) {
							SupplierExchangeProduct ex = l.get(0);
							
							ex.setStatus(4);			// 状态=4:提前终止
							ex.setStopDate(new Date());	
							ex.setLimitEnd(new Date());	// 使用期限=系统时间
							ex.setUpdateDate(ex.getLimitEnd());
							ex.setUpdateUser(userModel.getId());
							supplierExchangeProductService.update(ex);
							
							UserExchangeTicket entity = new UserExchangeTicket();
							entity.setExchangeProductId(ex.getId());
							entity.setStatus(3);	//3:已过期
							entity.setLimitEnd(ex.getLimitEnd());
							entity.setUpdateDate(ex.getLimitEnd());
							entity.setUpdateUser(userModel.getId());
							userExchangeTicketService.updateEnds(entity);
						}
					}
					// skuMap中保存着sku新旧的对应关系
					Map<Long, ProductSpecifications> skuMap = new HashMap<Long, ProductSpecifications>();
					// stockMap中保存着库存新旧的对应关系
					Map<Long, Inventory> stockMap = new HashMap<Long, Inventory>();
					ApprProduct appr = productFacade.productToapprProduct(new Long(s),0, skuMap, stockMap);
				}
			}
		}
				
		ActResult ret = productService.sellOff(idslist, userModel);
		if(ret.isSuccess()) {
			for (Long long1 : idslist) {
				this.destroyProduct(long1);
			}
		}
		return ret;
	}

	/**
	 * ajax更新商品 ,商品的删除和批量上下架，临时表状态的取消审核操作
	 **/
	@RequestMapping(value = "ajaxUpdate", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView ajaxUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		String ids = request.getParameter("ids");
		String isMarketable = request.getParameter("isMarketable");
		//String status = request.getParameter("status");
		String selltype = request.getParameter("selltype");
		String[] idsTemp = null;
		List<Long> idslist = new ArrayList<Long>();
		if (ids != null) {
			idsTemp = ids.split(",");
		}

		if (idsTemp != null) {
			for (String s : idsTemp) {
				idslist.add(new Long(s));
			}
		}
		Map map = new HashMap();
		map.put("idslist", idslist);
		if (!StringUtils.isEmpty(isMarketable)) { //传递过来的isMarketable不为空
			map.put("isMarketable", new Integer(isMarketable)); //删除传递过来的isMarketable就是-10
			if (isMarketable.equals("-10")) { //如果等于-10表示要删除商品
				// 删除商品
				if (!selltype.equals("selling")) {
					//临时表就传递临时表的id
					if (idsTemp != null) {
						for (String s : idsTemp) {
							apprProductService.deleteById(new Long(s)); // 临时表的数据就直接删除了，使用delete
							deleteApprRelationFacade.deleteApprRelation(new Long(s));//删除临时表关联的表信息
						}
					}
				} else {
					for (Long idDel : idslist) {
						this.destroyProduct(idDel);
					}
					productService.updateProductByids(map);
					
					//正式表传递过来的就是正式表的id
					if (idsTemp != null) {
						for (String s : idsTemp) {
							ApprProduct appr =apprProductService.selectProductIdAndStatus(new Long(s));
							if(appr!=null){
								//如果根据productid和status<2来查看临时表中有在编辑状态的此条数据，就删除
								apprProductService.deleteById(new Long(appr.getId())); // 临时表的数据就直接删除了，使用delete
								deleteApprRelationFacade.deleteApprRelation(new Long(appr.getId()));//删除临时表关联的表信息
							}
						}
					}
				}
			}
			//日志
			SupplierLog log = new SupplierLog();
			log.setUserId(userModel.getId());
			log.setUsername(userModel.getUserName());
			if (idslist != null && idslist.size() > 0) {
				log.setAct("商品 (ids=" + idslist.toString() + ") 删除");
			}
			log.setTime(new Date());
			log.setResult("success");
			supplierLogService.save(log);
			
		} else { 
			if (selltype.equals("waitcheck") || selltype.equals("reject")) { //取消审核后，把数据放入待售中
				//map.put("isMarketable", 0);
				map.put("status", new Integer(0));
				apprProductService.updateProductByids(map);
			}

		}
		
		Result result = new Result();
		result.setErrorCode("0");
		return new ModelAndView("", "result", result);
	}
    	
	/**
	 * ajax 弹出要修改的sku中价格，库存等信息
	 **/
	@RequestMapping(value = "ajaxGetProductForUpdate", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView ajaxGetProductForUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String display = request.getParameter("display");
		Result result = new Result();
		Map map = new HashMap();
		List<Product> prolist =null;
		//如果获取传过来的是正式表id
		Product ptemp =productService.getById(new Long(id));
		if( ptemp != null){
			if(new Long(display)==1){
				Long categoryId = ptemp.getCategoryId();
				map.put("categoryId", new Long(categoryId));
				map.put("productId", new Long(id));
				prolist = productService.getProductByMap(map);
			}else{
				//判读临时表中是否在审核该商品
				ApprProduct ptemp2 =apprProductService.selectProductIdAndStatus(ptemp.getId());
				if(ptemp2==null){
					Long categoryId = ptemp.getCategoryId();
					map.put("categoryId", new Long(categoryId));
					map.put("productId", new Long(id));
					prolist = productService.getProductByMap(map);
					
				}else{
					Long categoryId = ptemp2.getCategoryId();
					map.put("categoryId", new Long(categoryId));
					map.put("productId", ptemp2.getId()); //之所以要改这个，是因为apprproductmapper.xml中getProductByMap方法是以id来查询
					prolist = apprProductService.getProductByMap(map);
					result.setKey("1"); //如果在编辑和在售同时有，前端快捷修改才显示"在售价格"
				}
			}
		} else {
			// 传递过来的是临时表的id就直接走下边的逻辑
			ApprProduct ptemp1 = apprProductService.getById(new Long(id));
			if (ptemp1 != null) {
				Long categoryId = ptemp1.getCategoryId();
				map.put("categoryId", new Long(categoryId));
				map.put("productId", new Long(id));
				prolist = apprProductService.getProductByMap(map);

			}
		}
		
		Product pro = new Product();
		if (prolist != null) {
			pro = prolist.get(0);//pro可能是正式表对象也可能是临时表对象
		}

		Map<Long, String> specificationValueMap = new HashMap<Long, String>();
		if (pro != null && pro.getProductSpecificationValuelist() != null) {
			for (int i = 0; i < pro.getProductSpecificationValuelist().size(); i++) {
				specificationValueMap.put(pro.getProductSpecificationValuelist().get(i).getId(), pro.getProductSpecificationValuelist().get(i).getSpecificationValue());//保存商品的规格值{1818312129513107=绿色}
			}
		}
		
		if (pro != null && pro.getProductSpecificationslist() != null) {
			for (int i = 0; i < pro.getProductSpecificationslist().size(); i++) {
				String itemids = pro.getProductSpecificationslist().get(i).getItemids();//一条或多条sku
				String[] idstemp = itemids.split(",");
				String itemnames = "";
				for (String idtemp : idstemp) {
					itemnames = itemnames + specificationValueMap.get(new Long(idtemp)) + "/";
				}
				if (!itemnames.equals("")) {
					itemnames = itemnames.substring(0, itemnames.length() - 1);//去掉/符号
				}
				pro.getProductSpecificationslist().get(i).setItemnames(itemnames);//设置规格值
				//处理sku库存===缓存取值
				String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + pro.getProductSpecificationslist().get(i).getId());
				if (stock != null && !stock.equals("")){
					pro.getProductSpecificationslist().get(i).setStock(Integer.valueOf(stock));
				}
			}
		}
		
		result.setErrorCode("0");//js页面判断使用
		
		pro.setProductAttributelist(null);
		pro.setProductDetaillist(null);
		pro.setProductParameterValuelist(null);
		pro.setProductSpecificationValuelist(null);
		result.setMsgBody(pro); //把包含规格值，sku信息的商品对象返回给jsp
		
		return new ModelAndView("", "result", result);
	}
	
	/**
	 * ajax获取商品价格
	 **/
	@RequestMapping(value = "ajaxGetSelfSpecification", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView ajaxGetSelfSpecification(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String supplierId = request.getParameter("supplierId");
		String categoryId = request.getParameter("categoryId");
		String productId = request.getParameter("productId");

		Map categoryMap = new HashMap();
		categoryMap.put("categoryid", new Long(categoryId));
		categoryMap.put("supplierId", new Long(supplierId));
		categoryMap.put("productid", -1L);
		categoryMap.put("type", 2);
		List<SupplierSpecification> supplierSpecificationlist = supplierSpecificationService.getSpecificationlistByCategoryid(categoryMap);

		//在售商品编辑时不可以删除规格值
		Long pId = StringUtils.isEmpty(productId)?-1L:Long.parseLong(productId);
		Product ptemp =productService.getById(pId);
		if(ptemp!=null && ptemp.getIsMarketable()!=null && ptemp.getIsMarketable()==1) {
			pId =-1L;
		}
		
		for (SupplierSpecification supplierSpecification : supplierSpecificationlist) {
			for (SpecificationValue value : supplierSpecification.getValuelist()) {
				value.setUsedCont(supplierSpecificationService.getOtherUseCount(supplierSpecification.getId(), value.getName(), pId));
			}
		}
		Result result = new Result();
		result.setErrorCode("0");
		result.setMsgBody(supplierSpecificationlist);

		return new ModelAndView("", "result", result);
	}
	/**
	 * 新增数据
	 * @param request
	 * @param response
	 * @param entUser
	 * @return
	 */
	@RequestMapping(value="ajaxSaveSelfSpecification")
	@ResponseBody
	public List<SupplierSpecification> ajaxSaveSelfSpecification(HttpServletRequest request,Long supplierId,Long categoryId){
		////规格1
		SupplierSpecification specification1 = new SupplierSpecification();
		specification1.setName(request.getParameter("kingaku1"));
		specification1.setOrders(1);
		specification1.setType(2);
		specification1.setCreatedDate(new Date());
		////规格值
		String[] kingaku1VLnames = request.getParameterValues("kingaku1VLname");
		String[] valuelist1ids = request.getParameterValues("valuelist1id");
		String[] valuelist1imgs = request.getParameterValues("valuelist1img");
		List<SpecificationValue> spvs1= new ArrayList<SpecificationValue>();
		for (int i=0;i<kingaku1VLnames.length;i++) {
			SpecificationValue sv = new SpecificationValue();
			//名称
			sv.setName(kingaku1VLnames[i]);
			//id
			if(StringUtils.isEmpty(valuelist1ids[i])) {
				sv.setId(null);
			} else {
				sv.setId(Long.parseLong(valuelist1ids[i]));
			}
			//img
			if(StringUtils.isEmpty(valuelist1imgs[i])) {
				sv.setImage(null);
			} else {
				sv.setImage(valuelist1imgs[i]);
			}
			spvs1.add(sv);
		}

		////规格2
		SupplierSpecification specification2 = null;
		List<SpecificationValue> spvs2= new ArrayList<SpecificationValue>();
		String kingaku2chk = request.getParameter("kingaku2chk");
		if("2".equals(kingaku2chk)) {
			specification2 = new SupplierSpecification();
			specification2.setName(request.getParameter("kingaku2"));
			specification2.setOrders(2);
			specification2.setType(2);
			specification2.setCreatedDate(new Date());
			////规格值
			String[] kingaku2VLnames = request.getParameterValues("kingaku2VLname");
			String[] valuelist2ids = request.getParameterValues("valuelist2id");
			String[] valuelist2imgs = request.getParameterValues("valuelist2img");
			for (int i=0;i<kingaku2VLnames.length;i++) {
				SpecificationValue sv = new SpecificationValue();
				//名称
				sv.setName(kingaku2VLnames[i]);
				//id
				if(StringUtils.isEmpty(valuelist2ids[i])) {
					sv.setId(null);
				} else {
					sv.setId(Long.parseLong(valuelist2ids[i]));
				}
				//img
				if(StringUtils.isEmpty(valuelist2imgs[i])) {
					sv.setImage(null);
				} else {
					sv.setImage(valuelist2imgs[i]);
				}
				spvs2.add(sv);
			}
		}
		supplierSpecificationFacade.SaveSupplierSpecification(supplierId, categoryId, specification1, spvs1, specification2, spvs2);
		
		Map categoryMap = new HashMap();
		categoryMap.put("categoryid", categoryId);
		categoryMap.put("supplierId", supplierId);
		categoryMap.put("productid", -1L);
		categoryMap.put("type", 2);
		return supplierSpecificationService.getSpecificationlistByCategoryid(categoryMap);
	}

	/**
	 * ajax修改sku弹出框中信息，点击确认要操作的
	 **/
	@RequestMapping(value = "ajaxSpecificationsChange", method = RequestMethod.POST)
	@Token(remove = true)
	public ModelAndView ajaxSpecificationsChange(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<BigDecimal> pricelist = new ArrayList<BigDecimal>();// 价格，冒泡排序使用
		Integer allnum = 0;// 总库存
		String productid = request.getParameter("productid");
		String selltypenew = request.getParameter("selltypenew");
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		if (userModel == null) {
			// 会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			//// 商品sku
			String[] specifications_result = request.getParameterValues("specifications_result");
			boolean changePrice = false;
			List<ProductSpecifications> lsSku = new ArrayList<ProductSpecifications>();
			List<Inventory> lsStock = new ArrayList<Inventory>();
			if (specifications_result != null && specifications_result.length > 0) {
				for (int i = 0; i < specifications_result.length; i++) {
					String specifications = specifications_result[i];
					if (specifications != null && !specifications.trim().equals("")) {
						String[] specificationss = specifications.split("_");
						Long specificationsid = new Long(specificationss[0]);
						ProductSpecifications pa = productSpecificationsService.getById(specificationsid);
						if (pa != null) {
							String ret = "修改商品（id='" + pa.getProductId() + "')的sku (id='" + pa.getId()
									+ "')的价格库存预警值等属性：";
							ret += "价格由￥" + pa.getPrice() + " 修改为" + specificationss[1] + ";";
							if (!changePrice) {
								if (pa.getPrice().compareTo(new BigDecimal(specificationss[1])) == 0) {//如果电商价格没变
									// 价格没有变化
									if (specificationss.length >= 6) {
										//这里有几种情况首先第一点 1.原内购价可能为null。2.原内购价为null，就要进行计算，计算如果相等，就不是修改
										if (null == pa.getInternalPurchasePrice()){
											
											if (pa.getMaxFucoin().compareTo(new BigDecimal(specificationss[4])) != 0) { //最大内购券改变，说明内购价改变
												// 价格改变
												changePrice = true;
											}
										}else{
											if (new BigDecimal(specificationss[5]).compareTo(pa.getInternalPurchasePrice()) != 0){//内购价改变
												// 价格改变
												changePrice = true;
											}
										}
									} else {//长度不等于5
										if (pa.getMaxFucoin().compareTo(BigDecimal.ZERO) != 0) { //内购券改变，价格改变
											// 价格改变
											changePrice = true;
										}
									}
								} else {//修改电商价，价格改变
									// 价格改变
									changePrice = true;
								}
							}
							pa.setPrice(new BigDecimal(specificationss[1]));
							if (specificationss.length >= 6) {
								pa.setInternalPurchasePrice(new BigDecimal(specificationss[5]));//增加内购价
								if (new BigDecimal(specificationss[4])
										.compareTo(new BigDecimal(specificationss[1])) == 1) {//如果内购券金额大于电商价
									if (new BigDecimal(specificationss[1]).compareTo(new BigDecimal(1)) == 1) { //电商价大于1
										pa.setMaxFucoin(new BigDecimal(specificationss[1]));// new
																							// Integer(specificationss[1])
									} else {
										pa.setMaxFucoin(new BigDecimal(0));// 0
									}
								} else {//内购券金额不大于电商价，直接设置
									pa.setMaxFucoin(new BigDecimal(specificationss[4]));// new
																						// Integer(specificationss[4])
								}
								pa.setMinLimitNum(new Integer(specificationss[6]));
							} else { //如果长度不等于6
								pa.setMaxFucoin(new BigDecimal(0));// 0
							}

							// pa.setStock(new Integer(specificationss[2]));
							// pa.setWarnnum(new Integer(specificationss[3]));
							// 这个更新需要放在正式表数据更新到临时表之后操作
							//productSpecificationsService.saveOrUpdate(pa);
							lsSku.add(pa);
							
							Map map = new HashMap();
							map.put("productSpecificationsId", pa.getId());
							List<Inventory> inventorylist = inventoryService.findAllBymap(map);
							Inventory inventory = null;
							if (inventorylist != null && inventorylist.size() > 0) {
								inventory = inventorylist.get(0);
							}
							if (inventory == null) {
								inventory = new Inventory();
							}
							ret += "库存由" + inventory.getQuantity() + "修改为" + specificationss[2] + ";";
							ret += "库存预警值由" + inventory.getWarnQuantity() + "修改为" + specificationss[3] + ";";
							
							
							inventory.setProductSpecificationsId(pa.getId());
							inventory.setQuantity(new Integer(specificationss[2]));
							inventory.setWarnQuantity(new Integer(specificationss[3]));
							// 这个更新需要放在正式表数据更新到临时表后操作
							lsStock.add(inventory);
							//inventoryService.saveOrUpdate(inventory);

							// 日志
							SupplierLog log = new SupplierLog();
							log.setUserId(userModel.getId());
							log.setUsername(userModel.getUserName());
							log.setAct("修改商品（id='" + pa.getProductId() + "')的sku (id='" + pa.getId() + "')的价格库存预警值等属性");
							log.setTime(new Date());
							log.setResult(ret);
							supplierLogService.save(log);

							pricelist.add(pa.getPrice());
							allnum = allnum + new Integer(specificationss[2]);
						}
					}
				}
			}
			
			if (productid != null && !productid.equals("")) {
				ApprProduct product = null;
               
				// 假如传过来的是临时表的id，product是有数据的
				product = apprProductService.getById(new Long(productid));
                if(product !=null && product.getStatus()<2){
                	 //如果是临时表的sku，直接更新生成 
					for (ProductSpecifications sku : lsSku) {
						productSpecificationsService.saveOrUpdate(sku);
					}
					for (Inventory inven : lsStock) {
						//更新处理sku库存
						String key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(inven.getProductSpecificationsId());
						redis.del(key);
						redis.setData(key, inven.getQuantity().toString());
						redis.removeSet(RedisConstant.Inventory_CHANGE, String.valueOf(inven.getProductSpecificationsId()) + "");
						inventoryService.saveOrUpdate(inven);
					}
					if(changePrice) {//如果快捷修改sku价格改变，需要审核
						if(product.getUpdateDesc() == null || !product.getUpdateDesc().contains("sku价格或者库存改变")) {					
							product.setUpdateDesc((product.getUpdateDesc()==null?"":product.getUpdateDesc())+"sku价格或者库存改变,");
						}
					}
					
				} else {
					//查看是否在编辑状态有此商品
					product = apprProductService.selectProductIdAndStatus(new Long(productid));
					
					if(product !=null){ //如果有，临时表数据直接更新
	                	 //如果是临时表的sku，直接更新生成 
						for (ProductSpecifications sku : lsSku) {
							productSpecificationsService.saveOrUpdate(sku);
						}
						for (Inventory inven : lsStock) {
							inventoryService.saveOrUpdate(inven);
							// 更新缓存库存
							String key = RedisConstant.REDIS_SKU_INVENTORY + inven.getProductSpecificationsId();
							redis.del(key);
							redis.setData(key, String.valueOf(inven.getQuantity()));
							redis.removeSet(RedisConstant.Inventory_CHANGE, inven.getProductSpecificationsId() + "");
						}
						if(changePrice) {//如果快捷修改sku价格改变，需要审核
							if(product.getUpdateDesc() == null || !product.getUpdateDesc().contains("sku价格或者库存改变")) {			
								product.setUpdateDesc((product.getUpdateDesc()==null?"":product.getUpdateDesc())+"sku价格或者库存改变,");
							}
						}
					} else {//传递过来的是正式表id，修改完价格后要把正式表的数据更新到临时表中，
						// skuMap中保存着sku新旧的对应关系
						Map<Long, ProductSpecifications> skuMap = new HashMap<Long, ProductSpecifications>();
						// stockMap中保存着库存新旧的对应关系
						Map<Long, Inventory> stockMap = new HashMap<Long, Inventory>();
						if(changePrice) {//如果快捷修改sku价格改变，需要审核
							product = productFacade.productToapprProduct(new Long(productid),1,skuMap, stockMap);//返回的是临时表对象
							product.setUpdateDesc("sku价格或者库存改变");
							// 遍历要修改的正式表的sku
							for (ProductSpecifications sku : lsSku) {
								// 获取新生成的sku对象和老sku的对应关系
								ProductSpecifications n = skuMap.get(sku.getId());
								// copy sku to new
								n.setPrice(sku.getPrice());
								n.setMaxFucoin(sku.getMaxFucoin());
								n.setInternalPurchasePrice(sku.getInternalPurchasePrice());
								n.setMinLimitNum(sku.getMinLimitNum());
								// 把新的sku对象插入数据库中，内容为正式表的sku数据
								productSpecificationsService.saveOrUpdate(n);
							}
							for (Inventory inv : lsStock) {
								Inventory in = stockMap.get(inv.getId());
								// in.setLockQuantity(inv.getLockQuantity());
								in.setQuantity(inv.getQuantity());
								in.setWarnQuantity(inv.getWarnQuantity());
								inventoryService.saveOrUpdate(in);
								// 更新缓存库存
								String key = RedisConstant.REDIS_SKU_INVENTORY + in.getProductSpecificationsId();
								redis.del(key);
								redis.setData(key, String.valueOf(in.getQuantity()));
								redis.removeSet(RedisConstant.Inventory_CHANGE, in.getProductSpecificationsId() + "");
							}
						} else {//直接更新正式表数据
							// 查询正式表商品
							Product productOld = productService.getById(new Long(productid));
							// 更新正式表商品总库存
							productOld.setAllnum(allnum);
							productService.update(productOld);
							// 遍历要修改的正式表的sku中的起售数量
							for (ProductSpecifications sku : lsSku) {
								// 获取新生成的sku对象和老sku的对应关系
								ProductSpecifications n = productSpecificationsService.getById(sku.getId());
								n.setMinLimitNum(sku.getMinLimitNum());
								// 把新的sku对象插入数据库中，内容为正式表的sku数据
								productSpecificationsService.saveOrUpdate(n);
							}
							
							for (Inventory in : lsStock) { //更新旧版库存
								inventoryService.saveOrUpdate(in);

								// 更新缓存（正式表数据放缓存中），前端静态页面自动去redis中取出更新后的库存并显示，无需重新生成静态页
								String key = RedisConstant.REDIS_SKU_INVENTORY + in.getProductSpecificationsId();
								redis.del(key);
								redis.setData(key, String.valueOf(in.getQuantity()));
								redis.removeSet(RedisConstant.Inventory_CHANGE, in.getProductSpecificationsId() + "");
							}
							
							this.refreshProduct(new Long(productid), false); //更新缓存和索引，false表示不生成静态页
						}
					}

				}
				// 根据临时表product_id调用selectProductIdAndStatus获取status状态小于2
				if (product != null) {
					product.setAllnum(allnum);
					product.setUpdateDate(new Date());//快捷修改更新修改时间
					if (pricelist != null && pricelist.size() > 0) {
						bubbleSort(pricelist);// 价格排序
						product.setMinprice(pricelist.get(0));
						product.setShowPrice(pricelist.get(0) + "");
						product.setMaxprice(pricelist.get(pricelist.size() - 1));
						product.setShowPrice(pricelist.get(0) + "");
					}
					this.apprProductService.saveOrUpdate(product);

				}

			}

			result.setErrorCode("0");
			mv.setViewName("redirect:/product/gotoProductlist.html?selltype=" + selltypenew);
		
		}
		return mv;
	}
	/**
	 * 新增数据
	 * @param request
	 * @param response
	 * @param entUser
	 * @return
	 */
	@RequestMapping(value = "ajaxGetSku", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelMap ajaxGetSku(HttpServletRequest request,ModelMap model, Long productId){
		List<ProductSpecifications> ls = productSpecificationsService.getlistByProductid(productId);
		
		if(ls==null || ls.isEmpty()) { //临时保存功能回显sku信息
			//没有填写sku
			model.addAttribute("skuCnt", 0); //创建skuCnt参数用于判断是否是填写了sku（自己没想到）
			model.addAttribute("specificationType", "2");
			return model;
		}

		model.addAttribute("skuCnt", ls.size());
		Map<String,List<String>> smap =  new LinkedHashMap<String,List<String>>();
		Map<String,ProductSpecifications> skus =  new HashMap<String,ProductSpecifications>();
		String firstItemids = "";
		boolean first = false;
		List<String> keys = new ArrayList<String>();
		for (ProductSpecifications sku : ls) {
			JSONObject json =JSONObject.parseObject(sku.getItemValues());
			if(!first) {
				firstItemids = sku.getItemids();
				first = true;

				String[] ary = new String[2];
				int i=0;
				for (String key : json.keySet()) {
					ary[i++]=key;
				}

				if(StringUtils.isEmpty(ary[1])) {
					keys.add(ary[0]);
				} else {
					int index0 = sku.getItemValues().indexOf(ary[0]);
					int index1 = sku.getItemValues().indexOf(ary[1]);
					if(index0 < index1) {
						keys.add(ary[0]);
						keys.add(ary[1]);					
					} else {
						keys.add(ary[1]);
						keys.add(ary[0]);	
					}
				}
			}
			
				
			String skuKey = "";
			for (String key : keys) {
				List<String> values = null;
				if(smap.containsKey(key)) {
					values=smap.get(key);
				} else {
					values= new ArrayList<String>();
					smap.put(key, values);
				}
				
				String v = json.getString(key);
				boolean has = false;
				for (String string : values) {
					if(string.equals(v)) {
						has = true;
						break;
					}
				}
				if(!has) {
					values.add(v);
				}
				skuKey += ("," +v); 
			}
			
			skus.put(skuKey, sku);
		}
		
		List<Inventory> ils = inventoryService.findByProduct(productId);
		//处理sku库存从缓存里面取出==保证实时性
		for (Inventory il : ils) {
			String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + il.getProductSpecificationsId());
			if (stock != null && !stock.equals("")){
				il.setQuantity(Integer.valueOf(stock));
			}
		}
		Map<String,Inventory> inventorys =  new HashMap<String,Inventory>();
		for (Inventory inventory : ils) {
			inventorys.put(inventory.getProductSpecificationsId()+"", inventory);
		}
		
		List<ProductSpecificationsImage> imgls =  productSpecificationsImageService.getSkuImglistByProductId(productId);
		Map<String,String> imgs =  new HashMap<String,String>();
		for (ProductSpecificationsImage img : imgls) {
			if(imgs.containsKey(img.getSpecificationsId()+"")) {
				imgs.put(img.getSpecificationsId()+"", imgs.get(img.getSpecificationsId()+"")+"," + img.getSource());
			} else {
				imgs.put(img.getSpecificationsId()+"", img.getSource());
			}
		}
		model.addAttribute("names", keys);
		model.addAttribute("smap", smap);
		model.addAttribute("skus", skus);
		model.addAttribute("inventorys", inventorys);
		model.addAttribute("imgs", imgs);
		String specificationType = "0";
		if(firstItemids.contains(",")) firstItemids=firstItemids.substring(0, firstItemids.indexOf(","));
		SupplierSpecification s = supplierSpecificationService.getSpecificationByitemid(Long.parseLong(firstItemids));
		if(s!=null) {
			specificationType = s.getType()+"";
		}
		model.addAttribute("specificationType", specificationType);
		return model;
	}
	
	/**
	 * 冒泡排序
	 *
	 */
	public static void bubbleSort(List<BigDecimal> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 1; j < list.size() - i; j++) {
				BigDecimal a = new BigDecimal(0);
				if ((list.get(j - 1)).compareTo(list.get(j)) > 0) {   //比较两个整数的大小
					a = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, a);
				}
			}
		}
	}


	/*****************************商品列表***************************************/
	/**
	 * 商品list
	 **/
	@RequestMapping(value = "gotoProductlistForCenter", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView gotoProductlistForCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return productlistForCenter(request, response);
	}

	/**
	 * 商品list
	 **/
	@RequestMapping(value = "findProductlistForCenter")
	@Token(remove = true)
	public ModelAndView findProductlistForCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return productlistForCenter(request, response);
	}


	/**
	 * 商品list
	 **/
	public ModelAndView productlistForCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if (userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000");
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page = 1;
			Integer size = 10;
			mv.setViewName("product/shopsetting/productlistforcenter");
			if (pages == null || pages.equals("")) {
				pages = "1";
			}
			page = new Integer(pages);

			if (sizes == null || sizes.equals("")) {
				sizes = "10";
			}

			size = new Integer(sizes);

			if (size > 100) {
				size = 100;
			}

			String name = request.getParameter("name");
			String minprice = request.getParameter("minprice");
			String maxprice = request.getParameter("maxprice");
			String selltype = request.getParameter("selltype");
			//排序字段
			String sortname = request.getParameter("sortname");
			String pricesort = request.getParameter("pricesort");
			String createDatesort = request.getParameter("createDatesort");

			if (StringUtils.isEmpty(sortname)) {
				sortname = "createDatesort";
			}
			Map map = new HashMap();
			map.put("supplierId", supplier.getId());
			map.put("name", name);
			map.put("sortname", sortname);
			map.put("selltype", selltype);
			if (!StringUtils.isEmpty(minprice)) {
				map.put("minprice", new Double(minprice));
			}
			if (!StringUtils.isEmpty(maxprice)) {
				map.put("maxprice", new Double(maxprice));
			}
			if (!StringUtils.isEmpty(pricesort)) {
				map.put("pricesort", new Integer(pricesort));
			}
			if (StringUtils.isEmpty(createDatesort)) {
				createDatesort = "2";
			}
			map.put("createDatesort", new Integer(createDatesort));
			mv.addObject("pages", page);
			mv.addObject("sizes", size);
			mv.addObject("name", name);
			mv.addObject("minprice", minprice);
			mv.addObject("maxprice", maxprice);
			mv.addObject("sortname", sortname);
			mv.addObject("pricesort", pricesort);
			mv.addObject("createDatesort", createDatesort);
			mv.addObject("selltype", selltype);

			Integer total = productService.findProductlistPageCount(map);
			Integer startnum = (page - 1) * size;
			if (total > 0) {
				if (total < startnum) {
					startnum = total - size;
				}
				if (startnum < 0) {
					startnum = 0;
				}
				map.put("startnum", startnum);
				map.put("size", size);
				List<Product> list = productService.findProductlistPage(map);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
				result.setMsgBody(list);
			} else {
				result.setErrorCode("1000");
			}
			mv.addObject("result", result);
		}
		return mv;
	}


	/**
	 * ajax查看审核不通过的原因信息
	 **/
	@RequestMapping(value = "getProductCheckById", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView getProductCheckById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productId = request.getParameter("productId");
		Map map = new HashMap();
		ApprProduct  product=null;
		product=apprProductService.getById(new Long(productId));
		if(product !=null){
		//这里需要使用productid才能从审核表中取出审核不通过信息
		   map.put("productId", new Long(product.getProductId()));
		}
		//审核不通过提交过来的肯定是临时表id
		product = apprProductService.getProductCheckById(map);
		//审核表t_check_opinion通过checkid=product_id来获取审核信息
		Result result = new Result();
		result.setErrorCode("0");
		result.setMsgBody(product);
		return new ModelAndView("", "result", result);
	}

	/**
	 * 设置商品展示排序
	 *
	 * @return
	 */
	@RequestMapping(value = "setProductViewSort", method = RequestMethod.GET)
	public void setProductViewSort(HttpServletRequest request, HttpServletResponse response) {

		String _productId = request.getParameter("productId");
		String _sort = request.getParameter("sortNum");

		Long productId = Long.valueOf(_productId);
		Integer sort = 1;
		if (!StringUtils.isEmpty(_sort)) {
			sort = Integer.valueOf(_sort);
		}

		productService.updateSortNum(productId, sort);
		
		Product p = productService.getById(productId);
		if(p!=null && p.getIsMarketable()!=null && p.getIsMarketable()==1) {
			refreshProduct(productId,false);
		}
	}

	/**
	 * ajax获取运费模板
	 **/
	@RequestMapping(value="ajaxGetShippingTemplates")
	@ResponseBody
	public ActResult<String> ajaxGetShippingTemplates(HttpServletRequest request,Long supplierId){
		Supplier supplier = supplierService.getById(supplierId);
		ModelMap model = new ModelMap();
		ShippingTemplate record = new ShippingTemplate();
		record.setSupplierId(supplier.getId());
		record.setIsAudit(0);
		List<ShippingTemplate> shipptemplatelist = shippingTemplateService.selectByModel(record);
		
		ShippingFreeRule record2 = new ShippingFreeRule();//设置包邮规则
		record2.setTemplateId(supplier.getId());
		List<ShippingFreeRule> freerulelist = shippingFreeRuleService.selectByModel(record2);
		
		model.addAttribute("shipptemplatelist", shipptemplatelist);
		model.addAttribute("freerulelist", freerulelist);
		model.addAttribute("shippingFree", supplier.getShippingFree()+"");
		
		return ActResult.success(model);
	}
	
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	public void refreshProduct(Long productId,boolean crreateHtml) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/rebuild/"+productId, paramMap); //更新缓存和索引
			if(crreateHtml) {
				HttpClientUtil.sendHttpRequest("post", Constant.CREATHTML_API_URL + "/product", paramMap);//生成静态页
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	public void destroyProduct(Long productId) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/destroy/"+productId, paramMap); //更新缓存和索引
		} catch (Exception e) {
		}
	}
	/**
	 * 校验商品名称不能重复
	 * @param request
	 * @param apprId
	 * @param productId
	 * @param fullName
	 * @return
	 */
	
	@RequestMapping(value="validatorfullName")
	@ResponseBody
	public Integer validatorfullName(HttpServletRequest request,Long apprId,Long productId,String fullName,String productcopy){
		Map map=new HashMap<>();
		Integer flag=0;
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		map.put("fullName", fullName);
		map.put("supplierId", userModel.getSupplierId());
		
		if(productId == null) {
			//新添加商品
			Long aid = apprProductService.getSupplierapprFullname(map);//从临时表中查询状态小于2的商品名称
			if(aid!=null) return 1;
			
			Long pid = productService.getSupplierFullname(map); //从正式表中查询上架状态大于-2的商品名称
			if(pid!=null) return 1;
		} else {
			if(apprId==null) {
				//在售商品编辑 无编辑中数据
				Long aid = apprProductService.getSupplierapprFullname(map);//从临时表中查询状态小于2的商品名称
				if(aid!=null) return 1;

				Long pid = productService.getSupplierFullname(map); //从正式表中查询上架状态大于-2的商品名称
				if(pid!=null && !productId.equals(pid)) return 1;//如果有此商品，不修改自己
				
				if(pid!=null && null != productcopy && "copy".equals(productcopy)){//如果是复制的商品页面，则pid不为空就需要提示
					return 1;
				}
			} else {
				//编辑中数据
				//在售商品编辑 有编辑中数据
				Long aid = apprProductService.getSupplierapprFullname(map);//从临时表中查询状态小于2的商品名称
				if(aid!=null && !apprId.equals(aid)) return 1;

				ApprProduct appr = apprProductService.getById(apprId);
				
				Long pid = productService.getSupplierFullname(map); //从正式表中查询上架状态大于-2的商品名称
				if(pid!=null && !appr.getProductId().equals(pid)) return 1;		
			}
		}
		return 0;
	}
	
	
	/**
	 * 在售商品快捷修改点击还原在售信息按钮取消前次修改（删除待审核的数据）
	 * @param request
	 * @param ProductId
	 * @return
	 */
	
	@RequestMapping(value="deleteModifyAppr")
	@ResponseBody
	public Integer deleteModifyAppr(HttpServletRequest request,Long productId){
		ApprProduct product= apprProductService.selectProductIdAndStatus(new Long(productId));//根据正式表id获取是否有在编辑数据
		
		if(product!=null){
			apprProductService.deleteById(product.getId());
			deleteApprRelationFacade.deleteApprRelation(new Long(product.getId()));//删除临时表关联的表信息
			return 1;
		}
		return 0;
	}
	
	
	/**
	 * 查询商家邮费
	 * @param mv
	 * @param supplier
	 */
	private void selectShippingTemplate(ModelAndView mv, Supplier supplier) {
		ShippingTemplate record = new ShippingTemplate();
		record.setSupplierId(supplier.getId());
		record.setIsAudit(0);
		List<ShippingTemplate> shippingTemplateList = shippingTemplateService.selectByModel(record);
		mv.addObject("listShippingTemplate", shippingTemplateList);//根据商家id获取定义的运费模板一个或多个
		//这里进行判断如果这里面有version为2的也就是新版的要将version为2的显示隐藏version为1的如果没有新的走老的
		if(shippingTemplateList !=null){
			//现将存到modelAndView的值去掉
			mv.addObject("listShippingTemplate", null);
			List<ShippingTemplate> newShippingTemplateList = new ArrayList<ShippingTemplate>();
			for (ShippingTemplate shippingTemplate : shippingTemplateList) {
				if(shippingTemplate.getVersion() == 2){
					ShippingTemplateRule shippingTemplateRule = new ShippingTemplateRule();//模板规则（计价方式，发货时间等）
					shippingTemplateRule.setTemplateId(shippingTemplate.getId());
					shippingTemplate.setRulelist(shippingTemplateRuleService.selectByModel(shippingTemplateRule)); //设置模板规则
					
					ShippingFreeRule shippingFreeRule = new ShippingFreeRule();//设置包邮规则
					shippingFreeRule.setTemplateId(shippingTemplate.getId());
					shippingTemplate.setFreelist(shippingFreeRuleService.selectByModel(shippingFreeRule));
					mv.addObject("shippingTemplate", shippingTemplate);//只显示新版的
					mv.addObject("shippingTemplateId", shippingTemplate.getId());//获取商品所关联的运费模板id
					return;
				}
				
			}
			
		}
	}
}





