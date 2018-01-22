package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.GroupBuyDao;
import com.wode.factory.mapper.PaymentDao;
import com.wode.factory.mapper.RefundorderDao;
import com.wode.factory.mapper.ReturnorderDao;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.ExpressComService;
import com.wode.factory.service.GroupOrdersService;
import com.wode.factory.service.InvoiceApplyService;
import com.wode.factory.service.IssuedInvoiceService;
import com.wode.factory.service.PaymentService;
import com.wode.factory.service.SubOrderService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.GroupSubOrderVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysRole;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysRoleService;

@Controller
@RequestMapping("group")
public class GroupController {
	@Autowired
	private GroupOrdersService groupOrdersService;
	@Resource
	PaymentDao paymentDao;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysRoleService sysRoleService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
    private ReturnorderDao returnOrderDao;
	@Autowired
    private RefundorderDao refundOrderDao;
	@Autowired
	private ExpressComService expressComService;
	@Autowired
	private InvoiceApplyService invoiceApplyService;
	@Autowired
	private IssuedInvoiceService issuedInvoiceService;
	@Autowired
	private SubOrderService subOrderService;
	@Resource
	private PaymentService paymentService; 
	@Autowired
	private GroupBuyDao groupBuyDao;
	// 团订单
	@RequestMapping("groupOrder")
	public String groupOrder(Model model, HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
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
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("supplierList", getSupplierList(query));
		model.addAttribute("roles", roles);
		model.addAttribute("finance", 1);
		return "manager/group/group";
	}
	@RequestMapping("groupOrderFinance")
	public String groupOrderFinance(Model model, HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
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
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("supplierList", getSupplierList(query));
		model.addAttribute("roles", roles);
		model.addAttribute("finance", 0);
		return "manager/group/group";
	}
	/**
	 * 团订单列表
	 * @param params
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "grouplist", method = RequestMethod.POST)
	public String grouplist(@RequestParam Map<String, Object> params, Model model,HttpSession session,HttpServletRequest request,Integer type) {
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
		String suborderId = (String)params.get("groupId");
		if(!StringUtils.isEmpty(suborderId)){
			Suborder suborder = subOrderService.getById(suborderId);
			if(suborder!=null){
				params.put("groupId",suborder.getRelationId());
			}
		}
		PageInfo<GroupSubOrderVo> page = groupOrdersService.getgroupOrderList(params);
		List<GroupSubOrderVo> vo =  page.getList();
		for (GroupSubOrderVo suborderOrderVo : vo) {
			GroupBuy byId = groupBuyDao.getById(suborderOrderVo.getGroupId());
			suborderOrderVo.setGroupBuy(byId);
			if("wxpay".equals(suborderOrderVo.getThirdType())&&suborderOrderVo.getThirdNo()!=null){
				String paywxType = paywxType(suborderOrderVo.getThirdNo());
				suborderOrderVo.setThirdType(paywxType);
			}
		}
		if(type!=null){
			if(type==0){
				model.addAttribute("finance",1);
			}else{
				model.addAttribute("finance",0);
			}
		}
		
		model.addAttribute("page", page);
		return "manager/group/group-list";
	}
	/**
	 * 团订单详情
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("{mode}/showteail")
	public String showteail(Model model,String id,HttpSession session) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subOrderId", id);
		params.put("pageNum", 1);
		params.put("pageSize", 2);
		PageInfo<GroupSubOrderVo> page = groupOrdersService.getgroupOrderList(params);
		GroupSubOrderVo vo =  page.getList().get(0);
		GroupOrders order = groupOrdersService.findById(vo.getOrderId());
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
		
		model.addAttribute("items", groupOrdersService.findByItemsSubId(id));

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
	    if(vo.getInvoiceStatus()==2){
	    	IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(id);
	    	model.addAttribute("issuedInvoice", issuedInvoice);
	    }else{
	    	List<InvoiceApply> InvoiceApplyList = invoiceApplyService.getInvoiceApplyBySuborderId(id);
	    	if(InvoiceApplyList!=null && !InvoiceApplyList.isEmpty()){
	    		model.addAttribute("invoiceApply", InvoiceApplyList.get(0));
	    	}
	    }
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		List<SysRole> roles = sysRoleService.findUserRoleListByUserId(user.getId());
		model.addAttribute("roles", roles);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "manager/group/group-detail";
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
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		return supplierService.getPage(query).getList();
	}
	/**
	 * 弹窗
	 * 到款确认
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toConfirmPay", method = RequestMethod.POST)
	public String toConfirmPay(Model model,String id) {
		//Suborder order = subOrderService.getById(id);
		GroupSuborder groupSuborder = groupOrdersService.getBySubOrderId(id);
		model.addAttribute("order", groupSuborder);
		return "manager/group/confirmBox";
	}
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/confirmPay")
	@ResponseBody
	public ActResult<Object> comfirmReturn(String id,Integer payStatus, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		GroupSuborder order = groupOrdersService.getBySubOrderId(id);
		//List<GroupOrders> groupOrdersList = groupOrdersService.getByOrderId(order.getOrderId());
		Date now = new Date();
		Payment payment = paymentService.findPayByGroupOrderIdOrGroupSubOrderId(order.getSubOrderId());
		boolean whether = true;
		if(payment!=null){
			payment.setPayConfirm(payStatus);
			payment.setPayConfirmDate(now);
			payment.setUpdUser(user.getName());
			payment.setUpdateTime(now);
			//更新payment表
			paymentService.update(payment);
			//更新subOrder表
			if(StringUtils.isEmpty(payment.getSubOrderId())){
				//通过orderid查suborder
				//List<GroupSuborder> findByOrderId = groupOrdersService.findByOrderId(payment.getOrderId());
				List<GroupSuborder> groupSuborderList = groupOrdersService.findSuborderIdLikeOrderId(payment.getOrderId());
				for (GroupSuborder groupSuborder : groupSuborderList) {
					groupSuborder.setPayConfirm(payStatus);
					groupSuborder.setPayConfirmDate(now);
					groupSuborder.setUpdateBy(user.getName());
					groupSuborder.setUpdateTime(now);
					groupOrdersService.update(groupSuborder);
				}
			}else{
				GroupSuborder groupSuborder = groupOrdersService.getBySubOrderId(id);
				groupSuborder.setPayConfirm(payStatus);
				groupSuborder.setPayConfirmDate(now);
				groupSuborder.setUpdateBy(user.getName());
				groupSuborder.setUpdateTime(now);
				groupOrdersService.update(groupSuborder);
			}
		}else{
			
			order.setPayConfirm(payStatus);
			order.setPayConfirmDate(now);
			order.setUpdateBy(user.getName());
			order.setUpdateTime(now);
			groupOrdersService.update(order);
		}
			GroupOrders findById = groupOrdersService.findById(order.getOrderId());
			//根据团id查询全部团订单
			List<GroupOrders> findByGroupId = groupOrdersService.findByGroupId(findById.getGroupId());
			for (GroupOrders groupOrders : findByGroupId) {
				//查询子单
				List<GroupSuborder> findByOrderId = groupOrdersService.findByOrderId(groupOrders.getOrderId());
				for (GroupSuborder groupSuborder : findByOrderId) {
					if(groupSuborder.getPayConfirm()!=1) {
						whether = false;
						break;
					}
					//Payment payments = paymentService.findPayByGroupOrderIdOrGroupSubOrderId(groupSuborder.getSubOrderId());
				}
				if(whether==false) {
					break;
				}
			}
			if(whether==true) {//团下订单全部确认到款    更新订单
				List<Suborder> suborderList = subOrderService.findByRelationId(findById.getGroupId());
				if(suborderList!=null) {
					for (Suborder suborder : suborderList) {
						suborder.setPayConfirm(payStatus);
						suborder.setPayConfirmDate(now);
						suborder.setUpdateBy(user.getName());
						suborder.setUpdateTime(now);
						subOrderService.update(suborder);
					}
			   }
			}else {
				List<Suborder> suborderList = subOrderService.findByRelationId(findById.getGroupId());
				if(suborderList!=null) {
					for (Suborder suborder : suborderList) {
						suborder.setPayConfirm(0);
						suborder.setPayConfirmDate(now);
						suborder.setUpdateBy(user.getName());
						suborder.setUpdateTime(now);
						subOrderService.update(suborder);
					}
			   }
			}
		return ActResult.success(null);
	}
	

	//导出支付账单
	@RequestMapping(value = "exportExcelFinance")
	@ResponseBody
	public void exportExcelFinance(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
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
		String suborderId = (String)params.get("groupId");
		if(!StringUtils.isEmpty(suborderId)){
			Suborder suborder = subOrderService.getById(suborderId);
			if(suborder!=null){
				params.put("groupId",suborder.getRelationId());
			}
		}
		PageInfo<GroupSubOrderVo> page = groupOrdersService.getgroupOrderList(params);
		List<GroupSubOrderVo> vo =  page.getList();
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("换领单到款确认"); 
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
        headers.add("团订单ID");
        headers.add("支付状态");
        headers.add("付款时间");
        headers.add("取消时间");
        headers.add("团购状态");
        headers.add("订单关联key");
        headers.add("商家名称");
        headers.add("应付金额 ");
        headers.add("含运费");
        headers.add("现金券抵扣");
        headers.add("支付金额");
        headers.add("支付方式");
        headers.add("支付流水号");
        headers.add("到款状态");

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
        Map<Long,GroupBuy> map=new HashMap<Long,GroupBuy>();
		for (GroupSubOrderVo p : vo) {
			GroupBuy group = getGroupBuy(map,p.getGroupId());
			if("wxpay".equals(p.getThirdType())&&p.getThirdNo()!=null){
				String paywxType = paywxType(p.getThirdNo());
				p.setThirdType(paywxType);
			}
			
			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 

            //headers.add("团订单ID");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getSubOrderId()); 
            //headers.add("支付状态");
            if(p.getStatus()==0) {
	            row.createCell(col++).setCellValue("未支付");
            } else if(p.getStatus()==1 || p.getStatus()==2 || p.getStatus()==4){
	            row.createCell(col++).setCellValue("已支付");
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("付款时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getPayTime()==null?"":DateUtils.formatDate(p.getPayTime(),"yyyy-MM-dd")); //创建时间
            //headers.add("取消时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCancelTime()==null?"":DateUtils.formatDate(p.getCancelTime(),"yyyy-MM-dd")); //创建时间
            //headers.add("团购状态");
            if(group.getOrderStatus()==0){
            	row.createCell(col++).setCellValue("未下单");
            }else if(group.getOrderStatus()==1){
            	row.createCell(col++).setCellValue("已下单");
            }else if(group.getOrderStatus()==2){
            	row.createCell(col++).setCellValue("已发货 ");
            }else if(group.getOrderStatus()==4){
            	row.createCell(col++).setCellValue("已收货");
            }else if(group.getOrderStatus()==5){
            	row.createCell(col++).setCellValue("已分发");
            }else if(group.getOrderStatus()==-1){
            	row.createCell(col++).setCellValue("已关闭");
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("订单关联key");
            row.createCell(col++).setCellValue(group.getId());
            //headers.add("商家名称");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getSupplierName()); 
            //headers.add("应付金额 ");
            row.createCell(col++).setCellValue(p.getRealPrice().doubleValue()); 
            //headers.add("含运费");
            row.createCell(col++).setCellValue(p.getTotalShipping().doubleValue()); 
            //headers.add("现金券抵扣");
            row.createCell(col++).setCellValue(p.getCashPay()==null?0:p.getCashPay().doubleValue()); 
            //headers.add("支付金额");
            row.createCell(col++).setCellValue(p.getThirdPay()==null?0:p.getThirdPay().doubleValue()); 
            //headers.add("支付方式");
            if("zhifubao".equals(p.getThirdType())){
                row.createCell(col++).setCellValue("支付宝");
            }else if("pingtaiyue".equals(p.getThirdType())){
                row.createCell(col++).setCellValue("现金券");
            }else if("unionpay".equals(p.getThirdType())){
                row.createCell(col++).setCellValue("银联");
            } else {
	            row.createCell(col++).setCellValue(p.getThirdType());
            }
            //headers.add("支付流水号");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getThirdNo()); 
            //headers.add("到款状态");
            if(p.getPayConfirm()==null || p.getPayConfirm()==0){
                row.createCell(col++).setCellValue("未到款");
            } else {
	            row.createCell(col++).setCellValue("未到款");
            }
		}
        
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request,"团订单到款确认"));
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
	
	private GroupBuy getGroupBuy(Map<Long,GroupBuy> map,Long groupId) {
		if(!map.containsKey(groupId)) {
			map.put(groupId, groupBuyDao.getById(groupId));
		}
		return map.get(groupId);
	}
}
