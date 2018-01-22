/**
 * 
 */
package com.wode.factory.supplier.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.stereotype.Token;
import com.wode.common.util.ActResult;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Suborder;
import com.wode.factory.supplier.service.InvoiceApplyService;
import com.wode.factory.supplier.service.IssuedInvoiceService;
import com.wode.factory.supplier.service.OrdersService;
import com.wode.factory.supplier.service.SuborderService;

@Controller
@RequestMapping("invoice")
public class InvoiceController extends BaseSpringController{
	
	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	
	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;
	
	@Autowired
	private IssuedInvoiceService issuedInvoiceService;
	
	@Autowired
	private InvoiceApplyService invoiceApplyService;
	
	public InvoiceController() {
	}
	
	/**
	 * 查看发票详情
	 **/
	@RequestMapping(value="getInvoice")
	@Token(remove=true)
	public ModelAndView getInvoice(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/product/suborder/invoice");
		Suborder suborder = suborderService.getById(request.getParameter("id"));
		if(null != suborder){
			Orders order = ordersService.getById(suborder.getOrderId());
			mav.addObject("suborder",suborder);
			mav.addObject("order",order);
			IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(suborder.getSubOrderId());
			mav.addObject("invoice", issuedInvoice);
			if(null == issuedInvoice){
				mav.addObject("tit", 2);
			}else{
				mav.addObject("tit", 3);
			}
			//查询申请信息
			InvoiceApply invoiceApply = new InvoiceApply();
			invoiceApply.setSuborderid(suborder.getSubOrderId());
			List<InvoiceApply> list = invoiceApplyService.selectByModel(invoiceApply);
			if(list != null && !list.isEmpty()){
				mav.addObject("invoiceApply",list.get(0));
			}
		}
		return mav;
	}
	
	
	/**
	 * 增加发票
	 **/
	@RequestMapping(value="addInvoice")
	@Token(remove=true)
	public ModelAndView addInvoice(IssuedInvoice entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Suborder sub = suborderService.getById(entity.getSuborderid());
		sub.setInvoiceStatus(2);
		suborderService.update(sub);
		entity.setCreatetime(new Date());
		issuedInvoiceService.saveOrUpdate(entity);
		ModelAndView mav = new ModelAndView("redirect:/invoice/getInvoice?id=" + entity.getSuborderid());
		return mav;
	}
	
	/**
	 * 查看发票详情
	 **/
	@RequestMapping(value="ajaxAddInvoice")
	@ResponseBody
	public ActResult<Object> ajaxAddInvoice(IssuedInvoice entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActResult<Object> act = new ActResult<Object>();
		Suborder sub = suborderService.getById(entity.getSuborderid());
		sub.setInvoiceStatus(2);
		suborderService.update(sub);
		IssuedInvoice old = issuedInvoiceService.getIssuedInvoice(entity.getSuborderid());
		if(old != null){
			entity.setId(old.getId());
		}else{
			entity.setCreatetime(new Date());
		}
		issuedInvoiceService.saveOrUpdate(entity);
		return act;
	}
}

