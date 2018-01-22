package com.wode.factory.mapper;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.vo.SupplierLimitTicketVo;

public interface SupplierLimitTicketDao extends  FactoryBaseDao<SupplierLimitTicket>{

	List<SupplierLimitTicketVo> findInfoPageList(Map<String, Object> params);

	List<SupplierLimitTicket> getPastTicket();

	SupplierLimitTicket findInfoByMap(Map<String, Long> map);

}
