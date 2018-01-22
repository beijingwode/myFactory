/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Supplier;

public interface SupplierDao extends  EntityDao<Supplier,Long>{

	List<Supplier> findByManagerId(Map<String, String> queryMap);
}
