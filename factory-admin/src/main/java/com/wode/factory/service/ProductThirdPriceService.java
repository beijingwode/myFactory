package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.vo.ProductThirdPriceVO;

/**
 *
 */
public interface ProductThirdPriceService extends EntityService<ProductThirdPrice,Long> {
	public List<ProductThirdPrice> select5DaysAgo(String type);
	public void saveProductThirdPrice(ProductThirdPrice ptp);
	public List<ProductThirdPrice> selectByProductId(Long productId);
	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public PageInfo<ProductThirdPriceVO> findNotifyList(Map<String, Object> params);
	/**
	 * 通过商品id和规格值获取信息
	 * @param productId
	 * @param itemValues
	 * @return
	 */
	public ProductThirdPrice selectByProductIdAndItemValues(Long productId, String itemValues);
}
