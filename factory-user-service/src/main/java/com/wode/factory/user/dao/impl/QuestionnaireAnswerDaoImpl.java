/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.factory.model.QuestionnaireAnswer;
import com.wode.factory.user.dao.QuestionnaireAnswerDao;

@Repository("questionnaireAnswerDao")
public class QuestionnaireAnswerDaoImpl extends BasePageDaoImpl<QuestionnaireAnswer> implements QuestionnaireAnswerDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "QuestionnaireAnswerMapper";
	}
	
	@Override
	public Long getId(QuestionnaireAnswer model) {
		return model.getId();
	}

	@Override
	public int insertSelect(String answers,Long userId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("answers", answers);
		para.put("userId", userId);
		
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insertSelect", para);
	}

	@Override
	public Long hasAnswer(Long questionnaireId, Long userId) {
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("questionnaireId", questionnaireId);
		para.put("userId", userId);
		
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".hasAnswer", para);
	}
}
