/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.vo.ContactsVo;
import com.wode.factory.user.vo.UserContactsVo;

public interface UserContactsDao extends  FactoryBaseDao<UserContacts>{
	public List<UserContactsVo> selectVoByModel(UserContacts query);
	
	/**
	 * 查询好友列表 
	 * @param param {"userId":"当前用户id","friendId":好友id,"firendType":好友类型 （0 临时有备注，1 好友，不传时查询全部）}
	 * @return
	 */
	public List<ContactsVo> selectFriendWithIm(Map<String,Object> param);
	
}
