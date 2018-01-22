/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.supplier.query.SaleBillQuery;
import com.wode.factory.supplier.service.SaleBillService;
import com.wode.factory.supplier.service.SaleDetailService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

import cn.org.rapid_framework.page.Page;

@Controller
@RequestMapping("exportExcel")
public class ExportExcelController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("saleBillService")
	private SaleBillService saleBillService;
	
	@Autowired
	@Qualifier("saleDetailService")
	private SaleDetailService saleDetailService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	@Resource
	@Qualifier("pathMap")
	private Map<String,String> pathMap;

	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	private final String LIST_ACTION = "redirect:/saleBill/list.html";
	
	public ExportExcelController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 
	 * 执行搜索 
	 **/
	@RequestMapping(value="list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,SaleBillQuery query) {
		Page page = this.saleBillService.findPage(query);
		
		ModelAndView result = new ModelAndView("product/saleBill/list");
		result.addAllObjects(toModelMap(page, query));
		return result;
	}
	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SaleBill saleBill = (SaleBill)saleBillService.getById(id);
		return new ModelAndView("product/saleBill/show","saleBill",saleBill);
	}
	
	/** 
	 * 进入新增页面
	 **/
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView create(HttpServletRequest request,HttpServletResponse response,SaleBill saleBill) throws Exception {
		return new ModelAndView("product/saleBill/create","saleBill",saleBill);
	}
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,SaleBill saleBill) throws Exception {
		saleBillService.save(saleBill);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 * 进入更新页面
	 **/
	@RequestMapping(value="edit",method=RequestMethod.GET)
	@Token(save=true)
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		SaleBill saleBill = (SaleBill)saleBillService.getById(id);
		return new ModelAndView("product/saleBill/edit","saleBill",saleBill);
	}
	
	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Token(remove=true)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response) throws Exception {
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		
		SaleBill saleBill = (SaleBill)saleBillService.getById(id);
		bind(request,saleBill);
		saleBillService.update(saleBill);		
		return new ModelAndView(LIST_ACTION);
	}
	
	/**
	 *删除对象
	 **/
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) {
		//删除一个时删掉下面的
		java.lang.Long id = new java.lang.Long(request.getParameter("id"));
		saleBillService.removeById(id);
		return new ModelAndView(LIST_ACTION);
		
		//删除多个
		/*String[] items = request.getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			
			java.lang.Long id = new java.lang.Long((String)params.get("id"));
			
			saleBillService.removeById(id);
		}
		return new ModelAndView(LIST_ACTION);
		*/
	}
	/**
	 * 对账单list
	 **/
	@RequestMapping(value="ajaxExportSaleBillView",method=RequestMethod.GET)
	public void ajaxExportSaleBillView(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ServletContext context = request.getSession().getServletContext();
		ModelAndView mv = new ModelAndView();
		Result result = new Result();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		
		if(userModel == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("对账单详情");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        String saleBillId = request.getParameter("saleBillId");
		    SaleBill sb = saleBillService.getById(new Long(saleBillId));
		 // 第四步，创建单元格，并设置值表头 设置表头居中  
		    //sheet.autoSizeColumn(1);//自适应宽度
		    sheet.setColumnWidth((short)0,256*6);
		    sheet.setColumnWidth((short)1,256*20);
		    sheet.setColumnWidth((short)2,256*12);
		    sheet.setColumnWidth((short)3,256*12);
		    sheet.setColumnWidth((short)4,256*12);
		    sheet.setColumnWidth((short)5,256*13);
		    sheet.setColumnWidth((short)6,256*20);
		    sheet.setColumnWidth((short)7,256*15);
		    sheet.setColumnWidth((short)8,256*12);
		    sheet.setColumnWidth((short)9,256*8);
		    sheet.setColumnWidth((short)10,256*12);
		    sheet.setColumnWidth((short)11,256*8);
		    sheet.setColumnWidth((short)12,256*12);
		    sheet.setColumnWidth((short)13,256*12);
		    sheet.setColumnWidth((short)14,256*12);
		    sheet.setColumnWidth((short)15,256*12);
		    sheet.setColumnWidth((short)16,256*12);
		    sheet.setColumnWidth((short)17,256*12);
		    
	        HSSFCellStyle style0 = wb.createCellStyle();  
	        style0.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
	        style0.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	        style0.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	        style0.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	        style0.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	        style0.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	        style0.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	        
	        //创建HSSFFont实例，设置字体、字号、加粗、颜色
	        HSSFFont font0 = wb.createFont();
	        font0.setFontName(HSSFFont.FONT_ARIAL);//字体
	        font0.setFontHeightInPoints((short) 10);//字号 
	        font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
	        font0.setColor(HSSFColor.ROYAL_BLUE.index);//颜色
	        style0.setFont(font0);
	        
	        HSSFCellStyle style00 =  wb.createCellStyle(); 
	        HSSFFont font00 = wb.createFont();
	        font00.setFontName(HSSFFont.FONT_ARIAL);//字体
	        font00.setFontHeightInPoints((short) 14);//字号 
	        font00.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
	        font00.setColor(HSSFColor.ROYAL_BLUE.index);//颜色
	        style00.setFont(font00);
            //新版用法 3.8版  
//          sheet.addMergedRegion(new CellRangeAddress(     
//                  1, //first row (0-based)  from 行     
//                  2, //last row  (0-based)  to 行     
//                  1, //first column (0-based) from 列     
//                  1  //last column  (0-based)  to 列     
//          ));  
		    
          sheet.addMergedRegion(new CellRangeAddress(     
	          0, //first row (0-based)  from 行     
	          0, //last row  (0-based)  to 行     
	          2, //first column (0-based) from 列     
	          6  //last column  (0-based)  to 列     
	  		));
          sheet.addMergedRegion(new CellRangeAddress(     
    	          1, //first row (0-based)  from 行     
    	          1, //last row  (0-based)  to 行     
    	          2, //first column (0-based) from 列     
    	          6  //last column  (0-based)  to 列     
    	  	));
	        HSSFRow row = sheet.createRow((int) 0); 
	        //row.createCell((short) 1).setCellValue("标题");
	        HSSFCell cell0 = row.createCell((short) 2);
	        cell0.setCellValue(sb.getTitle());
	        cell0.setCellStyle(style00);
	        
	        row = sheet.createRow((int) 1);
	        cell0 = row.createCell((short) 1);
	        cell0.setCellValue("本期账期周期");
	        cell0.setCellStyle(style0);
	        String str = "";
	        if(sb.getStartTime()!=null){
	        	if(sb.getEndTime()!=null){
	        		str=new SimpleDateFormat("yyyy-MM-dd").format(sb.getStartTime())+" —— "+new SimpleDateFormat("yyyy-MM-dd").format(sb.getEndTime());
	        	}
	        }
	        row.createCell((short) 2).setCellValue(str);
	        
	        row = sheet.createRow((int) 2); 
	        cell0 = row.createCell((short) 1);
	        cell0.setCellValue("代收货款总额");
	        cell0.setCellStyle(style0);
	        row.createCell((short) 2).setCellValue(sb.getReceivePrice()!=null?new DecimalFormat("0.00").format(sb.getReceivePrice()):"-");
	        
	        row = sheet.createRow((int) 3);
	        cell0 = row.createCell((short) 1);
	        cell0.setCellValue("佣金总金额");
	        cell0.setCellStyle(style0);
	        row.createCell((short) 2).setCellValue(sb.getCommissionPrice()!=null?new DecimalFormat("0.00").format(sb.getCommissionPrice()):"-");
	        
	        row = sheet.createRow((int) 4); 
	        cell0 = row.createCell((short) 1);
	        cell0.setCellValue("扣款总额");
	        cell0.setCellStyle(style0);
	        row.createCell((short) 2).setCellValue(sb.getDeductPrice()!=null?new DecimalFormat("0.00").format(sb.getDeductPrice()):"-");
	        
	        row = sheet.createRow((int) 5);
	        cell0 = row.createCell((short) 1);
	        cell0.setCellValue("本期应付款总额");
	        cell0.setCellStyle(style0);
	        row.createCell((short) 2).setCellValue(sb.getPayPrice()!=null?new DecimalFormat("0.00").format(sb.getPayPrice()):"-");
	        
	        
	        
	        
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        
	        //创建HSSFFont实例，设置字体、字号、加粗、颜色
	        HSSFFont font = wb.createFont();
	        font.setFontName(HSSFFont.FONT_ARIAL);//字体
	        font.setFontHeightInPoints((short) 12);//字号 
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
	        font.setColor(HSSFColor.ROYAL_BLUE.index);//颜色
	        style.setFont(font);
	        row = sheet.createRow((int) 7); 
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("序号");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("订单号");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("付款日期");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("确认日期");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("退货日期");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("本企业订单");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("商品");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("商品分类");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("单价");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("数量");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 10);  
	        cell.setCellValue("金额");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 11);  
	        cell.setCellValue("优惠");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 12);  
	        cell.setCellValue("应收货款");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 13);  
	        cell.setCellValue("佣金比例");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 14);  
	        cell.setCellValue("应付佣金");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 15);  
	        cell.setCellValue("运费");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 16);  
	        cell.setCellValue("应收账款");
	        cell.setCellStyle(style);  
	        
	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
	        
	        // 生成并设置另一个样式,用于设置内容样式  
	        HSSFCellStyle style2 = wb.createCellStyle();  
	        style2.setFillForegroundColor(HSSFColor.WHITE.index);  
	        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	        // 生成另一个字体  
	        HSSFFont font2 = wb.createFont();  
	        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
	        // 把字体应用到当前的样式  
	        style2.setFont(font2); 
	        
			Map map =new HashMap();
			map.put("saleBillId", new Long(saleBillId));
			map.put("sortColumns", "createTime");
			List<SaleDetail> list = saleDetailService.findlistPage(map);
			if(list!=null&&list.size()>0){
		        for (int i = 7; i < list.size()+7; i++)
		        {  
		            row = sheet.createRow((int) i + 1);  
		            SaleDetail sd = (SaleDetail) list.get(i-7);  
		            // 第四步，创建单元格，并设置值  
		            cell = row.createCell((short)0);
		            cell.setCellStyle(style2);
		            cell.setCellValue((i-7+1)); //序号 
		            
		            cell = row.createCell((short)1);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getSubOrderId());  //订单号
		            
		            cell = row.createCell((short)2);
		            cell.setCellStyle(style2);
		            cell.setCellValue(com.wode.common.util.StringUtils.isNullOrEmpty(sd.getPayTime())?"-":new SimpleDateFormat("yyyy-MM-dd").format(sd.getPayTime()));  //付款日期
		            
		            cell = row.createCell((short) 3);
		            cell.setCellStyle(style2);
		            cell.setCellValue(com.wode.common.util.StringUtils.isNullOrEmpty(sd.getTakeTime())?"-":new SimpleDateFormat("yyyy-MM-dd").format(sd.getTakeTime()));
		            
		            cell = row.createCell((short)4);
		            cell.setCellStyle(style2);
		            cell.setCellValue(com.wode.common.util.StringUtils.isNullOrEmpty(sd.getReturnTime())?"-":new SimpleDateFormat("yyyy-MM-dd").format(sd.getReturnTime()));//收货日期
		            
		            cell = row.createCell((short)5);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getOwn()==0?"是":"-");//是否本企业
		            
		            cell = row.createCell((short)6);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getProductName());//商品名称
		            
		            cell = row.createCell((short)7);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getCategoryName());//商品分类
		            
		            cell = row.createCell((short)8);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getPrice()!=null?new DecimalFormat("0.00").format(sd.getSalePrice()):"-");//单价
		            
		            cell = row.createCell((short)9);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getNumber());//数量
		            
		            cell = row.createCell((short)10);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getAllPrice()!=null?new DecimalFormat("0.00").format(sd.getSalePrice().doubleValue()*sd.getNumber()):"-");//金额
		            
		            //优惠
		            cell = row.createCell((short)11);
		            cell.setCellStyle(style2);
		            if(sd.getHaveCheap()==null||sd.getHaveCheap()==0) {
			            cell.setCellValue("无");
		            } else if (sd.getHaveCheap()==3){
			            cell.setCellValue("换领");
		            } else if (sd.getHaveCheap()==5){
			            cell.setCellValue("试用");
		            } else {
			            cell.setCellValue("有");
		            }
		            
		            cell = row.createCell((short)12);
		            cell.setCellStyle(style2);
		            cell.setCellValue((sd.getStatus()==-1?"-":"")+ (sd.getRealPrice()!=null?new DecimalFormat("0.00").format(sd.getRealPrice()):"0"));//实付
		            
		            cell = row.createCell((short)13);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getCommissionRatio()!=null?new DecimalFormat("0.00").format(sd.getCommissionRatio())+"%":"0");//抽佣比例
		            
		            cell = row.createCell((short)14);
		            cell.setCellStyle(style2);
		            cell.setCellValue((sd.getStatus()==-1?"-":"")+ (sd.getCommission()!=null?new DecimalFormat("0.00").format(sd.getCommission()):"0"));//实收佣金
		            
		            cell = row.createCell((short)15);
		            cell.setCellStyle(style2);
		            cell.setCellValue(sd.getCarriagePrice()!=null?new DecimalFormat("0.00").format(sd.getCarriagePrice()):"0");//代收运费
		            
		            cell = row.createCell((short)16);
		            cell.setCellStyle(style2);
		            cell.setCellValue(new DecimalFormat("0.00").format(sd.getPayPrice().compareTo(new BigDecimal(0))>-1?sd.getPayPrice().doubleValue()*sd.getStatus():0));//应付货款
		        }  
			}
	        // 第六步，将文件存到指定位置  
	        try  
	        {  
	            String basePath = pathMap.get("salebillDownloadPath");
	            //basePath = "d:\\download\\";
	            if(StringUtils.isEmpty(basePath)){
	            	basePath=context.getRealPath("/");
				}
	            //页面路径                      
	    		File htmlFile = new File(basePath);  
	    		if (!htmlFile.exists()&& !htmlFile.isDirectory()) {
	    			htmlFile.mkdirs();
	    		}
	    		
	            if(basePath==null||basePath.equals("")){
	            	basePath =  "d:\\";
	            }
	            File f=new File(basePath+saleBillId+".xls");
	    		if (!f.exists()) {
	    			f.createNewFile();
	    		}
	            FileOutputStream fout = new FileOutputStream(basePath+saleBillId+".xls");
	            wb.write(fout);
	            fout.close(); 
	            
	            File file = new File(basePath+saleBillId+".xls");
	            // 取得文件名。  
	            String filename = file.getName();  
	            // 以流的形式下载文件。  
	            InputStream fis = new BufferedInputStream(new FileInputStream(basePath+saleBillId+".xls"));  
	            byte[] buffer = new byte[fis.available()];  
	            fis.read(buffer);  
	            fis.close();  
	            // 清空response  
	            response.reset();  
	            // 设置response的Header  
	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sb.getTitle().getBytes("gb2312"), "ISO-8859-1")+".xls");  
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(  
	                    response.getOutputStream());  
	            response.setContentType("application/vnd.ms-excel;");  
	            toClient.write(buffer);  
	            toClient.flush();  
	            toClient.close(); 
	            new File(basePath+saleBillId+".xls").delete();//删除文件
	        }catch (Exception e){  
	            e.printStackTrace();
	        }
		}
		mv.addObject("result",result);
		//return mv;
	}
	
}

