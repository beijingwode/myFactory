package com.wode.factory.model.enums;

/**
 * 退货退款订单状态
 * @author user
 *
 */
public enum ReturnOrderStatus {
	WAIT_SELLER_CONFIRM("WAIT_SELLER_CONFIRM",0),//申请退货
    SELLER_ARGEE("SELLER_ARGEE",2),//卖家同意退款
    SELLER_REFUSE("SELLER_REFUSE",3),//卖家拒绝退款
    BUYER_SEND("BUYER_SEND",4),//买家已经发货
    SELLER_RETURN("SELLER_RETURN",5),//卖家退款
    RETURN_SUCCESS("RETURN_SUCCESS",1),//退款成功
    RETURN_FAIL("RETURN_FAIL",-1),//退款失败
    RETURN_RECEIVE_REFUSE("RETURN_RECEIVE_REFUSE",6)//卖家收到货拒绝
    ;
	
	private ReturnOrderStatus(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	private String name;
	private Integer value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	// 普通方法
    public static String getName(Integer value) {
        for (ReturnOrderStatus c : ReturnOrderStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static Integer getValue(String name) {
        for (ReturnOrderStatus c : ReturnOrderStatus.values()) {
            if (c.getName().equals(name) ) {
                return c.value;
            }
        }
        return null;
    }
}
