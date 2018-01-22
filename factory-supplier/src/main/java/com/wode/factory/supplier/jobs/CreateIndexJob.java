package com.wode.factory.supplier.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.CommentsStatistics;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierProductExcel;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.service.CommentsService;
import com.wode.factory.supplier.query.BatchAddProductSku;
import com.wode.factory.supplier.query.BatchAddProductVo;
import com.wode.factory.supplier.service.ApprProductService;
import com.wode.factory.supplier.service.CommentsStatisticsService;
import com.wode.factory.supplier.service.ProductService;
import com.wode.factory.supplier.service.SupplierProductExcelService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.AddBatchProduct;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.ExcelUtil;
import com.wode.factory.supplier.util.ZipCompress;

@Component
public class CreateIndexJob {
	 /** Logger */
	private static final Logger logger = LoggerFactory.getLogger(CreateIndexJob.class);
	
	@Autowired
	@Qualifier("commentsService")
	private CommentsService commentsService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	@Autowired
	@Qualifier("commentsStatisticsService")
	private CommentsStatisticsService commentsStatisticsService;
	
	@Autowired
	@Qualifier("supplierProductExcelService")
	private SupplierProductExcelService supplierProductExcelService;
	
	@Autowired
	@Qualifier("excelUtil")
	ExcelUtil excelUtil;
	@Autowired
	@Qualifier("addBatchProduct")
	AddBatchProduct addBatchProduct;
	
	@Autowired
	@Qualifier("zipCompress")
	ZipCompress zipCompress;
	
	@Autowired
	@Qualifier("productService-supplier")
	private ProductService productService;
    @Autowired
    private ApprProductService apprProductService;
   // @Scheduled(fixedRate=1000*60*10)
	
//	@Scheduled(fixedRate = 5000)  
//    void doSomethingWithRate(){  
//        System.out.println("I'm doing with rate now!");  
//    } 


	static UploadService uploader = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	
    @Scheduled(cron="0 0 0 * * ?")
    public void doCreateIndex() throws HttpException, IOException{
    	logger.info("begin to read "); 
    	List<Supplier> supplierlist = supplierService.findAll();
    	commentsStatisticsService.deleteall();
    	Date nowdate = new Date();
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal=Calendar.getInstance(Locale.CHINA);
    	//其余的行不变只加入这一行即可，设定每周的起始日。
    	cal.setFirstDayOfWeek(Calendar.MONDAY); //以周1为首日
    	long millis = System.currentTimeMillis();
    	cal.setTimeInMillis(millis);//当前时间
    	//System.out.println("当前时间:"+simpleDateFormat.format(cal.getTime()));		
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	//System.out.println("周一时间:"+simpleDateFormat.format(cal.getTime()));
    	
    	String now = TimeUtil.dateToStr(nowdate);
    	String week = simpleDateFormat.format(cal.getTime());
    	String month = getFirstDay();
    	for (Supplier supplier : supplierlist) {
    		Map<String,Object> map = commentsService.getSupplierLevelCnt(supplier.getId(), TimeUtil.strToDate(week+" 00:00:00"), TimeUtil.strToDate(now+" 23:59:59"));
        	Long good = (Long)map.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT);
        	Long medium = (Long)map.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT);
        	Long bad = (Long)map.get(CommentsService.CACHE_MAP_KEY_BAD_CNT);

        	Long tote = good+medium+bad;
        	CommentsStatistics commentsStatistics = new CommentsStatistics();
        	commentsStatistics.setGood(good);
        	commentsStatistics.setMedium(medium);
        	commentsStatistics.setBad(bad);
        	commentsStatistics.setSupplierId(supplier.getId());
        	commentsStatistics.setDatetime(nowdate);
        	commentsStatistics.setTote(tote);
        	commentsStatistics.setType(0);
        	commentsStatisticsService.save(commentsStatistics);

        	map = commentsService.getSupplierLevelCnt(supplier.getId(), TimeUtil.strToDate(month+" 00:00:00"), TimeUtil.strToDate(now+" 23:59:59"));
        	good = (Long)map.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT);
        	medium = (Long)map.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT);
        	bad = (Long)map.get(CommentsService.CACHE_MAP_KEY_BAD_CNT);
        	tote = good+medium+bad;
        	
        	CommentsStatistics commentsStatistics2 = new CommentsStatistics();
        	commentsStatistics2.setGood(good);
        	commentsStatistics2.setMedium(medium);
        	commentsStatistics2.setBad(bad);
        	commentsStatistics2.setSupplierId(supplier.getId());
        	commentsStatistics2.setDatetime(nowdate);
        	commentsStatistics2.setTote(tote);
        	commentsStatistics2.setType(1);
        	commentsStatisticsService.save(commentsStatistics2);
    		
		}
    	
    	
    }
    
    
    @Scheduled(cron="0 0 2 1 * ?")
    public void doCreatemonth() throws HttpException, IOException{
    	List<Supplier> supplierlist = supplierService.findAll();
    	
    	Date nowdate = new Date();
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//取得系统当前时间
		Calendar cal = Calendar.getInstance();
		//取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		//输出上月最后一天日期
		String end = simpleDateFormat.format(cal.getTime());
		
		//取得系统当前时间
		Calendar cal2 = Calendar.getInstance();
		//取得系统当前时间所在月第一天时间对象
		cal2.set(Calendar.MONTH, cal2.get(Calendar.MONTH) - 1);
		//日期减一,取得上月最后一天时间对象
		cal2.set(Calendar.DAY_OF_MONTH, 1);
		//输出上月最后一天日期
		String begin = simpleDateFormat.format(cal2.getTime());
    	for (Supplier supplier : supplierlist) {
    		Map<String,Object> map = commentsService.getSupplierLevelCnt(supplier.getId(), TimeUtil.strToDate(begin+" 00:00:00"), TimeUtil.strToDate(end+" 23:59:59"));
        	Long good = (Long)map.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT);
        	Long medium = (Long)map.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT);
        	Long bad = (Long)map.get(CommentsService.CACHE_MAP_KEY_BAD_CNT);
        	
        	Long tote = good+medium+bad;
        	
        	CommentsStatistics commentsStatistics = new CommentsStatistics();
        	commentsStatistics.setGood(good);
        	commentsStatistics.setMedium(medium);
        	commentsStatistics.setBad(bad);
        	commentsStatistics.setSupplierId(supplier.getId());
        	commentsStatistics.setDatetime(nowdate);
        	commentsStatistics.setTote(tote);
        	commentsStatistics.setType(2);
        	commentsStatisticsService.save(commentsStatistics);
    	}
    }
    @Scheduled(cron="0 0/10 * * * ?")
    public void batchAddProduct(){
    	//按照时间升序查找出一条excel记录
    	SupplierProductExcel supplierProductExcel = this.supplierProductExcelService.getUndisposedByTimeAsc();//获取status = 0 or status is null
    	if(StringUtils.isNullOrEmpty(supplierProductExcel))
    		return ;
    	
    	// 一级文件夹名称 和zip压缩包名称相同
    	String level1FolderName = supplierProductExcel.getExcelUrl().substring(supplierProductExcel.getExcelUrl().lastIndexOf("/")+1, supplierProductExcel.getExcelUrl().lastIndexOf("."));
    	/**
		 * 将状态正常修改成处理中  1
		 * 正在处理中
		 * */
		this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 1, null);
		
		
    	/**
    	 * 解压缩zip文件
    	 * 
    	 * */
    	try {//supplierProductExcel.getExcelUrl()  zip上传路径   解压到properties定义的路径下
			zipCompress.readByApacheZipFile(supplierProductExcel.getExcelUrl(), Constant.PRODUCT_ZIP_OUTPUT);
		}  catch (IOException e3) {
			logger.error(supplierProductExcel.getId()+"-------IOException--------"+e3.getMessage());
			e3.printStackTrace();
			this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "zip文件处理异常");
			deleteFile(level1FolderName);//清除文件
			return ;
		}
    	////////////////////////////////////////////////////////////////////////////////////////////////////
    	/**
    	 * get  excel文件对象
    	 * */
    	ActResult<List<BatchAddProductVo>> act = new ActResult<List<BatchAddProductVo>>();
    	Sheet sheet = null;
    	Workbook wordBook = null;
		try {
			//读取解压的文件夹中xlsx文件    解压文件输出路径/+文件夹名称
			wordBook = excelUtil.getZipWorkbook(Constant.PRODUCT_ZIP_OUTPUT+level1FolderName);
			if(StringUtils.isNullOrEmpty(wordBook)){
				this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "压缩包中不存在excel文件,请重新上传");
				return ;
			}
			sheet = wordBook.getSheetAt(0);
		} catch (IOException e2) {
			logger.error(supplierProductExcel.getId()+"--------表格读取异常-------IOException---------"+e2.getMessage());
			e2.printStackTrace();
			this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "读取excel文件异常");
			deleteFile(level1FolderName);//清除文件
			return ;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * 校验批量上传的商品
		 * */
		try { //商品上传zip文件解压路径
			act = this.addBatchProduct.checkBatchAddProductExcel(Constant.PRODUCT_ZIP_OUTPUT+level1FolderName, supplierProductExcel.getSupplierId(),sheet);
		} catch (Exception e) {
			e.printStackTrace();
			this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "请下载并使用最新批量上传模板");
			logger.error(supplierProductExcel.getId()+"-----------------excel表格参数有误----------------"+e.getMessage());
			deleteFile(level1FolderName);//清除文件
			return ;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * 处理excel数据
		 * */
		//成功件数
		Integer successNumber = 0;
		//总件数
		Integer totalNumber = 0;//得到的是总件数
		Long supplierId = supplierProductExcel.getSupplierId();
		//插入操作
		for(BatchAddProductVo product:act.getData()){
			totalNumber=totalNumber+product.getSku().size();//得到总件数
			//可以保存
			if(product.getIsCouldAdd()){////是否能够添加，字段默认是isCouldAdd=true
				//Product pro = null;
				ApprProduct pro =null;
				try {//保存商品异常,null,有返回值
					//Product productModel = new Product();
					ApprProduct productModel = new ApprProduct();
					productModel.setSupplierId(supplierId);
					productModel.setFullName(product.getFullName());
					//查看该商品是否未删除
					//List<Product> products = this.productService.getNotDeleteProductByFullName(productModel);
					//这里需要使用is_maketable>-10来查出未被删除的商品，但是临时表没有这个标记，那就不传条件，查出该商铺下的所有商品
					List<ApprProduct> products = this.apprProductService.getNotDeleteProductByFullName(productModel);
					// 商品不存在
					if (StringUtils.isNullOrEmpty(products) || products.isEmpty()) {
						//上传pc详情图片并获取url
						if(!StringUtils.isNullOrEmpty(product.getPcImages())){
							product.setIntroduction(getIntroduction(product.getPcImages()));
						}
//						//上传app详情图片并获取url
//						if(!StringUtils.isNullOrEmpty(product.getAppImages())){
//							product.setIntroductionMobile(getIntroduction(product.getAppImages()));
//						}
						//上传商品主图
						for(BatchAddProductSku sku : product.getSku()){
							if(sku.getIsCouldAdd()){
								if(sku.getFolderImagePath()!=null){
									for(String image :sku.getFolderImagePath()){//sku图片
										if(StringUtils.isNullOrEmpty(product.getImage())){
											product.setImage(getUploadPicUrl(image));
										}
									}
								}
							}
						}
						pro = this.supplierProductExcelService.saveProduct(product, supplierId);
					}else{
						for (BatchAddProductSku sku : product.getSku()) {
							sku.setProcessingResult(new StringBuffer(StringUtils.isNullOrEmpty(sku.getProcessingResult())?"":sku.getProcessingResult()).append("商品已存在,").toString());
						}
					}
					if(pro==null){
						for(BatchAddProductSku sku : product.getSku()){
							Row row = sheet.getRow(sku.getLine()+1);//得到excel行数
							row.createCell(14).setCellValue(addBatchProduct.getProcessingResult(sku.getProcessingResult()));
						}
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
					for(BatchAddProductSku sku : product.getSku()){
						Row row = sheet.getRow(sku.getLine()+1);//得到excel行数
						row.createCell(14).setCellValue(sku.getProcessingResult()+"商品保存失败");
					}
					continue;
				}
				//商品无法保存
				if(StringUtils.isNullOrEmpty(pro)){
					for(BatchAddProductSku sku : product.getSku()){
						Row row = sheet.getRow(sku.getLine()+1);//得到excel行数
						row.createCell(14).setCellValue(addBatchProduct.getProcessingResult(sku.getProcessingResult()));
					}
				//商品保存成功
				}else{
					
					//////////////////////////////////////////////////////////////////////////
					Map<String,List<String>> imagePath= new HashMap<String, List<String>>();
					for(BatchAddProductSku sku:product.getSku()){
						
						//sku 图片
						List<String> images = imagePath.get(sku.getSpe1Value());
						if(StringUtils.isNullOrEmpty(images)||images.isEmpty()){
							//将sku图片从文件夹中取出来并上传到图片服务器
							List<String> imageUrl = getSpecificationsImageUrl(sku.getFolderImagePath());
							sku.setSkuImageUrl(imageUrl);
							imagePath.put(sku.getSpe1Value(), imageUrl);
						}else{
							sku.setSkuImageUrl(imagePath.get(sku.getSpe1Value()));
						}
						
						
						if(!sku.getIsCouldAdd()){
							Row row = sheet.getRow(sku.getLine()+1);//得到excel行数
							row.createCell(14).setCellValue(addBatchProduct.getProcessingResult(sku.getProcessingResult()));
						}
					}
					//////////////////////////////////////////////////////////////////////////
					int success = 0;
					try {
						//这里要使用临时表的id来获取sku
						success = this.supplierProductExcelService.saveProductSku(pro.getId(),product, supplierId,imagePath);
						
						
					} catch (Exception e) {
						e.printStackTrace();
						for(BatchAddProductSku sku : product.getSku()){
							Row row = sheet.getRow(sku.getLine()+1);//得到excel行数
							row.createCell(14).setCellValue(addBatchProduct.getProcessingResult(sku.getProcessingResult()+"商品规格保存失败"));
						}
					}
					successNumber= successNumber+success;
				}
				
			}else{
				for(BatchAddProductSku sku : product.getSku()){
					Row row = sheet.getRow(sku.getLine()+1);//得到excel行数
					row.createCell(14).setCellValue(addBatchProduct.getProcessingResult(sku.getProcessingResult()));
				}
			}
		}
		supplierProductExcel.setTotalNumber(totalNumber);
		supplierProductExcel.setSuccessNumber(successNumber);
		//总条数等于成功条数
//		if(totalNumber==successNumber){
//			this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "处理成功");
//		}else{
			
			try {
				//处理完excel文件后，将文件上传到指定路径
				File file = new File(Constant.EXCEL_OUTPUT);
				if(!file.exists()){
					file.mkdirs();
				}
				String uploadUrl = Constant.EXCEL_OUTPUT+supplierProductExcel.getId()+".xlsx";
				//将数据处理的信息写入到excel表中
				wordBook.write(new FileOutputStream(new File(uploadUrl)));
				//将数据处理的信息写入到excel表中
				supplierProductExcel.setExcelUrl(uploadUrl);
				this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "处理成功");
			}catch (IOException e) {
				logger.error("向excel表中写入结果集错误--------IOException--------"+e.getMessage());
				e.printStackTrace();
				this.supplierProductExcelService.updateSupplierProductExcel(supplierProductExcel, 2, "系统错误,请联系管理员");
				deleteFile(level1FolderName);//清除文件
			}
//		}
		
		/**
		 * 不管数据处理成功与否，都会删除zip文件和zip解压后的文件夹
		 * */
		deleteFile(level1FolderName);
    }
    
    
    /**
     * 当月第一天
     * @return
     */
    private static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }
    
    /**
     * 当月最后一天
     * @return
     */
    private static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }
    
    /**
     * 获得上传的商品详情
     * @param images
     * @return
     */
    private String getIntroduction(List<String> images){
    	StringBuffer sb = new StringBuffer();
    	for(String imagesPath:images){
    		String url = getUploadPicUrl(imagesPath);
    		if(url != null){
    			sb.append("<p><img alt="+url.substring(url.lastIndexOf("/")+1, url.length())+" src="+url+" title=\"\"/></p>");
    		}
    	}
    	return sb.toString();
    }
    
    /**获得上传图片的url
     * @param imagePath
     * @return
     */
    private String getUploadPicUrl(String imagePath){
    	File file = new File(imagePath);
		if(file.exists()){
			try {
				FileInputStream input = new FileInputStream(file);
				//byte[] buffer = new byte[input.available()];
				//input.read(buffer);
				
				ActResult<List<String>> act = uploader.uploadPic(input,input.available(),imagePath.substring(imagePath.lastIndexOf(".")),"");
				return "http://" + act.getData().get(0);
			} catch (FileNotFoundException e) {
				logger.error("url---"+imagePath+"----------------FileNotFoundException-------------------------"+e.getMessage());
			}catch (IOException e) {
				logger.error("url---"+imagePath+"----------------IOException-------------------------"+e.getMessage());
			}
		}
    	return null;
    }
    
    /**
     * get上传的规格图片url
     * @param images
     * @return
     */
    private List<String> getSpecificationsImageUrl(List<String> images){

    	List<String> list = new ArrayList<String>();
    	if(!StringUtils.isNullOrEmpty(images)||!images.isEmpty()){
    		for(String imagePath:images){
    			String url = getUploadPicUrl(imagePath);
    			if(url!=null)
    				list.add(url);
    		}
    	}
    	return list;
    }
    
	private void deleteFile(String folderName) {
		// zip文件
		File zipFile = new File(Constant.ZIP_UPLOAD_URL+folderName+ ".zip");
		if (zipFile.exists()) {
			zipFile.delete();
		}
		// zip解压的文件夹
		File folderFile = new File(Constant.PRODUCT_ZIP_OUTPUT+folderName);
		if(folderFile.exists()){
			deleteDir(folderFile);
		}
	}
    
    /**
     * 删除zip解压的文件夹
     * @param dir
     */
    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        // 目录此时为空，可以删除
        dir.delete();
    }
}
