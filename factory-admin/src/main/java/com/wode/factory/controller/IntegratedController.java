/**
 * 
 */
package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.DateUtils;
import com.wode.common.util.NumberUtil;
import com.wode.factory.model.StatisticalBenefit;
import com.wode.factory.model.StatisticalExchangeProduct;
import com.wode.factory.model.StatisticalFirstOrder;
import com.wode.factory.model.StatisticalManagerResult;
import com.wode.factory.model.StatisticalSale;
import com.wode.factory.model.StatisticalTrialProduct;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.StatisticalBenefitService;
import com.wode.factory.service.StatisticalExchangeProductService;
import com.wode.factory.service.StatisticalFirstOrderService;
import com.wode.factory.service.StatisticalManagerResultService;
import com.wode.factory.service.StatisticalSaleService;
import com.wode.factory.service.StatisticalTrialProductService;
import com.wode.factory.service.SupplierService;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

/**
 * @author user 综合数据统计Controller
 */

@Controller
@RequestMapping("integrated")
public class IntegratedController {

	// 注入商家Service
	@Autowired
	private SupplierService supplierService;

	// 注入综合查询Service
	// 注入Service接口 商品换领statisticalTrialProductService
	@Autowired
	private StatisticalExchangeProductService statisticalExchangeProductService;
	@Resource
	private StatisticalManagerResultService statisticalManagerResultService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private StatisticalSaleService statisticalSaleService;
	@Autowired
	private StatisticalBenefitService statisticalBenefitService;
	// 注入商家使用Service
	@Autowired
	private StatisticalTrialProductService statisticalTrialProductService;
	@Autowired
	private StatisticalFirstOrderService statisticalFirstOrderService;
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;

	/**
	 * 商品综合查询Controller
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/zongHeShow")
	public String toShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser) obj;

		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		return "tongji/managerTongji/Integrated/show";
	}

	/**
	 * 综合查询的List列表
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/zongHelist")
	public String queryFilter(@RequestParam Map<String, Object> params, ModelMap model, HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();

		PageInfo<StatisticalManagerResult> findList = statisticalManagerResultService.findList(params);
		for (StatisticalManagerResult result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getManagerId());
			if (mSysUser != null) {
				result.setManagerName(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/Integrated/list";
	}

	/**
	 * POI导入数据 商品综合查询的POI导入导出
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/zongHeexportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("招商经理");
		headers.add("员工首单（单）");
		headers.add("换领总额");
		headers.add("试用总额");
		headers.add("生日礼金（人次）");
		headers.add("过节费");
		headers.add("销售总额");
		headers.add("商家首单（单）");
		/**
		 * <th class="center">月份</th>
		 * <th class="center">招商经理</th>
		 * <th class="center">员工首单（单）</th>
		 * <th class="center">换领总额</th>
		 * <th class="center">试用总额</th>
		 * <th class="center">生日礼金（人次）</th>
		 * <th class="center">过节费</th>
		 * <th class="center">销售总额</th>
		 * <th class="center">商家首单（单）</th>
		 * 
		 * 设置订单详情表头 start
		 */
		HSSFRow row = sheet.createRow((int) 0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalManagerResult> page = statisticalManagerResultService.findList(params);
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		for (StatisticalManagerResult p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getManagerId());
			if (mSysUser != null) {
				p.setManagerName(mSysUser.getName());
			}
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			// headers.add("月份");
			row.createCell(col++).setCellValue(p.getMonth());
			// headers.add("招商经理");
			row.createCell(col++).setCellValue(p.getManagerName());
			// headers.add("员工首单（单）");
			row.createCell(col++).setCellValue(dealNull(p.getEmpOrderCnt()));
			// headers.add("换领总额");
			row.createCell(col++).setCellValue(dealNull(p.getExchangeAmount()));
			// headers.add("试用总额");
			row.createCell(col++).setCellValue(dealNull(p.getTrailAmount()));
			// headers.add("生日礼金（人次）");
			row.createCell(col++).setCellValue(dealNull(p.getBirthDayCnt()));
			// headers.add("过节费");
			row.createCell(col++).setCellValue(dealNull(p.getFestivalAmount()));
			// headers.add("销售总额");
			row.createCell(col++).setCellValue(dealNull(p.getOrderAmount()));
			// headers.add("商家首单（单）");
			row.createCell(col++).setCellValue(dealNull(p.getSupplierOrderCnt()));
		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理综合统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 商品换领Controller
	 * 
	 * @param model
	 * @param session
	 * @return
	 */

	@RequestMapping("/huanlingShow")
	public String huanlingShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;

		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/huanling/show";
	}

	/**
	 * 查询列表
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/huanlingList")
	public String queryhlFilter(@RequestParam Map<String, Object> params, ModelMap model, HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		PageInfo<StatisticalExchangeProduct> findList = statisticalExchangeProductService.findList(params);
		for (StatisticalExchangeProduct result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getSupplierManager());
			if (mSysUser != null) {
				result.setManagerName(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/huanling/list";

	}

	/**
	 * POI导入数据 POI换领商品导入
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/huanlingexportExcel")
	@ResponseBody
	public void huanlingdownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("招商经理");
		headers.add("商家");
		headers.add("商品种类");
		headers.add("参与员工数");
		headers.add("商品总数");
		headers.add("总金额");
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		HSSFRow row = sheet.createRow((int) 0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalExchangeProduct> page = statisticalExchangeProductService.findList(params);

		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		for (StatisticalExchangeProduct p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getSupplierManager());
			if (mSysUser != null) {
				p.setManagerName(mSysUser.getName());
			}
			
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			row.createCell(col++).setCellValue(p.getMonth());
			row.createCell(col++).setCellValue(p.getManagerName());
			row.createCell(col++).setCellValue(p.getSupplierName());
			row.createCell(col++).setCellValue(dealNull(p.getProductTypeCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getEmpCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getProductCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getProductAmount()));

		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理换领统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 员工首单Controller
	 * 
	 * @param ls
	 * @param mapManager
	 * @param managerId
	 * @return
	 */
	@RequestMapping("/shouDanShow")
	public String toShowDan(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;
		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/shoudan/show";
	}

	/**
	 * 商品首单List
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/ShouDanList")
	public String queryShouDanFilter(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();

		PageInfo<StatisticalFirstOrder> findList = statisticalFirstOrderService.countEnterpriseByModel(params);
		for (StatisticalFirstOrder result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getEnterpriseManager());
			if (mSysUser != null) {
				result.setManagerName(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/shoudan/list";
	}

	/**
	 * POI导入数据 员工首单导入
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/shpuDanexportExcel")
	@ResponseBody
	public void ShouDandownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);

		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}


		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("招商经理");
		headers.add("商家");
		headers.add("员工首单（单）");
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		HSSFRow row = sheet.createRow((int) 0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalFirstOrder> findList = statisticalFirstOrderService.countEnterpriseByModel(params);
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
			
		for (StatisticalFirstOrder p : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getEnterpriseManager());
			if (mSysUser != null) {
				p.setManagerName(mSysUser.getName());
			}
		
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			//headers.add("月份");
			row.createCell(col++).setCellValue(p.getMonth());
			//headers.add("招商经理");
			row.createCell(col++).setCellValue(p.getManagerName());
			//headers.add("商家");
			row.createCell(col++).setCellValue(p.getEnterpriseName());
			//headers.add("员工首单（单）");
			row.createCell(col++).setCellValue(dealNull(p.getEmpId()));

		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理员工首单统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 节日福利的Controller查询条件查询
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/jirRiShow")
	public String jieRitoShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;
		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/jieri/show";
	}

	// 跳转到list展示页面加载列表
	@RequestMapping("/jieRilist")
	public String jieRiqueryFilter(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		PageInfo<StatisticalBenefit> findList = statisticalBenefitService.findList(params);
		for (StatisticalBenefit result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getEnterpriseManager());
			if (mSysUser != null) {
				result.setEnterpriseManagerNamne(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/shengri/list";

	}

	/**
	 * 节日福利的POI导出
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/jieRiexportExcel")
	@ResponseBody
	public void jieRidownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);

		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}


		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("商家");
		headers.add("招商经理");
		headers.add("发放总人次");
		headers.add("发放总金额");
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		HSSFRow row = sheet.createRow((int) 0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalBenefit> page = statisticalBenefitService.findList(params);
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
			
		for (StatisticalBenefit p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getEnterpriseManager());
			if (mSysUser != null) {
				p.setEnterpriseManagerNamne(mSysUser.getName());
			}

			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			// headers.add("招商人员");
			row.createCell(col++).setCellValue(p.getMonth());
			// headers.add("已导入员工商家");
			row.createCell(col++).setCellValue(p.getEnterpriseName());
			// headers.add("商家数量");
			row.createCell(col++).setCellValue(p.getEnterpriseManagerNamne());
			// headers.add("总导入员工数");
			row.createCell(col++).setCellValue(dealNull(p.getEmpCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getCashAmount()));
			// headers.add("总激活人数");

		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商过节费统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生日福利的Controller 控制层
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 * 
	 * 		跳转到base条件页面 加载条件
	 */
	@RequestMapping("/shengRiShow")
	public String shengRitoShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;
		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/shengri/show";
	}

	/**
	 * 查询生日福利列表List
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	/*
	 * private void getBalanceList(Map<String, Object> query) {
	 * query.put("pageNum", 1); query.put("pageSize", 5000); }
	 */
	// 跳转到list展示页面加载列表
	@RequestMapping("/shengRilist")
	public String shengRiqueryFilter(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		PageInfo<StatisticalBenefit> findList = statisticalBenefitService.findList(params);
		for (StatisticalBenefit result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getEnterpriseManager());
			if (mSysUser != null) {
				result.setEnterpriseManagerNamne(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/shengri/list";

	}

	/**
	 * 生日福利的POI导出
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/shengRiexportExcel")
	@ResponseBody
	public void shnegRidownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);

		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("商家");
		headers.add("招商经理");
		headers.add("发放总人次");
		headers.add("发放总金额");
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		HSSFRow row = sheet.createRow((int) 0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalBenefit> page = statisticalBenefitService.findList(params);
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();

		for (StatisticalBenefit p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getEnterpriseManager());
			if (mSysUser != null) {
				p.setEnterpriseManagerNamne(mSysUser.getName());
			}
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			row.createCell(col++).setCellValue(p.getMonth());
			row.createCell(col++).setCellValue(p.getEnterpriseName());
			row.createCell(col++).setCellValue(p.getEnterpriseManagerNamne());
			row.createCell(col++).setCellValue(dealNull(p.getEmpCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getCashAmount()));

		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理生日礼金统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 商品使用的Controller控制层
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 * 
	 * 		跳转到base条件页面 加载条件
	 */
	@RequestMapping("/shiYongShow")
	public String shiYongtoShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;
		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/shiyong/show";
	}

	/**
	 * 查询商品试用列表
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	/*
	 * private void getBalanceList(Map<String, Object> query) {
	 * query.put("pageNum", 1); query.put("pageSize", 5000); }
	 */
	// 跳转到list展示页面加载列表
	@RequestMapping("/shiYonglist")
	public String shiYongqueryFilter(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		PageInfo<StatisticalTrialProduct> findList = statisticalTrialProductService.findList(params);
		for (StatisticalTrialProduct result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getSupplierManager());
			if (mSysUser != null) {
				result.setManagerName(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/shiyong/list";

	}

	/**
	 * 商品使用的POI导出Controller
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/shiYongexportExcel")
	@ResponseBody
	public void shiYongdownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);

		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}


		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("招商经理");
		headers.add("商家");
		headers.add("商品种类");
		headers.add("商品总数");
		headers.add("优惠总金额");
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		HSSFRow row = sheet.createRow((int) 0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalTrialProduct> page = statisticalTrialProductService.findList(params);
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		for (StatisticalTrialProduct p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getSupplierManager());
			if (mSysUser != null) {
				p.setManagerName(mSysUser.getName());
			}
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			// headers.add("招商人员");
			row.createCell(col++).setCellValue(p.getMonth());
			// headers.add("已导入员工商家");
			row.createCell(col++).setCellValue(p.getManagerName());
			// headers.add("商家数量");
			row.createCell(col++).setCellValue(p.getSupplierName());
			// headers.add("总导入员工数");
			row.createCell(col++).setCellValue(dealNull(p.getProductTypeCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getProductCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getBreakAmount()));
			// headers.add("总激活人数");

		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理试用商品统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 销售业绩的Controller控制层
	 * 
	 * @param request
	 *            跳转到base条件页面 加载条件
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/xiaoShouShow")
	public String xiaoShoutoShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;
		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/xiaoshou/show";
	}

	/**
	 * 查询销售额的列表List
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	// 跳转到list展示页面加载列表
	@RequestMapping("/xiaoShoulist")
	public String xiaoShouqueryFilter(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		PageInfo<StatisticalSale> findList = statisticalSaleService.findList(params);
		for (StatisticalSale result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getSupplierManager());
			if (mSysUser != null) {
				result.setManagerName(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/xiaoshou/list";

	}

	/**
	 * 销售额的POI导出
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/xiaoShouexportExcel")
	@ResponseBody
	public void xiaoShoudownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);

		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}

		

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("招商经理");
		headers.add("商家");
		headers.add("成交单数");
		headers.add("货款总金额");
		headers.add("运费总金额");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCell cellt = row.createCell(0);
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalSale> page = statisticalSaleService.findList(params);
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();
		for (StatisticalSale p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getSupplierManager());
			if (mSysUser != null) {
				p.setManagerName(mSysUser.getName());
			}
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			row.createCell(col++).setCellValue(p.getMonth());
			row.createCell(col++).setCellValue(p.getManagerName());
			row.createCell(col++).setCellValue(p.getSupplierName());
			row.createCell(col++).setCellValue(dealNull(p.getOrderCnt()));
			row.createCell(col++).setCellValue(dealNull(p.getRealPrice()));
			row.createCell(col++).setCellValue(dealNull(p.getShipping()));
			
		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理商家销售统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 商家首单的Controller控制层
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/shangShouDan")
	public String shangShouDantoShow(Model model, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		Map<String, Object> query = new HashMap<String, Object>();
		SysUser user = (SysUser) obj;
		if(isLeander(user.getId())) {
			model.addAttribute("mlist", getManagerList());
		}
		model.addAttribute("uid", user.getId());

		model.addAttribute("supplierList", getSupplierList(query));
		return "tongji/managerTongji/shangshoudan/show";
	}

	/**
	 * 查询商家首单列表List
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/shangShouDanlist")
	public String shangShouDanqueryFilter(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request) {
		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();

		PageInfo<StatisticalFirstOrder> findList = statisticalFirstOrderService.countSupplierByModel(params);
		for (StatisticalFirstOrder result : findList.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, result.getSupplierManager());
			if (mSysUser != null) {
				result.setManagerName(mSysUser.getName());
			}
		}
		model.addAttribute("page", findList);
		return "tongji/managerTongji/shangshoudan/list";
	}

	/**
	 * 商家首单的POI导出Controller
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/shangShouDanexportExcel")
	@ResponseBody
	public void shnagShouDandownLoadExcel(@RequestParam Map<String, Object> params, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);

		if ("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("招商一览");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		/**
		 * 设置样式 start
		 */
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		/**
		 * 设置样式 end
		 */
		List<String> headers = new ArrayList<String>();
		headers.add("月份");
		headers.add("招商经理");
		headers.add("商家");
		headers.add("成交单数");
		headers.add("成交总金额");
		headers.add("运费总金额");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCell cellt = row.createCell(0);
		/**
		 * 
		 * 
		 * 设置订单详情表头 start
		 */
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			// 设置值
			cell.setCellValue(headers.get(i));
			// 设置样式
			cell.setCellStyle(style);
		}
		/**
		 * 设置订单详情表头 end
		 */
		int currentRow = 0;
		PageInfo<StatisticalFirstOrder> page = statisticalFirstOrderService.countSupplierByModel(params);

		Map<Long, SysUser> mapManager = new HashMap<Long, SysUser>();
		List<SysUser> ls = getManagerList();

		for (StatisticalFirstOrder p : page.getList()) {
			SysUser mSysUser = this.getManager(ls, mapManager, p.getSupplierManager());
			if (mSysUser != null) {
				p.setManagerName(mSysUser.getName());
			}
			currentRow++;
			int col = 0;
			row = sheet.createRow(currentRow);
			// headers.add("招商人员");
			row.createCell(col++).setCellValue(p.getMonth());
			row.createCell(col++).setCellValue(p.getManagerName());
			row.createCell(col++).setCellValue(p.getSupplierName());
			row.createCell(col++).setCellValue(dealNull(p.getEmpId()));
			row.createCell(col++).setCellValue(dealNull(p.getRealPrice()));
			row.createCell(col++).setCellValue(dealNull(p.getShipping()));
		}

		// 第六步，将文件存到指定位置
		// 设置相应头信息
		response.addHeader("Content-Disposition", "attachment;" + getFileNameForSave(request,"招商经理商家首单统计"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			wb.write(response.getOutputStream());
			wb.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getFileNameForSave(HttpServletRequest request,String name) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = name+ DateUtils.formatDate(new Date(), "_yyyyMMdd") + ".xls";
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


	private List<SysUser> getManagerList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 500);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		return list;
	}


	private SysUser getManager(List<SysUser> ls, Map<Long, SysUser> mapManager, Long managerId) {
		if (managerId == null)
			return null;

		if (!mapManager.containsKey(managerId)) {
			for (SysUser sysUser : ls) {
				if (managerId.equals(sysUser.getId())) {
					mapManager.put(managerId, sysUser);
					break;
				}
			}
		}
		return mapManager.get(managerId);
	}

	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);

		return supplierService.getPage(query).getList();
	}

	private Integer dealNull(Long o) {
		if(o==null) return 0;
		return o.intValue();
	}
	private Integer dealNull(Integer o) {
		if(o==null) return 0;
		return o;
	}

	private Double dealNull(BigDecimal o) {
		if(o==null) return 0D;
		return o.doubleValue();
	}

	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}
}
