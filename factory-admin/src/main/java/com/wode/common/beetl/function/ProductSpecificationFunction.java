package com.wode.common.beetl.function;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.util.StringUtils;

@Component
public class ProductSpecificationFunction implements Function{

	
	@Override
	public Object call(Object[] params, Context ctx) {
		String sku = (String)params[0];
		if(StringUtils.isNullOrEmpty(sku))
			return null;
		
		JSONObject jsonObj = JSONObject.parseObject(sku);
		Map<String, String> map = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
			map.put(entry.getKey(), entry.getValue().toString());
		}
		return map;
	}

}
