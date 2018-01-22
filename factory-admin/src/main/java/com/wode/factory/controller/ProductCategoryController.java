/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.StringUtils;
import com.wode.common.util.UploadUtil;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.vo.CategoryBrandVo;
import com.wode.factory.vo.ProductCategoryVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;

@Controller
@RequestMapping("productCategory")
public class ProductCategoryController{
	@Autowired
	private ProductCategoryService productCategoryService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping
	public String index(Model model) throws UnsupportedEncodingException {
		List<ProductCategoryVo> list  = this.productCategoryService.getProductCategoryTree();
		for (ProductCategoryVo productCategoryVo : list) {
			productCategoryVo.setName(URLEncoder.encode(productCategoryVo.getName(),"UTF-8"));
			productCategoryVo.setParentName(productCategoryVo.getParentName()==null?null:URLEncoder.encode(productCategoryVo.getParentName(),"UTF-8"));
		}
		model.addAttribute("tree", JSON.toJSONString(list));
		return "manager/productCategory";
	}
	
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<ProductCategoryVo> tree() {
		return this.productCategoryService.getProductCategoryTree();
	}
	
	
	@RequestMapping("{url}/showlayer")
	public String selectProductCategory(@PathVariable("url") String url,Long id,Long parentId,Model model){
		if(StringUtils.isEquals(url, "edit")){
			ProductCategoryVo vo = this.productCategoryService.selectById(id);
			model.addAttribute("productCategory", vo);
		}else if(StringUtils.isEquals(url, "add")){
			ProductCategoryVo pVo = this.productCategoryService.selectById(parentId);
			model.addAttribute("parent", pVo);
		}
		
		
		return "add".equals(url)?"manager/productCategory-save":"manager/productCategory-update";
	}
	
	
	@RequestMapping("list")
	public String list(Model model,String name,Integer pageNum,Long rootId,String brotherOrderAll,Integer pageSize,ProductCategoryVo vo){
		PageInfo<ProductCategoryVo> list = this.productCategoryService.selectProductCategory(rootId,brotherOrderAll,name, pageNum, pageSize);
		model.addAttribute("productCategory", list);
		return "manager/productCategory-list";
	}
	
	
	@RequestMapping("save")
	public @ResponseBody Integer save(ProductCategory proCate){
		Integer i = 0;
		//id为空，添加数据
		if(StringUtils.isEmpty(proCate.getId())){
			i = this.productCategoryService.save(proCate);
		//id不为空，为修改数据
		}else{
			i = this.productCategoryService.update(proCate);
		}
		return i;
	}
	
	
	@RequestMapping("delete")
	public @ResponseBody ActResult delete(Long id,Long rootId,String brotherOrderAll){
		return this.productCategoryService.delete(id);
	}
	
	
	@RequestMapping("upload")
	@ResponseBody
	public ActResult  upload(HttpServletRequest request){
		return new UploadUtil().uploadImage(request);
	}

	@RequestMapping("base")
	public String toPageAttrView(Model model,HttpSession session) {
		
		base(model, session);
		return "manager/category/base";
	}

	
	@RequestMapping("baseCount")
	public String countBase(Model model,HttpSession session) {
		
		base(model, session);
		return "manager/category/base-count";
	}
	
	private void base(Model model, HttpSession session) {
		Map<String, Object> query = new HashMap<String, Object>();
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		model.addAttribute("mlist", managerList());
		model.addAttribute("uid", user.getId());;
		model.addAttribute("root", productCategoryService.findByDeep(1));			
		model.addAttribute("supplierList", getSupplierList(query));
	}

	private List<SysUser> managerList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		return list;
	}
	
	private List<Supplier> getSupplierList(Map<String, Object> query) {
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 50);
		
		return supplierService.getPage(query).getList();
	}

	@RequestMapping(value = "getChichdren")
	@ResponseBody
	public List<ProductCategory> getChichdren(Long pid){
		return productCategoryService.selectByPid(pid);
	}
	
	@RequestMapping(value = "listCont", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model,HttpSession session) {

		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		} 

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
				
		PageInfo<CategoryBrandVo> page = productCategoryService.findCountList(params);
//		for (ProductBrandVo p : page.getList()) {
//			p.setCategorys(productBrandService.getCategorysByBrand(p.getSupId(), p.getId()));
//		}

		model.addAttribute("uid", user.getId());
		model.addAttribute("page", page);
		return "manager/category/list";
	}

	@RequestMapping(value = "listPCount", method = RequestMethod.POST)
	public String listPCount(@RequestParam Map<String, Object> params, Model model,HttpSession session) {				
		PageInfo<CategoryBrandVo> page = productCategoryService.findPCountList(params);

		model.addAttribute("page", page);
		return "manager/category/p-list";
	}

	@RequestMapping(value = "exportExcelC")
	@ResponseBody
	public void exportExcelC(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {

		List<ProductCategoryVo> list  = this.productCategoryService.getProductCategoryTree();
		List<ProductCategoryVo> roots = new ArrayList<ProductCategoryVo>();
		
		// 1 级分类
		for (ProductCategoryVo productCategoryVo : list) {
			if(productCategoryVo.getPid() == null) {
				roots.add(productCategoryVo);
			}
		}
		
		// 2级分类
		for (ProductCategoryVo productCategoryVo : list) {
			if(productCategoryVo.getPid()!=null && productCategoryVo.getPid().equals(productCategoryVo.getRootId())) {
				ProductCategoryVo root=null;
				for (ProductCategoryVo r1 : roots) {
					if(productCategoryVo.getPid().equals(r1.getId())) {
						root=r1;
					}
				}
				
				if(root != null) {
					List<ProductCategory> ps = root.getChildrens();
					if(ps == null) {
						ps = new ArrayList<ProductCategory>();
						root.setChildrens(ps);
					}
					
					ps.add(productCategoryVo);
				}
			}
		}

		// 3级分类
		for (ProductCategoryVo productCategoryVo : list) {
			if(productCategoryVo.getPid()!=null && !productCategoryVo.getPid().equals(productCategoryVo.getRootId())) {
				ProductCategoryVo root=null;
				for (ProductCategoryVo r1 : roots) {
					if(productCategoryVo.getRootId().equals(r1.getId())) {
						root=r1;
					}
				}
				if(root != null) {
					ProductCategory p=null;
					for (ProductCategory p2 : root.getChildrens()) {
						if(productCategoryVo.getPid().equals(p2.getId())) {
							p=p2;
						}
					}
					
					if(p!=null) {
						List<ProductCategory> ps = p.getChildrens();
						if(ps == null) {
							ps = new ArrayList<ProductCategory>();
							p.setChildrens(ps);
						}
						
						ps.add(productCategoryVo);
					}
				}
			}
		}
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("品类一览"); 
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
        headers.add("分类id");
        headers.add("一级分类");
        headers.add("二级分类");
        headers.add("三级分类");
        headers.add("佣金比例(%)");
       
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
        for (ProductCategoryVo root : roots) {
			for (ProductCategory p : root.getChildrens()) {
				for (ProductCategory pc : p.getChildrens()) {
					currentRow++;
					int col=0;
		            row = sheet.createRow(currentRow); 
		            //headers.add("分类id");
		            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(pc.getId().toString());
		            //headers.add("一级分类");
		            row.createCell(col++).setCellValue(root.getName());
		            //headers.add("二级分类");
		            row.createCell(col++).setCellValue(p.getName());
		            //headers.add("三级分类");
		            row.createCell(col++).setCellValue(pc.getName());
		            //headers.add("佣金比例(%)");
		            row.createCell(col++).setCellValue(pc.getCommissionScale());
		      
				}
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

	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 100000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

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
        headers.add("分类id");
        headers.add("一级分类");
        headers.add("二级分类");
        headers.add("三级分类");
        headers.add("商标注册号");
        headers.add("品牌名称(英文)");
        headers.add("级别");
        headers.add("类型");
        headers.add("进口");
        headers.add("商家名称");
        headers.add("商家类型");
        headers.add("招商经理");
        headers.add("创建时间");
        headers.add("上架商品数");
        headers.add("折扣");
        headers.add("商品首次上传日");
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
        DecimalFormat df =new DecimalFormat("######0.00"); 
		PageInfo<CategoryBrandVo> page = productCategoryService.findCountList(params);
		for (CategoryBrandVo p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("分类id");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
            //headers.add("一级分类");
            row.createCell(col++).setCellValue(p.getName1());
            //headers.add("二级分类");
            row.createCell(col++).setCellValue(p.getName2());
            //headers.add("三级分类");
            row.createCell(col++).setCellValue(p.getName3());
            //headers.add("商标注册号");
            row.createCell(col++).setCellValue(p.getBrandNo());
            //headers.add("品牌名称(英文)");
            row.createCell(col++).setCellValue(StringUtils.isEmpty(p.getNameEN())?p.getName():p.getName()+"("+p.getNameEN()+")");
            //headers.add("级别");
            Integer cid = p.getPbLevel();
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
            row.createCell(col++).setCellValue(p.getNatural());
            //headers.add("进口");
            row.createCell(col++).setCellValue(p.getImportFlg()); 
            //headers.add("商家名称");
            row.createCell(col++).setCellValue(p.getSupplierName());
            //headers.add("商家类型");
            row.createCell(col++).setCellValue(p.getProperty());
            //headers.add("招商经理");
            row.createCell(col++).setCellValue(p.getManagerName());
            //headers.add("创建时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateDate()==null?"":DateUtils.formatDate(p.getCreateDate(),"yyyy-MM-dd")); //创建时间
            //headers.add("上架商品数");
            row.createCell(col++).setCellValue(p.getProCnt()==null?0:p.getProCnt());
            //headers.add("折扣");
            row.createCell(col++).setCellValue(p.getSale()==null?"":df.format(p.getSale()));
            //headers.add("商品首次上传日");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getCreateDatef()==null?"":DateUtils.formatDate(p.getCreateDatef(),"yyyy-MM-dd")); //创建时间
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
	
	@RequestMapping(value = "exportPExcel")
	@ResponseBody
	public void downLoadPExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 100000);
		Object obj = request.getSession().getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("品类一览"); 
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
        headers.add("分类id");
        headers.add("一级分类");
        headers.add("二级分类");
        headers.add("三级分类");
        headers.add("上架商品数");
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
        DecimalFormat df =new DecimalFormat("######0.00"); 
		PageInfo<CategoryBrandVo> page = productCategoryService.findPCountList(params);
		for (CategoryBrandVo p : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("分类id");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId().toString());
            //headers.add("一级分类");
            row.createCell(col++).setCellValue(p.getName1());
            //headers.add("二级分类");
            row.createCell(col++).setCellValue(p.getName2());
            //headers.add("三级分类");
            row.createCell(col++).setCellValue(p.getName3());
            //headers.add("上架商品数");
            row.createCell(col++).setCellValue(p.getProCnt()==null?0:p.getProCnt());
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
		String filename = "品类一览"+ DateUtils.formatDate(new Date(),"_yyyyMMdd") +".xls";
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

