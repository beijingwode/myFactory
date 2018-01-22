//Powered By if, Since 2014 - 2020

package com.wode.tongji.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MethodInvoker;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wode.common.base.ServiceMybatis;
import com.wode.common.exception.DynamicTaskException;
import com.wode.common.util.ActResult;
import com.wode.tongji.mapper.TaskDefinitionMapper;
import com.wode.tongji.model.TaskDefinition;

/**
 * 
 * @author 
 */

@Service("TaskDefinitionService")
public class TaskDefinitionService extends ServiceMybatis<TaskDefinition> {

	private final Logger logger = LoggerFactory.getLogger("TaskDefinitionService");

	@Resource
	private TaskDefinitionMapper taskDefinitionMapper;
	
	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private ApplicationContext applicationContext;

	private static Map<Long, ScheduledFuture<?>> taskMap = Maps.newConcurrentMap();
	
	
	public void initTask() { // 系统启动后，自动加载任务(不能用Postcust)
		List<Long> ids = Lists.newArrayList();
		List<TaskDefinition> list = taskDefinitionMapper.select(new TaskDefinition());
		for (TaskDefinition td : list) {
			ids.add(td.getId());			
		}
		if (!CollectionUtils.isEmpty(ids)) {
			startTask(ids.toArray(new Long[0]));
		}
	}

	
	public PageInfo<TaskDefinition> selectPage(int pageNum,int pageSize,TaskDefinition task){
		PageInfo<TaskDefinition> page=super.selectPage(pageNum, pageSize, task);
		for(TaskDefinition t:page.getList()){
			 ScheduledFuture<?> future = taskMap.get(t.getId());
             if(future != null&&!future.isCancelled()) {
                 t.setIsStart(true);
             }else{
            	 t.setIsStart(false);
             }
		}
		
		return page;
	}
	
	public int save(TaskDefinition record) {
		record.setBeanClass(record.getBeanClass().trim());
		Runnable run=this.createTask(record);
		if(run==null){
			return 0;
		}
		return super.save(record);
	}
	
   

    public int update(TaskDefinition taskDefinition) {
    	return taskDefinitionMapper.updateByPrimaryKeySelective(taskDefinition);
    }

    
    public void delete(boolean forceTermination, Long taskDefinitionId) {
        stopTask(forceTermination, taskDefinitionId);
        this.deleteByPrimaryKey(taskDefinitionId);
    }
    
    
    
    public synchronized void startTask(Long... taskDefinitionIds) {
		for (Long taskDefinitionId : taskDefinitionIds) {
			TaskDefinition td = this.selectByPrimaryKey(taskDefinitionId);
			if (td == null ) {
				continue;
			}

			try {
				 ScheduledFuture<?> future = taskMap.get(taskDefinitionId);
	                if(future != null&&!future.isCancelled()) {
	                	continue;
	                }
	                
				 future = taskScheduler.schedule(createTask(td), new CronTrigger(td.getCron()));
				 
				taskMap.put(taskDefinitionId, future);
				td.setIsStart(Boolean.TRUE);
			} catch (Exception e) {
				logger.error("start task error, task id:" + taskDefinitionId, e);
				td.setDescription(e.getMessage());
			}
			this.updateByPrimaryKey(td);
		}
	}

	private Runnable createTask(final TaskDefinition td) {
		final MethodInvoker methodInvoker = new MethodInvoker();
		try {
			methodInvoker.setTargetMethod(td.getMethodName());
			Object bean = null;
			if (StringUtils.isNotEmpty(td.getBeanName())&&applicationContext.containsBean(td.getBeanName())) {
				bean = applicationContext.getBean(td.getBeanName());
			} else {
				bean = applicationContext.getAutowireCapableBeanFactory().createBean(Class.forName(td.getBeanClass()));
			}
			methodInvoker.setTargetObject(bean);
			methodInvoker.prepare();
		} catch (Exception e) {
			throw new DynamicTaskException("create task error:" , e);
		}
		return new Runnable() {
			@Override
			public void run() {
				try {
					methodInvoker.invoke();
					td.setLastExcuteTime(new Date());
					taskDefinitionMapper.updateByPrimaryKey(td);
				} catch (Exception e) {
					logger.error("run dynamic task error:" , e);
				}
			}
		};
	}
    
    public synchronized ActResult stopTask(boolean forceTermination, Long  taskDefinitionId) {
    	
        	TaskDefinition td = this.selectByPrimaryKey(taskDefinitionId);

            if(td == null || !td.getIsStart()) {
                return ActResult.fail("任务不存在");
            }
            ActResult ret=ActResult.success(null);
            try {
                ScheduledFuture<?> future = taskMap.get(taskDefinitionId);
                if(future != null) {
                    future.cancel(forceTermination);
                }
                td.setIsStart(false);
               
            } catch (Exception e) {
                logger.error("stop task error, task id:" + taskDefinitionId, e);
                td.setDescription(e.getMessage());
                ret=ActResult.fail(e.getMessage());
            }
            this.updateByPrimaryKey(td);
            return ret;
            
        

    }

}
