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
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.supplier.dao.SupplierQuestionnaireDao;
import com.wode.factory.supplier.model.SupplierQuestionnaire;
import com.wode.factory.supplier.service.QuestionnaireOptionService;
import com.wode.factory.supplier.service.QuestionnaireQuestionService;
import com.wode.factory.supplier.service.SupplierQuestionnaireService;

@Service("supplierQuestionnaireService")
public class SupplierQuestionnaireServiceImpl extends BasePageServiceImpl<SupplierQuestionnaire> implements  SupplierQuestionnaireService{
	@Autowired
	private SupplierQuestionnaireDao dao;
	@Autowired
	private QuestionnaireQuestionService qs;
	@Autowired
	private QuestionnaireOptionService os;
	
	@Override
	protected SupplierQuestionnaireDao getBaseDao() {
		return dao;
	}

	@Override
	public SupplierQuestionnaire getExById(Long id) {
		SupplierQuestionnaire rtn = this.getById(id);
		
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

	@Override
	public List<SupplierQuestionnaire> selectListCnt(SupplierQuestionnaire model) {
		return dao.selectListCnt(model);
	}

	@Override
	@Transactional
	public void delExById(Long id) {
		// 删除模板
		this.removeById(id);
		
		// 删除问题、
		qs.deleteByQuestionnaire(id);
		
		// 删除问题选项
		os.deleteByQuestionnaire(id);
	}
}
