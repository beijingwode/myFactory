package com.wode.factory.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.service.InventoryService;
import com.wode.factory.service.PromotionProductService;
import com.wode.factory.vo.PromotionProductVo;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("promotionProduct")
public class PromotionProductController {
	
	@Autowired
	PromotionProductService promotionProductService;
	@Autowired
	InventoryService invSer;
	
	/**
	 * 跳转到位置管理页
	* @return
	 */
	@RequestMapping
	public String toPage(Model model){
		return "manager/promotionProduct";
	}
	
	/**
	 * 跳转到位置管理页
	 * @return
	 */
	@RequestMapping("set")
	public String toPageSet(){
		return "manager/promotionProductSet";
	}
	
	/**
	 * 
	 * 功能说明：分页查询秒杀活动商品
	 * 日期:	2015年8月5日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model,HttpSession session){
		SysUser user = (SysUser) session.getAttribute(Constant.SESSION_LOGIN_USER);
		params.put("userId", user.getId());
		String productName = String.valueOf(params.get("productName"));
		if(productName!= null && !"".equals(productName)){
			params.put("productName", "%"+productName+"%");
		}
		params.put("promotionId", "1");
		PageInfo<PromotionProductVo> page = promotionProductService.findList(params);
		model.addAttribute("page", page);
		return "manager/promotionProduct-list";
	}
	
	@RequestMapping(value = "listSet", method = RequestMethod.POST)
	public String listSet(@RequestParam Map<String, Object> params,Model model){
		params.put("promotionId", "1");
		PageInfo<PromotionProductVo> page = promotionProductService.findListSet(params);
		model.addAttribute("page", page);
		return "manager/promotionProductSet-list";
	}

	/**
	 * 促销商品详细
	 * 
	 * @param id 促销商品表ID
	 * @return
	 */
	@RequestMapping("detail")
	public ModelAndView detail(Long id, ModelAndView mav, HttpSession session) {
		
		SysUser user = (SysUser) session.getAttribute(Constant.SESSION_LOGIN_USER);
		
		// 返回Map
		Map<String, Object> map  = promotionProductService.detail(id, user.getId());
		
		// 返回验证状态
		mav.addObject("flg", map.get("flg"));
		// 返回错误信息
		mav.addObject("msg", map.get("msg"));
		// 返回是否有下一条
		mav.addObject("nextFlg", map.get("nextFlg"));
		// 返回审核的时List
		mav.addObject("hourList", map.get("hourList"));
		
		PromotionProductVo ppv = (PromotionProductVo) map.get("promotionProductVo");
		if(ppv != null) {
			Date joinStart = ppv.getJoinStart();
			if(joinStart != null && !(boolean)map.get("flg")) {
				SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
				String dbJoinStartYMD = sdfYMD.format(joinStart);
				if(dbJoinStartYMD.equals(map.get("promotionYMD"))) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH");
					String joinStartH = sdf.format(joinStart);
					// 返回审核的时
					mav.addObject("joinStartH", joinStartH);
				}
			}
		}
		
		// 返回促销商品详细信息
		mav.addObject("promotionProductVo", map.get("promotionProductVo")==null?new PromotionProductVo():map.get("promotionProductVo"));
		// 返回促销商品详细页面
		mav.setViewName("manager/promotionProduct-detail");
		return mav;
	}
	
	/**
	 * 对促销商品进行审核
	 * 
	 * @param id
	 * @param result
	 * @param startTime
	 * @return
	 */
	@RequestMapping("doReview")
	@ResponseBody
	public Map<String, Object> doReview(Long id, boolean result, String startTime, HttpSession session) {
		
		SysUser user = (SysUser) session.getAttribute(Constant.SESSION_LOGIN_USER);
		
		Map<String, Object> map = promotionProductService.doReview(id, user.getId(), result, startTime);
		PromotionProductVo ppv = (PromotionProductVo) map.get("promotionProductVo");
		if(ppv != null) {
			if(result) {
				Date joinStart = ppv.getJoinStart();
				if(joinStart != null) {
					SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
					String dbJoinStartYMD = sdfYMD.format(joinStart);
					if(dbJoinStartYMD.equals(map.get("promotionYMD"))) {
						SimpleDateFormat sdf = new SimpleDateFormat("HH");
						String joinStartH = sdf.format(joinStart);
						// 返回审核的时
						map.put("joinStartH", joinStartH);
					}
				}
			}
		} else {
			map.put("promotionProductVo", new PromotionProductVo());
		}
		return map;
	}
	
	/**
	 * 审核下一条数据的详细
	 * @author Liuc
	 * @param id 当前的数据ID
	 */
	@RequestMapping("nextDetail")
	@ResponseBody
	public Map<String, Object> nextDetail(Long id, HttpSession session) {
		SysUser user = (SysUser) session.getAttribute(Constant.SESSION_LOGIN_USER);
		// 返回Map
		Map<String, Object> map = promotionProductService.nextDetail(id, user.getId());
		PromotionProductVo ppv = (PromotionProductVo) map.get("promotionProductVo");
		if(ppv != null) {
			Date joinStart = ppv.getJoinStart();
			if(joinStart != null && !(boolean)map.get("flg")) {
				SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
				String dbJoinStartYMD = sdfYMD.format(joinStart);
				if(dbJoinStartYMD.equals(map.get("promotionYMD"))) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH");
					String joinStartH = sdf.format(joinStart);
					// 返回审核的时
					map.put("joinStartH", joinStartH);
				}
			}
		}
		// 返回促销商品详细信息
		map.put("promotionProductVo", map.get("promotionProductVo")==null?new PromotionProductVo():map.get("promotionProductVo"));
		return map;
	 }
	
	/**
	 * 对促销商品进行审核
	 * 
	 * @param id
	 * @param result
	 * @param startDate
	 * @param startTime
	 * @return
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public Integer updateStatus(HttpSession session,@RequestBody PromotionProduct proProduct) {
		// 当前登录用户实体
		SysUser user = (SysUser) session.getAttribute(Constant.SESSION_LOGIN_USER);
		// 当前系统时间
		Date systemDate = new Date(System.currentTimeMillis());
		// 审核通过或不通过人
		proProduct.setReviewedUserId(user.getId());
		// 审核通过或不通过时间
		proProduct.setReviewedDate(systemDate);
		// 促销商品修改时间
		proProduct.setModifyDate(systemDate);
		
		return promotionProductService.updateStatus(proProduct);
	}
	
	/**
	 * 跳转到修改页面
	 * 
	 * @param id
	 * @param result
	 * @param startDate
	 * @param startTime
	 * @return
	 */
	@RequestMapping("toEdit")
	@ResponseBody
	public String toEdit(Model model,String id,String status) {
		model.addAttribute("id",id);
		model.addAttribute("status",status);
		return "manager/promotion-update";
	}
	
}
