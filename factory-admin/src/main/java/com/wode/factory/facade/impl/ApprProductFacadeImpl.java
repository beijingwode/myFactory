package com.wode.factory.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtilEx;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.facade.ApprProductFacade;
import com.wode.factory.mapper.ApprProductDao;
import com.wode.factory.mapper.ProductSpecificationsDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.service.ApprProductService;
import com.wode.factory.service.ClientAccessLogService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;

@Service
@Transactional("tm_factory")
public class ApprProductFacadeImpl implements ApprProductFacade {

	@Autowired
	private ApprProductService apprProductService;
	@Resource
	private ProductService productService;
	@Resource
	private DBUtils dbUtils;
	@Autowired
    private ApprProductDao apprProductDao;
	@Autowired
	private RedisUtilEx redisUtilEx;
	@Autowired
	private ClientAccessLogService clientAccessLogService;
	@Autowired
	private ProductSpecificationsDao productSpecificationsDao;
	@Resource
	private ProductLadderService productLadderService;
	
	@Override
	@Transactional
	public Integer check(ApprProduct pro) {
		if (pro.getStatus() == 2) {
			pro.setIsMarketable(1);
		} else {
			pro.setIsMarketable(0);
		}
		Integer ret = apprProductDao.checkByid(pro);
		//这段是把临时表的数据更新到正式表中
		if(pro.getStatus()==2){
			this.apprToProduct(pro);
			// 试用
			if(pro.getSaleKbn() != null && pro.getSaleKbn()==5) {
				apprProductService.updQuestionnare(pro.getQuestionnaireId(),2);
			}
			productService.cache(pro.getProductId(),clientAccessLogService.getDetailPvCnt(null, pro.getProductId()));
	    }
		
		return ret;
	}
	
	@Transactional
	private void apprToProduct(ApprProduct appr) {
		// 保存商品信息

		Product old = productService.selectById(appr.getProductId());
		if (StringUtils.isEmpty(old)) {
			appr.setCreateDate(new Date());			//时间以审核为准
			apprProductService.insertProduct(appr);
			// BeanUtils.copyProperties(old, appr);
			apprProductService.changProductSpecifications(appr.getProductId(), appr.getId());
			apprProductService.changProductSpecificationValue(appr.getProductId(), appr.getId());
			apprProductService.changAttribute(appr.getProductId(), appr.getId());
			apprProductService.changProductDetailList(appr.getProductId(), appr.getId());
			apprProductService.changShipping(appr.getProductId(), appr.getId());
			apprProductService.changProductParameter(appr.getProductId(), appr.getId());
			apprProductService.changProductLadders(appr.getProductId(), appr.getId());
		} else {
			//清空缓存
			redisUtilEx.delKey("findByMinpriceCache_["+old.getId()+","+old.getShowPrice()+"]");
			// 更新问卷
			if(!NumberUtil.equals(old.getQuestionnaireId(), appr.getQuestionnaireId())) {
				apprProductService.updQuestionnare(old.getQuestionnaireId(),3);
			}
			
			if(old.getLocked()==1){ //判断如果在售商品时锁定状态，审核通过后还是锁定状态
				appr.setLocked(1); 
			}
			if(old.getSortNum()!=null){//如果在售商品设置了排序规则，审核通过后保持此排序规则
				appr.setSortNum(old.getSortNum()); 
			}
			//// 商品信息更新
			apprProductService.updateProduct(appr);

			//临时表id=商品id 无需做copy
			if(appr.getProductId().equals(appr.getId())) return;

			Long temp = dbUtils.CreateID();
			// 获取新旧sku 列表
			List<ProductSpecifications> new_skus = productSpecificationsDao.getlistByProductid(appr.getId());
			List<ProductSpecifications> old_skus = productSpecificationsDao.getlistByProductid(appr.getProductId());
			Map<Long,Long> keys=new HashMap<Long, Long>();
			for (ProductSpecifications n : new_skus) {
				for (ProductSpecifications o : old_skus) {
					if(n.getItemValues().equals(o.getItemValues())) {
						keys.put(n.getId(), o.getId());
					}
				}
			}
			
			// 更新sku规格
			apprProductService.changProductSpecifications(temp, appr.getProductId());// 旧版更新成临时
			apprProductService.changProductSpecifications(appr.getProductId(), appr.getId());// 新版更新到商品
			apprProductService.changProductSpecifications(appr.getId(), temp); // 临时更新到历史

			// 更新规格值
			apprProductService.changProductSpecificationValue(temp, appr.getProductId());
			apprProductService.changProductSpecificationValue(appr.getProductId(), appr.getId());
			apprProductService.changProductSpecificationValue(appr.getId(), temp);

			// 更新产品属性
			apprProductService.changAttribute(temp, appr.getProductId());
			apprProductService.changAttribute(appr.getProductId(), appr.getId());
			apprProductService.changAttribute(appr.getId(), temp);

			// 更新清单
			apprProductService.changProductDetailList(temp, appr.getProductId());
			apprProductService.changProductDetailList(appr.getProductId(), appr.getId());
			apprProductService.changProductDetailList(appr.getId(), temp);
			// 更新运费模板
			apprProductService.changShipping(temp, appr.getProductId());
			apprProductService.changShipping(appr.getProductId(), appr.getId());
			apprProductService.changShipping(appr.getId(), temp);
			// 更新产品参数
			apprProductService.changProductParameter(temp, appr.getProductId());
			apprProductService.changProductParameter(appr.getProductId(), appr.getId());
			apprProductService.changProductParameter(appr.getId(), temp);
			// 更新阶梯价格
			apprProductService.changProductLadders(temp, appr.getProductId());
			apprProductService.changProductLadders(appr.getProductId(), appr.getId());
			apprProductService.changProductLadders(appr.getId(), temp);
			
			// 保持原有skuId
			for (Long n : keys.keySet()) {
				Long tempId = dbUtils.CreateID();
				//更新sku主图
				apprProductService.changSkuImages(tempId, keys.get(n));			// 旧版更新成临时
				apprProductService.changSkuImages(keys.get(n), n);				// 新版更新到正式
				apprProductService.changSkuImages(n, tempId);					// 临时更新到历史
				
				//更新sku库存
				apprProductService.changSkuInventorys(tempId, keys.get(n));			// 旧版更新成临时
				apprProductService.changSkuInventorys(keys.get(n), n);				// 新版更新到正式
				apprProductService.changSkuInventorys(n, tempId);					// 临时更新到历史
				
				//更新skuId
				apprProductService.changSkuId(tempId, keys.get(n));			// 旧版更新成临时
				apprProductService.changSkuId(keys.get(n), n);				// 新版更新到正式
				apprProductService.changSkuId(n, tempId);					// 临时更新到历史
			}
			
			// 阶梯价格中的skuid
			if(keys.size()>0) {
				List<ProductLadder> ladders=  productLadderService.getByProductId(appr.getProductId());
				for (ProductLadder productLadder : ladders) {
					String ids = ","+productLadder.getSkuids();
					for (Long n : keys.keySet()) {
						ids=ids.replace(","+n+",", ","+keys.get(n)+",");
					}
					productLadder.setSkuids(ids.substring(1));
					productLadderService.update(productLadder);
				}
			}
		}
	}
}

