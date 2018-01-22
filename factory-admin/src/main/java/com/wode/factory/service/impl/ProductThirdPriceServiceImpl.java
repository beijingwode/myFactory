package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.ProductThirdPriceDao;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.service.ProductThirdPriceService;
import com.wode.factory.vo.ProductThirdPriceVO;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("productThirdPriceService")
public class ProductThirdPriceServiceImpl extends EntityServiceImpl<ProductThirdPrice,Long> implements ProductThirdPriceService {

	@Autowired
	ProductThirdPriceDao productThirdPriceMapper;

	@Override
	public ProductThirdPriceDao getDao() {
		return productThirdPriceMapper;
	}

	@Override
	public List<ProductThirdPrice> select5DaysAgo(String type) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("type", type);
		return getDao().select5DaysAgo(map);
	}

	@Override
	public void saveProductThirdPrice(ProductThirdPrice ptp) {
		productThirdPriceMapper.insert(ptp);
	}

	@Override
	public List<ProductThirdPrice> selectByProductId(Long productId) {
		return productThirdPriceMapper.selectyByProductId(productId);
	}

	@Override
	public PageInfo<ProductThirdPriceVO> findNotifyList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ProductThirdPriceVO> list = productThirdPriceMapper.findNotifyList(params);
		return new PageInfo<ProductThirdPriceVO>(list);
	}	
	@Override
	public ProductThirdPrice selectByProductIdAndItemValues(Long productId, String itemValues) {
		ProductThirdPrice p = new ProductThirdPrice();
		List<ProductThirdPrice> ptpList = productThirdPriceMapper.selectyByProductId(productId);
		if(ptpList!=null && !ptpList.isEmpty()){//第三方价格存在不为空
			for (ProductThirdPrice ptp : ptpList) {
				if(itemValues.equals(ptp.getItemValues())){
					 p = ptp;
				}
			}
		}
		return p;
	}	
}
