/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.EntBenefitApprDao;
import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.service.EntBenefitApprService;
import com.wode.factory.vo.AttributeVo;
import com.wode.factory.vo.EntBenefitApprVO;

@Service("entBenefitApprService")
public class EntBenefitApprServiceImpl implements  EntBenefitApprService{
	@Autowired
	private EntBenefitApprDao entBenefitApprDao;

	@Override
	public PageInfo<EntBenefitApprVO> findPage(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<EntBenefitApprVO> list = entBenefitApprDao.findPage(params);
		return new PageInfo<EntBenefitApprVO>(list);
	}

	@Override
	public int updateSelect(EntBenefitAppr pojo) {
		return entBenefitApprDao.updateSelect(pojo);
	}

	@Override
	public EntBenefitAppr getById(Long id) {
		// TODO Auto-generated method stub
		return this.entBenefitApprDao.getById(id);
	}

	@Override
	public void insert(EntBenefitAppr pojo) {
		this.entBenefitApprDao.insert(pojo);
	}
	
}
