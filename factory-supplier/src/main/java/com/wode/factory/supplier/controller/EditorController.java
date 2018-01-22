package com.wode.factory.supplier.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.UserConstant;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.controller.BaseCompanyController;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.Role;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.supplier.query.RoleQuery;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.UserInterceptor;
import com.wode.model.CommUser;

/**
 * Created by zoln on 2016/1/8.
 */
@Controller
public class EditorController  extends BaseCompanyController{

	@Resource(name = "userService")
	private UserService userService;

	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	EnterpriseUserService enterpriseUserService;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);

	//跳转到角色页面
	@RequestMapping(value = "permissions_role")
	public ModelAndView role(HttpServletRequest request, ModelAndView mv, RoleQuery roleQuery) {
		EnterpriseVo ent = getEnterpriseInfo(request);
		//商家角色信息
		List<Role> roles = this.userService.selectRoles(null, ent.getId());

		roleQuery.setSupplierId(ent.getId());
		PageInfo<RoleQuery> pageInfo = this.userService.findRolePage(roleQuery);

		mv.addObject("role", roles);
		mv.addObject("page", pageInfo);
		mv.addObject("query", roleQuery);
		mv.setViewName("product/permissions/role");
		return mv;
	}

	//跳转到用户页面
	@RequestMapping(value = "permissions_user")
	public ModelAndView user(HttpServletRequest request, ModelAndView mv, RoleQuery roleQuery) {
		EnterpriseVo ent = getEnterpriseInfo(request);
		//商家角色信息
		List<Role> roles = this.userService.selectRoles(null, ent.getId());

		roleQuery.setSupplierId(ent.getId());
		PageInfo<RoleQuery> pageInfo = this.userService.findUserNamePage(roleQuery);

		mv.addObject("role", roles);
		mv.addObject("page", pageInfo);
		mv.addObject("query", roleQuery);
		mv.setViewName("product/permissions/user");
		return mv;
	}

	//跳转到添加角色页面
	@RequestMapping(value = "permissions_role_add")
	public ModelAndView roleAdd(HttpServletRequest request,ModelAndView model) {
		model.addObject("status", "add");
		model.setViewName("product/permissions/role_add");
		return model;
	}
	
	@RequestMapping("checkRoleNameOnly")
	@ResponseBody
	public boolean checkRoleNameOnly(HttpServletRequest request,String roleName,Integer roleId){
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		List<Role> roles = this.userService.selectRoles(null, admin.getSupplierId());
		for(Role role : roles){
			if(role.getName().equals(roleName)){
				//添加角色
				if(StringUtils.isNullOrEmpty(roleId)){
					return false;
				}else{//修改角色
					//角色名称不能重复
					if(roleId.equals(role.getId())){
						return true;
					}else{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 跳转到修改角色页面
	 * @param request
	 * @param param  id-userId
	 * @return
	 */
	@RequestMapping(value = "permissions_role_update")
	public String roleUpdate(HttpServletRequest request, String param) {

		return "product/permissions/role_update";
	}

	@RequestMapping("/editor/add")
	@ResponseBody
	public ActResult<CommUser> addEditor(HttpServletRequest request, RoleQuery editor) {
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		return this.registerUserName(editor, request, admin);
	}
	@RequestMapping("/editor/update")
	@ResponseBody
	public ActResult<CommUser> updateEditor(HttpServletRequest request, RoleQuery editor) {
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		UserFactory user = this.userService.getById(editor.getUserId());
		if(StringUtils.isNullOrEmpty(user)){
			return this.registerUserName(editor, request, admin);
		}else{
			if (!user.getRealName().equals(editor.getRealName())) {//姓名修改
				user.setRealName(editor.getRealName());
			}
			if (!user.getRole().equals(editor.getId())) {
				user.setRole(editor.getId());
			}
			/**
			 * 因为共通中没有修改邮箱的接口。所以用删除后再次注册的方法//手机号、账号、
			 * */
			if(user.getEmail().equals(editor.getEmail())){//user.getUserName().equals(editor.getUserName())&&user.getPhone().equals(editor.getPhone())&&
				//密码不为空，修改密码
				if(!StringUtils.isNullOrEmpty(editor.getPassWord())){
					//us.updatePassword(editor.getUserId(), editor.getPassWord(), admin.getUserName());
				}
				//修改本地user表中的信息
				this.userService.update(user);
				EnterpriseUser empUser = this.enterpriseUserService.selectById(user.getId());
				if (empUser!=null) {
					empUser.setName(editor.getRealName());
					enterpriseUserService.update(empUser);
				}
				ActResult<CommUser> act = new ActResult<CommUser>();
				act.setSuccess(true);
				return act;
			}else{
				
				ActResult<String> act = us.updateEmail(editor.getEmail(), user.getId(), UserConstant.COMFROM);
				
				if(!act.isSuccess()) {
					//如果把原有的手机号转移到该企业，原来账号密码问题，id问题，不仅要修改厂还要修改企业中的员工id，牵连大。或者修改共通中的id，没有接口，还需要删除原有的账号i的，否则重复
					logger.error("修改员工接口：>>>>>>>>"+"邮箱："+user.getEmail()+">>>>>>新邮箱："+editor.getEmail()+">>>>>>status：修改失败，共通已存在该邮箱");
					return ActResult.fail(act.getMsg());
				}
				
				user.setEmail(editor.getEmail());
				this.userService.update(user);
				EnterpriseUser empUser = this.enterpriseUserService.selectById(user.getId());
				if (empUser!=null) {
					empUser.setName(editor.getRealName());
					empUser.setEmail(editor.getEmail());
					enterpriseUserService.update(empUser);
				}
				return ActResult.successSetMsg("修改成功");
			}
		}
	}
	private ActResult<CommUser> registerUserName(RoleQuery editor,HttpServletRequest request,UserFactory admin){
		ActResult<CommUser> comUser=null;
		UserFactory uf=null;
		boolean hasPhone=false,hasEmail=false;
		//邮箱检查
		if(!StringUtils.isEmpty(editor.getEmail())){
			comUser = us.findByEmail(editor.getEmail());
			if(comUser!=null && comUser.isSuccess()) {
				hasEmail=true;
			}
		}
		if(!hasEmail && !StringUtils.isEmpty(editor.getPhone())){
			comUser = us.findByPhone(editor.getPhone());
			if(comUser!=null && comUser.isSuccess()) {
				hasPhone=true;
			}
		}
		if(comUser!=null&&comUser.getData()!=null) {
			uf = userService.getById(comUser.getData().getUserId());
		}
		
		if(comUser!=null&&comUser.getData()!=null && uf==null) {
			comUser.setMsg("用户已注册");
			comUser.success(false);
			return comUser;	
		} else {
			//已经是商家用户
			if(uf!=null) {
				if(uf.getSupplierId()!=null && !admin.getSupplierId().equals(uf.getSupplierId())){
					if(hasPhone){
						comUser.setMsg("该手机号已被别的商家注册");
						comUser.success(false);
						return comUser;	
					}
					if(hasEmail){
						comUser.setMsg("该邮箱已被别的商家注册");
						comUser.success(false);
						return comUser;	
					}
				}
			}
		}
		Enterprise ent= enterpriseService.getById(admin.getSupplierId());
		String shopLink = StringUtils.isEmpty(ent.getEmpDefultAvatar())?null:(enterpriseService.getFirstShopId(admin.getSupplierId())+"");
		
		if(uf==null) {
			/**
			 * 注册共通+t_user表中用户数据
			 * */
			CommUser user = new CommUser();
			user.setUserName(editor.getUserName());
			user.setUserEmail(editor.getEmail());
			user.setUserPhone(editor.getPhone());
			if (!StringUtils.isNullOrEmpty(editor.getRealName())) {
				user.setNickName(editor.getRealName());
			}else{
				user.setNickName(editor.getUserName());
				if(user.getNickName().equals(user.getUserPhone())) {
					String nickName=editor.getPhone();
					if(nickName.length()>4) {
						nickName="1***"+nickName.substring(nickName.length()-4);
						user.setNickName(nickName);
					}
				}
			}
			user.setRealName(editor.getRealName());
			user.setPassword(editor.getUserName());
			user.setUsable(1);
			user.setEnabled(1);
			user.setUserCom(UserConstant.COMFROM);
			user.setCreatedTime(new Date());
			user.setUserIp(getIpAddr(request));
			user.setUserType(2);//1普通用户  2商家用户
			user.setUserCom("company");
			user.setUserLevel(0);
			ActResult<CommUser> actUser = us.insert(user);
			
			if(actUser.isSuccess()){
				//注册成功
				this.userService.addEditor(admin, editor, actUser.getData().getUserId(),ent.getEmpDefultAvatar(), shopLink);
				actUser.setData(null);
				actUser.setMsg("注册成功");
				return actUser;
			}else{
				actUser.setMsg("用户已注册");
				actUser.success(false);
				return actUser;
			}
		} else {
			uf.setSupplierId(admin.getSupplierId());
			uf.setType(3);
			uf.setRole(editor.getId());
			
			if(StringUtils.isEmpty(uf.getAvatar())) {
				uf.setAvatar(ent.getEmpDefultAvatar());
				uf.setShopLink(shopLink);
			}
			
			this.userService.update(uf);
			comUser.setData(null);
			comUser.setMsg("注册成功");
			return comUser;	
		}
	}

	@RequestMapping("/role/add")
	@ResponseBody
	public Role addRole(HttpServletRequest request, Role role, Integer[] adds) {
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		userService.saveRole(admin, role, adds);
		return role;
	}

	@RequestMapping(value = "/role/edit/{id}", method = RequestMethod.GET)
	public String editrole(HttpServletRequest request, Model model, @PathVariable("id") Integer id) {
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		Role role = userService.getRole(id, admin.getSupplierId());
		model.addAttribute("role", role);
		String resourceIds=",";
		if(role.getResources()!=null) {
			for (com.wode.factory.model.Resource r : role.getResources()) {
				resourceIds = resourceIds+r.getId()+",";
			}
		}
		model.addAttribute("resourceIds", resourceIds);
		model.addAttribute("status", "update");
		return "product/permissions/role_add";
	}

	/**
	 * 删除角色
	 *
	 * @param request
	 * @param roleQuery
	 * @return
	 */
	@RequestMapping("/role/delete")
	@ResponseBody
	public ActResult<String> editAuth(HttpServletRequest request, RoleQuery roleQuery) {
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		ActResult<String> act = new ActResult<String>();
		if (StringUtils.isNullOrEmpty(roleQuery.getId())) {
			act.setSuccess(false);
			act.setMsg("参数为空");
			return act;
		} else {
			/**
			 * 删除t_user、共通、role、roleResource表中数据
			 * */
			//t_user表
			UserFactory user = new UserFactory();
			user.setSupplierId(admin.getSupplierId());
			user.setRole(roleQuery.getId());
			List<UserFactory> users = this.userService.getUserList(user);
			if (!users.isEmpty() && users.size() > 0) {
				for (UserFactory uf : users) {
					EnterpriseUser empUser = enterpriseUserService.getById(uf.getId());
					if (empUser!=null) {
						//先修改本地user
						uf.setRole(0);
						uf.setType(1);
						this.userService.update(uf);
						//修改企业员工信息
						empUser.setType(2);
						enterpriseUserService.update(empUser);
					}else{
						//删除共通用户
						us.deleteUser(uf.getId(), UserConstant.COMFROM, admin.getId());
						//删除用户信息
						this.userService.deleteUserFactoryById(uf.getId());
					}
				}
			}
			//删除和角色相关的信息
			this.userService.deleteRole(admin.getSupplierId(), roleQuery);

		}
		return act;
	}

	@RequestMapping(value = "getByRoleId")
	@ResponseBody
	public ActResult<RoleQuery> getByRoleId(HttpServletRequest request, RoleQuery roleQuery) {
		UserFactory admin = UserInterceptor.getSessionUser(request,shopService);
		ActResult<RoleQuery> act = new ActResult<RoleQuery>();
		//用户信息
		List<RoleQuery> query = this.userService.selectRoleAndUserName(roleQuery);
		//全部角色
		List<Role> roles = this.userService.selectRoles(null, admin.getSupplierId());
		Map map = new HashMap();
		if (query.size() > 0){
			map.put("user", query.get(0));
		}
		map.put("roles", roles);
//		if (query.size() > 0) {
//			return act.success(query.get(0));
//		}
		return act.success(map);
	}

	/**
	 * 删除用户
	 * @param request
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public ActResult deleteUser(HttpServletRequest request, Long userId, Integer roleId) {
		ActResult<String> act = new ActResult<String>();
		UserFactory uf = this.userService.getById(userId);
		if (StringUtils.isNullOrEmpty(uf)) {
			act.setMsg("用户不存在");
			act.setSuccess(false);
			return act;
		} else {
			if (roleId.equals(uf.getRole())) {
				UserFactory user = getSessionUserModel(request);
				EnterpriseUser empUser = enterpriseUserService.getById(userId);
				if (empUser!=null) {
					//先修改本地user
					uf.setRole(0);
					uf.setType(1);
					this.userService.update(uf);
					//修改企业员工信息
					empUser.setType(2);
					enterpriseUserService.update(empUser);
					
				}else{
					this.userService.deleteUserFactoryById(userId);
					//删除共通用户
					us.deleteUser(userId, UserConstant.COMFROM, user.getId());
				}
				return act;
			} else {
				act.setMsg("数据有误");
				act.setSuccess(false);
				return act;
			}
		}
	}

	/**
	 * 重置密码
	 *
	 * @param request
	 * @param id
	 * @param newPass
	 * @return
	 */
	@RequestMapping("resetPass")
	@ResponseBody
	public ActResult resetPass(HttpServletRequest request, Long id) {
		ActResult act=us.resetPassword(id, UserConstant.COMFROM,false,null);// = us.updatePassword(id, newPass, "myFactory");
		if (StringUtils.isNullOrEmpty(act)) {
			act = new ActResult();
			act.setSuccess(false);
			act.setMsg("系统错误！！");
			return act;
		} else {
			act.setData(null);
			return act;
		}
	}

	/**
	 * 校验账号唯一
	 *
	 * @param request
	 * @param checkParam 校验参数 (账号、手机号、邮箱)
	 * @param status     校验参数类型
	 * @return
	 */
	@RequestMapping("checkOnly")
	@ResponseBody
	public ActResult<String> checkOnly(HttpServletRequest request,
			String checkParam, String status, Long userId) {
		ActResult<String> returnAct = new ActResult<String>();
		
		UserFactory user = null;
		ActResult<CommUser> ar= us.findByEmail(checkParam);
		if(ar.getData() == null) {
			returnAct.setMsg("校验参数不存在");
			returnAct.setSuccess(true);
			return returnAct;
		} else if(ar.getData().getUserId().equals(userId)) {
			returnAct.setSuccess(true);
			return returnAct;
		} else {
			returnAct.setSuccess(false);
			return returnAct;
		}
	}
	
	/**userId为空,表示是添加用户操作
	 * userId不为空,表示是修改用户操作
	 * @param returnAct
	 * @param user
	 * @param otherUser
	 * @param userId
	 * @return
	 */
	private ActResult<String> re(ActResult<String> returnAct,UserFactory user,UserFactory otherUser,Long userId){
		
		if (StringUtils.isNullOrEmpty(user)&&StringUtils.isNullOrEmpty(otherUser)) {
			returnAct.setMsg("校验参数不存在");
			returnAct.setSuccess(true);
			return returnAct;
		} else{
			if(!StringUtils.isNullOrEmpty(user)&&!StringUtils.isNullOrEmpty(otherUser)){
				//user和otherUser为空,userId为空，是添加用户校验操作
				if(null == userId){
					if(user.getId().equals(otherUser.getId())){
						returnAct.setSuccess(true);
						return returnAct;
					}else{
						returnAct.setSuccess(false);
						return returnAct;
					}
				//user和otherUser不为空,userId不为空，是修改用户校验操作
				}else{
					//两个对象id相等，并且userid和两个对象的id全相等。
					if(user.getId().equals(otherUser.getId())&&user.getId().equals(userId)&&otherUser.getId().equals(userId)){
						returnAct.setSuccess(true);
						return returnAct;
					}else{
						returnAct.setSuccess(false);
						return returnAct;
					}
				}
			}else{
				//user对象不为空，并对象id和userId相等
				if(!StringUtils.isNullOrEmpty(user)){
					if(null==userId){
						returnAct.setSuccess(false);
						return returnAct;
					}else{
						if(user.getId().equals(userId)){
							returnAct.setSuccess(true);
							return returnAct;
						}else{
							returnAct.setSuccess(false);
							return returnAct;
						}
					}
				//otherUser对象不为空，并对象id和userId相等
				}else if(!StringUtils.isNullOrEmpty(otherUser)){
					if(null==userId){
						returnAct.setSuccess(false);
						return returnAct;
					}else{
						if(otherUser.getId().equals(userId)){
							returnAct.setSuccess(true);
							return returnAct;
						}else{
							returnAct.setSuccess(false);
							return returnAct;
						}
					}
				}else{
					returnAct.setSuccess(true);
					return returnAct;
				}
			}
		}
		
	}
	
	private static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
