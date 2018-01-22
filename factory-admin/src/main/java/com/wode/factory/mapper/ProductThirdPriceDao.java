package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductThirdPrice;
import com.wode.factory.vo.ProductThirdPriceVO;

/**
 * Created by zoln on 2015/7/24.
 */
public interface ProductThirdPriceDao extends  EntityDao<ProductThirdPrice,Long> {
	//保存
	void insert(ProductThirdPrice ptp);
	//通过产品id获取第三方价格
	public List<ProductThirdPrice> selectyByProductId(Long productId);
	/**
	 * 功能说明：查询规格信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 */
	public List<ProductThirdPrice> select5DaysAgo(Map<String,String> type);
	/**
	 * 
	 * 功能说明：查询属性列表
	 *
	 * @param params
	 * @return
	 */
	public List<ProductThirdPriceVO> findNotifyList(Map<String, Object> params);
	void deleteBySupplierId(Long supplierId);
	void deleteQuestionnaireQuestionBySupplierId(Long supplierId);
	void deleteQuestionnaireOptionBySupplierId(Long supplierId);
	void deleteQuestionnaireAnswerBySupplierId(Long supplierId);
	void deleteProductQuestionnaireBySupplierId(Long supplierId);
}
