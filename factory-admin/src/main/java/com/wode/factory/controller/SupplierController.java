package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.EmailUtil;
import com.wode.common.util.FileUtils;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.SeasonUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.model.ApprSupplierExit;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.EntBenefitAppr;
import com.wode.factory.model.EntSeasonAct;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserShare;
import com.wode.factory.model.UserShareAutoBuy;
import com.wode.factory.model.UserShareTicket;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.ApprSupplierExitService;
import com.wode.factory.service.ApprSupplierService;
import com.wode.factory.service.EntBenefitApprService;
import com.wode.factory.service.EnterpriseService;
import com.wode.factory.service.EnterpriseUserService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.service.ProductSpecificationsService;
import com.wode.factory.service.SaleDurationParamService;
import com.wode.factory.service.SupplierCategoryService;
import com.wode.factory.service.SupplierDurationVoService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserFactoryService;
import com.wode.factory.service.UserShareAutoBuyService;
import com.wode.factory.service.UserShareService;
import com.wode.factory.service.UserShareTicketService;
import com.wode.factory.vo.EntBenefitApprVO;
import com.wode.factory.vo.EnterpriseVo;
import com.wode.factory.vo.SupplierExchangeProductVo;
import com.wode.factory.vo.SupplierVo;
import com.wode.factory.vo.UserFactoryVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;
import com.wode.sys.service.SysUserService;
import com.wode.tongji.model.AccountingLog;
import com.wode.tongji.model.ManagerBusiness;
import com.wode.tongji.service.ManagerBusinessService;


/**
 * @author mkx
 *
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("supplierList")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	SupplierCategoryService supplierCategoryService;
	@Autowired
	SaleDurationParamService saleDurationParamService;
	@Autowired
	SupplierDurationVoService supplierDurationVoService;
	@Autowired
	SupplierCloseFacade supplierCloseFacade;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	@Qualifier("entBenefitApprService")
	private EntBenefitApprService entBenefitApprService;
	@Autowired
	@Qualifier("enterpriseUserService")
	private EnterpriseUserService enterpriseUserService;
	@Resource
	private ProductService productService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private UserFactoryService userFactoryService;
	@Autowired
	ApprSupplierService apprSupplierService;
	@Autowired
	ApprSupplierExitService apprSupplierExitService;
	@Autowired
	EntBenefitFacade entBenefitFacade;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ManagerBusinessService managerBusinessService;
	@Resource
	private UserShareAutoBuyService userShareAutoBuyService;
	@Resource
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	@Autowired
	RedisUtil redis;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;
	
	@Autowired
	private UserShareService userShareService;
//	@Autowired
//	private LogService logService;
	@Autowired
	@Qualifier("emailUtil")
	private EmailUtil emailUtil;

	@Value("#{configProperties['front.creat_htmlurl']}")
	private  String apiUrl;
	@Autowired
	private UserShareTicketService userShareTicketService;
	@RequestMapping("toActiveEmail")
	public String toActiveEmail(Model model){
		return "sys/supplier/email-active";
	}
	
	@RequestMapping("activeEmail")
	@ResponseBody
	public ActResult<String> activeEmail(String email, Long id,HttpSession session) {
		UserFactory model = new UserFactory();
		model.setEmail(email);
		model.setEmployeeType(-1); //全部数据
		List<UserFactory> ls =userFactoryService.selectByModel(model);
		if(ls==null || ls.isEmpty()) return ActResult.fail("该邮箱未注册过，或者数据有误，请仔细确认邮箱是否输入正确。");
		UserFactory u = ls.get(0);
		if(u.getType() != 2 && u.getType() != 3)  return ActResult.fail("该邮箱不是商家注册账号");
		if(u.getEnabled() == 1)  return ActResult.fail("该邮箱已激活");
		//判断用户是否为启用状态
		CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
		ActResult<String> act = us.active(u.getId(), "myFactory","0",false,null);
		if (!act.isSuccess()) {
			return ActResult.fail(act.getMsg());
		} else {
			u.setEnabled(1);
			userFactoryService.update(u);
			redis.delMapData(RedisConstant.FACTORY_USER_MAP, u.getId()+"");
			return ActResult.success("激活成功");
		}
		//判断用户是否为启用状态
	}
	
	@RequestMapping("view")
	public String toSupplierView(Model model,HttpSession session){
		model.addAttribute("viewStatus", "view");
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("officeId", 0);
		params.put("officeType", "");
		params.put("roles", "-108,-111");
		params.put("pageNum", 1);
		params.put("pageSize", 120);

		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		
		model.addAttribute("mlist", list);
		model.addAttribute("uid", user.getId());
		return "sys/supplier/supplier-base";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model,String viewStatus,HttpSession session) {
		String[] str = viewStatus.split(",");
		if(str.length>0)
			viewStatus = str[0];
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}

		Calendar firstc = Calendar.getInstance();
		firstc.set(Calendar.DAY_OF_MONTH,1);
		firstc.set(Calendar.HOUR_OF_DAY, 0);
		firstc.set(Calendar.MINUTE, 0);
		firstc.set(Calendar.SECOND, 0);
		PageInfo pageInfo = supplierService.findSupplierCount(params);
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
//		for (Object obj : pageInfo.getList()) {
//			SupplierVo s=(SupplierVo)obj;
//			//经营品牌数
//			s.setComTel1(productBrandService.getCountBySupplier(s.getId()).toString());
//			//在售品牌数
//			s.setComTel2(productBrandService.getCountBySupplierForSale(s.getId()).toString());
//			//本月新增品牌数
//			s.setShopTel2(productBrandService.getCountBySupplierForSaleDate(s.getId(), firstc.getTime(), now).toString());
//			//上架商品数
//			s.setComTel3(productService.getCountBySupplier(s.getId()).toString());
//			//本月新增商品数
//			s.setShopTel3(productService.getCountBySupplierDate(s.getId(), firstc.getTime(), now).toString());
//			//企业员工数
//			s.setComPortraiture1(this.enterpriseUserService.findEnterprisePeopleNumber(s.getId()).toString());
//			//活跃员工数
//			s.setComPortraiture2(this.enterpriseUserService.findEnterpriseActivePeopleCnt(s.getId()).toString());
//			//本月新增员工数
//			s.setShopTel1(this.enterpriseUserService.findEnterpriseActivePeopleCntDate(s.getId(), firstc.getTime(), now).toString());
//		}
		model.addAttribute("uid", user.getId());
		model.addAttribute("page", pageInfo);
		return "sys/supplier/supplier-list-view";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void downLoadExcel(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 1000);
		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		}
		PageInfo pageInfo = supplierService.findSupplierCount(params);

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("商家一览"); 
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
        headers.add("商家");
        headers.add("品牌商");
        headers.add("类型");
        headers.add("入驻品牌数");
        headers.add("在售品牌数");
        headers.add("本周新增品牌数");
        headers.add("上架商品数");
        headers.add("本周新增商品数");
        headers.add("客服联系人");
        headers.add("客服QQ");
        headers.add("客服固定电话");
        headers.add("客服手机");
        headers.add("客服邮箱");
        headers.add("企业人数");
        headers.add("已注册员工数");
        headers.add("活跃员工数");
        headers.add("本周新增员工数");
        headers.add("招商经理");
        headers.add("创建时间");
        headers.add("商家ID");
                
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

		Calendar firstc = Calendar.getInstance();
		firstc.set(Calendar.DAY_OF_MONTH,1);
		firstc.set(Calendar.HOUR_OF_DAY, 0);
		firstc.set(Calendar.MINUTE, 0);
		firstc.set(Calendar.SECOND, 0);
        /** 设置订单详情表头 end
         * */
        int currentRow = 0;
		for (Object obj : pageInfo.getList()) {
			SupplierVo s=(SupplierVo)obj;
			currentRow++;
        	//行  
            row = sheet.createRow(currentRow); 
            row.createCell(0).setCellValue(s.getComName()); //商家
            row.createCell(1).setCellValue(s.getBrandOwner()); //商家
            String property = s.getProperty();
            if("0".equals(property)) {
                row.createCell(2).setCellValue("生产厂商"); //商家
            } else if("1".equals(property)) {
                row.createCell(2).setCellValue("品牌商"); //商家
            } else {
                row.createCell(2).setCellValue("代理商"); //商家            	
            }
            row.createCell(3).setCellValue(s.getComTel1()); //入驻品牌数
            row.createCell(4).setCellValue(s.getComTel2()); //在售品牌数
            row.createCell(5).setCellValue(s.getShopTel2()); //本月新增品牌数
            row.createCell(6).setCellValue(s.getComTel3()); //上架商品数
            row.createCell(7).setCellValue(s.getShopTel3()); //本月新增商品数
            String serContact = s.getSerContact();
            if (StringUtils.isEmpty(serContact)) {
				row.createCell(8).setCellValue("0");
			}else{
				row.createCell(8).setCellValue(s.getSerContact()); //客服联系人
			}
            Long qq = s.getQq();
            if (qq == null) {
				row.createCell(9).setCellValue("0");
			}else{
				row.createCell(9).setCellValue(s.getQq()); //客服qq
			}
            String serTel1 = s.getSerTel1();
            if (StringUtils.isEmpty(serTel1)) {
				row.createCell(10).setCellValue("0");
			}else{
				row.createCell(10).setCellValue(s.getSerTel1()); //客服固定电话
			}
            Long serPhone = s.getSerPhone();
            if (serPhone == null) {
				row.createCell(11).setCellValue("0");
			}else{
				row.createCell(11).setCellValue(s.getSerPhone()); //客服手机
			}
            String serEmail = s.getSerEmail();
            if (StringUtils.isEmpty(serEmail)) {
				row.createCell(12).setCellValue("0");
			}else{
				row.createCell(12).setCellValue(s.getSerEmail()); //客服邮箱
			}
            Integer peopleNumber = s.getPeopleNumber();
            if (peopleNumber == null) {
				row.createCell(13).setCellValue("0");
			}else{
				row.createCell(13).setCellValue(s.getPeopleNumber());//企业人数
			}
            row.createCell(14).setCellValue(s.getComPortraiture1()); //已注册员工数
            row.createCell(15).setCellValue(s.getComPortraiture2()); //活跃员工数
            row.createCell(16).setCellValue(s.getShopTel3()); //本月新增员工数
            row.createCell(17).setCellValue(s.getManagerName()); //招商经理
            row.createCell(18,HSSFCell.CELL_TYPE_STRING).setCellValue(DateUtils.formatDate(s.getCreatTime(),"yyyy-MM-dd")); //创建时间
            row.createCell(19,HSSFCell.CELL_TYPE_STRING).setCellValue(s.getId()+""); //商家id
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request, "商家一览"));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 功能说明： 查询退出申请信息
	 * 日期 ：2017年9月19日
	 * @param id
	 * @return
	 */
	@RequestMapping("/exit/showlayer")
	public ModelAndView exit(Long id){
		ModelAndView modelAndView = new ModelAndView("sys/supplier/supplier-exit-view");
		//商家信息
		Supplier supplier = supplierService.getSupplierDetailWithItems(id);
		//获取商家是否已经存在退出申请的信息
		ApprSupplierExit apprSupExit = new ApprSupplierExit();
		apprSupExit.setSupplierId(id);
		List<ApprSupplierExit> apprSupExitList = apprSupplierExitService.selectByModel(apprSupExit);
		if(apprSupExitList.size() > 0){
			apprSupExit = apprSupExitList.get(0);
		}
		apprSupExit.setSupplierName(supplier.getComName());
		apprSupExit.setJoinTime(supplier.getCreatTime());
		apprSupExit.setStatus(apprSupExit.getStatus() == null ?0:apprSupExit.getStatus());
		modelAndView.addObject("apprSupExit", apprSupExit);
		//获取关于商家审核表的所有信息
		List<CheckOpinion> checkList = new ArrayList<CheckOpinion>();
		if(apprSupExit.getId() != null){
			checkList = supplierService.getAllCheckOpinionBySupplierId(apprSupExit.getId());
		}
		modelAndView.addObject("checkList", checkList);
		return modelAndView;
	}
	/**
	 * 功能说明： 保存（修改）退出申请信息
	 * 日期 ：2017年9月19日
	 * @param pageTemplateSection
	 * @param session
	 * @return
	 */
	@RequestMapping("/exitSave")
	@ResponseBody
	public ActResult<String> pageTemplateSectionSave(ApprSupplierExit apprSupplierExit, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		//获取商家的信息保存进退出申请表中（备份）
		Supplier supplier = supplierService.getSupplierDetailWithItems(apprSupplierExit.getSupplierId());
		apprSupplierExit.setManagerId(supplier.getManagerId());
		apprSupplierExit.setManagerName(supplier.getManagerName());
		apprSupplierExit.setProperty(supplier.getProperty());
		apprSupplierExit.setBusScope(supplier.getBusScope());
		apprSupplierExit.setComRegisternum(supplier.getComRegisternum());
		apprSupplierExit.setComAdd(supplier.getComAdd());
		apprSupplierExit.setComAddress(supplier.getComAddress());
		apprSupplierExit.setComCity(supplier.getComCity());
		apprSupplierExit.setComPc(supplier.getComPc());
		apprSupplierExit.setComState(supplier.getComState());
		apprSupplierExit.setComTel(supplier.getComTel());
		apprSupplierExit.setPeopleNumber(supplier.getPeopleNumber());
		apprSupplierExit.setEditTime(new Date());
		apprSupplierExit.setUpdateUser(user.getUsername());
		apprSupplierExit.setUpdateTime(new Date());
		int status = apprSupplierExit.getStatus();
		boolean flag = false;//是否启用删除操作标示
		//获取未结订单总数、未结对账单数、现金券余额
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("supplierId", supplier.getId());
		List<ApprSupplierExit> counts = apprSupplierExitService.findCountsByMap(map);
		EntSeasonAct esa = entBenefitFacade.getEntSeasonAct(apprSupplierExit.getSupplierId(), SeasonUtil.getNowYear(), SeasonUtil.getNowSeason(),user.getName());
		if(counts.get(0).getProductCnt() > 0 && apprSupplierExit.getStatus() == 5){
			apprSupplierExit.setStatus(2);
			flag = true;
		}
		if(apprSupplierExit.getStatus() == 5 &&	(counts.get(3).getUnClosebillCnt() > 0 || counts.get(2).getUnCloseOrderCnt() > 0 || esa.getAllCashSum().subtract(esa.getGiveCashSum()).compareTo(BigDecimal.ZERO) == 1)){
			apprSupplierExit.setStatus(3);
			flag = true;
		}
		//开启删除商家所有关联信息操作
		if(status == 5 && flag == false){
			apprSupplierExitService.deleteSupplierCorrelationMsg(apprSupplierExit.getSupplierId());
		}
		apprSupplierExitService.saveOrUpdate(apprSupplierExit);
		//判断验证上级审核并存储审核意见信息表
		if(status >= 3 || status == 1){
			CheckOpinion checkOpinion = new CheckOpinion();
			checkOpinion.setType(0);
			checkOpinion.setCheckId(apprSupplierExit.getId());
			checkOpinion.setOpinion(apprSupplierExit.getCheckOpinion());
			checkOpinion.setUserId(user.getId());
			checkOpinion.setUsername(user.getName());
			checkOpinion.setResult(apprSupplierExit.getStatus() >= 3?2:-1);
			checkOpinion.setTime(new Date());
			supplierService.saveCheckOpinion(checkOpinion);
		}
		if(flag == true){
			if(counts.get(0).getProductCnt() > 0){
				return ActResult.fail("处理失败，原因：该商家还存有在线商品数，结果反馈到运营审核");
			}else{
				return ActResult.fail("处理失败，原因：该商家存在未结订单总数或未结对账单数或现金券余额，结果反馈到财务审核");
			}
		}
		//向各阶段审核人员发送邮件
		SysUser manager = sysUserService.selectByPrimaryKey(supplier.getManagerId());
		StringBuffer msg = new StringBuffer();
		if(!StringUtils.isEmpty(supplier.getManagerId())) {
			if(manager!=null) {
				//向招商人员发送邮件
				if(!StringUtils.isEmpty(manager.getEmail()) && status == 1) {
					msg.append("您的退出申请已得到反馈，请查看");
					emailUtil.sendSupplierExitCheckEmailForUs(manager.getEmail(),msg.toString());
				}
				
				// 向 运营发出邮件
				ManagerBusiness mb = managerBusinessService.getById(manager.getId());
				if(mb!=null && !StringUtils.isEmpty(mb.getBusinessEmail()) && (status == 2 || status == 4)){
					if(status == 2){
						msg.append("您的商家有新的退出申请需要处理");
					}else{
						msg.append("您的商家有新的退出执行需要处理");
					}
					emailUtil.sendSupplierExitCheckEmailForUs(mb.getBusinessEmail(),msg.toString());										
				}
				//向财务发送邮件
				if(status == 3){
					msg.append("您的商家有新的退出申请需要处理");
					emailUtil.sendSupplierExitCheckEmailForUs("yantaotao@wo-de.com",msg.toString());
				}
			}
		}
		return ActResult.success("");
	}
	/**
	 * 
	 * 功能说明： 查询商家详细
	 * 日期:	2015年7月24日
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail/showlayer")
	public String detail(Model model,Long id,String viewStatus) {
		//商家信息
		Supplier orderDetail = supplierService.getSupplierDetailWithItems(id);
		for (Shop shop : orderDetail.getShopList()) {
			//经营类目信息
			shop.setProductCategoryList(supplierService.getProductCategoryListBySupplierId(id,shop.getId()));
			//行业资质信息
			shop.setAttachmentList(supplierService.getAttachmentListBySupplierId(id,shop.getId()));
			//品牌信息
			shop.setProductBrandList(supplierService.getProductBrandListBySupplierId(id,shop.getId()));
		}
		
		//审核信息
		List<CheckOpinion> checkList = supplierService.getAllCheckOpinionBySupplierId(id);
		//账单日(结算)类型信息
		List<SaleDurationParam> saleParam = this.saleDurationParamService.findAll();
		//查询账单日
		SupplierDuration sd = this.supplierDurationVoService.getById(id);
		if(StringUtils.isEmpty(sd)){
			sd = new SupplierDuration();
		}
		if(StringUtils.isEmpty(sd.getStartTime())){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 3);
			model.addAttribute("startTime", calendar.getTime());
		}

		EnterpriseVo pojo = enterpriseService.getById(id);
		model.addAttribute("enterprise", pojo);
		EntBenefitAppr appr = this.entBenefitApprService.getById(id);
		model.addAttribute("entBenefitAppr", appr);
		model.addAttribute("peopleNumber", this.enterpriseUserService.findEnterprisePeopleNumber(id));
		model.addAttribute("curTotal", getCurrentTotal(id));
		
		model.addAttribute("supplierDuration", sd);
		model.addAttribute("saleParam", saleParam);
		model.addAttribute("supplier", orderDetail);

		String sdName ="";
		if(null != sd && sd.getSaleDurationKey().startsWith("3")){
			
			//截取3xx系列后面xx转换为账期天数
    		String	key = sd.getSaleDurationKey().substring(1,sd.getSaleDurationKey().length());
    		SaleDurationParam saleDurationParam = saleDurationParamService.selectByKey(sd.getSaleDurationKey());
    		if(null != saleDurationParam ){
    			sdName = "满" + saleDurationParam.getValue();
    		}
    		sdName += "或" +  Integer.parseInt(key) + "天";
		} else {
			if(sd!=null) {
	    		SaleDurationParam saleDurationParam = saleDurationParamService.selectByKey(sd.getSaleDurationKey());
				sdName=saleDurationParam.getCaption();
			}
		}
		UserShare userShare = userShareService.getByUserId(id);
		List<UserShareAutoBuy> skulst;
		if(userShare!=null) {
			// 为jsp 方便处理，暂时交换
			String wxUrl = userShare.getWxTempQrUrl();
			if(!StringUtils.isEmpty(wxUrl)) {
				userShare.setWxTempQrUrl(userShare.getShareUrl());
				userShare.setShareUrl(wxUrl);
			}
			UserShareAutoBuy query = new UserShareAutoBuy();
			query.setShareId(userShare.getId());
			skulst= userShareAutoBuyService.selectByModel(query);
		} else {
			skulst = new ArrayList<UserShareAutoBuy>();
		}
		BigDecimal avgAmount = BigDecimal.ZERO;
		if(userShare!=null) {
			List<SupplierExchangeProductVo> sep = supplierExchangeProductService.findProductBySupplierId(userShare.getId());
			if(sep.size()>0) {
				for (SupplierExchangeProductVo supplierExchangeProductVo : sep) {
					avgAmount = avgAmount.add(supplierExchangeProductVo.getEmpAvgAmount());
				}
			}
			model.addAttribute("sep", sep);
		}
		model.addAttribute("count", avgAmount);
		model.addAttribute("userBuySkus", skulst);
		model.addAttribute("userShare", userShare);
		model.addAttribute("sdName", sdName);
		
		//model.addAttribute("productCategory", productCategoryList);
		//model.addAttribute("attachment", attachmentList);
		//model.addAttribute("productBrand", productBrandList);
		model.addAttribute("checkList", checkList);
		model.addAttribute("apiUrl", Constant.FACTORY_API_URL);
		model.addAttribute("webUrl", Constant.FACTORY_WEB_URL);
		return "sys/supplier/supplier-detail-view";
	}
	@RequestMapping("/commission-rate/get")
	public String commissionRate(Model model,Long id) {
		BigDecimal commissionRate = supplierService.getCommissionRate(id);
		model.addAttribute("commissionRate", commissionRate);
		model.addAttribute("id", id);
		return "sys/supplier/commissionRate";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/commission-rate", method = RequestMethod.POST)
	@ResponseBody
	public ActResult updateCommissionRate(Long id, BigDecimal commissionRate) {
		if(commissionRate.floatValue() > 100) {
			return ActResult.fail("非法数值!");
		}
		supplierService.updateCommissionRate(commissionRate, id);
		return ActResult.successSetMsg(commissionRate.toString());
	}

	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showlayer(Model m,@PathVariable("mode") String mode,Long id,String cashDeposit,String platformUseFee){
		if("1".equals(mode)){
			m.addAttribute("id", id);
			m.addAttribute("cashDeposit", cashDeposit);
			return "sys/supplier/cashDeposit";
		}else if("2".equals(mode)){
			m.addAttribute("id", id);
			return "sys/supplier/platformUseFee";
		//企业信息
		}else if("enterprise".equals(mode)){
			List<Enterprise> listEnt = this.enterpriseService.findeAllEnterprise();
			if(StringUtils.isEmpty(id)){
				m.addAttribute("supplierId",id);
				m.addAttribute("supplierEntId", "");
			}else{
				Supplier supplier = this.supplierService.findByid(id);
				m.addAttribute("supplierId",id);
				m.addAttribute("supplierEntId", supplier.getEnterpriseId());
			}
			m.addAttribute("entInfo", listEnt);
			return "sys/supplier/supplier-enterprise";
		}else{
			return null;
		}
	}
	@RequestMapping("/setEntprise")
	@ResponseBody
	public int setEntprise(Long id,Long enterpriseId) {
		if(StringUtils.isEmpty(id)){
			return 0;
		}else{
			Supplier supplier = this.supplierService.findByid(id);
			supplier.setEnterpriseId(enterpriseId);
			//修改企业id
			this.supplierService.updateSupplierEnterpriseId(supplier);
			return 1;
		}
	}
	/**
	 * 
	 * 功能说明：设置保障金
	 * 日期:	2015年11月3日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping("/setCashDeposit")
	@ResponseBody
	public int setCashDeposit(@RequestParam Map<String, Object> params,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
				SysUser user = (SysUser)obj;
				AccountingLog al = new AccountingLog();
				al.setId(dbUtils.CreateID());
				al.setAct("设置保障金");
				al.setUserId(user.getId());
				al.setUsername(user.getName());
				al.setTime(new Date());
			return supplierService.updateSel(params,al);
		}
		return 0;
	}
	
	/**
	 * 
	 * 功能说明：设置平台使用费
	 * 日期:	2015年11月3日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping("/setFormUseFee")
	@ResponseBody
	public int setFormUseFee(@RequestParam Map<String, Object> params,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		if(obj !=null){
			SysUser user = (SysUser)obj;
			AccountingLog al = new AccountingLog();
			al.setId(dbUtils.CreateID());
			al.setAct("设置平台使用费");
			al.setUserId(user.getId());
			al.setUsername(user.getName());
			al.setTime(new Date());
			return supplierService.updateSel(params,al);
		}
		return 0;
		
	}

	private BigDecimal getCurrentTotal(Long enterpriseId) {
		BigDecimal total = BigDecimal.ZERO;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", 1);
		params.put("pageSize", 10);
		
		params.put("enterpriseId", enterpriseId);
		params.put("status", "2");
		params.put("curYear", SeasonUtil.getNowYear());
		params.put("curSeason", SeasonUtil.getNowSeason());
		PageInfo<EntBenefitApprVO> page = entBenefitApprService.findPage(params);
		if(page !=null && !page.getList().isEmpty()) {
			for (EntBenefitApprVO v : page.getList()) {
				if(v.getApplyLimit() != null) {
					total=total.add(v.getApplyLimit());
				}
			}
		}
		
		return total;
	}
	
	@RequestMapping("userBase")
	public String userBase(Model model,HttpSession session) {
		model.addAttribute("supplierList", getSupplierList());
		return "sys/supplier/user-base";
	}
	@RequestMapping(value = "userList", method = RequestMethod.POST)
	public String userList(@RequestParam Map<String, Object> params, Model model,HttpSession session) {
		PageInfo<UserFactoryVo> page =  userFactoryService.findList(params);
		for (UserFactoryVo vo : page.getList()) {
			if(StringUtils.isEmpty(vo.getSupplierName()) && vo.getSupplierId()!=null){
				// 未完成入驻的商家 设置用户 商家名称
				Map<String, Object> query = new HashMap<String, Object>();
				
				query.put("supplierId", vo.getSupplierId());
				query.put("pageNum", 1);
				query.put("pageSize", 10);
				PageInfo<ApprSupplier> apprs = apprSupplierService.findApprSupplier(query);
				if(!apprs.getList().isEmpty()) {
					vo.setSupplierName(apprs.getList().get(0).getComName());
				}
			}
		}
		model.addAttribute("page", page);		
		return "sys/supplier/user-list";
	}
	/**
	 * 删除用户
	* @param id 用户id
	* @return
	 */
	@RequestMapping(value="userReset",method = RequestMethod.POST)
	public @ResponseBody ActResult<String> userReset(Long id){
		if(id==null) return ActResult.fail("处理失败，请刷新后重试");

		CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
		return us.resetPassword(id, "myFactory",false,null);
	}
	
	private List<Supplier> getSupplierList() {
		Map<String, Object> query = new HashMap<String, Object>();
		
		query.put("status", 2);
		query.put("pageNum", 1);
		query.put("pageSize", 5000);
		
		// 查询已入驻商家列表
		List<Supplier> rtn = supplierService.getPage(query).getList();
		
		Map<Long,Long> supplierIdMap = new HashMap<Long,Long>();
		// 合并
		for (Supplier supplier : rtn) {
			supplierIdMap.put(supplier.getId(), supplier.getId());
		}
		

		// 查询未审核数据
		query.remove("status");
		PageInfo<ApprSupplier> apprs = apprSupplierService.findApprSupplier(query);
		
		for (ApprSupplier appr : apprs.getList()) {
			if(supplierIdMap.containsKey(appr.getSupplierId())) {
				continue;
			}
			
			Supplier supplier = new Supplier();
			supplier.setId(appr.getSupplierId());
			supplier.setComName(appr.getComName());
			
			supplierIdMap.put(supplier.getId(), supplier.getId());
			rtn.add(supplier);
		}
		return rtn;
	}
	
	//生成员工邀请码
	@RequestMapping("boundQRcode")
	@NoCheckLogin
	public @ResponseBody ActResult<String> boundQRcode(Model model,Long id) {
		Long ShareId = supplierService.boundQRcode(id);
		return ActResult.success(""+ShareId);
	}

	//生成员工邀请码
	@RequestMapping("saveTargetActionUrl")
	@NoCheckLogin
	public @ResponseBody ActResult<String> saveTargetActionUrl(Model model,Long shareId,String targetUrl) {
		
		UserShare us = userShareService.getById(shareId);
		if(us==null) {
			return ActResult.fail("数据有误，请刷新后重试");
		} else {
			if(targetUrl!=null) {
				targetUrl=targetUrl.trim();
			} else {
				targetUrl="";
			}
			String old = us.getTargetActionUrl();
			if(old==null) old="";
			
			if(!old.equals(targetUrl)) {
				if(StringUtils.isEmpty(targetUrl)) {
					us.setTargetActionUrl(null);
				} else {
					us.setTargetActionUrl(targetUrl);
				}
				userShareService.update(us);
				
				// 重新生成js
				Map<String,Object> paramMap=new HashMap<String,Object>();
				HttpClientUtil.sendHttpRequest("post", apiUrl+"/shareTargetJs", paramMap);
			}
			return ActResult.success(targetUrl);
		}
	}

	//生成员工邀请码 (微信关注)
	@RequestMapping("change2wxTempQrCode")
	@NoCheckLogin
	public @ResponseBody ActResult<JSONObject> change2wxTempQrCode(Model model,Long shareId) {
		UserShare us = userShareService.getById(shareId);
		if(us==null) {
			return ActResult.fail("数据有误，请刷新后重试");
		} else {
			JSONObject rtn =new JSONObject();
			try {
				WxOpenService wxo = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
				String qrUrl = wxo.getQRLink("company"+shareId, WxOpenService.MAX_EXPIRE_SECONDS);

				Calendar now = Calendar.getInstance();
				now.add(Calendar.DAY_OF_MONTH, 29);
				us.setWxTempQrUrl(qrUrl);
				us.setWxTempLimitEnd(now.getTime());
				userShareService.update(us);
				
				rtn.put("url", qrUrl);
				rtn.put("userNick", us.getUserNick());
				rtn.put("date", TimeUtil.dateToStr(now.getTime(), "yyyy-MM-dd"));
				return ActResult.success(rtn);
			} catch(Exception ex) {
				return ActResult.fail("系统异常，请稍后重试，或联系系统管理员");
			}
		}
	}
	/**
	 * 作废连接
	 * @param shareId
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/delCode")
	@ResponseBody
	public ActResult<String> delCode(Long shareId,Long supplierId) {
		if(shareId!=null){
			List<UserShareTicket> stList = userShareTicketService.getByShareId(shareId);
			if(stList!=null&&stList.size()>0) {
				for (UserShareTicket userShareTicket : stList) {
					userShareTicketService.removeById(userShareTicket.getId());
				}
			}
			userShareService.removeById(shareId);
			redis.del("REDIS_USER_SHARE_"+shareId);
			
			UserShareAutoBuy query = new UserShareAutoBuy();
			query.setShareId(shareId);
			List<UserShareAutoBuy> lst= userShareAutoBuyService.selectByModel(query);
			for (UserShareAutoBuy userShareAutoBuy : lst) {
				userShareAutoBuyService.removeById(userShareAutoBuy.getId());
			}
			return ActResult.success(supplierId);
		}else{
			return ActResult.fail("系统错误");
		}
	}
	

	//自动下单链接生成
	@RequestMapping("addAutoBuySku")
	@NoCheckLogin
	public @ResponseBody ActResult<JSONObject> addAutoBuySku(Long shareId,Long skuId,Integer skuNum) {
		if(shareId == null || skuId==null) {
			return ActResult.fail("数据有误，请刷新后重试");
		}
		if(skuNum==null || skuNum<1) skuNum=1;
		
		UserShare us = userShareService.getById(shareId);
		if(us==null) {
			return ActResult.fail("数据有误，请刷新后重试");
		} else {
			ProductSpecifications sku = productSpecificationsService.getById(skuId);
			if(sku==null) {
				return ActResult.fail("skuId 不存在请重新输入");
			}
			Product p = productService.getById(sku.getProductId());
			if(p==null) {
				return ActResult.fail("商品不存在请重新输入");
			}
			List<ProductSpecificationsImage> imgs = productSpecificationsImageService.findlistByProductSpecificationsid(skuId);
			UserShareAutoBuy skuBuy = new UserShareAutoBuy();
			skuBuy.setId(dbUtils.CreateID());
			skuBuy.setShareId(shareId);
			skuBuy.setProductId(p.getId());
			skuBuy.setSkuId(skuId);
			skuBuy.setSkuNum(skuNum);
			if(!StringUtils.isEmpty(imgs)) {
				skuBuy.setImage(imgs.get(0).getSource());
			}
			skuBuy.setProductName(p.getFullName());
			skuBuy.setItemValues(sku.getItemValues());
			skuBuy.setShareType(us.getShareType());
			JSONObject rtn =new JSONObject();
			try {
				WxOpenService wxo = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
				String qrUrl = wxo.getQRLink("autoBuyC"+skuBuy.getId(), WxOpenService.MAX_EXPIRE_SECONDS);

				Calendar now = Calendar.getInstance();
				now.add(Calendar.DAY_OF_MONTH, 29);
				skuBuy.setWxTempQrUrl(qrUrl);
				skuBuy.setWxTempLimitEnd(now.getTime());
				userShareAutoBuyService.save(skuBuy);
				
				rtn.put("data", skuBuy);
				rtn.put("date", TimeUtil.dateToStr(now.getTime(), "yyyy-MM-dd"));
				return ActResult.success(rtn);
			} catch(Exception ex) {
				return ActResult.fail("系统异常，请稍后重试，或联系系统管理员");
			}
		}
	}


	//自动下单链接生成
	@RequestMapping("delAutoBuySku")
	@NoCheckLogin
	public @ResponseBody ActResult<String> delAutoBuySku(Long autoId) {
		if(autoId == null) {
			return ActResult.fail("数据有误，请刷新后重试");		
		}
		userShareAutoBuyService.removeById(autoId);
		
		return ActResult.success("");
	}
	
}
