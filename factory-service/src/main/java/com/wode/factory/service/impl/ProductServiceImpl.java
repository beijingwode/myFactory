package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.QueryCached;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Attribute;
import com.wode.factory.model.ParameterGroup;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.model.Specification;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.CategoryAttributeService;
import com.wode.factory.service.ProductService;
import com.wode.factory.vo.ProductAttributeVo;
import com.wode.factory.vo.ProductParameterVo;
import com.wode.factory.vo.ProductSpecificationsImgVo;
import com.wode.factory.vo.ProductVo;
import com.wode.factory.vo.SupplierShopVo;

@Service("productService")
public class ProductServiceImpl implements ProductService {


	@Autowired
	private Dao dao;
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private CategoryAttributeService attrService;

	private static Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@QueryCached
	public List<ProductAttributeVo> findAttr(Long productId, Long categoryId,boolean withOutEmptyAttr) {
		
		List<ProductAttribute> pAttr=dao.query(ProductAttribute.class, Cnd.where("productId", "=", productId));
		
		List<Attribute> allAttr=attrService.findByCategory(categoryId);
		
		List<ProductAttributeVo> listAttr = new ArrayList<ProductAttributeVo>();
		
		for(Attribute attr:allAttr){
			ProductAttributeVo vo=new ProductAttributeVo();
			vo.setName(attr.getName());
			vo.setIsmust(attr.getIsmust());
			for(ProductAttribute pa:pAttr){
				if(pa.getAttributeId().equals(attr.getId())){
					vo.setValue(pa.getValue());
					break;
				}
			}
			boolean flag=!withOutEmptyAttr;
			
			if(withOutEmptyAttr&&!StringUtils.isEmpty(vo.getValue())){
				flag=true;
			}
			
			if(flag){
				listAttr.add(vo);
			}
			
			
		}
		
		
		return listAttr;
		
	}
	
	
	public List<ProductParameterVo> findPar(Long id, Long categoryId,boolean withOutEmptyAttr) {
		
		List<ProductParameterValue> proParList=dao.query(ProductParameterValue.class, Cnd.where("productId", "=", id));
		
		List<ParameterGroup> parGroupList=dao.query(ParameterGroup.class, Cnd.where("categoryId", "=", categoryId).asc("orders"));
		
		List<ProductParameterVo> listProParVo = new ArrayList<ProductParameterVo>();
		
		for(ParameterGroup attr:parGroupList){
			ProductParameterVo vo=new ProductParameterVo();
			vo.setName(attr.getName());
			//vo.setIsmust(attr.getIsmust());
			for(ProductParameterValue pa:proParList){
				if(pa.getParameterGroupId().equals(attr.getId())){
					vo.setValue(pa.getParameterValue());
					break;
				}
			}
			boolean flag=!withOutEmptyAttr;
			
			if(withOutEmptyAttr&&!StringUtils.isEmpty(vo.getValue())){
				flag=true;
			}
			
			if(flag){
				listProParVo.add(vo);
			}
			
		}
		return listProParVo;
	}
	
	
	public ProductVo findById(Long id,boolean withOutEmptyAttr) {
		ProductVo pdv =null;
		
		String json=redisUtil.getMapData(RedisConstant.PRODUCT_PRE+id, RedisConstant.PRODUCT_REDIS_INFO);
		
		if(json!=null){
			pdv=JsonUtil.getObject(json, ProductVo.class);
		} 
		if(pdv==null){

			logger.warn("缓存数据异常，缓存中无法获取商品base信息，商品ID:"+id);
			
			pdv = dao.fetch(ProductVo.class, id);
			dao.fetchLinks(pdv, "productAttributelist|productParameterValuelist|category");
		}
		
		return pdv;
	}
	
	
	public List<ProductSpecifications>  findSku(Long productId) {
		List<ProductSpecifications> ret=new ArrayList();
		 String skuJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+productId, RedisConstant.PRODUCT_REDIS_SKU);
		 if(!StringUtils.isEmpty(skuJsonStr)){
			 ret=JsonUtil.getList4Json(skuJsonStr, ProductSpecifications.class);
		 }else{
			//查询商品规格
			Criteria criProSpe = Cnd.cri();
			criProSpe.where().and("productId", "=", productId);
			ret = dao.query(ProductSpecifications.class, criProSpe);
			if(ret!=null&&ret.size()>0){
				for(ProductSpecifications p:ret){
					redisUtil.setMapData(RedisConstant.PRODUCT_SPE_ID_FOR_PRO_ID, p.getId()+"", productId+"");
				}
				redisUtil.setMapData(RedisConstant.PRODUCT_PRE+productId, RedisConstant.PRODUCT_REDIS_SKU, JsonUtil.toJsonString(ret));
				
			}
			 
		 }
		 
		return ret;
	}

	@QueryCached(keyPreFix="product_Allsku")
	public Map<String, List<ProductSpecificationValue>> findSku4ShowCache(Long id) {
		return findSku4Show(id);
	}
	public Map<String, List<ProductSpecificationValue>> findSku4Show(Long id) {
		
		Sql sql = Sqls.create("select distinct t.* from t_specification  t inner join t_product_specification_value p on t.id = p.specification_id where p.product_id = @pid and p.isDelete = 0 ORDER BY t.orders asc");
		sql.params().set("pid",id);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Specification.class));
		dao.execute(sql);
		List<Specification> sList = sql.getList(Specification.class);
		
		if(sList==null || sList.isEmpty()) {
			sql = Sqls.create("select distinct t.* from t_supplier_specification t inner join t_product_specification_value p on t.id = p.specification_id where p.product_id = @pid and p.isDelete = 0 ORDER BY t.orders asc");
			sql.params().set("pid",id);
			sql.setCallback(Sqls.callback.entities());
			sql.setEntity(dao.getEntity(Specification.class));
			dao.execute(sql);
			sList = sql.getList(Specification.class);
		}
			
		Map<String,List<ProductSpecificationValue>> map = new LinkedHashMap<String, List<ProductSpecificationValue>>();
		List<ProductSpecificationValue> psvList;
		for(Specification s:sList){
			Sql sqlPsv = Sqls.create("select p.*,sv.image from t_product_specification_value p inner join t_specification_value sv on p.specification_id = sv.specification_id and p.specification_value=sv.name  where p.product_id = @id and p.specification_id=@sid and p.isDelete = 0 ORDER BY p.orders asc");
			sqlPsv.params().set("id",id);
			sqlPsv.params().set("sid",s.getId());
			sqlPsv.setCallback(Sqls.callback.entities());
			sqlPsv.setEntity(dao.getEntity(ProductSpecificationValue.class));
			dao.execute(sqlPsv);
			psvList = sqlPsv.getList(ProductSpecificationValue.class);
			if(map.get(s.getName())==null){
				map.put(s.getName(), psvList);
			}else{
				List<ProductSpecificationValue> allList=map.get(s.getName());
				for(ProductSpecificationValue psv:psvList){
					allList.add(psv);
				}
			}
		}
		
		return map;
	}

	public SupplierShopVo findShopByProductId(Long id) {
		Sql supplierSql = Sqls.create("select a.com_name as companyName,a.com_state as companyState,"
				+ "a.com_city as companyCity,a.com_address as companyAddress,t.shop_id as shopId,b.* "
				+ "from t_supplier a INNER JOIN t_product t on t.supplier_id = a.id left join t_shop b on t.shop_id = b.id "
				+ "where t.id=@id");
		
		supplierSql.params().set("id",id);
		supplierSql.setCallback(Sqls.callback.entities());
		supplierSql.setEntity(dao.getEntity(SupplierShopVo.class));
		dao.execute(supplierSql);
		SupplierShopVo supplierShopVo = supplierSql.getObject(SupplierShopVo.class);
		
		return supplierShopVo;
	}
	
	/**
	 *根据产品Id查询该产品下所有sku图片相关信息 
	 */
	@Override
	public Map<String, List<ProductSpecificationsImgVo>> findSkuImg(Long productId) {
		
		Map<String, List<ProductSpecificationsImgVo>> map = new HashMap<String, List<ProductSpecificationsImgVo>>();
		List<ProductSpecificationsImgVo> psiList = null;
		
		Sql imgSql = Sqls.create("select a.id as productSpecificationsId,b.id as imageId,a.itemids as itemIds,b.source as imgUrl,a.product_id as productId from "
				+ "t_product_specifications a right join t_product_specifications_image b on a.id = b.specifications_id "
				+ "where a.product_id=@id and a.isDelete<>1 ORDER BY b.orders asc");
		imgSql.params().set("id",productId);
		imgSql.setCallback(Sqls.callback.entities());
		imgSql.setEntity(dao.getEntity(ProductSpecificationsImgVo.class));
		dao.execute(imgSql);
		psiList = imgSql.getList(ProductSpecificationsImgVo.class);
		for(ProductSpecificationsImgVo p:psiList){
			if(map.get(p.getItemIds())==null){
				List<ProductSpecificationsImgVo> list = new ArrayList<ProductSpecificationsImgVo>();
				p.setrItemIds(p.getItemIds().replace(",", "_"));
				list.add(p);
				map.put(p.getItemIds(), list);
			}else{
				List<ProductSpecificationsImgVo> allList=map.get(p.getItemIds());
				p.setrItemIds(p.getItemIds().replace(",", "_"));
				allList.add(p);
			}
		}
		return map;
	}
	
	/**
	 *根据产品skuId查询该sku图片相关信息 
	 */
	@Override
	public List<ProductSpecificationsImgVo> findSkuImgBySkuId(String skuId) {
		
		List<ProductSpecificationsImgVo> psiList = null;
		
		Sql imgSql = Sqls.create("select a.id as productSpecificationsId,b.id as imageId,a.itemids as itemIds,b.source as imgUrl,a.product_id as productId from "
				+ "t_product_specifications a right join t_product_specifications_image b on a.id = b.specifications_id "
				+ "where a.itemids=@id and a.isDelete<>1 ORDER BY b.orders asc");
		imgSql.params().set("id",skuId);
		imgSql.setCallback(Sqls.callback.entities());
		imgSql.setEntity(dao.getEntity(ProductSpecificationsImgVo.class));
		dao.execute(imgSql);
		psiList = imgSql.getList(ProductSpecificationsImgVo.class);
		return psiList;
	}
	
	
	private List<Product> findRecommendByCate(Long cateid) {
		 String json=redisUtil.getMapData("RECOMMEND_CATEGORY_PRODUCT",cateid+"");
		 List<Product> ret=new ArrayList();
		 if(json!=null){
			 List<String> ids=JsonUtil.getList(json, String.class);
			 String[] arry=new String[ids.size()];
			 for(int i=0;i<ids.size();i++){
				 arry[i]=RedisConstant.PRODUCT_PRE+ids.get(i);
			 }
			 List<String> ps=redisUtil.getMapDatas(arry,RedisConstant.PRODUCT_REDIS_INFO);
			 for(String p:ps){
				 
				 ret.add(JsonUtil.getObject(p, Product.class));
			 }
		 }
		return ret;
	}
	
	
	@Override
	public Product findBySku(Long skuId) {
		Product ret=null;
		String productId = redisUtil.getMapData(RedisConstant.PRODUCT_SPE_ID_FOR_PRO_ID, skuId+"");
		if(productId==null){
			logger.warn("缓存数据异常,无法通过skuid获取商品信息， sku ID:"+skuId);
			
			ProductSpecifications sku=dao.fetch(ProductSpecifications.class, skuId);
			if(sku!=null&&sku.getIsDelete()==0){
				redisUtil.setMapData(RedisConstant.PRODUCT_SPE_ID_FOR_PRO_ID, skuId+"",sku.getProductId()+"");
				productId=sku.getProductId()+"";
			}			
		}
		
		if(productId!=null){
			ret=this.findById(Long.valueOf(productId), true);
		}
		
		return ret;
	}

	
	public Supplier findSupplier(Long productId) {
		 String json=redisUtil.getMapData(RedisConstant.PRODUCT_PRE+productId,RedisConstant.PRODUCT_REDIS_SUPPLIER);
		 if(json!=null){
			 return JsonUtil.getObject(json, Supplier.class);
		 }
		 
		return null;
	}

	/**
	 * 根据商品表最低价格与商品ID查询相关最低价格的SKU的LIST
	 */
	@Override
	public List<ProductSpecifications> findByMinprice(Long productId,BigDecimal minprice) {
		List<ProductSpecifications> psList=dao.query(ProductSpecifications.class,Cnd.where("productId", "=", productId).and("isDelete", "=", 0));
		List<ProductSpecifications> rpsList = new ArrayList<ProductSpecifications>();
		
		for (ProductSpecifications productSpecifications : psList) {
			if(productSpecifications.getPrice().compareTo(minprice)<=0) {
				rpsList.add(productSpecifications);
			}
		}
		
		return rpsList.isEmpty()?psList:rpsList;
	}


	@Override
	public Specification findByItemsValue(String skuValueid) {
		ProductSpecificationValue psv=dao.fetch(ProductSpecificationValue.class,Cnd.where("id", "=", skuValueid));
		Specification s = dao.fetch(Specification.class,Cnd.where("id", "=", psv.getSpecificationId()));
		if(s == null) {
			SupplierSpecification ss = dao.fetch(SupplierSpecification.class,Cnd.where("id", "=", psv.getSpecificationId()));
			if(ss != null) {
				s =new Specification();
				s.setCategoryId(ss.getCategoryId());
				s.setId(ss.getId());
				s.setName(ss.getName());
				s.setOrders(ss.getOrders());
				s.setType(ss.getType());
			}
		}
		return s;
	}


	@Override
	public List<ProductSpecificationValue> findSKUValue(Long productId, Long id) {
		List<ProductSpecificationValue> psvList = dao.query(ProductSpecificationValue.class, Cnd.where("productId", "=", productId).and("specificationId", "=", id).and("isDelete", "=", 0).orderBy("orders", "desc"));
		return psvList;
	}


	@Override
	public SupplierShopVo findShopByProductIdCache(Long id) {
		return findShopByProductId(id);
	}


	@Override
	public List<ProductSpecifications> findByMinpriceCache(Long productId, BigDecimal minprice) {
		return findByMinprice(productId,minprice);
	}

	@Override
	public Specification findByItemsValueCache(String skuValueId) {
		return findByItemsValue(skuValueId);
	}


	@Override
	public boolean checkProductLimit(Product product, UserFactory user) {
		if(null != product.getLimitKbn() && product.getLimitKbn().intValue() == 3 && null != user.getType() && user.getType().intValue() != 2 && user.getType().intValue() != 3){
			return true;
		}
		return false;
	}
	
	@Override
	public List<ProductSpecifications> findByProductIdAndSkuId(Long productId, Long id) {
		List<ProductSpecifications> psList=dao.query(ProductSpecifications.class,Cnd.where("productId", "=", productId).and("isDelete", "=", 0).and("id","=",id));
		return psList;
	}



	@Override
	public boolean queryIsMarketableBySupplier(Long supplier) {
		int i = dao.count(Product.class, Cnd.where("is_marketable", "=", "1").and("supplier_id","=",supplier));
		if(i > 0) {
			return true;
		}
		return false;
	}


	@Override
	public List<ProductVo> findProductByShopId(Long shopId) {
		return dao.query(ProductVo.class,Cnd.where("shop_id", "=", shopId).and("sale_kbn","=",0).or("sale_kbn", "=", 3));
	}
	
}
