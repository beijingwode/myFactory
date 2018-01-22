/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import com.wode.factory.model.ProductQuestionnaire;

public interface ProductQuestionnaireService extends BasePageService<ProductQuestionnaire>{
	ProductQuestionnaire getExById(Long id);

    public Long getQuestionnaireId(Long id);
}
