/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class ExpressCom extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ExpressCom";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_CODE = "code";
	
	public static final String ALIAS_NAME = "name";
	
	public static final String ALIAS_ORDERNO = "orderno";
	
	public static final String ALIAS_PIC = "pic";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * code       db_column: code  
     * 
     * 
     * @Length(max=10)
     */	
	private java.lang.String code;
    /**
     * name       db_column: name  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String name;
    /**
     * orderno       db_column: orderno  
     * 
     * 
     * 
     */	
	private java.lang.Integer orderno;
    /**
     * pic       db_column: pic  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String pic;
	//columns END

	public ExpressCom(){
	}

	public ExpressCom(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setOrderno(java.lang.Integer value) {
		this.orderno = value;
	}
	
	public java.lang.Integer getOrderno() {
		return this.orderno;
	}
	public void setPic(java.lang.String value) {
		this.pic = value;
	}
	
	public java.lang.String getPic() {
		return this.pic;
	}

}

