
$(document).ready(function(){
	ajaxGetLevelCnt();//获取员工数量
	if(limitType && saleKbn && saleKbn==2){//换领
		setLimitType(limitType);
	}
	if(saleKbn && saleKbn==5){//试用
		var minInternalPurchasePrice=getMinInternalPurchasePrice();
		var empcash = minInternalPurchasePrice-$("#trialPrice").val();
		$("#calT em").html(parseFloat(empcash).toFixed(2));
	}
})
function ajaxGetLevelCnt(){
		var basePath = jsBasePath;
		$.ajax({
			url : basePath +'/company/emp/ajaxGetEmpLevelCount.json?supplierId='+supplierId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				var ls = data.lsc;
				var al = 0;
				var html = '';
				var html2 = '';
				for(var i=0;i<ls.length;i++) {
					al += ls[i].levelCount;
					html += '<div style="width:100px;float:left;padding-left:14px;"><input type="radio" name="divLevel" disabled="disabled" id="divLevel'+ls[i].welfareLevel+'" value="'+ls[i].welfareLevel+'" data="'+ls[i].levelCount+'" style="vertical-align: middle;margin: -2px 4px 1px 0;"><label for="divLevel'+ls[i].welfareLevel+'">'+ls[i].welfareLevel+'级（'+ls[i].levelCount+'）</label></div>';
					html2 += '<div style="width:100px;float:left;padding-left:14px;"><input type="radio" name="empLevel" disabled="disabled" id="empLevel'+ls[i].welfareLevel+'" value="'+ls[i].welfareLevel+'" data="'+ls[i].levelCount+'" style="vertical-align: middle;margin: -2px 4px 1px 0;"><label for="divLevel'+ls[i].welfareLevel+'">'+ls[i].welfareLevel+'级（'+ls[i].levelCount+'）</label></div>';
				}
				if(ls.length>4) {
					html += '<br />';
					html2 += '<br />';
				}
				html = '<div style="float:left;padding-left:14px;"><input type="radio" name="divLevel" id="divLevel-1" value="-1" data="'+al+'" checked style="vertical-align: middle;margin: -2px 4px 1px 0;" disabled="disabled"><label for="divLevel-1" >所有员工（'+al+'）</label></div>'
					+'<div class="bj_text" style="float: left; margin-left: 20px; display: inline">'
					+'<a class="add_new" href="'+jsBasePath+'/company/emp/page.html" target="_blank">查看</a>'
					+'</div><br />' + html;
				html += '<br /><p style="padding-left: 14px; line-height: 30px; color: #acadad;" id="calP">换领商品总数<em></em>个，平均每人获<em></em>个，合换领币<em></em>元</p>';
				

				html2 = '<div style="float:left;padding-left:14px;"><input type="radio" name="empLevel" id="empLevel-1" value="-1" data="'+al+'" checked style="vertical-align: middle;margin: -2px 4px 1px 0;" disabled="disabled"><label for="empLevel-1" >所有员工（'+al+'）</label></div>'
					+'<div class="bj_text" style="float: left; margin-left: 20px; display: inline">'
					+'<a class="add_new" href="'+jsBasePath+'/company/emp/page.html" target="_blank">查看</a>'
					+'</div><br />' + html2;
				html2 += '<br /><p style="padding-left: 14px; line-height: 30px; color: #acadad;"><input type="checkbox" name="empCash" id="empCash" value="1"" disabled="disabled"/> <label for="empCash">未购买此商品的员工可获得现金券（差价）补偿</label></p>';
				
				
				$("#divDivCnt").html(html);
				$("#empDivCnt").html(html2);
				
				if(divLevel != '') {
					setDivLevelCnt(divLevel);
				} else {
					setDivLevelCnt('-1');
				}

				if(empLevel != '') {
					setEmpLevelCnt(empLevel);
				} else {
					setEmpLevelCnt('-1');
				}
				

				if(empCash == '1' ) {
					$("#empCash").attr("checked","checked");
				}
			},
			error : function() {
				//alert("用户信息缺少，请重新登录");
			}
		});
	}
function setDivLevelCnt(v){
	$("#divLevel"+v).attr("checked","checked");
	$("#divLevel"+v).attr("disabled","disabled");
	calDiv();
}
function setLimitType(limitType){
	$("#limit"+limitType).attr("checked","checked");
	$("#limit"+limitType).attr("disabled","disabled");
}
function setEmpLevelCnt(v){
	$("#empLevel"+v).attr("checked","checked");
	$("#empLevel"+v).attr("disabled","disabled");
}

function calDiv(){
	var lsp = $("input[name='skuPrice']");
	var lsn = $("input[name='skuNum']");
	
	var price = 0;
	var cnt = 0;
	try {
		for(var i=0;i<lsp.length;i++) {
			if($(lsp[i]).val() == '' || $(lsn[i]).val() == '' ) {
				
			} else {
				var num = parseInt($(lsn[i]).val())
				cnt += num;
				price = parseFloat($(lsp[i]).val());
			}
		}
		var d=parseInt($("input[name='divLevel']:checked").attr("data"));
		var html = "";
		if(d!=0) {
			var c = 0;
			if(cnt > d) {
				c = parseInt(cnt/d);
			} else {
				c = parseFloat(cnt) /d;
			}
			html = "换领商品总数<em>"+cnt+"</em>个，平均每人获<em>"+parseFloat(c.toFixed(2))+"</em>个，合换领币<em>"+parseFloat(price*c).toFixed(2)+"</em>元";
			
		} else {
			html = "换领商品总数<em>"+cnt+"</em>个，平均每人获<em>，但因没有添加员工，暂不能参加换领。";
		}
		$("#calP").html(html);
	} catch(e) {}		
}


function getMinInternalPurchasePrice(){
	var lsp = $("input[name='internalPurchasePrice']");
	var min = 0.0;
		try {
			min = parseFloat($(lsp[0]).val());
			for(var i=1;i<lsp.length;i++) {
				if($(lsp[i]).val() == '') {
					
				} else {
					var p =  parseFloat($(lsp[i]).val());
					if(min>p) {
						min=p;
					}
				}
			}
		} catch(e) {alert(e);}	
	return min;
}
