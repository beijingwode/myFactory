package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.vo.SupplierLimitTicketVo;

public interface SupplierLimitTicketService extends FactoryEntityService<SupplierLimitTicket>{

	PageInfo<SupplierLimitTicketVo> findInfoPageList(Map<String, Object> params);

	List<SupplierLimitTicket> getPastTicket();

	SupplierLimitTicket findInfoByMap(Map<String, Long> map);

}
