package com.wode.factory.mapper;


import com.wode.factory.model.CategoryWeekStatistical;

/**
 * Created by zoln on 2015/7/24.
 */
public interface CategoryWeekStatisticalDao extends FactoryBaseDao<CategoryWeekStatistical> {

	public void updateByCategory(Long categoryId);

	public void insertByCategory(Long categoryId);

}
