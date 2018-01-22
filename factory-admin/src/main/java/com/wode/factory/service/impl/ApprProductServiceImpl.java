package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.NumberUtil;
import com.wode.factory.mapper.ApprProductDao;
import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.model.ApprProduct;
import com.wode.factory.service.ApprProductService;
/**
 * Created by zoln on 2015/7/24.
 */
@Service("apprProductService")
public class ApprProductServiceImpl extends FactoryEntityServiceImpl<ApprProduct> implements ApprProductService {
	
	@Autowired
	ApprProductDao dao;
//	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
//	private Set<String> interfaceUrl=new HashSet<String>();

    @Override
	public List<ApprProduct> find(Map<String, Object> params) {
		List<ApprProduct> list = dao.findList(params);
		return list;
	}

   //根据商品的上架信息和状态信息，查出要审核的商品列表

	@Override
	public PageInfo<ApprProduct> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ApprProduct> list = dao.findList(params);
		return new PageInfo<ApprProduct>(list);
	}

	@Override
	public ApprProduct getById(Long productId) {
		return dao.getById(productId);
		 
	}
	
	
	@Override
	public List<ApprProduct> findRecormendHotPro(ApprProduct parPro) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void updateAllNum(Long productId, int total) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", productId);
		params.put("allnum", total);
		dao.updateAllNum(params);
		
	}

	@Override
	public void changeBrand(Long supplierId, Long shopId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		params.put("shopId", shopId);
		dao.changeBrand(params);
		
	}

	// 把临时表的数据插入到正式表中
	@Override
	public void insertProduct(ApprProduct product) {
		dao.insertProduct(product);
		
	}
////把临时表的数据更新到正式表中
	@Override
	public void updateProduct(ApprProduct product) {
		dao.updateProduct(product);
		
	}
	@Override
	public ApprProduct selectById(Long productId) {
		return dao.selectById(productId);
	}
	@Override
	public void changShipping(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		
		dao.changShipping(map);
		
	}
	@Override
	public void changProductParameter(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		dao.changProductParameter(map);
		
	}
	@Override
	public void changProductSpecificationValue(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		dao.changProductSpecificationValue(map);
	}
	@Override
	public void changProductDetailList(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		dao.changProductDetailList(map);
	}
	@Override
	public void changAttribute(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		dao.changAttribute(map);
	}
	@Override
	public void changProductSpecifications(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		dao.changProductSpecifications(map);
	}
	
	@Override
	public void changProductLadders(Long productId,Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("productId", productId);
		dao.changProductLadders(map);
	}
	
	@Override
	public void insert(ApprProduct product) {
		dao.insert(product);
	}
	@Override
	public ApprProduct selectProductIdAndStatus(Long productId) {
		
		return dao.selectProductIdAndStatus(productId);
	}
	
	@Override
	public FactoryBaseDao<ApprProduct> getDao() {
		return getDao();
	}


	@Override
	public Long getId(ApprProduct entity) {
		return entity.getId();
	}

	@Override
	public void setId(ApprProduct entity, Long id) {
		entity.setId(id);
	}

	@Override
	public void updQuestionnare(Long id,int status) {
		if(NumberUtil.isGreaterZero(id)) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("status", status);
			
			dao.updQuestionnare(map);
		}
	}

	@Override
	public void changSkuImages(Long setId, Long whereId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newId", setId);
		map.put("oldId", whereId);
		dao.changSkuImages(map);
	}

	@Override
	public void changSkuInventorys(Long setId, Long whereId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newId", setId);
		map.put("oldId", whereId);
		dao.changSkuInventorys(map);
	}

	@Override
	public void changSkuId(Long setId, Long whereId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newId", setId);
		map.put("oldId", whereId);
		dao.changSkuId(map);
	}
}
