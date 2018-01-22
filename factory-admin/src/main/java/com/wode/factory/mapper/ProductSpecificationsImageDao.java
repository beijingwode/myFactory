package com.wode.factory.mapper;
import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductSpecificationsImage;


public interface ProductSpecificationsImageDao extends  EntityDao<ProductSpecificationsImage,Long>{

	/**
	 * 
	 * 功能说明：根据日查询
	 *
	 * @return
	 */
	public List<ProductSpecificationsImage> findlistByProductSpecificationsid(Long id);
	public List<ProductSpecificationsImage> getByProductId(Long productId);
	public void saveOrUpdate(ProductSpecificationsImage entity);
	public void insert(ProductSpecificationsImage entity);
	public void updateImg(Map<String,Object> param);
	
	public void deleteBySupplierId(Long supplierId);

}