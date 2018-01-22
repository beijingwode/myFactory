/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.constant.UserConstant;
import com.wode.common.frame.base.BaseService;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.dao.UserDao;
import com.wode.factory.user.query.UserQuery;
import com.wode.factory.user.service.UserService;

import cn.org.rapid_framework.page.Page;

@Service("userService")
public class UserServiceImpl extends BaseService<UserFactory,java.lang.Long> implements  UserService{
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
    @Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	
	public UserDao getEntityDao() {
		return this.userDao;
	}
	
	public Page findPage(UserQuery query) {
		return userDao.findPage(query);
	}

	@Override
	public UserFactory getById(Long userId) {
		String json = redisUtil.getMapData(RedisConstant.FACTORY_USER_MAP, userId+"");
		if(StringUtils.isEmpty(json)) {
			UserFactory user = this.selectById(userId);
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
		super.saveOrUpdate(entity);
	}
	
	@Override
	public UserFactory selectById(Long userId) {
		return userDao.getById(userId);
	}

	@Override
	public int specialSave(UserFactory user) {
		redisUtil.delMapData(RedisConstant.FACTORY_USER_MAP, user.getId()+"");
		return userDao.specialSave(user);
	}
	@Override
	public ActResult<UserFactory> findByPhone(String userName) {
		ActResult<UserFactory> re = new ActResult<UserFactory>();
		UserFactory user = userDao.getByPhone(userName);
		if(StringUtils.isNullOrEmpty(user)){
			re.setSuccess(false);
			re.setMsg("该手机号未绑定帐号");
		}else
			re.setData(user);
		return re;
	}

	@Override
	public ActResult<UserFactory> findByEmail(String userName) {
		ActResult<UserFactory> re = new ActResult<UserFactory>();
		UserFactory user = userDao.getByEmail(userName);
		if(StringUtils.isNullOrEmpty(user)){
			re.setSuccess(false);
			re.setMsg("该邮箱未绑定帐号");
		}else
			re.setData(user);
		return re;
	}

	@Override
	public UserFactory findByOrderId(Long orderId) {
		return userDao.findByOrderId(orderId);
	}

	@Override
	public EnterpriseUser getEmpById(Long id) throws DataAccessException {
		return userDao.getEmpById(id);
	}

	@Override
	public int updEmp(EnterpriseUser entity) {
		return userDao.updEmp(entity);
	}

	@Override
	public List<String> emailPostfixs() {
		String json = redisUtil.getData(UserConstant.EMAIL_POSTFIXS);
		if(StringUtils.isEmpty(json)) {
			List<String> rtn = new ArrayList<String>();
			List<Enterprise> ls= getEntityDao().findForEmailPostfix();
			for (Enterprise enterprise : ls) {
				if(!StringUtils.isEmpty(enterprise.getEmailPostfix1())) {
					rtn.add(enterprise.getEmailPostfix1());
				}
				if(!StringUtils.isEmpty(enterprise.getEmailPostfix2())) {
					rtn.add(enterprise.getEmailPostfix2());
				}
				if(!StringUtils.isEmpty(enterprise.getEmailPostfix3())) {
					rtn.add(enterprise.getEmailPostfix3());
				}
			}
			
			redisUtil.setData(UserConstant.EMAIL_POSTFIXS, JsonUtil.toJsonString(rtn));
			return rtn;
		} else {
			return JsonUtil.getList(json, String.class);
		}
	}

	@Override
	public Enterprise findByEmailPostfix(String emailPostfix) {
		return getEntityDao().findByEmailPostfix(emailPostfix);
	}


	
}
