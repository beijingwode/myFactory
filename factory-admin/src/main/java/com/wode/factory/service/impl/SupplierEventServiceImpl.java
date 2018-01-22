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
import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.model.UserSignRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierEventDao;
import com.wode.factory.service.SupplierEventService;

@Service("supplierEventService")
public class SupplierEventServiceImpl extends  FactoryEntityServiceImpl<SupplierEvent> implements SupplierEventService{

    @Autowired
    private SupplierEventDao dao;
    @Override
    public SupplierEventDao getDao() {
        return dao;
    }

    @Override
    public Long getId(SupplierEvent entity) {
        return entity.getId();
    }

    @Override
    public void setId(SupplierEvent entity, Long id) {
        if(entity!=null) {
            entity.setId(id);
        }
    }

	@Override
	public PageInfo<SupplierEvent> findInfoPageList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierEvent> list = dao.findInfoPageList(params);
		Map<String,Object> map = new HashMap<String,Object>();
		for (SupplierEvent supplierEvent : list) {
			map.put("acticityId", supplierEvent.getId());
			Integer count = dao.getSignCnt(map);
			supplierEvent.setSignCnt(count);
		}
		return new PageInfo(list);
	}

	@Override
	public List<SupplierEvent> getUserManagerGroup() {
		return dao.getUserManagerGroup();
	}

	@Override
	public List<UserSignRecord> getUserSignMsg(Map<String, Object> map) {
		return dao.getUserSignMsg(map);
	}

	@Override
	public List<UserPrizeRecord> getUserPrizeMsg(Map<String, Object> map) {
		return dao.getUserPrizeMsg(map);
	}

}