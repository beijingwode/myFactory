/**
 * 
 */
package com.wode.factory.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.DateUtils;
import com.wode.common.util.FileUtils;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.facade.EntBenefitFacade;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.facade.SupplierSaleCodeManageFacade;
import com.wode.factory.model.ExpressCompany;
import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;
import com.wode.factory.model.ServiceReceipt;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.service.CommissionRefundDetailService;
import com.wode.factory.service.CommissionRefundService;
import com.wode.factory.service.ExpressComService;
import com.wode.factory.service.OrderService;
import com.wode.factory.service.SaleBillService;
import com.wode.factory.service.ServiceReceiptService;
import com.wode.factory.service.SupplierDurationVoService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.SupplierTransferService;
import com.wode.factory.vo.ReceiptVo;
import com.wode.factory.vo.SaleBillDetailVo;
import com.wode.factory.vo.SuborderOrderVo;
import com.wode.factory.vo.SupplierTransferVo;
import com.wode.sys.mapper.SysUserMapper;
import com.wode.sys.model.SysUser;


/**
 * @author zcx
 *
 */
@Controller
@RequestMapping("saleBill")
public class SaleBillController {
	
	@Autowired
	private SaleBillService saleBillService;

	@Autowired
	private SupplierService supplierService;
	@Autowired
	SupplierDurationVoService supplierDurationVoService;
	@Autowired
	private ServiceReceiptService serviceReceiptService;
	@Autowired
	private ExpressComService expressComService;
	@Autowired
	private SupplierTransferService supplierTransferService;
	@Autowired
	private CommissionRefundService commissionRefundService;
	@Autowired
	private CommissionRefundDetailService commissionRefundDetailService;
	@Autowired
	private SupplierCloseFacade supplierCloseFacade;
	@Autowired
	private OrderService orderService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
    private SupplierSaleCodeManageFacade sscmf;
	@Autowired
	private EntBenefitFacade entBenefitFacade;

	@Autowired
	DBUtils dbUtils;
	
	@Value("#{configProperties['manager.leader']}")
	private  String leaders;

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager")
	public String toManager (@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(isLeander(user.getId())) {
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("officeId", 0);
			params2.put("officeType", "");
			params2.put("roles", "-108,-111");
			params2.put("pageNum", 1);
			params2.put("pageSize", 120);

			PageHelper.startPage(params2);
			List<SysUser> list = sysUserMapper.findPageInfo(params2);
			
			model.addAttribute("mlist", list);
		}
		return "manager/saleBill/saleBill-base-manager";//"sys/saleBill/saleBill-base";
	}

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/managerType")
	public String managerType(@RequestParam Map<String, Object> params,ModelMap model,Integer closeType,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(isLeander(user.getId())) {
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("officeId", 0);
			params2.put("officeType", "");
			params2.put("roles", "-108,-111");
			params2.put("pageNum", 1);
			params2.put("pageSize", 120);

			PageHelper.startPage(params2);
			List<SysUser> list = sysUserMapper.findPageInfo(params2);
			
			model.addAttribute("mlist", list);
		}
		model.addAttribute("closeType", closeType);
		return "manager/saleBill/saleBill-base-managerType";//"sys/saleBill/saleBill-base";
	}
	
	@RequestMapping("/view")
	public String toView(Model model){
		return "manager/saleBill/saleBill-base-view";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/business")
	public String toBusiness(Model model){
		return "manager/saleBill/saleBill-base-business";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/check")
	public String toCheck(Model model){
		return "manager/saleBill/saleBill-base-check";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/check201")
	public String toCheck201(Model model){
		return "manager/saleBill/saleBill-base-check201";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/finance")
	public String toFinance(Model model){
		return "manager/saleBill/saleBill-base-finance";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/finance201")
	public String toFinance201(Model model){
		return "manager/saleBill/saleBill-base-finance201";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/listBusiness")
	public String listBusiness(@RequestParam Map<String, Object> params,ModelMap model) {
		model.addAttribute("page", getSaiBillPage(params));
		return "manager/saleBill/saleBill-list";//"sys/saleBill/saleBill-list";
	}
	@RequestMapping("/listCheck")
	public String listCheck(@RequestParam Map<String, Object> params,ModelMap model) {
		model.addAttribute("page", getSaiBillPage(params));
		return "manager/saleBill/saleBill-list";//"sys/saleBill/saleBill-list";
	}
	@RequestMapping("/listFinance")
	public String listFinance(@RequestParam Map<String, Object> params,ModelMap model) {
		model.addAttribute("page", getSaiBillPage(params));
		return "manager/saleBill/saleBill-list";//"sys/saleBill/saleBill-list";
	}
	@RequestMapping("/listView")
	public String listView(@RequestParam Map<String, Object> params,ModelMap model) {
		model.addAttribute("page", getSaiBillPage(params));
		return "manager/saleBill/saleBill-list-view";//"sys/saleBill/saleBill-list";
	}
	
	private PageInfo<SaleBill> getSaiBillPage(Map<String, Object> params) {
		PageInfo<SaleBill> pageInfo = saleBillService.getPage(params);
		for (SaleBill sb : pageInfo.getList()) {
			setCashPay(sb);
		}
		return pageInfo;
	}
	
	private void setCashPay(SaleBill sb) {
		Map<String, Object> pos = new HashMap<String, Object>();
		pos.put("pageNum", 1);
		pos.put("pageSize", 10);
		pos.put("saleBillId", sb.getId());
		BigDecimal cash= BigDecimal.ZERO;
		PageInfo<SuborderOrderVo> orders= orderService.getSuborderList(pos);
		for (SuborderOrderVo vo : orders.getList()) {
			if(vo.getCashPay() != null) {
				cash=cash.add(vo.getCashPay());
			}
			if("pingtaiyue".equals(vo.getThirdType())) {
				if(vo.getThirdPay() != null) {
					cash=cash.add(vo.getThirdPay());
				}
			}
		}
		sb.setCashPay(cash);
		
        SupplierDuration sd = supplierDurationVoService.getById(sb.getSupplierId());
        Supplier supplier = supplierService.findByid(sb.getSupplierId());
        if("1".equals(sd.getAccountType())) {
        	sb.setBankName("支付宝");
        	sb.setBankNo(sd.getAlipayAccount());
        } else {
        	sb.setBankName(supplier.getBankId());
        	sb.setBankNo(supplier.getBankNum());
        }
	}

	
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/accountant")
	public String toAccountant(@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/saleBill-accountant-base";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/accountant201")
	public String toAccountant201(@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/saleBill-accountant-base201";//"sys/saleBill/saleBill-base";
	}
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/check_transfer")
	public String baseTransfer(@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/transfer-check-base";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/check_transfer201")
	public String baseTransfer201(@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/transfer-check-base201";//"sys/saleBill/saleBill-base";
	}

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/transfer")
	public String transfer (@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/transfer-base";//"sys/saleBill/saleBill-base";
	}
	@RequestMapping("/transfer201")
	public String transfer201(@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/transfer-base201";//"sys/saleBill/saleBill-base";
	}
	
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/invoice")
	public String toInvoice (@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/saleBill-invoice-base";//"sys/saleBill/saleBill-base";
	}

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/receipt")
	public String toReceipt (@RequestParam Map<String, Object> params,ModelMap model) {
		return "manager/saleBill/receipt-base";//"sys/saleBill/saleBill-base";
	}
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String queryFilter(@RequestParam Map<String, Object> params,ModelMap model) {
		PageInfo pageInfo = getSaiBillPage(params);
		model.addAttribute("page", pageInfo);
		return "manager/saleBill/saleBill-list";//"sys/saleBill/saleBill-list";
	}
	
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/receiptlist")
	public String receiptlist(@RequestParam Map<String, Object> params,ModelMap model) {
		PageInfo pageInfo = serviceReceiptService.getPage(params);
		model.addAttribute("page", pageInfo);
		return "manager/saleBill/receipt-list";//"sys/saleBill/saleBill-list";
	}
	
	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/list_manager")
	public String queryFilterManger(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(!isLeander(user.getId())) {
			params.put("managerId", user.getId());
		}
		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		}

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
		PageInfo pageInfo = getSaiBillPage(params);
		model.addAttribute("page", pageInfo);
		return "manager/saleBill/saleBill-list-manager";//"sys/saleBill/saleBill-list";
	}

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/list_managerType")
	public String listManagerType(@RequestParam Map<String, Object> params,ModelMap model,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		if(!isLeander(user.getId())) {
			params.put("managerId", user.getId());
		}
		if("-1".equals(params.get("supplierId"))) {
			params.remove("supplierId");
		} else {
			params.remove("supplierName");
		}

		if("-1".equals(params.get("managerId"))) {
			params.remove("managerId");
		} 
		PageInfo pageInfo = getSaiBillPage(params);
		model.addAttribute("page", pageInfo);
		return "manager/saleBill/saleBill-list-managerType";//"sys/saleBill/saleBill-list";
	}

	/**分页条件查询对账单
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping("/transferlist")
	public String transferlist(@RequestParam Map<String, Object> params,ModelMap model) {
		PageInfo<SupplierTransferVo> pageInfo = supplierTransferService.getPage(params);
		model.addAttribute("page", pageInfo);
		return "manager/saleBill/transfer-list";//"sys/saleBill/saleBill-list";
	}
	
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/updateSaleBillStatus")
	@ResponseBody
	public ActResult<Object> updateSaleBillStatus(@RequestBody List<SaleBill> saleBill,Integer pstatus,BigDecimal amount,String code,HttpSession session) {
		List<SaleBill> listSaleBill =saleBill;	// JsonUtil.getList(saleBill.toString(), SaleBill.class);
		if(pstatus!=null) {
			pstatus=pstatus+1;
			for (SaleBill saleBill2 : listSaleBill) {
				saleBill2.setPayStatus(saleBill2.getPayStatus()*pstatus);
			}
		}
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		ActResult<Object> act = this.saleBillService.updateSaleBillStatus(listSaleBill,amount,code,user.getId());
		return act;
	}

	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/paySaleBill")
	@ResponseBody
	public ActResult<Long> paySaleBill(Long id,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		ActResult<Long> as = ActResult.success(null);
		SaleBill sb = saleBillService.getById(id);
		if(sb.getPayType()!=null && sb.getPayType()==1) {
			Long flowId=dbUtils.CreateID();
			int r =entBenefitFacade.paySaleBill(id,user.getId(), user.getName(), flowId);
			
			if(r==1) {
				return ActResult.success(flowId);
			} else {
				return ActResult.fail("对账单状态不正确，可能已经处理过，或审核未通过，请刷新数据重试");
			}
			
		} else {
			as.setMsg("其他方式支付");
		}
		return as;
	}
	

	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/payTransfer")
	@ResponseBody
	public ActResult<Long> payTransfer(Long id,String payFlowCode,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		Long flowId=dbUtils.CreateID();
		int r =entBenefitFacade.cashTransfer(id, payFlowCode, user.getName(), flowId);
		if(r==1) {
			return ActResult.success(flowId);
		} else {
			return ActResult.fail("操作失败，请刷新数据重试");
		}
	}
	
	/**
	 * 对账单发票信息
	 * @param id  对账单id
	 * @return
	 */
	@RequestMapping("/receiptInfo")
	public String receiptInfo(String id,Model model) {//@RequestBody List<Long> id
		
		ActResult<ReceiptVo> act = this.saleBillService.getReceiptInfo(JsonUtil.getList(id, Long.class));
		model.addAttribute("receipt", act);
		model.addAttribute("sd", supplierDurationVoService.getById(act.getData().getPrice().get(0).getSupplierId()));
		return "manager/saleBill/saleBill-receiptBox";//"sys/saleBill/saleBill-receiptBox";
	}
	/**
	 * 对账单发票信息
	 * @param id  对账单id
	 * @return
	 */
	@RequestMapping("/makeSailBill")
	public String makeSailBill(Integer closeType,String id,Model model) {//@RequestBody List<Long> id
		
		ActResult<SaleBill> act = this.saleBillService.makeSailBill(closeType,JsonUtil.getList(id, Long.class));
		model.addAttribute("act", act);
		model.addAttribute("ids", id);
		model.addAttribute("sd", supplierDurationVoService.getById(act.getData().getSupplierId()));
		return "manager/saleBill/saleBill-makeBox";//"sys/saleBill/saleBill-receiptBox";
	}
	/**
	 * 对账单发票信息
	 * @param id  对账单id
	 * @return
	 */
	@RequestMapping("/doMakeSailBill")
	@ResponseBody
	public ActResult<SaleBill> doMakeSailBill(SaleBill relation,String idc,Model model, HttpSession session,HttpServletRequest request) {
		List<Long> ids = JsonUtil.getList(idc, Long.class);
		ActResult<SaleBill> act = new ActResult<SaleBill>();
		if(ids ==null ||ids.isEmpty()){
			act.setSuccess(false);
			act.setMsg("参数为空");
			return act;
		}
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		// 发票的价钱
		Date startTime=new Date();
		Date endTime=TimeUtil.strToDate("2016-12-07");
		List<SaleBill> list = new ArrayList<SaleBill>();
		for (int i = 0; i < ids.size(); i++) {
			// 根据对账单id查询对账单信息
			SaleBill saleBill = saleBillService.getById(ids.get(i));
			if (i == 0) {
				relation.setName(saleBill.getName());
				relation.setSupplierId(saleBill.getSupplierId());
			} else {
				// 判断方法参数id所对应的商家是否是同一家
				if (relation.getSupplierId().equals(saleBill.getSupplierId())) {
					// id相同，表示是同一家商家
				} else {
					act.setSuccess(false);
					act.setMsg("不能同时生成不同商家的结算单");
					return act;
				}
			}
			//获取最小开始日期
			if(startTime.compareTo(saleBill.getStartTime())>0) {
				startTime= saleBill.getStartTime();
			}
			
			//获取最大结束日期
			if(endTime.compareTo(saleBill.getEndTime())<0) {
				endTime= saleBill.getEndTime();
			}
			list.add(saleBill);
		}
		
		return supplierCloseFacade.execRelation(relation.getCloseType(),relation.getTitle(),relation.getCloseNote(), relation.getSupplierId(), 
				relation.getName(),	sscmf.getSaleCode(relation.getSupplierId()), list, user.getId(), user.getName(), startTime, endTime, relation.getPayType(), 
				relation.getPayNote());
	}
	/**
	 * 对账单发票信息
	 * @param id  对账单id
	 * @return
	 */
	@RequestMapping("/toDuration")
	public String toDuration(Long supplierId,String supplierName,Model model) {//@RequestBody List<Long> id

		model.addAttribute("supplierName", supplierName);
		model.addAttribute("sd", supplierDurationVoService.getById(supplierId));
		return "manager/saleBill/saleBill-duration";//"sys/saleBill/saleBill-duration";
	}
	/**
	 * 根据对账单id查询对账单及订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSaleBillDetail")
	public String getSaleBillDetail(Long id,Model model) {
		SaleBillDetailVo sbVo = this.saleBillService.getSaleBillDetail(id);
		String sub = "";
		for (int i = 0; i < sbVo.getSaleDetail().size(); i++) {
			SaleDetail sd = sbVo.getSaleDetail().get(i);
			if(sub.equals(sd.getSubOrderId())){
				sd.setCashPay(null);
				sd.setCashNo(null);
				sd.setThirdPay(null);
				sd.setThirdType(null);
				sd.setThirdNo(null);
			} else {
				sub=sd.getSubOrderId();
			}
		}
		model.addAttribute("info", sbVo);

		if(sbVo.getSaleBill().getCloseType() == 1 || sbVo.getSaleBill().getCloseType() == 2) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNum", 1);
			params.put("pageSize", 500);
			params.put("relationKey", id);
			model.addAttribute("relationList", saleBillService.getPage(params));
		}
		return "manager/saleBill/saleBillDetail";//"sys/saleBill/saleBillDetail";
	}
	
	/**
	 * 根据对账单id查询对账单及订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCommissionRefundDetail")
	public String getCommissionRefundDetail(Long id,Model model) {
		model.addAttribute("info", commissionRefundService.getById(id));
		model.addAttribute("list", commissionRefundDetailService.getByRefundId(id));
		return "manager/saleBill/commissionRefundDetail";//"sys/saleBill/saleBillDetail";
	}

	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toReceiptSend", method = RequestMethod.POST)
	public String toReceiptSend(Model model,Long id) {
		ServiceReceipt sr =serviceReceiptService.getById(id);
		Supplier s = supplierService.findByid(sr.getSupplierId());
		//保存商家账期
        SupplierDuration sd = supplierDurationVoService.getById(sr.getSupplierId());
        
        if(StringUtils.isEmpty(sd.getPhone())) {
        	sd.setPhone(s.getComTel());
        }
        if(StringUtils.isEmpty(sd.getContacts())) {
        	sd.setContacts(s.getCorName());
        }
        
		model.addAttribute("s", s);
		model.addAttribute("sr", sr);
		model.addAttribute("sd", sd);
		Map<String, ExpressCompany> map = expressComService.getExpressCompanys();

		model.addAttribute("ecs", map.values());
		return "manager/saleBill/receipt-sendBox";
	}
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/sendReceipt")
	@ResponseBody
	public ActResult<Object> sendReceipt(Long id,String sendExpressType,String sendExpressNo, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		ServiceReceipt sr =serviceReceiptService.getById(id);
		sr.setSendDate(new Date());
		sr.setSendExpressNo(sendExpressNo);
		sr.setSendExpressType(sendExpressType);
		
		sr.setStatus("3");
		sr.setUpdateTime(new Date());
		sr.setUpdateUser(user.getId());
		
		this.serviceReceiptService.update(sr);
		return ActResult.success(null);
	}
	@RequestMapping("/toExpress")
	public String toExpress(Model model,String expressType,String expressNo) {
	    ExpressCompany ci = expressComService.getExpressComById(expressType);
    	model.addAttribute("compInfo", ci);
    	model.addAttribute("expressCom", ci.getPinYin());
    	model.addAttribute("expressNo", expressNo);
    	
		return "manager/saleBill/receipt-expressBox";
	}

	@RequestMapping("/toComfirmReturn")
	public String toComfirmReturn(Model model,Long id,String rst) {
		ServiceReceipt sr =serviceReceiptService.getById(id);
		Supplier s = supplierService.findByid(sr.getSupplierId());

		model.addAttribute("s", s);
		model.addAttribute("sr", sr);
		model.addAttribute("rst", rst);
		
		return "manager/saleBill/receipt-returnApprBox";
	}

	@RequestMapping("/toComfirmTransfer")
	public String toComfirmTransfer(Model model,Long id,String rst,BigDecimal balance) {
		SupplierTransfer st =supplierTransferService.getById(id);
		
		Supplier s = supplierService.findByid(st.getSupplierId());

		model.addAttribute("s", s);
		model.addAttribute("st", st);
		model.addAttribute("rst", rst);
		model.addAttribute("balance", balance);
		
		return "manager/saleBill/transfer-apprBox";
	}
	
	@RequestMapping("/toCancelReceipt")
	public String toCancelReceipt(Model model,Long id) {
		ServiceReceipt sr =serviceReceiptService.getById(id);
		Supplier s = supplierService.findByid(sr.getSupplierId());

		model.addAttribute("s", s);
		model.addAttribute("sr", sr);
		
		return "manager/saleBill/receipt-cancelBox";
	}
	

	@RequestMapping("/cancelReceipt")
	@ResponseBody
	public ActResult<Object> cancelReceipt(Long id,HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		ServiceReceipt sr =serviceReceiptService.getById(id);
		List<SaleBill> ls=saleBillService.getByIds(sr.getSaleBillIds().substring(0, sr.getSaleBillIds().length()-1));
		for (SaleBill saleBill : ls) {
			saleBill.setReceiptStatus(1);
			saleBill.setReceiptCode(null);
			saleBill.setReceiptId(null);
			saleBillService.update(saleBill);
		}
		sr.setStatus("8");
		sr.setUpdateTime(new Date());
		sr.setUpdateUser(user.getId());
		
		this.serviceReceiptService.update(sr);
		return ActResult.success(null);
	}
	
	
	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/comfirmReturn")
	@ResponseBody
	public ActResult<Object> comfirmReturn(Long id,String status,String rejectNote, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		
		ServiceReceipt sr =serviceReceiptService.getById(id);
		sr.setStatus(status);
		if("5".equals(status)) {
			sr.setRejectNote(rejectNote);
		}
		sr.setUpdateTime(new Date());
		sr.setUpdateUser(user.getId());
		
		this.serviceReceiptService.update(sr);
		return ActResult.success(null);
	}

	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/comfirmTransfer")
	@ResponseBody
	public ActResult<Object> comfirmTransfer(Long id,Integer status,String rejectNote, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;

		SupplierTransfer st =supplierTransferService.getById(id);
		st.setStatus(status);
		if(-1 == status) {
			st.setRejectNote(rejectNote);
		}
		st.setUpdateTime(new Date());
		st.setUpdateUser(user.getId());
		
		this.supplierTransferService.update(st);
		return ActResult.success(null);
	}

	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toPayFail", method = RequestMethod.POST)
	public String toPayFail(Model model,Long id) {
		SupplierTransfer st =supplierTransferService.getById(id);

		model.addAttribute("st", st);
		//保存商家账期
        SupplierDuration sd = supplierDurationVoService.getById(st.getSupplierId());
        
        Supplier supplier = supplierService.findByid(st.getSupplierId());
        if(StringUtils.isEmpty(sd.getPhone())) {
        	sd.setPhone(supplier.getComTel());
        }
        if(StringUtils.isEmpty(sd.getContacts())) {
        	sd.setContacts(supplier.getCorName());
        }
        
		model.addAttribute("supplier", supplier);
		model.addAttribute("sd", sd);
		
		return "manager/saleBill/transfer-payFail";//"sys/saleBill/saleBill-paymentBox";
	}

	/**修改对账单状态值
	 * @param saleBill
	 * @return
	 */
	@RequestMapping("/payFail")
	@ResponseBody
	public ActResult<Long> payFail(Long id,String rejectNote, HttpSession session) {
		Object obj = session.getAttribute(Constant.SESSION_LOGIN_USER);
		SysUser user = (SysUser)obj;
		try {
			rejectNote = URLDecoder.decode(rejectNote, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Long flowId=dbUtils.CreateID();
		int r =entBenefitFacade.cashTransferFail(id, rejectNote, user.getName(), flowId);
		if(r==1) {
			return ActResult.success(flowId);
		} else {
			return ActResult.fail("操作失败，请刷新后重试");
		}
	}
	
	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "toPayTransfer", method = RequestMethod.POST)
	public String toPayTransfer(Model model,Long id) {
		SupplierTransfer st =supplierTransferService.getById(id);

		model.addAttribute("st", st);
		//保存商家账期
        SupplierDuration sd = supplierDurationVoService.getById(st.getSupplierId());
        
        Supplier supplier = supplierService.findByid(st.getSupplierId());
        if(StringUtils.isEmpty(sd.getPhone())) {
        	sd.setPhone(supplier.getComTel());
        }
        if(StringUtils.isEmpty(sd.getContacts())) {
        	sd.setContacts(supplier.getCorName());
        }
        
		model.addAttribute("supplier", supplier);
		model.addAttribute("sd", sd);
		
		return "manager/saleBill/transfer-paymentBox";//"sys/saleBill/saleBill-paymentBox";
	}
	
	/**
	 * 弹窗
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showlayer(@PathVariable("mode") String mode,Model model,Long id) {
		String str = "";
		//审核弹窗
		if("check".equals(mode)){
			SaleBill saleBill = this.saleBillService.getById(id);
			model.addAttribute("id", id);
			model.addAttribute("pstatus", saleBill.getPayStatus());
			str = "manager/saleBill/saleBill-checkBox";//"sys/saleBill/saleBill-checkBox";
		//付款弹窗
		}else if("payment".equals(mode)){
			SaleBill saleBill = this.saleBillService.getById(id);
			model.addAttribute("saleBill", saleBill);
			//保存商家账期
	        SupplierDuration sd = supplierDurationVoService.getById(saleBill.getSupplierId());
	        
	        Supplier supplier = supplierService.findByid(saleBill.getSupplierId());
	        if(StringUtils.isEmpty(sd.getPhone())) {
	        	sd.setPhone(supplier.getComTel());
	        }
	        if(StringUtils.isEmpty(sd.getContacts())) {
	        	sd.setContacts(supplier.getCorName());
	        }
	        
			model.addAttribute("supplier", supplier);
			model.addAttribute("sd", sd);
			
			str = "manager/saleBill/saleBill-paymentBox";//"sys/saleBill/saleBill-paymentBox";
		}else if("receipt".equals(mode)){
			model.addAttribute("id", id);
			List<Long> list = new ArrayList<Long>();
			list.add(id);
			ActResult<ReceiptVo> receipt=this.saleBillService.getReceiptInfo(list);
			model.addAttribute("receipt", receipt);
			SupplierDuration supplierDuration = supplierDurationVoService.getById(receipt.getData().getPrice().get(0).getSupplierId());
			if(supplierDuration.getBillType() == null){
				supplierDuration.setBillType(0);
				//暂不处理
//				SupplierDuration supplierDuration1 = new SupplierDuration();
//				supplierDuration.setBillType(0);
//				supplierDuration.setId(supplierDuration.getId());
//				supplierDurationVoService.update(supplierDuration1);
			}
			supplierDuration.setBillTypeValue(supplierDuration.getBillType() == 1?"专用发票":"普通发票");
			model.addAttribute("sd", supplierDuration);
			str = "manager/saleBill/saleBill-receiptBox";//"sys/saleBill/saleBill-receiptBox";
		}
		
		return str;
	}
	
	@RequestMapping(value = "downLoadExcel", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel(Long id,HttpServletResponse response,HttpServletRequest request) {
		SaleBillDetailVo sbVo = this.saleBillService.getSaleBillDetail(id);

		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("对账单详情");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        /**
         * 账单信息
         * ######################################################################################
         * */
        //账单信息
	    SaleBill sb = sbVo.getSaleBill();
	    //第一行
        HSSFRow row = sheet.createRow((int) 0); 
        //第一行、第二列
        row.createCell((short) 1).setCellValue("标题");
        //第一行、第三列
        row.createCell((short) 2).setCellValue(sb.getTitle());
        
        //第二行
        row = sheet.createRow((int) 1);
        //第二行、第二列
        row.createCell((short) 1).setCellValue("本期账期周期");
        //第二行、第三列
        row.createCell((short) 2).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(sb.getStartTime())+"--"+new SimpleDateFormat("yyyy-MM-dd").format(sb.getEndTime()));
        
        //第三行
        row = sheet.createRow((int) 2); 
        //第三行、第二列
        row.createCell((short) 1).setCellValue("代收货款总额");
        //第三行、第三列
        row.createCell((short) 2).setCellValue(new DecimalFormat("#.00").format(sb.getReceivePrice()));
        //第四行
        row = sheet.createRow((int) 3); 
        //第四行、第二列
        row.createCell((short) 1).setCellValue("代收运费总额");
        //第四行、第三列
        row.createCell((short) 2).setCellValue(new DecimalFormat("#.00").format(sb.getCarriagePrice()));
        
        row = sheet.createRow((int) 4); 
        row.createCell((short) 1).setCellValue("佣金总金额");
        row.createCell((short) 2).setCellValue(new DecimalFormat("#.00").format(sb.getCommissionPrice()));

        row = sheet.createRow((int) 5); 
        row.createCell((short) 1).setCellValue("返佣总金额");
        row.createCell((short) 2).setCellValue(new DecimalFormat("#.00").format(sb.getRefundAmount()));
        
        row = sheet.createRow((int) 6); 
        row.createCell((short) 1).setCellValue("扣款总额");
        row.createCell((short) 2).setCellValue(new DecimalFormat("#.00").format(sb.getDeductPrice()));
        
        row = sheet.createRow((int) 7); 
        row.createCell((short) 1).setCellValue("本期应付款总额");
        row.createCell((short) 2).setCellValue(new DecimalFormat("#.00").format(sb.getPayPrice()));
        /**######################################################################################
         * */
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        
        
        /**
         * 
         * 设置订单详情表头 start
         * */
        //第八行
        row = sheet.createRow((int) 8); 
        //第八行、第一列
        HSSFCell cell = row.createCell((short) 0);
        //设置值
        cell.setCellValue("序号");  
        //设置样式
        cell.setCellStyle(style);
        
        
        //第八行、第二列
        cell = row.createCell((short) 1);
        //设置值
        cell.setCellValue("订单号");
        //设置样式
        cell.setCellStyle(style);  
        
        
        //第八行、第三列
        cell = row.createCell((short) 2);  
        //设置值
        cell.setCellValue("付款日期");  
        //设置样式
        cell.setCellStyle(style);  
        
        
        //第八行、第四列
        cell = row.createCell((short) 3);  
        //设置值
        cell.setCellValue("确认日期");  
        cell.setCellStyle(style);
        
        
        //第八行、第五列
        cell = row.createCell((short) 4);  
        cell.setCellValue("退货日期");  
        cell.setCellStyle(style);  
        
        
        cell = row.createCell((short) 5);  
        cell.setCellValue("本企业订单");  
        cell.setCellStyle(style);  
        
        
        cell = row.createCell((short) 6);  
        cell.setCellValue("商品");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 7);  
        cell.setCellValue("商品分类");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 8);  
        cell.setCellValue("单价");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 9);  
        cell.setCellValue("数量");  
        cell.setCellStyle(style);  
       
        cell = row.createCell((short) 10);  
        cell.setCellValue("金额");  
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 11);  
        cell.setCellValue("优惠");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 12);  
        cell.setCellValue("实收货款");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 13);  
        cell.setCellValue("佣金比例");  
        cell.setCellStyle(style);  

        cell = row.createCell((short) 14);  
        cell.setCellValue("佣金");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 15);  
        cell.setCellValue("代收运费");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 16);  
        cell.setCellValue("应付账款");
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 17);  
        cell.setCellValue("现金券抵扣");
        cell.setCellStyle(style);  
        cell = row.createCell((short) 18);  
        cell.setCellValue("抵扣流水号");
        cell.setCellStyle(style);  
        cell = row.createCell((short) 19);  
        cell.setCellValue("实付金额");
        cell.setCellStyle(style);  
        cell = row.createCell((short) 20);  
        cell.setCellValue("支付方式");
        cell.setCellStyle(style);  
        cell = row.createCell((short) 21);  
        cell.setCellValue("支付流水号");
        cell.setCellStyle(style);  
        /** 设置订单详情表头 end
         * */
  
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        
		List<SaleDetail> list = sbVo.getSaleDetail();
  
		String sub = "";
        for (int i = 8; i < list.size()+8; i++)
        {  
        	//第九行  
            row = sheet.createRow((int) i + 1); 
            //获取订单信息  sd
            SaleDetail sd = (SaleDetail) list.get(i-8);
            
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue((i-7)); //序号 
            row.createCell((short) 1).setCellValue(sd.getSubOrderId());  //订单号
            row.createCell((short) 2).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(sd.getPayTime()));  //付款日期
            cell = row.createCell((short) 3);  
            cell.setCellValue(com.wode.common.util.StringUtils.isNullOrEmpty(sd.getTakeTime())?"-":new SimpleDateFormat("yyyy-MM-dd").format(sd.getTakeTime()));
            row.createCell((short) 4).setCellValue(com.wode.common.util.StringUtils.isNullOrEmpty(sd.getReturnTime())?"-":new SimpleDateFormat("yyyy-MM-dd").format(sd.getReturnTime()));//收货日期
            row.createCell((short) 5).setCellValue(sd.getOwn()==0?"是":"-");//是否本企业
            row.createCell((short) 6).setCellValue(sd.getProductName());//商品名称
            row.createCell((short) 7).setCellValue(sd.getCategoryName());//商品分类
            row.createCell((short) 8).setCellValue(sd.getSalePrice().doubleValue());//单价
            row.createCell((short) 9).setCellValue(sd.getNumber());//数量
            row.createCell((short) 10).setCellValue(sd.getSalePrice().doubleValue());//总货款
            //优惠
            if(sd.getHaveCheap()==1) {
                row.createCell((short) 11).setCellValue("有");//优惠
            } else if(sd.getHaveCheap()==3) {
                row.createCell((short) 11).setCellValue("换领");//优惠 
            } else if(sd.getHaveCheap()==5) {
                row.createCell((short) 11).setCellValue("试用");//优惠 
            } else {
                row.createCell((short) 11).setCellValue("无");//优惠 
            }
            row.createCell((short) 12).setCellValue(sd.getRealPrice().doubleValue()*sd.getStatus());//实付
            row.createCell((short) 13).setCellValue(new DecimalFormat("#.00").format(sd.getCommissionRatio())+"%");//抽佣比例
            row.createCell((short) 14).setCellValue(sd.getCommission().doubleValue()*sd.getStatus());//佣金
            
            row.createCell((short) 15).setCellValue(sd.getCarriagePrice().doubleValue());//代收运费
            row.createCell((short) 16).setCellValue(sd.getPayPrice().doubleValue()*sd.getStatus());//应付货款

            if(!sub.equals(sd.getSubOrderId())){
	            row.createCell((short) 17).setCellValue((sd.getCashPay()==null?BigDecimal.ZERO:sd.getCashPay()).doubleValue());//现金券抵扣
	            row.createCell((short) 18).setCellValue(sd.getCashNo()==null?"":sd.getCashNo()+"");//抵扣流水号
	            row.createCell((short) 19).setCellValue((sd.getThirdPay()==null?BigDecimal.ZERO:sd.getThirdPay()).doubleValue());//实付金额
	            HSSFCell cel= row.createCell((short) 20);												//支付方式
	            if("zhifubao".equals(sd.getThirdType())) {
	            	cel.setCellValue("支付宝");
	            } else if("pingtaiyue".equals(sd.getThirdType())) {
	            	cel.setCellValue("现金券");
	            } else if("wxpay".equals(sd.getThirdType())) {
	            	cel.setCellValue("微信");
	            } else if("unionpay".equals(sd.getThirdType())) {
	            	cel.setCellValue("银联");
	            } else {
	            	cel.setCellValue("");
	            }
	            row.createCell((short) 21).setCellValue(sd.getThirdNo()==null?"":sd.getThirdNo()+"");//支付流水号
	            
	            sub=sd.getSubOrderId();
            }
        }  
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;filename=" + sb.getBillId()+".xls");
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
	

	private boolean isLeander(Long userId) {
		return (","+leaders+",").contains(","+userId+",");
	}
	
	@RequestMapping(value = "exportExcelFinance")
	@ResponseBody
	public void exportExcelFinance(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		PageInfo<SaleBill> pageInfo = saleBillService.getPage(params);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("品牌一览"); 
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
        headers.add("对账单ID");
        headers.add("商家名称");
        headers.add("对账开始时间");
        headers.add("截止时间");
        headers.add("代收货款");
        headers.add("代收运费");
        headers.add("佣金");
        headers.add("返佣");
        headers.add("扣款");
        headers.add("应付款");
        headers.add("含现金券");
        headers.add("商家确认");
        headers.add("确认时间");
        headers.add("结算状态");
        headers.add("处理时间");
        headers.add("发票状态");
        headers.add("结算日期");

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
		for (SaleBill p : pageInfo.getList()) {
			this.setCashPay(p);
			
			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
	        //headers.add("对账单ID");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getId());
	        //headers.add("商家名称");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getName());
	        //headers.add("结算开始");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getStartTime()==null?"":DateUtils.formatDate(p.getStartTime(),"yyyy-MM-dd")); //创建时间
	        //headers.add("截止时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getEndTime()==null?"":DateUtils.formatDate(p.getEndTime(),"yyyy-MM-dd")); //创建时间
	        //headers.add("商品金额");
            row.createCell(col++).setCellValue(p.getReceivePrice()==null?0:p.getReceivePrice().doubleValue());
	        //headers.add("代收运费");
            row.createCell(col++).setCellValue(p.getCarriagePrice()==null?0:p.getCarriagePrice().doubleValue());
	        //headers.add("佣金");
            row.createCell(col++).setCellValue(p.getCommissionPrice()==null?0:p.getCommissionPrice().doubleValue());
	        //headers.add("返佣");
            row.createCell(col++).setCellValue(p.getRefundAmount()==null?0:p.getRefundAmount().doubleValue());
	        //headers.add("扣款");
            row.createCell(col++).setCellValue(p.getDeductPrice()==null?0:p.getDeductPrice().doubleValue());
	        //headers.add("应付款");
            row.createCell(col++).setCellValue(p.getPayPrice()==null?0:p.getPayPrice().doubleValue());
	        //headers.add("含现金券");
            row.createCell(col++).setCellValue(p.getCashPay()==null?0:p.getCashPay().doubleValue());
	        //headers.add("商家确认");
            if(p.getConfirmStatus()==-1) {
	            row.createCell(col++).setCellValue("商家不同意");
            } else if(p.getConfirmStatus()==1){
	            row.createCell(col++).setCellValue("商家已同意");
            } else if(p.getConfirmStatus()==0){
	            row.createCell(col++).setCellValue("待确认");
            } else {
	            row.createCell(col++).setCellValue("");
            }
	        //headers.add("确认时间");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getConfirmTime()==null?"":DateUtils.formatDate(p.getConfirmTime(),"yyyy-MM-dd")); 
	        //headers.add("结算状态");
            if(p.getPayStatus()==-1) {
	            row.createCell(col++).setCellValue("对账未通过");
            } else if(p.getPayStatus()==0){
	            row.createCell(col++).setCellValue("待审核");
            } else if(p.getPayStatus()==1){
	            row.createCell(col++).setCellValue("对账通过");
            } else if(p.getPayStatus()==2){
	            row.createCell(col++).setCellValue("运营通过");
            } else if(p.getPayStatus()==3){
	            row.createCell(col++).setCellValue("财务通过");
            } else if(p.getPayStatus()==4){
	            row.createCell(col++).setCellValue("已结算");
            } else if(p.getPayStatus()==-2){
	            row.createCell(col++).setCellValue("运营未通过");
            } else if(p.getPayStatus()==-3){
	            row.createCell(col++).setCellValue("财务未通过");
            } else {
	            row.createCell(col++).setCellValue("");
            }
            //headers.add("处理时间");
            if(p.getPayStatus()==-1) {
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime1()==null?"":DateUtils.formatDate(p.getLastUpdateTime1(),"yyyy-MM-dd")); 
            } else if(p.getPayStatus()==0){
	            row.createCell(col++).setCellValue("");
            } else if(p.getPayStatus()==1){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime1()==null?"":DateUtils.formatDate(p.getLastUpdateTime1(),"yyyy-MM-dd")); 
            } else if(p.getPayStatus()==2){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime2()==null?"":DateUtils.formatDate(p.getLastUpdateTime2(),"yyyy-MM-dd")); 
            } else if(p.getPayStatus()==3){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime3()==null?"":DateUtils.formatDate(p.getLastUpdateTime3(),"yyyy-MM-dd")); 
            } else if(p.getPayStatus()==4){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime4()==null?"":DateUtils.formatDate(p.getLastUpdateTime4(),"yyyy-MM-dd")); 
            } else if(p.getPayStatus()==-2){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime2()==null?"":DateUtils.formatDate(p.getLastUpdateTime2(),"yyyy-MM-dd")); 
            } else if(p.getPayStatus()==-3){
                row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getLastUpdateTime3()==null?"":DateUtils.formatDate(p.getLastUpdateTime3(),"yyyy-MM-dd"));
            } else {
	            row.createCell(col++).setCellValue("");
            }
	        //headers.add("发票状态");
            if(p.getReceiptStatus()==0) {
	            row.createCell(col++).setCellValue("未申请");
            } else if(p.getReceiptStatus()==1){
	            row.createCell(col++).setCellValue("已申请");
            } else if(p.getReceiptStatus()==2){
	            row.createCell(col++).setCellValue("已开发票");
            } else {
	            row.createCell(col++).setCellValue("");
            }
	        //headers.add("结算日期");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getPayTime()==null?"":DateUtils.formatDate(p.getPayTime(),"yyyy-MM-dd")); //创建时间
			
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request, "对账单一览"));
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

	@RequestMapping(value = "exportExcelTransfer")
	@ResponseBody
	public void exportExcelTransfer(@RequestParam Map<String, Object> params,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		params.put("pageNum", 1);
		params.put("pageSize", 50000);
		PageInfo<SupplierTransferVo> pageInfo = supplierTransferService.getPage(params);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("品牌一览"); 
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
        headers.add("财务代码");
        headers.add("收款方");
        headers.add("账号");
        headers.add("银行");
        headers.add("财务代码");
        headers.add("商家账户余额");
        headers.add("返佣累计");
        headers.add("状态");
        headers.add("提款金额");
        headers.add("转账日期");
        headers.add("转账流水号");
        headers.add("拒绝退回理由");
        headers.add("申请日期");

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
		for (SupplierTransferVo p : pageInfo.getList()) {
			
			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
	        //headers.add("财务代码");
            row.createCell(col++).setCellValue(p.getFinanceCode());
	        //headers.add("收款方");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getComName());
	        //headers.add("账号");
            row.createCell(col++,HSSFCell.CELL_TYPE_STRING).setCellValue(p.getBankNo());
	        //headers.add("银行");
            row.createCell(col++).setCellValue(p.getBankName());
	        //headers.add("商家账户余额");
            row.createCell(col++).setCellValue(p.getBalance()==null?0:p.getBalance().doubleValue());
	        //headers.add("返佣累计");
            row.createCell(col++).setCellValue(p.getReturnCash()==null?0:p.getReturnCash().doubleValue());
	        //headers.add("状态");
            if(p.getStatus()==-1) {
	            row.createCell(col++).setCellValue("已拒绝");
            } else if(p.getStatus()==1){
	            row.createCell(col++).setCellValue("已申请");
            } else if(p.getStatus()==2){
	            row.createCell(col++).setCellValue("已确认");
            } else if(p.getStatus()==3){
	            row.createCell(col++).setCellValue("已转账");
            } else if(p.getStatus()==-1){
            	row.createCell(col++).setCellValue("已拒绝");
            }else {
	            row.createCell(col++).setCellValue("转账失败");
            }
	        //headers.add("提款金额");
            row.createCell(col++).setCellValue(p.getAmount()==null?0:p.getAmount().doubleValue());
	        //headers.add("转账日期");
            row.createCell(col++).setCellValue(p.getPayDate()==null?"":DateUtils.formatDate(p.getPayDate(),"yyyy-MM-dd")); //创建时间
	        //headers.add("转账流水号");
            row.createCell(col++).setCellValue(p.getPayFlowCode()==null?"":p.getPayFlowCode());
	        //headers.add("拒绝退回理由");
            row.createCell(col++).setCellValue(p.getRejectNote()==null?"":p.getRejectNote());
	        //headers.add("申请日期");
            row.createCell(col++).setCellValue(p.getCreateDate()==null?"":DateUtils.formatDate(p.getCreateDate(),"yyyy-MM-dd")); //创建时间
			
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ FileUtils.getFileNameForSave(request, "提现单一览"));
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
}
