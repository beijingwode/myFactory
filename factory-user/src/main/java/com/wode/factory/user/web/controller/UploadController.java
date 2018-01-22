package com.wode.factory.user.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wode.common.constant.UserConstant;
import com.wode.common.util.ActResult;
import com.wode.common.util.FileUtils;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.user.util.Constant;

@Controller
@RequestMapping("upload")
public class UploadController {

	private static Logger logger = LoggerFactory.getLogger(UploadController.class);

	static UploadService upload = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	
	@RequestMapping("/pic")
	@ResponseBody
	public ActResult upload(HttpServletRequest request, String keepFilename) throws IOException {
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String referer = request.getHeader("Referer");
		logger.debug(referer);
		UserFactory user = null;
		Object us = request.getSession().getAttribute(UserConstant.USER_SESSION);
		if (us != null) {
			user = (UserFactory) us;
		}

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

		List flist = new ArrayList();
		result.setData(flist);

		for (String fname : map.keySet()) {
			// 获得文件：
			MultipartFile file = multipartRequest.getFile(fname);
			// 获得文件名：
			String filename = file.getOriginalFilename();

			String type = filename.substring(filename.lastIndexOf('.'));
			ActResult<List<String>> as1;
			try {
				String picTYpe = FileUtils.getPicType(file.getInputStream());
				if (picTYpe == null) {
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					cf.getFileItem().delete();
					logger.error(user + " upload " + filename + ", real type error");
					result.setMsg("文件格式不支持");
					result.setSuccess(false);
					return result;
				}

				if (user == null) {
					as1 = upload.uploadPic(file.getInputStream(), file.getSize(), type, "");
				} else {
					if (StringUtils.isEmpty(keepFilename)) {
						as1 = upload.uploadPic(file.getInputStream(), file.getSize(), type, "");
					} else {
						as1 = upload.uploadPic(file.getInputStream(), file.getSize(), type, "");
						//as1 = upservice.uploadPicKeepName(file.getInputStream(), file.getSize(), keepFilename + type, true, user.getId() + "");
					}
				}

				if (!as1.isSuccess()) {
					return as1;
				} else {
					flist.add(as1.getData().get(0));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (flist == null) {
			result.setMsg("没有上传文件");
			result.setSuccess(false);
		}

		return result;
	}

}
