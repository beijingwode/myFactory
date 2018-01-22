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
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/welfare.css">
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/placeholderfriend.js"></script>
<script>
var jsBasePath = '${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_benefit_giveBenefit.js"></script>

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
            <a href="javascript:void(0);">福利发放</a>
        </div>
        <div class="sale_wrap">
		    <form action="${basePath}/company/benefit/page.html" method="post" id="sub_form">
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
                        <input class="pubilc_input f218" type="text" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)" name="phone"  value="${query.phone}" maxLength="11">
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
                </div>
                
                <div class="clear"></div>
                <div class="btnwrap" style="width: 300px">
					<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
					<div class="checkbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
					<div class="resetbtn"><a href="javascript:void(0);" onclick="exportExcel();">导出EXCEL</a></div>
                </div>
                
            	<div class="sale_list_wrap">
                  <div class="sale_content">
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                     <thead>
                        <th style="min-width:100px;">员工序号</th>
                        <th style="width:130px;">姓名</th>
                        <th style="width:40px;">性别</th>
                        <th style="width:180px;">邮箱</th>
                        <th style="width:100px;">手机号码</th>
                        <th style="width:60px;">工龄</th>
                        <th style="width:50px;">福利级别</th>
                        <th style="width:80px;">内购券(本季)</th>
                        <th style="width:80px;">现金券(本季)</th>
                        <th style="width:120px;">操作</th>
                    </thead>
                     <tbody >
                    	<c:forEach items="${page.list}" var="i" varStatus="status">
                    		<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
	                    	<td>${i.empNumber}</td>
	                        <td>${i.name}</td>
	                        <td>${i.sex}</td>
	                        <td>${i.email}</td>
	                        <td>${i.phone}</td>
	                        <td>${i.seniority}</td>
	                        <td>${i.welfareLevel}</td>
	                        <td>
	                        	<span class="money-right s-color">
									<fmt:formatNumber type="number" value="${i.giveTicketSason }" maxFractionDigits="0" /> 
								</span>
							</td>
	                        <td>
	                        	<span class="money-right s-color">
									<fmt:formatNumber type="number" value="${i.giveCashSason }" maxFractionDigits="0" /> 
								</span>
							</td>
	                        <td>
	                        <span style="width: 110px;">
	                        	<a href="javascript:void(0)" <c:if test="${i.logout == 0 }">onclick="provide('${i.empId}')"</c:if><c:if test="${i.logout == 1 }">style="color:#ccc;"</c:if>     >发放福利</a>&nbsp;&nbsp;<a
								href="javascript:void(0)" <c:choose><c:when test="${i.giveTicketSason > 0 or i.giveCashSason > 0}">onclick="recycle('${i.empId}')"</c:when><c:otherwise>style="color:#ccc;"</c:otherwise></c:choose> >回收福利</a></span></td>
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
					<div class="r-btn-s">
						<a href="javascript:void(0)" id="levelGrant" >级别发放</a>
					</div>
					<div class="r-btn-s">
						<a href="#" id ="all-grade">批量发放</a>
					</div>
					<div class="r-btn-s">
						<a href="javascript:void(0)" onclick="down()">下载批量模板</a>
					</div>
			</div>

		</div>
	</div>
	<!--right end-->
	
  </div>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	

<!-- 遮盖层 -->
<div class="popup_bg"></div>
<!-- 发放福利 start -->
<div class="public_popup" id="provide" style="height:350px;">
<!-- 发放福利 end -->
</div>	
<!-- 回收福利 start -->
<div class="public_popup" id="recycle" style="height:350px;">
<!-- 回收福利 end -->
</div>

<!--级别发放 begin-->
<div class="public_popup g-width" id="grade" style="width:680px;">

<!--级别发放end -->
</div>
<!--批量发放-弹出框 begin-->
<div class="public_popup" id="all-send">
    <div class="pop-title">
    	<span>批量发放</span>
        <div class="add-close-btn" id="allsend-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<div class="alladd-mark-cont" style="margin-left:0;">
        	<h3>请导入包含员工信息的Excel表</h3>
            <div class="file-box"><span class="choice-file">选择文件</span><input class="file" type="file" id="file" name="file"></div>
            <div class="alladd-mark-box">
            	<span class="span-lt">说明：</span>
                <div class="alladd-mark">
                	<p>请下载批量注册模版并按要求填写Excel表格。</p>
                    <p>Excel表格文件大小应小于5Mb。</p>
                    <p>员工姓名、性别、年龄、手机号码为必填项。</p>
                    <p>福利级别为必选项，默认值为1。</p>
                    <p>其他项目为可选项。</p>
                    <p>福利额度、现金额度为正整数。</p>
                    <p>福利额度总和、现金额度总和不可大于各自可发放额度。</p>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="add-btn-box"><a id="allsend-ture-btn" class="true-btn" href="#">确认</a><a class="cansel-btn" id="allsend-cansel-btn" href="#">取消</a></div>
    </div>
</div>

<div class="public_popup" id="allsend-suceess" style="height:300px;">
    <div class="pop-title-2">
        <div class="suceess-close-btn allsend-suceess-close" ><i class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<!--<p>文件尺寸超过了系统要求，<br>请审查修改后重新上传！</p>
        <p>第67行、第87行数据重复<br>请审查修改后重新提交！</p>
        <p>第89行、第4列数据格式有误，<br>第102行、第2列数据有误，<br>请修改后重新上传！</p>
        <p>第89行、第4列数据格式有误，<br>第102行、第2列数据有误，<br>请审查修改后重新上传！</p>
        <p>现金额度/(福利额度)总和超过可发放额度，<br> 请检查修改后重新上传！</p>-->
        <p id ="erro_info">批量发放员工操作成功！</p><p id="erro_info1" style="display: none"><br>请审查修改后重新上传！</p>
        <div class="suceess-btn-box"><a class="true-btn allsend-suceess-close" href="#">确认</a></div>
    </div>
</div>
<!--批量发放-弹出框 end-->
</body>
</html>
