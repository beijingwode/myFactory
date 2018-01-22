package com.wode.search.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlUtil {

	 public static Map<String, String> decodeParams(String s) {
	        Map<String, String> params = new LinkedHashMap<String, String>();
	        String name = null;
	        int pos = 0; // Beginning of the unprocessed region
	        int i;       // End of the unprocessed region
	        char c = 0;  // Current character
	        for (i = 0; i < s.length(); i++) {
	            c = s.charAt(i);
	            if (c == '=' && name == null) {
	                if (pos != i) {
	                    name = decodeComponent(s.substring(pos, i), Charset.forName("UTF-8"));
	                }
	                pos = i + 1;
	            } else if (c == '&') {
	                if (name == null && pos != i) {
	                    // We haven't seen an `=' so far but moved forward.
	                    // Must be a param of the form '&a&' so add it with
	                    // an empty value.
	                    addParam(params, decodeComponent(s.substring(pos, i), Charset.forName("UTF-8")), "");
	                } else if (name != null) {
	                    addParam(params, name, decodeComponent(s.substring(pos, i), Charset.forName("UTF-8")));
	                    name = null;
	                }
	                pos = i + 1;
	            }
	        }

	        if (pos != i) {  // Are there characters we haven't dealt with?
	            if (name == null) {     // Yes and we haven't seen any `='.
	                addParam(params, decodeComponent(s.substring(pos, i), Charset.forName("UTF-8")), "");
	            } else {                // Yes and this must be the last value.
	                addParam(params, name, decodeComponent(s.substring(pos, i), Charset.forName("UTF-8")));
	            }
	        } else if (name != null) {  // Have we seen a name without value?
	            addParam(params, name, "");
	        }

	        return params;
	    }

	    private static String decodeComponent(String s, Charset charset) {
	        if (s == null) {
	            return "";
	        }

	        try {
	            return URLDecoder.decode(s, charset.name());
	        } catch (Exception e) {
	            return s;
	        }
	    }

	    private static void addParam(Map<String, String> params, String name, String value) {
//	        String[] values = value.split(",");
//	        for(String val : values){
//	            params.put(name, val);
//	        }
            params.put(name, value);
	    }
}
