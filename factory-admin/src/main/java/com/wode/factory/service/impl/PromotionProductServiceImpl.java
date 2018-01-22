package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.ProductDao;
import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.mapper.PromotionDao;
import com.wode.factory.mapper.PromotionProductDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.service.InventoryService;
import com.wode.factory.service.PromotionProductService;
import com.wode.factory.vo.PromotionProductVo;

@Service("promotionProductService")
public class PromotionProductServiceImpl implements PromotionProductService {

	@Autowired
	ProductDao productDao;
	
	@Autowired
	PromotionDao promotionDao;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	PromotionProductDao promotionProductDao;
	
	@Autowired
	ProductSpecificationsDao productSpecificationsDao;
	
	/**
	 * 促销商品详细
	 * @author Liuc
	 * @param id 促销商品表ID
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Object> detail(Long id, Long userId) {
		return doCheckAndDetail(id, userId, true);
	}

	/**
	 * 对促销商品进行审核
	 * @author Liuc
	 * @param id
	 * @param userId
	 * @param result
	 * @param startDate
	 * @param startTime
	 * @return
	 */
	public Map<String, Object> doReview(Long id, Long userId, boolean result, String startTime) {
		// 验证并返回错误信息或促销商品详细
		Map<String, Object> map = doCheckAndDetail(id, userId, true);
		if((boolean) map.get("flg")) {
			
			// 当前系统时间
			Date systemDate = new Date(System.currentTimeMillis());
			
			// 当审核为通过时
			if(result) {
				// 验证时间是否为空
				if(StringUtils.isEmpty(startTime)) {
					map.put("flg", false);
					map.put("msg", "促销商品参与活动开始时间为空");
					return map;
				}
				
				// 组合参与活动开始日期时间字符串
				String startDateStr = map.get("promotionYMD") + " " + startTime;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date joinStart = new Date();
				try {
					joinStart = sdf.parse(startDateStr);
				} catch (ParseException e) {
					map.put("flg", false);
					map.put("msg", "参与活动开始日期格式错误");
					return map;
				}
				// 组合参与活动结束时间(参与活动开始日期的次日凌晨)
				Calendar cal = Calendar.getInstance();    
		        cal.setTime(joinStart);
		        cal.set(Calendar.HOUR_OF_DAY, 23);
		        cal.set(Calendar.MINUTE, 59);
		        cal.set(Calendar.SECOND, 59);
		        
		        Date joinEnd = cal.getTime(); 
		        cal = null;
				
				PromotionProduct promotionProduct = new PromotionProduct();
				// ID
				promotionProduct.setId(id);
				// 参与活动开始日期时间
				promotionProduct.setJoinStart(joinStart);
				// 参与活动结束日期时间
				promotionProduct.setJoinEnd(joinEnd);
				// 审核状态（2：通过）
				promotionProduct.setStatus(2);
				// 审核通过人
				promotionProduct.setReviewedUserId(userId);
				// 审核通过时间
				promotionProduct.setReviewedDate(systemDate);
				// 促销商品修改时间
				promotionProduct.setModifyDate(systemDate);
				// 更新数据库
				promotionProductDao.updateReviewed(promotionProduct);
				
				// 促销活动成功报名数 + 1
				PromotionProductVo promotionProductVo = (PromotionProductVo)map.get("promotionProductVo");
				Promotion promotion = new Promotion();
				// 促销活动ID
				promotion.setId(promotionProductVo.getPromotionId());
				// 促销活动成功参加活动的商品数量
				promotion.setJoinTotal(promotionProductVo.getJoinTotal() + 1);
				// 促销活动修改时间
				promotion.setModifyDate(systemDate);
				
				promotionDao.updateByPrimaryKeySelective(promotion);
				
				// 向redis中添加促销库存
				//inventoryService.putPromotionStock(promotionProductVo.getSkuId(), promotionProductVo.getId(), promotionProductVo.getPromotionId());
				
				// 返回最新的促销商品详细信息
				map = doCheckAndDetail(id, userId, false);
				return map;
			} else {
				// 当审核为不通过时
				PromotionProduct promotionProduct = new PromotionProduct();
				// ID
				promotionProduct.setId(id);
				// 审核状态（-1：不通过）
				promotionProduct.setStatus(-1);
				// 审核不通过人
				promotionProduct.setReviewedUserId(userId);
				// 审核不通过时间
				promotionProduct.setReviewedDate(systemDate);
				// 促销商品修改时间
				promotionProduct.setModifyDate(systemDate);
				// 更新数据库
				promotionProductDao.updateReviewed(promotionProduct);
				// 返回最新的促销商品详细信息
				map = doCheckAndDetail(id, userId, false);
				return map;
			}
		}
		return map;
	}
	
	/**
	 * 验证并返回错误信息或促销商品详细
	 * @author Liuc
	 * @param id
	 * @param userId
	 * @param checkFlg 是否需要判断当前促销商品的审核状态
	 * @return
	 */
	private Map<String, Object> doCheckAndDetail(Long id, Long userId, boolean checkFlg) {
		// 创建Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 返回正常结果
		map.put("flg", true);
		
		// 当id为空时
		if(id == null) {
			map.put("flg", false);
			map.put("msg", "促销商品ID为空");
			return map;
		}
		
		// 查询促销商品详细
		PromotionProduct promotionProduct = promotionProductDao.selectByPrimaryKey(id);
		// 当促销商品详细为空时
		if(promotionProduct == null) {
			map.put("flg", false);
			map.put("msg", "促销商品不存在");
			return map;
		} else if(promotionProduct.getStatus() != 0 && (promotionProduct.getStatus() == 1 && !userId.equals(promotionProduct.getReviewingUserId()))) {
			
			map.put("flg", false);
			map.put("msg", "促销商品" + (promotionProduct.getStatus() == 2?"已审核通过":promotionProduct.getStatus() == -1?"已审核不通过":promotionProduct.getStatus() == -2?"促销活动已退出":"正在审核中"));
			
			return map;
		} else if(new Date().after(promotionProduct.getJoinStart())){
			map.put("flg", false);
			map.put("msg", "该商品参与的促销活动已开始，不能操作");
		
		} 
		
		// 查询促销活动详细
		Promotion promotion = promotionDao.selectByPrimaryKey(promotionProduct.getPromotionId());
		// 当促销活动详细为空时
		if(promotion == null) {
			map.put("flg", false);
			map.put("msg", "该商品参与的促销活动不存在");
			return map;
		}
		
		// 查询商品详细
		Product product = productDao.selectById(promotionProduct.getProductId());
		// 当商品详细为空时
		if(product == null) {
			map.put("flg", false);
			map.put("msg", "商品不存在");
			return map;
		}
//		else if(product.getIsMarketable() != 1) {
//			map.put("flg", false);
//			map.put("msg", "商品" + (product.getIsMarketable() == 0?"暂存":product.getIsMarketable() == -1?"已下架 (售完下架)":product.getIsMarketable() == -2?"已下架（手动下架）":"已删除"));
//		} else if(product.getStatus() != 2) {
//			map.put("flg", false);
//			map.put("msg", "商品" + (product.getStatus() == 0?"未提交审核":product.getStatus() == 1?"待审核":"已审核不通过"));
//		}
		
		// 查询商品规格（商品SKU）详细
		ProductSpecifications productSpecifications = productSpecificationsDao.getById(promotionProduct.getSkuId());
		// 当商品详细为空时
		if(productSpecifications == null) {
			map.put("flg", false);
			map.put("msg", "商品SKU不存在");
			return map;
		} 
//		else if(productSpecifications.getIsDelete() != 0) {
//			map.put("flg", false);
//			map.put("msg", "商品SKU已删除");
//		}
		
		
		// 获取促销商品的年月日
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String promotionYMD = sdf1.format(promotionProduct.getJoinStart());
		map.put("promotionYMD", promotionYMD);
		
		
		// 返回审核的时的列表
		List<String> hourList = hourList(promotionYMD);
		if(hourList == null || hourList.size() < 1) {
			map.put("flg", false);
			map.put("msg", "促销商品已经超过可审核时间");
		} else {
			map.put("hourList", hourList);
		}
		
		
		// 整理返回促销商品详细
		PromotionProductVo promotionProductVo = new PromotionProductVo();
		// ID
		promotionProductVo.setId(promotionProduct.getId());
		// 促销活动商品的SKUID
		promotionProductVo.setSkuId(promotionProduct.getSkuId());
		// 促销活动ID
		promotionProductVo.setPromotionId(promotionProduct.getPromotionId());
		// 促销活动成功参加活动的商品数量
		promotionProductVo.setJoinTotal(promotion.getJoinTotal());
		// 促销商品名称
		promotionProductVo.setProductName(product.getName());
		// 促销商品上传时间（格式化后的时间 yyyy年MM月dd日 HH:mm:ss）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		promotionProductVo.setFormatCreateDate(sdf.format(promotionProduct.getCreateDate()));
		// 促销商品原价（页面显示用）
		promotionProductVo.setShowOriginalPrice(productSpecifications.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		// 促销商品活动价（页面显示用）
		promotionProductVo.setShowPrice(promotionProduct.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		// 促销商品活动库存
		promotionProductVo.setJoinQuantity(promotionProduct.getJoinQuantity());
		// pc端图片
		promotionProductVo.setBigImage(promotionProduct.getBigImage());
		// 移动端图片
		promotionProductVo.setSmallImage(promotionProduct.getSmallImage());
		// 参与活动开始时间
		promotionProductVo.setJoinStart(promotionProduct.getJoinStart());
		// 审核状态
		promotionProductVo.setStatus(promotionProduct.getStatus());
		
		// 返回促销商品详细
		map.put("promotionProductVo", promotionProductVo);
		
		// 当成功查询到信息详细时，将该条数据状态更新为审核中
//		if((boolean) map.get("flg") && checkFlg) {
//			// 当前系统时间
//			Date systemDate = new Date(System.currentTimeMillis());
//			// 促销商品实体
//			PromotionProduct promotionProduct1 = new PromotionProduct();
//			// 促销商品ID
//			promotionProduct1.setId(id);
//			// 促销商品状态（1：审核中）
//			promotionProduct1.setStatus(1);
//			// 促销商品审核中人
//			promotionProduct1.setReviewingUserId(userId);
//			// 促销商品中日期
//			promotionProduct1.setReviewingDate(systemDate);
//			// 促销商品修改日期
//			promotionProduct1.setModifyDate(systemDate);
//			// 更新数据库
//			promotionProductDao.updateUnreviewOrReviewing(promotionProduct1);
//		}
		
		// 验证是否有下一条没有审核过的数据
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("id", id);
		map0.put("userId", userId);
		map0.put("startDate", promotionYMD);
		List<PromotionProductVo> ppvList = promotionProductDao.selectUnreviewAndUserReviewingList(map0);
		map.put("nextFlg", true);
		if(ppvList == null || ppvList.size() < 1) {
			map.put("nextFlg", false);
		} else if(ppvList.size() == 1 && ppvList.get(0).getId().equals(id)){
			map.put("nextFlg", false);
		}
		
		
		return map;
	}
	
	@Override
	public PageInfo<PromotionProductVo> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PromotionProductVo> list = promotionProductDao.findList(params);
		return new PageInfo<PromotionProductVo>(list);
	}

	/**
	 * 审核的时
	 * @author Liuc
	 * @param reviewDateS 审核的日期（格式：yyyy-MM-dd）
	 * @return
	 */
	public List<String> hourList(String reviewDateS) {
		// 格式：年月日
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		// 格式：时
		SimpleDateFormat sdfHMS = new SimpleDateFormat("HH");
		
		// 系统日期
		Date systemDateD = new Date();
		String systemDateS = sdfYMD.format(systemDateD);
		
		// 返回时的List
		List<String> list = new ArrayList<String>();
		
		try {
			// 格式：年月日
			Date reviewDateDD = sdfYMD.parse(reviewDateS);
			Date systemDateDD = sdfYMD.parse(systemDateS);
			
			if(reviewDateDD.equals(systemDateDD)) {
				
				// 取系统的时
				String systemTimeS = sdfHMS.format(systemDateD);
				// 把系统的时转换成数值类型
				int systemTimeI = Integer.parseInt(systemTimeS);
				// 当前时间点在22点及22点以后时，该天的促销商品不可审核
				if(systemTimeI >= 22) {
					return list;
				}
				
				// 当时为奇数时 + 1；当时为偶数时 + 2
				if(systemTimeI%2 != 0) {
					systemTimeI += 1;
				} else {
					systemTimeI += 2;
				}
				
				// 循环出剩下可用的时
				for(int i = systemTimeI; i <= 22; i++) {
					if(i%2 == 0) {
						list.add((String.valueOf(i).length() == 1?"0"+String.valueOf(i):String.valueOf(i)));
					}
				}
			} else if(reviewDateDD.after(systemDateDD)) {
				// 循环出全天的时
				for(int i = 0; i <= 22; i++) {
					if(i%2 == 0) {
						list.add((String.valueOf(i).length() == 1?"0"+String.valueOf(i):String.valueOf(i)));
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageInfo<PromotionProductVo> findListSet(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<PromotionProductVo> list = promotionProductDao.findListSet(params);
		for(PromotionProductVo ppv:list){
			int i = ppv.getJoinStart().compareTo(new Date());
			if(i > 0){
				ppv.setUpdateAble(true);
			}
		}
		return new PageInfo<PromotionProductVo>(list);
	}

	/**
	 * 根据不同条件查询促销商品列表
	 * @author Liuc
	 */
	@Override
	public List<PromotionProduct> promotionProductList(PromotionProduct promotionProduct) {
		return promotionProductDao.selectListSelective(promotionProduct);
	}

	/**
	 * 促销商品未审核与审核中之间的状态转换
	 * @author Liuc
	 */
	@Override
	public int updateUnreviewOrReviewing(PromotionProduct record) {
		return promotionProductDao.updateUnreviewOrReviewing(record);
	}

	/**
	 * 审核下一条数据的详细
	 * @author Liuc
	 * @param userId
	 */
	@Override
	public Map<String, Object> nextDetail(Long id, Long userId) {
		// 返回Map
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验当前数据是否还有下一条需要审核的数据
		Map<String, Object> map0 = doCheckAndDetail(id, userId, false);
		// 当还有需要审核的数据时
		if((boolean) map0.get("nextFlg")) {
			// 创建查询条件
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("id", id);
			map1.put("userId", userId);
			map1.put("startDate", map0.get("promotionYMD"));
			
			// 验证是否有下一条没有审核过的数据
			List<PromotionProductVo> ppvList = promotionProductDao.selectUnreviewAndUserReviewingList(map1);
			if(ppvList != null && ppvList.size() > 0) {
				if(ppvList.size() == 1 && !ppvList.get(0).getId().equals(id)) {
					return doCheckAndDetail(ppvList.get(0).getId(), userId, true);
				} else if(ppvList.size() > 1){
					Long nextId = null;
					for(int i = 0; i < ppvList.size(); i++) {
						if((i < ppvList.size() - 1) && ppvList.get(i).getId().equals(id)) {
							nextId = ppvList.get(i+1).getId();
							break;
						} else {
							nextId = ppvList.get(0).getId();
						}
					}
					return doCheckAndDetail(nextId, userId, true);
				}
			}
		}
		map.put("flg", false);
		map.put("msg", "下一条数据不存在");
		return map;
	}

	/**
	 * 修改促销商品状态
	 */
	@Override
	public int updateStatus(PromotionProduct proProduct) {
		// 促销活动实体
		Promotion record = new Promotion();
		// 促销活动ID
		record.setId(proProduct.getPromotionId());
		// 促销商品状态
		record.setStatus(proProduct.getStatus());
		// 促销活动修改时间
		record.setModifyDate(proProduct.getModifyDate());
		// 更新促销活动
		promotionDao.updateJoinTotal(record);
		
		// 获取促销商品
		PromotionProduct promotionProduct = promotionProductDao.selectByPrimaryKey(proProduct.getId());
		// 当获取促销商品不为空时
		if(promotionProduct != null) {
			if(proProduct.getStatus() == 2) {
				// 向redis中添加促销库存
				inventoryService.putPromotionStock(promotionProduct.getSkuId(), promotionProduct.getId(), promotionProduct.getPromotionId());
			} else if(proProduct.getStatus() == -1) {
				// 删除redis中该促销商品库存
				inventoryService.delPromotionStock(promotionProduct.getSkuId(), promotionProduct.getId(), promotionProduct.getPromotionId());
			}
		}
		// 更新促销商品
		return promotionProductDao.updateByPrimaryKeySelective(proProduct);
	}

	@Override
	public List<PromotionProductVo> findByCreateDate(Map<String, Object> params) {
		return promotionProductDao.findByCreateDate(params);
	}

	@Override
	public List<PromotionProduct> findOverduePromotionProduct(
			PromotionProduct record) {
		// TODO Auto-generated method stub
		return this.promotionProductDao.findOverduePromotionProduct(record);
	}
}
