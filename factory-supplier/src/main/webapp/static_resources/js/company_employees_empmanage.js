	//注册员工,该员工编号是否存在标示
	var add_emp_checkEmpNumber_flag;
	$(function(){
		//加载页面，控制左边的菜单
		$("#emp_manage").parent().parent().prev().addClass("active");
		$("#emp_manage").parent().parent().attr("style","display: block;");
		$("#emp_manage").parent().addClass("active");
		$("#emp_manage").addClass("active");
	});

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
			$("#pageNumber").val(page);
		}else{
			$("#pageNumber").val(1);
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
	
	$(document).ready(function(){
		//输入框获取光标
		$(".r-input,.r-num,.r-select,.c-r-select").live("focus",function(){
			$(this).addClass("p-red");
		});
		//输入框失去光标
		$(".r-input,.r-num,.r-select,.c-r-select").live("blur",function(){
			$(this).removeClass("p-red");
		});
		
		//条件筛选(分页查询)
		$("#filter_btn").click(function(){
			refreshHtml();
		});
		//##################add Box start###########################
		//点击注册员工按钮
		$("#add_emp").click(function(){
			$("#add_emp_phone").val("");
			$("#add_emp_name").val("");
			$("#add_emp_age").val("");
			$("#add_emp_seniority").val("");
			$("#add_emp_welfareLevel").val("");
			$("#add_emp_duty").val("");
			$("#add_emp_sectionName").val("");
			$("#add_emp_nan").attr("checked","checked");
			$("#add_emp_email").val("");
			//打开背景
			background_open();
			//打开弹窗
			ent_box_open("#add-employee");
			//获取光标
			var number = $("#add_emp_number").val();
			//获取光标
			$("#add_emp_number").val("").focus().val(number);
		});
		//批量新增
		$('#add_emp_set').click(function(){
			//打开背景
			background_open();
			//打开弹窗
			ent_box_open("#set_pb");
			
			$.ajax({
				url : jsBasePath +'/company/emp/getEnterpriseInfo?entId='+jsEntId,
				type : "GET",
				dataType: "json",  //返回json格式的数据  
			    async: true,
				success : function(data) {
					var ent=data.data;
					
					// postfix
					var html = "";
					//html +='<div class="suffix_con" >';
					//html +='<em>@</em><input type="text" value="'+ent.emailPostfix1+'" id="email1" /><i onclick="addInp()">+</i>';
					//html +='</div>';
					if(ent.emailPostfix1 == undefined || ent.emailPostfix1 == "" || ent.emailPostfix1== null) {
						html +='<div class="suffix_con" >';
						html +='<em>@</em><input type="text" value="" id="email1" /><i onclick="addInp()">+</i>';
						html +='</div>';
					} else {
						html +='<div class="suffix_con" >';
						html +='<em>@</em><input type="text" value="'+ent.emailPostfix1+'" id="email1" /><i onclick="addInp()">+</i>';
						html +='</div>';
					}
					
					if(ent.emailPostfix2 == undefined || ent.emailPostfix2 == "" || ent.emailPostfix2== null) {
					} else {
						html +='<div class="suffix_con" >';
						html +='<em>@</em><input type="text" value="'+ent.emailPostfix2+'" id="email1" /><i class="del" onclick="delInp(this);" >-</i>';
						html +='</div>';
					}
					if(ent.emailPostfix3 == undefined || ent.emailPostfix3 == "" || ent.emailPostfix3== null) {
					} else {
						html +='<div class="suffix_con" >';
						html +='<em>@</em><input type="text" value="'+ent.emailPostfix3+'" id="email1" /><i class="del" onclick="delInp(this);" >-</i>';
						html +='</div>';
					}
					html +='<p>必须为企业邮箱，用该邮箱注册后，自动成为该企业员工</p>';
					$(".email_suffix_rt").html(html);
					
					// default
					if(ent.empDefultAvatar == undefined || ent.empDefultAvatar == "" || ent.empDefultAvatar== null) {
					} else {
						$(".tx_rt img").attr("src",ent.empDefultAvatar);
						$(".tx_rt input").val(ent.empDefultAvatar);
						
					}
				},
				error : function() {}
			});
			
			
		});
		
		
		//员工注册设置		
		$('#alladd').click(function(){
			//打开背景
			background_open();
			//打开弹窗
			ent_box_open("#all-add");
		});
		
		
		//员工福利分享
		$('#share').click(function(){
			//打开背景
			background_open();
			if($('.share_link').html() == "") {
				$.ajax({
					url : jsBasePath + '/company/emp/makeQr.json',
					type : "POST",
					dataType: "json",  //返回json格式的数据  
				    async: true,
					success : function(data) {
						if(data.success) {
							$(".share_link").html(data.data.shareUrl);
							$(".ewm_share img").attr("src","http://api.wd-w.com/userShare/getQr?text="+data.data.shareUrl);
							$('#share').html("自行注册链接");
						}
					}, error : function() {
				    }  
				});
			}
			//打开弹窗
			ent_box_open("#share_pb");
		});
		
		if($('.share_link').html() != "") {
			$(".ewm_share img").attr("src","http://api.wd-w.com/userShare/getQr?text="+$('.share_link').html());
			$('#share').html("自行注册链接");
		}
		
		//上传excel
		$("#alladd-ture-btn").click(function(){
			var name = $("#file").val();
			var suffixName = name.substring(name.lastIndexOf(".")).toLowerCase();
			if(suffixName!=".xlsx"){
				ent_box_close("#all-add");
				operation_success_box("文件格式错误");
				return ;
			}
			 $.ajaxFileUpload({
		            url: jsBasePath + '/company/emp/addBatchEmp.json', 
		            type: 'post',
		            secureuri: false, //是否启用安全提交,默认为false 
		            fileElementId: 'file', //文件选择框的id属性
		            dataType: 'json', //返回值类型，一般设置为json、application/json、text
		            success: function(data, status){
		            	if(data.success) {
		            		ent_box_close("#all-add");
							operation_success_box(data.msg);
		            	} else {
		            		ent_box_close("#all-add");
							operation_success_box(data.msg);
		            	}
		            },
		            error: function(data){
		            	ent_box_close("#all-add");
						operation_success_box(data.msg);
		            }
		        });
		});
		//提交添加
		$("#add-employee-ture-btn").click(function(){
			var pattern = /^1\d{10}$/; 
			//phone_error   $.trim(userName).length==0  ^(1[3|4|5|7|8][0-9])\\d{8}$
			//获取手机号
			var phone = $("#add_emp_phone").val();
			//获取邮箱
			var email = $("#add_emp_email").val();
			//获取福利级别
			var welfareLevel = $("#add_emp_welfareLevel").val();
			//获取员工名称
			var name = $("#add_emp_name").val();
			//校验员工编号是否存在
			checkEmpNumber($("#add_emp_number").val());
			//员工编号存在
			if(add_emp_checkEmpNumber_flag){
				
			}else if($.trim(phone).length==0 && $.trim(email).length==0){
				$("#add_phone_error").html("手机号和邮箱不能同时为空");
				$("#add_phone_error").fadeIn();
				$("#add_email_error").html("手机号和邮箱不能同时为空");
				$("#add_email_error").fadeIn();
				setTimeout("gotomain_addEmp()",3000);
			}else if($.trim(phone).length>0 &&!pattern.test(phone)){
				$("#add_phone_error").html("手机号格式错误");
				$("#add_phone_error").fadeIn();
				setTimeout("gotomain_addEmp()",3000);
			}else if($.trim(email).length>0 && !checkEmail(email)){
				$("#add_email_error").html("邮箱格式错误");
				$("#add_email_error").fadeIn();
				setTimeout("gotomain_addEmp()",3000);
			}else if($.trim(name).length==0){
				$("#add_name_error").html("员工姓名不能为空");
				$("#add_name_error").fadeIn();
				setTimeout("gotomain_addEmp()",3000);
				
			}else if($.trim(welfareLevel).length==0){
				$("#add_welfareLevel_error").html("福利级别不能为空");
				$("#add_welfareLevel_error").fadeIn();
				setTimeout("gotomain_addEmp()",3000);
			}else{
				$.ajax({
					url : jsBasePath + '/company/emp/add.json',
					type : "POST",
					dataType: "json",  //返回json格式的数据  
				    async: true,
				    data:$("#add_emp_form").serialize(),
					success : function(data) {
						if(data==0){
							ent_box_close("#add-employee");
							operation_success_box("参数为空");
						}else if(data==1){
							ent_box_close("#add-employee");
							operation_success_box("添加成功");
						}else if(data==2){
							ent_box_close("#add-employee");
							operation_success_box("已经是本企业用户");
						}else if(data==3){
							ent_box_close("#add-employee");
							operation_success_box("用户在其他企业已注册");
						}else if(data==-1||data==-2||data==-3||data==-4){
							ent_box_close("#add-employee");
							operation_success_box("用户注册失败");
						}else if(data==-5){
							ent_box_close("#add-employee");
							operation_success_box("该用户为其他员工亲友，为确保员工利益，请通知用户删除员工后，再行添加");
						}else if(data==-6){
							ent_box_close("#add-employee");
							operation_success_box("手机号或者邮箱已被其他人使用，请确认后重试、");
						}else{
							ent_box_close("#add-employee");
						}
					}, error : function() {
						ent_box_close("#add-employee");
				    }  
				});
			}
		});
		
		$("#add_emp_number").blur(function(){
			var empNumber = $(this).val();
			checkEmpNumber(empNumber);
			
		});
		
		
		//关闭注册弹窗  
		$('#add-employee-close').live("click",function(){
			//关闭背景
			background_close();
			//关闭弹窗
			ent_box_close('#add-employee');
		});
		//取消注册按钮 
		$('#add-employee-cansel-btn').live("click",function(){
			background_close();
			ent_box_close('#add-employee');
		});
		
		
		//关闭批量添加弹窗  
		$('#alladd-close').live("click",function(){
			//关闭背景
			background_close();
			//关闭弹窗
			ent_box_close('#all-add');
		});
		//取消批量注册按钮 
		$('#alladd-cansel-btn').live("click",function(){
			background_close();
			ent_box_close('#all-add');
		});
		
		
		//关闭员工福利分享
		$('#share_pb_close').live("click",function(){
			//关闭背景
			background_close();

			//关闭弹窗
			ent_box_close('#share_pb');
		});
		
		//关闭员工注册设置
		$('#set_pb_close').live("click",function(){
			//关闭背景
			background_close();

			//关闭弹窗
			ent_box_close('#set_pb');
		});
		
		
		$('#clear_btn').live("click",function(){
			//关闭背景
			background_close();
			
			
			$.ajax({
				url : jsBasePath + '/company/emp/removeQr.json',
				type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
				success : function(data) {
					if(data.success) {
						$(".share_link").html("");
						$(".ewm_share img").attr("src","");
						$('#share').html("生成注册链接");
					}
				}, error : function() {
			    }  
			});
			
			//关闭弹窗
			ent_box_close('#share_pb');
		});
		
		//##################add Box end#############################           
		
		//###################update Box start#######################
		//点击更改福利级别的输入框
		$("#updateWelfareLevel").click(function(){
			//打开背景
			background_open();
			//打开弹窗      
			ent_box_open("#alter-setting");
			$("#welfareLevel_input").val("").focus().val(jsWelfareLevel);
		});
		//确认修改福利级别
		$("#alter-setting-ture-btn").click(function(){
			//修改之前的福利级别
			var oldWelLevel = $("#updateWelfareLevel").val();
			//新输入的福利级别
			var newWelLevel = $("#welfareLevel_input").val();
			//截取输入的首数字
			var welLevel_frist = newWelLevel.substring(0,1);
			//福利级别不能大于50
			if(newWelLevel>50){
				ent_box_close('#alter-setting');
				operation_success_box("福利级别不能大于50");
				$("#welfareLevel_input").val(oldWelLevel);
			}else if($.trim(newWelLevel)==""){
				$("#update_welfareLevel_error1").html("福利级别不能为空");
				$("#update_welfareLevel_error1").fadeIn();
				//设置原来的员工福利值
				$("#welfareLevel_input").val(oldWelLevel);
				setTimeout("gotomain()",3000);
			}else if(welLevel_frist==0&&$.trim(welLevel_frist).length!=0){
				$("#update_welfareLevel_error1").html("福利级别不能以0开头");
				$("#update_welfareLevel_error1").fadeIn();
				//设置原来的员工福利值
				$("#welfareLevel_input").val(oldWelLevel);
				setTimeout("gotomain()",3000);
			}else if(oldWelLevel<=newWelLevel){
				//更改福利级别
				updateWelfareLevel();
			}else{
				//关闭弹窗alter-grade
				ent_box_close('#alter-setting');
				ent_box_open('#alter-grade');
			}
		});
		$("#alter-grade-ture-btn").click(function(){
			//更改福利级别
			updateWelfareLevel();
		});
		//
		$('#aalter-grade-close').live("click",function(){
			//关闭背景
			background_close();
			//关闭弹窗
			ent_box_close('#alter-grade');  
			$("#welfareLevel_input").val(jsWelfareLevel);
		})
		$('#alter-grade-cansel-btn').live("click",function(){
			background_close();
			ent_box_close('#alter-grade');
			$("#welfareLevel_input").val(jsWelfareLevel);
		})
		//关闭修改福利级别弹窗
		$('#alter-setting-close').live("click",function(){
			//关闭背景
			background_close();
			//关闭弹窗
			ent_box_close('#alter-setting');  
			$("#welfareLevel_input").val(jsWelfareLevel);
		})
		//取消修改福利级别按钮  change-suceess-close
		$('#alter-setting-cansel-btn').live("click",function(){
			background_close();
			ent_box_close('#alter-setting');
			$("#welfareLevel_input").val(jsWelfareLevel);
		})
		
		//取消员工注册修改按钮  change-suceess-close
		$('#set-cansel-btn').live("click",function(){
			
			background_close();
			ent_box_close('#set_pb');
			$(".hint").hide();
			$(".suffix_con input").css("border-color","#ccc");
			
		})
		
		
		//关闭修改弹窗
		$('#change-close').live("click",function(){
			//关闭背景
			background_close();
			//关闭弹窗
			ent_box_close('#change');
			//清除修改信息页面弹出框内容
			clear_empty_html("#update_popup_box");
		})
		//取消修改按钮  change-suceess-close
		$('#change-cansel-btn').live("click",function(){
			background_close();
			ent_box_close('#change');
			//清除修改信息页面弹出框内容
			clear_empty_html("#update_popup_box");
		})
		//###################update Box end#######################
		//###################del Box begin#######################
		//关闭修改弹窗  
		$('#delete-info-close').live("click",function(){
			//关闭背景
			background_close();
			//关闭弹窗
			ent_box_close('#delete-info');
		})
		//取消修改按钮 
		$('#info-cansel-btn').live("click",function(){
			background_close();
			ent_box_close('#delete-info');
		})
		//###################del Box end#######################
	});
	
	//############################################update############################################################
	//修改员工信息操作(弹出修改信息页面)
	function update_getDetails(i){
		$.ajax({
			url : jsBasePath + '/company/emp/getById.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据 
		    async: true,
		    data:{"id":i},
			success : function(data) {
				var html = "";
				html+="<form id=\"update_submit\">";
				html+="<div class=\"public_popup\" id=\"change\">";
				html+="<div class=\"pop-title\"><span>修改员工信息</span><div class=\"add-close-btn\" id=\"change-close\"><i class=\"add-close-icon\"></i></div></div>";
				html+="<div class=\"pop-cont\">";
					if(data.empNumber == null){
						html+="<input type=\"hidden\" name=\"empNumber\" value=\"\">";
					}else{
						html+="<input type=\"hidden\" name=\"empNumber\" value="+data.empNumber+">";
					}
					html+="<ul class=\"change-list\">";
						/* html+="<li><span>员工序号</span><div class=\"c-r-box\">";
							if(data.empNumber==null){
								html+="<input type=\"text\" class=\"r-input\" maxLength=\"100\" name=\"empNumber\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\">";
							}else{
								html+="<input type=\"text\" class=\"r-input\" maxLength=\"100\" name=\"empNumber\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\" value="+data.empNumber+">";
							}
						html+="</div></li>"; */
						html+="<li><span>手机号码</span><input type=\"hidden\" name=\"userName\" value="+data.userName+"><input type=\"hidden\" name=\"id\" value="+data.id+">";
						html+="<input type=\"hidden\" name=\"type\" value="+data.type+"><div class=\"c-r-box\">";
						if(data.phone==null){
							html+="<input class=\"r-input\" id=\"update_phone\" type=\"text\" maxLength=\"11\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\" name=\"phone\">";
						}else{
							html+="<input class=\"r-input\" id=\"update_phone\" type=\"text\" maxLength=\"11\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\" name=\"phone\" value="+data.phone+">";
						}
						html+="<div class=\"error\" id=\"phone_error\"></div></div></li>";
						html+="<li><span>员工姓名</span><div class=\"c-r-box\">";
							if(data.name==null){
								html+="<input type=\"text\" class=\"r-input\" id=\"update_name\" maxLength=\"10\" name=\"name\">";
							}else{
								html+="<input type=\"text\" class=\"r-input\" id=\"update_name\" maxLength=\"10\" name=\"name\" value="+data.name+">";
							}
						html+="<div class=\"error\" id=\"update_name_error\"></div></div></li>";
						html+="<li><span>性别</span><div class=\"c-r-box\">";
							if(data.sex=="男"){
								html+="<input type=\"radio\" checked=\"checked\" name=\"sex\" value=\"男\">男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"radio\" name=\"sex\" value=\"女\">女";
							}else if(data.sex=="女"){
								html+="<input type=\"radio\" name=\"sex\" value=\"男\">男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"radio\" checked=\"checked\" name=\"sex\" value=\"女\">女";
							}else{
								html+="<input type=\"radio\" checked=\"checked\" name=\"sex\" value=\"男\">男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"radio\" name=\"sex\" value=\"女\">女";
							}
						html+="</div></li>";
						html+="<li><span>职务</span><div class=\"c-r-box\">";
							if(data.duty==null){
								html+="<input type=\"text\" class=\"r-input\" maxLength=\"10\" name=\"duty\">";
							}else{
								html+="<input type=\"text\" class=\"r-input\" maxLength=\"10\" name=\"duty\" value="+data.duty+">";
							}
						html+="</div></li>";
						html+="<li><span>部门</span><div class=\"c-r-box\">";
						if(data.sectionName==null){
							html+="<input type=\"text\" class=\"r-input\" maxLength=\"10\" name=\"sectionName\">";
						}else{
							html+="<input type=\"text\" class=\"r-input\" maxLength=\"10\" name=\"sectionName\" value="+data.sectionName+">";
						}
						html+="</div></li>";
						html+="<li><span>年龄</span><div class=\"c-r-box\">";
							if(data.age==null){
								html+="<input type=\"text\" class=\"r-input\" name=\"age\" maxLength=\"2\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\">";
							}else{ 
								html+="<input type=\"text\" class=\"r-input\" name=\"age\" maxLength=\"2\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\" value="+data.age+">";
							}
						html+="</div></li>";
						html+="<li><span>工龄</span><div class=\"c-r-box\">";
							if(data.seniority==null){
								html+="<input type=\"text\" class=\"r-input\" maxLength=\"10\" name=\"seniority\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\">";
							}else{
								html+="<input type=\"text\" class=\"r-input\" maxLength=\"10\" name=\"seniority\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\" value="+data.seniority+">";
							}
						html+="</div></li>";
						html+="<li><span>福利级别</span><div class=\"c-r-box\"><select class=\"c-r-select\" id=\"update_welfareLevel\" name=\"welfareLevel\">";
							for(var i = 1;i<=jsWelfareLevel;i++){
								if(i==data.welfareLevel){
									html+="<option selected=\"selected\" value="+i+">"+i+"</option>";
								}else{
									html+="<option value="+i+">"+i+"</option>";
								}
							}
						html+="</select><div class=\"error\" id=\"update_welfareLevel_error\"></div></div></li>";
						if(data.email==null){
							html+="<li><span>邮箱</span><div class=\"c-r-box\"><input class=\"r-input\" id=\"update_email\" type=\"text\" maxLength=\"50\" name=\"email\"><div class=\"error\" id=\"email_error\"></div></div></li>";
						}else{ 
							html+="<li><span>邮箱</span><div class=\"c-r-box\"><input class=\"r-input\" id=\"update_email\" type=\"text\" maxLength=\"50\" name=\"email\" value="+data.email+"><div class=\"error\" id=\"email_error\"></div></div></li>";
						}
					html+="</ul>";
				html+="<div class=\"add-btn-box\"><a id=\"change-ture-btn\" onclick=\"update_But()\" class=\"true-btn\" href=\"#\">确认</a><a class=\"cansel-btn\" id=\"change-cansel-btn\" href=\"#\">取消</a></div>";
				html+="</div>";
				html+="</div>";
				html+="</form>";
			    //将html页面放到  id为update_popup_box的标签中去
			    $("#update_popup_box").append(html);		
			    //背景弹出框弹出
			   	background_open();
			    //修改页面弹出框弹出
				ent_box_open('#change');
				
			}, error : function() {
		    }  
		});
	}
	
	function gotomain(){
		$("#phone_error").fadeOut();
		$("#update_name_error").fadeOut();
		$("#update_welfareLevel_error").fadeOut();
		$("#update_welfareLevel_error1").fadeOut();
		$("#email_error").fadeOut();
	}
	//确认修改员工信息
	function update_But(){
		var pattern = /^1\d{10}$/; 
		//phone_error   $.trim(userName).length==0  ^(1[3|4|5|7|8][0-9])\\d{8}$
		//获取手机号
		var phone = $("#update_phone").val();
		var email = $("#update_email").val();
		var name = $("#update_name").val();
		var welfareLevel = $("#update_welfareLevel").val();
		if($.trim(phone).length==0 && $.trim(email).length==0){
			$("#phone_error").html("手机号和邮箱不能同时为空");
			$("#phone_error").fadeIn();
			$("#email_error").html("手机号和邮箱不能同时为空");
			$("#email_error").fadeIn();
			setTimeout("gotomain()",3000);
		}else if($.trim(phone).length>0&&!pattern.test(phone)){
			$("#phone_error").html("手机号格式错误");
			$("#phone_error").fadeIn();
			setTimeout("gotomain()",3000);
		}else if($.trim(email).length>0&&!checkEmail(email)){
			$("#email_error").html("邮箱格式错误");
			$("#email_error").fadeIn();
			setTimeout("gotomain()",3000);
		}else if($.trim(name).length==0){
			$("#update_name_error").html("名称不能为空");
			$("#update_name_error").fadeIn();
			setTimeout("gotomain()",3000);
		}else if($.trim(welfareLevel).length==0){
			$("#update_welfareLevel_error").html("福利级别不能为空");
			$("#update_welfareLevel_error").fadeIn();
			setTimeout("gotomain()",3000);
		}else{
			$.ajax({
				url : jsBasePath + '/company/emp/update.json',
				type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:$("#update_submit").serialize(),
				success : function(data) {
					if(data>0){
						/* update_status("修改成功"); */
						ent_box_close("#change");
						clear_empty_html("#update_popup_box");
						operation_success_box("修改成功");
					}else if(data==-1){
						ent_box_close("#change");
						clear_empty_html("#update_popup_box");
						operation_success_box("手机号已注册");
					}else if(data==-2){
						ent_box_close("#change");
						clear_empty_html("#update_popup_box");
						operation_success_box("邮箱已注册");
					}else{
						ent_box_close("#change");
						clear_empty_html("#update_popup_box");
						operation_success_box("修改失败");
					}
				}, error : function() {
			    }  
			});
		}
	}
	//批量更改员工的福利等级
	function updateWelfareLevel(){
		//获取输入框中的福利级别  
		var oldWelLevel = $("#welfareLevel_input").val();
		$.ajax({
			url : jsBasePath + '/company/emp/updateEmpWelfareLevel.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:{"welfareLevel":oldWelLevel},
			success : function(data) {
				if(data.success==true){
					$("#updateWelfareLevel").val(data.data);
					$("#welfareLevel_input").val(data.data);
					//填写删除成功与否状态值
					operation_success_box("更改成功！！");
					//关闭弹窗 ">
					ent_box_close('#alter-grade');
					ent_box_close('#alter-setting');
				}else{
					$("#updateWelfareLevel").val(oldWelLevel);
					$("#welfareLevel_input").val(oldWelLevel);
					//填写删除成功与否状态值
					operation_success_box("更改失败！！");
					//关闭弹窗 ">
					ent_box_close('#alter-grade');
					ent_box_close('#alter-setting');
				}
			}, error : function() {
				
		    }  
		});
	}
	//############################################update############################################################
	
	//############################################delete start############################################################
	//删除操作弹出框  1
	function del_popup(i){
		background_open();
		//弹出删除操作提示框
		ent_box_open("#delete-info");
		//为隐藏的输入框添加要删除的信息id
		$("#del_id").val(i);
	}
	
	//删除员工信息操作  2.1
	function del(){
		//获取删除的id
		var del_id = $("#del_id").val();
		$.ajax({
			url : jsBasePath + '/company/emp/delete.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:{"id":del_id},
			success : function(data) {
				if(data>0){
					//填写删除成功与否状态值
					operation_success_box("删除成功！！");
					//关闭弹窗
					ent_box_close('#delete-info');
				}else{
					operation_success_box("删除失败！！");
					//关闭弹窗
					ent_box_close('#delete-info');
				}
			}, error : function() {
				
		    }  
		});
	}
	
	
	//############################################delete end############################################################
	//############################################add begin############################################################
	function gotomain_addEmp(){
			$("#add_phone_error").fadeOut();
			$("#add_welfareLevel_error").fadeOut();
			$("#add_name_error").fadeOut();
			$("#add_empNumber_error").fadeOut();
			$("#add_email_error").fadeOut();
	}
	//############################################add end############################################################
	
	
	//#############################################common###########################################################
	//提交表单并刷新页面
	function refreshHtml(){
		$("#pageNum").val(1);
		$("#emp_filter").submit();
	}
	//校验员工编号
	function checkEmpNumber(number){
		$.ajax({
			url : jsBasePath + '/company/emp/checkEmpNumber.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:{"empNumber":number},
			success : function(data) {
				if(data==true){
					$("#add_empNumber_error").html("该用户编号已存在");
					$("#add_empNumber_error").fadeIn();
					setTimeout("gotomain_addEmp()",3000);
					add_emp_checkEmpNumber_flag = true;
				}else{
					add_emp_checkEmpNumber_flag = false;
				}
			}
		});
	}
	
	function down(){
		location.href=jsBasePath + "/template/batchRegisterEmp.xlsx";
	}

	//校验邮箱
	function checkEmail(email){
		email=$.trim(email);
		if(email.length==0) return true;
		if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
		var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
		return pattern.test(email);
	}
	

	function toUpload() {
		$("#uploadFile").click();
	}
    
	function fileUpload() {
		 var elementIds=["flag"]; //flag为id、name属性名
	    $.ajaxFileUpload({
	        url: jsBasePath+'/upload/pic.json?folder='+jsEntId,
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "uploadFile", // 上传文件的id、name属性名
	        elementIds:elementIds,
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        success: function(data, status){
	        	if(data.success){
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
	        		$(".tx_rt img").attr("src",imgsrc);
	        		$(".tx_rt input").val(imgsrc);    			 
   				
	        	}else{
	        		show(data.msg);
	        	}
	        },
	        error: function(data, status, e){ 
	            alert(e);
	        }
	    });
	}
	
	function removeImg() {
		$(".tx_rt img").attr("src",jsStaticResources+"images/hp_tx.png");
		$(".tx_rt input").val("");
	}
	
	function saveEmpSet(){
		
		var emailPostfix1="";
		var emailPostfix2="";
		var emailPostfix3="";
		var s=$(".suffix_con input");
		     	 
		emailPostfix1=$.trim($(s[0]).val());

		// 输入检查
		// 1.不能输入@		
		if(!checkPostfix(emailPostfix1)){
			showInputErr(s,0,"格式不正确！请重新输入");			   
			return false;
			
		}

		// 2.不能有通用		
		if(isInArray(emailPostfix1)) {
			showInputErr(s,0,"请输入正确的企业邮箱");
		    return false;
		} 	    
	      	    
	    if(s.length>1){
		    emailPostfix2=$.trim($(s[1]).val());	
			// 输入检查		
			// 1.不能输入@		
			if(!checkPostfix(emailPostfix2)){
				showInputErr(s,1,"格式不正确！请重新输入");				   
				return false;
			}

			// 2.不能有通用		
			if(isInArray(emailPostfix2)) {
				showInputErr(s,1,"请输入正确的企业邮箱");			    
			    return false;
			}
		};
	      
	      
		if(s.length>2){
			emailPostfix3=$.trim($(s[2]).val());

			// 输入检查
			// 1.不能输入@		
			if(!checkPostfix(emailPostfix3)){
				showInputErr(s,2,"格式不正确！请重新输入");				   
				return false;
			}

			// 2.不能有通用		
			if(isInArray(emailPostfix3)) {
				showInputErr(s,2,"请输入正确的企业邮箱");    
			    return false;
			}
		}
		
		// 3.不能重复
		if(emailPostfix1 == emailPostfix2 && emailPostfix2!=""){
			showInputErr(s,1,"不能重复输入");  
			return false;
		}else if(emailPostfix2 == emailPostfix3 && emailPostfix3!=""){
			showInputErr(s,2,"不能重复输入");  
			return false;
		}else if(emailPostfix1 == emailPostfix3 && emailPostfix3!=""){
			showInputErr(s,2,"不能重复输入");  
			return false;
		};
		
		// 非法终止
		$.ajax({
			url : jsBasePath + '/company/emp/updateEnterpriseSet.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:{"emailPostfix1":emailPostfix1,"emailPostfix2":emailPostfix2,"emailPostfix3":emailPostfix3,"empDefultAvatar":$(".tx_rt input").val(),"canSearch":"0"},
			success : function(rst) {
				if (rst.success) {
					alert("保存成功");
					background_close();
					ent_box_close('#set_pb');
					
				} else {
					showMsg(rst.msg);
				}
			}, error : function() {
				
				
				
		    }  
		});
		$(".hint").hide();
	}
	
	function showInputErr(s,i,msg) {
		$(".hint").show();
		$(".hint").html(msg);
		$(s[i]).css("border-color","#ff4040");
		$(s[i]).focus(function(){
				$(s[i]).css("border-color","#ccc");
				hideMsg();
		});
		
		
	}
	
	function showMsg(msg) {
		$(".hint").show();
		$(".hint").html(msg);
		setTimeout("hideMsg", 1500);
		
	}
	
	function hideMsg() {
		$(".hint").hide();
	}
	function checkPostfix(postfix) {
		if(postfix=="") return true;
		
		return postfix.match(/^([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/);
	}
	
	function isInArray(emailPostfix){
		var str=",gmail.com,yahoo.com,msn.com,hotmail.com,aol.com,ask.com,live.com,qq.com,0355.net,163.com,163.net,263.net,3721.net,yeah.net,126.com,yahoo.com,sohu.com,sina.com,baidu.com";
		if(str.indexOf(","+emailPostfix+",") != "-1"){
			return true;
		}else{
			//alert("不存在");
			return false;
		}
	}