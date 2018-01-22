/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Attachment;
import com.wode.factory.supplier.dao.AttachmentDao;
import com.wode.factory.supplier.query.AttachmentQuery;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends BaseDao<Attachment,java.lang.Long> implements AttachmentDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "AttachmentMapper";
	}
	
	public void saveOrUpdate(Attachment entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(AttachmentQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	/**
	 * 根据条件获取所有符合条件的list
	 * @param map
	 * @return
	 */
	public List<Attachment> findAll(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap",map);
	}

	/**
	 * 清除掉上次的附件
	 * @param map
	 */
	public  void removeAllBySupplierid(Map map){
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeAllBySupplierid",map);
	}

	@Override
	public void copyByShop(Map<String, Long> map) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".copyByShop",map);
	}

}
