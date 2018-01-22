package com.wode.factory.exception;

public class SupplierShardeLessException extends RuntimeException {
	public SupplierShardeLessException(String text) {
		super("企业共享券不足，请刷新后重试："
				+ "（" + text + "）。");
	}
}
