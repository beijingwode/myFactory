/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import com.wode.factory.model.QuestionnaireAnswer;

public interface QuestionnaireAnswerService extends BasePageService<QuestionnaireAnswer>{
	
	void answerQuestion(String answers,Long userId,Long qid);

	boolean hasAnswer(Long questionnaireId,Long userId);
}
