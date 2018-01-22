package com.wode.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wode.common.constant.Constant;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.UserFactoryService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysRole;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysRoleService;
import com.wode.sys.service.SysUserService;
import com.wode.tongji.model.ManagerBusiness;
import com.wode.tongji.service.ManagerBusinessService;

@Controller
@RequestMapping("sysuser")
public class SysUserController {

	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private UserFactoryService userFactoryService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private ManagerBusinessService managerBusinessService;
	@Autowired
	RedisUtil redis;
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	/**
	 * 跳转到用户管理
	* @return
	 */
	@RequestMapping
	public String toSysUser(Model model){
		return "sys/user/user";
	}
	
	
	/**
	 * 保存用户
	* @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysUser sysUser,HttpServletRequest request){
		return sysUserService.saveSysUser(sysUser);
	}
	
	/**
	 * 删除用户
	* @param id 用户id
	* @return
	 */
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysUserService.deleteUser(id);
	}

	/**
	 * 删除用户
	* @param id 用户id
	* @return
	 */
	@RequestMapping(value="setRole",method = RequestMethod.POST)
	public @ResponseBody ActResult<String> del(Long id,Integer role){
		if(id==null) return ActResult.fail("处理失败，请确认手机号是否正确，并且该员工已导入到平台");
		
		UserFactory q = new UserFactory();
		q.setId(id);
		q.setEmployeeType(1);
		List<UserFactory> ls = userFactoryService.selectByModel(q);
		if(ls!=null && !ls.isEmpty()) {
			ls.get(0).setRole(role);
			userFactoryService.update(ls.get(0));
			redis.delMapData(RedisConstant.FACTORY_USER_MAP, ls.get(0).getId()+"");
			return ActResult.success("");
		} else {
			return ActResult.fail("处理失败，请确认手机号是否正确，并且该员工已导入到平台");
		}
	}
	
	/**
	 * 用户列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping(value = "manager")
	public String manager(@RequestParam Map<String, Object> params,Long[] roles,Model model){
		return "sys/user/manager";
	}
	
	/**
	 * 用户列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Long[] roles,Model model){
		params.put("roles", StringUtils.join(roles,','));
		PageInfo<SysUser> page = sysUserService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/user/user-list";
	}

	
	/**
	 * 用户列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping(value = "listManger", method = RequestMethod.POST)
	public String listManger(@RequestParam Map<String, Object> params,Model model,HttpSession session){
//		params.put("officeId", 0);
//		params.put("officeType", "");
//		params.put("roles", "-108,-111");
//		params.put("pageNum", 1);
//		params.put("pageSize", 20);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		for (SysUser su : list) {
			if(!StringUtils.isEmpty(su.getMobile())) {
				UserFactory q = new UserFactory();
				q.setEmployeeType(1);
				q.setPhone(su.getMobile());
				List<UserFactory> ls = userFactoryService.selectByModel(q);
				if(ls!=null && !ls.isEmpty()) {
					su.setNo(ls.get(0).getRole()+"");
					su.setOfficeId(ls.get(0).getId());
				} else {
					su.setNo("");
					su.setOfficeId(null);
				}
			} else {
				su.setNo("");
				su.setOfficeId(null);
			}
			
			ManagerBusiness mb = managerBusinessService.getById(su.getId());
			if(mb!=null) {
				su.setRemarks(mb.getBusinessName());
			} else {
				su.setRemarks("");
			}
		}

		model.addAttribute("page", new PageInfo<SysUser>(list));
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		model.addAttribute("leader", isLeander(user.getId())?1:0);
		return "sys/user/manager-list";
	}
	
	@RequestMapping(value = "/toSetBusiness", method = RequestMethod.POST)
	public String toSetManager(Model m,Long id){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-15");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		m.addAttribute("list", list);
		m.addAttribute("manager", sysUserService.selectByPrimaryKey(id));

		ManagerBusiness mb = managerBusinessService.getById(id);
		if(mb!=null) {
			m.addAttribute("businessId", mb.getBusinessId());
		} else {
			m.addAttribute("businessId", "");
		}
		
		return "sys/user/set_business";
	}
	

	@RequestMapping("/setBusiness")
	@ResponseBody
	public int setManager(Long business, Long id,HttpSession session) {
		
		ManagerBusiness mb = managerBusinessService.getById(id);

		if(business == null) {
			if(mb!=null) {
				managerBusinessService.removeById(id);
			}
		} else {
			SysUser manager = sysUserService.selectByPrimaryKey(id);
			SysUser businessO = sysUserService.selectByPrimaryKey(business);
			boolean isNew=false;
			if(mb==null) {
				mb=new ManagerBusiness();
				mb.setId(manager.getId());
				isNew=true;
			}

			mb.setName(manager.getName());
			mb.setBusinessId(businessO.getId());
			mb.setBusinessName(businessO.getName());
			mb.setBusinessEmail(businessO.getEmail());
			mb.setBusinessPhone(businessO.getPhone());
			mb.setBusinessMobile(businessO.getMobile());
			
			if(isNew) {
				managerBusinessService.save(mb);
			} else {
				managerBusinessService.update(mb);
			}
		}
		
		return 0;
	}
	
	/**
	 * 弹窗
	* @param id 用户id
	* @param mode 模式
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id,@PathVariable("mode") String mode, Model model){
		SysUser user = sysUserService.selectByPrimaryKey(id);;
		List<SysRole> roles = null;
		Map<Long, SysRole> rolesMap = Maps.newHashMap(),findUserRoleMap = null;
		if(StringUtils.equals("detail", mode)){
			roles = sysRoleService.findUserRoleListByUserId(id);
			model.addAttribute("roles", roles);
		}
		if(StringUtils.equals("edit", mode)){
			findUserRoleMap = sysRoleService.findUserRoleMapByUserId(id);
			rolesMap.putAll(sysRoleService.findCurUserRoleMap());
			rolesMap.putAll(findUserRoleMap);
			model.addAttribute("rolesMap", rolesMap)
				.addAttribute("findUserRoleMap", findUserRoleMap);
		}
		model.addAttribute("user", user);
		return mode.equals("detail")?"sys/user/user-detail":"sys/user/user-save";
	}
	
	/**
	 * 验证用户名是否存在
	* @param param
	* @return
	 */
	@RequestMapping(value="checkname",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkName(String param){
		Map<String, Object> msg = new HashMap<String, Object>();
		SysUser sysUser = new SysUser();
		sysUser.setUsername(param);
		int count = sysUserService.selectCount(sysUser);
		if(count>0){
			msg.put("info", "此登录名太抢手了,请换一个!");
			msg.put("status", "n");
		}else{
			msg.put("status", "y");
		}
		return msg;
	}

	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}
}
