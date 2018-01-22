package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;

@Service("factory-productLadderService")
public class ProductLadderServiceImpl implements  ProductLadderService {
	
	@Autowired
	private Dao dao;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
    private ProductService productService;
	
	private static Logger logger= LoggerFactory.getLogger(ProductLadderServiceImpl.class);
	
	@Override
	public List<ProductLadder> getListBySkuid(Long skuid) {
		String jsonString = null;
		String strPid=redisUtil.getMapData(RedisConstant.PRODUCT_SPE_ID_FOR_PRO_ID, skuid+"");
		if(!StringUtils.isEmpty(strPid)) {
			jsonString  = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + strPid, RedisConstant.PRODUCT_REDIS_SKU_LADDER);
		}
		
		if(StringUtils.isEmpty(jsonString)) {
			logger.warn("sku 阶梯价格缓存数据异常,skuid:"+skuid + ",商品id:"+strPid);
			List<ProductLadder> pAttr=dao.query(ProductLadder.class, Cnd.where("skuids", "like", "%"+skuid + "%").asc("price"));
			return pAttr;
		} else {
			Map<String, List<String>> skuCacheLadders = JsonUtil.getMap4Json(jsonString);
			List<String> skuCacheLadder = skuCacheLadders.get(skuid+"");
			if(skuCacheLadder!=null) {
				List<ProductLadder> rtn = new ArrayList<ProductLadder>();
				for (String s : skuCacheLadder) {
					rtn.add(JsonUtil.getObject(s, ProductLadder.class));
				}
				return rtn;
			} else {
				return null;
			}
		}
	}

	@Override
	public String getStringBySkuid(Long skuid) {
		List<ProductLadder> list = getListBySkuid(skuid);
		if(null != list){
			String str = "";
			 Collections.sort(list, new Comparator<ProductLadder>() {
		            public int compare(ProductLadder o1, ProductLadder o2) {
		                return o1.getNum().compareTo(o2.getNum());
		            }
		     });
			for (ProductLadder productLadder : list) {
				if(0==productLadder.getType()){
					str+=productLadder.getNum()+"件以上,"+productLadder.getPrice()+"元/件；";
				}else if(1==productLadder.getType()){
					str+=productLadder.getNum()+"件以上,"+productLadder.getPrice()+"折；";
				}
			}
			return str;
		}
		return "";
	}

	@Override
	public BigDecimal getPriceBySkuidAndPrice(Long skuid, Integer quantity) {
		List<ProductLadder> list = getListBySkuid(skuid);
		if(null != list){
			//根据数量排序
			 Collections.sort(list, new Comparator<ProductLadder>() {
		            public int compare(ProductLadder o1, ProductLadder o2) {
		                return o2.getNum().compareTo(o1.getNum());
		            }
		     });
			for (ProductLadder productLadder : list) {
				if(quantity.compareTo(productLadder.getNum()) >=0) {
					if(1==productLadder.getType()){//折扣
						List<ProductSpecifications> skuList = productService.findByProductIdAndSkuId(productLadder.getProductId(), skuid);
						BigDecimal price = null;
						if(skuList!=null && !skuList.isEmpty()){
							 price = productLadder.getPrice().multiply(new BigDecimal(0.1)).multiply(skuList.get(0).getPrice());
							 price = price.setScale(2,BigDecimal.ROUND_HALF_UP);
						}
						return price;
					}else{
						return productLadder.getPrice();
					}
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, BigDecimal> getSalePromotionMapBySkuid(Long skuid) {
		Map<String,BigDecimal> result= null;
		List<ProductLadder> list = getListBySkuid(skuid);
		if(null != list){
			result = new HashMap<>();
			//根据数量排序
			Collections.sort(list, new Comparator<ProductLadder>() {
				public int compare(ProductLadder o1, ProductLadder o2) {
	                return o2.getNum().compareTo(o1.getNum());
	            }
	     	});
			for (ProductLadder productLadder : list) {
				if(1==productLadder.getType()){//折扣
					List<ProductSpecifications> skuList = productService.findByProductIdAndSkuId(productLadder.getProductId(), skuid);
					BigDecimal price = null;
					if(skuList!=null && !skuList.isEmpty()){
						 price = productLadder.getPrice().multiply(new BigDecimal(0.1)).multiply(skuList.get(0).getPrice());
						 price = price.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					result.put(""+productLadder.getNum(), price);
				}else {
					result.put(""+productLadder.getNum(), productLadder.getPrice());
				}
			}
		}
		return result;
	}

}
