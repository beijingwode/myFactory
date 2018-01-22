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

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.common.util.NumberUtil;
import com.wode.factory.company.facade.EntBenefitFacade;
import com.wode.factory.company.query.EntBenefitFlowVo;
import com.wode.factory.company.query.SupplierTransferVo;
import com.wode.factory.company.service.EntBenefitFlowService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.model.ServiceReceipt;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.SaleBillQuery;
import com.wode.factory.supplier.query.ServiceReceiptVo;
import com.wode.factory.supplier.service.CommissionRefundService;
import com.wode.factory.supplier.service.SaleBillService;
import com.wode.factory.supplier.service.SaleDetailService;
import com.wode.factory.supplier.service.SaleDurationParamService;
import com.wode.factory.supplier.service.ServiceReceiptService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierDurationService;
import com.wode.factory.supplier.service.SupplierLogService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.SupplierTransferService;
import com.wode.factory.supplier.util.ExpressUtils;
import com.wode.factory.supplier.util.UserInterceptor;

import cn.org.rapid_framework.page.Page;

@Controller
@RequestMapping("saleBill")
public class SaleBillController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("saleBillService")
	private SaleBillService saleBillService;
	
	@Autowired
	@Qualifier("saleDetailService")
	private SaleDetailService saleDetailService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("supplierDurationService")
	private SupplierDurationService supplierDurationService;
	@Autowired
	private EntBenefitFlowService entBenefitFlowService;
	@Autowired
	private CommissionRefundService commissionRefundService;
	

	@Autowired
	private ServiceReceiptService serviceReceiptService;

	@Autowired
	private SupplierTransferService supplierTransferService;
	
	@Autowired
	private SaleDurationParamService saleDurationParamService;
	
	@Autowired
	private EntBenefitFacade entBenefitFacade;

	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;
	@Autowired
	@Qualifier("expressComService")
	private ExpressUtils expressComService;
	private final String LIST_ACTION = "redirect:/saleBill/list.html";
		
	public SaleBillController() {
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
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,SaleBillQuery query) {
		@SuppressWarnings("rawtypes")
		Page page = this.saleBillService.findPage(query);
		
		ModelAndView result = new ModelAndView("product/saleBill/list");
		result.addAllObjects(toModelMap(page, query));
		return result;
	}
	

	/** 
	 * 进入发货地址管理页面
	 **/
	@RequestMapping(value="receiptList")
	public ModelAndView receiptList(HttpServletRequest request,ServiceReceiptVo vo) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView result = new ModelAndView("product/salebill/receiptList");
		if(us == null) {
			//会话中usermodel对象为空
			result.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			vo.setSupplierId(us.getSupplierId());
			PageInfo<ServiceReceipt> page =  serviceReceiptService.findPage(vo);

			result.addObject("page", page);	
			result.addObject("query", vo);
		}
		result.addObject("ecs", expressComService.getExpressCompanys().values());
		
		return result;
	}


	/**
	 * 进入企业福利流水页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("account")
	public ModelAndView account(HttpServletRequest request, EntBenefitFlowVo vo, String type) {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		vo.setEnterpriseId(us.getSupplierId());

		ModelAndView mv = new ModelAndView();

		// pageNumber等于0 的时候，是第一次查询
		if ("2".equals(type)) {
			// 一个月
			Calendar cal = Calendar.getInstance();
			vo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			vo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		} else if ("3".equals(type)) {
			Calendar cal = Calendar.getInstance();
			vo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -90);
			vo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		} else {
			type="1";
		}
		vo.setPageSize(10);
		PageInfo<EntBenefitFlowVo> page =  entBenefitFlowService.findCashPage(vo);
		mv.setViewName("product/salebill/account");

		// 当前用户
		UserFactory u = UserInterceptor.getSessionUser(request,shopService);
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(u.getSupplierId(), SeasonUtil.getNowYear(),
				SeasonUtil.getNowSeason(), u.getUserName());

		mv.addObject("cashBalance", esa.getAllCashSum().subtract(esa.getGiveCashSum()));
		mv.addObject("ticketBalance", esa.getAllTicketSum().subtract(esa.getGiveTicketSum()).subtract(esa.getTransfeTicketSum()));

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -90);
		BigDecimal cashReturn = commissionRefundService.getRecentSum(us.getSupplierId(), cal.getTime(), new Date());
		mv.addObject("cashReturn", cashReturn==null?0:cashReturn);
		SupplierTransfer appr = supplierTransferService.getApprIng(us.getSupplierId());

		
		mv.addObject("btnName", appr==null?"申请提现":"提现审核中");
		mv.addObject("btnStatus", appr==null?"0":"1");
				
		mv.addObject("type", type);
		mv.addObject("page", page);
		mv.addObject("query", vo);
		return mv;
	}
	/**
	 * 进入企业福利流水页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("transferFlow")
	public ModelAndView entBenefitFlow(HttpServletRequest request, SupplierTransferVo vo, String type) {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		vo.setSupplierId(us.getSupplierId());

		ModelAndView mv = new ModelAndView();

		// pageNumber等于0 的时候，是第一次查询
		if ("2".equals(type)) {
			// 一个月
			Calendar cal = Calendar.getInstance();
			vo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			vo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			type = "2";
		} else if ("3".equals(type)) {
			Calendar cal = Calendar.getInstance();
			vo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -90);
			vo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		} else {
			type = "1";			
		}
		vo.setPageSize(10);
		PageInfo<SupplierTransfer> page =  supplierTransferService.findPage(vo);
		mv.setViewName("product/salebill/transferFlow");

		mv.addObject("type", type);
		mv.addObject("page", page);
		mv.addObject("query", vo);
		return mv;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SaleBill saleBill = (SaleBill)saleBillService.getById(id);
		return new ModelAndView("product/saleBill/show","saleBill",saleBill);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,SaleBill saleBill) throws Exception {
		return new ModelAndView("product/saleBill/create","saleBill",saleBill);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,SaleBill saleBill) throws Exception {
		saleBillService.save(saleBill);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SaleBill saleBill = (SaleBill)saleBillService.getById(id);
		return new ModelAndView("product/saleBill/edit","saleBill",saleBill);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		SaleBill saleBill = (SaleBill)saleBillService.getById(id);
		bind(request,saleBill);
		saleBillService.update(saleBill);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		saleBillService.removeById(id);
		return new ModelAndView(LIST_ACTION);
		
		//删除多个
		/*String[] items = request.getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			
			java.lang.Long id = new java.lang.Long((String)params.get("id"));
			
			saleBillService.removeById(id);
		}
		return new ModelAndView(LIST_ACTION);
		*/
	}
	
	
	/**
	 *对账单list
	 **/
	@RequestMapping(value="gotoSaleBillList",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView gotoProductlist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return saleBillList(request,response);
	}
	
	/**
	 * 对账单list
	 **/
	@RequestMapping(value="findSaleBillList",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView findProductlistPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return saleBillList(request,response);
	}
	
	
	/**
	 * 对账单list
	 **/
	public ModelAndView saleBillList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier supplier = supplierService.getById(userModel.getSupplierId());
		    
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=10;
			mv.setViewName("product/salebill/salebilllist");
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
	
			Long supplierId = supplier.getId();
			String supplierName = request.getParameter("supplierName");
			String billId = request.getParameter("billId");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String paystarttime = request.getParameter("paystarttime");
			String payendtime = request.getParameter("payendtime");
			String payStatus=request.getParameter("payStatus");
			String confirmStatus=request.getParameter("confirmStatus");
			String receiptStatus = request.getParameter("receiptStatus");
			
			Map<String,Object> map = new HashMap<String,Object>();

			if(supplierId!=null){
				map.put("supplierId",new Long(supplierId));
			}
			if(!StringUtils.isEmpty(supplierName)){
				map.put("supplierName",new Long(supplierName));
			}
			if(!StringUtils.isEmpty(billId)){
				map.put("billId",new Double(billId));
			}
			if(!StringUtils.isEmpty(startTime)){
				map.put("startTime",startTime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(endTime)){
				map.put("startTime",endTime+" 23:59:59");
			}
			if(!StringUtils.isEmpty(paystarttime)){
				map.put("payTimeBegin",paystarttime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(payendtime)){
				map.put("payTimeEnd",payendtime+" 23:59:59");
			}
			if(!StringUtils.isEmpty(payStatus)){
				map.put("payStatus",new Integer(payStatus));
			}
			if(!StringUtils.isEmpty(confirmStatus)){
				map.put("confirmStatus",new Integer(confirmStatus));
			}
			if(!StringUtils.isEmpty(receiptStatus)){
				map.put("receiptStatus", new Integer(receiptStatus));
			}
			
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("supplierId",supplierId);
			mv.addObject("supplierName",supplierName);
			mv.addObject("billId",billId);
			mv.addObject("startTime",startTime);
			mv.addObject("endTime",endTime);
			mv.addObject("paystarttime",paystarttime);
			mv.addObject("payendtime",payendtime);
			mv.addObject("payStatus",payStatus);
			mv.addObject("confirmStatus",confirmStatus);
			mv.addObject("receiptStatus",receiptStatus);
			
			Integer total = saleBillService.findlistPageCount(map);
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
				map.put("sortColumns", "createTime");
				List<SaleBill> list = saleBillService.findlistPage(map);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
				result.setMsgBody(list);
			}else{
				result.setErrorCode("1000");
			}
			mv.addObject("result",result);
		}
		
		return mv;
	}
	 /**
     * 进入更新页面
     */
    @RequestMapping(value = {"baseEdit"})
    public String edit(HttpServletRequest request, ModelMap model) throws Exception {
        com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);

        SupplierDuration sd = supplierDurationService.getBySupplierId(us.getSupplierId());
        
        Supplier supplier = supplierService.getById(us.getSupplierId());
        boolean flag = false;
        if(StringUtils.isEmpty(sd.getPhone())) {
        	sd.setPhone(supplier.getComTel());
        }
        if(StringUtils.isEmpty(sd.getContacts())) {
        	sd.setContacts(supplier.getCorName());
        }
        if(sd.getAddressNumber() == null){
        	sd.setAddressNumber(supplier.getComState()+supplier.getComCity()+supplier.getComAdd()+"、"+supplier.getComTel());
        	flag = true;
        }
        if(sd.getOpeningBanNumber() == null){
        	sd.setOpeningBanNumber(supplier.getBankId()+supplier.getBankName()+"、"+supplier.getBankNum());
        	flag = true;
        }
        if(sd.getTaxpayerNumber() == null){
        	sd.setTaxpayerNumber(supplier.getComRegisternum());
        	flag = true;
        }
        if(flag == true){
        	if(sd.getBillType() == null){
        		sd.setBillType(0);
        	}
        	supplierDurationService.update(sd);
        }
        model.addAttribute("sd", sd);
        model.addAttribute("sdName", getSdName(sd));
        model.addAttribute("s", supplier);
        return "product/salebill/edit";
    }
    /**
     * 保存更新对象
     */
    @RequestMapping(value = "baseSave", method = RequestMethod.POST)
    @Token(remove = true)
    public String update(HttpServletRequest request,SupplierDuration upd) throws Exception {
        com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
        SupplierDuration sd = supplierDurationService.getById(upd.getId());
        sd.setAddressNumber(upd.getAddressNumber());
        sd.setOpeningBanNumber(upd.getOpeningBanNumber());
        sd.setBillType(upd.getBillType());
        sd.setTaxpayerNumber(upd.getTaxpayerNumber());
        sd.setAccountType(upd.getAccountType());
        sd.setAlipayAccount(upd.getAlipayAccount());
        sd.setContacts(upd.getContacts());
        sd.setPhone(upd.getPhone());
        supplierDurationService.update(sd);
        
		//日志
		SupplierLog log = new SupplierLog();
		log.setUserId(userModel.getId());
		log.setUsername(userModel.getUserName());
		log.setAct("编辑 结算信息("+upd.getAccountType()+","+upd.getAlipayAccount()+","+upd.getContacts()+","+upd.getPhone()+")");
		log.setTime(new Date());
		log.setResult("success");
		supplierLogService.save(log);
		
        return "redirect:/saleBill/baseEdit.html";
    }
	/**
	 * 对账单list
	 **/
	@RequestMapping(value="toSaleBillView",method=RequestMethod.GET)
	public ModelAndView toSaleBillView(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
			return mv;
		} 
	
	    String saleBillId = request.getParameter("saleBillId");
	    SaleBill sb = saleBillService.getById(new Long(saleBillId));
		mv.addObject("saleBill",sb);
		mv.setViewName("product/salebill/salebillview");
		Map<String,Object> map =new HashMap<String,Object>();
		if(sb.getCloseType() == 1|| sb.getCloseType() == 2) {
			map.put("relationKey", new Long(saleBillId));
			map.put("startnum", 0);
			map.put("size",500);
			map.put("sortColumns", "createTime");
			mv.addObject("relationList", saleBillService.findlistPage(map));
			
		} else {
			map.put("saleBillId", new Long(saleBillId));

			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
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
	
			String supplierId = request.getParameter("supplierId");
			
			
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("supplierId",supplierId);
			
			Integer total = saleDetailService.findlistPageCount(map);
			Integer startnum=(page-1)*size;
			if(total>0){
				if(total<startnum){
					startnum=total-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				//map.put("startnum", startnum);
				//map.put("size",size);
				map.put("sortColumns", "createTime");
				List<SaleDetail> list = saleDetailService.findlistPage(map);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total);
				result.setErrorCode("0");
				result.setMsgBody(list);
			}else{
				result.setErrorCode("1000");
			}
		}
		
		mv.addObject("result",result);		
		return mv;
	}
	
	/**
	 * ajax上架商品
	 **/
	@RequestMapping(value="ajaxConfirm")
	@Token(remove=true)
	public ModelAndView ajaxConfirm(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		String saleBillId=request.getParameter("saleBillId");
		String confirmStatus=request.getParameter("confirmStatus");
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			SaleBill sb =saleBillService.getById(new Long(saleBillId));
			if(sb!=null){
				sb.setConfirmStatus(new Integer(confirmStatus));
				sb.setConfirmTime(new Date());
				saleBillService.saveOrUpdate(sb);
				result.setErrorCode("0");
				mv.addObject("result", result);
			}
		}
		return mv;
	}

	/**
	 * ajax上架商品
	 **/
	@RequestMapping(value="apprReceipt")
	public ModelAndView apprReceipt(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("pages",1);
			map.put("startnum", 0);
			map.put("size",200);
			map.put("supplierId",userModel.getSupplierId());
			map.put("payStatus",4);		//已支付
			map.put("receiptStatus",0); 	//未申请
			map.put("sortColumns", "createTime");
			List<SaleBill> ls= saleBillService.findlistPage(map);
			
			BigDecimal sum = BigDecimal.ZERO;
			for (SaleBill saleBill : ls) {
				sum=sum.add(saleBill.getCommissionPrice());
			}
			
			if(NumberUtil.isGreaterZero(sum)) {
				if(isYearEnd() || sum.compareTo(BigDecimal.valueOf(500))>=0) {
					for (SaleBill saleBill : ls) {
						saleBill.setReceiptStatus(1);	//已申请
						saleBillService.saveOrUpdate(saleBill);
					}
					result.setErrorCode("0");	
				} else {
					result.setErrorCode("1");
					result.setMessage("开票金额"+sum+"元,不足500元，不能申请开票，请继续累积并仔细阅读下方《发票申请须知》。");					
				}
			} else {
				result.setErrorCode("1");
				result.setMessage("开票金额"+sum+"元,不能申请开票，请继续累积并仔细阅读下方《发票申请须知》。");
			}
			mv.addObject("result", result);
		}
		return mv;
	}

	/**
	 * ajax上架商品
	 **/
	@RequestMapping(value="apprReturn")
	public ModelAndView apprReturn(HttpServletRequest request,Long id,String returnNote) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			ServiceReceipt sr= serviceReceiptService.getById(id);
			if(sr.getReturnLimit()!=null && sr.getReturnLimit().compareTo(new Date())<0) {
				result.setErrorCode("1");
			} else {
				sr.setStatus("4");
				sr.setReturnDate(new Date());
				sr.setReturnNote(returnNote);
				sr.setUpdateTime(new Date());
				sr.setUpdateUser(userModel.getId());
				serviceReceiptService.update(sr);
				result.setErrorCode("0");
			}
			mv.addObject("result", result);
		}
		return mv;
	}

	/**
	 * ajax上架商品
	 **/
	@RequestMapping(value="sendReturn")
	public ModelAndView sendReturn(HttpServletRequest request,Long id,String returnExpressType,String returnExpressNo) throws Exception {
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			ServiceReceipt sr= serviceReceiptService.getById(id);
			if(sr.getReturnLimit()!=null && sr.getReturnLimit().compareTo(new Date())<0) {
				result.setErrorCode("1");
			} else {
				sr.setStatus("7");
				sr.setReturnExpressType(returnExpressType);
				sr.setReturnExpressNo(returnExpressNo);
				sr.setUpdateTime(new Date());
				sr.setUpdateUser(userModel.getId());
				serviceReceiptService.update(sr);
				result.setErrorCode("0");
			}
			mv.addObject("result", result);
		}
		return mv;
	}

	/**
	 * ajax上架商品
	 **/
	@RequestMapping(value="apprTransfer")
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
	        SupplierDuration sd = supplierDurationService.getBySupplierId(userModel.getSupplierId());

			SupplierTransfer appr = supplierTransferService.getApprIng(userModel.getSupplierId());
	        if(appr!=null) {
				result.setErrorCode("1001");
				result.setMessage("提现申请正在审核中，不能重复提交");
	        } else {
	        	appr = new SupplierTransfer();
	        	appr.setSupplierId(userModel.getSupplierId());
	        	appr.setFinanceCode(sd.getFinanceCode());
	        	appr.setStatus(1);
	        	appr.setAmount(amount);
	        	appr.setCreateDate(new Date());
	        	appr.setCreateUser(userModel.getId());
	        	
	        	supplierTransferService.save(appr);
	        	
	    		//日志
	    		SupplierLog log = new SupplierLog();
	    		log.setUserId(userModel.getId());
	    		log.setUsername(userModel.getUserName());
	    		log.setAct("申请现金账户提现，提现金额("+amount+")");
	    		log.setTime(new Date());
	    		log.setResult("success");
	    		supplierLogService.save(log);
				result.setErrorCode("0");
			}
			mv.addObject("result", result);
		}
		return mv;
	}
	/**
	 * ajax获取商品价格
	 **/
	@RequestMapping(value = "getExpressCom", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelAndView getExpressCom(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String expressType = request.getParameter("expressType");

	    ExpressCompany ci = expressComService.getExpressComById(expressType);
		    
		Result result = new Result();
		result.setErrorCode("0");
		result.setMsgBody(ci.getName()+","+ci.getPinYin());

		return new ModelAndView("", "result", result);
	}
	

	private boolean isYearEnd() {
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		if(month==Calendar.DECEMBER) {
			return (day>=15 && day<=31);
		}
		return false;
	}

	private String getSdName(SupplierDuration sd) {
		String sdName = "按月结算";
		if("101".equals(sd.getSaleDurationKey())) {
        	sdName="按日结算";
        } else if("102".equals(sd.getSaleDurationKey())) {
        	sdName="每5天结算";
        } else if("103".equals(sd.getSaleDurationKey())) {
        	sdName="按周结算";
        } else if("104".equals(sd.getSaleDurationKey())) {
        	sdName="每10天结算";
        } else if("105".equals(sd.getSaleDurationKey())) {
        	sdName="半月结算，每月结算两次";
        } else if("106".equals(sd.getSaleDurationKey())) {
        	sdName="按月结算";
        } else if("107".equals(sd.getSaleDurationKey())) {
        	sdName="每月5,15,25日结算";
        } else if("201".equals(sd.getSaleDurationKey())) {
        	sdName="先款日结";
        } else if("211".equals(sd.getSaleDurationKey())) {
        	sdName="先款日结佣金单收";
        } else if("221".equals(sd.getSaleDurationKey())) {
        	sdName="先款日结运费单收";
        } else if(sd.getSaleDurationKey().startsWith("3")) {
        	sdName = "";
    		//截取3xx系列后面xx转换为账期天数
    		String	key = sd.getSaleDurationKey().substring(1,sd.getSaleDurationKey().length());
    		SaleDurationParam saleDurationParam = saleDurationParamService.findSaleDurationParamByKey(sd.getSaleDurationKey());
    		if(null != saleDurationParam ){
    			sdName = "满" + saleDurationParam.getValue();
    		}
    		sdName += "或" +  Integer.parseInt(key) + "天";
        }
		
		return sdName;
	}
}

