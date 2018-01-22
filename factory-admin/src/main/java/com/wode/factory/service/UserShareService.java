package com.wode.factory.service;

import com.wode.factory.model.UserShare;

public interface UserShareService extends FactoryEntityService<UserShare>{

	UserShare getByUserId(Long id);
}
