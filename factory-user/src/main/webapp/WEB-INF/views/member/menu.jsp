<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="Me_nav">
	<p class="Me_nav_title">个人中心</p>
	<div class="Me_nav_con">
		<p class="M_list_title">
			<i></i>我的交易
		</p>
		<ul class="Me_nav_list">
			<li <c:if test="${menu eq 'myorders' }">class="surr"</c:if>><a href="/member/myorders">我的订单</a></li>
			<li <c:if test="${menu eq 'mybalance' }">class="surr"</c:if>><a href="/member/center">我的余额</a></li>
			<li <c:if test="${menu eq 'myticket' }">class="surr"</c:if>><a href="/member/myticket">我的内购券</a></li>
			<li><a href="/cart/list">我的购物车</a></li>
			<li <c:if test="${menu eq 'myhlOrder' }">class="surr"</c:if>><a href="/member/myhlOrder">我的换领</a></li>
			<li <c:if test="${menu eq 'myinvoice' }">class="surr"</c:if>><a href="/invoice/myinvoice">我的发票</a></li>
			<li <c:if test="${menu eq 'mycomments' }">class="surr"</c:if>><a href="/member/mycomments">我的评价</a></li>
			<li ${menu eq 'rights' ? 'class="surr"' : '' }><a href="/member/myrights">我的维权</a></li>
		</ul>
	</div>

	<div class="Me_nav_con">
		<p class="M_list_title">
			<i class="My_concern"></i>我的关注
		</p>
		<ul class="Me_nav_list">
			<li <c:if test="${menu eq 'personalStore' }">class="surr"</c:if>><a href="<%=basePath %>member/personalStore">关注店铺</a></li>
			<li <c:if test="${menu eq 'personalProduct' }">class="surr"</c:if>><a href="<%=basePath %>member/personalProduct">关注商品</a></li>
		</ul>
	</div>

	<div class="Me_nav_con border">
		<p class="M_list_title">
			<i class="My_Accounts"></i>账户管理
		</p>
		<ul class="Me_nav_list">
			<li <c:if test="${menu eq 'personalInformation' }">class="surr"</c:if>><a href="<%=basePath %>member/personalInformation">个人信息</a></li>
			<li <c:if test="${menu eq 'security' }">class="surr"</c:if>><a href="<%=basePath %>member/security">安全设置</a></li>
			<li <c:if test="${menu eq 'userAddress' }">class="surr"</c:if>><a href="<%=basePath %>member/userAddress">收货地址</a></li>
		</ul>
	</div>

</div>