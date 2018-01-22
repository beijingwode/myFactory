package com.wode.factory.user.vo;



/**
 * 代收点
 * @author user
 *
 */
public class CollectingPointVo{
    
    /**
      * 主键
      **/
	private Long id;
    /**
      * 对应【我的用户】编号 register.id
      **/
	private Long userid;
    /**
      * 代收点编号
      **/
	private String code;
    /**
      * 0 注册代派点 1 采集代派点
      **/
	private Integer type;
    /**
      * 代理品牌
      **/
	private String brand;
    /**
      * 代收点名称
      **/
	private String name;
    /**
      * 联系电话
      **/
	private String phone;
    /**
      * 代收点地址
      **/
	private String address;
    /**
      * 是否支持寄件服务 0 支持 1不支持
      **/
	private Integer send_service;
    /**
      * 是否支持代收服务  0支持 1不支持
      **/
	private Integer collect_service;
    /**
      * 位置坐标
      **/
	private Double lat;
    /**
      * 位置坐标
      **/
	private Double lng;
    /**
      * 发件量（快递员--》网点--》消费者）
      **/
	private Integer collect_exp;
    /**
      * 收件量（消费者--》网点--》快递员）
      **/
	private Integer send_exp;
    /**
      * 开始工作时间
      **/
	private String start_time;
    /**
      * 下班时间
      **/
	private String end_time;
    /**
      * 被赞次数
      **/
	private Integer praise_times;
    /**
      * 被收藏次数
      **/
	private Integer collect_times;
    /**
      * 库存状态 0：满仓 1：未满
      **/
	private Integer stock;
    /**
      * 创建时间
      **/
	private java.sql.Timestamp ctime;
    /**
      * 审核状态 0 未审核 1审核中 3审核通过 4审核未通过 
      **/
	private Integer apply_status;
    /**
      * t_courier_apply.id
      **/
	private Long apply_id;


	public CollectingPointVo() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getSend_service() {
		return this.send_service;
	}

	public void setSend_service(Integer send_service) {
		this.send_service = send_service;
	}
	public Integer getCollect_service() {
		return this.collect_service;
	}

	public void setCollect_service(Integer collect_service) {
		this.collect_service = collect_service;
	}
	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return this.lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Integer getCollect_exp() {
		return this.collect_exp;
	}

	public void setCollect_exp(Integer collect_exp) {
		this.collect_exp = collect_exp;
	}
	public Integer getSend_exp() {
		return this.send_exp;
	}

	public void setSend_exp(Integer send_exp) {
		this.send_exp = send_exp;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getPraise_times() {
		return this.praise_times;
	}

	public void setPraise_times(Integer praise_times) {
		this.praise_times = praise_times;
	}
	public Integer getCollect_times() {
		return this.collect_times;
	}

	public void setCollect_times(Integer collect_times) {
		this.collect_times = collect_times;
	}
	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public java.sql.Timestamp getCtime() {
		return this.ctime;
	}

	public void setCtime(java.sql.Timestamp ctime) {
		this.ctime = ctime;
	}
	public Integer getApply_status() {
		return this.apply_status;
	}

	public void setApply_status(Integer apply_status) {
		this.apply_status = apply_status;
	}
	public Long getApply_id() {
		return this.apply_id;
	}

	public void setApply_id(Long apply_id) {
		this.apply_id = apply_id;
	}

	

}