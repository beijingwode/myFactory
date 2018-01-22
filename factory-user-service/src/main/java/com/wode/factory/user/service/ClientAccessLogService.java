/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Product;
import com.wode.factory.model.UserFactory;

public interface ClientAccessLogService {

	ClientAccessLog saveNormal(Integer type,String key,String text,Long userId,UserFactory user,String uuid,String ip);
	ClientAccessLog saveNormal(Integer type,String key,String text,Long userId,UserFactory user,String uuid,String ip,Integer hitCnt,Double maxScore);
	ClientAccessLog saveProduct(Integer type,Long skuId,String text,Long userId,UserFactory user,Long productId,Product p,String uuid,String ip);
	ClientAccessLog saveActivity(String app,String url,Long userId,UserFactory user,String uuid,String ip);
	Long[] getDayPvCnt(String date);
	List<JSONObject> getDaySearchKeyCnt(String date);
}
