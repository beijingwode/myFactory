package com.wode.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标签controller
* @ClassName: TagController  
* @author  
* @date 2014年11月28日 下午1:52:11 
*
 */
@Controller
@RequestMapping("tag")
public class TagController {
	
	/**
	 * 树标签
	* @param model
	* @return
	 */
	@RequestMapping("treeselect")
	public String treeSelect(HttpServletRequest request,Model model){
		model.addAttribute("id", request.getParameter("id")); //提交的id,必填
		model.addAttribute("nameId", request.getParameter("nameId")); //选择的节点名称id,必填
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("idKey",  request.getParameter("idKey")); //id属性名称
		model.addAttribute("pIdKey", request.getParameter("pIdKey")); //pid属性名称
		model.addAttribute("curId", request.getParameter("curId")); //当前id
		model.addAttribute("isLayer", request.getParameter("isLayer")); //是否弹窗模式
		model.addAttribute("treeSelectId", request.getParameter("treeSelectId")); //ul树的id
		model.addAttribute("rootNodeName", request.getParameter("rootNodeName")); //顶级节点名称
		model.addAttribute("chkboxType", request.getParameter("chkboxType"));
		return "modules/tree-select";
	}
	
	/**
	 * 功能说明：树标签(商品分类)
	 * 日期:	2015年7月1日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("productcategorytree")
	public String productcategorytree(HttpServletRequest request,Model model){
		model.addAttribute("id", request.getParameter("id")); //提交的id,必填
		model.addAttribute("nameId", request.getParameter("nameId")); //选择的节点名称id,必填
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("idKey",  request.getParameter("idKey")); //id属性名称
		model.addAttribute("pIdKey", request.getParameter("pIdKey")); //pid属性名称
		model.addAttribute("curId", request.getParameter("curId")); //当前id
		model.addAttribute("isLayer", request.getParameter("isLayer")); //是否弹窗模式
		model.addAttribute("treeSelectId", request.getParameter("treeSelectId")); //ul树的id
		model.addAttribute("rootNodeName", request.getParameter("rootNodeName")); //顶级节点名称
		model.addAttribute("chkboxType", request.getParameter("chkboxType"));
		model.addAttribute("scenario", request.getParameter("scenario"));//树形结构使用的场景（默认商品分类。规格管理、属性管理、参数管理  等等）
		return "modules/productcategorytree-select";
	}
	
	@RequestMapping("fontawesome")
	public String fontAwesome(String id,Model model){
		model.addAttribute("id", id);
		return "modules/font-awesome";
	}

}
