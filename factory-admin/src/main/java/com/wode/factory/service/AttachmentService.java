package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Attachment;

/**
 *
 */
public interface AttachmentService {

	List<Attachment> findByMap(Map<String, Object> map);

	void changShop(Long oldId,Long shopId);
}
