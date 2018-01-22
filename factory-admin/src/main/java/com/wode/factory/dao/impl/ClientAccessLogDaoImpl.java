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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.FactoryMongoBase;
import com.wode.common.util.NumberUtil;
import com.wode.factory.dao.ClientAccessLogDao;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Product;
import com.wode.factory.model.UserFactory;


@Repository("clientAccessLogDao")
public class ClientAccessLogDaoImpl extends FactoryMongoBase<ClientAccessLog> implements ClientAccessLogDao {

	@Override
	public String getCollName() {
		return "t_client_access_logs";
	}

	@Override
	public ClientAccessLog initBean(Document doc) {
		ClientAccessLog bean = new ClientAccessLog();
		doc2Bean(bean,doc);
		return bean;
	}

	private void doc2Bean(Object obj,Document doc) {
		Class objClass = obj.getClass();
		String linkObjs = ",product,user,";
		for (String key : doc.keySet()) {
    		if(linkObjs.contains("," + key + ",")) {
    			if("product".equals(key)) {
    				Product p = new Product();
    				((ClientAccessLog)obj).setProduct(p);
    				doc2Bean(p,(Document)doc.get(key));
    			} else if("user".equals(key)) {
    				UserFactory u = new UserFactory();
    				((ClientAccessLog)obj).setUser(u);
    				doc2Bean(u,(Document)doc.get(key));
    			} 
    		} else {
                try{
                	if("_id".equals(key)) {
                		((ClientAccessLog)obj).setId((String)doc.get(key).toString());
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
	public Document toDocment(ClientAccessLog obj) {
		return obj2Document(obj);
	}

	private Document obj2Document(Object obj) {
		Document doc = new Document();
		String linkObjs = ",product,user,";
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
            		if(value instanceof BigDecimal) {
                		doc.put(fieldName, ((BigDecimal)value).doubleValue());
            		} else {
                		doc.put(fieldName, value);
            			
            		}
            	} else if(linkObjs.contains("," + fieldName + ",")) {
            		doc.put(fieldName, obj2Document(value));
            	}
            }
    	}
    	
    	return doc;
	}
	
	@Override
	public Bson getFilter(ClientAccessLog newdoc) {
		List<Bson> lst = new ArrayList<Bson>();
		obj2Filter(newdoc,lst,"");
		if(lst.size()==0) {
			return null;
		} else {
			return Filters.and(lst);
		}
	}

	private void obj2Filter(Object obj,List<Bson> lst,String scope) {
		String linkObjs = ",product,user,";
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
	public ClientAccessLog query2Bean(BaseQuery obj) {
		ClientAccessLog bean = new ClientAccessLog();

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
	public Map<Long,Long> getDetailPvCnt(Long supplierId,Long productId) {
		Map<Long,Long> rtn = new HashMap<Long,Long>();
		
		List<Bson> lst = new ArrayList<Bson>();
		//类型搜素0
		lst.add(Filters.eq("accessType", ClientAccessLog.ACCESS_TYPE_PRODUCT));
		//按关键字搜素
		if(supplierId !=null) {
			lst.add(Filters.eq("product.supplierId", supplierId));
		}
		if(productId !=null) {
			lst.add(Filters.eq("productId", productId));
		}
		
		Bson match = Aggregates.match(Filters.and(lst));

		Bson group = Aggregates.group(new Document("type", "$productId"), 
				//计算该商品一共被浏览过的次数
				new BsonField("cnt",new Document("$sum",1)));
		
		//Bson sorts = Aggregates.sort(Sorts.descending("cnt"));
		AggregateIterable<Document> result= this.getMongoCollection().aggregate(Arrays.asList(match,group));
		if(!result.iterator().hasNext()) {
		} else {
			for (Document document : result) {
				rtn.put(NumberUtil.toLong(((Document)document.get("_id")).get("type")), NumberUtil.toLong(document.get("cnt")));
			}
		}
		return rtn;
	}
}