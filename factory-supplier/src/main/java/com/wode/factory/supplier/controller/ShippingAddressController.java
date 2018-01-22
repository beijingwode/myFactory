/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierAddress;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductService;
import com.wode.factory.supplier.facade.ShippingTemplateFacade;
import com.wode.factory.supplier.query.ShippingTemplateVo;
import com.wode.factory.supplier.service.ShippingFreeRuleService;
import com.wode.factory.supplier.service.ShippingTemplateRuleService;
import com.wode.factory.supplier.service.ShippingTemplateService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierAddressService;
import com.wode.factory.supplier.service.SupplierLogService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("shippingAddress")
public class ShippingAddressController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("supplierAddressService")
	private SupplierAddressService supplierAddressService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	@Autowired
	@Qualifier("productCategoryService")
	private ProductCategoryService productCategoryService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("shippingTemplateService")
	private ShippingTemplateService shippingTemplateService;
	
	@Autowired
	private ShippingTemplateFacade shippingTemplateFacade;
	@Autowired
	@Qualifier("supplierShippingTemplateRuleService")
	private ShippingTemplateRuleService shippingTemplateRuleService;
	@Autowired
	@Qualifier("supplierShippingFreeRuleService")
	private ShippingFreeRuleService shippingFreeRuleService;

	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;
	
	@Autowired
	private ProductService productService;
	
	public ShippingAddressController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 
	 * 进入发货地址管理页面
	 **/
	@RequestMapping(value="todeliver",method=RequestMethod.GET)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/dispatching/deliver");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Map<String,Object> reparm = new HashMap<String, Object>();
			reparm.put("supplierId", us.getSupplierId()); 
			List<SupplierAddress> supplierAddress = supplierAddressService.fetchSupplierAddress(reparm);
			mv.addObject("supplierAddress", supplierAddress);
			mv.addObject("size", supplierAddress==null?0:supplierAddress.size());
			
			List<ProductCategory> productCategoryList = productCategoryService.findRoot();
			mv.addObject("productCategoryList", productCategoryList);
			
		}
		
		return mv;
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,SupplierAddress supplierAddress) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("redirect:/shippingAddress/todeliver.html");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			result.setErrorCode("1000");
		} else {
			Map<String,Object> reparm = new HashMap<String, Object>();
			reparm.put("supplierId", us.getSupplierId());
			List<SupplierAddress> list = supplierAddressService.fetchSupplierAddress(reparm);
			if(supplierAddress.getId()!=null){
				for (SupplierAddress supplierAddress2 : list) {
					if(supplierAddress2.getId().equals(supplierAddress.getId())) {
						supplierAddress2.setAddress(supplierAddress.getAddress());
						supplierAddress2.setAid(supplierAddress.getAid());
						supplierAddress2.setAreaName(supplierAddress.getAreaName());
						supplierAddress2.setCityName(supplierAddress.getCityName());
						supplierAddress2.setComments(supplierAddress.getComments());
						supplierAddress2.setCompanyname(supplierAddress.getCompanyname());
						supplierAddress2.setName(supplierAddress.getName());
						supplierAddress2.setPhone(supplierAddress.getPhone());
						supplierAddress2.setPostcode(supplierAddress.getPostcode());
						supplierAddress2.setProvinceName(supplierAddress.getProvinceName());
						supplierAddress2.setTel(supplierAddress.getTel());
						
						supplierAddressService.update(supplierAddress2);	
						
						break;
					}
				}
			}else{
				if(list.size()==0){
					supplierAddress.setReturned(1);
					supplierAddress.setSend(1);
				} else {
					supplierAddress.setReturned(0);
					supplierAddress.setSend(0);
				}
				supplierAddress.setSupplierId(us.getSupplierId());
				supplierAddressService.save(supplierAddress);
				list.add(supplierAddress);
			}
			result.setErrorCode("0");
			result.setMsgBody(list);
		}
		mv.addObject("result", result);
				
		return mv;
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="ajaxsave")
	public ModelAndView ajaxsave(HttpServletRequest request,HttpServletResponse response,SupplierAddress supplierAddress) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			result.setErrorCode("1000");
		} else {
			Map<String,Object> reparm = new HashMap<String, Object>();
			reparm.put("supplierId", us.getSupplierId());
			List<SupplierAddress> list = supplierAddressService.fetchSupplierAddress(reparm);
			if(supplierAddress.getId()!=null){
				java.lang.Long id = new java.lang.Long(request.getParameter("id"));
				supplierAddress = supplierAddressService.getById(id);
				supplierAddress.setSupplierId(supplierAddress.getSupplierId());
				supplierAddress.setSend(supplierAddress.getSend());
				supplierAddress.setReturned(supplierAddress.getReturned());
				supplierAddressService.update(supplierAddress);		
			}else{
				if(list.size()<20){
					supplierAddress.setReturned(list.size()==0?1:0);
					supplierAddress.setSend(list.size()==0?1:0);
					supplierAddress.setSupplierId(us.getSupplierId());
					supplierAddressService.save(supplierAddress);
				}
			}
			result.setErrorCode("0");
			result.setMsgBody(supplierAddress);
		}
		mv.addObject("result", result);
				
		return mv;
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SupplierAddress supplierAddress = supplierAddressService.getById(id);
		result.setErrorCode("0");
		result.setMsgBody(supplierAddress);
		return new ModelAndView("","result",result);
	}
	
	
	/**
	 * 设置默认地址
	 **/
	@RequestMapping(value="setdefault")
	public ModelAndView setdefault(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		String type = request.getParameter("type");
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			result.setErrorCode("10000");
		} else {
			Map<String,Object> reparm = new HashMap<String, Object>();
			reparm.put("supplierId", us.getSupplierId());
			if("1".equals(type)){
				reparm.put("send", 1);
				supplierAddressService.updatedefault(reparm);
			}else if("2".equals(type)){
				reparm.put("returned", 1);
				supplierAddressService.updatedefault(reparm);
			}
			reparm.put("id", id);
			supplierAddressService.setdefault(reparm);
			result.setErrorCode("0");
		}
		mv.addObject(result);
		return mv;
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			result.setErrorCode("10000");
		} else {
			java.lang.Long id = new java.lang.Long(request.getParameter("id"));
			SupplierAddress supplierAddress = supplierAddressService.getById(id);
			supplierAddressService.removeById(id);
			Map<String, Object> map = new  HashMap<String, Object>();
			map.put("supplierId", us.getSupplierId());
			List<SupplierAddress> addresslist = supplierAddressService.fetchSupplierAddress(map);
			int size = 0;
			if(supplierAddress.getSend()==1&&addresslist!=null&&addresslist.size()>0){
				map.put("send", 1);
				map.put("id", addresslist.get(0).getId());
				supplierAddressService.setdefault(map);
				result.setKey(addresslist.get(0).getId().toString());
				result.setMessage("send");
				size++;
			}
			if(supplierAddress.getReturned()==1&&addresslist!=null&&addresslist.size()>0){
				map.put("returned", 1);
				map.put("id", addresslist.get(0).getId());
				supplierAddressService.setdefault(map);
				result.setKey(addresslist.get(0).getId().toString());
				result.setMessage("returned");
				size++;
			}
			result.setSize(size);
			result.setErrorCode("0");
		}
		mv.addObject(result);
		return mv;
	}
	
	/**
	 *ajax获取发货（退货）地址列表
	 **/
	@RequestMapping(value="ajaxGetAddresslist",method=RequestMethod.GET)
	public ModelAndView ajaxGetAddresslist(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			result.setErrorCode("10000");
		} else {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("supplierId", us.getSupplierId());
			List<SupplierAddress> addresslist = supplierAddressService.fetchSupplierAddress(map);
			result.setMsgBody(addresslist);
			result.setErrorCode("0");
		}
		mv.addObject(result);
		return mv;
	}
	

	/** 
	 * 进入发货地址管理页面
	 **/
	@RequestMapping(value="template_edit",method=RequestMethod.GET)
	public ModelAndView templateEdit(HttpServletRequest request,Long templateId) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/dispatching/template_edit");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			ShippingTemplate template =shippingTemplateService.getById(templateId);
			mv.addObject("template", template);
		}
		
		return mv;
	}
	/** 
	 * 进入全场包邮策略编辑页面
	 **/
	@RequestMapping(value="free_template_edit",method=RequestMethod.GET)
	public ModelAndView freetemplateEdit(HttpServletRequest request,Long supplierTemplateId) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/dispatching/free_template_edit");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			
			ShippingFreeRule record2 = new ShippingFreeRule();//设置包邮规则
			record2.setTemplateId(us.getSupplierId());
			mv.addObject("freelist", shippingFreeRuleService.selectByModel(record2));
			mv.addObject("supplierTemplateId", us.getSupplierId());
		}
		
		return mv;
	}
	
	/** 
	 * 进入发货地址管理页面
	 **/
	@RequestMapping(value="freight_templates")
	public ModelAndView freightTemplates(HttpServletRequest request,ShippingTemplateVo vo) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/dispatching/template_edit");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			vo.setIsAudit(null);
			vo.setSupplierId(us.getSupplierId());
			vo.setPageSize(5); //目前分页有问题
			PageInfo<ShippingTemplate> page =  shippingTemplateService.findPage(vo);
			//2017-4-28 11:40:19 增加判断如果模板不为空，就判断里面是否有version = 2 也就是新版的模板如果有就跳转新版修改页面不展示列表，如果没有走以前的逻辑
			if(!page.getList().isEmpty()){
				ShippingTemplate stv = new ShippingTemplate();
				stv.setSupplierId(us.getSupplierId());
				stv.setVersion(2);
				stv.setIsAudit(1);
				List<ShippingTemplate> list =  shippingTemplateService.selectByModel(stv);
				stv.setIsAudit(0);
				if(null == list || list.size() == 0) {
					list =  shippingTemplateService.selectByModel(stv);
				}
				
				List<ShippingTemplate> oldlist =shippingTemplateService.selectByModel(stv);
				if(null != oldlist && oldlist.size() > 0) {
					mv.addObject("oldShippingTemplate", shippingTemplateService.getById(oldlist.get(0).getId()) );
				}
				//判断是否有新的模板
				if(null != list && list.size() > 0){
					List<CheckOpinion> checkOptions = supplierService.getCheckOpinionListBySupplierId(Long.valueOf(list.get(0).getId()));	
					mv.addObject("checkOptions", checkOptions);
					mv.addObject("template", shippingTemplateService.getById(list.get(0).getId()));
					return mv;
				}else{
					
					mv.addObject("template", null);
				}
			}
			//没有模板数据，直接跳到添加画面
			if(page.getList().isEmpty()) {
				mv.addObject("template", null);
			} else {
				
				for (ShippingTemplate template : page.getList()) {

					ShippingTemplateRule record = new ShippingTemplateRule();//模板规则（计价方式，发货时间等）
					record.setTemplateId(template.getId());
					template.setRulelist(shippingTemplateRuleService.selectByModel(record)); //设置模板规则
					
					ShippingFreeRule record2 = new ShippingFreeRule();//设置包邮规则
					record2.setTemplateId(template.getId());
					template.setFreelist(shippingFreeRuleService.selectByModel(record2));
					
				}
			}
			Supplier supplier=supplierService.getById(us.getSupplierId());
 			mv.addObject("page", page);	
			mv.addObject("query", vo);
			mv.addObject("supplier", supplier);
			Double amount=supplier.getShippingFree();
			//定义一个常量
			Double number =8000000.00D;
			int flag=0;
			if(amount ==-1D){//如果不设置免邮模板
				flag=1;
			}else if(amount > number){ //如果设置免邮模板
				flag=2;
			}else {//如果设置全场满多少包邮
                flag=3;
		    }
			mv.addObject("flag", flag);

			ShippingFreeRule record2 = new ShippingFreeRule();//设置包邮规则
			record2.setTemplateId(supplier.getId());
			mv.addObject("freelist", shippingFreeRuleService.selectByModel(record2));
			int len =shippingFreeRuleService.selectByModel(record2).size();
			mv.addObject("len", len);//判定freelist的长度到jsp中判断freelist是否为空
			mv.addObject("supplierTemplateId", supplier.getId());
			
		}
		return mv;
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="ajaxTemplateOprate")
	@ResponseBody
	public ActResult<String> ajaxTemplateOprate(HttpServletRequest request,String oprate,Long templateId) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us == null) {
			return ActResult.fail("no login");
		} else {
			ActResult<String> rtn = null;
			if("copy".equals(oprate)) {
				return shippingTemplateFacade.copyTemplate(templateId, us.getUserName());
			} else if("delete".equals(oprate)) {
				return shippingTemplateFacade.deleteTemplate(templateId);
			}
		}
		return ActResult.fail("系统异常请刷新页面重试");
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="ajaxSaveTemplate")
	@ResponseBody
	public ActResult<String> ajaxSaveTemplate(HttpServletRequest request,ShippingTemplate template) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us == null) {
			return ActResult.fail("no login");
		} else {
			//设置供应商id
			template.setSupplierId(us.getSupplierId());
			//设置新版模板名称和新版版本标示,新版都是按照件数
			if(StringUtils.isNullOrEmpty(template.getVersion()) || template.getVersion().equals("1")){
				return ActResult.fail("只能添加或保存新版运费模板");
			}
			template.setName("商家运费模板");
			template.setVersion(2);
			template.setCountType("1");
			//如果添加的是新模板则要判断是否已经有了新模板
			ShippingTemplate stv = new ShippingTemplate();
			stv.setSupplierId(us.getSupplierId());
			stv.setVersion(2);
			stv.setIsAudit(template.getIsAudit());
			List<ShippingTemplate> list =  shippingTemplateService.selectByModel(stv);
			//判断是否有新的模板
			if(null != list && list.size() > 0  ){
				if(null == template.getId() ||! new Long(list.get(0).getId()).equals(new Long(template.getId()))){
					return ActResult.fail("已有新模板,请刷新页面后重试");
				}
			}
			////产品属性
			String[] areasNames = request.getParameterValues("areasName");
			String[] areasCodes = request.getParameterValues("areasCode");
			String[] firstCnts = request.getParameterValues("first_cnt");
			String[] firstPrices = request.getParameterValues("first_price");
			String[] plusCnts = request.getParameterValues("plus_cnt");
			String[] plusPrices = request.getParameterValues("plus_price");
			
			List<ShippingTemplateRule> rules = new ArrayList<ShippingTemplateRule>();
			for(int i=0;i<areasNames.length;i++) {
				ShippingTemplateRule rule = new ShippingTemplateRule();
				
				//计价方式 1:按件数/2:按重量/3:按体积'
				rule.setCountType(template.getCountType());
				//运送方式 1:快递/2:EMS/3:平邮
				rule.setSendType("1");
				//'排序'
				rule.setSort(i);
				//运送范围 城市或省名称 空表示全国 空格分隔
				rule.setAreasName(areasNames[i]);
				//运送范围 城市或省代码 0 表示全国 逗号分隔'
				rule.setAreasCode(areasCodes[i]);
				//首件
				rule.setFirstCnt(new BigDecimal(firstCnts[i]));
				//首件运费
				rule.setFirstPrice(new BigDecimal(firstPrices[i]));
				//续件
				rule.setPlusCnt(new BigDecimal(plusCnts[i]));
				//续建价格
				rule.setPlusPrice(new BigDecimal(plusPrices[i]));
				
				rules.add(rule);
			}	
			
            //获取包邮的条件
			String[] free_areasNames = request.getParameterValues("free_areasName");
			String[] free_areasCodes = request.getParameterValues("free_areasCode");
			String[] free_countTypeDess = request.getParameterValues("free_countTypeDes");
			String[] free_param1s = request.getParameterValues("free_param1");
			String[] free_param2s = request.getParameterValues("free_param2");
			List<ShippingFreeRule> frees = new ArrayList<ShippingFreeRule>();
			if(free_areasNames!=null){
				for(int i=0;i<free_areasNames.length;i++) {
					ShippingFreeRule rule = new ShippingFreeRule();
		
					//计价方式 1:按件数/2:按重量/3:按体积'
					rule.setCountType(template.getCountType());
					//运送方式 1:快递/2:EMS/3:平邮
					rule.setSendType("1");
					//'排序'
					rule.setSort(i);
					//运送范围 城市或省名称 空表示全国 空格分隔
					rule.setAreasName(free_areasNames[i]);
					//运送范围 城市或省代码 0 表示全国 逗号分隔'
					rule.setAreasCode(free_areasCodes[i]);
					//包邮条件
					rule.setCountTypeDes(free_countTypeDess[i]);
					//计价方式
					rule.setParam1(new BigDecimal(free_param1s[i]));
					//金额
					rule.setParam2(new BigDecimal(free_param2s[i]));
					
					frees.add(rule);
				}
			}
			// 判断商品是否修改过，如果没有修改则不生成新的运费模板
			if (shippingTemplateFacade.checkTemplateChange(template, rules, frees)) {
				// 判断是否有上架的商品，如果有则生成新的运费模板，没有则走原来修改的逻辑
				// 判断是否有上架的商品
				if (productService.queryIsMarketableBySupplier(us.getSupplierId())) {
					// 先把审核中的模板删掉，然后在增加
					//shippingTemplateFacade.deleteAuditTemplate(us.getSupplierId());
					//未审核的需要保留
					if(null == template.getIsAudit() || template.getIsAudit() == 0) {
					   template.setId(null);
					}
					template.setIsAudit(1);
					return shippingTemplateFacade.saveTemplate(template, rules, frees, us.getUserName());
				} else {
					//没有上架的商品，运费模板自动审核通过
					template.setIsAudit(0);
					shippingTemplateFacade.saveTemplate(template, rules, frees, us.getUserName());
					return ActResult.success("2");
				}
			} else {
				return ActResult.success("0");
			}
		}
	}
	
	/** 
	 * 保存全场包邮策略对象
	 **/
	@RequestMapping(value="ajaxSaveFreeTemplate")
	@ResponseBody
	public ActResult<String> ajaxSaveFreeTemplate(HttpServletRequest request,ShippingFreeRule FreetemplateRule) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us == null) {
			return ActResult.fail("no login");
		} else {
			//设置供应商id
			//FreetemplateRule.setTemplateId(us.getSupplierId());
            //获取包邮的条件
			String[] free_areasNames = request.getParameterValues("free_areasName");
			String[] free_areasCodes = request.getParameterValues("free_areasCode");
			String[] free_countTypeDess = request.getParameterValues("free_countTypeDes");
			String[] free_param1s = request.getParameterValues("free_param1");
			String[] free_param2s = request.getParameterValues("free_param2");
			List<ShippingFreeRule> frees = new ArrayList<ShippingFreeRule>();
			if(free_areasNames!=null){
				for(int i=0;i<free_areasNames.length;i++) {
					ShippingFreeRule rule = new ShippingFreeRule();
					//计价方式 1:按件数
					rule.setCountType("1");
					//运送方式 1:快递/2:EMS/3:平邮
					rule.setSendType("1");
					//'排序'
					rule.setSort(i);
					//运送范围 城市或省名称 空表示全国 空格分隔
					rule.setAreasName(free_areasNames[i]);
					//运送范围 城市或省代码 0 表示全国 逗号分隔'
					rule.setAreasCode(free_areasCodes[i]);
					//包邮条件
					rule.setCountTypeDes(free_countTypeDess[i]);
					//计价方式
					rule.setParam1(new BigDecimal(free_param1s[i]));
					//金额
					rule.setParam2(new BigDecimal(free_param2s[i]));
					
					frees.add(rule);
					
				}
			}
			//删除旧包邮规则
			if(FreetemplateRule.getTemplateId() != null) {
				ShippingFreeRule record = new ShippingFreeRule();
				record.setTemplateId(FreetemplateRule.getTemplateId());
				List<ShippingFreeRule> oldRules = shippingFreeRuleService.selectByModel(record);
				for (ShippingFreeRule role: oldRules) {
					shippingFreeRuleService.removeById(role.getId());
				}
			}
			//保存模板包邮规则
			for (ShippingFreeRule role: frees) {
				role.setTemplateId(FreetemplateRule.getTemplateId());
				shippingFreeRuleService.saveOrUpdate(role);
			}
			return ActResult.success(FreetemplateRule.getTemplateId()+"");
		}
	}


	/**
	 * ajax设置全场包邮价格
	 **/
	@RequestMapping(value="setShippingFree")
	public ModelAndView apprTransfer(HttpServletRequest request,BigDecimal amount) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			if(amount==null) {
				supplierService.updateShippingFree(-1D, userModel.getSupplierId());
			} else {
				supplierService.updateShippingFree(amount.doubleValue(), userModel.getSupplierId());
			}
				
    		//日志
    		SupplierLog log = new SupplierLog();
    		log.setUserId(userModel.getId());
    		log.setUsername(userModel.getUserName());
    		log.setAct("商家设置全场包邮("+amount+")");
    		log.setTime(new Date());
    		log.setResult("success");
    		supplierLogService.save(log);
			result.setErrorCode("0");
			
			mv.addObject("result", result);
		}
		return mv;
	}
	
	/** 
	 * 查询商家运费模板策略
	 **/
	@RequestMapping(value="ajaxGetShippingTemplate")
	@ResponseBody
	public ActResult<ShippingTemplate> ajaxGetShippingTemplate(HttpServletRequest request){
		ActResult<ShippingTemplate> result = new ActResult<ShippingTemplate>();
		result.setSuccess(false);
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
		} else {
			ShippingTemplate stv = new ShippingTemplate();
			stv.setSupplierId(userModel.getSupplierId());
			stv.setVersion(2);
			stv.setIsAudit(0);
			List<ShippingTemplate> list =  shippingTemplateService.selectByModel(stv);
			//判断是否有新的模板
			if(null != list && list.size() > 0){
				result.setSuccess(true);
				result.setData(shippingTemplateService.getById(list.get(0).getId()));
			}
		}
		
		return result;
	}
}

