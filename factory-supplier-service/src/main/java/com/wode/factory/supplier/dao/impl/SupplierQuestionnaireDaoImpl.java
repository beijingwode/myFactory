/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.supplier.dao.SupplierQuestionnaireDao;
import com.wode.factory.supplier.model.SupplierQuestionnaire;

@Repository("supplierQuestionnaireDao")
public class SupplierQuestionnaireDaoImpl extends BasePageDaoImpl<SupplierQuestionnaire> implements SupplierQuestionnaireDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierQuestionnaireMapper";
	}
	
	@Override
	public Long getId(SupplierQuestionnaire model) {
		return model.getId();
	}

	@Override
	public List<SupplierQuestionnaire> selectListCnt(SupplierQuestionnaire model) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectListCnt", model);
	}
}
