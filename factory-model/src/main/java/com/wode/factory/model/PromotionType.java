package com.wode.factory.model;

import java.util.Date;

/**
 * 促销类型
 * @author Liuc
 *
 */
public class PromotionType {
	// ID
    private Integer id;
    // 促销类型名称
    private String name;
    // 促销类型创建时间
    private Date creatTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}