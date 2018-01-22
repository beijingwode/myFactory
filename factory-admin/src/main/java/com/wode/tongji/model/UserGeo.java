package com.wode.tongji.model;

import java.util.Date;

public class UserGeo {
    private String city;

    private Long registNumber;

    private Date updateTime;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Long getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(Long registNumber) {
        this.registNumber = registNumber;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}