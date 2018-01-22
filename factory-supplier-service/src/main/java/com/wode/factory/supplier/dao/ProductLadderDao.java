package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseQuery;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.supplier.query.SkuLadderVo;

public interface ProductLadderDao extends  BasePageDao<ProductLadder> {
	
	
	/**
	 * 删除该商品对应的所有属性
	 * @param productid
	 */
	public void removeAllByProductid(Map<String,String> map);
	
	/**
	 * 查询
	 * @param mapz
	 * @return
	 */
	public List<ProductLadder> getList(Map<String,String> map);
	

	public PageInfo<SkuLadderVo> findPageVo(BaseQuery query);
}
