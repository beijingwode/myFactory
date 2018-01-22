$(document).ready(function(){
	//控制菜单背景
	selectedHeaderLi("permissions_header");
});
	//删除////////////////////////////////////////////////////////////
	//确认删除
	function del_role(roleId,userName){
		if(roleId==null)
			return ;
		
		if(userName!=""){
			$("#deleteBoxInfo").text("该角色下有用户,您确定删除？？");
		}else{
			$("#deleteBoxInfo").text("该角色下没有用户,您可以删除！！");
		}
		$('.popup_bg').show();
		$('#delete_pop').show();
		$('.add-close-icon').click(function(){
			$('.popup_bg').hide();
			$('#delete_pop').hide();	
		});
		$('.cansel-btn').click(function(){
			$('.popup_bg').hide();
			$('#delete_pop').hide();	
		})
		
		
		$('#deleteRoleSub').click(function(){
		
			$.ajax({
				url : jsBasePath+'/role/delete.json',
				type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    data:{"id":roleId},
				success : function(data) {
					if(data.success){
						alert("删除成功");
						location.reload()
					}else{
						alert("删除失败");
						location.reload()
					}
					$('.popup_bg').hide();
					$('#delete_pop').hide();
				}, error : function() {
			    }  
			});	
		})
	}
	//////////////////////////////////////////////////////////////

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
	//修改角色
	function update_role(i){
		$.ajax({
			url : jsBasePath+'/getByRoleId.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:{id:i},
			success : function(data) {
				if(data.success){
					
				}
				
				$('.popup_bg').show();
				$('#alter_pop').show();
			}, error : function() {
		    }  
		});
	}
