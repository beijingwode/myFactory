/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.user.dao.QuestionnaireOptionDao;
import com.wode.factory.user.service.QuestionnaireOptionService;

@Service("questionnaireOptionService")
public class QuestionnaireOptionServiceImpl extends BasePageServiceImpl<QuestionnaireOption> implements  QuestionnaireOptionService{
	@Autowired
	private QuestionnaireOptionDao dao;
	
	@Override
	protected QuestionnaireOptionDao getBaseDao() {
		return dao;
	}
}
