/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.DateUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.SupplierVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("employee")
public class EmployeeController {
	@Autowired
	private SupplierService supplierService;

	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;
	@Resource
	private ProductService productService;
	
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	
	@Resource
	private HtmlAction htmlAction;
	@Resource
	private SysUserMapper sysUserMapper;

	private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@RequestMapping("base")
	public String toPageAttrView(Model model,HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
//		if(!isLeander(user.getId())) {
//			query.put("managerId", user.getId());
//		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("officeId", 0);
			params.put("officeType", "");
			params.put("roles", "-108,-111");
			params.put("pageNum", 1);
			params.put("pageSize", 120);

			PageHelper.startPage(params);
			List<SysUser> list = sysUserMapper.findPageInfo(params);
			
			model.addAttribute("mlist", list);
			model.addAttribute("uid", user.getId());
//		}
		return "manager/employee/base";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model,HttpSession session) {

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
//		SysUser user = (SysUser)obj;
//		if(!isLeander(user.getId())) {
//			if(!params.containsKey("managerId")){
//				params.put("managerId", user.getId());
//			}
//		}
		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 

		Calendar firstc = Calendar.getInstance();
		firstc.set(Calendar.DAY_OF_MONTH,1);
		firstc.set(Calendar.HOUR_OF_DAY, 0);
		firstc.set(Calendar.MINUTE, 0);
		firstc.set(Calendar.SECOND, 0);
		if(StringUtils.isEmpty((String)params.get("startDate"))) {
			params.put("startDate", firstc.getTime());
		}
		if(StringUtils.isEmpty((String)params.get("endDate"))) {
			params.put("endDate", new Date());
		}
		PageInfo<SupplierVo> page = supplierService.findEmploeeCnt(params);
		
		model.addAttribute("page", page);
		return "manager/employee/list";
	}


	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 5000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 

		Calendar firstc = Calendar.getInstance();
		firstc.set(Calendar.DAY_OF_MONTH,1);
		firstc.set(Calendar.HOUR_OF_DAY, 0);
		firstc.set(Calendar.MINUTE, 0);
		firstc.set(Calendar.SECOND, 0);
		String s=(String)params.get("startDate");
		String end=(String)params.get("endDate");
		if(StringUtils.isEmpty(s)) {
			params.put("startDate", firstc.getTime());
			s=TimeUtil.dateToStr(firstc.getTime());
		}
		if(StringUtils.isEmpty(end)) {
			params.put("endDate", new Date());
			end=TimeUtil.dateToStr(new Date());
		}

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("招商一览"); 
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
        headers.add("招商人员");
        headers.add("商家数量");
        headers.add("已导入员工商家");
        headers.add("总导入员工数");
        headers.add("总激活人数");
        headers.add("新增导入员工数");
        headers.add("新增激活员工数");
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        HSSFCell cellt = row.createCell(0);
        //设置值
        cellt.setCellValue("统计日期："+s+"~"+end);  
        //设置样式
        cellt.setCellStyle(style);
        row = sheet.createRow((int) 1); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 1;
		PageInfo<SupplierVo> page = supplierService.findEmploeeCnt(params);
		for (SupplierVo p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("招商人员");
            row.createCell(col++).setCellValue(p.getManagerName());
            //headers.add("已导入员工商家");
            row.createCell(col++).setCellValue(p.getComTel1());
            //headers.add("商家数量");
            row.createCell(col++).setCellValue(p.getShopTel1());
            //headers.add("总导入员工数");
            row.createCell(col++).setCellValue(p.getComTel2());
            //headers.add("总激活人数");
            row.createCell(col++).setCellValue(p.getComTel3());
            //headers.add("增导入员工数");
            row.createCell(col++).setCellValue(p.getShopTel2());
            //headers.add("增激活员工数");
            row.createCell(col++).setCellValue(p.getShopTel3());
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
		String filename = "员工导入统计"+ DateUtils.formatDate(new Date(),"_yyyyMMdd") +".xls";
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

