package com.wode.factory.model.enums;

public enum OrderStatus {
	PAYMENT("PAYMENT",0),//未支付
	PAY("PAY",1),//已支付
	SEND("SEND",2),//已发货
	RETURN_ING("RETURN_ING",3),//退货退款申请中
	RECEIVE("RECEIVE",4),//已收货
	REFUND_ING("REFUND_ING",5),//仅退款申请中
	RETURN_END("RETURN_END",11),//已退货退款完毕
	REFUND_END("REFUND_END",12),//已仅退款完毕
	COLES("COLES",-1),//已关闭
	AGREE_RETURN("AGREE_RETURN",13),//同意退货
	REFUSE_RETURN("REFUSE_RETURN",14),//拒绝退货
	AGREE_REFUND("AGREE_REFUND",15),//同意退款
	REFUSE_REFUND("REFUSE_REFUND",16) //拒绝退款
	;
	private String name;
	private Integer value;
	
	
	private OrderStatus(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	// 普通方法
    public static String getName(Integer value) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static Integer getValue(String name) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.getName().equals(name) ) {
                return c.value;
            }
        }
        return null;
    }


}
