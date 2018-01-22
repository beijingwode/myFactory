package com.wode.user.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.ProductDao;
import com.wode.factory.model.Product;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.ProductService;

@Service
public class ProductPvDayOnline {
	
	@Autowired
	private ProductDao productDao;
	@Resource
	private ProductService productService;
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	/**
	 * 指定日期上线商品数据每天统计信息
	 */
	public void run() {
		// 获取当前系统时间
		Calendar date = Calendar.getInstance();
		// 设置创建时间 将月份减3表示当前时间的三个月时间
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("selfTime", date.getTime());
		productPvDayOnlineStatistic(map);
	}

	private void productPvDayOnlineStatistic(Map<String, Object> map) {
		map.put("selfType", "2");
		//List<Product> productList = productDao.findList(map);
		List<Product> productList =productDao.findBySelfTypeAndSelfTime(map);
		if(productList!=null && !productList.isEmpty()){
			for (Product product : productList) {
				//修改上线状态
				product.setSelfType(3);
				productDao.updateSelfType(product);
				//商品添加到缓存
				productService.cache(product.getId(),clientAccessLogService.getDetailPvCnt(null, product.getId()));
			}
		}
	}
}
