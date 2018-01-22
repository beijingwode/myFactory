package com.wode.factory.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_page_section_data")
public class PageSectionData extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5468909487295152561L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 页id       db_column: page_id
     * 
     * 
     * 
     */ 
    @Column("page_id")
    private java.lang.Long pageId;
    /**
     * 渠道 1:pc 2:微信/app 同page       db_column: channel
     * 
     * 
     * 
     */ 
    @Column("channel")
    private java.lang.Integer channel;
    /**
     * 位置id       db_column: section_id
     * 
     * 
     * 
     */ 
    @Column("section_id")
    private java.lang.Long sectionId;
    /**
     * 标题       db_column: title
     * 
     * 
     * 
     */ 
    @Column("title")
    private java.lang.String title;
    /**
     * 图片路径       db_column: image_path
     * 
     * 
     * 
     */ 
    @Column("image_path")
    private java.lang.String imagePath;
    /**
     * 排序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    /**
     * 链接       db_column: link
     * 
     * 
     * 
     */ 
    @Column("link")
    private java.lang.String link;
    /**
     * 扩展1值       db_column: ex1_value
     * 
     * 
     * 
     */ 
    @Column("ex1_value")
    private java.lang.String ex1Value;
    /**
     * 扩展2值       db_column: ex2_value
     * 
     * 
     * 
     */ 
    @Column("ex2_value")
    private java.lang.String ex2Value;
    /**
     * 扩展3值       db_column: ex3_value
     * 
     * 
     * 
     */ 
    @Column("ex3_value")
    private java.lang.String ex3Value;
    /**
     * 扩展4值       db_column: ex3_value
     * 
     * 
     * 
     */ 
    @Column("ex4_value")
    private java.lang.String ex4Value;
    /**
     * 扩展5值       db_column: ex3_value
     * 
     * 
     * 
     */ 
    @Column("ex5_value")
    private java.lang.String ex5Value;
    /**
     * 扩展6值       db_column: ex3_value
     * 
     * 
     * 
     */ 
    @Column("ex6_value")
    private java.lang.String ex6Value;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 标题       db_column: title
     * 
     */ 
    @Column("title1")
    private java.lang.String title1;
    /**
     * 标题       db_column: title
     * 
     */ 
    @Column("title2")
    private java.lang.String title2;
    //columns END

    
	private Integer locked;	//锁定 0:未锁定/1:锁定',
	private String lockReason; 
	private java.lang.Integer isMarketable;
	private String sectionTitle; 
	private Long productId;
	private boolean isLinkProduct;
	private String pageTitle; 	
    
    public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("PageId",getPageId())
            .append("Channel",getChannel())
            .append("SectionId",getSectionId())
            .append("Title",getTitle())
            .append("ImagePath",getImagePath())
            .append("Orders",getOrders())
            .append("Link",getLink())
            .append("Ex1Value",getEx1Value())
            .append("Ex2Value",getEx2Value())
            .append("Ex3Value",getEx3Value())
            .append("Ex4Value",getEx3Value())
            .append("Ex5Value",getEx3Value())
            .append("Ex6Value",getEx3Value())
            .append("CreateTime",getCreateTime())
            .append("UpdateTime",getUpdateTime())
            .append("Title1",getTitle1())
            .append("Title2",getTitle2())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getPageId() {
		return pageId;
	}

	public void setPageId(java.lang.Long pageId) {
		this.pageId = pageId;
	}

	public java.lang.Integer getChannel() {
		return channel;
	}

	public void setChannel(java.lang.Integer channel) {
		this.channel = channel;
	}

	public java.lang.Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(java.lang.Long sectionId) {
		this.sectionId = sectionId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getImagePath() {
		return imagePath;
	}

	public void setImagePath(java.lang.String imagePath) {
		this.imagePath = imagePath;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.String getLink() {
		return link;
	}

	public void setLink(java.lang.String value) {
		this.link = value;
		if(isLinkProduct(value)) {
			this.productId = Long.valueOf(value);
			this.isLinkProduct = true;
		}
	}

	public java.lang.String getEx1Value() {
		return ex1Value;
	}

	public void setEx1Value(java.lang.String ex1Value) {
		this.ex1Value = ex1Value;
	}

	public java.lang.String getEx2Value() {
		return ex2Value;
	}

	public void setEx2Value(java.lang.String ex2Value) {
		this.ex2Value = ex2Value;
	}

	public java.lang.String getEx3Value() {
		return ex3Value;
	}

	public void setEx3Value(java.lang.String ex3Value) {
		this.ex3Value = ex3Value;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isLinkProduct() {
		return this.isLinkProduct;
	}

	public static boolean isLinkProduct(String link) {
		if(StringUtils.isNotBlank(link)) {
			Pattern pattern = Pattern.compile("^[1-9][0-9]+");
			Matcher matcher = pattern.matcher(link);
			return matcher.matches();
		}
		return false;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public java.lang.Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(java.lang.Integer isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public java.lang.String getEx4Value() {
		return ex4Value;
	}

	public void setEx4Value(java.lang.String ex4Value) {
		this.ex4Value = ex4Value;
	}

	public java.lang.String getEx5Value() {
		return ex5Value;
	}

	public void setEx5Value(java.lang.String ex5Value) {
		this.ex5Value = ex5Value;
	}

	public java.lang.String getEx6Value() {
		return ex6Value;
	}

	public void setEx6Value(java.lang.String ex6Value) {
		this.ex6Value = ex6Value;
	}
	
	public java.lang.String getTitle1() {
		return title1;
	}

	public void setTitle1(java.lang.String title1) {
		this.title1 = title1;
	}

	public java.lang.String getTitle2() {
		return title2;
	}

	public void setTitle2(java.lang.String title2) {
		this.title2 = title2;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

