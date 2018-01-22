package com.wode.factory.model.enums;

/**
 * 售后类型
 * @author user
 *
 */
public enum RefundRefundType {
	
	REFUDN_FEE_ONLY("REFUDN_FEE_ONLY",1),//退款
	RETURN_OF_GOODS("RETURN_OF_GOODS",2);//退货退款
	
	
	private String name;
	private Integer value;
	
	
	private RefundRefundType(String name, Integer value) {
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
        for (RefundRefundType c : RefundRefundType.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static Integer getValue(String name) {
        for (RefundRefundType c : RefundRefundType.values()) {
            if (c.getName().equals(name) ) {
                return c.value;
            }
        }
        return null;
    }

}
