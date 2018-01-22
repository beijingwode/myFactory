package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.FLJProductModel;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.dao.ProductSpecificationsDao;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.vo.ProductSpecificationsVo;

import cn.org.rapid_framework.beanutils.BeanUtils;
@Service("productSpecificationsService")
public class ProductSpecificationsServiceImpl  implements ProductSpecificationsService {

	@Autowired
	private ProductSpecificationsDao psd;
	@Autowired
    private InventoryService inventoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ProductLadderService productLadderService;

	private static Logger logger= LoggerFactory.getLogger(ProductSpecificationsServiceImpl.class);
	
	@Override
	public ProductSpecificationsVo selectProductSpecifications(String itemids) {
		String skuId =redisUtil.getMapData(RedisConstant.REDIS_ITEMSIDS_SKU_MAP, itemids);
		ProductSpecificationsVo ps=null;
		if(StringUtils.isEmpty(skuId)) {
			ps = psd.selectOne(itemids);
			if(ps!=null) {
				redisUtil.setMapData(RedisConstant.REDIS_ITEMSIDS_SKU_MAP, itemids, ps.getId()+"");
			}
		} else {
			ps = this.findByIdCache(NumberUtil.toLong(skuId),"");
		}
		return ps;
	}

	@Override
	public List<FLJProductModel> findTop3(Long supplierId) {
		return psd.findTop3(supplierId);
	}

	@Override
	public ProductSpecificationsVo findByIdCache(long id,String productId) {
		Product product = null;
		if(StringUtils.isEmpty(productId)) {
			//商品
			product = productService.findBySku(id);
			if(product!=null) {
				productId=product.getId().toString();
			}
		} else {
			product = productService.findById(NumberUtil.toLong(productId), true);
		}
		
		if(product != null) {
			
			String jsonS=redisUtil.getMapData(RedisConstant.PRODUCT_PRE+productId, RedisConstant.PRODUCT_REDIS_SKU);
			if(!StringUtils.isEmpty(jsonS)){
				ProductSpecificationsVo rtn = null;
				List<ProductSpecificationsVo> skus=JsonUtil.getList(jsonS, ProductSpecificationsVo.class);
				for (ProductSpecificationsVo productSpecifications : skus) {
					if(id==productSpecifications.getId()) {
						rtn=productSpecifications;
						rtn.setCarriage(product.getCarriage());
						rtn.setIsMarketable(product.getIsMarketable());
						Integer q = inventoryService.getInventoryFromRedis(id);
						if(q == null) {
							logger.warn("缓存数据异常，缓存中商品SKU列表信息中不存在指定的skuId的库存，商品ID:"+productId + ",sku ID:"+id);
							return null;
						}
						rtn.setQuantity(q);
						
						return rtn;
					}
				}
				logger.warn("缓存数据异常，缓存中商品SKU列表信息中不存在指定的skuId，商品ID:"+productId + ",sku ID:"+id);
				
				//商品改变价格后，skuid 更换
				ProductSpecifications sku = psd.getById(id);
				if(sku!=null) {
					for (ProductSpecificationsVo productSpecifications : skus) {
						if(sku.getItemValues().equals(productSpecifications.getItemValues())) {
							rtn=productSpecifications;
							rtn.setCarriage(product.getCarriage());
							rtn.setIsMarketable(product.getIsMarketable());
							rtn.setQuantity(inventoryService.getInventoryFromRedis(id));
							
							return rtn;
						}
					}
					logger.warn("缓存数据异常，缓存中商品SKU列表信息中不存在指定的itemids，商品ID:"+productId + ",sku ID:"+id);
				}
			} else {
				logger.warn("缓存数据异常，缓存中无法获取商品SKU列表信息，商品ID:"+productId + ",sku ID:"+id);
			}
		} else {
			logger.warn("商品数据异常,无法通过skuid获取商品信息， 商品id:"+productId + ",sku ID:"+id);
		}
		
		
		ProductSpecifications sku = psd.getById(id);
		if(sku == null) {
			logger.warn("商品数据异常,无法通过skuid获取商品信息， 商品id:"+productId + ",sku ID:"+id);
			return null;
		}
		ProductSpecificationsVo rtn = new ProductSpecificationsVo();
		BeanUtils.copyProperties(rtn, sku);
		if(product!=null) {
			rtn.setCarriage(product.getCarriage());
			rtn.setIsMarketable(product.getIsMarketable());
			rtn.setSaleKbn(product.getSaleKbn());
			rtn.setSaleNote(product.getSaleNote());
//			rtn.setQuestionId(product.getQuestionnaireId());
		}
		rtn.setQuantity(inventoryService.getInventoryFromRedis(id));
		
		return rtn;
	}

	@Override
	public boolean resetPrice(ProductSpecifications sku, Product product, UserFactory user,boolean isDetail,Integer quantity) {
		boolean isLadderPrice = false;
		//BigDecimal big=entParamCodeService.getBenefitSubsidy();
//		if(big.compareTo(sku.getMaxFucoin())<=0) {
//			sku.setBenefitSubsidy(big);
//			sku.setCost(big);
//		} else {
			sku.setCost(sku.getMaxFucoin());
			sku.setBenefitSubsidy(sku.getMaxFucoin());
//		}
		
		//如果没有数量则不进行阶梯价查询
		if(null != quantity){
			//这里查询商品是否有阶梯价
			BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(sku.getId(), quantity);
			if(null != ladderPrice){
				sku.setMaxFucoin(new BigDecimal("0.01"));
				sku.setInternalPurchasePrice(ladderPrice);
				isLadderPrice=true;
			}
		}
//		if(user != null && (product.getSaleKbn()==null?0:product.getSaleKbn())==4) {
//			if(product.getSupplierId().equals(user.getSupplierId())) {
//				if(product.getEmpLevel() != -1) {
//					EnterpriseUser emp = userService.getEmpById(user.getId());
//					if(emp!=null && emp.getWelfareLevel()!=null && emp.getWelfareLevel()==product.getEmpLevel()) {
//						isLadderPrice=false;
//						sku.setPrice(product.getEmpPrice());
//						sku.setMaxFucoin(BigDecimal.ZERO);
//						sku.setCost(product.getEmpPrice());
//						sku.setInternalPurchasePrice(product.getEmpPrice());
//						sku.setEmpPrice(product.getEmpPrice());
//					}
//				} else {
//					isLadderPrice=false;
//					sku.setCost(product.getEmpPrice());
//					sku.setPrice(product.getEmpPrice());
//					sku.setInternalPurchasePrice(product.getEmpPrice());
//					sku.setMaxFucoin(BigDecimal.ZERO);
//					sku.setEmpPrice(product.getEmpPrice());
//				}				
//			}
//		}
		
		//商品详情以外直接改变电商价与内购券
		if(!isDetail) {
			if(sku.getCost()!=null && sku.getMaxFucoin()!=null) {
				if(sku.getCost().compareTo(sku.getMaxFucoin())<0) {
					sku.setPrice(sku.getPrice().subtract(sku.getMaxFucoin().subtract(sku.getCost())));
					sku.setMaxFucoin(sku.getCost());
				}
			}
		}
		return isLadderPrice;
	}

	/**
	 * 通过商品Id获取所有sku信息
	 */
	@Override
	public List<ProductSpecifications> findByProductId(String productId) {
		if(!StringUtils.isEmpty(productId)){//
			String jsonp = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+productId,RedisConstant.PRODUCT_REDIS_INFO);
			List<ProductSpecifications> pssList = new ArrayList<>();
			if(!StringUtils.isEmpty(jsonp)) {
				Product p = JsonUtil.getObject(jsonp, Product.class);
				if(p!=null){
					String jsonS=redisUtil.getMapData(RedisConstant.PRODUCT_PRE+productId, RedisConstant.PRODUCT_REDIS_SKU);
					if(!StringUtils.isEmpty(jsonS)){
						ProductSpecifications rtn = null;
						List<ProductSpecifications> skus=JsonUtil.getList(jsonS, ProductSpecifications.class);
						for (ProductSpecifications productSpecifications : skus) {
							rtn=productSpecifications;
							rtn.setQuantity(inventoryService.getInventoryFromRedis(productSpecifications.getId()));
							pssList.add(rtn);
						}
						return pssList;
					}
				}
			}else{
				pssList =psd.findByProductId(productId);
				return pssList;
			}
		}
		return null;
	}

	
	
}
