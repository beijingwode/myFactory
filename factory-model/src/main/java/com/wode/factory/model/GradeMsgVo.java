package com.wode.factory.model;

import java.util.List;

public class GradeMsgVo {

	private String gradeName;
	private Integer prizeGrade;
	private List<UserPrizeRecord> list;
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getPrizeGrade() {
		return prizeGrade;
	}
	public void setPrizeGrade(Integer prizeGrade) {
		this.prizeGrade = prizeGrade;
	}
	public List<UserPrizeRecord> getList() {
		return list;
	}
	public void setList(List<UserPrizeRecord> list) {
		this.list = list;
	}
	
	
}
