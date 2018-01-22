package com.wode.factory.company.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.company.facade.EntBenefitFacade;
import com.wode.factory.company.query.EmpLevelCountVo;
import com.wode.factory.company.query.EntBenefitFlowVo;
import com.wode.factory.company.query.GiveBenefitRecordVo;
import com.wode.factory.company.service.EmpBalanceService;
import com.wode.factory.company.service.EmpSeasonActService;
import com.wode.factory.company.service.EntBenefitApprService;
import com.wode.factory.company.service.EntBenefitFlowService;
import com.wode.factory.company.service.EntParamCodeService;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.model.EmpSeasonAct;
import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.util.AppPushUtil;
import com.wode.factory.supplier.util.ExcelUtil;

/**
 * 福利管理
 * 
 * @author liangkq
 *
 */
@Controller
@RequestMapping("company/benefit")
public class EntBenefitController extends BaseCompanyController {

	@Autowired
	private EmpSeasonActService empSeasonActService;
	@Autowired
	private EnterpriseUserService enterpriseUserService;
	@Autowired
	private EntBenefitFacade entBenefitFacade;
	@Autowired
	private EntBenefitApprService entBenefitApprService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private EntBenefitFlowService entBenefitFlowService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private EmpBalanceService empBalanceService;

	/**
	 * 查询企业季度福利额度
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getEntSeasonAct")
	public ModelAndView getEntSeasonAct(HttpServletRequest request) {

		// 当前用户
		UserFactory u = getSessionUserModel(request);
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(u.getSupplierId(), SeasonUtil.getNowYear(),
				SeasonUtil.getNowSeason(), u.getUserName());

		ModelAndView mv = new ModelAndView();
		mv.setViewName("company/benefit/amount");

		mv.addObject("info", esa);
		// 福利总额
		BigDecimal allTicketSum = esa.getAllTicketSum();
		// 本季度获取内购券总额
		//BigDecimal currentTicketSum = esa.getCurrentTicketSum();
		// 已划拨福利
		BigDecimal transfeTicketSum = esa.getTransfeTicketSum();
		// 已发放福利
		BigDecimal giveTicketSum = esa.getGiveTicketSum();
		// 剩余福利额度 = 福利总额 - 已发放 - 以划拨
		BigDecimal shengyu = (allTicketSum.subtract(giveTicketSum)).subtract(transfeTicketSum);
		// 预计可划拨福利额度 福利总额/2 - 已划拨福利
		BigDecimal planTransfer = allTicketSum.divide(new BigDecimal(2)).subtract(transfeTicketSum);
		// 实际可划拨福利 = 预计可划拨福利 < 剩余福利额度 ? 预计可划拨福利 :剩余福利额度
		BigDecimal canTransfer = planTransfer.compareTo(shengyu) == -1 ? planTransfer : shengyu;
		mv.addObject("canTransfer", canTransfer);

		return mv;
	}

	/**
	 * @param giveBenefitRecordVo
	 * @return
	 */
	@RequestMapping("/goGiveBenefit")
	public ModelAndView goGiveBenefit(GiveBenefitRecordVo giveBenefitRecordVo) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("company/benefit/giveBenefit");
		return mv;
	}

	/**
	 * 加载福利发放记录
	 * 
	 * @param request
	 * @param giveBenefitRecordVo
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView getPageInfo(HttpServletRequest request, GiveBenefitRecordVo giveBenefitRecordVo) {
		giveBenefitRecordVo.setEnterpriseId(getSupplierId(request));
		giveBenefitRecordVo.setCurYear(SeasonUtil.getNowYear() + "");
		giveBenefitRecordVo.setCurSeason(SeasonUtil.getNowSeason() + "");

		PageInfo<GiveBenefitRecordVo> pageInfo = empSeasonActService.selectPageInfo(giveBenefitRecordVo);
		Enterprise ent = this.enterpriseService.getById(getSupplierId(request));
		ModelAndView mv = new ModelAndView();
		mv.addObject("page", pageInfo);
		mv.addObject("query", giveBenefitRecordVo);
		// 企业的福利级别
		mv.addObject("welfareLevel", ent.getWelfareLevel());
		mv.setViewName("company/benefit/giveBenefit");
		return mv;
	}

	/**
	 * 根据id查询企业用户信息
	 * 
	 * @param request
	 * @param giveBenefitRecordVo
	 * @return
	 */
	@RequestMapping("/getEnterpriseUserById")
	@ResponseBody
	public GiveBenefitRecordVo getEnterpriseUserById(HttpServletRequest request, Long empId) {
		EnterpriseUser user = enterpriseUserService.selectById(empId);
		GiveBenefitRecordVo vo = new GiveBenefitRecordVo();
		vo.setUser(user);
		/**
		 * 本季度发放的福利
		 * */
		EmpSeasonAct empSeasonAct = empSeasonActService.getEmpSeasonAct(empId, SeasonUtil.getNowYear(),
				SeasonUtil.getNowSeason(), getSupplierId(request));
		//现金余额
		UserBalance ubCash = empBalanceService.fetchByUserAndName(empId, "balance");
		//内购券余额
		UserBalance ubTicket = empBalanceService.fetchByUserAndName(empId, "companyTicket");
		if (ubCash != null&&empSeasonAct!=null) {
			//剩余的现金金额大于等于本季度发放的金额。回收本季度发放的现金金额
			if(ubCash.getBalance().compareTo(empSeasonAct.getGiveCashSum())>-1){
				vo.setGiveCashSason(empSeasonAct.getGiveCashSum());
			}else{
				vo.setGiveCashSason(ubCash.getBalance());
			}
		}else{
			vo.setGiveCashSason(new BigDecimal(0));
		}
		if(ubTicket!=null){
			vo.setGiveTicketSason(ubTicket.getBalance());
		}else{
			vo.setGiveTicketSason(new BigDecimal(0));
		}

		// 当前用户
		UserFactory u = getSessionUserModel(request);
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(u.getSupplierId(), SeasonUtil.getNowYear(),
				SeasonUtil.getNowSeason(), u.getUserName());

		// 查询企业剩余福利及现金额度
		vo.setEntTicketSasonSum(esa.getAllTicketSum());
		vo.setEntCashSasonSum(esa.getAllCashSum());

		return vo;
	}

	/**
	 * 进入级别发放福利界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toGiveBenefitForLevel")
	@ResponseBody
	public Map<String, Object> toGiveBenefitForLevel(HttpServletRequest request) {
		Enterprise ent = this.enterpriseService.getById(getSupplierId(request));
		Integer welfareLevel = ent.getWelfareLevel();
		Map<String, Object> map = new HashMap<String, Object>();

		// 查询各等级人数
		EmpLevelCountVo vo = new EmpLevelCountVo();
		vo.setEnterpriseId(ent.getId());
		List<EmpLevelCountVo> lsc = enterpriseUserService.selectLevelCount(vo);

		// 各等级人数list做成
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= welfareLevel; i++) {
			int cnt = 0;

			for (EmpLevelCountVo empLevelCountVo : lsc) {
				if (i == empLevelCountVo.getWelfareLevel()) {
					cnt = empLevelCountVo.getLevelCount();
					break;
				}
			}
			list.add(cnt);
		}
		map.put("list", list);

		// 当前用户
		UserFactory u = getSessionUserModel(request);
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(u.getSupplierId(), SeasonUtil.getNowYear(),
				SeasonUtil.getNowSeason(), u.getUserName());
		// 可用
		map.put("ticketSum",
				esa.getAllTicketSum().subtract((esa.getGiveTicketSum().add(esa.getTransfeTicketSum()))).intValue());
		map.put("cashSum", esa.getAllCashSum().subtract(esa.getGiveCashSum()).intValue());

		return map;
	}

	/**
	 * 发放福利(单人)
	 * 
	 * @param request
	 * @param absCashs
	 * @param absTickets
	 * @param empIds
	 * @param empNames
	 * @return
	 */
	@RequestMapping("/giveBenefitForOne")
	@ResponseBody
	public ActResult<Integer> giveBenefitForOne(HttpServletRequest request, String absCashs, String absTickets,
			String empIds, String empNames,String exBenefitType) {
		ActResult<Integer> act = new ActResult<Integer>();
		List<BigDecimal> absCashsList = new ArrayList<BigDecimal>();
		List<BigDecimal> absTicketsList = new ArrayList<BigDecimal>();
		List<Long> empIdsList = new ArrayList<Long>();
		List<String> empNamesList = new ArrayList<String>();
		List<String> exBenefitTypes = new ArrayList<String>();
		BigDecimal ticket = NumberUtil.checkBigDecimal(absTickets);
		BigDecimal cash = NumberUtil.checkBigDecimal(absCashs);
		
		empIdsList.add(Long.parseLong(empIds));
		absTicketsList.add(ticket);
		absCashsList.add(cash);
		empNamesList.add(empNames);
		exBenefitTypes.add(exBenefitType);
		
		int r = entBenefitFacade.giveBenefit(getSupplierId(request), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),
				cash, ticket, empIdsList, absCashsList, absTicketsList, empNamesList, this.getCurrentUserName(request),exBenefitTypes);
		switch (r) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(1);
			
			BigDecimal amount = BigDecimal.ZERO;
			String cId = "1";
			String msg = "公司向您发放福利";
			if(cash.compareTo(BigDecimal.ZERO)>0){
				msg += "，现金券："+cash;
				cId = "0";
				amount = cash;
			}
			if(ticket.compareTo(BigDecimal.ZERO)>0){
				msg += "，内购券："+ticket;
				if(amount.equals(BigDecimal.ZERO)) {
					amount = ticket;
				}
			}
			msg += "，可以用来购买心仪商品，或转发给亲友。享受真正的员工福利。";
			AppPushUtil.pushMsg(empIdsList.get(0), "公司发放福利", msg);
			
			AppPushUtil.pushWxBalace(empIdsList.get(0), amount.toString(), TimeUtil.getStringDateShort(), msg, cId);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3);
			break;
		case -1001:
			act.setSuccess(false);
			act.setMsg("当前季度已结转不能进行该操作");
			act.setData(-1001);
			break;

		default:
			break;
		}

		return act;
	}

	/**
	 * 发放福利(批量)
	 * 
	 * @param request
	 * @param absCashs
	 * @param absTickets
	 * @param empIds
	 * @param empNames
	 * @return
	 */
	@RequestMapping("/giveBenefit")
	@ResponseBody
	public ActResult<Integer> giveBenefit(HttpServletRequest request, String absCashs, String absTickets,String exBenefitType) {
		ActResult<Integer> act = new ActResult<Integer>();
		BigDecimal cashSum = BigDecimal.ZERO;
		BigDecimal ticketSum = BigDecimal.ZERO;

		List<Long> empIdsList = new ArrayList<Long>();
		List<BigDecimal> absCashsList = new ArrayList<BigDecimal>();
		List<BigDecimal> absTicketsList = new ArrayList<BigDecimal>();
		List<String> empNamesList = new ArrayList<String>();
		List<String> exBenefitTypes = new ArrayList<String>();

		// 当前用户
		UserFactory u = getSessionUserModel(request);
		Long entId = u.getSupplierId();

		// 画面输入
		String[] listT = absTickets.split("#");
		String[] listC = absCashs.split("#");
		for (int i = 0; i < listT.length; i++) {
			// 券
			BigDecimal absT = new BigDecimal(listT[i]).abs();
			// 现金
			BigDecimal absC = new BigDecimal(listC[i]).abs();

			// 两方非零时 处理
			if (!BigDecimal.ZERO.equals(absT) || !BigDecimal.ZERO.equals(absC)) {

				// 查询出该福利级别的员工
				EnterpriseUser model = new EnterpriseUser();
				model.setEnterpriseId(entId);
				model.setWelfareLevel(i + 1);
				model.setLogout(Byte.valueOf(0 + ""));
				List<EnterpriseUser> userList = enterpriseUserService.selectByModel(model);

				for (EnterpriseUser enterpriseUser : userList) {
					// 员工id
					empIdsList.add(enterpriseUser.getId());
					// 现金
					absCashsList.add(absC);
					// 券
					absTicketsList.add(absT);
					// 员工姓名
					empNamesList.add(enterpriseUser.getName());
					// 福利科目
					exBenefitTypes.add(exBenefitType);
					
					// 现金合计
					cashSum = cashSum.add(absC);
					// 券合计
					ticketSum = ticketSum.add(absT);
				}
			}
		}

		// 两方非零时 处理
		if (!BigDecimal.ZERO.equals(cashSum) || !BigDecimal.ZERO.equals(ticketSum)) {
			int r = entBenefitFacade.giveBenefit(entId, SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(), cashSum,
					ticketSum, empIdsList, absCashsList, absTicketsList, empNamesList, u.getUserName(),exBenefitTypes);
			switch (r) {
			case 1:
				act.setSuccess(true);
				act.setMsg("正常处理");
				act.setData(1);
				
				for (int i = 0; i < empIdsList.size(); i++) {
					BigDecimal amount = BigDecimal.ZERO;
					String cId = "1";
					String msg = "公司向您发放福利";
					if(absCashsList.get(i).compareTo(BigDecimal.ZERO)>0){
						msg += "，现金券："+absCashsList.get(i);
						cId = "0";
						amount = absCashsList.get(i);
					}
					if(absTicketsList.get(i).compareTo(BigDecimal.ZERO)>0){
						msg += "，内购券："+absTicketsList.get(i);
						if(amount.equals(BigDecimal.ZERO)) {
							amount = absTicketsList.get(i);
						}
					}
					msg += "，可以用来购买心仪商品，或转发给亲友。享受真正的员工福利。";
					AppPushUtil.pushMsg(empIdsList.get(i), "公司发放福利", msg);
					
					AppPushUtil.pushWxBalace(empIdsList.get(i), amount.toString(), TimeUtil.getStringDateShort(), msg, cId);
				}
				
				break;
			case 0:
				act.setSuccess(false);
				act.setMsg("系统异常");
				act.setData(0);
				break;
			case -1:
				act.setSuccess(false);
				act.setMsg("现金余额不足");
				act.setData(-1);
				break;
			case -2:
				act.setSuccess(false);
				act.setMsg("内购券余额不足");
				act.setData(-2);
				break;
			case -3:
				act.setSuccess(false);
				act.setMsg("现金、内购券余额都不足");
				act.setData(-3);
				break;
			case -1001:
				act.setSuccess(false);
				act.setMsg("当前季度已结转不能进行该操作");
				act.setData(-1001);
				break;

			default:
				break;
			}

			return act;
		} else {
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(1);
			return act;
		}

	}

	/**
	 * 回收福利
	 * 
	 * @param request
	 * @param absCashs
	 * @param absTickets
	 * @param empIds
	 * @param empNames
	 * @return
	 */
	@RequestMapping("/recycleBenefit")
	@ResponseBody
	public ActResult<Integer> recycleBenefit(HttpServletRequest request, BigDecimal absCashs, BigDecimal absTickets,
			Long empIds, String empNames) {
		ActResult<Integer> act = new ActResult<Integer>();
		List<BigDecimal> absCashsList = new ArrayList<BigDecimal>();
		// 清零处理
		if (absCashs == null)
			absCashs = BigDecimal.ZERO;
		if (absTickets == null)
			absTickets = BigDecimal.ZERO;

		absCashsList.add(absCashs);
		List<BigDecimal> absTicketsList = new ArrayList<BigDecimal>();
		absTicketsList.add(absTickets);
		List<Long> empIdsList = new ArrayList<Long>();
		empIdsList.add(empIds);
		List<String> empNamesList = new ArrayList<String>();
		empNamesList.add(empNames);
		int result;
		String erro = "";
		try {
			result = entBenefitFacade.reCoverBenefit(getSupplierId(request), SeasonUtil.getNowYear(),
					SeasonUtil.getNowSeason(), absCashs, absTickets, empIdsList, absCashsList, absTicketsList,
					empNamesList, this.getCurrentUserName(request));
		} catch (BenefitLessException e) {
			result = -4;
			erro = e.getMessage();
		}
		switch (result) {
		case 1:
			act.setSuccess(true);
			act.setMsg("正常处理");
			act.setData(1);
			break;
		case 0:
			act.setSuccess(false);
			act.setMsg("系统异常");
			act.setData(0);
			break;
		case -1:
			act.setSuccess(false);
			act.setMsg("现金余额不足");
			act.setData(-1);
			break;
		case -2:
			act.setSuccess(false);
			act.setMsg("内购券余额不足");
			act.setData(-2);
			break;
		case -3:
			act.setSuccess(false);
			act.setMsg("现金、内购券余额都不足");
			act.setData(-3);
			break;
		case -4:
			act.setSuccess(false);
			act.setMsg(erro);
			act.setData(-4);
			break;
		case -1001:
			act.setSuccess(false);
			act.setMsg("当前季度已结转不能进行该操作");
			act.setData(-1001);
			break;

		default:
			break;
		}

		return act;
	}

	/**
	 * 企业申请福利额度
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyBenefit")
	@ResponseBody
	public int applyBenefit(HttpServletRequest request) {
		long id = dbUtils.CreateID();
		EntBenefitAppr model = new EntBenefitAppr();
		model.setId(id);
		model.setEnterpriseId(getSupplierId(request));
		model.setCurYear(SeasonUtil.getNowYear() + "");
		model.setCurSeason(SeasonUtil.getNowSeason() + "");
		model.setCreateDate(new Date());
		model.setStatus(1);
		entBenefitApprService.save(model);

		return 1;
	}

	/**
	 * 查询福利申请记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getApplyBenefitRecord")
	public ModelAndView getApplyBenefitRecord(HttpServletRequest request) {
		EntBenefitAppr model = new EntBenefitAppr();
		String curSeason = SeasonUtil.getNowSeason() + "";
		String curYear = SeasonUtil.getNowYear() + "";
		model.setEnterpriseId(getSupplierId(request));
		model.setCurYear(curYear);
		model.setCurSeason(curSeason);
		List<EntBenefitAppr> list = entBenefitApprService.selectByModel(model);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("company/benefit/applyBenefit");
		mv.addObject("curSeason", curSeason);
		mv.addObject("curYear", curYear);

		if (list.size() > 0) {
			mv.addObject("info", list.get(0));
		}
		return mv;
	}

	/**
	 * 跳转现金储值页面
	 * 
	 * @return
	 */
	@RequestMapping("/toSaveCash")
	public ModelAndView toSaveCash(HttpServletRequest request) {
		String success = request.getParameter("success");
		String error = request.getParameter("error");
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("error", error);
		mv.setViewName("company/benefit/saveCash");
		return mv;
	}

	/**
	 * 进入企业福利流水页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("entBenefitFlow")
	public ModelAndView entBenefitFlow(HttpServletRequest request, EntBenefitFlowVo entBenefitFlowVo, String type) {
		entBenefitFlowVo.setEnterpriseId(getSupplierId(request));

		ModelAndView mv = new ModelAndView();

		// pageNumber等于0 的时候，是第一次查询
		if (StringUtils.isNullOrEmpty(type)) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,1);
			entBenefitFlowVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DATE,-1);
			cal.set(Calendar.DAY_OF_MONTH, 1);// 本月第一天
			entBenefitFlowVo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			type = "1";
		} else if ("2".equals(type)) {
			// 一个月
			Calendar cal = Calendar.getInstance();
			entBenefitFlowVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			entBenefitFlowVo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		} else if ("3".equals(type)) {
			Calendar cal = Calendar.getInstance();
			entBenefitFlowVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -90);
			entBenefitFlowVo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		}
		mv.addObject("startDate", entBenefitFlowVo.getStartDate());
		mv.addObject("endDate", entBenefitFlowVo.getEndDate());
		mv.addObject("opCode", entBenefitFlowVo.getOpCode());
		mv.addObject("type", type);

		PageInfo<EntBenefitFlowVo> page = entBenefitFlowService.selectPageInfo(entBenefitFlowVo);
		Map<String, EntParamCode> map = entParamCodeService.getBenefitFlowCode();
		List<EntParamCode> codeList = new ArrayList<EntParamCode>();
		if (map != null) {
			// 查询code为“1”开头的数据
			for (Map.Entry<String, EntParamCode> entry : map.entrySet()) {
				if (entry.getKey().startsWith("1")) {
					codeList.add(entry.getValue());
				}
			}
		}
		mv.setViewName("company/benefit/benefitFlow");

		mv.addObject("page", page);
		mv.addObject("query", entBenefitFlowVo);
		mv.addObject("codeList", codeList);
		return mv;
	}

	private void getResponse(HttpServletResponse response, String str) {
		PrintWriter pw = null;
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(str);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	/**
	 * 上传模板批量发放福利
	 * 
	 * @param file
	 *            模板文件
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/allSend")
	@ResponseBody
	public ActResult allSend(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ActResult<String> act = new ActResult<String>();
		if (file.isEmpty()) {
			act.setSuccess(false);
			act.setMsg("请选择文件后上传");
			return act;
		}
		if (!(file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"))) {
			act.setSuccess(false);
			act.setMsg("只能上传.xlsx格式的文件！");
			return act;
		}
		if (file.getSize() > (1024 * 1024 * 5)) {
			act.setSuccess(false);
			act.setMsg("文件大小不能大于5M！");
			return act;
		}
		// 上传文件并校验数据有效性
		try {

			List<BigDecimal> absCashsList = new ArrayList<BigDecimal>();// 发放现金金额集合
			List<BigDecimal> absTicketsList = new ArrayList<BigDecimal>();// 发放福利金额集合
			List<Long> empIdsList = new ArrayList<Long>();// 发放福利员工id集合
			List<String> empNamesList = new ArrayList<String>();// 发放福利员工姓名集合
			BigDecimal ticketSum = new BigDecimal(0);// 发放内购券总额
			BigDecimal cashSum = new BigDecimal(0);// 发放现金总额
			List<String> exBenefitTypes = new ArrayList<String>();

			// 读取Excel生成的数据（包含中间的空行）
			List<String[]> list = ExcelUtil.fileUpload(file, request);
			if (list.isEmpty()) {
				act.setSuccess(false);
				act.setMsg("上传文件内容有误");
				return act;
			}
			act = ExcelUtil.checkoutExcelData(list);

			if (act.isSuccess()) {
				// 校验手机号码在DB中是否存在
				for (int i = 0; i < list.size(); i++) {
					// 电话号码
					String phone = list.get(i)[1];
					BigDecimal ticket = NumberUtil.checkBigDecimal(list.get(i)[7]);// 内购券
					BigDecimal cash = NumberUtil.checkBigDecimal(list.get(i)[8]);// 现金
					if (!StringUtils.isEmpty(phone) && (NumberUtil.isGreaterZero(ticket) || NumberUtil.isGreaterZero(cash))) {
						EnterpriseUser model = new EnterpriseUser();
						model.setEnterpriseId(getSupplierId(request));
						model.setPhone(phone);
						List<EnterpriseUser> userList = enterpriseUserService.selectByModel(model);
						if (userList == null || userList.size() == 0) {
							StringBuffer error = new StringBuffer();
							error.append("第");
							error.append((i + 2));
							error.append("行、第1列，根据此手机号码查询不到员工！");
							act.setSuccess(false);
							act.setMsg(error.toString());
							return act;
						} else {
							EnterpriseUser user = userList.get(0);
							empIdsList.add(user.getId());
							empNamesList.add(user.getName());
							absTicketsList.add(ticket);
							absCashsList.add(cash);
							ticketSum = ticketSum.add(ticket);
							cashSum = cashSum.add(cash);
							
							exBenefitTypes.add(fomartBenefitType(list.get(i)[9]));
						}

					}
				}

				// 批量插入福利发放数据
				int r = entBenefitFacade.giveBenefit(getSupplierId(request), SeasonUtil.getNowYear(),
						SeasonUtil.getNowSeason(), cashSum, ticketSum, empIdsList, absCashsList, absTicketsList,
						empNamesList, "",exBenefitTypes);

				if (r <= 0) {
					// 插入数据失败
					act.setSuccess(false);
					act.setMsg("现金额度／（福利额度）总和超过可发放额度!");
					return act;
				} else {
					act.setSuccess(true);
					act.setMsg("发放福利成功");

					
					for (int i = 0; i < empIdsList.size(); i++) {
						BigDecimal amount = BigDecimal.ZERO;
						String cId = "1";
						String msg = "公司向您发放福利";

						if(absCashsList.get(i).compareTo(BigDecimal.ZERO)>0){
							msg += "，现金券："+absCashsList.get(i);
							cId = "0";
							amount = absCashsList.get(i);
						}
						if(absTicketsList.get(i).compareTo(BigDecimal.ZERO)>0){
							msg += "，内购券："+absTicketsList.get(i);
							if(amount.equals(BigDecimal.ZERO)) {
								amount = absTicketsList.get(i);
							}
						}
						msg += "，可以用来购买心仪商品，或转发给亲友。享受真正的员工福利。";
						AppPushUtil.pushMsg(empIdsList.get(i), "公司发放福利", msg);
						
						AppPushUtil.pushWxBalace(empIdsList.get(i), amount.toString(), TimeUtil.getStringDateShort(), msg, cId);
					}					
				}

			} else {
				//this.getResponse(response, act.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			act.setSuccess(false);
			act.setMsg("文件上传失败！");
			return act;
		}

		return act;
	}

	/**
	 * 根据输入内容判断福利科目种类
	 * @param string
	 * @return
	 */
	private String fomartBenefitType(String string) {
		if(!StringUtils.isEmpty(string)) {
			if(string.contains("生日")) {
				return "1";
			} else if(string.contains("节")) {
				return "2";				
			} else {
				return "";
			}
		}
		return null;
	}

	/**
	 * 下载批量发放模板
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downloadTemlplate")
	@ResponseBody
	public int downloadTemlplate(HttpServletRequest request) {

		EnterpriseUser model = new EnterpriseUser();
		model.setEnterpriseId(getSupplierId(request));
		List<EnterpriseUser> list = enterpriseUserService.selectByModel(model);
		int r = ExcelUtil.writeExcelData(list, request);
		return r;
	}
		
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,GiveBenefitRecordVo giveBenefitRecordVo) throws Exception {

		giveBenefitRecordVo.setEnterpriseId(getSupplierId(request));
		giveBenefitRecordVo.setCurYear(SeasonUtil.getNowYear() + "");
		giveBenefitRecordVo.setCurSeason(SeasonUtil.getNowSeason() + "");
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("发放福利记录"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("员工序号");
        headers.add("姓名");
        headers.add("性别");
        headers.add("年龄");
        headers.add("邮箱");
        headers.add("手机号码");
        headers.add("工龄");
        headers.add("福利级别");
        headers.add("福利额度(本季)");
        headers.add("现金储值(本季)");
                
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
        giveBenefitRecordVo.setPageNumber(1);
        giveBenefitRecordVo.setPageSize(5000);
		PageInfo<GiveBenefitRecordVo> pageInfo = empSeasonActService.selectPageInfo(giveBenefitRecordVo);

		for (GiveBenefitRecordVo vo : pageInfo.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("员工序号");
            row.createCell(col++).setCellValue(vo.getEmpNumber()+"");
            //headers.add("姓名");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getName()==null?"":vo.getName());
            //headers.add("性别");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getSex()==null?"未设置":vo.getSex());
            //headers.add("年龄");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getAge()==null?"":vo.getAge()+"");
            //headers.add("邮箱");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getPhone()==null?"":vo.getPhone());
            //headers.add("手机号码");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getPhone()==null?"":vo.getPhone());
            //headers.add("工龄");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getSeniority()==null?"":vo.getSeniority()+"");
            //headers.add("福利级别");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getWelfareLevel()+"");
            //headers.add("福利额度(本季)");
            row.createCell(col++).setCellValue(vo.getGiveTicketSason()==null?"":vo.getGiveTicketSason().setScale(2, BigDecimal.ROUND_DOWN) + "");
            //headers.add("现金储值(本季)");
            row.createCell(col++).setCellValue(vo.getGiveCashSason()==null?"":vo.getGiveCashSason().setScale(2, BigDecimal.ROUND_DOWN) + "");
		}
				
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	


	private String getFileNameForSave(HttpServletRequest request) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = "发放福利记录"+ TimeUtil.dateToStr(new Date(),"_yyyyMMdd") +".xls";
		String new_filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		String rtn = "filename=\"" + new_filename + "\"";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
				rtn = "filename=\"" + new_filename + "\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
}
