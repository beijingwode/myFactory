package com.wode.factory.service.impl;

import java.util.Date;

import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.Teatime;
import com.wode.factory.service.TeaTimeService;

@Service("teaTimeService")
public class TeaTimeServiceImpl implements TeaTimeService {

	@Autowired
	private Dao dao;
	
	@Override
	public boolean insert(Teatime teatime) {
		teatime.setCreatetime(new Date());
		dao.insert(teatime);
		return true;
	}

}
