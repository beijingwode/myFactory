/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.SupplierEvent;
import com.wode.factory.user.dao.SupplierEventDao;
import com.wode.factory.user.service.SupplierEventService;

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

}