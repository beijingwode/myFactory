package com.wode.factory.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.Suborderitem;


public interface SuborderitemDao {

	/**
	 * 
	 * 功能说明：根据日查询
	 * 日期:	2015年5月14日
	 * 开发者:宋艳垒
	 *
	 * @param day
	 * @return
	 */
	public List<Suborderitem> selectByDay(Date day);
	/**
	 * 功能说明：根据子单ID查询子单项list
	 * @param subOrderId
	 * @return
	 */
	public Suborderitem findBySubId(String subOrderId);
	/**
	 * 功能说明：根据子单ID查询子单项list
	 * @param subOrderId
	 * @return
	 */
	public List<Suborderitem> findBySubIdForView(String subOrderId);
	/**
	 * 查询指定收货日期的未评价使用订单
	 * @param takeTime
	 * @return
	 */
	public List<Suborderitem> findTrialByDate(String takeTime);

	/**
	 * 功能说明：根据子单ID查询子单项list
	 * @param subOrderId
	 * @return
	 */
	public List<Suborderitem> findBySubIdAndSaleKbn(Map<String,Object> map);
	

	public int insert(Suborderitem suborderitem);
	
	/**
	 * 功能说明： 根据商品Id和(拍下减库存和付款减库存)标示查询销售单总数（去除取消的单子信息）
	 * @param map
	 * @return
	 */
	public List<Suborderitem> findSubCountByProId(Map<String, Object> map);
	
	public List<Suborderitem> selectByModel(Suborderitem query);
	
	public void update(Suborderitem soi);
}