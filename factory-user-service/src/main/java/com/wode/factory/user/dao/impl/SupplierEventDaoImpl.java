/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.SupplierEvent;
import com.wode.factory.user.dao.SupplierEventDao;

@Repository("supplierEventDao")
public class SupplierEventDaoImpl extends  FactoryBaseDaoImpl<SupplierEvent> implements SupplierEventDao{

    @Override
    public String getIbatisMapperNamesapce() {
        return "SupplierEventMapper";
    }
}