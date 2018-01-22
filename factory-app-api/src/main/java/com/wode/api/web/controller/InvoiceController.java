package com.wode.api.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Suborder;

import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.InvoiceApplyService;
import com.wode.factory.user.service.IssuedInvoiceService;

import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.vo.SubOrderVo;

/**
 * 发票
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;
	
	@Autowired
	private InvoiceApplyService invoiceApplyService;

	@Autowired
	private IssuedInvoiceService issuedInvoiceService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	/**
	 * 根据订单id查询状态，如果是已开票跳到发票详情，如果没开票进入开票申请页面。
	 * 
	 * @param suborderid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getInvoice.user")
	@ResponseBody
	public ActResult<Map<String, Object>> getInvoice(String suborderid, HttpServletRequest request, HttpServletResponse response) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(loginUser.getId());
		query.setSubOrderId(suborderid);
		SubOrderVo subOrder = suborderService.findOrderDetailById(query);
		if(subOrder==null){
			return ActResult.fail("暂时无此订单");
		}
		Map<String, Object> data = new HashMap<String, Object>();
			data.put("subOrder", subOrder);
			// 加载常用地址
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(loginUser.getId());
			data.put("shippingAddressList", shippingAddressList);
			IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(suborderid);
			data.put("issuedInvoice", issuedInvoice);
			InvoiceApply ia = new InvoiceApply();
			ia.setSuborderid(suborderid);
			ia.setUserId(loginUser.getId());
			List<InvoiceApply> list = invoiceApplyService.selectByModel(ia);
			if (null != list && !list.isEmpty()) {
				data.put("invoiceApply", list.get(0));
			}
		return ActResult.success(data);
	}

	@RequestMapping("/addInvoice.user")
	@ResponseBody
	public ActResult<Object> addInvoice(InvoiceApply ia, HttpServletRequest request, HttpServletResponse response) {
		ActResult<Object> act = new ActResult<Object>();
		if(ia.getType() == 0 && StringUtils.isNullOrEmpty(ia.getTitle())){
			ia.setTitle("个人");
		}
		ia.setUserId(loginUser.getId());
		ia.setCreatetime(new Date());
		invoiceApplyService.saveOrUpdate(ia);
		Suborder sub = suborderService.getById(ia.getSuborderid());
		sub.setInvoiceStatus(1);
		suborderService.update(sub);
		act.setSuccess(true);
		return act;
	}
	
	

	@RequestMapping(value = { "/myinvoice" })
	public ModelAndView myinvoice(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		InvoiceApply ia = new InvoiceApply();
		ia.setUserId(loginUser.getId());
		mav.setViewName("/member/myinvoice");
		List<InvoiceApply> list = invoiceApplyService.selectByModel(ia);
		if (null != list && !list.isEmpty()) {
			for (InvoiceApply invoiceApply : list) {
				invoiceApply.setIssuedInvoice(issuedInvoiceService.getIssuedInvoice(invoiceApply.getSuborderid()));
			}
			mav.addObject("invoiceList", list);
		}
		mav.addObject("menu", "myinvoice");
		return mav;
	}
	/**
	 * 微信订单
	 * @param uid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView page(String suborderId,String uid,ModelAndView model,HttpServletRequest request){
		model.addObject("suborderId", suborderId);
		model.addObject("uid", uid);
		model.setViewName("inoviceApply");
		return model;
	}
}
