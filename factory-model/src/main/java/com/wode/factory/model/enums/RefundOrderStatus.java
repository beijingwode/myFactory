package com.wode.factory.model.enums;

/**
 * 退款单订单枚举类
 * @author user
 *
 */
public enum RefundOrderStatus {
	
	WAIT_SELLER_CONFIRM("WAIT_SELLER_CONFIRM",1),//提交申请
	SUPPLIER_REFUSE("SUPPLIER_REFUSE",4),//卖家拒绝
	SUPPLIER_AGREE("SUPPLIER_AGREE",5),//卖家同意
	REFUND_FINISHED("REFUND_FINISHED",10),//退款完毕
	REFUND_CLOSED("REFUND_CLOSED",3);//退款失败
	
	private String name;
	private Integer value;
	
	
	private RefundOrderStatus(String name, Integer value) {
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
        for (RefundOrderStatus c : RefundOrderStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static Integer getValue(String name) {
        for (RefundOrderStatus c : RefundOrderStatus.values()) {
            if (c.getName().equals(name) ) {
                return c.value;
            }
        }
        return null;
    }
}
