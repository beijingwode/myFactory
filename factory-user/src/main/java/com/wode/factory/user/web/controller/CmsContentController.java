
package com.wode.factory.user.web.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wode.factory.model.CmsContent;
import com.wode.factory.user.service.CmsContentService;

/**
 * 
 * <pre>
 * 功能说明: cms内容管理
 * 日期:	2015年6月8日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年6月8日
 * </pre>
 */
@Controller
@RequestMapping("/cmsContent")
public class CmsContentController extends BaseController{    
	
	@Autowired
	private CmsContentService cmsContentService;
	
	/**
	 * 
	 * 功能说明：
	 * 日期:	2015年6月9日
	 * 开发者:宋艳垒
	 *
	 * @param model
	 * @param id 文章详情id
	 * @param cmsChannelId 栏目id
	 * @return
	 */
	@RequestMapping("")
	public String toDetail(Model model,Long id,Long channelId){
		//查询文章详情
		CmsContent cmsContent = cmsContentService.getById(id);
		//根据栏目id查询文章列表
		List<CmsContent> listCmsContent = cmsContentService.getBychannelId(channelId,id);
		model.addAttribute("cmsContent", cmsContent);
		model.addAttribute("list", listCmsContent);
	    return "/cms/information_detail";
	}
	
}
