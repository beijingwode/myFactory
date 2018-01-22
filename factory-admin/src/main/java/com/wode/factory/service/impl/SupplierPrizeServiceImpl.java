/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.SupplierEvent;
import com.wode.factory.model.SupplierPrize;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierEventDao;
import com.wode.factory.mapper.SupplierPrizeDao;
import com.wode.factory.service.SupplierEventService;
import com.wode.factory.service.SupplierPrizeService;

@Service("supplierPrizeService")
public class SupplierPrizeServiceImpl extends  FactoryEntityServiceImpl<SupplierPrize> implements SupplierPrizeService{

    @Autowired
    private SupplierPrizeDao dao;
    @Override
    public SupplierPrizeDao getDao() {
        return dao;
    }

    @Override
    public Long getId(SupplierPrize entity) {
        return entity.getId();
    }

    @Override
    public void setId(SupplierPrize entity, Long id) {
        if(entity!=null) {
            entity.setId(id);
        }
    }

	@Override
	public PageInfo<SupplierPrize> findInfoPageList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierPrize> list = dao.findInfoPageList(params);
		return new PageInfo(list);
	}


}