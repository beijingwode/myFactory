/**
 * 
 */
package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.DateUtils;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.SuborderitemDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.EmpBenefitFlowService;
import com.wode.factory.service.EntBenefitFlowService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.EmpBenefitFlowVo;
import com.wode.factory.vo.EntBenefitFlowVo;
import com.wode.sys.model.SysUser;


/**
 * @author zcx
 *
 */
@Controller
@RequestMapping("entBenefitFlow")
public class EntBenefitFlowController {
	
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private EntBenefitFlowService entBenefitFlowService;
	@Autowired
	private EmpBenefitFlowService empBenefitFlowService;
	@Autowired
	private SuborderitemDao suborderitemDao;
	
	@RequestMapping
	public String toBase(Model model){

		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/entBenefitFlow/base";//"sys/saleBill/saleBill-base";
	}

	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		return supplierService.getPage(query).getList();
	}
	
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request) {
		String[] ary = request.getParameterValues("opCode");
		if(ary!=null && ary.length>0) {
			String status = Arrays.toString(ary);
			params.put("opCode", status.substring(1, status.length()-1).replace(" ", ""));
		}
		PageInfo pageInfo = entBenefitFlowService.findList(params);
		model.addAttribute("page", pageInfo);
		return "manager/entBenefitFlow/list";//"sys/saleBill/saleBill-list";
	}

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/empList")
	public String queryEmpFilter(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request) {
		PageInfo<EmpBenefitFlowVo> pageInfo = empBenefitFlowService.findList(params);
		model.addAttribute("page", pageInfo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleKbn", "5"); //试用
		for (EmpBenefitFlowVo vo : pageInfo.getList()) {
			map.put("subOrderId", vo.getKeyId());
			 List<Suborderitem> ls = suborderitemDao.findBySubIdAndSaleKbn(map);
			 if(ls!=null && !ls.isEmpty()) {

				 vo.setItemValues(ls.get(0).getItemValues());
				 vo.setProductName(ls.get(0).getProductName());
				 vo.setPrice(ls.get(0).getPrice());
				 vo.setNumber(ls.get(0).getNumber());
			 }
		}
		return "manager/entBenefitFlow/emp-list";//"sys/saleBill/saleBill-list";
	}
	
	@RequestMapping("/toEmpBase")
	public String toEmpBase(Model model){

		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/entBenefitFlow/emp-base";
	}
	
	@RequestMapping("/toBankBase")
	public String toBankBase(Model model){

		Map<String, Object> query = new HashMap<String, Object>();
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/entBenefitFlow/bank_base";
	}

	@RequestMapping("/toBank")
	public String toBank(Model model){

		Map<String, Object> query = new HashMap<String, Object>();
		query.put("id", 1019589081269290L);
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/entBenefitFlow/bank";
	}
	
	@RequestMapping(value="bank")
	@ResponseBody
	public String bank(Long supplierId,String value,String desrc,HttpSession session){

		String updName = "admin";
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj != null){
			SysUser user = (SysUser)obj;
			updName = user.getUsername();
		}

		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("entId", supplierId);
		paramMap.put("amount", value);
		paramMap.put("desrc", StringUtils.isEmpty(desrc)?"现金储值":desrc);
		paramMap.put("updName",updName);

	
		try{
			HttpClientUtil.sendHttpRequest("post", Constant.FACTORY_SUPPLIER_URL+"api/bank", paramMap);			
		} catch(Exception ex) {
			
		}
		
		return "1";
	}
	
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		String[] ary = request.getParameterValues("opCode");
		if(ary!=null && ary.length>0) {
			String status = Arrays.toString(ary);
			params.put("opCode", status.substring(1, status.length()-1).replace(" ", ""));
		}

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("现金券一览"); 
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
        headers.add("系统流水号");
        headers.add("商家名称");
        headers.add("财务代码");
        headers.add("时间");
        headers.add("操作");
        headers.add("金额");
        headers.add("操作后余额");
        headers.add("备注");
        headers.add("外部流水号");
        headers.add("外部交易类型");
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
		PageInfo<EntBenefitFlowVo> pageInfo = entBenefitFlowService.findList(params);
		for (EntBenefitFlowVo p : pageInfo.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("系统流水号");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
            //headers.add("商家名称");
            row.createCell(col++).setCellValue(p.getSupplierName());
            //headers.add("财务代码");
            row.createCell(col++).setCellValue(p.getFinanceCode());
            //headers.add("创建时间");
            row.createCell(col++).setCellValue(p.getOpDate()==null?"":DateUtils.formatDate(p.getOpDate(),"yyyy-MM-dd")); //创建时间
            
            //headers.add("操作");           
            BigDecimal mul=BigDecimal.ONE;
            if("112".equals(p.getOpCode())) {
                row.createCell(col++).setCellValue("现金储值");
            } else if("117".equals(p.getOpCode())) {
            	mul=BigDecimal.valueOf(-1);
                row.createCell(col++).setCellValue("发放福利");
            } else if("116".equals(p.getOpCode())) {
                row.createCell(col++).setCellValue("回收福利");
            } else if("120".equals(p.getOpCode())) {
                row.createCell(col++).setCellValue("货款结入");
            } else if("123".equals(p.getOpCode())) {
            	mul=BigDecimal.valueOf(-1);
                row.createCell(col++).setCellValue("余额提现");
            } else {
                row.createCell(col++).setCellValue("");
            } 
            //headers.add("金额");
            row.createCell(col++).setCellValue(p.getCash().multiply(mul).doubleValue());
            //headers.add("操作后余额");
            row.createCell(col++).setCellValue(p.getCashBalance().doubleValue());
            //headers.add("备注");
            row.createCell(col++).setCellValue(p.getNote()); 
            //headers.add("外部流水号");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getTradeNo()); //创建时间
            //headers.add("外部交易类型");
            if("zhifubao".equals(p.getTransferType())) {
                row.createCell(col++).setCellValue("支付宝");
            } else if("unionpay".equals(p.getTransferType())) {
                row.createCell(col++).setCellValue("银联");
            } else if("pingtaiyue".equals(p.getTransferType())) {
                row.createCell(col++).setCellValue("现金券");
            } else if("wxpay".equals(p.getTransferType())) {
                row.createCell(col++).setCellValue("微信");
            } else {
                row.createCell(col++).setCellValue("");
            } 
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
	

	@RequestMapping(value = "exportEmpExcel")
	@ResponseBody
	public void exportEmpExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("买家返现一览"); 
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
        headers.add("系统流水号");
        headers.add("会员ID");
        headers.add("时间");
        headers.add("金额");
        headers.add("订单ID");
        headers.add("备注");
        headers.add("商品");
        headers.add("价格");
        headers.add("数量");
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
        PageInfo<EmpBenefitFlowVo> pageInfo = empBenefitFlowService.findList(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleKbn", "5"); //试用
		for (EmpBenefitFlowVo p : pageInfo.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("系统流水号");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
            //headers.add("会员ID");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getEmpId().toString());
            //headers.add("创建时间");
            row.createCell(col++).setCellValue(p.getOpDate()==null?"":DateUtils.formatDate(p.getOpDate(),"yyyy-MM-dd")); //创建时间
            //headers.add("金额");
            row.createCell(col++).setCellValue(p.getCash().doubleValue());
            //headers.add("订单ID");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getKeyId());
            //headers.add("备注");
            row.createCell(col++).setCellValue(p.getNote()); 
            
            map.put("subOrderId", p.getKeyId());
			List<Suborderitem> ls = suborderitemDao.findBySubIdAndSaleKbn(map);
			if(ls!=null && !ls.isEmpty()) {
	            //headers.add("商品");
	            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(ls.get(0).getProductName() + "" + ls.get(0).getItemValues());
	            //headers.add("价格");
	            row.createCell(col++).setCellValue(ls.get(0).getPrice().doubleValue());
	            //headers.add("数量");
	            row.createCell(col++).setCellValue(ls.get(0).getNumber());
			}			 
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
		String filename = "现金券流水一览"+ DateUtils.formatDate(new Date(),"_yyyyMMdd") +".xls";
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
