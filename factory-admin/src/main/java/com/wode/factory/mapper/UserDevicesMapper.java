package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.UserDevice;

public interface UserDevicesMapper {

	List<UserDevice> findByUserId(Long userId);

}
