/**
 * 订单相关操作 session 记录状态及处理页面回退
 * created 下单成功
 * payZero 全部抵扣无需支付
 * payCancel 支付取消
 * paySuccess 支付成功
 * orderPay 子单支付
 */

function sessionBefore2Order() {
	sessionStorage.removeItem("orderStep");
}

function sessionSetOrderStep(step) {
	if(""==step || step==null) {
		sessionStorage.removeItem("orderStep");
	} else {
		sessionStorage.setItem("orderStep",step);
	}
}

function sessionCheckOrder(action,type) {
	var orderStep =sessionStorage.getItem("orderStep");
	if("order_confirm" == action) {
		// 订单确认页
		if(typeof(orderStep)==undefined || orderStep==null || orderStep=="" ){
			return true;
		} else {
			if("orderList"== orderStep) {
				history.back();
			} else if("payCancel"== orderStep || "created"== orderStep) {
				if(type==5){
					window.location=jsBasePath +'exchangeOrder/myhl.user?uid='+uid+'&from=pay';
				}else if(type==0){
					window.location=jsBasePath +'order/page?status=0&from=pay';
				} else {
					history.back();
				}
			} else {
				history.back();
			}
			return false;
		}
	} else if("pay" == action) {
		// 支付页
		if("created"== orderStep || "orderPay"== orderStep){
			return true;
		} else {
			history.back();
			return false;
		}
	} else if("orderList" == action) {
		// 未支付订单
		if("payCancel"== orderStep || "created"== orderStep){
			showInfoBox("订单未完成支付，可在24小时内，点击订单进行支付");
			sessionSetOrderStep("orderList");
		}
		return true;

	} else if("paySuccess" == action) {
		// 支付成功页
		return ture;
	}
	return true;
}