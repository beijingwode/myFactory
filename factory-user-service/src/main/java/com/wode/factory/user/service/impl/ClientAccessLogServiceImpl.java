/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Product;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.dao.ClientAccessLogDao;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.ProductService;
import com.wode.factory.user.service.UserService;

@Service("clientAccessLogService")
public class ClientAccessLogServiceImpl extends MongoBaseService<ClientAccessLog>
        implements ClientAccessLogService {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientAccessLogDao clientAccessLogDao;
    
	@Override
	public ClientAccessLogDao getMongoDao() {
		return clientAccessLogDao;
	}

	@Override
	public ClientAccessLog saveNormal(Integer type, String key, String text, Long userId,UserFactory user,String uuid,String ip) {
		return saveNormal(type, key, text, userId, user, uuid, ip, null, null);
	}


	@Override
	public ClientAccessLog saveActivity(String app, String url, Long userId, UserFactory user, String uuid, String ip) {
		return saveNormal(ClientAccessLog.ACCESS_TYPE_ACTIVITY, app, url, userId, user, uuid, ip);
	}
	
	@Override
	public ClientAccessLog saveNormal(Integer type, String key, String text, Long userId, UserFactory user, String uuid,
			String ip, Integer hitCount, Double maxScore) {
		
		ClientAccessLog model = new ClientAccessLog();
		model.setAccessKey(key);
		model.setAccessText(text);
		model.setAccessType(type);
		model.setCreatTime(new Date());
		model.setUserId(userId);
		if(user != null) {
			model.setUser(user);
		} else if(userId!=null) {
			model.setUser(userService.getById(userId));
		}
		model.setUuid(uuid);
		model.setIpAddr(ip);
		model.setHitCount(hitCount);
		model.setMaxScore(maxScore);
		getMongoDao().insert(model);
		return model;
	}
	
	@Override
	public ClientAccessLog saveProduct(Integer type, Long skuId, String text, Long userId,UserFactory user, Long productId,Product p,String uuid,String ip) {
		ClientAccessLog model = new ClientAccessLog();
		model.setAccessKey(skuId+"");
		model.setAccessText(text);
		model.setAccessType(type);
		model.setCreatTime(new Date());
		model.setUserId(userId);
		if(user != null) {
			model.setUser(user);
		} else if(userId!=null) {
			model.setUser(userService.getById(userId));
		}
		model.setProductId(productId);
		if(p != null) {
			model.setProduct(p);
		} else if(productId!=null) {
			model.setProduct(productService.getById(productId));
		}
		getMongoDao().insert(model);
		model.setUuid(uuid);
		model.setIpAddr(ip);
		return model;
	}

	@Override
	public Long[] getDayPvCnt(String date) {
		return getMongoDao().getDayPvCnt(date);
	}

	@Override
	public List<JSONObject> getDaySearchKeyCnt(String date) {
		return getMongoDao().getDaySearchKeyCnt(date);
	}
	

}
