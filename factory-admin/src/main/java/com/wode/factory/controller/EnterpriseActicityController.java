package com.wode.factory.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierEvent;
import com.wode.factory.model.SupplierPrize;
import com.wode.factory.model.SupplierTemp;
import com.wode.factory.service.SupplierEventService;
import com.wode.factory.service.SupplierPrizeService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.SupplierTempService;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("/acticity")
public class EnterpriseActicityController{
	
	@Autowired
	private SupplierEventService supplierEventService;
	
	@Autowired
	private SupplierTempService supplierTempService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierPrizeService supplierPrizeService;
	
	/**
	 * 跳转到年后抽奖管理页
	 * @return
	 */
	@RequestMapping("/toActicityManagerPage")
	public ModelAndView toActicityManagerPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/sign_draw_prize_m");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		List<SupplierEvent> mlist = supplierEventService.getUserManagerGroup();
		modelAndView.addObject("uid", user.getId());
		modelAndView.addObject("mlist", mlist);
		return modelAndView;
	}
	
	/**
	 * 获取活动信息
	 * @param params
	 * @param session
	 * @return
	 */
	@RequestMapping("/acticityList")
	public ModelAndView findActicityList(@RequestParam Map<String, Object> params,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/sign_draw_prize_m_list");
		PageInfo<SupplierEvent> page = supplierEventService.findInfoPageList(params);
		modelAndView.addObject("page", page);
		modelAndView.addObject("apiUrl", Constant.FACTORY_API_URL);
		modelAndView.addObject("webUrl", Constant.FACTORY_WEB_URL);
		
		return modelAndView;
	}
	
	/**
	 * 跳转添加活动的页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toAddActicityPage")
	public ModelAndView toAddActicityPage(Model model,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/add_acticity");
		List<SupplierTemp> tempList = supplierTempService.findAll();
		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplier", getSupplierList(query));
		model.addAttribute("tempList", tempList);
		return modelAndView;
	}
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);

		return supplierService.getPage(query).getList();
	}
	
	/**
	 * 添加活动信息
	 * @param enterpriseId
	 * @param enterpriseType
	 * @param maxTicket
	 * @param eventNote
	 * @param startDate
	 * @param session
	 * @return
	 */
	@RequestMapping("/addActicityMsg")
	@ResponseBody
	public ActResult<Object> addActicityMsg(Long enterpriseId,Integer enterpriseType,String enterpriseName,String eventTitle,BigDecimal maxTicket,String pcPageBg,String pcPageBanner,String wxPageBanner,String eventNote,
			String startDate,HttpSession session) {
		SupplierEvent supplierEvent = new SupplierEvent();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		supplierEvent.setEnterpriseId(enterpriseId);
		supplierEvent.setMaxTicket(maxTicket);
		supplierEvent.setEventNote(eventNote);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			supplierEvent.setStartDate(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		supplierEvent.setEnterpriseName(enterpriseName);
		supplierEvent.setPcPageBanner(pcPageBanner);
		supplierEvent.setPcPageBg(pcPageBg);
		supplierEvent.setWxPageBanner(wxPageBanner);
		supplierEvent.setEnterpriseType(enterpriseType);
		supplierEvent.setCreateDate(new Date());
		supplierEvent.setEventTitle(eventTitle);
		supplierEvent.setCreateUser(user.getId());
		supplierEvent.setCreateUserName(user.getName());
		supplierEvent.setStatus(1);
		supplierEventService.save(supplierEvent);
		return ActResult.success(null);
	}
	
	/**
	 * 跳转修改活动的页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toEditActicityMsg")
	public ModelAndView toEditActicityMsg(Long id,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/edit_acticity");
		SupplierEvent supplierEvent = supplierEventService.getById(id);
		modelAndView.addObject("event", supplierEvent);
		List<SupplierTemp> tempList = supplierTempService.findAll();
		Map<String, Object> query = new HashMap<String, Object>();
		modelAndView.addObject("supplier", getSupplierList(query));
		modelAndView.addObject("tempList", tempList);
		return modelAndView;
	}
	
	/**
	 * 修改活动信息
	 * @param id
	 * @param eventTitle
	 * @param maxTicket
	 * @param pcPageBg
	 * @param pcPageBanner
	 * @param wxPageBanner
	 * @param eventNote
	 * @param session
	 * @return
	 */
	@RequestMapping("/editActicityMsg")
	@ResponseBody
	public ActResult<Object> editActicityMsg(Long id,Long enterpriseId,Integer enterpriseType,String enterpriseName,BigDecimal maxTicket,String pcPageBg,String pcPageBanner,String wxPageBanner,String eventNote,String startDate,String eventTitle,
			HttpSession session) {
		SupplierEvent supplierEvent = supplierEventService.getById(id);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			supplierEvent.setStartDate(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		supplierEvent.setEnterpriseName(enterpriseName);
		supplierEvent.setEnterpriseId(enterpriseId);
		supplierEvent.setEventTitle(eventTitle);
		supplierEvent.setEnterpriseType(enterpriseType);
		supplierEvent.setMaxTicket(maxTicket);
		supplierEvent.setEventNote(eventNote);
		supplierEvent.setPcPageBanner(pcPageBanner);
		supplierEvent.setPcPageBg(pcPageBg);
		supplierEvent.setWxPageBanner(wxPageBanner);
		supplierEvent.setUpdateDate(new Date());
		supplierEvent.setUpdateUser(user.getId());
		supplierEventService.update(supplierEvent);
		return ActResult.success(null);
	} 
	
	/**
	 * 跳转到奖品设置页
	 * @return
	 */
	@RequestMapping("/toPrizeSetPage")
	public ModelAndView toPrizeSetPage(Long acticityId,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/prize_set_m");
		SupplierEvent query = new SupplierEvent();
		List<SupplierEvent> mlist = supplierEventService.selectByModel(query);
		modelAndView.addObject("acticityId", acticityId);
		modelAndView.addObject("mlist", mlist);
		return modelAndView;
	}
	
	/**
	 * 获取活动信息
	 * @param params
	 * @param session
	 * @return
	 */
	@RequestMapping("/prizeList")
	public ModelAndView prizeList(@RequestParam Map<String, Object> params,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/prize_set_m_list");
		PageInfo<SupplierPrize> page = supplierPrizeService.findInfoPageList(params);
		modelAndView.addObject("page", page);
		return modelAndView;
	}
	
	
	/**
	 * 跳转添加奖品的页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toAddPrizePage")
	public ModelAndView toAddPrizePage(Model model,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/add_prize");
		SupplierEvent query = new SupplierEvent();
		List<SupplierEvent> mlist = supplierEventService.selectByModel(query);
		model.addAttribute("acticityList", mlist);
		return modelAndView;
	}
	
	/**
	 * 添加奖品信息
	 * @param supplierPrize
	 * @param session
	 * @return
	 */
	@RequestMapping("/addPrizeMsg")
	@ResponseBody
	public ActResult<Object> addPrizeMsg(SupplierPrize supplierPrize,HttpSession session) {
		SupplierEvent event = supplierEventService.getById(supplierPrize.getActicityId());
		supplierPrize.setEnterpriseId(event.getEnterpriseId());
		supplierPrize.setActicityTheme(event.getEventTitle());
		supplierPrizeService.save(supplierPrize);
		return ActResult.success(null);
	}
	
	/**
	 * 跳转修改奖品的页面
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("/toEditPrizeMsg")
	public ModelAndView toEditPrizeMsg(Long id,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("manager/acticity/edit_prize");
		SupplierPrize supplierPrize = supplierPrizeService.getById(id);
		modelAndView.addObject("prize", supplierPrize);
		SupplierEvent query = new SupplierEvent();
		List<SupplierEvent> mlist = supplierEventService.selectByModel(query);
		modelAndView.addObject("acticityList", mlist);
		return modelAndView;
	}
	
	/**
	 * 修改奖品信息
	 * @param supplierPrize
	 * @param session
	 * @return
	 */
	@RequestMapping("/editPrizeMsg")
	@ResponseBody
	public ActResult<Object> editPrizeMsg(SupplierPrize supplierPrize,HttpSession session) {
		SupplierEvent event = supplierEventService.getById(supplierPrize.getActicityId());
		supplierPrize.setEnterpriseId(event.getEnterpriseId());
		supplierPrize.setActicityTheme(event.getEventTitle());
		supplierPrizeService.update(supplierPrize);
		return ActResult.success(null);
	}
	
	/**
	 * 删除奖品信息
	 * @param supplierPrize
	 * @param session
	 * @return
	 */
	@RequestMapping("/delPrize")
	@ResponseBody
	public ActResult<Object> delPrize(Long id,HttpSession session) {
		supplierPrizeService.removeById(id);
		return ActResult.success(null);
	}
	
}
