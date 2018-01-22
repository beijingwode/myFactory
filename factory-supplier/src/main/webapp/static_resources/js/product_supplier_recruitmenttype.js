    	$(document).ready(function(){
    		
//     		var $listli = $("#ul1").find("li");
//     		var ppid = "${ppid}".split(",");
//     		var pid = "${pid}".split(",");
    		
    		
//     		if(ppid.length>0&&ppid[0]!=""){
//     			ajaxLoadNext("${ppid}",2,0);
//     		}
//     		if(pid.length>0&&pid[0]!=""){
//     			ajaxLoadNext("${pid}",3,0);
//     		}
    		
//     		setTimeout("setProductAddress()",500);
    		
//     		$listli.each(function(i,val){
//     			for(var j=0;j<ppid.length;j++){
//     				if($($listli[i]).attr("id")==ppid[j]){
//         				$($listli[i]).addClass("current");
//         			};
//         		}
//     		});
    		if(jsApprType=='1') {
        		$(".recuitment_btn01 a").html("提交");
        		$(".recuitment_btn02").hide();
        		$(".recuitment_btn03 a").html("取消");
    		}
		});
    	
    	//赋值
    	function setProductAddress(){
    		var pid = jsPsid.split(",");
    		var cid = jsCid.split(",");
    		
    		var $listli2 = $("#ul2").find("li");
    		$listli2.each(function(i,val){
    			for(var j=0;j<pid.length;j++){
    				if($($listli2[i]).attr("id")==pid[j]){
        				$($listli2[i]).addClass("current");
        			};
        		}
    		});
    		
    		var $listli3 = $("#ul3").find("li");
    		$listli3.each(function(i,val){
    			for(var j=0;j<cid.length;j++){
    				if($($listli3[i]).attr("id")==cid[j]){
        				$($listli3[i]).addClass("current");
        			};
        		}
    		});
    		
    	}
    	
    	function selectall2(obj){
    		var $listli2 = $("#ul2").find("li");
    		$listli2.each(function(i,val){
    			if($(obj).find("input[type=checkbox]").attr("checked")!="checked"){
    				$($listli2[i]).removeClass("current");
    				$($listli2[i]).find("input[type=checkbox]").attr("checked",false);
    			}else{
    				$($listli2[i]).addClass("current");
       				$($listli2[i]).find("input[type=checkbox]").attr("checked",true);
    			}
    		});
    		
    		var $list = $("#ul2").find("li[class='current']");
    		var ids="";
    		$list.each(function(i,val){
    			ids=ids+$($list[i]).attr("id")+",";
    		});
    		
    		if(ids!=""){
    			ids = ids.substring(0,ids.length-1);
    			ajaxLoadNext(ids,3,$(obj).attr("id"));
    		}else{
    			$("#ul3").html("");
    		}
    		ajaxhtml(false);
    	}
    	
    	function selectall3(obj){
    		var $listli2 = $("#ul3").find("li");
    		$listli2.each(function(i,val){
    			if($(obj).find("input[type=checkbox]").attr("checked")!="checked"){
    				$($listli2[i]).removeClass("current");
    				$($listli2[i]).find("input[type=checkbox]").attr("checked",false);
    				var id = $($listli2[i]).attr("id");
        			if($("#re"+id).siblings("p").length==0){
    					$("#re"+id).parents("tr").remove();
    				}else{
    					if($("[idp="+$("#re"+id).attr("idp")+"]").length>1){
    						$("#re"+id).remove();
    					}else{
    						$("#"+$("#re"+id).attr("idp")).remove();
    						$("#re"+id).remove();
    					}
    				}
    			}else{
    				$($listli2[i]).addClass("current");
       				$($listli2[i]).find("input[type=checkbox]").attr("checked",true);
    			}
    		});
    		
    		ajaxhtml(false);
    	}
    	
    	/**
    	*第一级的单击事件
    	*/
    	function checkli1(obj){
    		//添加选中状态
    		$(obj).siblings("li").removeClass("current").end().addClass("current");
   			ajaxLoadNext($(obj).attr("id"),2,$(obj).attr("id"));
			$("#ul3").html("");
			ajaxhtml(false);
			
			
			setTimeout("checkli1_1()",600);
			
    	}
    	
    	function checkli1_1(){
    		var $list = $("#ul2").find("li[class='current']");
    		var ids="";
    		$list.each(function(i,val){
    			ids=ids+$($list[i]).attr("id")+",";
    		});
    		
    		if(ids!=""){
    			ids = ids.substring(0,ids.length-1);
    			ajaxLoadNext(ids,3,0);
    		}else{
    			$("#ul3").html("");
    		}
    		
    	}
    	
    	
    	/**
    	*第二级的单击事件
    	*/
    	function checkli2(obj){
    		//添加选中状态
    		if($(obj).hasClass("current")){
    			$(obj).removeClass("current");
    			$(obj).find("input[type=checkbox]").attr("checked",false);
    			var id = $(obj).attr("id");
    			if($("#re"+id).siblings("p").length==0){
					$("[idp=re"+id+"]").parents("tr").remove();
				}else{
					$("#re"+id).remove();
					$("[idp=re"+id+"]").remove();
				}
    		}else{
    			$(obj).addClass("current");
    			$(obj).find("input[type=checkbox]").attr("checked",true);
    		}
    		
    		var $list = $(obj).parent().find("li[class='current']");
    		var ids="";
    		$list.each(function(i,val){
    			ids=ids+$($list[i]).attr("id")+",";
    		});
    		
    		if(ids!=""){
    			ids = ids.substring(0,ids.length-1);
    			ajaxLoadNext(ids,3,$(obj).attr("id"));
    		}else{
    			$("#ul3").html("");
    		}
    		
    		ajaxhtml(false);
			
    	}
    	
    	/**
    	*第二级的单击事件
    	*/
    	function checkli2id(id){
    		//添加选中状态
    		if($("#"+id).hasClass("current")){
    			$("#"+id).removeClass("current");
    			$("#"+id).find("input[type=checkbox]").attr("checked",false);
    		}
    		
    		var $list = $("#"+id).parent().find("li[class='current']");
    		var ids="";
    		$list.each(function(i,val){
    			ids=ids+$($list[i]).attr("id")+",";
    		});
    		
    		if(ids!=""){
    			ids = ids.substring(0,ids.length-1);
    			ajaxLoadNext(ids,3,id);
    		}else{
    			$("#ul3").html("");
    		}
    		
    		ajaxhtml(false);
			
    	}
    	
    	/**
    	*第二级的单击事件
    	*/
    	function checkli3(obj){
    		//添加选中状态
    		//添加选中状态
    		if($(obj).hasClass("current")){
    			$(obj).removeClass("current");
    			$(obj).find("input[type=checkbox]").attr("checked",false);
    			var id = $(obj).attr("id");
    			if($("#re"+id).siblings("p").length==0){
					$("#re"+id).parents("tr").remove();
				}else{
					if($("[idp="+$("#re"+id).attr("idp")+"]").length>1){
						$("#re"+id).remove();
					}else{
						$("#"+$("#re"+id).attr("idp")).remove();
						$("#re"+id).remove();
					}
				}
    		}else{
    			$(obj).addClass("current");
    			$(obj).find("input[type=checkbox]").attr("checked",true);
    		}
    		ajaxhtml(false);
    	}
    	
    	function ajaxhtml(tp){
    		
    		var nowids = '';
    		var regS = new RegExp("re","gi");
    		var $list = $("#table").find("[name=childrensid]");
    		$list.each(function(i,val){
    			nowids = nowids+$($list[i]).attr("id")+",";
    		});
    		
    		var ids="";
    		var $list = $("#ul3").find("li[class='current']");
    		$list.each(function(i,val){
    			ids=ids+$($list[i]).attr("id")+",";
    		});
    		
    		if(ids!=""){
    			ids = ids.substring(0,ids.length-1);
    		}else{
    			//alert("请选择类目");
    			if(tp){
    				return;
    			}
    		}
    		
    		ids = ids.replace(regS,"");
    		nowids = nowids.replace(regS,"");
    		
    		var basePath = jsBasePath;
    		$.ajax({
                cache: true,
                type: "POST",
                dataType: "json",
                url:jsBasePath+"/shopSetting/findbyajax.json",
                data:"categoryids="+ids+"&nowids="+nowids,// 你的formid
                async: true,
                error: function(request) {
                    //alert("Connection error");
                    return false;
                },
                success: function(data) {
                	if(data.result.errorCode==0){
                		var html = '';
                		var supplierCategoryLists = data.supplierCategoryLists;
                		html += '<tr>';
                		html += '<th>一级类目</th>';
                		html += '<th>二级类目</th>';
                		html += '<th>三级类目(佣金比例%)</th>';
                		html += '</tr>';
                		for(var i=0;i<supplierCategoryLists.length;i++){
                			html += '<tr id="re'+supplierCategoryLists[i].id+'">';
                			html += '<td><p onclick="delcategory('+supplierCategoryLists[i].id+',1)">'+supplierCategoryLists[i].name+'</p></td>';
                			html += '<td>';
                			var childrens = supplierCategoryLists[i].childrens;
                			var html2 = '';
                			for(var b=0;b<childrens.length;b++){
                				$("#"+childrens[b].id).addClass("current");
            	    			$("#"+childrens[b].id).find("input[type=checkbox]").attr("checked",true);
                				html += '<p id="re'+childrens[b].id+'" onclick="delcategory('+childrens[b].id+',2)">'+childrens[b].name+'</p>';
                				var childrens2 = childrens[b].childrens; 
                				for(var c=0;c<childrens2.length;c++){
                					$("#"+childrens2[c].id).addClass("current");
                	    			$("#"+childrens2[c].id).find("input[type=checkbox]").attr("checked",true);

									var name = childrens2[c].name;
										name +='          ('+childrens2[c].commissionScale+'%)';
									
                					html2 += '<p onclick="delcategory('+childrens2[c].id+',3)" id="re'+childrens2[c].id+'" idp="re'+childrens[b].id+'" name="childrensid">'+name+'</p>';
                				};
                			}
                			html += '</td>';
                			html += '<td>';
                			html += html2;
                			html += '</td>';
                			html += '</tr>';
                		}
                		$("#table").html(html);
                	}else if(data.result.errorCode==1000){
                		var html = '';
                		html += '<tr>';
                		html += '<th>一级类目</th>';
                		html += '<th>二级类目</th>';
                		html += '<th>三级类目(佣金比例%)</th>';
                		html += '</tr>';
                		$("#table").html(html);
                	}
                	else{
                		$("#sb").fadeIn();
                		setTimeout("display()",6000);
                		//alert("错误："+data.result.errorCode+"  原因："+data.result.message);
                	};
                }
            });
    	}
    	
    	
    	/**
    	*动态格式化下一级的类目
    	*/
    	function ajaxLoadNext(parentid,order,objid){
    		var basePath = jsBasePath;
			$.ajax({
				url : basePath +'/productCategory/getProductCategoryList.json?pids='+parentid+"&shopId="+jsShopId,
				type : "GET",
				dataType: "json",  //返回json格式的数据  
			    async: false,
				success : function(data) {
					var html = "<li class=\"allselect\" onclick=\"selectall"+order+"(this);\"><a href=\"javascript:void(0);\"><input class=\"r_radio\" type=\"checkbox\" name=\"\" value=\"\">全选</a></li>";
					if(data.result.errorCode==0){
						if(data.result.msgBody!=undefined){
							if(data.result.msgBody.length>0){
								for(var i=0;i<data.result.msgBody.length;i++){
									var name = data.result.msgBody[i].name;
									if(order==3) {
										name +='          ('+data.result.msgBody[i].commissionScale+'%)';
									}
									html+='<li id="'+data.result.msgBody[i].id+'" onclick="checkli'+order+'(this);"><a href=\"javascript:void(0);\"><input class=\"r_radio\" type=\"checkbox\" name=\"\" value=\"\">'+name +'</a></li>';
								}
							}else{
								
							}
						}
					}
					$("#ul"+order).html(html);
					var nowids = '';
		    		var $list2 = $("#table").find("[name=childrensid]");
		    		$list2.each(function(i,val){
		    			nowids = nowids+$($list2[i]).attr("id")+",";
		    		});
		    		if(nowids!=""){
		    			nowids = nowids.substring(0,nowids.length-1).split(",");
		    			for(var i=0;i<nowids.length;i++){
		    				$("#"+nowids[i].replace('re', '')).addClass("current");
			    			$("#"+nowids[i].replace('re', '')).find("input[type=checkbox]").attr("checked",true);
		    			}
		    		}
				}, error : function() {    
			     }  
			});
    	}
    	
    	function deltan(){
    		$(".popup_bg").show();
    		$("#shop_popup_delete").fadeIn();
    	}
    	
    	function delcategory(id,type){
    		$("#shop_popup_delete").fadeOut();
    		$("#deleteid").val(id);
    		$("#typeid").val(type);
    		$(".popup_bg").show();
    		$("#shop_popup_delete").fadeIn();
    	}
    	
    	function delcategory2(){
    		$(".popup_bg").hide();
    		$("#shop_popup_delete").fadeOut();
    	}
    	
    	function delcategory1(){
    		
    		var id = $("#deleteid").val();
    		var type = $("#typeid").val();
    			if(type == '1'){
    				$("#re"+id).remove();
    			}else if(type == '2'){
    				if($("#re"+id).siblings("p").length==0){
    					$("[idp=re"+id+"]").parents("tr").remove();
    				}else{
    					$("#re"+id).remove();
    					$("[idp=re"+id+"]").remove();
    				}
    				$("#"+id).find("input[type=checkbox]").attr("checked",false);
    				$("#"+id).removeClass("current"); 
    				checkli2id(id);
    			}else if(type == '3'){
    				if($("#re"+id).siblings("p").length==0){
    					$("#re"+id).parents("tr").remove();
    				}else{
    					if($("[idp="+$("#re"+id).attr("idp")+"]").length>1){
    						$("#re"+id).remove();
    					}else{
    						$("#"+$("#re"+id).attr("idp")).remove();
    						$("#re"+id).remove();
    					}
    				}
    				$("#"+id).find("input[type=checkbox]").attr("checked",false);
    				$("#"+id).removeClass("current"); 
    			}
    			$(".popup_bg").hide();
    			$("#shop_popup_delete").fadeOut();
    			
    			//window.location.href = "${basePath}/supplierCategory/deletebymap.html?id="+id+"&type="+type+"";
    	}
    	
    	var flag = false;
    	var flag2 = false;
    	/**
    	*跳转下一步
    	*/
    	function papa(type){
    		$(".recuitment_btn01").addClass("btngray").removeAttr("onclick");
    		$(".recuitment_btn02").addClass("btngray").removeAttr("onclick");
    		$(".recuitment_btn03").addClass("btngray").removeAttr("onclick");
    		setTimeout("submit("+type+")",600);
    	}
    	
    	function submit(type){
    		//papa();
    		//Pause(this,3000);
    		//alert($('input[name="type"]:checked').val());
    		if(type==3){
        		if(jsApprType=='1') {
        			if(jsApprId == '' || jsApprId=='null') {
        				window.location.href = jsBasePath+"/shopSetting/categoryBrand.html?id="+jsOid;
        			} else {
        				window.location.href = jsBasePath+"/supplier/cancelEdit.html";
        			}
        		} else {
        			window.location.href = jsBasePath+"/supplier/torecruitmentstore.html";        			
        		}
    		}else if(type==2){
    			var ids="";
        		var $list = $("#table").find("[name=childrensid]");
        		$list.each(function(i,val){
        			ids = ids+$($list[i]).attr("id")+",";
        		});
        		if(ids==""){
        			$("#ts").fadeIn();
    				$(".recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
    	    		$(".recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    	    		$(".recuitment_btn03").removeClass("btngray").attr("onclick","papa('3')");
    				setTimeout("display()",6000);
    				return false;
        		}
    			var size = jsScsize;
   				flag2 = false;
       			z_sub();
       			if(flag2){
       				window.location.href = jsBasePath+"/supplier/torecruitmentnewbrand.html";
       			}else{
       				$("#sb").fadeIn();
       			}
       			setTimeout("display()",6000);
    		}else{
    			
    			var ids="";
        		var $list = $("#table").find("[name=childrensid]");
        		$list.each(function(i,val){
        			ids = ids+$($list[i]).attr("id")+",";
        		});
        		if(ids==""){
        			$("#ts").fadeIn();
    				setTimeout("display()",6000);
    				$(".recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
    	    		$(".recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    	    		$(".recuitment_btn03").removeClass("btngray").attr("onclick","papa('3')");
    				return false;
        		}
    			flag2 = false;
    			z_sub();
    			if(flag2){
            		if(jsApprType=='1') {
            			window.location.href = jsBasePath+"/shopSetting/categoryBrand.html?id="+jsShopId;
            		}
    				$("#cg").fadeIn();
    			}else{
    				$("#sb").fadeIn();
    			}
    			setTimeout("display()",6000);
    		}	
    		$(".recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
    		$(".recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    		$(".recuitment_btn03").removeClass("btngray").attr("onclick","papa('3')");
    		
    	}
    	
    	function display(){
    		$("#cg").fadeOut();
    		$("#sb").fadeOut();
    		$("#ts").fadeOut();
    	}
    	
    	function z_sub(){
    		var ids="";
    		var $list = $("#table").find("[name=childrensid]");
    		var regS = new RegExp("re","gi");
    		$list.each(function(i,val){
    			ids = ids+$($list[i]).attr("id")+",";
    		});
    		if(ids!=""){
    			ids = ids.substring(0,ids.length-1);
    		}
    		ids = ids.replace(regS,""); //全部替换
    		$.ajax({
                type: "POST",
                dataType: "json",
                url:jsBasePath+"/shopSetting/save.json?oid="+jsOid,
                data:"categoryids="+ids,// 你的formid
                async: false,
                error: function(request) {
                    //alert("Connection error");
                    return false;
                },
                success: function(data) {
                	if(data.result.errorCode==0){
                		flag = true;
                		flag2 = true;
                		return true;
               			//window.location.href = "${basePath}/supplier/torecruitmenttype.html";
                	}else{
                		
                		return false;
                		//$("#sb").fadeIn();
                		//setTimeout("display()",6000);
                		//alert("错误："+data.result.errorCode+"  原因："+data.result.message);
                	}
                	
                }
            });
    	}