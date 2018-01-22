//伪元素兼容低版本浏览器
$(function(){
	$('.left_list li:last-child').css('border-bottom','none');	
	$('.scr_con:last-child').css('border-right','none');
	$('.add_list li:last-child').css('border-bottom','none');
	$('.sort_list:nth-child(even)').css('border-right','none');
})
//js输入框只等输入数字
function inutNumber(obj) {
	obj.value=obj.value.replace(/\D/g,'');
}
function inutNumber_double(obj) {
	obj.value = obj.value.replace(/[^\d.]/g,"");
}

/**
 * js清除系统禁止文字
 * 禁止文字包含 ` , # $ & = | { } [ ] ' : . \ " < > 
 */
function clearNgText(obj) {
	var val = obj.value;
	val = val.replace("`","").replace(",","").replace("#","")
		.replace("$","").replace("&","").replace("=","").replace("|","")
		.replace("{","").replace("}","").replace("[","").replace("]","")
		.replace("'","").replace(":","").replace(".","").replace("\\","")
		.replace("\"","").replace("<","").replace(">","");
	obj.value = val;
}

//清除遍历所修改公司的html内容
function clear_empty_html(selector){
	$(selector).empty();
}
//弹出公司的弹窗--开  
function ent_box_open(selector){
	$(selector).show();
}
//弹出公司的弹窗--关 
function ent_box_close(selector){
	$(selector).hide();
}
//黑背景弹出框--开
function background_open(){
	$('.popup_bg').show();
}
//黑背景弹出框--关
function background_close(){
	$('.popup_bg').hide();
}

//操作完成的弹窗，操作成功与否信息
function operation_success_box(cont){
	//设置弹窗内容
	$("#operation_success_cont").html(cont);
	operation_success_open();
}
//弹出操作完成的弹窗--开
function operation_success_open(){
	$('#operation-suceess').show();
}
//弹出操作完成的弹窗--关
function operation_success_close(){
	$('#operation-suceess').hide();
}
//关闭操作成功的弹窗
function success_close(){
	//关闭弹窗
	operation_success_close();
	//关闭背景
	background_close();
	//刷新页面
	location.reload();
}