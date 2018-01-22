/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.vo.ContactsVo;
import com.wode.factory.user.vo.UserContactsVo;

public interface UserContactsService extends FactoryEntityService<UserContacts>{

	public UserContacts selectOneByModel(UserContacts query);
	
	/**
	 * 普通用户的亲友列表
	 * @param userId 普通用户id
	 * @return
	 */
	public ActResult<List<UserContactsVo>> normalUserFriendsList(Long userId);
	/**
	 * 员工用户的亲友列表
	 * @param empId
	 * @return
	 */
	public ActResult<List<UserContactsVo>> empUserFriendsList(Long empId);
	
	/**
	 * 员工查询联系人列
	 * @param supplierId
	 * @param userId
	 * @return
	 */
	public List<ContactsVo> selectContactsByEmployee(Long supplierId,Long userId);
	
	/**
	 * 获取好友列表
	 * @param userId
	 * @return
	 */
	public List<ContactsVo> selectContactsByUser(Long userId);
	/**
	 * 获取某个好友信息
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public ContactsVo selectContactByUser(Long userId,Long friendId);

	/**
	 * 员工查询联系人列
	 * @param supplierId
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public ContactsVo findColleague(Long supplierId, Long userId, Long friendId);
	
	/**
	 * 查询个人信息
	 * @param userId
	 * @return
	 */
	public ContactsVo findOneSelf(Long userId);
	
	/**
	 * 创建团购群聊
	 * @param userId
	 * @param shoppingGroupId
	 * @return
	 */
	public String createGroupBuyGroup(Long userId, String shoppingGroupId) ;
}
