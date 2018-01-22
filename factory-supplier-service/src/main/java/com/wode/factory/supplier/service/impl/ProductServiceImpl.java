/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.redis.RedisUtilEx;
import com.wode.common.util.ActResult;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.dao.ProductDao;
import com.wode.factory.supplier.query.ProductQuery;
import com.wode.factory.supplier.query.SpecificationValueQuery;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.ProductAttributeService;
import com.wode.factory.supplier.service.ProductDetailListService;
import com.wode.factory.supplier.service.ProductParameterValueService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ProductShippingService;
import com.wode.factory.supplier.service.ProductSpecificationValueService;
import com.wode.factory.supplier.service.ProductSpecificationsImageService;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.SpecificationValueService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.service.SupplierSpecificationService;

import cn.org.rapid_framework.page.Page;

@Service("productService-supplier")
public class ProductServiceImpl extends BaseService<Product, java.lang.Long> implements ProductService {
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;


	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;

	@Autowired
	private ProductShippingService productShippingService;	
	
	@Autowired
	private SupplierSpecificationService supplierSpecificationService;

	@Autowired
	private SpecificationValueService specificationValueService;
	
	@Autowired
	@Qualifier("productAttributeService")
	private ProductAttributeService productAttributeService;

	@Autowired
	@Qualifier("productParameterValueService")
	private ProductParameterValueService productParameterValueService;

	@Autowired
	@Qualifier("productSpecificationValueService")
	private ProductSpecificationValueService productSpecificationValueService;

	@Autowired
	@Qualifier("productDetailListService")
	private ProductDetailListService productDetailListService;

	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;

	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;

	@Autowired
	@Qualifier("productSpecificationsImageService")
	private ProductSpecificationsImageService productSpecificationsImageService;

	@Autowired
	private RedisUtilEx redisUtil;

	@Autowired
	private RedisUtil redis;

	public EntityDao getEntityDao() {
		return this.productDao;
	}

	public PageInfo findPage(ProductQuery query) {
		return productDao.findPage(query);
	}


	/**
	 * 获取商品列表（带分页）
	 *
	 * @param map
	 * @return
	 */
	public List<Product> findProductlistPage(Map map) {
		return setSkuStockFromRedis(productDao.findProductlistPage(map));
	}

	/**
	 * 获取商品列表总条数（带分页）
	 *
	 * @param map
	 * @return
	 */
	public Integer findProductlistPageCount(Map map) {
		return productDao.findProductlistPageCount(map);
	}

	/**
	 * 批量更新
	 *
	 * @param map
	 */
	public void updateProductByids(Map map) {
		productDao.updateProductByids(map);
	}

	/**
	 * 获取商品列表（带分页）
	 *
	 * @param map
	 * @return
	 */
	public List<Product> getProductByMap(Map map) {
		List<Product> products = productDao.getProductByMap(map);
		return setSkuStockFromRedis(products);
	}

	/**
	 * 获取实体类对象，包含评价不通过信息
	 *
	 * @return
	 */
	public Product getProductCheckById(Map map) {
		return setSkuStockFromRedis(productDao.getProductCheckById(map));
	}

	/**
	 * 销售排行榜
	 *
	 * @return
	 */
	public List<Product> getSaleroom(Map map) {
		return productDao.getSaleroom(map);
	}


	public ActResult sellOn(List<Long> productId, UserFactory user) {

		ActResult ret = ActResult.success(null);
		List<Long> idslist = new ArrayList<Long>();
		Supplier supplier = supplierService.getById(user.getSupplierId());
		for (Long pid : productId) {
			Product p = this.getById(pid);
			if (!supplier.getId().equals(p.getSupplierId())) {
				ret.setMsg(ret.getMsg() + p.getName() + "无权限操作;");
				ret.setSuccess(false);
				continue;
			}
			//审核通过的直接上架
			//if (p.getStatus() == 2) {
				p.setIsMarketable(1);
			//} else {//未通过的提交审核
				p.setStatus(1);
			//}
			productDao.saveOrUpdate(p);
			idslist.add(pid);
		}

		return ret.success(idslist);
	}


	public ActResult sellOff(List<Long> productId, UserFactory user) {
		ActResult ret = ActResult.success(null);
		Map map = new HashMap();

		if (productId.size() > 0) {
			map.put("idslist", productId);
			map.put("isMarketable", -2);
			map.put("status",0);
			map.put("locked",0);
			productDao.updateProductByids(map);
		}

		return ret;

	}

	private SupplierSpecification mergeSkuSpecification(Product product, String[] skuSpecification) {
		SupplierSpecification record = new SupplierSpecification();
		record.setCategoryId(product.getCategoryId());
		record.setSupplierId(product.getSupplierId());
		record.setType(1);
		List<SupplierSpecification> lst = supplierSpecificationService.selectByModel(record);
		
		SupplierSpecification specification = null;
		if(lst == null || lst.isEmpty()) {
			specification = new SupplierSpecification();
			specification.setCategoryId(product.getCategoryId());
			specification.setSupplierId(product.getSupplierId());
			specification.setName("规格");
			specification.setOrders(1);
			specification.setType(1);
			supplierSpecificationService.save(specification);
		} else {
			specification = lst.get(0);
		}
		SpecificationValueQuery query = new SpecificationValueQuery();
		query.setPageSize(100);
		query.setSpecificationId(specification.getId());
		Page p = specificationValueService.findPage(query);
		Map<String,SpecificationValue> mv = new HashMap<String,SpecificationValue>();
		int maxOrder=0;
		for (Object object : p) {
			SpecificationValue sv = (SpecificationValue)object;
			mv.put(sv.getName(), sv);
			if(sv.getOrders() != null && sv.getOrders()>maxOrder) {
				maxOrder = sv.getOrders();
			}
		}
		
		for (String value : skuSpecification) {
			if(!mv.containsKey(value)) {
				SpecificationValue sv = new SpecificationValue();
				sv.setName(value);
				sv.setOrders(++maxOrder);
				sv.setSpecificationId(specification.getId());
				
				specificationValueService.save(sv);
				
				mv.put(value, sv);
			}
		}
		return specification;
	}


	/**
	 * 根据供应商ID获取所有在售商品，以商品排位数降序排列
	 *
	 * @param supplierId 供应商ID
	 * @return
	 */
	public List<Product> getSellingBySupplierId(Long supplierId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		return setSkuStockFromRedis(productDao.getSellingBySupplierId(map));
	}

	/**
	 * 根据商品ID更新商品排位数
	 *
	 * @param productId 商品ID
	 * @param sortNum   商品展位排位数
	 */
	public void updateSortNum(Long productId, Integer sortNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("sortNum", sortNum);
		productDao.updateSortNum(map);
	}

	/**
	 * 冒泡排序
	 *
	 */
	public static void bubbleSort(List<BigDecimal> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 1; j < list.size() - i; j++) {
				BigDecimal a;
				if ((list.get(j - 1)).compareTo(list.get(j)) > 0) {   //比较两个整数的大小
					a = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, a);
				}
			}
		}
	}

	public List<Product> setSkuStockFromRedis(List<Product> products) {
		List<ProductSpecifications> skus;
		for (Product product : products) {
			skus = productSpecificationsService.getlistByProductid(product.getId());
			if (skus.size() > 0) {
				Integer allnum=0;
				for (ProductSpecifications sku : skus) {
					String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + sku.getId());
					if (StringUtils.isNotBlank(stock)){
						sku.setStock(Integer.valueOf(stock));
						allnum += Integer.valueOf(stock);
					}
				}
				product.setAllnum(allnum);
			}
		}
		return products;
	}

	public Product setSkuStockFromRedis(Product product) {
		List<ProductSpecifications> skus = product.getProductSpecificationslist();
		if (skus.size() > 0) {
			for (ProductSpecifications sku : skus) {
				String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + sku.getId());
				if (StringUtils.isNotBlank(stock))
					sku.setStock(Integer.valueOf(stock));
			}
		}
		return product;
	}

	public static void main(String[] args) {
//		List<Long> list = null;
//		for (Long i : list) {
//			System.out.println();
//		}
	}

	@Override
	public List<Product> findProduct(ProductQuery productQuery) {
		return this.productDao.findProduct(productQuery);
	}

	@Override
	public List<Product> getNotDeleteProductByFullName(Product product) {
		return this.productDao.getNotDeleteProductByFullName(product);
	}
	/**
	 * 根据商品id获取第三方价格
	 */
	@Override
	public List<ProductThirdPrice> getProductThirdPriceByProductId(Long id) {
		return this.productDao.getProductThirdPriceByProductId(id);
	}

	@Override
	public Long getSupplierFullname(Map map) {	
		return this.productDao.getSupplierFullname(map);
	}

	@Override
	public List<Product> findProductlist(Map<String, Object> map) {
		return this.productDao.findProductlist(map);
	}

	@Override
	public void changProductOuterId(Map map) {
		 this.productDao.changProductOuterId(map);
	}
}

