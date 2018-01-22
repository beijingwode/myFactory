package com.wode.factory.interpreter;

import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.model.ThirdPriceVo;


public abstract class Expression {
	protected String html;
	protected String url;
	
    // 解释器 
    public void Interpret(ThirdPriceContext context) 
    { 
    	ExcuteUrl(context.getObj());
        if (StringUtils.isEmpty(url)) 
        { 
            return; 
        } 
        else 
        {
        	if(url.startsWith("https://")) {
        		html = HttpClientUtil.sendHttpRequest("get", url, null);
        	} else {
    			html = HttpClientUtil.sendHttpsRequest("get", url, null);        		
        	}
			if(StringUtils.isEmpty(html)) {
				context.getObj().setUrlStatus(-1);
				return;
			}
        	ThirdPriceVo vo= new ThirdPriceVo();
        	//解释内容文本并形成历史信
    		ExcutePrice(vo);
    		context.setVo(vo);

			if(vo==null || vo.getPrice()==null) {
				context.getObj().setUrlStatus(-2);
			} else {
				context.getObj().setLastPrice(vo.getPrice());
			}
			
        } 
    } 
	
    // 解释公司
	protected abstract void ExcutePrice(ThirdPriceVo vo);
    // 解释公司
	protected abstract void ExcuteUrl(ProductThirdPrice obj);
}
