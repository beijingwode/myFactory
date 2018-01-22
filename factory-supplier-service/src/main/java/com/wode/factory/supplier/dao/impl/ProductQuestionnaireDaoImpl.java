/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.supplier.dao.ProductQuestionnaireDao;

@Repository("productQuestionnaireDao")
public class ProductQuestionnaireDaoImpl extends BasePageDaoImpl<ProductQuestionnaire> implements ProductQuestionnaireDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductQuestionnaireMapper";
	}
	
	@Override
	public Long getId(ProductQuestionnaire model) {
		return model.getId();
	}

	@Override
	public void delTempByProductId(Long productId) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".delTempByProductId",productId);
	}

	@Override
	public void clearAppr(Long productId, Long qid) {
		Map<String,Long> para = new HashMap<String,Long>();
		para.put("productId", productId);
		para.put("qid", qid);
		
		this.getSqlSession().update(getIbatisMapperNamesapce()+".clearAppr",para);
	}
}
