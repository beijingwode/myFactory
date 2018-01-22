package com.wode.factory.company.dao.impl;


import org.springframework.dao.DataAccessException;

import com.wode.factory.company.dao.EntBenefitApprDao;
import com.wode.factory.model.EmpSeasonAct;
import com.wode.factory.model.EntBenefitAppr;

public class EntBenefitApprDaoImpl extends BasePageDaoImpl<EntBenefitAppr> implements EntBenefitApprDao {

	@Override
	public String getIbatisMapperNamesapce() {
		return "EntBenefitApprMapper";
	}

	@Override
	public void saveOrUpdate(EntBenefitAppr entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Long getId(EntBenefitAppr model) {
		return model.getId();
	}
}
