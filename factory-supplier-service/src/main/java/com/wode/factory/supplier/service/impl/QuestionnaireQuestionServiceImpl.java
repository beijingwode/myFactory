/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.supplier.dao.QuestionnaireQuestionDao;
import com.wode.factory.supplier.service.QuestionnaireQuestionService;

@Service("questionnaireQuestionService")
public class QuestionnaireQuestionServiceImpl extends BasePageServiceImpl<QuestionnaireQuestion> implements  QuestionnaireQuestionService{
	@Autowired
	private QuestionnaireQuestionDao dao;
	
	@Override
	protected QuestionnaireQuestionDao getBaseDao() {
		return dao;
	}

	@Override
	public int copyFromTemplate(Long oldId, Long newId) {
		return dao.copyFromTemplate(oldId, newId);
	}

	@Override
	public int deleteByQuestionnaire(Long id) {
		return dao.deleteByQuestionnaire(id);
	}
}
