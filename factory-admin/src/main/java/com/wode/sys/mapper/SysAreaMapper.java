//Powered By if, Since 2014 - 2020

package com.wode.sys.mapper;

import com.github.abel533.mapper.Mapper;
import com.wode.sys.model.SysArea;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

public interface SysAreaMapper extends Mapper<SysArea>{
	
	public int updateParentIds(SysArea sysArea);
	
	public int deleteAreaByRootId(Long id);

	public List<SysArea> findSysAreaList(Map<String, Object> params);
	
}
