var globalMapDate;
$().ready(function() {
	wode.checkLogin();
	//修改代收点弹窗
/*	$(document).on("click","#updata_expressCollectingPoints_box",function(){*/
		$("#updata_expressCollectingPoints_box").live("click",function(){
		$("#add_address").val("");
		//加载省份信息 
		express_getArea($("#express_province"),null);//加载省
		$("#express_city").html("<option value=0>请选择...</option>");
		$("#express_area").html("<option value=0>请选择...</option>");
		//加载省份信息 
		/*//经度
		var lng = $("#express_lng").val();
		//纬度
		var lat = $("#express_lat").val();*/
		//收货地址
		var address = $("#shipping_address").val();
		$("#update_shipping_name").val($("#shipping_name").val());
		$("#update_shipping_phone").val($("#shipping_phone").val());
		$(".box").show();
		$("#daishoudian").show();
		//地图左侧ul代收点列表
		box_left_map(address);
		
	});
		
	$("#express_province").change(function(){
		express_getArea($("#express_city"),$("#express_province").val());//加载市
	});
	$("#express_city").change(function(){
		express_getArea($("#express_area"),$("#express_city").val());//加载市
	});
	
	//修改代收点 搜地址按钮事件
	$("#search_address").click(function(){
		var province = $("#express_province").find("option:selected").text();
		var city = $("#express_city").find("option:selected").text();
		var area = $("#express_area").find("option:selected").text();
		var address = $.trim($("#add_address").val());
		var defaultName = "请选择...";
		//为空或者下拉列表没有选择。不可进行查询
		if(defaultName==province||defaultName==city||defaultName==area||""==address){
			$(".pop-error").fadeIn();
			setTimeout("gotomain()",3000);
			return ;
		}else{
			
			box_left_map(province+city+area+address);
			
		}
	});
	//修改代收点 搜店名按钮事件
	$("#search_shopName").click(function(){
		//获取省市县
		var province = $("#express_province").find("option:selected").text();
		var city = $("#express_city").find("option:selected").text();
		var area = $("#express_area").find("option:selected").text();
		//获取地址
		var address = $.trim($("#add_address").val());
		var defaultName = "请选择...";
		//为空或者下拉列表没有选择。不可进行查询
		if(defaultName==province||defaultName==city||defaultName==area||""==address){
			return ;
		}else{
			$.ajax({
				url : "/express/collectingPoints",
				type : "POST",
				data : {"address":province+city+area+address,"status":false},
				dataType : "json",
				success:function(data){
					var fristLng;
					var fristLat;
					//清空地图左侧的列表
					$("#map_left_ul").empty();
					var d = data.data;
					var html="";
					var arr = new Array();
					if(d==null){
						box_right_map("","","");
					}else{
						$.each(d,function(i){
							//排除地址不相同的情况
							if(address==d[i].name){
								var array = new Array();
								array.push(d[i].lng);
								array.push(d[i].lat);
								array.push("地址:"+d[i].address+"<br/>电话:"+d[i].phone);
								arr.push(array);
								//默认第一个代收点是选中状态
								if(i==1){
									fristLng= d[i].lng;
									fristLat= d[i].lat;
									html+="<li><input class=\"dai-radio\" onclick=\"collectingPoint_radio("+d[i].lng+","+d[i].lat+");\" checked=\"checked\" type=\"radio\" name=\"danxuan\">";
								}else{
									html+="<li><input class=\"dai-radio\" onclick=\"collectingPoint_radio("+d[i].lng+","+d[i].lat+");\" type=\"radio\" name=\"danxuan\">";
								}
								html+="<input id=\"lng\" type=\"hidden\" value="+d[i].lng+"><input id=\"lat\" type=\"hidden\" value="+d[i].lat+"><input type=\"hidden\" value="+d[i].id+"><input type=\"hidden\" value="+d[i].address+"电话:("+d[i].phone+")"+">";
								html+="<div class=\"dai-info\">";
								html+="<strong>"+d[i].name+"</strong>";
								html+="<p>地址:"+d[i].address+"<br/>电话:"+d[i].phone+"</p>";
								html+="</div>";
								html+="</li>";
							}
						});
						//将地图标注的信息赋值给全局变量
						globalMapDate = arr;
						box_right_map(fristLng,fristLat,arr);
					}
					
					$("#map_left_ul").append(html);
				}
			});
		}
	});
	//检查手机号输入是否正确
	$("#update_shipping_phone").live("keyup",function(){
		var phone = $(this).val();
		if($.trim(phone).length==11){
			//如果手机号和正则不匹配
			if(!phone.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/)){
				$("#update_phone_error").html("<i class=\"error-icon\">ⓧ</i>手机号错误！");
				$("#update_phone_error").fadeIn();
				setTimeout("gotomain()",3000);
			}
		}
	});
	
});
//根据code获取区域
function express_getArea($select,code) {
	$.ajax({
		url : "getArea",
		type : "POST",
		data : {
			"code" : code
		},
		dataType : "json",
		cache : false,
		beforeSend : function() {
			// $submit.prop("disabled", true);
		},
		success : function(data) {
			var option = "<option value=0>请选择...</option>";
			for (var i = 0; i < data.length; i++) {
			    var item = data[i];
			    option += "<option value=\""+item["code"]+"\">"+item["name"]+"</option>\n";
			}
			$select.html(option);
		},
		complete : function() {
			// $submit.prop("disabled", false);
		}
	});
}

//修改好代收点信息，进行提交
function submit(){
	//收货人的名称和电话号
	var name = $("#update_shipping_name").val();
	var phone = $("#update_shipping_phone").val();
	//代收点经纬度 id 和收货地址  collectingPoint
	var lng = $("input[name=danxuan]:checked").next().val();
	var lat = $("input[name=danxuan]:checked").next().next().val();
	var address = $("input[name=danxuan]:checked").next().next().next().val();
	var cpPhone = $("input[name=danxuan]:checked").next().next().next().next().val();
	var check = $("input[name=danxuan]:checked").next().next().next().next().next().val();
	if(name==""){
		$("#update_shipping_error").fadeIn();
		setTimeout("gotomain()",3000);
		return ;
	}else if(phone==""){
		$("#update_phone_error").html("<i class=\"error-icon\">ⓧ</i>不能为空！");
		$("#update_phone_error").fadeIn();
		setTimeout("gotomain()",3000);
		return ;
	}else if(!phone.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/)){
		$("#update_phone_error").html("<i class=\"error-icon\">ⓧ</i>手机号错误！");
		$("#update_phone_error").fadeIn();
		setTimeout("gotomain()",3000);
	}else if(check==undefined){
		$(".dai-agree-error").fadeIn();
		setTimeout("gotomain()",3000);
		return ;
	}else{
		//收货人的名称
		$("#shipping_name").val(name);
		//收货人的手机
		$("#shipping_phone").val(phone);
		$("#shipping_address").val(address);
		//收货人的代收地址
		$("#collectingPointsVal").html(address+"&nbsp;&nbsp;(电话:"+cpPhone+")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+name+" 收)&nbsp;"+phone);
		//代收点的经纬度加id
		$("#express_lng").val(lng);
		$("#express_lat").val(lat);
		
		//关闭弹窗
		$("#daishoudian").hide();
		$(".box").hide();
	}
	
}
//错误信息隐藏
function gotomain(){
	$(".pop-error").fadeOut();
	$("#update_shipping_error").fadeOut();
	$("#update_phone_error").fadeOut();
	$(".dai-agree-error").fadeOut();
}
//修改代收点页面 代收点单选按钮
function collectingPoint_radio(lng,lat){
	
	box_right_map(lng,lat,globalMapDate);
}


//地图左侧ul代收点列表
function box_left_map(address,status){
	$.ajax({
		url : "/express/collectingPoints",
		type : "POST",
		data : {"address":address,"status":false},
		dataType : "json",
		success:function(data){
			var fristLng;
			var fristLat;
			//清空地图左侧的列表
			$("#map_left_ul").empty();
			var d = data.data;
			var html="";
			var arr = new Array();
			if(d==null){
				box_right_map("","","");
			}else{
				$.each(d,function(i){
					var array = new Array();
					array.push(d[i].lng);
					array.push(d[i].lat);
					array.push("地址:"+d[i].address+"<br/>电话:"+d[i].phone);
					arr.push(array);
					if(i==0){
						fristLng= d[i].lng;
						fristLat= d[i].lat;
						html+="<li><input class=\"dai-radio\" onclick=\"collectingPoint_radio("+d[i].lng+","+d[i].lat+");\" checked=\"checked\" type=\"radio\" name=\"danxuan\">";
					}else{
						html+="<li><input class=\"dai-radio\" onclick=\"collectingPoint_radio("+d[i].lng+","+d[i].lat+");\" type=\"radio\" name=\"danxuan\">";
					}
					html+="<input id=\"lng\" type=\"hidden\" value="+d[i].lng+">";
					html+="<input id=\"lat\" type=\"hidden\" value="+d[i].lat+">";
					html+="<input type=\"hidden\" value="+d[i].address+">";
					html+="<input type=\"hidden\" value="+d[i].phone+">";
					html+="<div class=\"dai-info\">";
					html+="<strong>"+d[i].name+"</strong>";
					html+="<p>地址:"+d[i].address+"<br/>电话:"+d[i].phone+"</p>";
					html+="</div>";
					html+="</li>";
				});
				//将地图标注的信息赋值给全局变量
				globalMapDate = arr;
				box_right_map(fristLng,fristLat,arr);
			}
			
			$("#map_left_ul").append(html);
		}
	});
}


//弹窗右侧的地图         中心点经度，中心店纬度，标注点的经纬度+地址
function box_right_map(cenLng,cenLat,data){
	// 百度地图API功能	
	map = new BMap.Map("baidu_map");
	map.centerAndZoom(new BMap.Point(cenLng,cenLat), 15);
	var data_info = data;
	var opts = {
				width : 250,     // 信息窗口宽度
				height: 80,     // 信息窗口高度
				title : "信息窗口" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
	for(var i=0;i<data_info.length;i++){
		var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
		var content = data_info[i][2];
		map.addOverlay(marker);               // 将标注添加到地图中
		addClickHandler(content,marker);
	}
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
}