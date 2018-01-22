/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.supplier.dao.QuestionnaireOptionDao;
import com.wode.factory.supplier.service.QuestionnaireOptionService;

@Service("questionnaireOptionService")
public class QuestionnaireOptionServiceImpl extends BasePageServiceImpl<QuestionnaireOption> implements  QuestionnaireOptionService{
	@Autowired
	private QuestionnaireOptionDao dao;
	
	@Override
	protected QuestionnaireOptionDao getBaseDao() {
		return dao;
	}

	@Override
	public int copyFromTemplate(Long oldId, Long newId) {
		return dao.copyFromTemplate(oldId, newId);
	}

	@Override
	public int deleteByQuestionnaire(Long id) {
		return dao.deleteByQuestionnaire(id);
	}

	@Override
	public int afterInsert(Long id) {
		return dao.afterInsert(id);
	}

	@Override
	public List<QuestionnaireOption> selectExByQuestionnaire(Long id) {
		return dao.selectExByQuestionnaire(id);
	}
}
