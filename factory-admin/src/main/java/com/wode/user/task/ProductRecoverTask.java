package com.wode.user.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.ProductHidden;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.ProductHiddenService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.ProductThirdPriceService;
@Service
public class ProductRecoverTask {
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	
	@Autowired
	private ProductHiddenService productHiddenService;
	
	@Autowired
    private ProductThirdPriceService productThirdPriceService;
	
	@Resource
	private ProductService productService;
	
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	
	public void run() {
		timingRecoverProduct();
	}
	/**
	 * 定期商品复活
	 */
	public void timingRecoverProduct() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 3);
		ProductHidden ph = new ProductHidden();
		ph.setUpdateTime(date.getTime());
		boolean flag = false;
		List<ProductHidden> productHiddenList = productHiddenService.selectByModel(ph);//查询出小于当前系统时间-3天的隐藏商品
		if(productHiddenList!=null&&productHiddenList.size()>0) {
			for (ProductHidden productHidden : productHiddenList) {
				List<ProductSpecifications> psList = productSpecificationsService.findlistByProductid(productHidden.getId());//获取商品所有规格
				for (ProductSpecifications productSpecifications : psList) {
					//获取最新电商价
					ProductThirdPrice productThirdPrice = productThirdPriceService.selectByProductIdAndItemValues(productSpecifications.getProductId(), productSpecifications.getItemValues());
					if(productSpecifications.getInternalPurchasePrice().compareTo(productThirdPrice.getLastPrice())==-1) {//比较每一个规格的内购价是否小于最新电商价
						flag = true;
					}else {
						flag = false;
					}
				}
				if(flag) {//判断所有商品规格的内购价是否都小于电商价
					productService.cache(productHidden.getId(),clientAccessLogService.getDetailPvCnt(null, productHidden.getId()));//将此商品添加到索引里
					productHiddenService.removeById(productHidden.getId());//移除隐藏商品
				}
			}
		}
		
	}
}
