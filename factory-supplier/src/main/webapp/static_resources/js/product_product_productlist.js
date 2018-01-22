// 修改商品排位（仅更新本条商品的排位，无其他限制）
function changeSort(productId) {
	// 获取排位数
	var sortNum = $("#productViewSort_" + productId).val();
	// 校验
	/* if(sortNum == null || sortNum == "") {
		alert("不允许为空");
	} else { */
	$.ajax({
		url : jsBasePath+'/product/setProductViewSort.json?productId='+productId+"&sortNum="+sortNum,
		type : "GET",
		dataType: "json",//返回json格式的数据  
		async:false,
		success : function(data) {
			showInfoBox("修改商品排序成功！");
		},
		error : function() {
			
		}
		
	});
	/* } */
}

/* // 修改商品排位（全部重新排序，无重复排序数， 排位数不得超过总的在售商品数）
function changeSort(productId) {
	// 校验
	// 获取排位数
	var sortNum = $("#productViewSort_" + productId).val();
	// 总的在售商品数
	var total = $("#productViewSort_total_hidden").val();
	
	if(sortNum == null || sortNum == "") {
		alert("不允许为空");
	} else if((sortNum < 1) || (sortNum > total)) {
		alert("商品排序应在 1 到 " + total + " 之间");
	} else {
		$.ajax({
			url : '${basePath}/product/setProductViewSort.json?productId='+productId+"&sortNum="+sortNum,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
			async:false,
			success : function(data) {
				if(data != null) {
					var result = eval(data);
					if(result != null && result.length > 0) {
						for(var i = 0; i < result.length; i++) {
							var productViewSort_Object = $("#productViewSort_"+result[i].id);
							if(productViewSort_Object != "") {
								productViewSort_Object.val(result[i].sortNum);
							}
						}
					}
				}
			},
			error : function() {
				
			}
		});
	}
} */



$(document).ready(function(){
	selectedHeaderLi("spgl_header");
});

$(document).ready(function(){
	ajaxGetCategoryListByids();
	ajaxGetStoreCategoryList();
	
	$('#sub_specificationsChange').submit(function() {
	    // 提交表单
	   $('#sub_specificationsChange').ajaxSubmit(function(data){
           alert(data);
       });
	    // 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
	    return false;
	   });
});

/**
 * 全选记录
 */
function checkTotal(obj) {
	var isTotalCheck = $(obj).prop("checked");
	var checkNum = 0;
	if (isTotalCheck) {
		$("input:checkbox[name='ck']").prop("checked", function(i, val) {
			        
				if($("input:checkbox[name='ck']:eq("+i+")").prop("disabled")){
		        	return false;
		        }
				checkNum = checkNum + 1;
				return true;
				});
	} else {
		$("input:checkbox[name='ck']").prop("checked", false);
	}
}

/**
 * 选择记录
 */
function check() {
	var checkTotalNum = $("input:checkbox[name='ck']");
	var isAllChecked = true;
	var checkNum = 0;
	checkTotalNum.each(function(i, val) {
				if ($(checkTotalNum[i]).prop("checked")) {
					checkNum = checkNum + 1;
				} else {
					if(!$(checkTotalNum[i]).prop("disabled")) {
						isAllChecked = false;
					}
				}
			});
	
	if (!isAllChecked) {
		$("input:checkbox[id='total']").prop("checked", false);
	} else {
		if(checkTotalNum.length!=0){
			$("input:checkbox[id='total']").prop("checked", true);
		}
	}
}

/**
 * 快速跳转
 */
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

/**
 * 表单提交
 */
function formSubmit(page){
	if(page!=undefined){
		$("#pages").val(page);
	}else{
		$("#pages").val(1);
	}
	$("#sub_form").submit();
}

/**
 * 重置
 */
function formReset(){
	//$("#sub_form").reset();
	//document.getElementById("sub_form").reset();
	$("#sub_form").find("input[type!='hidden']").val("");
	$("#sub_form").find("select").find("option:first").attr("selected","selected");
}

/**
*查看审核不通过原因
*/
function reasonView(id){
	$.ajax({
		url : jsBasePath+'/product/getProductCheckById.json?productId='+id,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html="";
			if(data.result.errorCode==0){
				html+='<div class="popup_tl">';
				html+='<span>商品条码：'+data.result.msgBody.barcode+'</span>';
				html+='<span>所属类目：'+data.result.msgBody.categoryName+'</span>';
				html+='</div>';
				html+='<div class="popup_info">';
				html+='<div class="p_img"><a href="'+jsBasePath+'/product/productView.html?productId='+data.result.msgBody.id+'"><img src="'+data.result.msgBody.image+'" width="78" height="78" alt="Me-order-img"></a></div>';
				html+='<p><a href="'+jsBasePath+'/product/productView.html?productId='+data.result.msgBody.id+'">'+data.result.msgBody.name+'</a></p>';
				html+='</div>';
				html+='<div class="faster_alter">';
				html+='<div class="alter_mark">未通过原因</div>';
				html+='<div class="alter_cont">';
				html+='<p>'+data.result.msgBody.opinion+'</p>';
				html+='</div>';
				html+='</div>';
				html+='<div class="clear"></div>';
				html+=' <div class="popup_btn">';
				html+='<div class="popupbtn"><a  href="javascript:void(0);" onclick="cancelButton(\'shop_popup_fail\');">确认</a></div>';
				html+='</div>';
			}
			$("#shop_popup_fail").find(".popup_cont").html(html);
			$(".popup_bg").show();
			$("#shop_popup_fail").show();
		}, error : function() {    
	    }  
	});
}


function deleteAllObject(id,isMarketable){
	if(id==0){//批量删除
		var $list = $("[name=ck]:checked");
		if($list.length==0){
			showInfoBox("至少选择一条!");
			return;
		}else{
			$("#shop_popup_delete").find("#id_del").val(id);
			$("#shop_popup_delete").find("#isMarketable_del").val(isMarketable);
			$("#shop_popup_delete").show();
			$(".popup_bg").show();
		}
	}else{//单个删除
		$("#shop_popup_delete").find("#id_del").val(id);
		$("#shop_popup_delete").find("#isMarketable_del").val(isMarketable);
		$("#shop_popup_delete").show();
		$(".popup_bg").show();
	}
	
}
//删除提交
function subforDelete(){
	var id=$("#shop_popup_delete").find("#id_del").val();
	var isMarketable = $("#shop_popup_delete").find("#isMarketable_del").val();
	var status = null;
	updateAllObject(id,isMarketable,status);
}

function cancelAllObject(id,status){
	if(id==0){//批量删除
		var $list = $("[name=ck]:checked"); //获取所有被选中的checkbox
		if($list.length==0){
			showInfoBox("至少选择一条!");
			return;
		}else{
			$("#shop_popup_cancel").find("#id_cancel").val(id); //find()方法获得当前元素集合中每个元素的后代
			$("#shop_popup_cancel").find("#status_cancel").val(status);
			$("#shop_popup_cancel").show();
			$(".popup_bg").show();
		}
	}else{//单个删除
		$("#shop_popup_cancel").find("#id_cancel").val(id);
		$("#shop_popup_cancel").find("#status_cancel").val(status);
		$("#shop_popup_cancel").show(); //show的用法如果被选元素已被隐藏，则显示这些元素
		$(".popup_bg").show();
	}
	
}

function subforCancel(){
	var id=$("#shop_popup_cancel").find("#id_cancel").val();
	var status = $("#shop_popup_cancel").find("#isMarketable_cancel").val();
	var isMarketable = null;
	updateAllObject(id,isMarketable,status);
}

/**
 * 删除（批量删除、单个删除）
 
 更新删除，上下架，取消审核的操作
 */
function updateAllObject(id,isMarketable,status){
	var ids = "";
	if(id==0){//批量删除
		$list = $("[name=ck]:checked");
		$list.each(function(i,val){ //each()方法规定为每个匹配元素规定运行的函数。
			ids = ids+$($list[i]).attr("value")+",";
		});
		if(ids!=""){
			ids = ids.substring(0,ids.length-1);//删除最后一个逗号
		}
	}else{//单个删除
		ids = id;
	}
	var selltype=$("#selltype").val();
	
	var datapate = {
			isMarketable:isMarketable,
			status:status,
			ids:ids,
			selltype:selltype
	}
	if(ids!=''){
		$.ajax({
			url : jsBasePath+'/product/ajaxUpdate.json',
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:datapate,
			success : function(data) {
				if(data.result.errorCode==0){
					formSubmit(1);//表单提交刷新页面
				}
			}, error : function() {    
		    }  
		});
	}else{
		showInfoBox("至少选择一条!");
	}
}

/**
*ajax加载类目
*/
function ajaxGetCategoryListByids(){
	var categoryidtemp = $("#categoryidtemp").val();
	
	$.ajax({
		url : jsBasePath +'/productCategory/ajaxGetCategoryListByids.json',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html = '<option value="">--请选择--</option>';
			if(data.result.errorCode==0){
				if(data.result.msgBody.length>0){
					for(var i=0;i<data.result.msgBody.length;i++){
						if(data.result.msgBody[i].id==categoryidtemp){
							html+='<option value="'+data.result.msgBody[i].id+'" selected>'+data.result.msgBody[i].name+'</option>';
						}else{
							html+='<option value="'+data.result.msgBody[i].id+'">'+data.result.msgBody[i].name+'</option>';
						}
					}
				}
			}
			$("#categoryid").html(html);
		}, error : function() {    
	     }  
	});
}


/**
*ajax加载商家自定义类目
*/
function ajaxGetStoreCategoryList(){
	var stoIdtemp = $("#stoIdtemp").val();

	$.ajax({
		url : jsBasePath +'/category/ajaxGetStoreCategoryList.json',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html = '<option value="">--请选择--</option>';
			if(data.result.errorCode==0){
				if(data.result.msgBody.length>0){
					for(var i=0;i<data.result.msgBody.length;i++){
						if(data.result.msgBody[i].id==stoIdtemp){
							html+='<option value="'+data.result.msgBody[i].id+'" selected>'+data.result.msgBody[i].name+'</option>';
						}else{
							html+='<option value="'+data.result.msgBody[i].id+'">'+data.result.msgBody[i].name+'</option>';
						}
					}
				}
			}
			$("#stoId").html(html);
		}, error : function() {    
	     }  
	});
}


/**
 * 弹出层 上下架或提交审核
 */
function subCheckAlert(name,id,act){
	//alert(name+"_"+id);
	$("#shop_popup_true").find("#alertproductname").text(name);
	$("#shop_popup_true").find(".btn_ok").attr("act",act);
	$("#shop_popup_true").find(".alertproductid").val(id);
	$("#shop_popup_true").show();
	$(".popup_bg").show();
}

/**
 * 确定提交审核或上下架
 */
function subforCheck(obj){
	var id = $(obj).prev(".alertproductid").val(); //prev()获得匹配元素集合中每个元素紧邻的前一个同胞元素，遍历每个obj，找到类名为 ".alertproductid" 的前一个同胞元素
	var selltype = $("#selltype").val();
	
	if(selltype=='selling'){//下架（只有在售商品才有下架功能）
		var ids=[id];
		batchSellOff(ids);
	}else if(selltype=='waitsell'){//上架(只有待售才有上架功能，审核和问题商品没有上架功能)
		var ids=[id];
		batchSellOn(ids);
	}else if(selltype=='waitcheck' || selltype =='reject'){
		updateAllObject(id,null,0);	//如果是待审核和问题商品，则上架状态设为null，status设为0，这里怎么把上架状态设为null呢，在controller1592行中判断isMarketable为空的话，执行的操作
	}
}
/**
 * 弹出层 上架
 */
function batchSellOnAlert(){
	var $listAll = $("[name=ck]:checked");
	if($listAll.length>0){
		$("#shop_popup_true_all").show();
		$(".popup_bg").show();
		$("#shop_popup_true_all").find(".btn_ok").unbind("click").bind("click",function(){
			batchSellOn();
		});
	}else{
		showInfoBox("至少选择一条!");
	}
}
/**
 * 批量上架
 */
function batchSellOn(ids){
	if(typeof(ids)=="undefined"){
		$list = $("[name=ck]:checked");
		ids=[];
		$list.each(function(i,val){
			ids.push($(this).val()); //push()方法可向数组的末尾添加一个或多个元素，并返回新的长度
		});
	}

	cancelButton("shop_popup_true_all");
	
	if(ids.length>0){
		$.ajax({
			url : jsBasePath +'/product/ajaxSellOn.json',
			type : "GET",
			dataType: "json", 
			data:{"ids":ids},
		    async: true,
			success : function(data) {
				if(data.actResult.success){
					if(data.actResult.msg!=null && data.actResult.msg!="") {
						showInfoBox(data.actResult.msg);
						setTimeout("formSubmit(1)", 1500);
					} else {
						formSubmit(1);//表单提交刷新页面
					}
				}else{
					showInfoBox(data.actResult.msg);
				}
				
			}
		})
		
	}else{
		showInfoBox("至少选择一条!");
	}
}
/**
 * 弹出层 批量上架，下架取消
 */
function batchCheckcancelAlert(){
	var $listAll = $("[name=ck]:checked");
	if($listAll.length>0){
		$("#shop_popup_true_all").show();
		$(".popup_bg").show();
		$("#shop_popup_true_all").find(".btn_ok").unbind("click").bind("click",function(){
			updateAllObject(0,null,0);
		});
	}else{
		showInfoBox("至少选择一条!");
	}
}
/**
 * 弹出层 下架
 */
function batchSellOffAlert(){
	var $listAll = $("[name=ck]:checked");
	if($listAll.length>0){
		$("#shop_popup_true_all").show();
		$(".popup_bg").show();
		$("#shop_popup_true_all").find(".btn_ok").unbind("click").bind("click",function(){
			batchSellOff();
		});
	}else{
		showInfoBox("至少选择一条!");
	}
}
/**
 * 批量下架
 */
function batchSellOff(ids){
	if(typeof(ids)=="undefined"){
		$list = $("[name=ck]:checked");
		ids=[];
		$list.each(function(i,val){
			ids.push($(this).val());
		});
	}
	
	if(ids.length>0){
		$.ajax({
			url : jsBasePath +'/product/ajaxSellOff.json',
			type : "POST",
			data:{"ids":ids},
			dataType: "json",   
		    async: true,
			success : function(data) {
				if(data.actResult.success){
					formSubmit(1);//表单提交刷新页面
				}else{
					cancelButton("shop_popup_true");
					showInfoBox(data.actResult.msg);
				}
				
			}
		})
		
	}else{
		showInfoBox("至少选择一条!");
	}
}

/**
*弹出层修改sku价格
**/
function ajaxUpdatePrice(productid,priceOrstock){
	var ftop =$("#shop_popup").offset().top+150;
	$("#shop_popup").attr("style","z-index:9999999;position:absolute!important;top:"+ftop+"px;width:830px;");
	
	
	$("#alertPriceDiv").html('<img alt="加载中" src="<%=static_resources %>images/loading_updateproduct.gif" style="margin:20px 40% 40px 40%">');
	$(".popup_bg").show();
	$("#shop_popup").show();
	
	var selltype=$("#selltype").val();
	var display =0;
	$.ajax({
		url : jsBasePath +'/product/ajaxGetProductForUpdate.json?id='+productid+'&display='+display,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var stringTime = data.result.msgBody.updateDate;
			var d =new Date(parseInt(stringTime));
			if (!!window.ActiveXObject || "ActiveXObject" in window){//区分IE
	           var date=d.toLocaleString(); 
	        }else{
	           var date=d.toLocaleString('chinese',{hour12:false});        //
	        }       
			var html='';
			if(data.result.errorCode==0){
	    	html+='<div class="popup_tl">';
	    	html+='<span>商品条码：'+data.result.msgBody.barcode+'</span>';
	    	html+='<span>所属类目：'+data.result.msgBody.categoryName+'</span>';
	    	if(data.result.msgBody.saleKbn==4){
		    	html+='<span>(员工专享价：'+data.result.msgBody.empPrice.toFixed(2)+'元)</span>';
	    	}else if(data.result.msgBody.saleKbn==5){
	    		html+='<span>(评价后返现：'+data.result.msgBody.empPrice.toFixed(2)+'元)</span>';
	    	}
	    	if(selltype=='selling' &&  data.result.key=="1"){
	    		html+='<div class="add_new" style="float:right; margin-right:30px">';
	    		html+='<a href="http://www.wd-w.com/'+ productid +'.html" target="_blank" style="color: #2b8dff">查看在售信息</a></div>';
	    		html+='<div style="float:right;margin-right:50px">';
	    		html+='<span>已于&nbsp;'+date+'&nbsp;修改</span>';
	    		html+='</div>';
	    	}
	    	html+='<input type="hidden" id="bak_saleKbn" value="'+data.result.msgBody.saleKbn+'"/>';
	    	html+='<input type="hidden" id="bak_empPrice" value="'+data.result.msgBody.empPrice+'"/>';
	    	html+='</div>';
	    	html+='<div class="popup_info">';
	    	html+='<div class="p_img"><a href="#"><img src="'+data.result.msgBody.image+'" width="78" height="78" alt="Me-order-img"></a></div>';
	    	html+='<p><a href="#">'+data.result.msgBody.fullName+'</a></p>';         
	    	html+='</div>';
	    	html+='<div class="faster_alter">';
	    	html+='<div class="alter_mark">快捷修改</div>';
	    	html+='<div class="alter_cont" style="width:730px;">';
	    	html+='<div class="alter_cont_title" style="width:586px;">';
	    	html+='<strong>电商价</strong>';
	    	html+='<strong>内购价</strong>';
	    	html+='<strong>库存</strong>';
	    	//html+='<strong>预警值</strong>'; 
	    	//html+='<strong>可用内购券金额</strong>'; 
	    	html+='<strong>起售数量</strong>'; 
	    	html+='</div>';
	    	html+='<form id="sub_specificationsChange"  action="'+jsBasePath+'/product/ajaxSpecificationsChange.html" method="post">';
	    	html+='<input type="hidden" id="selltypenew" name="selltypenew" value="'+jsSelltype+'"/>';
	    	html+='<input type="hidden" id="productid" name="productid" value="'+data.result.msgBody.id+'"/>';
	    	if(data.result.msgBody.productSpecificationslist!=null&&data.result.msgBody.productSpecificationslist!=""){
	    		for(var i=0;i<data.result.msgBody.productSpecificationslist.length;i++){
	    			if(priceOrstock==2){
		    			html+='<div class="alter_cont_list">';
		    	    	html+='<span style="width: 130px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;cursor:default;" title="'+data.result.msgBody.productSpecificationslist[i].itemnames+'">'+data.result.msgBody.productSpecificationslist[i].itemnames+'</span>';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);" onblur="specificationsChange(this)" name="productprice"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d)$/,\'$1$2.$3\');this.value=$.trim(this.value);"  ismust="1"   typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].price+'">';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);" oninput="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="fproductprice"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d)$/,\'$1$2.$3\');this.value=$.trim(this.value);"  ismust="1"   typename="input"   maxLength="8" value="';
		    	    	//判断如果有内购价直接显示，没有则进行判断
		    	    	if(data.result.msgBody.productSpecificationslist[i].internalPurchasePrice){
		    	    		html+=data.result.msgBody.productSpecificationslist[i].internalPurchasePrice;
		    	    	}else{
		    	    		html+=(data.result.msgBody.productSpecificationslist[i].price-data.result.msgBody.productSpecificationslist[i].maxFucoin).toFixed(2);
		    	    	}
		    	    	html+='">';
		    	    	html+='<input class="common_input f98" type="text" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="productnum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].stock+'">';
		    	    	html+='<input class="common_input f98" type="hidden" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="warnnum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].warnnum+'">';
		    	    	html+='<input class="common_input f98" type="hidden" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="maxFucoin" value="'+data.result.msgBody.productSpecificationslist[i].maxFucoin+'">';
		    	    	html+='<input class="common_input f98" type="text" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="minLimitNum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].minLimitNum+'">';
		    	    	html+='<input type="hidden" id="'+data.result.msgBody.productSpecificationslist[i].id+'" name="specifications_result" value="'+data.result.msgBody.productSpecificationslist[i].id+'_'+data.result.msgBody.productSpecificationslist[i].price+'_'+data.result.msgBody.productSpecificationslist[i].stock+'_'+data.result.msgBody.productSpecificationslist[i].warnnum+'_'+data.result.msgBody.productSpecificationslist[i].maxFucoin+ '_' + data.result.msgBody.productSpecificationslist[i].internalPurchasePrice+ '_' + data.result.msgBody.productSpecificationslist[i].minLimitNum + '"/>';
		    	    	html+='</div>';
	    			}else if(priceOrstock==1){
	    				html+='<div class="alter_cont_list">';
		    	    	html+='<span style="width: 130px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;cursor:default;" title="'+data.result.msgBody.productSpecificationslist[i].itemnames+'">'+data.result.msgBody.productSpecificationslist[i].itemnames+'</span>';
		    	    	html+='<input class="common_input f98" type="text"  onblur="specificationsChange(this)" name="productprice"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d)$/,\'$1$2.$3\');this.value=$.trim(this.value);"  ismust="1"   typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].price+'">';
		    	    	html+='<input class="common_input f98" type="text" onblur="fpriceChange(this)"  oninput="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="fproductprice"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d)$/,\'$1$2.$3\');this.value=$.trim(this.value);"  ismust="1"   typename="input"   maxLength="8" value="';
		    	    	//判断如果有内购价直接显示，没有则进行判断
		    	    	if(data.result.msgBody.productSpecificationslist[i].internalPurchasePrice){
		    	    		html+=data.result.msgBody.productSpecificationslist[i].internalPurchasePrice;
		    	    	}else{
		    	    		html+=(data.result.msgBody.productSpecificationslist[i].price-data.result.msgBody.productSpecificationslist[i].maxFucoin).toFixed(2);
		    	    	}
		    	    	html+='">';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="productnum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].stock+'">';
		    	    	html+='<input class="common_input f98" type="hidden" readonly="readonly" style="background-color: rgb(242, 242, 242);" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="warnnum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].warnnum+'">';
		    	    	html+='<input class="common_input f98" type="hidden" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="maxFucoin" value="'+data.result.msgBody.productSpecificationslist[i].maxFucoin+'">';
		    	    	html+='<input class="common_input f98" type="text" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="minLimitNum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].minLimitNum+'">';
		    	    	html+='<input type="hidden" id="'+data.result.msgBody.productSpecificationslist[i].id+'" name="specifications_result" value="'+data.result.msgBody.productSpecificationslist[i].id+'_'+data.result.msgBody.productSpecificationslist[i].price+'_'+data.result.msgBody.productSpecificationslist[i].stock+'_'+data.result.msgBody.productSpecificationslist[i].warnnum+'_'+data.result.msgBody.productSpecificationslist[i].maxFucoin+'_' + data.result.msgBody.productSpecificationslist[i].internalPurchasePrice+ '_' + data.result.msgBody.productSpecificationslist[i].minLimitNum + '"/>';
		    	    	html+='</div>';
	    			}else{
	    				html+='<div class="alter_cont_list">';
		    	    	html+='<span style="width: 130px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;cursor:default;" title="'+data.result.msgBody.productSpecificationslist[i].itemnames+'">'+data.result.msgBody.productSpecificationslist[i].itemnames+'</span>';
		    	    	html+='<input class="common_input f98" type="text" onblur="specificationsChange(this)" name="productprice"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d)$/,\'$1$2.$3\');this.value=$.trim(this.value);"  ismust="1"   typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].price+'">';
		    	    	html+='<input class="common_input f98" type="text" onblur="fpriceChange(this)" oninput="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="fproductprice"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d)$/,\'$1$2.$3\');this.value=$.trim(this.value);"  ismust="1"   typename="input"   maxLength="8" value="';
		    	    	//判断如果有内购价直接显示，没有则进行判断
		    	    	if(data.result.msgBody.productSpecificationslist[i].internalPurchasePrice){
		    	    		html+=data.result.msgBody.productSpecificationslist[i].internalPurchasePrice;
		    	    	}else{
		    	    		html+=(data.result.msgBody.productSpecificationslist[i].price-data.result.msgBody.productSpecificationslist[i].maxFucoin).toFixed(2);
		    	    	}
		    	    	html+='">';
		    	    	html+='<input class="common_input f98" type="text" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="productnum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].stock+'">';
		    	    	html+='<input class="common_input f98" type="hidden" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)" onclick="specificationsChange(this)" name="warnnum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].warnnum+'">';
		    	    	html+='<input class="common_input f98" type="hidden" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="maxFucoin" value="'+data.result.msgBody.productSpecificationslist[i].maxFucoin+'">';
		    	    	html+='<input class="common_input f98" type="text" onchange="specificationsChange(this)" onpropertychange="specificationsChange(this)"  onclick="specificationsChange(this)" name="minLimitNum"  ismust="1"  onkeyup="this.value=this.value.replace(/\\D/g,\'\');"  typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].minLimitNum+'">';
		    	    	html+='<input type="hidden" id="'+data.result.msgBody.productSpecificationslist[i].id+'" name="specifications_result" value="'+data.result.msgBody.productSpecificationslist[i].id+'_'+data.result.msgBody.productSpecificationslist[i].price+'_'+data.result.msgBody.productSpecificationslist[i].stock+'_'+data.result.msgBody.productSpecificationslist[i].warnnum+'_'+data.result.msgBody.productSpecificationslist[i].maxFucoin+'_' + data.result.msgBody.productSpecificationslist[i].internalPurchasePrice+ '_' + data.result.msgBody.productSpecificationslist[i].minLimitNum + '"/>';
		    	    	html+='</div>';
	    			}
	    		}
    		}
	    	html+='</form>';
	    	html+='</div>';
	        if(selltype=='selling' &&  data.result.key=="1"){
	    	html+='<div style="float:right;margin-right:20px" class="showselling"><a href="javascript:void(0)"; onclick="clickshowselling('+productid+','+priceOrstock+')" style="color: #2b8dff">+在售价格</a></div>';
	        }
	    	html+='</div>';
	    	html+='<div id="displayselling" style="display:none">';
	    	html+='</div>';
	    	html+='<div><span style="display:none;color:#F00" id="errorMsg"/></div>';
	    	html+='<div class="clear"></div>';
	    	html+='<div class="popup_btn">';
	    	html+='<a href="Javascript:void(0);" onclick="fproductpriceChangeValidate()">确认</a>';
	    	html+='<a href="javascript:void(0);"  onclick="cancelButton(\'shop_popup\');">取消</a>';
	    	
	    	html+='</div>';
	    	
            
			
			}
			$("#alertPriceDiv").html(html);
		}, error : function() {    
	     }  
	});
}

/*
 * 在售价格显示 onclick事件
 */
function clickshowselling(productid,priceOrstock){
	
	var display=1;
	$.ajax({
		url : jsBasePath +'/product/ajaxGetProductForUpdate.json?id='+productid+'&display='+display,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';
			if(data.result.errorCode==0){
			html+='<div class="faster_alter">';
	    	html+='<div class="alter_mark">在售价格</div>';
	    	html+='<div class="alter_cont" style="width:730px;">';
	    	html+='<div class="alter_cont_title" style="width:586px;">';
	    	html+='<strong>电商价</strong>';
	    	html+='<strong>内购价</strong>';
	    	html+='<strong>库存</strong>';
	    	//html+='<strong>预警值</strong>'; 
	    	//html+='<strong>可用内购券金额</strong>'; 
	    	html+='<strong>起售数量</strong>';
	    	html+='</div>';
			if(data.result.msgBody.productSpecificationslist!=null&&data.result.msgBody.productSpecificationslist!=""){
	    		for(var i=0;i<data.result.msgBody.productSpecificationslist.length;i++){
						html+='<div class="alter_cont_list">';
		    	    	html+='<span style="width: 130px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;cursor:default;" title="'+data.result.msgBody.productSpecificationslist[i].itemnames+'">'+data.result.msgBody.productSpecificationslist[i].itemnames+'</span>';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="productprice"    ismust="1"   typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].price+'">';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="fproductprice"    ismust="1"   typename="input"   maxLength="8" value="';
		    	    	//判断如果有内购价直接显示，没有则进行判断
		    	    	if(data.result.msgBody.productSpecificationslist[i].internalPurchasePrice){
		    	    		html+=data.result.msgBody.productSpecificationslist[i].internalPurchasePrice;
		    	    	}else{
		    	    		html+=(data.result.msgBody.productSpecificationslist[i].price-data.result.msgBody.productSpecificationslist[i].maxFucoin).toFixed(2);
		    	    	}
		    	    	html+='">';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="productnum"  ismust="1"    typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].stock+'">';
		    	    	html+='<input class="common_input f98" type="hidden" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="warnnum"  ismust="1"    typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].warnnum+'">';
		    	    	html+='<input class="common_input f98" type="hidden" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="maxFucoin" value="'+data.result.msgBody.productSpecificationslist[i].maxFucoin+'">';
		    	    	html+='<input class="common_input f98" type="text" readonly="readonly" style="background-color: rgb(242, 242, 242);"  name="minLimitNum"  ismust="1"    typename="input"   maxLength="8" value="'+data.result.msgBody.productSpecificationslist[i].minLimitNum+'">';
		    	    	html+='</div>';
		    	    	
				}
	    		html+='<div class="add_new" style="float:right;margin-top:16px;">';
	    		html+='<a href="javascript:void(0);" onclick="deleteModifyAppr('+productid+','+priceOrstock+')">还原在售信息</a></div>';
    		}

			html+='</div>';
			html+='</div>';	
			}
			$("#displayselling").html(html);
			if ($("#displayselling").is(":hidden")) {
				$("#displayselling").show();
				$(".showselling a").html("-在售价格");
			} else {
				$("#displayselling").hide();
				$(".showselling a").html("+在售价格");
			}
			
		},error : function() {    
	     } 
	});
		
}

/*
点击还原在售信息按钮就删除前次修改的数据
*/
function deleteModifyAppr(productid,priceOrstock){
	
	$.ajax({
		url : jsBasePath +'/product/deleteModifyAppr.json?productId='+productid,

		type : "GET",
		async : false,
		dataType: "json",  //返回json格式的数据  
	    success: function(data){       
		    if(data==1){
		    	$("#displayselling").hide();
	          //刷新快捷修改页面，让其显示在售信息
	           //setTimeout("ajaxUpdatePrice("+productid+","+priceOrstock+")",1500); 
	          ajaxUpdatePrice(productid,priceOrstock);
		    }
	    }, error : function() {}           
	});
}
//快捷修改设置内购价不能低于特享价格或试用价格 
function fpriceChange(obj){
	var fproductprice = $(obj).val();
	var baksalekbn = $("#bak_saleKbn").val();
	var bakempprice =$("#bak_empPrice").val();
	if(baksalekbn==4){
		if(parseFloat(fproductprice) < parseFloat(bakempprice)){
			$("#errorMsg").html('内购价不能低于员工专享价！');
            //让span显示出来
            $("#errorMsg").show();
			return false;
		}else{
			$("#errorMsg").hide();
		}
	}else if(baksalekbn==5){
		if(parseFloat(fproductprice) < parseFloat(bakempprice)){
			$("#errorMsg").html('内购价不能低于评价后返现金额！');
            //让span显示出来
            $("#errorMsg").show();
			return false;
		}else{
			$("#errorMsg").hide();
		}
	}
	
	return true;
}

/*
 *快捷商品修改校验内购价不能低于特享和试用价格 onclick事件 
 */
function fproductpriceChangeValidate(){
	var baksalekbn = $("#bak_saleKbn").val();
	var bakempprice =$("#bak_empPrice").val();
	if(baksalekbn==4 || baksalekbn==5) {
		var lsp = $("input[name='fproductprice']");
		for(var i=0;i<lsp.length;i++){
			 if(!fpriceChange($(lsp[i]))){
				return false;
			} 
		}	
	}
	$('#sub_specificationsChange').submit();	
}
function cancelButton(id){
	$("#"+id).hide();
	$(".popup_bg").hide();
}
/**
*skuchange
*/
function specificationsChange(obj){
	$(obj).removeClass("bctxt");
	var $par =$(obj).parent(".alter_cont_list");
	var productprice = $par.find("[name=productprice]").val();
	var productnum = $par.find("[name=productnum]").val();
	var warnnum = $par.find("[name=warnnum]").val();
	var fproductprice = $par.find("[name=fproductprice]").val();
	var minLimitNum = $par.find("[name=minLimitNum]").val();
	var maxFucoin= 0; //$par.find("[name=maxFucoin]").val();
	if(fproductprice==''){
		fproductprice = 0;
	}
	if(productprice!=''&&fproductprice!=''){
		if(parseFloat(fproductprice)>parseFloat(productprice)){
			$par.find("[name=fproductprice]").val(productprice);
			fproductprice = parseFloat(productprice);
			maxFucoin = 0;
			$par.find("[name=maxFucoin]").val(0);
		} else {
			maxFucoin = parseFloat(productprice)-parseFloat(fproductprice);
			maxFucoin = maxFucoin.toFixed(2);
			$par.find("[name=maxFucoin]").val(maxFucoin);
		}
	}
	
	if(productprice!=''&&productnum!=''&&warnnum!=''){
		var id = $par.find("[name=specifications_result]").attr("id");
		$par.find("[name=specifications_result]").val(id+"_"+productprice+"_"+productnum+"_"+warnnum+"_"+maxFucoin + "_" + fproductprice + "_" + minLimitNum);
	}else{
		$par.find("[name=specifications_result]").val("");
	}
}

function ajaxFormSubmit(){
	$('#sub_specificationsChange').ajaxForm(function() {
		showInfoBox("Thank you for your comment!");
    });
}

/**
 *排序
 */
function sortOclick(name){
	if($("#"+name).val()==''){
		$("#"+name).val(1);
	}
	
	if($("#"+name).val()==1){
		$("#"+name).val(2);
	}else{
		$("#"+name).val(1);
	}
	
	if($("[name='"+name+"_i'").hasClass("down")){
		$("[name='"+name+"_i'").removeClass("down");
	}else{
		$("[name='"+name+"_i'").addClass("down");
	}
	
	$("#sortname").val(name);
	
	formSubmit();
}

/**
 * 商品详情
 */
function productVideo(id){
 	window.location.href=jsBasePath+"/product/productView.html?productId="+id;	
}
