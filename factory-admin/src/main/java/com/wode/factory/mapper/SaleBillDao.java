/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SaleBill;

public interface SaleBillDao extends  EntityDao<SaleBill,Long>{
	
	/**
	 * 功能说明：向数据库插入数据
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param SupplierCloseCmd
	 */
	public void insert(SaleBill entity);
	
	/**
	 * 功能说明：通过明细合计出账单
	 * 日期:	2015年11月09日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param id(明细插入时id)
	 */
	public SaleBill sumBydetail(Long id);
	/**
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<SaleBill> findPageSaleBill(Map<String, Object> map);
	/**
	 * 分页条件查询对账单信息
	 * @param map
	 * @return
	 */
	public List<SaleBill> getByIds(String ids);	
	
	/**
	 * 根据对账单id查询发票信息
	 * @param id
	 * @return  
	 */
	public SaleBill getSaleBillDetail(@Param("id") Long id);
	
	
}
