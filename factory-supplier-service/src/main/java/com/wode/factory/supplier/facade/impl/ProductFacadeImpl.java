package com.wode.factory.supplier.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
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
import com.wode.factory.supplier.facade.ProductFacade;
import com.wode.factory.supplier.service.ApprProductService;
import com.wode.factory.supplier.service.InventoryService;
import com.wode.factory.supplier.service.ProductAttributeService;
import com.wode.factory.supplier.service.ProductDetailListService;
import com.wode.factory.supplier.service.ProductLadderService;
import com.wode.factory.supplier.service.ProductParameterValueService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ProductShippingService;
import com.wode.factory.supplier.service.ProductSpecificationValueService;
import com.wode.factory.supplier.service.ProductSpecificationsImageService;
import com.wode.factory.supplier.service.ProductSpecificationsService;

import cn.org.rapid_framework.beanutils.BeanUtils;

@Service
public class ProductFacadeImpl implements ProductFacade {

	@Resource
	private ApprProductService apprProductService;
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	@Autowired
	@Qualifier("productSpecificationsService")
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	@Qualifier("productSpecificationValueService")
	private ProductSpecificationValueService productSpecificationValueService;
	@Autowired
	private ProductShippingService productShippingService;
	@Autowired
	@Qualifier("productParameterValueService")
	private ProductParameterValueService productParameterValueService;
	@Autowired
	@Qualifier("productAttributeService")
	private ProductAttributeService productAttributeService;
	@Autowired
	@Qualifier("productDetailListService")
	private ProductDetailListService productDetailListService;
	@Autowired
	@Qualifier("inventoryService")
	private InventoryService inventoryService;
	@Autowired
	@Qualifier("productSpecificationsImageService")
	private ProductSpecificationsImageService productSpecificationsImageService;
	@Autowired
	@Qualifier("productLadderService")
	private ProductLadderService productLadderService;
	/*
	 * 这里要操作sku弹出修改价格的时候，修改完价格后要把正式表的数据更新到临时表中，还有关联的商品规格表，属性等操作要放在一个事务中
	 */
	@Override
	@Transactional
	public ApprProduct productToapprProduct(Long productid,Integer status,Map<Long,ProductSpecifications> skuMap,Map<Long,Inventory> stockMap) {
		// 根据正式表的id获取临时表对象
		// 临时表的对象可以根据状态来找出
		Product product = productService.getById(productid);
		ApprProduct appr = null;
		// 下架操作时，判断如果在编辑状态有该商品的话，就不需要复制下面的sku等关联表
		appr = apprProductService.selectProductIdAndStatus(productid);
		if(appr==null){
			appr =new ApprProduct();
		
			BeanUtils.copyProperties(appr, product);
			// 有上面的一行代码就不需要insertApprProduct了，正式表的数据要copy到临时表中
			// productService.insertApprProduct(product);
			appr.setId(null);
			appr.setProductId(product.getId());
			// 还要把appr的状态设置为1，需要审核
			appr.setStatus(status);
			// 手动下架
			if(status == 0) {
				appr.setIsMarketable(-2);
			}
			if(appr.getPromotion() ==null){
				appr.setPromotion("");
			}
			if(appr.getMarque()==null){
			    appr.setMarque("");
			}
			if(appr.getBarcode()==null){
				appr.setBarcode("");
			}
			if(appr.getSavestate() ==null){//正式表的数据copy到临时表中，肯定是信息完整的商品信息
				appr.setSavestate(0);
			}
			appr.setLocked(0);		// 解锁
			apprProductService.saveOrUpdate(appr); // 更新appr的id到数据库中
	
			// copy规格值，然后把临时表的id更新到product_id
			Map<String,String> mapPsv = new HashMap<String,String>();
			List<ProductSpecificationValue> psv = productSpecificationValueService.getByProductId(product.getId());
			if (psv != null && psv.size() > 0) {
				for (ProductSpecificationValue pv : psv) {
					ProductSpecificationValue pf = new ProductSpecificationValue();
					BeanUtils.copyProperties(pf, pv);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					productSpecificationValueService.saveOrUpdate(pf);
					
					mapPsv.put(""+pv.getId(), ""+pf.getId());
				}
			}
			
			// copy sku，然后把临时表的id更新到product_id
			List<ProductSpecifications> psku = productSpecificationsService.getlistByProductid(product.getId());
			if (psku != null && psku.size() > 0) {
				for (ProductSpecifications ps : psku) {
					ProductSpecifications p = new ProductSpecifications();
					BeanUtils.copyProperties(p, ps);
					p.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					p.setProductId(appr.getId());
					String oldItemids=ps.getItemids();
					String newItemids="";
					String[] ary = oldItemids.split(",");
					for (int i = 0; i < ary.length; i++) {
						newItemids += mapPsv.get(ary[i])+",";
					}
					if(newItemids.length()>0) {
						newItemids=newItemids.substring(0, newItemids.length()-1); //去掉末尾的逗号
					}
					p.setItemids(newItemids);
					productSpecificationsService.saveOrUpdate(p);
					//根据sku的id copy库存
					Map map = new HashMap();
					map.put("productSpecificationsId", ps.getId());
					List<Inventory> inventorylist = inventoryService.findAllBymap(map);
					//List<Inventory> inventorylist = inventoryService.getByProductId(p.getId());
					Inventory inventory = null;
					if (inventorylist != null && inventorylist.size() > 0) {
						inventory = inventorylist.get(0);
					}
					if (inventory == null) {
						inventory = new Inventory();
					}
					Inventory in=new Inventory();
					BeanUtils.copyProperties(in, inventory);
					in.setId(null);
					in.setProductSpecificationsId(p.getId());
					inventoryService.saveOrUpdate(in);
					 //把老的库存数据的id，映射到新生成的库存对象，这样可以新旧关联
					stockMap.put(inventory.getId(), in);
					//把老的sku数据的id，映射到新生成的sku对象
					skuMap.put(ps.getId(), p);
					////根据sku的id copy图片
					List<ProductSpecificationsImage> psi = productSpecificationsImageService.getByProductId(ps.getId());
					if (psi != null && psi.size() > 0) {
						for(ProductSpecificationsImage image : psi){
							ProductSpecificationsImage psfi=new ProductSpecificationsImage();
							BeanUtils.copyProperties(psfi, image);
							psfi.setId(null);
							psfi.setSpecificationsId(p.getId());
							productSpecificationsImageService.save(psfi);
						}
					}
				}
			}
			
			// copy参数值
			List<ProductParameterValue> psfv = productParameterValueService.getByProductId(product.getId());
			if (psfv != null && psfv.size() > 0) {
				for (ProductParameterValue pvf : psfv) {
					ProductParameterValue pf = new ProductParameterValue();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					productParameterValueService.saveOrUpdate(pf);
				}
			}
			// copy运费模板值
			List<ProductShipping> psp = productShippingService.getByProductId(product.getId());
			if (psp != null && psp.size() > 0) {
				for (ProductShipping pvf : psp) {
					ProductShipping pf = new ProductShipping();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					productShippingService.saveOrUpdate(pf);
				}
			}
			// copy清单
			List<ProductDetailList> pdl = productDetailListService.getByProductId(product.getId());
			if (pdl != null && pdl.size() > 0) {
				for (ProductDetailList pvf : pdl) {
					ProductDetailList pf = new ProductDetailList();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					productDetailListService.saveOrUpdate(pf);
				}
			}
			// copy属性
			List<ProductAttribute> pat = productAttributeService.getByProductId(product.getId());
			if (pat != null && pat.size() > 0) {
				for (ProductAttribute pvf : pat) {
					ProductAttribute pf = new ProductAttribute();
					BeanUtils.copyProperties(pf, pvf);
					pf.setId(null);
					// 把临时表中的product_id设置成临时表的id，然后保存，就不需要上面的
					pf.setProductId(appr.getId());
					productAttributeService.saveOrUpdate(pf);
				}
			}
			
			// copy 阶梯价格
			List<ProductLadder> ladders=  productLadderService.getlistByProductid(product.getId());
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
					productLadderService.saveOrUpdate(pln);
				}
			}
		}

		return appr;
	}

}
