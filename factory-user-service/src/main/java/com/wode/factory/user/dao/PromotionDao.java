/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.query.PromotionQuery;

public interface PromotionDao extends  EntityDao<Promotion,Long>{
	public Page findPage(PromotionQuery query);
	public void saveOrUpdate(Promotion entity);
	public Integer findAllCount(Map<String, Object> map);
	public List<Promotion> findByMap(Map<String, Object> map);
	public Integer findAllProductCount(Map<String, Object> map);
	public List<PromotionProduct> findProductByMap(Map<String, Object> map);

}
