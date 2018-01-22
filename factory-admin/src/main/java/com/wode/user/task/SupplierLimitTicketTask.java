package com.wode.user.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.service.SupplierLimitTicketService;
import com.wode.factory.service.UserLimitTicketService;
@Service
public class SupplierLimitTicketTask {
	@Autowired
	private SupplierLimitTicketService supplierLimitTicketService;
	@Autowired
	private UserLimitTicketService userLimitTicketService;
	public void run() {
		limitTicketPast();
	}
	public void limitTicketPast() {
		List<SupplierLimitTicket> supplierLimitTicketList = supplierLimitTicketService.getPastTicket();
		for (SupplierLimitTicket supplierLimitTicket : supplierLimitTicketList) {
				List<UserLimitTicket> userLimitTicketList = userLimitTicketService.getpastLimitTicket(supplierLimitTicket.getId());
				for (UserLimitTicket userLimitTicket : userLimitTicketList) {
					if(userLimitTicket.getStatus()!=3) {
						userLimitTicket.setStatus(3);
						userLimitTicketService.update(userLimitTicket);
					}
				}
		}
	}
}
