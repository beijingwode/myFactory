                                                              /**
 * 
 */
package com.wode.factory.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtilEx;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.ApprCheckFacade;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Attachment;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.CheckReview;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ApprShopService;
import com.wode.factory.service.ApprSupplierService;
import com.wode.factory.service.CheckReviewService;
import com.wode.factory.service.SaleDurationParamService;
import com.wode.factory.service.SupplierCategoryService;
import com.wode.factory.service.SupplierDurationVoService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.vo.SupplierCheckVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysUserService;


/**
 * @author mkx
 *
 */
@Controller
@RequestMapping("appr")
public class ApprController {

	@Autowired
	private SupplierService supplierService;
	@Autowired
	SupplierCategoryService supplierCategoryService;
	@Autowired
	SaleDurationParamService saleDurationParamService;
	@Autowired
	SupplierDurationVoService supplierDurationVoService;
	@Autowired
	ApprSupplierService apprSupplierService;
	@Autowired
	ApprShopService apprShopService;
	@Autowired
	ApprCheckFacade apprCheckFacade;
	@Autowired
	CheckReviewService checkReviewService;
	
	@Autowired
	private UserFactoryService userFactoryService;
	@Resource
	private SysUserMapper sysUserMapper;
	
//	@Autowired
//	private LogService logService;
	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;

	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	@Resource
	private SysUserService sysUserService;
	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;
	
	@Autowired
	private RedisUtilEx redisUtilEx;
	/////////////////////////////////////////////////招商分配///////////////////////////////////////////////////
	
	@RequestMapping("/emptySupplier")
	public String toSupplier(Model model,HttpSession session){
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(isLeander(user.getId())) {
			model.addAttribute("leader", "1");
		} else {
			model.addAttribute("leader", "0");
		}
		return "manager/appr/supplier/empty_supplier_base";
	}		
	
	@RequestMapping("/emptySupplierlist")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model) {

		if("supplier".equals(params.get("managerId"))) {
			params.remove("managerId");
			model.addAttribute("page", supplierService.findSupplierCount(params));
			model.addAttribute("type", "supplier");
		} else {
			model.addAttribute("page", apprSupplierService.findApprSupplierEmpty(params));
			model.addAttribute("type", "appr");
		}
		return "manager/appr/supplier/empty_supplier_list";
	}
	
	@RequestMapping(value = "/supplier/toSetManager", method = RequestMethod.POST)
	public String toSetManager(Model m,Long id){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		m.addAttribute("list", list);
		m.addAttribute("id", id);
		
		return "manager/appr/supplier/set_manager";
	}
	
	@RequestMapping("/supplier/setManager")
	@ResponseBody
	public int setManager(String manager, Long id,HttpSession session) {
		
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		String[] m = manager.split(",");
		ApprSupplier appr = apprSupplierService.getById(id);
		if(obj !=null && appr!=null ){
			// 设置appr 招商经理
			appr.setManagerId(NumberUtil.toLong(m[0]));
			if(m.length>2) {
				appr.setManagerName(StringUtils.isEmpty(m[2])?m[1]:m[2]);
			} else {
				appr.setManagerName(m[1]);
			}
			
			appr.setUpdateTime(new Date());
			appr.setUpdateUser(user.getId());
			appr.setUpdateName(user.getName());
			apprSupplierService.update(appr);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("supplierId", appr.getSupplierId());
			// 设置店铺appr  招商经理
			List<ApprShop> shops = apprShopService.findApprShopEmpty(params);
			for (ApprShop shop : shops) {
				shop.setManagerId(appr.getManagerId());
				shop.setManagerName(appr.getManagerName());
				
				shop.setUpdateTime(new Date());
				shop.setUpdateUser(user.getId());
				shop.setUpdateName(user.getName());
				apprShopService.update(shop);
			}
			
			// 设置商家  招商经理
			Supplier supplier = supplierService.findByid(appr.getSupplierId());
			if(supplier!=null) {
				supplier.setManagerId(appr.getManagerId());
				supplier.setManagerName(appr.getManagerName());
				
				supplierService.setManager(supplier);
			}
			return 1;
		} else {

			Supplier supplier = supplierService.findByid(id);
			if(supplier!=null) {
				// 设置商家  招商经理
				supplier.setManagerId(NumberUtil.toLong(m[0]));
				if(m.length>2) {
					supplier.setManagerName(StringUtils.isEmpty(m[2])?m[1]:m[2]);
				} else {
					supplier.setManagerName(m[1]);
				}
				supplierService.setManager(supplier);

				// 设置appr  招商经理
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("supplierId", supplier.getId());
				params.put("pageNum", 1);
				params.put("pageSize", 10);
				PageInfo<ApprSupplier> page = apprSupplierService.findApprSupplier(params);
				for (ApprSupplier appri : page.getList()) {
					appri.setManagerId(supplier.getManagerId());
					appri.setManagerName(supplier.getManagerName());
					appri.setUpdateTime(new Date());
					appri.setUpdateUser(user.getId());
					appri.setUpdateName(user.getName());
					apprSupplierService.update(appri);
				}

				// 设置店铺appr  招商经理
				List<ApprShop> shops = apprShopService.findApprShopEmpty(params);
				for (ApprShop shop : shops) {
					shop.setManagerId(supplier.getManagerId());
					shop.setManagerName(supplier.getManagerName());
					
					shop.setUpdateTime(new Date());
					shop.setUpdateUser(user.getId());
					shop.setUpdateName(user.getName());
					apprShopService.update(shop);
				}
			}
		}
		return 0;
	}
	/////////////////////////////////////////////////招商分配///////////////////////////////////////////////////
	

	/////////////////////////////////////////////////招商审核///////////////////////////////////////////////////
	@RequestMapping("/supplier/manager")
	public String toManager(Model model){
		return "manager/appr/supplier/manager_base";
	}		

	@RequestMapping("/supplier/managerList")
	public String managerList(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		if(!isLeander(user.getId())) {
			params.put("managerId", user.getId());
		}
		//params.put("status", 1);
		PageInfo pageInfo = apprSupplierService.findApprSupplier(params);
		model.addAttribute("page", pageInfo);
		return "manager/appr/supplier/manager_list";
	}
	
	@RequestMapping(value = "/supplier/toManagerCheck", method = RequestMethod.POST)
	public String toManagerCheck(Model model,Long id){

		//商家信息
		ApprSupplier appr = apprSupplierService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		//账单日(结算)类型信息
		List<SaleDurationParam> saleParam = this.saleDurationParamService.findAll();
		//查询账单日
		SupplierDuration sd = this.supplierDurationVoService.getById(id);
		if(sd==null) sd = this.supplierDurationVoService.getById(appr.getSupplierId());
		
		if(StringUtils.isNullOrEmpty(sd)){
			sd = new SupplierDuration();
			sd.setSaleDurationKey("103");
		}
		if(StringUtils.isNullOrEmpty(sd.getStartTime())){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 3);
			model.addAttribute("startTime", calendar.getTime());
		}
		
		model.addAttribute("supplierDuration", sd);
		model.addAttribute("saleParam", saleParam);
		model.addAttribute("checkList", checkList);
		model.addAttribute("supplier", appr);
				
		return "manager/appr/supplier/manager_check";
	}

	@RequestMapping("/supplier/managerCheck")
	@ResponseBody
	public ActResult doCheck(Long id,Integer status,String opinion,String bill_date,String bill_type, HttpServletRequest request){//HttpServletRequest request, Supplier pojo,String opinion
		ActResult  ar = new ActResult();
		HttpSession session = request.getSession();

			if(StringUtils.isEmpty(id)){
				ar.setMsg("id不能为空");
				ar.setSuccess(false);
				return ar;
			}
			if(StringUtils.isEmpty(status)){
				ar.setMsg("审核状态不能为空");
				ar.setSuccess(false);
				return ar;
			}

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			ApprSupplier appr = apprSupplierService.getById(id);
			
			SysUser user = (SysUser)obj;
			//审核
			try {

				appr.setManagerChkId(user.getId());
				
				appr.setSaleDurationKey(bill_type);
				appr.setStartTime(DateUtils.parseDate(bill_date));
				
				appr.setStatus(status);
				appr.setManagerChkDesc(opinion);
				appr.setManagerChkTime(new Date());
				appr.setUpdateTime(new Date());
				appr.setUpdateUser(user.getId());
				appr.setUpdateName(user.getName());
				
				apprSupplierService.update(appr);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//记录审核表
			CheckOpinion co = new CheckOpinion();
			//co.setId(db.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(id);
			co.setResult(status);
			co.setOpinion(opinion);
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			try {
				supplierService.saveCheckOpinion(co);
			} catch (Exception e) {
				ar.setMsg("记录审核表时失败");
				e.printStackTrace();
			}
			
			
			//审核通过
			if(status > 0) {
				this.sendMailToLawyer("supplier", opinion);
			} else if(status==-1) {
				/**
				 * 获取邮箱并发送邮件提醒商家审核结果
				 * */
				if(!StringUtils.isNullOrEmpty(appr.getToEmail())){
					this.emailUtil.sendSupplierCheckEmail(appr.getToEmail(), opinion, false);
				}
			}
		
		}		
		ar.setMsg("审核成功!");
		ar.setSuccess(true);
		return ar;
	}
	/////////////////////////////////////////////////招商审核///////////////////////////////////////////////////
	

	/////////////////////////////////////////////////法务审核///////////////////////////////////////////////////
	@RequestMapping("/supplier/lawyer")
	public String toLawyer(Model model){
		return "manager/appr/supplier/lawyer_base";
	}		

	@RequestMapping("/supplier/lawyerList")
	public String lawyerList(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {

		params.put("status", 2);
		PageInfo pageInfo = apprSupplierService.findApprSupplier(params);
		model.addAttribute("page", pageInfo);
		return "manager/appr/supplier/lawyer_list";
	}
	
	@RequestMapping(value = "/supplier/toLawyerCheck", method = RequestMethod.POST)
	public String toLawyerCheck(Model model,Long id){

		//商家信息
		ApprSupplier appr = apprSupplierService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		//账单日(结算)类型信息
		List<SaleDurationParam> saleParam = this.saleDurationParamService.findAll();
		
		String durationType = "月结";
		Date startTime = appr.getStartTime();
		for (SaleDurationParam saleDurationParam : saleParam) {
			if(saleDurationParam.getKey().equals(appr.getSaleDurationKey())) {
				durationType=saleDurationParam.getCaption();
			}
		}
		model.addAttribute("durationType", durationType);
		model.addAttribute("startTime", startTime);
		model.addAttribute("checkList", checkList);
		model.addAttribute("supplier", appr);
				
		return "manager/appr/supplier/lawyer_check";
	}

	@RequestMapping("/supplier/lawyerCheck")
	@ResponseBody
	public ActResult doLawyerCheck(Long id,Integer status,String opinion, HttpServletRequest request){//HttpServletRequest request, Supplier pojo,String opinion
		ActResult  ar = new ActResult();
		HttpSession session = request.getSession();

			if(StringUtils.isEmpty(id)){
				ar.setMsg("id不能为空");
				ar.setSuccess(false);
				return ar;
			}
			if(StringUtils.isEmpty(status)){
				ar.setMsg("审核状态不能为空");
				ar.setSuccess(false);
				return ar;
			}

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			ApprSupplier appr = apprSupplierService.getById(id);
			
			SysUser user = (SysUser)obj;
			//审核
			try {
				appr.setStatus(2+status);
				appr.setLawChkDesc(opinion);
				appr.setLawChkTime(new Date());
				appr.setLawChkId(user.getId());
				
				appr.setUpdateTime(new Date());
				appr.setUpdateUser(user.getId());
				appr.setUpdateName(user.getName());
				
				apprSupplierService.update(appr);
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			//记录审核表
			CheckOpinion co = new CheckOpinion();
			//co.setId(db.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(id);
			co.setResult(status);
			co.setOpinion(opinion);
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			try {
				supplierService.saveCheckOpinion(co);
			} catch (Exception e) {
				ar.setMsg("记录审核表时失败");
				e.printStackTrace();
			}		
					
			//审核通过
			if(status > 0) {
				//this.sendMailToBusiness("supplier", opinion);
				//审核
				appr.setStatus(4);
				appr.setBusChkId(user.getId());
				appr.setBusChkDesc(opinion);
				appr.setBusChkTime(new Date());
				
				appr.setUpdateTime(new Date());
				appr.setUpdateUser(user.getId());
				appr.setUpdateName(user.getName());

				apprCheckFacade.apprToSupplier(appr);
				
				if(!StringUtils.isNullOrEmpty(appr.getToEmail())){
					this.emailUtil.sendSupplierCheckEmail(appr.getToEmail(), null, true);
				}
				
			} else if(status==-1) {
				this.sendMailToManager(appr.getManagerId(), "supplier", false, opinion, appr.getComName());
			}
		}		
		ar.setMsg("审核成功!");
		ar.setSuccess(true);
		return ar;
	}
	/////////////////////////////////////////////////法务审核///////////////////////////////////////////////////

	@RequestMapping(value = "/supplier/toDetail", method = RequestMethod.POST)
	public String toSupplierDetail(Model model,Long id){

		//商家信息
		ApprSupplier appr = apprSupplierService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		//账单日(结算)类型信息
		List<SaleDurationParam> saleParam = this.saleDurationParamService.findAll();
		
		String durationType = "月结";
		Date startTime = appr.getStartTime();
		for (SaleDurationParam saleDurationParam : saleParam) {
			if(saleDurationParam.getKey().equals(appr.getSaleDurationKey())) {
				durationType=saleDurationParam.getCaption();
			}
		}
		model.addAttribute("durationType", durationType);
		model.addAttribute("startTime", startTime);
		model.addAttribute("checkList", checkList);
		model.addAttribute("supplier", appr);
				
		return "manager/appr/supplier/detail";
	}


	/////////////////////////////////////////////////运营审核///////////////////////////////////////////////////
	@RequestMapping("/supplier/business")
	public String toBusiness(Model model){
		return "manager/appr/supplier/business_base";
	}		

	@RequestMapping("/supplier/businessList")
	public String businessList(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {

		params.put("status", 3);
		PageInfo pageInfo = apprSupplierService.findApprSupplier(params);
		model.addAttribute("page", pageInfo);
		return "manager/appr/supplier/business_list";
	}
	
	@RequestMapping(value = "/supplier/toBusinessCheck", method = RequestMethod.POST)
	public String toBusinessCheck(Model model,Long id){

		//商家信息
		ApprSupplier appr = apprSupplierService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		//账单日(结算)类型信息
		List<SaleDurationParam> saleParam = this.saleDurationParamService.findAll();
		//查询账单日
		SupplierDuration sd = this.supplierDurationVoService.getById(id);

		String durationType = "月结";
		Date startTime = appr.getStartTime();
		for (SaleDurationParam saleDurationParam : saleParam) {
			if(saleDurationParam.getKey().equals(appr.getSaleDurationKey())) {
				durationType=saleDurationParam.getCaption();
			}
		}
		model.addAttribute("durationType", durationType);
		model.addAttribute("startTime", startTime);
		model.addAttribute("checkList", checkList);
		model.addAttribute("supplier", appr);
				
		return "manager/appr/supplier/business_check";
	}

	@RequestMapping("/supplier/businessCheck")
	@ResponseBody
	public ActResult doBusinessCheck(Long id,Integer status,String opinion, HttpServletRequest request){//HttpServletRequest request, Supplier pojo,String opinion
		ActResult  ar = new ActResult();
		HttpSession session = request.getSession();

			if(StringUtils.isEmpty(id)){
				ar.setMsg("id不能为空");
				ar.setSuccess(false);
				return ar;
			}
			if(StringUtils.isEmpty(status)){
				ar.setMsg("审核状态不能为空");
				ar.setSuccess(false);
				return ar;
			}

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			ApprSupplier appr = apprSupplierService.getById(id);
			
			SysUser user = (SysUser)obj;
			//审核
			appr.setStatus(3+status);
			appr.setBusChkId(user.getId());
			appr.setBusChkDesc(opinion);
			appr.setBusChkTime(new Date());
			
			appr.setUpdateTime(new Date());
			appr.setUpdateUser(user.getId());
			appr.setUpdateName(user.getName());
			
			if(3+status == 4) {
				apprCheckFacade.apprToSupplier(appr);
			} else {
				apprSupplierService.update(appr);
			}

			//记录审核表
			CheckOpinion co = new CheckOpinion();
			//co.setId(db.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(id);
			co.setResult(status);
			co.setOpinion(opinion);
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			try {
				supplierService.saveCheckOpinion(co);
			} catch (Exception e) {
				ar.setMsg("记录审核表时失败");
				e.printStackTrace();
			}

			//审核通过
			if(status > 0) {
				if(!StringUtils.isNullOrEmpty(appr.getToEmail())){
					this.emailUtil.sendSupplierCheckEmail(appr.getToEmail(), null, true);
				}
			} else if(status==-1) {
				this.sendMailToLawyer("supplier", opinion);
			}
		}		
		ar.setMsg("审核成功!");
		ar.setSuccess(true);
		return ar;
	}
	/////////////////////////////////////////////////运营审核///////////////////////////////////////////////////
	

	/////////////////////////////////////////////////招商审核///////////////////////////////////////////////////
	@RequestMapping("/shop/manager")
	public String toShopoManager(Model model){
		return "manager/appr/shop/manager_base";
	}		

	@RequestMapping("/shop/managerList")
	public String managerShopList(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(!isLeander(user.getId())) {
			params.put("managerId", user.getId());
		}
		//params.put("status", 1);
		PageInfo pageInfo = apprShopService.findApprShop(params);
		model.addAttribute("page", pageInfo);
		return "manager/appr/shop/manager_list";
	}
	
	@RequestMapping(value = "/shop/toManagerCheck", method = RequestMethod.POST)
	public String toManagerShopoCheck(Model model,Long id){

		//商家信息
		ApprShop appr = apprShopService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		
		Supplier s = supplierService.findByid(appr.getSupplierId());
		model.addAttribute("checkList", checkList);
		model.addAttribute("supplier", s);
		model.addAttribute("shop", appr);
		//经营类目信息
		List<ProductCategory> productCategoryList = supplierService.getProductCategoryListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//商家资质信息
		List<Attachment> attachmentList = supplierService.getAttachmentListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//品牌信息
		List<ProductBrand> productBrandList = supplierService.getProductBrandListBySupplierId(appr.getSupplierId(),appr.getShopId());
		model.addAttribute("productCategory", productCategoryList);
		model.addAttribute("attachment", attachmentList);
		model.addAttribute("productBrand", productBrandList);
		return "manager/appr/shop/manager_check";
	}

	@RequestMapping("/shop/managerCheck")
	@ResponseBody
	public ActResult doManagerShopCheck(@RequestBody SupplierCheckVo supplierCheck,HttpServletRequest request){//HttpServletRequest request, Supplier pojo,String opinion
		ActResult  ar = new ActResult();
		HttpSession session = request.getSession();

		if(StringUtils.isEmpty(supplierCheck.getId())){
			ar.setMsg("id不能为空");
			ar.setSuccess(false);
			return ar;
		}
		if(StringUtils.isEmpty(supplierCheck.getStatus())){
			ar.setMsg("审核状态不能为空");
			ar.setSuccess(false);
			return ar;
		}

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			ApprShop appr = apprShopService.getById(supplierCheck.getId());
			
			SysUser user = (SysUser)obj;
			//审核
			try {

				appr.setManagerChkId(user.getId());
								
				appr.setStatus(supplierCheck.getStatus());
				appr.setManagerChkDesc(supplierCheck.getOpinion());
				appr.setManagerChkTime(new Date());
				appr.setUpdateTime(new Date());
				appr.setUpdateUser(user.getId());
				appr.setUpdateName(user.getName());
				
				apprShopService.update(appr);
			} catch (Exception e) {
				e.printStackTrace();
			}
						

			//添加店铺对应分类的佣金比例
			List<SupplierCategory> listSupplierCate = supplierCheck.getCommission();
			for(SupplierCategory supplierCate:listSupplierCate){
				this.supplierCategoryService.updateCommissionRatio(supplierCate);
			}
			
			//记录审核表
			CheckOpinion co = new CheckOpinion();
			//co.setId(db.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(supplierCheck.getId());
			co.setResult(supplierCheck.getStatus());
			co.setOpinion(supplierCheck.getOpinion());
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			try {
				supplierService.saveCheckOpinion(co);
			} catch (Exception e) {
				ar.setMsg("记录审核表时失败");
				e.printStackTrace();
			}

			//审核通过
			if(supplierCheck.getStatus() > 0) {
				this.sendMailToLawyer("shop", supplierCheck.getOpinion());
			} else if(supplierCheck.getStatus()==-1) {
				/**
				 * 获取邮箱并发送邮件提醒商家审核结果
				 * */
				if(!StringUtils.isNullOrEmpty(appr.getToEmail())){
					this.emailUtil.sendSupplierCheckEmail(appr.getToEmail(), supplierCheck.getOpinion(), false);
				}
			}
		
		}		
		ar.setMsg("审核成功!");
		ar.setSuccess(true);
		return ar;
	}
	/////////////////////////////////////////////////招商审核///////////////////////////////////////////////////
	

	/////////////////////////////////////////////////法务审核///////////////////////////////////////////////////
	@RequestMapping("/shop/lawyer")
	public String toLawyerShop(Model model){
		return "manager/appr/shop/lawyer_base";
	}		

	@RequestMapping("/shop/lawyerList")
	public String lawyerShopList(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {

		params.put("status", 2);
		PageInfo pageInfo = apprShopService.findApprShop(params);
		model.addAttribute("page", pageInfo);
		return "manager/appr/shop/lawyer_list";
	}
	
	@RequestMapping(value = "/shop/toLawyerCheck", method = RequestMethod.POST)
	public String toLawyerShopCheck(Model model,Long id){

		//商家信息
		ApprShop appr = apprShopService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		model.addAttribute("checkList", checkList);
		model.addAttribute("shop", appr);

		Supplier s = supplierService.findByid(appr.getSupplierId());
		model.addAttribute("supplier", s);
		//经营类目信息
		List<ProductCategory> productCategoryList = supplierService.getProductCategoryListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//商家资质信息
		List<Attachment> attachmentList = supplierService.getAttachmentListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//品牌信息
		List<ProductBrand> productBrandList = supplierService.getProductBrandListBySupplierId(appr.getSupplierId(),appr.getShopId());
		for (ProductBrand productBrand : productBrandList) {
			CheckReview q = new CheckReview();
			q.setObjKey(productBrand.getId());
			List<CheckReview> ls = checkReviewService.selectByModel(q);
			if(ls != null && !ls.isEmpty()) {
				productBrand.setCheckMemo(ls.get(0).getMemo());
				productBrand.setCheckAlarmDate(ls.get(0).getAlarmDate());
			} else if (productBrand.getOldId() != null) {
				q.setObjKey(productBrand.getOldId());
				ls = checkReviewService.selectByModel(q);
				if(ls != null && !ls.isEmpty()) {
					productBrand.setCheckMemo(ls.get(0).getMemo());
					productBrand.setCheckAlarmDate(ls.get(0).getAlarmDate());
				}
			}
		}
		model.addAttribute("productCategory", productCategoryList);
		model.addAttribute("attachment", attachmentList);
		model.addAttribute("productBrand", productBrandList);
		
		return "manager/appr/shop/lawyer_check";
	}

	@RequestMapping("/shop/lawyerCheck")
	@ResponseBody
	public ActResult doLawyerShopCheck(Long id,Integer status,String opinion,String review, HttpServletRequest request){//HttpServletRequest request, Supplier pojo,String opinion
		ActResult  ar = new ActResult();
		HttpSession session = request.getSession();

		if(StringUtils.isEmpty(id)){
			ar.setMsg("id不能为空");
			ar.setSuccess(false);
			return ar;
		}
		if(StringUtils.isEmpty(status)){
			ar.setMsg("审核状态不能为空");
			ar.setSuccess(false);
			return ar;
		}

		String[] reviews = {};
		String memos="";
		if(!StringUtils.isEmpty(review)) {
			reviews = review.split("&__&");
		}
		List<CheckReview> ls = new ArrayList<CheckReview>();
		for (String string : reviews) {
			String[] crs = string.split("&##&");
			CheckReview n = new CheckReview();
			n.setObjKey(NumberUtil.toLong(crs[0]));
			n.setMemo(crs[1]);
			if(crs.length>2) {;
				n.setAlarmDate(TimeUtil.strToDate(crs[2] + " 00:00:00"));
			}
			ls.add(n);
			memos += " " + n.getMemo() + ";";
		}
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			ApprShop appr = apprShopService.getById(id);
			
			SysUser user = (SysUser)obj;
			//审核
			try {
				appr.setStatus(2+status);
				appr.setLawChkDesc(opinion);
				appr.setLawChkTime(new Date());
				appr.setLawChkId(user.getId());
				
				appr.setUpdateTime(new Date());
				appr.setUpdateUser(user.getId());
				appr.setUpdateName(user.getName());
				
				apprShopService.update(appr);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//记录审核表
			CheckOpinion co = new CheckOpinion();
			//co.setId(db.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(id);
			co.setResult(status);
			if(status > 0) {
				opinion += memos;
			}
			co.setOpinion(opinion);
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			try {
				supplierService.saveCheckOpinion(co);
								
			} catch (Exception e) {
				ar.setMsg("记录审核表时失败");
				e.printStackTrace();
			}		
			//审核通过
			if(status > 0) {
				checkReviewService.saveCheckReviews(id, ls, user.getName());
				
				//this.sendMailToBusiness("shop", opinion);

				appr.setStatus(4);
				appr.setBusChkId(user.getId());
				appr.setBusChkDesc(opinion);
				appr.setBusChkTime(new Date());
				
				appr.setUpdateTime(new Date());
				appr.setUpdateUser(user.getId());
				appr.setUpdateName(user.getName());
				
				apprCheckFacade.apprToShop(appr);
				
				if(!StringUtils.isNullOrEmpty(appr.getToEmail())){
					this.emailUtil.sendSupplierCheckEmail(appr.getToEmail(), null, true);
				}
				//清空缓存
				redisUtilEx.delKey("shop_getShopSettingById_["+appr.getOldId()+"]");
			} else if(status==-1) {
				this.sendMailToManager(appr.getManagerId(), "shop", false, opinion, appr.getShopname());
			}
		}		
		ar.setMsg("审核成功!");
		ar.setSuccess(true);
		return ar;
	}
	/////////////////////////////////////////////////法务审核///////////////////////////////////////////////////
	
	@RequestMapping(value = "/shop/toDetail", method = RequestMethod.POST)
	public String toDetail(Model model,Long id){

		//商家信息
		ApprShop appr = apprShopService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		model.addAttribute("checkList", checkList);
		model.addAttribute("shop", appr);

		Supplier s = supplierService.findByid(appr.getSupplierId());
		model.addAttribute("supplier", s);
		//经营类目信息
		List<ProductCategory> productCategoryList = supplierService.getProductCategoryListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//商家资质信息
		List<Attachment> attachmentList = supplierService.getAttachmentListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//品牌信息
		List<ProductBrand> productBrandList = supplierService.getProductBrandListBySupplierId(appr.getSupplierId(),appr.getShopId());
		for (ProductBrand productBrand : productBrandList) {
			CheckReview q = new CheckReview();
			q.setObjKey(productBrand.getId());
			List<CheckReview> ls = checkReviewService.selectByModel(q);
			if(ls != null && !ls.isEmpty()) {
				productBrand.setCheckMemo(ls.get(0).getMemo());
				productBrand.setCheckAlarmDate(ls.get(0).getAlarmDate());
			} else if (productBrand.getOldId() != null) {
				q.setObjKey(productBrand.getOldId());
				ls = checkReviewService.selectByModel(q);
				if(ls != null && !ls.isEmpty()) {
					productBrand.setCheckMemo(ls.get(0).getMemo());
					productBrand.setCheckAlarmDate(ls.get(0).getAlarmDate());
				}
			}
		}
		model.addAttribute("productCategory", productCategoryList);
		model.addAttribute("attachment", attachmentList);
		model.addAttribute("productBrand", productBrandList);
		
		return "manager/appr/shop/detail";
	}
	
	/////////////////////////////////////////////////运营审核///////////////////////////////////////////////////
	@RequestMapping("/shop/business")
	public String toBusinessShop(Model model){
		return "manager/appr/shop/business_base";
	}		

	@RequestMapping("/shop/businessList")
	public String businessShopList(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {

		params.put("status", 3);
		PageInfo pageInfo = apprShopService.findApprShop(params);
		model.addAttribute("page", pageInfo);
		return "manager/appr/shop/business_list";
	}
	
	@RequestMapping(value = "/shop/toBusinessCheck", method = RequestMethod.POST)
	public String toBusinessShopCheck(Model model,Long id){

		//商家信息
		ApprShop appr = apprShopService.getById(id);
		//审核信息
		List<CheckOpinion> checkList = supplierService.getCheckOpinionListBySupplierId(id);
		model.addAttribute("checkList", checkList);
		model.addAttribute("shop", appr);
		Supplier s = supplierService.findByid(appr.getSupplierId());
		model.addAttribute("supplier", s);

		//经营类目信息
		List<ProductCategory> productCategoryList = supplierService.getProductCategoryListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//商家资质信息
		List<Attachment> attachmentList = supplierService.getAttachmentListBySupplierId(appr.getSupplierId(),appr.getShopId());
		//品牌信息
		List<ProductBrand> productBrandList = supplierService.getProductBrandListBySupplierId(appr.getSupplierId(),appr.getShopId());
		model.addAttribute("productCategory", productCategoryList);
		model.addAttribute("attachment", attachmentList);
		model.addAttribute("productBrand", productBrandList);
		
		return "manager/appr/shop/business_check";
	}

	@RequestMapping("/shop/businessCheck")
	@ResponseBody
	public ActResult doBusinessShopCheck(Long id,Integer status,String opinion, HttpServletRequest request){//HttpServletRequest request, Supplier pojo,String opinion
		ActResult  ar = new ActResult();
		HttpSession session = request.getSession();

			if(StringUtils.isEmpty(id)){
				ar.setMsg("id不能为空");
				ar.setSuccess(false);
				return ar;
			}
			if(StringUtils.isEmpty(status)){
				ar.setMsg("审核状态不能为空");
				ar.setSuccess(false);
				return ar;
			}

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			ApprShop appr = apprShopService.getById(id);
			
			SysUser user = (SysUser)obj;
			//审核
			appr.setStatus(3+status);
			appr.setBusChkId(user.getId());
			appr.setBusChkDesc(opinion);
			appr.setBusChkTime(new Date());
			
			appr.setUpdateTime(new Date());
			appr.setUpdateUser(user.getId());
			appr.setUpdateName(user.getName());
			
			if(3+status == 4) {
				apprCheckFacade.apprToShop(appr);
			} else {
				apprShopService.update(appr);
			}
				
			//记录审核表
			CheckOpinion co = new CheckOpinion();
			//co.setId(db.CreateID());
			co.setUsername(user.getName());
			co.setCheckId(id);
			co.setResult(status);
			co.setOpinion(opinion);
			co.setTime(new Date());
			co.setType(0);
			co.setUserId(user.getId());
			try {
				supplierService.saveCheckOpinion(co);
			} catch (Exception e) {
				ar.setMsg("记录审核表时失败");
				e.printStackTrace();
			}
			
			//审核通过
			if(status > 0) {
				if(!StringUtils.isNullOrEmpty(appr.getToEmail())){
					this.emailUtil.sendSupplierCheckEmail(appr.getToEmail(), null, true);
				}
			} else if(status==-1) {
				this.sendMailToLawyer("shop", opinion);
			}
		}		
		ar.setMsg("审核成功!");
		ar.setSuccess(true);
		return ar;
	}
	/////////////////////////////////////////////////运营审核///////////////////////////////////////////////////
	
	/**
	 * 给法务发邮件
	 * @param type
	 * @param checkNotWhy
	 */
	private void sendMailToLawyer(String type,String checkNotWhy){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-109");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		SysUser lawyer = null;
		for (SysUser sysUser : list) {
			if(!StringUtils.isEmpty(sysUser.getEmail())) {
				lawyer=sysUser;
				break;
			}
		}
		
		String linkUrl = "http://wdwmananger.wd-w.com/#/ajax/appr/"+type+"/lawyer";
		
		if(lawyer!=null) {
			pushMsg(StringUtils.isEmpty(lawyer.getPhone())?lawyer.getMobile():lawyer.getPhone(),"您好，有需要您处理的审核");
			//emailUtil.sendSupplierCheckEmailForUs(lawyer.getEmail(), checkNotWhy, "您好，有需要您处理的审核", linkUrl);
		}
	}
	

	/**
	 * 给法务发邮件
	 * @param type
	 * @param checkNotWhy
	 */
	private void sendMailToManager(Long mId, String type,boolean result,String checkNotWhy,String objName){

		SysUser manager = sysUserMapper.selectByPrimaryKey(mId);
		
		String linkUrl = "http://wdwmananger.wd-w.com/#/ajax/appr/"+type+"/manager";

		pushMsg(StringUtils.isEmpty(manager.getPhone())?manager.getMobile():manager.getPhone(),result?"您好，有需要您处理的审核":objName+"的" + ("supplier".equals(type)?"商家":"店铺") + "审核被退了回来");
		//emailUtil.sendSupplierCheckEmailForUs(manager.getEmail(), checkNotWhy, result?"您好，有需要您处理的审核":objName+"的" + ("supplier".equals(type)?"商家":"店铺") + "审核被退了回来", linkUrl);
	}
	

	/**
	 * 给法务发邮件
	 * @param type
	 * @param checkNotWhy
	 */
	private void sendMailToBusiness(String type,String checkNotWhy){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "19");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		SysUser lawyer = null;
		for (SysUser sysUser : list) {
			if(!StringUtils.isEmpty(sysUser.getEmail())) {
				lawyer=sysUser;
				break;
			}
		}
		
		String linkUrl = "http://wdwmananger.wd-w.com/#/ajax/appr/"+type+"/business";

		if(lawyer!=null) {
			pushMsg(StringUtils.isEmpty(lawyer.getPhone())?lawyer.getMobile():lawyer.getPhone(),"您好，有需要您处理的审核");
			//emailUtil.sendSupplierCheckEmailForUs(lawyer.getEmail(), checkNotWhy, "您好，有需要您处理的审核", linkUrl);
		}
	}

	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}
	
	private void pushMsg(String phone,String msg){
		
		if(StringUtils.isEmpty(phone)) return;
		
		UserFactory model = new UserFactory();
		model.setPhone(phone);
		model.setEnabled(1);
		model.setUsable(1);
		model.setEmployeeType(-1); //全部数据

		List<UserFactory> lst= userFactoryService.selectByModel(model);
		if(lst.isEmpty()) return;
		
		Map paramPush=new HashMap();
		paramPush.put("title", "有您需要处理的审核");
		paramPush.put("msg", msg);
		
		try{
			//app 推送
			paramPush.put("userId", lst.get(0).getId());
			HttpClientUtil.sendHttpRequest("post", apiUrl+"user/pushMsg", paramPush);
		} catch(Exception ex) {
			
		}
	}
}
