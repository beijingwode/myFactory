<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家入驻</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recruitment.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<style>

.add_new {
float:right;
   
    padding: 0 8px;
    border: 1px solid #2b8dff;
    height: 23px;
    text-align: center;
    border-radius: 3px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
}
.add_new a:link, .add_new a:visited {
    display: block;
    font: 12px/23px "Microsoft YaHei";
    color: #2b8dff;
}
.ft_lt{float:left;}
</style>
</head>
<body>
<!--top begin-->
<div id="top">
	<ul class="progress progress-5">
        <li class="t1 current">填写公司信息</li>
        <li class="t2 current">创建店铺</li>
        <li class="t3 current">选择类目</li>
        <li class="t4-2 current">上传资质</li>
        <li class="t5 current">确认合同</li>
    </ul>	
</div>
<!--top end-->

<!--content begin-->
<div id="content">
	<div class="recruitment_title">确认合同<span class="red">所有资质必须清晰可辨并加盖贵司红章/彩章<em>（即在资质复印件上加盖贵司自己的红章，再扫描或拍照上传）</em></span></div>
    <div class="recruitment_cont">
    	<div class="store col">我的网服务信息</div>
        <div class="contact_lt">
        	<span>合作模式：</span>
            <strong>${mode }</strong>
        </div>
        <div class="contact_lt">
        	<span>甲方：</span>
            <strong>${comName }</strong>
            <span>乙方：</span>
            <strong>北京我的网科技有限公司</strong>
        </div>
        <div class="contact_lt">
        	<span>店铺名称：</span>
            <strong>${ss.shopname}&nbsp;&nbsp;&nbsp;&nbsp;(<c:if test="${empty ss.type || ss.type eq 0 }">专营店</c:if><c:if test="${ss.type eq 1 }">专卖店</c:if><c:if test="${ss.type eq 2 }">旗舰店</c:if>)</strong>
        </div>
        <div class="contact_lt">
        	<span>协议期限：</span>
            <strong><fmt:formatDate value="${begin}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${end}" pattern="yyyy-MM-dd"/></strong>
        </div>
        <div class="contact_lt">
        	<span>保证金金额：</span>
            <strong class="redcolor">${cashDeposit}元</strong>
        </div>
        <div class="contact_lt">
        	<span>消费者保障基金：</span>
            <strong class="redcolor">0.1%</strong>
        </div>
        <div class="contact_lt" >
        	<span>经营范围：</span>
            <div class="c_table" style="overflow:hidden;width:1000px;margin-left:40px;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr class="tr_title">
                <td>经营品类</td>
                <td>佣金扣点(%)</td>
                <td>优惠</td>
                <td>经营品牌名称</td>
              </tr>
              <c:forEach var="item" items="${csList}">
	              <tr>
	                <td>${item.categoryName}</td>
	                <td>${item.commissionRatio}</td>
	                <td>返佣</td>
	                <td><c:forEach var="item1" items="${item.productBrandList}">${item1.name} </c:forEach></td>
	              </tr>
              </c:forEach>
            </table>
			</div>
        </div>
        <div class="explain_box">
        	<span><i>*</i>&nbsp;注</span>
        	<p>佣金返还细则：</p>
        	<ul>
        		<li>1、我的网在以账期为单位结算技术服务费（也称佣金）的同时，向商家在“我的网平台”的现金储值账户中将佣金按比例进行折让并计入商家的现金储值总额，可用于再次向员工进行发放。</li>
        		<li>（1） 在当期结算账期内，品牌商商家以商家自身员工购买其他商家商品并完成妥投的销售额（不含内购券）加和为基数，按所购买商品的佣金点位的百分之五十计算，以现金储值形式返还。（员工所购买商品佣金点位为2%及以下的，商家不享受佣金返还。）</li>
        		<li>（2） 若商家为品牌代理商，商家介绍品牌员工成功注册并上线我的网平台的，首年度可获得员工所购买的其他商家的商品的佣金点位的百分之五十的佣金返还，以现金储值形式存入商家在我的网平台账户中。（员工所购买商品佣金点位为2%及以下的，商家不享受佣金返还。）</li>
        		<li>（3） 佣金返还总额合计不超过商家实缴佣金的一半。</li>
        	</ul>
        </div>
<!--         <div class="page"> -->
<!--         	<a href="#">前一页</a> -->
<!--             <a href="#">1</a> -->
<!--             <a href="#">2</a> -->
<!--             <a href="#">3</a> -->
<!--             <span>...</span> -->
<!--             <a href="#">45</a> -->
<!--             <a href="#">后一页</a> -->
<!--             <span>共45页</span> -->
<!--             <input type="text" name="" class="input_text"> -->
<!--             <span>页</span> -->
<!--             <input type="button" value="确定" class="input_btn"> -->
<!--			<wodepageform:PageFormTag pageSize="${result.size}"  totalPage="${result.totalPage}" currentPage="${result.page}" url=""/> -->
<!--         </div> -->
		
        <div class="clear"></div>
        <!--<div class="protocol"><input class="r_radio" id="checkbox" type="checkbox" name="checkbox" value="" onclick="checkcss();">我已经阅读并同意<a href="#">《我的网商家服务协议》</a></div>-->
    </div>
    <div class="recuitment_btn">
    	<!--当所有信息填完后提交按钮变为红色的样式-->
    	<div class="recuitment_btn01" id="sub" onclick="papa(1);"><a href="javascript:void(0);">提交</a></div>
        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa(2);"><a href="javascript:void(0);">返回上传资质</a></div>
    </div>
    <form id="sub_form" action="${basePath}/supplier/torecruitmentcontract.html" method="post">
    	 <input type="hidden" id="pages" name="pages" value="${pages}"/>
     	 <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
    </form>
</div>
<!--content end-->
	<%@ include file="/commons/footer.jsp" %>

  <script type="text/javascript">
var jsBasePath='${basePath}';

</script>
    <script type="text/javascript" src="<%=static_resources %>js/product_supplier_recruitmentcontract.js"></script>
</body>