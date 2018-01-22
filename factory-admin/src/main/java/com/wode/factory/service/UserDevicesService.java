package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.UserDevice;

public interface UserDevicesService {

	List<UserDevice> findByUserId(Long userId);

}
