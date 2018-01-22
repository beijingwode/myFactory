package com.wode.factory.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Shop;
import com.wode.factory.model.UserFactory;

public class GroupOrdersVo extends GroupOrders implements Serializable {
	private static final long serialVersionUID = 8725336396811019936L;
	private List<GroupSuborder> groupSuborderList;
	private List<GroupSuborderitem> groupSuborderitemList;
	private GroupBuy groupBuy;
	//用户头像
	private String userAvatar;
	//用户昵称
	private String commanderName;
	//用户手机号
	private String phoneNum;
	//是否是团长
	private Integer isLadder;
	//节省商品金额
	private BigDecimal saveProductAmonut;
	//团共省金额
	private BigDecimal groupSaveAmonut;
	
	public Integer getIsLadder() {
		return isLadder;
	}
	public void setIsLadder(Integer isLadder) {
		this.isLadder = isLadder;
	}
	public BigDecimal getGroupSaveAmonut() {
		return groupSaveAmonut;
	}
	public void setGroupSaveAmonut(BigDecimal groupSaveAmonut) {
		this.groupSaveAmonut = groupSaveAmonut;
	}
	public BigDecimal getSaveProductAmonut() {
		return saveProductAmonut;
	}
	public void setSaveProductAmonut(BigDecimal saveProductAmonut) {
		this.saveProductAmonut = saveProductAmonut;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public String getCommanderName() {
		return commanderName;
	}
	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public List<GroupSuborder> getGroupSuborderList() {
		return groupSuborderList;
	}
	public void setGroupSuborderList(List<GroupSuborder> groupSuborderList) {
		this.groupSuborderList = groupSuborderList;
	}
	public List<GroupSuborderitem> getGroupSuborderitemList() {
		return groupSuborderitemList;
	}
	public void setGroupSuborderitemList(List<GroupSuborderitem> groupSuborderitemList) {
		this.groupSuborderitemList = groupSuborderitemList;
	}
	
	public GroupBuy getGroupBuy() {
		return groupBuy;
	}
	public void setGroupBuy(GroupBuy groupBuy) {
		this.groupBuy = groupBuy;
	}
}
