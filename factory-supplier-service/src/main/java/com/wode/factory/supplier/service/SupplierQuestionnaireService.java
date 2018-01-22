/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.supplier.model.SupplierQuestionnaire;

public interface SupplierQuestionnaireService extends BasePageService<SupplierQuestionnaire>{
	SupplierQuestionnaire getExById(Long id);
	void delExById(Long id);
	List<SupplierQuestionnaire> selectListCnt(SupplierQuestionnaire model);
}
