Date.prototype.format = function(format) 
{
  var o = {
    "M+" : this.getMonth()+1, //month 
    "d+" : this.getDate(),    //day 
    "h+" : this.getHours(),   //hour 
    "m+" : this.getMinutes(), //minute 
    "s+" : this.getSeconds(), //second 
    "S" : this.getMilliseconds() //millisecond 
  }
  
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
    (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o)if(new RegExp("("+ k +")").test(format)) 
    format = format.replace(RegExp.$1, 
      RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length)); 
  return format; 
}

 String.prototype.endWith=function(str){
  if(str==null||str==""||this.length==0||str.length>this.length)
     return false;
  if(this.substring(this.length-str.length)==str){
     return true;
  }else{
     return false;
 }
 }
 
 String.prototype.startWith=function(str){
  if(str==null||str==""||this.length==0||str.length>this.length)
   return false;
  if(this.substr(0,str.length)==str){
     return true;
  }else{
     return false;
  }
 }
 
String.prototype.autolink = function(){
	var reg = /(http:\/\/[\w.\/]+)(?![^<]+>)/gi;
	return this.replace(reg, '<a href="$1" target="_blank">$1</a>');
};


/* 
* 获得时间差,时间格式为 年-月-日 小时:分钟:秒 或者 年/月/日 小时：分钟：秒 
* 返回精度为：秒，分，小时，天
*/
function getDateDiff(startTime, endTime, diffType) {
    //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式 
   // startTime = startTime.replace(/\-/g, "/");
    //endTime = endTime.replace(/\-/g, "/");
    //将计算间隔类性字符转换为小写
    diffType = diffType.toLowerCase();
    var sTime = new Date(startTime);      //开始时间
    var eTime = new Date(endTime);  //结束时间
    //作为除数的数字
    var divNum = 1;
    switch (diffType) {
        case "second":
            divNum = 1000;
            break;
        case "minute":
            divNum = 1000 * 60;
            break;
        case "hour":
            divNum = 1000 * 3600;
            break;
        case "day":
            divNum = 1000 * 3600 * 24;
            break;
        default:
            break;
    }
    return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}


Date.prototype.getWeekDay=function(){
	var d=["日","一", "二", "三", "四", "五", "六"];
	return d[this.getDay()];

}

Number.prototype.toFixed = function(d) {
var s = this + ""; 
if (!d) 
	d = 0; 
if (s.indexOf(".") == -1)
	s += "."; 
s += new Array(d + 1).join("0");
if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
	var s = "0" + RegExp.$2, pm = RegExp.$1, a = RegExp.$3.length, b = true; 
	if (a == d + 2) {
		a = s.match(/\d/g); 
		if (parseInt(a[a.length - 1]) > 4) {
			for (var i = a.length - 2; i >= 0; i--) {
				a[i] = parseInt(a[i]) + 1; 
				if (a[i] == 10) { 
				a[i] = 0; 
				b = i != 1; 
				} else 
				break; 
			} 
		} 
		s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"),"$1.$2"); 
	} 
	if (b) 
		s = s.substr(1); 
	return (pm + s).replace(/\.$/, ""); 
} 
return this + ""; 
}; 

function isFloat(s) {
 	var re = /^\d+(\.\d+)?$/; // 非负小数测试
	return re.test(s);
}


function isString(_str) {
   return typeof _str == 'string' || _str.constructor == String;
}


function Map(){
    this.obj = {};
    this.count = 0;
}

Map.prototype.put = function(key, value){
    var oldValue = this.obj[key];
    if(oldValue == undefined){
        this.count++;
    }
    this.obj[key] = value;
    return oldValue;
}

Map.prototype.get = function(key){
    return this.obj[key];
}

Map.prototype.remove = function(key){
    var oldValue = this.obj[key];
    if(oldValue != undefined){
        this.count--;
        delete this.obj[key];
    }
    return oldValue;
}

Map.prototype.size = function(){
    return this.count;
}

function Set(getKey){
    this.map = new Map();
    if(typeof(getKey)=="undefined"){
    	this.getKey = function(val){
    		return val;
    		//return "strkey_"+val;
    	};
    }else{
     	this.getKey = getKey;
    }
   
}

Set.prototype.add = function(value){
    var key = this.getKey(value);
    this.map.put(key, value);
}

Set.prototype.remove = function(value){
    var key = this.getKey(value);
    this.map.remove(key);
}
Set.prototype.getAll=function(){
    var tempArray=new Array();
    for(var i in this.map.obj){
       tempArray.push(i);
    }
    return tempArray;
}

Set.prototype.size = function(){
    return this.map.size();
}



var isArray = function(v) {
    return Object.prototype.toString.apply(v) === '[object Array]';
}

function clone(myObj){

  if(typeof(myObj) != 'object') return myObj;
  if(myObj == null) return myObj;
  
  var myNewObj = new Object();
  if(isArray(myObj)){
  	myNewObj=[];
  	for(var i=0;i<myObj.length;i++){
  		myNewObj[i] = clone(myObj[i]);
  	}
  }else{
  	 for(var i in myObj)
     myNewObj[i] = clone(myObj[i]);
  }
 

  return myNewObj;
}



/**
 * 获取url所有参数
 * 
 * @returns {Object}
 */
function getRequest() {

	var url = window.location.search; // 获取url中"?"符后的字串

	var theRequest = new Object();

	if (url.indexOf("?") != -1) {

		var str = url.substr(1);

		strs = str.split("&");

		for (var i = 0; i < strs.length; i++) {

			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);

		}

	}

	return theRequest;

}

/**
 * 获取url单个参数值
 * 
 * @param name
 * @returns
 */
function getQueryString(name) {

	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	
	var r = window.location.search.substr(1).match(reg);

	if (r != null)
		return unescape(r[2]);
	return null;

}

/*
* url 目标url(http://www.phpernote.com/)
* arg 需要替换的参数名称
* arg_val 替换后的参数的值
* return url 参数替换后的url
*/
function changeURLArg(url,arg,arg_val){
	var pattern=arg+'=([^&]*)';
	var replaceText=arg+'='+arg_val;
	if(url.match(pattern)){
		var tmp='/('+ arg+'=)([^&]*)/gi';
		tmp=url.replace(eval(tmp),replaceText);
		return tmp;
	}else{
		if(url.match('[\?]')){
			return url+'&'+replaceText;
		}else{
			return url+'?'+replaceText;
		}
	}
	return url+'\n'+arg+'\n'+arg_val;
}


/**
一个用作js模板替换的代码
template格式和数组格式如下
var template = "<div><h1>{title}</h1><p>{content}</p></div>";
var data = [{title:"a",content:"aaaa"},{title:"b",content:"bbb"},{title:"c",content:"ccc"}];
只需要数据格式对应
*/
var dataTemplate = function(template,data){
        var outPrint="";
        if(data instanceof Array){
        	 for(var i = 0 ; i < data.length ; i++){
                var matchs = template.match(/\{[a-zA-Z]+\}/gi);
                var temp="";
                for(var j = 0 ; j < matchs.length ;j++){
                        if(temp == "")
                                temp = template;
                        var re_match = matchs[j].replace(/[\{\}]/gi,"");
                        var value=data[i][re_match];
                        if(value==null){
                        	value="";
                        }
                        temp = temp.replace(matchs[j],value);
                }
                outPrint += temp;
        	}
        }else{
        	var matchs = template.match(/\{[a-zA-Z]+\}/gi);
                var temp="";
                for(var j = 0 ; j < matchs.length ;j++){
                        if(temp == "")
                                temp = template;
                        var re_match = matchs[j].replace(/[\{\}]/gi,"");
                        var value=data[re_match];
                        if(value==null){
                        	value="";
                        }
                        temp = temp.replace(matchs[j],value);
                }
                outPrint += temp;
        }
       
        return outPrint;
}

	
	//超链接转化为表单提交
function linktoform(linkurl,target){
	 var url=linkurl.split("?");
	 var formid="jspost";
	 var formobj;//jquery对象
	  while($("#"+formid).length>0){
		  formid+=new Date().getTime();			 
	  }
	  $("body").append("<form id="+formid+"></form>");
	  formobj=$("#"+formid);
	  formobj.attr("action",url[0]);
	  formobj.attr("method","post");
	  if(typeof(target)!= "undefined"&&target!=null){
		  formobj.attr("target",target);	
	  }
	  
	  if(url[1]!=null){
		  var pars=url[1].split("&");
		  var htmlcode="";
		  for(var i=0;i<pars.length;i++){
			  var temp=pars[i].split("=");
			  htmlcode+="<input type='hidden' name='"+temp[0]+"' value='"+temp[1]+"'>";
		  }
		  formobj.append(htmlcode);
		  $("#"+formid)[0].submit();
		  return false;
	  }		 
	  
}



function goback(){
	history.go(-1);
}

 
 function creatMethod(mn){
   	this.func = new Function(mn+"();");
 }


 jQuery.postJSON = function( url,data, success) {
	    return jQuery.ajax({
	        type: "POST",
	        dataType :"json",
	        url: url,
	        data: data,
	        success: function(data){
	        	success(data);
	        }
	    });
	};
