/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.supplier.model.SupplierQuestionnaire;

public interface SupplierQuestionnaireDao extends  BasePageDao<SupplierQuestionnaire>{
	public List<SupplierQuestionnaire> selectListCnt(SupplierQuestionnaire model);
}
