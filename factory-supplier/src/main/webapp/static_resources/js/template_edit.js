	var rowCnt = 100;
	$(document).ready(function(){
		$(".city_box_con dl dd ul li img").click(function(){
			$(".city_ch").hide();
			$(".city_box_con dl dd ul li").removeClass("li_bg_change");
			$(this).parent("li").addClass("li_bg_change");
			$(this).siblings(".city_ch").show();
			
		});
		$(".cle_btn").click(function(){
			$(this).parent().hide();
			$(".city_box_con dl dd ul li").removeClass("li_bg_change");	
		});

		var listTr = $("#rulesTable>tr");
		listTr.each(function(i){$(this).attr("id","tr_"+(i+1));});
		rowCnt +=listTr.length;
		listTr = $(".table_box_fre table tbody tr");
		listTr.each(function(i){$(this).attr("id","tr_"+(++rowCnt));});

		rowCnt+=50;
		
		$(".postage-detail").find("[type='text']").attr("onkeyup","if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');");

		$(".table_box_fre").find("[type='text']").attr("onkeyup","if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');");
		
		//城市 checkbox
		var cityChks = $(".city_box").find("[type='checkbox']");
		cityChks.attr("onclick","selectCity(this);");
		cityChks.each(function(i,val){
			$(this).attr("id","chk_"+(i+1));
			$(this).next().attr("for","chk_"+(i+1));
		});
		//省 checkbox
		$("#city_box>div>dl>dd>ul>li>input").attr("onclick","selectPro(this);");
		//区域 checkbox
		$("#city_box>div>dl>dt>span>input").attr("onclick","selectArea(this);");
	});
	/**
	 * 快速跳转
	 */
	function addRow(){
		var html = '<tr id="tr_'+(++rowCnt)+'">';
		html += '<td class="con_td1">';
		html += '<span>未添加地区</span><div onclick="showArea(this,\'\');"><a href="javascript:void(0);">编辑</a></div>';
		html += '	<input type="hidden" name="areasName" />';
		html += '	<input type="hidden" name="areasCode" />';
		html += '</td>';
		html += '<td><input type="text" name="first_cnt" value="1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");"/></td>';
		html += '<td><input type="text" name="first_price" value="10" maxlength="6"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");"/></td>';
		html += '<td><input type="text" name="plus_cnt" value="1" maxlength="6"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");"/></td>';
		html += '<td><input type="text" name="plus_price" value="5" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" /></td>';
		html += '<td><div onclick="delRow(this);"><a href="javascript:void(0);">删除</a></div></td>';
		html += '</tr>';
		
		$("#rulesTable").append(html);
	}

	/**
	 * 快速跳转
	 */
	function delRow(obj){
		$(obj).parent().parent().remove();
	}
	
	var editTrId="";
	function showArea(obj,strPrefix) {
		var ftop =$(obj).offset().top+40;
		editTrId=$(obj).parent().parent().attr("id");
		
		var allSelectCode="";
		var pClass=".postage-detail";
		if(strPrefix == "free_") {
			pClass=".table_box_fre";
		}	
		
		var codes = $(pClass).find("[name='"+strPrefix+"areasCode']");
		codes.each(function(i,val){
			allSelectCode=allSelectCode+$(codes[i]).val();
		});
		
		var currtSelectCode=$("#"+editTrId).find("[name='"+strPrefix+"areasCode']").val();
		initCityCheckBok(allSelectCode,currtSelectCode);
		$(".city_box").attr("style","top:"+ftop+"px;");
		$(".city_box").show();
	}

	//改变checkbox可选状态及选中状态
	function initCityCheckBok(allSelectCode,currtSelectCode) {
		//回复最初状态
		var checkboxs=$(".city_box").find("[type='checkbox']");
		checkboxs.removeAttr("disabled");
		checkboxs.removeAttr("checked");

		//设置checkbox可选状态及选中状态
		var ary = allSelectCode.split(",");
		for(var i=0;i<ary.length;i++){
			var checkbox=$(".city_box").find("[value='"+ary[i]+"']");
			
			if(checkbox.length > 0) {
				//本行是否已选
				if(currtSelectCode.indexOf(ary[i])>-1) {
					//选中
					if(ary[i].substring(2) == '0000') {
						//省选中 
						checkbox.parent().find("[type='checkbox']").attr("checked","checked");
					} else {
						checkbox.attr("checked","checked");
					}
				} else {
					//选中
					if(ary[i].substring(2) == '0000') {
						//省选中 
						checkbox.parent().find("[type='checkbox']").attr("disabled","disabled");
					} else {
						checkbox.attr("disabled","disabled");
					}
				}
			}
		}
		
		//设置省checkbox可选状态及选中状态
		var pros= $("#city_box>div>dl>dd>ul>li>input");
		pros.each(function(i,val){
			refreshPro(this);
		});

		//设置区域checkbox可选状态及选中状态
		var areas= $("#city_box>div>dl>dt>span>input");
		areas.each(function(i,val){
			refreshArea(this);
		});
	}

	//计算省选中及数量
	function refreshPro(obj) {
		var citys = $(obj).next().next().next().next().find("[type='checkbox']");
		
		var selCnt=0;
		//城市不可选，则省不可选
		for(var j=0;j<citys.length;j++) {
			var city=$(citys[j]);
			if(city.attr("checked") || city.attr("checked")=="checked") {
				selCnt++;
			}
			if(city.attr("disabled") || city.attr("disabled")=="disabled") {
				$(obj).attr("disabled","disabled");
			}
		}
		
		//设置选择数量
		if(selCnt>0) {
			$(obj).next().next().html("("+selCnt+")");
			if(selCnt == citys.length) {
				$(obj).attr("checked","checked");
			} else {
				$(obj).removeAttr("checked");
			}
		} else {
			$(obj).next().next().html("");
			$(obj).removeAttr("checked");
		}
	}

	//计算省选中及数量
	function refreshArea(obj) {
		var prosp = $($(obj).parent().parent().next().children()[0]).children();
		
		var selCnt=0;
		//城市不可选，则省不可选
		for(var j=0;j<prosp.length;j++) {
			var pro=$($(prosp[j]).children()[0]);
			if(pro.attr("checked") || pro.attr("checked")=="checked") {
				selCnt++;
			}
			if(pro.attr("disabled") || pro.attr("disabled")=="disabled") {
				$(obj).attr("disabled","disabled");
			}
		}
		
		//设置选择数量
		if(selCnt == prosp.length) {
			$(obj).attr("checked","checked");
		} else {
			$(obj).removeAttr("checked");
		}
	}
	
	//城市选择
	function selectCity(obj) {

		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
			$(obj).attr("checked","checked")
		} else {
			$(obj).removeAttr("checked");
		}
		refreshPro($(obj).parent().parent().parent().prev().prev().prev().prev());
		refreshArea($($(obj).parent().parent().parent().parent().parent().parent().prev().children()[0]).children()[0]);
	}
	
	function selectPro(obj) {
		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
			$(obj).next().next().next().next().find("[type='checkbox']").attr("checked","checked");
		} else {
			$(obj).next().next().next().next().find("[type='checkbox']").removeAttr("checked");
		}
		
		refreshPro(obj);
		refreshArea($($(obj).parent().parent().parent().prev().children()[0]).children()[0]);
	}

	function selectArea(obj) {
		if($(obj).attr("checked") || $(obj).attr("checked")=="checked") {
			$(obj).parent().parent().next().find("[type='checkbox']").attr("checked","checked");
		} else {
			$(obj).parent().parent().next().find("[type='checkbox']").removeAttr("checked");
		}
		
		var prosp = $($(obj).parent().parent().next().children()[0]).children();
		for(var j=0;j<prosp.length;j++) {
			refreshPro($(prosp[j]).children()[0]);
		}
		
		refreshArea(obj);
	}
	
	function saveArea() {
		var currtSelectCode="";
		var currtSelectName="";
		var checkboxs=$(".city_box").find("[checked='checked']");
		checkboxs.each(function(i,val){
			var val = $(this).val();
			if(val.length==6 && currtSelectCode.indexOf(val) == -1  && currtSelectCode.indexOf(val.substring(0,2)+'0000') == -1) {
				currtSelectCode += val + ",";
				currtSelectName += $(this).next().html() + " ";
			}
		});
		
		var td = $("#"+editTrId).children()[0];
		$($(td).children()[0]).html(currtSelectName);
		$($(td).children()[2]).val(currtSelectName);
		$($(td).children()[3]).val(currtSelectCode);
	}
	
	//非空验证
	function validatorForm(){
		var inputs = $("#sub_form .form-elem").find("[type='text']");
		inputs.attr("ismust","1");
		inputs.attr("onfocus","clearErr(this);");
		
		inputs = $(".postage-detail").find("[type='text']");
		inputs.attr("ismust","1");
		inputs.attr("onfocus","clearErr(this);");
		
		inputs = $(".table_box_fre").find("[type='text']");
		inputs.attr("ismust","1");
		inputs.attr("onfocus","clearErr(this);");
		
		var $list = $("[ismust=1]");
				
		var ret = 0;
		
		$list.each(function(i,val){
			if($.trim($($list[i]).val())==''){
				$($list[i]).addClass("bctxt");
				ret++;
			}
		});
		
						
		if(ret>0){
			return false;
		}else{
			return true;
		}						
		
	}

	function clearErr(obj) {
		$(obj).removeClass("bctxt");
	}
	
	/**
	*submit
	*/
	
	function formSubmit(){
		if(validatorForm()){
			
			var must=0;
			var hiddens = $(".postage-detail").find("[name='areasCode']");
			hiddens.each(function(i,val){
				if($.trim($(this).val())==''){
					must++;
					return;
				}
			}); 
			hiddens = $(".table_box_fre").find("[name='free_areasCode']");
			hiddens.each(function(i,val){
				if($.trim($(this).val())==''){
					must++;
					return;
				}
			});
			if(must>0) {
			 	showInfoBox("运送到地区必须指定，请编辑后，再次提交！");
				return;
			}
			var msg = 0;
			var inputsII = $(".postage-detail").find("[name='first_cnt']");
			inputsII.attr("ismustII","1");
			
			inputsII = $(".postage-detail").find("[name='plus_cnt']");
			inputsII.attr("ismustII","1");
			
			inputsII = $(".table_box_fre").find("[name='first_cnt']");
			inputsII.attr("ismustII","1");
			
			inputsII = $(".table_box_fre").find("[name='plus_cnt']");
			inputsII.attr("ismustII","1");
			
			
			var $listII = $("[ismustII=1]");

			$listII.each(function(i,val){
				if($($listII[i]).attr("type") == "text") {
					if(parseFloat($($listII[i]).val())<=0){
						$($listII[i]).addClass("bctxt");
						msg++;	
						return;
					}
				}
			});
			if(msg>0){
				showInfoBox("只能输入大于0的数字");
				return;
			}
			
			//这里判断必须大于等于0的数
			var daYuDengLing = $(".postage-detail").find("[name='first_price']");
			daYuDengLing.attr("ismustII","2")
			
			daYuDengLing = $(".postage-detail").find("[name='plus_price']");
			daYuDengLing.attr("ismustII","2");
			
			
			daYuDengLing = $(".table_box_fre").find("[name='free_param1']");
			daYuDengLing.attr("ismustII","2");
			
			daYuDengLing = $(".table_box_fre").find("[name='free_param2']");
			daYuDengLing.attr("ismustII","2");
			
			var $daYuDengLing = $("[ismustII=2]");
			var flagLing = 0;
			$daYuDengLing.each(function(i,val){
				if($($daYuDengLing[i]).attr("type") == "text") {
					if(parseFloat($($daYuDengLing[i]).val())<0){
						$($daYuDengLing[i]).addClass("bctxt");
						flagLing++;	
						return;
					}
				}
			});
			if(flagLing>0){
				showInfoBox("只能输入大于等于0的数字");
				return;
			}
			
			
			
			$.ajax({
				url : jsBasePath+'/shippingAddress/ajaxSaveTemplate.json',
				type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:$("#sub_form").serialize(),
				success : function(data) {
					if(data.success){
						if(data.data == '0'){
							window.location=jsBasePath+'/shippingAddress/freight_templates.html?ty=1&isAudit='+ $("#isAudit").val();
						}else if(data.data == '2'){
                            window.location=jsBasePath+'/shippingAddress/freight_templates.html?ty=1&isAudit=0';
						}else{
							window.location=jsBasePath+'/shippingAddress/freight_templates.html?ty=1&isAudit=1';
						}
	                } else {
	                	if(data.msg == 'no login') {
	                		window.location=jsBasePath+'/user/login.html';
	                	} else {
	                		showInfoBox(data.msg);
	                	}
	                }				
				}, error : function() {
			    }  
			});
		}else{
			
				showInfoBox("请补充完整红色区域后，再次提交！");
			
		}
		
		
	}
	/**
	*全场包邮策略form submit
	*
	*/
	function formFreeSubmit(){
		if(validatorForm()){
			var must=0;
			var hiddens = $(".table_box_fre").find("[name='free_areasCode']");
			hiddens.each(function(i,val){
				if($.trim($(this).val())==''){
					must++;
					return;
				}
			});
			if(must>0) {
			 	showInfoBox("运送到地区必须指定，请编辑后，再次提交！");
				return;
			}
			
			$.ajax({
				url : jsBasePath+'/shippingAddress/ajaxSaveFreeTemplate.json',
				type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:$("#sub_free_form").serialize(),
				success : function(data) {
					if(data.success){
	            		window.location=jsBasePath+'/shippingAddress/freight_templates.html';
	                } else {
	                	if(data.msg == 'no login') {
	                		window.location=jsBasePath+'/user/login.html';
	                	} else {
	                		showInfoBox(data.msg);
	                	}
	                }				
				}, error : function() {
			    }  
			});
		}else{
			showInfoBox("请补充完整红色区域后，再次提交！");
		}
	}
	
	//切换计价方式
    function changeType(obj){
		var kbn=obj.value;
		showConfirm(null,"切换计价方式后，所设置当前模板的运输信息将被清空，您确定要这要样做吗？","clearRules("+kbn+")");
		return false;
    }

    function clearRules(kbn){
    	var tani,first,plus;
    	switch(kbn){
    	case 2:
    		tani="kg";
    		first="首重(kg)";
    		plus="续重(kg)";
    		$("#J_CalcRuleWeight").attr("checked","checked");
    		break;
    	case 3:
    		tani="m³";
    		first="首体积(m³)";
    		plus="续体积(m³)";
    		$("#J_CalcRuleVolume").attr("checked","checked");
    		break;
    	default:
    		tani="件";
			first="首件(件)";
			plus="续件(件)";
			$("#J_CalcRuleNumber").attr("checked","checked");
    	}
		
    	//默认运费：
		$(".detail-con-top").html('默认运费：<input type="hidden" name="areasName" value="全国 "/>'
				+'<input type="hidden" name="areasCode" value="0,"/>'
				+'<input type="text" name="first_cnt" value="1" maxlength="6"  />'+tani+'内，'
				+'<input type="text" name="first_price" value="10" maxlength="6"  />元，每增加'
				+'<input type="text" name="plus_cnt" value="1" maxlength="6"  />'+tani+'，增加运费'
				+'<input type="text" name="plus_price" value="5" maxlength=""  />元'
				);
    	
    	//更换表头
    	$(".detail_tab_box table thead tr").html('<th class="con_th1">运送到</th><th>'+first+'</th><th>运费（元）</th><th>'+plus+'</th><th class="con_th2">续费（元）</th><th class="con_th3">操作</th>');
    	//清空内容
    	$("#rulesTable").html('');
    	
    	//清空包邮信息
    	$("#freeRule").removeAttr('checked');
    	$(".table_box_fre").html('');
    }
    
    function changeFree(obj){
    	if($(obj).attr('checked') || $(obj).attr('checked')=='checked') {
    		var kbn=1;
    		if($("#J_CalcRuleWeight").attr('checked') || $("#J_CalcRuleWeight").attr('checked')=='checked') {
    			kbn=2;
    		} else if($("#J_CalcRuleVolume").attr('checked') || $("#J_CalcRuleVolume").attr('checked')=='checked') {
    			kbn=3;
    		} 
    		
    		var html='<table  border="0px" cellpadding="0" cellspacing="0"><thead><tr><th>选择地区</th><th>设置包邮条件</th><th>操作</th></tr></thead>';
    		html+='<tbody>';
    		html+=makeFreeRowHtml(kbn);
    		html+='</tbody></table>';
    		html+='<div class="add_adr_btn" style="margin-left:80px;"><a href="javascript:addFreeRow();">为指定地区城市设置包邮条件</a></div>';

        	$(".table_box_fre").html(html); 
    	} else {
        	//清空包邮信息
        	$(".table_box_fre").html('');    		
    	}
    }
    
    function makeFreeRowHtml(kbn) {

		var html='<tr id="tr_'+(++rowCnt)+'">';
		html+='<td class="con_td1">';
		html+='<span>未添加地区 </span><div onclick="showArea(this,\'free_\');"><a href="javascript:void(0);">编辑</a></div>';
		html+='<input type="hidden" name="free_areasName" />';
		html+='<input type="hidden" name="free_areasCode" />';
		html+='</td>';
		html+='<td class="con_td1 con_td2"><div class="slt_wh">';
		html+='<select name="free_countTypeDes" onchange="typeDesChange(this);">';
		if(kbn==2) {
			html+='<option value="1">重量</option><option value="2">金额</option><option value="3">重量＋金额</option>';
		} else if(kbn==3) {
			html+='<option value="1">体积</option><option value="2">金额</option><option value="3">体积＋金额</option>';
		} else {
			html+='<option value="1">件数</option><option value="2">金额</option><option value="3">件数＋金额</option>';
		}
		html+='</select>';
		html+='<div class="inp_text_wh">';
		if(kbn==2) {
			html+='在&nbsp;<input type="text" name="free_param1" maxlength="6" />&nbsp;kg内包邮 <input type="hidden" name="free_param2" value="0.00"/>';
		} else if(kbn==3) {
			html+='在&nbsp;<input type="text" name="free_param1" maxlength="6" />&nbsp;m³内包邮<input type="hidden" name="free_param2" value="0.00"/>';
		} else {
			html+='满&nbsp;<input type="text" name="free_param1" maxlength="6" />&nbsp;件包邮 <input type="hidden" name="free_param2" value="0.00"/>';
		}
		html+='</div>';
		html+='</div></td>';
		html+='<td><div onclick="delRow(this);"><a href="javascript:void(0);">删除</a></div></td>';
		html+='</tr>';
		
		return html;
    }

	/**
	 * 快速跳转
	 */
	function addFreeRow(){
		var kbn=1;
		if($("#J_CalcRuleWeight").attr('checked') || $("#J_CalcRuleWeight").attr('checked')=='checked') {
			kbn=2;
		} else if($("#J_CalcRuleVolume").attr('checked') || $("#J_CalcRuleVolume").attr('checked')=='checked') {
			kbn=3;
		} 
		
		$(".table_box_fre table tbody").append(makeFreeRowHtml(kbn));
	}
	
	function typeDesChange(obj) {
		var val= obj.value;
		var kbn=1;
		if($("#J_CalcRuleWeight").attr('checked') || $("#J_CalcRuleWeight").attr('checked')=='checked') {
			kbn=2;
		} else if($("#J_CalcRuleVolume").attr('checked') || $("#J_CalcRuleVolume").attr('checked')=='checked') {
			kbn=3;
		}
		var html ="";
		if(val=="2") {
			html+='<input type="hidden" name="free_param1" value="0.00"/>满&nbsp;<input type="text" name="free_param2" maxlength="6"  onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");"/>&nbsp;元包邮'; 
		} else if(val=="3") {
			if(kbn==2) {
				html+='在&nbsp;<input type="text" name="free_param1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;kg内,且&nbsp;<input type="text" name="free_param2" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;元以上&nbsp;包邮';
			} else if(kbn==3) {
				html+='在&nbsp;<input type="text" name="free_param1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;m³内,且&nbsp;<input type="text" name="free_param2" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;元以上&nbsp;包邮';
			} else {
				html+='满&nbsp;<input type="text" name="free_param1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;件,且&nbsp;<input type="text" name="free_param2" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;元以上&nbsp;包邮';
			}
		} else {
			if(kbn==2) {
				html+='在&nbsp;<input type="text" name="free_param1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;kg内包邮 <input type="hidden" name="free_param2" value="0.00"/>';
			} else if(kbn==3) {
				html+='在&nbsp;<input type="text" name="free_param1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;m³内包邮<input type="hidden" name="free_param2" value="0.00"/>';
			} else {
				html+='满&nbsp;<input type="text" name="free_param1" maxlength="6" onkeyup="if(isNaN(value))execCommand(\'undo\');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,\'$1$2.$3\');");" />&nbsp;件包邮 <input type="hidden" name="free_param2" value="0.00"/>';
			}
		}
		$(obj).next().html(html);
	}
	
	function deleteTemplate(templateId){
		$.ajax({
			url : jsBasePath+'/shippingAddress/ajaxTemplateOprate.json?oprate=delete&templateId=' + templateId,
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				if(data.success){
            		window.location=jsBasePath+'/shippingAddress/freight_templates.html';
                } else {
                	if(data.msg == 'no login') {
                		window.location=jsBasePath+'/user/login.html';
                	} else {
                		showInfoBox(data.msg);
                	}
                }				
			}, error : function() {
		    }  
		});
	}

	function oldShow(){
		$("#old").show();
	}