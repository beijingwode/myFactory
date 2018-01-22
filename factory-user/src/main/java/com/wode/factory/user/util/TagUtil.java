package com.wode.factory.user.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 自定义标签
 * @author Bing King
 *
 */
public class TagUtil {

	/**
	 * 解码URL
	 * @param url
	 * @return
	 */
	public static String decode(String url) {
		try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		return "";
	}

	/**
	 * 转码URL
	 * @param url
	 * @return
	 */
	public static String encode(String url) {
	    try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return "";
	}
}
