package com.wode.factory.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;

import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyProduct;
import com.wode.factory.model.GroupBuyUser;

public class GroupBuyVo extends GroupBuy implements Serializable{

	/**
	 */
	private static final long serialVersionUID = -2484016160122336916L;
	
	

	/**
	 * 1:表示已参团 db_column: joinStatus
	 * 
	 * 
	 * @Length(max=255)
	 */
	@Column(value="joinStatus")
	private String joinStatus;

	private String userImImGroupId;
	private String userImGroupname;
	private GroupOrdersVo groupOrder;
	private List<GroupBuyProduct> groupBuyProductList;
	private List<GroupOrdersVo> groupOrdersList;
	// 最少节省运费
	private BigDecimal saveShippingFee;
	
	private String countDown;//限时倒计时
	
	
	
	public String getCountDown() {
		return countDown;
	}

	public void setCountDown(String countDown) {
		this.countDown = countDown;
	}

	public List<GroupOrdersVo> getGroupOrdersList() {
		return groupOrdersList;
	}

	public void setGroupOrdersList(List<GroupOrdersVo> groupOrdersList) {
		this.groupOrdersList = groupOrdersList;
	}

	public BigDecimal getSaveShippingFee() {
		return saveShippingFee;
	}

	public void setSaveShippingFee(BigDecimal saveShippingFee) {
		this.saveShippingFee = saveShippingFee;
	}
	public String getUserImImGroupId() {
		return userImImGroupId;
	}
	public void setUserImImGroupId(String userImImGroupId) {
		this.userImImGroupId = userImImGroupId;
	}
	public String getUserImGroupname() {
		return userImGroupname;
	}
	public void setUserImGroupname(String userImGroupname) {
		this.userImGroupname = userImGroupname;
	}
	public GroupOrdersVo getGroupOrder() {
		return groupOrder;
	}
	public void setGroupOrder(GroupOrdersVo groupOrder) {
		this.groupOrder = groupOrder;
	}
	
	public String getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}
	
	public List<GroupBuyProduct> getGroupBuyProductList() {
		return groupBuyProductList;
	}

	public void setGroupBuyProductList(List<GroupBuyProduct> groupBuyProductList) {
		this.groupBuyProductList = groupBuyProductList;
	}

	//团购人列表
	private List<GroupBuyUser> groupBuyUserList;
	
	@Deprecated
	public List<GroupBuyUser> getGroupBuyUserList() {
		return groupBuyUserList;
	}
	public void setGroupBuyUserList(List<GroupBuyUser> groupBuyUserList) {
		this.groupBuyUserList = groupBuyUserList;
	}
}
