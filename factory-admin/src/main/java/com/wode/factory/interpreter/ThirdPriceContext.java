package com.wode.factory.interpreter;

import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ThirdPriceVo;;

public class ThirdPriceContext {
	//
	//快递文本 json串
	private ProductThirdPrice obj;
	
	public ProductThirdPrice getObj() {
		return obj;
	}
	public void setObj(ProductThirdPrice obj) {
		this.obj = obj;
	}
	private ThirdPriceVo vo;
	public ThirdPriceVo getVo() {
		return vo;
	}
	public void setVo(ThirdPriceVo vo) {
		this.vo = vo;
	}
	
}
