
package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.mapper.SupplierCategoryVoDao;
import com.wode.factory.service.SupplierCategoryVoService;
import com.wode.factory.vo.SupplierCategoryVo;

@Service("SupplierCategoryVoService")
public class SupplierCategoryVoServiceImpl extends EntityServiceImpl<SupplierCategoryVo,Long> implements SupplierCategoryVoService {
	
	@Autowired
	private SupplierCategoryVoDao dao;

	@Override
	public List<SupplierCategoryVo> find(SupplierCategoryVo entity) {
		return dao.find(entity);
	}

	@Override
	public EntityDao<SupplierCategoryVo, Long> getDao() {
		return dao;
	}	
}
