package com.wode.factory.user.vo;

import com.wode.factory.model.Comments;
import com.wode.factory.model.Suborderitem;


public class SubOrderItemVo2 extends Suborderitem {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4134148111080579738L;
	private Long supplierId;	//商家ID
    private Long userId;	//商家ID

    private Double score; //评分
    private Long count; //评论总数
    
    private Comments comment;
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Comments getComment() {
		return comment;
	}
	public void setComment(Comments comment) {
		this.comment = comment;
	}
	
}
