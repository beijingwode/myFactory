package com.wode.api.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.user.util.Constant;

/**
 * 2015-6-17
 *
 * @author 谷子夜
 */
@Controller
@RequestMapping("/app/version")
@ResponseBody
public class AppVersionController extends BaseController {

	/**
	 * 功能：验证密码找回/修改验证码
	 *
	 * @param request
	 * @param phoneNumber
	 * @param code
	 * @return
	 */
	@RequestMapping("getVersion")
	@NoCheckLogin
	public ActResult<AppVesion> getVersion(HttpServletRequest request) {
		String name = request.getParameter("name");
		if (StringUtils.isNullOrEmpty(name)) {
			return ActResult.success(new AppVesion()); 
		}else{
			String versionCode= Constant.IOS_VERSION_CODE;
			String mustUpdate=Constant.IOS_MUST_UPDATE;
			String updateMsg=Constant.IOS_UPDATE_MSG;
			String downloadUrl=Constant.IOS_DOWNLOAD_URL;
			return ActResult.success(new AppVesion(versionCode,mustUpdate,updateMsg,downloadUrl));
		}
	}

	class AppVesion {
		private String versionCode;
		private String mustUpdate;
		private String updateMsg;
		private String downloadUrl;
		
		public AppVesion() {
			versionCode= Constant.APP_VERSION_CODE;
			mustUpdate=Constant.APP_MUST_UPDATE;
			updateMsg=Constant.APP_UPDATE_MSG;
			downloadUrl=Constant.APP_DOWNLOAD_URL;
		}
		
		public AppVesion(String versionCode, String mustUpdate, String updateMsg, String downloadUrl) {
			super();
			this.versionCode = versionCode;
			this.mustUpdate = mustUpdate;
			this.updateMsg = updateMsg;
			this.downloadUrl = downloadUrl;
		}

		public String getVersionCode() {
			return versionCode;
		}
		public void setVersionCode(String versionCode) {
			this.versionCode = versionCode;
		}
		public String getMustUpdate() {
			return mustUpdate;
		}
		public void setMustUpdate(String mustUpdate) {
			this.mustUpdate = mustUpdate;
		}

		public String getUpdateMsg() {
			return updateMsg;
		}

		public void setUpdateMsg(String updateMsg) {
			this.updateMsg = updateMsg;
		}

		public String getDownloadUrl() {
			return downloadUrl;
		}

		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}
		
	}
	
}
	
