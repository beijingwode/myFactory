	//删除公司的id数组
	var del_ent_id = new Array();
$(function(){
	//输入框获取光标
	$(".add-input").live("focus",function(){
		$(this).addClass("p-red");
	});
	//输入框失去光标
	$(".add-input").live("blur",function(){
		$(this).removeClass("p-red");
	});
	
	//加载页面，控制左边的菜单
	$("#ent_structure").addClass("curr");
	//##############################add begin###################################
	//所属母公司   添加按钮
	$("#mother_add").click(function(){
		add_ent_box_html("添加母公司","添加一家母公司");
		//更改添加弹窗确定按钮的id值
		$("#ture-btn").attr("onclick","mother_add_sub_but()");
		/* $("#ture-btn").live("click",function(){
		mother_add_sub_but();
	}); */
		background_open();
		ent_box_open('#add');
	});
	
	//所属子公司  添加按钮
	$("#child_add").click(function(){
		add_ent_box_html("添加子公司","添加一家子公司");
		//更改添加弹窗确定按钮的id值
		$("#ture-btn").attr("onclick","child_add_sub_but()");
		/* $("#ture-btn").live("click",function(){
			child_add_sub_but();
		}); */
		background_open();
		ent_box_open('#add');
	});
	
	//添加公司信息 ---关闭弹窗
	$('#add-close').click(function(){
		$("#add_input").val("");
		background_close();
		ent_box_close('#add');	
	})
	//添加公司信息 ---取消按钮弹窗
	$('#cansel-btn').click(function(){
		$("#add_input").val("");
		background_close();
		ent_box_close('#add');
	})
	//##############################add end###################################
	//##############################update begin###################################
	//修改  母公司按钮
		$("#mother_update").click(function(){
			clear_empty_html("#update_info");
			update_ent_box_html("修改母公司");
			//更改添加弹窗确定按钮的id值
			$("#alter-ture-btn").attr("onclick","update_sub_but('motherEnt')");
			/* $("#alter-ture-btn").live("click",function(){
				update_sub_but('motherEnt');
			}); */
			changeBtn();
			//打开背景弹窗
			background_open();
			//弹出修改弹窗
			ent_box_open('#alter');
		});
		
		//修改  子公司按钮
		$("#child_update").click(function(){
			clear_empty_html("#update_info");
			update_ent_box_html("修改子公司");
			//更改添加弹窗确定按钮的id值
			$("#alter-ture-btn").attr("onclick","update_sub_but('childEnt')");//child_update_sub_but
			
			/* $("#alter-ture-btn").live("click",function(){
				update_sub_but('childEnt');
			}); */
			
			changeIdVal();
			//打开背景弹窗
			background_open();
			//弹出修改弹窗
			ent_box_open('#alter');
		});
		
		
		
		
		//修改公司信息 ---关闭弹窗
		$('#alert-close').click(function(){
			clear_empty_html("#update_info");
			background_close();
			ent_box_close('#alter');
		})
		//修改公司信息 ---取消按钮弹窗
		$('#alter-cansel-btn').click(function(){
			clear_empty_html("#update_info");
			background_close();
			ent_box_close('#alter');	
		})
	//##############################update end###################################
	//##############################del begin###################################
	//删除母公司按钮
		$("#mother_del").click(function(){
			clear_empty_html("#del_info");
			del_ent_box_html("删除母公司");
			//更改添加弹窗确定按钮的id值
			$("#delete-ture-btn").attr("onclick","del_sub_but()");
			/* $("#delete-ture-btn").live("click",function(){
				del_sub_but();
			}); */
			   deleteMotherEnt();
				//打开背景弹窗
				background_open();
				//弹出弹窗
				ent_box_open('#delete');
		});
		
		//删除子公司按钮
		$("#child_del").click(function(){
			clear_empty_html("#del_info");
			del_ent_box_html("删除子公司");
			//更改添加弹窗确定按钮的id值
			$("#delete-ture-btn").attr("onclick","del_sub_but()");
			/* $("#delete-ture-btn").live("click",function(){
				del_sub_but();
			}); */
				deleteChildEnt();
				//打开背景弹窗
				background_open();
				//弹出弹窗
				ent_box_open('#delete');
		});
		
		//删除单个公司，并隐藏
		$(".delete-icon").live("click",function(){
			//隐藏图标
			$(this).attr("style","display: none;");
			//隐藏输入框
			$(this).prev().attr("style","display: none;");
		});
		
		//删除公司信息 ---关闭弹窗
		$('#delete-close').click(function(){
			/* $("#add_input").val("");
			clear_empty_html("#del_info");
			background_close();
			ent_box_close('#delete'); */
			//刷新页面
			location.reload();
		})
		//删除公司信息 ---取消按钮弹窗
		$('#delete-cansel-btn').click(function(){
			/* $("#add_input").val("");
			clear_empty_html("#del_info");
			background_close();
			ent_box_close('#delete'); */
			//刷新页面
			location.reload();
		})
	//##############################del end###################################
	
}); 

//##########################add begin##################################
//添加公司   弹出弹窗的文本内容
function add_ent_box_html(title,cont){
	//设置弹窗标题
	$("#add_ent_title").html(title);
	//设置弹窗内容
	$("#add_ent_cont").html(cont);
}


//显示添加企业错误信息
function add_ent_error_open(str,closeTime){
	$("#add_input_error").html(str);
	$("#add_input_error").fadeIn();
	setTimeout("add_ent_error_close()",closeTime);
}
//隐藏添加企业错误信息
function add_ent_error_close(str){
	$("#add_input_error").fadeOut();
}
//添加母/子公司
function add_ajax(type,name){
	var name1 = $("#add_input").val();
	if($.trim(name1).length==0){
		add_ent_error_open("公司名称不能为空",3000);
	}else{
		$.ajax({
			url : jsBasePath+'/company/enterprise/getByEnterpriseName.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:{"name":name1},
			success : function(data) {
				if(data==null){
					add_ent_error_open("公司还未注册",3000);			
				}else if(data.id==entId){
					add_ent_error_open("不能添加自己的公司",3000);			
				}else{
					var ent_id = data.id;
					$.ajax({
						url : jsBasePath+'/company/enterprise/structure/addEnterprise.json',
						type : "POST",
						dataType: "json",  //返回json格式的数据  
					    async: true,
					    data:{"type":type,"relatedEntId":ent_id},//1表示母企业 2表示子企业
						success : function(data) {
							if(data>0){
								//弹出添加成功的弹窗
								operation_success_box("添加成功");
								ent_box_close('#add');
								$(".add-input").val("");
							}else if(data==0){
								add_ent_error_open("添加失败",3000);
							}else if(data==-1){
								add_ent_error_open("已经添加",3000);
							}else{
								add_ent_error_open("未知错误",3000);
							}
						}, error : function() {
					    }  
					});
				}
			}, error : function() {
			}  
		});
	}
}
//添加母公司确认按钮
function mother_add_sub_but(){
	addChildEnt();
}
//添加母公司确认按钮
function child_add_sub_but(){
	addMotherEnt();
}
//##########################add end##################################
//##########################update begin##################################
//修改母公司信息
	function update_sub_but(flag){
		var ent = new Array();
	 
 	
		$("#update_info p").each(function(){
			//新输入公司名称
			var new_ent_name = $(this).find(".add-input").val();
			//原来的公司名称
			var ent_name=$(this).find(".hidden").val();
			//原来公司企业组织结构id
			var entStr_id=$(this).find(".hidden").attr("id");
			
			if(flag == "childEnt"){
				mEnt();
				var i = 0;
				$("#update_info p").each(function(){
					var entName = $(this).find(".add-input").val();
					if(new_ent_name==entName){
						i++;
					}
				});
				if(i>1){
					operation_success_box("不能重复添加");
					ent_box_close('#alter');
					return ;
				}
			}else if(flag == "motherEnt"){
				cEnt();
			}else{
				return ;
			}
			
			if(ent_name!=new_ent_name){
				var obj={};
				obj.id = entStr_id;
				obj.enterpriseName =new_ent_name;
				
				ent.push(obj);
			}
			
		});
		
		if(ent.length>0){
			$.ajax({
				url : jsBasePath+'/company/enterprise/structure/updateEnterprise.json',
				type : "POST",
				contentType: 'application/json',
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:JSON.stringify(ent),
				success : function(data) {
					if(data>0){
						operation_success_box("修改成功");
						ent_box_close('#alter');
					}else if(data==-1){
						operation_success_box("企业不存在")
						ent_box_close('#alter');
					}else{
						operation_success_box("修改失败")
						ent_box_close('#alter');
					}
				}, error:function(textStatus, textStatus, errorThrown){
					alert(errorThrown);
					alert(textStatus);
					alert(textStatus);
				}
			});
		}
	}
	//修改子公司信息
	function child_update_sub_but(){
		alert("child");
	}
	//修改公司   弹出弹窗的文本内容
	function update_ent_box_html(title){
		//设置弹窗标题
		$("#update_ent_title").html(title);
	}
//##########################update end##################################
//##########################del begin##################################
//提交删除公司ajax    del_ent_id
	function del_sub_but(){
		$.each($("#del_info").find(".add-input"),function(){
			var styleVal = $(this).attr("style");
			if(styleVal=="display: none"||styleVal=="display: none;"){
				var obj={};
				obj.id=$(this).attr("id");
				del_ent_id.push(obj);
			}
		});
		if(del_ent_id.length>0){  
			$.ajax({
				url : jsBasePath+'/company/enterprise/structure/delEnterprise.json',
				type : "POST",
				contentType: 'application/json',
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:JSON.stringify(del_ent_id),
				success : function(data) {
					if(data>0){
						operation_success_box("删除成功");
						ent_box_close('#delete');
					}else{
						operation_success_box("删除失败");
						ent_box_close('#delete');
					}
					
				}
			});
		}
		
		
	}
	
	//添加公司   弹出弹窗的文本内容
	function del_ent_box_html(title){
		//设置弹窗标题
		$("#del_ent_title").html(title);
	}
	
//##########################del end##################################
	//##########################commen begin##################################
	
	//##########################commen end##################################