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
import com.wode.factory.mapper.UserFactoryDao;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.vo.UserFactoryVo;

@Service("userFactoryService")
public class UserFactoryServiceImpl implements  UserFactoryService{
	@Autowired
	UserFactoryDao userFactoryDao;

	@Override
	public List<UserFactory> selectByModel(UserFactory model) {
		return userFactoryDao.selectByModel(model);
	}

	@Override
	public void update(UserFactory model) {
		userFactoryDao.update(model);
	}

	@Override
	public List<UserIm> selectByUserId(UserIm model) {
		return userFactoryDao.selectByUserId(model);
	}

	@Override
	public PageInfo<UserFactoryVo> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<UserFactoryVo> list = userFactoryDao.getSupplierUserVo(params);
		return new PageInfo<UserFactoryVo>(list);
	}

	@Override
	public List<UserFactory> selectNoPhone(UserFactory model) {
		return userFactoryDao.selectNoPhone(model);
	}

	@Override
	public List<UserFactory> selectNoNickName(UserFactory model) {
		return userFactoryDao.selectNoNickName(model);
	}

	@Override
	public UserFactory getById(Long id) {
		return userFactoryDao.getById(id);
	}

	@Override
	public List<UserFactory> getYYTUser() {
		return userFactoryDao.getYYTUser();
	}

	@Override
	public void deleteUserWeixinByUserId(Long userId) {
		userFactoryDao.deleteUserWeixinByUserId(userId);
	}

	@Override
	public void delete(Long id) {
		userFactoryDao.delete(id);
	}
}
