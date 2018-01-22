/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Resource;
import com.wode.factory.model.Role;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.dao.UserDao;
import com.wode.factory.supplier.query.RoleQuery;
import com.wode.factory.supplier.query.UserFactoryQuery;
import com.wode.factory.supplier.service.UserService;

import cn.org.rapid_framework.page.Page;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseService<UserFactory, java.lang.Long> implements UserService {
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
    @Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;

	/**
	 * 日志记录对象
	 */
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	public EntityDao<?, ?> getEntityDao() {
		return this.userDao;
	}

	public Page<?> findPage(UserFactoryQuery query) {
		return userDao.findPage(query);
	}

	@Override
	public UserFactory getById(Long userId) {
		String json = redisUtil.getMapData(RedisConstant.FACTORY_USER_MAP, userId+"");
		if(StringUtils.isEmpty(json)) {
			UserFactory user = super.getById(userId);
			redisUtil.setMapData(RedisConstant.FACTORY_USER_MAP, userId+"", JSONObject.toJSONString(user));
			return user;
		} else {
			return JsonUtil.getObject(json, UserFactory.class);
		}
	}

	@Override
	public void update(UserFactory entity) throws DataAccessException{
		redisUtil.delMapData(RedisConstant.FACTORY_USER_MAP, entity.getId()+"");
		super.update(entity);
	}

	@Override
	public void saveOrUpdate(UserFactory entity) {
		if(entity.getId() != null) {
			redisUtil.delMapData(RedisConstant.FACTORY_USER_MAP, entity.getId()+"");
		}
		userDao.saveOrUpdate(entity);
	}

	public String reader(String name, String proname) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(proname);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p.getProperty(name);
	}

	@Override
	public UserFactory getByEmail(String toEmail) {
		return userDao.getByEmail(toEmail);
	}

	@Override
	public UserFactory getByPhone(String toPhone) {
		return userDao.getByPhone(toPhone);
	}
	@Override
	public UserFactory getByUserName(String toUserName) {
		// TODO Auto-generated method stub
		return userDao.getByUserName(toUserName);
	}

	@Override
	public UserFactory getByEmailNew(String toEmail) {
		return userDao.getByEmailNew(toEmail);
	}

	@Override
	public void saveId(UserFactory fus) {
		userDao.saveId(fus);
	}

	@Override
	public void addEditor(UserFactory admin, RoleQuery editor,Long userId,String empDefultAvatar,String shopLink) {
		
		UserFactory addUser = new UserFactory();
		addUser.setId(userId);
		addUser.setUserName(editor.getUserName());
		addUser.setEmail(editor.getEmail());
		addUser.setPhone(editor.getPhone());
		addUser.setType(3);//1:买家 2:卖家 3:卖家员工
		addUser.setCreatTime(new Date());
		if (!StringUtils.isNullOrEmpty(editor.getRealName())) {
			addUser.setNickName(editor.getRealName());
		}else{
			addUser.setNickName(editor.getUserName());
			if(addUser.getNickName().equals(addUser.getPhone())) {
				String nickName=addUser.getPhone();
				if(nickName.length()>4) {
					nickName="1***"+nickName.substring(nickName.length()-4);
					addUser.setNickName(nickName);
				}
			}
		}
		addUser.setRealName(editor.getRealName());
		addUser.setUsable(1);//0禁用；1可用
		addUser.setUserLevel(0);//用户等级
		addUser.setEnabled(0);//0未激活；1已激活
		addUser.setEmployeeType(0);//1：员工   2：亲友 0：普通
		addUser.setSupplierId(admin.getSupplierId());
		addUser.setRole(editor.getId());
		addUser.setEnabled(1);
		addUser.setAvatar(empDefultAvatar);
		addUser.setShopLink(shopLink);
		saveId(addUser);
		logger.info(admin.getUserName() + " added an editor named " + editor.getUserName());
	}

	@Override
	public void saveRole(UserFactory admin, Role role, Integer[] adds) {
		role.setSupplierId(admin.getSupplierId());
		if(role.getId() != null) {
			userDao.updateRole(role);
		} else {
			userDao.addRole(role);
		}
		editRoleAuth(admin, role, adds);
	}

	private void editRoleAuth(UserFactory admin, Role role, Integer[] adds) {
		if (role != null || role.getId() != null) {
			// 删除之后插入
			userDao.delAuthResources(role.getId(), null);
			if (adds != null && adds.length > 0)
				for (Integer resource : adds) {
					userDao.addAuthResources(role.getId(), resource);
					logger.info(admin.getUserName() + "added auth resource {0} for " + role.getName(), resource);
				}
		}
	}

	@Override
	public List<Role> selectRoles(Long id, Long enterpriseId) {
		// TODO Auto-generated method stub
		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("id", id);
		roleMap.put("supplierId", enterpriseId);
		return userDao.selectRoles(roleMap);
	}

	@Override
	public List<RoleQuery> selectRoleAndUserName(RoleQuery roleQuery) {
		// TODO Auto-generated method stub
		return this.userDao.selectRoleAndUserName(roleQuery);
	}

	@Override
	public Role getRole(Integer id, Long enterpriseId) {
		return userDao.getRole(id, enterpriseId);
	}

	/* 删除角色
		 */
	@Override
	public void deleteRole(Long supplierId, RoleQuery roleQuery) {
		// role表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		params.put("id", roleQuery.getId());
		List<Role> roles = this.userDao.selectRoles(params);
		if (!roles.isEmpty() && roles.size() > 0) {
			// 删除roleResource
			this.userDao.delAuthResources(roleQuery.getId(), null);
			// 删除role
			Role role = new Role();
			role.setId(roleQuery.getId());
			role.setSupplierId(supplierId);
			this.userDao.deleteRole(role);
		}
	}

	@Override
	public List<UserFactory> getUserList(UserFactory user) {
		List<UserFactory> users = this.userDao.getUserList(user);
		return users;
	}

	@Override
	public void deleteUserFactoryById(Long id) {
		this.userDao.deleteById(id);
	}

	@Override
	public List<Resource> getAuth(Long userId) {
		return userDao.getAuth(userId);
	}

	@Override
	public PageInfo<RoleQuery> findRolePage(RoleQuery roleQuery) {
		return this.userDao.findRolePage(roleQuery);
	}

	@Override
	public PageInfo<RoleQuery> findUserNamePage(RoleQuery roleQuery) {
		return this.userDao.findUserNamePage(roleQuery);
	}
}
