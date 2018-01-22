package com.wode.factory.company.util;
import java.util.Calendar;

public class SeasonUtil {

	/**
	 * 获取当前年度
	 * @return
	 */
    public static int getNowYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    /**
     * 获取当前季度
     * @return
     */
    public static int getNowSeason() {
		int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (nowMonth == 1 || nowMonth == 2 || nowMonth == 3) {
			return 1;
		} else if (nowMonth == 4 || nowMonth == 5 || nowMonth == 6) {
			return 2;
		} else if (nowMonth == 7 || nowMonth == 8 || nowMonth == 9) {
			return 3;
		} else {
			return 4;
		}
    }
    
    public static void main(String[] args){
        System.out.println(getNowYear());

        System.out.println(getNowSeason());
    }
}