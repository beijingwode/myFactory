<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name = "format-detection" content = "telephone=no">
<title>我的换领币</title>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/rob_stamps.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/stamps_css/My_stamps.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>

<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head> 

<body style="background:#f0f0f5;">
<div class="main-cont" id="main-cont">
	
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a>我的换领币</h1>
    </div>
    <div class="main-box" >
    	<input type="hidden" name="empId" id="empId" value="${empId }">
		<div class="rob_banner">
			<img src="<%=static_resources %>images/f3_bg.png" />
			<div class="rob_banner_con">	
				1、公司发放福利商品已合算成换领币。<br />
				2、凭换领币可直接领取该福利商品或用于领取其它商品时现金抵扣。<br />
				3、换领币过期后将失效，请留意换领币有效日期。
			</div>
		</div>
		
	  	<c:if test="${1==0}">
    	<ul>
    		<li class=""><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->
    			<a href="javascript:void();">
    				<div class="select_icon" style="display:none;"><img src="<%=static_resources %>images/stamps_images/weixuanzhong_icon.png" /></div><!-- 未选weixuanzhong_icon.png  已选xuanzhong_icon.png -->
    				
    				<p class="p1"><img src="<%=static_resources %>images/stamps_images/beijing_top_bg.png" /></p>
    				<div class="stamps_box stamps_box_hl">
    					<div class="stamps_con">
    						<div class="stamps_lt">
    							<span class="span1">9999.99</span>
    							<span class="span2">期限：2018.12.31</span>
    						</div>
    						<div class="stamps_rt">
    							<div class="rt_con">
    								<dl>
    									<dt><img src="<%=static_resources %>images/stamps_images/xin_icon.png" /><i></i></dt>
    									<dd class="dd1">豆香韵豆浆粉（同一地址满40份发货）</dd>
    									<dd class="dd2">规格：豆香韵豆浆粉</dd>
    								</dl>
    								  								
    								<div class="rt_bottom"><p class="p3" style="width:100%;">2018年春节，xxxx公司为本公司企业员工提供福利商品</p></div>
    							</div>
    						</div>
    					</div>
    					<div class="zhang yishixao_icon"><img src="<%=static_resources %>images/stamps_images/yishixiao_icon.png" /></div>
    					<div class="zhang yiyongwan_icon"><img src="<%=static_resources %>images/stamps_images/yiyongwan_icon.png" /></div>
    					
    				</div>
    				<p class="p1"><img src="<%=static_resources %>images/stamps_images/beijing_bottom_bg.png" /></p> 
    				
    			</a>
    		</li>
    	</ul>
    	</c:if>
    
    
        <div class="stamps_box">
	  	  <c:forEach items="${info}" var="pro" varStatus="status">
	  	  	<c:choose>
	  	  	  
	  	  	 <c:when test="${pro.status==0 || pro.status==1}">
		  	  	<div class="stamps_con stamps_con1">
	            	
	            	<div class="barbox">
	            		<img src="<%=static_resources %>images/stamps_bg3.png" />
                        <div class="barline">
                            <div  width="0" style="width:0%; background-image: url(http://localhost:8080/api/static_resources/images/stamps_bg2.png)" class="charts" >
                            </div>
                        </div>
                    </div>
	                <div class="sp_con_lt"  onclick="goNext(${pro.id})">
	                	<ul>
	                    	<!--  <li class="li1">余额：</li> -->
	                    	<li class="li1"><i>余额：</i><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em></li>
	                        <li class="li2">总额：<fmt:formatNumber value="${pro.empAvgAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></li>
	                        <li class="li3">使用期限：<fmt:formatDate value="${pro.limitEnd}" pattern="yyyy.MM.dd"/></li>
	                    </ul>
	                    <!--<div class="price"><em>￥</em><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span></div>-->
	                </div>
	                <div class="sp_con_rt">
	                	<a href="javascript:void(0);" class="cjl_btn" onclick="goNext(${pro.id})">查记录</a>
	                	<a href="javascript:void(0);" class="qsy_btn" onclick="javascript:go2HL()">去使用</a>
	                </div>
	            </div>
	  	  	   </c:when>
	  	  	  <c:when test="${pro.status==2}">
	            <div class="stamps_con stamps_con3">
	            	<img src="<%=static_resources %>images/stamps_bg_yyw.png" />
	                <div class="sp_con_lt lt_yyw" onclick="goNext(${pro.id})">
	                	<ul>
	                    	<li class="li1"><i>余额：</i><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em></li>
	                        <li class="li2">总额：<fmt:formatNumber value="${pro.empAvgAmount}" type="number" groupingUsed="false" minFractionDigits="2"/>,${pro.ticketNote}</li>
	                        <li class="li3">使用期限：<fmt:formatDate value="${pro.limitEnd}" pattern="yyyy.MM.dd"/></li>
	                    </ul>
	                    <!-- <div class="price"><em>￥</em><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span></div> -->
	                </div>
	                <div class="tuzhang"><img src="<%=static_resources %>images/tuzhang_yyw.png" /></div>
	                <div class="sp_con_rt">
	                	<a href="javascript:void(0);" class="cjl_btn exStutis" onclick="goNext(${pro.id})">查记录</a>
	                	<!-- <a href="javascript:void(0);" class="qsy_btn" onclick="javascript:go2HL()">去使用</a> -->
	                	<!-- <a href="javascript:goNext(${pro.id});">
	                    	<div class="bor_jd">    
	                        	<span class="span1">已激活${pro.activeAmount}%</span>     
	                            <div class="barbox">
	                                <div class="barline">
	                                    <div width="${pro.activeAmount}" style="width:0px;" class="charts"></div>
	                                </div>
	                            </div>
	                            <span class="span2">已用完</span>
	                        </div>
	                    </a> -->
	                </div>
	            </div>
	  	  	  </c:when>
	  	  	  
	  	  	  <c:when test="${pro.status==3}">
	  	  	    <div class="stamps_con stamps_con2">
	            	<img src="<%=static_resources %>images/stamps_bg_ygq.png" />
	                <div class="sp_con_lt lt_ygq" onclick="goNext(${pro.id})">
	                	<ul>
	                    	<li class="li1"><i>余额：</i><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em></li>
	                        <li class="li2">总额：<fmt:formatNumber value="${pro.empAvgAmount}" type="number" groupingUsed="false" minFractionDigits="2"/>,${pro.ticketNote}</li>
	                        <li class="li3">使用期限：<fmt:formatDate value="${pro.limitEnd}" pattern="yyyy.MM.dd"/></li>
	                    </ul>
	                    <!-- <div class="price"><em>￥</em><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span></div> -->
	                </div>
	                <div class="tuzhang"><img src="<%=static_resources %>images/tuzhang_ygq.png" /></div>
	                <div class="sp_con_rt">
	               <%--  <a href="javascript:void(0);" class="cjl_btn" onclick="goNext(${pro.id})">查记录</a> --%>
	                	<!--<a href="javascript:goNext(${pro.id});">
	                    	<div class="bor_jd">
	                        	<span class="span1">已激活${pro.activeAmount}%</span>
	                            <div class="barbox">
	                                <div class="barline">
	                                    <div width="${pro.activeAmount}" style="width:0px;" class="charts"></div>
	                                </div>
	                            </div>
	                            <span class="span2">已过期</span>
	                        </div>
	                    </a>-->
	                </div>
	            </div>
	  	  	 </c:when>
	  	  	  
	  	  	 <c:when test="${pro.status==4}">
	  	  	    <div class="stamps_con stamps_con2">
	            	<img src="<%=static_resources %>images/stamps_bg_yzx.png" />
	                <div class="sp_con_lt lt_yzx" onclick="goNext(${pro.id})">
	                	<ul>
	                    	<li class="li1"><i>余额：</i><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em></li>
	                        <li class="li2">总额：<fmt:formatNumber value="${pro.empAvgAmount}" type="number" groupingUsed="false" minFractionDigits="2"/>,${pro.ticketNote}</li>
	                        <li class="li3">使用期限：<fmt:formatDate value="${pro.limitEnd}" pattern="yyyy.MM.dd"/></li>
	                    </ul>
	                    <!-- <div class="price"><em>￥</em><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span></div> -->
	                </div>
	                <div class="tuzhang"><img src="<%=static_resources %>images/tuzhang_yzx.png" /></div>
	                <div class="sp_con_rt">
	                	<a href="javascript:void(0);" class="cjl_btn exStutis" onclick="goNext(${pro.id})">查记录</a>
	                	<!--<a href="javascript:goNext(${pro.id});">
	                    	<div class="bor_jd">
	                        	<span class="span1">已激活${pro.activeAmount}%</span>
	                            <div class="barbox">
	                                <div class="barline">
	                                    <div width="${pro.activeAmount}" style="width:0px;" class="charts"></div>
	                                </div>
	                            </div>
	                            <span class="span2">已折现</span>
	                        </div>
	                    </a>-->
	                </div>
	            </div>
	  	  	  </c:when>
	  	  	  
	  	  	  <c:when test="${pro.status==6}">
	  	  	    <div class="stamps_con stamps_con2">
	            	<img src="<%=static_resources %>images/stamps_bg_xxhl.png" />
	                <div class="sp_con_lt  lt_xxhl" onclick="goNext(${pro.id})">
	                	<ul>
	                    	<li class="li1"><i>余额：</i><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em></li>
	                        <li class="li2">总额：<fmt:formatNumber value="${pro.empAvgAmount}" type="number" groupingUsed="false" minFractionDigits="2"/>,${pro.ticketNote}</li>
	                        <li class="li3">使用期限：<fmt:formatDate value="${pro.limitEnd}" pattern="yyyy.MM.dd"/></li>
	                    </ul>
	                    <!-- <div class="price"><em>￥</em><span><fmt:formatNumber value="${pro.empAvgAmount-pro.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span></div> -->
	                </div>
	                <div class="tuzhang"><img src="<%=static_resources %>images/tuzhang_xxhl.png" /></div>
	                <div class="sp_con_rt">
	                	<!--<a href="javascript:goNext(${pro.id});">
	                    	<div class="bor_jd">
	                        	<span class="span1">已激活${pro.activeAmount}%</span>
	                            <div class="barbox">
	                                <div class="barline">
	                                    <div width="${pro.activeAmount}" style="width:0px;" class="charts"></div>
	                                </div>
	                            </div>
	                            <span class="span2">线下换领</span>
	                        </div>
	                    </a>-->
	                </div>
	            </div>
	  	  	  </c:when>
	  	  	  
	  	  	</c:choose>
	      </c:forEach>          
        </div>
    </div>
</div>
<!-- 背景蒙层 -->
<div class="thickdiv" ></div>
<div class="hl_stamps">
	<ul>
		<li class="li1"><a href="javascript:;">领取更多商品</a></li>
		<li class="li2"><a href="javascript:;">就要它了</a></li>
		<li class="li3"><a href="javascript:;">查看记录</a></li>
		<li class="li4"><a href="javascript:;">取消</a></li>
	</ul>
</div>
<script>
$(function(){
	$(".main-box ul li a").click(function(){
		$(".thickdiv").show();
		$(".hl_stamps").show();
	});
	$(".hl_stamps ul li a").click(function(){
		$(".thickdiv").hide();
		$(".hl_stamps").hide();
	})
})
</script>

<%@ include file="/commons/alertMessage.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","0");
	}

	//animate();
	var ua = navigator.userAgent.toLowerCase();
	if (/android/.test(ua)) {//安卓
		$(".sp_con_lt ul").css("padding","4% 0");
		$(".price").css("top","10%");
		//$(".sp_con_lt ul li").css("padding","3px 0");
	}
});

function animate(){
	var barboxWidth = $(".barbox:eq(0)").width();
	var barboxHeight = $(".barbox:eq(0)").height();
	
	$(".charts").each(function(i,item){
		
		$(item).css({"background-size":barboxWidth + "px "+barboxHeight +"px","height":barboxHeight+"px"});
		
		var a=parseInt($(item).attr("width"));
		if(i>0) 
		$(item).animate({
			width: a+"%",
		},1000);
	    

		if(a == 0){
			var s1 = $(".span1").eq($(this).index());
			var s2 = $(".span2").eq($(this).index());
			
			s2.html("未激活");
			s2.css({"color":"#cb1b1b","border":"1px solid #cb1b1b","background":"none","padding":"2px 4px"});
			
		}
		
		
		
		
	});
}
function goNext(ticketId){//查看换领使用流水
	var empId = $("#empId").val();
	window.location =jsBasePath +'bargainFlow/exChangeTicketFlow?cId=3&empId'+empId+'&ticketId='+ticketId;
}
function go2HL(){//跳转到换领频道
	try{
		if (isWeiXinH5()) {
			window.location = jsBasePath +'huanling.html';
		}else{
			Toast.show("go2HuanLing");
		}
		}catch (e) {
			window.location = "go2HuanLing";
		}	
}
</script>
</body>
</html>
