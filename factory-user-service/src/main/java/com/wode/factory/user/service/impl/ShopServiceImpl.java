package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.FLJModel;
import com.wode.factory.model.Shop;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.ShopSettingDao;
import com.wode.factory.user.dao.StoreCategoryDao;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.query.ProductQuery;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.vo.SupplierShopVo;

/**
 * Created by Bing King on 2015/3/9.
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopSettingDao shopSettingDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private StoreCategoryDao storeCategoryDao;
	
	@Autowired
	private SupplierDao supplierDao;
	
	@Override
	public List<StoreCategory> getStoreCategories(Long supplierId) {
		return storeCategoryDao.getStoreCategoriesBySupplierId(supplierId);
	}

	@Override
	public PageInfo getProductsByStoreCategory(Long storeCategoryId, Integer page) {
		ProductQuery query = new ProductQuery();
		query.setPageSize(20);
		query.setId(storeCategoryId);
		query.setPageNumber(page);
		query.setIsMarketable(1);
		query.setStatus(2);
		return storeCategoryDao.getProductsByStoreCategory(query);
	}

	@Override
	public PageInfo getProductsByShop(Long supplierId, Long shopId,Integer page) {
		ProductQuery query = new ProductQuery();
		query.setPageSize(20);
		query.setSupplierId(supplierId);
		query.setShopId(shopId);
		query.setPageNumber(page);
		query.setIsMarketable(1);
		query.setStatus(2);
		query.setSortColumns("sortNum asc");
		return productDao.findProducts(query);
	}

	@Override
	public Shop getShopSettingById(Long shopId) {
		return shopSettingDao.getById(shopId);
	}

	@Override
	public List<FLJModel> findAllShop() {
		return shopSettingDao.findAllShop();
	}

	@Override
	public int findProductCount(Long supplierId,Long shopId) {
		return shopSettingDao.findcount(supplierId,shopId);
	}

	@Override
	public List<Shop> getShopBySupplierId(Long supplierId) {
		return shopSettingDao.getBySupplierId(supplierId);
	}

	@Override
	public SupplierShopVo findShopByShopIdCache(Long shopId) {
		SupplierShopVo supplierShopVo = new SupplierShopVo();
		Shop shop = this.getShopSettingById(shopId);
		if(shop!=null){
			BeanUtils.copyProperties(shop, supplierShopVo);
			Supplier supplier = supplierDao.getById(shop.getSupplierId());
			if(supplier!=null){
				supplierShopVo.setCompanyState(supplier.getComState());// 公司状态
				supplierShopVo.setCompanyName(supplier.getComName());// 公司名称
				supplierShopVo.setCompanyAddress(supplier.getComAddress());// 公司地址
				supplierShopVo.setCompanyCity(supplier.getComCity());
			}
		}
		return supplierShopVo;
	}
}
