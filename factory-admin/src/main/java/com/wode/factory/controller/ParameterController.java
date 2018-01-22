/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntParamCodeVo;
import com.wode.factory.service.EntParamCodeService;

@Controller
@RequestMapping("parameter")
public class ParameterController {
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping("base")
	public String toPageAttrView(Model model,HttpSession session) {
		return "manager/mparameter";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(Model model,HttpSession session) {
		
		List<EntParamCodeVo> list = entParamCodeService.selectBanks();
		Collections.sort(list,new Comparator<EntParamCodeVo>(){

			@Override
			public int compare(EntParamCodeVo arg0, EntParamCodeVo arg1) {
				int i1 = NumberUtil.toInt(arg0.getCode());
				int i2 = NumberUtil.toInt(arg1.getCode());
				return i1-i2;
			}});
		
		model.addAttribute("list", list);
		return "manager/mparameter-list";
	}

	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toDelete", method = RequestMethod.POST)
	public String toDelete(Model model,Long id) {
		EntParamCode p = new EntParamCode();
		p.setId(id);
		model.addAttribute("epc", entParamCodeService.selectByModel(p).get(0));
		return "manager/mparameter-del";
	}
	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toAdd", method = RequestMethod.POST)
	public String toAdd(Model model,Long id) {
		return "manager/mparameter-add";
	}
	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toUpdate", method = RequestMethod.POST)
	public String toUpdate(Model model,Long id) {
		EntParamCode p = new EntParamCode();
		p.setId(id);
		model.addAttribute("epc", entParamCodeService.selectByModel(p).get(0));
		return "manager/mparameter-upd";
	}
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public ActResult<Object> del(Long epcId) {
		entParamCodeService.delete(epcId);
		redisUtil.del("REDIS_ENT_PARAM_CODE_040");
		return ActResult.success(null);
	}
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/upd")
	@ResponseBody
	public ActResult<Object> upd(Long epcId,String bname) {
		EntParamCode p = new EntParamCode();
		p.setId(epcId);
		EntParamCode u = entParamCodeService.selectByModel(p).get(0);
		u.setName(bname);
		entParamCodeService.update(u);

		redisUtil.del("REDIS_ENT_PARAM_CODE_040");
		return ActResult.success(null);
	}
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ActResult<Object> add(String bname) {
		bname = bname.trim();
		List<EntParamCodeVo> list = entParamCodeService.selectBanks();
		Collections.sort(list,new Comparator<EntParamCodeVo>(){
			@Override
			public int compare(EntParamCodeVo arg0, EntParamCodeVo arg1) {
				int i1 = NumberUtil.toInt(arg0.getCode());
				int i2 = NumberUtil.toInt(arg1.getCode());
				return i1-i2;
			}});
		
		for (EntParamCodeVo entParamCodeVo : list) {
			if(bname.equals(entParamCodeVo.getName())){
				return ActResult.fail("银行名称重复");				
			}
		}
		EntParamCode p = new EntParamCode();
		p.setGroupCd("040");
		p.setCode(""+ (NumberUtil.toInt(list.get(list.size()-1).getCode())+1));
		p.setValue("");
		p.setCreateDate(new Date());
		p.setDescr("");
		p.setName(bname);
		p.setStopFlg("0");
		entParamCodeService.insert(p);
		
		redisUtil.del("REDIS_ENT_PARAM_CODE_040");
		return ActResult.success(null);
	}
}

