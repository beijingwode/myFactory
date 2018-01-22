package com.wode.factory.supplier.open.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.OpenApiUtils;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.OpenRequestBase;
import com.wode.factory.model.OpenResponse;
import com.wode.factory.supplier.model.SupplierAppSecurity;
import com.wode.factory.supplier.service.SupplierAppSecurityService;

@Controller
public class OpenController extends BaseController {
	
	@Autowired
	private SupplierAppSecurityService supplierAppSecurityService;
	
	private static Logger logger = LoggerFactory.getLogger(OpenController.class);
	
	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	
	@RequestMapping(value="open")
	public Object open(HttpServletRequest request,HttpServletResponse response,OpenRequestBase openRequestBase){
		Map map = getMap(request.getParameterMap());
		if(null == map || map.isEmpty()){
			return "redirect:/develop_doc_html/develop_doc11.html";
		}
		//校验签名
		OpenResponse result = checkSign(request,map );
		if(null != result && !result.isSuccess()){
			return "forward:/open/error?message="+result.getMessage();
		}
		if(!StringUtils.isEmpty(openRequestBase.getMethod())){			
			logger.info("url is /open/" + openRequestBase.getMethod().replace(".", "/")+ "?supplierId="+ result.getData() );
			return "forward:/open/" + openRequestBase.getMethod().replace(".", "/")+ "?supplierId="+ result.getData() ;
		}
		return null;
	}
	@RequestMapping(value="open/doc")
	public Object doc(HttpServletRequest request,HttpServletResponse response,OpenRequestBase openRequestBase){
		return "redirect:/develop_doc_html/develop_doc11.html";
	}
	
	@RequestMapping(value="open/error")
	@ResponseBody
	public Object error(HttpServletRequest request,HttpServletResponse response,OpenResponse openResponse){
		return openResponse;
	}
	
	//校验签名
	private OpenResponse checkSign(HttpServletRequest request,Map map){
		
		if(null == MapUtils.getLong(map, "appid")){
			return OpenResponse.fail("appid不能为空");
		}
		
		if(StringUtils.isEmpty(MapUtils.getString(map, "method"))){
			return OpenResponse.fail("method不能为空");
		}
		if(StringUtils.isEmpty( MapUtils.getString(map, "timestamp"))){
			return OpenResponse.fail("timestamp不能为空");
		}else{
			try {
				//比对时间戳
				Date timestamp =TimeUtil.strToDate(MapUtils.getString(map, "timestamp"));
				Date now = new Date();
				long cha = now.getTime() - timestamp.getTime();
				if((cha / 1000 / 60) > 10){
					return OpenResponse.fail("timestamp超时");
				}
			} catch (Exception e) {
				return OpenResponse.fail("timestamp不合法");
			}
		}
		if(StringUtils.isEmpty(MapUtils.getString(map, "sign"))){
			return OpenResponse.fail("sign不能为空");
		}
		SupplierAppSecurity supplierAppSecurity = supplierAppSecurityService.getById(MapUtils.getLong(map, "appid"));
		if(null == supplierAppSecurity){
			return OpenResponse.fail("功能尚未开放，请核对信息");
		}
		String sign = MapUtils.getString(map, "sign");
		map.remove("sign");
		String result = OpenApiUtils.formatParaMap(map, false, false,supplierAppSecurity.getSecret());
		logger.info("open.api.send.sign.is [{}] formatSign is [{}]",sign+"",result+"");
		if(!sign.equals(result)){
			return OpenResponse.fail("sign验证失败");
		}
		return OpenResponse.success(supplierAppSecurity.getSupplierId());
	}

	/**
	 * 拼接request中的参数
	 * @param map
	 * @return
	 */
	private Map<String,String> getMap(Map map){
		Map<String,String> result = new HashMap<String,String>();
		if(null != map){
		    Set keSet=map.entrySet();  
		    for(Iterator itr=keSet.iterator();itr.hasNext();){  
		        Map.Entry me=(Map.Entry)itr.next();  
		        Object ok=me.getKey();  
		        Object ov=me.getValue();  
		        String[] value=new String[1];  
		        if(ov instanceof String[]){  
		            value=(String[])ov;  
		        }else{  
		            value[0]=ov.toString();  
		        }  
		        result.put(""+ok, ""+value[0]);
		      }  
		}
		return result;
	}
}
