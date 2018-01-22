/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.supplier.dao.QuestionnaireQuestionDao;

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

	@Override
	public int copyFromTemplate(Long oldId, Long newId) {
		Map<String,Long> parameter = new HashMap<String,Long>();
		parameter.put("newId", newId);
		parameter.put("oldId", oldId);
		return getSqlSession().insert(getIbatisMapperNamesapce()+".copyFromTemplate", parameter);
	}

	@Override
	public int deleteByQuestionnaire(Long id) {
		return getSqlSession().delete(getIbatisMapperNamesapce()+".deleteByQuestionnaire", id);
	}
	
	
}
