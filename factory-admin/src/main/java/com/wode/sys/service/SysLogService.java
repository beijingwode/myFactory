//Powered By if, Since 2014 - 2020

package com.wode.sys.service;

import com.wode.common.base.ServiceMybatis;
import com.wode.sys.mapper.SysLogMapper;
import com.wode.sys.model.SysLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysLogService")
public class SysLogService extends ServiceMybatis<SysLog> {

	@Resource
	private SysLogMapper sysLogMapper;
	
	/**
	 *新增或更新SysLog
	 */
	public int saveSysLog(SysLog sysLog){
		int count = 0;
		if(null == sysLog.getId()){
			count = this.insertSelective(sysLog);
		}else{
			count = this.updateByPrimaryKeySelective(sysLog);
		}
	    return count;
	}
	
	/**
	 * 根据条件分页查询SysLog列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public List<SysLog> findSysLogList(Map<String,Object> params) {
        List<SysLog> list = sysLogMapper.findSysLogList(params);
        return list;
	}
	

}
