package com.wode.factory.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wode.common.util.NumberUtil;

/**
 * 购物车
 * @author haisheng
 *
 */
public class Cart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CartItem> items;//购物车项
	private Object errKey;
	//private Map<String, List<CartItem>> items;//购物车项，按supplierId拆分购车项
	
	
	public Cart() {
		items = new ArrayList<CartItem>();
	}
	
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	/**
	 * 根据商家分组
	 * @return
	 * key ：商家id
	 */
	public Map<Long, List<CartItem>> groupBySupplier() {
		 Map<Long, List<CartItem>> ret=new LinkedHashMap();
		 
		for(int j = 0; j < items.size(); j++){
			CartItem cartItem = items.get(j);
			List<CartItem> li=ret.get(cartItem.getSupplierId());
			if(li==null){
				li=new ArrayList();
				ret.put(cartItem.getSupplierId(), li);
			}
			li.add(cartItem);
		}
		return ret;
	}


	
	
	//计算购物车总价
	public BigDecimal calculateTotalPrice(){
		BigDecimal totalPrice =BigDecimal.ZERO;
		for(int j = 0; j < items.size(); j++){
			CartItem cartItem = items.get(j);
			if(cartItem.isBuyFlag()){
				totalPrice = totalPrice.add(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
			}
		}
		
		return totalPrice;
	}
	
	//计算购物车总价(不包含内购券)
		public BigDecimal calculateTotalRealPrice(){
			BigDecimal totalPrice =BigDecimal.ZERO;
			for(int j = 0; j < items.size(); j++){
				CartItem cartItem = items.get(j);
				if(cartItem.isBuyFlag()){
					totalPrice = totalPrice.add(cartItem.getRealPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
				}
			}
			
			return totalPrice;
		}
		//计算购物车总价(包含内购券 用内购价加上内购券)
		public BigDecimal calculateTotalRealPriceWithMaxCompanyTicket(){
			BigDecimal totalPrice =BigDecimal.ZERO;
			for(int j = 0; j < items.size(); j++){
				CartItem cartItem = items.get(j);
				if(cartItem.isBuyFlag()){
					totalPrice = totalPrice.add((cartItem.getRealPrice().add(null == cartItem.getMaxCompanyTicket() ?BigDecimal.ZERO:cartItem.getMaxCompanyTicket())).multiply(BigDecimal.valueOf(cartItem.getQuantity())));
				}
			}
			
			return totalPrice;
		}
	
	//计算购物车总数量
	public int calculateTotalNumber(){
		int num = 0;
			for(int j = 0; j < items.size(); j++){
				CartItem cartItem = items.get(j);
				num += cartItem.getQuantity();
			}
		
		return num;
	}
	
	//计算已购买商品总运费
	public BigDecimal calculateTotalFreight(){
		BigDecimal totalFreight = BigDecimal.ZERO;
		for(int j = 0; j < items.size(); j++){
			CartItem cartItem = items.get(j);
			totalFreight = totalFreight.add(cartItem.getFreight()==null?BigDecimal.ZERO:cartItem.getFreight());
		}

		return totalFreight;
	}

	/**
	 * 计算不同供应商的运费，按同一商品 的不同规格的商品 算一份运费，忽略购买数量、重量
	 * @param id 
	 * 商家id
	 * @return
	 */
	public BigDecimal calculateSupplierFreight(Long id){
		BigDecimal totalFreight = BigDecimal.ZERO;
		for(int j = 0; j < items.size(); j++){
			CartItem cartItem = items.get(j);
			if(cartItem.getSupplierId().equals(id)&&cartItem.isBuyFlag()){
				totalFreight = totalFreight.add(cartItem.getFreight()==null?BigDecimal.ZERO:cartItem.getFreight());
			}
		}
		return totalFreight;
	}
	
	//删除
	 public boolean deleteItem(String partNumber){
		 boolean isRemove = false;//是否已删除
		
		 Iterator<CartItem> it=items.iterator();
		 while (it.hasNext()) {
			 CartItem item =it.next();
			 if(item.getPartNumber().equals(partNumber)){//遍历同一供应商的购物项，是否已购买
				 it.remove();
				 isRemove=true;
				 break;
			 }
		 }
		 
		 return isRemove;
	 }
	 
	
	 
	//设置购买商品
	public List<CartItem> setSelectProduct(Set<Long> skuIds) {
		
		List<CartItem> list = new ArrayList<CartItem>();
			
		for (CartItem cartItem : items) {
			if (!skuIds.contains(Long.parseLong(cartItem.getPartNumber()))) {// 是否有选中商品
				cartItem.setBuyFlag(false);
			} else {
				cartItem.setBuyFlag(true);
				list.add(cartItem);
			}
		}
				
			
			
		
		
		return list ;
		
	}
	 
	 //下单完成，清除已购买商品
	 public void clearBuied(){
		 Iterator<CartItem> it=items.iterator();
		 while(it.hasNext()){
			 CartItem item =it.next();
			 if(item.isBuyFlag()){
				 it.remove();
			 }
		 }
	 }
	 
	 //下单完成，清除已购买商品
	 public void clearBuied(List<CartItem> buyItems){
		 for (CartItem cartItem : buyItems) {
			 CartItem item = this.getItem(NumberUtil.toLong(cartItem.getPartNumber()));
			 if(item!=null) {
				 items.remove(item);
			 }
		}
	 }
	 
	 
	 public void addItem(CartItem item){
		 if(!items.contains(item)){
			 items.add(item);
		 }
	 }
	 
	 public CartItem getItem(Long skuid){
		 CartItem rets =null;
		 for(CartItem item : items){
			 if(item.getPartNumber().equals(skuid+"")){
				 rets=item;
				 break;
			 }
		 }
		 return rets;
	 }
	 

	 public List<CartItem> getItemsByProductId(Long pid){
		 List<CartItem> list = new ArrayList<CartItem>();
		 for(CartItem item : items){
			 if(item.getProductId().equals(pid)){
				 list.add(item);
			 }
		 }
		 return list;
	 }
	
	 public List<CartItem> getAllItems(){
		
		 return items;
	 }

	public Object getErrKey() {
		return errKey;
	}

	public void setErrKey(Object errKey) {
		this.errKey = errKey;
	}
	 
}
