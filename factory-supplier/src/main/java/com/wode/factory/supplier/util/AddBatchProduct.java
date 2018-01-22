package com.wode.factory.supplier.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Specification;
import com.wode.factory.model.SpecificationValue;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.supplier.query.BatchAddProductSku;
import com.wode.factory.supplier.query.BatchAddProductVo;
import com.wode.factory.supplier.service.ProductBrandService;
import com.wode.factory.supplier.service.ShippingTemplateService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SpecificationService;
import com.wode.factory.supplier.service.SpecificationValueService;
import com.wode.factory.supplier.service.SupplierCategoryService;
import com.wode.factory.supplier.service.SupplierSpecificationService;
@Service("addBatchProduct")
public class AddBatchProduct {
	
	private DecimalFormat zipDf = new DecimalFormat("##.#");
	
	@Autowired
	@Qualifier("excelUtil")
	private ExcelUtil excelUtil;
	
	@Autowired
	@Qualifier("zipCompress")
	ZipCompress zipCompress;
	
	@Autowired
	@Qualifier("shopService")
	ShopService shopService;
	
	@Autowired
	@Qualifier("supplierSpecificationService")
	private SupplierSpecificationService supplierSpecificationService;
	
	@Autowired
	@Qualifier("productBrandService")
	private ProductBrandService productBrandService;
	@Autowired
	@Qualifier("specificationService")
	private SpecificationService specificationService;
	
	@Autowired
	@Qualifier("shippingTemplateService")
	private ShippingTemplateService shippingTemplateService;
	
	@Autowired
	@Qualifier("specificationValueService")
	private SpecificationValueService specificationValueService;
	@Autowired
	@Qualifier("supplierCategoryService")
	private SupplierCategoryService supplierCategoryService;
	/*
	 * 
	 * 功能描述：批量添加商品
	 */
	
	//校验批量上传的商品（貌似通过商品编号来）
	public ActResult<List<BatchAddProductVo>> checkBatchAddProductExcel(String url,Long supplierId,Sheet sheet){
		List<BatchAddProductVo> product = new ArrayList<BatchAddProductVo>();
		//解压后文件的地址
		String folderPath  = url;
//				Constant.PRODUCT_ZIP_OUTPUT+url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")+1);
		Row row = null;
		// 这里获得的是总行数
		int totalRows = sheet.getLastRowNum();
		//第二行中的数据
		Row firstRow = sheet.getRow(1);
		if(totalRows<0||firstRow==null){
			//内容为空
			return ActResult.fail("excel表中数据为空");
		}
		//第二行总列数
		int firstRowTotalCells = firstRow.getLastCellNum();
		if(firstRowTotalCells!=14){
			return ActResult.fail("请下载并使用最新批量上传模板");
		}
		Row lineTwo =  sheet.getRow(1);//excel表第二行数据
		//从第三行开始取数据
		for(int line =2;line<totalRows+1;line++){
			row = sheet.getRow(line);//行
			//判断改行是不是为空
			if(this.isCellNull(row)){
				continue;
			}
			//判断商品编号在集合中是否存在。默认不存在
			boolean flag = false;
			//商品规格的处理结果
			StringBuffer processingResult = new StringBuffer();
			//商品编号-------------------------
			Cell productNumber = row.getCell(0);
			for(int j =0;j<product.size();j++){
				BatchAddProductVo productVo = product.get(j);
				
				if(!StringUtils.isNullOrEmpty(productNumber)){//如果商品编号不为空
					String proNumber=null;
					if(productNumber.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){//表格类型
						DecimalFormat df = new DecimalFormat("#");//#表示格式化为阿拉伯数字，如果不存在则显示为空
						proNumber = df.format(excelUtil.returnParam(productNumber));
					}else if(productNumber.getCellType()==HSSFCell.CELL_TYPE_STRING){//如果是string就转成
						proNumber = StringUtils.toString(excelUtil.returnParam(productNumber));
					}
					//line行的 商品编号和product集合中商品的编号相等
					if(productVo.getProductNumber().equals(proNumber)){
						//校验除了sku信息
						this.checkSkuOther(row, processingResult, productVo, true,supplierId,folderPath);
//						List<BatchAddProductSku> skus = productVo.getSku();
						BatchAddProductSku sku = this.checkSku(row,lineTwo, processingResult, supplierId,productVo,folderPath+"/"+productVo.getProductNumber()+"/主图/",line-1);
						List<BatchAddProductSku> skus = productVo.getSku();
						skus.add(sku);
						productVo.setSku(skus);
						flag = true;
						break;
					}
				}
			}
			//商品编号不存在,需要保存
			if(!flag){
				BatchAddProductVo productVo = new BatchAddProductVo();
				this.checkSkuOther(row, processingResult, productVo, false,supplierId,folderPath);
				List<BatchAddProductSku> skus = new ArrayList<BatchAddProductSku>();
				BatchAddProductSku sku = this.checkSku(row,lineTwo, processingResult, supplierId,productVo,folderPath+"/"+productVo.getProductNumber()+"/主图/",line-1);
				skus.add(sku);
				productVo.setSku(skus);
				product.add(productVo);
			}
		}
		
		return ActResult.success(product);
	}
	
	/**
	 * 校验sku以外 的信息
	 * @param row
	 * @param processingResult 返回报告
	 * @param productVo 空对象
	 * @param proNumberIsAvailable 商品编号是否存在。List<BatchAddProductVo> product = new ArrayList<BatchAddProductVo>();
	 *  						         商品编号在product集合中存在不存在。如果存在 值为true,不存在为false
	 * @param folderPath 文件夹地址
	 */
	public void checkSkuOther(Row row,StringBuffer processingResult,BatchAddProductVo productVo,Boolean proNumberIsAvailable,Long supplierId,String folderPath){
		//基本内容------------------------
		Cell productNumber = row.getCell(0);//第一列 商品编号
		Cell categoryName = row.getCell(1);//第二列 分类
		Cell shopName = row.getCell(2);//第三列店铺名称
		Cell fullName = row.getCell(3);//第四列 标题
		Cell name = row.getCell(4);//第五列 副标题
		Cell brand = row.getCell(5);//第六列 商品品牌	
		
		if(StringUtils.isNullOrEmpty(productNumber)){
			processingResult.append("商品编号不能为空,");
			productVo.setIsCouldAdd(false);
		}else{
			//商品编号
			if(!proNumberIsAvailable){
				if(productNumber.getCellType()==HSSFCell.CELL_TYPE_STRING){
					productVo.setProductNumber(StringUtils.toString(excelUtil.returnParam(productNumber)));
				}else if(productNumber.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					productVo.setProductNumber(zipDf.format(excelUtil.returnParam(productNumber)));
				}
			}
		}
		
		//商品的详情图片
		if (!proNumberIsAvailable) {
			List<String> folderFileImagePath = this.zipCompress.getFolderFileImagePath(folderPath+"/"+productVo.getProductNumber()+"/详情图/", false);
			if (!folderFileImagePath.isEmpty()) {
				productVo.setPcImages(folderFileImagePath);
				productVo.setAppImages(folderFileImagePath);
			}
		}
		//店铺名称 
		if(StringUtils.isNullOrEmpty(shopName)){
//			processingResult.append("店铺名称不能为空,");
//			productVo.setIsCouldAdd(false);
		}else{
			String shopname = StringUtils.toString(excelUtil.returnParam(shopName));
//			if(shopName.getCellType()==HSSFCell.CELL_TYPE_STRING||shopName.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				if(proNumberIsAvailable){//
					if(!(StringUtils.isNullOrEmpty(productVo.getShopname())?"":productVo.getShopname()).equals(shopname)){
						processingResult.append("店铺名称不相同,");
						productVo.setIsCouldAdd(false);			
					}
				}else{
					Shop shopModel = new Shop();
					shopModel.setSupplierId(supplierId);
					shopModel.setShopname(shopname);
					List<Shop> shops = this.shopService.selectByModel(shopModel);
					if(StringUtils.isNullOrEmpty(shops)||shops.isEmpty()){
						productVo.setShopname(shopname);
					}else{
						Shop shop = shops.get(0);
						if(supplierId.equals(shop.getSupplierId())){
							productVo.setShopname(shopname);
							productVo.setShopId(shop.getId());//店铺id
						}else{
							processingResult.append("请使用本店铺名称,");
							productVo.setIsCouldAdd(false);			
						}
					}
				}
//			}else{
//				processingResult.append("店铺名称参数类型错误,");
//				productVo.setIsCouldAdd(false);
//			}
		}
		
		//商品分类
		if(StringUtils.isNullOrEmpty(categoryName)){
			productVo.setCategoryName("临时分类");//分类名称
			productVo.setCategoryId(88888888L);//设为临时分类
		}else{
			String cateName = StringUtils.toString(excelUtil.returnParam(categoryName));
			//商品分类不相等，且商品编号存在
			if(!(StringUtils.isNullOrEmpty(productVo.getCategoryName())?"":productVo.getCategoryName()).equals(cateName)&&proNumberIsAvailable){
				processingResult.append("商品分类不相同,");
				productVo.setIsCouldAdd(false);						
			}else if(!proNumberIsAvailable){
				//校验商品分类是不是该商铺的
//				if(!StringUtils.isNullOrEmpty(productVo.getShopId())){
					//是否是本店铺的商品分类
					List<SupplierCategory> supplierCategory = getShopCategory(supplierId, productVo.getShopId(),cateName);
					if(supplierCategory!=null && !supplierCategory.isEmpty()){//isShopCategory(productVo.getShopId(),cateName)
						productVo.setCategoryName(cateName);//分类名称
						productVo.setCategoryId(supplierCategory.get(0).getCategoryId());//分类在商家分类表(supplierCategory)中的id
					}else{
						productVo.setCategoryName(cateName);//分类名称
						productVo.setCategoryId(88888888L);//设为临时分类
					}
//				} else {
//					productVo.setCategoryName(cateName);//分类名称
//					productVo.setCategoryId(88888888L);//设为临时分类
//					
//				}
			}
		}
		
		//商品标题
		if(StringUtils.isNullOrEmpty(fullName)){
			processingResult.append("商品标题不能为空,");
			productVo.setIsCouldAdd(false);
		}else{
			String strFullName = StringUtils.toString(excelUtil.returnParam(fullName));
			//商品分类不相等，且商品编号存在
			if(!(StringUtils.isNullOrEmpty(productVo.getFullName())?"":productVo.getFullName()).equals(strFullName)&&proNumberIsAvailable){
				processingResult.append("商品标题不相同,");
				productVo.setIsCouldAdd(false);								
			}else if(!proNumberIsAvailable){
				productVo.setFullName(strFullName);
			}
		}
		
		//校验副标题
		if(StringUtils.isNullOrEmpty(name)){
//			processingResult.append("商品副标题不能为空,");
//			productVo.setIsCouldAdd(false);
		}else{
			String strName = StringUtils.toString(excelUtil.returnParam(name));
			if(!(StringUtils.isNullOrEmpty(productVo.getName())?"":productVo.getName()).equals(strName)&&proNumberIsAvailable){//有标题 
				processingResult.append("商品副标题不相同,");
				productVo.setIsCouldAdd(false);								
			}else if(!proNumberIsAvailable){//没有标题
				productVo.setName(strName);
			}
		}
		
		/**
		 * 
		 * 根据商品名称和商家id查询商品品牌
		 * */
		if(StringUtils.isNullOrEmpty(brand)){
//			processingResult.append("商品品牌不能为空,");
//			productVo.setIsCouldAdd(false);
		}else{
			String strBrande = StringUtils.toString(excelUtil.returnParam(brand));
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name", strBrande);
			map.put("supplierId", supplierId);
			map.put("shopId", productVo.getShopId());
			List<ProductBrand> productBrands = this.productBrandService.findAllBymap(map);
			if(productBrands.isEmpty()){
////					if(proNumberIsAvailable){
//					productVo.setIsCouldAdd(false);								
//					processingResult.append("商品品牌不存在,");
////					}
			}else{
				Boolean brandIsAvailable = false;
				for(ProductBrand productBrand:productBrands){
					if(productBrand.getName().equals(strBrande)&&productBrand.getSupplierId().equals(supplierId)){
						brandIsAvailable=true;
						if(!proNumberIsAvailable){
							productVo.setBrandName(strBrande);
							productVo.setBrandId(productBrand.getId());
						}
					}
				}
				//商品品牌不存在
//					if(!brandIsAvailable){
//						if(proNumberIsAvailable){
//							processingResult.append("商品品牌不存在,");
//							productVo.setIsCouldAdd(false);								
//						}
//					}
			}
		}
		
		//运费
		productVo.setCarriage(BigDecimal.ZERO);

		
		//售后服务
//		if(!StringUtils.isNullOrEmpty(afterService)){
//			if(afterService.getCellType()==HSSFCell.CELL_TYPE_STRING){
//				if(!(StringUtils.isNullOrEmpty(productVo.getAfterService())?"":productVo.getAfterService()).equals(StringUtils.toString(excelUtil.returnParam(afterService)))&&proNumberIsAvailable){
//					processingResult.append("售后服务内容不相同,");
//					productVo.setIsCouldAdd(false);
//				}else if(!proNumberIsAvailable){
//					productVo.setAfterService(StringUtils.toString(excelUtil.returnParam(afterService)));
//				}
//			} else if(afterService.getCellType()==HSSFCell.CELL_TYPE_BLANK){
//				//可以为空
//			}else{
//				processingResult.append("售后服务应该为汉字,");
//				productVo.setIsCouldAdd(false);
//			}
//		}
		//其他服务
		productVo.setStockLockType(1);  //拍下减库存
	}
	/**
	 * 校验sku基本信息
	 * @param row
	 * @param processingResult
	 * @param supplierId
	 * @param folderPath  文件夹/商品编号/主图/
	 * @return
	 */
	public BatchAddProductSku checkSku(Row row,Row lineTwo,StringBuffer processingResult,Long supplierId,BatchAddProductVo productVo,String folderPath,int line){
		Cell productImageFolder = row.getCell(6);//第十列 商品主图片文件夹
		Cell sku1 = row.getCell(7);//第十一列 商品规格一
		Cell sku2 = row.getCell(8);//第十二列 商品规格二
		Cell productCode = row.getCell(9);//第十三列 条形码
		/*DecimalFormat df = new DecimalFormat("#"); //这里为了去掉科学计数法
		String productCodeTemp =df.format(productCode.getNumericCellValue());*/
		Cell price = row.getCell(10);//第十四列 电商价
		Cell realPrice = row.getCell(11);//第十七列 内购价
		Cell stock = row.getCell(12);//第十五列 库存
		Cell warnnum = row.getCell(13);//第十六列 库存预警值
		BatchAddProductSku sku = new BatchAddProductSku();
		sku.setLine(line);
		List<String> skuImagesPath = null;
		String folderName=null;
		String sku1Value =null;
		String sku2Value =null;
		List<BatchAddProductSku> skus =productVo.getSku();//
		/**
		 * 商品图片文件夹的图片文件
		 * */
		if(StringUtils.isNullOrEmpty(productImageFolder)){
			processingResult.append("商品图片文件夹不存在,");
			sku.setIsCouldAdd(false);
		}else{
			folderName = StringUtils.toString(excelUtil.returnParam(productImageFolder));
//			if(productImageFolder.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//				folderName = zipDf.format(excelUtil.returnParam(productImageFolder));
//				//得到mian文件夹下所有的图片path
//				skuImagesPath = this.zipCompress.getFolderFileImagePath(folderPath+folderName,true);
//				sku.setProductImageFolder(folderName);
//				sku.setFolderImagePath(skuImagesPath);
//			}else if(productImageFolder.getCellType()==HSSFCell.CELL_TYPE_STRING){
				skuImagesPath = this.zipCompress.getFolderFileImagePath(folderPath+folderName,true);
				if(skuImagesPath != null && skuImagesPath.size()>0) {
					int i= 0;
					while (skuImagesPath.size()<5) {
						skuImagesPath.add(skuImagesPath.get(i));
						i++;
					}
				}
				sku.setProductImageFolder(folderName);
				sku.setFolderImagePath(skuImagesPath);
//			}else{
//				processingResult.append("商品图片文件夹名称错误,");
//				sku.setIsCouldAdd(false);
//			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//获取店铺的分类规格
		List<SupplierSpecification> specifications = getShopCategorySpecification(supplierId, productVo.getShopId(), productVo.getCategoryId());
		if(specifications == null){
			//商品分类不存在，在checkSkuOther方法中已经校验
		}else{
			//get 规格一的值
			if(!StringUtils.isNullOrEmpty(sku1)){
				sku1Value = StringUtils.toString(excelUtil.returnParam(sku1));
				sku.setSpe1Value(sku1Value);
			}
			//get 规格二的值
			if(!StringUtils.isNullOrEmpty(sku2)){
				sku2Value = StringUtils.toString(excelUtil.returnParam(sku2));
				if(StringUtils.isEmpty(sku2Value) || "null".equals(sku2Value)) {
					sku2Value=null;
				}
				sku.setSpe2Value(sku2Value);
			}
			
			if(specifications.isEmpty()){
				//该商品分类不存在规格信息
//			processingResult.append("该商品分类下没有规格");
//			sku.setIsCouldAdd(false);
				sku.setCustomSpe(true);//是自定义规格
				Cell spe1 = lineTwo.getCell(7);
				Cell spe2 = lineTwo.getCell(8);
				if(StringUtils.isNullOrEmpty(spe1)){
					sku.setIsCouldAdd(false);
					processingResult.append("规格一不能为空,");
				}else{
					sku.setSpe1(StringUtils.toString(excelUtil.returnParam(spe1)));//规格一
				}
				
				if(!StringUtils.isNullOrEmpty(spe2)){
					sku.setSpe2(StringUtils.toString(excelUtil.returnParam(spe2)));//规格二
				}
				
			}else{
				/**
				 * 判断商品分类下规格数量。如果为1 规格一不能为空，规格二可以为空。如果为2 规格1 和规格2都不能为空。
				 * */
				int speSize = specifications.size();
				if(speSize==1){
					if(StringUtils.isEmpty(sku1Value)){
						processingResult.append("规格一值不能为空,");
						sku.setIsCouldAdd(false);
					}else if(!StringUtils.isEmpty(sku2Value)){
						processingResult.append("您的分类中只能存在一种规格值,");
						sku.setIsCouldAdd(false);
					}else if(!StringUtils.isEmpty(sku1Value)){
						/**
						 * 
						 * 规格一不用校验存在不存在。需要在保存sku的时候进行校验，不存在就插入规格值
						 * 
						 * */
						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						SpecificationValue speValue = getSpecificationValue(specifications.get(0),StringUtils.isNullOrEmpty(sku1)?"":sku1Value, supplierId);
						if(StringUtils.isNullOrEmpty(speValue)){
							processingResult.append("商品规格一值不存在,");
							sku.setIsCouldAdd(false);
						}else{
							sku.setSpe1Id(speValue.getSpecificationId());
							sku.setSpe1ValueOrders(speValue.getOrders());
						}
//					sku.setSku1SpecificationId(specifications.get(0).getId());//规格一的规格id
						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						
						
					}
				}else if(speSize == 2){
					/**
					 * 规格一
					 * */
					if(StringUtils.isEmpty(sku1Value)) {
						processingResult.append("规格一值不能为空,");
						sku.setIsCouldAdd(false);
					} else if(StringUtils.isEmpty(sku2Value)) {
						processingResult.append("规格二值不能为空,");
						sku.setIsCouldAdd(false);
					} else {
						/**
						 * 
						 * 规格一|规格二不用校验存在不存在。需要再保存sku的时候进行校验，不存在就插入规格值
						 * 
						 * */
						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						SpecificationValue spe1Value = getSpecificationValue(specifications.get(0),StringUtils.isNullOrEmpty(sku1)?"":sku1Value, supplierId);
						SpecificationValue spe2Value = getSpecificationValue(specifications.get(1),StringUtils.isNullOrEmpty(sku2)?"":sku2Value, supplierId);
						if(StringUtils.isNullOrEmpty(spe1Value)){
							processingResult.append("商品规格一值不存在,");
							sku.setIsCouldAdd(false);
						}else{
							sku.setSpe1Id(spe1Value.getSpecificationId());
							sku.setSpe1ValueOrders(spe1Value.getOrders());
						}
						if(StringUtils.isNullOrEmpty(spe2Value)){
							processingResult.append("商品规格二值不存在,");
							sku.setIsCouldAdd(false);
						}else{
							sku.setSpe2Id(spe2Value.getSpecificationId());
							sku.setSpe2ValueOrders(spe2Value.getOrders());//排序值
						}
						/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						
					}
				}else{
					processingResult.append("商品规格不能大于2个,");
					sku.setIsCouldAdd(false);
				}
			}
			
		}
		
		/**
		 * 条形码
		 * 
		 * 添加商品没有校验商品条形码唯一
		 * */
		//条形码为空。
		if(StringUtils.isNullOrEmpty(productCode)){
			processingResult.append("商品条形码不能为空,");
			sku.setIsCouldAdd(false);
		}else{
			sku.setProductCode(StringUtils.toString(excelUtil.returnParam(productCode)));//不知道为什么不能屏蔽科学计数法
			//sku.setProductCode(productCodeTemp);
		}
		
		//电商价
		if(StringUtils.isNullOrEmpty(price)){
			processingResult.append("商品电商价不能为空,");
			sku.setIsCouldAdd(false);
		}else{
			try{
				BigDecimal priceValue = new BigDecimal(excelUtil.returnParam(price)+"");
				sku.setPrice(priceValue);
				if(priceValue.compareTo(new BigDecimal(1))<0){
					processingResult.append("商品售价不能为0或者小于0,");
					sku.setIsCouldAdd(false);
				}else{
					
					//内购价
					if(StringUtils.isNullOrEmpty(realPrice)){
						processingResult.append("商品内购价不能为空,");
						sku.setIsCouldAdd(false);
					}else{
						try{
							BigDecimal real = new BigDecimal(excelUtil.returnParam(realPrice)+"");
							if(StringUtils.isNullOrEmpty(real)){
								processingResult.append("商品内购价不能为空,");
								sku.setIsCouldAdd(false);
							}else if(real.compareTo(new BigDecimal(0))<0){
								processingResult.append("商品内购价不能为负数,");
								sku.setIsCouldAdd(false);
							}else{
								if(real.compareTo(priceValue)>0){
									processingResult.append("商品内购价不能大于商品的电商价,");
									sku.setIsCouldAdd(false);
								}
							}	
							sku.setMaxFucoin(priceValue.subtract(real).setScale(2, BigDecimal.ROUND_DOWN));
							sku.setRealPrice(real);
						} catch(Exception e){
							processingResult.append("商品可内购券应该为数字,");
							sku.setIsCouldAdd(false);
						}
					}
					
				}
			} catch(Exception e){
				processingResult.append("商品可内购券应该为数字,");
				sku.setIsCouldAdd(false);
			}
		}
		//库存
		if(StringUtils.isNullOrEmpty(stock)){
			processingResult.append("商品库存不能为空,");
			sku.setIsCouldAdd(false);
		}else{
			try{
				Integer stockValue = new BigDecimal(excelUtil.returnParam(stock)+"").intValue();
				sku.setStock(stockValue);
				if(stockValue<1){
					processingResult.append("商品库存不能为0或者小于0,");
					sku.setIsCouldAdd(false);
				}
			} catch(Exception e){
				processingResult.append("商品库存应该为数字,");
				sku.setIsCouldAdd(false);
			}
		}
		//库存预警值
		if(StringUtils.isNullOrEmpty(warnnum)){
			processingResult.append("商品库存预警值不能为空,");
			sku.setIsCouldAdd(false);
		}else{
			try{
				Integer warnnumValue = new BigDecimal(excelUtil.returnParam(warnnum)+"").intValue();
				sku.setWarnnum(warnnumValue);
				if(warnnumValue<1){
					processingResult.append("商品库存预警值不能为0或者小于0,");
					sku.setIsCouldAdd(false);
				}
			} catch(Exception e){
				processingResult.append("商品库存预警值应该为数字,");
				sku.setIsCouldAdd(false);
			}
		}
		if(StringUtils.isNullOrEmpty(skus)||skus.isEmpty()){
			
			sku.setProcessingResult(processingResult.toString());
			return sku;
		/**
		 * sku不为空
		 * 
		 * 商品图片文件夹名称和规格一名称是对应的。
		 * 相同的图片文件夹不能有不同的规格一，反之不同的图片文件夹不能有相同的规格一
		 * */
		}else{
			for(BatchAddProductSku productSku :skus){
				//文件夹相同
				if((StringUtils.isNullOrEmpty(folderName)?"":folderName).equals(productSku.getProductImageFolder())){
					if(sku1Value == null){
						processingResult.append("规格一值不能为空,");
						sku.setIsCouldAdd(false);
					}else{
						if(sku1Value.equals(productSku.getSpe1Value())){
							if(sku2Value !=null){
								if(sku2Value.equals(productSku.getSpe2Value())){
									processingResult.append("规格二值错误,");
									sku.setIsCouldAdd(false);
									break;
								}
							}
						}else{
							processingResult.append("规格一值错误,");
							sku.setIsCouldAdd(false);
							break;
						}
					}
						
				//规格一相同
				}else if((StringUtils.isNullOrEmpty(sku1Value)?"":sku1Value).equals(productSku.getSpe1Value())){
					if((StringUtils.isNullOrEmpty(folderName)?"":folderName).equals(productSku.getProductImageFolder())){
						if(sku2Value!=null){
							if(sku2Value.equals(productSku.getSpe2Value())){
								processingResult.append("规格二值不能重复,");
								sku.setIsCouldAdd(false);
								break;
							}
						}
					}else{
						processingResult.append("图片文件夹名称错误,");
						sku.setIsCouldAdd(false);
						break;
					}
				}
			}
		}
		sku.setProcessingResult(processingResult.toString());
		return sku;
	}
	
	
	
	
	/**
	 * 校验行是否为空
	 * @param row
	 * @return
	 */
	private boolean isCellNull(Row row){
		Cell cell_0 = row.getCell(0);
		Cell cell_3 = row.getCell(3);
		Cell cell_6 = row.getCell(6);
		Cell cell_7 = row.getCell(7);
		Cell cell_10 = row.getCell(10);
		Cell cell_11 = row.getCell(11);
		Cell cell_12 = row.getCell(12);
		Cell cell_13 = row.getCell(13);
		if((cell_0==null?true:"".equals(StringUtils.toString(cell_0).trim()))
				&&(cell_3==null?true:"".equals(StringUtils.toString(cell_3).trim()))
				&&(cell_6==null?true:"".equals(StringUtils.toString(cell_6).trim()))
				&&(cell_7==null?true:"".equals(StringUtils.toString(cell_7).trim()))
				&&(cell_10==null?true:"".equals(StringUtils.toString(cell_10).trim()))
				&&(cell_11==null?true:"".equals(StringUtils.toString(cell_11).trim()))
				&&(cell_12==null?true:"".equals(StringUtils.toString(cell_12).trim()))
				&&(cell_13==null?true:"".equals(StringUtils.toString(cell_13).trim())))
		{
			return true;
		}
		return false;
	}
	
	/**判断该分类是否是该店铺的
	 * @param shopId
	 * @param categoryName
	 * @return
	 */
	private boolean isShopCategory(Long supplierId,Long shopId,String categoryName){
		List<SupplierCategory> supplierCategory = this.getShopCategory(supplierId,shopId, categoryName);//获取分类信息
		if(StringUtils.isNullOrEmpty(supplierCategory)&&supplierCategory.isEmpty())
			return false;
		
		return true;
	}
	
	/**
	 * 获取店铺的分类信息
	 * @param shopId
	 * @param categoryName
	 * @return
	 */
	private List<SupplierCategory> getShopCategory(Long supplierId, Long shopId,String categoryName){
		List<SupplierCategory> supplierCategory = this.supplierCategoryService.getShopCategory(supplierId,shopId,categoryName);
		if(StringUtils.isNullOrEmpty(supplierCategory))
			return null;
		return supplierCategory;
	}
	
	/**
	 *  获取店铺的分类规格
	 * @param supplierId
	 * @param shopId
	 * @param categoryName
	 * @return
	 */
	private List<SupplierSpecification> getShopCategorySpecification(Long supplierId,Long shopId,Long categoryId){
		
		/**
		 * 根据分类查规格
		 * 
		 * 店铺自定义规格优先
		 * */
		List<SupplierSpecification> listSpe = new ArrayList<SupplierSpecification>();
		SupplierSpecification supplierSpecification = new SupplierSpecification();
		supplierSpecification.setCategoryId(categoryId);
		supplierSpecification.setSupplierId(supplierId);
		supplierSpecification.setType(2);
		List<SupplierSpecification> supplierSpecifications = this.supplierSpecificationService.selectByModel(supplierSpecification);
		if(StringUtils.isNullOrEmpty(supplierSpecifications)||supplierSpecifications.isEmpty()){
			Specification q = new Specification();
			q.setCategoryId(categoryId);
			List<Specification> specifications = this.specificationService.selectByModel(q);
			if(!StringUtils.isNullOrEmpty(supplierSpecifications)||!specifications.isEmpty()){
				for(Specification spe : specifications){
					SupplierSpecification specification = new  SupplierSpecification();
					specification.setName(spe.getName());
					specification.setOrders(spe.getOrders());
					specification.setId(spe.getId());
					listSpe.add(specification);
				}
			}
				
		}else{
			for(SupplierSpecification spe:supplierSpecifications){
				SupplierSpecification specification = new  SupplierSpecification();
				specification.setCategoryId(spe.getCategoryId());
				specification.setName(spe.getName());
				specification.setOrders(spe.getOrders());
				specification.setId(spe.getId());
				specification.setSupplierId(spe.getSupplierId());
				listSpe.add(specification);
			}
		}
		return listSpe;
	}
	
	/**
	 * @param categoryName 分类
	 * @param skuName sku 名称
	 * @param supplierId 供应商id
	 * @return
	 */
	private SpecificationValue getSpecificationValue(SupplierSpecification specification,String skuName,Long supplierId){
		SpecificationValue q = new SpecificationValue();
		q.setSpecificationId(specification.getId());
		List<SpecificationValue> ls = this.specificationValueService.findSpecificationValue(q);
		for (SpecificationValue specificationValue : ls) {
			if(specificationValue.getName().equals(skuName)) return specificationValue;
		}
		if(specification.getSupplierId() == null || StringUtils.isEmpty(skuName)) {
			return null;
		}
		SpecificationValue speOne = new SpecificationValue();
		speOne.setName(skuName);
		speOne.setOrders(ls.size()+1);
		speOne.setSpecificationId(specification.getId());
		specificationValueService.save(speOne);
		return speOne;
	}
	/**
	 * 获取处理后的处理返回值
	 * @param processingResult
	 * @return
	 */
	public String getProcessingResult(String processingResult){
		if(StringUtils.isNullOrEmpty(processingResult)){
			return null;
		}else{
			int length = processingResult.length();
			if(length>1){
				if(processingResult.substring(length-1, length).equals(",")){
					return processingResult.substring(0,length-1);
				}else{
					return processingResult;
				}
			}else{
				return null;
			}
		}
	}
	/**
	 * 是否是运费模板
	 * @param freight
	 * @return 返回true 是运费模板，返回false 不是运费模板
	 */
	public boolean isShipTemplate(Cell freight){
		try {
			Double.valueOf(excelUtil.returnParam(freight)+"");
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
