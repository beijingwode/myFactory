package com.wode.factory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.SeasonUtil;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.model.ApprSupplierExit;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ApprSupplierExitService;
import com.wode.factory.service.ApprSupplierService;
import com.wode.factory.service.EntBenefitApprService;
import com.wode.factory.service.EntSeasonActService;
import com.wode.factory.service.EnterpriseService;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SaleDurationParamService;
import com.wode.factory.service.SupplierCategoryService;
import com.wode.factory.service.SupplierDurationVoService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.service.UserShareService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysUserService;
import com.wode.tongji.model.ManagerBusiness;
import com.wode.tongji.service.ManagerBusinessService;

/**
 * 
 * @author liuchenghao
 *
 */
@Controller
@RequestMapping("supplierExit")
public class SupplierExitController {

	@Autowired
	EntBenefitFacade entBenefitFacade;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	SupplierCategoryService supplierCategoryService;
	@Autowired
	SaleDurationParamService saleDurationParamService;
	@Autowired
	SupplierDurationVoService supplierDurationVoService;
	@Autowired
	SupplierCloseFacade supplierCloseFacade;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	@Qualifier("entBenefitApprService")
	private EntBenefitApprService entBenefitApprService;
	@Autowired
	@Qualifier("enterpriseUserService")
	private EnterpriseUserService enterpriseUserService;
	@Resource
	private ProductService productService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	ApprSupplierService apprSupplierService;
	@Autowired
	ApprSupplierExitService apprSupplierExitService;
	@Autowired
	private UserShareService userShareService;
	@Autowired
	ManagerBusinessService managerBusinessService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 功能说明： （招商管理）跳传商家退出页面
	 * 日期 ：2017年9月19日
	 * @return
	 */
	@RequestMapping("/supplierExitPage")
	public String supplierExitPage(Model model,HttpSession session){
//		model.addAttribute("viewStatus", "view");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
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
		return "/sys/supplier/supplier-list-base";
	}
	
	/**
	 * 功能说明： （招商管理）商家退出申请数据信息列表
	 * 日期 ：2017年9月19日
	 * @param params
	 * @param viewStatus
	 * @param session
	 * @return
	 */
	@RequestMapping("/exitlists")
	public ModelAndView querySupplierExitLists(@RequestParam Map<String, Object> params,HttpSession session){
		ModelAndView modelAndView = new ModelAndView("/sys/supplier/supplier-exit-viewList");
		PageHelper.startPage(params);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		PageInfo pageInfo = apprSupplierExitService.getApprSupplierList(params,user.getName());
		modelAndView.addObject("page", pageInfo);
		modelAndView.addObject("uid", user.getId());
		return modelAndView;
	}
	/**
	 * 功能说明：招商管理处理信息页面
	 * 日期 ：2017年9月21日
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkViewPage")
	public ModelAndView checkViewPage(Long id,HttpSession session){
		ModelAndView modelAndView = new ModelAndView("/sys/supplier/supplier-list-checkView");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		//商家信息
		Supplier supplier = supplierService.getSupplierDetailWithItems(id);
		//获取商家是否已经存在退出申请的信息
		ApprSupplierExit apprSupExit = new ApprSupplierExit();
		apprSupExit.setSupplierId(id);
		List<ApprSupplierExit> apprSupExitList = apprSupplierExitService.selectByModel(apprSupExit);
		if(apprSupExitList.size() > 0){
			apprSupExit = apprSupExitList.get(0);
		}
		apprSupExit.setSupplierName(supplier.getComName());
		apprSupExit.setJoinTime(supplier.getCreatTime());
		modelAndView.addObject("apprSupExit", apprSupExit);
		//获取关于商家审核表的所有信息
		List<CheckOpinion> checkList = new ArrayList<CheckOpinion>();
		if(apprSupExit.getId() != null){
			checkList = supplierService.getAllCheckOpinionBySupplierId(apprSupExit.getId());
		}
		modelAndView.addObject("checkList", checkList);
		return modelAndView;
	}
	
	
	/**
	 * 功能说明： （运营管理）跳传商家退出申请页面
	 * 日期 ：2017年9月19日
	 * @return
	 */
	@RequestMapping("/exitPage")
	public String exitPage(Model model,HttpSession session){
		model.addAttribute("viewStatus", "view");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		model.addAttribute("uid", user.getId());
		return "/manager/product/product_mangaer-base";
	}
	
	/**
	 * 功能说明： （运营管理）商家退出申请数据信息（查询退出申请表数据）
	 * 日期 ：2017年9月19日
	 * @param params
	 * @param viewStatus
	 * @param session
	 * @return
	 */
	@RequestMapping("/exitlist")
	public ModelAndView querySupplierExitList(@RequestParam Map<String, Object> params,String viewStatus,HttpSession session){
		ModelAndView modelAndView = null;
		if(!"view2".equals(viewStatus)){
			modelAndView = new ModelAndView("view1".equals(viewStatus)?"/manager/saleBill/saleBill-exit-view":"/manager/product/product-exit-view");
		}else{
			modelAndView = new ModelAndView("/manager/product/exitExecute-exit-view");
		}
		PageHelper.startPage(params);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		//获取所有的招商经理Id
		ManagerBusiness managerBusiness = new ManagerBusiness();
		managerBusiness.setBusinessId(user.getId());
		List<ManagerBusiness> managerBusinessList = managerBusinessService.selectByModel(managerBusiness);
		StringBuffer supplierIds = new StringBuffer();
		for (ManagerBusiness managerBusiness2 : managerBusinessList) {
			supplierIds.append(managerBusiness2.getId()+",");
		}
		if(supplierIds.length() > 0 ){
			supplierIds.deleteCharAt(supplierIds.length() - 1);
		}
		if(supplierIds.length() > 0){
			params.put("manegerIds", supplierIds.toString());
		}else{
			params.put("manegerIds", -1);
		}
		PageInfo pageInfo = apprSupplierExitService.selectByMap(params,user.getName());
		modelAndView.addObject("page", pageInfo);
		return modelAndView;
	}
	
	/**
	 * 功能说明：运营审核申请信息页面
	 * 日期 ：2017年9月19日
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkPage")
	public ModelAndView exit(Long id){
		ModelAndView modelAndView = new ModelAndView("/manager/product/product-check-view");
		//商家信息
		Supplier supplier = supplierService.getSupplierDetailWithItems(id);
		//获取商家是否已经存在退出申请的信息
		ApprSupplierExit apprSupExit = new ApprSupplierExit();
		apprSupExit.setSupplierId(id);
		List<ApprSupplierExit> apprSupExitList = apprSupplierExitService.selectByModel(apprSupExit);
		if(apprSupExitList.size() > 0){
			apprSupExit = apprSupExitList.get(0);
		}
		apprSupExit.setSupplierName(supplier.getComName());
		apprSupExit.setJoinTime(supplier.getCreatTime());
		//获取在售商品数
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplier.getId());
		List<ApprSupplierExit> counts = apprSupplierExitService.findCountsByMap(map);
		apprSupExit.setProductCnt(counts.get(0).getProductCnt());
		modelAndView.addObject("apprSupExit", apprSupExit);
		//获取关于商家审核表的所有信息
		List<CheckOpinion> checkList = new ArrayList<CheckOpinion>();
		if(apprSupExit.getId() != null){
			checkList = supplierService.getAllCheckOpinionBySupplierId(apprSupExit.getId());
		}
		modelAndView.addObject("checkList", checkList);
		return modelAndView;
	}
	
	/**
	 * 功能说明： （结算管理）跳传商家退出申请页面/////////////////////////////////////////////
	 * 日期 ：2017年9月20日
	 * @return
	 */
	@RequestMapping("/financeExitPage")
	public String financeExitPage(Model model,HttpSession session){
		model.addAttribute("viewStatus", "view1");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		model.addAttribute("uid", user.getId());
		return "/manager/saleBill/saleBill-mangaer-base";
	}
	
	
	/**
	 * 功能说明：财务审核申请信息页面
	 * 日期 ：2017年9月20日
	 * @param id
	 * @return
	 */
	@RequestMapping("/financeCheckPage")
	public ModelAndView financeCheckPage(Long id,HttpSession session){
		ModelAndView modelAndView = new ModelAndView("/manager/saleBill/saleBill-check-view");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		//商家信息
		Supplier supplier = supplierService.getSupplierDetailWithItems(id);
		//获取商家是否已经存在退出申请的信息
		ApprSupplierExit apprSupExit = new ApprSupplierExit();
		apprSupExit.setSupplierId(id);
		List<ApprSupplierExit> apprSupExitList = apprSupplierExitService.selectByModel(apprSupExit);
		if(apprSupExitList.size() > 0){
			apprSupExit = apprSupExitList.get(0);
		}
		apprSupExit.setSupplierName(supplier.getComName());
		apprSupExit.setJoinTime(supplier.getCreatTime());
		
		//获取未结订单总数、未结对账单数、现金券余额
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplier.getId());
		List<ApprSupplierExit> counts = apprSupplierExitService.findCountsByMap(map);
		apprSupExit.setUnCloseOrderCnt(counts.get(2).getUnCloseOrderCnt());
		apprSupExit.setUnClosebillCnt(counts.get(3).getUnClosebillCnt());
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(apprSupExit.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),user.getName());
		if(esa != null){
			apprSupExit.setCashBalance(esa.getAllCashSum().subtract(esa.getGiveCashSum()));
		}
		
		modelAndView.addObject("apprSupExit", apprSupExit);
		//获取关于商家审核表的所有信息
		List<CheckOpinion> checkList = new ArrayList<CheckOpinion>();
		if(apprSupExit.getId() != null){
			checkList = supplierService.getAllCheckOpinionBySupplierId(apprSupExit.getId());
		}
		modelAndView.addObject("checkList", checkList);
		return modelAndView;
	}
	
	/**
	 * 功能说明： （运营管理）跳传商家退出申请页面/////////////////////////////////////////////
	 * 日期 ：2017年9月21日
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/exitExecutePage")
	public String exitExecutePage(Model model,HttpSession session){
		model.addAttribute("viewStatus", "view2");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		model.addAttribute("uid", user.getId());
		return "/manager/product/exitExecute-mangaer-base";
	}
	
	
	/**
	 * 功能说明：运营审核退出执行信息页面
	 * 日期 ：2017年9月21日
	 * @param id
	 * @return
	 */
	@RequestMapping("/exitExecuteCheckPage")
	public ModelAndView exitExecuteCheckPage(Long id,HttpSession session){
		ModelAndView modelAndView = new ModelAndView("/manager/product/exitExecute-check-view");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		//商家信息
		Supplier supplier = supplierService.getSupplierDetailWithItems(id);
		//获取商家是否已经存在退出申请的信息
		ApprSupplierExit apprSupExit = new ApprSupplierExit();
		apprSupExit.setSupplierId(id);
		List<ApprSupplierExit> apprSupExitList = apprSupplierExitService.selectByModel(apprSupExit);
		if(apprSupExitList.size() > 0){
			apprSupExit = apprSupExitList.get(0);
		}
		apprSupExit.setSupplierName(supplier.getComName());
		apprSupExit.setJoinTime(supplier.getCreatTime());
		
		//获取未结订单总数、未结对账单数、现金券余额
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplier.getId());
		List<ApprSupplierExit> counts = apprSupplierExitService.findCountsByMap(map);
		apprSupExit.setProductCnt(counts.get(0).getProductCnt());
		apprSupExit.setUnCloseOrderCnt(counts.get(2).getUnCloseOrderCnt());
		apprSupExit.setUnClosebillCnt(counts.get(3).getUnClosebillCnt());
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(apprSupExit.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),user.getName());
		if(esa != null){
			apprSupExit.setCashBalance(esa.getAllCashSum().subtract(esa.getGiveCashSum()));
		}
		
		modelAndView.addObject("apprSupExit", apprSupExit);
		//获取关于商家审核表的所有信息
		List<CheckOpinion> checkList = new ArrayList<CheckOpinion>();
		if(apprSupExit.getId() != null){
			checkList = supplierService.getAllCheckOpinionBySupplierId(apprSupExit.getId());
		}
		modelAndView.addObject("checkList", checkList);
		return modelAndView;
	}
	@RequestMapping("/findMangerId")
	@ResponseBody
	@NoCheckLogin
	public ActResult<String> findMangerId(Long userId){
		ActResult act = new ActResult();
		UserFactory userFactory = userFactoryService.getById(userId);
		SysUser sysUser = sysUserService.findMangerId(userFactory.getPhone());
		act.setData(sysUser.getId().toString());
		act.setMsg(sysUser.getName());
		return act;
	}
}
