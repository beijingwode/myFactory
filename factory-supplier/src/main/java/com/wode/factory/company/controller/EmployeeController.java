/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.UserConstant;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.stereotype.Token;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.company.facade.EmpBenefitFacade;
import com.wode.factory.company.query.EmpLevelCountVo;
import com.wode.factory.company.query.EnterpriseUserTakeOrderVo;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.company.query.GiveBenefitRecordVo;
import com.wode.factory.company.service.EnterpriseService;
import com.wode.factory.company.service.EnterpriseUserService;
import com.wode.factory.company.service.UserShareService;
import com.wode.factory.company.util.SeasonUtil;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserShare;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.supplier.service.UserService;
import com.wode.factory.supplier.util.AppOrWxPushUtil;
import com.wode.factory.supplier.util.Constant;
import com.wode.factory.supplier.util.ExcelUtil;
import com.wode.model.CommUser;

@Controller
@RequestMapping("company/emp")
public class EmployeeController extends BaseCompanyController{
	@Autowired
	EnterpriseUserService enterpriseUserService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	UserService userService;
	@Autowired
	UserShareService userShareService;

	@Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;
	
	@Autowired
	DBUtils dbUtils;
	@Autowired
	ExcelUtil excelUtil;
	HttpServletRequest re;
	private static Logger logger= LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmpBenefitFacade empBenefitFacade;

	@Value("#{configProperties['factory.api.url']}")
	private  String apiUrl;

	static CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
	
	/**
	 * 进入员工自提订单列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="takeOrder")
	@NoCheckLogin
	public ModelAndView toTakeOrderPage(HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ModelAndView result = new ModelAndView("company/employees/takeOrderList");
		return result;
	}
	
	/**
	 * 自提订单信息
	 * @param request
	 * @param response
	 * @param takeOrderVo
	 * @return
	 */
	@RequestMapping(value="takeOrderPage")
	public ModelAndView takeOrderList(HttpServletRequest request,HttpServletResponse response,EnterpriseUserTakeOrderVo takeOrderVo) {
		if(takeOrderVo.getOrderStatus() == null)takeOrderVo.setOrderStatus(1);
		EnterpriseVo ent = getEnterpriseInfo(request);
		takeOrderVo.setEnterpriseId(ent.getId());
		PageInfo<EnterpriseUserTakeOrderVo> page = enterpriseUserService.findTakeOrderListByPage(takeOrderVo);
		ModelAndView result = new ModelAndView("company/employees/takeOrderList");
		result.addObject("page", page);
		result.addObject("query", takeOrderVo);
		return result;
	}
	
	/**
	 * 自提订单导出数据
	 * @param request
	 * @param response
	 * @param giveBenefitRecordVo
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,EnterpriseUserTakeOrderVo takeOrderVo) throws UnsupportedEncodingException {

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("员工自提订单列表"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("员工");
        headers.add("电话");
        headers.add("部门");
        headers.add("商品名称");
        headers.add("规格");
        headers.add("数量");
        headers.add("下单日期");
        headers.add("状态");
                
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
        takeOrderVo.setPageNumber(1);
        takeOrderVo.setPageSizeNoMax(5000);
        EnterpriseVo ent = getEnterpriseInfo(request);
		takeOrderVo.setEnterpriseId(ent.getId());
        PageInfo<EnterpriseUserTakeOrderVo> page = enterpriseUserService.findTakeOrderListByPage(takeOrderVo);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (EnterpriseUserTakeOrderVo vo : page.getList()) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("姓名");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getName()==null?"":vo.getName());
            //headers.add("电话");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getPhone()==null?"":vo.getPhone());
            //headers.add("部门");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getSectionName()==null?"":vo.getSectionName());
            //headers.add("商品名称");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getProductName()==null?"":vo.getProductName());
            //headers.add("规格");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getItemValues()==null?"":vo.getItemValues());
            //headers.add("数量");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getNumber()==null?"":vo.getNumber());
            //headers.add("下单日期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(vo.getCreateTime()==null?"":sdf.format(vo.getCreateTime()));
            //headers.add("状态");
            String statusValue = "";
            if(vo.getOrderStatus() != null) {
            	if(vo.getOrderStatus() == 1 || vo.getOrderStatus() == 2) {
            		statusValue = "未收货";
            	}
            	if(vo.getOrderStatus() >= 4) {
            		statusValue = "已收货";
            	}
            }
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(statusValue);
		}
				
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	private String getFileNameForSave(HttpServletRequest request) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = "员工自提订单列表"+ TimeUtil.dateToStr(new Date(),"_yyyyMMdd") +".xls";
		String new_filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		String rtn = "filename=\"" + new_filename + "\"";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
				rtn = "filename=\"" + new_filename + "\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
	
	/** 
	 * 进入员工信息管理界面
	 **/
	@RequestMapping(value="to_emp_manage")
	@NoCheckLogin
	public ModelAndView toempmanage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView("company/employees/empmanage");
	}
	
	@RequestMapping(value="page")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,EnterpriseUserVo entUserVo) {
		EnterpriseVo ent = getEnterpriseInfo(request);
		
		entUserVo.setEnterpriseId(ent.getId());
		PageInfo<EnterpriseUserVo> page = enterpriseUserService.findByPage(entUserVo);
		ModelAndView result = new ModelAndView("company/employees/empmanage");
		result.addObject("page", page);
		result.addObject("query", entUserVo);
		//查询最大的员工序号
		String maxEmpNumber = this.enterpriseUserService.selectMaxEmpNumber(ent.getId());
		
		result.addObject("maxEmpNumber", maxEmpNumber);
		//企业的福利级别
		if(!StringUtils.isEmpty(ent))
			result.addObject("welfareLevel", ent.getWelfareLevel());
		UserShare query = new UserShare();
		query.setUserId(ent.getId());
		query.setShareType(9);
		List<UserShare> ls = userShareService.selectByModel(query);
		if(ls!=null && !ls.isEmpty()) {
			result.addObject("us", ls.get(0));			
		}
		result.addObject("ent", ent);
		return result;
	}
 
	/**
	 * 根据主键id查询数据
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getById")
	@ResponseBody
	public EnterpriseUser  selectById(HttpServletRequest request,HttpServletResponse response,Long id){
		
		return this.enterpriseUserService.selectById(id);
	}
	/**
	 * 批量更改员工的福利等级
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateEmpWelfareLevel")
	@ResponseBody
	public ActResult updateEmpWelfareLevel(HttpServletRequest request,HttpServletResponse response,Integer welfareLevel){
		EnterpriseVo entVo = getEnterpriseInfo(request);
		

		ActResult act=this.enterpriseUserService.updateEmpWelfareLevel(getSupplierId(request), welfareLevel,entVo);
		
		
		EnterpriseVo enterpriseVo = getEnterpriseInfo(request);
		HttpSession session = request.getSession();
		enterpriseVo.setWelfareLevel(welfareLevel);
		session.setAttribute(ENTERPRISE_SESSION, enterpriseVo);
		return act;
	}
	
	/**
	 * 员工注册设置
	 * @param request
	 * @param response
	 * @param emailPostfix1		邮箱后缀1
	 * @param emailPostfix2		邮箱后缀2
	 * @param emailPostfix3		邮箱后缀3
	 * @param empDefultAvatar	员工默认头像
	 * @param canSearch			搜索可见
	 * @return
	 */
	@RequestMapping(value="updateEnterpriseSet")
	@ResponseBody
	public ActResult updateEnterpriseSet(HttpServletRequest request,HttpServletResponse response,String emailPostfix1,String emailPostfix2,String emailPostfix3,String empDefultAvatar,String canSearch){
		Long entId=this.getSupplierId(request);
		if(!StringUtils.isEmpty(emailPostfix1)) {
			Enterprise ent = enterpriseService.findByEmailPostfix(emailPostfix1);
			if(ent!=null && !ent.getId().equals(entId)) {
				return ActResult.fail("邮箱后缀：@"+emailPostfix1+"，已被其他企业使用，请使用正确企业邮箱或联系客服进行举报");
			}
		}
		if(!StringUtils.isEmpty(emailPostfix2)) {
			Enterprise ent = enterpriseService.findByEmailPostfix(emailPostfix2);
			if(ent!=null && !ent.getId().equals(entId)) {
				return ActResult.fail("邮箱后缀：@"+emailPostfix2+"，已被其他企业使用，请使用正确企业邮箱或联系客服进行举报");
			}
			
		}
		if(!StringUtils.isEmpty(emailPostfix3)) {
			Enterprise ent = enterpriseService.findByEmailPostfix(emailPostfix3);
			if(ent!=null && !ent.getId().equals(entId)) {
				return ActResult.fail("邮箱后缀：@"+emailPostfix3+"，已被其他企业使用，请使用正确企业邮箱或联系客服进行举报");
			}
		}			
		enterpriseService.updateEnterpriseSet(entId, emailPostfix1, emailPostfix2, emailPostfix3, empDefultAvatar, canSearch);
		
		// 更新session中的vo
		HttpSession session = request.getSession();
		session.removeAttribute(ENTERPRISE_SESSION);
		getEnterpriseInfo(request);
		
		// 清空邮箱后缀缓存,以便获取DB中最新内容
		redisUtil.del(UserConstant.EMAIL_POSTFIXS);
		return ActResult.success("");
	}
	

	/**
	 * 获取企业信息
	 * @param request
	 * @param response
	 * @param entId
	 * @return
	 */
	@RequestMapping(value="getEnterpriseInfo")
	@ResponseBody
	public ActResult updateEnterpriseSet(HttpServletRequest request,HttpServletResponse response,Long entId){
		if(StringUtils.isEmpty(entId)) {
			return ActResult.fail("参数错误。");
		} 
		Enterprise ent = enterpriseService.getById(entId);
		if(ent==null) {
			return ActResult.fail("参数错误。");
		}

		return ActResult.success(ent);
	}
	
	/**
	 * 修改数据
	 * @param request
	 * @param response
	 * @param entUser
	 * @return
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public Integer update(HttpServletRequest request,HttpServletResponse response,EnterpriseUser entUser){
		entUser.setEnterpriseId(getSupplierId(request));
		//输入检查
		if (StringUtils.isEmpty(entUser.getId())
				&& StringUtils.isEmpty(entUser.getUserName())
				&& StringUtils.isEmpty(entUser.getPhone())) {
			return 0;
		}
		// 获取修改前数据
		EnterpriseUser old = enterpriseUserService.getById(entUser.getId());
		if (!StringUtils.isEquals(toStr(old.getName()),toStr(entUser.getName()))) {//姓名修改
			UserFactory uf = userService.getById(entUser.getId());
			uf.setRealName(entUser.getName());
			this.userService.update(uf);
		}
		if(StringUtils.isEquals(toStr(old.getPhone()), toStr(entUser.getPhone())) && StringUtils.isEquals(toStr(old.getEmail()), toStr(entUser.getEmail()))){
			//手机号或者邮箱没有修改，只修改企业中的信息
			return this.enterpriseUserService.updateById(entUser,"update_ent");
		}else{ 
			//手机号有改动，需要修改全部的账号信息(共通、厂、企业员工)
			/**
			 * 修改共通用户手机号，如果手机号在共同不存在可以修改成功。但是存在的话，就返回false，提示该用户已存在
			 * */
			ActResult<String> act = null;
			
			//手机修改
			if(!StringUtils.isEquals(toStr(old.getPhone()), toStr(entUser.getPhone()))) {
				act = us.updatePhone(entUser.getPhone(), entUser.getId(), UserConstant.COMFROM);
				
				if(!act.isSuccess()) {
					//如果把原有的手机号转移到该企业，原来账号密码问题，id问题，不仅要修改厂还要修改企业中的员工id，牵连大。或者修改共通中的id，没有接口，还需要删除原有的账号i的，否则重复
					logger.error("修改员工接口：>>>>>>>>"+"手机号："+old.getPhone()+">>>>>>新手机号："+entUser.getPhone()+">>>>>>status：修改失败，共通已存在该手机号");
					return -1;
				}
			}

			//邮箱修改
			if(!StringUtils.isEquals(toStr(old.getEmail()), toStr(entUser.getEmail()))) {
				act = us.updateEmail(entUser.getEmail(), entUser.getId(), UserConstant.COMFROM);
				
				if(!act.isSuccess()) {
					//如果把原有的手机号转移到该企业，原来账号密码问题，id问题，不仅要修改厂还要修改企业中的员工id，牵连大。或者修改共通中的id，没有接口，还需要删除原有的账号i的，否则重复
					logger.error("修改员工接口：>>>>>>>>"+"邮箱："+old.getEmail()+">>>>>>新邮箱："+entUser.getEmail()+">>>>>>status：修改失败，共通已存在该邮箱");
					return -2;
				}
			}

			return this.enterpriseUserService.updateById(entUser,"update_all");
		}
	}
	private String toStr(String p) {
		if(p==null) return "";
		return p.trim();
	}
	/**
	 * 删除数据
	 * @param request
	 * @param response
	 * @param entUser
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Integer delete(HttpServletRequest request,HttpServletResponse response,Long id){
		return this.enterpriseUserService.deleteEnterpriseUser(id);
	}
	
	/**
	 * 新增数据
	 * @param request
	 * @param response
	 * @param entUser
	 * @return
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Integer add(HttpServletRequest request,HttpServletResponse response,EnterpriseUser entUser){
		if(StringUtils.isEmpty(entUser.getPhone()) && StringUtils.isEmpty(entUser.getEmail())){
			return 0;
		}
		entUser.setEnterpriseId(getSupplierId(request));
		re = request;

		Enterprise ent= enterpriseService.getById(entUser.getEnterpriseId());
		String shopLink = StringUtils.isEmpty(ent.getEmpDefultAvatar())?null:(enterpriseService.getFirstShopId(entUser.getEnterpriseId())+"");
		
		List<EnterpriseUser> newEmps = new ArrayList<EnterpriseUser>();
		Integer rtn = this.addEnterpriseUser(entUser,request,newEmps,ent.getEmpDefultAvatar(),shopLink);
		
		autoBenefit(request, newEmps);
		return rtn;
	}

	/**
	 * 13.1	员工商品分享（邀请亲友用）
	 * 
	 * @param type	
	 * @param productId
	 * @param skuId
	 * @return
	 */
	@RequestMapping("makeQr")
	@ResponseBody
	public ActResult<UserShare> makeQr(HttpServletRequest request) {

		EnterpriseVo vo = getEnterpriseInfo(request);
		UserShare us =new UserShare();
		us.setId(dbUtils.CreateID());
		us.setUserId(vo.getId());
		if(!StringUtils.isEmpty(vo.getNickName())){
			us.setUserNick(vo.getNickName());	
		}else{
			us.setUserNick(vo.getName());	
		}
		us.setUserAvatar(null);
		us.setUserType(9);
		us.setShareType(9);
		us.setShareTitle("扫码领取内购券");
		//us.setShareMsg1(loginUser.getNickName());

		if(!StringUtils.isEmpty(vo.getNickName())){
			us.setShareMsg1(vo.getNickName());	
		}else{
			us.setShareMsg1(vo.getName());	
		}
		us.setShareMsg2("【我的福利】<span>"+vo.getName()+"</span>的员工绑定手机号，可立即获得1000元内购券。");
		us.setShareItemCnt(10);
		us.setShareMsg3("又为大家争取到了新的福利，以下超值商品任意购，内购券当钱用还能分享给好友。");
		us.setShareFooter1("长按识别图中二维码");
		us.setShareFooter2("首次绑定手机即获500元内购券");
		us.setShareFooter3("仅限员工专享");
		us.setCreateTime(new Date());
		
		us.setShareUrl("http://api.wd-w.com/userShare/page"+us.getId());
		us.setNextAction("http://api.wd-w.com/userShare/toCompanyBind"+us.getId());
		userShareService.save(us);
		
		return ActResult.success(us);
	}
	/**
	 * 13.1	员工商品分享（邀请亲友用）
	 * 
	 * @param type	
	 * @param productId
	 * @param skuId
	 * @return
	 */
	@RequestMapping("removeQr")
	@ResponseBody
	public ActResult<String> removeQr(HttpServletRequest request) {
		EnterpriseVo vo = getEnterpriseInfo(request);
		UserShare query = new UserShare();
		query.setUserId(vo.getId());
		query.setShareType(9);
		redisUtil.del("REDIS_USER_SHARE_"+vo.getId());
		List<UserShare> ls = userShareService.selectByModel(query);
		if(ls!=null && !ls.isEmpty()) {
			userShareService.removeById(ls.get(0).getId());
			
			return ActResult.success("");
		} else {
			return ActResult.fail("删除失败，请刷新后重试");
		}
	}

	private void autoBenefit(HttpServletRequest request, List<EnterpriseUser> newEmps) {
		for (EnterpriseUser enterpriseUser : newEmps) {
			int ticket = 500;
			int result= empBenefitFacade.dealEmpBenefit(enterpriseUser.getId(), "216", BigDecimal.ZERO, NumberUtil.toBigDecimal(ticket), 
					getCurrentUserName(request), enterpriseUser.getId()+"", dbUtils.CreateID(), enterpriseUser.getEnterpriseId(), 
					enterpriseUser.getName(), "员工注册，平台代企业下发内购券。", SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),"");
			
			//String msg = "公司向您发放福利，内购券："+ticket+"，可以用来购买心仪商品，或转发给亲友。享受真正的员工福利。";
			
			String msg = enterpriseUser.getId()+"_userBind_500ticket_all";
			AppOrWxPushUtil.pushMsgAll(redisUtil, msg, AppOrWxPushUtil.PUSH_TYPE_USER_BIND);
			//AppPushUtil.pushMsg(enterpriseUser.getId(), "公司发放福利", msg);

			//AppPushUtil.pushWxBalace(enterpriseUser.getId(), ticket+"", TimeUtil.getStringDateShort(), msg, "1");
		}
	}
	
	@RequestMapping(value="addBatchEmp.json",method=RequestMethod.POST)
	@ResponseBody
	public void addBatchEmp(MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		ActResult act = new ActResult();
		JSONObject jo = new JSONObject();
		if(file.isEmpty()){
			act.setSuccess(false);
			act.setMsg("请选择文件后上传");
			this.getResponse(response, jo.toJSONString(act));
	     }
	     if(file.getSize() > (1024*1024*5)){
	    	act.setSuccess(false);
			act.setMsg("文件大小不能大于5M！");
			this.getResponse(response, jo.toJSONString(act));
	     }
	     
	     try {
	 		re = request;
	    	//将文件上传到服务器
	    	 String url = excelUtil.uploadFileExcel(file, request);
	    	 //根据路径解析excel文件中的数据，并返回正确数据
	    	 ActResult<List<EnterpriseUser>> listAct = excelUtil.availableData(url, getEnterpriseInfo(request).getWelfareLevel(),this.getEnterpriseInfo(request).getId());
	    	 //根据url删除服务器中的文件
	    	 excelUtil.deleteUploadFileExcel(url);
	    	//读取Excel生成的数据（包含中间的空行）
			if(listAct.isSuccess()){
				this.getResponse(response, jo.toJSONString(this.newAddBatchEmp(listAct.getData(), getEnterpriseInfo(request).getId(),request)));
			}else{
				this.getResponse(response, jo.toJSONString(listAct));
			}
		} catch (Exception e) {
			e.printStackTrace();
			act.setSuccess(false);
			act.setMsg("异常错误");
			this.getResponse(response, jo.toJSONString(act));
		}
	}
	@RequestMapping(value="checkEmpNumber")
	@ResponseBody
	public boolean checkEmpNumber(HttpServletRequest request,HttpServletResponse response,String empNumber){
		 return this.enterpriseUserService.selectByempNumber(empNumber, getEnterpriseInfo(request).getId())==null?false:true;
	}
	
	public void getResponse(HttpServletResponse response,String str){
		PrintWriter pw = null;
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.write(str);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	
	/**
	 * 批量新增员工
	 * @param listEnt
	 * @param enterpriseId
	 * @return
	 */
	public ActResult<String> newAddBatchEmp(List<EnterpriseUser> listEnt,
			Long enterpriseId,HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sb6 = new StringBuffer();
		ActResult<String> act_return = new ActResult<String>();
		int i = 0;
		List<EnterpriseUser> newEmps = new ArrayList<EnterpriseUser>();
		Enterprise ent= enterpriseService.getById(enterpriseId);
		String shopLink = StringUtils.isEmpty(ent.getEmpDefultAvatar())?null:(enterpriseService.getFirstShopId(enterpriseId)+"");
		for(int rows = 0;rows<listEnt.size();rows++){
			try {
				EnterpriseUser entUser = listEnt.get(rows);
				entUser.setEnterpriseId(enterpriseId);
				//添加员工信息  
				i = this.addEnterpriseUser(entUser,request,newEmps,ent.getEmpDefultAvatar(),shopLink);
			} catch (Exception e) {
				// TODO: handle exception
				sb.append((rows+2)+" ");
			}
			/**
			 * 1成功
			 * 2账号在本企业已存在
			 * 3账号在其他企业已存在
			 * -1 共通注册失败
			 * -2厂注册失败
			 * -3 企业注册失败
			 * -4用户企业id为空
			 * 	-5用户为他人亲友
			 * 	-6手机号或者邮箱已被其他人使用，请确认后重试、
			 * 0 参数为空
			 * */
			if(i==-5) {
				sb3.append((rows+2)+" ");
			} else if(i==-6) {
				sb6.append((rows+2)+" ");
			} else if(i<0){
				sb.append((rows+2)+" ");
			}else if(i>0){
				if(i==2){
					sb1.append((rows+2)+" ");
				}else if(i==3){
					sb2.append((rows+2)+" ");
				}
			}
		}
		if(sb.length()==0&&sb1.length()==0&&sb2.length()==0&&sb3.length()==0){
			act_return.setMsg("添加员工成功");
		}else{
			act_return.setMsg(
					sb.length() > 0 ? sb.append("行添加失败<br/>").toString(): ""
					+ (sb1.length() > 0 ? sb1.append("行账号在本企业已存在<br/>").toString() : "")
					+ (sb2.length() > 0 ? sb2.append("行账号在其他企业已存在<br/>").toString() : "")
					+ (sb3.length() > 0 ? sb3.append("行为其他员工亲友，为确保员工利益，请通知员工删除亲友后，再行添加<br/>").toString(): "")
					+ (sb6.length() > 0 ? sb6.append("行手机号或者邮箱已被其他人使用，请确认后重试、<br/>").toString(): ""));
		}
		
		if(!newEmps.isEmpty()){
			autoBenefit(request, newEmps);
		}
		act_return.setSuccess(true);
		return act_return;
	}
	/**
	 * 
	 * return 
	 * 	1成功
	 * 	2账号在本企业已存在
	 * 	3账号在其他企业已存在
	 *	-1 共通注册失败
	 * 	-2厂注册失败
	 * 	-3 企业注册失败
	 * 	-4用户企业id为空
	 * 	-5用户为他人亲友
	 * 	0 参数为空
	 * */
	private Integer addEnterpriseUser(EnterpriseUser addEntUser,HttpServletRequest request,List<EnterpriseUser> newEmps,String empDefultAvatar,String shopLink) {
		EnterpriseVo ent = getEnterpriseInfo(request);
		if(StringUtils.isEmpty(addEntUser)||StringUtils.isEmpty(addEntUser.getEnterpriseId())){
			return 0;
		}else{
			CommUser phoneUser = null;
			CommUser emailUser = null;

			// 通过手机号获得共通用户信息
			ActResult<CommUser> actPhoneUser = us.findByPhone(addEntUser.getPhone());
			if(actPhoneUser.isSuccess()) {
				phoneUser = actPhoneUser.getData();
			}
			
			// 通过邮箱获得共通用户信息
			if(!StringUtils.isNullOrEmpty(addEntUser.getEmail())) {
				ActResult<CommUser> actEmailUser = us.findByEmail(addEntUser.getEmail());
				if(actEmailUser.isSuccess()) {
					emailUser = actEmailUser.getData();
				}
			}
			
			if(phoneUser != null && emailUser!=null) {
				if(!phoneUser.getUserId().equals(emailUser.getUserId())) {
					// 手机用户 邮箱用户 不为同一个人时保存
					return -6;
				}
			}
			
			CommUser commUser = phoneUser==null?emailUser:phoneUser; 
			
			//判断共通用户
			if(commUser==null) {
				//共通用户不存在 ，注册共通用户
				ActResult<CommUser> actUser = this.registerCommonByPhone(addEntUser.getPhone(),addEntUser.getEmail(),1,addEntUser.getName());

				//共通注册成功
				if(actUser.isSuccess()){
					
					CommUser user = actUser.getData();
					//注册厂用户
					this.registerFactoryUser(user.getUserId(), addEntUser.getPhone(), addEntUser.getEmail(),
							user.getCreatedTime(), addEntUser.getEnterpriseId(), empDefultAvatar, shopLink,
							addEntUser.getName());

					addEntUser.setId(user.getUserId());
					//注册员工信息
					EnterpriseUser entEmp = this.registerEnterpriseUser(addEntUser,2);
					//this.sendSms(addEntUser.getPhone(),ent.getName()+" (公司)已加入我的福利联合内购平台，公司将定期给您发送福利，请随时登陆关注。更多员工专享福利尽在我的福利平台。登陆账号及密码均为您收到此短信的手机号码，更多内容猛戳 http://wd-w.com/a.htm，回复ＴＤ退订");
					
					if(entEmp!=null){
						newEmps.add(entEmp);
						return 1;
					}else{
						return -3;
					}
				}else{
					return -1;
				}
			} else {
				//共通用户已存在
				EnterpriseUser entVo = enterpriseUserService.getById(commUser.getUserId());
				/**
				 * 该手机号在企业表中不存在，需要注册
				 * */
				if(StringUtils.isNullOrEmpty(entVo)){
					//企业用户不存在   判断该手机号在factory厂存在不存在
					UserFactory uf = userService.getById(commUser.getUserId());
					if(StringUtils.isNullOrEmpty(uf)){
						/**
						 * factory不存在 
						 * 		 注册共通
						 * 		 注册factory
						 * 		 注册企业
						 * */
							
						// 注册厂用户
						this.registerFactoryUser(commUser.getUserId(), addEntUser.getPhone(), addEntUser.getEmail(),
								commUser.getCreatedTime(), addEntUser.getEnterpriseId(), empDefultAvatar, shopLink,addEntUser.getName());

						addEntUser.setId(commUser.getUserId());
						//注册员工信息
						EnterpriseUser entEmp = this.registerEnterpriseUser(addEntUser,2);
						//this.sendSms(addEntUser.getPhone(),ent.getName()+" (公司)已加入我的福利联合内购平台，公司将定期给您发送福利，请随时登陆关注。更多员工专享福利尽在我的福利平台。登陆账号及密码均为您收到此短信的手机号码，更多内容猛戳 http://wd-w.com/a.htm，回复ＴＤ退订");
						
						if(entEmp!=null){
							newEmps.add(entEmp);
							return 1;
						}else{
							return -3;
						}
					/**
					 * 只要factory存在该用户，表示共通中已经存在用户了。
					 * 
					 * factory用户存在  注册企业用户,并将factory用户更新成员工
					 * */
					}else{
						
						addEntUser.setId(uf.getId());
						//注册企业用户
						EnterpriseUser entEmp = this.registerEnterpriseUser(addEntUser,2);
						if(entEmp!=null){
							//用户类型：0普通，1员工，2亲友  
							uf.setEmployeeType(1);
							//企业id（用户属于哪个企业）
							uf.setSupplierId(addEntUser.getEnterpriseId());
							if(StringUtils.isEmpty(uf.getEmail()) && !StringUtils.isEmpty(addEntUser.getEmail())) {
								uf.setEmail(addEntUser.getEmail());
								us.updateEmail(addEntUser.getEmail(), uf.getId(), UserConstant.COMFROM);
							}
							uf.setPhone(uf.getPhone()!=null?uf.getPhone():addEntUser.getPhone());
							if(StringUtils.isEmpty(uf.getAvatar())) {
								uf.setAvatar(ent.getEmpDefultAvatar());
								uf.setShopLink(shopLink);
							}
							//更新factory用户表中用户类型和企业id
							this.userService.update(uf);

							newEmps.add(entEmp);
							return 1;
						} else {
							return -3;
						}
					}
					
				} else {
					/**
					 * 该手机号在企业中存在,需要判断该手机号是不是本企业用户,如果不是，需要提示用户,如果是本企业的那就提示用户
					 * 
					 * 但是共通中的用户信息是删除前的信息，修改的只是企业子系统中用户的信息
					 * 
					 * */
					
					//1   已注销(已注销用户的企业id不存在)
					if(entVo.getLogout().equals(new Byte("1"))){
						addEntUser.setId(entVo.getId());
						addEntUser.setLogout(new Byte("0"));
						addEntUser.setType(2);
						addEntUser.setUserName(addEntUser.getPhone());
						this.enterpriseUserService.updateEmp(addEntUser);
						//添加企业员工。员工账号存在,判断员工账号的类型 。 1为员工2为亲友
						UserFactory uf = userService.getById(entVo.getId());
						if(!StringUtils.isNullOrEmpty(uf)&&uf.getEmployeeType()!=1){
							
							uf.setEmployeeType(1);
							//企业id（用户属于哪个企业）
							uf.setSupplierId(addEntUser.getEnterpriseId());
							if(StringUtils.isEmpty(uf.getEmail()) && !StringUtils.isEmpty(addEntUser.getEmail())) {
								uf.setEmail(addEntUser.getEmail());
								us.updateEmail(addEntUser.getEmail(), uf.getId(), UserConstant.COMFROM);
							}
							uf.setPhone(uf.getPhone()!=null?uf.getPhone():addEntUser.getPhone());
							if(StringUtils.isEmpty(uf.getAvatar())) {
								uf.setAvatar(ent.getEmpDefultAvatar());
								uf.setShopLink(shopLink);
							}
							this.userService.update(uf);
						}
						return 1;
					}
					if(StringUtils.isNullOrEmpty(entVo.getEnterpriseId())){
						//员工企业id不存在，表示该数据有误
						return -4;
					}else{
						//管理员的企业id和被添加的用户企业id相等
						if(addEntUser.getEnterpriseId().equals(entVo.getEnterpriseId())){
							//有更新用户,变为提示用户该账号在本企业已注册
//							addEntUser.setId(entVo.getId());
//							return this.updateEmpUser(addEntUser);
							return 2;
						}else{
							return 3;
						}
					}
				}
			}
		}
	}
	
	/**注册企业用户
	 * @param userId
	 * @param email
	 * @return
	 */
	private EnterpriseUser registerEnterpriseUser(EnterpriseUser entuser,int type){
		EnterpriseUser entUser = new EnterpriseUser();
		entUser.setId(entuser.getId());
		//企业注册
		if(type==1 && !StringUtils.isEmpty(entuser.getEmail())&&!StringUtils.isEmpty(entuser.getId())){
			//企业id自己生成
			entUser.setEnterpriseId(dbUtils.CreateID());
			entUser.setEmail(entuser.getEmail());
			entUser.setUserName(entuser.getEmail());
			entUser.setType(1);
		//员工注册
		}else if(type==2 &&!StringUtils.isEmpty(entuser.getEnterpriseId())&&!StringUtils.isEmpty(entuser.getId())){
			entUser.setEnterpriseId(entuser.getEnterpriseId());
			if (!StringUtils.isEmpty(entuser.getPhone())) {
				entUser.setUserName(entuser.getPhone());
			}else{
				entUser.setUserName(entuser.getEmail());
			}
			entUser.setType(2);
			entUser.setAge(entuser.getAge());
			entUser.setEmpNumber(entuser.getEmpNumber());
			entUser.setName(entuser.getName());
			entUser.setPhone(entuser.getPhone());
			entUser.setEmail(entuser.getEmail());
			entUser.setSeniority(entuser.getSeniority());
			entUser.setSex(entuser.getSex());
			entUser.setDuty(entuser.getDuty());
			entUser.setSectionName(entuser.getSectionName());
			entUser.setWelfareLevel(entuser.getWelfareLevel());
			entUser.setLogout(Byte.valueOf("0"));
		}else{
			return null;
		}
		this.enterpriseUserService.insertSelective(entUser);
		return entUser;
		
	}
	
	/**注册共通的用户，用手机号注册
	 * @param phone
	 * @return
	 */
	private ActResult<CommUser> registerCommonByPhone(String phone,String email,int userType,String e_name){
		CommUser user = new CommUser();
		String nickName = "";
		if (StringUtils.isNullOrEmpty(phone)) {
			user.setUserName(email);
			nickName = email;
		}else{
			user.setUserName(phone);
			nickName=phone;
			if(nickName.length()>4) {
				nickName="1***"+nickName.substring(nickName.length()-4);
			}
		}
		if(!StringUtils.isEmpty(e_name)) {
			user.setNickName(e_name);
		} else {
			user.setNickName(nickName);
		}
		user.setPassword(phone);
		user.setUserPhone(phone);
		user.setUserEmail(email);
		user.setUsable(1);
		user.setUserType(userType);
		user.setEnabled(1);//用户默认是被激活的
		user.setUserCom("company");
		user.setUserIp(getIpAddr(re));
		return us.insert(user);
	}

	/**
	 * 新增数据
	 * @param request
	 * @param response
	 * @param entUser
	 * @return
	 */
	@RequestMapping(value = "ajaxGetEmpLevelCount", method = RequestMethod.GET)
	@Token(remove = true)
	public ModelMap ajaxGetEmpLevelCount(HttpServletRequest request,ModelMap model){
		EnterpriseVo ent = getEnterpriseInfo(request);
		// 查询各等级人数
		EmpLevelCountVo vo = new EmpLevelCountVo();
		vo.setEnterpriseId(ent.getId());
		List<EmpLevelCountVo> lsc = enterpriseUserService.selectLevelCount(vo);

		model.addAttribute("lsc", lsc);
		return model;
	}
	
	/**
	 * 注册厂买家信息，并激活
	 * @param userId
	 * @param email
	 * @param creatDate
	 * @param enterpriseId
	 * @return
	 */
	private Boolean registerFactoryUser(Long userId,String phone,String email,Date creatDate,Long enterpriseId,String empDefultAvatar,String shopLink,String e_name){
	 	UserFactory fus = new UserFactory();
		fus.setId(userId);
		String nickName="";
		if (!StringUtils.isNullOrEmpty(phone)) {
			fus.setUserName(phone);
			nickName=phone;
			if(nickName.length()>4) {
				nickName="1***"+nickName.substring(nickName.length()-4);
			}
			
		}else{
			fus.setUserName(email);
			nickName = email;
		}
		if(!StringUtils.isEmpty(e_name)) {
			fus.setNickName(e_name);
		} else {
			fus.setNickName(nickName);
		}
		fus.setEmail(email);
		fus.setPhone(phone);
		//禁用标示  0:禁用。1:未禁用
		fus.setUsable(1);
		//类型 2:是商家，1:是买家
		fus.setType(1);//2
		//是否激活  0:未激活。1:表示已激活
		fus.setEnabled(1);//0
		//用户等级，默认为0
		fus.setUserLevel(0);
		fus.setCreatTime(creatDate);
		//用户类型：0普通，1员工，2亲友  
		fus.setEmployeeType(1);
		//企业id
		fus.setSupplierId(enterpriseId);
		fus.setAvatar(empDefultAvatar);
		fus.setShopLink(shopLink);
		userService.saveId(fus);
		return true;
    }
	
	private static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

