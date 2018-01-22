/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.ProductQuestionnaire;

public interface ProductQuestionnaireService extends BasePageService<ProductQuestionnaire>{
	ProductQuestionnaire getExById(Long id);
	void delTempByProductId(Long productId);
	
	/**
	 * 停止问卷收集。如果该问卷正在被试用（商品在售），则返回商品id,商品需要刷新
	 * @param id
	 * @return
	 */
	Long stop(Long id);
}
