/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.UploadUtil;
import com.wode.factory.model.PageData;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.service.PageDataService;
import com.wode.factory.service.PageTypeService;
import com.wode.factory.service.ProductService;

@Controller
@RequestMapping("pageData")
public class PageDataController{
	@Autowired
	private PageDataService pageDataService;
	@Autowired
	private PageTypeService pageTypeService;
	@Resource
	private ProductService productService;

	private Set<String> interfaceUrl=new HashSet<String>();
	
	@Qualifier("creat_html_url")
	@Autowired
	public void setInterfaceUrl(String interfaceUrl) {
		String[] arr=interfaceUrl.split(",");
		for(String url:arr){
			if(!StringUtils.isEmpty(url)){
				this.interfaceUrl.add(url);
			}
		}

	}
	
	/**
	 * 功能说明：跳转到默认的页面
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping
	public String toPageSetting(Model model, String type, Integer pageNum, Integer pageSize) {
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		ProductCategory pc = new ProductCategory();
		pc.setId(1l);
		pc.setName("商城页");
		list.add(0, pc);
		PageTypeSetting data = new PageTypeSetting();
		data.setChannelId(1);
		data.setPage("1");
		List<PageTypeSetting> pageTypeSettingList = this.pageTypeService.selectPageType(data);
		model.addAttribute("pageTypeSettingList", pageTypeSettingList);
		model.addAttribute("listCategory", list);
		return "manager/pageData";
	}
	
	/**
	 * 功能说明：查询查询app端首页配置信息
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @param pageTypeId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("pageDataInfo")
	public String pageSettingInfo(Model model,@RequestParam Map<String, Object> params){
		PageInfo<PageData> li_page = null;
		li_page	= pageDataService.selectList(params);
		for (PageData pd : li_page.getList()) {
			if(pd.isLinkProduct() && pd.getProductId()!=null) {
				Product p = productService.getById(pd.getProductId());
				if(p!=null) {
					pd.setLocked(p.getLocked());
					pd.setLockReason(p.getLockReason());
					pd.setIsMarketable(p.getIsMarketable());
				}
			}
		}
		model.addAttribute("pageInfo", li_page);
		return "manager/pageData-list";
	}

	/**
	 * 功能说明：删除数据
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public @ResponseBody Integer deletePageSettingInfo(Model model,Integer id){
		PageData pd = pageDataService.selectById(id);
		clearAutoLock(pd.getLink());
		return this.pageDataService.deleteByPageId(id);
	}
	
	/**
	 * 功能说明：查询具体数据，查询需要修改的信息
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("showlayer")
	public String showlayer(Model model,Integer id,String page,Integer channelId){
		PageData pageSetting = this.pageDataService.selectById(id);
		PageTypeSetting pt = new PageTypeSetting();
		pt.setPage(page);
		pt.setChannelId(channelId);
		//根据page 页面标示 1表示首页，其他值表示分类id、channelId 1表示pc,2表示app。进行查询该页面位置数据的分类
		List<PageTypeSetting> listPageType = this.pageTypeService.selectPageType(pt);
		model.addAttribute("pageType", listPageType);
		model.addAttribute("pageInfo", pageSetting);
		return "manager/pageData-update";
	}
	
	/**
	 * 功能说明：修改信息
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param model
	 * @param id
	 * @param imagePath
	 * @param pageTypeId
	 * @param title
	 * @param orders
	 * @param link
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody Integer update(Model model,PageData pd,String image,Long oldPageTypeId){
		String oldLink = pageDataService.selectById(pd.getId().intValue()).getLink();
		Integer rtn = this.pageDataService.updatePageSetting(image,pd,oldPageTypeId);
		if(!StringUtils.isEquals(oldLink, pd.getLink())) {
			autoLock(pd.getLink());
			clearAutoLock(oldLink);
		}
		
		return rtn;
	}
	
	
	/**
	 * 功能说明：保存信息
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pageTypeId
	 * @param orders
	 * @param title
	 * @param link
	 * @param imagePath
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody Integer insertPageTypeSetting(String pageTypeIdAndPid,String page,Integer orders,String title,String link,String image){
		Integer rtn = this.pageDataService.insertPageSetting(pageTypeIdAndPid,page, orders, title, link, image);
		autoLock(link);
		return rtn;
	}
	
	
	@RequestMapping("upload/pic")
	@ResponseBody
	public ActResult upload(HttpServletRequest request)  { 
		return new UploadUtil().uploadImage(request);
    } 

	private void autoLock(String link) {
		if(PageData.isLinkProduct(link)) {
			Product p = productService.getById(NumberUtil.toLong(link));
			if(p!=null) {
				if(p.getLocked() ==null || p.getLocked()==0) {
					p.setLocked(1);
					p.setLockReason(ProductService.PAGE_LOCK_REASON);
					productService.updateByBusiness(p);
				}
			}
		}
	}
	
	private void clearAutoLock(String link) {
		if(PageData.isLinkProduct(link)) {
			Integer cnt = pageDataService.selectCountByLink(link);
			if(cnt==null || cnt==0) {
				Product p = productService.getById(NumberUtil.toLong(link));
				if(p!=null && p.getLocked() !=null && p.getLocked()==1) {
					String lockReason = p.getLockReason();
					if(lockReason!=null) {
						lockReason=lockReason.trim();
					}
					
					if("首页产品".equals(lockReason) || ProductService.PAGE_LOCK_REASON.equals(lockReason)) {
						p.setLocked(0);
						p.setLockReason(null);
						productService.updateByBusiness(p);
					}		
				}
			}
		}
	}
}

