/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.FactoryMongoBase;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.supplier.dao.SupplierLogDao;

@Repository("supplierLogDao")
public class SupplierLogDaoImpl extends FactoryMongoBase<SupplierLog> implements SupplierLogDao{

	@Override
	public SupplierLog query2Bean(BaseQuery obj) {

		SupplierLog bean = new SupplierLog();

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
	public String getCollName() {
		return "t_supplier_log";
	}

	@Override
	public SupplierLog initBean(Document doc) {
		SupplierLog bean = new SupplierLog();
		doc2Bean(bean,doc);
		return bean;
	}

	@Override
	public Document toDocment(SupplierLog obj) {
		return obj2Document(obj);
	}

	@Override
	public Bson getFilter(SupplierLog newdoc) {
		List<Bson> lst = new ArrayList<Bson>();
		obj2Filter(newdoc,lst,"");
		if(lst.size()==0) {
			return null;
		} else {
			return Filters.and(lst);
		}
	}
	
	private void doc2Bean(Object obj,Document doc) {
		Class objClass = obj.getClass();
		String linkObjs = "";
		for (String key : doc.keySet()) {
    		if(linkObjs.contains("," + key + ",")) {
//    			if("suborderitem".equals(key)) {
//    				Suborderitem soi = new Suborderitem();
//    				((SupplierLog)obj).setSuborderitem(soi);
//    				doc2Bean(soi,(Document)doc.get(key));
//    			} else if("suborder".equals(key)) {
//    				Suborder so = new Suborder();
//    				((SupplierLog)obj).setSuborder(so);
//    				doc2Bean(so,(Document)doc.get(key));
//    			} else if("product".equals(key)) {
//    				Product p = new Product();
//    				((SupplierLog)obj).setProduct(p);
//    				doc2Bean(p,(Document)doc.get(key));
//    			} else if("user".equals(key)) {
//    				UserFactory u = new UserFactory();
//    				((SupplierLog)obj).setUser(u);
//    				doc2Bean(u,(Document)doc.get(key));
//    			} else if("supplier".equals(key)) {
//    				Supplier s = new Supplier();
//    				((SupplierLog)obj).setSupplier(s);
//    				doc2Bean(s,(Document)doc.get(key));
//    			} 
    		} else {
                try{
                	if("_id".equals(key)) {
                		((SupplierLog)obj).setId((String)doc.get(key).toString());
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

	private Document obj2Document(Object obj) {
		Document doc = new Document();
		String linkObjs = "";
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

	private void obj2Filter(Object obj,List<Bson> lst,String scope) {
		String linkObjs = "";
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
	
}
