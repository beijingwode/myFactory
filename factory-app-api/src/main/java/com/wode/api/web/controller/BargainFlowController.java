package com.wode.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.user.model.BargainFlowVo;
import com.wode.factory.user.service.BargainFlowVoService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserTicketHisService;

@Controller
@RequestMapping("/bargainFlow")
public class BargainFlowController {

	@Autowired
	private BargainFlowVoService bargainFlowVoService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private UserTicketHisService userTicketHisService;
	@Autowired
	private UserBalanceService userBalanceService;
	
	@RequestMapping()
    public ModelAndView page(Long empId,Long cId,String charge,ModelAndView model,HttpServletRequest request,String wxOpen) {
		if("1".equals(wxOpen) && (cId==0)) {
			return new ModelAndView("redirect:/pay/bargainFlow?empId="+empId+"&cId="+cId+"&charge="+charge);
		}
		
		List<BargainFlowVo> list = null;
		if(cId==3){
			Date now = new Date();
			Calendar calendar = Calendar.getInstance(); 
			calendar.add(Calendar.MONTH, -1);
			UserExchangeTicket query = new UserExchangeTicket();
			query.setUserId(empId);
			query.setLimitEnd(calendar.getTime());
			List<UserExchangeTicket> ls = userExchangeTicketService.selectByModel(query);
			//List<UserExchangeTicket> ls = userExchangeTicketService.selectExByUser(empId);
			for (UserExchangeTicket t : ls) {
				if(t.getLimitEnd().before(now)) {
					if(t.getStatus()<2) {
						t.setStatus(3);	// 标记已过期
					}
				}
				if (t.getTicketNote()!=null && t.getTicketNote().length()>9) {
					String ticketNote = t.getTicketNote().substring(0, 7);
					ticketNote+="...";
					t.setTicketNote(ticketNote);
				}
			}
			model.addObject("empId", empId);	
			if(ls.isEmpty()) {
				model.setViewName("nobenefits3");
				return model;
			}
			model.addObject("info", ls);
			model.setViewName("exchangeTicket");
			return model;
		}else {
			list = getLogFromQiye(empId, cId);
		}
		model.addObject("localHtml", com.wode.api.util.Constant.pageFont);
		model.addObject("title", getTitle(cId));
		model.addObject("info", list);

		model.addObject("cId", cId);
		model.addObject("charge", charge);
		model.addObject("ub", userBalanceService.findByUserAndType(empId, cId));
		model.setViewName("benefits");
		return model;
	}
	
	/**
	 * 查询现金及内购券流水
	 * @param empId
	 * @param cId
	 * @return
	 */
	private List<BargainFlowVo> getLogFromQiye(Long empId, Long cId) {
		List<BargainFlowVo> list;
		PageInfo<BargainFlowVo> p=bargainFlowVoService.findByQuery(empId, cId, null, 1, 50);
		list = p.getList();
		//根据操作代码取得操作属性
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		
		for (BargainFlowVo bargainFlowVo : list) {
			String code = bargainFlowVo.getOpCode();
			EntParamCode epc = mapCode.get(code);
			bargainFlowVo.setValue(getFlg(epc.getValue()));
			bargainFlowVo.setIconUrl("icon_" + code + ".png");
		}
		return list;
	}
	
	/**
	 * 查询换购券流水
	 * @param empId
	 * @return
	 */
	private List<BargainFlowVo> getPurchasedFlow(Long empId,Long ticketId) {
		List<BargainFlowVo> list = new ArrayList<BargainFlowVo>();
		PageInfo<UserTicketHis>  p =userTicketHisService.findPageListByUserid(empId,ticketId, 1, 50);
		//根据操作代码取得操作属性
		Map<String,EntParamCode> mapCode = entParamCodeService.getBenefitFlowCode();
		
		for (UserTicketHis uth : p.getList()) {
			BargainFlowVo vo = new BargainFlowVo();
			vo.setOpDate(uth.getOpDate());
			vo.setOpCode(uth.getOpCode()); 	
			vo.setNote(uth.getNote());
			EntParamCode epc = mapCode.get(uth.getOpCode());
			vo.setValue(getFlg(epc.getValue()));
			vo.setAmount(uth.getTicket());
			vo.setIconUrl("icon_"+uth.getOpCode()+".png");
			list.add(vo);
			
		}
		return list;
	}
	
	private String getTitle(Long cId) {
		String title = "现金明细";
		if(cId == 1){
			title="内购券明细";
		} else if(cId == 3){
			title="换领币明细";
		}
		return title;
	}

	private String getFlg(String value) {
		String flg = "-";
		if("1".equals(value)) {
			flg="+";
		}
		return flg;
	}

	private BigDecimal getLeft(UserExchangeTicket t) {
		return t.getEmpAvgAmount().subtract(t.getUsedAmount());
	}
	/**
	 * 换领券流水
	 * @param empId
	 * @param cId
	 * @param ticketId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/exChangeTicketFlow" })
    public ModelAndView page(Long empId,Long cId,Long ticketId,ModelAndView model,HttpServletRequest request) {
		List<BargainFlowVo> list = null;
		list = getPurchasedFlow(empId,ticketId);
		UserExchangeTicket ut = userExchangeTicketService.getById(ticketId);
		if(ut==null) {
			model.setViewName("nobenefits3");
			return model;
		}
		if (ut.getLimitEnd().before(new Date())) {//过了有效期
			model.addObject("qId",2);
		}else{
			model.addObject("qId", 1);
		}
		
		model.addObject("title", getTitle(cId));
		model.addObject("info", list);
		model.addObject("ut", ut);
		model.addObject("cId", cId);
		model.setViewName("benefits");
		return model;
	}
}
