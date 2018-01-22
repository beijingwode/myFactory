/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.wode.factory.model.ProductQuestionnaire;

public interface ProductQuestionnaireDao extends  BasePageDao<ProductQuestionnaire>{
	ProductQuestionnaire getProductById(Long productId);
	int addAnswer(Long id);
}
