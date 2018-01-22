package com.wode.tongji.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_search_day_statistical")
public class SearchDayStatistical extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5452539312238031437L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Integer id;
    /**
     * 日期       db_column: day
     * 
     * 
     * 
     */ 
    @Column("day")
    private java.util.Date day;
    /**
     * 统计日期       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 搜素关键字       db_column: search_key
     * 
     * 
     * 
     */ 
    @Column("search_key")
    private java.lang.String searchKey;
    /**
     * 关键字搜索次数       db_column: search_cnt
     * 
     * 
     * 
     */ 
    @Column("search_cnt")
    private java.lang.Long searchCnt;
    /**
     * 关键字搜索次数       db_column: search_cnt
     * 
     * 
     * 
     */ 
    @Column("hits_cnt")
    private java.lang.Long hitsCnt;
    /**
     * 关键字搜索次数       db_column: search_cnt
     * 
     * 
     * 
     */ 
    @Column("avg_score")
    private java.lang.Double avgScore;
    /**
     * 其他1       db_column: else1_cnt
     * 
     * 
     * 
     */ 
    @Column("else1_cnt")
    private java.lang.Long else1Cnt;
    /**
     * 其他2       db_column: else2_cnt
     * 
     * 
     * 
     */ 
    @Column("else2_cnt")
    private java.lang.Long else2Cnt;
    /**
     * 其他3       db_column: else3_cnt
     * 
     * 
     * 
     */ 
    @Column("else3_cnt")
    private java.lang.Long else3Cnt;
    /**
     * 其他1       db_column: else1
     * 
     * 
     * 
     */ 
    @Column("else1")
    private java.lang.String else1;
    /**
     * 其他2       db_column: else2
     * 
     * 
     * 
     */ 
    @Column("else2")
    private java.lang.String else2;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Day",getDay())
            .append("CreateTime",getCreateTime())
            .append("SearchKey",getSearchKey())
            .append("SearchCnt",getSearchCnt())
            .append("Else1Cnt",getElse1Cnt())
            .append("Else2Cnt",getElse2Cnt())
            .append("Else3Cnt",getElse3Cnt())
            .append("Else1",getElse1())
            .append("Else2",getElse2())
            .toString();
    }

    public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.util.Date getDay() {
		return day;
	}

	public void setDay(java.util.Date day) {
		this.day = day;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(java.lang.String searchKey) {
		this.searchKey = searchKey;
	}

	public java.lang.Long getSearchCnt() {
		return searchCnt;
	}

	public void setSearchCnt(java.lang.Long searchCnt) {
		this.searchCnt = searchCnt;
	}

	public java.lang.Long getElse1Cnt() {
		return else1Cnt;
	}

	public void setElse1Cnt(java.lang.Long else1Cnt) {
		this.else1Cnt = else1Cnt;
	}

	public java.lang.Long getElse2Cnt() {
		return else2Cnt;
	}

	public void setElse2Cnt(java.lang.Long else2Cnt) {
		this.else2Cnt = else2Cnt;
	}

	public java.lang.Long getElse3Cnt() {
		return else3Cnt;
	}

	public void setElse3Cnt(java.lang.Long else3Cnt) {
		this.else3Cnt = else3Cnt;
	}

	public java.lang.String getElse1() {
		return else1;
	}

	public void setElse1(java.lang.String else1) {
		this.else1 = else1;
	}

	public java.lang.String getElse2() {
		return else2;
	}

	public void setElse2(java.lang.String else2) {
		this.else2 = else2;
	}

	public java.lang.Long getHitsCnt() {
		return hitsCnt;
	}

	public void setHitsCnt(java.lang.Long hitsCnt) {
		this.hitsCnt = hitsCnt;
	}

	public java.lang.Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(java.lang.Double avgScore) {
		this.avgScore = avgScore;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

