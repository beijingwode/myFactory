<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的网商家中心-活动列表</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<!--content begin-->
<div id="content">        
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<c:if test="${userSession.type != 3 || userSession.hasAuth('promotionList')}">
				<li class="curr"><a href="${basePath}/promotion/list.html">活动申请中心</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('myPromotionList')}">
				<li><a href="${basePath}/promotion/mylist.html">申请中的活动</a></li>
			</c:if>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <input id="bmTime" type="hidden" name="bmTime" value="${bmTime}"/>
    <input type="hidden" id="promotionId" name="promotionId" value="${promotionId}"/>
    <div class="right">
        <div class="merchant_info">
        	<div class="process step_1"></div>
            <div class="s1-wrap">
            	<div class="s1-step">第一步 同意条款</div>
                <div class="s1-xy">
                	<div class="s1-xy-scl">
                	<p>
	<br />
</p>
<h3 style="text-align:center;">
	<span style="line-height:1;">平台秒杀活动规则</span>
</h3>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span>
</p>
<h4>
	<span style="line-height:1;">一、店铺准入条件</span>
</h4>
<p class="MsoNormal">
	<span style="line-height:1.5;">1</span><span style="line-height:1.5;">、符合《我的网店铺服务协议》</span><span style="line-height:1.5;">和《我的网平台联盟协议》等平台协议和规则。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">2</span><span style="line-height:1.5;">、符合我的网各类目的行业资质标准。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">3</span><span style="line-height:1.5;">、本年度内，报名店铺</span><span style="line-height:1.5;">30</span><span style="line-height:1.5;">天内退款纠纷不超过六笔。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">4</span><span style="line-height:1.5;">、报名</span><span style="line-height:1.5;">店铺评分必须在</span><span style="line-height:1.5;">4.</span><span style="line-height:1.5;">6</span><span style="line-height:1.5;">分以上</span><span style="line-height:1.5;">。&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">5</span><span style="line-height:1.5;">、报名店铺</span><span style="line-height:1.5;">180</span><span style="line-height:1.5;">日内无任何虚假交易处罚。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">6</span><span style="line-height:1.5;">、报名店铺本年度内无出售假冒商品违规处罚。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">7、以上除违规类准入条件，类目若有特殊情况，可根据书面说明，另行处理。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;</span>
</p>
<h4>
	<span style="line-height:1;">二、商品报名条件</span>
</h4>
<p class="MsoNormal">
	<span style="line-height:1.5;">1</span><span style="line-height:1.5;">、疲劳度控制</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;"><span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;">（</span></span><span style="line-height:1.5;">1</span><span style="line-height:1.5;">）一个店铺</span><span style="line-height:1.5;">1</span><span style="line-height:1.5;">个自然月内最多可以报名</span><span style="line-height:1.5;">6</span><span style="line-height:1.5;">款商品。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;"><span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;">（</span></span><span style="line-height:1.5;">2</span><span style="line-height:1.5;">）从秒杀活动商品报名开始到活动结束前，同一店铺同一商品不允许重复报名。在此期间内，若审核未通过或活动取消，可以再次报名。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;"><span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;">（</span></span><span style="line-height:1.5;">3</span><span style="line-height:1.5;">）不同</span><span style="line-height:1.5;">店铺的同款商品，秒杀活动每次只取秒杀报名价较低者，若商品所有信息都一致，则取报名时间靠前的商品。&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">2、品牌商品必须有品牌方提供的售卖证明、或者商品以报名库存为要求的购买发票、或者有品牌渠道商的资质证明；自有品牌商品提供自有品牌的相关证明。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">3、店铺报名秒杀活动的商品数量必需大于</span><span style="line-height:1.5;">0</span><span style="line-height:1.5;">同时不可大于已上架的该产品的产品总数。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">3.</span><span style="line-height:1.5;">店铺报名秒杀活动成功之后，秒杀商品数量将会冻结库存，活动结束之后释放剩余库存。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">4.报名店铺可自行设置每个</span><span style="line-height:1.5;">ID</span><span style="line-height:1.5;">的购买数量（默认可购任意数量但不大于商品总数）。限购规则：报名秒杀的商品，在设置限购数量时，最多可以设置为限购</span><span style="line-height:1.5;">5</span><span style="line-height:1.5;">件。特殊类目最多可以设置为</span><span style="line-height:1.5;">10</span><span style="line-height:1.5;">件。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">5.店铺报名秒杀活动每次只允许一样商品参与秒杀活动报名。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">6.店铺可以设定店铺秒杀价格（固定金额、折扣），并显示商品原价。报名商品必须为一口价。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">且排期后不得修改商品原价。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">7.&nbsp;</span><span style="line-height:1.5;">报名商品秒杀价不高于近</span><span style="line-height:1.5;">30</span><span style="line-height:1.5;">天商品最低成交价。（若为新品，则不足</span><span style="line-height:1.5;">30</span><span style="line-height:1.5;">天的自商品上架之日起算）。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">8.店铺首次指定秒杀产品后，可修改秒杀产品属性但不可更换其他产品，秒杀活动开始后冻结所有修改权限直到秒杀活动结束（出现操作错误由卖家一方承担）</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">9.</span><span style="line-height:1.5;">报名参加秒杀的商品，必须全场包邮。全场包邮是指：除本规则对特殊类目商品另有规定外，由卖家承担从卖家处发货到买家处的大陆地区首次发货的运费，买家只需支付所挑选商品的相应价格即可。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;"><span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;">a.</span></span><span style="line-height:1.5;">大陆地区，是指除香港、澳门、台湾地区以外的中国所有省、直辖市和自治区。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;b.</span><span style="line-height:1.5;">特殊类目商品，是指家装主材、基础建材，住宅家具、商业</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">办公家具等类目下的大件商品，必须支持包物流，同时家装主材、基础建材、住宅家具类目在提供包物流的基础上必须提供物流配送服务。&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;"><span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="line-height:1.5;">c.</span></span><span style="line-height:1.5;">家装主材、基础建材类目下的大件商品就本规则所附的全国</span><span style="line-height:1.5;">314</span><span style="line-height:1.5;">个主城区提供免费的物流配送服务；住宅家具类目的大件商品就本规则所附的全国</span><span style="line-height:1.5;">314</span><span style="line-height:1.5;">个主城区提供免费的物流配送安装服务。具体城区见后附下表并以其为准，其他城区不做物流配送或配送安装服务免费的强制规定。具体城区见《</span><span style="line-height:1.5;">314</span><span style="line-height:1.5;">个主城区免费配送定义和范围》。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">10.商品图片为</span><span style="line-height:1.5;">640*640</span><span style="line-height:1.5;">白底</span><span style="line-height:1.5;">240K</span><span style="line-height:1.5;">以内，图片清晰，主题明确且美观，不拉伸变形、不拼接，无水印、无</span><span style="line-height:1.5;">logo</span><span style="line-height:1.5;">、无文字信息，支持</span><span style="line-height:1.5;">JPG</span><span style="line-height:1.5;">、</span><span style="line-height:1.5;">JPEG</span><span style="line-height:1.5;">、</span><span style="line-height:1.5;">PNG</span><span style="line-height:1.5;">格式，注：一图一个商品或单个模特。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">11.报名商品图片需保证取得有效版权人或肖像权人等第三方权利人明确授权并有可转授权权利证明。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">12.报名商品标题需为</span><span style="line-height:1.5;">18-24</span><span style="line-height:1.5;">个汉字或者</span><span style="line-height:1.5;">36-48</span><span style="line-height:1.5;">个字符，标题格式为“品牌”</span><span style="line-height:1.5;">+</span><span style="line-height:1.5;">“商品内容”</span><span style="line-height:1.5;">+</span><span style="line-height:1.5;">“商品描述”，对商品有准确清晰的描述，严禁堆砌关键字，不可出现特殊符号。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">13.买家付款后，店铺需按《店铺服务协议》进行发货。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">14.以上报名条件，类目若有特殊情况，可根据书面说明，另行处理。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span>
</p>
<h4>
	<span style="line-height:1;">三、秒杀活动须知及要求</span>
</h4>
<p class="MsoNormal">
	<span style="line-height:1.5;">1.&nbsp;店铺申请秒杀活动，应在活动日期前</span><span style="line-height:1.5;">3</span><span style="line-height:1.5;">至</span><span style="line-height:1.5;">30</span><span style="line-height:1.5;">个工作日内申请，且每次申请只能提交一个活动日期。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">2.&nbsp;</span><span style="line-height:1.5;">我的网平台系统会在符合店铺准入条件和商品准入条件的</span><span style="line-height:1.5;">店铺中抽取</span><span style="line-height:1.5;">100</span><span style="line-height:1.5;">名店主获得秒杀活动资格，活动当天报名人数未满</span><span style="line-height:1.5;">100</span><span style="line-height:1.5;">人取当天报名总数。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">3.店铺成功申请秒杀开始后，不得以缺货等接口将商品于活动期内下架。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">4.</span><span style="line-height:1.5;">平台系统将自动分配</span><span style="line-height:1.5;">店铺申请秒杀日期的活动参与时间段。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">5.</span><span style="line-height:1.5;">活动区原价与常规售卖区原价必须保持一致，在获得秒杀活动资格期间，参与秒杀商品冻结更改原价权限，直到活动当日截止。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">8.</span><span style="line-height:1.5;">秒杀活动开始时间为每日</span><span style="line-height:1.5;">2</span><span style="line-height:1.5;">点，</span><span style="line-height:1.5;">4</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,6</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,8</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,10</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,12</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,14</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,16</span><span style="line-height:1.5;">点，</span><span style="line-height:1.5;">18</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,20</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,22</span><span style="line-height:1.5;">点</span><span style="line-height:1.5;">,24</span><span style="line-height:1.5;">点。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">9.</span><span style="line-height:1.5;">秒杀活动结束时间为商品售罄或当日</span><span style="line-height:1.5;">0:00</span><span style="line-height:1.5;">。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">10.</span><span style="line-height:1.5;">秒杀活动商品更新时间为次日</span><span style="line-height:1.5;">0:01</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span>
</p>
<h4>
	<span style="line-height:1;">四、附则</span>
</h4>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;&nbsp;&nbsp;&nbsp;<span style="line-height:1.5;">&nbsp;</span><span style="line-height:1.5;">店铺参与活动仍需遵守《我的网店铺服务协议》和《我的网平台联盟协议》，本规则未涵盖的内容，按照我的网公布的其它规则执行。招商规则中涉及的特殊类目说明详见本文解读。</span></span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span>
</p>
<h3 style="text-align:center;">
	<span style="line-height:1;">《平台秒杀活动规则解读》</span>
</h3>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">1.</span><span style="line-height:1.5;">限购特殊类目说明：</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特殊类目如下：零食</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">坚果</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">特产、茶</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">咖啡</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">冲饮、水产肉类</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">新鲜蔬果</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">熟食、粮油米面</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">南北干货</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">调味品、酒类、保健食品</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">膳食营养补充食品、传统滋补营养品、</span><span style="line-height:1.5;">OTC</span><span style="line-height:1.5;">药品</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">医疗器械</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">隐形眼镜</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">计生用品、成人用品</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">避孕</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">计生用品、美容护肤</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">美体</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">精油、彩妆</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">香水</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">美妆工具、美发护发</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">假发、女士内衣</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">男士内衣</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">家居服、书籍</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">杂志</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">报纸、音乐</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">影视</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">明星</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">音像、服饰配件</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">皮带</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">帽子</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">围巾。洗护清洁剂</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">卫生巾</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">纸</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">香薰。</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">2.</span><span style="line-height:1.5;">物流大件类商品说明：</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;A.	母婴大件类目：童床</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">餐椅</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">儿童安全座椅</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">童车（推车、学步车、自行车、三轮车、电动车、扭扭车</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">家具大件类商品：住宅家具、商业办公</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">家具类目：高于</span><span style="line-height:1.5;">0.6</span><span style="line-height:1.5;">立方米或大于</span><span style="line-height:1.5;">20</span><span style="line-height:1.5;">公斤</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;B.	建材大件类商品：卫浴陶瓷（浴室柜、坐便器、淋浴房、台盆）、地板、瓷砖、油漆、涂料、门、电线、基础建材大件商品，高于</span><span style="line-height:1.5;">0.6</span><span style="line-height:1.5;">立方米或大于</span><span style="line-height:1.5;">20</span><span style="line-height:1.5;">公斤灯具和安防</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;C.	汽车配件部分类目：指轮胎、轮毂类；另外整车自提</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;D.	运动户外大件类商品：跑步机</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">大型健身器械、踏步机</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">中小型健身器材、自行车整车、电动车</span><span style="line-height:1.5;">/</span><span style="line-height:1.5;">电动车配件</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;E.	数码电器大件类商品：大家电</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;F.	箱包大件类商品：旅行箱</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1;">&nbsp;</span><span style="line-height:1.5;"><span style="line-height:1;"></span><span style="line-height:1;"></span></span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">3.</span><span style="line-height:1.5;">承担物流费用说明：</span>
</p>
<p class="MsoNormal">
	<span style="line-height:1.5;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;指包含店铺发货至消费者确认的收货地所在的地级市物流提货点的费用（地级市到买家收货当地的物流点费用需店铺与买家协商而定）。收货人负责将商品从物流提货点提至收货地址。</span>
</p>
<p>
	<br />
</p>
<p>
	<br />
</p>
					</div>
                    <div class="s-btn-agree">
                        <a href="javascript:toNext();">同意以上条款</a>
                    </div>
                </div> 
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<script type="text/javascript">

$(document).ready(function(){
	selectedHeaderLi("hdgl_header");
});

function toNext(){
	window.location.href = "${basePath}/promotion/productlist.html?bmTime="+$("#bmTime").val()+"&promotionId="+$("#promotionId").val();
}

</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>