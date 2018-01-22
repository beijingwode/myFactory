package com.wode.factory.model.enums;

/**
 * 退货物流状态
 * @author user
 *
 */
public enum RefundGoodsStatus {
	
	BUYER_SEND_GOODS("BUYER_SEND_GOODS",0),SELLER_SIGNED_GOODS("SELLER_SIGNED_GOODS",1);
	
	private String name;
	private Integer value;
	
	
	private RefundGoodsStatus(String name, Integer value) {
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
        for (RefundGoodsStatus c : RefundGoodsStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    
    // 普通方法
    public static Integer getValue(String name) {
        for (RefundGoodsStatus c : RefundGoodsStatus.values()) {
            if (c.getName().equals(name) ) {
                return c.value;
            }
        }
        return null;
    }

}
