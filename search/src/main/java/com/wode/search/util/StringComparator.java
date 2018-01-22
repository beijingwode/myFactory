package com.wode.search.util;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bing King on 2015/4/2.
 * 用来排序价格区间字段
 */
public class StringComparator implements Comparator<String>{

    public int compare(String s1, String s2) {
        Object l1 = getLongFromString(s1);
        Object l2 = getLongFromString(s2);
        if(l1 instanceof String || l2 instanceof  String) {
            return (l1.toString()).compareTo(l2.toString());
        } else {
            return (Long.valueOf(l1.toString())).compareTo(Long.valueOf(l2.toString()));
        }
    }

    private Object getLongFromString(String s) {
        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return Long.valueOf(matcher.group());
        }
        return s;
    }

    public static void main(String[] s) {
        StringComparator sc = new StringComparator();
        System.out.println(sc.getLongFromString("%234100-1000"));
    }
}
