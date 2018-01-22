/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.Specification;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.SupplierProductExcel;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.supplier.dao.ApprProductDao;
import com.wode.factory.supplier.dao.InventoryDao;
import com.wode.factory.supplier.dao.ProductBrandDao;
import com.wode.factory.supplier.dao.ProductCategoryDao;
import com.wode.factory.supplier.dao.ProductDao;
import com.wode.factory.supplier.dao.ProductShippingDao;
import com.wode.factory.supplier.dao.ProductSpecificationValueDao;
import com.wode.factory.supplier.dao.ProductSpecificationsDao;
import com.wode.factory.supplier.dao.ProductSpecificationsImageDao;
import com.wode.factory.supplier.dao.ShippingTemplateDao;
import com.wode.factory.supplier.dao.SpecificationDao;
import com.wode.factory.supplier.dao.SpecificationValueDao;
import com.wode.factory.supplier.dao.SupplierProductExcelDao;
import com.wode.factory.supplier.dao.SupplierSkuImageDao;
import com.wode.factory.supplier.dao.SupplierSpecificationDao;
import com.wode.factory.supplier.query.BatchAddProductSku;
import com.wode.factory.supplier.query.BatchAddProductVo;
import com.wode.factory.supplier.query.SupplierProductExcelQuery;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.SupplierProductExcelService;

@Service("supplierProductExcelService")
public class SupplierProductExcelServiceImpl extends BaseService<SupplierProductExcel,java.lang.Long> implements  SupplierProductExcelService{
	@Autowired
	@Qualifier("supplierProductExcelDao")
	private SupplierProductExcelDao supplierProductExcelDao;
	
	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;	
	@Autowired
	private DBUtils dbUtils;
	
	@Autowired
	private RedisUtil redis;
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;
	
	@Autowired
	@Qualifier("specificationValueDao")
	private SpecificationValueDao specificationValueDao;
	
	@Autowired
	@Qualifier("productShippingDao")
	private ProductShippingDao productShippingDao;
	@Autowired
	@Qualifier("shippingTemplateDao")
	private ShippingTemplateDao shippingTemplateDao;
	
	@Autowired
	@Qualifier("productBrandDao")
	private ProductBrandDao productBrandDao;

	@Autowired
	@Qualifier("supplierSpecificationDao")
	private SupplierSpecificationDao supplierSpecificationDao;
	@Autowired
	@Qualifier("specificationDao")
	private SpecificationDao specificationDao;
	@Autowired
	@Qualifier("productSpecificationsDao")
	private ProductSpecificationsDao productSpecificationsDao;
	
	@Autowired
	@Qualifier("productSpecificationValueDao")
	private ProductSpecificationValueDao productSpecificationValueDao;
	@Autowired
	@Qualifier("supplierSkuImageDao")
	private SupplierSkuImageDao supplierSkuImageDao;
	@Autowired
	@Qualifier("productSpecificationsImageDao")
	private ProductSpecificationsImageDao productSpecificationsImageDao;
	@Autowired
	@Qualifier("productCategoryDao")
	private ProductCategoryDao productCategoryDao;
	@Autowired
	@Qualifier("inventoryDao")
	private InventoryDao inventoryDao;
	@Autowired
	private ApprProductDao apprProductDao;
	public EntityDao getEntityDao() {
		return this.supplierProductExcelDao;
	}

	@Override
	public PageInfo<SupplierProductExcelQuery> selectPageInfo(SupplierProductExcelQuery productExcelQuery) {
		return this.supplierProductExcelDao.selectPageInfo(productExcelQuery);
	}
	
	
	@Override
	//public Product saveProduct(BatchAddProductVo pro, Long supplierId) {
	public ApprProduct saveProduct(BatchAddProductVo pro, Long supplierId) {
//		// 运费模板
//		ProductShipping proShip = null;
		// 临时表id
		Long id=dbUtils.CreateID();
		// 临时表product_id
		Long productId = dbUtils.CreateID();
		//
		/**
		 * t_product
		 * */
		//Product proModel = new Product();
		ApprProduct proModel = new ApprProduct();
		proModel.setId(id);
		proModel.setProductId(productId);
		///proModel.setId(productId);
		proModel.setFullName(pro.getFullName());// 标题
		proModel.setName(pro.getName());// 副标题
//		proModel.setPromotion(pro.getPromotion());// 广告语
//		proModel.setMarque(pro.getMarque());// 型号
//		proModel.setBarcode(pro.getBarcode());// 条形码
//		proModel.setRoughWeight(pro.getRoughWeight());// 毛重
//		proModel.setNetWeight(pro.getNetWeight());// 净重
//		proModel.setLength(pro.getLength());// 单品长
//		proModel.setWidth(pro.getWidth());// 单品宽
//		proModel.setHeight(pro.getHeight());// 单品高
//		proModel.setBulk(pro.getBulk());// 单品体积
		proModel.setSupplierId(supplierId);// 商家id
//		proModel.setProvince(pro.getProvince());// 省
//		proModel.setTown(pro.getTown());// 市
//		proModel.setCounty(pro.getCounty());// 县
//		proModel.setProduceaddress(pro.getProduceaddress());// 产地
//		proModel.setSendProvince(pro.getSendProvince());
//		proModel.setSendTown(pro.getSendTown());
//		proModel.setSendAddress(pro.getSendAddress());// 发货地址
		proModel.setShopId(pro.getShopId());// 供应商id
		proModel.setBrandId(pro.getBrandId());// 品牌
		proModel.setImage(pro.getImage());
		// 商品种类查询
		proModel.setCategoryId(pro.getCategoryId());

//		// 运费模板
//		if (!StringUtils.isNullOrEmpty(pro.getShippingTemplate())) {
//			ShippingTemplate template = new ShippingTemplate();
//			template.setSupplierId(supplierId);
//			template.setName(pro.getShippingTemplate());
//			List<ShippingTemplate> templates = this.shippingTemplateDao.selectByModel(template);
//			if (StringUtils.isNullOrEmpty(templates) || templates.isEmpty()) {
//				for (BatchAddProductSku sku : pro.getSku()) {
//					sku.setProcessingResult(new StringBuffer(sku.getProcessingResult()).append("商品模板不存在").toString());
//				}
//				return null;
//			} else {
//				/**
//				 * t_product_shipping
//				 * */
//				proShip = new ProductShipping();
//				proShip.setProductId(productId);
//				proShip.setTemplateId(templates.get(0).getId());
//			}
//			proModel.setCarriage(BigDecimal.ZERO);
//		} else {
//			proModel.setCarriage(pro.getCarriage());
//		}
		proModel.setIntroduction(pro.getIntroduction());// 详情
		proModel.setIntroductionMobile(pro.getIntroductionMobile());// 详情
		proModel.setAfterService(pro.getAfterService());// 售后服务
		proModel.setStockLockType(pro.getStockLockType());// 其它服务

		proModel.setType("1");
		proModel.setModel("1");
		proModel.setIsMarketable(0);// 0暂存
		proModel.setStatus(0);// 待审核
		proModel.setLimitCnt(0);
		proModel.setAreasCode("0");
		proModel.setAreasName("全国");
		proModel.setCreateDate(new Date());
		proModel.setSaleKbn(0);// 销售区分 0为普通，1为特省。默认为0
		proModel.setLimitKbn(0);//这个字段报错提示不能为空
		proModel.setLocked(0);//这个字段报错提示不能为空
		BigDecimal maxPrice = BigDecimal.ZERO;
		BigDecimal minPrice = null;
		Integer allNum = 0;
		// 商品sku信息
		for (BatchAddProductSku sku : pro.getSku()) {
			if (sku.getIsCouldAdd()) {
				// /////////////////////////////////////////////////////////////////
				allNum += sku.getStock();// 增加库存
				if (maxPrice.compareTo(sku.getPrice()) < 0) {// 查找价格最大值
					maxPrice = sku.getPrice();
				}

				if (StringUtils.isNullOrEmpty(minPrice)) {// 最小值
					minPrice = sku.getPrice();
				} else {
					if (minPrice.compareTo(sku.getPrice()) > 0) {
						minPrice = sku.getPrice();
					}
				}
				// /////////////////////////////////////////////////////////////////
			}
		}
		proModel.setMaxprice(maxPrice);// 最大价格
		proModel.setMinprice(minPrice);// 最小价格
		proModel.setAllnum(allNum);// sku库存之和
		proModel.setSavestate(1);
		proModel.setQuestionnaireId(-1L);
		// 保存商品
		//this.productDao.insert(proModel);
		this.apprProductDao.insert(proModel);
//		// 保存运费模板
//		if (!StringUtils.isNullOrEmpty(proShip)) {
//			this.productShippingDao.save(proShip);
//		}
		return proModel;
	}
	
	/**
	 * 获得规格值信息
	 * @param categoryName
	 * @param skuName
	 * @param supplierId
	 * @return
	 */
	public SpecificationValue getSpecificationValue(String categoryName,String skuName,Long supplierId,int orders){
		SpecificationValue specification = this.specificationValueDao.findSpecificationValue(categoryName, skuName, null,orders);
		if(StringUtils.isNullOrEmpty(specification)){
			specification = this.specificationValueDao.findSpecificationValue(categoryName, skuName,supplierId,orders);
		}
		return specification;
	}
	/**
	 * 获取规格值中最大的排序值
	 * @param specificationId
	 * @return
	 */
	private int getSpeValueMaxOrders(Long specificationId){
		SpecificationValue speValue = new SpecificationValue();
		speValue.setSpecificationId(specificationId);
		List<SpecificationValue> speValues = this.specificationValueDao.findSpecificationValue(speValue);
		int order = 0;
		for(SpecificationValue spe :speValues){
			if((StringUtils.isNullOrEmpty(spe.getOrders())?0:spe.getOrders())>order){
				order = spe.getOrders();
			}
		}
		return order;
	}
	
	private String getSpecificationName(Long specificationId){
		Specification specification = this.specificationDao.getById(specificationId);
		if(StringUtils.isNullOrEmpty(specification)){
			SupplierSpecification supplierSpecification = this.supplierSpecificationDao.getById(specificationId);
			if(StringUtils.isNullOrEmpty(supplierSpecification)){
				return null;
			}else{
				return supplierSpecification.getName();
			}
		}else{
			return specification.getName();
		}
	}
	private ProductSpecificationsImage setProSpeImage(String url,Long supplierId,Long specificationId,Integer order){
		ProductSpecificationsImage image = new ProductSpecificationsImage();
		image.setCreateDate(new Date());
		image.setSupplyId(supplierId);
		image.setSpecificationsId(specificationId);
		image.setSource(url);
		image.setOrders(order);
		return image;
	}

	/* 获取未处理的zip文件。升序取一条
	 */
	@Override
	public SupplierProductExcel getUndisposedByTimeAsc() {
		return this.supplierProductExcelDao.getUndisposedByTimeAsc();
	}

	@Override
	public void updateSupplierProductExcel(SupplierProductExcel supplierProductExcel, int status,String processingResult) {
		supplierProductExcel.setStatus(status);
		supplierProductExcel.setProcessingResult(processingResult);
		this.supplierProductExcelDao.update(supplierProductExcel);
	}
    /**
     * 保存sku
     */
	@Override
	public int saveProductSku(Long productId,BatchAddProductVo product, Long supplierId,Map<String,List<String>> skuImages) {//传过来的就是临时表的id
		List<BatchAddProductSku> skus = product.getSku();
		if(StringUtils.isNullOrEmpty(skus)||skus.isEmpty()){
			return 0;//用户统计保存sku条数
		}
		//规格一
		Map<String,BatchAddProductSku> speOneValues = new HashMap<String, BatchAddProductSku>();
		Map<String,BatchAddProductSku> speTwoValues = new HashMap<String, BatchAddProductSku>();
		//规格二
//		List<BatchAddProductSku> speTwoValue = new ArrayList<BatchAddProductSku>();
		
		for(BatchAddProductSku sku : skus){
			if(sku.getIsCouldAdd()){
				// key  sku1值   value  sku1值
				if(!speOneValues.containsKey(sku.getSpe1Value())){
					speOneValues.put(sku.getSpe1Value(), sku);
				}
				if(!StringUtils.isNullOrEmpty(sku.getSpe2Value())){
					if(!speTwoValues.containsKey(sku.getSpe2Value())){
						speTwoValues.put(sku.getSpe2Value(), sku);
					}
				}
			}
		}
		/**
		 * 先保存规格值信息
		 * 规格一值先保存，规格二值在规格一之后面在保存
		 * */
		SupplierSpecification supplierSpe1 = null;
		Map<String,SpecificationValue> mapValues1 = null;

		SupplierSpecification model = new SupplierSpecification();
		model.setSupplierId(supplierId);
		model.setCategoryId(product.getCategoryId());
		model.setType(2);
		List<SupplierSpecification> ls = this.supplierSpecificationDao.selectByModel(model);
		
		//先保存规格一值
		for (String key : speOneValues.keySet()) {
			BatchAddProductSku speValues = speOneValues.get(key);
			ProductSpecificationValue proSpeValue = new ProductSpecificationValue();
			if(speValues.isCustomSpe()){//自定义规格
				//保存规格
				if(supplierSpe1 == null){
					//保存规格1和规格1值
					supplierSpe1 = saveSpe(ls,supplierId,product.getCategoryId(),speValues.getSpe1(),1);
					
					mapValues1 = getSpeValueMap(supplierSpe1.getId());
				}
				//保存规格值
				SpecificationValue speValue = saveSpeValue(supplierSpe1.getId(), mapValues1, speValues.getSpe1Value());
				
				proSpeValue.setOrders(speValue.getOrders());//规格值排序
				proSpeValue.setSpecificationId(speValue.getSpecificationId());//规格id
			}else{
				proSpeValue.setOrders(speValues.getSpe1ValueOrders());//规格值排序
				proSpeValue.setSpecificationId(speValues.getSpe1Id());//规格id
			}
			proSpeValue.setId(dbUtils.CreateID());//id
			proSpeValue.setSpecificationValue(speValues.getSpe1Value());//规格一值
			proSpeValue.setProductId(productId);//id
			//proSpeValue.setProductId(product.getId());//我们还是使用临时表的id来关联sku等属性，但是315行我已经做了处理productId所以不用
			proSpeValue.setIsDelete(0);
			this.productSpecificationValueDao.insert(proSpeValue);
		}
		//接着保存规格二值
		if(!speTwoValues.isEmpty()){
			SupplierSpecification supplierSpe2 = null;
			Map<String,SpecificationValue> mapValues2 = null;
			for (String key : speTwoValues.keySet()) {
				BatchAddProductSku speValues = speTwoValues.get(key);
				ProductSpecificationValue proSpeValue = new ProductSpecificationValue();
				if(speValues.isCustomSpe()){//自定义规格
					//保存规格
					if(supplierSpe2 == null){
						//保存规格2和规格2值
						supplierSpe2 = saveSpe(ls,supplierId,product.getCategoryId(),speValues.getSpe2(),2);
						
						if(supplierSpe2==null) return 0;
						mapValues2 = getSpeValueMap(supplierSpe2.getId());
					}
					//保存规格值
					SpecificationValue speValue = saveSpeValue(supplierSpe2.getId(), mapValues2, speValues.getSpe2Value());
					
					//商品规格值
					proSpeValue.setOrders(speValue.getOrders());//规格值排序
					proSpeValue.setSpecificationId(speValue.getSpecificationId());//规格id
				}else{
					proSpeValue.setOrders(speValues.getSpe2ValueOrders());//规格值排序
					proSpeValue.setSpecificationId(speValues.getSpe2Id());//规格id
				}
					proSpeValue.setId(dbUtils.CreateID());//id
					proSpeValue.setSpecificationValue(speValues.getSpe2Value());//规格二值名称
					proSpeValue.setProductId(productId);//id
					//proSpeValue.setProductId(product.getId());////我们还是使用临时表的id来关联sku等属性，但是315行我已经做了处理productId所以不用
					proSpeValue.setIsDelete(0);
					this.productSpecificationValueDao.insert(proSpeValue);
				
				
			}
		}
		int number = 0;
		//生成商品sku信息
		for(BatchAddProductSku  allSpeValue :skus){
			if(!allSpeValue.getIsCouldAdd()){//sku不能保存,跳出本次循环
				continue;
			}
			
			//接下来保存sku
			ProductSpecifications proSpe = new ProductSpecifications();
			/**
			 * t_product_specifications
			 * */
			proSpe.setId(dbUtils.CreateID());
			proSpe.setProductId(productId);
			//proSpe.setProductId(product.getId());////我们还是使用临时表的id来关联sku等属性，但是315行我已经做了处理productId所以不用
			proSpe.setPrice(allSpeValue.getPrice());// 价格
			proSpe.setIsDelete(0);// 0未删除
			proSpe.setWarnnum(allSpeValue.getWarnnum());// 预警值
			proSpe.setMaxFucoin(allSpeValue.getMaxFucoin());// 内购券
			proSpe.setProductCode(allSpeValue.getProductCode());
			proSpe.setInternalPurchasePrice(allSpeValue.getRealPrice());//内购价
			Map<String, String> ItemValues = new LinkedHashMap<String, String>();
			//查询商品规格一值是否存在
			ProductSpecificationValue proSpeOneValue = getProductSpecificationValue(productId, allSpeValue.getSpe1Value());
			if(!StringUtils.isNullOrEmpty(proSpeOneValue)){
				
				ItemValues.put(getSpecificationName(proSpeOneValue.getSpecificationId()), proSpeOneValue.getSpecificationValue());
				
				if(StringUtils.isNullOrEmpty(allSpeValue.getSpe2Value())){
					proSpe.setItemids(proSpeOneValue.getId()+"");
				}else{
					ProductSpecificationValue proSpeTwoValue = getProductSpecificationValue(productId, allSpeValue.getSpe2Value());
					if(!StringUtils.isNullOrEmpty(proSpeTwoValue)){
						proSpe.setItemids(proSpeOneValue.getId()+","+proSpeTwoValue.getId());
						ItemValues.put(getSpecificationName(proSpeTwoValue.getSpecificationId()), proSpeTwoValue.getSpecificationValue());
					}
				}
				proSpe.setItemValues(JSON.toJSONString(ItemValues));
				this.productSpecificationsDao.insert(proSpe);//保存sku
				
				//库存
				Inventory inventory = new Inventory();
				inventory.setProductSpecificationsId(proSpe.getId());
				inventory.setLockQuantity(0);
				inventory.setQuantity(allSpeValue.getStock());
				inventory.setWarnQuantity(allSpeValue.getWarnnum());
				//保存库存预警值
				inventoryService.saveOrUpdate(inventory);
			}
			
			//保存图片
			List<String> images = skuImages.get(allSpeValue.getSpe1Value());
			if(images!=null){
				for(int i = 0 ;i<images.size();i++){
					this.productSpecificationsImageDao.save(setProSpeImage(images.get(i), supplierId, proSpe.getId(), i+1));
				}
			}
			number++;//成功加一条
		}
		return number;
	}
    //保存规格值
	private SpecificationValue saveSpeValue(Long speId,Map<String, SpecificationValue> mapValues, String name) {
		
		SpecificationValue speValue = null;
		if(mapValues.containsKey(name)) {
			speValue=mapValues.get(name);
		} else {
			speValue = new SpecificationValue();
			speValue.setId(dbUtils.CreateID());
			speValue.setSpecificationId(speId);
			speValue.setName(name);
			speValue.setOrders(mapValues.size()+1);
			this.specificationValueDao.save(speValue);
			
			mapValues.put(speValue.getName(),speValue);
		}
		return speValue;
	}

	private Map<String, SpecificationValue> getSpeValueMap(Long speId) {
		Map<String, SpecificationValue> mapValues = new HashMap<String, SpecificationValue>();
		SpecificationValue speValue = new SpecificationValue();
		speValue.setSpecificationId(speId);
		List<SpecificationValue> values= specificationValueDao.findSpecificationValue(speValue);
		for (SpecificationValue specificationValue : values) {
			mapValues.put(specificationValue.getName(), specificationValue);
		}
		
		return mapValues;
	}

	private SupplierSpecification saveSpe(List<SupplierSpecification> ls,Long supplierId,Long categoryId, String name,int orders) {

		
		if(ls!=null && !ls.isEmpty()) {
			if(orders==1) return ls.get(0);
			
			if(orders==2 && ls.size()>1) return ls.get(1);
			
			if(orders==2 && ls.size()==1) {
				return null; //就规格中没有规格2
			}
		}
		
		SupplierSpecification supplierSpe1;
		supplierSpe1 = new SupplierSpecification();
		supplierSpe1.setId(dbUtils.CreateID());
		supplierSpe1.setSupplierId(supplierId);
		supplierSpe1.setName(name);
		supplierSpe1.setCategoryId(categoryId);
		supplierSpe1.setOrders(orders);
		supplierSpe1.setType(2);
		supplierSpe1.setCreatedDate(new Date());
		this.supplierSpecificationDao.save(supplierSpe1);
		return supplierSpe1;
	}
	
	
	/**
	 * 获取商品规格值
	 * @param product
	 * @param speValue
	 * @return
	 */
	public ProductSpecificationValue getProductSpecificationValue(Long product,String speValue){
		ProductSpecificationValue proSpeValue = new ProductSpecificationValue();
		proSpeValue.setProductId(product);
		proSpeValue.setSpecificationValue(speValue);
		//查询规格一是否存在
		List<ProductSpecificationValue> speValues = this.productSpecificationValueDao.selectByModel(proSpeValue);
		if(speValues.isEmpty()){
			return null;
		}else{
			return speValues.get(0);
		}
	}
}
