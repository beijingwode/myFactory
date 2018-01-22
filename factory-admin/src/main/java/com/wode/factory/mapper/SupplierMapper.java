package com.wode.factory.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.Attachment;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.InnerUserLog;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.vo.SupplierVo;

/**
 * Created by zoln on 2015/7/24.
 */
public interface SupplierMapper {

	List<Supplier> findSupplierBySelective(Map<String, Object> map);
	List<SupplierVo> findSupplierVo(Map<String, Object> map);
	List<SupplierVo> findSupplierCount(Map<String, Object> map);
	List<SupplierVo> findEmploeeCnt(Map<String, Object> map);

	Supplier getSupplierByIdWithItems(Long supplierId);

	List<ProductCategory> getProductCategoryListBySupplierId(Long id,Long shopId);

	List<Attachment> getAttachmentListBySupplierId(Long id,Long shopId);

	List<ProductBrand> getProductBrandListBySupplierId(Long id,Long shopId);

	ProductBrand getProductBrandById(Long id);

	void updateSupplierStatus(Supplier pojo);
	/**
	 * 修改企业id
	 * @param pojo
	 */
	void updateSupplierEnterpriseId(Supplier pojo);
	/**
	 * 修改企业id
	 * @param pojo
	 */
	void setManager(Supplier pojo);

	void saveLog(InnerUserLog log);

	void saveCheckOpinion(CheckOpinion co);

	List<CheckOpinion> getCheckOpinionListBySupplierId(Long id);
	List<CheckOpinion> getAllCheckOpinionBySupplierId(Long supplierId);
	
	Supplier findByid(Long id);
	Supplier findByEmpId(Long id);
	
	List<ProductAttribute> findProductAttributeByProductid(Long id);

	int updateSel(Map<String, Object> params);

	BigDecimal getCommissionRate(Long id);

	int updateCommissionRate(Supplier supplier);
	List<Supplier> findAllSupplier();
	
	SupplierVo getBrandOwnerBySupplierId(Long supplierId);
	void updateFirmLogo(Supplier supplier);
	void deleteBySupplierId(Long supplierId);
}
