package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.ApprProduct;

/**
 *
 */
public interface ApprProductService extends FactoryEntityService<ApprProduct> {

	/**
	 * @param query 查询条件存储对象, 目前紧支持时间参数, startDate(开始时间) endDate(结束时间)
	 * @return
	 */
	/*public PageInfo<ApprProduct> findApprProduct(Map<String, Object> params);
	public List<ApprProduct> findApprProductEmpty(Map<String, Object> map);
	*/
	//用于更新临时表
	//public void updateStatus(Long productId);
	//public void saveOrUpdate(ApprProduct entity); 
	/**
	 * 
	 * 功能说明：查询商品列表（带分页）
	 * @param params
	 * @return
	 */
	PageInfo<ApprProduct> findList(Map<String, Object> params);

	/**
	 * 
	 * 功能说明：商品详情
	 * @param id
	 * @return
	 */
	//ApprProductVO getById(Long productId);
	ApprProduct getById(Long productId);
	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param id
	 * @return
	 */
	public ApprProduct selectById(Long productId);
	
	/**
	 * 
	 * 功能说明：查询热卖商品
	 * 日期:	2015年8月18日
	 * 开发者:宋艳垒
	 *
	 * @param parPro
	 * @return
	 */
	List<ApprProduct> findRecormendHotPro(ApprProduct parPro);

	List<ApprProduct> find(Map<String, Object> params);
	/**
	 * 更新商品总库存
	 * @param productId
	 * @param total
	 */
	void updateAllNum(Long productId, int total);

	public void changeBrand(Long supplierId,Long shopId);
	
	//把临时表的数据插入到正式表中
	void insertProduct(ApprProduct appr);
	////把临时表的数据更新到正式表中
	void updateProduct(ApprProduct appr);
	
	
	
	//更新运费模板
	void changShipping(Long productId,Long id);
	//更新产品参数
	void changProductParameter(Long productId,Long id);
	//更新规格值
	void changProductSpecificationValue(Long productId,Long id);
	//更新清单
	void changProductDetailList(Long productId,Long id);
	
	//更新产品属性
	void changAttribute(Long productId,Long id);
	
    //更新sku
	void changProductSpecifications(Long productId,Long id);
	
	public void insert(ApprProduct product);
	//根据productId和status判断临时表中是否有正式表要操作的数据
	public ApprProduct selectProductIdAndStatus(Long productId);

	void updQuestionnare(Long id,int status);
    //更新阶梯价
	void changProductLadders(Long productId, Long id);
    //更新sku主图
	void changSkuImages(Long setId, Long whereId);
    //更新sku库存
	void changSkuInventorys(Long setId, Long whereId);
    //更新skuId
	void changSkuId(Long setId, Long whereId);
}
