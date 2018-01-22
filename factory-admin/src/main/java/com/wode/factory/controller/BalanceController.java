package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.DateUtils;
import com.wode.factory.model.BalanceMonthStatistical;
import com.wode.factory.service.BalanceMonthStatisticalService;
import com.wode.sys.model.SysUser;
@Controller
@RequestMapping("balance")
public class BalanceController {
	@Autowired
	private BalanceMonthStatisticalService balanceMonthStatisticalService;
	
	@RequestMapping
	public String toBase(Model model){
		Map<String,Object> query = new HashMap<String,Object>();
		return "manager/balance/base";
	}
	private void getBalanceList(Map<String, Object> query) {
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
	}
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String,Object> params,ModelMap model,HttpServletRequest request){
		PageInfo pageInfo =balanceMonthStatisticalService.findList(params);
		model.addAttribute("page", pageInfo);
		return "manager/balance/list";
	}
	
}
