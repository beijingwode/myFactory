/**
 * 收货地址
 * @author 刘聪 2015-06-25
 */
package com.wode.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Area;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Supplier;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.util.AreaUtils;

@Controller
@RequestMapping("/address")
@ResponseBody
public class AddressController extends BaseController{

	@Autowired
	private ShippingAddressService shippingAddressService;
	@Autowired
    private SupplierDao supplierDao;
	/**
	 * 根据code 获取下一级地区
	 */
	@RequestMapping("area")
	public  ActResult<List<Area>> getArea(Integer code) {
		if (null == code || code < 10000) {
			return  ActResult.success(AreaUtils.getArea90(0, null));
		}else if (code % 10000 == 0){
			return  ActResult.success(AreaUtils.getArea90(2, code));
		} else {
			return  ActResult.success(AreaUtils.getArea90(3, code));
		}
	}
	
	
	/**
	 * 查询所有收货地址列表
	 * @author 刘聪
	 * @since 2015-06-25
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/all.user")
	public ActResult<List<ShippingAddress>> all(){
		// 根据用户ID获取用户收货地址
		List<ShippingAddress> listAddress = shippingAddressService.findByUserId(loginUser.getId());
		
		return ActResult.success(listAddress);
	}
	
	/**
	 * 新增收货地址
	 * @author 刘聪
	 * @since 2015-06-25
	 */
	@RequestMapping("/add.user")
	public ActResult<ShippingAddress> add(ShippingAddress sa, Integer cityId){
		// 判断收货区县代码是否为空
		if(sa.getAid() != null) {
			// 获取收货区县信息
			List<Area> areaList = AreaUtils.getArea90(3, cityId);
			// 判断收货区县信息是否存在
			if(areaList != null && areaList.size() > 0) {
				boolean areaFlg = false;
				for(Area area : areaList) {
					if(Integer.valueOf(sa.getAid()).equals(area.getCode())) {
						areaFlg = true;
						break;
					}
				}
				if(areaFlg == false){
					return ActResult.fail("收货区县不存在");
				}
			} else {
				return ActResult.fail("收货区县不存在");
			}
		} else {
			return ActResult.fail("收货区县不允许为空");
		}
		sa.setUserId(loginUser.getId());
		
		// 新增/修改收货地址
		sa = shippingAddressService.saveOrupdateAddress(sa);
		return ActResult.success(sa);
	}
	
	/**
	 * 修改收货地址
	 * @author 刘聪
	 * @since 2015-06-25
	 */
	@RequestMapping("/update.user")
	public ActResult<ShippingAddress> update(ShippingAddress sa, Integer cityId){
		ShippingAddress check = shippingAddressService.getById(loginUser.getId(),sa.getId());
		if(!loginUser.getId().equals(check.getUserId())){
			return ActResult.fail("用户地址不匹配");
		}
		// 判断收货区县代码是否为空
		if(sa.getAid() != null) {
			// 获取收货区县信息
			List<Area> areaList = AreaUtils.getArea90(3, cityId);
			// 判断收货区县信息是否存在
			if(areaList != null && areaList.size() > 0) {
				boolean areaFlg = false;
				for(Area area : areaList) {
					if(Integer.valueOf(sa.getAid()).equals(area.getCode())) {
						areaFlg = true;
						break;
					}
				}
				if(areaFlg == false){
					return ActResult.fail("收货区县不存在");
				}
			} else {
				return ActResult.fail("收货区县不存在");
			}
		} else {
			return ActResult.fail("收货区县不允许为空");
		}
		sa.setUserId(loginUser.getId());
		// 新增/修改收货地址
		sa = shippingAddressService.saveOrupdateAddress(sa);
		return ActResult.success(sa);
	}
	
	/**
	 * 根据id查询收货地址
	 * @author 刘聪
	 * @since 2015-06-25
	 */
	@RequestMapping("/selectOne.user")
	public ActResult<ShippingAddress> selectOne(Long id){
		ActResult<ShippingAddress> ar =new ActResult<ShippingAddress>();
		// 根据id查询收货地址
		ShippingAddress sa = shippingAddressService.getById(loginUser.getId(),id);
		ar.setData(sa);
		return ar;
	}
	
	/**
	 * 根据id删除收货地址
	 * @author 刘聪
	 * @since 2015-06-25
	 */
	@RequestMapping("/delete.user")
	public ActResult<Object> delete(Long id){
		ActResult<Object> ar =new ActResult<Object>();
		shippingAddressService.deleteAddress(loginUser.getId(),id);
		// 根据id删除收货地址
		return ar;
	}
	/**
	 * 微信地址管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView page(ModelAndView model, HttpServletRequest request, String orderType, String specificationsId,
			String productId, String quantity, String partNumbers, String backNum) {
		model.addObject("orderType", orderType);
		model.addObject("specificationsId", specificationsId);
		model.addObject("productId", productId);
		model.addObject("quantity", quantity);
		model.addObject("partNumbers", partNumbers);
		model.addObject("backNum", StringUtils.isEmpty(backNum) ? "0" : backNum);
		model.setViewName("address");
		return model;
	}
	/**
	 * 选择所在地区
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/arealist")
	public ModelAndView area(ShippingAddress sa,Integer pageId,ModelAndView model,HttpServletRequest request){
		model.addObject("id", sa.getId());
		model.addObject("name", sa.getName());
		model.addObject("aid", sa.getAid());
		model.addObject("phone", sa.getPhone());
		model.addObject("provinceName", sa.getProvinceName());
		model.addObject("cityName", sa.getCityName());
		model.addObject("areaName", sa.getAreaName());
		model.addObject("address", sa.getAddress());
		if (0==pageId) {
			model.setViewName("selection_province");//选择省区页面
			return model;
		}else if(1==pageId){
			model.setViewName("selection_city");//选择市区页面
			return model;
		}else{
			model.setViewName("selection_area");//选择区页面
			return model;
		}
		
		
	}*/
	@RequestMapping("/newAddress.user")
	public ModelAndView newAddress(ShippingAddress sa, Integer pageId, ModelAndView model, HttpServletRequest request,
			String orderType, String specificationsId, String productId, String quantity, String partNumbers,
			String backNum) {
		model.addObject("sa", sa);
		model.addObject("id", sa.getId());
		if (pageId == 0) {// 新增页面
			model.addObject("orderType", orderType);
			model.addObject("specificationsId", specificationsId);
			model.addObject("productId", productId);
			model.addObject("quantity", quantity);
			model.addObject("partNumbers", partNumbers);
			model.addObject("backNum", StringUtils.isEmpty(backNum) ? "0" : backNum);
			model.setViewName("newAddress");
			return model;
		} else {
			model.setViewName("updateAddress");
			return model;
		}
	}
	@RequestMapping("/addAddress.user")
	public ActResult<ShippingAddress> addAddress(){
		ActResult<ShippingAddress> ar =new ActResult<ShippingAddress>();
		ShippingAddress sa = new ShippingAddress();
		//根据用户id获取地址信息
		List<ShippingAddress> listAddress = shippingAddressService.findByUserId(loginUser.getId());
		if (listAddress==null ||listAddress.isEmpty()) {
			Supplier supplier = supplierDao.getById(loginUser.getSupplierId());
			if (!loginUser.getNickName().equals(loginUser.getPhone())) {
				sa.setName(loginUser.getNickName());
			}
			sa.setPhone(loginUser.getPhone());
			sa.setProvinceName(supplier.getComState());
			sa.setCityName(supplier.getComCity());
			sa.setAreaName(supplier.getComAdd());
			sa.setAddress(supplier.getComAddress());
			sa.setAid(AreaUtils.getACode(supplier.getComAdd()).toString());
			ar.setData(sa);
			return ar;
		}else{
			return ActResult.fail("已经存在收货地址");
		}
	}
	
}
