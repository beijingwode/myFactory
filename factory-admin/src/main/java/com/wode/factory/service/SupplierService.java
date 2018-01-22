package com.wode.factory.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Attachment;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.vo.SupplierVo;
import com.wode.tongji.model.AccountingLog;

/**
 *
 */
public interface SupplierService {

	/**
	 * @param query 查询条件存储对象, 目前紧支持时间参数, startDate(开始时间) endDate(结束时间)
	 * @return
	 */
	PageInfo<Supplier> getPage(Map<String, Object> query);
	PageInfo<SupplierVo> findSupplierCount(Map<String, Object> query);
	
	PageInfo<SupplierVo> findEmploeeCnt(Map<String, Object> params);
	/**
	 * 获取商家详情
	 *
	 * @param id
	 * @return
	 */
	Supplier getSupplierDetailWithItems(Long id);

	/**
	 * 获取商家分类列表
	 *
	 * @param id
	 * @return
	 */
	List<ProductCategory> getProductCategoryListBySupplierId(Long id,Long shopId);

	/**
	 * 获取商家资质列表
	 *
	 * @param id
	 * @return
	 */
	List<Attachment> getAttachmentListBySupplierId(Long id,Long shopId);

	/**
	 * 获取商家品牌列表
	 *
	 * @param id
	 * @return
	 */
	List<ProductBrand> getProductBrandListBySupplierId(Long id,Long shopId);

	/**
	 * 获取品牌
	 *
	 * @param id
	 * @return
	 */
	ProductBrand getProductBrandById(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<ProductAttribute> findProductAttributeByProductid(Long id);
	/**
	 * 修改商家审核状态
	 * @param Supplier
	 * @return
	 */
	void updateSupplierStatus(Supplier pojo);
	
	/**修改企业id
	 * @param pojo
	 */
	void updateSupplierEnterpriseId(Supplier pojo);

	/**
	 * 记录审核表
	 *
	 * @param CheckOpinion
	 * @return
	 */
	void saveCheckOpinion(CheckOpinion co);

	/**
	 * 获取历史审核列表
	 *
	 * @param id
	 * @return
	 */
	List<CheckOpinion> getCheckOpinionListBySupplierId(Long id);
	List<CheckOpinion> getAllCheckOpinionBySupplierId(Long supplierId);
	
	Supplier findByid(Long id);
	Supplier findByEmpId(Long id);
	
	/**
	 * 根据类型id获取父节点对象
	 * @param id
	 * @return
	 */
	ProductCategory getParentCategoryByid(Long id);

	/**
	 * 
	 * 功能说明：
	 * 日期:	2015年11月2日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @param user 
	 * @return
	 */
	int updateSel(Map<String, Object> params,AccountingLog sysUser);

	/**
	 * 获取佣金比例
	 *
	 * @param id
	 * @return
	 */
	BigDecimal getCommissionRate(Long id);

	/**
	 * 更新佣金比例
	 *
	 * @param commissionRate 佣金比例
	 * @param id supplier 主键ID
	 */
	void updateCommissionRate(BigDecimal commissionRate, Long id);
	
	/**获取商家用户
	 * @param supplierId
	 * @return
	 */
	public UserFactory getSupplierUser(Long supplierId);
	void setManager(Supplier pojo);
	
	List<Supplier> findAll();
	void updateFirmLogo(Supplier supplier);
	Long boundQRcode(Long id);
}
