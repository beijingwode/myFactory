package com.wode.factory.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.factory.model.LimitSupplierVo;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductTrialLimitGroup;
import com.wode.factory.model.ProductTrialLimitItem;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.ProductTrialLimitGroupService;
import com.wode.factory.service.ProductTrialLimitItemService;
import com.wode.factory.vo.ProductTrialLimitItemProductVo;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("productTrial")
public class ProductTrialLimitController {

	@Autowired
	private ProductTrialLimitItemService productTrialLimitItemService;
	
	@Autowired
	private ProductTrialLimitGroupService productTrialLimitGroupService;
	
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	//企采
	@Autowired
	private ProductLadderService productLadderService;
	
	@Resource
	private ProductService productService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 跳转分组管理页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toGroupManagePage")
	public String toGroupManagePage(Model model,HttpSession session) {
		List<ProductTrialLimitGroup> limitGroupList = productTrialLimitGroupService.findGroupOperatorList();
		model.addAttribute("limitGroupList", limitGroupList);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		model.addAttribute("uid", user.getId());
		return "/manager/gift/group-manager-base";
	}
	
	/**
	 * 分组管理List
	 * @param params
	 * @param session
	 * @return
	 */
	@RequestMapping("/groupManageList")
	public ModelAndView groupManageList(@RequestParam Map<String, Object> params,HttpSession session) {
		ModelAndView model = new ModelAndView("/manager/gift/group-manager-view");
		PageInfo pageInfo = productTrialLimitGroupService.getGroupManageListByMap(params);
		model.addObject("page", pageInfo);
		return model;
	}
	
	/**
	 * 删除限购组
	 * @return
	 */
	@RequestMapping("/delGroup")
	@ResponseBody
	public ActResult<String> delGroup(Long id){
		productTrialLimitGroupService.delGroup(id);
		return ActResult.success("");
	}
	
	/**
	 * 跳转添加分组页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toGroupAddPage")
	public String toGroupAddPage(Model model,HttpSession session) {
		return "/manager/gift/group-add";
	}
	
	/**
	 * 添加分组
	 * @param productTrialLimitGroup
	 * @param session
	 * @return
	 */
	@RequestMapping("/addGroupMsg")
	@ResponseBody
	public ActResult<String> addGroupMsg(ProductTrialLimitGroup productTrialLimitGroup, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		try {
			productTrialLimitGroup.setLimitStart(sdf.parse(sdf1.format(productTrialLimitGroup.getLimitStart())+" 00:00:00"));
			productTrialLimitGroup.setLimitEnd(sdf.parse(sdf1.format(productTrialLimitGroup.getLimitEnd())+" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		productTrialLimitGroup.setStatus(0);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		productTrialLimitGroup.setOperatorId(user.getId());
		productTrialLimitGroup.setUpdateTime(new Date());
		productTrialLimitGroup.setOperator(user.getUsername());
		productTrialLimitGroupService.addGroupMsg(productTrialLimitGroup);
		return ActResult.success("");
	}
	
	/**
	 * 处理from表单时间问题
	 * @param binder
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	dateFormat.setLenient(false);  
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
	} 
	
	/**
	 * 跳转到修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/toEditGroupMsgPage")
	public ModelAndView toEditGroupMsgPage(Long id) {
		ModelAndView model = new ModelAndView("/manager/gift/group-edit");
		ProductTrialLimitGroup productTrialLimitGroup = productTrialLimitGroupService.getGroupMsgById(id);
		model.addObject("limitGroup", productTrialLimitGroup);
		return model;
	}
	
	
	/**
	 * 修改分组信息（无需更新redis中商品id）
	 * @param productTrialLimitGroup
	 * @return
	 */
	@RequestMapping("/editGroupMsg")
	@ResponseBody
	public ActResult<String> editGroupMsg(ProductTrialLimitGroup productTrialLimitGroup){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		try {
			productTrialLimitGroup.setLimitStart(sdf.parse(sdf1.format(productTrialLimitGroup.getLimitStart())+" 00:00:00"));
			productTrialLimitGroup.setLimitEnd(sdf.parse(sdf1.format(productTrialLimitGroup.getLimitEnd())+" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		productTrialLimitGroup.setUpdateTime(new Date());
		//处理修改后的redis中商品id
		productTrialLimitGroupService.saveOrUpdate(productTrialLimitGroup);
		return ActResult.success("");
	}
	
	/**
	 * 跳转限购商品页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/toGroupLimitProductPage")
	public ModelAndView toGroupLimitProductPage(Long id,HttpSession session) {
		if(id==null) {
			id=Long.valueOf(0);
		}
		ModelAndView modelAndView = new ModelAndView("/manager/gift/group-limit-product-base");
		ProductTrialLimitGroup query = new ProductTrialLimitGroup();
		List<ProductTrialLimitGroup> limitGroupList = productTrialLimitGroupService.selectByModel(query);
		List<LimitSupplierVo> limitSupplierList = productTrialLimitItemService.findSupplierByProductId();
		modelAndView.addObject("limitSupplierList", limitSupplierList);
		modelAndView.addObject("limitGroupList", limitGroupList);
		modelAndView.addObject("groupId", id);
		return modelAndView;
	}
	
	/**
	 * 限购商品list
	 * @param params
	 * @param session
	 * @return
	 */
	@RequestMapping("/getGroupLimitProductList")
	public ModelAndView getGroupLimitProductList(@RequestParam Map<String, Object> params,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/manager/gift/group-limit-product-view");
		PageInfo<ProductTrialLimitItemProductVo> pageInfo = productTrialLimitItemService.getGroupLimitProductList(params);
		for (ProductTrialLimitItemProductVo p : pageInfo.getList()) {
			//显示临时表的价格
			p.setProductSpecificationslist(productSpecificationsService.findlistByProductid(p.getProductId()));
			List<ProductLadder> productLadderlist = productLadderService.getByProductId(p.getProductId());
			p.setProductLadderlist(productLadderlist);
		}
		modelAndView.addObject("page", pageInfo);		
		return modelAndView;
	}
	
	/**
	 * 删除限购商品（更新删除redis中productId====前提验证该productId是否存在其他有效分组中）
	 * @param id
	 * @param productId
	 * @return
	 */
	@RequestMapping("/delProduct")
	@ResponseBody
	public ActResult<String> delProduct(Long id,Long productId,Long groupId) {
		productTrialLimitItemService.delProductById(id,productId,groupId);
		return ActResult.success("");
	}
	
	/**
	 * 跳转到增加商品页面
	 * @return
	 */
	@RequestMapping("/toLimitProductAddPage")
	public ModelAndView toLimitProductAddPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/manager/gift/group-product-add");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		ProductTrialLimitGroup query = new ProductTrialLimitGroup();
		query.setOperatorId(user.getId());
		List<ProductTrialLimitGroup> limitGroupList = productTrialLimitGroupService.selectByModel(query);
		modelAndView.addObject("limitGroupList", limitGroupList);
		return modelAndView;
	}
	
	/**
	 * 获取商品信息
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getProductMsg")
	@ResponseBody
	public ActResult<Object> getProductMsg(Long productId){
		Product p = productService.getById(productId);
		//显示临时表的价格
		p.setProductSpecificationslist(productSpecificationsService.findlistByProductid(p.getId()));
		List<ProductLadder> productLadderlist = productLadderService.getByProductId(p.getId());
		p.setProductLadderList(productLadderlist);
		return  ActResult.success(p);
	}
	
	/**
	 * 添加限购商品（更新添加redis中productId===前提验证该分组是否在有效时间内并且productId是否已经存在于缓存中）
	 * @return
	 */
	@RequestMapping("/addGroupLimitProduct")
	@ResponseBody
	public ActResult<String> addGroupLimitProduct(ProductTrialLimitItem productTrialLimitItem){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupId", productTrialLimitItem.getGroupId());
		map.put("productId", productTrialLimitItem.getProductId());
		ProductTrialLimitItem productLimitItem = productTrialLimitItemService.getGroupLimitProductByMap(map);
		if(productLimitItem != null) {
			return ActResult.fail("该商品已存在于该限购组中");
		}
		productTrialLimitItemService.addGroupLimitProduct(productTrialLimitItem);
		return ActResult.success("");
	}
	
	/**
	 * 修改状态（更新删除或添加redis中该分组内的所有productId====前提验证该分组是否在有效时间内并且该productId是否存在于缓存，且该productId是否存在于其他有效分组中）
	 * @return
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public ActResult<String> updateStatus(Long id,Integer status){
		productTrialLimitGroupService.updateStatus(id,status);
		return ActResult.success("");
	}
}

