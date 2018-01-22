<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta content="always" name="referrer">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/welfare.css">
<%@ include file="/commons/js.jsp" %>

<script type="text/javascript">
	var jsBasePath = '${basePath}';
	var jsStaticResources = '<%=static_resources %>';
	var jsWelfareLevel="${welfareLevel}";
	var jsEntId="${ent.id}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_employees_empmanage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
</head>
<body>
	<!-- top start -->
	<%@ include file="/commons/header.jsp" %>
	<!-- top end -->

	<div id="content" class="clear">
		<!-- left menu start -->
		<%@ include file="/commons/leftmenu.jsp"%>
		<!-- left menu end -->
		  <!--right begin-->
    	<div class="right">
	     <div class="position">
			<span>您所在的位置：</span>
			<a href="javascript:void(0);">商家中心</a><em>></em>
			<a href="${basePath}/company/enterprise/getEnterpriseInfo.html">员工福利</a><em>></em>
			<a href="javascript:void(0);">员工管理</a>
	      </div>
          <div class="sale_wrap">
			<form action="${basePath}/company/emp/page.html" method="post" id="sub_form">
        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
            <div class="sale_info">
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">员工序号</div>
                        <input class="pubilc_input f218" type="text" name="empNumber" value="${query.empNumber}" maxLength="20">
                    </div>
                    <div class="sale_option">
                    	<div class="title">姓名</div>
                        <input class="pubilc_input f218" type="text" name="name" value="${query.name}" maxLength="20">
                    </div>
                	<div class="sale_option">
                    	<div class="title">性别</div>
                        <select class="pubilc_input f226" id="sex" name="sex">
							<option value=""></option>
							<option value="女" <c:if test="${'女' eq query.sex}">selected="selected"</c:if> >女</option>
							<option value="男" <c:if test="${'男' eq query.sex}">selected="selected"</c:if> >男</option>
                        </select>
                    </div>
                </div>
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">福利级别</div>
                        <select class="pubilc_input f226" id="welfareLevel" name="welfareLevel">
							<option value=""></option>
						<c:forEach var="s"  begin="1" end="${welfareLevel}">
							<option value="${s}" <c:if test="${s eq query.welfareLevel}">selected="selected"</c:if> >${s}</option>
						</c:forEach>
                        </select>
                    </div>
                    <div class="sale_option">
                    	<div class="title">手机号</div>
                        <input class="pubilc_input f218" type="text" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)" name="phone"  value="${query.userName}" maxLength="11">
                   </div>
                	<div class="sale_option">
                    	<div class="title">邮箱</div>
                        <input class="pubilc_input f218" type="text" name="email"  value="${query.email}" maxLength="50">
                    </div>
                </div>
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">工龄</div>
                        <input class="pubilc_input f91 r-num" type="text" id="startSeniority" name="startSeniority" value="${query.startSeniority}" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)"/>
                        <span><div class="r-ln"></div></span>
                        <input class="pubilc_input f91 r-num" style="margin-left:5px" type="text" id="endSeniority" name="endSeniority" value="${query.endSeniority}" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)"/>
                    </div>
                    <div class="sale_option">
                    	<div class="title">部门名称</div>
                        <input class="pubilc_input f218" type="text" name="sectionName"  value="${query.sectionName}" maxLength="50">
                    </div>
                </div>
                <div class="clear"></div>
				<div class="col-last">
                	<span>更改福利级别</span>
                    <input class="r-input" id="updateWelfareLevel" type="text" name="" value="${welfareLevel}" >
               	</div>
                <div class="btnwrap">
					<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
					<div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
                </div>
            	<div class="sale_list_wrap">
                <div class="sale_content">
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                     <thead>
                        <th style="min-width:100px;">员工序号</th>
                        <th style="width:150px;">姓名</th>
                        <th style="width:40px;">性别</th>
                        <th style="width:200px;">邮箱</th>
                        <th style="width:110px;">手机号码</th>
                        <th style="width:100px;">部门</th>
                        <th style="width:100px;">职务</th>
                        <th style="width:40px;">工龄</th>
                        <th style="width:100px;">福利级别</th>
                        <th style="width:100px;">操作</th>
                    </thead>
                    	<tbody >
                    	<c:forEach items="${page.list}" var="i" varStatus="status">
                    		<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
	                    	<td>${i.empNumber}</td>
	                        <td>${i.name}</td>
	                        <td>${i.sex}</td>
	                        <td>${i.email}</td>
	                        <td>${i.phone}</td>
	                        <td>${i.sectionName}</td>
	                        <td>${i.duty}</td>
	                        <td>${i.startSeniority}</td>
	                        <td>${i.welfareLevel}</td>
	                        <td><a href="javascript:void(0)" onclick="update_getDetails('${i.id}')">修改</a>&nbsp;&nbsp;
	                        <c:if test="${i.type==2}">
	                        <a href="javascript:void(0)" onclick="del_popup('${i.id}')">删除</a>
	                        </c:if>
	                        </td>
	                        </tr>
                    	</c:forEach>
                    	</tbody>
                    </table>
                </div>
	           	</div>
	        </div>
           	</form>
            <!-- 分页位置 start--> 
            <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
            
            <!-- 分页位置 end-->
			 <div class="r-btn-box">
            	<div class="r-btn-s"><a href="javascript:void(0)" id="add_emp">注册员工</a></div>
                <div class="r-btn-s"><a href="javascript:void(0)" id="alladd">批量注册</a></div>
                <div class="r-btn-s"><a href="javascript:void(0)" onclick="down()">下载批量模板</a></div>
                <div class="r-btn-s"><a href="javascript:void(0)" id="share">生成注册链接</a></div>
                <div class="r-btn-s"><a href="javascript:void(0)" id="add_emp_set">员工注册设置</a></div>
            </div>
		  </div>
		</div>
		<!--right end-->
	</div>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
<!--修改员工信息-弹出框 begin-->
 
<div id="update_popup_box" style="margin:0;">
</div>
<!--修改员工信息-弹出框 end-->


<!-- 背景弹出框 begin -->
<div class="popup_bg"></div>
<!-- 背景弹出框 end -->


<!--删除-弹出框 begin-->
<div class="public_popup" id="delete-info" style="height:250px;">
    <div class="pop-title">
    	<span>删除员工信息</span>
        <div class="add-close-btn" id="delete-info-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p class="add-tl">删除这个员工的信息？</p>
    	<input type="hidden" id="del_id">
        <div class="add-btn-box"><a id="info-ture-btn" onclick="del()" class="true-btn" href="javascript:void(0)">确认</a><a class="cansel-btn" id="info-cansel-btn" href="javascript:void(0)">取消</a></div>
    </div>
</div>

<!--删除-弹出框 end-->

<!--新增员工-弹出框 begin-->
<div class="public_popup" id="add-employee" style="height:auto;">
    <div class="pop-title">
    	<span>新增员工信息</span>
        <div class="add-close-btn" id="add-employee-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont" style="margin:0;">
    <form id="add_emp_form">
    	<ul class="change-list">
        	<li>
            	<span>员工序号</span>
                <div class="c-r-box"><input class="r-input" id="add_emp_number" value="${maxEmpNumber}" maxLength="100" type="text" name="empNumber" ><span id="add_empNumber_error"></span></div>
            </li>
    		<li>
            	<span>手机号</span>
                <div class="c-r-box"><input id="add_emp_phone" class="r-input" type="text" name="phone" maxLength="11" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)"><span id="add_phone_error"></span></div>
            </li>
        	<li>
            	<span>员工姓名</span>
                <div class="c-r-box"><input id="add_emp_name" maxLength="10" class="r-input" type="text" name="name"><span id="add_name_error"></span></div>
            </li>
        	<li>
            	<span>性别</span>
                <div class="c-r-box"><input id="add_emp_nan" type="radio" checked="checked" name="sex" value="男">男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="add_emp_nv" type="radio" name="sex" value="女">女</div>
            </li>
        	<li>
            	<span>职务</span>
                <div class="c-r-box"><input class="r-input" maxLength="10" id="add_emp_duty" type="text" name="duty"></div>
            </li>
        	<li>
            	<span>部门</span>
                <div class="c-r-box"><input class="r-input" maxLength="10" id="add_emp_sectionName" type="text" name="sectionName"></div>
            </li>
            <li>
            	<span>年龄</span>
                <div class="c-r-box"><input class="r-input" type="text" id="add_emp_age" name="age" maxLength="2" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)"></div>
            </li>
            <li>
            	<span>工龄</span>
                <div class="c-r-box"><input class="r-input" type="text" id="add_emp_seniority" name="seniority" maxLength="10" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)"></div>
            </li>
            <li>
            	<span>福利级别</span>
            	
                <div class="c-r-box">
						<select id="add_emp_welfareLevel" class="c-r-select" name="welfareLevel">
							<option value=""></option>
							<c:forEach var="s" begin="1" end="${welfareLevel}">
								<option value="${s}">${s}</option>
							</c:forEach>
						</select>
				        <span id="add_welfareLevel_error"></span>
				</div>
            </li>
    		<li>
            	<span>邮箱</span>
                <div class="c-r-box"><input id="add_emp_email" class="r-input" type="text" name="email" maxLength="50"><span id="add_email_error"></span></div>
            </li>
        </ul>
    </form>
        <div class="add-btn-box"><a id="add-employee-ture-btn" class="true-btn" href="javascript:void(0)">确认</a><a class="cansel-btn" id="add-employee-cansel-btn" href="javascript:void(0)">取消</a></div>
    </div>
</div>


<div class="public_popup" id="operation-suceess" style="height:250px;">
    <div class="pop-title-2">
        <div class="suceess-close-btn" id="add-employee-suceess-close"><i onclick="success_close()" class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p id="operation_success_cont"></p>
        <div class="suceess-btn-box"><a onclick="success_close()" class="true-btn" href="javascript:void(0)">确认</a></div>
    </div>
</div>
<!--新增员工-弹出框 end-->
<!--批量新增-弹出框 begin-->
<div class="public_popup" id="all-add" style="height:auto">
    <div class="pop-title">
    	<span>批量新增员工</span>
        <div class="add-close-btn" id="alladd-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont" style="margin:0">
    	<div class="alladd-mark-cont" style="margin-left:0">
        	<h3>请导入包含员工信息的Excel表</h3>
            <div class="file-box"><span class="choice-file">选择文件</span><input class="file" type="file" id="file" name="file"></div>
            <div class="alladd-mark-box">
            	<span class="span-lt">说明：</span>
                <div class="alladd-mark">
                	<p>请下载批量注册模版并按要求填写Excel表格。</p>
                    <p>Excel表格文件大小应小于5Mb。</p>
                    <p>员工姓名、性别、员工编号、福利级别为必填项。</p>
                    <p>手机号和邮箱必须填写一个。</p>
                    <p>福利级别为必选项，默认值为1。</p>
                    <p>其他项目为可选项。</p>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="add-btn-box"><a id="alladd-ture-btn" class="true-btn" href="javascript:void(0)">确认</a><a class="cansel-btn" id="alladd-cansel-btn" href="javascript:void(0)">取消</a></div>
    </div>
</div>

<div class="public_popup" id="alladd-suceess">
    <div class="pop-title-2">
        <div class="suceess-close-btn" id="alladd-suceess-close"><i class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<!--<p>文件尺寸超过了系统要求，<br>请审查修改后重新上传！</p>
        <p>第67行、第87行数据重复<br>请审查修改后重新提交！</p>
        <p>第89行、第4列数据格式有误，<br>第102行、第2列数据有误，<br>请修改后重新上传！</p>
        <p>第89行、第4列数据格式有误，<br>第102行、第2列数据有误，<br>请审查修改后重新上传！</p>-->
        <p>批量新增员工操作成功！</p>
        <div class="suceess-btn-box"><a class="true-btn" href="javascript:void(0)">确认</a></div>
    </div>
</div>
<!--批量新增-弹出框 end-->
<!--更改福利级别-弹出框 begin-->
<div class="public_popup" id="alter-grade" style="height:auto">
    <div class="pop-title">
    	<span>确认提示框</span>
        <div class="add-close-btn" id="alter-grade-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p class="add-tl">减少福利级别可能导致员工福利级别丢失，<br>重新设置福利级别数量？</p>
        <div class="add-btn-box"><a id="alter-grade-ture-btn" class="true-btn" href="javascript:void(0)">确认</a><a class="cansel-btn" id="alter-grade-cansel-btn" href="javascript:void(0)">取消</a></div>
    </div>
</div>

<div class="public_popup" id="alter-setting" style="height:auto">
    <div class="pop-title">
    	<span>更改福利级别设置</span>
        <div class="add-close-btn" id="alter-setting-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<ul class="change-list">
        	<li>
            	<span>福利级别</span>
                <div class="c-r-box">
                <input class="r-input" type="text" name="" id="welfareLevel_input" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)" value="${welfareLevel}">
                <div class="error" id="update_welfareLevel_error1"></div>
                </div>
            </li>        
        </ul>
        <div class="add-btn-box"><a id="alter-setting-ture-btn" class="true-btn" href="javascript:void(0)">确认</a><a class="cansel-btn" id="alter-setting-cansel-btn" href="javascript:void(0)">取消</a></div>
    </div>
</div>

<!--更改福利级别-弹出框 end-->

<!-- 员工福利分享弹层begin -->
<div class="public_popup" id="share_pb" style="height:auto;display:none;">
    <div class="pop-title">
    	<span>员工绑定二维码</span>
        <div class="add-close-btn" id="share_pb_close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<div class="ewm_share"><img src=""  /></div>
    	<p class="share_p">扫描二维码或者复制以下链接分享给员工，员工可自行完成信息录入</p>
    	<div class="share_link">${us.shareUrl}</div>
    	<p class="share_p">请不要分享给员工以外的朋友，以保障员工的专享权益</p>
        <div class="add-btn-box">
        	<a class="true-btn" id="clear_btn" href="javascript:void(0)" style="margin:0!important;">作废二维码</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;
        	<a class="true-btn" href="http://api.wd-w.com/userShare/downLoadQr?text=${us.shareUrl}"" style="margin:0!important;">下载推广版</a>
        </div>
    </div>
</div>
<!-- 员工福利分享弹层end -->
<!-- 员工福利设置弹层begin-->
<div class="public_popup" id="set_pb" style="height:auto;display:none;">
    <div class="pop-title">
    	<span>员工注册设置</span>
        <div class="add-close-btn" id="set_pb_close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont" style="padding:25px 30px">
    	<div class="email_suffix">
    		<span>邮箱后缀</span>
    		<div class="email_suffix_rt">
    			<!--<div class="suffix_con" >
    			     <em>@</em><input type="text" value="${ent.emailPostfix1}" id="email1" /><i onclick="addInp()">+</i>
    			</div>
    		  <c:if test="${not empty ent.emailPostfix2}">
    			<div class="suffix_con" >
    			     <em>@</em><input type="text" value="${ent.emailPostfix2}" /><i class="del" onclick="delInp(this);" >-</i>
    			</div>
    		  </c:if>
    		  <c:if test="${not empty ent.emailPostfix3}">
    			<div class="suffix_con" >
    			     <em>@</em><input type="text" value="${ent.emailPostfix3}" /><i class="del" onclick="delInp(this);" >-</i>
    			</div>
    		  </c:if> -->
    			 
    			<p>必须为企业邮箱，用该邮箱注册后，自动成为该企业员工</p>
    		</div>
    		
    	</div>
    	<div class="tx">
    		<span>员工默认头像</span>
    		<div class="tx_rt">
    			<dl>
    				<dt><c:if test="${empty ent.empDefultAvatar}"><img src="<%=static_resources %>images/hp_tx.png"  /></c:if><c:if test="${not empty ent.empDefultAvatar}"><img src="${ent.empDefultAvatar}"  /></c:if>
    					<input type="hidden" value="${ent.empDefultAvatar}"/>
    				</dt>
    				<dd class="dd1">可以品牌、企业logo作为员工默认头像<br />点击logo后可直接进入店铺(120*120px)</dd>
    				<dd class="dd2"><a href="javascript:toUpload();">上传</a><a href="javascript:removeImg();" class="del_btn">删除</a></dd>
    			</dl>
    		</div>
    	</div>
    	<div class="hint">错误提示</div>
    	<div class="add-btn-box" style="margin-top:20px;"><a id="set-ture-btn" class="true-btn" href="javascript:;" onclick="saveEmpSet();">保存</a><a class="cansel-btn" id="set-cansel-btn" href="javascript:void(0)">取消</a></div>
    </div>
</div>

<!-- 员工福利设置弹层end -->
<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>
<%@ include file="/commons/box.jsp" %>
</body>
<script>
//添加邮箱后缀输入框
	function addInp(){
		
		var html = "";
	    html +='<div class="suffix_con"  >';
	    html +='<em>@</em><input type="text" value="" /><i class="del" onclick="delInp(this);" >-</i>';
	    html +='</div>';
	    
		var s=$(".suffix_con");
		if(s.length<3){
			$(".email_suffix_rt p").before(html);
		}else{
			
			//$(".email_suffix_rt .p2").show();
			
		};
			
	};
//删除邮箱后缀输入框	
	function delInp(obj){	
		$(obj).parent().remove();
	};
	



     

</script>
</html>
