/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.facade.ProductExchangeFacade;
import com.wode.factory.facade.SupplierCloseFacade;
import com.wode.factory.facade.SupplierSaleCodeManageFacade;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCloseCmd;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.service.SupplierCloseCmdService;
import com.wode.factory.service.SupplierExchangeProductService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.task.SupplierCloseTask;
import com.wode.tongji.service.AppService;

@Controller
@RequestMapping("close")
public class SuperCloseController{
	@Resource
	private AppService appService;

	@Resource
	private SupplierCloseFacade supplierCloseFacade;

	@Autowired
    private SupplierCloseCmdService supplierCloseCmdService;

	@Autowired
    private SupplierCloseFacade scf;
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
    private SupplierSaleCodeManageFacade sscmf;
	@Autowired
	private ProductExchangeFacade productExchangeFacade;
	@Autowired
	private SupplierExchangeProductService supplierExchangeProductService;
	
	@Autowired
	private SupplierCloseTask supplierCloseTask;

	/**
	 * 结算异常时的补救处理，通过cmdId,再次执行结算处理，但不做任何的账期处理
	* @param cmdId t_supplier_close_cmd表id
	* @return
	 */
	@RequestMapping(value="cmd")
	public @ResponseBody Long cmd(Long cmdId){
		
		if(StringUtils.isEmpty(cmdId)) return 0L;
		////////////////////////////////////////////////////////////////////
		//查询执行任务
		SupplierCloseCmd entity = supplierCloseCmdService.getById(cmdId);
		////////////////////////////////////////////////////////////////////
		if(entity == null) return 0L;
		
		//循环执行，如遇异常跳过，执行下一个
		Long saleBillId = 0L;
		String billId="";

		////////////////////////////////////////////////////////////
		//取得商家信息 
		Supplier s =supplierService.findByid(entity.getSupplierId());
		if(s == null) { 
			return 0L;
		} else {
			//用以识别本企业员工的id应为商家关联的企业id
			entity.setEnterpriseId(s.getEnterpriseId()==null ? s.getId():s.getEnterpriseId());
		}
		////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////
		//对账单ID 表示用

		billId = sscmf.getSaleCode(entity.getSupplierId());

		////////////////////////////////////////////////////////////
		//账务处理
		try{
			ActResult<Long> ar= scf.execClose(entity,s.getComName(),billId);
			if(ar.isSuccess()) {
				saleBillId = ar.getData();
			} else {
				System.out.println(ar.getMsg());
			}
		} catch(Exception e) {
			saleBillId = 0L;
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////

		
		return saleBillId;
	}
	

	/**
	 * 结算异常时的补救处理，通过cmdId,再次执行结算处理，但不做任何的账期处理
	* @param cmdId t_supplier_close_cmd表id
	* @return
	 */
	@RequestMapping(value="exchangeShare")
	public @ResponseBody Long share(Long id){

		////////////////////////////////////////////////////////////////////
		SupplierExchangeProduct sep =supplierExchangeProductService.getById(id);
		////////////////////////////////////////////////////////////////////
		// 循环执行任务
		try {
			productExchangeFacade.stopExchangeAndShare(sep);
			return 1L;
		} catch (Exception e) {
			e.printStackTrace();
			// third.setUrlStatus(-1);
		}
		
		return 0L;
	}
	
	/**
	 * 结算异常时的补救处理，通过cmdId,再次执行结算处理，但不做任何的账期处理
	* @param cmdId t_supplier_close_cmd表id
	* @return
	 */
	@RequestMapping(value="exchangeClear")
	public @ResponseBody Long clear(Long id){

		////////////////////////////////////////////////////////////////////
		SupplierExchangeProduct sep =supplierExchangeProductService.getById(id);
		////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////
		// 循环执行任务
		try {
			productExchangeFacade.ClearLimitTicket(sep, "手动执行");
			return 1L;
		} catch (Exception e) {
			e.printStackTrace();
			// third.setUrlStatus(-1);
		}
		
		return 0L;
	}
	
	/**
	 * 触发账期操作，慎用慎用
	 */
	@RequestMapping("supplierCloseTask")
	public void testSupplierCloseTask(){
		supplierCloseTask.checkReceivesProduct();
	}
}

