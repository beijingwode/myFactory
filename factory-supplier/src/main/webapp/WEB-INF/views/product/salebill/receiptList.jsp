<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>对账单总单</title>
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
    <style>
        table tbody tr td {
            text-align: center;
            height: 30px;
        }

        table thead th {
            border-right: 1px solid #e6e8ea;
            background: #e9e7e7;
            height: 30px;
        }

        .td_bor td {
            border: 1px solid #e6e8ea;
            border-left: none;
        }

        .td_bor_g td {
            background: #f4f3f3;
            border-right: 1px solid #e6e8ea;
        }
 #divExpress{width:100%;padding:0;}
#divExpress p{height:24px;line-height:24px;color:#6a6a6a;padding-left:20px;}
#divExpress p span{padding-left:20px;}
#divExpress .es_box{height:30px;margin-top:20px;}
#divExpress .es {width:200px;float:left;}
#divExpress .es span{padding-left:0px;font-size:14px;}
#divExpress .dt{font-size:14px;height:40px;line-height:40px;margin-top:10px;border-top:1px solid #f2f1f1}
#divExpress .cont_box1{width:100%;background:none;padding-top:30px;}

#divExpress .cont_box1 p{width:380px;margin:10px 0 0 50px;height:30px;padding:0;font-size:14px;}
#divExpress .cont_box1 p span{height:30px;width:70px;text-align:right;padding-right:10px;display:block;float:left;font-size:14px;line-height:30px;}
#divExpress .cont_box1 p em{height:30px;width:280px;line-height:30px;float:left;font-size:12px;font-style:normal}
#divExpress .cont_box1 p input{width:250px;margin:0}
#divExpress .cont_box1 select{width:158px;height:30px;color:#6a6a6a;}
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
  
        /*
        css 注释：
        只对table td设置左与上边框；
        对table设置右与下边框；
        为了便于截图，我们将css 注释说明换行排版
        */
    </style>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/gotoSaleBillList.html">对账管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/account.html">现金账户</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/transferFlow.html">提现记录</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('commissionRefund')}">
                <li><a href="${basePath}/commissionRefund/gotoCommissionRefundList.html">佣金返还</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li class="curr"><a href="${basePath}/saleBill/receiptList.html">发票管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/baseEdit.html">结算设置</a></li>
            </c:if>
        </ul>
    </div>
    <!--left end-->
    <!--right begin-->


    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/saleBill/gotoSaleBillList.html">结算管理</a><em>></em>
            <a href="javascript:void(0);">发票管理</a>
        </div>
        <div class="sale_wrap">
            <form id="sub_form" action="${basePath}/saleBill/receiptList.html" method="post">
        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>

                <div class="sale_info">
                    <div class="sale_one">
                        <div class="sale_option">
                            <div class="title">对账单ID：</div>
                            <input class="pubilc_input f218" type="text" name="saleBillIds" value="${query.saleBillIds}" maxLength="20">
                        </div>
                        <div class="sale_option">
                            <div class="title">发票状态：</div>
                            <input type="hidden" id="statusTemp" value="${query.status}">
                            <select class="pubilc_input f226" id="status" name="status">
                                <option value="">全部</option>
                                <option value="2">已开出</option>
                                <option value="3">已寄出</option>
                                <option value="4">申请退回</option>
                                <option value="5">拒绝退回</option>
                                <option value="6">同意退回</option>
                                <option value="7">退回已寄出</option>
                                <option value="8">退回成功</option>
                            </select>
                        </div>
                        <div class="sale_option">
                            <div class="title">开票日期：</div>
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="startCreateDate" name="startCreateDate" value="${query.startCreateDate}" onClick="WdatePicker()"/><span>到</span>
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="endCreateDate" name="endCreateDate" value="${query.endCreateDate}" onClick="WdatePicker()"/>
                        </div>
                    </div>

                    <div class="clear"></div>
                    <div class="btnwrap">
                        <div class="checkbtn"><a href="javascript:void(0);" onclick="formSubmit('1');">查询</a></div>
                        <div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
                    </div>
                </div>
            </form>
            <div class="sale_list_wrap">
                <div class="sale_content">
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                        <th style="width:75px;">开票日期</th>
                        <th style="width:70px;">发票号码</th>
                        <th style="width:90px;">金额合计</th>
                        <th style="width:65px;">状态</th>
                        <th style="min-width:95px;">邮寄信息</th>
                        <th style="min-width:110px;">退回邮寄信息</th>
                        <th style="min-width:170px;">拒绝退回理由</th>
                        <th style="min-width:200px;">对账单id</th>
                        <th style="min-width:65px;">操作</th>
                        </thead>
                        <tbody>
               			  <c:forEach items="${page.list}" var="i" varStatus="status">
                            <tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
                            	<td><fmt:formatDate value="${i.createDate}" pattern="yyyy-MM-dd"/></td>
                            	<td>${i.code}</td>
                                <td><fmt:formatNumber value="${i.amount}" type="currency" pattern="0.00"/></td>
                                <td>
                                    <c:choose>
						    			<c:when test="${i.status =='2'}">
						    			 	已开出
						    			</c:when>
						    			<c:when test="${i.status =='3'}">
						    			 	已寄出
						    			</c:when>
						    			<c:when test="${i.status =='4'}">
						    			 	申请退回
						    			</c:when>
						    			<c:when test="${i.status =='5'}">
						    			 	拒绝退回
						    			</c:when>
						    			<c:when test="${i.status =='6'}">
						    			 	同意退回
						    			</c:when>
						    			<c:when test="${i.status =='7'}">
						    			 	退回已寄出
						    			</c:when>
						    			<c:when test="${i.status =='8'}">
						    			 	退回成功
						    			</c:when>
						    		</c:choose>
                                </td>
                                <td>
                                    <c:if test="${i.status>2}"><a href="javascript:showExpressInfo('${i.sendExpressType}','${i.sendExpressNo}');">查看物流</a></c:if>
                                </td>
                                <td>
                                    <c:if test="${i.status>6}"><a href="javascript:showExpressInfo('${i.returnExpressType}','${i.returnExpressNo}');">查看退回物流</a></c:if>
                                </td>
                                <td>
                                	${i.rejectNote}&nbsp;
                                </td>
                                <td>
                                	${i.saleBillIds}
                                </td>
                                <td>
                                    <div class="operate">
                                        <c:if test="${i.status==3}"><a href="javascript:toReturn(${i.id});">申请退回</a></c:if>
                                        <c:if test="${i.status==6}"><a href="javascript:toSendReturn(${i.id});">退回邮寄</a></c:if>
                                        &nbsp;
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            <!-- 分页位置 start--> 
            <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
            </div>
            
	        <div class="explain_box">
	        	<span><i>*</i>&nbsp;发票申请须知：</span>
	        	<ul>
	        		<li>（1） 开票类型：增值税普通发票</li>
	        		<li>（2）申请开票金额：当佣金结算金额满500元后（包括500元）可申请开票，佣金结算未满500元时，需累计至500元可申请开票，当年佣金不满500元时，年底可统一申请开票。如有特殊申请开票问题，可致平台处理。 </li>
	        		<li>（3）发票开具及寄出时间：平台自收到开票申请起10个工作日内开具并寄出发票。</li>
	        		<li>（4）变更发票抬头：发票抬头与入驻商户的公司名称一致,如需更改名称，首页中点击商户信息进行修改，公司名称经平台审核通过后，发票抬头自动更改。</li>
	        		<li>（5）开具的发票有误或其他问题需要重新开具：当月开出的发票（看发票上的开具时间）只要在当月内（以我的网平台实际收到发票时间为准）可将发票退回重新开具，隔月发票退回需开具证明加盖公司公章，与发票同时退回。</li>
	        		<li>（6）开具的发票未收到:可在发票开具后台查看发票当前处理状态及快递查询（平台寄出发票后由于物流信息查询原因，请在平台寄出快递3个月内查询），如确认未收到，请致平台核实并处理。</li>	        		
	        	</ul>
	        </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--物流信息-->
<div class="shop_popup" id="shop_popup">
	<div class="popup_title">
    	<span>邮寄信息</span>
        <label onclick="hiddenObjById('shop_popup');"><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
   	<div class="popup_cont" id="divLoading" style="padding-right:0px;"><img alt="加载中" src="<%=static_resources %>images/loading_updateproduct.gif" style="margin:20px 40% 40px 40%"></div>
	<div class="popup_cont" id="divExpress">
		<div class="cont_box">
		</div>
	</div>
   
    <div class="clear"></div>
  	<div id="ajaxErrMsg" class="box_msg" style="color: red;"></div>        
        <div class="popup_btn1" style="padding-bottom:20px;">
            <a href="javascript:void(0);" onclick="hiddenObjById('shop_popup');">关闭</a>
        </div>
	</div> 
</div>
<!--物流信息end-->

<!--申请退回-->
<div class="shop_popup" id="return_popup" ">
	<div class="popup_title">
    	<span>申请发票退回</span>
        <label onclick="hiddenObjById('return_popup');"><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
   	<div class="popup_cont" id="divExpress" style="padding:0">
		<div class="cont_box cont_box1">
		<p><span>退回理由:</span><input  id="returnId" type="hidden"/><input class="pubilc_input " id="returnNote" type="text" maxlength="80" /> </p>
		</div>
	</div>
   
    <div class="clear"></div>
  	<div id="ajaxErrMsg_return" class="box_msg" style="color: red;padding-left:152px;"></div>        
    <div class="popup_btn1" style="padding-bottom:20px;">
            <a href="Javascript:void(0);" onclick="return returnSubmit();">确认</a>
            <a href="javascript:void(0);" onclick="hiddenObjById('return_popup');">取消</a>
        </div>
	</div> 
</div>
<!--申请退回end-->

<!--邮寄退回-->
<div class="shop_popup" id="send_return_popup" style="width:500px;">
	<div class="popup_title">
    	<span>发票退回</span>
        <label onclick="hiddenObjById('send_return_popup');"><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
   	<div class="popup_cont" id="divExpress"  style="padding:0">
		<div class="cont_box cont_box1">
			<p><span>退回地址：</span><em style="line-height:20px;">北京市朝阳区崔各庄乡草场地美术馆46号院  北京我的网科技有限公司</em></p>
			<p><span>联系人：</span><em>闫陶陶</em></p>
			<p><span>联系电话：</span><em>010-56208251</em></p>
			<p style=""><span>快递公司：</span>
				<select id="returnExpressType">
		        <c:forEach var="item" items="${ecs }">
		        	<option value="${item.id }">${item.name }</option>
		        </c:forEach>
				</select>
			</p>
			
			<p style="margin-top:15px;"><span>运单号：</span><input id="sendReturnId" style="width:150px;" type="hidden"/><input class="pubilc_input " style="width:150px;" id="sendReturnNo" type="text" maxlength="20" /></p>
			
		</div>
	</div>
   
    <div class="clear"></div>
  	<div id="ajaxErrMsg_send_return" class="box_msg" style="color: red;padding-left:152px;"></div>        
        <div class="popup_btn1" style="padding-bottom:20px;">
            <a href="Javascript:void(0);" onclick="return sendReturn();">确认</a>
            <a href="javascript:void(0);" onclick="hiddenObjById('send_return_popup');">取消</a>
        </div>
	</div> 
</div>
<!--邮寄退回end-->

<script type="text/javascript">
var jsBasePath='${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_salebill_receiptList.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>