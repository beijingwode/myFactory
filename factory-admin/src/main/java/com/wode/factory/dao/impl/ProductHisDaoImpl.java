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
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.FactoryMongoBase;
import com.wode.common.util.NumberUtil;
import com.wode.factory.dao.ProductHisDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.model.ProductHis;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;


@Repository("productHisDao")
public class ProductHisDaoImpl extends FactoryMongoBase<ProductHis> implements ProductHisDao {
    
	@Override
	public String getCollName() {
		return "t_product_his";
	}

	@Override
	public ProductHis initBean(Document doc) {
		ProductHis bean = new ProductHis();
		doc2Bean(bean,doc);
		return bean;
	}

	private void doc2Bean(Object obj,Document doc) {
		Class objClass = obj.getClass();
		String linkObjs = ",product,productSpecificationslist,productSpecificationValuelist,productAttributelist,productParameterValuelist,productDetaillist,";
		for (String key : doc.keySet()) {
    		if(linkObjs.contains("," + key + ",")) {
    			if("product".equals(key)) {
    				Product p = new Product();
    				((ProductHis)obj).setProduct(p);
    				doc2Bean(p,(Document)doc.get(key));
    			} else if("productSpecificationslist".equals(key)) {

    		    	if(obj instanceof Product) {
	    				List<ProductSpecifications> productSpecificationslist = new ArrayList<ProductSpecifications>();
	    				((Product)obj).setProductSpecificationslist(productSpecificationslist);
	    				List<?> ary=(List<?>)doc.get(key);
	    				for (Object document : ary) {
	    					ProductSpecifications pss = new ProductSpecifications();
	        				doc2Bean(pss,(Document)document);
	        				productSpecificationslist.add(pss);
						}
    		    	}
    			} else if("productSpecificationValuelist".equals(key)) {
    				List<ProductSpecificationValue> productSpecificationValuelist = new ArrayList<ProductSpecificationValue>();
    				((Product)obj).setProductSpecificationValuelist(productSpecificationValuelist);
    				List<?> ary=(List<?>)doc.get(key);
    				for (Object document : ary) {
    					ProductSpecificationValue pss = new ProductSpecificationValue();
        				doc2Bean(pss,(Document)document);
        				productSpecificationValuelist.add(pss);
					}
    			} else if("productAttributelist".equals(key)) {
    				List<ProductAttribute> productAttributelist = new ArrayList<ProductAttribute>();
    				((Product)obj).setProductAttributelist(productAttributelist);
    				List<?> ary=(List<?>)doc.get(key);
    				for (Object document : ary) {
    					ProductAttribute pss = new ProductAttribute();
        				doc2Bean(pss,(Document)document);
        				productAttributelist.add(pss);
					}
    			} else if("productParameterValuelist".equals(key)) {
    				List<ProductParameterValue> productParameterValuelist = new ArrayList<ProductParameterValue>();
    				((Product)obj).setProductParameterValuelist(productParameterValuelist);
    				List<?> ary=(List<?>)doc.get(key);
    				for (Object document : ary) {
    					ProductParameterValue pss = new ProductParameterValue();
        				doc2Bean(pss,(Document)document);
        				productParameterValuelist.add(pss);
					}
    			} else if("productDetaillist".equals(key)) {
    				List<ProductDetailList> productDetaillist = new ArrayList<ProductDetailList>();
    				((Product)obj).setProductDetaillist(productDetaillist);
    				List<?> ary=(List<?>)doc.get(key);
    				for (Object document : ary) {
    					ProductDetailList pss = new ProductDetailList();
        				doc2Bean(pss,(Document)document);
        				productDetaillist.add(pss);
					}
    			} 
    		} else {
                try{
                	if("_id".equals(key)) {
                		((ProductHis)obj).setId((String)doc.get(key).toString());
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
	public Document toDocment(ProductHis obj) {
		return obj2Document(obj);
	}

	private Document obj2Document(Object obj) {
		Document doc = new Document();
		String linkObjs = ",product,productSpecificationslist,productSpecificationValuelist,productAttributelist,productParameterValuelist,productDetaillist,";
    	Field[] fileds = obj.getClass().getDeclaredFields();

    	
    	if("com.wode.factory.model.ApprProduct".equals(obj.getClass().getName())) {
    		fileds = obj.getClass().getSuperclass().getDeclaredFields();
    	} else if("com.wode.factory.vo.ApprProductVO".equals(obj.getClass().getName())) {
    		fileds = obj.getClass().getSuperclass().getSuperclass().getDeclaredFields();
    	} else if("com.wode.factory.vo.ProductVO".equals(obj.getClass().getName())) {
    		fileds = obj.getClass().getSuperclass().getDeclaredFields();
    	}
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
	
	@Override
	public Bson getFilter(ProductHis newdoc) {
		List<Bson> lst = new ArrayList<Bson>();
		obj2Filter(newdoc,lst,"");
		if(lst.size()==0) {
			return null;
		} else {
			return Filters.and(lst);
		}
	}

	private void obj2Filter(Object obj,List<Bson> lst,String scope) {
		String linkObjs = ",product,productSpecificationslist,productSpecificationValuelist,productAttributelist,productParameterValuelist,productDetaillist,";
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
	public ProductHis query2Bean(BaseQuery newdoc) {
		// TODO Auto-generated method stub
		return null;
	}
}