package com.wode.factory.supplier.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.dao.EnterpriseUserDao;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.supplier.service.ProductBrandService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.ShippingTemplateService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SpecificationService;
import com.wode.factory.supplier.service.SpecificationValueService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierSkuImageService;
import com.wode.factory.supplier.service.SupplierSpecificationService;
@Service("excelUtil")
public class ExcelUtil {
//	@Autowired
//	EnterpriseUserService enterpriseUserService;
	@Autowired
	EnterpriseUserService enterpriseUserService;
	@Autowired
	EnterpriseUserDao enterpriseUserDao;
	
	@Autowired
	@Qualifier("supplierCategoryService")
	private SupplierCategoryService supplierCategoryService;
	
	@Autowired
	@Qualifier("supplierSkuImageService")
	private SupplierSkuImageService supplierSkuImageService;
	
	@Autowired
	@Qualifier("supplierSpecificationService")
	private SupplierSpecificationService supplierSpecificationService;
	
	@Autowired
	@Qualifier("productBrandService")
	private ProductBrandService productBrandService;
	
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
	
	@Autowired
	@Qualifier("specificationValueService")
	private SpecificationValueService specificationValueService;
	@Autowired
	@Qualifier("specificationService")
	private SpecificationService specificationService;
	
	@Autowired
	@Qualifier("shippingTemplateService")
	private ShippingTemplateService shippingTemplateService;
	
	@Autowired
	@Qualifier("zipCompress")
	ZipCompress zipCompress;

	@Autowired
	@Qualifier("shopService")
	ShopService shopService;
	
	private DecimalFormat zipDf = new DecimalFormat("##.#");

	// 判断excel版本
	static Workbook openWorkbook(InputStream in, String filename)
			throws IOException {
		Workbook wb = null;
		if (filename.endsWith(".xlsx")) {// fileFileName
			wb = new XSSFWorkbook(in);// Excel 2007
		} else {
			wb = (Workbook) new HSSFWorkbook(in);// Excel 2003
		}
		return wb;
	}
	// 判断excel版本
	static Workbook openWorkbook(String filename)throws IOException {
		// 创建输入流
		InputStream in = new FileInputStream(filename);
		Workbook wb = null;
		if (filename.endsWith(".xlsx")) {// fileFileName
			wb = new XSSFWorkbook(in);// Excel 2007
		} else {
			wb = (Workbook) new HSSFWorkbook(in);// Excel 2003
		}
		in.close();
		return wb;
	}
	
	public Workbook getZipWorkbook(String fileName) throws IOException{
		File file = new File(fileName);
		if(file.exists()){	
			return openWorkbook(fileName+"/product.xlsx");
		}else{
			return null;
		}
	}
	
	public static List<String[]> getExcelData(String fileName) throws Exception {
		InputStream in = new FileInputStream(fileName); // 创建输入流
		Workbook wb = openWorkbook(in, fileName);// 获取Excel文件对象
		Sheet sheet = wb.getSheetAt(0);// 获取文件的指定工作表m 默认的第一个
		List<String[]> list = new ArrayList<String[]>();
		Row row = null;
		Cell cell = null;
		int totalRows = sheet.getLastRowNum(); // 总行数
		Row ro = sheet.getRow(0);
		if(totalRows==0||ro==null){
			//内容为空
			return list;
		}else if(ro.getLastCellNum()!=9 && ro.getLastCellNum()!=10){
			//列数错误
			return list;
		}
		int totalCells = ro.getLastCellNum();// 总列数
		//起步在第二行（排除第一样字段名称）
		for (int r = 1; r <= totalRows; r++) {
			row = sheet.getRow(r);
			String[] cells = new String[10] ;	//返回10列
			if(row == null ) {
				list.add(new String[10]);		//返回10列
				continue;
			}
//			System.out.println((r+1)+"="+row);
//			System.out.print("第" + r + "行");
			for (int c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						DecimalFormat df = new DecimalFormat("0");
						cellValue = df.format(cell.getNumericCellValue()) + "";
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;
					default:
						cellValue = "未知类型";
						break;
					}
//					System.out.print("  | " + cellValue + "\t");
					cells[c] = cellValue;
					
				}else {
					if(c<=totalCells){
//						System.out.print("  | " + "" + "\t");
						//如果单元可为null 插入“”
						cells[c] = "";
					}
				}
				
				
			}
//			System.out.println();
			list.add(cells);
		}
		// 返回值集合
		return list;
	}
	
	/**
	 * 校验Excel 数据
	 * @param list 读取Excel文件返回的 数据集合
	 * @return
	 */
	public static ActResult<String> checkoutExcelData(List<String[]> list){
		StringBuffer error = new StringBuffer();//错误信息
		ActResult<String> act = new ActResult<String>();
		//整数和小数
		Pattern p = Pattern.compile("^\\d*[0-9]\\d*$");
		 
		boolean success = true  ;//成功标示
		//电话号码list
		List<String> mobileList = new ArrayList<String>();
		for (int r = 0; r < list.size(); r++) {
			String[] cells = list.get(r);
			
			//判断电话号码 是否重复
			if(!StringUtils.isEmpty(cells[1]) && mobileList.contains(cells[1])){
				int index = mobileList.indexOf(cells[1]);
				error.append("第");
				error.append((index+2));
				error.append("行与第");
				error.append((r+2));
				error.append("手机号码重复！");
				success=false;
				break;
			}
			//电话号码
			mobileList.add(cells[1]);
			/**
			 * 第一列  员工姓名 cells[0]
			 * 第二列 手机号码 cells[1]
			 * 第三列 性别 cells[2]
			 * 第四列 年龄 cells[3]
			 * 第五列 福利级别 cells[4]
			 * 第六列 职务 cells[5]
			 * 第七列 工龄 cells[6]
			 * 第八列 福利额度 cells[7]
			 * 第九列 现金额度 cells[8]
			 * */
			//*******************判断 是否为空start****************
			//排除空行
			if(StringUtils.isEmpty(cells[0]) && StringUtils.isEmpty(cells[1])){// && isEmpty(cells[7]) && isEmpty(cells[8])
				continue ;
			}else{
				if(StringUtils.isEmpty(cells[0])){
					error.append("第");
					error.append((r+2));
					error.append("行、第1列内容不能为空！");
					success=false;
					break;
				}else if(StringUtils.isEmpty(cells[1])){
					error.append("第");
					error.append((r+2));
					error.append("行、第2列数据不能为空！");
					success=false;
					break;
				//第七列不为空  福利额度
				}else if(!StringUtils.isEmpty(cells[7])){
					Matcher m =  p.matcher(cells[7]);
					if(m.matches()){
						
					}else{
						error.append("第");
						error.append((r+2));
						error.append("行福利额度参数有误,只能是整数！");
						success=false;
						break;
					}
				}else if(!StringUtils.isEmpty(cells[8])){
					Matcher m =  p.matcher(cells[8]);
					if(m.matches()){
						
					}else{
						error.append("第");
						error.append((r+2));
						error.append("行现金额度参数有误,只能是整数！");
						success=false;
						break;
					}
				}
//第8列，第9列。可以为空。
//				else if(isEmpty(cells[7]) && isEmpty(cells[8])){
//					error.append("第");
//					error.append((r+2));
//					error.append("行、第7列内购券和金额不能都为空！");
//					success=false;
//					break;
//				}
			}
			//*******************判断 是否为空 end****************
			
			//*******************判断数据格式start****************
			if(!StringUtils.isEmpty(cells[1])){
				if (cells[1].length() != 11 || !cells[1].matches("[0-9]+")) {
					error.append("第");
					error.append((r+2));
					error.append("行、第2列数据格式有误！");
					success=false;
					break;
				}
			}
			if(!StringUtils.isEmpty(cells[7])){
				if(cells[7].length() >10 || !cells[7].matches("[0-9]+")){
					error.append("第");
					error.append((r+2));
					error.append("行、第8列数据格式有误！");
					success=false;
					break;
				}
			}
			if(!StringUtils.isEmpty(cells[8])){
				if(cells[8].length() >10 || !cells[8].matches("[0-9]+")){
					error.append("第");
					error.append((r+2));
					error.append("行、第9列数据格式有误！");
					success=false;
					break;
				} 
			}
			//*******************判断数据格式end****************
		}
      	 act.setSuccess(success);
      	 act.setMsg(error.toString());
      	 return act;
	}
	
	
	/**
	 * 上传excel文件
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String uploadFileExcel(MultipartFile file,HttpServletRequest request) throws Exception {
        	String realPath = Constant.EXCEL_UPLOAD_URL;
	        String originalFilename = null;
	        if(file.isEmpty()){
	        	return null;
	        }else {
	            	originalFilename = file.getOriginalFilename();
	            	String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
	            	//文件路径
	            	String filePath =realPath+"\\allsend_"+System.currentTimeMillis()+fileType;
	            	/**
	            	 * 文件夹不存在，则建立文件夹
	            	 * */
	            	File f = new File(realPath);
	            	if(!f.exists()){
	            		f.mkdirs();//mkdir
	            	}
	            	File excelFile = new File(filePath);
	            	//此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
	            	//接收到的文件转移到给定的目标文件
	            	file.transferTo(excelFile);
	            	return filePath;
			}
	}
	/**
	 * 返回excel中的可用数据
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	public ActResult<List<EnterpriseUser>> availableData(String url,Integer maxWelfareLevel,Long enterpriseId) throws IOException{
		// 获取Excel文件对象 
		Workbook wb = openWorkbook(url);
		// 获取文件的指定工作表m 默认的第一个
		Sheet sheet = wb.getSheetAt(0);
		ActResult<List<EnterpriseUser>> act = new ActResult<List<EnterpriseUser>>();
		List<EnterpriseUser> listEnt = new ArrayList<EnterpriseUser>();
		//员工编号 用于
		//*************************************************
		List<String> listEmpNumber = new ArrayList<String>();
		List<String> listEmpPhone = new ArrayList<String>();
		List<String> listEmpEmail = new ArrayList<String>();
		//*************************************************
		Row row = null;
		Cell cell = null;
		// 这里获得的是总行数
		int totalRows = sheet.getLastRowNum(); 
		//第一行中的数据
		Row firstRow = sheet.getRow(0);
		if(totalRows<0||firstRow==null){
			//内容为空
			act.setSuccess(false);
			act.setMsg("excel表中数据为空");
			return act;
		}
		/**
		 * 判断excel表头是否有误
		 * ******************************************************************************************
		 * 得出第一行的总列数
		 * */
		int firstRowTotalCells = firstRow.getLastCellNum();
		Integer empNumberCell=null;//员工编号
		Integer empNameCell=null;//员工名称
		Integer empPhoneCell=null;//员工手机
		Integer empSexCell=null;//员工性别
		Integer empWelfareLevelCell =null;//员工福利级别
		Integer empAge = null;//年龄
		Integer empSeniority = null;//工龄
		Integer empDuty = null;//职务
		Integer empSectionName = null;//部门
		Integer empEmailCell = null;//邮箱
		for(int i = 0; i < firstRowTotalCells; i++){
			//列
			cell = firstRow.getCell(i);
			String str;
			if(null == cell){
				str=null;
			}else{
				str = cell.getStringCellValue();
			}
			str=str.toLowerCase();
			if(null== str){
				
			}else if(str.contains("编号")){
				empNumberCell = i+1;
			}else if(str.contains("姓名")){
				empNameCell = i+1;
			}else if(str.contains("手机号码")){
				empPhoneCell = i+1;
			}else if(str.contains("电话")){
				if(empPhoneCell==null)
					empPhoneCell = i+1;
			
			}else if(str.contains("性别")){
				empSexCell = i+1;
			}else if(str.contains("福利级别")){
				empWelfareLevelCell = i+1;
			}else if(str.contains("职务")){
				empDuty = i+1;
			}else if(str.contains("部门")){
				empSectionName = i+1;
			}else if(str.contains("年龄")){
				empAge = i+1;
			}else if(str.contains("工龄")){
				empSeniority = i+1;
			}else if(str.contains("邮箱") || str.contains("email")){
					empEmailCell = i+1;
			}
		}
		
		if(empNumberCell==null&&empNameCell==null &&empSexCell==null&&empWelfareLevelCell==null){
			act.setSuccess(false);
			act.setMsg("上传表格有误");
			return act;
		}else if(empPhoneCell==null && empEmailCell==null){
			act.setSuccess(false);
			act.setMsg("上传表格有误");
			return act;
		}
		/**
		 * *****************************************************************************************
		 * */
		
		/**
		 * 起步在第二行（排除第一样字段名称）
		 * */
		//总行数 totalRows
		act.setSuccess(false);
		for (int r = 1; r <= totalRows; r++) {
			row = sheet.getRow(r);
			//本行为空，跳出本次循环
			if(row == null ) {
				
				break;
			}
			EnterpriseUser eu = new EnterpriseUser();
			// 员工编号
			if(empNumberCell !=null) {
				Object val = getValue(row.getCell(empNumberCell-1),null);
				if(StringUtils.isEmpty(val)) {
					act.setMsg("第"+(r+1)+"行,第"+empNumberCell+"列,员工编号为空");
					return act;
				} else {
					String empNumber = "";
					if(val instanceof String) {
						empNumber=(String)val;
						empNumber=empNumber.trim();
					} else {
						empNumber = NumberUtil.toLong(val) + "";
					}
					if(listEmpNumber.contains(empNumber)){
						act.setMsg("第"+(r+1)+"行和第"+(listEmpNumber.indexOf(empNumber)+2)+"行,员工编号重复");
						return act;
					} else {
						//将编号放入集合中，防止编号重复
						listEmpNumber.add(empNumber);
						eu.setEmpNumber(empNumber);
					}
				}
			}

			//手机号
			if(empPhoneCell !=null) {
				Object val = getValue(row.getCell(empPhoneCell-1),null);

				if(!StringUtils.isEmpty(val)) {
					String phone="";
					if(val instanceof String) {
						phone=(String)val;
						phone=phone.trim();
					} else {
						phone = NumberUtil.toLong(val) + "";
					}
					if(!StringUtils.isPhoneNumber(phone)){
						act.setMsg("第"+(r+1)+"行,第"+empPhoneCell+"列手机号格式错误:"+phone);
						return act;
					} else if(listEmpPhone.contains(phone)){
						act.setMsg("第"+(r+1)+"行,员工手机号跟前面重复");
						return act;
					} 
					//将手机号放入集合中，防止手机号重复 
					listEmpPhone.add(phone);
					
					//企业用户是否存在
					EnterpriseUserVo entVo = this.enterpriseUserDao.selectByAccount(phone);
					if(StringUtils.isEmpty(entVo)){
						eu.setPhone(phone);
					}else{
						//0 是未注销，1是已注销
						if(Byte.valueOf("1").equals(entVo.getLogout())){
							/**
							 * 手机号存在&&用户已注销&&用户企业id和本企业id不同
							 * 满足上述条件可以注册
							 * */								
							eu.setPhone(phone);
						//未注销
						}else{
							 if(enterpriseId.equals(entVo.getEnterpriseId())){
								eu.setPhone(phone);
							}else{
								act.setMsg("第"+(r+1)+"行,第"+empPhoneCell+"列手机号已在别的企业注册");
								return act;
							}
						}
					}
				} 
			}

			//名称
			if(empNameCell !=null) {
				String name = getValue(row.getCell(empNameCell-1),"").toString();
				if(StringUtils.isEmpty(name)) {
					act.setMsg("第"+(r+1)+"行,第"+empNameCell+"列,员工名称为空");
					return act;
				} else {
					name=name.trim();
					eu.setName(name);
				}
			}

			//性别
			if(empSexCell !=null) {
				String tmp = getValue(row.getCell(empSexCell-1),"").toString();
				if(StringUtils.isEmpty(tmp)){
					act.setMsg("第"+(r+1)+"行,第"+empSexCell+"列,员工性别为空");
					return act;
				}else {
					tmp=tmp.trim();
					if(tmp.equals("男")||tmp.equals("女")){
						eu.setSex(tmp);
					}else{
						act.setMsg("第"+r+"行,第"+empSexCell+"列性别错误");
						return act;
					}
				}
			}

			//福利级别
			if(empWelfareLevelCell !=null) {
				String tmp = getValue(row.getCell(empWelfareLevelCell-1),1).toString();
				if(StringUtils.isEmpty(tmp)){
					act.setMsg("第"+(r+1)+"行,第"+empWelfareLevelCell+"列,员工福利级别为空");
					return act;
				} else {
					tmp=tmp.trim();
					if(!StringUtils.isNumeric(tmp)) {
						act.setMsg("第"+(r+1)+"行,第"+empWelfareLevelCell+"列福利级别格式错误");
						return act;
					} else {
						Integer level = NumberUtil.toInteger(tmp);
						if(maxWelfareLevel>=level){
							eu.setWelfareLevel(level);
						}else{
							act.setMsg("第"+(r+1)+"行,第"+empWelfareLevelCell+"列福利级别错误");
							return act;
						}
					}
				}
			}

			// 年龄
			if(empAge !=null) {
				String tmp = getValue(row.getCell(empAge-1),"").toString();
				if(!StringUtils.isEmpty(tmp)){
					tmp=tmp.trim();
					if(!StringUtils.isNumeric(tmp)) {
						act.setMsg("第"+(r+1)+"行,第"+empAge+"列年龄错误");
						return act;
					} else {
						eu.setAge(NumberUtil.toInteger(tmp));
					} 
				}
			}

			// 工龄
			if(empSeniority !=null) {
				String tmp = getValue(row.getCell(empSeniority-1),"").toString();
				if(!StringUtils.isEmpty(tmp)){
					tmp=tmp.trim();
					if(!StringUtils.isNumeric(tmp)) {
						act.setMsg("第"+(r+1)+"行,第"+empSeniority+"列工龄错误");
						return act;
					} else {
						eu.setSeniority(NumberUtil.toInteger(tmp));
					} 
				}
			}

			// 部门
			if(empSectionName !=null) {
				String tmp = getValue(row.getCell(empSectionName-1),"").toString();
				if(!StringUtils.isEmpty(tmp)){
					tmp=tmp.trim();
					eu.setSectionName(tmp);
				}
			}
			
			// 职务
			if(empDuty !=null) {
				String tmp = getValue(row.getCell(empDuty-1),"").toString();
				if(!StringUtils.isEmpty(tmp)){
					tmp=tmp.trim();
					eu.setDuty(tmp);
				}
			}

			// 邮箱
			if(empEmailCell !=null) {
				String tmp = getValue(row.getCell(empEmailCell-1),"").toString();
				if(!StringUtils.isEmpty(tmp)){
					tmp=tmp.trim();
					if(listEmpEmail.contains(tmp)){
						act.setMsg("第"+(r+1)+"行,员工邮箱跟前面重复");
						return act;
					}else if(!StringUtils.isEmail(tmp)){
						act.setMsg("第"+(r+1)+"行,第"+empEmailCell+"列邮箱格式错误");
						return act;
					}
					//将手机号放入集合中，防止手机号重复 
					listEmpEmail.add(tmp);
					
					//企业用户是否存在
					EnterpriseUserVo entVo = this.enterpriseUserDao.selectByAccount(tmp);
					if(StringUtils.isEmpty(entVo)){
						eu.setEmail(tmp);
					}else{
						//0 是未注销，1是已注销
						if(Byte.valueOf("1").equals(entVo.getLogout())){
							/**
							 * 手机号存在&&用户已注销&&用户企业id和本企业id不同
							 * 满足上述条件可以注册
							 * */								
							eu.setEmail(tmp);
						//未注销
						}else{
							 if(enterpriseId.equals(entVo.getEnterpriseId())){
								eu.setEmail(tmp);
							}else{
								act.setMsg("第"+(r+1)+"行,第"+empEmailCell+"列邮箱已在别的企业注册");
								return act;
							}
						}
					}
				}
			}
			
			if(StringUtils.isEmpty(eu.getEmail()) && StringUtils.isEmpty(eu.getPhone())) {
				act.setMsg("第"+(r+1)+"行,员工的手机和邮箱都为空");
				return act;
			}
			listEnt.add(eu);
		}

		for (EnterpriseUser eu : listEnt) {
			//查询该企业是否有重复的员工序号 有 返回true
			EnterpriseUser ent = this.enterpriseUserService.selectByempNumber(eu.getEmpNumber(), enterpriseId);
			//logout == 0未注销  1已注销
			if(!StringUtils.isEmpty(ent)&&Byte.valueOf("0").equals(ent.getLogout())){
				//if(!ent.getPhone().equals(eu.getPhone())) {
					act.setMsg("员工编号："+eu.getEmpNumber() + " ,已被其他员工("+ent.getName()+")使用.");
					return act;
				//}
			}
			
		}
		
		act.setData(listEnt);
		act.setSuccess(true);
		act.setMsg("数据处理成功");
		return act;
	}
	/**
	 * 删除对应url的文件
	 * @param url
	 */
	public static void deleteUploadFileExcel(String url){
		File excelFile = new File(url);
		excelFile.delete();
	}
	
	
	public static List<String[]> fileUpload(MultipartFile file,
			HttpServletRequest request) throws Exception {
		 	//可以在上传文件的同时接收其它参数
	        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
	        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        	String realPath = Constant.EXCEL_UPLOAD_PATH;
	        //上传文件的原名(即上传前的文件名字)
	        String originalFilename = null;
	        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
	        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
	        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
	        if(!file.isEmpty()){
                originalFilename = file.getOriginalFilename();
                	String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                	//文件路径
                    String filePath =realPath+"\\allsend_"+System.currentTimeMillis()+fileType;
                    
					File f = new File(realPath);
					if(!f.exists()){
						f.mkdir();
					}
					File excelFile = new File(filePath);
					//这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
//	                                                  此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
//	                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originalFilename));
//	                FileUtils.copyInputStreamToFile(myfile.getInputStream(), excelFile );
                	file.transferTo(excelFile );
        			List<String[]> list = ExcelUtil.getExcelData(filePath);
        			excelFile.delete();//处理结束删除文件
	                return list;
	            }
		return null;
	}

		public static int writeExcelData(List<EnterpriseUser> list , HttpServletRequest request) {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/template")+"/batchGiveBenefit.xlsx";
				InputStream in = new FileInputStream(realPath); // 创建输入流
				Workbook wb = openWorkbook(in, realPath);// 获取Excel文件对象
				Sheet sheet = wb.getSheetAt(0);// 获取文件的指定工作表m 默认的第一个
				int totalRows =sheet.getLastRowNum();
				//清空原有数据
				for (int r = 1; r <= totalRows ; r++) {
					sheet.removeRow(sheet.getRow(r));
				}
				for (int i = 0; i < list.size(); i++) {
					EnterpriseUser user = list.get(i);
					Row row = sheet.createRow(i+1);//排除 第一行 
					row.createCell(0).setCellValue(user.getName() ==null ?"":user.getName());
					row.createCell(1).setCellValue(user.getPhone()==null ?"":user.getPhone());
					row.createCell(2).setCellValue(user.getSex()==null ?"":user.getSex());
					row.createCell(3).setCellValue(user.getAge()==null ?0:user.getAge());
					row.createCell(4).setCellValue(user.getWelfareLevel()==null ?0:user.getWelfareLevel());
					row.createCell(5).setCellValue(user.getDuty()==null ?"":user.getDuty());
					row.createCell(6).setCellValue(user.getSeniority()==null ?0:user.getSeniority());
					row.createCell(9).setCellValue("过节费");
				}
				FileOutputStream out=new FileOutputStream(realPath);
				out.flush();
				wb.write(out);
				out.close();
				in.close();
				return 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		}
		//__________________________________________________________________________________________________________________________
		
		/**
		 * 校验批量上传商品数据
		 * @throws IOException 
		 */
//		public ActResult<List<BatchAddProductVo>> checkBatchAddProductExcel(String url,Long supplierId,Sheet sheet) throws IOException{
//			List<BatchAddProductVo> product = new ArrayList<BatchAddProductVo>();
//			//解压后文件的地址
//			String folderPath  = url;
////					Constant.PRODUCT_ZIP_OUTPUT+url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")+1);
//			Row row = null;
//			// 这里获得的是总行数
//			int totalRows = sheet.getLastRowNum();
//			//第二行中的数据
//			Row firstRow = sheet.getRow(1);
//			if(totalRows<0||firstRow==null){
//				//内容为空
//				return ActResult.fail("excel表中数据为空");
//			}
//			//第二行总列数
//			int firstRowTotalCells = firstRow.getLastCellNum();
//			if(firstRowTotalCells!=26){
//				return ActResult.fail("请使用本公司提供的excel模板");
//			}
//			//从第三行开始取数据
//			for(int line =2;line<totalRows+1;line++){
//				row = sheet.getRow(line);//行
//				//判断改行是不是为空
//				if(this.isCellNull(row)){
//					continue;
//				}
//				//判断商品编号在集合中是否存在。默认不存在
//				boolean flag = false;
//				//商品规格的处理结果
//				StringBuffer processingResult = new StringBuffer();
//				//商品编号-------------------------
//				Cell productNumber = row.getCell(0);
//				for(int j =0;j<product.size();j++){
//					BatchAddProductVo productVo = product.get(j);
//					
//					if(!StringUtils.isEmpty(productNumber)){
//						String proNumber=null;
//						if(productNumber.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//							DecimalFormat df = new DecimalFormat("#");
//							proNumber = df.format(returnParam(productNumber));
//						}else if(productNumber.getCellType()==HSSFCell.CELL_TYPE_STRING){
//							proNumber = String.valueOf(returnParam(productNumber));
//						}
//						//line行的 商品编号和product集合中商品的编号相等
//						if(productVo.getProductNumber().equals(proNumber)){
//							//校验除了sku信息
//							this.checkSkuOther(row, processingResult, productVo, true,supplierId,folderPath);
////							List<BatchAddProductSku> skus = productVo.getSku();
//							BatchAddProductSku sku = this.checkSku(row, processingResult, supplierId,productVo,folderPath+"/"+productVo.getProductNumber()+"/main/",line-1);
//							List<BatchAddProductSku> skus = productVo.getSku();
//							skus.add(sku);
//							productVo.setSku(skus);
//							flag = true;
//							break;
//						}
//					}
//				}
//				//商品编号不存在,需要保存
//				if(!flag){
//					BatchAddProductVo productVo = new BatchAddProductVo();
//					this.checkSkuOther(row, processingResult, productVo, false,supplierId,folderPath);
//					List<BatchAddProductSku> skus = new ArrayList<BatchAddProductSku>();
//					BatchAddProductSku sku = this.checkSku(row, processingResult, supplierId,productVo,folderPath+"/"+productVo.getProductNumber()+"/main/",line-1);
//					skus.add(sku);
//					productVo.setSku(skus);
//					product.add(productVo);
//				}
//			}
//			
//			return ActResult.success(product);
//		}
		
		/**
		 * 校验sku以外 的信息
		 * @param row
		 * @param processingResult 返回报告
		 * @param productVo 空对象
		 * @param proNumberIsAvailable 商品编号是否存在。List<BatchAddProductVo> product = new ArrayList<BatchAddProductVo>();
		 *  						         商品编号在product集合中存在不存在。如果存在 值为true,不存在为false
		 * @param folderPath 文件夹地址
		 */
//		public void checkSkuOther(Row row,StringBuffer processingResult,BatchAddProductVo productVo,Boolean proNumberIsAvailable,Long supplierId,String folderPath){
//			//基本内容------------------------
//			Cell productNumber = row.getCell(0);//第一列 商品编号
//			Cell categoryName = row.getCell(1);//第二列 分类
//			Cell shopName = row.getCell(2);//第三列店铺名称
//			Cell fullName = row.getCell(3);//第四列 标题
//			Cell name = row.getCell(4);//第五列 副标题
//			Cell promotion = row.getCell(5);//第六列 广告词
//			Cell brand = row.getCell(6);//第七列 商品品牌
//			Cell marque = row.getCell(7);//第八列 商品型号
//			Cell barcode = row.getCell(8);//第九列 条形码
//			Cell roughWeight = row.getCell(9);//第十列 商品毛重
//			Cell netWeight = row.getCell(10);//第十一列 单品净重
//			Cell length = row.getCell(11);//第十二列 单品长
//			Cell width = row.getCell(12);//第十三列 单品宽
//			Cell height = row.getCell(13);//第十四列 单品高
////			Cell bulk = row.getCell(14);//第十五列 单品体积
//			//------------------------------
//			//运费----------------------------
//			Cell shipAddress = row.getCell(14);//第十六列 发货地
//			Cell freight = row.getCell(15);//第十七列 运费
//			//------------------------------
//			//售后服务------------------------
//			Cell afterService = row.getCell(16);//第十八列 售后服务
//			//其它服务-------------------------
//			Cell otherService = row.getCell(17);//第十九列 其它服务
//			//------------------------------
//			if(StringUtils.isEmpty(productNumber)){
//				processingResult.append("商品编号不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				//商品编号
//				if(!proNumberIsAvailable){
//					if(productNumber.getCellType()==HSSFCell.CELL_TYPE_STRING){
//						productVo.setProductNumber(String.valueOf(returnParam(productNumber)));
//					}else if(productNumber.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//						productVo.setProductNumber(zipDf.format(returnParam(productNumber)));
//					}
//				}
//			}
//			
//			//商品的详情图片
//			if(!proNumberIsAvailable){
//				List<String> appImagesPath = this.zipCompress.getFolderFileImagePath(folderPath+"/"+productVo.getProductNumber()+"/detail/app",false);
//				List<String> pcImagesPath = this.zipCompress.getFolderFileImagePath(folderPath+"/"+productVo.getProductNumber()+"/detail/pc",false);
//				if(!appImagesPath.isEmpty()&&!pcImagesPath.isEmpty()){//app和pc图片都不为空
//					productVo.setPcImages(pcImagesPath);
//					productVo.setAppImages(appImagesPath);
//				}else if(appImagesPath.isEmpty()&&!pcImagesPath.isEmpty()){//app图片为空,pc图片不为空
//					productVo.setPcImages(pcImagesPath);
//					productVo.setAppImages(pcImagesPath);
//				}else if(!appImagesPath.isEmpty()&&pcImagesPath.isEmpty()){//app图片不为空,pc图片为空
//					productVo.setPcImages(appImagesPath);
//					productVo.setAppImages(appImagesPath);
//				}
//			}
//			
//			//店铺名称 
//			if(StringUtils.isEmpty(shopName)){
//				processingResult.append("店铺名称不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				String shopname = String.valueOf(returnParam(shopName));
//				if(shopName.getCellType()==HSSFCell.CELL_TYPE_STRING||shopName.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if(proNumberIsAvailable){//
//						if(!(StringUtils.isEmpty(productVo.getShopname())?"":productVo.getShopname()).equals(shopname)){
//							processingResult.append("店铺名称不相同,");
//							productVo.setIsCouldAdd(false);			
//						}
//					}else{
//						Shop shopModel = new Shop();
//						shopModel.setSupplierId(supplierId);
//						shopModel.setShopname(shopname);
//						List<Shop> shops = this.shopService.selectByModel(shopModel);
//						if(StringUtils.isEmpty(shops)||shops.isEmpty()){
//							processingResult.append("店铺名称没有注册,");
//							productVo.setIsCouldAdd(false);			
//						}else{
//							Shop shop = shops.get(0);
//							if(supplierId.equals(shop.getSupplierId())){
//								productVo.setShopname(shopname);
//								productVo.setShopId(shop.getId());//店铺id
//							}else{
//								processingResult.append("请使用本店铺名称,");
//								productVo.setIsCouldAdd(false);			
//							}
//						}
//					}
//				}else{
//					processingResult.append("店铺名称参数类型错误,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//商品分类
//			if(StringUtils.isEmpty(categoryName)){
//				processingResult.append("商品分类不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(categoryName.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					//商品分类不相等，且商品编号存在
//					if(!(StringUtils.isEmpty(productVo.getCategoryName())?"":productVo.getCategoryName()).equals(String.valueOf(returnParam(categoryName)))&&proNumberIsAvailable){
//						processingResult.append("商品分类不相同,");
//						productVo.setIsCouldAdd(false);								
//					}else if(!proNumberIsAvailable){
//						//校验商品分类是不是该商铺的
//						if(!StringUtils.isEmpty(productVo.getShopId())){
//							String cateName = String.valueOf(returnParam(categoryName));
//							
//							//是否是本店铺的商品分类
//							if(isShopCategory(productVo.getShopId(),cateName)){
//								productVo.setCategoryName(cateName);
//							}else{
//								processingResult.append("该店铺没有此商品分类,");
//								productVo.setIsCouldAdd(false);	
//							}
//						}
//					}
//				}else{
//					processingResult.append("商品分类内容应该为字符串,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//商品标题
//			if(StringUtils.isEmpty(fullName)){
//				processingResult.append("商品标题不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(fullName.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					//商品分类不相等，且商品编号存在
//					if(!(StringUtils.isEmpty(productVo.getFullName())?"":productVo.getFullName()).equals(String.valueOf(returnParam(fullName)))&&proNumberIsAvailable){
//						processingResult.append("商品标题不相同,");
//						productVo.setIsCouldAdd(false);								
//					}else if(!proNumberIsAvailable){
//						productVo.setFullName(String.valueOf(returnParam(fullName)));
//					}
//				}else{
//					processingResult.append("商品标题应该为字符串,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//校验副标题
//			if(StringUtils.isEmpty(name)){
//				processingResult.append("商品副标题不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(name.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					if(!(StringUtils.isEmpty(productVo.getName())?"":productVo.getName()).equals(String.valueOf(returnParam(name)))&&proNumberIsAvailable){//有标题 
//						processingResult.append("商品副标题不相同,");
//						productVo.setIsCouldAdd(false);								
//					}else if(!proNumberIsAvailable){//没有标题
//						productVo.setName(String.valueOf(returnParam(name)));
//					}
//				}else{
//					processingResult.append("商品副标题内容应该为字符串,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			//广告词
//			if(!StringUtils.isEmpty(promotion)){
//				if(promotion.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					if(!(StringUtils.isEmpty(productVo.getPromotion())?"":productVo.getPromotion()).equals(String.valueOf(returnParam(promotion)))&&proNumberIsAvailable){//有标广告词
//						processingResult.append("商品广告词不相同,");
//						productVo.setIsCouldAdd(false);								
//					}else if(!proNumberIsAvailable){//没有标题
//						productVo.setPromotion(String.valueOf(returnParam(promotion)));
//					}
//				}else{
//					processingResult.append("商品广告词内容应该为汉字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			/**
//			 * 
//			 * 根据商品名称和商家id查询商品品牌
//			 * */
//			if(StringUtils.isEmpty(brand)){
//				processingResult.append("商品品牌不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(brand.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					Map<String,Object> map = new HashMap<String, Object>();
//					map.put("name", String.valueOf(returnParam(brand)));
//					map.put("supplierId", supplierId);
//					List<ProductBrand> productBrands = this.productBrandService.findAllBymap(map);
//					if(productBrands.isEmpty()){
//						if(proNumberIsAvailable){
//							productVo.setIsCouldAdd(false);								
//							processingResult.append("商品品牌不存在,");
//						}
//					}else{
//						Boolean brandIsAvailable = false;
//						for(ProductBrand productBrand:productBrands){
//							if(productBrand.getName().equals(String.valueOf(returnParam(brand)))&&productBrand.getSupplierId().equals(supplierId)){
//								brandIsAvailable=true;
//								if(!proNumberIsAvailable){
//									productVo.setBrandName(String.valueOf(returnParam(brand)));
//								}
//							}
//						}
//						//商品品牌不存在
//						if(!brandIsAvailable){
//							if(proNumberIsAvailable){
//								processingResult.append("商品品牌不存在,");
//								productVo.setIsCouldAdd(false);								
//							}
//						}
//					}
//				}else{
//					processingResult.append("商品品牌内容应该为字符串,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//商品型号
//			if(StringUtils.isEmpty(marque)){
//				processingResult.append("商品型号不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(marque.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					if(!(StringUtils.isEmpty(productVo.getMarque())?"":productVo.getMarque()).equals(String.valueOf(returnParam(marque)))&&proNumberIsAvailable){
//						processingResult.append("商品型号不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setMarque(String.valueOf(returnParam(marque)));
//					}
//				}else if(marque.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if(!(StringUtils.isEmpty(productVo.getMarque())?"":productVo.getMarque()).equals(zipDf.format(returnParam(marque)))&&proNumberIsAvailable){
//						processingResult.append("商品型号不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setMarque(zipDf.format(returnParam(marque)));
//					}
//				}else{
//					processingResult.append("商品型号类型错误,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//条形码
//			if(StringUtils.isEmpty(barcode)){
//				processingResult.append("条形码不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(barcode.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					if(!(StringUtils.isEmpty(productVo.getBarcode())?"":productVo.getBarcode()).equals(String.valueOf(returnParam(barcode)))&&proNumberIsAvailable){
//						processingResult.append("条形码不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setBarcode(String.valueOf(returnParam(barcode)));
//					}
//				}else if(barcode.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if(!(StringUtils.isEmpty(productVo.getBarcode())?"":productVo.getBarcode()).equals(zipDf.format(returnParam(barcode)))&&proNumberIsAvailable){
//						processingResult.append("条形码不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setBarcode(zipDf.format(returnParam(barcode)));
//					}
//				}else{
//					processingResult.append("条形码应该为字符串,");
//					productVo.setIsCouldAdd(false);
//				}
//				
//			}
//			
//			//单品毛重
//			if(StringUtils.isEmpty(roughWeight)){
//				processingResult.append("单品毛重不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(roughWeight.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if((StringUtils.isEmpty(productVo.getRoughWeight())?new BigDecimal(0):productVo.getRoughWeight()).compareTo(new BigDecimal(returnParam(roughWeight)+""))!=0&&proNumberIsAvailable){
//						processingResult.append("单品毛重不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setRoughWeight(new BigDecimal(returnParam(roughWeight)+""));
//					}
//				}else{
//					processingResult.append("单品毛重应该为数字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			//单品净重
//			if(StringUtils.isEmpty(netWeight)){
//				processingResult.append("单品净重不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(netWeight.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if((StringUtils.isEmpty(productVo.getNetWeight())?new BigDecimal(0):productVo.getNetWeight()).compareTo(new BigDecimal(returnParam(netWeight)+""))!=0&&proNumberIsAvailable){
//						processingResult.append("单品净重不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setNetWeight(new BigDecimal(returnParam(netWeight)+""));
//					}
//				}else{
//					processingResult.append("单品净重应该为数字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//单品长
//			if(StringUtils.isEmpty(length)){
//				processingResult.append("单品长不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(length.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if((StringUtils.isEmpty(productVo.getLength())?new BigDecimal(0):productVo.getLength()).compareTo(new BigDecimal(returnParam(length)+""))!=0&&proNumberIsAvailable){
//						processingResult.append("单品长不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setLength(new BigDecimal(returnParam(length)+""));
//					}
//				}else{
//					productVo.setLength(BigDecimal.ZERO);
//					processingResult.append("单品长应该为数字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			//单品宽
//			if(StringUtils.isEmpty(width)){
//				processingResult.append("单品宽不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(width.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if((StringUtils.isEmpty(productVo.getWidth())?new BigDecimal(0):productVo.getWidth()).compareTo(new BigDecimal(returnParam(width)+""))!=0&&proNumberIsAvailable){
//						processingResult.append("单品宽不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setWidth(new BigDecimal(returnParam(width)+""));
//					}
//				}else{
//					productVo.setWidth(BigDecimal.ZERO);
//					processingResult.append("单品宽应该为数字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			//单品高
//			if(StringUtils.isEmpty(height)){
//				processingResult.append("单品高不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(height.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					if((StringUtils.isEmpty(productVo.getHeight())?new BigDecimal(0):productVo.getHeight()).compareTo(new BigDecimal(returnParam(height)+""))!=0&&proNumberIsAvailable){
//						processingResult.append("单品高不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setHeight(new BigDecimal(returnParam(height)+""));
//					}
//				}else{
//					productVo.setHeight(BigDecimal.ZERO);
//					processingResult.append("单品高应该为数字,");
//					productVo.setIsCouldAdd(false);
//				}
//				
//			}
//
//			//单品体积    商品编号第一次出现。计算商品体积
//			if(!proNumberIsAvailable){
//				productVo.setBulk(productVo.getLength().multiply(productVo.getWidth()).multiply(productVo.getHeight()));
//			}
//			/**
//			 * 发货地址 校验
//			 * */  
//			if(StringUtils.isEmpty(shipAddress)){
//				processingResult.append("发货地址不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(shipAddress.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					String address = String.valueOf(returnParam(shipAddress));
////					if(isMunicipality(address)&&!proNumberIsAvailable){
////						productVo.setSendAddress(address+"直辖市");
////					}else{}
//
//					List<Area> areas = this.getArea(address, processingResult,true);
//					if(areas==null){
//						processingResult.append("发货地址错误,");
//						productVo.setIsCouldAdd(false);
//					}else{
//						String sendProvince = null;
//						String sendTown = null;
//						boolean isAddressError = false;
//						for(Area area:areas){
//							if(proNumberIsAvailable){
//								//发货地址省市和productVo中不符合
//								if(!Long.valueOf(area.getCode()).equals(productVo.getSendProvince())&&area.getGrade()==1){
//									if(!isAddressError){
//										processingResult.append("发货地址错误,");
//										productVo.setIsCouldAdd(false);
//										isAddressError = true;
//									}
//								}
//								if(!Long.valueOf(area.getCode()).equals(productVo.getSendTown())&&area.getGrade()==2){
//									if(!isAddressError){
//										processingResult.append("发货地址错误,");
//										productVo.setIsCouldAdd(false);
//										isAddressError = true;
//									}
//								}
//							}else{
//								if(area.getGrade()==1){
//									productVo.setSendProvince(Long.valueOf(area.getCode()));
//									sendProvince = area.getName();
//								}else{
//									productVo.setSendTown(Long.valueOf(area.getCode()));
//									sendTown = area.getName();
//								}
//							}
//						}
//						if(!proNumberIsAvailable){
//							if(sendProvince==null||sendTown==null){
//								processingResult.append("发货地址错误,");
//								productVo.setIsCouldAdd(false);
//							}else{
//								productVo.setSendAddress(sendProvince+""+sendTown);
//							}
//						}
//					}
//				
//				}else{
//					processingResult.append("发货地址应该为字符串,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			//运费
//			if(StringUtils.isEmpty(freight)){
//				processingResult.append("运费模板不能为空,");
//				productVo.setIsCouldAdd(false);
//			}else{
//				if(freight.getCellType()==HSSFCell.CELL_TYPE_STRING){//运费模板
//					/**
//					 * 校验运费模板是否符合
//					 * */
//					ShippingTemplate template = new ShippingTemplate();
//					template.setSupplierId(supplierId);
//					template.setName(String.valueOf(returnParam(freight)));
//					List<ShippingTemplate> shippingTemplates = this.shippingTemplateService.selectByModel(template);
//					if(shippingTemplates.isEmpty()){
//						if(proNumberIsAvailable){// 标题已存在
//							processingResult.append("不存在该运费模板,");
//							productVo.setIsCouldAdd(false);
//						}else{
//							productVo.setIsCouldAdd(false);
//						}
//					}else{
//						if(!proNumberIsAvailable){
//							productVo.setShippingTemplate(String.valueOf(returnParam(freight)));
//						}
//					}
//				}else if(freight.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){//数字，为运费
//					if((StringUtils.isEmpty(productVo.getCarriage())?new BigDecimal(0):productVo.getCarriage()).compareTo(new BigDecimal(returnParam(freight)+""))!=0&&proNumberIsAvailable){
//						processingResult.append("运费不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setCarriage(new BigDecimal(returnParam(freight)+""));
//					}
//				}else{
//					processingResult.append("运费填写错误,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			//售后服务
//			if(!StringUtils.isEmpty(afterService)){
//				if(afterService.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					if(!(StringUtils.isEmpty(productVo.getAfterService())?"":productVo.getAfterService()).equals(String.valueOf(returnParam(afterService)))&&proNumberIsAvailable){
//						processingResult.append("售后服务内容不相同,");
//						productVo.setIsCouldAdd(false);
//					}else if(!proNumberIsAvailable){
//						productVo.setAfterService(String.valueOf(returnParam(afterService)));
//					}
//				}else{
//					processingResult.append("售后服务应该为汉字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			//其他服务
//			if(StringUtils.isEmpty(otherService)){
//				productVo.setStockLockType(1);
//			}else{
//				if(otherService.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					String otherStr = String.valueOf(returnParam(otherService));
//					Integer i = "拍下减库存".equals(otherStr)?1:"付款减库存".equals(otherStr)?2:0;
//					if(i.equals(0)){
//						processingResult.append("其他服务填写错误,");
//						productVo.setIsCouldAdd(false);
//					}else{
//						if(!(StringUtils.isEmpty(productVo.getStockLockType())?Integer.valueOf(0):productVo.getStockLockType()).equals(i)&&proNumberIsAvailable){
//							processingResult.append("其他服务不相同,");
//							productVo.setIsCouldAdd(false);
//						}else if(!proNumberIsAvailable){
//							productVo.setStockLockType(i);
//						}
//					}
//				}else{
//					processingResult.append("其他服务为汉字,");
//					productVo.setIsCouldAdd(false);
//				}
//			}
//			
//			
//		}
		/**
		 * 校验sku基本信息（数据类型、是否空、数据库是否存在）
		 * @param row
		 * @param processingResult
		 * @param supplierId
		 * @param folderPath  文件夹/商品编号/main/
		 * @return
		 */
//		public BatchAddProductSku checkSku(Row row,StringBuffer processingResult,Long supplierId,BatchAddProductVo productVo,String folderPath,int line){
//			Cell productImageFolder = row.getCell(18);//第十九列 商品主图片文件夹
//			Cell sku1 = row.getCell(19);//第二十列 商品规格一
//			Cell sku2 = row.getCell(20);//第二十一列 商品规格二
//			Cell productCode = row.getCell(21);//第二十二列 条形码
//			Cell price = row.getCell(22);//第二十三列 售价
//			Cell stock = row.getCell(23);//第二十四列 库存
//			Cell warnnum = row.getCell(24);//第二十五列 库存预警值
//			Cell maxFucoin = row.getCell(25);//第二十六列 可使用内购券
//			Cell categoryName = row.getCell(1);//第二列 分类
//			BatchAddProductSku sku = new BatchAddProductSku();
//			sku.setLine(line);
//			List<String> skuImagesPath = null;
//			String folderName=null;
//			String sku1Value =null;
//			String sku2Value =null;
//			List<BatchAddProductSku> skus = StringUtils.isEmpty(productVo)?null:productVo.getSku();
//			/**
//			 * 商品图片文件夹的图片文件
//			 * */
//			if(StringUtils.isEmpty(productImageFolder)){
//				processingResult.append("商品图片文件夹不存在,");
//				sku.setIsCouldAdd(false);
//			}else{
//				if(productImageFolder.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					folderName = zipDf.format(returnParam(productImageFolder));
//					//得到mian文件夹下所有的图片path
//					skuImagesPath = this.zipCompress.getFolderFileImagePath(folderPath+folderName,true);
//					sku.setProductImageFolder(folderName);
//					sku.setFolderImagePath(skuImagesPath);
//				}else if(productImageFolder.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					folderName = String.valueOf(returnParam(productImageFolder));
//					skuImagesPath = this.zipCompress.getFolderFileImagePath(folderPath+folderName,true);
//					sku.setProductImageFolder(folderName);
//					sku.setFolderImagePath(skuImagesPath);
//				}else{
//					processingResult.append("商品图片文件夹名称错误,");
//					sku.setIsCouldAdd(false);
//				}
//			}
//			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			//获取店铺的分类规格
//			List<Specification> specifications = getShopCategorySpecification(supplierId, productVo.getShopId(), productVo.getCategoryName());
//			if(specifications == null){
//				//商品分类不存在，在checkSkuOther方法中已经校验
//			}else if(specifications.isEmpty()){
//				//该商品分类不存在规格信息
//				processingResult.append("该商品分类下没有规格");
//				sku.setIsCouldAdd(false);
//			}else{
//				//get sku1的值
//				if(!StringUtils.isEmpty(sku1)){
//					if(sku1.getCellType()==HSSFCell.CELL_TYPE_STRING){
//						sku1Value = String.valueOf(returnParam(sku1));
//						sku.setSku1(sku1Value);
//					}else if(sku1.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//						sku1Value =zipDf.format(returnParam(sku1));
//						sku.setSku1(sku1Value);
//					}else{
//						processingResult.append("商品规格一应该为字符串,");
//						sku.setIsCouldAdd(false);
//					}
//				}
//				//get sku2的值
//				if(!StringUtils.isEmpty(sku2)){
//					if(sku2.getCellType()==HSSFCell.CELL_TYPE_STRING){
//						sku2Value = String.valueOf(returnParam(sku2));
//						sku.setSku2(sku2Value);
//					}else if(sku2.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//						sku2Value =zipDf.format(returnParam(sku2));
//						sku.setSku2(sku2Value);
//					}else{
//						processingResult.append("商品规格二应该为字符串,");
//						sku.setIsCouldAdd(false);
//					}
//				}
//				/**
//				 * 判断商品分类下规格数量。如果为1 sku1不能为空，sku2可以为空。如果为2 sku1 和sku2都不能为空。
//				 * */
//				int speSize = specifications.size();
//				if(speSize==1){
//					if(sku1Value==null){
//						processingResult.append("规格一不能为空,");
//						sku.setIsCouldAdd(false);
//					}else if(sku2Value != null){
//						processingResult.append("您的分类中只能存在一种规格值,");
//						sku.setIsCouldAdd(false);
//					}else if(sku1Value!=null){
//						SpecificationValue speValue = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":String.valueOf(returnParam(categoryName)),StringUtils.isEmpty(sku1)?"":sku1Value, supplierId,1);
//						if(StringUtils.isEmpty(speValue)){
//							processingResult.append("商品规格一不存在,");
//							sku.setIsCouldAdd(false);
//						}
//					}
//				}else if(speSize == 2){
//					/**
//					 * sku1
//					 * */
//					if(sku1Value==null){
//						processingResult.append("规格一不能为空,");
//						sku.setIsCouldAdd(false);
//					}else if(sku2Value==null){
//						processingResult.append("规格二不能为空,");
//						sku.setIsCouldAdd(false);
//					}else{
//						SpecificationValue spe1Value = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":String.valueOf(returnParam(categoryName)),StringUtils.isEmpty(sku1)?"":sku1Value, supplierId,1);
//						SpecificationValue spe2Value = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":String.valueOf(returnParam(categoryName)),StringUtils.isEmpty(sku2)?"":sku2Value, supplierId,2);
//						if(StringUtils.isEmpty(spe1Value)){
//							processingResult.append("商品规格一不存在,");
//							sku.setIsCouldAdd(false);
//						}
//						if(StringUtils.isEmpty(spe2Value)){
//							processingResult.append("商品规格一不存在,");
//							sku.setIsCouldAdd(false);
//						}
//					}
//				}else{
//					processingResult.append("商品规格不能大于2个,");
//					sku.setIsCouldAdd(false);
//				}
//			}
//			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			
////			//sku1
////			if(StringUtils.isEmpty(sku1)){
////				processingResult.append("商品规格一不能为空,");
////				sku.setIsCouldAdd(false);
////			}else{
////				if(sku1.getCellType()==HSSFCell.CELL_TYPE_STRING){
////					sku1Value = String.valueOf(returnParam(sku1));
////					sku.setSku1(sku1Value);
////					/**
////					 * 查询规格一，有效
////					 * */
////					SpecificationValue speValue = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":String.valueOf(returnParam(categoryName)),StringUtils.isEmpty(sku1)?"":sku1Value, supplierId);
////					if(StringUtils.isEmpty(speValue)){
////						processingResult.append("商品规格一不存在,");
////						sku.setIsCouldAdd(false);
////					}
////				}else if(sku1.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
////					sku1Value =zipDf.format(returnParam(sku1));
////					sku.setSku1(sku1Value);
////					/**
////					 * 查询规格一，有效
////					 * */
////					SpecificationValue speValue = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":String.valueOf(returnParam(categoryName)),StringUtils.isEmpty(sku1)?"":sku1Value, supplierId);
////					if(StringUtils.isEmpty(speValue)){
////						processingResult.append("商品规格一不存在,");
////						sku.setIsCouldAdd(false);
////					}
////				}else{
////					processingResult.append("商品规格一应该为字符串,");
////					sku.setIsCouldAdd(false);
////				}
////			}
////			//sku2
////			if(skuTwo){
////				if(sku2IsCheckNull){
////					if(StringUtils.isEmpty(sku2)){
////						processingResult.append("商品规格二不能为空,");
////						sku.setIsCouldAdd(false);
////					}
////				}
////				if(!StringUtils.isEmpty(sku2)){
////					if(sku2.getCellType()==HSSFCell.CELL_TYPE_STRING){
////						sku2Value = String.valueOf(returnParam(sku2));
////						sku.setSku2(sku2Value);
////						/**
////						 * 查询规格二，有效
////						 * */
////						SpecificationValue speValue = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":(String.valueOf(returnParam(categoryName))),StringUtils.isEmpty(sku2)?"":sku2Value, supplierId);
////						if(StringUtils.isEmpty(speValue)){
////							processingResult.append("商品规格二不存在,");
////							sku.setIsCouldAdd(false);
////						}
////					}else if(sku2.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
////						sku2Value = zipDf.format(returnParam(sku2));
////						sku.setSku2(sku2Value);
////						/**
////						 * 查询规格二，有效
////						 * */
////						SpecificationValue speValue = getSpecificationValue(StringUtils.isEmpty(categoryName)?"":(String.valueOf(returnParam(categoryName))),StringUtils.isEmpty(sku2)?"":sku2Value, supplierId);
////						if(StringUtils.isEmpty(speValue)){
////							processingResult.append("商品规格二不存在,");
////							sku.setIsCouldAdd(false);
////						}
////					}else{
////						processingResult.append("商品规格二应该为字符串,");
////						sku.setIsCouldAdd(false);
////					}
////				}
////			}
//			
//			
//			/**
//			 * 条形码
//			 * 
//			 * 添加商品没有校验商品条形码唯一
//			 * */
//			//条形码为空。
//			if(StringUtils.isEmpty(productCode)){
//				processingResult.append("商品条形码不能为空,");
//				sku.setIsCouldAdd(false);
//			}else{
//				if(productCode.getCellType()==HSSFCell.CELL_TYPE_STRING){
//					
//					sku.setProductCode(String.valueOf(returnParam(productCode)));
//					
//				}else if(productCode.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					sku.setProductCode(zipDf.format(returnParam(productCode)));
//					
//				}else{
//					processingResult.append("商品条形码应该为汉字,");
//					sku.setIsCouldAdd(false);
//				}
//			}
//			
//			//售价
//			if(StringUtils.isEmpty(price)){
//				processingResult.append("商品售价不能为空,");
//				sku.setIsCouldAdd(false);
//			}else{
//				if(price.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					BigDecimal priceValue = new BigDecimal(returnParam(price)+"");
//					sku.setPrice(priceValue);
//					if(priceValue.compareTo(new BigDecimal(1))<0){
//						processingResult.append("商品售价不能为0或者小于0,");
//						sku.setIsCouldAdd(false);
//					}else{
//						//商品可用内购券
//						if(maxFucoin.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//							BigDecimal fucoin = new BigDecimal(returnParam(maxFucoin)+"");
//							sku.setMaxFucoin(fucoin);
//							//库存预警值为空。
//							if(StringUtils.isEmpty(fucoin)){
//								processingResult.append("商品可内购券不能为空,");
//								sku.setIsCouldAdd(false);
//							}else if(fucoin.compareTo(new BigDecimal(0))<0){
//								processingResult.append("商品可内购券不能为负数,");
//								sku.setIsCouldAdd(false);
//							}else{
//								if(fucoin.compareTo(new BigDecimal(returnParam(price)+""))==1){
//									processingResult.append("商品可用内购券不能大于商品的售价,");
//									sku.setIsCouldAdd(false);
//								}
//							}
//						}else{
//							processingResult.append("商品可内购券应该为数字,");
//							sku.setIsCouldAdd(false);
//						}
//					}
//				}else{
//					processingResult.append("商品可内购券应该为数字,");
//					sku.setIsCouldAdd(false);
//				}
//			}
//			//库存
//			if(StringUtils.isEmpty(stock)){
//				processingResult.append("商品库存不能为空,");
//				sku.setIsCouldAdd(false);
//			}else{
//				if(stock.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					Integer stockValue = new BigDecimal(returnParam(stock)+"").intValue();
//					sku.setStock(stockValue);
//					if(stockValue<1){
//						processingResult.append("商品库存不能为0或者小于0,");
//						sku.setIsCouldAdd(false);
//					}
//				}else{
//					processingResult.append("商品库存应该为数字,");
//					sku.setIsCouldAdd(false);
//				}
//			}
//			//库存预警值
//			if(StringUtils.isEmpty(warnnum)){
//				processingResult.append("商品库存预警值不能为空,");
//				sku.setIsCouldAdd(false);
//			}else{
//				if(warnnum.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//					Integer warnnumValue = new BigDecimal(returnParam(warnnum)+"").intValue();
//					sku.setWarnnum(warnnumValue);
//					if(warnnumValue<1){
//						processingResult.append("商品库存预警值不能为0或者小于0,");
//						sku.setIsCouldAdd(false);
//					}
//				}else{
//					processingResult.append("商品库存预警值应该为数字,");
//					sku.setIsCouldAdd(false);
//				}
//			}
//			if(StringUtils.isEmpty(skus)||skus.isEmpty()){
//				sku.setProcessingResult(processingResult.toString());
//				return sku;
//			/**
//			 * sku不为空
//			 * 
//			 * 商品图片文件夹名称和规格一名称是对应的。
//			 * 相同的图片文件夹不能有不同的规格一，反之不同的图片文件夹不能有相同的规格一
//			 * */
//			}else{
//				for(BatchAddProductSku productSku :skus){
//					//文件夹相同
//					if(folderName.equals(productSku.getProductImageFolder())){
//						if(sku1Value == null){
//							processingResult.append("规格一不能为空,");
//							sku.setIsCouldAdd(false);
//						}else if(sku1Value.equals(productSku.getSku1())){
//							if(sku2Value !=null){
//								if(sku2Value.equals(productSku.getSku2())){
//									processingResult.append("规格二值错误,");
//									sku.setIsCouldAdd(false);
//									break;
//								}
//							}
//						}else{
//							processingResult.append("规格一值错误,");
//							sku.setIsCouldAdd(false);
//							break;
//						}
//					//规格一相同
//					}else if(sku1Value.equals(productSku.getSku1())){
//						if(folderName.equals(productSku.getProductImageFolder())){
//							if(sku2Value!=null){
//								if(sku2Value.equals(productSku.getSku2())){
//									processingResult.append("规格二值不能重复,");
//									sku.setIsCouldAdd(false);
//									break;
//								}
//							}
//						}else{
//							processingResult.append("图片文件夹名称错误,");
//							sku.setIsCouldAdd(false);
//							break;
//						}
//					}
//				}
//			}
//			sku.setProcessingResult(processingResult.toString());
//			return sku;
//		}
		
		
		public Object returnParam(Cell cell){
			if(StringUtils.isEmpty(cell)){
				return null;
			}else{
				if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
					return cell.getNumericCellValue();
				}else if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
					return cell.getStringCellValue();
				}else if(cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
					return cell.getBooleanCellValue();
				}else if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
					return cell.getCellFormula();
				}else{
					return null;
				}
			}
		}
		
		/**得到运费中的发货地地址
		 * @param address
		 * @param processingResult
		 * @return
		 */
//		public List<Area> getArea(String address,StringBuffer processingResult,boolean isShipAddress){
//			if(StringUtils.isEmpty(address)){
//				return null;
//			}else{
//				List<Area> areaList = new ArrayList<Area>();
//				Integer index=0;
//				int provinceIndex = address.indexOf("省");
//				int cityIndex = address.indexOf("市");
//				int areaIndex = address.indexOf("区");
//				if(provinceIndex>0)
//					index=provinceIndex;
//				else if(cityIndex>0)
//					index=cityIndex;
//				else if(areaIndex>0)
//					index=areaIndex;
//					
//				if(index>0){
//					//得到省份
//					String province = address.substring(0, index+1);
//					Integer code = AreaUtils.getACode(province);//省份的code
//					if(StringUtils.isEmpty(code)){
////						processingResult.append("地址错误,");
//						return null;
//					}else{
//						areaList.add(setArea(province, code, 1));
//						List<Area> areas = AreaUtils.getLevel2(code);
//						//////////////////////////////////////////////////////////////////////////
//						boolean isMunicipality = false;
//						String[] municipality ={"北京市","重庆市","上海市","天津市"};
//						for(int i = 0;i<municipality.length;i++){
//							if(province.equals(municipality[i])){
//								isMunicipality = true;
//								break;
//							}
//						}
//						//////////////////////////////////////////////////////////////////////////
//						String city = null;
//						//是直辖市
//						if(isMunicipality){
//							city = "市辖区";
//						}else{
//							city = address.substring(index+1, address.length());
//						}
//						
//						//发货地址
//						if(isShipAddress){
//							Area area = getLevel2(areas, city);
//							if(area==null){
////								processingResult.append("地址错误,");
//								return null;
//							}else{
//								areaList.add(area);
//								return areaList;
//							}
//						//商品产地
//						}else{
//							for(Area area:areas){
//								//字符串包含二级城市
//								int level2Index = city.indexOf(area.getName());
//								if(level2Index>-1){
//									areaList.add(setArea(area.getName(), area.getCode(), 2));
//									List<Area> lastAreas = AreaUtils.getLevel3(area.getCode());
//									//最后一级城市
//									String lastCity = address.substring(province.length()+area.getName().length(), address.length());
//									for(Area lastArea:lastAreas){
//										if(lastArea.getName().equals(lastCity)){
//											areaList.add(setArea(lastArea.getName(), lastArea.getCode(), 3));
//										}
//									}
//								}
//							}
//							if(areaList.size()==3){
//								return areaList;
//							}else{
////								processingResult.append("地址错误,");
//								return null;
//							}
//						}
//					}
//				}else{
////					processingResult.append("地址错误,");
//					return null;
//				}
//				
//			}
//		}
		/**
		 * 得到二级城市
		 * @param areas
		 * @param city
		 * @return
		 */
//		private Area getLevel2(List<Area> areas,String city){
//			for(Area a:areas){//匹配二级城市名称
//				if(a.getName().equals(city)){
//					Area area = new Area();
//					area.setGrade(2);
//					area.setName(a.getName());
//					area.setCode(a.getCode());
//					return area;
//				}
//			}
//			return null;
//		}
		
//		private Area setArea(String name,Integer code,Integer grade){
//			Area area = new Area();
//			area.setGrade(grade);
//			area.setName(name);
//			area.setCode(code);
//			return area;
//		}
		
//		private boolean isCellNull(Row row){
//			Cell cell_0 = row.getCell(0);
//			Cell cell_1 = row.getCell(1);
//			Cell cell_2 = row.getCell(2);
//			Cell cell_3 = row.getCell(3);
//			Cell cell_4 = row.getCell(4);
//			Cell cell_5 = row.getCell(5);
//			Cell cell_6 = row.getCell(6);
//			Cell cell_7 = row.getCell(7);
//			Cell cell_8 = row.getCell(8);
//			Cell cell_9 = row.getCell(9);
//			Cell cell_10 = row.getCell(10);
//			Cell cell_11 = row.getCell(11);
//			Cell cell_12 = row.getCell(12);
//			Cell cell_13 = row.getCell(13);
//			Cell cell_14 = row.getCell(14);
//			Cell cell_15 = row.getCell(15);
//			Cell cell_16 = row.getCell(16);
//			Cell cell_17 = row.getCell(17);
//			Cell cell_18 = row.getCell(18);
//			Cell cell_19 = row.getCell(19);
//			Cell cell_20 = row.getCell(20);
//			Cell cell_21 = row.getCell(21);
//			Cell cell_22 = row.getCell(22);
//			Cell cell_23 = row.getCell(23);
//			Cell cell_24 = row.getCell(24);
//			Cell cell_25 = row.getCell(25);
//			if((cell_0==null?true:"".equals(String.valueOf(cell_0).trim()))
//					&&(cell_1==null?true:"".equals(String.valueOf(cell_1).trim()))
//					&&(cell_2==null?true:"".equals(String.valueOf(cell_2).trim()))
//					&&(cell_3==null?true:"".equals(String.valueOf(cell_3).trim()))
//					&&(cell_4==null?true:"".equals(String.valueOf(cell_4).trim()))
//					&&(cell_5==null?true:"".equals(String.valueOf(cell_5).trim()))
//					&&(cell_6==null?true:"".equals(String.valueOf(cell_6).trim()))
//					&&(cell_7==null?true:"".equals(String.valueOf(cell_7).trim()))
//					&&(cell_8==null?true:"".equals(String.valueOf(cell_8).trim()))
//					&&(cell_9==null?true:"".equals(String.valueOf(cell_9).trim()))
//					&&(cell_10==null?true:"".equals(String.valueOf(cell_10).trim()))
//					&&(cell_11==null?true:"".equals(String.valueOf(cell_11).trim()))
//					&&(cell_12==null?true:"".equals(String.valueOf(cell_12).trim()))
//					&&(cell_13==null?true:"".equals(String.valueOf(cell_13).trim()))
//					&&(cell_14==null?true:"".equals(String.valueOf(cell_14).trim()))
//					&&(cell_15==null?true:"".equals(String.valueOf(cell_15).trim()))
//					&&(cell_16==null?true:"".equals(String.valueOf(cell_16).trim()))
//					&&(cell_17==null?true:"".equals(String.valueOf(cell_17).trim()))
//					&&(cell_18==null?true:"".equals(String.valueOf(cell_18).trim()))
//					&&(cell_19==null?true:"".equals(String.valueOf(cell_19).trim()))
//					&&(cell_20==null?true:"".equals(String.valueOf(cell_20).trim()))
//					&&(cell_21==null?true:"".equals(String.valueOf(cell_21).trim()))
//					&&(cell_22==null?true:"".equals(String.valueOf(cell_22).trim()))
//					&&(cell_23==null?true:"".equals(String.valueOf(cell_23).trim()))
//					&&(cell_24==null?true:"".equals(String.valueOf(cell_24).trim()))
//					&&(cell_25==null?true:"".equals(String.valueOf(cell_25).trim())))
//			{
//				return true;
//			}
//			return false;
//		}
		
		/**
		 * @param categoryName 分类
		 * @param skuName sku 名称
		 * @param supplierId 供应商id
		 * @return
		 */
//		private SpecificationValue getSpecificationValue(String categoryName,String skuName,Long supplierId,int orders){
//			SpecificationValue speOne = this.specificationValueService.findSpecificationValue(categoryName,skuName,supplierId,orders);
//				if(StringUtils.isEmpty(speOne)){
//					speOne = this.specificationValueService.findSpecificationValue(categoryName,skuName,null,orders);
//					return speOne;
//				}
//			return speOne;
//		}
		
		/**
		 * 判断你是不是直辖市
		 * @param address
		 * @return
		 */
//		private boolean isMunicipality(String address){
//			if(StringUtils.isEmpty(address))
//				return false;
//			String[] municipality ={"北京市","重庆市","上海市","天津市"};
//			for(int i = 0;i<municipality.length;i++){
//				if(address.equals(municipality[i])){
//					return true;
//				}
//			}
//			return false;
//		}
		
		/**判断该分类是否是该店铺的
		 * @param shopId
		 * @param categoryName
		 * @return
		 */
//		private boolean isShopCategory(Long shopId,String categoryName){
//			List<SupplierCategory> supplierCategory = this.getShopCategory(shopId, categoryName);
//			if(StringUtils.isEmpty(supplierCategory)&&supplierCategory.isEmpty())
//				return false;
//			
//			return true;
//		}
		
		/**
		 * 获取店铺的分类信息
		 * @param shopId
		 * @param categoryName
		 * @return
		 */
//		private List<SupplierCategory> getShopCategory(Long shopId,String categoryName){
//			List<SupplierCategory> supplierCategory = this.supplierCategoryService.getShopCategory(shopId,categoryName);
//			if(StringUtils.isEmpty(supplierCategory))
//				return null;
//			return supplierCategory;
//		}
		
		/**
		 *  获取店铺的分类规格
		 * @param supplierId
		 * @param shopId
		 * @param categoryName
		 * @return
		 */
//		private List<Specification> getShopCategorySpecification(Long supplierId,Long shopId,String categoryName){
//			List<SupplierCategory> supplierCategory = this.getShopCategory(shopId, categoryName);//获取分类信息
//			if(supplierCategory == null)
//				return null;
//			
//			/**
//			 * 根据分类查规格
//			 * 
//			 * 店铺自定义规格优先
//			 * */
//			List<Specification> listSpe = new ArrayList<Specification>();
//			Specification specification = null;
//			SupplierSpecification supplierSpecification = new SupplierSpecification();
//			supplierSpecification.setCategoryId(supplierCategory.get(0).getCategoryId());
//			supplierSpecification.setSupplierId(supplierId);
//			List<SupplierSpecification> supplierSpecifications = this.supplierSpecificationService.selectByModel(supplierSpecification);
//			if(StringUtils.isEmpty(supplierSpecifications)||supplierSpecifications.isEmpty()){
//				specification = new Specification();
//				specification.setCategoryId(supplierCategory.get(0).getCategoryId());
//				List<Specification> specifications = this.specificationService.selectByModel(specification);
//				if(!StringUtils.isEmpty(supplierSpecifications)||!specifications.isEmpty()){
//					for(Specification spe : specifications){
//						specification = new Specification();
//						specification.setName(spe.getName());
//						listSpe.add(specification);
//					}
//				}
//					
//			}else{
//				for(SupplierSpecification spe:supplierSpecifications){
//					specification = new Specification();
//					specification.setCategoryId(spe.getCategoryId());
//					specification.setName(spe.getName());
//					listSpe.add(specification);
//				}
//			}
//			return listSpe;
//		}
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @return 单元格值
	 */
	public static Object getValue(Cell cell,Object defaultVal) {
		if(cell == null) return defaultVal;
		
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return defaultVal;
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return cell.getStringCellValue();
		}
	}
}
