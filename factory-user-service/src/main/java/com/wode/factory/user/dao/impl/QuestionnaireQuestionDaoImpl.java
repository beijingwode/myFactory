/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.user.dao.QuestionnaireQuestionDao;

@Repository("questionnaireQuestionDao")
public class QuestionnaireQuestionDaoImpl extends BasePageDaoImpl<QuestionnaireQuestion> implements QuestionnaireQuestionDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "QuestionnaireQuestionMapper";
	}
	
	@Override
	public Long getId(QuestionnaireQuestion model) {
		return model.getId();
	}
	
}
