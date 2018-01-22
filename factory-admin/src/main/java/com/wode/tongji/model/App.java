package com.wode.tongji.model;


public class App{
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	private java.lang.Long id;
    /**
     * code       db_column: code  
     * 
     * 
     * @NotBlank @Length(max=100)
     */	
	private java.lang.String code;
    /**
     * 下载地址       db_column: url  
     * 
     * 
     * @NotBlank @Length(max=200)
     */	
	private java.lang.String url;
    /**
     * 版本       db_column: version  
     * 
     * 
     * @NotBlank @Length(max=50)
     */	
	private java.lang.String version;
    /**
     * 更新说明       db_column: introduce  
     * 
     * 
     * @Length(max=1000)
     */	
	private java.lang.String introduce;
    /**
     * 创建时间       db_column: creat_time  
     * 
     * 
     * @NotNull 
     */	
	private java.util.Date creatTime;
    /**
     * 下载次数       db_column: download_times  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long downloadTimes;
	
	public java.lang.Long getId() {
		return id;
	}
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.String getCode() {
		return code;
	}
	
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	
	public java.lang.String getUrl() {
		return url;
	}
	
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getVersion() {
		return version;
	}
	
	public void setVersion(java.lang.String version) {
		this.version = version;
	}
	
	public java.lang.String getIntroduce() {
		return introduce;
	}
	
	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}
	
	public java.util.Date getCreatTime() {
		return creatTime;
	}
	
	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}
	
	public java.lang.Long getDownloadTimes() {
		return downloadTimes;
	}
	
	public void setDownloadTimes(java.lang.Long downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
}

