package com.wode.factory.company.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.StringUtils;
import com.wode.factory.company.facade.EntBenefitFacade;
import com.wode.factory.company.query.EnterpriseStructureVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseStructureService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.UserFactory;

@Controller
@RequestMapping("company/transfer")
public class TransferBenefitController extends BaseCompanyController {
	@Autowired
	EnterpriseStructureService enterpriseStructureService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	EntBenefitFacade entBenefitFacade;

	@RequestMapping(value = "to_transferPage")
	public ModelAndView page(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String result=request.getParameter("result");//划拨成功的标示参数
		// 当前用户
		UserFactory u = this.getSessionUserModel(request);
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(
				u.getSupplierId(), SeasonUtil.getNowYear(),
				SeasonUtil.getNowSeason(), u.getUserName());

		// 福利总额
		BigDecimal allTicketSum = esa.getAllTicketSum();
		// 已划拨福利
		BigDecimal transfeTicketSum = esa.getTransfeTicketSum();
		// 已发放福利
		BigDecimal giveTicketSum = esa.getGiveTicketSum();
		// 剩余福利额度 = 福利总额 - 已发放 - 以划拨
		BigDecimal shengyu = (allTicketSum.subtract(giveTicketSum)).subtract(transfeTicketSum);
		// 预计可划拨福利额度 福利总额/2 - 已划拨福利
		BigDecimal planTransfer = allTicketSum.divide(new BigDecimal(2))
				.subtract(transfeTicketSum);
		// 实际可划拨福利 = 预计可划拨福利 < 剩余福利额度 ? 预计可划拨福利 :剩余福利额度
		BigDecimal canTransfer = planTransfer.compareTo(shengyu) == -1 ? planTransfer
				: shengyu;
		mv.addObject("canTransfer", canTransfer);

		// 全部的合作企业（可划拨福利的企业）
		List<EnterpriseStructureVo> list_entStr = this.enterpriseStructureService
				.selectEnterpriseInfo(u.getSupplierId(), 3,null);
		mv.addObject("ent_info", list_entStr);
		mv.addObject("result", result);
		mv.setViewName("company/cooperate_ent/stamps");
		return mv;
	}

	/**
	 * 划拨福利
	 * 
	 * @param request
	 * @param map
	 * @param from
	 * @return
	 */
	@RequestMapping(value = "transferBenefit")
	@ResponseBody
	public ModelAndView transferBenefit(HttpServletRequest request) {
		//TODO 异常处理
		
		//参数取得
		String[] ids = request.getParameterValues("ent_id");
		//如果没有给企业划拨福利将传过来输入框的默认值 -->请输入划拨额度
		String[] abs = request.getParameterValues("abs");
		
		BigDecimal ticketSum = BigDecimal.ZERO;
		List<Long> subEntIds = new ArrayList<Long>();
		List<BigDecimal> absCashs = new ArrayList<BigDecimal>();
		List<BigDecimal> absTickets = new ArrayList<BigDecimal>();
		
		//参数处理
		for(int i=0;i<ids.length;i++){
			String val = abs[i].trim();
			if(!StringUtils.isEmpty(val)&&(!"请输入划拨额度".equals(val))) {
				BigDecimal bd = new BigDecimal(val);
				//TODO 数值转化异常  请输入划拨额度
				
				if(bd.compareTo(BigDecimal.ZERO) > 0) {
					//友盟企业id
					subEntIds.add(Long.parseLong(ids[i].trim()));
					//现金
					absCashs.add(BigDecimal.ZERO);
					//内购券
					absTickets.add(bd);
					
					//合计计算
					ticketSum = ticketSum.add(bd);
				}
				//TODO 小于0异常				
			}
		}
		
		int result= 0;
		//划拨福利总额大于0，正常操作
		if(ticketSum.compareTo(BigDecimal.ZERO) > 0) {
			//当前用户
			UserFactory u = this.getSessionUserModel(request);
			//当前企业名称
			String opEntName = enterpriseService.getById(u.getSupplierId()).getName();
			
			//划拨处理
			  result = entBenefitFacade.transferBenefit(u.getSupplierId(),
					SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),
					BigDecimal.ZERO, ticketSum, subEntIds, absCashs, absTickets,
					opEntName, u.getUserName());
			
			//TODO 操作异常
			//result <= 0 时 异常处理
		}
		//TODO 处理成功
		ModelAndView mv = new ModelAndView();
		mv.addObject("result",result+"");
		mv.setViewName("redirect:/company/transfer/to_transferPage");
		return mv;
	}
	/**友盟企业管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="to_entmanage")
	public ModelAndView entmanage(HttpServletRequest request)  {
		ModelAndView mv = new ModelAndView();
		List<EnterpriseStructureVo> list_entStr = this.enterpriseStructureService.selectEnterpriseInfo(getSupplierId(request), 3, null);
		
		//盟友
		mv.addObject("ally", list_entStr);
		
		//企业id
		mv.addObject("ent_id", getSupplierId(request));
		//企业名称
		mv.addObject("enterprise", getEnterpriseInfo(request).getName());
		mv.setViewName("company/cooperate_ent/entmanage");
		return mv;
	}
}
