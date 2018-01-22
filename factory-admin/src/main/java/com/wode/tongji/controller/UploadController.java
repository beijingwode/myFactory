package com.wode.tongji.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.ApkUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.tongji.model.ApkInfo;

@Controller
@RequestMapping("upload")
public class UploadController {

	static UploadService upservice = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	
	@RequestMapping("/apk")
	@ResponseBody
	public ActResult upload(HttpServletRequest request) throws IOException {
		ActResult result = new ActResult();
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> map = multipartRequest.getFileMap();
		
		List flist = new ArrayList();
		result.setData(flist);
		
		ApkInfo apkInfo = new ApkInfo();
		for (String fname : map.keySet()) {
			// 获得文件：
			MultipartFile mfile = multipartRequest.getFile(fname);
			//判断文件类型
			CommonsMultipartFile cf= (CommonsMultipartFile)mfile; 
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	        File f = fi.getStoreLocation();
	        apkInfo = new ApkUtil().getApkInfo(f);
			if(StringUtils.isNullOrEmpty(apkInfo.getPackageName())){
				result.setMsg("上传文件不正确");
				result.setSuccess(false);
				return result;
			}else{
				result.setMsg(apkInfo.getVersionName());
			}
			// 获得文件名：
			String filename = mfile.getOriginalFilename();
			String type = filename.substring(filename.lastIndexOf('.'));
			ActResult as1 = null;
			try {

				ActResult<List<String>> as = upservice.uploadPic(mfile.getInputStream(),mfile.getSize(), ".apk","");
				if (!as1.isSuccess()) {
					return as1;
				} else {
					flist.add(as.getData().get(0));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
