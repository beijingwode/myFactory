package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserDevicesMapper;
import com.wode.factory.model.UserDevice;
import com.wode.factory.service.UserDevicesService;
@Service("userDevicesService")
public class UserDevicesServiceImpl implements UserDevicesService {
@Autowired
private UserDevicesMapper userDevicesMapper;
	@Override
	public List<UserDevice> findByUserId(Long userId) {
		return userDevicesMapper.findByUserId(userId);
	}

}
