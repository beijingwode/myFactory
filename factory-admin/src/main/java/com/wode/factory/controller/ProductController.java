/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.facade.ApprProductFacade;
import com.wode.factory.facade.ProductExchangeFacade;
import com.wode.factory.facade.ProductFacadeOff;
import com.wode.factory.facade.SpecialSaleFacade;
import com.wode.factory.mapper.ProductBrandMapper;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductECard;
import com.wode.factory.model.ProductHis;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.model.SupplierLimitTicketSku;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.service.ApprProductService;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.InventoryService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductECardService;
import com.wode.factory.service.ProductHisService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.ProductThirdPriceService;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateRuleService;
import com.wode.factory.service.ShippingTemplateService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierLimitTicketService;
import com.wode.factory.service.SupplierLimitTicketSkuService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserExchangeTicketService;
import com.wode.factory.vo.ApprProductVO;
import com.wode.factory.vo.ProductCategoryVo;
import com.wode.factory.vo.ProductVO;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysRole;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysRoleService;

@Controller
@RequestMapping("product")
@SuppressWarnings("rawtypes")
public class ProductController {
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductThirdPriceService ptpService;
	@Autowired
	private ApprProductService apprProductService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;
	@Resource
	private ProductService productService;
	@Autowired
	private ShippingTemplateRuleService shippingTemplateRuleService;
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	@Autowired
	private ProductBrandMapper productBrandMapper;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductHisService productHisService;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	private ProductExchangeFacade productExchangeFacade;
	@Autowired
	private SpecialSaleFacade specialSaleFacade;
	@Resource
	private ApprProductFacade apprProductFacade;
	@Autowired
    private ProductFacadeOff productFacade;
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	
	@Resource
	private HtmlAction htmlAction;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysRoleService sysRoleService;
	//运费模板
	@Autowired
	private ShippingTemplateService shippingTemplateService;
	//企采
	@Autowired
	private ProductLadderService productLadderService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	@Autowired
	private ProductECardService productECardService;
	@Autowired
	private SupplierLimitTicketService supplierLimitTicketService;
	@Autowired
	private SupplierLimitTicketSkuService supplierLimitTicketSkuService;
	@Autowired
	private InventoryService inventoryService;
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@RequestMapping("/rebuildAll")
	@ResponseBody
	public ActResult rebuildAll(HttpServletRequest request) {
		logger.info("rebuildAll on sell product...");
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("isMarketable", 1);
		List<Product> listPro = productService.find(params);
		logger.info(listPro.size() + " product to rebuild...");
		Map<Long,Long> pPvs=  clientAccessLogService.getDetailPvCnt(null, null);
		StringBuffer erSb = new StringBuffer();
		for (Product pro : listPro) {
			try {
				productService.cache(pro.getId(),pPvs);
			} catch (Exception e) {
				erSb.append("id=:" + pro.getId() + "出错：" + e.getLocalizedMessage());
			}

		}
		logger.info(listPro.size() + " product rebuild completed!");
		return ActResult.success(erSb.toString());
	}
	
	@RequestMapping("/rebuild/{productid}")
	@ResponseBody
	@NoCheckLogin
	public ActResult rebuild(HttpServletRequest request, @PathVariable Long productid) {
		try {
			Map<Long,Long> pPvs=  clientAccessLogService.getDetailPvCnt(null, productid);
			Product pro= productService.cache(productid,pPvs);
			htmlAction.createProductDetail(request, pro.getId());
			return ActResult.success(pro);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail(e.getMessage());
		}
	}

	@RequestMapping("/destroy/{productid}")
	@ResponseBody
	@NoCheckLogin
	public ActResult destroy(HttpServletRequest request, @PathVariable Long productid) {
		try {
			productService.destroy(productid);
			return ActResult.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail(e.getMessage());
		}
	}


	@RequestMapping("/rebuild/supplier/{supplierId}")
	@ResponseBody
	public ActResult rebuildSupplier(HttpServletRequest request, @PathVariable Long supplierId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("supplierId", supplierId);
		params.put("isMarketable", 1);
		List<Product> listPro = productService.find(params);
		logger.info("rebuildSupplier("+supplierId+")"+listPro.size() + " product to rebuild...");
		Map<Long,Long> pPvs=  clientAccessLogService.getDetailPvCnt(supplierId, null);
		StringBuffer erSb = new StringBuffer();
		for (Product pro : listPro) {
			productService.cache(pro.getId(),pPvs);
		}
		logger.info("rebuildSupplier("+supplierId+")"+listPro.size() + " product be rebuilded");
		return ActResult.success(erSb.toString());
	}


	@RequestMapping("/create/supplier/{supplierId}")
	@ResponseBody
	public ActResult createAll(HttpServletRequest request, @PathVariable Long supplierId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("supplierId", supplierId);
		params.put("isMarketable", 1);
		List<Product> listPro = productService.find(params);
		logger.info("rebuildSupplier("+supplierId+")"+listPro.size() + " product to rebuild...");
		Map<Long,Long> pPvs=  clientAccessLogService.getDetailPvCnt(supplierId, null);
		StringBuffer erSb = new StringBuffer();
		for (Product pro : listPro) {
			try {
				productService.cache(pro.getId(),pPvs);
				htmlAction.createProductDetail(request, pro.getId());
			} catch (Exception e) {
				erSb.append("id=:" + pro.getId() + "出错：" + e.getLocalizedMessage());
			}

		}
		logger.info("rebuildSupplier("+supplierId+")"+listPro.size() + " product be rebuilded");
		return ActResult.success(erSb.toString());
	}
	/**
	 * 线下销售
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="upDateProduct",method = RequestMethod.POST)
	@ResponseBody
	public ActResult upDateProduct(HttpServletRequest request, Long id) {
		productService.updateProduct(id);
		return ActResult.success("");
	}
	/**
	 * 跳转到位置管理页
	 *
	 * @return
	 */
	@RequestMapping("baseCheck")
	public String toPageCheck(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 150);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		return "manager/product/product-check-base";
	}
	
	/**
	 * 跳转到位置管理页
	 *
	 * @return
	 */
	@RequestMapping("productManage")
	public String toProductManage(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 150);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		return "manager/product/productmanage-check-base";
	}

	/**
	 * 跳转到位置管理页
	 *
	 * @return
	 */
	@RequestMapping("ptp")
	public String toPtp(Model model,HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		
		//Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		//SysUser user = (SysUser)obj;
		//if(isLeander(user.getId())) {
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("officeId", 0);
			params2.put("officeType", "");
			params2.put("roles", "-108,-111");
			params2.put("pageNum", 1);
			params2.put("pageSize", 150);

			PageHelper.startPage(params2);
			List<SysUser> list = sysUserMapper.findPageInfo(params2);
			
			model.addAttribute("mlist", list);
		//}
		
		
		return "manager/product/ptp-base";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ptplist", method = RequestMethod.POST)
	public String ptplist(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {

		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
//		if(!isLeander(user.getId())) {
//			params.put("managerId", user.getId());
//		}
		String isMark = request.getParameter("isMarketable");
		if (!StringUtils.isEmpty(isMark)) {
			if ("1".equals(isMark)) {//上架
				params.put("isMark", 1);
			}else{
				params.put("isMarket", 2);
			}
		}
		model.addAttribute("page", ptpService.findNotifyList(params));
		return "manager/product/ptp-list";
	}
	/**
	 * 跳转到位置管理页
	 *
	 * @return
	 */
	@RequestMapping
	public String toPageAttr(Model model) {
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		return "manager/product/product";
	}
	
	@RequestMapping("view")
	public String toPageAttrView(Model model,HttpSession session) {
		model.addAttribute("status", "view");
		
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
//		if(!isLeander(user.getId())) {
//			query.put("managerId", user.getId());
//		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("officeId", 0);
			params.put("officeType", "");
			params.put("roles", "-108,-111");
			params.put("pageNum", 1);
			params.put("pageSize", 120);

			PageHelper.startPage(params);
			List<SysUser> list = sysUserMapper.findPageInfo(params);
			
			model.addAttribute("mlist", list);
			model.addAttribute("uid", user.getId());
//		}
		
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/product/product";
	}


	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		return supplierService.getPage(query).getList();
	}


	/**
	 * 商品审核属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model,String viewStatus,HttpServletRequest request,HttpSession session) {
		String[] saleKbnArr = request.getParameterValues("saleKbn");
		if(saleKbnArr!=null && saleKbnArr.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : saleKbnArr) {
				sb.append(string).append(",");
			}
			params.put("saleKbn",sb.toString());
		}
		String[] selfTypeArr = request.getParameterValues("selfType");
		if(selfTypeArr!=null && selfTypeArr.length>0) {
			StringBuilder sb2 =new StringBuilder();
			for (String string : selfTypeArr) {
				sb2.append(string).append(",");
			}
			params.put("selfType",sb2.toString());
		}
		String[] str = viewStatus.split(",");
		if(str.length>0)
			viewStatus = str[0];

//		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
//		SysUser user = (SysUser)obj;
//		if(!isLeander(user.getId())) {
//			if(!params.containsKey("managerId")){
//				params.put("managerId", user.getId());
//			}
//		}
		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
		
		if("2".equals(params.get("status"))){
			PageInfo<Product> page = productService.findList(params);
			
			for (Product p : page.getList()) {
				
				p.setProductSpecificationslist(productSpecificationsService.findlistByProductid(p.getId()));
				
			}
			model.addAttribute("page", page);
		}else{
			PageInfo<ApprProduct> page = apprProductService.findList(params);
			
			for (ApprProduct p : page.getList()) {
				//显示临时表的价格
				p.setProductSpecificationslist(productSpecificationsService.findlistByProductid(p.getId()));
				if("view".equals(viewStatus)) {
					p.setId(p.getProductId());
				}
				
			}
			model.addAttribute("page", page);
			
		}		
		model.addAttribute("supplierUrl", Constant.FACTORY_SUPPLIER_URL);
		return "view".equals(viewStatus)?"manager/product/product-list-view":"manager/product/product-list";
	}
	
	/**
	 * 商品管理属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "productlist", method = RequestMethod.POST)
	public String productlist(@RequestParam Map<String, Object> params, Model model,String viewisMarketable,HttpSession session,HttpServletRequest request) {
		String[] ary = request.getParameterValues("saleKbn");
		if(ary!=null && ary.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : ary) {
				sb.append(string).append(",");
			}
			params.put("saleKbn",sb.toString());
		}
		String[] str = viewisMarketable.split(",");
		if(str.length>0)
			viewisMarketable = str[0];

		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
		Boolean flag = false;
		if("1".equals(params.get("Enterpriseprocurement"))){//企采
			flag=true;
		}
		
		PageInfo<Product> page = productService.findList(params);
		
		List<Product> delList = new ArrayList<Product>();
		for (Product p : page.getList()) {
			//显示临时表的价格
			p.setProductSpecificationslist(productSpecificationsService.findlistByProductid(p.getId()));
			List<ProductLadder> productLadderlist = productLadderService.getByProductId(p.getId());
			p.setProductLadderList(productLadderlist);
			if (productLadderlist == null || productLadderlist.isEmpty()) {// 无阶梯价格
				delList.add(p);
			}
			
		}
		if(flag){
			page.getList().removeAll(delList);
		}
		model.addAttribute("page", page);
		model.addAttribute("supplierUrl", Constant.FACTORY_SUPPLIER_URL);
		return "view".equals(viewisMarketable)?"manager/product/productmanage-list-view":"manager/product/productmanage-list";
	}
	

	@RequestMapping(value = "toSetWelfare", method = RequestMethod.POST)
	public String toSetWelfare(Long id, Model model) {
		//查询商品信息
		Product product = productService.getById(id);

		model.addAttribute("product", product);
		return "manager/product/setWelfare";
	}

	@RequestMapping(value = "toLock", method = RequestMethod.POST)
	public String toLock(Long id, Model model) {
		//查询商品信息
		Product product = productService.getById(id);

		model.addAttribute("product", product);
		return "manager/product/lockProduct";
	}

	@RequestMapping(value = "toUnLock", method = RequestMethod.POST)
	public String toUnLock(Long id, Model model) {
		//查询商品信息
		Product product = productService.getById(id);

		model.addAttribute("product", product);
		return "manager/product/unlockProduct";
	}

	/**
	 * 功能说明：审核商品,并且声称商品详情页、对应的索引，对应的缓存
	 *
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "setWelfare", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Integer> setWelfare(HttpServletRequest request, Long productId, BigDecimal welfarePrice) {

		//查询商品信息
		Product product = productService.getById(productId);
		product.setWelfarePrice(welfarePrice);
		//审核通过 修改  t_product 中字段 status
		Integer checkResult = productService.updateByBusiness(product);
		
		return ActResult.success(checkResult);
	}

	/**
	 * 功能说明：审核商品,并且声称商品详情页、对应的索引，对应的缓存
	 *
	 * @param pojo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "lockProdect", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<Integer>  lockProdect(HttpServletRequest request, Long productId, Integer r,String lockReason) {

		//查询商品信息
		Product product = productService.getById(productId);
		if(r==1) {
			product.setLocked(1);
			product.setLockReason(lockReason);
		} else {
			product.setLocked(0);
			product.setLockReason(null);
		}
		//审核通过 修改  t_product 中字段 status
		Integer checkResult = productService.updateByBusiness(product);
		
		return ActResult.success(checkResult);
	}
	/**
	 * 功能说明：跳转到修改页面
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "toCheck", method = RequestMethod.POST)
	public String toCheck(Long productId, Model model,String status,Long id) {
		//查询商品信息
		//Product product = productService.getById(id);
		ApprProduct product =apprProductService.getById(id);
		Map<Long,List<ProductSpecificationsImage>> imgs = new HashMap<Long,List<ProductSpecificationsImage>>();
		if(product.getProductSpecificationslist() != null) {
			for (ProductSpecifications sku : product.getProductSpecificationslist()) {
				imgs.put(sku.getId(), productSpecificationsImageService.findlistByProductSpecificationsid(sku.getId()));
			}
		}
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(productId);
		
		//运费规则
		model.addAttribute("shippingTemplate",shippingTemplateRuleService.getByProductId(id));//product实体类
		//运费包邮规则
		model.addAttribute("shippingFreeRule",shippingFreeRuleService.getByProductId(id));
		Map map = new HashMap();
		map.put("productId", id);
		
		List<ProductSpecificationValue> pv1 = new ArrayList<ProductSpecificationValue>();
		List<ProductSpecificationValue> pv2 = new ArrayList<ProductSpecificationValue>();
		List<ProductSpecificationValue> pvs = productSpecificationsService.findProductSpecificationValueBymap(map);
		String kigaku2="";
		String kigaku1="规格";
		if(!pvs.isEmpty()) {
			kigaku1=pvs.get(0).getSpecificationName();
			kigaku2="";
			for (ProductSpecificationValue pv : pvs) {
				if(kigaku1.equals(pv.getSpecificationName())) {
					pv1.add(pv);
				} else {
					kigaku2=pv.getSpecificationName();
					pv2.add(pv);
				}
			}			
		}
		
		model.addAttribute("specTable", this.makeSpecTable(1,product.getProductSpecificationslist(),  pv2.isEmpty()?1:2, kigaku1, pv1, kigaku2, pv2, imgs,productHisService.getLast(product.getProductId())));

		model.addAttribute("checkList", checkList);
		model.addAttribute("product", product);
		model.addAttribute("skuPrice", product.getProductSpecificationslist().get(0).getInternalPurchasePrice());
		Supplier supplier = supplierService.findByid(product.getSupplierId());
		model.addAttribute("supplier", supplier);
		//List<ProductThirdPrice> ptpList = ptpService.selectByProductId(productId);
		//if(ptpList==null) ptpList= new ArrayList<ProductThirdPrice>();
		/*for(int i=ptpList.size();i<1;i++) {
			ptpList.add(new ProductThirdPrice());
		}
		model.addAttribute("ptpHtml", makePtpTr2(ptpList));*/
		
		model.addAttribute("ptpHtml", makePtp(product.getProductSpecificationslist(),pv1,pv2,product.getProductId()));
		model.addAttribute("id", id);
		/*
		 * 设置全场包邮运费模板
		 */
		Double amount=supplier.getShippingFree();	
		//定义一个常量
		Double number =8000000.00D;
		int flag=0;
		if(amount ==-1D){//如果不设置免邮模板
			flag=1;
		}else if(amount > number){ //如果设置免邮模板
			flag=2;
			ShippingFreeRule record2 = new ShippingFreeRule();//设置包邮规则
			record2.setTemplateId(amount.longValue());
			//运费包邮规则
			model.addAttribute("supplierShippingFreeRule",shippingFreeRuleService.selectByModel(record2));
		}else{//如果设置全场满多少包邮
            flag=3;
	    }
		//这里判断是否有新的包邮模板
		ShippingTemplate shippingTemplate = new ShippingTemplate();
		shippingTemplate.setSupplierId(supplier.getId());
		shippingTemplate.setVersion(2);
		shippingTemplate.setIsAudit(0);
		List<ShippingTemplate> shippingTemplateList = shippingTemplateService.selectByModel(shippingTemplate);
		if(null != shippingTemplateList && !shippingTemplateList.isEmpty() ){
			ShippingTemplate newShippingTemplate = shippingTemplateList.get(0);
			model.addAttribute("isNewShippingTemplate", "1");// 将老模版本至空
			if(BigDecimal.ZERO.compareTo(product.getCarriage()) == 0){
				model.addAttribute("isNewCarriage", "1");// 将老模版本至空
			}
			flag = -1;// 将老模版本至空
			ShippingTemplateRule shippingTemplateRule = new ShippingTemplateRule();
			shippingTemplateRule.setTemplateId(newShippingTemplate.getId());
			model.addAttribute("shippingTemplate", shippingTemplateRuleService.selectByModel(shippingTemplateRule));// product实体类

			ShippingFreeRule record2 = new ShippingFreeRule();// 设置包邮规则
			record2.setTemplateId(newShippingTemplate.getId());
			List<ShippingFreeRule> shippingFreeRuleList = shippingFreeRuleService.selectByModel(record2);
			if (null != shippingFreeRuleList && shippingFreeRuleList.size() > 0) {
				// 运费包邮规则
				model.addAttribute("supplierShippingFreeRule", shippingFreeRuleList);

			} else {
				// 运费包邮规则
				model.addAttribute("supplierShippingFreeRule", null);
			}
		}
		model.addAttribute("flag", flag);
//		SupplierExchangeProduct query = new SupplierExchangeProduct();
//		query.setSupplierId(product.getSupplierId());
//		query.setProductId(product.getProductId());
//		query.setStatus(1);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("supplierId", product.getSupplierId());
		param.put("productId", product.getProductId());
		param.put("status", "1,2");
		List<SupplierExchangeProduct> ls= supplierExchangeProductService.findListByMap(param);
		if(!ls.isEmpty()) {
			model.addAttribute("exchangeProduct", ls.get(0));
		}
		// 阶梯企采
		List<ProductLadder> productLadderList = productLadderService.getByProductId(id);
		//处理规格
		if (productLadderList != null && !productLadderList.isEmpty()) {// 阶梯价格非空
			
			ProductSpecifications sku =null;
			for (ProductLadder productLadder : productLadderList) {
				String[] skuIds = productLadder.getSkuids().split(",");
				String skuValues = "";
				for (String skuId : skuIds) {
					if("null".equals(skuId.trim())) continue;
					sku = productSpecificationsService.getById(Long.valueOf(skuId));
					if(sku !=null){
						 skuValues += getSpecificationValue(sku,pv1,pv2)+"&nbsp;&nbsp;&nbsp;&nbsp;";
						
					}
				}
				productLadder.setSkuids(skuValues);
			}
		}
		model.addAttribute("supplierUrl", Constant.FACTORY_SUPPLIER_URL);
		model.addAttribute("productLadderList", productLadderList);
		return "view".equals(status)?"manager/product/productmanage-check-view":"manager/product/product-check";
	}
	private String getSpecificationValue(ProductSpecifications sku, List<ProductSpecificationValue> pv1,
			List<ProductSpecificationValue> pv2) {
		String skuValues ="";
		for (ProductSpecificationValue v1 : pv1) {
			if (pv2 != null && !pv2.isEmpty()) {
				for (ProductSpecificationValue v2 : pv2) {
					if (sku.getItemids().equals(v1.getId() + "," + v2.getId())
							|| sku.getItemids().equals(v2.getId() + "," + v1.getId())) {
						skuValues += v1.getSpecificationValue() + "/" + v2.getSpecificationValue();
					}
				}
			} else {
				if (sku.getItemids().equals(v1.getId().toString())) {
					skuValues += v1.getSpecificationValue();
				}
			}
		}
		return skuValues;
	}

	/**
	 * 功能说明：跳转到修改页面
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toManage", method = RequestMethod.POST)
	//public String toManage(Long productId, Model model,String status,Long id) {
	public String toManage(Model model,String status,Long id) {
		//查询商品信息
		ApprProduct appr = apprProductService.selectProductIdAndStatus(id);
		Product product = productService.getById(id);
		if(appr!=null) {
			if(product == null) {
				//没有正式商品
				return toCheck(id, model, status, appr.getId());
			} else {
				//正式商品已下架
				if(product.getIsMarketable() !=null && product.getIsMarketable()<1){
					return toCheck(id, model, status, appr.getId());
				}
				
				//有待审核数据
				if(appr.getStatus() == 1) {
					return toCheck(id, model, status, appr.getId());
				}
			}
		}
		//ApprProduct product =apprProductService.getById(id);
		Map<Long,List<ProductSpecificationsImage>> imgs = new HashMap<Long,List<ProductSpecificationsImage>>();
		if(product.getProductSpecificationslist() != null) {
			for (ProductSpecifications sku : product.getProductSpecificationslist()) {
				imgs.put(sku.getId(), productSpecificationsImageService.findlistByProductSpecificationsid(sku.getId()));
			}
		}
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);

		//运费规则
		model.addAttribute("shippingTemplate",shippingTemplateRuleService.getByProductId(id));//product实体类
		//运费包邮规则
		model.addAttribute("shippingFreeRule",shippingFreeRuleService.getByProductId(id));
		Map map = new HashMap();
		map.put("productId", id);
		
		List<ProductSpecificationValue> pv1 = new ArrayList<ProductSpecificationValue>();
		List<ProductSpecificationValue> pv2 = new ArrayList<ProductSpecificationValue>();
		List<ProductSpecificationValue> pvs = productSpecificationsService.findProductSpecificationValueBymap(map);
		String kigaku2="";
		String kigaku1="规格";
		if(!pvs.isEmpty()) {
			kigaku1=pvs.get(0).getSpecificationName();
			kigaku2="";
			for (ProductSpecificationValue pv : pvs) {
				if(kigaku1.equals(pv.getSpecificationName())) {
					pv1.add(pv);
				} else {
					kigaku2=pv.getSpecificationName();
					pv2.add(pv);
				}
			}			
		}
		
		model.addAttribute("specTable", this.makeSpecTable(0,product.getProductSpecificationslist(),  pv2.isEmpty()?1:2, kigaku1, pv1, kigaku2, pv2, imgs,productHisService.getLast(product.getId())));
		model.addAttribute("checkList", checkList);
		model.addAttribute("product", product);
		Supplier supplier = supplierService.findByid(product.getSupplierId());
		model.addAttribute("supplier", supplier);
		//List<ProductThirdPrice> ptpList = ptpService.selectByProductId(id);
		//if(ptpList==null) ptpList= new ArrayList<ProductThirdPrice>();
		/*for(int i=ptpList.size();i<2;i++) {
			ptpList.add(new ProductThirdPrice());
		}*/
		//model.addAttribute("ptpHtml", makePtpTr2(ptpList));
		model.addAttribute("ptpHtml", makePtp(product.getProductSpecificationslist(),pv1,pv2,product.getId()));
		model.addAttribute("id", id);
		/*
		 * 商家定义全场包邮模板
		 */
		Double amount=supplier.getShippingFree();
		//定义一个常量
		Double number =8000000.00D;
		int flag=0;
		if(amount ==-1D){//如果不设置免邮模板
			flag=1;
		}else if(amount > number){ //如果设置免邮模板
			flag=2;
			ShippingFreeRule record2 = new ShippingFreeRule();//设置包邮规则
			record2.setTemplateId(amount.longValue());
			//运费包邮规则
			model.addAttribute("supplierShippingFreeRule",shippingFreeRuleService.selectByModel(record2));
		}else{//如果设置全场满多少包邮
            flag=3;
	    }
		//这里判断是否有新的包邮模板
		ShippingTemplate shippingTemplate = new ShippingTemplate();
		shippingTemplate.setSupplierId(supplier.getId());
		shippingTemplate.setVersion(2);
		shippingTemplate.setIsAudit(0);
		List<ShippingTemplate> shippingTemplateList = shippingTemplateService.selectByModel(shippingTemplate);
		if (null != shippingTemplateList && !shippingTemplateList.isEmpty()) {
			ShippingTemplate newShippingTemplate = shippingTemplateList.get(0);
			model.addAttribute("isNewShippingTemplate", "1");// 将老模版本至空
			if(BigDecimal.ZERO.compareTo(product.getCarriage()) == 0){
				model.addAttribute("isNewCarriage", "1");// 将老模版本至空
			}
			flag = -1;// 将老模版本至空
			ShippingTemplateRule shippingTemplateRule = new ShippingTemplateRule();
			shippingTemplateRule.setTemplateId(newShippingTemplate.getId());
			model.addAttribute("shippingTemplate", shippingTemplateRuleService.selectByModel(shippingTemplateRule));// product实体类

			ShippingFreeRule record2 = new ShippingFreeRule();// 设置包邮规则
			record2.setTemplateId(newShippingTemplate.getId());
			List<ShippingFreeRule> shippingFreeRuleList = shippingFreeRuleService.selectByModel(record2);
			if (null != shippingFreeRuleList && shippingFreeRuleList.size() > 0) {
				// 运费包邮规则
				model.addAttribute("supplierShippingFreeRule", shippingFreeRuleList);
			} else {
				// 运费包邮规则
				model.addAttribute("supplierShippingFreeRule", null);
			}

		}
		model.addAttribute("flag", flag);
		
		SupplierExchangeProduct query = new SupplierExchangeProduct();
		query.setSupplierId(product.getSupplierId());
		query.setProductId(product.getId());
		List<SupplierExchangeProduct> ls= supplierExchangeProductService.selectByModel(query);
		if(!ls.isEmpty()) {
			model.addAttribute("exchangeProduct", ls.get(0));
		}
		
		// 阶梯企采
		List<ProductLadder> productLadderList = productLadderService.getByProductId(id);
		// 处理规格
		if (productLadderList != null && !productLadderList.isEmpty()) {// 阶梯价格非空

			ProductSpecifications sku = null;
			for (ProductLadder productLadder : productLadderList) {
				String[] skuIds = productLadder.getSkuids().split(",");
				String skuValues = "";
				for (String skuId : skuIds) {
					sku = productSpecificationsService.getById(Long.valueOf(skuId));
					if (sku != null) {
						skuValues += getSpecificationValue(sku, pv1, pv2) + "&nbsp;&nbsp;&nbsp;&nbsp;";

					}
				}
				productLadder.setSkuids(skuValues);
			}
		}
		model.addAttribute("productLadderList", productLadderList);
		model.addAttribute("supplierUrl", Constant.FACTORY_SUPPLIER_URL);
		return "view".equals(status)?"manager/product/productmanage-check-view":"manager/product/productmanage-check";
	}
	
	/**
	 * 功能说明：审核商品,并且声称商品详情页、对应的索引，对应的缓存
	 *
	 * @param pojo
	 * @return
	 */
	@RequestMapping(value = "doCheck", method = RequestMethod.POST)
	@ResponseBody
	public Integer doCheck(HttpServletRequest request, ApprProduct product) {
        String id=request.getParameter("id");
		String opinion = request.getParameter("opinion");//意见
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		String selfDate = request.getParameter("selfDate");//日期
		String imgIds = request.getParameter("imgIds");
		sortImages(imgIds);
		//删除旧数据
		List<ProductThirdPrice> oldptpList = ptpService.selectByProductId(product.getProductId());//通过商品id获取所有的第三方价格
		for (ProductThirdPrice productThirdPrice : oldptpList) {
			ptpService.removeById(productThirdPrice.getId());
		}
		
		String ptp2 = request.getParameter("ptp");
		if (!StringUtils.isEmpty(ptp2)) {
			List<ProductThirdPrice> ptpList = JSON.parseArray(ptp2, ProductThirdPrice.class);
			if(ptpList!=null && !ptpList.isEmpty()){
				for (ProductThirdPrice ptp : ptpList) {
					if(!StringUtils.isEmpty(ptp.getItemUrl().trim())){//链接非空
						ptp.setConfrimDate(new Date());
						ptp.setLastPrice(ptp.getPrice());
						ptp.setProductId(product.getProductId());
						ptp.setUpdateDate(new Date());
						if(ptp.getId() == null){
							ptp.setId(dbUtils.CreateID());
						}
						ptp.setUrlStatus(1);
						ptpService.saveProductThirdPrice(ptp);
					}
				}
			}
 		}
		
		if (obj != null) {
			//记录审核表
			CheckOpinion co = new CheckOpinion();
			co.setId(dbUtils.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(product.getProductId());
			co.setResult(product.getStatus());
			co.setOpinion(opinion);
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			supplierService.saveCheckOpinion(co);
			
		}
		ApprProduct pro = this.apprProductService.getById(new Long(id));
		pro.setStatus(product.getStatus());
		pro.setSelfType(product.getSelfType());
		if(product.getSelfType()==1 || product.getSelfType()==0){
			pro.setSelfTime(new Date());
		}else{//指定日期上线
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
			try {
				pro.setSelfTime(format1.parse(selfDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//审核通过 修改  t_product 中字段 status
		Integer checkResult = apprProductFacade.check(pro);
		//获取正式表商品信息为下面的换领和特价商品处理使用
		Product pro1 = this.productService.getById(product.getProductId());
        
		Map<String,Long> map = new HashMap<String,Long>();
	    map.put("productId", product.getId());
		//map.put("productId", pro.getId());
		pro.setProductSpecificationValuelist(productSpecificationsService.findProductSpecificationValueBymap(map));
		//审核通过getProductSpecificationslist()
		if(checkResult>0){
			// 2通过 -1不通过
			if (product.getStatus() == 2) {
				// 换领商品处理
				if(pro1.getSaleKbn()!=null && pro1.getSaleKbn() == 2) {
					ActResult<List<UserTicketHis>> act = productExchangeFacade.ExchangeProuctCheck(pro1,user.getName());//特省处理（正式表）
					//屏蔽换领币分配
//					if (act.getData() != null) {
//						Map<String,Object> paramPush = new HashMap<String,Object>();
//						paramPush.put("title", "收到换领币");
//						
//						Map<String,Object> paramWX = new HashMap<String,Object>();
//						paramWX.put("type", "balace");
//						paramWX.put("cId", "3");
//						paramWX.put("date", TimeUtil.getStringDateShort());
//						paramWX.put("note", "收到换领币");
//						for (UserTicketHis userTicketHis : act.getData()) {
//							try {
//								paramPush.put("msg", "您已收到"+userTicketHis.getTicket()+"换领币。换领活动已开启，大牌商品限时免费领~");
//
//								// app 推送
//								paramPush.put("userId", userTicketHis.getUserId());
//								HttpClientUtil.sendHttpRequest("post", apiUrl + "user/pushMsg", paramPush);
//
//								//w微信 推送
//								paramWX.put("userId", userTicketHis.getUserId());
//								paramWX.put("amount", userTicketHis.getTicket());
//								HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
//							} catch (Exception ex) {
//
//							}
//						}
//					}
				}else {
					// 非换领时 删除记录
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("supplierId", pro1.getSupplierId());
					param.put("productId", pro1.getId());
					param.put("status", "1,2");
					List<SupplierExchangeProduct> ls= supplierExchangeProductService.findListByMap(param);
					if(ls != null && ls.size() > 0) {
						UserExchangeTicket query = new UserExchangeTicket();
						for (SupplierExchangeProduct supplierExchangeProduct : ls) {
							//换领商品变其他类商品验证处理
							query.setExchangeProductId(supplierExchangeProduct.getId());
							List<UserExchangeTicket> list = userExchangeTicketService.selectByModel(query);
							if(list.size() > 0) {
								supplierExchangeProduct.setStatus(4);			// 状态=4:提前终止
								supplierExchangeProduct.setStopDate(new Date());	
								supplierExchangeProduct.setLimitEnd(new Date());	// 使用期限=系统时间
								supplierExchangeProduct.setUpdateDate(supplierExchangeProduct.getLimitEnd());
								supplierExchangeProductService.update(supplierExchangeProduct);
								
								UserExchangeTicket entity = new UserExchangeTicket();
								entity.setExchangeProductId(supplierExchangeProduct.getId());
								entity.setStatus(3);	//3:已过期
								entity.setLimitEnd(new Date());
								entity.setUpdateDate(entity.getLimitEnd());
								userExchangeTicketService.updateEnds(entity);
							}else {
								//换领币没有被领用过 直接删除记录
								supplierExchangeProductService.removeById(supplierExchangeProduct.getId());
							}
						}
					}
					
				}
				
				specialSaleFacade.SpecialSaleCheck(pro1,user);//特享商品处理
//				if (act.getData() != null) {
//					Map<String,Object> paramPush = new HashMap<String,Object>();
//					paramPush.put("title", "公司发放福利");
//					Map<String,Object> paramWX = new HashMap<String,Object>();
//					paramWX.put("type", "special_sale");
//					paramWX.put("productId", pro1.getId());
//					paramWX.put("productName", pro1.getFullName());
//					paramWX.put("amount", pro1.getEmpPrice());
//					paramWX.put("date", TimeUtil.getStringDateShort());					
//					paramWX.put("comName", supplierService.findByid(pro1.getSupplierId()).getComName());
//										
//					for (EnterpriseUser eu : act.getData()) {
//						try {
//							paramPush.put("msg", "公司新上架商品："+pro1.getFullName()+",本企业员工可超低价享受福利，商品id:"+pro1.getId());
//
//							// app 推送
//							paramPush.put("userId", eu.getId());
//							HttpClientUtil.sendHttpRequest("post", apiUrl + "user/pushMsg", paramPush);
//
//							//w微信 推送
//							paramWX.put("userId", eu.getId());
//							HttpClientUtil.sendHttpRequest("post", apiUrl + "wx/templateMsgSend", paramWX);
//						} catch (Exception ex) {
//
//						}
//					}
//				}
				//下面的代码是生成静态页面
				try {
					htmlAction.createProductDetail(request, product.getProductId());
				} catch (Exception e) {
						e.printStackTrace();
				}
			}
			
			/**
			 * 审核完成后，需要发送邮件提醒用户
			 * */
			//获取商家账号邮箱
			UserFactory uf = this.supplierService.getSupplierUser(pro.getSupplierId());
			if(uf!=null) {
				String toEmail = StringUtils.isEmpty(uf.getEmail())?uf.getUserName():uf.getEmail();
				if(!StringUtils.isEmpty(toEmail)){
					// 2通过 -1不通过
					if (product.getStatus() == 2) {
						//emailUtil.sendProductCheckEmail(toEmail, pro.getFullName(), pro.getName(),null, true);
					} else if (product.getStatus() == -1) {
						emailUtil.sendProductCheckEmail(toEmail, pro.getFullName(), pro.getName(), opinion, false);
					}
				}
			}
		}
		String flag = request.getParameter("flag");
		List<ProductSpecifications> skuList = productSpecificationsService.findlistByProductid(pro1.getId());
		if(flag.equals("1")) {//判断是否自动增加券
			BigDecimal bigDecimal = new BigDecimal("400.00");
			for (ProductSpecifications productSpecifications : skuList) {
				SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
				slts.setSkuId(productSpecifications.getId());
				List<SupplierLimitTicketSku> supplierLimitTicketSkuList = supplierLimitTicketSkuService.selectByModel(slts);
				if(supplierLimitTicketSkuList!=null&&supplierLimitTicketSkuList.size()>0) {
					for (SupplierLimitTicketSku supplierLimitTicketSku : supplierLimitTicketSkuList) {
						SupplierLimitTicket supplierLimitTicket = supplierLimitTicketService.getById(supplierLimitTicketSku.getLimitTicketId());
						if(supplierLimitTicket.getTicketType()==1) {
							if(productSpecifications.getMaxFucoin().compareTo(bigDecimal)>=0) {
								supplierLimitTicket.setTicket(productSpecifications.getMaxFucoin());
								supplierLimitTicketService.update(supplierLimitTicket);
								supplierLimitTicketSku.setSkuId(productSpecifications.getId());
								supplierLimitTicketSku.setPrice(productSpecifications.getPrice());
								supplierLimitTicketSku.setSalePrice(productSpecifications.getInternalPurchasePrice());
								supplierLimitTicketSku.setTicket(productSpecifications.getMaxFucoin());
								supplierLimitTicketSkuService.update(supplierLimitTicketSku);
							}else {
								supplierLimitTicketService.removeById(supplierLimitTicket.getId());
								supplierLimitTicketSkuService.removeById(supplierLimitTicketSku.getId());
							}
						}
					}
				}else {
					if(productSpecifications.getMaxFucoin().compareTo(bigDecimal)>=0) {
						SupplierLimitTicket slt = new SupplierLimitTicket();
						slt.setId(dbUtils.CreateID());
						slt.setSupplierId((long) -1);
						slt.setCompanyName("我的福利 联合内购");
						slt.setOneceFlag(1);
						slt.setTicketType(1);
						slt.setLimitType(2);
						slt.setLimitKey("skuId_"+productSpecifications.getId());
						slt.setRegisteFlg("2");
						slt.setRegisteNormalPrzie("0");
						slt.setRegisteAutoPlus("0");
						slt.setTicket(productSpecifications.getMaxFucoin());
						slt.setCash(BigDecimal.ZERO);
						slt.setTicketNum(inventoryService.getInventoryFromRedis(productSpecifications.getId()));
						slt.setLimitStart(new Date());
						Calendar now = Calendar.getInstance();
						now.add(Calendar.DAY_OF_MONTH, 90);
						slt.setLimitEnd(now.getTime());
						slt.setNextAction("http://www.wd-w.com/"+pro1.getId()+".html?skuId="+productSpecifications.getId());
						slt.setTicketNote(pro1.getFullName());
						slt.setStatus(1);
						slt.setCreateDate(new Date());
						slt.setCreateUser(user.getId());
						slt.setCreateUserName(user.getName());
						slt.setReceiveNum(0);
						supplierLimitTicketService.save(slt);
						
						List<ProductSpecificationsImage> imgs = productSpecificationsImageService
								.findlistByProductSpecificationsid(Long.valueOf(productSpecifications.getId()));
						SupplierLimitTicketSku slts2 = new SupplierLimitTicketSku();
						slts2.setId(dbUtils.CreateID());
						slts2.setProductId(productSpecifications.getProductId());
						slts2.setProductName(pro1.getFullName());
						slts2.setLimitTicketId(slt.getId());
						slts2.setSkuId(productSpecifications.getId());
						slts2.setSkuNum(1);
						slts2.setItemValues(productSpecifications.getItemValues());
						slts2.setPrice(productSpecifications.getPrice());
						slts2.setSalePrice(productSpecifications.getInternalPurchasePrice());
						slts2.setTicket(productSpecifications.getMaxFucoin());
						slts2.setImage(imgs.get(0).getSource());
						supplierLimitTicketSkuService.save(slts2);
					}
				}
			}
		}else {
			for (ProductSpecifications sku : skuList) {
				SupplierLimitTicketSku slts = new SupplierLimitTicketSku();
				slts.setSkuId(sku.getId());
				List<SupplierLimitTicketSku> supplierLimitTicketSkuList = supplierLimitTicketSkuService.selectByModel(slts);
				if(supplierLimitTicketSkuList!=null&&supplierLimitTicketSkuList.size()>0) {
					for (SupplierLimitTicketSku supplierLimitTicketSku : supplierLimitTicketSkuList) {
						SupplierLimitTicket supplierLimitTicket = supplierLimitTicketService.getById(supplierLimitTicketSku.getLimitTicketId());
						if(supplierLimitTicket.getTicketType()==1) {
							supplierLimitTicketService.removeById(supplierLimitTicket.getId());
							supplierLimitTicketSkuService.removeById(supplierLimitTicketSku.getId());
						}
					}
				}
			}
		}
		ProductHis ph =null;
		if(product.getStatus()==2) {//通过
			ph= new ProductHis();
			ph.setCreateDate(new Date());
			ph.setProductId(product.getProductId());
			ph.setProduct(pro);
			productHisService.save(ph);  //向mongodb中存储
		}else {
			if(pro1==null || (pro1.getIsMarketable()!=null && pro1.getIsMarketable()!=1)) {
				ph= new ProductHis();
				ph.setCreateDate(new Date());
				ph.setProductId(product.getProductId());
				ph.setProduct(pro);
				productHisService.save(ph);  //向mongodb中存储
			}
		}
		
		return checkResult;
	}
	
	/**
	 * 功能说明：商品管理强制下架的操作
	 *
	 * @param pojo
	 * @return
	 */
	@RequestMapping(value = "doManageCheck", method = RequestMethod.POST)
	@ResponseBody
    public Integer doManageCheck(HttpServletRequest request, Product product) {
		String opinion = request.getParameter("opinion");//意见
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;

		//删除旧数据
		List<ProductThirdPrice> oldptpList = ptpService.selectByProductId(product.getId());//通过商品id获取所有的第三方价格
		for (ProductThirdPrice productThirdPrice : oldptpList) {
			ptpService.removeById(productThirdPrice.getId());
		}
		
		String ptp2 = request.getParameter("ptp");
		if (!StringUtils.isEmpty(ptp2)) {
			List<ProductThirdPrice> ptpList = JSON.parseArray(ptp2, ProductThirdPrice.class);
			if(ptpList!=null && !ptpList.isEmpty()){
				for (ProductThirdPrice ptp : ptpList) {
					if(!StringUtils.isEmpty(ptp.getItemUrl().trim())){//链接非空
						ptp.setConfrimDate(new Date());
						ptp.setLastPrice(ptp.getPrice());
						ptp.setProductId(product.getId());
						ptp.setUpdateDate(new Date());
						if(ptp.getId() == null){
							ptp.setId(dbUtils.CreateID());
						}
						ptp.setUrlStatus(1);
						ptpService.saveProductThirdPrice(ptp);
					}
				}
			}
 		}
		
		Integer checkResult=null;//这里的赋初值要注意
		//如果是做强制下架操作就记录
		if(product.getIsMarketable()!=null && product.getIsMarketable()==-2){ //ismaketable来决定正式表的上下架操作
			if (obj != null) {
				//记录审核表
				CheckOpinion co = new CheckOpinion();
				co.setId(dbUtils.CreateID());
				co.setUsername(user.getName());
				co.setCheckId(product.getId());
				//co.setResult(product.getStatus());	
				co.setResult(-2);//商品管理，强制下架肯定是审核不通过	
				co.setOpinion(opinion);
				co.setTime(new Date());
				co.setType(0);
				co.setUserId(user.getId());
				supplierService.saveCheckOpinion(co);
			}
			//审核通过 修改  t_product 中字段 status
			//Integer checkResult = productService.check(product);
			checkResult =productFacade.forceselloff(product); //强制下架
		} else {
			checkResult=1;
		}
		return checkResult;
	}

	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("roles", roles);
		if(!isLeander(user.getId()) && !isBussiness(roles)) {
			params.put("managerId", user.getId());
		}
		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
		
//		String status = (String)params.get("status");
//		if(!StringUtils.isEmpty(status)) {
//			if("-10".equals(status)) {
//				params.put("isMarketable", "-10");
//				params.remove("status");
//			} else {
//				params.remove("isMarketable");
//			}
//		}

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("商品sku一览"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("商品id");
        headers.add("品牌");
        headers.add("分类（一级）");
        headers.add("分类（二级）");
        headers.add("分类（三级）");
        headers.add("商品标题");
        headers.add("商品副标题");
        headers.add("所属供应商");
        headers.add("招商经理");
        headers.add("销售区分");
        headers.add("特省理由");
        headers.add("锁定");
        headers.add("上下架");
        headers.add("上线标识");
        headers.add("状态");
        headers.add("创建时间");
        headers.add("内部价");
        headers.add("sku电商价");
        headers.add("sku内购价");
        headers.add("sku库存");
        headers.add("sku内购券");
        headers.add("sku规格信息");
                
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
        PageInfo page = null;
		if("2".equals(params.get("status"))){
			page = productService.findList(params);
		}else{
			page = apprProductService.findList(params);
		}	
		
		Map<Long,ProductCategoryVo> mapRoot = new HashMap<Long,ProductCategoryVo>();
		Map<Long,ProductCategoryVo> mapPc = new HashMap<Long,ProductCategoryVo>();
		Map<Long,ProductBrand> mapBrand = new HashMap<Long,ProductBrand>();
		Map<Long,Supplier> mapSupplier = new HashMap<Long,Supplier>();
		for (Object o : page.getList()) {
			Product p = (Product)o;
			if(!mapSupplier.containsKey(p.getSupplierId())) {
				mapSupplier.put(p.getSupplierId(), supplierService.findByid(p.getSupplierId()));
			}
			Supplier s = mapSupplier.get(p.getSupplierId());
			
			if(!mapBrand.containsKey(p.getBrandId())) {
				Map<String, Object> bmap = new HashMap<String, Object>();
				bmap.put("id", p.getBrandId());
				List<ProductBrand> brands= productBrandMapper.findByMap(bmap);

				if(brands!=null && !brands.isEmpty()) {
					mapBrand.put(p.getBrandId(), brands.get(0));
				}
			}
			ProductBrand brand=mapBrand.get(p.getBrandId());
			
			if(!mapPc.containsKey(p.getCategoryId())) {
				mapPc.put(p.getCategoryId(), productCategoryService.selectById(p.getCategoryId()));
			}
			ProductCategoryVo pc = mapPc.get(p.getCategoryId());

			ProductCategoryVo rootpc = null;
			if(pc!=null){
				if(!mapRoot.containsKey(pc.getRootId())) {
					mapRoot.put(pc.getRootId(), productCategoryService.selectById(pc.getRootId()));
				}
				
				rootpc=mapRoot.get(pc.getRootId());
			}
			
			p.setProductSpecificationslist(productSpecificationsService.findlistByProductid(p.getId()));
			for (ProductSpecifications sku : p.getProductSpecificationslist()) {

				currentRow++;
				int col=0;
	            row = sheet.createRow(currentRow); 
	            //ProductVO pv= (ProductVO)p;
	            //headers.add("商品id");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
	            //headers.add("品牌");
	            row.createCell(col++).setCellValue(brand==null?"":(StringUtils.isEmpty(brand.getName())?brand.getNameEn():brand.getName())); //商家
	            //headers.add("分类（一级）");
	            row.createCell(col++).setCellValue(rootpc==null?"":rootpc.getName());
	            //headers.add("分类（二级）");
	            row.createCell(col++).setCellValue(pc==null?"":pc.getParentName()); 
	            //headers.add("分类（三级）");
	            row.createCell(col++).setCellValue(pc==null?"":pc.getName()); 
	            //headers.add("商品标题");
	            row.createCell(col++).setCellValue(p.getFullName()); //商家
	            //headers.add("商品副标题");
	            row.createCell(col++).setCellValue(p.getName());
	            //headers.add("所属供应商");
	            if(p instanceof ProductVO) {
		            row.createCell(col++).setCellValue(((ProductVO)p).getSupplierName());
	            } else if(p instanceof ApprProductVO) {
		            row.createCell(col++).setCellValue(((ApprProductVO)p).getSupplierName());
	            } else {
		            row.createCell(col++).setCellValue("");
	            }
	            //headers.add("招商经理");
	            row.createCell(col++).setCellValue(s==null?"":s.getManagerName());
	            //headers.add("销售区分");
	            switch(p.getSaleKbn()==null?0:p.getSaleKbn()){
	            case 1:
	            	row.createCell(col++).setCellValue("特省"); //商家
	            	break;
	            case 2:
	            	row.createCell(col++).setCellValue("换领"); //商家
	            	break;
	            case 4:
	            	row.createCell(col++).setCellValue("专享"); //商家
	            	break;
	            case 5:
	            	row.createCell(col++).setCellValue("试用"); //商家
	            	break;
            	default:
	            	row.createCell(col++).setCellValue(""); //商家
	            	break;
	            }
	            //headers.add("特省");
	            if(p.getSaleKbn() == 1 || p.getSaleKbn() == 2 || p.getSaleKbn() == 5) {
		            row.createCell(col++).setCellValue(p.getSaleNote());
	            } else {
		            row.createCell(col++).setCellValue("");
	            }
	            //headers.add("锁定");
	            if(p.getLocked() == 1) {
		            row.createCell(col++).setCellValue(p.getLockReason());
	            } else {
		            row.createCell(col++).setCellValue("");
	            }
	            //headers.add("上下架");
	            if(p.getIsMarketable() == null) {
	            	p.setIsMarketable(0);
	            }
	            switch(p.getIsMarketable()){
		            case 1:
		            	row.createCell(col++).setCellValue("上架"); //商家
		            	break;
		            case -1:
		            case -2:
		            	row.createCell(col++).setCellValue("下架"); //商家
		            	break;
		            case -10:
		            	row.createCell(col++).setCellValue("删除"); //商家
		            	break;
	            	default:
		            	row.createCell(col++).setCellValue(""); //商家
		            	break;
	            }
	            if(p.getSelfType() == null) {
	            	p.setSelfType(-1);
	            }
				switch (p.getSelfType()) {
					case 0:
					case 3:
						row.createCell(col++).setCellValue("线上销售"); // 商家
						break;
					case 1:
						row.createCell(col++).setCellValue("线下销售"); // 商家
						break;
					case 2:
						row.createCell(col++).setCellValue("延时上线"); // 商家
						break;
					default:
						row.createCell(col++).setCellValue(""); // 商家
						break;
				}
	            
	            //headers.add("状态");
	            switch(p.getIsMarketable()){
		            case 1:
		            	row.createCell(col++).setCellValue("待审核"); //商家
		            	break;
		            case 2:
		            	row.createCell(col++).setCellValue("审核通过"); //商家
		            	break;
		            case -1:
		            	row.createCell(col++).setCellValue("审核未通过"); //商家
		            	break;
	            	default:
		            	row.createCell(col++).setCellValue(""); //商家
		            	break;
	            }
	            //headers.add("创建时间");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(DateUtils.formatDate(p.getCreateDate(),"yyyy-MM-dd")); //创建时间
	            //headers.add("内部价");
	            if(p.getWelfarePrice() != null) {
		            row.createCell(col++).setCellValue(p.getWelfarePrice().doubleValue());
	            } else {
		            row.createCell(col++).setCellValue("");
	            }
	            if(sku.getPrice()!=null) {
		            //headers.add("sku售价");
		            row.createCell(col++).setCellValue(sku.getPrice().doubleValue());
		            //headers.add("sku内购价");
		            row.createCell(col++).setCellValue(sku.getPrice().subtract(sku.getMaxFucoin()).doubleValue());
		            //headers.add("sku库存");
		            row.createCell(col++).setCellValue(sku.getStock()==null?0:sku.getStock());
		            //headers.add("sku内购券");
		            row.createCell(col++).setCellValue(sku.getMaxFucoin().doubleValue());
	            }
	            //headers.add("sku规格信息");
	            row.createCell(col++).setCellValue(sku.getItemValues());
	            
			}
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}

	private boolean isBussiness(List<SysRole> roles) {
		for (SysRole sysRole : roles) {
			if(sysRole.getId().equals(-15L)) {
				return true;
			}
		}
		return false;
	}
	
	private String makePtpTr2(List<ProductThirdPrice> ptpList) {
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<ptpList.size();i++) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("<input id=\"ptp_id" + i + "\" type=\"hidden\" value=\"" + (ptpList.get(i).getId()==null?"":ptpList.get(i).getId()) + "\">");
			sb.append("<select class=\"third\" id=\"ptp_thirdType"+i+"\">");
			sb.append("<option value=\"jd\"" + ("jd".equals(ptpList.get(i).getThirdType())?" selected":"") +">京东</option>");
			sb.append("<option value=\"tmall\"" + ("tmall".equals(ptpList.get(i).getThirdType())?" selected":"") +">天猫</option>");
			sb.append("<option value=\"taobao\"" + ("taobao".equals(ptpList.get(i).getThirdType())?" selected":"") +">淘宝</option>");
			sb.append("</select>");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<input class=\"third\" id=\"ptp_itemUrl" + i + "\" type=\"url\" value=\"" + (ptpList.get(i).getItemUrl()==null?"":ptpList.get(i).getItemUrl()) + "\" style=\"width:98%\">");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<input class=\"third\" id=\"ptp_price" + i + "\" type=\"price\" value=\"" + (ptpList.get(i).getPrice()==null?"":ptpList.get(i).getPrice()) + "\"");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(ptpList.get(i).getLastPrice()==null?"":ptpList.get(i).getLastPrice());
			sb.append("</td>");
			sb.append("<td>");
			if(ptpList.get(i).getUrlStatus() == null) {
			} else if(ptpList.get(i).getUrlStatus() == 1) {
				sb.append("正常");
			} else if(ptpList.get(i).getUrlStatus() == -2) {
				sb.append("无法解析");
			} else if(ptpList.get(i).getUrlStatus() == -1) {
				sb.append("无法访问");
			}
			sb.append("</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}
	private String makePtp(List<ProductSpecifications> skus,List<ProductSpecificationValue> pv1,List<ProductSpecificationValue> pv2,Long productId) {
		StringBuilder sb= new StringBuilder();
		for (ProductSpecifications sku : skus) {
			
			sb.append("<tr class='attrValue_class'>");
			sb.append("<input type=\"hidden\" value=" + sku.getItemValues() + " name=\"itemValues\">");
			ProductThirdPrice ptp = ptpService.selectByProductIdAndItemValues(productId,sku.getItemValues());
			sku.setItemValues(getSpecificationValue(sku, pv1, pv2));
			sb.append("<td class='center'>").append(sku.getItemValues()).append("</td>");//规格值
			sb.append("<td class='center'>").append(sku.getPrice()).append("</td>");//市场价
			sb.append("<td class='center'>").append(sku.getInternalPurchasePrice()).append("</td>");//内购价
			
			sb.append("<input id=\"id\" type=\"hidden\" name=\"id\" value=\""
					+ (ptp.getId() == null ? "" : ptp.getId()) + "\">");
			sb.append("<td class='center'>");
			sb.append("<select class=\"third\" id=\"thirdType\" name=\"thirdType\">");
			sb.append("<option value=\"jd\"" + ("jd".equals(ptp.getThirdType()) ? " selected" : "")
					+ ">京东</option>");
			sb.append("<option value=\"tmall\"" + ("tmall".equals(ptp.getThirdType()) ? " selected" : "")
					+ ">天猫</option>");
			sb.append("<option value=\"taobao\"" + ("taobao".equals(ptp.getThirdType()) ? " selected" : "")
					+ ">淘宝</option>");
			sb.append("</select>");
			sb.append("</td>");
			sb.append("<td class='center'>");
			sb.append("<input class=\"third\" id=\"ptp_itemUrl\" name=\"itemUrl\" type=\"url\" value=\""
					+ (ptp.getItemUrl() == null ? "" : ptp.getItemUrl())
					+ "\" style=\"width:100%\">");
			sb.append("</td>");
			sb.append("<td class='center'>");
			sb.append("<input class=\"third\" id=\"ptp_price\" name=\"price\" type=\"price\" value=\""
					+ (ptp.getPrice() == null ? "" : ptp.getPrice()) + "\">");
			sb.append("</td>");
			sb.append("<td class='center'>");
			sb.append(ptp.getLastPrice() == null ? "" : ptp.getLastPrice());
			// sb.append(ptp.getLastPrice()==null?"":ptpList.get(i).getLastPrice());
			sb.append("</td>");
			sb.append("<td class='center'>");
			if (ptp.getUrlStatus() == null) {
			} else if (ptp.getUrlStatus() == 1) {
				sb.append("正常");
			} else if (ptp.getUrlStatus() == -2) {
				sb.append("无法解析");
			} else if (ptp.getUrlStatus() == -1) {
				sb.append("无法访问");
			}
			sb.append("</td>");
			sb.append("</tr>");
			}
		//}
		return sb.toString();
	}

	private String makeSpecTable(int type,List<ProductSpecifications> skus,int specCnt,String kigaku1,List<ProductSpecificationValue> pv1,String kigaku2,List<ProductSpecificationValue> pv2,Map<Long,List<ProductSpecificationsImage>> imgs,Product old){
		StringBuilder sb = new StringBuilder();
		sb.append("<table id='parameterTable' class='table table-striped table-bordered table-hover nomargin-bottom'>");
		sb.append("<thead><tr>");
		sb.append("<th class='center'>主图</th><th class='center'>").append(kigaku1).append("</th>");
		if (specCnt != 1) {
			sb.append("<th class='center'>").append(kigaku2).append("</th>");
		}
		sb.append("<th class='center'>折扣</th><th class='center'>电商价</th><th class='center'>内购价</th><th class='center'>库存</th><th class='center'>内购券</th><th class='center'>起售数量</th><th class='center'>skuId</th>");
		sb.append("</tr></thead><tbody><tr></tr>");
		int skuConut = 0;
		for (ProductSpecificationValue v1 : pv1) {
			if(type == 0){
				if (specCnt == 1) {
					
					ProductSpecifications sku = null;
					for (ProductSpecifications i : skus) {
						if (i.getItemids().equals(v1.getId().toString())) {
							sku = i;
							break;
						}
					}
					
					if (sku != null) {
						sb.append("<tr class='attrValue_class'>");
						sb.append("<td class='center'>");
						sb.append("<div class='uploadimg_list").append(skuConut).append("'>");
						sb.append("<ul class='gbin1-list'>");
						List<ProductSpecificationsImage> ls = imgs.get(sku.getId());
						if (ls != null) {
							for (ProductSpecificationsImage img : ls) {
								sb.append("<li>");
								sb.append("<img data-id='").append(img.getId()).append("' width='80px' height='80px' src='").append(img.getSource())
									.append("' /><span style='width:10px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
								sb.append("</li>");
							}
						}
						sb.append("</ul>");
						sb.append("</div>");
						sb.append("</td>");
						sb.append("<td class='center'>").append(v1.getSpecificationValue()).append("</td>");
						sb.append(getSkuPrice5td(sku, old));
						sb.append("<td class='center'>").append(sku.getId()).append("</td>");
						sb.append("</tr>");
					}
				} else {
					
					List<ProductSpecifications> lsSku = new ArrayList<ProductSpecifications>();
					for (ProductSpecificationValue v2 : pv2) {
						for (ProductSpecifications s : skus) {
							if (s.getItemids().equals(v1.getId() + "," + v2.getId())
									|| s.getItemids().equals(v2.getId() + "," + v1.getId())) {
								lsSku.add(s);
								break;
							}
						}
					}
					
					// img id合并
					String imgIds[] = {"","","","",""};
					for (ProductSpecifications sku2 : lsSku) {
						List<ProductSpecificationsImage> ls = imgs.get(sku2.getId());
						if(ls!=null) {
							for(int i=0;i<5 && i<ls.size();i++) {
								imgIds[i] += ls.get(i).getId()+ "_";
							}
						}
					}
					boolean isFirst = true;
					for (ProductSpecificationValue v2 : pv2) {
						ProductSpecifications sku = null;
						for (ProductSpecifications s : skus) {
							if (s.getItemids().equals(v1.getId() + "," + v2.getId())
									|| s.getItemids().equals(v2.getId() + "," + v1.getId())) {
								sku = s;
								break;
							}
						}
						
						if (sku != null) {
							sb.append("<tr class='attrValue_class'>");
							if (isFirst) {
								sb.append("<td class='center' rowspan='" + lsSku.size() + "'>");
								sb.append("<div class='uploadimg_list").append(skuConut).append("'>");
								sb.append("<ul class='gbin1-list'>");
								List<ProductSpecificationsImage> ls = imgs.get(sku.getId());
								if (ls != null) {
									int index=0;
									for (ProductSpecificationsImage img : ls) {
										sb.append("<li>");
										sb.append("<img data-id='").append(imgIds[index]).append("' width='80px' height='80px' src='").append(img.getSource())
											.append("' /><span style='width:10px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
										sb.append("</li>");
										
										index++;
										if(index==5) index=0;
									}
								}
								sb.append("</ul>");
								sb.append("</div>");
								sb.append("</td>");
								
								sb.append("<td class='center' rowspan='" + lsSku.size() + "'>")
								.append(v1.getSpecificationValue()).append("</td>");
							}
							sb.append("<td class='center'>").append(v2.getSpecificationValue()).append("</td>");
							sb.append(getSkuPrice5td(sku, old));
							sb.append("<td class='center'>").append(sku.getId()).append("</td>");
							sb.append("</tr>");
							
							isFirst = false;
						}
					}
				}
			}else{
				if (specCnt == 1) {
					
					ProductSpecifications sku = null;
					for (ProductSpecifications i : skus) {
						if (i.getItemids().equals(v1.getId().toString())) {
							sku = i;
							break;
						}
					}
					
					if (sku != null) {
						sb.append("<tr class='attrValue_class'>");
						sb.append("<td class='center'>");
						sb.append("<div class='uploadimg_list").append(skuConut).append("'>");
						sb.append("<ul class='gbin1-list'>");
//						int i =0;
						List<ProductSpecificationsImage> ls = imgs.get(sku.getId());
						if (ls != null) {
							for (ProductSpecificationsImage img : ls) {
								sb.append("<li>");
								sb.append("<img data-id='").append(img.getId()).append("' name='loadimage' width='80px' height='80px' src='").append(img.getSource())
									.append("' /><span style='width:10px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
								sb.append("</li>");
//								i++;
							}
						}
						sb.append("</ul>");
						sb.append("</div>");
						sb.append("</td>");
						sb.append("<td class='center'>").append(v1.getSpecificationValue()).append("</td>");
						sb.append(getSkuPrice5td(sku, old));
						sb.append("<td class='center'>").append(sku.getId()).append("</td>");
						sb.append("</tr>");
					}
				} else {
					
					List<ProductSpecifications> lsSku = new ArrayList<ProductSpecifications>();
					for (ProductSpecificationValue v2 : pv2) {
						for (ProductSpecifications s : skus) {
							if (s.getItemids().equals(v1.getId() + "," + v2.getId())
									|| s.getItemids().equals(v2.getId() + "," + v1.getId())) {
								lsSku.add(s);
								break;
							}
						}
					}
					

					// img id合并
					String imgIds[] = {"","","","",""};
					for (ProductSpecifications sku2 : lsSku) {
						List<ProductSpecificationsImage> ls = imgs.get(sku2.getId());
						if(ls!=null) {
							for(int i=0;i<5 && i<ls.size();i++) {
								imgIds[i] += ls.get(i).getId()+ "_";
							}
						}
					}
					
					boolean isFirst = true;
					for (ProductSpecificationValue v2 : pv2) {
						ProductSpecifications sku = null;
						for (ProductSpecifications s : skus) {
							if (s.getItemids().equals(v1.getId() + "," + v2.getId())
									|| s.getItemids().equals(v2.getId() + "," + v1.getId())) {
								sku = s;
								break;
							}
						}
						
						if (sku != null) {
							sb.append("<tr class='attrValue_class'>");
							if (isFirst) {
								sb.append("<td class='center' rowspan='" + lsSku.size() + "'>");
								sb.append("<div class='uploadimg_list").append(skuConut).append("'>");
								sb.append("<ul class='gbin1-list'>");
								List<ProductSpecificationsImage> ls = imgs.get(sku == null ? "&nbsp;" : sku.getId());
								if (ls != null) {
									int index=0;
									for (ProductSpecificationsImage img : ls) {
										sb.append("<li>");
										sb.append("<img data-id='").append(imgIds[index]).append("' name='loadimage' width='80px' height='80px' src='").append(img.getSource())
											.append("' /><span style='width:10px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
										sb.append("</li>");
										
										index++;
										if(index==5) index=0;
									}
								}
								sb.append("</ul>");
								sb.append("</div>");
								sb.append("</td>");
								
								sb.append("<td class='center' rowspan='" + lsSku.size() + "'>")
								.append(v1.getSpecificationValue()).append("</td>");
							}
							sb.append("<td class='center'>").append(v2.getSpecificationValue()).append("</td>");
							sb.append(getSkuPrice5td(sku, old));
							sb.append("<td class='center'>").append(sku.getId()).append("</td>");
							sb.append("</tr>");
							
							isFirst = false;
						}
					}
				}
			}
			skuConut++;
		}

		sb.append("</table>");
		return sb.toString();
	}
	
	private String getSkuPrice5td(ProductSpecifications sku, Product old) {
		if (sku == null) {
			return "<td class='center'>&nbsp;</td><td class='center'>&nbsp;</td><td class='center'>&nbsp;</td><td class='center'>&nbsp;</td><td class='center'>&nbsp;</td>";
		} else {
			ProductSpecifications osku = null;
			if (old != null) {
				for (ProductSpecifications obj : old.getProductSpecificationslist()) {
					//if (sku.getId().equals(obj.getId())) {
					//我们不能使用sku的id来进行比较，因为新的sku的id发生了改变，可以根据规格来比较
					if (sku.getItemValues().equals(obj.getItemValues())) {						
							osku = obj;
						break;
					}
				}
			}

			if (osku == null) {
				BigDecimal discount = BigDecimal.ZERO;
				if(NumberUtil.isGreaterZero(sku.getPrice())) {
					discount=sku.getPrice().subtract(sku.getMaxFucoin()).multiply(new BigDecimal(10)).divide(sku.getPrice(), 1, BigDecimal.ROUND_DOWN);
				}
				
				return "<td class='center'><font color=red>" + discount + "</font></td>"
						+ "<td class='center'><font color=red>" + sku.getPrice().doubleValue() + "</font></td>"
						+ "<td class='center'><font color=red>" + (sku.getPrice().subtract(sku.getMaxFucoin()).doubleValue()) + "</font></td>"
						+ "<td class='center'><font color=red>" + sku.getStock() + "</font></td>"
						+ "<td class='center'><font color=red>" + sku.getMaxFucoin().doubleValue() + "</font></td>"
						+"<input type='hidden' name='maxFucoin' value='"+ sku.getMaxFucoin().doubleValue()+"'>"
						+ "<td class='center'><font color=red>" + sku.getMinLimitNum() + "</font></td>";
			} else {
				StringBuilder sb= new StringBuilder();
				
				osku.setPrice(osku.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
				osku.setMaxFucoin(osku.getMaxFucoin().setScale(2, BigDecimal.ROUND_HALF_UP));
				sku.setPrice(sku.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
				sku.setMaxFucoin(sku.getMaxFucoin().setScale(2, BigDecimal.ROUND_HALF_UP));
				//折扣
				BigDecimal os = BigDecimal.ZERO;
				if(NumberUtil.isGreaterZero(osku.getPrice())) {
					os=osku.getPrice().subtract(osku.getMaxFucoin()).multiply(BigDecimal.TEN).divide(osku.getPrice(), 1, BigDecimal.ROUND_DOWN);
				}				
				
				BigDecimal ns = BigDecimal.ZERO;
				if(NumberUtil.isGreaterZero(sku.getPrice())) {
					ns=sku.getPrice().subtract(sku.getMaxFucoin()).multiply(BigDecimal.TEN).divide(sku.getPrice(), 1, BigDecimal.ROUND_DOWN);
				}
				
				int change = os.compareTo(ns);
				if(change == 0) {
					sb.append("<td class='center'>").append(os.doubleValue()).append(" --> ").append(ns.doubleValue()).append("</td>");
				} else if(change >0 ) {
					sb.append("<td class='center'><font color=green>").append(os.doubleValue()).append(" --> ").append(ns.doubleValue()).append("</font></td>");
				} else {
					sb.append("<td class='center'><font color=red>").append(os.doubleValue()).append(" --> ").append(ns.doubleValue()).append("</font></td>");
				}
				//价格
				change = osku.getPrice().compareTo(sku.getPrice());
				if(change == 0) {
					sb.append("<td class='center'>").append(osku.getPrice().setScale(2, BigDecimal.ROUND_DOWN).doubleValue()).append(" --> ").append(sku.getPrice().doubleValue()).append("</td>");
				} else if(change >0 ) {
					sb.append("<td class='center'><font color=green>").append(osku.getPrice().doubleValue()).append(" --> ").append(sku.getPrice().doubleValue()).append("</font></td>");
				} else {
					sb.append("<td class='center'><font color=red>").append(osku.getPrice().doubleValue()).append(" --> ").append(sku.getPrice().doubleValue()).append("</font></td>");
				}
				//内购价
				BigDecimal skuF = sku.getPrice().subtract(sku.getMaxFucoin());
				BigDecimal oskuF = osku.getPrice().subtract(osku.getMaxFucoin());
				change = oskuF.compareTo(skuF);
				if(change == 0) {
					sb.append("<td class='center'>").append(oskuF.doubleValue()).append(" --> ").append(skuF.doubleValue()).append("</td>");
				} else if(change >0 ) {
					sb.append("<td class='center'><font color=green>").append(oskuF.doubleValue()).append(" --> ").append(skuF.doubleValue()).append("</font></td>");
				} else {
					sb.append("<td class='center'><font color=red>").append(oskuF.doubleValue()).append(" --> ").append(skuF.doubleValue()).append("</font></td>");
				}
				
				//库存
				Integer oStock = osku.getStock()==null?0:osku.getStock();
				Integer nStock = sku.getStock()==null?0:sku.getStock();				
				change = oStock.compareTo(nStock);
				if(change == 0) {
					sb.append("<td class='center'>").append(oStock).append(" --> ").append(nStock).append("</td>");
				} else if(change >0 ) {
					sb.append("<td class='center'><font color=red>").append(oStock).append(" --> ").append(nStock).append("</font></td>");
				} else {
					sb.append("<td class='center'><font color=green>").append(oStock).append(" --> ").append(nStock).append("</font></td>");
				}
				//内购券
				change = osku.getMaxFucoin().compareTo(sku.getMaxFucoin());
				if(change == 0) {
					sb.append("<td class='center'>").append(osku.getMaxFucoin().doubleValue()).append(" --> ").append(sku.getMaxFucoin().doubleValue()).append("</td>");
					sb.append("<input type='hidden' name='maxFucoin' value='").append(sku.getMaxFucoin().doubleValue()).append("'>");
				} else if(change >0 ) {
					sb.append("<td class='center'><font color=red>").append(osku.getMaxFucoin().doubleValue()).append(" --> ").append(sku.getMaxFucoin().doubleValue()).append("</font></td>");
					sb.append("<input type='hidden' name='maxFucoin' value='").append(sku.getMaxFucoin().doubleValue()).append("'>");
				} else {
					sb.append("<td class='center'><font color=green>").append(osku.getMaxFucoin().doubleValue()).append(" --> ").append(sku.getMaxFucoin().doubleValue()).append("</font></td>");
					sb.append("<input type='hidden' name='maxFucoin' value='").append(sku.getMaxFucoin().doubleValue()).append("'>");
				}
				//起售
				//内购券
				if(osku.getMinLimitNum().compareTo(sku.getMinLimitNum()) == 0) {
					sb.append("<td class='center'>").append(osku.getMinLimitNum()).append(" --> ").append(sku.getMinLimitNum()).append("</td>");
				} else if(osku.getMinLimitNum() > sku.getMinLimitNum()) {
					sb.append("<td class='center'><font color=red>").append(osku.getMinLimitNum()).append(" --> ").append(sku.getMinLimitNum()).append("</font></td>");					
				} else {
					sb.append("<td class='center'><font color=green>").append(osku.getMinLimitNum()).append(" --> ").append(sku.getMinLimitNum()).append("</font></td>");
				}
				return sb.toString();
			}
		}
	}
	
	private String getFileNameForSave(HttpServletRequest request) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = "商品一览"+ DateUtils.formatDate(new Date(),"_yyyyMMdd") +".xls";
		String new_filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		String rtn = "filename=\"" + new_filename + "\"";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
				rtn = "filename=\"" + new_filename + "\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
	/**
	 * 设置电子卡券
	 * @param model
	 * @param id 商品id
	 * @param pname 商品名称
	 * @return
	 */
	@RequestMapping(value = "setDianzikajian")
	public String setDianzikajian(Model model,long id,String pname,HttpSession session){
		ProductECard PECard = new ProductECard();
		PECard.setProductId(id);
		List<ProductECard> pEcardList = productECardService.selectByModel(PECard);
		if(pEcardList==null || pEcardList.isEmpty()){
			PECard.setProductId(id);
			PECard.setProductName(pname);
			
		}else{
			PECard = pEcardList.get(0);
		}
		model.addAttribute("list",PECard);
		return "manager/product/set-ECard";
	}
	/**
	 * 删除电子卡券设置
	 * @param id 电子卡券设置id
	 */
	@RequestMapping(value = "delectECard")
	public void delectECard(Long id){
		if(id!=null){
			productECardService.removeById(id);
		}
	}
	/**
	 * 更改电子卡券设置
	 * @param productECard
	 */
	@RequestMapping(value = "updateCardse")
	public void updateCardse(ProductECard productECard, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		ProductECard pro = new ProductECard();
		if (productECard.getId() == null) {
			productECard.setCreateTime(new Date());
			productECard.setCreateBy(user.getName());
			productECardService.save(productECard);
		} else {
			pro.setId(productECard.getId());
			pro.setProductId(productECard.getProductId());
			pro.setProductName(productECard.getProductName());
			pro.setCardPage(productECard.getCardPage());
			pro.setCardPws(productECard.getCardPws());
			pro.setSendType(productECard.getSendType());
			pro.setCreateTime(new Date());
			pro.setCreateBy(user.getName());
			productECardService.update(pro);
		}
	}
	
	//修改图片位置
	private void sortImages(String id) {
		if(StringUtils.isEmpty(id)) return;
		Date now = new Date();
		String[] ids = id.split(",");
		int orders = 1;
		
		for (int i = 0; i < ids.length; i++) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orders", orders);
			param.put("updateDate", now);
			param.put("ids", ids[i].replace("_", ","));
			productSpecificationsImageService.updateImg(param);

			orders++;
			if(orders==6)orders=1;
		}
	}
	
}

