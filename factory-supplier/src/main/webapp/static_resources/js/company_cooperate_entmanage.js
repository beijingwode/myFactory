$(function(){
	//加载页面，控制左边的菜单
	$("#entmanage").parent().parent().prev().addClass("active");
	$("#entmanage").parent().parent().attr("style","display: block;");
	$("#entmanage").parent().addClass("active");
	$("#entmanage").addClass("active");
});
var entName = "${enterprise}";
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
		$("#entmanage").addClass("curr");
		//########################add begin############################
		//添加按钮
		$("#add_emp_ally").click(function(){
			background_open();
			ent_box_open('#add');
		});
		//添加友盟企业
		$("#add_but_sub").click(function(){
			var name1 = $("#add_input").val();
			if($.trim(name1).length==0){
				add_ent_error_open("公司名称不能为空",3000);
			}else{
				$.ajax({
					url : jsBasePath +'/company/enterprise/getByEnterpriseName.json',
					type : "POST",
					dataType: "json",  //返回json格式的数据  
				    async: true,
				    data:{"name":name1},
					success : function(data) {
						if(data==null){
							add_ent_error_open("公司还未注册",3000);			
						}else if(data.id=="${ent_id}"){
							add_ent_error_open("不能添加自己的公司",3000);			
						}else{
							var ent_id = data.id;
							$.ajax({
								url :jsBasePath + '/company/enterprise/structure/addEnterprise.json',
								type : "POST",
								dataType: "json",  //返回json格式的数据  
							    async: true,
							    data:{"type":3,"relatedEntId":ent_id},//1表示母企业 2表示子企业 3表示友盟企业
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
		});
		//添加友盟企业信息 ---关闭弹窗
		$('#add-close').click(function(){
			$("#add_input").val("");
			background_close();
			ent_box_close('#add');	
		})
		//添加友盟企业信息 ---取消按钮弹窗
		$('#cansel-btn').click(function(){
			$("#add_input").val("");
			background_close();
			ent_box_close('#add');
		})
		
		//########################add end############################
		//########################update begin############################
		//修改按钮
		$("#update_emp_ally").click(function(){
			changeBtn();
		});
		
		$("#alter-ture-btn").click(function(){
			//新修改的
			var ent = new Array();
			//新修改的+未修改的（全部的企业名称）
			var all_end = new Array();
			$("#update_info p").each(function(){
				//新输入公司名称
				var new_ent_name = $(this).find(".add-input").val();

				//原来的公司名称
				var ent_name=$(this).find(".hidden").val();
				
				all_end.push(new_ent_name);
				//原来公司企业组织结构id
				var ent_id=$(this).find(".hidden").attr("id");
				if(ent_name!=new_ent_name){
					var obj={};
					obj.id = ent_id;
					obj.enterpriseName =new_ent_name;
					
					ent.push(obj);
					
				}
			});

			//获取新修改的企业名称
			for(var i = 0;i<ent.length;i++){
				if(ent[i].enterpriseName==""){
					operation_success_box("企业不能为空");
					ent_box_close('#alter');
					return ;
				}else if(ent[i].enterpriseName==entName){
					operation_success_box("不能添加自己的公司");
					ent_box_close('#alter');
					return ;
				}
				
				var q = 0;//全部的企业名称
				for(var j =0 ;j<all_end.length;j++){
					if(ent[i].enterpriseName==all_end[j]){
						q++;
					}
				}
				
				if(q>1){
				operation_success_box("重复修改企业");
				ent_box_close('#alter');
				return ;
				}
				
			}

			if(ent.length>0){
				$.ajax({
					url : jsBasePath +'/company/enterprise/structure/updateEnterprise.json',
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
					}, error : function() {
				    }  
				});
			}
		});
		
		
		
		//修改友盟企业信息 ---关闭弹窗
		$('#alert-close').click(function(){
			clear_empty_html("#update_info");
			background_close();
			ent_box_close('#alter');
		})
		//修改友盟企业信息 ---取消按钮弹窗
		$('#alter-cansel-btn').click(function(){
			clear_empty_html("#update_info");
			background_close();
			ent_box_close('#alter');
		})
		//########################update end##############################
		//########################del begin##############################\
		
		//删除按钮
		$("#del_emp_ally").click(function(){
			deleteBtn();
        	
		});
		//删除友盟企业的id集合
		var del_ally_ent_id = new Array();
		//确认删除
		$("#delete-ture-btn").click(function(){
			$.each($("#del_info").find(".add-input"),function(){
				if($(this).attr("style")=="display: none;"){
					var obj={};
					obj.id=$(this).attr("id");
					
					del_ally_ent_id.push(obj);
				}
			});
			if(del_ally_ent_id.length>0){
				$.ajax({
					url : jsBasePath + '/company/enterprise/structure/delEnterprise.json',
					type : "POST",
					contentType: 'application/json',
					dataType: "json",  //返回json格式的数据  
				    async: true,
				    data:JSON.stringify(del_ally_ent_id),
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
		});
		
		
		//删除单个公司，并隐藏
		$(".delete-icon").live("click",function(){
			//隐藏图标
			$(this).attr("style","display: none;");
			//隐藏输入框
			$(this).prev().attr("style","display: none;");
		});
		//修改友盟企业信息 ---关闭弹窗
		$('#delete-close').click(function(){
			/* clear_empty_html("#del_info");
			background_close();
			ent_box_close('#delete'); */
			//刷新页面
			location.reload();
		})
		//修改友盟企业信息 ---取消按钮弹窗
		$('#delete-cansel-btn').click(function(){
			/* clear_empty_html("#del_info");
			background_close();
			ent_box_close('#delete'); */
			//刷新页面
			location.reload();
		})
		//########################del end##############################
	}); 
	
	
	//########################add begin############################
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
	//########################add end############################
