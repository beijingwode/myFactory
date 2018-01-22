package com.wode.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 * 功能说明: 缓存管理
 * 日期:	2015年5月21日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年5月21日
 * </pre>
 */
public class CacheManager {   
	
    private static Map cacheMap = new HashMap();   
  
    //判断是否存在一个缓存   
    public  static boolean hasCache(String key) {   
        return cacheMap.containsKey(key);   
    }   
    
    public  static void add(String key) {   
         cacheMap.put(key, new Date());
    }   
  
  
    //清除所有缓存   
    public  static void clearAll() {   
        cacheMap.clear();   
    }   
  
    //清除指定的缓存   
    public  static void clearOnly(String key) {   
        cacheMap.remove(key);   
    }   
  
}