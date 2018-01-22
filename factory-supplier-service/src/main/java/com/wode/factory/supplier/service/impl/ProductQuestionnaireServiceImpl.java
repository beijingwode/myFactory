/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.supplier.dao.ProductQuestionnaireDao;
import com.wode.factory.supplier.service.ProductQuestionnaireService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.QuestionnaireOptionService;
import com.wode.factory.supplier.service.QuestionnaireQuestionService;

@Service("productQuestionnaireService")
public class ProductQuestionnaireServiceImpl extends BasePageServiceImpl<ProductQuestionnaire> implements  ProductQuestionnaireService{
	@Autowired
	private ProductQuestionnaireDao dao;
	@Autowired
	private QuestionnaireQuestionService qs;
	@Autowired
	private QuestionnaireOptionService os;
	@Autowired
	private ProductService ps;
	
	@Override
	protected ProductQuestionnaireDao getBaseDao() {
		return dao;
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
			List<QuestionnaireOption> lsO =  os.selectExByQuestionnaire(rtn.getId());
			
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
	public void delTempByProductId(Long productId) {
		this.dao.delTempByProductId(productId);
	}


	@Override
	@Transactional
	public Long stop(Long id) {
		// 更新问卷状态
		ProductQuestionnaire pq = this.getById(id);
		pq.setStatus(3);			//3:已结束
		pq.setEndDate(pq.getEndDate()==null?(new Date()):pq.getEndDate());
		this.update(pq);
		
		// 更新商品临时表
		dao.clearAppr(pq.getProductId(), pq.getId());
		
		// 更新正式商品
		Product p =ps.getById(pq.getProductId());
		if(p!=null) {
			p.setQuestionnaireId(-1L);
			ps.update(p);
			if(p.getIsMarketable()!=null && p.getIsMarketable()==1) {
				return p.getId();
			}
		}
		return null;
	}
}
