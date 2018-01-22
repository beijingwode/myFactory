/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.query.UserQuery;
import com.wode.factory.user.vo.ContactsVo;

import cn.org.rapid_framework.page.Page;

public interface UserDao extends  EntityDao<UserFactory,Long>{
	public Page findPage(UserQuery query);
	public void saveOrUpdate(UserFactory entity);
	public int specialSave(UserFactory user);
	public UserFactory getByPhone(String userName);
	public UserFactory getByEmail(String userName);
	public UserFactory findByOrderId(Long orderId);
	public EnterpriseUser getEmpById(Long id) throws DataAccessException;
	public int updEmp(EnterpriseUser entity);
	
	List<Enterprise> findForEmailPostfix();
	
	Enterprise findByEmailPostfix(String emailPostfix);
	

	
	/**
	 * 查询好友列表 
	 * @param param {"userId":"当前用户id","supplierId":企业id,"friendId":好友id 不填时查询全部}
	 * @return
	 */
	public List<ContactsVo> findColleagueWithIm(Map<String,Object> param);
}
