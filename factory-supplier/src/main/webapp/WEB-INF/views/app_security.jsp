<%@page import="com.wode.factory.supplier.util.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" %>
    <%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>API安全</title>
<head>
<%@ include file="/commons/js.jsp" %>

</head>
<style>
.store_cont{width: 702px;
    margin: 50px ;
    position: relative;}
.store_cont h2 {
    font: 12px "Microsoft YaHei";
    color: #959595;
}
.orange-light {
    font: 12px "Microsoft YaHei";
    color: #caab80;
}
.AccountTab {
    width: 702px;
    margin-top: 20px;
    text-align: center;
    font: 12px "Microsoft YaHei";
    color: #6a6a6a;
    border:none;
}
.tab_box{height:80px;}

.AccountTab .Tabname {
    width: 120px;
    font: 14px "Microsoft YaHei";
    color:#222222;
    float:left;
    text-align:left;
    line-height:24px;
}

.AccountTab .Tabcont {
font-size:14px;
    text-align: left;
    width:580px;
    height:40px;
   line-height:24px;
   float:left;
}
.Tabcont p{height:24px;line-height:24px;}
.AccountTab .Tabcont input{color:#222222;}
.AccountTab .Tabcont em{color:#222222;font-style:normal;font-size:14px;}
.AccountTab .Tabcont a{font-size:15px;color:#ff6161;cursor:pointer;margin-right:5px;float:left;}
.AccountTab .Tabcont i{display:block;float:left;margin-top:5px;cursor:pointer;position:relative;font-style:normal;}
.AccountTab .Tabcont i span{width:150px;height:auto;display:none;background:#f6f6f7;padding:5px;border:1px solid #e7e7eb;position:absolute;top:18px;left:-40px;border-radius:4px;}
.AccountTab .Tabcoper {
    width: 124px;
}
.Tabcoper a:visited {color:#ff6161;cursor:pointer}

 #divExpress{width:100%;padding:0;position:absolute;left:50%;top:40%;}

#divExpress .cont_box1{width:100%;background:none;padding-top:30px;}


 .shop_popup{width:500px;}      
 .popup_btn1 {
    text-align: center;
    width: 100%;
    margin: 30px auto 0;
}
.popup_btn1 a:link, .popup_btn1 a:visited {
    display: inline-block;
    text-decoration: none;
    font: 14px/30px "Microsoft YaHei";
    color: #fff;
    width: 86px;
    height: 30px;
    text-align: center;
    margin-right: 20px;
    background: #5d6781;
    border-radius: 3px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
}
.new_secret{width:480px;margin:10px 0 0 20px;background:#fff;overflow:hidden}
.new_secret span{width:70px;height:30px;line-height:30px;display:block;float:left;}
.new_secret .secret_inp{width:410px;height:auto;float:left;overflow:hidden;}
.new_secret .secret_inp .inp_box{width:410px;height:auto;}
.new_secret .secret_inp .inp_box .inp{width:300px;height:30px;border:1px solid #e7e7eb;border-radius:2px;float:left;}

.new_secret .secret_inp .inp_box .inp input{width:250px;height:30px;float:left;padding:0 5px;border:none;}
.new_secret .secret_inp .inp_box .inp em{width:40px;height:30px;display:block;text-align:center;line-height:30px;font-style:normal;float:left;}
.new_secret .secret_inp .inp_box a{height:30px;width:100px;display:block;float:left;text-align:center;line-height:30px;color:#449dea;}
.new_secret .secret_inp p{width:410px;height:40px;line-height:40px;float:left;color:#8d8d8d;}
</style>
<body>
<%@ include file="/commons/header.jsp" %>
<!--header end-->

<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<li><a href="${basePath}/user/tosuppliermain.html">首页</a></li>
			<li><a href="${basePath}/user/info.html">个人信息</a></li>	
			<li><a href="${basePath}/user/security.html">安全设置</a></li>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<li class="curr"><a href="javascript:;">API安全</a></li>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置</span><em>></em>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/user/security.html">API安全</a>
        </div>
        <div class="sort_wrap">
            <div class="sort_title">API安全</div>
            
	            <div class="store_cont">
		        	<div>
						<img class="loading_img" id="loading" src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/loading_small.gif" style="display: none;"/>
					</div>
		            <div width="100%" class="AccountTab" >
		              <div class="tab_box">
		                <div class="Tabname">应用ID<br />(AppID)</div>
		                <div class="Tabcont"><input type="hidden" id="appId" value="${app.id}"><em>${app.id}</em><br />应用ID是应用唯一标识，配合应用秘钥可调用系统的接口能力。</div>
		              </div>
		              <div class="tab_box">
		                <div class="Tabname">应用密钥<br />(AppSecret)</div>
		                <div class="Tabcont">
		               	<c:if test="${empty app.secret}">
			              <p><a>生成</a></p>
		              	</c:if>
		               	<c:if test="${not empty app.secret}">
			              <p><a>重置</a><i><img src="<%=static_resources %>images/wenhao1.png" width="16px" height="16px"><span>为保障帐号安全，平台不再储存AppSecret；如果遗忘，请重置
</span></i></p>
		              	</c:if>
		                <br />应用秘钥是校验合作伙伴身份的密码，具有极高的安全性。切记勿把密码直接交给第三方开发者或直接存储在代码中。</div>
		              </div>
		            </div>
	            </div>
		
        </div>
    </div>
    <!--right end-->
</div>

<!-- 背景弹出框 begin -->
<div class="popup_bg" style="display:none"></div>
<!-- 背景弹出框 end -->
<div class="shop_popup" id="divExpress" style="width:500px;display:none;overflow:hidden;z-index:99999999">
	<div class="popup_title">
    	<span>设置应用密钥</span>
        <label onclick="closeBtn();"><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
   	<div class="popup_cont" style="padding:0;width:500px;"  >
		<div class="cont_box" style="padding-top:30px;width:500px;background:#fff;">
			<div class="new_secret">
				<span>新密钥</span>
				<div class="secret_inp">
					<div class="inp_box">
						<div class="inp"><input id="val1" type="text" value="" maxlength="32" oninput="checkNum (this)" onpropertychange="checkNum (this)" /><em>0/32</em></div>
						<a href="javascript:;" onclick="_getRandomString();">自动生成新密钥</a>
					</div>
					<p>请输入32个字符，只允许输入数字和英文大小写字母的组合。</p>
				</div>
			</div>
			<div class="new_secret">
				<span>确认新密钥</span>
				<div class="secret_inp">
					<div class="inp_box">
						<div class="inp"><input  id="val2" type="text" value="" maxlength="32" oninput="checkNum (this)" onpropertychange="checkNum (this)" /><em>0/32</em></div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
   
    <div class="clear"></div>
  	<div id="box_msg" class="box_msg" style="color:#ff4040;height:30px;width:440px;text-align:center;margin-top:20px;"></div>        
     <div class="popup_btn1" style="padding-bottom:20px;margin-top:10px;">
         <a href="Javascript:void(0);" onclick="sureBtn();">确认</a>
         <a href="javascript:void(0);" onclick="closeBtn();">取消</a>
     </div>
</div> 

<!--content end-->
<script type="text/javascript">
	var jsBasePath = '${basePath}';

	$(document).ready(function(){
		selectedHeaderLi("sy_header");
	});
	
	
</script>
<script type="text/javascript" src="<%=static_resources %>js/views_app_security.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
