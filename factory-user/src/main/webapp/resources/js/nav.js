// JavaScript Document

$(".nav").children("ul").html('<li><a href="/index.html">首页</a></li>'+
		'<li><a href="/tesheng.html">特省</a></li>' +
		'<li><a href="/shiyong.html">试用</a></li>' +
		'<li><a href="/huanling.html">换领</a></li>' +		
		'<li><a href="/qicai.html">企采</a></li>'
		);

try{
	setActive();
} catch(e) {
	
}

