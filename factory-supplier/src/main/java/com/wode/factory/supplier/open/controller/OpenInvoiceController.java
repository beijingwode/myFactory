
package com.wode.factory.supplier.open.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.model.OpenRequestBase;
import com.wode.factory.model.OpenResponse;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.open.InvoiceGet;
import com.wode.factory.supplier.service.IssuedInvoiceService;
import com.wode.factory.supplier.service.SuborderService;

/**
 * 发票
 * 
 * @author user
 *
 */
@Controller
@RequestMapping("open/supplier")
public class OpenInvoiceController extends BaseController {

	/**
	 * 订单
	 */
	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	
	@Autowired
	private IssuedInvoiceService issuedInvoiceService;

	/**
	 * 增加发票
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "trade/invoice/add")
	@ResponseBody
	public Object invoiceAdd(HttpServletRequest request, OpenRequestBase openRequestBase, InvoiceGet invoiceGet) throws IllegalAccessException, InvocationTargetException {
		String suborderId = invoiceGet.getTrade_id();
		String supplierId = invoiceGet.getSupplierId();
		Suborder suborder = suborderService.getById(suborderId);
		if (suborder == null) {
			return OpenResponse.fail("订单信息错误,请联系客服!");
		} else {
			if (supplierId.equals(suborder.getSupplierId().toString())) {
				IssuedInvoice old = issuedInvoiceService.getIssuedInvoice(suborderId);
				IssuedInvoice in = new IssuedInvoice();
				BeanUtils.copyProperties(in, invoiceGet);
				in.setCreatetime(new Date());
				in.setSuborderid(suborderId);
				if(old == null){
					issuedInvoiceService.save(in);
				}else{
					in.setId(old.getId());
					issuedInvoiceService.update(in);
				}
				suborder.setInvoiceStatus(2);//订单发票已开
				suborderService.update(suborder);
				return OpenResponse.success("");
			}
		}
		return OpenResponse.fail("订单信息错误,请联系客服!");
	}

}
