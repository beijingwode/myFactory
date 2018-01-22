package com.wode.user.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.CategoryWeekStatisticalDao;
import com.wode.factory.mapper.ProductCategoryDao;
import com.wode.factory.model.ProductCategory;


@Service
public class CategoryWeekStatistic {
	@Autowired
	private ProductCategoryDao productCategoryMapper;
	@Autowired
	private CategoryWeekStatisticalDao categoryWeekStatisticalMapper;
	
	/**
	 * 添加周统计信息
	 */
	public void run() {
		System.out.println("开始时间"+new java.util.Date(System.currentTimeMillis())+"\n");
		//查询所有分类数据
		List<ProductCategory> categoryList = productCategoryMapper.selectByDeep(3);
		if (categoryList.size()>0 && categoryList!=null) {
			//遍历
			for (ProductCategory s : categoryList) {
				Long categoryId = s.getId();
				//查询上周数据,并修改
				categoryWeekStatisticalMapper.updateByCategory(categoryId);
				//获取新数据，并添加
				categoryWeekStatisticalMapper.insertByCategory(categoryId);
			}
		}
		System.out.println("结束时间"+new java.util.Date(System.currentTimeMillis())+"\n");
	}
	

}
