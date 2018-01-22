package com.wode.factory.supplier.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.supplier.dao.ProductLadderDao;
import com.wode.factory.supplier.query.SkuLadderVo;
import com.wode.factory.supplier.service.ProductLadderService;

@Service("productLadderService")
public class ProductLadderServiceImpl  extends BaseService<ProductLadder,Long> implements ProductLadderService {

	@Autowired
	private ProductLadderDao productLadderDao;
	
	@Transactional
	@Override
	public boolean saveProductLadder(Long supplier, Long productId, List<Integer> nums, List<BigDecimal> prices, List<List<Long>> skuids,Integer type) {
		if(null != nums && nums.size() > 0){
			for (int i = 0; i < nums.size(); i++) {
				ProductLadder productLadder = new ProductLadder();
				productLadder.setSupplierId(supplier);
				productLadder.setProductId(productId);
				productLadder.setNum(nums.get(i));
				productLadder.setPrice(prices.get(i));
				productLadder.setSkuids(getStringList(skuids.get(i)));
				productLadder.setCreateTime(new Date());
				productLadder.setUpdateTime(new Date());
				productLadder.setType(type);
				productLadderDao.saveOrUpdate(productLadder);
			}
			return true;
		}
		return false;
	}

	@Override
	protected EntityDao getEntityDao() {
		return this.productLadderDao;
	}
	
	@Override
	public void removeAllByProductid(Map<String, String> map) {
		productLadderDao.removeAllByProductid(map);
	}

	
	private String getStringList(List<Long> list){
		String result = "";
		for (Long long1 : list) {
			result +=long1 + ",";
		}
		return result;
	}

	@Override
	public List<ProductLadder> getlistByProductid(Long productId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", ""+productId);
		return productLadderDao.getList(map);
	}

	@Override
	public PageInfo<SkuLadderVo> findPageVo(SkuLadderVo query) {
		return productLadderDao.findPageVo(query);
	}


}
