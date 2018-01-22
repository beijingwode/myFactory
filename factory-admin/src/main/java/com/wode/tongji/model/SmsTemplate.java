package com.wode.tongji.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_sms_template")
public class SmsTemplate extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5706046558438290178L;
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
     * 标题       db_column: title
     * 
     * 
     * 
     */ 
    @Column("title")
    private java.lang.String title;
    /**
     * 短信内容 含可变参数       db_column: msg
     * 
     * 
     * 
     */ 
    @Column("msg")
    private java.lang.String msg;
    /**
     * 模板代码       db_column: code
     * 
     * 
     * 
     */ 
    @Column("code")
    private java.lang.String code;
    /**
     * 可变参数1 json 表达       db_column: param1
     * 
     * 
     * 
     */ 
    @Column("param1")
    private java.lang.String param1;
    /**
     * 可变参数2 json 表达       db_column: param2
     * 
     * 
     * 
     */ 
    @Column("param2")
    private java.lang.String param2;
    /**
     * 可变参数3 json 表达       db_column: param3
     * 
     * 
     * 
     */ 
    @Column("param3")
    private java.lang.String param3;
    /**
     * 可变参数4 json 表达       db_column: param4
     * 
     * 
     * 
     */ 
    @Column("param4")
    private java.lang.String param4;
    /**
     * 可变参数5 json 表达       db_column: param5
     * 
     * 
     * 
     */ 
    @Column("param5")
    private java.lang.String param5;
    /**
     * 可变参数6 json 表达       db_column: param6
     * 
     * 
     * 
     */ 
    @Column("param6")
    private java.lang.String param6;
    /**
     * 后台发送标致 1:后台发送\\2:可手动发送    db_column: auto_send
     * 
     * 
     * 
     */ 
    @Column("auto_send")
    private java.lang.String autoSend;
    /**
     * 签名 一般为我的福利       db_column: signature
     * 
     * 
     * 
     */ 
    @Column("signature")
    private java.lang.String signature;
    /**
     * 停用标志 0:正常使用 1:停止使用       db_column: stop_flg
     * 
     * 
     * 
     */ 
    @Column("stop_flg")
    private java.lang.String stopFlg;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Title",getTitle())
            .append("Msg",getMsg())
            .append("Code",getCode())
            .append("Param1",getParam1())
            .append("Param2",getParam2())
            .append("Param3",getParam3())
            .append("Param4",getParam4())
            .append("Param5",getParam5())
            .append("Param6",getParam6())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getMsg() {
		return msg;
	}

	public void setMsg(java.lang.String msg) {
		this.msg = msg;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getParam1() {
		return param1;
	}

	public void setParam1(java.lang.String param1) {
		this.param1 = param1;
	}

	public java.lang.String getParam2() {
		return param2;
	}

	public void setParam2(java.lang.String param2) {
		this.param2 = param2;
	}

	public java.lang.String getParam3() {
		return param3;
	}

	public void setParam3(java.lang.String param3) {
		this.param3 = param3;
	}

	public java.lang.String getParam4() {
		return param4;
	}

	public void setParam4(java.lang.String param4) {
		this.param4 = param4;
	}

	public java.lang.String getParam5() {
		return param5;
	}

	public void setParam5(java.lang.String param5) {
		this.param5 = param5;
	}

	public java.lang.String getParam6() {
		return param6;
	}

	public void setParam6(java.lang.String param6) {
		this.param6 = param6;
	}

	public java.lang.String getAutoSend() {
		return autoSend;
	}

	public void setAutoSend(java.lang.String autoSend) {
		this.autoSend = autoSend;
	}

	public java.lang.String getSignature() {
		return signature;
	}

	public void setSignature(java.lang.String signature) {
		this.signature = signature;
	}

	public java.lang.String getStopFlg() {
		return stopFlg;
	}

	public void setStopFlg(java.lang.String stopFlg) {
		this.stopFlg = stopFlg;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

