package com.wode.factory.user.vo;

import java.util.List;

import com.wode.factory.model.SupplierLimitTicketSku;
import com.wode.factory.model.UserLimitTicket;

public class UserLimitTicketVo extends UserLimitTicket {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098611164354030117L;
	
	private List<SupplierLimitTicketSku> supplierLimitTicketSkuList;

	public List<SupplierLimitTicketSku> getSupplierLimitTicketSkuList() {
		return supplierLimitTicketSkuList;
	}

	public void setSupplierLimitTicketSkuList(List<SupplierLimitTicketSku> supplierLimitTicketSkuList) {
		this.supplierLimitTicketSkuList = supplierLimitTicketSkuList;
	}
	
	

}
