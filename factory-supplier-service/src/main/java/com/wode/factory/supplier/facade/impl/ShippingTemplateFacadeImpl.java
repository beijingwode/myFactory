/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtilEx;
import com.wode.common.util.ActResult;
import com.wode.common.util.ObjectUtils;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.supplier.facade.ShippingTemplateFacade;
import com.wode.factory.supplier.service.ShippingFreeRuleService;
import com.wode.factory.supplier.service.ShippingTemplateRuleService;
import com.wode.factory.supplier.service.ShippingTemplateService;

@Service("shippingTemplateFacade")
public class ShippingTemplateFacadeImpl implements ShippingTemplateFacade {
	
	private static Logger logger = LoggerFactory.getLogger(ShippingTemplateFacadeImpl.class);


	@Autowired
	private ShippingTemplateService shippingTemplateService;
	@Autowired
	@Qualifier("supplierShippingTemplateRuleService")
	private ShippingTemplateRuleService shippingTemplateRuleService;
	@Autowired
	@Qualifier("supplierShippingFreeRuleService")
	private ShippingFreeRuleService shippingFreeRuleService;
	@Autowired
	private RedisUtilEx redisUtilEx;
	
	@Autowired
	DBUtils dbUtils;
	
	@Deprecated//新版运费模板一个商家只有一个模板不允许复制
	@Override
	@Transactional
	public ActResult<String> copyTemplate(Long templateId, String updateUser) {
		ShippingTemplate template= shippingTemplateService.getById(templateId);
		if(template==null) return ActResult.fail("原模板不存在，请刷新页面后重试");
		
		List<ShippingTemplateRule> rules= template.getRulelist();
		List<ShippingFreeRule> frees= template.getFreelist();
		Date now = new Date();
		//复制模板
		template.setId(null);	//ID清空
		template.setName(template.getName()+"_1");	//名称+_1
		template.setCreateTime(now);
		template.setCreateUser(updateUser);
		
		//复制模板规则
		for (ShippingTemplateRule role: rules) {
			role.setId(null);
			role.setTemplateId(null);
		}

		//复制模板包邮规则
		for (ShippingFreeRule role: frees) {
			role.setId(null);
			role.setTemplateId(null);
		}
		return saveTemplate(template,rules,frees,updateUser);
	}

	@Override
	@Transactional
	public ActResult<String> deleteTemplate(Long templateId) {
		ShippingTemplate template= shippingTemplateService.getById(templateId);
		if(template==null) return ActResult.fail("原模板不存在，请刷新页面后重试");
		
		//删除运费模板规则
		for (ShippingTemplateRule role: template.getRulelist()) {
			shippingTemplateRuleService.removeById(role.getId());
		}

		//删除运费包邮规则
		for (ShippingFreeRule role: template.getFreelist()) {
			shippingFreeRuleService.removeById(role.getId());
		}

		//删除运费模板
		shippingTemplateService.removeById(templateId);
		
		return ActResult.success(template.getId()+"");
	}

	@Override
	@Transactional
	public ActResult<String> saveTemplate(ShippingTemplate template, List<ShippingTemplateRule> rules, List<ShippingFreeRule> frees, String updateUser) {
		
		//删除旧规则
		if(template.getId() != null) {
			ShippingTemplateRule record = new ShippingTemplateRule();
			record.setTemplateId(template.getId());
			List<ShippingTemplateRule> oldRules = shippingTemplateRuleService.selectByModel(record);
			List<Long> idList = new ArrayList<Long>();
			for (ShippingTemplateRule role: oldRules) {
				idList.add(role.getId());
			}
			//改成批量删除
			shippingTemplateRuleService.batchDelete(idList);
		}
		
		//删除旧包邮规则
		if(template.getId() != null) {
			ShippingFreeRule record = new ShippingFreeRule();
			record.setTemplateId(template.getId());
			List<ShippingFreeRule> oldRules = shippingFreeRuleService.selectByModel(record);
			List<Long> idList = new ArrayList<Long>();
			for (ShippingFreeRule role: oldRules) {
				idList.add(role.getId());
			}
			//改成批量删除
			shippingFreeRuleService.batchDelete(idList);
		}
		
		Date now = new Date();
		template.setUpdateTime(now);
		template.setUpdateUser(updateUser);
		
		//保存模板
		shippingTemplateService.saveOrUpdate(template);

		//保存模板规则
		for (ShippingTemplateRule role: rules) {
			role.setId(dbUtils.CreateID());
			role.setTemplateId(template.getId());
		}
		//改成批量插入
		shippingTemplateRuleService.batchAdd(rules);

		//保存模板包邮规则
		for (ShippingFreeRule role: frees) {
			role.setId(dbUtils.CreateID());
			role.setTemplateId(template.getId());
		}
		//改成批量插入
		shippingFreeRuleService.batchAdd(frees);
        //修改完，删除缓存
        redisUtilEx.delKey("selectTemplateBySupplierId_["+template.getSupplierId()+"]");
		return ActResult.success(template.getId()+"");
	}

	@Override
	public ActResult<String> deleteAuditTemplate(Long supplierId) {
		ShippingTemplate template = new ShippingTemplate();
		template.setSupplierId(supplierId);
		template.setIsAudit(1);
		List<ShippingTemplate> templateList = shippingTemplateService.selectByModel(template);
		if(null == templateList || templateList.size() == 0) {
			template.setIsAudit(2);
			templateList = shippingTemplateService.selectByModel(template);
		}
		for (ShippingTemplate shippingTemplate : templateList) {
			
			//删除旧规则
			if(shippingTemplate.getId() != null) {
				ShippingTemplateRule record = new ShippingTemplateRule();
				record.setTemplateId(shippingTemplate.getId());
				List<ShippingTemplateRule> oldRules = shippingTemplateRuleService.selectByModel(record);
				List<Long> idList = new ArrayList<Long>();
				for (ShippingTemplateRule role: oldRules) {
					idList.add(role.getId());
				}
				//改成批量删除
				shippingTemplateRuleService.batchDelete(idList);
			}
			
			//删除旧包邮规则
			if(shippingTemplate.getId() != null) {
				ShippingFreeRule record = new ShippingFreeRule();
				record.setTemplateId(shippingTemplate.getId());
				List<ShippingFreeRule> oldRules = shippingFreeRuleService.selectByModel(record);
				List<Long> idList = new ArrayList<Long>();
				for (ShippingFreeRule role: oldRules) {
					idList.add(role.getId());
				}
				//改成批量删除
				shippingFreeRuleService.batchDelete(idList);
			}
			shippingTemplateService.removeById(shippingTemplate.getId());
		}
		return ActResult.success("");
	}

	@Override
	public boolean checkTemplateChange(ShippingTemplate template, List<ShippingTemplateRule> rules,
			List<ShippingFreeRule> frees) {
		if (null == template.getId()) {
			return true;
		}
		ShippingTemplate template2 = shippingTemplateService.getById(template.getId());
		logger.info(template.toString() + " == " + template2.toString());
		List<String> excludeList = new ArrayList<String>();
		excludeList.add("updateUser");
		excludeList.add("updateTime");
		excludeList.add("rulelist");
		excludeList.add("freelist");
		if(ObjectUtils.classOfSrc(template, template2,excludeList)) {
			return true;
		}

		// 比较旧规则
		if (template.getId() != null) {
			ShippingTemplateRule record = new ShippingTemplateRule();
			record.setTemplateId(template.getId());
			List<ShippingTemplateRule> oldRules = shippingTemplateRuleService.selectByModel(record);
			if(null == oldRules) {
				oldRules = new ArrayList<ShippingTemplateRule>();
			}
			if(rules.size() != oldRules.size()) {
				return true;
			}
			for (int i = 0; i < oldRules.size(); i++) {
				logger.info(oldRules.get(i).toString() + " == " + rules.get(i).toString());
				excludeList.add("id");
				excludeList.add("templateId");
				if(ObjectUtils.classOfSrc(oldRules.get(i), rules.get(i),excludeList)) {
					return true;
				}
			}
		}

		// 比较旧包邮规则
		if (template.getId() != null) {
			ShippingFreeRule record = new ShippingFreeRule();
			record.setTemplateId(template.getId());
			List<ShippingFreeRule> oldRules = shippingFreeRuleService.selectByModel(record);
			if(null == oldRules) {
				oldRules = new ArrayList<ShippingFreeRule>();
			}
			if(frees.size() != oldRules.size()) {
				return true;
			}
			for (int i = 0; i < oldRules.size(); i++) {
				logger.info(oldRules.get(i).toString() + " == " + frees.get(i).toString());
				if(ObjectUtils.classOfSrc(oldRules.get(i), frees.get(i),excludeList)) {
					return true;
				}
			}
		}
		return false;
	}
	
}
