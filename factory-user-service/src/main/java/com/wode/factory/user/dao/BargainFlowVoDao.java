/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.user.model.BargainFlowVo;
import com.wode.factory.user.query.BargainFlowVoQuery;

public interface BargainFlowVoDao extends  EntityDao<BargainFlowVo,Long>{
    public PageInfo<BargainFlowVo> findByQuery(BargainFlowVoQuery query);
    
}
