/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.model.QuestionnaireText;
import com.wode.factory.user.dao.QuestionnaireTextDao;

@Repository("questionnaireTextDao")
public class QuestionnaireTextDaoImpl extends BasePageDaoImpl<QuestionnaireText> implements QuestionnaireTextDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "QuestionnaireTextMapper";
	}
	
	@Override
	public Long getId(QuestionnaireText model) {
		return model.getId();
	}
}
