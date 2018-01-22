package com.wode.factory.model;

import java.util.List;

public interface ProductCategoryMapper {
    int countByExample(ProductCategoryExample example);

    int deleteByExample(ProductCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    List<ProductCategory> selectByExample(ProductCategoryExample example);
    
    List<ProductCategory> selectByCategory(ProductCategory productCategory);

    ProductCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(ProductCategory record, ProductCategoryExample example);

    int updateByExample(ProductCategory record, ProductCategoryExample example);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

	List<ProductCategory> selectLastLevel(ProductCategory parLast);
	
	/**
	 * 
	 * @author syl
	 * @Description : 根据父类别排行删除子类别
	 * @CreateDate ; 2015-1-9 上午11:24:21
	 * @lastModified ; 2015-1-9 上午11:24:21
	 * @version ; 1.0
	 * @param brotherOrderAll
	 */
	void delChild(String brotherOrderAll);
}