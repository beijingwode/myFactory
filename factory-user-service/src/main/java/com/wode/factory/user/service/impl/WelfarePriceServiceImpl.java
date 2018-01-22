package com.wode.factory.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.factory.user.dao.PageDataDao;
import com.wode.factory.user.service.WelfarePriceService;
import com.wode.factory.user.vo.PageTypeSettingVo;
@Service("welfarePrice")
public class WelfarePriceServiceImpl implements WelfarePriceService {
	@Autowired
	@Qualifier("pageDataDao")
	private PageDataDao pageDataDao;
	
	/* 获取员工内购价首页信息
	 * @see com.wode.factory.user.service.WelfarePriceService#getWelfarePriceHome()
	 */
	@Override
	public Map getWelfarePriceHome(Integer pageMark) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		List<PageTypeSettingVo> pageTypeInfo = this.pageDataDao.findPageDataInfo(pageMark+"",null,2);
		for(PageTypeSettingVo pts:pageTypeInfo){
			map.put(pts.getName()+"", pts.getPageDataList());
		}
		
		
		
		return map;
	}

}
