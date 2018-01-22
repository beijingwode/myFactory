package com.wode.factory.user.web.controller;

import java.util.Date;
import java.util.List;

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
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.InvoiceApplyService;
import com.wode.factory.user.service.IssuedInvoiceService;
import com.wode.factory.user.service.OrdersService;
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

	@Qualifier("shippingAddressService")
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
	@RequestMapping(value = { "/getInvoice" })
	public ModelAndView getInvoice(String suborderid, HttpServletRequest request, HttpServletResponse response) {
		UserFactory user = getUser(request, response);
		ModelAndView mav = new ModelAndView();
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(suborderid);
		SubOrderVo suborderVo = suborderService.findOrderDetailById(query);
		if (suborderVo.getInvoiceStatus() == 2) {// 进入发票详情
			IssuedInvoice issuedInvoice = issuedInvoiceService.getIssuedInvoice(suborderid);
			if(!StringUtils.isNullOrEmpty(issuedInvoice.getElectronicInvoice())){
				mav.setViewName("redirect:" + issuedInvoice.getElectronicInvoice());
			}else if(!StringUtils.isNullOrEmpty(issuedInvoice.getPaperInvoice())){
				mav.setViewName("redirect:" + issuedInvoice.getPaperInvoice());
			}else{
				mav.addObject("error", "商家尚未传递发票信息");
				mav.setViewName("redirect:/common/error.jsp");
			}
		} else {// 进入发票申请
			InvoiceApply ia = new InvoiceApply();
			ia.setSuborderid(suborderid);
			ia.setUserId(user.getId());
			mav.setViewName("/member/inoviceApply");
			List<InvoiceApply> list = invoiceApplyService.selectByModel(ia);
			if (null != list && !list.isEmpty()) {
				mav.addObject("invoiceApply", list.get(0));
				mav.addObject("tit", "2");
			} else {
				mav.addObject("tit", "1");
			}
			mav.addObject("subOrder", suborderVo);
			// 加载常用地址
			List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(user.getId());
			mav.addObject("shippingAddressList", shippingAddressList);
		}
		return mav;
	}

	@RequestMapping(value = { "/addInvoice" })
	@ResponseBody
	public ActResult<Object> addInvoice(InvoiceApply ia, HttpServletRequest request, HttpServletResponse response) {
		ActResult<Object> act = new ActResult<Object>();
		UserFactory user = getUser(request, response);
		act.setSuccess(false);
		if (user == null) {
			return act;
		}
		if(ia.getType() == 0 && StringUtils.isNullOrEmpty(ia.getTitle())){
			ia.setTitle("个人");
		}
		ia.setUserId(user.getId());
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
		UserFactory user = getUser(request, response);
		ModelAndView mav = new ModelAndView();
		InvoiceApply ia = new InvoiceApply();
		ia.setUserId(user.getId());
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
}
