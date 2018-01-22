/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.IssuedInvoice;

public interface IssuedInvoiceDao extends  FactoryBaseDao<IssuedInvoice>{

	public IssuedInvoice getIssuedInvoice(String suborder);
}
