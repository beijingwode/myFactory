package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.Attribute;

/**
 * 分类下的商品属性
 * 
 * @author 谢海生
 *
 */
public interface CategoryAttributeService {

	List<Attribute> findByCategory(Long categoryid);
	
}
