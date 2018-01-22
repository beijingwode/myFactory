/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;





import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_category_advertisement")
public class CategoryAdvertisement extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CategoryAdvertisement";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_URL = "url";
	
	public static final String ALIAS_CATEGORY_ID = "二级分类id";
	
	public static final String ALIAS_IMG_PATH = "广告位图片路径";
	
	public static final String ALIAS_TEXT = "广告位文本";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * url       db_column: url  
     * 
     * 
     * @NotBlank @Length(max=255)
     */
	@Column("url")
	private java.lang.String url;
    /**
     * 二级分类id       db_column: categoryId  
     * 
     * 
     * @NotNull 
     */	
	@Column("categoryId")
	private java.lang.Long categoryId;
    /**
     * 广告位图片路径       db_column: imgPath  
     * 
     * 
     * @NotBlank @Length(max=255)
     */	
	@Column("imgPath")
	private java.lang.String imgPath;
    /**
     * 广告位文本       db_column: text  
     * 
     * 
     * @NotBlank @Length(max=255)
     */
	@Column("text")
	private java.lang.String text;
	//columns END
	
	@One(target = ProductCategory.class, field = "categoryId")
	private ProductCategory productCategory;
	
	

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public CategoryAdvertisement(){
	}

	public CategoryAdvertisement(
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
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	public void setImgPath(java.lang.String value) {
		this.imgPath = value;
	}
	
	public java.lang.String getImgPath() {
		return this.imgPath;
	}
	public void setText(java.lang.String value) {
		this.text = value;
	}
	
	public java.lang.String getText() {
		return this.text;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Url",getUrl())
			.append("CategoryId",getCategoryId())
			.append("ImgPath",getImgPath())
			.append("Text",getText())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CategoryAdvertisement == false) return false;
		if(this == obj) return true;
		CategoryAdvertisement other = (CategoryAdvertisement)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

