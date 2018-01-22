package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.constant.Constant;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.mapper.SupplierMapper;
import com.wode.factory.mapper.UserFactoryDao;
import com.wode.factory.model.ApprSupplierExit;
import com.wode.factory.model.Attachment;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserShare;
import com.wode.factory.service.ApprSupplierExitService;
import com.wode.factory.service.EnterpriseService;
import com.wode.factory.service.SupplierService;
import com.wode.factory.service.UserShareService;
import com.wode.factory.vo.EnterpriseVo;
import com.wode.factory.vo.SupplierVo;
import com.wode.tongji.model.AccountingLog;
import com.wode.tongji.service.AccountingLogService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("supplierServiceImpl")
@Transactional
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierMapper supplierMapper;
	@Autowired
	UserFactoryDao userFactoryDao;
	@Autowired
	AccountingLogService accountingLogService;
	@Autowired
	RedisUtil redis;
	@Autowired
	private DBUtils dbUtils;
	@Autowired
	private ApprSupplierExitService apprSupplierExitService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private UserShareService userShareService;

	private  String apiUrl=Constant.FACTORY_API_URL;
	@Override
	@Transactional(readOnly = true)
	public PageInfo<Supplier> getPage(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierVo> ordersList = supplierMapper.findSupplierVo(params);
		return new PageInfo(ordersList);
	}
	

	@Override
	@Transactional(readOnly = true)
	public PageInfo<SupplierVo> findSupplierCount(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierVo> ordersList = supplierMapper.findSupplierCount(params);
		for (SupplierVo obj : ordersList) {
			SupplierVo supplierVo = supplierMapper.getBrandOwnerBySupplierId(obj.getId());
			if(null != supplierVo) {
				obj.setBrandOwner(supplierVo.getComName());
			}
			if(obj.getStatus() == -2) {
				ApprSupplierExit apprSupplierExit = apprSupplierExitService.getApprSupplierExitBySupplierId(obj.getId());
				if(apprSupplierExit != null)obj.setExitTime(apprSupplierExit.getUpdateTime());
			}
		}
		return new PageInfo(ordersList);
	}
	

	@Override
	public PageInfo<SupplierVo> findEmploeeCnt(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierVo> ordersList = supplierMapper.findEmploeeCnt(params);
		return new PageInfo(ordersList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Supplier getSupplierDetailWithItems(Long id) {
		Assert.notNull(id);
		return supplierMapper.getSupplierByIdWithItems(id);
	}

	@Override
	public List<ProductCategory> getProductCategoryListBySupplierId(Long id,Long shopId) {
		Assert.notNull(id);
		return supplierMapper.getProductCategoryListBySupplierId(id,shopId);
	}

	@Override
	public List<Attachment> getAttachmentListBySupplierId(Long id,Long shopId) {
		Assert.notNull(id);
		return supplierMapper.getAttachmentListBySupplierId(id,shopId);
	}

	@Override
	public List<ProductBrand> getProductBrandListBySupplierId(Long id,Long shopId) {
		Assert.notNull(id);
		return supplierMapper.getProductBrandListBySupplierId(id,shopId);
	}

	/**
	 * 获取品牌
	 * @param id
	 * @return
	 */
	public ProductBrand getProductBrandById(Long id){
		return supplierMapper.getProductBrandById(id);
	}
	
	@Override
	public void updateSupplierStatus(Supplier pojo) {
		supplierMapper.updateSupplierStatus(pojo);
	}

	@Override
	public void saveCheckOpinion(CheckOpinion co) {
		supplierMapper.saveCheckOpinion(co);
	}

	@Override
	public List<CheckOpinion> getCheckOpinionListBySupplierId(Long id) {
		Assert.notNull(id);
		return supplierMapper.getCheckOpinionListBySupplierId(id);
	}
	@Override
	public List<CheckOpinion> getAllCheckOpinionBySupplierId(Long supplierId) {
		Assert.notNull(supplierId);
		return supplierMapper.getAllCheckOpinionBySupplierId(supplierId);
	}
	
	public Supplier findByid(Long id){
		return supplierMapper.findByid(id);
	}

	@Override
	public List<ProductAttribute> findProductAttributeByProductid(Long id) {
		return supplierMapper.findProductAttributeByProductid(id);
	}

	@Override
	public ProductCategory getParentCategoryByid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateSel(Map<String, Object> params,AccountingLog al) {
		int i = supplierMapper.updateSel(params);
		if(i == 1){
			al.setResult("成功");
		}else{
			al.setResult("失败");
		}
		accountingLogService.insert(al);
		return i;
	}

	public BigDecimal getCommissionRate(Long id) {
		return supplierMapper.getCommissionRate(id);
	}

	public void updateCommissionRate(BigDecimal commissionRate, Long id) {
		Supplier supplier = new Supplier();
		supplier.setId(id);
		supplier.setCommissionRate(commissionRate);
		supplierMapper.updateCommissionRate(supplier);
	}

	@Override
	public void updateSupplierEnterpriseId(Supplier pojo) {
		// TODO Auto-generated method stub
		this.supplierMapper.updateSupplierEnterpriseId(pojo);
	}

	@Override
	public UserFactory getSupplierUser(Long supplierId) {
		// TODO Auto-generated method stub
		return this.userFactoryDao.getSupplierUser(supplierId);
	}


	@Override
	public Supplier findByEmpId(Long id) {
		return supplierMapper.findByEmpId(id);
	}


	@Override
	public void setManager(Supplier pojo) {
		supplierMapper.setManager(pojo);
	}


	@Override
	public List<Supplier> findAll() {
		return supplierMapper.findAllSupplier();
	}


	@Override
	public void updateFirmLogo(Supplier supplier) {
		supplierMapper.updateFirmLogo(supplier);
		redis.del("REDIS_USER_SUPPLIER_" + supplier.getId());
	}
	public Long boundQRcode(Long id){
		Supplier orderDetail = this.getSupplierDetailWithItems(id);
		UserShare us =new UserShare();
		if(orderDetail!=null) {
			us.setId(dbUtils.CreateID());
			us.setUserId(orderDetail.getId());
			if(!StringUtils.isEmpty(orderDetail.getNickName())){
				us.setUserNick(orderDetail.getNickName());
			}else{
				us.setUserNick(orderDetail.getComName());
			}
			us.setUserAvatar(null);
			us.setUserType(9);
			us.setShareType(9);
			us.setShareTitle("扫码领取内购券");
			if(!StringUtils.isEmpty(orderDetail.getNickName())){
				us.setShareMsg1(orderDetail.getNickName());	
			}else{
				us.setShareMsg1(orderDetail.getComName());	
			}
			us.setShareMsg2("【我的福利】<span>"+orderDetail.getComName()+"</span>的员工绑定手机号，可立即获得1000元内购券。");
			us.setShareItemCnt(10);
			us.setShareMsg3("又为大家争取到了新的福利，以下超值商品任意购，内购券当钱用还能分享给好友。");
			us.setShareFooter1("长按识别图中二维码");
			us.setShareFooter2("首次绑定手机即获500元内购券");
			us.setShareFooter3("仅限员工专享");
			us.setCreateTime(new Date());
			us.setShareUrl(apiUrl + "userShare/page"+us.getId());
			us.setNextAction(apiUrl + "userShare/toCompanyBind"+us.getId());
		}else {
			EnterpriseVo byId = enterpriseService.getById(id);
			us.setId(dbUtils.CreateID());
			us.setUserId(byId.getId());
			us.setUserNick(byId.getName());
			us.setUserAvatar(null);
			us.setUserType(9);
			us.setShareType(9);
			us.setShareTitle("扫码领取内购券");
			us.setShareMsg1(byId.getName());
			us.setShareMsg2("【我的福利】<span>"+byId.getName()+"</span>的员工绑定手机号，可立即获得1000元内购券。");
			us.setShareItemCnt(10);
			us.setShareMsg3("又为大家争取到了新的福利，以下超值商品任意购，内购券当钱用还能分享给好友。");
			us.setShareFooter1("长按识别图中二维码");
			us.setShareFooter2("首次绑定手机即获500元内购券");
			us.setShareFooter3("仅限员工专享");
			us.setCreateTime(new Date());
			us.setShareUrl(apiUrl + "userShare/page"+us.getId());
			us.setNextAction(apiUrl + "userShare/toCompanyBind"+us.getId());
		}
		userShareService.save(us);
		return us.getId();
	}

}
