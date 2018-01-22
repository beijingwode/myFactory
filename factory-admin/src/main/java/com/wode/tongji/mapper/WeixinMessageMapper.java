package com.wode.tongji.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.model.WeixinMessage;

/**
 * Created by zoln on 2015/7/24.
 */
public interface WeixinMessageMapper extends  FactoryBaseDao<WeixinMessage> {

	List<WeixinMessage> findWeiXinMessageList(Map<String, Object> params);
}
