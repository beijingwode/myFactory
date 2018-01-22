/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.model.QuestionnaireAnswer;
import com.wode.factory.user.dao.ProductQuestionnaireDao;
import com.wode.factory.user.dao.QuestionnaireAnswerDao;
import com.wode.factory.user.service.QuestionnaireAnswerService;

@Service("auestionnaireAnswerService")
public class QuestionnaireAnswerServiceImpl extends BasePageServiceImpl<QuestionnaireAnswer> implements  QuestionnaireAnswerService{
	@Autowired
	private QuestionnaireAnswerDao dao;
	@Autowired
	private ProductQuestionnaireDao qdao;
	
	@Override
	protected QuestionnaireAnswerDao getBaseDao() {
		return dao;
	}

	@Override
	@Transactional
	public void answerQuestion(String answers, Long userId, Long qid) {
		dao.insertSelect(answers, userId);
		
		qdao.addAnswer(qid);
	}

	@Override
	public boolean hasAnswer(Long questionnaireId, Long userId) {
		if(questionnaireId == null) return true;
		if(userId == null) return false;

		Long r = dao.hasAnswer(questionnaireId, userId);
		
		return r!=null;
	}
}
