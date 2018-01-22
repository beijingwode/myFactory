package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.Suborderitem;

public interface SuborderItemService {
	
	public List<Suborderitem> selectByModel(Suborderitem query);
	
	public void update(Suborderitem soi);
	
}
