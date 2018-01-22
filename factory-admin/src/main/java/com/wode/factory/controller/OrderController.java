package com.wode.factory.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.FileUtils;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.PaymentDao;
import com.wode.factory.mapper.RefundorderDao;
import com.wode.factory.mapper.ReturnorderDao;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.mapper.UserFactoryDao;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.EmpBenefitFlowService;
import com.wode.factory.service.ExchangeSuborderService;
import com.wode.factory.service.ExpressComService;
import com.wode.factory.service.GroupOrdersService;
import com.wode.factory.service.InvoiceApplyService;
import com.wode.factory.service.IssuedInvoiceService;
import com.wode.factory.service.OrderService;
import com.wode.factory.service.PaymentService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.vo.SuborderOrderVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysRole;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysRoleService;

@Controller
@RequestMapping("orderList")
@SuppressWarnings("unchecked")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Resource
	private PaymentService paymentService; 
	
	@Value("#{configProperties['tongji.download']}")
	private  String downloadFilePatch;

	@Resource
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private ExpressComService expressComService;

	@Autowired
	private SuborderitemDao suborderitemDao;

	@Autowired
	private SubOrderService subOrderService;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	private UserFactoryDao userFactoryDao;
	@Resource
	private SysRoleService sysRoleService;
	@Autowired
	EmpBenefitFlowService empBenefitFlowService;
	@Resource
	PaymentDao paymentDao;
	
	@Autowired
	private InvoiceApplyService invoiceApplyService;
	@Autowired
	private IssuedInvoiceService issuedInvoiceService;
	
	@Autowired
    private ReturnorderDao returnOrderDao;
	@Autowired
    private RefundorderDao refundOrderDao;
	
	@Autowired
	private GroupOrdersService groupOrdersService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	private Set<String> interfaceUrl=new HashSet<String>();
	
	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}

	}
	
	@RequestMapping
	public String toOrder(Model model){
		return "sys/order/order-base";
	}
	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {
		String[] ary = request.getParameterValues("status");
		if(ary!=null && ary.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if("1".equals(string)) {
					params.put("payStatus", 1);
				} else {
					sb.append(string).append(",");
					if("3".equals(string) || "5".equals(string)){
						if("3".equals(string)){
							sb.append("13").append(",");
							sb.append("14").append(",");
						}
						sb.append("15").append(",");
						sb.append("16").append(",");
					}
				}
			}
			params.put("status", sb.toString());
		}
		PageInfo<SuborderOrderVo> page = orderService.getSuborderList(params);
		List<SuborderOrderVo> vo =  page.getList();
		for (SuborderOrderVo suborderOrderVo : vo) {
			if("wxpay".equals(suborderOrderVo.getThirdType())&&suborderOrderVo.getThirdNo()!=null){
				String paywxType = paywxType(suborderOrderVo.getThirdNo());
				suborderOrderVo.setThirdType(paywxType);
			}
		}
		model.addAttribute("page", page);
		return "manager/order/list";
	}
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listDeal", method = RequestMethod.POST)
	public String listDeal(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {
		String[] ary = request.getParameterValues("status");
		if(ary!=null && ary.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if("1".equals(string)) {
					params.put("payStatus", 1);
				} else {
					sb.append(string).append(",");
					if("3".equals(string) || "5".equals(string)){
						if("3".equals(string)){
							sb.append("13").append(",");
							sb.append("14").append(",");
						}
						sb.append("15").append(",");
						sb.append("16").append(",");
					}
				}
			}
			params.put("status", sb.toString());
		}
		PageInfo<SuborderOrderVo> page = orderService.getSuborderList(params);
		for (SuborderOrderVo suborderOrderVo : page.getList()) {
			Suborderitem item = suborderitemDao.findBySubId(suborderOrderVo.getSubOrderId());
			suborderOrderVo.setProductName(item.getProductName());
		}
		model.addAttribute("page", page);
		return "manager/order/list-deal";
	}

	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "listFinance", method = RequestMethod.POST)
//	public String listFinance(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request) {
//		String[] ary = request.getParameterValues("status");
//		if(ary!=null && ary.length>0) {
//			StringBuilder sb =new StringBuilder();
//			for (String string : ary) {
//				string = string.trim();
//				if("1".equals(string)) {
//					params.put("payStatus", 1);
//				} else {
//					sb.append(string).append(",");		
//					if("3".equals(string) || "5".equals(string)){
//						if("3".equals(string)){
//							sb.append("13").append(",");
//							sb.append("14").append(",");
//						}
//						sb.append("15").append(",");
//						sb.append("16").append(",");
//					}
//				}
//			}
//			params.put("status", sb.toString());
//		}
//		String s = request.getParameter("purchasedStatus");
//		if (!StringUtils.isEmpty(s)) {
//			if ("3".equals(s)) {
//				params.put("benefitType", 3);
//			} else {
//				params.put("realPrice", 0);
//				params.put("benefitType", 3);
//			}
//		}
//		params.put("noTest", "1");
//		PageInfo<SuborderOrderVo> page = orderService.getSuborderList(params);
//		List<SuborderOrderVo> vo =  page.getList();
//		for (SuborderOrderVo suborderOrderVo : vo) {
//			if("wxpay".equals(suborderOrderVo.getThirdType())&&suborderOrderVo.getThirdNo()!=null){
//				String paywxType = paywxType(suborderOrderVo.getThirdNo());
//				suborderOrderVo.setThirdType(paywxType);
//			}
//		}
//		model.addAttribute("page", page);
//		return "manager/order/list-finance";
//	}
	/**
	 * 
	 * 功能说明： 查询订单详细
	 * 日期:	2015年7月24日
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("{mode}/showlayer")
	public String showlayer(Model model,String id,HttpSession session) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subOrderId", id);
		params.put("pageNum", 1);
		params.put("pageSize", 2);
		PageInfo<SuborderOrderVo> page = orderService.getSuborderList(params);
		SuborderOrderVo vo =  page.getList().get(0);
		List dataList = new ArrayList();
		//判断是否为团购订单
		if("1".equals(vo.getOrderType())||"4".equals(vo.getOrderType())){
			if(vo.getRelationId()!=null&&vo.getRelationId()!=0){
				List<GroupOrders> groupOrdersList = groupOrdersService.findByGroupId(vo.getRelationId());
				for (GroupOrders groupOrders : groupOrdersList) {
					List<GroupSuborder> findByOrderId = groupOrdersService.findByOrderId(groupOrders.getOrderId());
					for (GroupSuborder groupSuborder : findByOrderId) {
						if("wxpay".equals(groupSuborder.getThirdType())&&groupSuborder.getThirdNo()!=null){
							groupSuborder.setThirdType(paywxType(groupSuborder.getThirdNo()));
						}
						dataList.add(groupSuborder);
					}
				}
			}
			model.addAttribute("gsdataList",dataList);
		}else if("5".equals(vo.getOrderType())){//判断是否为换领订单
			ExchangeSuborder ex = new ExchangeSuborder();
			if(vo.getRelationId()==null){
				ex.setSubOrderId(vo.getSubOrderId());
			}else{
				ex.setBatchId(vo.getRelationId());
			}
			List<ExchangeSuborder> selectByModel = exchangeSuborderService.selectByModel(ex);
			for (ExchangeSuborder exchangeSuborder : selectByModel) {
				if("wxpay".equals(exchangeSuborder.getThirdType())&&exchangeSuborder.getThirdNo()!=null){
					exchangeSuborder.setThirdType(paywxType(exchangeSuborder.getThirdNo()));
				}
			}
			model.addAttribute("exdataList",selectByModel);
		}
		Orders order = orderService.findById(vo.getOrderId());
		String phone = order.getMobile();
		if(!StringUtils.isEmpty(phone) && !"空".equals(phone)){
			phone = phone.substring(0, 3) + "****" + phone.substring(phone.length()-4, phone.length());
			order.setMobile(phone);
		}
		String m = order.getAddress();
		if(m.contains("(电话")) {
			m = m.substring(0, m.lastIndexOf("(电话"));
			order.setAddress(m);
		}
		model.addAttribute("vo", vo);
		model.addAttribute("order", order);
		if (!StringUtils.isEmpty(vo.getNoto())) {//subOrder表中的noto不为空
			model.addAttribute("note", vo.getNoto());
		}else{
			model.addAttribute("note", order.getNote());
		}
		if(vo.getReturnOrderId()!=null){
			Returnorder returnorder = returnOrderDao.getById(vo.getReturnOrderId());
			if(returnorder!=null){
				model.addAttribute("returnSeason", returnorder.getReason());
			}
		}else{
			if(vo.getRefundOrderId()!=null){
				Refundorder refundorder = refundOrderDao.getById(vo.getRefundOrderId());
				if(refundorder!=null){
					model.addAttribute("returnSeason", refundorder.getReason());
				}
			}
		}
		Supplier s =supplierService.findByEmpId(order.getUserId());
		model.addAttribute("enterPriseName", s==null?"":s.getComName());
		
		model.addAttribute("items", suborderitemDao.findBySubIdForView(id));

	    ExpressCompany ci = expressComService.getExpressComById(vo.getExpressType());
	    if(ci!=null){
	    	model.addAttribute("expressName", ci.getName());
	    	model.addAttribute("expressCom", ci.getPinYin());
	    	if("14660000000000000".equals(vo.getExpressType())) {
	    		String expressNo = vo.getExpressNo();
	    		if(StringUtils.isEmpty(expressNo)) {
			    	model.addAttribute("expressNo", "");
	    		} else {
	    			if(expressNo.length() > 4) {
				    	model.addAttribute("expressNo",expressNo.substring(0,2)+ "****"+expressNo.substring(expressNo.length()-2,expressNo.length()));	    				
	    			} else {
				    	model.addAttribute("expressNo", "****");
	    			}
	    		}
	    	} else {
		    	model.addAttribute("expressNo", vo.getExpressNo());
	    	}
	    }
	    model.addAttribute("issuedInvoice", null);
	    model.addAttribute("invoiceApply", null);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		/*if(vo.getInvoiceStatus()==2){
			IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(id);
			model.addAttribute("issuedInvoice", issuedInvoice);
		}else{
			List<InvoiceApply> InvoiceApplyList = invoiceApplyService.getInvoiceApplyBySuborderId(id);
			if(InvoiceApplyList!=null && !InvoiceApplyList.isEmpty()){
				model.addAttribute("invoiceApply", InvoiceApplyList.get(0));
			}
		}*/
		if(vo.getInvoiceStatus()==2){
			IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(id);
			model.addAttribute("issuedInvoice", issuedInvoice);
			List<InvoiceApply> InvoiceApplyList = invoiceApplyService.getInvoiceApplyBySuborderId(id);
			if(InvoiceApplyList!=null && !InvoiceApplyList.isEmpty()){
				model.addAttribute("invoiceApply", InvoiceApplyList.get(0));
			}
		}else{
			List<InvoiceApply> InvoiceApplyList = invoiceApplyService.getInvoiceApplyBySuborderId(id);
			if(InvoiceApplyList!=null && !InvoiceApplyList.isEmpty()){
				model.addAttribute("invoiceApply", InvoiceApplyList.get(0));
			}
		}
		SysUser user = (SysUser)obj;
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("roles", roles);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/order/detail";
	}
	@RequestMapping(value = "payAppId")
	@ResponseBody
	public ActResult<String> payAppId(HttpServletRequest request,HttpServletResponse response,String tradeNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("way", "wxpay");
		params.put("status", "2");
		params.put("tradeNo", tradeNo);
		String appId = "";
		List<Payment> ls = paymentDao.findList(params);
		for (Payment pay : ls) {
			appId = pay.getAppId()==null?"":pay.getAppId();
			break;
		}
		return ActResult.success(appId);
	}
	public String paywxType(String tradeNo){ 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("way", "wxpay");
		params.put("status", "2");
		params.put("tradeNo", tradeNo);
		String appId = "";
		List<Payment> ls = paymentDao.findList(params);
		for (Payment pay : ls) {
			appId = pay.getAppId()==null?"":pay.getAppId();
			break;
		}
		if("wx1b153767a3760be4".equals(appId)) {
			appId="微信APP";
		} else if ("wxb62e121cbeffdddf".equals(appId)){
			appId="微信公众号";
		}
		return appId;
	}
	/**
	 * 弹窗
	 * 到款确认
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toClose", method = RequestMethod.POST)
	public String toClose(Model model,String id) {
		Suborder order = subOrderService.getById(id);
		model.addAttribute("order", order);
		return "manager/order/closeBox";
	}

	/**
	 * 弹窗
	 * 到款确认
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toStockDown", method = RequestMethod.POST)
	public String toStockDown(Model model,String id) {
		Suborder order = subOrderService.getById(id);
		model.addAttribute("order", order);
		return "manager/order/stockDownBox";
	}

	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/close")
	@ResponseBody
	public ActResult<Object> comfirmReturn(String id,String closeReason, HttpSession session) {
//		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
//		SysUser user = (SysUser)obj;
		Suborder subOrder = subOrderService.getById(id);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Orders order = orderService.findById(subOrder.getOrderId());
		paramMap.put("userId", order.getUserId());
		paramMap.put("subOrderId", subOrder.getSubOrderId());
		paramMap.put("closeReason", "平台："+closeReason);

		for(String apiurl:interfaceUrl){
			try {
				String response = HttpClientUtil.sendHttpRequest("post", apiurl.replace("creatHtml","member/autoCancelOrder"), paramMap);
				return JsonUtil.getObject(response, ActResult.class);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return ActResult.success(null);
	}

	@RequestMapping("/stockDown")
	@ResponseBody
	public ActResult<Object> stockDown(String id, HttpSession session) {

		subOrderService.updateToStockUp(id, 0);
		return ActResult.success(null);
	}
	
	@RequestMapping("base")
	public String toPageAttrView(Model model,HttpSession session) {
		
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
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("supplierList", getSupplierList(query));
		model.addAttribute("roles", roles);
		return "manager/order/base";
	}
	
	@RequestMapping("baseDeal")
	public String baseDeal(Model model,HttpSession session) {
		
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
//		}
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("supplierList", getSupplierList(query));
		model.addAttribute("roles", roles);
		return "manager/order/base-deal";
	}
	
	@RequestMapping("baseFinance")
	public String toBaseFinance(Model model,HttpSession session) {
		
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/order/base-finance";
	}

	@RequestMapping("baseFinance201")
	public String toBaseFinance201(Model model,HttpSession session) {
		
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/order/base-finance201";
	}
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		return supplierService.getPage(query).getList();
	}

	@RequestMapping(value = "getCashFlow")
	@ResponseBody
	public List<EmpBenefitFlow> getCashFlow(HttpServletRequest request,HttpServletResponse response,Long userId) {
		return empBenefitFlowService.selectCashByUserId(userId);
	}
	
	@RequestMapping("/exportXls")
	@ResponseBody
	public String exportXls(@RequestParam Map<String, Object> params,ModelMap model,HttpServletResponse response) {
		orderService.createOrderXls(params);
		download(downloadFilePatch+"orders.xls", response);
		return "";
	}
		
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		String[] ary = request.getParameterValues("status");
		if(ary!=null && ary.length>0) {
			StringBuilder sb =new StringBuilder();
			for (String string : ary) {
				string = string.trim();
				if("1".equals(string)) {
					params.put("payStatus", 1);
				} else {
					sb.append(string).append(",");					
				}
			}
			params.put("status", sb.toString());
		}
		params.put("noTest", "1");
		PageInfo<SuborderOrderVo> page = orderService.getSuborderList(params);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("订单一览"); 
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
        headers.add("买家ID ");
        headers.add("公司ID");
        headers.add("公司");
        headers.add("订单号 ");
        headers.add("订单类型");
        headers.add("运单号（卡券密码）");
        headers.add("支付状态");
        headers.add("支付时间");
        headers.add("物流状态");
        headers.add("商家名称");
        headers.add("招商经理");
        headers.add("下单时间");
        headers.add("妥投时间");
        headers.add("取消时间");
        headers.add("取消原因 ");
        headers.add("商品所属类目 ");
        headers.add("商品名称（标题） ");
        headers.add("商品规格 ");
        headers.add("商品金额");
        headers.add("商品数量");
        headers.add("运费");
        headers.add("内购券金额");
        headers.add("佣金比例");

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
		UserFactory q = new UserFactory();
		q.setEmployeeType(1);
		q.setSupplierId(1019589081269290L); //我的圈
		List<UserFactory> lst= userFactoryService.selectByModel(q);
		Map<Long,UserFactory> mapUsers = new HashMap<Long,UserFactory>();
		for (SuborderOrderVo p : page.getList()) {
			List<Suborderitem> items = suborderitemDao.findBySubIdForView(p.getSubOrderId());
			boolean isFirst=true;
			boolean isWodequan=this.inList(lst, p.getUserId());
			for (Suborderitem vo : items) {
				currentRow++;
				int col=0;
	            row = sheet.createRow(currentRow); 
		        //headers.add("买家ID ");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getUserId()+"");
		        //headers.add("公司");
	            if(isWodequan) {
			        //headers.add("公司ID ");
		            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue("1019589081269290");
	            	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue("我的圈");
	            } else {
	            	UserFactory uf = this.getUser(mapUsers, p.getUserId());
	            	if(uf==null) {
		            	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue("");
	            	} else {
		            	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(uf.getSupplierId()+"");
	            	}
	            	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue("");
	            }	            
		        //headers.add("订单号 ");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getSubOrderId());
	            if("1".equals(p.getOrderType())||"4".equals(p.getOrderType())){
	            	row.createCell(col++).setCellValue("团购订单");
	            }else if("5".equals(p.getOrderType())){
	            	row.createCell(col++).setCellValue("换领订单");
	            }else{
	            	row.createCell(col++).setCellValue("");
	            }
		        //headers.add("运单号（物流单号）");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getExpressNo());
	            //headers.add("支付状态");
	            if(p.getStatus()==0) {
		            row.createCell(col++).setCellValue("未支付");
	            } else if(p.getStatus()==1 || p.getStatus()==2 || p.getStatus()==4){
		            row.createCell(col++).setCellValue("已支付");
	            } else if(p.getStatus()==3){
		            row.createCell(col++).setCellValue("申请退货");
	            } else if(p.getStatus()==5){
		            row.createCell(col++).setCellValue("申请退款");
	            } else if(p.getStatus()==11){
		            row.createCell(col++).setCellValue("已退货");
	            } else if(p.getStatus()==12){
		            row.createCell(col++).setCellValue("已退款");
	            } else if(p.getStatus()==-1){
		            row.createCell(col++).setCellValue("已取消");
	            } else {
		            row.createCell(col++).setCellValue("");
	            }
		        //headers.add("支付时间");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getPayTime()==null?"":DateUtils.formatDate(p.getPayTime(),"yyyy-MM-dd")); //创建时间
		        //headers.add("物流状态");
	            if(p.getStatus()==0 || p.getStatus()==1|| p.getStatus()==-1) {
		            row.createCell(col++).setCellValue("未发货");
	            } else if(p.getStatus()==2){
		            row.createCell(col++).setCellValue("已发货");
	            } else if(p.getStatus()==3){
		            row.createCell(col++).setCellValue("申请退货");
	            } else {
		            row.createCell(col++).setCellValue("已收货");
	            }
		        //headers.add("商家名称");
	            row.createCell(col++).setCellValue(p.getSupplierName());
		        //headers.add("招商经理");
	            row.createCell(col++).setCellValue(p.getManagerName());
		        //headers.add("下单时间");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateTime()==null?"":DateUtils.formatDate(p.getCreateTime(),"yyyy-MM-dd HH:mm")); //创建时间
		        //headers.add("妥投时间");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getTakeTime()==null?"":DateUtils.formatDate(p.getTakeTime(),"yyyy-MM-dd HH:mm")); //创建时间
		        //headers.add("取消时间");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCancelTime()==null?"":DateUtils.formatDate(p.getCancelTime(),"yyyy-MM-dd HH:mm")); //创建时间
		        //headers.add("取消原因");
	            row.createCell(col++).setCellValue(p.getCloseReason());
		        //headers.add("商品所属类目 ");
	            row.createCell(col++).setCellValue(vo.getCategoryName());
		        //headers.add("商品名称（标题） ");
	            row.createCell(col++).setCellValue(vo.getProductName());
		        //headers.add("商品规格  ");
	            row.createCell(col++).setCellValue(vo.getItemValues());
		        //headers.add("商品金额");
	            row.createCell(col++).setCellValue(vo.getRealPay()==null?"":vo.getRealPay().doubleValue()+"");
		        //headers.add("商品数量");
	            row.createCell(col++).setCellValue(vo.getNumber());
		        //headers.add("运费");
	            if(isFirst){
	            	row.createCell(col++).setCellValue(p.getTotalShipping().doubleValue());
	            	isFirst = false;
	            } else {
		            row.createCell(col++).setCellValue("");
	            }
		        //headers.add("内购券金额");
	            row.createCell(col++).setCellValue(vo.getRealPay()==null?"":(vo.getPrice().multiply(new BigDecimal(vo.getNumber())).subtract(vo.getRealPay()).doubleValue())+""); //创建时间
		        //headers.add("佣金比例%");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getCommissionRatio()==null?"":vo.getCommissionRatio()+""); //创建时间
			}
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request,"订单一览"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private UserFactory getUser(Map<Long,UserFactory> mapUsers,Long id) {
		if(!mapUsers.containsKey(id)) {
			mapUsers.put(id, userFactoryDao.getById(id));
		}
		return mapUsers.get(id);
	}

	private boolean inList(List<UserFactory> lst,Long id) {
		for (int i=0;i<lst.size();i++) {
			if(lst.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	private void download(String path, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

}
