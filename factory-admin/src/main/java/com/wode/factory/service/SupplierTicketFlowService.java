package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierTicketFlow;
import com.wode.factory.vo.SupplierTicketFlowVo;

/**
 *
 */
public interface SupplierTicketFlowService extends FactoryEntityService<SupplierTicketFlow> {
	public PageInfo<SupplierTicketFlowVo> findPageList(Map<String, Object> map);
}
