package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.AttachmentMapper;
import com.wode.factory.model.Attachment;
import com.wode.factory.service.AttachmentService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("attachmentServiceImpl")
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	AttachmentMapper attachmentMapper;

	@Override
	public List<Attachment> findByMap(Map<String, Object> map) {
		return attachmentMapper.findByMap(map);
	}

	@Override
	public void changShop(Long oldId,Long shopId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oldId", oldId);
		map.put("shopId", shopId);
		
		attachmentMapper.changShop(map);
	}
	
		
}
