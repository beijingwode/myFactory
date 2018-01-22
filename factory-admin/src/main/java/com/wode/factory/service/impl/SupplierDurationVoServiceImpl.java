
package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.SupplierDurationVoDao;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.service.SupplierDurationVoService;

@Service("SupplierDurationVoService")
public class SupplierDurationVoServiceImpl extends EntityServiceImpl<SupplierDuration,Long> implements SupplierDurationVoService {
	
	@Autowired
	private SupplierDurationVoDao dao;

	@Override
	public SupplierDurationVoDao getDao() {
		return dao;
	}

	@Override
	public void saveSupplierDuration(SupplierDuration sd) {
		// TODO Auto-generated method stub
		this.getDao().saveDurationVo(sd);
	}

	@Override
	public String getNewFinanceCode() {
		String max =this.getDao().selectMaxFinanceCode();
		if(StringUtils.isEmpty(max)){
			max="1000";
		} else {
			max = (Integer.parseInt(max)+1)+"";
		}
		return max;
	}

	@Override
	public boolean isPayFirst(SupplierDuration sd) {
		if(sd==null) return false;
		
		return ( "201".equals(sd.getSaleDurationKey()) || "211".equals(sd.getSaleDurationKey()) || "221".equals(sd.getSaleDurationKey()) );
	}

}
