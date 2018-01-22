
package com.wode.tongji.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wode.common.captcha.ValidateCode;
import com.wode.factory.model.CooperationId;
import com.wode.factory.model.PromotionCode;
import com.wode.factory.service.CooperationIdService;
import com.wode.tongji.mapper.PromotionCodeMapper;
import com.wode.tongji.service.PromotionCodeService;

@Service("promotionCodeService")
public class PromotionCodeServiceImpl implements  PromotionCodeService{
	
	@Resource
	private PromotionCodeMapper promotionCodeMapper;
	
	@Resource
	private CooperationIdService cooperationIdService;

	@Override
	public List<PromotionCode> findList(Map<String, Object> parm) {
		return promotionCodeMapper.findList(parm);
	}

	@Override
	public int findCount(PromotionCode pc) {
		return promotionCodeMapper.findCount(pc);
	}
	
	@Override
	public int updateSelective(List<PromotionCode> list) {
		return promotionCodeMapper.updateSelective(list);
	}

	@Override
	public List<PromotionCode> extract(int count,int status) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("i", count);
		parm.put("status", status);
		List<PromotionCode> list = findList(parm);
		List<CooperationId> listCoop = new ArrayList<CooperationId>();
		for(PromotionCode pc : list){
			CooperationId coop = new CooperationId();
			coop.setRegisterNumber(pc.getCode());
			coop.setStatus(0);
			listCoop.add(coop);
			pc.setStatus(1);
		}
		updateSelective(list);
		cooperationIdService.batchInsert(listCoop);
		return list;
	}
	
	public void batchInsert() {
		for(int i=0;i<10000;i++){
			ValidateCode vc = new ValidateCode();
			vc.setCodeCount(4);
			vc.createPromotionCode();
			String code1 = vc.getCode();
			vc.createPromotionCode();
			String code2 = vc.getCode();
			PromotionCode pc = new PromotionCode();
			String j = (i+10)+"";
			pc.setId(Long.parseLong(j));
			pc.setCode(code1+code2);
			promotionCodeMapper.insert(pc);
		}
		
	}
	
	
}
