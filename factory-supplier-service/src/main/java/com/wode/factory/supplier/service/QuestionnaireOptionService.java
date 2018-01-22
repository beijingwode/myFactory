/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.QuestionnaireOption;

public interface QuestionnaireOptionService extends BasePageService<QuestionnaireOption>{
	int copyFromTemplate(Long oldId,Long newId);
	int deleteByQuestionnaire(Long id);
	int afterInsert(Long id);
	List<QuestionnaireOption> selectExByQuestionnaire(Long id);
}
