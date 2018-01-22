package com.wode.factory.supplier.facade;

import java.util.List;

import com.wode.common.util.ActResult;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;

public interface ShippingTemplateFacade {

	/**
	 * 复制运费模板
	 * @param templateId
	 * @param updName
	 * @return
	 */
	ActResult<String> copyTemplate(Long templateId,String updName);
	/**
	 * 删除复制运费模板及模板规则
	 * @param templateId
	 * @return
	 */
	ActResult<String> deleteTemplate(Long templateId);
	/**
	 * 保存运费模板及模板规则
	 * @param template
	 * @param rules
	 * @param frees
	 * @param updName
	 * @return
	 */
	ActResult<String> saveTemplate(ShippingTemplate template,List<ShippingTemplateRule> rules, List<ShippingFreeRule> frees,String updName);

	/**
	 * 删除商家所有的待审核的运费模板
	 * @param supplierId
	 * @return
	 */
	ActResult<String> deleteAuditTemplate(Long supplierId);
	
	/**
	 * 查看模板是否有改变
	 * @param template
	 * @param rules
	 * @param frees
	 * @return
	 */
	boolean checkTemplateChange(ShippingTemplate template,List<ShippingTemplateRule> rules, List<ShippingFreeRule> frees);
}
