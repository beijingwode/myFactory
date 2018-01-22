/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.user.dao.ProductQuestionnaireDao;

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
	public ProductQuestionnaire getProductById(Long productId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getProductById", productId);
	}

	@Override
	public int addAnswer(Long id) {
		return getSqlSession().update(getIbatisMapperNamesapce()+".addAnswer", id);
	}
}
