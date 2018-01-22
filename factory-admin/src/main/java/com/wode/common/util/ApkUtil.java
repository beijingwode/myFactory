package com.wode.common.util;
import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.AXmlResourceParser;

import com.wode.tongji.model.ApkInfo;

public class ApkUtil {
	
	public static ApkInfo getApkInfo(File apkFile) {
	    ZipFile zipFile;
	    ApkInfo apkInfo = new ApkInfo();
	    try {
	        zipFile = new ZipFile(apkFile);
	        Enumeration enumeration = zipFile.entries();
	        ZipEntry zipEntry = zipFile.getEntry(("AndroidManifest.xml"));
	        if(!StringUtils.isNullOrEmpty(zipEntry)){
	            AXmlResourceParser parser = new AXmlResourceParser();
	            parser.open(zipFile.getInputStream(zipEntry));
	            boolean flag = true;
	            while (flag) {
	                int type = parser.next();
	                if (type == XmlPullParser.START_TAG) {
	                    int count = parser.getAttributeCount();
	                    for (int i = 0; i < count; i++) {
	                        String name = parser.getAttributeName(i);
	                        String value = parser.getAttributeValue(i);
	                        if(value.contains("MAIN")){
	                        	flag = false;
	                            break;
	                        }else if("name".equals(name)){
		                    	break;
		                    }else{
		                        if(name.equals("versionCode")){
		                        	apkInfo.setVersionCode(value);
		                        }
		                        if (("versionName").equals(name)) {
		                        	apkInfo.setVersionName(value);
		                        }
		                        if(("package").equals(name)){
		                        	apkInfo.setPackageName(value);
		                        }
		                        if(("minSdkVersion").equals(name)){
		                        	apkInfo.setMinSdkVersion(value);
		                        }
		                        if(("targetSdkVersion").equals(name)){
		                        	apkInfo.setTargetSdkVersion(value);
		                        }
		                    }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	    }
	    return apkInfo;
	}
	
}