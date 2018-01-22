package com.wode.factory.supplier.open.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.wode.common.redis.RedisUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.OpenRequestBase;
import com.wode.factory.model.OpenResponse;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.model.open.SkuInfo;
import com.wode.factory.model.open.SkusGet;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ProductSpecificationsService;
import com.wode.factory.supplier.service.SupplierLogService;


@Controller
@RequestMapping("open/supplier")
public class OpenProductController  extends BaseController {
	
	/**
	 * 商品
	 */
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	/**
	 * sku
	 */
	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;
	/**
	 * 库存
	 */
	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;
	
	@Autowired
	@Qualifier("supplierLogService")
	private SupplierLogService supplierLogService;
	@Autowired
	private RedisUtil redis;
	
	@RequestMapping(value="products/get")
	@ResponseBody
	public Object get(HttpServletRequest request,HttpServletResponse response,OpenRequestBase openRequestBase) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", openRequestBase.getSupplierId());
		List<Product> list = productService.findProductlist(map);
		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
		if(null != list && list.size() > 0){
			for (Product product : list) {
				Map<String,Object> result = new HashMap<String, Object>();
				result.put("p_id", product.getId());
				result.put("p_title", product.getFullName());
				result.put("p_category", product.getCategoryName());
				result.put("outer_id", product.getOuterId());
				resultMap.add(result);
			}
		}
		return OpenResponse.success(resultMap);
	}
	
	@RequestMapping(value="product/skus/get")
	@ResponseBody
	public Object skusGet(HttpServletRequest request,HttpServletResponse response,SkusGet skusGet) {
		String pId = skusGet.getP_id();
		String pTitle = skusGet.getP_title();
		//校验参数
		if(StringUtils.isNullOrEmpty(pId) && StringUtils.isNullOrEmpty(pTitle) &&  StringUtils.isNullOrEmpty(skusGet.getOuter_id())){
			return OpenResponse.fail("p_id ,Outer_id 和 p_title 不能同时为空");
		}
		//查询集合
		List<ProductSpecifications> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productId", pId);
		map.put("supplierId", skusGet.getSupplierId());
		map.put("pTitle", pTitle);
		map.put("outerId", skusGet.getOuter_id());
		list = productSpecificationsService.getProductSpecificationsByProductId(map);
		
		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
		if(null != list && list.size() > 0){
			for (ProductSpecifications productSpecifications : list) {
				Map<String,Object> result = new HashMap<String, Object>();
				result.put("sku_id", productSpecifications.getId());
				result.put("price", productSpecifications.getInternalPurchasePrice());
				result.put("quantity", productSpecifications.getQuantity());
				result.put("spec", dealItemValues(productSpecifications.getItemValues()));
				result.put("image", productSpecifications.getMainImage());
				result.put("outer_id", productSpecifications.getOuterId());
				resultMap.add(result);
			}
		}
		return OpenResponse.success(resultMap);
	}
	/**
	 * 更新product 的外部订单id
	 * @param request
	 * @param response
	 * @param skusGet
	 * @return
	 */
	@RequestMapping(value="product/outerid/upd")
	@ResponseBody
	public Object productUpd(HttpServletRequest request,HttpServletResponse response,SkusGet skusGet) {
		String pId = skusGet.getP_id();
		String pTitle = skusGet.getP_title();
		//校验参数
		if((StringUtils.isNullOrEmpty(pId) && StringUtils.isNullOrEmpty(pTitle)) ||  StringUtils.isNullOrEmpty(skusGet.getOuter_id())){
			return OpenResponse.fail("p_id 和 p_title 不能同时为空");
		}
		//查询集合
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pId", pId);
		map.put("supplierId", skusGet.getSupplierId());
		map.put("pTitle", pTitle);
		List<Product> list = productService.findProductlist(map);
		if(null != list && !list.isEmpty()){
			for (Product product : list) {
				Map<String, Object> quaryMap = new HashMap<String, Object>();
				quaryMap.put("outerId", skusGet.getOuter_id());
				quaryMap.put("id", product.getId());
				productService.changProductOuterId(quaryMap);
			}
			return OpenResponse.success(null);
		}
		return OpenResponse.fail(null);
	}
	/**
	 * 更新库存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="product/sku/upd")
	@ResponseBody
	public Object skuUpd(HttpServletRequest request,HttpServletResponse response,OpenRequestBase openRequestBase,SkuInfo skuInfo) {
		String productName = skuInfo.getP_title();
		Long quantity = skuInfo.getQuantity();
		String spec = skuInfo.getSpec();
		String skuId = skuInfo.getSku_id();
		if(quantity ==  null){
			return OpenResponse.fail("库存数量不能为空!");
		}
		
		//判断查询的是否是多个sku
		List<String> skuIds = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", skuInfo.getSupplierId());
		Inventory inventory = null;
		if(StringUtils.isNullOrEmpty(skuId) && StringUtils.isNullOrEmpty(productName) && StringUtils.isNullOrEmpty(spec) && StringUtils.isNullOrEmpty(skuInfo.getOuter_id()) ){
			return OpenResponse.fail("参数有误，请确认参数信息!");
		}else{
			if(spec!=null && spec.indexOf(",")!=-1){
				spec = spec.replace(',', '%');
			}
			map.put("productName", productName);
			map.put("spec", spec);
			map.put("skuId", skuId);
			map.put("outerId", skuInfo.getOuter_id());
			
			List<ProductSpecifications> list = productSpecificationsService.findSkuByProductNameAndItemValue(map);
			if (null != list && !list.isEmpty()) {
				if(list.size() > 0){
					for (ProductSpecifications productSpecifications : list) {
						skuIds.add(productSpecifications.getId().toString());
					}
				}
			} else {
				return OpenResponse.fail("参数有误，请确认参数信息!");
			}
		}
		if(skuIds.size() > 0){
			for (String string : skuIds) {
				Integer inventoryFromRedis = getInventoryFromRedis(Long.valueOf(string));
				if(!Integer.valueOf(quantity.toString()).equals(inventoryFromRedis)){//修改跟原库存一样不要操作
					//先清空缓存，在修改表
					String ret = "库存由" + inventoryFromRedis + "修改为" + quantity + ";";
					String key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(string);
					redis.del(key);
					redis.setData(key, quantity.toString());
					redis.removeSet(RedisConstant.Inventory_CHANGE, string + "");
					inventory = findBySpecId(Long.valueOf(string));
					inventory.setQuantity(Integer.valueOf(quantity.toString()));
					inventoryService.saveOrUpdate(inventory);
					//日志
					SupplierLog log = new SupplierLog();
					log.setUserId(Long.valueOf(skuInfo.getSupplierId()));
					log.setAct("商家（id='" + skuInfo.getSupplierId() + "')修改商品sku(id='" + inventory.getProductSpecificationsId() + "')的库存属性");
					log.setTime(new Date());
					log.setResult(ret);
					supplierLogService.save(log);
				}
				
			}
			
		}
		
		return OpenResponse.success(quantity);
	}
	/**
	 * 更新sku的外部订单id
	 * @param request
	 * @param response
	 * @param openRequestBase
	 * @param skuInfo
	 * @return
	 */
	@RequestMapping(value="product/sku/outerid/upd")
	@ResponseBody
	public Object outeridUpd(HttpServletRequest request,HttpServletResponse response,OpenRequestBase openRequestBase,SkuInfo skuInfo) {
		String productName = skuInfo.getP_title();
		String spec = skuInfo.getSpec();
		String skuId = skuInfo.getSku_id();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", skuInfo.getSupplierId());
		if((StringUtils.isNullOrEmpty(skuId) && StringUtils.isNullOrEmpty(productName) && StringUtils.isNullOrEmpty(spec)) || StringUtils.isNullOrEmpty(skuInfo.getOuter_id()) ){
			return OpenResponse.fail("参数有误，请确认参数信息!");
		}else{
			if(spec!=null && spec.indexOf(",")!=-1){
				spec = spec.replace(',', '%');
			}
			map.put("productName", productName);
			map.put("spec", spec);
			map.put("skuId", skuId);
			
			List<ProductSpecifications> list = productSpecificationsService.findSkuByProductNameAndItemValue(map);
			if (null == list || list.isEmpty()) {
				return OpenResponse.fail("参数有误，请确认参数信息!");
			}else{
				for (ProductSpecifications productSpecifications : list) {
					productSpecifications.setOuterId(skuInfo.getOuter_id());
					productSpecificationsService.update(productSpecifications);
				}
				return OpenResponse.success(null);
			}
		}
	}
	/**
	 * 处理规格值
	 * @param itemValues
	 * @return
	 */
	public static String dealItemValues(String itemValues){
		if(!StringUtils.isNullOrEmpty(itemValues)){
			String str = "";
			itemValues = itemValues.replace("\"", "").replace("{", "").replace("}", "");
			String[] d = itemValues.split(",");
			if(null != d && d.length >0){
				for (String string : d) {
					String[] f = string.split(":");
					if(null != f && f.length >1){
						str+=f[1] + ",";
					}
				}
				if(!StringUtils.isNullOrEmpty(str)){
					return str.substring(0,str.length()-1);
				}
			}
		}
		return "";
	}
	
	/**
	 * 从redis中获取库存数量, 如果获取不到则从数据库中获取并放置到redis中
	 *
	 * @param skuId
	 * @return
	 */
	public Integer getInventoryFromRedis(long skuId) {
		String key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
		String inventory = redis.getData(key);
		if (inventory != null) {
			return Integer.valueOf(inventory);
		} else {
			Inventory entity = findBySpecId(skuId);
			redis.setData(key, String.valueOf(entity.getQuantity()));
			return entity.getQuantity();
		}
	}

	private Inventory findBySpecId(long skuId) {
		return inventoryService.findBySpecId(skuId);
	}
}
