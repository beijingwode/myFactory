/*
O * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.controller;

import java.text.DecimalFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.result.Result;
import com.wode.factory.model.Comments;
import com.wode.factory.model.CommentsStatistics;
import com.wode.factory.model.Supplier;
import com.wode.factory.service.CommentsService;
import com.wode.factory.supplier.service.CommentsStatisticsService;
import com.wode.factory.supplier.service.ShopService;
import com.wode.factory.supplier.service.SupplierService;
import com.wode.factory.supplier.util.UserInterceptor;

@Controller
@RequestMapping("comments")
public class CommentsController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("commentsService")
	private CommentsService commentsService;
	
	@Autowired
	@Qualifier("commentsStatisticsService")
	private CommentsStatisticsService commentsStatisticsService;
	
	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	@Autowired
	@Qualifier("shopService")
	private ShopService shopService;
	
	public CommentsController() {
	}

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	

	
	/** 
	 * 进入评价管理
	 **/
	@RequestMapping(value="toevaluation")
	public ModelAndView toevaluation(HttpServletRequest request,HttpServletResponse response) throws Exception {
		com.wode.factory.model.UserFactory us = UserInterceptor.getSessionUser(request,shopService);
		ModelAndView mv =new ModelAndView();
		Result result = new Result();
		mv.setViewName("product/order/evaluation");
		String commentDegree = request.getParameter("commentDegree");
		if(us == null) {
			//会话中usermodel对象为空
			mv.setViewName("redirect:/user/login.html");
			logger.error("点击用户中心首页时session中userModel对象为空！errorcode：10000" );
		} else {
			Supplier resupplier = supplierService.getById(us.getSupplierId());
			Map<String, Object> reparm = new HashMap<String, Object>();
			reparm.put("type", 0);
			reparm.put("supplier_id", resupplier.getId());
			List<CommentsStatistics> week = commentsStatisticsService.findbymap(reparm);
			String good = "";
			String medium = "";
			String bad = "";
			String tote = "";
			Long gw = 0L;
			Long mw = 0L;
			Long bw = 0L;
			Long gm = 0L;
			Long mm = 0L;
			Long bm = 0L;
			Long g1 = 0L;
			Long m1 = 0L;
			Long b1 = 0L;
			Long g2 = 0L;
			Long m2 = 0L;
			Long b2 = 0L;
			Long totew = 0L;
			Long totem = 0L;
			Long tote1 = 0L;
			Long tote2 = 0L;
			
			//最近一周
			if(week.size()>0){
				CommentsStatistics commentsStatistics = week.get(0);
				good = (commentsStatistics.getGood()!=null?commentsStatistics.getGood():0) + ",";
				medium = (commentsStatistics.getMedium()!=null?commentsStatistics.getMedium():0) + ",";
				bad = (commentsStatistics.getBad()!=null?commentsStatistics.getBad():0) + ",";
				gw = gw + commentsStatistics.getGood();
				mw = mw + commentsStatistics.getMedium();
				bw = bw + commentsStatistics.getBad();
				totew = gw + mw + bw;
				tote = totew + ",";
			}else{
				good = "0,";
				medium = "0,";
				bad = "0,";
				tote = "0,";
			}
			
			//最近1月
			reparm.put("type", 1);
			List<CommentsStatistics> month = commentsStatisticsService.findbymap(reparm);
			if(month.size()>0){
				CommentsStatistics commentsStatistics = month.get(0);
				good = good + (commentsStatistics.getGood()!=null?commentsStatistics.getGood():0) + ",";
				medium = medium + (commentsStatistics.getMedium()!=null?commentsStatistics.getMedium():0) + ",";
				bad = bad + (commentsStatistics.getBad()!=null?commentsStatistics.getBad():0) + ",";
				tote += totem + ",";
				
				gm = gm + commentsStatistics.getGood();
				mm = mm + commentsStatistics.getMedium();
				bm = bm + commentsStatistics.getBad();
				g1 = g1 + gm;
				m1 = m1 + mm;
				b1 = b1 + bm;
				totem = gm + mm + bm;
			}else{
				good = good + "0,";
				medium = medium + "0,";
				bad = bad + "0,";
				tote += totem + ",";
				g1 = g1 + gm;
				m1 = m1 + mm;
				b1 = b1 + bm;
				totem = gm + mm + bm;
			}
			reparm.put("type", 2);
			List<CommentsStatistics> list = commentsStatisticsService.findbymap(reparm);
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					CommentsStatistics commentsStatistics = list.get(i);
					if(i<6){
						// 前5个月统计+当前月
						g1 = g1 + commentsStatistics.getGood();
						m1 = m1 + commentsStatistics.getMedium();
						b1 = b1 + commentsStatistics.getBad();
					}else{
						// 前6个月统计
						g2 = g2 + commentsStatistics.getGood();
						m2 = m2 + commentsStatistics.getMedium();
						b2 = b2 + commentsStatistics.getBad();
					}
				}
				tote1 = g1 + m1 + b1;
				tote2 = g2 + m2 + b2;
				
				// 最近6个月
				good = good + g1 + ",";
				medium = medium + m1 + ",";
				bad = bad + b1 + ",";
				tote = tote + tote1 + ",";
				
				// 6个月前
				good = good + g2 + ",";
				medium = medium + m2 + ",";
				bad = bad + b2 + ",";
				tote = tote + tote2 + ",";
				
				// 总计
				good = good + ( g1 + g2);
				medium = medium + ( m1 + m2);
				bad = bad + ( b1 + b2);
				tote = tote + (tote1 + tote2);
			}else{
				// 最近6个月
				good = good + g1 + ",";
				medium = medium + m1 + ",";
				bad = bad + b1 + ",";
				tote = tote + tote1 + ",";

				// 6个月前
				good = good + g2 + ",";
				medium = medium + m2 + ",";
				bad = bad + b2 + ",";
				tote = tote + tote2 + ",";

				// 总计
				good = good + (g1 + g2);
				medium = medium + (m1 + m2);
				bad = bad + (b1 + b2);				
				tote = tote + (tote1 + tote2);
			}
			String pages = request.getParameter("pages");
			String sizes = request.getParameter("sizes");
			Integer page=1;
			Integer size=10;
			if(pages==null||pages.equals("")){
				pages = "1";
			}
			page = new Integer(pages);
			if(sizes == null || sizes.equals("")){
				sizes="10";
			}
			size= new Integer(sizes);
			if(size>100){
				size=100;
			}
			Supplier supplier = supplierService.getById(us.getSupplierId());

			Long total=0L;
			Map<String ,Object> levelMap=commentsService.getSupplierLevelCnt(supplier!=null?supplier.getId():new Long(0), null, null);
    		if("all".equals(commentDegree)) {
    			Long goodCnt = (Long)levelMap.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT);
            	Long mediumCnt = (Long)levelMap.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT);
            	Long badCnt = (Long)levelMap.get(CommentsService.CACHE_MAP_KEY_BAD_CNT);
            	total = goodCnt+mediumCnt+badCnt;
    		} else if ("1".equals(commentDegree)) {
    			total = (Long)levelMap.get(CommentsService.CACHE_MAP_KEY_BAD_CNT);
    		} else if ("3".equals(commentDegree)) {
    			total = (Long)levelMap.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT);
    		} else if ("5".equals(commentDegree)) {
    			total = (Long)levelMap.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT);
    		}
        	
			if(total>0){
				Comments model = new Comments();
				model.setSupplyid(supplier!=null?supplier.getId():new Long(0));
				if(!"all".equals(commentDegree)){
					model.setCommentDegree(Integer.parseInt(commentDegree));
				}
				
				List<Comments> csList = commentsService.findComments(model, page, size);
				mv.addObject("csList", csList);
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total.intValue());
				result.setErrorCode("0");
				
			}else{
				result.setErrorCode("1000");
				result.setPage(page);
				result.setSize(size);
				result.setTotal(total.intValue());
			}
			mv.addObject("goods", (tote1 + tote2)>0?myPercent((g1+g2),(tote1 + tote2)):"100.00%");	//好评率计算时不含中评  +m1+m2
			mv.addObject("good", good);
			mv.addObject("medium", medium);
			mv.addObject("bad", bad);
			mv.addObject("tote", tote);
			mv.addObject("pages",page);
			mv.addObject("sizes",size);
			mv.addObject("result",result);
			mv.addObject("commentDegree",commentDegree);
		}
		return mv;
	}
	
	public static String myPercent(Long y, Long z) {  
        String baifenbi = "";// 接受百分比的值  
        double baiy = y * 1.0;  
        double baiz = z * 1.0;  
        double fen = baiy / baiz;  
        DecimalFormat df1 = new DecimalFormat("##.00%"); // ##.00%  
        baifenbi = df1.format(fen);  
        return baifenbi;  
    }
}

