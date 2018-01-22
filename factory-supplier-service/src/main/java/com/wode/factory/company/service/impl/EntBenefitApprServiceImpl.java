package com.wode.factory.company.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EntBenefitApprDao;
import com.wode.factory.company.service.EntBenefitApprService;
import com.wode.factory.model.EntBenefitAppr;

public class EntBenefitApprServiceImpl extends BasePageServiceImpl<EntBenefitAppr>
		implements EntBenefitApprService {

	@Autowired
	EntBenefitApprDao entBenefitApprDao;

	@Override
	protected BasePageDao<EntBenefitAppr> getBaseDao() {
		return entBenefitApprDao;
	}

}
