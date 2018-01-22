/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.QuestionnaireText;
import com.wode.factory.supplier.dao.QuestionnaireTextDao;
import com.wode.factory.supplier.service.QuestionnaireTextService;

@Service("questionnaireTextService")
public class QuestionnaireTextServiceImpl extends BasePageServiceImpl<QuestionnaireText> implements  QuestionnaireTextService{
	@Autowired
	private QuestionnaireTextDao dao;
	
	@Override
	protected QuestionnaireTextDao getBaseDao() {
		return dao;
	}
}
