package com.wode.factory.supplier.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.wode.common.util.JsonUtil;
import com.wode.factory.model.Area;

public class AreaUtils {

	private static ResourceBundle res = ResourceBundle.getBundle("application");
	//快递接口Domain
	public static String filePath = res.getString("area.json.file");
	
	private static Map<Integer, Area> mapChina;
	private static Map<String, Integer> mapCity;

	private static void initMap() {
		mapChina = new HashMap<Integer, Area>();
		mapCity = new HashMap<String, Integer>();
		try {
			String configContent = readFile();
			List<Area> ret=JsonUtil.getList(configContent, Area.class);
			for (Area area : ret) {
				mapChina.put(area.getCode(), area);
				mapCity.put(area.getName().replace(" ",""), area.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Area getArea(Integer code) {
		if(mapChina == null) {
			initMap();
		}
		return mapChina.get(code);
	}

	public static Integer getACode(String city) {
		if(mapCity == null) {
			initMap();
		}
		return mapCity.get(city);
	}
	private static Integer[] roots={110000,120000,130000,140000,150000,210000,220000,230000,310000,320000,330000,340000,350000,360000,370000,410000,420000,430000,440000,450000,460000,500000,510000,520000,530000,540000,610000,620000,630000,640000,650000,710000,810000,820000,990000};

	//获取一级行政区域
	public static List<Area> getRootArea() {
		List<Area> ary=new ArrayList<Area>();
		for(int i=0;i<roots.length;i++) {
			ary.add(getArea(roots[i]));
		}
		return ary;
	}
	
	/**
	 * 获取行政区域
	 * @param grade 行政区域级别
	 * @param fatherCd 上级行政取与代码
	 * @returns {Array}
	 */
	public static List<Area> getArea90(Integer grade,Integer fatherCd) {
		switch(grade) {
		case 2:
			return getLevel2(fatherCd);
		case 3:
			return getLevel3(fatherCd);
		default:
			return getRootArea();
		}
	}
	
	/**
	 * 获取行政区域
	 * @param fatherCd 上级行政区域代码
	 * @returns {Array}
	 */
	public static List<Area> getLevel2(Integer father){
		List<Area> ary=new ArrayList<Area>();
		if(father!=null && father>=100000 && father % 10000==0) {
			for(int i=1;i<91;i++) {
				Area o=getArea(father+i*100);
				if(o != null) {
					ary.add(o);
				}
			}
		}
		return ary;
	}

	/**
	 * 获取行政区域
	 * @param fatherCd 上级行政区域代码
	 * @returns {Array}
	 */
	public static List<Area> getLevel3(Integer father){
		List<Area> ary=new ArrayList<Area>();
		if(father!=null && father>=100000 && father % 100==0) {
			for(int i=1;i<91;i++) {
				Area o=getArea(father+i);
				if(o != null) {
					ary.add(o);
				}
			}
		}
			return ary;
	}

	private static String readFile() throws IOException {
		StringBuilder builder = new StringBuilder();

		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
			BufferedReader bfReader = new BufferedReader(reader);

			String tmpContent = null;

			while ((tmpContent = bfReader.readLine()) != null) {
				builder.append(tmpContent);
			}

			bfReader.close();
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}

		return filter(builder.toString());
	}

	private static String filter(String input) {
		return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
	}
}
