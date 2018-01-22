package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ProductCategory;
import com.wode.factory.vo.CategoryBrandVo;
import com.wode.factory.vo.ProductCategoryVo;


public interface ProductCategoryDao {

	/**
	 * 功能说明：查询商品分类书信息
	 * 日期:	2015年6月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public List<ProductCategoryVo> selectProductCategoryTree();
	/**
	 * 功能说明：查询商品分类信息，并分页
	 * 日期:	2015年7月1日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public List<ProductCategoryVo> selectProductCategory(Map map);
	
	public ProductCategoryVo selectByPrimarkey(Long id);
	/**
	 * 功能说明：根据节点查询商品分类信息
	 * 日期:	2015年7月1日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param map
	 * @return
	 */
//	public PageInfo<ProductCategoryVo> selectByNode(Long rootId,String brotherOrderAll,Integer pages,Integer size);
	/**
	 * 功能说明：查询节点及节点一下的全部数据
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param rootId
	 * @param brotherOrderAll
	 * @return
	 */
	public List<ProductCategoryVo> selectByNode(Map map);
	/**
	 * 功能说明：添加商品分类
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pro
	 * @return
	 */
	public Integer insertProductCategory(ProductCategory pro);
	/**
	 * 功能说明：批量删除
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param vo
	 * @return
	 */
	public Integer deleteBatch(List<ProductCategoryVo> vo);
	
	
	public Integer delete(ProductCategory entity);
	
	public Long findProductCount(Long cateid);
	
	/**
	 * 功能说明：更新
	 * 日期:	2015年7月2日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pro
	 * @return
	 */
	public Integer updateProductCategory(ProductCategory pro);
	/**
	 * 功能说明：根据deep升序查询
	 * 日期:	2015年7月3日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param deep
	 * @return
	 */
	public List<ProductCategory> selectByDeep(Integer deep);
	/**
	 * 功能说明：查询所有的商品分类
	 * 日期:	2015年7月3日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public List<ProductCategory> selectAll();
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
	 * 功能说明：查询最后一级分类
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 * @param rootId
	 * @return
	 */
	public List<ProductCategory> findLastLevel(Long rootId);
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
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<CategoryBrandVo> findCountList(Map<String, Object> params);
	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<CategoryBrandVo> findPCountList(Map<String, Object> params);
	
}