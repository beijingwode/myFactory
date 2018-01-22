   	/**
   	*submit
   	*/
   	function subForm(){
   		if(validatorForm()){
			/* $("#sub_button0").addClass("heycolor").removeAttr("onclick"); */

			$.ajax({
				url : jsBasePath + '/company/enterprise/saveOrUpdate.json',
				type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:$("#sub_form").serialize(),
				success : function(data) {
					if(data>0){
						showInfoBox("修改成功");
					}else{
						showInfoBox("修改失败");
						$("#sub_button0").removeClass("heycolor").attr("onclick","subForm();");
					}
				}, error : function() {
					showInfoBox("修改失败");
					$("#sub_button0").removeClass("heycolor").attr("onclick","subForm();");
			    }  
			});
   		}
   	}
   	
   	//非空验证
   	function validatorForm(){
   		var $list = $("[ismust=1]");
   		var ret = 0;
   		$list.each(function(i,val){
   			if($($list[i]).attr("typename")=='select'){
   				if($($list[i]).val()=='-1'){
   					$($list[i]).addClass("bctxt");
   					ret++;
   				}
   			}else if($($list[i]).attr("typename")=='input'){
   				if($.trim($($list[i]).val())==''){
   					$($list[i]).addClass("bctxt");
   					ret++;
   				}
   			}else if($($list[i]).attr("typename")=='checkboxdiv'){
   				if($($list[i]).find("input:checked").length==0){
   					$($list[i]).addClass("bordercolor");
   					ret++;
   				}
   			}
   		});
   		
   		if(ret>0){
   			showInfoBox("请补充完整红色区域后，再次提交！");
   		}else{
   		//营业额
   			var turnover = $("#update_turnover").val();
   			//企业人数
   			var peopleNumber = $("#update_peopleNumber").val();
   			//企业名称
   			var name = $("#update_name").val();
   			//截取营业额第一个数字
   			var turnover_frist = turnover.substring(0,1);
   			//截取企业人数第一个数字
   			var peopleNumber_frist = peopleNumber.substring(0,1);
   			if($.trim(name)==""){
				showInfoBox("企业名称不能为空");
   				return false;
   			}else if(turnover_frist==0&&$.trim(turnover_frist).length!=0){
				showInfoBox("营业额开头不能为0");
   				return false;
   			}else if(peopleNumber_frist==0&&$.trim(peopleNumber_frist).length!=0){
				showInfoBox("企业人数开头不能为0");
   				return false;
   			}
   			
   			return true;
   		}
   	}