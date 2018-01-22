$(document).ready(function() {
	getPrizeMsg();
//	getDrawPrize();
	$("ul#prizeMsg").on("click","li",function(){   
		$(".jiangji_box").hide();
		$(".jiangji").html($(this).text());
		$("#gradeId").val($(this).val());
		var	html = $(this).text()+"："+map.get($(this).val())+"(共"+map.get("number_"+$(this).val())+"名)";
		$(".jp_name").html(html);
		$(".zjr").html("");
	});
});

	function changeStyle(){
		var obj = $("#gradeId").val();
		var ul=document.getElementById("prizeMsg").getElementsByTagName("li");
        for(var i=0;i<ul.length;i++){
           var cityid=ul[i].getAttribute("value");
           if(cityid == obj){
	           ul[i].className = "thisOne";
           }else{
        	   ul[i].className = "";
           }
        }
        
	}
	var map = new getMap();
	function getPrizeMsg(){
		$.ajax({
			url : jsBasePath+'acticity/getPrizeMsg?acticityId='+acticityId,
			type : "POST",
			dataType: "json",  //返回json格式的数据  
			async: false,
		    cache:false,
			success : function(data) {
				var result = data.data;
				var html = "";
				for (var i = 0; i < result.length; i++) {
					map.put(result[i].prizeGrade,result[i].prizeName);
					html += "<li value="+result[i].prizeGrade+">"+result[i].gradeName+"</li>";
					map.put("objId_"+result[i].prizeGrade,result[i].id);
					map.put("gradeN_"+result[i].prizeGrade,result[i].gradeName);
					map.put("number_"+result[i].prizeGrade,result[i].number);
				}
				$("#prizeMsg").html(html);
			}
		});
	}
	
	function getDrawPrize(){
			//获取抽奖号码开始抽奖
			$.ajax({
				url : jsBasePath+'acticity/getPrizePhoneMsg?acticityId='+acticityId,
				type : "POST",
				dataType: "json",  //返回json格式的数据  
				async: false,
			    cache:false,
				success : function(data) {
					var result = data.data;
					var arr = new Array();
					for (var i = 0; i < result.length; i++) {
						var html = "";
						var arr1 = new Array();
						var p1 = result[i].phone.substr(0,1);
						var p2 = result[i].phone.substr(1,1);
						var p3 = result[i].phone.substr(2,1);
						var p4 = result[i].phone.substr(result[i].phone.length-4,1);
						var p5 = result[i].phone.substr(result[i].phone.length-3,1);
						var p6 = result[i].phone.substr(result[i].phone.length-2,1);
						var p7 = result[i].phone.substr(result[i].phone.length-1,1);
						var prr1 = "number_"+result[i].userId;
						var prr2 = "name_"+result[i].userId;
						var prr3 = "obj_"+result[i].userId;
						var prr4 = "phone_"+result[i].userId;
						arr1.push(p1);
						arr1.push(p2);
						arr1.push(p3);
						arr1.push(p4);
						arr1.push(p5);
						arr1.push(p6);
						arr1.push(p7);
						for (var j = 0; j < arr1.length; j++) {
							if(j == 3){
								html += "<li class='hz' value='0'>我</li>";
								html += "<li class='hz' value='0'>的</li>";
								html += "<li class='hz' value='0'>福</li>";
								html += "<li class='hz' value='0'>利</li>";
							}
							html += "<li value="+result[i].userId+">"+arr1[j]+"</li>";
						}
						arr.push(html);
						localStorage.setItem(prr1,result[i].luckyNumber);
						localStorage.setItem(prr2,result[i].name);
						localStorage.setItem(prr3,result[i].id);
						localStorage.setItem(prr4,result[i].phone);
					}
					localStorage.setItem("phoneData",arr.join(","))
				}
			});
	}
	
	var	timer=null;//定时器
//	var flag=1;//阻止多次回车
	var data = [];
//	var	temp = [];
//	var	arr = [];
//	var	isEnter = true;
//	window.onload=function(){			
//				// 键盘事件
//			      document.onkeyup=function(event){
//			        event = event || window.event;
//			        // 回车键的code值：13
//			        if(event.keyCode==13){
//			          if(flag==0){
//			            	/* drawPrize(); */
//			            flag=1;
//			           }else{
//			              stopFun();
//			              flag=1;
//			              var phone =$("#phoneMsg").html();
//			        	  var ul=document.getElementById("phoneMsg").getElementsByTagName("li");
//			        	  var prr="";
//			              for(var i=0;i<ul.length;i++){
//			                 var cityid=ul[i].getAttribute("value");
//			                 prr += cityid;
//			              }
//			              var prrEnd = prr.substr(prr.length-4,4);
//			              var prrStart = prr.substr(0,3);
//			              var html = "<em>No.</em>"+localStorage.getItem("number_"+prrEnd)+"&nbsp;&nbsp;&nbsp;"+localStorage.getItem("name_"+prrEnd)+"&nbsp;&nbsp;"+prrStart+"****"+prrEnd+"";
//			              $(".zjr").html(html);
//			        	  if(!phone){
//			        		  var index = Math.floor(Math.random()*(data.length));
//			        		  phone = data[index];
//			        	  }
//			        	  //添加获奖信息记录
//			        	  $.ajax({
//			        			url : jsBasePath+'acticity/addPrizeRecord?acticityId='+acticityId+"&userId="+localStorage.getItem("obj_"+prrEnd)+"&prizeGrade="+$("#gradeId").val()+"&prizeId="+map.get("objId_"+$("#gradeId").val()),
//			        			type : "POST",
//			        			dataType: "json",  //返回json格式的数据  
//			        			async: false,
//			        		    cache:false,
//			        			success : function(data) {
//			        			}
//			        		});
//			            	  for(var i=0;i<data.length;i++){
//			            		  if(data[i].trim() == phone){
//			            			  data.splice(i,1);
//			            		  }
//			            	  }
//			            	  
//			              }
//			              localStorage.setItem("phoneData",data);
//			              
//			              var winner = localStorage.getItem("winner");
//			              if(winner == null || winner==""){
//			            	  winner = winnerInfo;
//			              }else{
//			            	  winner += "@"+winnerInfo;
//			              }
//			              localStorage.setItem("winner",winner);
//			         }
//			 }
//		}
//	}
	function stopFunEx(){
		setTimeout('stopFun()', 600);
	}
  function stopFun(){
	  $(".cj_btn").show();
	  $(".ting_btn").hide();
	  clearInterval(timer);
	  addPrizeRecord();
  }
  
  function addPrizeRecord(){
	  var phone =$("#phoneMsg").html();
	  var ul=document.getElementById("phoneMsg").getElementsByTagName("li");
      var userid=ul[0].getAttribute("value");
      var prr =   localStorage.getItem("phone_"+userid)
      var prrEnd = prr.substr(prr.length-4,4);
      var prrStart = prr.substr(0,3);
      var html = "<em>No.</em>"+localStorage.getItem("number_"+userid)+"&nbsp;&nbsp;&nbsp;"+localStorage.getItem("name_"+userid)+"&nbsp;&nbsp;"+prrStart+"****"+prrEnd+"";
      $(".zjr").html(html);
	  if(!phone){
		  var index = Math.floor(Math.random()*(data.length));
		  phone = data[index];
	  }
	  //添加获奖信息记录
	  $.ajax({
			url : jsBasePath+'acticity/addPrizeRecord?acticityId='+acticityId+"&userId="+localStorage.getItem("obj_"+userid)+"&prizeGrade="+$("#gradeId").val()+"&prizeId="+map.get("objId_"+$("#gradeId").val())+"&gradeName="+map.get("gradeN_"+$("#gradeId").val()),
			type : "POST",
			dataType: "json",  //返回json格式的数据  
			async: false,
			success : function(data) {
			}
		});
  }
  function begin(){
	  $(".cj_btn").hide();
	  $(".ting_btn").show();
	  localStorage.removeItem("phoneData");
	  getDrawPrize();
	  data = localStorage.getItem("phoneData") != null?localStorage.getItem("phoneData").split(","):[];
	  drawPrize();
  }
	function drawPrize(){
		if($("#gradeId").val() == null || $("#gradeId").val() == ""){
			alert("请选择奖品信息")
			$(".cj_btn").show();
			$(".ting_btn").hide();
			return;
		}
		var len = data.length;
		if(len<=1){
			alert("抽奖结束！无用户信息");
			$(".cj_btn").show();
			$(".ting_btn").hide();
			return;
		}
		clearInterval(timer);
		timer=setInterval(function(){
			var index = Math.floor(Math.random()*len);
			$("#phoneMsg").html(data[index]);
        },60);
	}
	
	var map1 = new getMap();
	function prizeUserMsg(){
		 $.ajax({
   			url : jsBasePath+'acticity/getUserPrizeRecordMsg?acticityId='+acticityId,
   			type : "POST",
   			dataType: "json",  //返回json格式的数据  
   			async: false,
   		    cache:false,
   			success : function(data) {
   				var result = data.data;
   				if(result.length > 0){
   					$(".mask").show();
   					$("#prizeDiv").show();
   					var html = "";
   					for (var i = 0; i < result.length; i++) {
   						html += '<div class="tit">'+result[i].gradeName+'</div>';
   						html += '<div class="tit_con" id='+result[i].prizeGrade+'>';
   						html += '<dl class="dl1">';
   						html += '<dt><span class="span1">幸运数字</span><span class="span2">姓名</span><span class="span3">手机号码</span></dt>';
   						var list = result[i].list;
   						var flag = true;
   						for (var j = 0; j < list.length; j+=2) {
   							if(flag == true){
   								flag = false;
   								html += '<dd class="dd1"><span class="span1">No.'+list[j].luckyNumber+'</span><span class="span2">'+list[j].name+'</span><span class="span3">'+list[j].phone.substr(0,3)+'****'+list[j].phone.substr(list[j].phone.length-4,4)+'</span></dd>';
   							}else{
   								flag = true;
   								html += '<dd class="dd2"><span class="span1">No.'+list[j].luckyNumber+'</span><span class="span2">'+list[j].name+'</span><span class="span3">'+list[j].phone.substr(0,3)+'****'+list[j].phone.substr(list[j].phone.length-4,4)+'</span></dd>';
   							}
						}
   						html += '</dl>';
   						html += '<dl class="dl2">';
   						html += '<dt><span class="span1">幸运数字</span><span class="span2">姓名</span><span class="span3">手机号码</span></dt>';
   						var flag2 = true;
   						for (var m = 1; m < list.length; m+=2) {
   							if(flag2 == true){
   								flag2 = false;
   								html += '<dd class="dd1"><span class="span1">No.'+list[m].luckyNumber+'</span><span class="span2">'+list[m].name+'</span><span class="span3">'+list[m].phone.substr(0,3)+'****'+list[m].phone.substr(list[m].phone.length-4,4)+'</span></dd>';
   							}else{
   								flag2 = true;
   								html += '<dd class="dd2"><span class="span1">No.'+list[m].luckyNumber+'</span><span class="span2">'+list[m].name+'</span><span class="span3">'+list[m].phone.substr(0,3)+'****'+list[m].phone.substr(list[m].phone.length-4,4)+'</span></dd>';
   							}
						}
   						html += '</dl>';
   						html += '</div>';
   					}
   					$(".hjmd_scroll").html(html);
   					
   				}else{
   					alert("暂无获奖记录")
   				}
   			}
   		});
	}
	
	function signMsg(){
		$.ajax({
   			url : jsBasePath+'acticity/signAllMsg?acticityId='+acticityId,
   			type : "POST",
   			dataType: "json",  //返回json格式的数据  
   			async: false,
   		    cache:false,
   			success : function(data) {
   				var result = data.data;
   				if(result.length > 0){
   					$(".mask").show();
   					$("#signDiv").show();
   					var html = "";
   						html += '<dl class="dl3">';
   						html += '<dt><span class="span1">姓名</span><span class="span2">手机号</span><span class="span3">签到时间</span></dt>';
   						var list = result;
   						var flag = true;
   						for (var i = 0; i < list.length; i++) {
   							if(flag == true){
   								flag = false;
   								html += '<dd class="dd1"><span class="span1">'+list[i].name+'</span><span class="span2">'+list[i].phone.substr(0,3)+'****'+list[i].phone.substr(list[i].phone.length-4,4)+'</span><span class="span3">'+new Date(list[i].createDate).format("yyyy-MM-dd hh:mm:ss")+'</span></dd>';
   							}else{
   								flag = true;
   								html += '<dd class="dd2"><span class="span1">'+list[i].name+'</span><span class="span2">'+list[i].phone.substr(0,3)+'****'+list[i].phone.substr(list[i].phone.length-4,4)+'</span><span class="span3">'+new Date(list[i].createDate).format("yyyy-MM-dd hh:mm:ss")+'</span></dd>';
   							}
						}
   						html += '</dl>';
//   						html += '<dl class="dl2">';
//   						html += '<dt><span class="span1">姓名</span><span class="span2">手机号</span><span class="span3">签到时间</span></dt>';
//   						var flag2 = true;
//   						for (var j = 1; j < list.length; j+=2) {
//   							if(flag2 == true){
//   								flag2 = false;
//   								html += '<dd class="dd1"><span class="span1">'+list[j].name+'</span><span class="span2">'+list[j].phone.substr(0,3)+'****'+list[j].phone.substr(list[j].phone.length-4,4)+'</span><span class="span3">'+new Date(list[j].createDate).format("yyyy-MM-dd hh:mm:ss")+'</span></dd>';
//   							}else{
//   								flag2 = true;
//   								html += '<dd class="dd2"><span class="span1">'+list[j].name+'</span><span class="span2">'+list[j].phone.substr(0,3)+'****'+list[j].phone.substr(list[j].phone.length-4,4)+'</span><span class="span3">'+new Date(list[j].createDate).format("yyyy-MM-dd hh:mm:ss")+'</span></dd>';
//   							}
//						}
//   						html += '</dl>';
   					$(".tit_con").html(html);
   					
   				}else{
   					alert("暂无签到信息")
   				}
   			}
   		});
	}
	
	Date.prototype.format = function(fmt) { 
	     var o = { 
	        "M+" : this.getMonth()+1,                 //月份 
	        "d+" : this.getDate(),                    //日 
	        "h+" : this.getHours(),                   //小时 
	        "m+" : this.getMinutes(),                 //分 
	        "s+" : this.getSeconds(),                 //秒 
	        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	        "S"  : this.getMilliseconds()             //毫秒 
	    }; 
	    if(/(y+)/.test(fmt)) {
	            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	    }
	     for(var k in o) {
	        if(new RegExp("("+ k +")").test(fmt)){
	             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	         }
	     }
	    return fmt; 
	}  
	
	function exportMsg(){
		window.location.href=jsBasePath+'acticity/export?acticityId='+acticityId;
	}
	
	function getMap(){  
        /** 存放键的数组(遍历用到) */      
        var keys = new Array();       
        var map_ = new Object();  
        var data = new Object();     
        map_.put = function(key, value) {   
            if(data[key] == null){       
                keys.push(key);      
            }  
            data[key] = value;  
            map_[key] = value;    
        };    
        map_.keys = function(key, value) {   
            if(map_[key] == null){       
                keys.push(key);      
            }  
        };   
        map_.get = function(key) {    
            return map_[key];    
        };    
        map_.remove = function(key) {    
            delete map_[key];    
        };    
        map_.each = function(fn){       
            if(typeof fn != 'function'){       
                return;       
            }       
            var len = keys.length;       
            for(var i=0;i<len;i++){       
                var k = keys[i];       
                fn(k,data[k],i);       
            }       
        };       
        map_.keyset = function() {    
            var ret = "";    
            for(var p in map_) {    
                if(typeof p == 'string' && p.substring(p.length-1) == "_") {   
                    ret += ",";    
                    ret += p.substring(0,p.length-1);    
                }    
            }    
            if(ret == "") {  
                return ret.split(",");   
            }else{    
                return ret.substring(1).split(",");   
            }    
        };    
        return map_;    
     };  
	