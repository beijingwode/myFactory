package com.wode.factory.interpreter;

import com.wode.common.util.NumberUtil;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ThirdPriceVo;

public class JdExpression extends Expression {

	private final static String priceid="\"p\":\"";
	private final static String mark="";
	private final static String urlstart="/item/";
	private final static String urlend=".htm";
	private final static String end="\",\"";
	private final static String jdUrl = "http://p.3.cn/prices/get?type=1&area=1_72_2799&pdtk=&pduid=1960919890&pdpin=&pdbp=0&skuid=J_XXXXXXXX&callback=cnp";
	
	@Override
	protected void ExcutePrice(ThirdPriceVo vo) {
		int index = this.html.indexOf(priceid);
		if(index!=-1) {
			html=html.substring(index+priceid.length());

			//html=html.substring(html.indexOf(mark)+mark.length());
			html=html.substring(0, html.indexOf(end));
			html=html.trim();
			vo.setPrice(NumberUtil.toBigDecimal(html));
		}
	}

	@Override
	protected void ExcuteUrl(ProductThirdPrice obj) {
		url = obj.getItemUrl();
		url = url.substring(url.indexOf(urlstart)+urlstart.length());
		url = url.substring(0,url.indexOf(urlend));
		url = jdUrl.replace("XXXXXXXX", url);
	}
	

}
