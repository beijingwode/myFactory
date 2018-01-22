  
  
        
    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i];
            }
        }
        this.length -= 1;
    }

    //init seleCategory
    $(document).ready(function(){
    	//控制菜单背景
    	selectedHeaderLi("permissions_header");
    	
    	
		var $list = $("ul li a input");
    	for(var i=1;i<olds.length;i++) {
   			for(var j=0;j<$list.length;j++){
   				if($list[j].value== olds[i]) {
   					//空二级菜单处理
   					if(4==olds[i] || 5==olds[i] || 6==olds[i]) {
   	   					pushSeleCategory($("#10400"));
   					} else if(19==olds[i] || 20==olds[i]) {
   	   					pushSeleCategory($("#70100"));
   					} else if(21==olds[i]) {
   	   					pushSeleCategory($("#70200"));
   					} else if(22==olds[i] || 23==olds[i] || 24==olds[i] || 25==olds[i] || 26==olds[i]) {
   	   					pushSeleCategory($("#70300"));
   					} else if(27==olds[i]) {
   	   					pushSeleCategory($("#70400"));
   					} else if(28==olds[i] || 29==olds[i]) {
   	   					pushSeleCategory($("#70500"));
   					} 
   					setModel($($list[j]).parent().parent());
   					break;
   				}
   			}
    	}
    });
    
    function pushSeleCategory(obj) {
    	var id = obj.attr("id");
    	if(!isSontains(id)){
	        var o = {};
	        o.id = id;
	        o.idp = obj.attr("idp");
	        o.name = obj.attr("name");
	        o.level = obj.attr("level");
	        seleCategory.push(obj);
    	}
    }
    
    function isSontains(id) {
    	for(var i=0;i<seleCategory.length;i++) {
    		if(id==seleCategory[i].id){
    			return true;
    		}
    	}
    	return false;
    }
    
    function setModel(obj) {
    	pushSeleCategory(obj);
        if (!obj.attr("isnextlevel") || obj.attr("isnextlevel") == "false") {
        	var id = obj.find("input").val();
            if (id) {
            	var trHtml="";
            	var rTdId = "r_" + obj.attr("id").substring(0,1) + "0000";
            	var pTdId = "p_" + obj.attr("id").substring(0,3) + "00";
            	var dess= obj.attr("des").split("> ");
        		
				var rTd = $("#" + rTdId);
				var pTd = $("#" + pTdId);
				
				//二级菜单已存在
				if(pTd.length > 0) {
					rTd.attr("rowspan",parseInt(rTd.attr("rowspan")) + 1);
					var pCon=parseInt(pTd.attr("rowspan"));
					var lastBr = pTd.parent();
					for(var i=1;i<pCon;i++) {
						lastBr= lastBr.next();
					}
					pTd.attr("rowspan",pCon + 1);
					
					trHtml = "<tr id=\"selected_"+id + "\"><td>" +dess[2] + "</tr>";
					$(lastBr).after(trHtml);
				} else {
					//一级菜单已存在
					if(rTd.length > 0) {
						var pCon=parseInt(rTd.attr("rowspan"));
						rTd.attr("rowspan",pCon + 1);
						var lastBr = rTd.parent();
						for(var i=1;i<pCon;i++) {
							lastBr= lastBr.next();
						}
						
						if(dess.length == 2) {
							trHtml = "<tr id=\"selected_"+id + "\"><td id=\""+pTdId + "\" rowspan=1>" +dess[1] + "</td><td>&nbsp;</td></tr>";
						} else {
							trHtml = "<tr id=\"selected_"+id + "\"><td id=\""+pTdId + "\" rowspan=1>" +dess[1] + "<td>" +dess[2] + "</p></tr>";
						}
						$(lastBr).after(trHtml);
					} else {
						if(dess.length == 3) {
	                    	$("#selected").append("<tr id=\"selected_"+id + "\"><td id=\""+rTdId + "\" rowspan=1>" +dess[0] + "</td><td id=\""+pTdId + "\" rowspan=1>" +dess[1] + "</td><td>" +dess[2] + "</td></tr>");
						} else {
							$("#selected").append("<tr id=\"selected_"+id + "\"><td id=\""+rTdId + "\" rowspan=1>" +dess[0] + "</td><td id=\""+pTdId + "\" rowspan=1>" +dess[1] + "</td><td>&nbsp;</td></tr>");
						}
					}
				}
                adds.push(id);
            }
        }
    }

    function removeModel(liObj) {
        for (var j = 0; j < seleCategory.length; j++) {
            if (seleCategory[j].attr("id") == liObj.attr("id") && liObj.attr("idp") == seleCategory[j].attr("idp")) {
                seleCategory.remove(j);//集合中删除
                liObj.removeClass("current");
                liObj.find("input[type=checkbox]").attr("checked", false);
            }
        }
        if (!liObj.attr("isnextlevel") || liObj.attr("isnextlevel") == "false") {
        	var id = liObj.find("input").val(), index = $.inArray(id, adds);
        	var rTdId = "r_" + liObj.attr("id").substring(0,1) + "0000";
        	var pTdId = "p_" + liObj.attr("id").substring(0,3) + "00";
        	var dess= liObj.attr("des").split("> ");

			var rTd = $("#" + rTdId);
			var pTd = $("#" + pTdId);
			var curRow = $("#selected_" + id);
			var pCon=parseInt(pTd.attr("rowspan"));
			if(pCon>1){
				var firstTdId = curRow.children().get(0).id;
				if(firstTdId == pTdId) {
					var fb = curRow.next();
					var newTd = "<td id=\""+pTdId + "\" rowspan="+(pCon - 1)+">" +pTd.html() + "</td>";
					rTd.attr("rowspan",parseInt(rTd.attr("rowspan")) - 1);
					fb.html(newTd+fb.html());
				} else if (firstTdId==rTdId) {
					var fb = curRow.next();
					var newTd = "<td id=\""+rTdId + "\" rowspan="+(parseInt(rTd.attr("rowspan")) - 1)+">" + rTd.html() + "</td><td id=\""+pTdId + "\" rowspan="+(pCon - 1)+">" +pTd.html() + "</td>";
					fb.html(newTd+fb.html());
				} else {
					rTd.attr("rowspan",parseInt(rTd.attr("rowspan")) - 1);
					pTd.attr("rowspan",pCon - 1);
				}
			} else {
				pCon=parseInt(rTd.attr("rowspan"));
				if(pCon>1){
					var firstTdId = curRow.children().get(0).id;
					if (firstTdId==rTdId) {
						var fb = curRow.next();
						var newTd = "<td id=\""+rTdId + "\" rowspan="+(parseInt(rTd.attr("rowspan")) - 1)+">" + rTd.html() + "</td>";
						fb.html(newTd+fb.html());
					} else {
						rTd.attr("rowspan",pCon - 1);
					}
				}
			}
			curRow.remove();
            if (index >= 0) {
                adds.splice(index, 1);
            }
        }
    }

    function save(status) {
	   	var name = $.trim($("#name").val()), description = $.trim($("#description").val());
    	if(status=="add"){
    		if(name==""){
    			roleNameError("角色名称不能为空");
    			return ;
        	}else{
        		if(ajaxCheckRoleNameOnly(name)){
   					addRole();
        		}
        	}
    		
    	}else if(status=="update"){
    		if(name==""){
    			roleNameError("角色名称不能为空");
    			return ;
        	}else{
        		if(ajaxCheckRoleNameOnly(name)){
   					addRole();
        		}
        	}
    	}
	    
    }
    
    function addRole(){
    	if(validatorForm()) {
	        $.post(jsBasePath+"/role/add", 
	        	{id: $("#id").val(),name: $("#name").val(), description: $("#description").val(), adds: adds.join(",")},
	        	function (role) {
		            if (role) {
						showInfoBox("保存成功");
						setTimeout("goList()",3000);
		            }
	        }, "json");
        }
    }
    
     function ajaxCheckRoleNameOnly(roleName){
    	var flag = false;
    	if(roleName!=""){
    		var roleId = $("#id").val();
    		var data;
    		if(roleId==""){
				data={"roleName":roleName};			
			}else{
				data={"roleName":roleName,"roleId":roleId};			
			}  		
    		
    		$.ajax({
    			url : jsBasePath+'/checkRoleNameOnly.json',
    			type : "POST",
    			async:false,
    			dataType: "json",  //返回json格式的数据  
    		    data:data,
    			success : function(data) {
    				if(data==false){
    					roleNameError("角色名称已添加");
    					flag = false;
    				}else{
    					flag =  true;
    				}
    			}, error : function() {
    				flag = false;
    			}  
    		});
    		return flag;
    	}
    } 
    
    function roleNameError(text){
    	$("#role_name_error").text(text);
    	$("#role_name_error").attr("style","display:block;color: red;text-align: center;");
    	$("#role_name_error").fadeIn();
    	setTimeout("gotomain()",2000);
    	return false;
    }
    
    function gotomain(){
   	 $("#role_name_error").fadeOut();
   	 $("#sb").fadeOut();
   }

    function goList() {
    	location.href = jsBasePath+"/permissions_role.html";
    }
   	//非空验证
   	function validatorForm(){
   		var $list = $("[ismust=1]");
   		var ret = 0;
   		$list.each(function(i,val){
   			if($($list[i]).attr("typename")=='input'){
   				if($.trim($($list[i]).val())==''){
   					$($list[i]).addClass("bctxt");
   					ret++;
   				}
   			}
   		});
   		
   		if(ret>0){
   			showInfoBox("名称必须填写。");
            $("#name").focus();
   			return false;
   		}else{
   			return true;
   		}
   	}
   	
    //liObj 对象    点击1级类目项
    function checkModelUl2(liObj) {
        var v = true;
        //获取全部二级列表项
        var $ul2 = $("#ul2").find("li");
        $ul2.each(function (i, val) {
            if ($($ul2[i]).attr("idp") != "0") {
                var ul2Idp = $($ul2[i]).attr("idp");
                //如果存放信息集合为空,将会在二级类目中遍历和一级类目项相关的信息
                if (seleCategory.length == 0) {
                    if (liObj.attr("id") == $($ul2[i]).attr("idp")) {
                        $($ul2[i]).attr("style", "display: block;");
                    } else {
                        $($ul2[i]).removeClass("current");
                        $($ul2[i]).attr("style", "display: none;");
                    }
                    //集合不为空，将集合中数据有关的信息在二级类目中表示出来
                } else {
                    var flag = false;
                    for (var j = 0; j < seleCategory.length; j++) {
                        //如果集合中有何一级类目相关的二级类目,显示出来
                        if (seleCategory[j].attr("idp") == liObj.attr("id") && seleCategory[j].attr("id") == $($ul2[i]).attr("id")) {//&&liObj.attr("id")==$($ul2[i]).attr("idp")
                            $($ul2[i]).addClass("current");
                            $($ul2[i]).attr("style", "display: block;");
                            $($ul2[i]).find("input[type=checkbox]").attr("checked", true);
                            //二级列表如果由下一级
                            if ($($ul2[i]).attr("isnextlevel") == "true") {
                                var ul3Flag = true;
                                for (var w = 0; w < seleCategory.length; w++) {

                                    if (seleCategory[w].attr("idp") == $($ul2[i]).attr("id")) {
                                        /* var ul3 = $("#ul3").find("li");
                                         ul3.each(function(k,val){
                                         if($(ul3[k]).attr("idp")==seleCategory[w].attr("idp")){

                                         }
                                         }); */
                                        ul3Flag = false;
                                    }
                                }
                                if (ul3Flag) {
                                    $($ul2[i]).removeClass("current");
                                    $($ul2[i]).find("input[type=checkbox]").attr("checked", false);
                                }
                            }

                            v = false;
                            flag = true;
                        }
                    }
                    //集合中有数据
                    if (!flag) {
                        if (liObj.attr("id") == $($ul2[i]).attr("idp")) {
                            $($ul2[i]).removeClass("current");
                            $($ul2[i]).find("input[type=checkbox]").attr("checked", false);
                            $($ul2[i]).attr("style", "display: block;");
                        } else {
                            $($ul2[i]).removeClass("current");
                            $($ul2[i]).attr("style", "display: none;");
                            $($ul2[i]).find("input[type=checkbox]").attr("checked", false);
                        }
                    }
                }
            }
        });
        if (v) {
            $ul2.each(function (i, val) {
                if ($($ul2[i]).attr("idp") == liObj.attr("id")) {
                    $($ul2[i]).attr("style", "display: block;");
                } else {
                    if ($($ul2[i]).attr("idp") != 0) {
                        $($ul2[i]).removeClass("current");
                        $($ul2[i]).attr("style", "display: none;");
                        $($ul2[i]).find("input[type=checkbox]").attr("checked", false);
                    }
                }
            });
        }
    }
    //liObj 一级
    function checkModelUl3(liObj) {
        //获取全部二级列表项
        var $ul2 = $("#ul2").find("li");
        var $ul3 = $("#ul3").find("li");
        $ul2.each(function (i, val) {
            //二级列表显示
            if ($($ul2[i]).attr("style") == "display: block;" && $($ul2[i]).attr("class") == "current" && $($ul2[i]).attr("isnextlevel") == "true") {
                $ul3.each(function (j, val) {
                    if ($($ul3[j]).attr("idp") == "0") {
                        $($ul3[j]).attr("style", "display: block;");
                    } else {
                        if ($($ul2[i]).attr("id") == $($ul3[j]).attr("idp")) {
                            var flag = true;
                            for (var x = 0; x < seleCategory.length; x++) {
                                if (seleCategory[x].attr("id") == $($ul3[j]).attr("id")) {
                                    $($ul3[j]).addClass("current");
                                    $($ul3[j]).attr("style", "display: block;");
                                    $($ul3[j]).find("input[type=checkbox]").attr("checked", true);
                                    flag = false;
                                }
                            }
                            if (flag) {
                                var v = false;
                                for (var x = 0; x < seleCategory.length; x++) {
                                    if (seleCategory[x].attr("idp") == $($ul2[i]).attr("id")) {
                                        v = true;
                                    }
                                }
                                if (v) {
                                    $($ul3[j]).attr("style", "display: block;");
                                    $($ul3[j]).find("input[type=checkbox]").attr("checked", false);
                                }
                            }
                        }
                    }

                });
            }

        });
    }
    /**
     *第一级的单击事件
     */
    var prevObje = null;
    function checkli1(obj) {
        if ($(obj).attr("id") != $(prevObje).attr("id")) {
            //控制2  3级类目的全选按钮//////////////////////////////////////////////////////////
            var $ul2 = $("#ul2").find("li");
            var $ul3 = $("#ul3").find("li");
            $ul2.each(function (i, val) {
                if ($($ul2[i]).attr("idp") == "0") {
                    $($ul2[i]).find("input[type=checkbox]").attr("checked", false);
                }
            });
            $ul3.each(function (i, val) {
                if ($($ul3[i]).attr("idp") == "0") {
                    $($ul3[i]).find("input[type=checkbox]").attr("checked", false);
                }
            });
            //关闭全部第三级类目
            closeUl3All();
            //添加选中状态
            $(obj).siblings("li").removeClass("current").end().addClass("current");
            checkModelUl2($(obj));
            checkModelUl3($(obj));
        }


        prevObje = obj;
    }

    /**
     *第二级的单击事件
     */
    function checkli2(obj) {
        var obj2 = $(obj);
        //获取全部三级列表项
        var $ul3 = $("#ul3").find("li");
        //取消选中状态
        if (obj2.hasClass("current")) {
            obj2.removeClass("current");
            obj2.find("input[type=checkbox]").attr("checked", false);
            //在集合中删除二级列表信息
            removeModel(obj2);
            ///////////////////////////////////////////////////////////////////////////
            //二级列表下还有三级列表,就需要操作三级列表
            if (obj2.attr("isnextlevel") == "true") {
                //删除三级类目中的项
                $ul3.each(function (i, val) {
                    if ($($ul3[i]).attr("idp") == obj2.attr("id") && $($ul3[i]).attr("idp") != 0) {
                        removeModel($($ul3[i]));//删除三级项
                        $($ul3[i]).removeClass("current");
                        $($ul3[i]).attr("style", "display: none;");
                        $($ul3[i]).find("input[type=checkbox]").attr("checked", false);
                    }
                });
                var j = 0;
                var $u = $("#ul3").find("li");
                $u.each(function (i, val) {
                    if ($($u[i]).attr("style") == "display: block;") {
                        j++;
                    }
                });
            }
            ///////////////////////////////////////////////////////////////////////////
            //选中状态
        } else {
            obj2.addClass("current");
            obj2.find("input[type=checkbox]").attr("checked", true);
            //二级列表下还有三级列表,就需要操作三级列表
            if (obj2.attr("isnextlevel") == "true") {
                //将二级项，且没有下一级的放入集合中
                setModel(obj2);
                //选中二级列表中某个选项的id
                var ojbId = obj2.attr("id");

                //打开或关闭二级列表中的项
                $ul3.each(function (i, val) {
                    var ul3Idp = $($ul3[i]).attr("idp");
                    if (ul3Idp != "0") {
                        if (ojbId == ul3Idp) {
                            $($ul3[i]).attr("style", "display: block;");
                        }
                    } else {
                        $($ul3[i]).attr("style", "display: block;");
                    }
                });
            } else {
                //将二级项，且没有下一级的放入集合中
                setModel(obj2);
            }
        }
    }

    /**
     * 三级类目单选
     */
    function checkli3(obj) {
        //添加选中状态
        if ($(obj).hasClass("current")) {
            $(obj).removeClass("current");
            $(obj).find("input[type=checkbox]").attr("checked", false);
            //删除集合数据
            removeModel($(obj));
        } else {
            $(obj).addClass("current");
            $(obj).find("input[type=checkbox]").attr("checked", true);
            //将三级项，放入集合中
            setModel($(obj));
        }

    }


    //关闭全部第三级类目
    function closeUl3All() {
        var $ul3 = $("#ul3").find("li");
        $ul3.each(function (i, val) {
            $($ul3[i]).attr("style", "display: none;");
        });
    }


    function deltan() {
        $(".popup_bg").show();
        $("#shop_popup_delete").fadeIn();
    }

    function delcategory(id, type) {
        $("#shop_popup_delete").fadeOut();
        $("#deleteid").val(id);
        $("#typeid").val(type);
        $(".popup_bg").show();
        $("#shop_popup_delete").fadeIn();
    }

    function delcategory2() {
        $(".popup_bg").hide();
        $("#shop_popup_delete").fadeOut();
    }

    function delcategory1() {

        var id = $("#deleteid").val();
        var type = $("#typeid").val();
        if (type == '1') {
            $("#re" + id).remove();
        } else if (type == '2') {
            if ($("#re" + id).siblings("p").length == 0) {
                $("[idp=re" + id + "]").parents("tr").remove();
            } else {
                $("#re" + id).remove();
                $("[idp=re" + id + "]").remove();
            }
            $("#" + id).find("input[type=checkbox]").attr("checked", false);
            $("#" + id).removeClass("current");
            checkli2id(id);
        } else if (type == '3') {
            if ($("#re" + id).siblings("p").length == 0) {
                $("#re" + id).parents("tr").remove();
            } else {
                if ($("[idp=" + $("#re" + id).attr("idp") + "]").length > 1) {
                    $("#re" + id).remove();
                } else {
                    $("#" + $("#re" + id).attr("idp")).remove();
                    $("#re" + id).remove();
                }
            }
            $("#" + id).find("input[type=checkbox]").attr("checked", false);
            $("#" + id).removeClass("current");
        }
        $(".popup_bg").hide();
        $("#shop_popup_delete").fadeOut();

        //window.location.href = "http://localhost:8080/supplier/supplierCategory/deletebymap.html?id="+id+"&type="+type+"";
    }

    var flag = false;
    var flag2 = false;


    function display() {
        $("#cg").fadeOut();
        $("#sb").fadeOut();
        $("#ts").fadeOut();
    }
