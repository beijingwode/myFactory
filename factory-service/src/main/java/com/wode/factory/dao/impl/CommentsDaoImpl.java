/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.nutz.img.Images;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.FactoryMongoBase;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.dao.CommentsDao;
import com.wode.factory.model.Comments;
import com.wode.factory.model.Product;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.vo.CommentsQuery;


@Repository("commentsDao")
public class CommentsDaoImpl extends FactoryMongoBase<Comments> implements CommentsDao {

    @Override
    public PageInfo<Comments> findPage(CommentsQuery query) {
    	Page<Comments> page = new Page<Comments>(query.getPageNumber(),query.getPageSize());
    	Long cnt = this.findCount(query);
    	if(cnt > 0) {
    		page.addAll(this.find(this.query2Bean(query), "t_suborderitem.createTime", -1, query.getPageNumber(), query.getPageSize()));
    		page.setTotal(cnt);
    	}
    	
    	return new PageInfo<Comments>(page);
    }
    
	@Override
	public String getCollName() {
		return "t_comments";
	}

	@Override
	public Comments initBean(Document doc) {
		Comments bean = new Comments();
		doc2Bean(bean,doc);
		return bean;
	}

	private void doc2Bean(Object obj,Document doc) {
		Class objClass = obj.getClass();
		String linkObjs = ",suborderitem,suborder,product,user,supplier,";
		for (String key : doc.keySet()) {
    		if(linkObjs.contains("," + key + ",")) {
    			if("suborderitem".equals(key)) {
    				Suborderitem soi = new Suborderitem();
    				((Comments)obj).setSuborderitem(soi);
    				doc2Bean(soi,(Document)doc.get(key));
    			} else if("suborder".equals(key)) {
    				Suborder so = new Suborder();
    				((Comments)obj).setSuborder(so);
    				doc2Bean(so,(Document)doc.get(key));
    			} else if("product".equals(key)) {
    				Product p = new Product();
    				((Comments)obj).setProduct(p);
    				doc2Bean(p,(Document)doc.get(key));
    			} else if("user".equals(key)) {
    				UserFactory u = new UserFactory();
    				((Comments)obj).setUser(u);
    				doc2Bean(u,(Document)doc.get(key));
    			} else if("supplier".equals(key)) {
    				Supplier s = new Supplier();
    				((Comments)obj).setSupplier(s);
    				doc2Bean(s,(Document)doc.get(key));
    			} 
    		} else {
                try{
                	if("_id".equals(key)) {
                		((Comments)obj).setId((String)doc.get(key).toString());
                	} else {
	        			String setName = "set" + key.substring(0, 1).toUpperCase()+key.substring(1);
	        			Field f = objClass.getDeclaredField(key);
	    				Method setMethod=obj.getClass().getMethod(setName,f.getType());
	    				if("BigDecimal".equals(f.getType().getSimpleName())) {
	    					setMethod.invoke(obj,NumberUtil.toBigDecimal(doc.get(key)));
	    				} else {
		    				setMethod.invoke(obj,doc.get(key));
	    				}
                	}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
		}
	}
	
	@Override
	public Document toDocment(Comments obj) {
		return obj2Document(obj);
	}

	private Document obj2Document(Object obj) {
		Document doc = new Document();
		String linkObjs = ",suborderitem,suborder,product,user,supplier,images,";
    	Field[] fileds = obj.getClass().getDeclaredFields();
    	for(Field f : fileds){
			String fieldName=f.getName();
			if("shopList".equals(fieldName)) continue;
            String stringLetter=fieldName.substring(0, 1).toUpperCase();
            String getName = "get" + stringLetter+fieldName.substring(1);
            Object value = null;
            try{
				Method setMethod=obj.getClass().getMethod(getName);
				value=setMethod.invoke(obj);
			} catch (Exception e) {
				
			}
            
            if(value != null) {
            	if(isBasicType(f)) {
            		if(value instanceof BigDecimal) {
                		doc.put(fieldName, ((BigDecimal)value).doubleValue());
            		} else {
                		doc.put(fieldName, value);
            			
            		}
            	}else if(value instanceof ArrayList){
            			doc.put(fieldName, value);
            	}else if(linkObjs.contains("," + fieldName + ",")) {
            		doc.put(fieldName, obj2Document(value));
            	}
            }
    	}
    	
    	return doc;
	}
	
	@Override
	public Bson getFilter(Comments newdoc) {
		List<Bson> lst = new ArrayList<Bson>();
		obj2Filter(newdoc,lst,"");
		if(lst.size()==0) {
			return null;
		} else {
			return Filters.and(lst);
		}
	}

	private void obj2Filter(Object obj,List<Bson> lst,String scope) {
		String linkObjs = ",suborderitem,suborder,product,user,supplier,";
    	Field[] fileds = obj.getClass().getDeclaredFields();
    	for(Field f : fileds){
			String fieldName=f.getName();
            String stringLetter=fieldName.substring(0, 1).toUpperCase();
            String getName = "get" + stringLetter+fieldName.substring(1);
            Object value = null;
            try{
				Method setMethod=obj.getClass().getMethod(getName);
				value=setMethod.invoke(obj);
			} catch (Exception e) {
				
			}
            
            if(value != null) {
            	if(isBasicType(f)) {
        			lst.add(Filters.eq(scope + fieldName, value));
            	} else if(linkObjs.contains("," + fieldName + ",")) {
            		obj2Filter(value,lst,fieldName + ".");
            	}
            }
    	}
	}
	
	private boolean isBasicType(Field f) {
		String linkObjs = ",String,int,Long,Integer,Date,Double,double,BigDecimal,";
		return linkObjs.contains("," + f.getType().getSimpleName() + ",");
	}
	
	@Override
	public Map<String, Object> getAggregate(Comments bean) {
		Map<String, Object> rtn = new HashMap<String, Object>();
	
		//获得评论总和
//		Bson project = Aggregates.project(Projections.fields(
//				Projections.include("cnt","goodsSum","logisticsSum","serviceSum")));
		Bson match = Aggregates.match(getFilter(bean));
		Bson group = Aggregates.group(null,
				new BsonField("cnt",new Document("$sum",1)),
				new BsonField("goodsSum",new Document("$sum","$star1")),
				new BsonField("logisticsSum",new Document("$sum","$star2")),
				new BsonField("serviceSum",new Document("$sum","$star3")));
		
		AggregateIterable<Document> result= this.getMongoCollection().aggregate(Arrays.asList(match,group));
		
		//查询不到该数据
		if(!result.iterator().hasNext()) {
			
			rtn.put(CommentsDao.MAP_KEY_ALL_CNT, 0L);
			rtn.put(CommentsDao.MAP_KEY_GOODS_SUM, 5L);
			rtn.put(CommentsDao.MAP_KEY_LOGISTICS_SUM, 5L);
			rtn.put(CommentsDao.MAP_KEY_SERVICE_SUM, 5L);	

			rtn.put(CommentsDao.MAP_KEY_BAD_CNT, 0L);
			rtn.put(CommentsDao.MAP_KEY_NOMAL_CNT, 0L);
			rtn.put(CommentsDao.MAP_KEY_PRAISE_CNT, 0L);
			
		} else {
			Document obj = result.first();
			rtn.put(CommentsDao.MAP_KEY_ALL_CNT, obj.get("cnt"));
			rtn.put(CommentsDao.MAP_KEY_GOODS_SUM, obj.get("goodsSum"));
			rtn.put(CommentsDao.MAP_KEY_LOGISTICS_SUM, obj.get("logisticsSum"));
			rtn.put(CommentsDao.MAP_KEY_SERVICE_SUM, obj.get("serviceSum"));
			
			rtn.putAll(this.getSupplierLevelCnt(bean,null,null));
		}

		return rtn;
	}

	@Override
	public Long findCount(CommentsQuery query) {
		return this.count(query);
	}

	@Override
	public Comments query2Bean(BaseQuery obj) {
		Comments bean = new Comments();

    	Field[] fileds = obj.getClass().getDeclaredFields();
    	for(Field f : fileds){
			String fieldName=f.getName();
            String stringLetter=fieldName.substring(0, 1).toUpperCase();
            String getName = "get" + stringLetter+fieldName.substring(1);
            Object value = null;
            try{
				Method setMethod=obj.getClass().getMethod(getName);
				value=setMethod.invoke(obj);
			} catch (Exception e) {
				
			}
            
            if(value != null) {

                try{
                    String setName = "set" + stringLetter+fieldName.substring(1);
    				Method setMethod=bean.getClass().getMethod(setName,f.getType());
    				setMethod.invoke(bean,value);
    			} catch (Exception e) {
    				
    			}
            }
    	}
		return bean;
	}

	@Override
	public Map<String, Object> getSupplierLevelCnt(Comments bean,Date begin,Date end) {
		Bson match;
		if(StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)) {
			match = Aggregates.match(getFilter(bean));
		} else if(StringUtils.isEmpty(begin)) {
			match = Aggregates.match(Filters.and(getFilter(bean),Filters.lte("creatTime", end)));
		} else if(StringUtils.isEmpty(end)) {
			match = Aggregates.match(Filters.and(getFilter(bean),Filters.gte("creatTime", begin)));
		} else {
			match = Aggregates.match(Filters.and(getFilter(bean),Filters.gte("creatTime", begin),Filters.lte("creatTime", end)));
		}

		//Bson project2 = Aggregates.project(Projections.fields(Projections.include("cnt")));
		Bson group2 = Aggregates.group(new Document("degree","$commentDegree"),
				new BsonField("cnt",new Document("$sum",1)));
		
		AggregateIterable<Document> result2 = this.getMongoCollection()
				.aggregate(Arrays.asList(match, group2));

		Long badCnt =0L;
		Long normalCnt =0L;
		Long praseCnt =0L;
		
		for (Document document : result2) {
			int degree = ((Document)document.get("_id")).getInteger("degree", 0);
			//差评
			if(degree <= 1) {
				badCnt = badCnt + NumberUtil.toLong(document.get("cnt"));
			} else if (degree <= 3) {
				normalCnt = normalCnt + NumberUtil.toLong(document.get("cnt"));
			} else {
				praseCnt = praseCnt + NumberUtil.toLong(document.get("cnt"));
			}
		}

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(CommentsDao.MAP_KEY_BAD_CNT, badCnt);
		rtn.put(CommentsDao.MAP_KEY_NOMAL_CNT, normalCnt);
		rtn.put(CommentsDao.MAP_KEY_PRAISE_CNT, praseCnt);
		
		return rtn;
	}

}