/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.user.dao.QuestionnaireQuestionDao;
import com.wode.factory.user.service.QuestionnaireQuestionService;

@Service("questionnaireQuestionService")
public class QuestionnaireQuestionServiceImpl extends BasePageServiceImpl<QuestionnaireQuestion> implements  QuestionnaireQuestionService{
	@Autowired
	private QuestionnaireQuestionDao dao;
	
	@Override
	protected QuestionnaireQuestionDao getBaseDao() {
		return dao;
	}

}
