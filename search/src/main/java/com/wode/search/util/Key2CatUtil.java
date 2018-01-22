package com.wode.search.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class Key2CatUtil {
	private static Map<String,String> _convetMap;
	/**
	 * 根据关键字处理从新匹配分词
	 * @param s
	 * @return
	 */
	public static String convertUrl(String s) {
		// 无效参数
		if(s==null || s.length()==0) return s;
		String rtn = s.replace("cat=&", "");
		if(rtn.endsWith("cat=")) {
			rtn=rtn.substring(0, rtn.length()-4);
		}		
		if(rtn.length()==0) return rtn;
		
		// 已包含分类，不进行转换
		if(rtn.contains("cat=")) {
			return rtn;
		}
		try {
			// 字符转换
			rtn = URLDecoder.decode(rtn, "UTF-8");
			int pKey = rtn.indexOf("key=");
			if(pKey>-1) {
				String key = rtn.substring(pKey+4);
				pKey = key.indexOf("&");
				if(pKey>-1) {
					key = key.substring(0,pKey);
				}
				if(key.startsWith("医疗")) return "key=/";
				String cat=getConvetCat(key);
				if(cat != null) {
					return rtn.replace("key="+key, cat);
				}
			}
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}
	private static String getConvetCat(String key) {
		return getConvetMap().get(key);
	}
	private static synchronized Map<String,String> getConvetMap() {
		if(_convetMap==null) {
			_convetMap = new HashMap<String, String>();
			_convetMap.put("包", "cat=70300,70400,70500");
			_convetMap.put("包包", "cat=70300,70400,70500");
			_convetMap.put("化妆品", "cat=60101,60102,60602,60603,60604,60605");
			_convetMap.put("洗护用品", "cat=60201,60202,60301,60302");
			_convetMap.put("洗护", "cat=60201,60202");
			_convetMap.put("套装", "cat=60105,60205,60305,60404,60608");
			_convetMap.put("个护健康", "cat=10400");
			_convetMap.put("油", "cat=110703");
			_convetMap.put("充电线", "cat=20203");
			_convetMap.put("面包粉", "cat=110701");
			_convetMap.put("雪花粉", "cat=110701");
			_convetMap.put("面粉", "cat=110701");
			_convetMap.put("低筋面粉", "cat=110701");
			_convetMap.put("奶糖", "cat=110307");
			_convetMap.put("椰汁", "cat=110600");
			_convetMap.put("礼包", "cat=110200,110300");
			_convetMap.put("变压器", "cat=40203");
			_convetMap.put("燃气灶", "cat=10109");
			_convetMap.put("烘焙", "cat=10300,40100");
			_convetMap.put("棋", "cat=121103,80511");
			_convetMap.put("宠物", "cat=40800");
			_convetMap.put("食品", "cat=110000");
			_convetMap.put("家用电器", "cat=10000");
			_convetMap.put("洗发护发", "cat=60200");
			_convetMap.put("手机", "cat=20101");
			_convetMap.put("面膜", "cat=60103");
			_convetMap.put("单鞋", "cat=70101");
			_convetMap.put("休闲鞋", "cat=70102,70201,70202");
			_convetMap.put("电视机", "cat=10101");
			_convetMap.put("电视", "cat=10101");
			_convetMap.put("电脑", "cat=30100");
			_convetMap.put("运动户外", "cat=80000");
			_convetMap.put("男装冬", "cat=50200&key=冬");
			_convetMap.put("童装", "cat=121000");
			_convetMap.put("无线鼠标", "cat=30301&key=无线");
			_convetMap.put("黄酱", "cat=110704&key=酱");
			_convetMap.put("首饰", "cat=50500");
			_convetMap.put("行车记录仪", "cat=90203");
			_convetMap.put("保健洗脚", "cat=100500");
			_convetMap.put("沙发", "cat=40409&key=沙发");
			_convetMap.put("热水器", "cat=10110");
			_convetMap.put("家纺", "cat=40300");
			_convetMap.put("内增高女鞋", "cat=70000,70100&key=内增高");
			_convetMap.put("登山鞋", "cat=80612");
			_convetMap.put("呢子大衣", "cat=50218");
			_convetMap.put("相机", "cat=20301,20302");
			_convetMap.put("时尚女鞋", "cat=70100");
			_convetMap.put("精品男包", "cat=70400");
			_convetMap.put("潮流女包", "cat=70300");
			_convetMap.put("户外鞋服", "cat=80600");
			_convetMap.put("蓝牙耳机", "cat=20202,20204,20501");
		}
		return _convetMap;
	}
}
