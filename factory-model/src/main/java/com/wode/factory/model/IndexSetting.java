/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

public class IndexSetting extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "IndexSetting";

	public static final String ALIAS_ID = "id";

	public static final String ALIAS_INDEX_KEY = "key";

	public static final String ALIAS_DESCIPT = "descipt";

	public static final String ALIAS_INDEX_VALUE = "value";

	//date formats

	//columns START
	/**
	 * id       db_column: id
	 */
	@PrimaryKey
	private java.lang.Long id;
	/**
	 * key       db_column: index_key
	 *
	 * @Length(max=255)
	 */
	private java.lang.String indexKey;
	/**
	 * descipt       db_column: descipt
	 *
	 * @Length(max=255)
	 */
	private java.lang.String descipt;
	/**
	 * value       db_column: index_value
	 *
	 * @Length(max=500)
	 */
	private java.lang.String indexValue;
	//columns END

	public IndexSetting() {
	}

	public IndexSetting(
			java.lang.Long id
	) {
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setIndexKey(java.lang.String value) {
		this.indexKey = value;
	}

	public java.lang.String getIndexKey() {
		return this.indexKey;
	}

	public void setDescipt(java.lang.String value) {
		this.descipt = value;
	}

	public java.lang.String getDescipt() {
		return this.descipt;
	}

	public void setIndexValue(java.lang.String value) {
		this.indexValue = value;
	}

	public java.lang.String getIndexValue() {
		return this.indexValue;
	}

}

