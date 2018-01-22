/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.user.dao.UserContactsApprDao;
import com.wode.factory.user.model.UserContactsAppr;
import com.wode.factory.user.service.UserContactsApprService;
import com.wode.factory.user.vo.UserContactsApprVo;

@Service("userContactsApprService")
public class UserContactsApprServiceImpl extends FactoryEntityServiceImpl<UserContactsAppr> implements  UserContactsApprService{
	@Autowired
	private UserContactsApprDao dao;
	
	@Override
	public UserContactsApprDao getDao() {
		return dao;
	}

	@Override
	public Long getId(UserContactsAppr entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserContactsAppr entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public List<UserContactsApprVo> selectVoByModel(UserContactsAppr query) {
		return dao.selectVoByModel(query);
	}

}