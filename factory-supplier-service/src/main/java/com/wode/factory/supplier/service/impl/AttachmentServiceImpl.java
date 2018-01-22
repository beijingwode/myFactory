/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Attachment;
import com.wode.factory.supplier.dao.AttachmentDao;
import com.wode.factory.supplier.query.AttachmentQuery;
import com.wode.factory.supplier.service.AttachmentService;

@Service("attachmentService")
public class AttachmentServiceImpl extends BaseService<Attachment,java.lang.Long> implements  AttachmentService{
	@Autowired
	@Qualifier("attachmentDao")
	private AttachmentDao attachmentDao;
	
	public EntityDao getEntityDao() {
		return this.attachmentDao;
	}
	
	public Page findPage(AttachmentQuery query) {
		return attachmentDao.findPage(query);
	}
	
	/**
	 * 根据条件获取所有符合条件的list
	 * @param map
	 * @return
	 */
	public List<Attachment> findAll(Map map){
		return attachmentDao.findAll(map);
	}
	
	/**
	 * 清除掉上次的附件
	 * @param map
	 */
	public  void removeAllBySupplierid(Map map){
		attachmentDao.removeAllBySupplierid(map);
	}

	@Override
	public void copyByShop(Long supplierId, Long shopId, Long oldId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("oldId", oldId);
		attachmentDao.copyByShop(map);
	}
		
}
