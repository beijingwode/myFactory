package com.wode.search.util;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;

public class HitComparator implements Comparator<HashMap<String, Object>> {
	public static final int NAME_MATCH = 10000;
	public static final int SHOP_MATCH = 9000;
	public static final int SHOP_EC = 10;
	public static final int NO_MATCH = -1;
	private String searchKey;
	public HitComparator(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
		if(searchKey!=null && !"".equals(searchKey.trim()))
		{
			int o1Score = getScore(o1);
			int o2Score = getScore(o2);
			
			if(o1Score>o2Score) return -1;
			if(o1Score<o2Score) return 1;
		}
		int o1Level = getBrandLevel(o1);
		int o2Level = getBrandLevel(o2);

		if(o1Level<o2Level) return -1;
		if(o1Level>o2Level) return 1;
		
		Double o1p = (Double)o1.get("price");
		Double o2p = (Double)o2.get("price");

		Double o1f = (Double) o1.get("maxFucoin");
		Double o2f = (Double)o2.get("maxFucoin");
		
		if(o1p==null || o1f==null) {
			return 1;
		}
		if(o2p==null || o2f==null) {
			return -1;
		}
		
		Double o1z = o1f/o1p;
		Double o2z = o2f/o2p;
		
		if(o1z > o2z) return -1;
		if(o1z < o2z) return 1;
		return 0;
	}

	private int getScore(HashMap<String, Object> o){
		String name = (String)o.get("name"); 
		if(isEmpty(name) || !name.contains("<em>")) return SHOP_EC;
		if(isContainKey(name)) return NAME_MATCH;
		
		int matchName = getiMatchScore(name,NAME_MATCH);
		
		if(matchName>SHOP_MATCH) return matchName;
		
		String shopName = (String)o.get("shopName");
		if(isContainKey(shopName)) return SHOP_MATCH;
		
		int matchShop = getiMatchScore(shopName,SHOP_MATCH);
		
		
		return matchName>matchShop?matchName:matchShop;
	}

	private int getBrandLevel(HashMap<String, Object> o){
		
		Integer brandLevel = toInteger(o.get("brandLevel")); 
		if(brandLevel == null || brandLevel<1) return 10;
		return brandLevel;
	}
	
	private boolean isContainKey(String source) {
		return source.replace("<em>", "").replace("</em>", "").contains(searchKey);
	}

	private int getiMatchScore(String source,int fullScore) {
		int maxMactch = 0;
		String[] ary = source.split("<em>");
		if(ary.length < 1) return 0;
		for (String string : ary) {
			int i = string.indexOf("</em>");
			if(i>maxMactch) {
				maxMactch = i;
			}
		}
		return maxMactch*fullScore/searchKey.length();
	}
	
	private boolean isEmpty(String obj) {
		if(obj==null) return true;
		return "".equals(obj);
	}
	

    /**
	 * 获取Long型
	 * @param s
	 * @return
	 */
    public static Integer toInteger(Object s) {
    	if(s==null) {
    		return null;
    	} else if(s instanceof Long) {
    		return ((Long)s).intValue();
    	} else if (s instanceof Integer) {
    		return (Integer)s;
    	} else if (s instanceof Double) {
    		return ((Double)s).intValue();
    	} else if (s instanceof BigDecimal) {
    		return ((BigDecimal)s).intValue();
    	} else if (s instanceof String) {
    		if("".equals(s)) {
    			return null;
    		} else {
    			return new Integer((String)s);
    		}
    	} else {
    		return new Integer(s.toString());
    	}
    }
}
