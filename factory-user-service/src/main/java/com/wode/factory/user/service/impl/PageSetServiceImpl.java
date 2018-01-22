/**
 * 
 */
package com.wode.factory.user.service.impl;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.user.service.PageSetService;
import com.wode.factory.user.vo.PageSettingVo;



/**
 * 
 * <pre>
 * 功能说明: 
 * 日期:	2015年1月19日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年1月19日
 * </pre>
 */
@Service("pageSetService")
public class PageSetServiceImpl  implements PageSetService{
	
	@Autowired
	private Dao dao;
	
	@Override
	public List<PageSettingVo> findByPidAndChannel(int pid, Integer channelId) {
		Sql sql = null; 		
		if(channelId == null) {
			sql = Sqls.create("select t.title pageTypeTitle, t.name,p.pageTypeId,p.title,p.imagePath,p.link from t_page_type_setting  t right join t_page_data p on t.pageTypeId = p.pageTypeId where t.page = @pid ORDER BY p.pageTypeId,p.orders");
			sql.params().set("pid",pid);
		} else {
			sql = Sqls.create("select t.title pageTypeTitle, t.name,p.pageTypeId,p.title,p.imagePath,p.link from t_page_type_setting  t right join t_page_data p on t.pageTypeId = p.pageTypeId where t.page = @pid and t.channelId=@channelId ORDER BY p.pageTypeId,p.orders");
			sql.params().set("pid",pid);
			sql.params().set("channelId",channelId);			
		}
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(PageSettingVo.class));
		dao.execute(sql);
		List<PageSettingVo> list = sql.getList(PageSettingVo.class);
		return list;
	}

	
	
}
