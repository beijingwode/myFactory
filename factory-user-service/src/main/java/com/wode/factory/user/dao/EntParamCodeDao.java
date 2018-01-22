package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.EntParamCode;


public interface EntParamCodeDao extends EntityDao<EntParamCode,Long> {

	public List<EntParamCode> selectByModel(EntParamCode m);
}