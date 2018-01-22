/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.user.dao.UserExchangeFavoritesDao;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.UserExchangeFavoritesService;
import com.wode.search.WodeResult;
import com.wode.search.WodeSearchManager;
import com.wode.search.WodeSearcher;

@Service("userExchangeFavoritesService")
public class UserExchangeFavoritesServiceImpl extends FactoryEntityServiceImpl<UserExchangeFavorites> implements  UserExchangeFavoritesService{
	@Autowired
	private Dao nutzDao;
	@Autowired
	private UserExchangeFavoritesDao dao;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
    @Autowired
    private WodeSearchManager wsm;

	@Override
	public UserExchangeFavoritesDao getDao() {
		return dao;
	}
	@Override
	public Long getId(UserExchangeFavorites entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserExchangeFavorites entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}
	
	@Override
	public QueryResult getSelectable(Integer page, Integer pageSize, Long userId) {

		
		BigDecimal sum = exchangeSuborderService.getOrderAmount(userId);
		
		List<HashMap<String, Object>> searchs =this.searchList(userId, BigDecimal.ZERO, sum);
		Pager pager = nutzDao.createPager(page, pageSize);
		pager.setRecordCount(searchs.size());
		int toIndex=page*pageSize;
		if(toIndex>searchs.size()) {
			toIndex=searchs.size();
		}
		int fromIndex = (page-1)*pageSize;
		while(fromIndex >= toIndex && fromIndex>0) {
			page--;
			fromIndex = (page-1)*pageSize;
		}
		// 查询购物团列表
		return new QueryResult(searchs.subList(fromIndex, toIndex), pager);
	}

	@Override
	public ActResult<String> addFavorites(Long userId, Long productId) {
		// TODO Auto-generated method stub
		Map<String, Object> map= wsm.getIndexById(productId);
		if(map==null) {
			return ActResult.fail("所选商品不存在，请刷新后重试");
		}		

		UserExchangeFavorites q = new UserExchangeFavorites();
		q.setUserId(userId);
		q.setProductId(productId);
		List<UserExchangeFavorites> result= selectByModel(q);
		if(result!=null && !result.isEmpty()) {
			return ActResult.successSetMsg("商品已添加，请刷新当前列表");
		}
		
		Date now = new Date();
		this.makeNewEntity(userId, map, now);
		return ActResult.successSetMsg("添加成功");
	}
	@Override
	public List<UserExchangeFavorites> autoAddFavorites(Long userId, ExchangeSuborder subOrder) {
		//if(!"1".equals(subOrder.getFreeSwap())) return 0;
		
		// 计算区间
		BigDecimal sum = exchangeSuborderService.getOrderAmount(userId);
		BigDecimal from = subOrder.getTotalProduct().subtract(subOrder.getTotalShipping());
		from = sum.subtract(from);

		List<HashMap<String, Object>> searchs =this.searchList(userId, from, sum);
		List<UserExchangeFavorites> uef = new ArrayList<UserExchangeFavorites>();
		for (HashMap<String, Object> hashMap : searchs) {
			UserExchangeFavorites q =new UserExchangeFavorites();
			q.setUserId(userId);
			q.setProductId(NumberUtil.toLong(hashMap.get("productId")));
			Long supplierId = NumberUtil.toLong(hashMap.get("supplierId"));
			q.setSupplierId(supplierId);
			q.setProductName((String)hashMap.get("name"));
			q.setShopName((String)hashMap.get("shopName"));
			q.setSupplierId(NumberUtil.toLong(hashMap.get("minSkuId")));
			q.setSalePrice(NumberUtil.toBigDecimal(hashMap.get("salePrice")));
			q.setStock(NumberUtil.toInteger(hashMap.get("stock")));
			q.setImagePath((String)hashMap.get("image"));
			if(SUPPER_SUPPLIER_ID.equals(supplierId)) {
				q.setOrders(500);	//我的圈 500
			} else {
				q.setOrders(100);	//我的圈 500
			}
			uef.add(q);
		}
		
		return uef;
	}
	
	/**
	 * 根据金额检索出合适换领商品。并排除调剂清单中的商品
	 * @param userId
	 * @param from
	 * @param to
	 * @return
	 */
	private List<HashMap<String, Object>> searchList(Long userId,BigDecimal from,BigDecimal to) {
		WodeSearcher searcher = wsm.getSearcher();
		searcher.setFetchSource(UserExchangeFavoritesService.fecthFields, null);
		searcher.setPageSize(200);
		//固定2个属性
		String[] fields = new String[]{"品牌", "价格"};
		WodeResult resulta = searcher.search("salePrice=-"+to.doubleValue()+"&saleKbn=2&sort=createDate_1", fields, false,false,false);

		
		// 获取已选商品
		UserExchangeFavorites q = new UserExchangeFavorites();
		q.setUserId(userId);
		List<UserExchangeFavorites> result= selectByModel(q);
		
		// 删除已选项
		List<HashMap<String, Object>> searchs= resulta.getHits();
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -1);
		Date lastDelete = now.getTime();
		for (UserExchangeFavorites favorites : result) {
			for(int i=searchs.size()-1;i>-1;i--) {
				HashMap<String, Object> hashMap=searchs.get(i);
				if(favorites.getProductId().equals(NumberUtil.toLong(hashMap.get("productId")))) {
					searchs.remove(i);
					break;
				}
			}
			if(lastDelete.compareTo(favorites.getCreateTime())<0) {
				lastDelete=favorites.getCreateTime();
			}
		}
		
		if(NumberUtil.isGreaterZero(from)) {
			for(int i=searchs.size()-1;i>-1;i--) {
				HashMap<String, Object> hashMap=searchs.get(i);
				if(from.compareTo(NumberUtil.toBigDecimal(hashMap.get("salePrice")))>0  
						&& lastDelete.compareTo(new Date(NumberUtil.toLong(hashMap.get("createDate"))))>0) {
					searchs.remove(i);	
				}
			}
		}
		//搜索结果库存为0，移除
		for(int i=searchs.size()-1;i>-1;i--) {
			HashMap<String, Object> hashMap=searchs.get(i);
			if(NumberUtil.toInteger(hashMap.get("allStock"))<=0) {
				searchs.remove(i);	
			}
		}
		
		return searchs;
	}
	
	/**
	 * 添加至调剂清单
	 * @param userId
	 * @param indexMap
	 * @param now
	 * @return
	 */
	private UserExchangeFavorites makeNewEntity(Long userId, Map<String, Object> indexMap, Date now) {
		UserExchangeFavorites q =new UserExchangeFavorites();
		q.setUserId(userId);
		q.setProductId(NumberUtil.toLong(indexMap.get("productId")));
		Long supplierId = NumberUtil.toLong(indexMap.get("supplierId"));
		q.setSupplierId(supplierId);
		q.setProductName((String)indexMap.get("name"));
		q.setShopName((String)indexMap.get("shopName"));
		q.setSupplierId(NumberUtil.toLong(indexMap.get("minSkuId")));
		q.setSalePrice(NumberUtil.toBigDecimal(indexMap.get("salePrice")));
		q.setStock(NumberUtil.toInteger(indexMap.get("stock")));
		q.setImagePath((String)indexMap.get("image"));
		//q.setItemValues((String)indexMap.get("itemValues"));
		q.setCreateTime(now);
		if(SUPPER_SUPPLIER_ID.equals(supplierId)) {
			q.setOrders(500);	//我的圈 500
		} else {
			q.setOrders(100);	//我的圈 500
		}
		this.save(q);
		return q;
	}
	@Override
	public List<HashMap<String, Object>> getMaySelectable(Long id, BigDecimal productPrice, BigDecimal sum) {
		List<HashMap<String,Object>> searchList = this.searchList(id, productPrice, sum);
		List<HashMap<String,Object>> result  = null;
		if(searchList.size()>=4) {
			result =  new ArrayList<HashMap<String, Object>>();
			result.add(0, searchList.get(0));
			result.add(1, searchList.get(1));
			result.add(2, searchList.get(2));
			result.add(3, searchList.get(3));
		}else {
			result = searchList;
		}
		return result;
	}
	
}