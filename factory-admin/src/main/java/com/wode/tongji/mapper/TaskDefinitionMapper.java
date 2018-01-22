//Powered By if, Since 2014 - 2020

package com.wode.tongji.mapper;

import com.github.abel533.mapper.Mapper;
import com.wode.tongji.model.TaskDefinition;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

public interface TaskDefinitionMapper extends Mapper<TaskDefinition> {


	public List<TaskDefinition> findTaskDefinitionList(Map<String, Object> params);
   

}
