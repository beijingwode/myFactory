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
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.DateUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.mapper.ProductBrandImageDao;
import com.wode.factory.model.CheckReview;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.CheckReviewService;
import com.wode.factory.service.ProductBrandService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.ProductBrandVo;
import com.wode.search.WodeSearchManager;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("brand")
public class ProductBrandController {
	@Autowired
	private SupplierService supplierService;

    @Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;
	@Resource
	private ProductService productService;
	@Autowired
	private ProductBrandService productBrandService;
	@Autowired
	CheckReviewService checkReviewService;
	
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	
	@Resource
	private HtmlAction htmlAction;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private WodeSearchManager wsm;
	@Autowired
	private ProductBrandImageDao productBrandImageDao;

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
		
		model.addAttribute("supplierList", getSupplierList(query));
		return "manager/brand/base";
	}
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		return supplierService.getPage(query).getList();
	}


	@RequestMapping("baseLawyer")
	public String baseLawyer(Model model,HttpSession session) {
		
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
		
		return "manager/brand/base-lawyer";
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
		SysUser user = (SysUser)obj;
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
				
		PageInfo<ProductBrandVo> page = productBrandService.findList(params);
//		for (ProductBrandVo p : page.getList()) {
//			p.setCategorys(productBrandService.getCategorysByBrand(p.getSupId(), p.getId()));
//		}

		model.addAttribute("uid", user.getId());
		model.addAttribute("page", page);
		return "manager/brand/list";
	}

	/**
	 * 属性列表
	 *
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listLawyer", method = RequestMethod.POST)
	public String listLawyer(@RequestParam Map<String, Object> params, Model model,HttpSession session) {

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
				
		PageInfo<ProductBrandVo> page = productBrandService.findListLawyer(params);

		model.addAttribute("page", page);
		return "manager/brand/list-lawyer";
	}
	
	@RequestMapping(value = "/toSetLevel", method = RequestMethod.POST)
	public String toSetManager(Model m,Long id){
		m.addAttribute("id", id);
		return "manager/brand/set_level";
	}
	

	@RequestMapping(value = "/toSetMemo", method = RequestMethod.POST)
	public String toSetMemo(Model m,Long id,Long checkId){
		
		m.addAttribute("id", id);
		m.addAttribute("check", checkReviewService.getById(checkId));
		return "manager/brand/set-memo";
	}
	
	@RequestMapping("/setLevel")
	@ResponseBody
	public int setManager(Long categoryId, Long id,HttpSession session) {
		
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		productBrandService.setLevel(categoryId, id,user.getName());
		
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);
		List<ProductBrand>  page = productBrandService.findByMap(params);
		ProductBrand pbBrand =page.get(0);
		Integer result=10;
		if(categoryId!=null && categoryId>0)  {
			result=categoryId.intValue();
		}
		redisUtil.setMapData("REDIS_PRODUCT_BRAND", StringUtils.isEmpty(pbBrand.getName())?pbBrand.getNameEn():pbBrand.getName(), result+"");
		//根据品牌id查找该品牌下的已上架的商品
		Map<String, Object> param = new HashMap();
		param.put("status", 2);
		param.put("isMarketable", 1);
		param.put("brandId", id);
		List<Product> listPro = productService.find(param);
		for (Product pro : listPro) {
			//更新商品的品牌等级
			wsm.updateBrandLevel(pro.getId(),result);
		}
		return 0;
	}
	
	@RequestMapping("/delMemo")
	@ResponseBody
	public int delMemo(Long checkId, HttpSession session) {

		if(checkId!=null){
			checkReviewService.removeById(checkId);
		}
		return 0;
	}

	@RequestMapping("/setMemo")
	@ResponseBody
	public int setMemo(Long id,Long checkId,String memo,String alarmDate, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		CheckReview n = new CheckReview();
		n.setObjKey(id);
		n.setMemo(memo);
		if(!StringUtils.isEmpty(alarmDate)) {
			n.setAlarmDate(TimeUtil.strToDate(alarmDate + " 00:00:00"));
		}
		n.setCreateTime(new Date());
		n.setCreateUser(user.getName());
		
		if(checkId!=null){
			checkReviewService.removeById(checkId);
		}
		
		checkReviewService.insert(n);
		return 0;
	}
	
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(!isLeander(user.getId())) {
			if(!params.containsKey("managerId")){
				params.put("managerId", user.getId());
			}
		}
		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("品牌一览"); 
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
        headers.add("品牌id");
        headers.add("商标注册号");
        headers.add("品牌名称(英文)");
        headers.add("级别");
        headers.add("类型");
        headers.add("TM/R");
        headers.add("有效期");
        headers.add("店铺名称");
        headers.add("授权有效期");
        headers.add("商家名称");
        headers.add("招商经理");
        headers.add("商品首次上传日");
        headers.add("上架商品数");
        headers.add("创建时间");
        headers.add("关联分类");
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
		PageInfo<ProductBrandVo> page = productBrandService.findList(params);
		for (ProductBrandVo p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("品牌id");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
            //headers.add("商标注册号");
            row.createCell(col++).setCellValue(p.getBrandNo());
            //headers.add("品牌名称(英文)");
            row.createCell(col++).setCellValue(StringUtils.isEmpty(p.getNameEN())?p.getName():p.getName()+"("+p.getNameEN()+")");
            //headers.add("级别");
            Long cid = p.getCategoryId();
            if(cid==null) {
                row.createCell(col++).setCellValue("");
            } else if(cid==1L) {
                row.createCell(col++).setCellValue("一类品牌");
            } else if(cid==2L) {
                row.createCell(col++).setCellValue("二类品牌");
            } else if(cid==3L) {
                row.createCell(col++).setCellValue("三类品牌");
            } else if(cid==4L) {
                row.createCell(col++).setCellValue("四类品牌");
            } else if(cid==0L) {
                row.createCell(col++).setCellValue("未知");
            } else {
                row.createCell(col++).setCellValue("未设置");
            } 
            //headers.add("类型");
            row.createCell(col++).setCellValue("0".equals(p.getNatural())?"自有":"代理");
            //headers.add("TM/R");
            row.createCell(col++).setCellValue("0".equals(p.getBrandType())?"TM标":"R标");
            //headers.add("有效期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue("0".equals(p.getBrandType())?DateUtils.formatDate(p.getBrandcreatedTM(),"yyyy-MM-dd"):DateUtils.formatDate(p.getBegintimeR(),"yyyy-MM-dd")+"~"+DateUtils.formatDate(p.getEndtimeR(),"yyyy-MM-dd") ); //创建时间
            //headers.add("店铺名称");
            row.createCell(col++).setCellValue(p.getShopName()); 
            //headers.add("授权有效期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue((p.getBegintimeAuth()==null?"":DateUtils.formatDate(p.getBegintimeAuth(),"yyyy-MM-dd"))+"~"+(p.getEndtimeAuth()==null?"":DateUtils.formatDate(p.getEndtimeAuth(),"yyyy-MM-dd"))); //创建时间
            //headers.add("商家名称");
            row.createCell(col++).setCellValue(p.getSupplierName());
            //headers.add("招商经理");
            row.createCell(col++).setCellValue(p.getManagerName());
            //headers.add("商品首次上传日");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getFirstCreate()==null?"":DateUtils.formatDate(p.getFirstCreate(),"yyyy-MM-dd")); //创建时间
            //headers.add("上架商品数");
            row.createCell(col++).setCellValue(p.getProCnt()==null?"":p.getProCnt()+"");
            //headers.add("创建时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateDate()==null?"":DateUtils.formatDate(p.getCreateDate(),"yyyy-MM-dd")); //创建时间
            //headers.add("关联分类");
            //row.createCell(col++).setCellValue(productBrandService.getCategorysByBrand(p.getSupId(), p.getId()));
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
	

	@RequestMapping(value = "exportExcelLawyer")
	@ResponseBody
	public void exportExcelLawyer(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("品牌一览"); 
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
        headers.add("品牌id");
        headers.add("商标注册号");
        headers.add("品牌名称(英文)");
        headers.add("类型");
        headers.add("进口");
        headers.add("TM/R");
        headers.add("有效期");
        headers.add("店铺名称");
        headers.add("授权有效期");
        headers.add("商家名称");
        headers.add("招商经理");
        headers.add("创建时间");
        headers.add("备注");
        headers.add("再审提醒日");
        headers.add("商标授权书");
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
		
        PageInfo<ProductBrandVo> page = productBrandService.findListLawyer(params);
		for (ProductBrandVo p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("品牌id");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
            //headers.add("商标注册号");
            row.createCell(col++).setCellValue(p.getBrandNo());
            //headers.add("品牌名称(英文)");
            row.createCell(col++).setCellValue(StringUtils.isEmpty(p.getNameEN())?p.getName():p.getName()+"("+p.getNameEN()+")");
            //headers.add("类型");
            row.createCell(col++).setCellValue("0".equals(p.getNatural())?"自有":"代理");
            //headers.add("进口");
            row.createCell(col++).setCellValue("1".equals(p.getImportFlg())?"进口":"非进口");
            //headers.add("TM/R");
            row.createCell(col++).setCellValue("0".equals(p.getBrandType())?"TM标":"R标");
            //headers.add("有效期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue("0".equals(p.getBrandType())?DateUtils.formatDate(p.getBrandcreatedTM(),"yyyy-MM-dd"):DateUtils.formatDate(p.getBegintimeR(),"yyyy-MM-dd")+"~"+DateUtils.formatDate(p.getEndtimeR(),"yyyy-MM-dd") ); //创建时间
            //headers.add("店铺名称");
            row.createCell(col++).setCellValue(p.getShopName()); 
            //headers.add("授权有效期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue((p.getBegintimeAuth()==null?"":DateUtils.formatDate(p.getBegintimeAuth(),"yyyy-MM-dd"))+"~"+(p.getEndtimeAuth()==null?"":DateUtils.formatDate(p.getEndtimeAuth(),"yyyy-MM-dd"))); //创建时间
            //headers.add("商家名称");
            row.createCell(col++).setCellValue(p.getSupplierName());
            //headers.add("招商经理");
            row.createCell(col++).setCellValue(p.getManagerName());
            //headers.add("创建时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(DateUtils.formatDate(p.getCreateDate(),"yyyy-MM-dd")); //创建时间
            //headers.add("备注");
            row.createCell(col++).setCellValue(p.getCheckMemo());
            //headers.add("再审提醒日");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCheckAlarmDate()==null?"":DateUtils.formatDate(p.getCheckAlarmDate(),"yyyy-MM-dd")); //创建时间
            
            //headers.add("商标授权书");
			List<ProductBrandImage> pbiList = productBrandImageDao.findByBrandId(p.getId());
			String pimg = "";
			for (ProductBrandImage productBrandImage : pbiList) {
				pimg = productBrandImage.getSource()+"\n";
			}
            row.createCell(col++).setCellValue(pimg);
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
	
	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}
	

	private String getFileNameForSave(HttpServletRequest request) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = "品牌一览"+ DateUtils.formatDate(new Date(),"_yyyyMMdd") +".xls";
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

