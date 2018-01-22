package com.wode.tongji.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.WeixinMessage;

/**
 *
 */
public interface WeixinMessageService extends FactoryEntityService<WeixinMessage> {

	PageInfo<WeixinMessage> findWeiXinMessageList(Map<String, Object> params);

	public void pushMsg(String msg);
}
