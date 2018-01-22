/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.user.dao.QuestionnaireOptionDao;

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
}
