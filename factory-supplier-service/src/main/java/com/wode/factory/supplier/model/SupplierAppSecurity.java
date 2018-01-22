package com.wode.factory.supplier.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_app_security")
public class SupplierAppSecurity extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6600015557636034117L;
	//columns START
    /**
     * app id 暂时默认商家id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 加密方式 1:秘钥加密/2:固定ip       db_column: security_type
     * 
     * 
     * 
     */ 
    @Column("security_type")
    private java.lang.String securityType;
    /**
     * 秘钥       db_column: secret
     * 
     * 
     * 
     */ 
    @Column("secret")
    private java.lang.String secret;
    /**
     * 客户端ip1(对接系统ip )       db_column: client_ip1
     * 
     * 
     * 
     */ 
    @Column("client_ip1")
    private java.lang.String clientIp1;
    /**
     * 客户端ip2       db_column: client_ip2
     * 
     * 
     * 
     */ 
    @Column("client_ip2")
    private java.lang.String clientIp2;
    /**
     * 客户端ip3       db_column: client_ip3
     * 
     * 
     * 
     */ 
    @Column("client_ip3")
    private java.lang.String clientIp3;
    /**
     * 客户端ip4       db_column: client_ip4
     * 
     * 
     * 
     */ 
    @Column("client_ip4")
    private java.lang.String clientIp4;
    /**
     * 客户端ip5       db_column: client_ip5
     * 
     * 
     * 
     */ 
    @Column("client_ip5")
    private java.lang.String clientIp5;

    //columns END

    
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("SecurityType",getSecurityType())
            .append("Secret",getSecret())
            .append("ClientIp1",getClientIp1())
            .append("ClientIp2",getClientIp2())
            .append("ClientIp3",getClientIp3())
            .append("ClientIp4",getClientIp4())
            .append("ClientIp5",getClientIp5())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(java.lang.String securityType) {
		this.securityType = securityType;
	}

	public java.lang.String getSecret() {
		return secret;
	}

	public void setSecret(java.lang.String secret) {
		this.secret = secret;
	}

	public java.lang.String getClientIp1() {
		return clientIp1;
	}

	public void setClientIp1(java.lang.String clientIp1) {
		this.clientIp1 = clientIp1;
	}

	public java.lang.String getClientIp2() {
		return clientIp2;
	}

	public void setClientIp2(java.lang.String clientIp2) {
		this.clientIp2 = clientIp2;
	}

	public java.lang.String getClientIp3() {
		return clientIp3;
	}

	public void setClientIp3(java.lang.String clientIp3) {
		this.clientIp3 = clientIp3;
	}

	public java.lang.String getClientIp4() {
		return clientIp4;
	}

	public void setClientIp4(java.lang.String clientIp4) {
		this.clientIp4 = clientIp4;
	}

	public java.lang.String getClientIp5() {
		return clientIp5;
	}

	public void setClientIp5(java.lang.String clientIp5) {
		this.clientIp5 = clientIp5;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

