/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Attachment;
import com.wode.factory.supplier.query.AttachmentQuery;

public interface AttachmentDao extends  EntityDao<Attachment,Long>{
	public Page findPage(AttachmentQuery query);
	public void saveOrUpdate(Attachment entity);
	
	/**
	 * 根据条件获取所有符合条件的list
	 * @param map
	 * @return
	 */
	public List<Attachment> findAll(Map map);

	/**
	 * 清除掉上次的附件
	 * @param map
	 */
	public  void removeAllBySupplierid(Map map);

	public void copyByShop(Map<String,Long> map);
}
