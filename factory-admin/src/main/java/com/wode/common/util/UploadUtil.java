package com.wode.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wode.common.constant.Constant;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;

public class UploadUtil {

    
    static UploadService uploadService = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
    
	public ActResult uploadImage(HttpServletRequest request)  { 
        // 转型为MultipartHttpRequest：   
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        
        ActResult result= new ActResult();
        Map<String,MultipartFile> map=multipartRequest.getFileMap();
        List flist=new ArrayList();
        result.setData(flist);
        
        for(String fname:map.keySet()){
        	// 获得文件： 
            MultipartFile file = multipartRequest.getFile(fname);   
            // 获得文件名：   
            String filename = file.getOriginalFilename();   
            String type=filename.substring(filename.lastIndexOf('.'));
			try {
				ActResult<List<String>> as1 =uploadService.uploadPic(file.getInputStream(), file.getSize(), type, "");
				if(!as1.isSuccess()){
	            	return as1;
	            }else{
	            	flist.add(as1.getData().get(0));
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
            
        }
         if(flist==null){
        	 result.setMsg("没有上传文件");
         	 result.setSuccess(false);
         }
        return result;
    }
}
