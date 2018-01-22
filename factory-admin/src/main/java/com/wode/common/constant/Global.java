package com.wode.common.constant;

import com.wode.common.util.DateUtils;
import com.wode.common.util.SysConstant;


public class Global {
	
	/**
	 *  下载的路径
	* @param groupFile 分组文件夹名
	 */
	public static String getDownloadRootPath(String groupFile){
		String dateFile = DateUtils.getDate();
		StringBuilder builder = new StringBuilder();
		builder.append(SysConstant.getValue("download.rootPath")+"/");
		builder.append(groupFile+"/");
		builder.append(dateFile+"/");
		return builder.toString();
	}
	
	public static String getUploadRootPath(String groupFile){
		String dateFile = DateUtils.getDate();
		StringBuilder builder = new StringBuilder();
		builder.append(SysConstant.getValue("upload.rootPath")+"/");
		builder.append(groupFile+"/");
		builder.append(dateFile+"/");
		return builder.toString();
	}
	
}
