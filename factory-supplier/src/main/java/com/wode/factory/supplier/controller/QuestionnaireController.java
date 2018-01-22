/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.query.ProductQuestionnaireQuery;
import com.wode.factory.model.ProductQuestionnaire;
import com.wode.factory.model.QuestionnaireOption;
import com.wode.factory.model.QuestionnaireQuestion;
import com.wode.factory.supplier.model.SupplierQuestionnaire;
import com.wode.factory.supplier.service.ProductQuestionnaireService;
import com.wode.factory.supplier.service.QuestionnaireOptionService;
import com.wode.factory.supplier.service.QuestionnaireQuestionService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierQuestionnaireService;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.UserInterceptor;
@Controller
@RequestMapping("questionnaire")
public class QuestionnaireController extends BaseSpringController {

	@Autowired
	private ProductQuestionnaireService productQuestionnaireService;
	@Autowired
	private SupplierQuestionnaireService supplierQuestionnaireService;
	@Autowired
	private QuestionnaireOptionService questionnaireOptionService;
	@Autowired
	private QuestionnaireQuestionService questionnaireQuestionService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	@Autowired
	DBUtils dbUtils;
	
	public QuestionnaireController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	/**
	 * ajax获取运费模板
	 **/
	@RequestMapping(value="ajaxGetQuestionnaires")
	@ResponseBody
	public ActResult<List<SupplierQuestionnaire>> ajaxGetQuestionnaires(HttpServletRequest request,Long supplierId,Long questionnaireId){
		SupplierQuestionnaire q = new SupplierQuestionnaire();
		q.setSupplierId(supplierId);
		List<SupplierQuestionnaire> listQuestionnaire = supplierQuestionnaireService.selectByModel(q);
		if(!questionnaireId.equals(-1L)) {
			ProductQuestionnaire pq=  productQuestionnaireService.getById(questionnaireId);
			if(pq != null) {
				listQuestionnaire.add(new SupplierQuestionnaire(pq));
			}
		}
		return ActResult.success(listQuestionnaire);
	}
	

	/** 
	 * 进入发货地址管理页面
	 **/
	@RequestMapping(value="template_edit",method=RequestMethod.GET)
	public ModelAndView templateEdit(HttpServletRequest request,Long templateId) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/order/questionnaire_template");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			SupplierQuestionnaire template =supplierQuestionnaireService.getExById(templateId);
			mv.addObject("template", template);
			mv.addObject("userId", us.getId());
		}
		
		return mv;
	}


	/** 
	 * 进入发货地址管理页面
	 **/
	@RequestMapping(value="templates",method=RequestMethod.GET)
	public ModelAndView templates(HttpServletRequest request,Long templateId) throws Exception {
		
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		mv.setViewName("product/order/questionnaire_templates");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			SupplierQuestionnaire model=new SupplierQuestionnaire();
			model.setSupplierId(us.getSupplierId());
			mv.addObject("list", supplierQuestionnaireService.selectListCnt(model));
		}
		
		return mv;
	}
	

	/**
	 * ajax获取运费模板
	 **/
	@RequestMapping(value="aJaxSaveTemplate")
	@ResponseBody
	public ActResult<String> aJaxSaveTemplate(HttpServletRequest request,String jsonStr){

		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		SupplierQuestionnaire template = JsonUtil.getObject(jsonStr, SupplierQuestionnaire.class);
		if(us == null) {
			return ActResult.fail("登陆超时，请注销，重新登录后操作。");
		} else {
			Date now = new Date();
			SupplierQuestionnaire query = new SupplierQuestionnaire();
			query.setSupplierId(us.getSupplierId());
			query.setTemplateTitle(template.getTemplateTitle());
			List<SupplierQuestionnaire> ls = supplierQuestionnaireService.selectByModel(query);
			for (SupplierQuestionnaire supplierQuestionnaire : ls) {
				if(!supplierQuestionnaire.getId().equals(template.getId())){
					return ActResult.fail("模板名称不能重复。");
				}
			}
			// 保存模板
			if(StringUtils.isEmpty(template.getId())) {
				template.setSupplierId(us.getSupplierId());
				template.setCreateDate(now );
				template.setCreateUser(us.getId());
				
				supplierQuestionnaireService.save(template);
			} else {
				SupplierQuestionnaire old = supplierQuestionnaireService.getById(template.getId());
				old.setTemplateTitle(template.getTemplateTitle());
				old.setTestFlg(template.getTestFlg());
				supplierQuestionnaireService.update(old);
								
				questionnaireQuestionService.deleteByQuestionnaire(old.getId());
				questionnaireOptionService.deleteByQuestionnaire(old.getId());
			}

			// 保存问题
			for (QuestionnaireQuestion qq : template.getListQuestion()) {
				qq.setQuestionnaireId(template.getId());
				qq.setOldId(dbUtils.CreateID());
				questionnaireQuestionService.save(qq);
				
				for (QuestionnaireOption qo : qq.getListOption()) {
					qo.setQuestionnaireId(template.getId());
					qo.setQuestionId(qq.getOldId());
					qo.setSelectType(qq.getSelectType());
					
					questionnaireOptionService.save(qo);
				}
			}
			
			questionnaireOptionService.afterInsert(template.getId());
		}
		return ActResult.success("");
	}
	

	/**
	 * ajax获取运费模板
	 **/
	@RequestMapping(value="delTemplate")
	@ResponseBody
	public ActResult<String> delTemplate(HttpServletRequest request,Long id){

		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		if(us == null) {
			return ActResult.fail("登陆超时，请注销，重新登录后操作。");
		} else {
			supplierQuestionnaireService.delExById(id);
		}
		return ActResult.success("");
	}
	
	/**
	 * 商品list
	 **/
	@RequestMapping(value = "trialProduct")
	public ModelAndView exchageProduct(HttpServletRequest request, ProductQuestionnaireQuery vo,String leftMenu) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		vo.setSupplierId(us.getSupplierId());
		vo.setPageSize(10);
		PageInfo<ProductQuestionnaire> page =  productQuestionnaireService.findPage(vo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("product/product/trial_product");
		mv.addObject("page", page);
		mv.addObject("query", vo);
		mv.addObject("leftMenu", leftMenu==null?"":leftMenu);
		return mv;
	}
	

	/**
	 *对账单list
	 **/
	@RequestMapping(value="result",method=RequestMethod.GET)
	@Token(remove=true)
	@NoCheckLogin
	public ModelAndView result(HttpServletRequest request,HttpServletResponse response,Long qId,String leftMenu) throws Exception {
		
		Result result = new Result();
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("product/product/q_result");
		mv.addObject("leftMenu", leftMenu==null?"":leftMenu);
		ProductQuestionnaire pq = productQuestionnaireService.getExById(qId);
		if(pq == null) {
			mv.addObject("q",supplierQuestionnaireService.getExById(qId));
			mv.addObject("type","0");			
		} else {
			mv.addObject("q",productQuestionnaireService.getExById(qId));
			mv.addObject("type","1");
		}
		return mv;
	}
	

	/**
	 * ajax修改sku弹出框中信息，点击确认要操作的
	 **/
	@RequestMapping(value = "stop", method = RequestMethod.POST)
	@ResponseBody
	public ActResult stop(HttpServletRequest request, HttpServletResponse response,Long selId)
			throws Exception {
		
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		if (userModel == null) {
			return ActResult.fail("请重新登录后，再试");
		} else {
			Long productId = productQuestionnaireService.stop(selId);
			if(productId != null) {
				this.refreshProduct(productId, false);
			}
			return ActResult.success(null);
		}
	}
	
	/**
	 * ajax修改sku弹出框中信息，点击确认要操作的
	 **/
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ActResult delete(HttpServletRequest request, HttpServletResponse response,Long selId)
			throws Exception {
		
		// 在session中获取userModel
		com.wode.factory.model.UserFactory userModel = UserInterceptor.getSessionUser(request, shopService);
		if (userModel == null) {
			return ActResult.fail("请重新登录后，再试");
		} else {
			ProductQuestionnaire pq = productQuestionnaireService.getById(selId);
			if(pq != null) {
				pq.setStatus(-1);		//删除
				productQuestionnaireService.update(pq);
			}
			return ActResult.success(null);
		}
	}
	
	/**
	 * 根据商品id 更新缓存、索引、静态页
	 * @param productId
	 */
	public void refreshProduct(Long productId,boolean crreateHtml) {
		ActResult<String> ret = ActResult.success(null);
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		try {
			HttpClientUtil.sendHttpRequest("post", Constant.CACHE_API_URL + "/product/rebuild/"+productId, paramMap); //更新缓存和索引
			if(crreateHtml) {
				HttpClientUtil.sendHttpRequest("post", Constant.CREATHTML_API_URL + "/product", paramMap);//生成静态页
			}
		} catch (Exception e) {
		}
	}
}





