package com.wode.factory.supplier.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BatchAddProductSku  implements Serializable{
	private static final long serialVersionUID = -4689737585748485772L;
	//商品图片文件夹名称
	private String productImageFolder;
	//文件夹中图片的路径
	private List<String> folderImagePath;
	private List<String> skuImageUrl;
	//商品规格值1
	private String spe1Value;
	//商品规格1
	private String spe1;
	//商品规格2
	private String spe2;
	//商品规格值2
	private String spe2Value;
	//规格值1排序
	private int spe1ValueOrders;
	//规格值2排序
	private int spe2ValueOrders;
	//商品价钱
	private BigDecimal price;
	//商品库存
	private Integer stock;
	//商品预警值
	private Integer warnnum;
	//商品最大内购券使用
	private BigDecimal maxFucoin;
	//商品条形码
	private String productCode;
	//是否添加
	private Boolean isAdd=false;
	//是否能够添加
	public Boolean isCouldAdd=true;
	//是否是自定义规格
	public boolean isCustomSpe = false;
	//处理结果
	public String processingResult;
	//规格一的id
	public Long spe1Id;
	//规格二的id
	public Long spe2Id;
	//第几行
	public Integer line;
	//内购价
	private BigDecimal realPrice;
	public String getSpe1() {
		return spe1;
	}
	public void setSpe1(String spe1) {
		this.spe1 = spe1;
	}
	public String getSpe2() {
		return spe2;
	}
	public void setSpe2(String spe2) {
		this.spe2 = spe2;
	}
	public boolean isCustomSpe() {
		return isCustomSpe;
	}
	public void setCustomSpe(boolean isCustomSpe) {
		this.isCustomSpe = isCustomSpe;
	}
	public Long getSpe1Id() {
		return spe1Id;
	}
	public void setSpe1Id(Long spe1Id) {
		this.spe1Id = spe1Id;
	}
	public Long getSpe2Id() {
		return spe2Id;
	}
	public void setSpe2Id(Long spe2Id) {
		this.spe2Id = spe2Id;
	}
	public int getSpe1ValueOrders() {
		return spe1ValueOrders;
	}
	public void setSpe1ValueOrders(int spe1ValueOrders) {
		this.spe1ValueOrders = spe1ValueOrders;
	}
	public int getSpe2ValueOrders() {
		return spe2ValueOrders;
	}
	public void setSpe2ValueOrders(int spe2ValueOrders) {
		this.spe2ValueOrders = spe2ValueOrders;
	}
	public List<String> getSkuImageUrl() {
		return skuImageUrl;
	}
	public void setSkuImageUrl(List<String> skuImageUrl) {
		this.skuImageUrl = skuImageUrl;
	}
	public List<String> getFolderImagePath() {
		return folderImagePath;
	}
	public void setFolderImagePath(List<String> folderImagePath) {
		this.folderImagePath = folderImagePath;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getProcessingResult() {
		return processingResult;
	}
	public void setProcessingResult(String processingResult) {
		this.processingResult = processingResult;
	}
	public Boolean getIsCouldAdd() {
		return isCouldAdd;
	}
	public void setIsCouldAdd(Boolean isCouldAdd) {
		this.isCouldAdd = isCouldAdd;
	}
	public Boolean getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}
	public String getProductImageFolder() {
		return productImageFolder;
	}
	public void setProductImageFolder(String productImageFolder) {
		this.productImageFolder = productImageFolder;
	}
	public String getSpe1Value() {
		return spe1Value;
	}
	public void setSpe1Value(String spe1Value) {
		this.spe1Value = spe1Value;
	}
	public String getSpe2Value() {
		return spe2Value;
	}
	public void setSpe2Value(String spe2Value) {
		this.spe2Value = spe2Value;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getWarnnum() {
		return warnnum;
	}
	public void setWarnnum(Integer warnnum) {
		this.warnnum = warnnum;
	}
	public BigDecimal getMaxFucoin() {
		return maxFucoin;
	}
	public void setMaxFucoin(BigDecimal maxFucoin) {
		this.maxFucoin = maxFucoin;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	
}
