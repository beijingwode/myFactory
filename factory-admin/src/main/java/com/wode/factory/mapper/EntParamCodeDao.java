/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.EntParamCode;
import com.wode.factory.model.EntParamCodeVo;
import com.wode.factory.model.SaleDurationParam;

public interface EntParamCodeDao extends  EntityDao<EntParamCode,Long>{

	public void insert(EntParamCode entity);
	public void update(SaleDurationParam entity);

	/**
	 * 分頁查詢
	 * mapper 中需要定義 findPage 及 findPage_count
	 * @param query 
	 * @return
	 */
	public List<EntParamCode> selectByModel(EntParamCode model);
	
	public List<EntParamCodeVo> selectBanks();
}
