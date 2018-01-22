package com.wode.factory.user.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.factory.model.CmsChannel;
import com.wode.factory.model.CmsContent;
import com.wode.factory.user.service.CmsChannelService;
import com.wode.factory.user.service.CmsContentService;

/**
 * @author zcx
 *
 */
@Controller
@RequestMapping("/cms")
public class CmsChannelController extends BaseController {
	@Qualifier("cmsChannelService")
	@Autowired
	private CmsChannelService cmsChannelService;
	@Qualifier("CmsContentService")
	@Autowired
	private CmsContentService cmsContentService;

	@RequestMapping(value = { "/allInfo" })
	public String selectAllCmsChannel(HttpServletRequest request,Model model) {
		List<CmsChannel> allCmsChannel = new ArrayList<CmsChannel>();
		allCmsChannel = this.cmsChannelService.findAllCmsChannel();
		model.addAttribute("channelInfo", allCmsChannel);
		if(!allCmsChannel.isEmpty()){
			Map<String, Object> content = this.selectCmsPageInfo(request, model, allCmsChannel.get(0).getId(), 1);
			model.addAttribute("content",content);
		}
		
		return "/cms/information";
	}

	@RequestMapping(value = { "/pageInfo" })
	@ResponseBody
	public Map<String,Object> selectCmsPageInfo(HttpServletRequest request,Model model,Long channelId,Integer pages){
		Map<String,Object> map = new HashMap<String,Object>();
		//每页查询多少数据
		Integer size = 10;
		//查询的内容
		List<CmsContent> li_content = this.cmsContentService.findPageInfo(channelId,pages,size);
		//总数量
		Long totalNumber = this.cmsContentService.findByChannelIdCount(channelId);
		//内容
		map.put("content", li_content);
		//总数量
		map.put("totalNumber", totalNumber);
		//当前页码
		map.put("pages", pages);
		if(!li_content.isEmpty())
		map.put("channelId", li_content.get(0).getChannelid());
		//总的页码
		map.put("totalPages", totalNumber%size!=0?totalNumber/size+1:totalNumber/size);
		return map;
	}
	
}
