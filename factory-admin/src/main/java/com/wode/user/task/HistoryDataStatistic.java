package com.wode.user.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ApprProductDao;
import com.wode.factory.mapper.ApprShopDao;
import com.wode.factory.mapper.ApprSupplierDao;
import com.wode.factory.mapper.AttachmentMapper;
import com.wode.factory.mapper.BrandProducttypeDao;
import com.wode.factory.mapper.InventoryDao;
import com.wode.factory.mapper.PaymentDao;
import com.wode.factory.mapper.ProductAttributeMapper;
import com.wode.factory.mapper.ProductBrandImageDao;
import com.wode.factory.mapper.ProductBrandMapper;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.ProductDetailListMapper;
import com.wode.factory.mapper.ProductParameterValueMapper;
import com.wode.factory.mapper.ProductShippingMapper;
import com.wode.factory.mapper.ProductSpecificationValueMapper;
import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.mapper.ProductSpecificationsImageDao;
import com.wode.factory.mapper.ShopDao;
import com.wode.factory.mapper.SupplierCategoryMapper;
import com.wode.factory.mapper.SupplierCloseCmdDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.Attachment;
import com.wode.factory.model.BrandProducttype;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Payment;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.Shop;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.service.ApprProductMongoService;
import com.wode.factory.service.ApprShopMongoService;
import com.wode.factory.service.ApprSupplierMongoService;
import com.wode.factory.service.PaymentMongoService;
import com.wode.factory.service.SupplierCloseCmdMongoService;
import com.wode.factory.vo.ProductVO;

/**
 * 临时商品历史数据统计
 * @author user
 *
 */
@Service
public class HistoryDataStatistic {
	@Autowired
	private ApprProductDao apprProductDao;
	@Autowired
	private ApprShopDao apprShopDao;
	@Autowired
	private ApprSupplierDao apprSupplierDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductSpecificationsDao productSpecificationsDao;
	@Autowired
	private ProductAttributeMapper productAttributeDao;
	@Autowired
	private ProductSpecificationValueMapper productSpecificationValueDao;
	@Autowired
	private ProductDetailListMapper productDetailListDao;
	@Autowired
	private ProductParameterValueMapper productParameterValueDao;
	@Autowired
	private ProductShippingMapper productShippingDao;
	@Autowired
	private ProductSpecificationsImageDao productSpecificationsImageDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private ApprProductMongoService apprProductMongoService;
	@Autowired
	private ApprSupplierMongoService apprSupplierMongoService;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ProductBrandMapper productBrandDao;
	@Autowired
	private ProductBrandImageDao productBrandImageDao;
	@Autowired
	private BrandProducttypeDao brandProducttypeDao;
	@Autowired
	private SupplierCategoryMapper supplierCategoryDao;
	@Autowired
	private AttachmentMapper attachmentDao;
	@Autowired
	private ApprShopMongoService apprShopMongoService;
	@Autowired
	private SupplierCloseCmdDao supplierCloseCmdDao;
	@Autowired
	private SupplierCloseCmdMongoService supplierCloseCmdMongoService;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private PaymentMongoService paymentMongoService;
	/**
	 * 添加商品历史数据每天统计信息
	 */
	public void run() {
		// 获取当前系统时间
		Calendar date = Calendar.getInstance();
		// 设置创建时间 将月份减3表示当前时间的三个月时间
		date.set(Calendar.MONTH, (int) (date.get(Calendar.MONTH) - 3));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creatTime", date.getTime());
		
		productHistoryStatistic(map);
		supplierHistoryStatistic(map);
		shopHistoryStatistic(map);
		saleBillHistoryStatistic(map);
		payMentHistoryStatistic(map);
	}
	
	/**
	 * 商品系历史数据
	 * @param map 
	 * @param date 
	 */
	public void productHistoryStatistic(Map<String, Object> map){
		// 获取当前时间半年以前的数据
		List<ApprProduct> apprProductList = apprProductDao.findAllByCreateDate(map);
		if (!apprProductList.isEmpty() && apprProductList.size() > 0) {
			for (ApprProduct apprProduct : apprProductList) {
				Long id = apprProduct.getId();
				ProductVO productVo = productDao.getById(id);
				if (productVo != null) {
					// 先保存再删除
					apprProductMongoService.save(apprProduct);
					apprProductDao.deleteById(id);
				} else {
					List<ProductSpecifications> productSpecifications = productSpecificationsDao.findlistByProductid(id);
					List<ProductSpecificationValue> productSpecificationValue = productSpecificationValueDao.getByProductId(id);
					List<ProductSpecificationsImage> psImageList = null;
					List<Inventory> inventoryList = null;
					if (productSpecifications != null && !productSpecifications.isEmpty()) {// 查看sku主图和库存
						psImageList = new ArrayList<ProductSpecificationsImage>();
						inventoryList = new ArrayList<Inventory>();
						for (ProductSpecifications ps : productSpecifications) {
							List<ProductSpecificationsImage> psImage = productSpecificationsImageDao.findlistByProductSpecificationsid(ps.getId());// sku主图
							psImageList.addAll(psImage);
							Inventory inventory = inventoryDao.getBySku(ps.getId());// sku库存
							inventoryList.add(inventory);
						}
					}
					List<ProductShipping> productShipping = productShippingDao.getByProductId(id);// 运费
					List<ProductAttribute> productAttribute = productAttributeDao.getByProductId(id);
					List<ProductDetailList> productDetailList = productDetailListDao.getByProductId(id);
					List<ProductParameterValue> productParameterValue = productParameterValueDao.getByProductId(id);

					apprProduct.setProductSpecificationslist(productSpecifications);// 查询sku
					apprProduct.setProductSpecificationValuelist(productSpecificationValue);// 查询sku值
					apprProduct.setProductSpecificationsImage(psImageList);// sku主图
					apprProduct.setProductInventory(inventoryList);// sku库存
					apprProduct.setProductShipping(productShipping);// 运费
					apprProduct.setProductAttributelist(productAttribute);// 属性
					apprProduct.setProductDetaillist(productDetailList);// 清单
					apprProduct.setProductParameterValuelist(productParameterValue);// 参数

					// 保存到mongodb
					apprProductMongoService.save(apprProduct);
					// 删除参数
					if (productParameterValue != null && !productParameterValue.isEmpty()) {
						for (ProductParameterValue ppv : productParameterValue) {
							productParameterValueDao.deleteById(ppv.getId());
						}
					}
					// 删除清单
					if (productDetailList != null && !productDetailList.isEmpty()) {
						for (ProductDetailList pdl : productDetailList) {
							productDetailListDao.deleteById(pdl.getId());
						}
					}
					// 删除属性
					if (productAttribute != null && !productAttribute.isEmpty()) {
						for (ProductAttribute pa : productAttribute) {
							productAttributeDao.deleteById(pa.getId());
						}
					}
					// 删除运费
					if (productShipping != null && !productShipping.isEmpty()) {
						for (ProductShipping pShipping : productShipping) {
							productShippingDao.deleteById(pShipping.getId());
						}
					}
					// 删除sku库存
					if (inventoryList != null && !inventoryList.isEmpty()) {
						for (Inventory inventory : inventoryList) {
							inventoryDao.deleteById(inventory.getId());
						}
					}
					// 删除sku主图
					if (psImageList != null && !psImageList.isEmpty()) {
						for (ProductSpecificationsImage psI : psImageList) {
							productSpecificationsImageDao.deleteById(psI.getId());
						}
					}
					// 删除sku值
					if (productSpecificationValue != null && !productSpecificationValue.isEmpty()) {
						for (ProductSpecificationValue psv : productSpecificationValue) {
							productSpecificationValueDao.deleteById(psv.getId());
						}
					}
					// 删除sku
					if (productSpecifications != null && !productSpecifications.isEmpty()) {
						for (ProductSpecifications ps : productSpecifications) {
							productSpecificationsDao.deleteById(ps.getId());
						}
					}
					// 删除临时表
					apprProductDao.deleteById(id);
				}
			}
		}
	}
	
	/**
	 * 统计商家历史数据
	 */
 	public void supplierHistoryStatistic(Map<String, Object> map){
		
		List<ApprSupplier> apprSupplierList = apprSupplierDao.findByCreatTime(map);
		if (apprSupplierList!=null && !apprSupplierList.isEmpty()) {
			for (ApprSupplier apprSupplier : apprSupplierList) {
				//先保存再删除
				apprSupplierMongoService.save(apprSupplier);
				apprSupplierDao.deleteById(apprSupplier.getId());
			}
		}
	}
 	
	/**
	 * 统计店铺历史数据
	 */
	public void shopHistoryStatistic(Map<String, Object> map) {
		//获取半年以上的历史数据
		List<ApprShop> apprShopList =  apprShopDao.findByCreatTime(map);
		if (apprShopList!=null && !apprShopList.isEmpty()) {
			for (ApprShop apprShop : apprShopList) {
				Long shopId = apprShop.getShopId();
				Shop shop = shopDao.getById(shopId);
				if (shop!=null && shopId.equals(shop.getId())) {
					//首次添加的时数据先保存再删除
					apprShopMongoService.save(apprShop);
					apprShopDao.deleteById(apprShop.getId());
				}else{
					List<ProductBrand> productBrandList = productBrandDao.findByShopId(shopId);// 品牌
					List<ProductBrandImage> productBrandImageList = null;// 品牌图集合
					List<BrandProducttype> brandProducttypeList = null;// 品牌分类集合
					if (productBrandList != null && !productBrandList.isEmpty()) {
						productBrandImageList = new ArrayList<ProductBrandImage>();
						brandProducttypeList = new ArrayList<BrandProducttype>();
						for (ProductBrand productBrand : productBrandList) {
							List<ProductBrandImage> pbiList = productBrandImageDao.findByBrandId(productBrand.getId());
							productBrandImageList.addAll(pbiList);
							Map map2 = new HashMap<>();
							map2.put("brandId", productBrand.getId());
							map2.put("supplierId", productBrand.getSupplierId());
							List<BrandProducttype> bptList = brandProducttypeDao.findByBrandIdAndSupplierId(map2);
							brandProducttypeList.addAll(bptList);
						}
					}
					List<SupplierCategory> supplierCategoryList = supplierCategoryDao.findByShopId(shopId);// 类型
					List<Attachment> attachmentList = attachmentDao.findByShopId(shopId);// 资质

					apprShop.setProductBrandList(productBrandList);
					apprShop.setProductBrandImageList(productBrandImageList);
					apprShop.setBrandProducttypeList(brandProducttypeList);
					apprShop.setSupplierCategoryList(supplierCategoryList);
					apprShop.setAttachmentList(attachmentList);

					// 保存到mongo
					apprShopMongoService.save(apprShop);

					// 删除资质
					if (attachmentList != null && !attachmentList.isEmpty()) {
						for (Attachment attachment : attachmentList) {
							attachmentDao.deleteById(attachment.getId());
						}
					}
					// 删除品类
					if (supplierCategoryList != null && !supplierCategoryList.isEmpty()) {
						for (SupplierCategory supplierCategory : supplierCategoryList) {
							supplierCategoryDao.deleteById(supplierCategory.getId());
						}
					}
					// 品牌品类
					if (brandProducttypeList != null && !brandProducttypeList.isEmpty()) {
						for (BrandProducttype brandProducttype : brandProducttypeList) {
							brandProducttypeDao.deleteById(brandProducttype.getId());
						}
					}
					// 删除品牌图片集
					if (productBrandImageList != null && !productBrandImageList.isEmpty()) {
						for (ProductBrandImage productBrandImage : productBrandImageList) {
							productBrandImageDao.deleteById(productBrandImage.getId());
						}
					}
					// 删除品牌集
					if (productBrandList != null && !productBrandList.isEmpty()) {
						for (ProductBrand productBrand : productBrandList) {
							productBrandDao.deleteById(productBrand.getId());
						}
					}
					// 删除历史数据
					apprShopDao.deleteById(apprShop.getId());
					}
				}
			}
	}
	
	/**
	 * 结算单历史数据
	 */
	public void saleBillHistoryStatistic(Map<String, Object> map){
		//map.put("creatTime", "2016-04-23 01:30:03");
		// 修改为已执行时间判读，并且不判断执行结果
		List<SupplierCloseCmd> supplierCloseCmdList  = supplierCloseCmdDao.findByCreateTime(map);
		if (supplierCloseCmdList!=null && !supplierCloseCmdList.isEmpty()) {
			for (SupplierCloseCmd supplierCloseCmd : supplierCloseCmdList) {
				//先保存再删除
				supplierCloseCmdMongoService.save(supplierCloseCmd);
				supplierCloseCmdDao.deleteById(supplierCloseCmd.getId());
			}
		}
	}
	/**
	 * 支付帐单历史数据
	 */
	public void payMentHistoryStatistic(Map<String, Object> map){
		//map.put("creatTime", "2016-04-23 01:30:03");
		// 修改为已执行时间判读，并且不判断执行结果
		List<Payment> paymentList = paymentDao.findHistoryData(map);
		if (paymentList!=null && !paymentList.isEmpty()) {
			for (Payment payment : paymentList) {
				//先保存再删除
				paymentMongoService.save(payment);
				paymentDao.deleteById(payment.getOutTradeNo());
			}
		}
	}
}
