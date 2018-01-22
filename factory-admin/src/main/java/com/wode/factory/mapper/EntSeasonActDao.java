package com.wode.factory.mapper;

import java.math.BigDecimal;

import com.wode.factory.model.EntSeasonAct;

public interface EntSeasonActDao extends FactoryBaseDao<EntSeasonAct> {
	public BigDecimal selectBalanceByDate(String lastMonth);
}