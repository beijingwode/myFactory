/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.wode.factory.model.QuestionnaireAnswer;

public interface QuestionnaireAnswerDao extends  BasePageDao<QuestionnaireAnswer>{
	int insertSelect(String answers,Long userId);
	Long hasAnswer(Long questionnaireId,Long userId);
}
