/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class CommentsQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** orderid */
	private java.lang.String orderid;
	/** user_id */
	private java.lang.Long userId;
	/** supplyid */
	private java.lang.Long supplyid;
	/** 商品评级 */
	private java.lang.Integer star1;
	/** 服务评级 */
	private java.lang.Integer star2;
	/** 物流评级 */
	private java.lang.Integer star3;
	/** 评论内容 */
	private java.lang.String text;
	/** 图片 */
	private java.lang.String pic;
	/** creat_time */
	private java.util.Date creatTimeBegin;
	private java.util.Date creatTimeEnd;
	/** replayId */
	private java.lang.Long replayId;
	/** productId */
	private java.lang.Long productId;
	/** status */
	private java.lang.Integer status;
	/** attributeJson */
	private java.lang.String attributeJson;
	/** 评论度：1：差评；3中评；5好评 */
	private java.lang.Integer commentDegree;
	/** subOrderItemId */
	private java.lang.Long subOrderItemId;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getSupplyid() {
		return this.supplyid;
	}
	
	public void setSupplyid(java.lang.Long value) {
		this.supplyid = value;
	}
	
	public java.lang.Integer getStar1() {
		return this.star1;
	}
	
	public void setStar1(java.lang.Integer value) {
		this.star1 = value;
	}
	
	public java.lang.Integer getStar2() {
		return this.star2;
	}
	
	public void setStar2(java.lang.Integer value) {
		this.star2 = value;
	}
	
	public java.lang.Integer getStar3() {
		return this.star3;
	}
	
	public void setStar3(java.lang.Integer value) {
		this.star3 = value;
	}
	
	public java.lang.String getText() {
		return this.text;
	}
	
	public void setText(java.lang.String value) {
		this.text = value;
	}
	
	public java.lang.String getPic() {
		return this.pic;
	}
	
	public void setPic(java.lang.String value) {
		this.pic = value;
	}
	
	public java.util.Date getCreatTimeBegin() {
		return this.creatTimeBegin;
	}
	
	public void setCreatTimeBegin(java.util.Date value) {
		this.creatTimeBegin = value;
	}	
	
	public java.util.Date getCreatTimeEnd() {
		return this.creatTimeEnd;
	}
	
	public void setCreatTimeEnd(java.util.Date value) {
		this.creatTimeEnd = value;
	}
	
	public java.lang.Long getReplayId() {
		return this.replayId;
	}
	
	public void setReplayId(java.lang.Long value) {
		this.replayId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.String getAttributeJson() {
		return this.attributeJson;
	}
	
	public void setAttributeJson(java.lang.String value) {
		this.attributeJson = value;
	}
	
	public java.lang.Integer getCommentDegree() {
		return this.commentDegree;
	}
	
	public void setCommentDegree(java.lang.Integer value) {
		this.commentDegree = value;
	}
	
	public java.lang.Long getSubOrderItemId() {
		return this.subOrderItemId;
	}
	
	public void setSubOrderItemId(java.lang.Long value) {
		this.subOrderItemId = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

