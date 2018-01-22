/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SupplierProductExcel;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.SupplierProductExcelQuery;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierProductExcelService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.ExcelUtil;
import com.wode.factory.supplier.util.UserInterceptor;
import com.wode.factory.supplier.util.ZipCompress;

@Controller
@RequestMapping("supplierProductExcel")
public class SupplierProductExcelController extends BaseSpringController{
	@Autowired
	@Qualifier("supplierProductExcelService")
	private SupplierProductExcelService supplierProductExcelService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	@Autowired
	@Qualifier("excelUtil")
	ExcelUtil excelUtil;
	
	@Autowired
	@Qualifier("zipCompress")
	ZipCompress zipCompress;
	
	private static final Logger logger = LoggerFactory.getLogger(SupplierProductExcelController.class);
	/**
	 * 添加批量上传excel
	 * @param request
	 * @param productExcel
	 * @return
	 */
	@RequestMapping("addProductExcel")
	@ResponseBody
	public ActResult<Object> addImageResource(HttpServletRequest request,SupplierProductExcel productExcel){
		if(StringUtils.isNullOrEmpty(productExcel.getExcelUrl()))
			return ActResult.fail("excel为空");
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			return ActResult.fail("用户未登录");
		}
		productExcel.setCreateTime(new Date());
		productExcel.setSupplierId(us.getSupplierId());
		productExcel.setStatus(0);
		
		this.supplierProductExcelService.save(productExcel);
		return ActResult.success("保存成功");
	}
	
	
	/**
	 * 分页查询
	 * @param request
	 * @param model
	 * @param productExcelQuery
	 * @return
	 */
	@RequestMapping("fetchProductExcel")
	public ModelAndView fetchProductExcel(HttpServletRequest request,ModelAndView model,SupplierProductExcelQuery productExcelQuery){
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			model.setViewName("redirect:/user/login.html");
		}
		
		productExcelQuery.setSupplierId(us.getSupplierId());
		PageInfo<SupplierProductExcelQuery> page = this.supplierProductExcelService.selectPageInfo(productExcelQuery);
		model.addObject("page", page);
		model.addObject("query", productExcelQuery);
		model.setViewName("product/product/batch_upload");
		return model;
	}
	
	/**
	 * 修改状态
	 * @param request
	 * @param model
	 * @param productExcel
	 * @return
	 */
	@RequestMapping("editorProductExcelStatus")
	@ResponseBody
	public ActResult<Object> editorProductExcelStatus(HttpServletRequest request,ModelAndView model,SupplierProductExcel productExcel){
		if(StringUtils.isNullOrEmpty(productExcel.getId()))
			return ActResult.fail("参数错误");
		
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			return ActResult.fail("用户未登录");
		}
		SupplierProductExcel supplierProductExcel = this.supplierProductExcelService.getById(productExcel.getId());
		if(supplierProductExcel.getSupplierId().equals(us.getSupplierId())){
			/**
			 * -1已取消
			 * 0正常
			 * 1处理中
			 * 2处理完成
			 * */
			if(supplierProductExcel.getStatus()!=null&&supplierProductExcel.getStatus().equals(1)){
				return ActResult.fail("表格正在处理中,不能取消");
			}
			if(supplierProductExcel.getStatus()!=null&&supplierProductExcel.getStatus().equals(2)){
				return ActResult.fail("表格已完成处理,不能取消");
			}
			supplierProductExcel.setSupplierId(us.getSupplierId());
			supplierProductExcel.setStatus(-1);
			this.supplierProductExcelService.update(supplierProductExcel);
			return ActResult.success("修改成功");
		}else{
			return ActResult.fail("参数错误");
		}
	}
	
	/**
	 * 上传excel文件
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="uploadExcel.json",method=RequestMethod.POST)
	@ResponseBody
	public void uploadExcel(MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		JSONObject jo = new JSONObject();
		if(file.isEmpty()){
			this.response(response, jo.toJSONString(ActResult.fail("请选择文件后上传")));
	     }
	     if(file.getSize() > (1024*1024*30)){
			this.response(response, jo.toJSONString(ActResult.fail("文件大小不能大于30M！")));
	     }
    	 try {
    		//将文件上传到服务器
			String url = zipCompress.uploadFileZip(file, request);
			if(StringUtils.isNullOrEmpty(url)){
				this.response(response, jo.toJSONString(ActResult.fail("上传失败")));
			}else{
				this.response(response, jo.toJSONString(ActResult.success(url)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("zip文件夹上传异常"+"----------------------------"+e.getMessage());
			e.printStackTrace();
			this.response(response, jo.toJSONString(ActResult.fail("上传异常")));
		}
		
	}
	
	/**
	 *  保存上传的excel文件记录
	 * @param request
	 * @param productExcel
	 * @return
	 */
	@RequestMapping(value="addProductExcel",method=RequestMethod.POST)
	@ResponseBody
	public ActResult<Object> addProductExcel(HttpServletRequest request,SupplierProductExcel productExcel){
		UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us==null){
			return ActResult.fail("用户未登录");
		}
		productExcel.setCreateTime(new Date());
		productExcel.setSupplierId(us.getSupplierId());
		this.supplierProductExcelService.save(productExcel);
		return ActResult.success("添加成功");
	}
	
	
	private void response(HttpServletResponse response,String str){
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
	
	@RequestMapping(value="checkFileExist",method=RequestMethod.POST)
	@ResponseBody
	public ActResult<String> checkFileExist(HttpServletRequest request,HttpServletResponse response,Long param){
		//excel文件的名称
		String fileName = param+".xlsx";
		//excel文件下载路径
		String downUrl = Constant.EXCEL_OUTPUT+fileName;
		ActResult<String> act = new ActResult<String>();
		File f = new File(downUrl);
    	if(f.exists()){
    		return act;
    	}else{
    		act.setSuccess(false);
    		return act;
    	}
	}
	
	
	@RequestMapping(value="downLoad",method=RequestMethod.GET)
	public void downLoad(HttpServletRequest request,HttpServletResponse response,Long param){
		java.io.BufferedInputStream bis = null;  
        java.io.BufferedOutputStream bos = null;
		try {
			//excel文件的名称
			String fileName = param+".xlsx";
			//excel文件下载路径
			String downUrl = Constant.EXCEL_OUTPUT+fileName;
			File f = new File(downUrl);
        	if(f.exists()){
        		long fileLength = f.length();  
        		response.setContentType("application/x-msdownload;");
        		response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
        		response.setHeader("Content-Length", String.valueOf(fileLength));  
        		bis = new BufferedInputStream(new FileInputStream(downUrl));
        		bos = new BufferedOutputStream(response.getOutputStream());
        		byte[] buff = new byte[2048];  
        		int bytesRead;
        		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
        			bos.write(buff, 0, bytesRead);
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null){
            	try {
            		bis.close();
            		if (bos != null)  
            			bos.close();  
            	} catch (IOException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
            }
        } 
	}
}

