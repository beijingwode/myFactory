package com.wode.api.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.api.util.IPUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ClientAccessLog;
import com.wode.factory.model.Currency;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.service.ClientAccessLogService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.PageDataService;
import com.wode.factory.user.service.PageSectionDataService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.vo.PageTypeSettingVo;

@Controller
@RequestMapping("/pageData")
@ResponseBody
public class PageDataController{
	@Autowired
	private PageDataService pageDataService;
    @Autowired
    private ClientAccessLogService clientAccessLogService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private PageSectionDataService pageSectionDataService;
	
	@Autowired
	private UserService userService;
	/**
	 * 功能说明：
	 * @param page  1为首页,其它见文档
	 * @param dataType   类型
	 * @return
	 */
	@RequestMapping("/{page}")
	public ActResult<List<PageTypeSettingVo>> getPageData(@PathVariable String page,String dataType) {
		ActResult<Object> act = new ActResult<Object>();
		if(StringUtils.isEmpty(page)){
			return act.fail("页面标示为空");
		}else{
			List<PageTypeSettingVo> li = this.pageDataService.findPageDataInfo(page, dataType,2);
			if("1".equals(page)) {
				List<PageTypeSettingVo> rtnList = new ArrayList<PageTypeSettingVo>();
				for (PageTypeSettingVo pageTypeSettingVo : li) {
					if(isForOld(pageTypeSettingVo.getName())) {
						rtnList.add(pageTypeSettingVo);
					}
				}
				return act.success(rtnList);
			} else {
				return act.success(li);
			}
		}
	}

	/**
	 * 功能说明：
	 * @param page  1为首页,其它见文档
	 * @param dataType   类型
	 * @return
	 */
	@RequestMapping("/index")
	public ActResult<List<PageTypeSettingVo>> getPageIndexData(String page,String dataType,Long uid,HttpServletRequest request) {
		if(StringUtils.isEmpty(page)){
			return ActResult.fail("页面标示为空");
		}else{
			List<PageTypeSettingVo> li =null;
			if("301".equals(page)) {
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "qicai", "企采频道", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("qicai");
			} else if("302".equals(page)) {
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "shengri", "生日频道", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("shengri");
			} else if("201".equals(page)) {
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "tesheng", "特省频道", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("tesheng");
			} else if("202".equals(page)) {
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "huanling", "换领频道", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("huanling");
			} else if("205".equals(page)) {
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "shiyong", "试用频道", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("shiyong");
			}else if("299".equals(page)){
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "neigouhui", "内购汇", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("neigouhui");
			}
			if(li==null) {
					
				clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "index", "首页", uid,null,null,IPUtils.getClientAddress(request));
				li = this.pageSectionDataService.findPageDataForApp("index");
				//li = this.pageDataService.findPageDataInfo(page, dataType,2);
			}
			ActResult<List<PageTypeSettingVo>> act = ActResult.success(li);
			act.setMsg(entParamCodeService.getBenefitSubsidy().toString());
			return act;
		}
	}
	
	private boolean isForOld(String name) {
		return "banner".equals(name) || "hot_activity".equals(name) || "brand_selling".equals(name);
	}
	
	/**
	 * 新版首页数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/newIndex")
	@ResponseBody
	public ActResult getDifferentgoods(HttpServletRequest request, HttpServletResponse response,Long uid) {
		if(uid==null) return ActResult.fail("用户信息错误");
		UserFactory uf = userService.getById(uid);
		if(uf==null) return ActResult.fail("用户信息错误");
		List<PageTypeSettingVo> li =null;
		li = this.pageSectionDataService.findIndexPageData(uf);
		clientAccessLogService.saveNormal(ClientAccessLog.ACCESS_TYPE_INDEX, "index", "首页", uid,null,null,IPUtils.getClientAddress(request));
		ActResult result = ActResult.success(li);
		result.setMsg(entParamCodeService.getBenefitSubsidy().toString());
		return result;
	}
}
