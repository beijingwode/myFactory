/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.ProductQuestionnaireDao;
import com.wode.factory.user.service.ProductQuestionnaireService;
import com.wode.factory.user.service.QuestionnaireOptionService;
import com.wode.factory.user.service.QuestionnaireQuestionService;

@Service("productQuestionnaireService")
public class ProductQuestionnaireServiceImpl extends BasePageServiceImpl<ProductQuestionnaire> implements  ProductQuestionnaireService{
	@Autowired
	private ProductQuestionnaireDao dao;
	@Autowired
	private QuestionnaireQuestionService qs;
	@Autowired
	private QuestionnaireOptionService os;

	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;
	
	@Override
	protected ProductQuestionnaireDao getBaseDao() {
		return dao;
	}


	@Override
	public Long getQuestionnaireId(Long id) {
		return productDao.getQuestionnaireId(id);
	}
	
	@Override
	public ProductQuestionnaire getExById(Long id) {
		ProductQuestionnaire rtn = this.getById(id);
		
		if(rtn != null) {
			// 获取问题列表
			QuestionnaireQuestion q = new QuestionnaireQuestion();
			q.setQuestionnaireId(rtn.getId());
			List<QuestionnaireQuestion> lsQ =  qs.selectByModel(q);
			
			rtn.setListQuestion(lsQ);
			
			// 获取选项列表
			QuestionnaireOption o = new QuestionnaireOption();
			o.setQuestionnaireId(rtn.getId());
			List<QuestionnaireOption> lsO =  os.selectByModel(o);
			
			for (QuestionnaireOption qo : lsO) {
				for (QuestionnaireQuestion qq : lsQ) {
					if(qo.getQuestionId().equals(qq.getId())) {
						qq.getListOption().add(qo);
						break;
					}
				}
			}
			
		}
		return rtn;
	}
}
