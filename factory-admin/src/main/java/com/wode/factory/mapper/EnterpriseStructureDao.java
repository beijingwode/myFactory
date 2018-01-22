package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.EnterpriseStructure;

public interface EnterpriseStructureDao {

	public void insert(EnterpriseStructure enterpriseStructure);
	
	public List<EnterpriseStructure> selectByModel(EnterpriseStructure enterpriseStructure);
	
	public void deleteOld(Long id);
}
