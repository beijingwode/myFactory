package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Attachment;

/**
 * Created by zoln on 2015/7/24.
 */
public interface AttachmentMapper {
	List<Attachment> findByMap(Map<String, Object> map);
	
	void changShop(Map<String, Object> map);

	List<Attachment> findByShopId(Long shopId);

	void deleteById(Long id);
}
