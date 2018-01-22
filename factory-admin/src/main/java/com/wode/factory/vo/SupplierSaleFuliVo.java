
package com.wode.factory.vo;


import java.math.BigDecimal;

public class SupplierSaleFuliVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6051479369256535166L;
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	private java.lang.Long supplierId;
    /**
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	//columns END
	
	private BigDecimal fuli;
	public java.lang.Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}
	public BigDecimal getFuli() {
		return fuli;
	}
	public void setFuli(BigDecimal fuli) {
		this.fuli = fuli;
	}	
}

