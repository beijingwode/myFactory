package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.Area;

public interface AreaDao {
	
	public List<Area> findByGrade(Integer grade);
	public List<Area> findByFather(Integer father);
	public List<Area> findAll();

}