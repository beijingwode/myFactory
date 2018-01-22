$(document).ready(function() {
	$(".info_one .radio").click(function(){
		$(".info_one .radio").removeAttr("checked");
		$(this).attr("checked","checked");
		$("#gender").val($(this).val());
	});
	getArea(1,"");
	$("#province").change(function(){
		getArea(2,$('#province option:selected').val());
	});
	$("#city").change(function(){
		getArea(3,$('#city option:selected').val());
	});
	$(".info_submitbtn").click(function(){
		wode.showBox("数据提交中","数据提交中",{"hideBtn":true});
		$.ajax({
			dataType: 'json',
			url:"/user/modifyuser",
			data : {
				"avatar" : $("#avatarValue").val(),
				"nickName" : $("#nickName").val(),
				"gender" : $("#gender").val(),
				"province" : $('#province option:selected').val(),
				"city" : $('#city option:selected').val(),
				"district" : $('#district option:selected').val(),
				"birthDay" : $('#birthDay').val()
			},
			success: function(data){
				if(data.success){
					wode.showBox("修改结果","修改成功",{"icon":"success"});
				}else{
					wode.showBox("修改结果",data.msg);
				}
				$("#boxCancel").hide();
				$("#boxCheck").css("margin-right","0px");
				
				$("#boxCheck").click(function(){
					location.href="/member/personalInformation";
				});
			}
		});
	});
});

/**
 * 根据code获取地址信息
 */
function getArea(grade,areaCode) {
	var data = window.China;
	if(grade==2)
		$("#city>option").remove();
	if(grade==3)
		$("#district>option").remove();
	$.each(data, function (key, value) {
		var name = value.name;
        var level = value.grade;
        var code = value.code;
        var parentId = value.father;
        if(grade!=level){
            return true;
        }
        if(areaCode!=null){
	        if(grade==2 && parentId!=areaCode){
	        	return true;
	        }
	        if(grade==3 && parentId!=areaCode){
	        	return true;
	        }
		}
        switch (level) {
            case 1 :
            	if(key==$("#provinceNo").val())
		    		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
		    	else
		    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
                $("#province").append(option);
				getArea(2,$('#province>option:selected').val());
                break;
            case 2 :
            	if(key==$("#cityNo").val())
            		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
		    	else
		    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
            	$("#city").append(option);
				getArea(3,$('#city option:selected').val());
                break;
            case 3 :
            	if(key==$("#districtNo").val())
            		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
		    	else
		    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
            	$("#district").append(option);
                break;
        }
    });
}

/**
 * 图片选择
 */
function fileSelect() {
    $("#avatar").click(); 
}
/**
 * 头像保存
 * @param id
 */
function ajaxFileUpload() {
    var elementIds=["avatar"]; //flag为id、name属性名
    $.ajaxFileUpload({
        url: '/upload/pic?keepFilename=avatar',
        type: 'post',
        secureuri: true, //一般设置为false
        fileElementId: "avatar", // 上传文件的id、name属性名
        dataType: 'json', //返回值类型，一般设置为json、application/json
        elementIds: elementIds, //传递参数到服务器
        success: function(data, status){
        	if(data.success){
        		var imgPath = "http://"+data.data[0];
        		$("#avatarValue").val(imgPath);
				$("#avatarShow").attr('src',imgPath);
        	}else
        		wode.showBox("错误",data.msg);
        },
        error: function(data, status, e){ 
        	wode.showBox("错误",e);
        }
    });
}