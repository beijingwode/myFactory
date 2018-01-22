/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.vo.UserFactoryVo;

public interface UserFactoryService{

	UserFactory getById(Long id);
	List<UserFactory> selectByModel(UserFactory model);
	List<UserFactory> selectNoPhone(UserFactory model);
	List<UserFactory> selectNoNickName(UserFactory model);
	void update(UserFactory model);
	

	List<UserIm> selectByUserId(UserIm model);
	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<UserFactoryVo> findList(Map<String, Object> params);
	
	/**
	 * 获取运营测试用户 yyt1~yyt20@wo-de.com
	 * @return
	 */
	List<UserFactory> getYYTUser();
	

	/**
	 * 删除微信绑定
	 */
	void deleteUserWeixinByUserId(Long userId);

	void delete(Long id);
}
