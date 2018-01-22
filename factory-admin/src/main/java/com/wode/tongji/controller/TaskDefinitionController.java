
package com.wode.tongji.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.base.BaseController;
import com.wode.common.util.ActResult;
import com.wode.tongji.model.TaskDefinition;
import com.wode.tongji.service.TaskDefinitionService;


@Controller
@RequestMapping("/task")
public class TaskDefinitionController extends BaseController {
	
	@Autowired
	private TaskDefinitionService service;
	
	
	@RequestMapping
	public String toTask(Model model) {
		return "sys/task/task";
	}
	
	
	/**
	 * 保存
	* @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody ActResult save(@ModelAttribute TaskDefinition task,HttpServletRequest request){
		try{
			int i=service.save(task);
			if(i>0){
				 return ActResult.success(null);
			 }else{
				 return ActResult.fail("");
			 }
		}catch(Exception e){
			return ActResult.fail(e.getLocalizedMessage());
		}
		 
	}
	
	/**
	 * 删除
	* @param id 任务id
	* @return
	 */
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		 service.delete(true, id);
		 return 1;
	}
	
	/**
	 * 停止
	* @param id 任务id
	* @return
	 */
	@RequestMapping(value="stop",method = RequestMethod.POST)
	public @ResponseBody ActResult stop(Long id){
		return service.stopTask(true, id);
	}
	
	
	/**
	 * 弹窗
	* @param id 用户id
	* @param mode 模式
	* @return
	 */
	@RequestMapping(value="/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id,  Model model){
		
		model.addAttribute("task", service.selectByPrimaryKey(id));
		
		return  "sys/task/detail";
	}
	
	
	/**
	 * 启动
	* @param id 任务id
	* @return
	 */
	@RequestMapping(value="start",method = RequestMethod.POST)
	public @ResponseBody ActResult start(Long id){
		 service.startTask(id);
		 return ActResult.success(null);
	}
	
	
	/**
	 * 列表
	* @param params
	* @param model
	* @return
	 */
	@RequestMapping("list")
	public String list(int pageNum,int pageSize,@ModelAttribute TaskDefinition task,Model model){
		model.addAttribute("page", service.selectPage(pageNum,pageSize,task));
		return "sys/task/list";
	}
	
	
}
