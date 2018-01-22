package com.wode.factory.interpreter;

import com.wode.common.util.NumberUtil;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ThirdPriceVo;

public class TmallExpression extends Expression {

	private final static String priceid="class=\"tm-price\"";
	private final static String mark=">";
	private final static String end="</";
	
	@Override
	protected void ExcutePrice(ThirdPriceVo vo) {
		int index = this.html.indexOf(priceid);
		if(index!=-1) {
			html=html.substring(index+priceid.length());

			html=html.substring(html.indexOf(mark)+mark.length());
			html=html.substring(0, html.indexOf(end));
			html=html.trim();
			vo.setPrice(NumberUtil.toBigDecimal(html));
		}
	}

	@Override
	protected void ExcuteUrl(ProductThirdPrice obj) {
		url=obj.getItemUrl();
	}
	

}
