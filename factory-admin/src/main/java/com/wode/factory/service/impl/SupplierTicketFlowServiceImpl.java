package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierTicketFlowDao;
import com.wode.factory.model.SupplierTicketFlow;
import com.wode.factory.service.SupplierTicketFlowService;
import com.wode.factory.vo.SupplierTicketFlowVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("SupplierTicketFlowService")
public class SupplierTicketFlowServiceImpl extends FactoryEntityServiceImpl<SupplierTicketFlow> implements SupplierTicketFlowService {
	@Autowired
	SupplierTicketFlowDao dao;
	
	@Override
	public SupplierTicketFlowDao getDao() {
		return dao;
	}


	@Override
	public Long getId(SupplierTicketFlow entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierTicketFlow entity, Long id) {
		entity.setId(id);
	}


	@Override
	public PageInfo<SupplierTicketFlowVo> findPageList(Map<String, Object> map) {

		PageHelper.startPage(map);
		List<SupplierTicketFlowVo> listSaleBill = this.getDao().findPageList(map);
		return new PageInfo(listSaleBill);
	}
}
