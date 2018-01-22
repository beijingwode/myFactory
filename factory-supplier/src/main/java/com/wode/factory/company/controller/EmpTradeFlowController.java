package com.wode.factory.company.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.EmpTradeFlowVo;
import com.wode.factory.company.service.EmpBenefitFlowService;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.model.EntParamCode;

/**
 * 员工交易流水管理
 * @author liangkq
 *
 */
@Controller
@RequestMapping("company/tradeFlow")
public class EmpTradeFlowController extends BaseCompanyController {

	@Autowired
	private EmpBenefitFlowService empBenefitFlowService;

	@Autowired
	private EntParamCodeService entParamCodeService;
	
	@RequestMapping("page")
	public ModelAndView getPageInfo(HttpServletRequest request ,EmpTradeFlowVo empTradeFlowVo){
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		
		empTradeFlowVo.setEnterpriseId(getSupplierId(request));
		//pageNumber等于0 的时候，是第一次查询
		if(empTradeFlowVo.getPageNumber()==0){
			Calendar cal = Calendar.getInstance();
			empTradeFlowVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.set(Calendar.DAY_OF_MONTH, 1);//本月第一天
			empTradeFlowVo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		}
		PageInfo<EmpTradeFlowVo> page = empBenefitFlowService.selectPageInfo(empTradeFlowVo );
		for (EmpTradeFlowVo vo : page.getList()) {
			EntParamCode code = mapCode.get(vo.getOpCode());
			vo.setOpName(code.getName());
			vo.setValue(code.getValue());
			//子单查不到则查询订单
			if(vo.getRealPrice() == null) {
				Long orderId = null;
				int index  = vo.getKeyId().indexOf("-");
				if(index==-1) {
					orderId=Long.parseLong(vo.getKeyId());
				} else {
					orderId=Long.parseLong(vo.getKeyId().substring(0, index));
				}
				vo.setRealPrice(empBenefitFlowService.selectOrderRealPrice(orderId));
			}
			//订单总额
			if(vo.getTotalProduct()==null){
				Long orderId = null;
				int index  = vo.getKeyId().indexOf("-");
				if(index==-1) {
					orderId=Long.parseLong(vo.getKeyId());
				} else {
					orderId=Long.parseLong(vo.getKeyId().substring(0, index));
				}
				vo.setTotalProduct(empBenefitFlowService.selectOrderTotalProduct(orderId));
			}
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("company/tradeFlow/empTradeFlow");
		mv.addObject("page",page);
		mv.addObject("query",empTradeFlowVo);
		
		return mv ;
	}
	
}
