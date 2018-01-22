package com.wode.api.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wode.common.util.ActResult;
import com.wode.common.util.FileUtils;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.user.util.Constant;

@Controller
@RequestMapping("upload")
public class UploadController {

	static UploadService upservice = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	
//	private static Logger logger = LoggerFactory.getLogger(UploadController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/pic")
	@ResponseBody
	public ActResult upload(HttpServletRequest request) throws IOException {
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		ActResult result = new ActResult();
		Map<String, MultipartFile> map = multipartRequest.getFileMap();
		Long size = 0l;
		for (String key : map.keySet()) {
			size = map.get(key).getSize();
			if (size > 2097152) {
				result.setSuccess(false);
				result.setMsg("图片大小不能超过2M");
				return result;
			}
		}

		List<String> flist = new ArrayList<String>();
		result.setData(flist);

		for (String fname : map.keySet()) {
			// 获得文件：
			MultipartFile file = multipartRequest.getFile(fname);
			// 获得文件名：
			String filename = file.getOriginalFilename();

			String type = filename.substring(filename.lastIndexOf('.'));

			try {
				String picTYpe = FileUtils.getPicType(file.getInputStream());
				if (picTYpe == null) {
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					cf.getFileItem().delete();
					result.setMsg("文件格式不支持");
					result.setSuccess(false);
					return result;
				}

				ActResult<List<String>> as2 = upservice.uploadPic(file.getInputStream(), file.getSize(), type, "");

				if (!as2.isSuccess()) {
					return as2;
				} else {
					flist.add(as2.getData().get(0));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (flist == null || flist.size()==0) {
			result.setMsg("没有上传文件");
			result.setSuccess(false);
		}

		return result;
	}

}
