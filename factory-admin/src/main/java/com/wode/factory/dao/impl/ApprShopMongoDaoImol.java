package com.wode.factory.dao.impl;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.FactoryMongoBase;
import com.wode.factory.dao.ApprShopMongoDao;
import com.wode.factory.model.ApprShop;
@Repository("apprShopMongoDao")
public class ApprShopMongoDaoImol extends FactoryMongoBase<ApprShop> implements ApprShopMongoDao {

	@Override
	public String getCollName() {
		return "t_appr_shop";
	}

	@Override
	public ApprShop initBean(Document doc) {
		return null;
	}

	@Override
	public Document toDocment(ApprShop obj) {
		return obj2Document(obj);
	}

	private Document obj2Document(Object obj) {
		Document doc = new Document();
		String linkObjs = ",,";
    	Field[] fileds = obj.getClass().getDeclaredFields();

    	/*if("com.wode.factory.model.ApprShop".equals(obj.getClass().getName())) {
    		Field[] superfileds = obj.getClass().getSuperclass().getDeclaredFields();
    		fileds = ArrayUtils.addAll(superfileds, obj.getClass().getSuperclass().getSuperclass().getDeclaredFields());
    	}*/
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
                	if(value instanceof ArrayList<?>) {
                		List<Document> ary =new ArrayList<Document>();
                		List<?> ls = (List<?>)value;
                		for (Object object : ls) {
                			ary.add(obj2Document(object));
						}
                		doc.put(fieldName, ary);
                	} else {
                		doc.put(fieldName, obj2Document(value));
                	}
            	}
            }
    	}
    	
    	return doc;
	}
	
	private boolean isBasicType(Field f) {
		String linkObjs = ",String,int,Long,Integer,Date,Double,double,BigDecimal,";
		return linkObjs.contains("," + f.getType().getSimpleName() + ",");
	}
	
	@Override
	public Bson getFilter(ApprShop newdoc) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ApprShop query2Bean(BaseQuery newdoc) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
