package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ApprProduct;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ApprProductDao extends  FactoryBaseDao<ApprProduct> {
	/**
	 * @param query 查询条件存储对象, 目前紧支持时间参数, startDate(开始时间) endDate(结束时间)
	 * @return
	 */
	//public void updateStatus(Long productId);
	//public void saveOrUpdate(ApprProduct entity);
	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<ApprProduct> findList(Map<String, Object> params);
	
	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param pageTypeId
	 * @return
	 */
	//public ApprProductVO getById(Long productId);
	//public ApprProductVO getById(Long id);
	public ApprProduct getById(Long id);
	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param id
	 * @return
	 */
	public ApprProduct selectById(Long productId);
	
	/**
	 * 功能说明：
	 *
	 * @param pojo
	 */
	public Integer checkByid(ApprProduct map);
	/**
	 * 功能说明：
	 *
	 * @param pojo
	 */
	public Integer updateByBusiness(ApprProduct map);
	
	/**
	 * 
	 * 功能说明：查询热卖商品
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 * @param parPro
	 * @return
	 */
	public List<ApprProduct> findRecormendHotPro(ApprProduct parPro);
	/**
	 * 更新总库存
	 * @param params
	 */
	public void updateAllNum(Map<String, Object> params);
	/**
	 * 更新总库存
	 * @param params
	 */
	public void unlockExchangeProduct(ApprProduct map);
	/**
	 * 更新总库存
	 * @param params
	 */
	public void changeBrand(Map<String, Object> params);
	
	
	public Integer getCountBySupplier(Long supplierId);

	public Integer getCountBySupplierDate(Map<String,Object> map);
	//把临时表的数据插入到正式表中
	void insertProduct(ApprProduct appr);
	////把临时表的数据更新到正式表中
	void updateProduct(ApprProduct appr);
	
	
	
	//更新运费模板
	void changShipping(Map<String, Object> map);
	//更新产品参数
	void changProductParameter(Map<String, Object> map);
	//更新规格值
	void changProductSpecificationValue(Map<String, Object> map);
	//更新清单
	void changProductDetailList(Map<String, Object> map);
	
	//更新产品属性
	void changAttribute(Map<String, Object> map);
	
    //更新sku
	void changProductSpecifications(Map<String, Object> map);
		
	public void insert(ApprProduct product);
	//根据productId和status判断临时表中是否有正式表要操作的数据
	public ApprProduct selectProductIdAndStatus(Long productId);

	void updQuestionnare(Map<String, Object> map);
	//根据创建时间和状态查询所有数据
	public List<ApprProduct> findAllByCreateDate(Map<String, Object> map);
    //更新阶梯价
	public void changProductLadders(Map<String, Object> map);
    //更新sku
	void changSkuImages(Map<String, Object> map);
    //更新sku
	void changSkuInventorys(Map<String, Object> map);
    //更新sku
	void changSkuId(Map<String, Object> map);

	public void deleteBySupplierId(Long supplierId);
}
