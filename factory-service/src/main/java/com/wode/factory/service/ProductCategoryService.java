/**
 * 
 */
package com.wode.factory.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.ProductCategory;

/**
 * 
 * <pre>
 * 功能说明: 平台的商品分类服务
 * 日期:	2015年2月4日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 
 * </pre>
 */
public interface ProductCategoryService  {
	/**
	 * 查找下一级分类
	 * @param pojo
	 * @return
	 */
	List<ProductCategory> findSub(ProductCategory pojo);
	
	/**
	 * 查找所有根分类
	 * @return
	 */
	List<ProductCategory> findRoot();
	
	
	/**
	 * 查找多个分类
	 * @return
	 */
	List<ProductCategory> findIds(List<Long> ids);
	
	
	/**
	 * 查找一二级分类
	 * @param ids
	 * 3级或最低级节点
	 * @return
	 * 
	 */
	Map<Long,List<ProductCategory>> findParents(List<Long> ids);
	
	
	
	/**
	 *  查找一二级分类
	 * @param ids
	 * 3级或最低级节点
	 * @return
	 * 树状结构（从根节点到本节点的结构）
	 */
	Collection<ProductCategory> findParentsByGroup(List<Long> ids);
	
	/**
	 * 查找父级分类
	 * @param id
	 * @return
	 * 一级、二级
	 */
	@QueryCached(keyPreFix="cateParents")
	List<ProductCategory> findParents(Long id);

	List<Long> find3CategoryId();
	/**
	 * 根据分类id查
	 * @param id
	 * @return
	 */
	ProductCategory findById(Long id);
	
	/**
	 * 检索时分类自动过滤
	 * @param cat
	 * @return
	 */
	String getSearchCat(String cat);
}
