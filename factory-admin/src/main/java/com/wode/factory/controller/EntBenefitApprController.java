/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.util.ActResult;
import com.wode.common.util.SeasonUtil;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.service.EntBenefitApprService;
import com.wode.factory.service.EnterpriseService;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.vo.EntBenefitApprVO;
import com.wode.factory.vo.EnterpriseVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("entBenefitAppr")
public class EntBenefitApprController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("entBenefitApprService")
	private EntBenefitApprService entBenefitApprService;
	
	@Autowired
	@Qualifier("enterpriseService")
	private EnterpriseService enterpriseService;
	@Autowired
	@Qualifier("enterpriseUserService")
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private EntBenefitFacade entBenefitFacade;

	@Autowired
	DBUtils dbUtils;

	@Resource
	private SysUserMapper sysUserMapper;

	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	
	@RequestMapping
	public String toHtml(Model model) {
		return "manager/entBenefitAppr/entBenefitAppr";
	}
	@RequestMapping(value="view")
	public String toView(Model model,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(isLeander(user.getId())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("officeId", 0);
			params.put("officeType", "");
			params.put("roles", "-108,-111");
			params.put("pageNum", 1);
			params.put("pageSize", 120);

			PageHelper.startPage(params);
			List<SysUser> list = sysUserMapper.findPageInfo(params);
			
			model.addAttribute("mlist", list);
		}
		model.addAttribute("status", "view");
		return "manager/entBenefitAppr/entBenefitAppr";
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="list")
	public String list(@RequestParam Map<String, Object> params,Model model,String viewStatus,HttpSession session) {
		String[] str = viewStatus.split(",");
		if(str.length>0)
			viewStatus = str[0];

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(!isLeander(user.getId())) {
			params.put("managerId", user.getId());
		}
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		params.put("sortColumns", "create_date");
		PageInfo<EntBenefitApprVO> page = entBenefitApprService.findPage(params);
		model.addAttribute("page", page);
		return "view".equals(viewStatus)?"manager/entBenefitAppr/entBenefitAppr-list-view":"manager/entBenefitAppr/entBenefitAppr-list";
	}
	
	@RequestMapping(value="detail")
	public String detail(Long enterpriseId,Long id,Model model){
		EnterpriseVo pojo = enterpriseService.getById(enterpriseId);
		model.addAttribute("enterprise", pojo);
		EntBenefitAppr appr = this.entBenefitApprService.getById(id);
		model.addAttribute("entBenefitAppr", appr);
		model.addAttribute("peopleNumber", this.enterpriseUserService.findEnterprisePeopleNumber(enterpriseId));
		model.addAttribute("curTotal", getCurrentTotal(enterpriseId));
		
		return "manager/entBenefitAppr/enterprise-detail";
	}
	
	private BigDecimal getCurrentTotal(Long enterpriseId) {
		BigDecimal total = BigDecimal.ZERO;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", 1);
		params.put("pageSize", 10);
		
		params.put("enterpriseId", enterpriseId);
		params.put("status", "2");
		params.put("curYear", SeasonUtil.getNowYear());
		params.put("curSeason", SeasonUtil.getNowSeason());
		PageInfo<EntBenefitApprVO> page = entBenefitApprService.findPage(params);
		if(page !=null && !page.getList().isEmpty()) {
			for (EntBenefitApprVO v : page.getList()) {
				if(v.getApplyLimit() != null) {
					total=total.add(v.getApplyLimit());
				}
			}
		}
		
		return total;
	}
	
	/**
	 * 
	 * 功能说明：审核发放内购券
	 * 日期:	2015年9月15日
	 * 开发者:宋艳垒
	 *
	 * @param enterpriseId 企业id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toCheck",method=RequestMethod.POST)
	public String toCheck(Long enterpriseId,Long id,Model model,HttpSession session){
		EnterpriseVo pojo = enterpriseService.getById(enterpriseId);
		model.addAttribute("enterprise", pojo);
		model.addAttribute("id", id);
		model.addAttribute("enterpriseId", enterpriseId);
		model.addAttribute("peopleNumber", this.enterpriseUserService.findEnterprisePeopleNumber(enterpriseId));
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj != null){
			SysUser user = (SysUser)obj;
			model.addAttribute("updName", user.getUsername());
		}
		model.addAttribute("curTotal", getCurrentTotal(enterpriseId));
		
		return "manager/entBenefitAppr/enterprise-check";
	}

	@RequestMapping(value="doCheck",method=RequestMethod.POST)
	@ResponseBody
	public ActResult doCheck(@RequestBody EntBenefitAppr pojo,Model model,HttpSession session){
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		
		ActResult<String> ret = ActResult.success(null);
		if(pojo.getId()==null) {
			
			pojo.setId(dbUtils.CreateID());
			pojo.setCreateDate(new Date());
			pojo.setCurYear(SeasonUtil.getNowYear()+"");
			pojo.setCurSeason(SeasonUtil.getNowSeason()+"");
			pojo.setStatus(1);
			pojo.setUpdateDate(new Date());
			if(obj != null){
				SysUser user = (SysUser)obj;
				pojo.setUpdateUser(user.getUsername());
			}
			
			entBenefitApprService.insert(pojo);
		}
		String updName="";
		if(obj != null){
			SysUser user = (SysUser)obj;
			updName = user.getUsername();
		}
		Long flwId = dbUtils.CreateID();
		int r = entBenefitFacade.grantBenefit(pojo.getId(), pojo.getEnterpriseId(), pojo.getApplyLimit(), flwId, updName);
		
		if(r<0){
			ret.setSuccess(false);
			ret.setMsg("审批记录已被处理过");
			return ret;
		} else {
			return ActResult.success(flwId);
		}
	}
	

	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}
}

