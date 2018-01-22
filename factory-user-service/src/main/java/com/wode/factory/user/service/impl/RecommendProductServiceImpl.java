package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.CategoryAttributeService;
import com.wode.factory.user.service.RecommendProductService;
import com.wode.factory.user.vo.SkuVo;

@Service("recommendProductService")
public class RecommendProductServiceImpl implements RecommendProductService {

	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private CategoryAttributeService attrService;
	
	private static Logger logger= LoggerFactory.getLogger(RecommendProductServiceImpl.class);
	
	
	public List<Product> findRecommendByCate(Long cateid) {
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
				 Product product=JsonUtil.getObject(p, Product.class);
				 if(product!=null&&!ret.contains(product)){
					 ret.add(product);
				 }
				 
			 }
		 }
		return ret;
	}
	
	public List<Product> findHotSellByCate(Long cateid) {
		 String json=redisUtil.getMapData(RedisConstant.HOT_SELL,cateid+"");
		 List<Product> ret=new ArrayList();
		 if(json!=null){
			 List<String> ids=JsonUtil.getList(json, String.class);
			 String[] arry=new String[ids.size()];
			 for(int i=0;i<ids.size();i++){
				 arry[i]=RedisConstant.PRODUCT_PRE+ids.get(i);
			 }
			 List<String> ps=redisUtil.getMapDatas(arry,RedisConstant.PRODUCT_REDIS_INFO);
			 for(String p:ps){
				 Product product=JsonUtil.getObject(p, Product.class);
				 if(product!=null&&!ret.contains(product)){	
					 if(product.getSelfType()==null || (product.getSelfType()==0||product.getSelfType()==3)) {
						 ret.add(product);
					 }
				 }
			 }
			 
			 
		 }
		return ret;
	}
	
	
	public List<Product> findHotSell() {
		
		Map<String,String> map=redisUtil.getMap(RedisConstant.HOT_SELL);
		
		
		 List<Product> ret=new ArrayList();
		 if(map!=null){
			 Collection<String> listId=map.values();
			 List<String> ids=new ArrayList();
			 for(Iterator<String> it=listId.iterator();it.hasNext();){
				 ids.addAll(JsonUtil.getList(it.next(), String.class));
			 }
			 String[] arry=new String[ids.size()];
			 for(int i=0;i<ids.size();i++){
				 arry[i]=RedisConstant.PRODUCT_PRE+ids.get(i);
			 }
			 List<String> ps=redisUtil.getMapDatas(arry,RedisConstant.PRODUCT_REDIS_INFO);
			 for(String p:ps){
				 Product product=JsonUtil.getObject(p, Product.class);
				 if(product!=null&&!ret.contains(product)){
					 ret.add(product);
				 }
			 }
			 
			 
		 }
		return ret;
	}
	
	public List<Product> random() {
		List<Product> ret=new ArrayList<>();
		Map<String,String> map=redisUtil.getMap(RedisConstant.HOT_SELL);
		
		List<String> ids=new LinkedList<>();
		for(String json:map.values()){
			 List<String> pids=JsonUtil.getList(json, String.class);
			 for(int i=0;i<pids.size();i++){
				 if(!ids.contains(RedisConstant.PRODUCT_PRE+pids.get(i))){
					 ids.add(RedisConstant.PRODUCT_PRE+pids.get(i));
					 
					 
				 }
				
			 }
		}
		
		
		//随机取
		while(ids.size()>10){
			int index = (int)(Math.random() * ids.size());
			ids.remove(index);
			
		}
		
		
		String[] arry=new String[ids.size()];
		ids.toArray(arry);
		List<String> ps=redisUtil.getMapDatas(arry,RedisConstant.PRODUCT_REDIS_INFO);
		
		for(String productJson:ps){
			Product p=JsonUtil.getObject(productJson, Product.class);
			if(p!=null){

				if(p.getProductSpecificationslist() == null || p.getProductSpecificationslist().isEmpty()) {

					p.setShowPrice("<p class=\"p1\"><span>内购价：￥"+(StringUtils.isEmpty(p.getShowPrice())?p.getMinprice()+"":p.getMinprice())+"</span></p>");
				} else {
					ProductSpecifications sku = p.getProductSpecificationslist().get(0);
					p.setShowPrice("<p class=\"p1\"><span>内购价：￥"+sku.getPrice().subtract(sku.getMaxFucoin()).setScale(2, BigDecimal.ROUND_DOWN)+"</span><em>"
							+sku.getPrice().subtract(sku.getMaxFucoin()).multiply(new BigDecimal(10)).divide(sku.getPrice(), BigDecimal.ROUND_DOWN).setScale(1, BigDecimal.ROUND_DOWN)+"折</em></p>");
					p.setMinprice(sku.getPrice());
				}
				
				ret.add(p);
			}
			
		}
		
		return ret;
	}
	
	
	@Override
	public List<SkuVo> findHotSell1() {
		
		Map<String,String> map=redisUtil.getMap(RedisConstant.HOT_SELL);
		
		
		 List<SkuVo> ret=new ArrayList();
		 if(map!=null){
			 Collection<String> listId=map.values();
			 List<String> ids=new ArrayList();
			 for(Iterator<String> it=listId.iterator();it.hasNext();){
				 ids.addAll(JsonUtil.getList(it.next(), String.class));
			 }
			 String[] arry=new String[ids.size()];
			 for(int i=0;i<ids.size();i++){
				 arry[i]=RedisConstant.PRODUCT_PRE+ids.get(i);
			 }
			 List<Map> productAll=redisUtil.getMaps(arry);
			 for(Map<String,String> all:productAll){
				 
				 Product product=JsonUtil.getObject(all.get(RedisConstant.PRODUCT_REDIS_INFO), Product.class);
				 if(product==null){
					 continue;
				 }
				 List<ProductSpecifications> skus=JsonUtil.getList(all.get(RedisConstant.PRODUCT_REDIS_SKU), ProductSpecifications.class);
				 if(skus!=null&&skus.size()>0){
					 ProductSpecifications ps=skus.get(0);
					 if(ps==null){
						 continue;
					 }
					 SkuVo sku=new SkuVo();
					 sku.setName(product.getName());
					 sku.setPrice(ps.getPrice());
					 sku.setCompanyTicket(ps.getMaxFucoin()==null?new BigDecimal(0):ps.getMaxFucoin());
					 sku.setImage(product.getImage());
					 sku.setProductId(product.getId());
					 sku.setSkuId(ps.getId());
					 if(!ret.contains(sku)){
						 ret.add(sku);
					 }
				 }
				 
				 
			 }
			 
			 
		 }
		return ret;
	}

}
