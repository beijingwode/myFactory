package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.vo.CategoryBrandVo;
import com.wode.factory.vo.ProductCategoryVo;


public interface ProductCategoryService{
	/**
	 * 功能说明：商品分类树信息
	 * 日期:	2015年7月1日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public List<ProductCategoryVo> getProductCategoryTree();

	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<CategoryBrandVo> findCountList(Map<String, Object> params);
	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<CategoryBrandVo> findPCountList(Map<String, Object> params);
	
	public ProductCategoryVo selectById(Long id);
	/**
	 * 功能说明：商品分类信息，分页
	 * 日期:	2015年7月1日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public PageInfo<ProductCategoryVo> selectProductCategory(Long rootId,String brotherOrderAll,String name,Integer pages,Integer size);
	/**
	 * 功能说明：保存商品分类信息
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param vo
	 * @return
	 */
	public Integer save(ProductCategory vo);
	/**
	 * 功能说明：修改商品分类信息
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param vo
	 * @return
	 */
	public Integer update(ProductCategory vo);
	/**
	 * 功能说明：批量删除数据
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param rootId
	 * @param brotherOrderAll
	 * @return
	 */
	public Integer deleteBatchProductCategory(Long id,Long rootId,String brotherOrderAll);
	
	public ActResult delete (Long id);

	public List<ProductCategory> findLastLevel(Long id);
	/**
	 * 功能说明：根据pid查询子节点的信息
	 * 日期:	2015年7月3日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pId
	 * @return
	 */
	public List<ProductCategory> selectByPid(Long pId);

	/**
	 * 
	 * 功能说明：根据id查询父级类别
	 * 日期:	2015年8月28日
	 * 开发者:宋艳垒
	 *
	 * @param categoryId
	 * @return
	 */
	public ProductCategory getParentCategoryByid(Long categoryId);

	/**
	 * 
	 * 功能说明：根据深度查询分类
	 * 日期:	2015年9月6日
	 * 开发者:宋艳垒
	 *
	 * @param i
	 * @return
	 */
	public List<ProductCategory> findByDeep(int i);
	
}
