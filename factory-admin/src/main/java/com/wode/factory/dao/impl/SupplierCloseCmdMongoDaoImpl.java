package com.wode.factory.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseQuery;
import com.wode.common.frame.base.FactoryMongoBase;
import com.wode.factory.dao.SupplierCloseCmdMongoDao;
import com.wode.factory.model.SupplierCloseCmd;
@Repository("supplierCloseCmdMongoDao")
public class SupplierCloseCmdMongoDaoImpl extends FactoryMongoBase<SupplierCloseCmd>  implements SupplierCloseCmdMongoDao {

	@Override
	public String getCollName() {
		return "t_supplier_close_cmd";
	}

	@Override
	public Document toDocment(SupplierCloseCmd obj) {
		return obj2Document(obj);
	}

	private Document obj2Document(Object obj) {
		Document doc = new Document();
		String linkObjs = ",,";
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
	public Bson getFilter(SupplierCloseCmd newdoc) {
		return null;
	}
	
	@Override
	public SupplierCloseCmd query2Bean(BaseQuery newdoc) {
		return null;
	}

	@Override
	public SupplierCloseCmd initBean(Document doc) {
		return null;
	}
}
