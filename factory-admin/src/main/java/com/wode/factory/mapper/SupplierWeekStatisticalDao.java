package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.SupplierWeekStatistical;

/**
 * Created by zoln on 2015/7/24.
 */
public interface SupplierWeekStatisticalDao extends FactoryBaseDao<SupplierWeekStatistical> {

	public List<SupplierWeekStatistical> selectBySupplierId(Long supplierId);

}
