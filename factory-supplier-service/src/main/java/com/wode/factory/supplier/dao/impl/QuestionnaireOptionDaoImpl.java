/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.supplier.dao.QuestionnaireOptionDao;

@Repository("questionnaireOptionDao")
public class QuestionnaireOptionDaoImpl extends BasePageDaoImpl<QuestionnaireOption> implements QuestionnaireOptionDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "QuestionnaireOptionMapper";
	}
	
	@Override
	public Long getId(QuestionnaireOption model) {
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

	@Override
	public int afterInsert(Long id) {
		return getSqlSession().update(getIbatisMapperNamesapce()+".afterInsert", id);
	}

	@Override
	public List<QuestionnaireOption> selectExByQuestionnaire(Long id) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectExByQuestionnaire", id);
	}
}
