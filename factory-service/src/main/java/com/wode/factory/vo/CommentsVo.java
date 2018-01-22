package com.wode.factory.vo;

import java.io.Serializable;
import java.util.List;

import com.wode.factory.model.Comments;

public class CommentsVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4671365152842179089L;
	
	private String attributeJson;
	private Integer commentDegree;
	private Long creatTime;
	private String creatTimeString;
	private String fullName;
	private Integer num;
	private String pic;
	private Double price;
	private Long productId;
	private Long replayId;
	private Double star1;
	private Double star2;
	private Double star3;
	private Integer status;
	private Long subOrderItemId;
	private String subOrderid;
	private Long supplyid;
	private String text;
	private Long userId;
	private String userNickName;
	private Integer userLevel;
	private String shopLink;
	private String avatar;
	private List images;
	public String getAttributeJson() {
		return attributeJson;
	}
	public void setAttributeJson(String attributeJson) {
		this.attributeJson = attributeJson;
	}
	public Integer getCommentDegree() {
		return commentDegree;
	}
	public void setCommentDegree(Integer commentDegree) {
		this.commentDegree = commentDegree;
	}
	public Long getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Long creatTime) {
		this.creatTime = creatTime;
	}
	public String getCreatTimeString() {
		return creatTimeString;
	}
	public void setCreatTimeString(String creatTimeString) {
		this.creatTimeString = creatTimeString;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getReplayId() {
		return replayId;
	}
	public void setReplayId(Long replayId) {
		this.replayId = replayId;
	}
	public Double getStar1() {
		return star1;
	}
	public void setStar1(Double star1) {
		this.star1 = star1;
	}
	public Double getStar2() {
		return star2;
	}
	public void setStar2(Double star2) {
		this.star2 = star2;
	}
	public Double getStar3() {
		return star3;
	}
	public void setStar3(Double star3) {
		this.star3 = star3;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getSubOrderItemId() {
		return subOrderItemId;
	}
	public void setSubOrderItemId(Long subOrderItemId) {
		this.subOrderItemId = subOrderItemId;
	}
	public String getSubOrderid() {
		return subOrderid;
	}
	public void setSubOrderid(String subOrderid) {
		this.subOrderid = subOrderid;
	}
	public Long getSupplyid() {
		return supplyid;
	}
	public void setSupplyid(Long supplyid) {
		this.supplyid = supplyid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	
	public String getShopLink() {
		return shopLink;
	}
	public void setShopLink(String shopLink) {
		this.shopLink = shopLink;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public static CommentsVo CreateByComment(Comments comments) {
		CommentsVo obj = new CommentsVo();
		obj.setAttributeJson(comments.getAttributeJson());
		obj.setCommentDegree(comments.getCommentDegree());
		obj.setCreatTime(comments.getCreatTime()==null?null:comments.getCreatTime().getTime());
		obj.setCreatTimeString(comments.getCreatTimeString());
		obj.setFullName(comments.getProduct()==null?null:comments.getProduct().getFullName());
		obj.setNum(comments.getSuborderitem()==null?1:comments.getSuborderitem().getNumber());
		obj.setPic(comments.getPic());
		obj.setPrice(comments.getSuborderitem()==null?0D:comments.getSuborderitem().getPrice().doubleValue());
		obj.setProductId(comments.getProductId());
		obj.setReplayId(comments.getReplayId());
		obj.setStar1(comments.getStar1().doubleValue());
		obj.setStar2(comments.getStar2().doubleValue());
		obj.setStar3(comments.getStar3().doubleValue());
		obj.setStatus(comments.getStatus());
		obj.setSubOrderItemId(comments.getSubOrderItemId());
		obj.setSubOrderid(comments.getSubOrderid());
		obj.setSupplyid(comments.getSupplyid());
		obj.setText(comments.getText());
		obj.setUserId(comments.getUserId());
		if("1".equals(comments.getAnonymous())){
			obj.setUserNickName("***");
		} else {
			if(comments.getUser()!=null) {
				obj.setUserNickName(comments.getUser().getNickName());
				obj.setShopLink(comments.getUser().getShopLink());
				obj.setAvatar(comments.getUser().getAvatar());
			}
		}
		obj.setUserLevel(comments.getUser()==null?null:comments.getUser().getUserLevel());
		obj.setImages(comments.getImages());
		return obj;
	}
	public List getImages() {
		return images;
	}
	public void setImages(List images) {
		this.images = images;
	}
}
