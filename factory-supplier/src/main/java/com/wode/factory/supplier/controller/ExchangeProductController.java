/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.Token;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.company.query.UserExchangeTicketVo;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.facade.ProductFacade;
import com.wode.factory.supplier.service.ExchangeSuborderService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SupplierExchangeProductService;
import com.wode.factory.supplier.service.UserExchangeTicketService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.UserInterceptor;
@Controller
@RequestMapping("exchangeProduct")
public class ExchangeProductController extends BaseSpringController {

    @Autowired
    @Qualifier("shopService")
    private ShopService shopService;

	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
    
	@Resource
	private SupplierExchangeProductService supplierExchangeProductService;
	
	@Resource
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private ProductFacade productFacade;
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	
	public ExchangeProductController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/**
	 * 换领记录
	 * @param request
	 * @param response
	 * @param ticketId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="exchangeProductHis")
	@Token(remove=true)
	public ModelAndView exchangeProductHis(HttpServletRequest request,HttpServletResponse response,Long ticketId) throws Exception {
		
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			mv.setViewName("product/product/exchangeProductHis");
			Integer page=1;
			Integer size=10;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="10";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			String type=request.getParameter("type");
			if(StringUtils.isEmpty(type)){
				type ="1";
			}
			SupplierExchangeProduct sep =supplierExchangeProductService.getById(ticketId);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("productId", sep.getProductId());
			Integer reservedTotal = exchangeSuborderService.findOrderTotalByMap(map);
			sep.setReservedNum(exchangeSuborderService.findReservedNumByMap(map));//已预订数
			Integer exchangeSuTotal = suborderService.findOrderTotalByMap(map);
			sep.setExchangeSuNum(suborderService.findExchangeSuNumByMap(map));//已换领数
			List<ExchangeProductVo> exReservedlist = new ArrayList<ExchangeProductVo>();
			map.put("size",size);
			Integer startnum=(page-1)*size;
			if(type.equals("1")) {
				result.setTotal(reservedTotal);
				if(reservedTotal<startnum){
					startnum=reservedTotal-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				map.put("startnum", startnum);
				exReservedlist = exchangeSuborderService.findExchangeSubOrderItemByMap(map);
			}else {
				result.setTotal(exchangeSuTotal);
				if(exchangeSuTotal<startnum){
					startnum=exchangeSuTotal-size;
				}
				if(startnum<0){
					startnum = 0;
				}
				map.put("startnum", startnum);
				exReservedlist = suborderService.findSubOrderItemByMap(map);
			}
			if(exReservedlist != null && exReservedlist.size() >0) {
				for (ExchangeProductVo exchangeProductVo : exReservedlist) {
					if(exchangeProductVo.getPhone() != null && !"".equals(exchangeProductVo.getPhone())) {
						exchangeProductVo.setPhone(exchangeProductVo.getPhone().substring(exchangeProductVo.getPhone().length()-3, exchangeProductVo.getPhone().length()));
					}
					if(exchangeProductVo.getNickname() == null || "".equals(exchangeProductVo.getNickname())) {
						exchangeProductVo.setNickname(exchangeProductVo.getComName());
					}
				}
			}
			
			result.setPage(page);
			result.setSize(size);
			result.setErrorCode("0");
			result.setMsgBody(exReservedlist);
			mv.addObject("type", type);
			mv.addObject("sep", sep);
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);
			mv.addObject("ticketId",ticketId);
		}
		
		return mv;
	}

	/**
	 *对账单list换领币领用
	 **/
	@RequestMapping(value="exchangeHis",method=RequestMethod.GET)
	@Token(remove=true)
	public ModelAndView exchangeHis(HttpServletRequest request,HttpServletResponse response,Long ticketId) throws Exception {
		
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		//在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request,shopService);
		if(userModel == null) {
			//会话中usermodel对象为空
			result.setErrorCode("10000");
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			SupplierExchangeProduct sep =supplierExchangeProductService.getById(ticketId);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("exchangeProductId", ticketId);
			List<UserExchangeTicketVo> uets = userExchangeTicketService.findListByMap(map);
			mv.setViewName("product/product/exchangeHis");
			
			mv.addObject("sep",sep);
			mv.addObject("uets",uets);
			BigDecimal share = sep.getShareAmount();
			if(sep.getClearStatus() != null && sep.getClearStatus() == 0) {
				for (UserExchangeTicketVo uet : uets) {
					BigDecimal left = this.getLeft(uet);
					if(share.compareTo(left)>0) {
						uet.setActiveAmount(left);
						share=share.subtract(left);
					} else {
						uet.setActiveAmount(share);
						share=BigDecimal.ZERO;
					}
					
					// 暂时回退预付
					if(NumberUtil.isGreaterZero(uet.getPrepayAmount())) {
						uet.setUsedAmount(uet.getUsedAmount().subtract(uet.getPrepayAmount()));
					}
					left = this.getLeft(uet);
					
					if(NumberUtil.isGreaterZero(left)) {
						uet.setLeftCnt(left.divide(sep.getProductPrice(), 2, BigDecimal.ROUND_DOWN));
					} else {
						uet.setLeftCnt(BigDecimal.ZERO);
					}
				}
			}
		}
		
		return mv;
	}
	
	/**
	 * ajax修改sku弹出框中信息，点击确认要操作的
	 **/
	@RequestMapping(value = "exchangStop", method = RequestMethod.POST)
	@ResponseBody
	public ActResult exchangStop(HttpServletRequest request, HttpServletResponse response,Long delId)
			throws Exception {
		
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		if (userModel == null) {
			return ActResult.fail("请重新登录后，再试");
		} else {
			SupplierExchangeProduct ex =supplierExchangeProductService.getById(delId);
			
			ex.setStatus(4);			// 状态=4:提前终止
			ex.setLimitEnd(new Date());	// 使用期限=系统时间
			ex.setUpdateDate(ex.getLimitEnd());
			ex.setUpdateUser(userModel.getId());
			supplierExchangeProductService.update(ex);
			
			UserExchangeTicket entity = new UserExchangeTicket();
			entity.setExchangeProductId(ex.getId());
			entity.setStatus(3);	//3:已过期
			entity.setLimitEnd(ex.getLimitEnd());
			entity.setUpdateDate(ex.getLimitEnd());
			entity.setUpdateUser(userModel.getId());
			userExchangeTicketService.updateEnds(entity);
			
			// 解锁换领商品，并更新成特省
			// productService.unlockExchangeProduct(ex.getProductId());
			this.sellOff(ex.getProductId(),userModel);
			return ActResult.success(null);
		}
	}

	/**
	 * ajax修改sku弹出框中信息，点击确认要操作的
	 **/
	@RequestMapping(value = "exchangOffline", method = RequestMethod.POST)
	@ResponseBody
	public ActResult exchangOffline(HttpServletRequest request, HttpServletResponse response,Long selId)
			throws Exception {
		
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		if (userModel == null) {
			return ActResult.fail("请重新登录后，再试");
		} else {
			SupplierExchangeProduct ex =supplierExchangeProductService.getById(selId);
			
			ex.setStatus(9);			// 状态=9:线下发放
			ex.setOfflineDate(new Date());	// 使用期限=系统时间
			ex.setUpdateDate(ex.getOfflineDate());
			ex.setUpdateUser(userModel.getId());
			supplierExchangeProductService.update(ex);
			
			UserExchangeTicket entity = new UserExchangeTicket();
			entity.setExchangeProductId(ex.getId());
			entity.setStatus(6);	//3:已过期
			entity.setLimitEnd(ex.getLimitEnd());
			entity.setUpdateDate(ex.getLimitEnd());
			entity.setUpdateUser(userModel.getId());
			userExchangeTicketService.updateEnds(entity);
			
			return ActResult.success(null);
		}
	}

	/**
	 * ajax修改sku弹出框中信息，点击确认要操作的
	 **/
	@RequestMapping(value = "exchangDelay", method = RequestMethod.POST)
	@ResponseBody
	public ActResult exchangDelay(HttpServletRequest request, HttpServletResponse response,Long selId,Integer delay)
			throws Exception {
		
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		if (userModel == null) {
			return ActResult.fail("请重新登录后，再试");
		} else {
			SupplierExchangeProduct ex =supplierExchangeProductService.getById(selId);

			Calendar c = Calendar.getInstance();
			c.setTime(ex.getLimitEnd());
			if(delay==5) {
				c.add(Calendar.DAY_OF_MONTH, 15);
			} else {
				c.add(Calendar.MONTH, delay);
			}
			ex.setLimitEnd(c.getTime());
			ex.setUpdateDate(new Date());
			ex.setUpdateUser(userModel.getId());
			supplierExchangeProductService.update(ex);
			
			UserExchangeTicket entity = new UserExchangeTicket();
			entity.setExchangeProductId(ex.getId());
			entity.setLimitEnd(ex.getLimitEnd());
			entity.setUpdateDate(new Date());
			entity.setUpdateUser(userModel.getId());
			userExchangeTicketService.updateEnds(entity);
			
			return ActResult.success(null);
		}
	}
	

	@RequestMapping(value = "exportExchangeHis")
	@ResponseBody
	public void exportExchangeHis(Long ticketId,Integer type,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		SupplierExchangeProduct sep =supplierExchangeProductService.getById(ticketId);

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("换领商品发放");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        String saleBillId = request.getParameter("saleBillId");
	   
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
        font0.setFontHeightInPoints((short)10);//字号 
        font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font0.setColor(HSSFColor.ROYAL_BLUE.index);//颜色
        style0.setFont(font0);
        
        HSSFCellStyle style00 =  wb.createCellStyle(); 
        HSSFFont font00 = wb.createFont();
        font00.setFontName(HSSFFont.FONT_ARIAL);//字体
        font00.setFontHeightInPoints((short)14);//字号 
        font00.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font00.setColor(HSSFColor.ROYAL_BLUE.index);//颜色
        style00.setFont(font00);
        //新版用法 3.8版  
//      sheet.addMergedRegion(new CellRangeAddress(     
//              1, //first row (0-based)  from 行     
//              2, //last row  (0-based)  to 行     
//              1, //first column (0-based) from 列     
//              1  //last column  (0-based)  to 列     
//      ));  
	    
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
        HSSFRow row = sheet.createRow(0); 
        HSSFCell cell0 = row.createCell(2);
        if(type==1) {
        	cell0.setCellValue("换领商品发放(可发放)");
        } else if(type==2) {
        	cell0.setCellValue("换领商品发放(已使用)");
        } else {
        	cell0.setCellValue("换领商品发放（全部）");
        }
        cell0.setCellStyle(style00);
        
        row = sheet.createRow(1);
        cell0 = row.createCell(1);
        cell0.setCellValue("商品名称");
        cell0.setCellStyle(style0);
        row.createCell(2).setCellValue(sep.getProductName());
        
//        row = sheet.createRow(2); 
//        cell0 = row.createCell(1);
//        cell0.setCellValue("商品数量");
//        cell0.setCellStyle(style0);
//        row.createCell(2).setCellValue(sep.getProductCnt()-sep.getSellCnt()-sep.getDistributeCnt());
        
//        row = sheet.createRow(3);
//        cell0 = row.createCell(1);
//        cell0.setCellValue("员工范围");
//        cell0.setCellStyle(style0);
//        if(sep.getEmpLevel()==-1) {
//        	row.createCell(2).setCellValue("全体员工");
//        } else {
//        	row.createCell(2).setCellValue(sep.getEmpLevel()+"级员工");
//        }
        
//        row = sheet.createRow(4); 
//        cell0 = row.createCell(1);
//        cell0.setCellValue("员工人数");
//        cell0.setCellStyle(style0);
//        row.createCell(2).setCellValue(sep.getEmpCnt());
        
        row = sheet.createRow(2);
        cell0 = row.createCell(1);
        cell0.setCellValue("换领期限");
        cell0.setCellStyle(style0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        row.createCell(2).setCellValue(format.format(sep.getLimitStart())+"-"+format.format(sep.getLimitEnd()));        
        
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
        font.setFontHeightInPoints((short)12);//字号 
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font.setColor(HSSFColor.ROYAL_BLUE.index);//颜色
        style.setFont(font);
        row = sheet.createRow(7); 
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("姓名");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("职务");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("电话");  
        cell.setCellStyle(style);
        cell = row.createCell(4);  
        cell.setCellValue("部门");  
        cell.setCellStyle(style);  
        cell = row.createCell(5);  
        cell.setCellValue("换领币（￥）");  
        cell.setCellStyle(style);  
        cell = row.createCell(6);  
        cell.setCellValue("已消费（￥）");  
        cell.setCellStyle(style);  
        cell = row.createCell(7);  
        cell.setCellValue("消费内容");  
        cell.setCellStyle(style);  
        cell = row.createCell(8);  
        cell.setCellValue("领取时间");  
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
        
//		UserExchangeTicket record = new UserExchangeTicket();
//		record.setExchangeProductId(ticketId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("exchangeProductId", ticketId);
		List<UserExchangeTicketVo> uets = userExchangeTicketService.findListByMap(map);
		int rowIndex =7;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < uets.size(); i++)
        {  
            UserExchangeTicketVo uet = uets.get(i); 
            if(type==1) {
            	if(!NumberUtil.isGreaterZero(uet.getLeftCnt())) {
            		continue;
            	}
            } else if(type==2) {
            	if(!NumberUtil.isGreaterZero(uet.getUsedAmount())) {
            		continue;
            	}
            }
            rowIndex++;
            row = sheet.createRow(rowIndex);  
            // 第四步，创建单元格，并设置值  
            cell = row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellValue((i+1)); //序号 
            
            cell = row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getNickname());  //姓名
            
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getDuty());  //职务
            
            cell = row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getPhone());	//电话
            
            cell = row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getSectionName());//部门
            
            cell = row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getEmpAvgAmount().doubleValue());//换领币（￥）
            
            cell = row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getUsedAmount().doubleValue());//已消费（￥）
            
            cell = row.createCell(7);
            cell.setCellStyle(style2);
            cell.setCellValue(uet.getUsedNote());//消费内容
            
            cell = row.createCell(8);
            cell.setCellStyle(style2);
            cell.setCellValue(sdf.format(uet.getCreateDate() == null?new Date():uet.getCreateDate()));//领取时间
        }  
        
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request, "换领商品发放一览_"));
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
	
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	private void refreshProduct(Long productId,boolean crreateHtml) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/rebuild/"+productId, paramMap); //更新缓存和索引
			if(crreateHtml) {
				HttpClientUtil.sendHttpRequest("post", Constant.CREATHTML_API_URL + "/product", paramMap);//生成静态页
			}
		} catch (Exception e) {
		}
	}
	
	private void sellOff(Long productId,UserFactory userModel) {
		
		Product product =productService.getById(productId);
		if (product != null) {
			// skuMap中保存着sku新旧的对应关系
			Map<Long, ProductSpecifications> skuMap = new HashMap<Long, ProductSpecifications>();
			// stockMap中保存着库存新旧的对应关系
			Map<Long, Inventory> stockMap = new HashMap<Long, Inventory>();
			productFacade.productToapprProduct(productId,0, skuMap, stockMap);
			
			List<Long> idslist = new ArrayList<Long>();
			idslist.add(productId);
			ActResult ret = productService.sellOff(idslist, userModel);
			if(ret.isSuccess()) {
				for (Long long1 : idslist) {
					this.destroyProduct(long1);
				}
			}
		}
	}
	
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	private void destroyProduct(Long productId) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/destroy/"+productId, paramMap); //更新缓存和索引
		} catch (Exception e) {
		}
	}
	
	private BigDecimal getLeft(UserExchangeTicketVo uet) {
		return uet.getEmpAvgAmount().subtract(uet.getUsedAmount()).subtract(uet.getActiveAmount());
	}
	

	private String getFileNameForSave(HttpServletRequest request,String name) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMdd");
		
		String filename =name+format.format(new Date()) +".xls";
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





