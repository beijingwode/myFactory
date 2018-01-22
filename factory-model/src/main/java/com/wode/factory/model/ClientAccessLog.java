/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_client_access_logs")
public class ClientAccessLog implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4085707027717881611L;

	//date formats
    public static final String FORMAT_CREAT_TIME = "yyyy-MM-dd HH:mm:ss";

	//date formats
    public static final Integer ACCESS_TYPE_PRODUCT = 0;	//商品
    public static final Integer ACCESS_TYPE_INDEX = 1;		//首页
    public static final Integer ACCESS_TYPE_SEARCH = 2;		//检索
    public static final Integer ACCESS_TYPE_SHOP = 3;		//店铺
    public static final Integer ACCESS_TYPE_CART = 4;		//购物车
    public static final Integer ACCESS_TYPE_DIRECT = 5;		//直接购买
    public static final Integer ACCESS_TYPE_ACTIVITY = 6;	//活动页

    //columns START
    /**
     * id       db_column: id
     */
    @PrimaryKey
    @Column("_id")
    private java.lang.String id;
    /**
     * user_id       db_column: user_id
     */
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 关键字       db_column: access_key
     *
     * @Length(max=500)
     */
    @Column("access_key")
    private java.lang.String accessKey;
    /**
     * 简略内容       db_column: access_text
     *
     * @Length(max=500)
     */
    @Column("access_text")
    private java.lang.String accessText;
    /**
     * 访问类型  0:商品/1:首页/2:检索/3:店铺/4.购物车/5:直接购买    db_column: access_type
     *
     * @Length(max=500)
     */
    @Column("access_type")
    private java.lang.Integer accessType;
    /**
     * creat_time       db_column: creat_time
     *
     * @NotNull
     */
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * 关键字2       db_column: access_key2
     *
     * @Length(max=500)
     */
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 关键字2       db_column: access_key2
     *
     * @Length(max=500)
     */
    @Column("ip_addr")
    private java.lang.String ipAddr;

    /**
     * 关键字2       db_column: access_key2
     *
     * @Length(max=500)
     */
    @Column("uuid")
    private java.lang.String uuid;

    @Column("hitCount")
    private Integer hitCount;
    @Column("maxScore")
    private java.lang.Double maxScore;
    //columns END
    
    private Product product;
    private UserFactory user;
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public java.lang.String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(java.lang.String accessKey) {
		this.accessKey = accessKey;
	}
	public java.lang.String getAccessText() {
		return accessText;
	}
	public void setAccessText(java.lang.String accessText) {
		this.accessText = accessText;
	}
	public java.lang.Integer getAccessType() {
		return accessType;
	}
	public void setAccessType(java.lang.Integer accessType) {
		this.accessType = accessType;
	}
	public java.util.Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}
	public java.lang.Long getProductId() {
		return productId;
	}
	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}
	public java.lang.String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(java.lang.String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public java.lang.String getUuid() {
		return uuid;
	}
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public UserFactory getUser() {
		return user;
	}
	public void setUser(UserFactory user) {
		this.user = user;
	}
	public Integer getHitCount() {
		return hitCount;
	}
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}
	public java.lang.Double getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(java.lang.Double maxScore) {
		this.maxScore = maxScore;
	}

}

