package com.wode.factory.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.facade.ProductFacadeOff;
import com.wode.factory.mapper.ApprProductDao;
import com.wode.factory.mapper.InventoryDao;
import com.wode.factory.mapper.ProductAttributeMapper;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.ProductDetailListMapper;
import com.wode.factory.mapper.ProductParameterValueMapper;
import com.wode.factory.mapper.ProductShippingMapper;
import com.wode.factory.mapper.ProductSpecificationValueMapper;
import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.mapper.ProductSpecificationsImageDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;

import cn.org.rapid_framework.beanutils.BeanUtils;

@Service
public class ProductFacadeOffImpl implements ProductFacadeOff {

	@Resource
	private ApprProductDao apprProductdao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductSpecificationsDao productSpecificationsDao;
	@Autowired
	private ProductSpecificationValueMapper productSpecificationValueDao;
	@Autowired
	private ProductShippingMapper productShippingDao;
	@Autowired
	private ProductParameterValueMapper productParameterValueDao;
	@Autowired
	private ProductAttributeMapper productAttributeDao;
	@Autowired
	private ProductDetailListMapper productDetailListDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private ProductSpecificationsImageDao productSpecificationsImageDao;
	@Autowired
	private DBUtils dbUtils;
	@Resource
	private ProductService productService;
	@Resource
	private ProductLadderService productLadderService;
	
	@Override
	public Integer forceselloff(Product product) {
		//如果勾选强制下架，做处理
		Integer ret = productDao.selloffByid(product);
		//这里把正式表的数据复制到临时表，并且关联的sku，规格参数，产品属性等等都重新生成一个新的版本
		Long productId=product.getId();
		this.productToapprProduct(productId); 

		productService.destroy(productId);
		return ret;

	}
	
	/*
	 * 这里要操作正式表商品下架后要把他放入临时表的待售状态中，修改完价格后要把正式表的数据更新到临时表中，还有商品的规格，属性等操作要放在一个事务中
	 */
	@Override
	@Transactional("tm_factory")
	public void productToapprProduct(Long productId) {
		// 根据正式表的id获取临时表对象
		// 临时表的对象可以根据状态来找出
		Product product = productDao.getById(productId);
		ApprProduct appr = null;
		// 强制下架操作时，判断如果在编辑状态有该商品的话，就不需要复制下面的sku等关联表
		appr = apprProductdao.selectProductIdAndStatus(productId);
		if (appr == null) {
			appr = new ApprProduct();
			BeanUtils.copyProperties(appr, product);
			appr.setId(null);
			appr.setProductId(product.getId());
			appr.setStatus(-1); // 后端审核的是强制下架操作，正式表ismaketable是-2，临时表的状态为-1及问题商品
			
			if (appr.getPromotion() == null) {
				appr.setPromotion("");
			}
			if (appr.getMarque() == null) {
				appr.setMarque("");
			}
			if (appr.getBarcode() == null) {
				appr.setBarcode("");
			}
			
			appr.setId(dbUtils.CreateID());
			apprProductdao.insert(appr); // 更新appr,产生一个新的版本
			
			// copy规格值，然后把临时表的id更新到product_id
			Map<String, String> mapPsv = new HashMap<String, String>();
			List<ProductSpecificationValue> psv = productSpecificationValueDao.getByProductId(product.getId());
			if (psv != null && psv.size() > 0) {
				for (ProductSpecificationValue pv : psv) {
					ProductSpecificationValue pf = new ProductSpecificationValue();
					BeanUtils.copyProperties(pf, pv);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					if (pf.getId() == null) {
						pf.setId(dbUtils.CreateID());
						productSpecificationValueDao.insert(pf);
					}
					mapPsv.put("" + pv.getId(), "" + pf.getId());
				}
			}

			// copy sku，然后把临时表的id更新到product_id
			Map<Long,ProductSpecifications> skuMap = new HashMap<Long,ProductSpecifications>();		//旧skuId 对应新sku
			List<ProductSpecifications> psku = productSpecificationsDao.getlistByProductid(product.getId());
			if (psku != null && psku.size() > 0) {
				for (ProductSpecifications ps : psku) {
					ProductSpecifications p = new ProductSpecifications();
					BeanUtils.copyProperties(p, ps);
					p.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					p.setProductId(appr.getId());
					String oldItemids = ps.getItemids();
					String newItemids = "";
					String[] ary = oldItemids.split(",");
					for (int i = 0; i < ary.length; i++) {
						newItemids += mapPsv.get(ary[i]) + ",";
					}
					if (newItemids.length() > 0) {
						newItemids = newItemids.substring(0, newItemids.length() - 1); // 去掉末尾的逗号
					}
					p.setItemids(newItemids);
					if (p.getId() == null) {
						p.setId(dbUtils.CreateID());
						productSpecificationsDao.insert(p);
					}
					skuMap.put(ps.getId(), p);
					
					// 根据sku的id copy库存
					Map map = new HashMap();
					map.put("productSpecificationsId", ps.getId());
					List<Inventory> inventorylist = inventoryDao.findAllBymap(map);
					// List<Inventory> inventorylist =
					// inventoryService.getByProductId(p.getId());
					Inventory inventory = null;
					if (inventorylist != null && inventorylist.size() > 0) {
						inventory = inventorylist.get(0);
					}
					if (inventory == null) {
						inventory = new Inventory();
					}
					Inventory in = new Inventory();
					BeanUtils.copyProperties(in, inventory);
					in.setId(null);
					in.setProductSpecificationsId(p.getId());
					if (in.getId() == null) {
						in.setId(dbUtils.CreateID());
						inventoryDao.insert(in);
					}
					List<ProductSpecificationsImage> psi = productSpecificationsImageDao.getByProductId(ps.getId());
					if (psi != null && psi.size() > 0) {
						for (ProductSpecificationsImage image : psi) {
							ProductSpecificationsImage psfi = new ProductSpecificationsImage();
							BeanUtils.copyProperties(psfi, image);
							psfi.setId(null);
							psfi.setSpecificationsId(p.getId());
							if (psfi.getId() == null) {
								psfi.setId(dbUtils.CreateID());
								productSpecificationsImageDao.insert(psfi);
							}
						}
					}

				}
			}

			// copy参数值
			List<ProductParameterValue> psfv = productParameterValueDao.getByProductId(product.getId());
			if (psfv != null && psfv.size() > 0) {
				for (ProductParameterValue pvf : psfv) {
					ProductParameterValue pf = new ProductParameterValue();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					if (pf.getId() == null) {
						pf.setId(dbUtils.CreateID());
						productParameterValueDao.insert(pf);
					}
				}
			}
			// copy运费模板值
			List<ProductShipping> psp = productShippingDao.getByProductId(product.getId());
			if (psp != null && psp.size() > 0) {
				for (ProductShipping pvf : psp) {
					ProductShipping pf = new ProductShipping();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					if (pf.getId() == null) {
						pf.setId(dbUtils.CreateID());
						productShippingDao.insert(pf);
					}
				}
			}
			// copy清单
			List<ProductDetailList> pdl = productDetailListDao.getByProductId(product.getId());
			if (pdl != null && pdl.size() > 0) {
				for (ProductDetailList pvf : pdl) {
					ProductDetailList pf = new ProductDetailList();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					if (pf.getId() == null) {
						pf.setId(dbUtils.CreateID());
						productDetailListDao.insert(pf);
					}
				}
			}
			// copy属性
			List<ProductAttribute> pat = productAttributeDao.getByProductId(product.getId());
			if (pat != null && pat.size() > 0) {
				for (ProductAttribute pvf : pat) {
					ProductAttribute pf = new ProductAttribute();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					if (pf.getId() == null) {
						pf.setId(dbUtils.CreateID());
						productAttributeDao.insert(pf);
					}
				}
			}
			
			// copy 阶梯价格
			List<ProductLadder> ladders=  productLadderService.getByProductId(product.getId());
			if (ladders != null && ladders.size() > 0) {
				for (ProductLadder plo : ladders) {
					ProductLadder pln = new ProductLadder();
					BeanUtils.copyProperties(pln, plo);
					pln.setId(null);
					String skuIds = "";
					if(!StringUtils.isEmpty(pln.getSkuids())) {
						String ary[] = pln.getSkuids().split(",");
						for (String string : ary) {
							ProductSpecifications sku = skuMap.get(NumberUtil.toLong(string));
							if(sku!=null) {
								skuIds += sku.getId()+","; 
							}
						}
					}
					pln.setSkuids(skuIds);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pln.setProductId(appr.getId());
					productLadderService.save(pln);
				}
			}

		}

	}
	
}
	
