package com.wode.factory.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.vo.SupplierShopVo;



/**
 * 
 * <pre>
 * 功能说明: 商品分类
 * 日期:	2015年1月21日
 * 开发者:	宋艳垒，谢海生
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年2月7日
 * </pre>
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService{
	
	
	private RedisUtil redisUtil;
	
	private Dao dao;
	
	private static Logger logger= LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	
	@Autowired
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	
	private List<ProductCategory> findSubDB(Long pid) {
		
		return dao.query(ProductCategory.class, Cnd.where("pid", "=", pid).orderBy("orders", "asc"));
	}

	
	private List<ProductCategory> findRootDB() {
		
		return dao.query(ProductCategory.class, Cnd.where("deep", "=", 1).orderBy("orders", "asc"));
	}
	
	
	
	@Override
	public List<ProductCategory> findSub(ProductCategory pojo) {
		
		if(pojo!=null&&pojo.getId()!=null){
			if(pojo.getBrotherOrderAll()==null){
				pojo=this.findById(pojo.getId());
			}
			String key = pojo.getRootId()+pojo.getBrotherOrderAll();
			String json = redisUtil.getMapData("PRODUCT_CATEGORY_CHILD",key);
			if(!StringUtils.isEmpty(json)){
				return JsonUtil.getList(json, ProductCategory.class);
			}else{
				logger.warn("缓存分类"+pojo.getId()+"的子分类数据失效");
				return findSubDB(pojo.getId());
			}
		}
		return null;
	}
	
	
	public List<ProductCategory> findRoot() {
		String json = redisUtil.getData("PRODUCT_CATEGORY_ROOT");
		if(!StringUtils.isEmpty(json)){
			return JsonUtil.getList(json, ProductCategory.class);
		}else{
			logger.warn("缓存根分类数据失效");
			return findRootDB();
		}
		
	}
	
	
	public ProductCategory findById(Long id) {
		String json = redisUtil.getMapData("PRODUCT_CATEGORY", id+"");
		if(!StringUtils.isEmpty(json)){
			return JsonUtil.getObject(json, ProductCategory.class);
		}else{
			logger.warn("缓存分类"+id+"数据失效");
			return dao.fetch(ProductCategory.class, id);
		}
	}
	
	
	public List<ProductCategory> findIds(List<Long> ids) {
		List<ProductCategory> ret=new ArrayList<ProductCategory>();
		if(ids!=null){
			String[] arr=new String[ids.size()];
			for(int i=0;i<arr.length;i++){
				arr[i]=ids.get(i)+"";
			}
			List<String> jsons=redisUtil.getMapData("PRODUCT_CATEGORY", arr);
			for(String str:jsons){
				if(!StringUtils.isEmpty(str)){
					ret.add(JsonUtil.getObject(str, ProductCategory.class));
				}
			}
		}
		return ret;
	}
	
	
	public Map<Long, List<ProductCategory>> findParents(List<Long> ids) {
		if(StringUtils.isEmpty(ids) || ids.size()<1){
			return null;
		}
		Map<Long, List<ProductCategory>> map = new HashMap<Long, List<ProductCategory>>();
		for(Long cateid:ids){
			 map.put(cateid,  findParents(cateid));
		}
		return map;
	}
	
	
	
	public Collection<ProductCategory> findParentsByGroup(List<Long> ids) {
		if(StringUtils.isEmpty(ids) || ids.size()<1){
			return null;
		}
		
		Map<Long, ProductCategory> root = new HashMap<Long, ProductCategory>();
		
		List<ProductCategory> list = findIds(ids);
		for(ProductCategory cate:list){
			List<String> li=new ArrayList<String>();
			String flag=cate.getBrotherOrderAll();
			int last=flag.lastIndexOf("_");
			while(last>0){
				flag=flag.substring(0, last);
				li.add(flag);
				last=flag.lastIndexOf("_");
			}
			//查父节点
			List<ProductCategory> parents=dao.query(ProductCategory.class, Cnd.where("rootId", "=", cate.getRootId()).and("brotherOrderAll", "in", li).asc("deep"));
			if(parents==null||parents.size()<1){
				continue;
			}
			//分组(从根节点开始向下添加子节点)
			ProductCategory p=parents.get(0);
			if(!root.containsKey(p.getId())){
				root.put(p.getId(), p);
			}else{
				p=root.get(p.getId());
			}
			
			for(int i=1;i<parents.size();i++){
				ProductCategory cate1=parents.get(i);
				int index=p.getChildrens().indexOf(cate1);
				if(index==-1){
					p.getChildrens().add(cate1);
					p=cate1;
				}else{
					p=p.getChildrens().get(index);
				}
				
			}
			//加入当前节点
			p.getChildrens().add(cate);
			
		}
		
		return root.values();
	}
	
	@Override
	public List<Long> find3CategoryId() {
		String json = redisUtil.getData("PRODUCT_CATEGORY_SELL");
		if(!StringUtils.isEmpty(json)){
			return JsonUtil.getList4Json(json, Long.class);
		}else{
			List<Long> rtn = new ArrayList<Long>();
			List<ProductCategory> cs = find3ProductCategoryId();
			for (ProductCategory productCategory : cs) {
				rtn.add(productCategory.getId());
			}
			json = JsonUtil.toJsonString(rtn);
			redisUtil.setData("PRODUCT_CATEGORY_SELL", json, 6*60*60);
			return rtn;
		}
	}
	private List<ProductCategory> find3ProductCategoryId() {
		Sql supplierSql = Sqls.create("SELECT DISTINCT p.categoryId id FROM t_product p WHERE p.is_marketable=1");
		supplierSql.setCallback(Sqls.callback.entities());
		supplierSql.setEntity(dao.getEntity(ProductCategory.class));
		dao.execute(supplierSql);
		return supplierSql.getList(ProductCategory.class);
	}
	
	public List<ProductCategory> findParents(Long id) {
		if(id==null){
			return null;
		}
		ProductCategory cate = findById(id);
		if(cate==null){
			return null;
		}
		List<String> li=new ArrayList<String>();
		String flag=cate.getBrotherOrderAll();
		int last=flag.lastIndexOf("_");
		while(last>0){
			flag=flag.substring(0, last);
			li.add(flag);
			last=flag.lastIndexOf("_");
		}
		if(li.isEmpty() || id.equals(cate.getRootId())) {
			List<ProductCategory> ret = new ArrayList<>();
			ret.add(cate);
			return ret;
			
		} else {
			List<ProductCategory> ret = dao.query(ProductCategory.class,
					Cnd.where("rootId", "=", cate.getRootId()).and("brotherOrderAll", "in", li).asc("deep"));
			ret.add(cate);
			return ret;
		}
	}

	@Override
	public String getSearchCat(String cat) {

		String[] cats = cat.split(",");
		List<Long> ns = new ArrayList<Long>();
		List<ProductCategory> trace = new ArrayList<ProductCategory>();
		for (int i = 0; i < cats.length; i++) {
			ProductCategory p=findById(Long.valueOf(cats[i]));
			if(p!=null) {
				trace.add(p);	
				ns.add(p.getId());
			}
		}
		
		for (ProductCategory productCategory : trace) {
			//删除根节点
			if(productCategory.getRootId()!=null && !productCategory.getId().equals(productCategory.getRootId())){
				removeElement(ns,productCategory.getRootId());
			}
			//删除父节点
			if(productCategory.getPid()!=null && !productCategory.getId().equals(productCategory.getPid())){
				removeElement(ns,productCategory.getPid());
			}
		}
		
		String realCat="";
		for (Long id : ns) {
			realCat +=id+",";
		}
		if(realCat.length() == 0) return "";
		return realCat.substring(0,realCat.length()-1);
	}
	
	// 判断存在 并删除元素
	private boolean removeElement(List<Long> ns,Long id) {
		for(int i=0;i<ns.size();i++) {
			if(id.equals(ns.get(i))) {
				ns.remove(i);
				return true;
			}
		}
		return false;
	}

}
