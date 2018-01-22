package com.wode.factory.mapper;

import com.wode.factory.model.UserShare;

public interface UserShareDao extends FactoryBaseDao<UserShare>{

	void deleteBySupplierId(Long supplierId);
	void updateUserBySupplierId(Long supplierId);
}
