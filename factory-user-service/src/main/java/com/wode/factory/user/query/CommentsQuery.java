/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class CommentsQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

    /**
     * id
     */
    private Long id;
    /**
     * orderid
     */
    private String subOrderid;
    /**
     * user_id
     */
    private Long userId;
    /**
     * supplyid
     */
    private Long supplyid;
    /**
     * 商品评级
     */
    private Integer star1;
    /**
     * 服务评级
     */
    private Integer star2;
    /**
     * 物流评级
     */
    private Integer star3;
    /**
     * 评论内容
     */
    private String text;
    /**
     * 图片
     */
    private String pic;
    /**
     * creat_time
     */
    private java.util.Date creatTimeBegin;
    private java.util.Date creatTimeEnd;
    /**
     * replayId
     */
    private Long replayId;
    /**
     * productId
     */
    private Long productId;
    /**
     * status
     */
    private Integer status;
    /**
     * attributeJson
     */
    private String attributeJson;
    /**
     * 评论度：1：差评；3中评；5好评
     */
    private Integer commentDegree;
    //评论标识
    private Boolean commentFlag;

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getSubOrderid() {
        return subOrderid;
    }

    public void setSubOrderid(String subOrderid) {
        this.subOrderid = subOrderid;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getSupplyid() {
        return this.supplyid;
    }

    public void setSupplyid(Long value) {
        this.supplyid = value;
    }

    public Integer getStar1() {
        return this.star1;
    }

    public void setStar1(Integer value) {
        this.star1 = value;
    }

    public Integer getStar2() {
        return this.star2;
    }

    public void setStar2(Integer value) {
        this.star2 = value;
    }

    public Integer getStar3() {
        return this.star3;
    }

    public void setStar3(Integer value) {
        this.star3 = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String value) {
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

    public Long getReplayId() {
        return this.replayId;
    }

    public void setReplayId(Long value) {
        this.replayId = value;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long value) {
        this.productId = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public String getAttributeJson() {
        return this.attributeJson;
    }

    public void setAttributeJson(String value) {
        this.attributeJson = value;
    }

    public Integer getCommentDegree() {
        return this.commentDegree;
    }

    public void setCommentDegree(Integer value) {
        this.commentDegree = value;
    }

    public Boolean getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(Boolean commentFlag) {
        this.commentFlag = commentFlag;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}

