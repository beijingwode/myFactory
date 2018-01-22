package com.wode.factory.supplier.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.FileUtils;
import com.wode.common.util.JsonUtil;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.util.ActionEnter;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("upload")
public class UploadController {


	private static Logger logger = LoggerFactory.getLogger(UploadController.class);
	@Resource(name = "maxUpLoadLimit")
	private Map<String, String> maxUpLoadLimit;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;

	static UploadService uploader = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	/**
	 * ueditor
	 **/
	@RequestMapping(value = "ueditConfig.json")
	@NoCheckLogin
	public void ueditConfig(HttpServletRequest request, HttpServletResponse response, @RequestParam String action) {
		String json = "";
		String rootPath = UploadController.class.getResource("/").getPath();
		json = new ActionEnter(request, rootPath).exec();
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ueditor
	 **/
	@RequestMapping(value = "uedit.json")
	@NoCheckLogin
	public void uedit(HttpServletRequest request, HttpServletResponse response, @RequestParam String action) {
		String json = "";
		if (action.startsWith("upload")) {
			ActResult ar = upload(request);
			if (ar.isSuccess()) {
				State state = new BaseState(true);
				JSONObject map = (JSONObject) ((List) ar.getData()).get(0);
				for (String key : map.keySet()) {
					if ("ofilename".equals(key)) {
						state.putInfo("original", map.getString(key));
						//state.putInfo("title", map.getString(key));
					} else {
						state.putInfo(key, map.getString(key));
					}
				}
				json = state.toJSONString();
			} else {
				json = "{\"state\": \"" + ar.getMsg() + "\"}";
			}

		} else {
			String rootPath = UploadController.class.getResource("/").getPath();
			json = new ActionEnter(request, rootPath).exec();
		}

		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@RequestMapping("/pic_ie.json")
	@NoCheckLogin
	public void uploadFromIE(HttpServletRequest request, HttpServletResponse response) {
		ActResult ret = upload(request);
		String str = JsonUtil.toJson(ret);
		response.setContentType("text/html;charset=UTF-8");//解决ie弹出下载框的提示
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(str);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@RequestMapping("/pic.json")
	@NoCheckLogin
	@ResponseBody
	public ActResult upload(HttpServletRequest request) {
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String referer = request.getHeader("Referer");
		logger.debug(referer);
		UserFactory user = UserInterceptor.getSessionUser(request,shopService);

		ActResult result = new ActResult();
		Map<String, MultipartFile> map = multipartRequest.getFileMap();
		List flist = new ArrayList();
		result.setData(flist);

		for (String fname : map.keySet()) {
			// 获得文件：
			MultipartFile file = multipartRequest.getFile(fname);
			//文件大小校验,flash客户端控制大小
			if (referer != null && !referer.endsWith(".swf")) {
				String page = referer.substring(referer.lastIndexOf("/") + 1);
				String max = maxUpLoadLimit.get(page);
				if (max != null) {
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					logger.debug(file.getName() + ":" + cf.getFileItem().getSize() / 1024 + "KB");
					if (cf.getFileItem().getSize() / 1024 > Integer.valueOf(max)) {
						result.setMsg("文件超过规定大小");
						result.setSuccess(false);
						return result;
					}

				}
			}

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
					as1 = uploader.uploadPic(file.getInputStream(), file.getSize(), type, "");
				} else {
					as1 = uploader.uploadPic(file.getInputStream(), file.getSize(), type, user.getId() + "");
				}

				if (!as1.isSuccess()) {
					return as1;
				} else {
					JSONObject jo = new JSONObject();
					jo.put("size", file.getBytes().length);
					jo.put("original",as1.getData());
					jo.put("ofilename", filename);
					jo.put("type", type);
					jo.put("size", file.getBytes().length);
					jo.put("url", "http://" + as1.getData().get(0));
					flist.add(jo);
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

	@RequestMapping("writeUploadResult")
	@ResponseBody
	@NoCheckLogin
	public ActResult writeUserResult(HttpServletRequest request,String data){
		ActResult as1= JsonUtil.getObject(data, ActResult.class);
		return as1;
	}

	@RequestMapping("writeUeditResult")
	@ResponseBody
	@NoCheckLogin
	public void writeUeditResult(HttpServletRequest request, HttpServletResponse response, @RequestParam String action,String data){
		String json = "";
		if (action.startsWith("upload")) {
			ActResult ar = JsonUtil.getObject(data, ActResult.class);
			if (ar.isSuccess()) {
				State state = new BaseState(true);
				JSONObject map = (JSONObject) ((List) ar.getData()).get(0);

				String filename = map.getString("original");
				filename =  filename.substring(filename.lastIndexOf('/')+1);
				String type = filename.substring(filename.lastIndexOf('.'));

				map.put("ofilename", filename);
				map.put("type", type);
				map.put("url", "http://" + map.getString("original"));
				
				for (String key : map.keySet()) {
					if ("ofilename".equals(key)) {
						state.putInfo("original", map.getString(key));
						//state.putInfo("title", map.getString(key));
					} else {
						state.putInfo(key, map.getString(key));
					}
				}
				json = state.toJSONString();
			} else {
				json = "{\"state\": \"" + ar.getMsg() + "\"}";
			}

		} else {
			String rootPath = UploadController.class.getResource("/").getPath();
			json = new ActionEnter(request, rootPath).exec();
		}

		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
