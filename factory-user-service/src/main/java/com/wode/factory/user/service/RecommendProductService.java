package com.wode.factory.user.service;

import java.util.List;

import com.wode.factory.model.Product;
import com.wode.factory.user.vo.SkuVo;
/**
 * 推荐商品查询
 * @author 谢海生
 *
 */
public interface RecommendProductService {
	/**
	 * 按分类推荐的商品
	 * @param cateid
	 * @return
	 */
	public List<Product> findRecommendByCate(Long cateid) ;
	
	/**
	 * 热卖商品
	 * @param cateid
	 * @return
	 */
	public List<Product> findHotSellByCate(Long cateid) ;
	
	public List<Product> findHotSell() ;
	
	public List<SkuVo> findHotSell1() ;
	
	/**
	 * 随机推荐
	 * @return
	 */
	public List<Product> random();
	
}
