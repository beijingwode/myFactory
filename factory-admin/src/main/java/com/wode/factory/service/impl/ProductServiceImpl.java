package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.ProductVO;
import com.wode.search.WodeSearchManager;
import com.wode.search.Entity.ProductEntity;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private WodeSearchManager wsm;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	@Autowired
	private ProductLadderService productLadderService;
	
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	private Set<String> interfaceUrl=new HashSet<String>();

	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}

	}

	@Override
	public List<Product> find(Map<String, Object> params) {
		List<Product> list = productDao.findList(params);
		return list;
	}


	@Override
	public PageInfo<Product> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Product> list = productDao.findList(params);
		return new PageInfo<Product>(list);
	}

	@Override
	public Product getById(Long id) {
		Product p = productDao.getById(id);
		//处理规格
//		List<ProductSpecificationValue> list = p.getProductSpecificationValuelist();
//		List<ProductSpecificationValue> listnew = new ArrayList<>();
//		Map<String, List<ProductSpecificationValue>> mappsf = new HashMap();
//		List<ProductSpecificationValue> listtemp;
//		if (p != null) {
			
			
//			for (ProductSpecificationValue psf : list) {
//				listtemp = mappsf.get(psf.getSpecificationName());
//				if (listtemp == null) {
//					listtemp = new ArrayList<>();
//				}
//				listtemp.add(psf);
//				mappsf.put(psf.getSpecificationName(), listtemp);
//			}
//
//			Iterator ite = mappsf.keySet().iterator();
//
//			while (ite.hasNext()) {
//				String key = (String) ite.next();
//				List<ProductSpecificationValue> value = mappsf.get(key);
//				String values = "";
//				for (ProductSpecificationValue psvalue : value) {
//					values = values + psvalue.getSpecificationValue() + ",";
//				}
//
//				ProductSpecificationValue pstemp = new ProductSpecificationValue();
//				pstemp.setSpecificationName(key);
//				pstemp.setSpecificationValue(values);
//				listnew.add(pstemp);
//			}
//			p.setProductSpecificationValuelist(listnew);
//		}
		return p;
	}

	public Product cache(Long productId,Map<Long,Long> pPvs) {

		if (StringUtils.isEmpty(productId)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();

		//先清除就缓存数据 如有
		Map<String, String> oldMap = redisUtil.getMap(RedisConstant.PRODUCT_PRE + productId);
		Map<String, Object> old=null;
		if(oldMap!=null) {			
			old=clearCach(productId,JsonUtil.getList(oldMap.get(RedisConstant.PRODUCT_REDIS_SKU), ProductSpecifications.class));
		}
		
		ProductVO product = productDao.getById(productId);

		//缓存产品信息
		Supplier resSup = supplierService.findByid(product.getSupplierId());
		//缓存供应商信息
		map.put(RedisConstant.PRODUCT_REDIS_SUPPLIER, JsonUtil.toJsonString(resSup));

		//查询有效的商品规格
		List<ProductSpecifications> listResProSpe = productSpecificationsService.findlistByProductid(productId);
		//规格对应的图片
		Map<String, String> speImg = new HashMap<String, String>();
		List<ProductLadder> productLadders = productLadderService.getByProductId(productId);
		Map<Long, List<ProductLadder>> skuLadders = new HashMap<Long, List<ProductLadder>>();
		Map<String, List<String>> skuCacheLadders = new HashMap<String, List<String>>();
		
		ProductSpecifications minPrice = null;
		if (!StringUtils.isEmpty(listResProSpe)) {
			for (ProductSpecifications sku : listResProSpe) {
				redisUtil.setMapData(RedisConstant.PRODUCT_SPE_ID_FOR_PRO_ID, sku.getId() + "", productId + "");
				redisUtil.setMapData(RedisConstant.REDIS_ITEMSIDS_SKU_MAP,sku.getItemids(), sku.getId() + "");	
				redisUtil.setData(RedisConstant.REDIS_SKU_INVENTORY+sku.getId(), String.valueOf(sku.getStock()));
				//查询商品图片信息
				List<ProductSpecificationsImage> ListproIma = productSpecificationsImageService.findlistByProductSpecificationsid(sku.getId());
				if (!StringUtils.isEmpty(ListproIma) && ListproIma.size() > 0) {
					speImg.put(sku.getId() + "", JsonUtil.toJsonString(ListproIma));
					for (ProductSpecificationsImage psi : ListproIma) {
						if (psi.getOrders() == 1) {
							sku.setMainImage(psi.getSource());
						}
					}
				}
				
				List<ProductLadder> skuLadder=new ArrayList<ProductLadder>();
				List<String> skuCacheLadder=new ArrayList<String>();
				for (ProductLadder productLadder : productLadders) {
					if((","+productLadder.getSkuids()).contains(","+sku.getId()+",")) {
						skuLadder.add(productLadder);
						skuCacheLadder.add(JsonUtil.toJsonString(productLadder));
					}
				}
				
				
				if(!skuLadder.isEmpty()) {
					skuLadders.put(sku.getId(), skuLadder);
					skuCacheLadders.put(sku.getId()+"", skuCacheLadder); // 避免map 转换时 类型问题
				}
				if(!lessThan(minPrice,sku,skuLadders)) {
					minPrice=sku;
				}
			}
		}

		List<ProductSpecifications> oneLs = new ArrayList<ProductSpecifications>();
		oneLs.add(minPrice);
		product.setProductSpecificationslist(oneLs);

		//缓存商品信息
		map.put(RedisConstant.PRODUCT_REDIS_INFO, JsonUtil.toJsonString(product));
		//缓存规格对应的图片信息
		map.put(RedisConstant.PRODUCT_REDIS_IMAGE, JsonUtil.toJsonString(speImg));
		//缓存SKU
		map.put(RedisConstant.PRODUCT_REDIS_SKU, JsonUtil.toJsonString(listResProSpe));
		//缓存SKU阶梯价
		map.put(RedisConstant.PRODUCT_REDIS_SKU_LADDER, JsonUtil.toJsonString(skuCacheLadders));
		
		redisUtil.setData(RedisConstant.PRODUCT_PRE + productId, map);
		redisUtil.del("product_AllskuImg_[" + productId + "]");
		redisUtil.del("product_Allsku_[" + productId + "]");
		redisUtil.del("findByMinpriceCache_[" + productId + ","+product.getMinprice()+"]");
		String error =null;
		if(product.getSelfType()==0 || product.getSelfType()==3){//立即上线商品或者自动上线加入索引
			//创建索引
			List<ProductEntity> psv = toIndexEntity(product,skuLadders, pPvs,old);
			error = wsm.buildIndex(psv);
		}
		if (error != null) {
			logger.error("创建商品索引失败:" + error);
		}

		return product;
	}

	@Override
	public void destroy(Long productId) {
		if (StringUtils.isEmpty(productId)) {
			return;
		}

		clearCach(productId,productSpecificationsService.findlistByProductid(productId));
	}
	
	private Map<String, Object> clearCach(Long productId, List<ProductSpecifications> listResProSpe) {
		// 删除商品缓存及索引
		redisUtil.del(RedisConstant.PRODUCT_PRE + productId);
		
		Map<String, Object> old = wsm.getIndexById(productId);
		if(old!=null) {
			wsm.deleteIndex(productId);
		}
		
		if(listResProSpe!=null) {
			for (ProductSpecifications p : listResProSpe) {
				redisUtil.delMapData(RedisConstant.PRODUCT_SPE_ID_FOR_PRO_ID, p.getId()+"");
				redisUtil.delMapData(RedisConstant.REDIS_ITEMSIDS_SKU_MAP, p.getItemids());
				redisUtil.del(RedisConstant.REDIS_SKU_INVENTORY+p.getId());
			}
		}
		
		return old;
	}

	private boolean lessThan(ProductSpecifications fisrt,ProductSpecifications second,Map<Long, List<ProductLadder>> skuLadders) {
		if(fisrt==null) return false;
		if(second==null) return true;
		BigDecimal firstPrice = fisrt.getInternalPurchasePrice();
		BigDecimal secondPrice = second.getInternalPurchasePrice();
		
		if(firstPrice==null || !NumberUtil.isGreaterZero(firstPrice)) return false;
		if(secondPrice==null || !NumberUtil.isGreaterZero(secondPrice)) return true;
		
		List<ProductLadder> skuLadder = skuLadders.get(fisrt.getId());
		if(skuLadder!=null && !skuLadder.isEmpty()) {
			firstPrice = skuLadder.get(0).getPrice();
		}
		skuLadder = skuLadders.get(second.getId());
		if(skuLadder!=null && !skuLadder.isEmpty()) {
			secondPrice = skuLadder.get(0).getPrice();
		}
				
		return firstPrice.compareTo(secondPrice) < 0;
	}
	
	/**
	 * 功能说明：产品信息转换成创建索引格式信息
	 * 日期:	2015年3月2日
	 * 开发者:宋艳垒
	 *
	 * @param pv
	 * @return
	 */
	private List<ProductEntity> toIndexEntity(ProductVO pv,Map<Long, List<ProductLadder>> skuLadders,Map<Long,Long> pPvs,Map<String,Object> old) {
		List<ProductEntity> listPro = new ArrayList<ProductEntity>();
		//设置sku
		List<ProductSpecifications> skus = pv.getProductSpecificationslist();
		ProductEntity indexEntity = null;
		for (ProductSpecifications sku : skus) {
			indexEntity = new ProductEntity();

			ProductEntity.Category category = indexEntity.new Category();
			String spNames = changeItemValues(sku.getItemValues());
			String productName = pv.getFullName() + spNames;
			indexEntity.setId(pv.getId() + "");
			// 电商价
			indexEntity.setPrice(sku.getPrice());
			// 销售价
			BigDecimal salePrice = sku.getInternalPurchasePrice(); //默认=内购价
			List<ProductLadder> skuLadder = skuLadders.get(sku.getId());
			if(skuLadder!= null && !skuLadder.isEmpty()) {
				//如有 阶梯价格 销售价=最低阶梯价格
				if(skuLadder.get(0).getType()==1){//折扣
					salePrice=skuLadder.get(0).getPrice().multiply(new BigDecimal(0.1)).multiply(sku.getPrice()).setScale(2, BigDecimal.ROUND_DOWN);
				}else{
					salePrice=skuLadder.get(0).getPrice();
				}
			}
			// 内购价，阶梯价=null 业务上不存在。安全性做如下处理
			if(salePrice == null) {
				if(!StringUtils.isEmpty(sku.getPrice())) {
					// 电商价不等于null
					if (!StringUtils.isEmpty(sku.getMaxFucoin())) {
						// 内购券不等于null 销售价=电商价-内购券
						salePrice = sku.getPrice().subtract(sku.getMaxFucoin()).setScale(2, BigDecimal.ROUND_DOWN);
					} else {
						//销售价=电商价
						salePrice = sku.getPrice();
					}
				} else {
					// 电商价=null 销售价=0
					salePrice = BigDecimal.ZERO;
				}
			}
			indexEntity.setSalePrice(salePrice);
			// 折扣=销售价/电商价*10
			if(NumberUtil.isGreaterZero(sku.getPrice())) {
				indexEntity.setDiscount(salePrice.multiply(BigDecimal.TEN).divide(sku.getPrice(), 1, BigDecimal.ROUND_DOWN).doubleValue());
			} else {
				indexEntity.setDiscount(10.0D);
			}				
			
			indexEntity.setImage(sku.getMainImage());
			indexEntity.setMinSkuId(sku.getId());
			indexEntity.setPromotion(pv.getPromotion());
			indexEntity.setSaleNum(pv.getSellNum());
			indexEntity.setSortNum(pv.getSortNum());
			indexEntity.setMaxFucoin(sku.getMaxFucoin());
			
			Integer tagFlg=0;
			//标签站位符
			if(skuLadders.size() > 0 ) {
				//该商品有阶梯价格
				tagFlg=1;
			} else {
				tagFlg=0;
			}
			if(pv.getLimitKbn() == 3) {
				tagFlg = tagFlg | 2;	
			}
			// 标签 tagFlg 二进制占位符 XXXXXXXXXXXXX企业级限购企采 
			// 企采:  XXXXXX1
			// 企业级限购 :XXXXX1X			
			indexEntity.setTagFlg(tagFlg);
			
			long count = 0;
			if (pv.getCommentCount() != null) {
				count = pv.getCommentCount();
			}
			indexEntity.setRevNum(count);
			indexEntity.setSaleKbn(pv.getSaleKbn()==null?"0":pv.getSaleKbn()+"");

			indexEntity.setShopName(pv.getShopname());
			indexEntity.setSupplierId(pv.getSupplierId());
			indexEntity.setShopId(pv.getShopId());
			
			ProductBrand retPb = supplierService.getProductBrandById(pv.getBrandId());

			if (!StringUtils.isEmpty(retPb)) {
				//当中文品牌为空时用英文品牌建索引
				if (StringUtils.isEmpty(retPb.getName())) {
					retPb.setName(retPb.getNameEn());
				}
				indexEntity.setBrand(retPb.getName());
				indexEntity.setBrandLevel((retPb.getCategoryId()==null||retPb.getCategoryId()<1)?9:retPb.getCategoryId().intValue());
				
				// 如果商品名称中不含品牌名称，则自动拼接品牌名
				if(!productName.contains(retPb.getName())) {
					productName = retPb.getName() + " " + productName;
				}
			}
			// 商品名
			indexEntity.setName(productName);

			Map<String, Object> attMap = new HashMap<String, Object>();
			category.setId(pv.getCategoryId() + "");
			category.setName(pv.getCategoryName());
			//得到三级分类名称
			String concatName=pv.getCategoryName();
			//根据类别查询父级类别
			ProductCategory ProCat = productCategoryService.getParentCategoryByid(pv.getCategoryId());
			if (!StringUtils.isEmpty(ProCat)) {
				ProductEntity.Category  parrent = indexEntity.new Category();
				parrent.setId(ProCat.getId() + "");
				parrent.setName(ProCat.getName());
				category.setParent(parrent);
				category.setAncestor(ProCat.getRootId()+"");
				
				//将二级分类名称与三级分类名称相拼接
				concatName = ProCat.getName() + " "+ concatName;
				ProductCategory treeLevel = productCategoryService.getParentCategoryByid(ProCat.getId());
				if (!StringUtils.isEmpty(treeLevel)) {
					//获取一级分类名称,并与二三级分类名称拼接
					concatName = treeLevel.getName() + " "+ concatName;	
				}
			}
			//类别
			indexEntity.setCategory(category);
			//将查询分类名称字段加入索引
			indexEntity.setCategoryName(concatName);
			//将浏览次数字段加入索引
			Long viewCnt=0L;
			if(pPvs.containsKey(pv.getId())) {
				viewCnt=pPvs.get(pv.getId());
			}
			indexEntity.setViewCount(viewCnt);
			List<ProductAttribute> listProAtr = supplierService.findProductAttributeByProductid(pv.getId());
			if (!StringUtils.isEmpty(listProAtr)) {
				for (ProductAttribute pa : listProAtr) {
					if(!StringUtils.isEmpty(pa.getAttributeName())) {
						attMap.put(pa.getAttributeName(), pa.getValue().replace(",", " "));
					}
				}
				//设置产品属性
				indexEntity.setParams(attMap);
			}
			indexEntity.setProductId(pv.getId() + "");
			indexEntity.put("barcode", pv.getBarcode());
			indexEntity.put("customCat", pv.getStoreCategoryId());
			indexEntity.setCreateDate(pv.getCreateDate());
			//设置库存信息
			if(old==null) {
				indexEntity.setStock(sku.getStock());
				indexEntity.setAllStock(sku.getStock());
			} else {
				if(sku.getId().equals(old.get("minSkuId"))) {
					Integer stock = NumberUtil.toInteger(old.get("stock"));
					Integer allStock = NumberUtil.toInteger(old.get("allStock"));
					if(stock==null || allStock==null) {
						indexEntity.setStock(sku.getStock());
						indexEntity.setAllStock(sku.getStock());
					} else {
						if(stock>0 && allStock>0) {
							allStock=allStock+sku.getStock()-stock;
							indexEntity.setStock(sku.getStock());
							indexEntity.setAllStock(allStock);
						} else {
							indexEntity.setStock(sku.getStock());
							indexEntity.setAllStock(sku.getStock());
						}
					}
				} else {
					indexEntity.setStock(sku.getStock());
					indexEntity.setAllStock(sku.getStock());
				}
			}
			listPro.add(indexEntity);
		}
		return listPro;
	}

	/**
	 * 功能说明：从itemValues中拿出规格值
	 * 日期:	2015年7月6日
	 * 开发者:宋艳垒
	 *
	 * @param itemValues
	 * @return
	 */
	private String changeItemValues(String itemValues) {
		if (itemValues == null && "".equals(itemValues)) {
			return null;
		}
		Map<String, String> map = JsonUtil.getMap4Json(itemValues);
		StringBuffer sb = new StringBuffer("(");
		for (String str : map.values()) {
			sb.append(str + " ");
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public List<Product> findRecormendHotPro(Product parPro) {
		PageHelper.startPage(1, 10);
		return productDao.findRecormendHotPro(parPro);
	}


	@Override
	public void updateAllNum(Long productId, int total) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", productId);
		params.put("allnum", total);
		productDao.updateAllNum(params);
	}

	@Override
	public ActResult createProductHtml(Long productId) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap=new HashMap();
		paramMap.put("productId", productId);
		for(String apiurl:interfaceUrl){
			String response= HttpClientUtil.sendHttpRequest("post", apiurl+"/product", paramMap);
			ActResult as = JsonUtil.getObject(response, ActResult.class);
			if(!as.isSuccess()){
				ret.setMsg(apiurl+":"+as.getMsg());
				ret.setSuccess(false);
			}
		}
		return ret;
	}

	@Override
	public Integer getCountBySupplier(Long supplierId) {
		return productDao.getCountBySupplier(supplierId);
	}

	@Override
	public void changeBrand(Long supplierId, Long shopId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		params.put("shopId", shopId);
		productDao.changeBrand(params);
	}

	@Override
	public Integer getCountBySupplierDate(Long supplierId,Date startDate,Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return productDao.getCountBySupplierDate(map);
	}

	@Override
	public Integer updateByBusiness(Product product) {
		return productDao.updateByBusiness(product);
	}

	@Override
	public void unlockExchangeProduct(Product map) {
		productDao.unlockExchangeProduct(map);
		cache(map.getId(),clientAccessLogService.getDetailPvCnt(null, map.getId()));
	}

	@Override
	public Product selectById(Long id) {
		return productDao.selectById(id);
		
	}

	@Override
	public void updateProduct(Long id) {
		//ProductVO product = productDao.getById(id);
		productDao.updateProductSelfType(id);
		Map<String, Object> old = wsm.getIndexById(id);
		if(old!=null) {
			wsm.deleteIndex(id);
		}
	}

	@Override
	public void updateProductSaleKbnByMap(Map<String, Object> map) {
		productDao.updateProductSaleKbnByMap(map);
	}

}
