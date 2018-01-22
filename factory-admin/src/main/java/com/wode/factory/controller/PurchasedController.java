package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.DateUtils;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.SupplierTicketFlowService;
import com.wode.sys.model.SysUser;

/**
 * 
 * @author jzc
 *
 */
@Controller
@RequestMapping("purchased")
public class PurchasedController {
	
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	@Autowired
	private SupplierTicketFlowService supplierTicketFlowService;
	
	@RequestMapping
	public String toBase(Model model){
		Map<String,Object> query = new HashMap<String,Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/purchased/base";
	}
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		return supplierService.getPage(query).getList();
	}
	
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String,Object> params,ModelMap model,HttpServletRequest request){
		String[] ary = request.getParameterValues("ticketType");
		if(ary!=null && ary.length>0) {
			String status = Arrays.toString(ary);
			params.put("ticketType", status.substring(1, status.length()-1).replace(" ", ""));
		}
		PageInfo pageInfo =supplierExchangeProductService.findPageList(params);
		model.addAttribute("page", pageInfo);
		return "manager/purchased/list";
	}
	@RequestMapping("/baseView")
	public String toBaseView(@RequestParam Map<String, Object> params,ModelMap model){
		Map<String,Object> query = new HashMap<String,Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/purchased/base-view";
	}
	/**
	 * 进入流水详情页
	 * @param params
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listView")
	public String queryFilterView(@RequestParam Map<String,Object> params,Model model,HttpServletRequest request){
		String[] ary = request.getParameterValues("opCode");
		if(ary!=null && ary.length>0) {
			String status = Arrays.toString(ary);
			params.put("opCode", status.substring(1, status.length()-1).replace(" ", ""));
		}
		PageInfo pageInfo =supplierTicketFlowService.findPageList(params);
		model.addAttribute("page", pageInfo);
		return "manager/purchased/list-view";
	}
	
	
	/**
	 * 导出换领账户
	 * @param params
	 * @param model
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("exportExcel")
	public void downLoadExcel(@RequestParam Map<String,Object> params, ModelMap model, HttpServletRequest request,HttpServletResponse response)throws UnsupportedEncodingException{
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		String[] ary = request.getParameterValues("opCode");
		if(ary!=null && ary.length>0) {
			String status = Arrays.toString(ary);
			params.put("opCode", status.substring(1, status.length()-1).replace(" ", ""));
		}
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("换领商品一览"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        
        /**
         * 设置样式开始
         */
        //第四步，创建单元格，并设置表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//创建居中格式
        /**
         * 样式设置结束
         */
        List<String> headers = new ArrayList<String>();
        headers.add("换领账户号");
        headers.add("商家名称");
        headers.add("券类型");
        headers.add("换领商品总额");
        headers.add("已分配额度");
        headers.add("可分配额度");
        headers.add("欠额");
        headers.add("欠额产生日期");
        headers.add("欠额清除日期");
        headers.add("换领券分配日期");
        headers.add("换领券使用期限");
        /**
         * 设置详情表头开始
         */
        HSSFRow row = sheet.createRow((int)0);
        for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			//设置值
			cell.setCellValue(headers.get(i));
			//设置样式
			cell.setCellStyle(style);
		}
        /**
         * 设置详情表头结束
         */
//        int currentRow =0;
//        PageInfo<SupplierTicketVo> pageInfo = supplierTicketService.findList(params);
//        for (SupplierTicketVo p : pageInfo.getList()) {
//			
//        	currentRow++;
//        	int col =0;
//        	row = sheet.createRow(currentRow);
//        	//headers.add("换领号")
//        	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
//        	//headers.add("商家名称");
//        	row.createCell(col++).setCellValue(p.getSupplierName());
//        	BigDecimal mul=BigDecimal.ONE;
//        	//headers.add("换领商品总额");
//        	row.createCell(col++).setCellValue(p.getProductAmount().multiply(mul).doubleValue());
//        	//headers.add("已分配额度");
//        	row.createCell(col++).setCellValue(p.getDivededAmount().multiply(mul).doubleValue());
//        	//headers.add("可分配额度");
//        	row.createCell(col++).setCellValue(p.getBalanceAmount().multiply(mul).doubleValue());
//        	//headers.add("欠额");
//        	row.createCell(col++).setCellValue(p.getDebtAmount().multiply(mul).doubleValue());
//        	//headers.add("欠额产生日期")
//        	row.createCell(col++).setCellValue(p.getDebtDate()==null?"":DateUtils.formatDate(p.getDebtDate(),"yyyy-MM-dd"));
//        	//headers.add("欠额清除日期")
//        	row.createCell(col++).setCellValue(p.getDebtClearDate()==null?"":DateUtils.formatDate(p.getDebtClearDate(),"yyyy-MM-dd"));
//        	//headers.add("换领券分配日期")
//        	row.createCell(col++).setCellValue(p.getTicketLastDate()==null?"":DateUtils.formatDate(p.getTicketLastDate(),"yyyy-MM-dd"));
//        	//headers.add("换领券使用期限")
//        	row.createCell(col++).setCellValue(p.getTicketLimitDate()==null?"":DateUtils.formatDate(p.getTicketLimitDate(),"yyyy-MM-dd"));
//		}
        // 第六步，将文件存到指定位置
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request,"换领账户一览"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        
        try{
        	wb.write(response.getOutputStream());
        	wb.close();
        } catch (IOException e){
        	e.printStackTrace();
        }
	}
	
	/**
	 * 导出换领流水
	 * @param response
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "downLoadExcel",method = RequestMethod.POST)
	@ResponseBody
	public void downLoadExcelView(@RequestParam Map<String,Object> params, ModelMap model, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		String[] ary = request.getParameterValues("opCode");
		if(ary!=null && ary.length>0) {
			String status = Arrays.toString(ary);
			params.put("opCode", status.substring(1, status.length()-1).replace(" ", ""));
		}
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("换领账户流水");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        /**
         * 设置样式开始
         */
        //第四步，创建单元格，并设置表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//创建居中格式
        /**
         * 样式设置结束
         */
        List<String> headers = new ArrayList<String>();
        headers.add("换领账户流水号");
        headers.add("商家名称");
        headers.add("时间");
        headers.add("操作");
        headers.add("额度");
        headers.add("换领商品总额");
        headers.add("已分配额度");
        headers.add("可分配额度");
        headers.add("操作后欠额");
        headers.add("备注");
        /**
         * 设置详情表头开始
         */
        HSSFRow row = sheet.createRow((int)0);
        for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			//设置值
			cell.setCellValue(headers.get(i));
			//设置样式
			cell.setCellStyle(style);
		}
        /**
         * 设置详情表头结束
         */
        int currentRow =0;
//        PageInfo<SupplierTicketHisVo> pageInfo = supplierTicketHisService.findList(params);
//        for (SupplierTicketHisVo p : pageInfo.getList()) {
//			
//        	currentRow++;
//        	int col =0;
//        	row = sheet.createRow(currentRow);
//        	//headers.add("ID")
//        	row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
//        	//headers.add("商家名称");
//        	row.createCell(col++).setCellValue(p.getSupplierName());
//        	//headers.add("时间");
//        	row.createCell(col++).setCellValue(p.getOpDate()==null?"":DateUtils.formatDate(p.getOpDate(),"yyyy-MM-dd")); //创建时间
//        	
//        	//headers.add("操作");
//        	BigDecimal mul=BigDecimal.ONE;
//        	if ("100".equals(p.getOpCode())) {
//        		row.createCell(col++).setCellValue("换领商品上架");
//			}else if("101".equals(p.getOpCode())){
////				mul=BigDecimal.valueOf(-1);
//				row.createCell(col++).setCellValue("换领券发放");
//			}else if("102".equals(p.getOpCode())){
//				row.createCell(col++).setCellValue("换领券消费");
//			}else if ("103".equals(p.getOpCode())) {
//				row.createCell(col++).setCellValue("换领商品消费");
//			}else if ("105".equals(p.getOpCode())) {
//				row.createCell(col++).setCellValue("换领券清算");
//			}else if ("106".equals(p.getOpCode())) {
//				row.createCell(col++).setCellValue("换领商品清算");
//			}else{
//				row.createCell(col++).setCellValue("");
//			}
//        	//headers.add("额度");
//        	row.createCell(col++).setCellValue(p.getAmount().multiply(mul).doubleValue());
//        	//headers.add("换领商品总额");
//        	row.createCell(col++).setCellValue(p.getProductAmount().multiply(mul).doubleValue());
//        	//headers.add("已分配额度");
//        	row.createCell(col++).setCellValue(p.getDivededAmount().multiply(mul).doubleValue());
//        	//headers.add("可分配额度");
//        	row.createCell(col++).setCellValue(p.getBalanceAmount().multiply(mul).doubleValue());
//        	//headers.add("操作后欠额");
//        	row.createCell(col++).setCellValue(p.getDebtAmount().multiply(mul).doubleValue());
//        	//headers.add("备注");
//        	row.createCell(col++).setCellValue(p.getNote());
//		}
        // 第六步，将文件存到指定位置
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request,"换领流水一览"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        try{
        	wb.write(response.getOutputStream());
        	wb.close();
        } catch (IOException e){
        	e.printStackTrace();
        }
    }
	
	private String getFileNameForSave(HttpServletRequest request,String name) throws UnsupportedEncodingException {
		
		String userAgent = request.getHeader("user-agent");
		String filename =name+DateUtils.formatDate(new Date(),"_yyyyMMdd") +".xls";
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
