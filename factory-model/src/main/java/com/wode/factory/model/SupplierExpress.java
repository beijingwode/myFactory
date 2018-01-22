package com.wode.factory.model;
/**
 *	常用快递公司信息
 */
public class SupplierExpress {
  private Long id;
  private Long expressId;
  private String name;
  private Long supplierId;

  
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Long getExpressId() {
	return expressId;
}
public void setExpressId(Long expressId) {
	this.expressId = expressId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Long getSupplierId() {
	return supplierId;
}
public void setSupplierId(Long supplierId) {
	this.supplierId = supplierId;
}

}
