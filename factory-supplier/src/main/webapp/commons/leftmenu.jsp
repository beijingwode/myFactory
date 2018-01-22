<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("qygl_header");
    });


</script>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>
<!--left begin-->
<div class="left">
    <div class="left_store_list">
        <ul>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('basiManage')}">
                <li class="cl_show"><a href="javascript:;"><i class="icon01"></i>基本信息管理</a>
                    <ul class="twomenu">
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('enterpriseInfo')}">
                            <li><a href="${basePath}/company/enterprise/getEnterpriseInfo.html" class="active">企业基本信息</a></li>
                            <!-- class="curr" -->
                        </c:if>
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('enterpriseStructure')}">
                            <li><a href="${basePath}/company/enterprise/structure/getEnterprise.html">公司结构管理</a></li>
                        </c:if>
                    </ul>

                </li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('staffManage')}">
                <li class="cl_show">
                    <a href="javascript:;">
                        <i class="icon01">
                        </i>
                        员工管理
                    </a>
                    <ul class="twomenu">
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('staffManage')}">
                        <li><a href="${basePath}/company/emp/page.html">员工管理</a></li>
                       </c:if>
                         <c:if test="${userSession.type != 3 || userSession.hasAuth('takeOrder')}">
                        	<li><a href="${basePath}/company/emp/takeOrderPage.html">员工自提订单</a></li>
                         </c:if>
                    </ul>

                </li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('fuliManage')}">
                <li class="cl_show"><a href="javascript:;"><i class="icon01"></i>福利管理</a>
                    <ul class="twomenu">
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('fuliBenefit')}">
                            <li><a href="${basePath}/company/benefit/getEntSeasonAct.html">福利额度总览</a></li>
                        </c:if>
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('applyBenefit')}">
                            <li><a href="${basePath}/company/benefit/getApplyBenefitRecord.html">额度申请</a></li>
                        </c:if>
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('cash')}">
                            <li><a href="${basePath}/company/benefit/toSaveCash.html">现金储值</a></li>
                        </c:if>
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('fuliGiven')}">
                            <li><a href="${basePath}/company/benefit/page.html">福利发放</a></li>
                        </c:if>
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('fuliFlow')}">
                            <li><a href="${basePath}/company/benefit/entBenefitFlow">福利流水</a></li>
                        </c:if>
                    </ul>

                </li>
            </c:if>
           <%--  <c:if test="${userSession.type != 3 || userSession.hasAuth('saleFlowManage')}">
                <li class="cl_show"><a href="javascript:;"><i class="icon01"></i>交易流水管理</a>
                    <ul class="twomenu">
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('tradFlow')}">
                            <li><a href="${basePath}/company/tradeFlow/page.html">员工交易流水</a></li>
                        </c:if>
                    </ul>

                </li>
            </c:if> --%>

            <c:if test="${userSession.type != 3 || userSession.hasAuth('unionManage')}">
                <li class="cl_show"><a href="javascript:;"><i class="icon01"></i>友盟企业管理</a>
                    <ul class="twomenu">
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('comUnion')}">
                            <li><a id="entmanage" href="${basePath}/company/transfer/to_entmanage.html">友盟企业管理</a></li>
                        </c:if>
                        <c:if test="${userSession.type != 3 || userSession.hasAuth('fuliTransfer')}">
                            <li><a id="stamps" href="${basePath}/company/transfer/to_transferPage.html">划拨内购券额度</a></li>
                        </c:if>
                    </ul>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<!--left end-->
