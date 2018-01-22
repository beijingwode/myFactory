package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.vo.UserFactoryVo;

public interface UserFactoryDao {

	UserFactory getById(Long id);
	
	UserFactory getSupplierUser(Long supplierId);

	void update(UserFactory model);
	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	List<UserFactory> selectByModel(UserFactory model);
	
	List<UserFactory> selectNoPhone(UserFactory model);
	List<UserFactory> selectNoNickName(UserFactory model);
	
	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	List<UserIm> selectByUserId(UserIm model);
	
	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	List<UserFactoryVo> getSupplierUserVo(Map<String, Object> params);

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
