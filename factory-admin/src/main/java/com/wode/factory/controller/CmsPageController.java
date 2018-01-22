/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.UploadUtil;
import com.wode.factory.model.PageSectionData;
import com.wode.factory.model.PageSetting;
import com.wode.factory.model.PageTemplate;
import com.wode.factory.model.PageTemplateSection;
import com.wode.factory.model.Product;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.PageSectionDataService;
import com.wode.factory.service.PageSettingService;
import com.wode.factory.service.PageTemplateSectionService;
import com.wode.factory.service.PageTemplateService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SupplierService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("cmsPage")
@SuppressWarnings("unchecked")
public class CmsPageController{
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private PageSettingService pageSettingService;
	@Autowired
	private PageTemplateService pageTemplateService;
	@Autowired
	private PageTemplateSectionService pageTemplateSectionService;
	@Autowired
	private PageSectionDataService pageSectionDataService;
	@Resource
	private ProductService productService;
	@Autowired
	private RedisUtil redisUtil;
	
	private Set<String> interfaceUrl=new HashSet<String>();

	private  String apiUrl=Constant.FACTORY_API_URL;
	
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
	@Resource
	private SysUserMapper sysUserMapper;
	
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
	@RequestMapping("pageBase")
	public String toPageSetting(Model model,HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-15");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		
		model.addAttribute("supplierList", getSupplierList(query));
		return "cms/page/base";
	}
	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pageList", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model,HttpSession session) {				
		PageInfo<PageSetting> page = pageSettingService.findPage(params);
		model.addAttribute("page", page);
		return "cms/page/list";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "preview")
	public String preview(Model model,HttpSession session,Long pid,String pram) {
		PageSetting page = pageSettingService.getById(pid);
		ActResult<String> ret = makeHtml(page.getId(),true,pram);
		
        if(ret.isSuccess()) {
    		return "redirect:"+page.getUrl().replace(".html", "_preView.html").replace("_m.htm", "_preView.html");
        } else {
    		return "";
        }		
	}

	/**
	 * 作废页面
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "destroyHtml")
	public String cancelHtml(Model model,HttpSession session,Long pid,String pram) {
		PageSetting page = pageSettingService.getById(pid);
		page.setStatus(-1);
		page.setUpdateTime(new Date());
		pageSettingService.update(page);//更新状态已作废
		
		ActResult<String> ret = destroyHtml(page.getId(),false,pram);
		
		if(ret.isSuccess()) {
			if(page.getChannel() == 2) {
				redisUtil.del("findPageDataForApp_[\""+page.getPageKey()+"\"]");        		
			}
			return "redirect:"+page.getUrl();
		} else {
			return ""; 
		}			
	}
	
	/**
	 * 作废页面404
	 * @param pid
	 * @param preView
	 * @param pram
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ActResult<String> destroyHtml(Long pid,boolean preView,String pram) {
		ActResult<String> ret = ActResult.success(null);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("preView", preView);
		paramMap.put("pram", pram);
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/destroyPage"+pid, paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(!as.isSuccess()){
	        	ret.setMsg(apiurl+":"+as.getMsg());
	        	ret.setSuccess(false);
	        }
		}
		return ret;
	}
	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "createHtml")
	public String createHtml(Model model,HttpSession session,Long pid,String pram) {
		PageSetting page = pageSettingService.getById(pid);
		page.setStatus(1);
		page.setUpdateTime(new Date());
		pageSettingService.update(page);//更新状态已生效
		ActResult<String> ret = makeHtml(page.getId(),false,pram);
		
        if(ret.isSuccess()) {
        	if(page.getChannel() == 2) {
        		redisUtil.del("findPageDataForApp_[\""+page.getPageKey()+"\"]");        		
        	}
    		return "redirect:"+page.getUrl();
        } else {
    		return "";
        }			
	}
	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pageEdit")
	public String pageEdit(Model model,HttpSession session,Integer channel,Long templateId,Long id) {
		PageSetting page = null;
		channel = channel==null?1:channel;
		if(id != null) {
			page=pageSettingService.getById(id);
			if(page != null) {
				channel=page.getChannel();
				templateId=page.getTemplateId();
			}
		}
		PageTemplate query = new PageTemplate();
		query.setChannel(channel);

		model.addAttribute("channel", channel);
		model.addAttribute("templateId", templateId);
		model.addAttribute("page", page);
		model.addAttribute("templates", pageTemplateService.selectByModel(query));
		model.addAttribute("supplierList", getSupplierList(new HashMap<String, Object>()));
		
		return "cms/page/edit";
	}
	@RequestMapping(value = "pageSave", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> pageSave(PageSetting page, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		// 检查关键字
		PageSetting query = new PageSetting();
		query.setChannel(page.getChannel());
		query.setPageKey(page.getPageKey());
		List<PageSetting> ls= pageSettingService.selectByModel(query);
		for (PageSetting pageSetting : ls) {
			if(pageSetting.getPageKey().equals(page.getPageKey()) && !pageSetting.getId().equals(page.getId())) {
				return ActResult.fail("关键字重复，请更换");
			}
		}
		
		// 检查模板
		PageTemplate template =pageTemplateService.getById(page.getTemplateId());
		if(template==null) {
			return ActResult.fail("模板不存在，请刷新后重试");
		}
		page.setTemplateTitle(template.getTitle());
		if(StringUtils.isEmpty(template.getHtmlPath())) {
			if(page.getChannel() == 1) {
				page.setUrl(Constant.FACTORY_WEB_URL+page.getPageKey()+".html");
			} else {
				if("index".equals(page.getPageKey())) {
					page.setUrl(apiUrl + "index_m.htm");					
				}else {
					page.setUrl(apiUrl + page.getPageKey()+".html");
				}
			}
		} else {
			page.setUrl(template.getHtmlPath()+page.getPageKey()+".html");
		}

		// 检查商家
		if(page.getSupplierId() !=null) {
			Supplier s= supplierService.findByid(page.getSupplierId());
			if(s==null) {
				return ActResult.fail("商家不存在，请刷新后重试，或清空专属企业");				
			}
			page.setSupplierName(s.getComName());
		}
		
		Date now=new Date();
		if(page.getId() == null) {
			// 新添加
			page.setCreateTime(now);
			page.setCreateUserId(user.getId());
			page.setCreateUserName(user.getName());
			page.setUpdateTime(now);
			page.setStatus(0);
			pageSettingService.save(page);
			
			PageTemplateSection squery = new PageTemplateSection();
			squery.setTemplateId(page.getTemplateId());
			List<PageTemplateSection> sList = pageTemplateSectionService.selectByModel(squery);
			for (int i=sList.size()-1;i>-1;i--) {
				PageTemplateSection s=sList.get(i);
				if(this.isJson(s.getEx1Name()) || this.isJson(s.getEx2Name()) || this.isJson(s.getEx3Name())|| this.isJson(s.getEx4Name())|| this.isJson(s.getEx5Name())|| this.isJson(s.getEx6Name())) {					
				} else {
					sList.remove(i);
				}
			}
			
			// 处理默认值
			if (sList.size() > 0) {
				// 获取刚刚保存的page
				PageSetting pquery = new PageSetting();
				pquery.setPageKey(page.getPageKey());
				pquery.setChannel(page.getChannel());
				List<PageSetting> pls= pageSettingService.selectByModel(pquery);
				page= pls.get(0);
				
				for (PageTemplateSection section : sList) {
					String ex1Value="";
					String ex2Value="";
					String ex3Value="";
					String ex4Value="";
					String ex5Value="";
					String ex6Value="";
					

					if(isJson(section.getEx1Name())) {

						JSONObject jo = JSONObject.parseObject(section.getEx1Name());
						if("page".equals(jo.getString("default")) && !StringUtils.isEmpty(jo.getString("value"))) {
							ex1Value = jo.getString("value");
						}
					}

					if(isJson(section.getEx2Name())) {

						JSONObject jo = JSONObject.parseObject(section.getEx2Name());
						if("page".equals(jo.getString("default")) && !StringUtils.isEmpty(jo.getString("value"))) {
							ex2Value = jo.getString("value");
						}
					}

					if(isJson(section.getEx3Name())) {

						JSONObject jo = JSONObject.parseObject(section.getEx3Name());
						if("page".equals(jo.getString("default")) && !StringUtils.isEmpty(jo.getString("value"))) {
							ex3Value = jo.getString("value");
						}
					}
					
					if(isJson(section.getEx4Name())) {

						JSONObject jo = JSONObject.parseObject(section.getEx4Name());
						if("page".equals(jo.getString("default")) && !StringUtils.isEmpty(jo.getString("value"))) {
							ex4Value = jo.getString("value");
						}
					}
					
					if(isJson(section.getEx5Name())) {

						JSONObject jo = JSONObject.parseObject(section.getEx5Name());
						if("page".equals(jo.getString("default")) && !StringUtils.isEmpty(jo.getString("value"))) {
							ex5Value = jo.getString("value");
						}
					}
					
					if(isJson(section.getEx6Name())) {

						JSONObject jo = JSONObject.parseObject(section.getEx6Name());
						if("page".equals(jo.getString("default")) && !StringUtils.isEmpty(jo.getString("value"))) {
							ex6Value = jo.getString("value");
						}
					}
					//有默认值
					if(ex1Value!="" || ex2Value!="" || ex3Value!="" || ex4Value!="" || ex5Value!="" || ex6Value!="" ) {
						PageSectionData data = new PageSectionData();
						data.setPageId(page.getId());
						data.setChannel(page.getChannel());
						data.setSectionId(section.getId());
						data.setOrders(0);
						data.setEx1Value(ex1Value);
						data.setEx2Value(ex2Value);
						data.setEx3Value(ex3Value);
						data.setEx4Value(ex4Value);
						data.setEx5Value(ex5Value);
						data.setEx6Value(ex6Value);
						data.setCreateTime(now);
						data.setUpdateTime(now);
						
						pageSectionDataService.save(data);
					}
				}
			}
		} else {
			PageSetting old = pageSettingService.getById(page.getId());
			page.setCreateTime(old.getCreateTime());
			page.setCreateUserId(old.getCreateUserId());
			page.setCreateUserName(old.getCreateUserName());
			page.setUpdateTime(now);
			pageSettingService.update(page);
		}
		
		return ActResult.success("");
	}
	/**
	 * 复制属性列表
	 * @param model
	 * @param session
	 * @param channel
	 * @param templateId
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "copyPageEdit")
	public String copyPageEdit(Model model,HttpSession session,Integer channel,Long templateId,Long id) {
		PageSetting page = null;
		channel = channel==null?1:channel;
		if(id != null) {
			page=pageSettingService.getById(id);
			if(page != null) {
				channel=page.getChannel();
				templateId=page.getTemplateId();
			}
		}
		PageTemplate query = new PageTemplate();
		query.setChannel(channel);

		model.addAttribute("channel", channel);
		model.addAttribute("templateId", templateId);
		model.addAttribute("page", page);
		model.addAttribute("templates", pageTemplateService.selectByModel(query));
		model.addAttribute("supplierList", getSupplierList(new HashMap<String, Object>()));
		
		return "cms/page/copyEdit";
	}
	/**
	 * 复制页保存
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "copyPageSave", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> copyPageSave(PageSetting page, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;
		Long wornId = page.getId();
		
		// 检查关键字
		PageSetting query = new PageSetting();
		query.setChannel(page.getChannel());
		query.setPageKey(page.getPageKey());
		List<PageSetting> ls = pageSettingService.selectByModel(query);
		for (PageSetting pageSetting : ls) {
			if (pageSetting.getPageKey().equals(page.getPageKey())) {// && !pageSetting.getId().equals(page.getId())
				return ActResult.fail("关键字重复，请更换");
			}
		}

		// 检查模板
		PageTemplate template = pageTemplateService.getById(page.getTemplateId());
		if (template == null) {
			return ActResult.fail("模板不存在，请刷新后重试");
		}
		page.setTemplateTitle(template.getTitle());
		if (StringUtils.isEmpty(template.getHtmlPath())) {
			if (page.getChannel() == 1) {
				page.setUrl(Constant.FACTORY_WEB_URL + page.getPageKey() + ".html");
			} else {
				if ("index".equals(page.getPageKey())) {
					page.setUrl(apiUrl + "index_m.htm");
				} else {
					page.setUrl(apiUrl + page.getPageKey() + ".html");
				}
			}
		} else {
			page.setUrl(template.getHtmlPath() + page.getPageKey() + ".html");
		}

		// 检查商家
		if (page.getSupplierId() != null) {
			Supplier s = supplierService.findByid(page.getSupplierId());
			if (s == null) {
				return ActResult.fail("商家不存在，请刷新后重试，或清空专属企业");
			}
			page.setSupplierName(s.getComName());
		}

		Date now = new Date();
		// 新添加
		page.setId(null);
		page.setCreateTime(now);
		page.setCreateUserId(user.getId());
		page.setCreateUserName(user.getName());
		page.setUpdateTime(now);
		page.setStatus(0);
		PageSetting pageSetting2 = pageSettingService.save(page);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newId", pageSetting2.getId());
		params.put("wornId", wornId);
		pageSectionDataService.copyPageData(params);
		return ActResult.success("");
	}
	/**
	 * 删除页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "pageDel", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> pageDel(Long id) {
		//通过位置id获取位置下的数据
		PageSectionData queryData = new PageSectionData();
		queryData.setPageId(id);
		List<PageSectionData> pageSectionDataList = pageSectionDataService.selectByModel(queryData);
		//先删除该位置下数据
		if(pageSectionDataList!=null && !pageSectionDataList.isEmpty()){
			for (PageSectionData pageSectionData : pageSectionDataList) {
				pageSectionDataService.removeById(pageSectionData.getId());
				this.clearAutoLock(pageSectionData.getLink());
			}
		}
		
		pageSettingService.removeById(id);
	
		return ActResult.success("");
	}
	
	
	@RequestMapping("dataBase")
	public String toData(Model model,HttpSession session,Long pid) {
		PageSetting page = null;
		int channel =1;
		if(pid != null) {
			page=pageSettingService.getById(pid);
			if(page != null) {
				channel=page.getChannel();
			}
		}
				
		PageSetting query = new PageSetting();
		query.setChannel(channel);
		model.addAttribute("plist", pageSettingService.selectByModel(query));
		model.addAttribute("pid", pid);
		model.addAttribute("channel", channel);
		
		return "cms/data/base";
	}

	
	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "dataList", method = RequestMethod.POST)
	public String dataList(@RequestParam Map<String, Object> params, Model model,HttpSession session) {				
		PageInfo<PageSectionData> page = pageSectionDataService.findPage(params);
		for (PageSectionData pd : page.getList()) {
			if(pd.isLinkProduct() && pd.getProductId()!=null) {
				Product p = productService.getById(pd.getProductId());
				if(p!=null) {
					pd.setLocked(p.getLocked());
					pd.setLockReason(p.getLockReason());
					pd.setIsMarketable(p.getIsMarketable());
				}
			}
		}
		model.addAttribute("page", page);
		return "cms/data/list";
	}
	

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "dataEdit")
	public String dataEdit(Model model,HttpSession session,Integer channel,Long pageId,Long sectionId,String ex1Value,Long id) {
		PageSectionData data =null;
		channel = channel==null?1:channel;
		if(id != null) {
			data=pageSectionDataService.getById(id);
		}

		String ex2Value ="";
		String ex3Value ="";
		String ex4Value ="";
		String ex5Value ="";
		String ex6Value ="";
		String title1Value ="";
		String title2Value ="";
		// 编辑
		if(data != null) {
			channel=data.getChannel();
			pageId=data.getPageId();
			sectionId = data.getSectionId();
			ex1Value = data.getEx1Value();
			ex2Value = data.getEx2Value();
			ex3Value = data.getEx3Value();
			ex4Value = data.getEx4Value();
			ex5Value = data.getEx5Value();
			ex6Value  = data.getEx6Value();
			title1Value=data.getTitle1();
			title2Value=data.getTitle2();
		}

		// page
		PageSetting pquery = new PageSetting();
		pquery.setChannel(channel);
		List<PageSetting> plist = pageSettingService.selectByModel(pquery);
		
		if(pageId==null) {
			if(plist!=null && !plist.isEmpty()){
				pageId= plist.get(0).getId();
			}
		}
		// 位置
		List<PageTemplateSection> sList=null;
		PageTemplateSection section = null;
		PageSetting page = pageSettingService.getById(pageId);
		if (page != null) {
			PageTemplateSection squery = new PageTemplateSection();
			squery.setTemplateId(page.getTemplateId());
			sList= pageTemplateSectionService.selectByModel(squery);
		}
		if(sectionId==null) {
			if(sList!=null && !sList.isEmpty()) {
				sectionId= sList.get(0).getId();
			}
		}
		if(sectionId!=null) {
			section=pageTemplateSectionService.getById(sectionId);
		}
				
		model.addAttribute("channel", channel);
		model.addAttribute("pageId", pageId);
		model.addAttribute("sectionId", sectionId);
		model.addAttribute("data", data);
		model.addAttribute("section", section);
		model.addAttribute("plist", plist);
		model.addAttribute("sList", sList);
		
		String ex1Name="扩展1";
		String ex2Name="扩展2";
		String ex3Name="扩展3";
		String ex4Name="扩展4";
		String ex5Name="扩展5";
		String ex6Name="扩展6";
		String title1Name="副标题1";
		String title2Name="副标题2";
		String ex1Placeholder = "";
		String ex2Placeholder = "";
		String ex3Placeholder = "";
		String ex4Placeholder = "";
		String ex5Placeholder = "";
		String ex6Placeholder = "";
		String title1Placeholder = "";
		String title2Placeholder = "";
		
		if(section!=null) {
			if(!StringUtils.isEmpty(section.getEx1Name())) {
				if(isJson(section.getEx1Name())) {
					JSONObject jo = JSONObject.parseObject(section.getEx1Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						ex1Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						ex1Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(ex1Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							ex1Value = jo.getString("value");
						}
					}
				} else {
					ex1Name=section.getEx1Name();
				}
			}

			if(!StringUtils.isEmpty(section.getEx2Name())) {
				if(isJson(section.getEx2Name())) {
					JSONObject jo = JSONObject.parseObject(section.getEx2Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						ex2Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						ex2Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(ex2Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							ex2Value = jo.getString("value");
						}
					}
				} else {
					ex2Name=section.getEx2Name();
				}
			}

			if(!StringUtils.isEmpty(section.getEx3Name())) {
				if(isJson(section.getEx3Name())) {
					JSONObject jo = JSONObject.parseObject(section.getEx3Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						ex3Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						ex3Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(ex3Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							ex3Value = jo.getString("value");
						}
					}
				} else {
					ex3Name=section.getEx3Name();
				}
			}
			if(!StringUtils.isEmpty(section.getEx4Name())) {
				if(isJson(section.getEx4Name())) {
					JSONObject jo = JSONObject.parseObject(section.getEx4Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						ex4Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						ex4Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(ex4Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							ex4Value = jo.getString("value");
						}
					}
				} else {
					ex4Name=section.getEx4Name();
				}
			}
			if(!StringUtils.isEmpty(section.getEx5Name())) {
				if(isJson(section.getEx5Name())) {
					JSONObject jo = JSONObject.parseObject(section.getEx5Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						ex5Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						ex5Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(ex5Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							ex5Value = jo.getString("value");
						}
					}
				} else {
					ex5Name=section.getEx5Name();
				}
			}
			if(!StringUtils.isEmpty(section.getEx6Name())) {
				if(isJson(section.getEx6Name())) {
					JSONObject jo = JSONObject.parseObject(section.getEx6Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						ex6Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						ex6Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(ex6Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							ex6Value = jo.getString("value");
						}
					}
				} else {
					ex6Name=section.getEx6Name();
				}
			}
			
			if(!StringUtils.isEmpty(section.getTitle1Name())) {
				if(isJson(section.getTitle1Name())) {
					JSONObject jo = JSONObject.parseObject(section.getTitle1Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						title1Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						title1Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(title1Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							title1Value = jo.getString("value");
						}
					}
				} else {
					title1Name=section.getTitle1Name();
				}
			}
			

			if(!StringUtils.isEmpty(section.getTitle2Name())) {
				if(isJson(section.getTitle2Name())) {
					JSONObject jo = JSONObject.parseObject(section.getTitle2Name());
					if(!StringUtils.isEmpty(jo.getString("name"))) {
						title2Name = jo.getString("name");
					}
					if(!StringUtils.isEmpty(jo.getString("placeholder"))) {
						title2Placeholder = jo.getString("placeholder");
					}
					if(StringUtils.isEmpty(title2Value)) {
						if(!StringUtils.isEmpty(jo.getString("value"))) {
							title2Value = jo.getString("value");
						}
					}
				} else {
					title2Name=section.getTitle2Name();
				}
			}
		}
		model.addAttribute("ex1Name", ex1Name);
		model.addAttribute("ex2Name", ex2Name);
		model.addAttribute("ex3Name", ex3Name);
		model.addAttribute("ex4Name", ex4Name);
		model.addAttribute("ex5Name", ex5Name);
		model.addAttribute("ex6Name", ex6Name);
		model.addAttribute("title1Name", title1Name);
		model.addAttribute("title2Name", title2Name);
		model.addAttribute("ex1Placeholder", ex1Placeholder);
		model.addAttribute("ex2Placeholder", ex2Placeholder);
		model.addAttribute("ex3Placeholder", ex3Placeholder);
		model.addAttribute("ex4Placeholder", ex4Placeholder);
		model.addAttribute("ex5Placeholder", ex5Placeholder);
		model.addAttribute("ex6Placeholder", ex6Placeholder);
		model.addAttribute("title1Placeholder", title1Placeholder);
		model.addAttribute("title2Placeholder", title2Placeholder);
		model.addAttribute("ex1Value", ex1Value);
		model.addAttribute("ex2Value", ex2Value);
		model.addAttribute("ex3Value", ex3Value);
		model.addAttribute("ex4Value", ex4Value);
		model.addAttribute("ex5Value", ex5Value);
		model.addAttribute("ex6Value", ex6Value);
		model.addAttribute("title1Value", title1Value);
		model.addAttribute("title2Value", title2Value);
		
		return "cms/data/edit";
	}

	@RequestMapping(value = "dataSave", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> dataSave(PageSectionData data, HttpSession session) {		
		// 检查page
		PageSetting page = null; 
		if(data.getPageId() !=null) {
			page=pageSettingService.getById(data.getPageId());
		}
		if(page==null) {
			return ActResult.fail("所属页面不存在，请刷新后重试");
		}
		
		// 检查位置
		PageTemplateSection section = null;
		if(data.getSectionId() !=null) {
			section=pageTemplateSectionService.getById(data.getSectionId());
		}
		if(section==null) {
			return ActResult.fail("所属位置不存在，请刷新后重试");
		}
		
		Date now=new Date();
		if(data.getId() == null) {
			// 新添加
			data.setCreateTime(now);
			data.setUpdateTime(now);
			
			pageSectionDataService.save(data);
			
			autoLock(data.getLink());
		} else {
			PageSectionData old = pageSectionDataService.getById(data.getId());
			data.setCreateTime(old.getCreateTime());
			data.setUpdateTime(now);
			pageSectionDataService.update(data);
			
			if(!StringUtils.isEquals(old.getLink(), data.getLink())) {
				clearAutoLock(old.getLink());
				autoLock(data.getLink());
			}
		}
		
		return ActResult.success("");
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
	@RequestMapping("dataDel")
	public @ResponseBody Integer dataDel(Model model,Long id){
		PageSectionData old = pageSectionDataService.getById(id);
		pageSectionDataService.removeById(id);
		clearAutoLock(old.getLink());
		return 1;
	}
	
	@RequestMapping(value = "getTemplates", method = RequestMethod.POST)
	@ResponseBody
	public List<PageTemplate> getTemplates(int channel){
		PageTemplate query = new PageTemplate();
		query.setChannel(channel);
		return pageTemplateService.selectByModel(query);
	}

	@RequestMapping(value = "getPages", method = RequestMethod.POST)
	@ResponseBody
	public List<PageSetting> getPages(int channel){
		PageSetting query = new PageSetting();
		query.setChannel(channel);
		return pageSettingService.selectByModel(query);
	}

	@RequestMapping(value = "getSections", method = RequestMethod.POST)
	@ResponseBody
	public List<PageTemplateSection> getSections(Long pid){
		PageSetting page = pageSettingService.getById(pid);
		PageTemplateSection query = new PageTemplateSection();
		if (page != null) {
			query.setTemplateId(page.getTemplateId());
		} else {
			query.setTemplateId(-1L);
		}
		return pageTemplateSectionService.selectByModel(query);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("upload/pic")
	@ResponseBody
	public ActResult upload(HttpServletRequest request)  { 
		return new UploadUtil().uploadImage(request);
    }
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		return supplierService.getPage(query).getList();
	}

	/**
	 * 模板管理
	 * @param model
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("templateBase")
	public String toTemplateBase(Model model,HttpSession session) {
		return "cms/template/base";
	}

	
	/**
	 * 模板属性列表
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "templateList", method = RequestMethod.POST)
	public String templateList(@RequestParam Map<String, Object> params, Model model,HttpSession session) {				
		PageInfo<PageTemplate> page = pageTemplateService.findTemplateList(params);
		model.addAttribute("page", page);
		return "cms/template/list";
	}
	
	/**
	 * 编辑模板
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "templateEdit")
	public String templateEdit(Model model,HttpSession session,Long id) {
		PageTemplate pagetemplate = null;
		if(id != null) {
			pagetemplate=pageTemplateService.getById(id);
		}else{
			pagetemplate = new PageTemplate();
		}
		model.addAttribute("pageTemplate", pagetemplate);
		return "cms/template/edit";
	}
	/**
	 * 模板保存
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "templateSave", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> templateSave(PageTemplate pageTemplate, HttpSession session) {
		Date now=new Date();
		if(pageTemplate.getId() == null) {
			// 新添加
			PageTemplate query = new PageTemplate();
			query.setChannel(pageTemplate.getChannel());
			List<PageTemplate> templateList = pageTemplateService.selectByModel(query);
			if(templateList!=null && !templateList.isEmpty()){
				for (PageTemplate template : templateList) {
					if(pageTemplate.getTitle().equals(template.getTitle())){
						return ActResult.fail("此标题在该渠道已存在!");
					}
				}
			}
			pageTemplate.setCreateTime(now);
			pageTemplate.setUpdateTime(now);
			pageTemplateService.save(pageTemplate);
		} else {
			PageTemplate old = pageTemplateService.getById(pageTemplate.getId());
			pageTemplate.setCreateTime(old.getCreateTime());
			pageTemplate.setUpdateTime(now);
			pageTemplateService.update(pageTemplate);
		}
		return ActResult.success("");
	}
	@RequestMapping(value = "templatedel", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> templatedel(Long id) {
		PageSetting pageSetting = new PageSetting();
		pageSetting.setTemplateId(id);
		List<PageSetting> pageSettingList = pageSettingService.selectByModel(pageSetting);
		//List<PageSetting> pageSettingList = pageSettingService.selectByTemplateId(id);
		if(pageSettingList!=null && !pageSettingList.isEmpty()){
			return ActResult.fail("该模板有页面正在使用无法删除!");
		}else{
			pageTemplateService.delete(id);
		}
		return ActResult.success("");
		
	}
	
	@SuppressWarnings("rawtypes")
	private ActResult<String> makeHtml(Long pid,boolean preView,String pram) {
		ActResult<String> ret = ActResult.success(null);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("preView", preView);
		paramMap.put("pram", pram);
		for(String apiurl:interfaceUrl){
			String response=HttpClientUtil.sendHttpRequest("post", apiurl+"/page"+pid, paramMap);
	        ActResult as = JsonUtil.getObject(response, ActResult.class);
	        if(!as.isSuccess()){
	        	ret.setMsg(apiurl+":"+as.getMsg());
	        	ret.setSuccess(false);
	        }
		}
		return ret;
	}
	
	/**
	 * 位置管理
	 * @param model
	 * @param session
	 * @param pid
	 * @return
	 */
	@RequestMapping("pageTemplateSectionBase")
	public String topageTemplateSectionBase(Model model,HttpSession session) {
		return "cms/pageTemplateSection/base";
	}

	
	/**
	 * 位置属性列表
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pageTemplateSectionList", method = RequestMethod.POST)
	public String pageTemplateSectionList(@RequestParam Map<String, Object> params, Model model,HttpSession session) {
		PageInfo<PageTemplateSection> page = pageTemplateSectionService.findPageTemplateSectionList(params);
		model.addAttribute("page", page);
		return "cms/pageTemplateSection/list";
	}
	
	/**
	 * 编辑位置
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pageTemplateSectionEdit")
	public String pageTemplateSectionEdit(Model model,HttpSession session,Long id,Long templateId,Integer channel) {
		PageTemplateSection pageTemplateSection = null;
		channel = channel==null?1:channel;
		if(id != null) {//编辑
			pageTemplateSection=pageTemplateSectionService.getById(id);
			if(pageTemplateSection!=null){
				channel = pageTemplateSection.getChannel();
				templateId = pageTemplateSection.getTemplateId();
			}
		}else{//新增
			pageTemplateSection = new PageTemplateSection();
		}
		PageTemplate query = new PageTemplate();
		query.setChannel(channel);
		model.addAttribute("channel", channel);
		model.addAttribute("templateId", templateId);
		model.addAttribute("templates", pageTemplateService.selectByModel(query));
		model.addAttribute("pageTemplateSection", pageTemplateSection);
		return "cms/pageTemplateSection/edit";
	}
	/**
	 * 位置保存
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "pageTemplateSectionSave", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> pageTemplateSectionSave(PageTemplateSection pageTemplateSection, HttpSession session) {
		Date now=new Date();
		if(pageTemplateSection.getId() == null) {
			// 新添加
			pageTemplateSection.setCreateTime(now);
			pageTemplateSection.setUpdateTime(now);
			pageTemplateSectionService.save(pageTemplateSection);
		} else {
			PageTemplateSection old = pageTemplateSectionService.getById(pageTemplateSection.getId());
			pageTemplateSection.setCreateTime(old.getCreateTime());
			pageTemplateSection.setUpdateTime(now);
			pageTemplateSectionService.update(pageTemplateSection);
		}
		return ActResult.success("");
	}
	
	@RequestMapping(value = "pageTemplateSectionDel", method = RequestMethod.POST)
	@ResponseBody
	public ActResult<String> pageTemplateSectionDel(Long id) {
		//通过位置id获取位置下的数据
		PageSectionData queryData = new PageSectionData();
		queryData.setSectionId(id);
		List<PageSectionData> pageSectionDataList = pageSectionDataService.selectByModel(queryData);
		//先删除该位置下数据
		if(pageSectionDataList!=null && !pageSectionDataList.isEmpty()){
			for (PageSectionData pageSectionData : pageSectionDataList) {
				pageSectionDataService.removeById(pageSectionData.getId());
			}
		}
		//再删除位置信息
		pageTemplateSectionService.removeById(id);
		
		return ActResult.success("");
		
	}


	private boolean isJson(String jsonStr) {
		return jsonStr!=null && jsonStr.indexOf("{")>-1;
	}

	private void autoLock(String link) {
		if(PageSectionData.isLinkProduct(link)) {
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
		if(PageSectionData.isLinkProduct(link)) {

			PageSectionData queryData = new PageSectionData();
			queryData.setLink(link);
			List<PageSectionData> pageSectionDataList = pageSectionDataService.selectByModel(queryData);
			if(pageSectionDataList.isEmpty()) {
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

