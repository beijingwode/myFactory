package com.wode.factory.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
@Table("t_user_device")
public class UserDevice extends BaseModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2319802637229731734L;

	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;

    /**
     * 用户id      db_column: user_id  
     * 
     */	
	@Column("user_id")
	private Long userId;

    /**
     * 设备id     db_column: device_id  
     * 
     */	
	@Column("device_id")
	private String deviceId;


    /**
     * 设备类型     db_column: device_type  
     * 
     */	
	@Column("device_type")
	private String deviceType;


    /**
     * 设备名称    db_column: device_name  
     * 
     */	
	@Column("device_name")
	private String deviceName;

    /**
     * 别名  db_column: asname  
     * 
     */	
	@Column("asname")
	private String asname;

    /**
     * 别名  db_column: channel_id  
     * 
     */	
	@Column("channel_id")
	private String channelId;

    /**
     * 状态 0:未登录/1:已登录  db_column: status  
     * 
     */	
	@Column("status")
	private String status;

    /**
     * 状态 0:未登录/1:已登录  db_column: status  
     * 
     */	
	@Column("update_time")
	private Date updateTime;

    /**
     * ticket  db_column: ticket  
     * 
     */	
	@Column("ticket")
	private String ticket;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAsname() {
		return asname;
	}

	public void setAsname(String asname) {
		this.asname = asname;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	//columns END
	
}