//Powered By if, Since 2014 - 2020

package com.wode.tongji.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wode.common.base.BaseEntity;

/**
 * 
 * @author 
 */

@Table(name="sys_task_definition")
public class TaskDefinition   implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(generator="JDBC")
    private Long id; //id <主键id>
	
    private String beanClass; //bean_class <>

	
    private String beanName; //bean_name <>

	
    private String cron; //cron <>

	
    private String description; //description <>

	
    private Boolean isStart; //is_start <>

	
    private String methodName; //method_name <>

	
    private String name; //name <>
  
    private Date creatTime;
    
    @Column(name = "last_excute_time")
    private Date lastExcuteTime;
    
	
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

	public Date getLastExcuteTime() {
		return lastExcuteTime;
	}

	public void setLastExcuteTime(Date lastExcuteTime) {
		this.lastExcuteTime = lastExcuteTime;
	}


}
