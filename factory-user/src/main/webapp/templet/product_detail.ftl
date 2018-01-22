<!doctype html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="description" content="${product.name!}">
    <meta name="keywords" content="${product.name!}">
    <title>我的福利-${product.name!}</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/loginpopup.css">
</head>
<script type="text/javascript" src="/resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="/resources/js/product.js"></script>
<script type="text/javascript" src="/resources/js/application.js"></script>
<body>

<!--top begin-->
<div id="top">
<#include "addCartSuccess.ftl">
<#include "header_01.ftl">
    <div class="nav_line">
        <div class="nav">
            <div class="allproduct_menu"><p>全部商品分类</p></div>
            <!--下拉菜单 begin-->
            <div class="menu_list">
            </div>
            <!--下拉菜单 end-->
            <ul>
                <li><a href="index.html">首页</a></li>
                <li class="active"><a href="#">衣柜</a></li>
                <li><a href="#">厨房</a></li>
                <li><a href="#">精品</a></li>
                <li><a href="/flj/index">内购价</a></li>
            </ul>
        </div>
    </div>
</div>
<!--top end-->

<!--content begin-->
<div id="content">
<#if product??>
    <div class="position">
        <#list clist as c>
            <a href="${c.url!}">${c.name}</a><em>></em>
        </#list>
        <a href="/product/list?cat=${cateid?c}&brand=${productBrand.name!}">${productBrand.name!}</a><em>></em>
        <a href="#">${product.name!}</a>
    </div>

<div class="product_intro">
    <div class="preview">
        <div class="zoombox">        
            <#if product.saleKbn==1>        
    		<div class="picon_box">
    			<div class="picon"><img src="images/picon.png" /></div>
    			<p>原因：${product.saleNote!}</p>
    		</div>
            </#if>
            <#if product.saleKbn==2>        
    		<div class="picon_box">
    			<div class="picon"><img src="images/picon_c.png" /></div>
    			<p>原因：${product.saleNote!}</p>
    		</div>
            </#if>
            <#if product.saleKbn==4>        
    		<div class="picon_box">
    			<div class="picon"><img src="images/picon_z.png" /></div>
    			<p></p>
    		</div>
            </#if>
            <#if product.saleKbn==5>        
    		<div class="picon_box">
    			<div class="picon"><img src="images/picon_t.png" /></div>
    			<p>${product.saleNote!}</p>
    		</div>
            </#if>
            <#list imgmap?keys as key>
                <#if key_index==0>
                    <#assign imgList =imgmap["${key}"] >
                    <#list imgList as imgtop >
                        <#if imgtop_index==0 >
                            <div class="zoompic"><img src="${imgtop.imgUrl!}" width="420" height="416"
                                                      alt="${imgtop.imgUrl!}"/></div>
                        </#if>
                    </#list>
                </#if>
            </#list>
            <div class="sliderbox">
                <div id="btn-left" class="arrow-btn"></div>
                <div class="slider" id="thumbnail">
                    <ul>
                        <#list imgmap?keys as key>
                            <#assign imgList =imgmap["${key}"] >
                            <#list imgList as img >
                                <li class="${img.productSpecificationsId!?c}"><img src="${img.imgUrl!}" width="50"
                                                                                   height="50" alt="${img.imgUrl!}"/>
                                </li>
                            </#list>
                        </#list>
                    </ul>
                </div>
                <div id="btn-right" class="arrow-btn"></div>
            </div>
        </div>
    </div>
    <div class="p_item_inner">
        <div class="p_item_title">
            <h1>${product.fullName!}</h1>
            <div class="p_ad">
                <span>${product.promotion!}</span>
                <input type="text" id="productId" value="${product.id!?c}" style="display: none;"/>
                <strong><i></i><a href="javascript:void(0);">关注</a></strong>
            </div>
        </div>
        <div class="p_item_info">
            <div class="summay">
                <ul>
                    <li>
                    	<div style="width:300px;height:30px;">
	                        <span class="sm_metatit">电商价：</span>
	                        <strong>￥<span id="price">${product.showPrice!}</span></strong>
	                    </div>
                    	<p class="benifit-mark">员工内购价格正在制定中...</p>
                    </li>
                    <li id="productSalesPromotion" class="cx_box" >
                    </li>
                    <li>
                        <span class="sm_metatit">运&nbsp;&nbsp;费：</span>
                        <div class="sm_postage">
                            <div class="sm_price">
                                ￥&nbsp;${product.carriage!?string('0.00')}<#if (product.sendAddress )??>
                                (从${product.sendAddress!}发货) </#if></div>
                        </div>
                    </li>
                    ${limitText!}
                </ul>
            </div>
            <div class="choose">
                <#list smap?keys as key>
                <div class="choose_color">
                	<#--sku key值 -->
                    <div class="c_dt">${key!}：</div>
                    <#--sku value值    sv.image-->
                    <div class="c_dd">
                        <#assign svList =smap["${key}"] >
                        <#assign skuId =mainPriceSKU["${key}"] >
                        <#list svList as sv >
                        	<#-- 图片不为空 -->
                        	<#if (sv.image)??>
                        		<#if "${sv.id!?c}"=="${skuId}">
	                            <div class="c_item selected " id="${sv.id!?c}" style="height:40px;width:40px;padding:0">
	                            <#else>
	                            <div class="c_item " id="${sv.id!?c}" style="height:40px;width:40px;padding:0">
	                            </#if>
	                            <b></b>
	                            <a href="javascript:void(0);"  onclick="check('${sv.id!?c}')" title="${sv.specificationValue!}">
								<img src="${sv.image!}" style="height:40px;width:40px;"></a>
	                        	</div>
                        	<#else>
                        		 <#if "${sv.id!?c}"=="${skuId}">
	                            <div class="c_item selected" id="${sv.id!?c}" onclick="check('${sv.id!?c}')">
	                            <#else>
	                            <div class="c_item" id="${sv.id!?c}" onclick="check('${sv.id!?c}')">
	                            </#if>
	                            <b></b>
	                        	<#-- <input type="text" id="specification" value="${sv.id!?c}" style="display: none;"/> -->
	                            <a href="javascript:void(0);">${sv.specificationValue!}</a>
	                        	</div>
                        	</#if>
                        </#list>
                    </div>
                    </div>
                </#list>
                <input type="hidden" id="specificationId"/>
                <div class="choose_color">
                    <div class="c_dt">库存：</div>
                    <div class="c_dd">
                        <div class="c_num">0</div>
                    </div>
                </div>
                <div class="choose_amount">
                    <div class="c_dt">购买数量：</div>
                    <div class="c_dd">
                        <div class="wrap_input">
                            <a id="decrease" href="javascript:void(0);">-</a>
                            <input class="text_amount" type="text" id="buyCount" value="1">
                            <a id="increase" href="javascript:void(0);">+</a>
                        </div>
                    </div>
                </div>

                <div class="choose_selected">
                    已选择<span id="selectValue">
                    <#list smap?keys as key>
                        <#assign svList =smap["${key}"] >
                        <#list svList as sv >
                            <#if sv_index = 0>
                            ${sv.specificationValue!}&nbsp;
                            </#if>
                        </#list>
                    </#list></span>
                </div>
            </div>
                <div class="item_btn">
                  <#if product.saleKbn!=2 >
                    <div class="buybtn item_btn_01"><a href="javascript:void(0);">加入购物车</a></div>
                  </#if>
                  <#if product.saleKbn==5 && product.empPrice == 0 && product.saleNote?length == 10 >
                    <div class="buybtn item_btn_02"><a href="javascript:void(0);">申请试用</a></div>
                  <#elseif product.saleKbn==2>
                    <div class="buybtn item_btn_02 item_btn_02_2"><a href="javascript:void(0);">直接购买</a></div>
                    <div class="buybtn item_btn_04"><a href="javascript:void(0);">我想领</a></div>
                    <div class="hl_help"><a href="javascript:;" title="点击查看换领规则"><img src="images/hl_help_icon1.png" /></a></div>
                  <#else>
                    <div class="buybtn item_btn_02"><a href="javascript:void(0);">直接购买</a></div>
                  </#if>
                </div>
            </div>
            <div class="p_item_ext">
                <h2>商家：${supplierShop.shopname!}</h2>
                <input type="text" id="supplierId" data="${supplierShop.id!?c}" value="${supplierShop.supplierId!?c}"
                       style="display: none;"/>
                <div class="item_ext_cont">

                    <div class="p_grade">
                        <ul>
                            <li>
                                <span class="grade_lt">评分</span>
                                <span>同行业对比</span>
                            </li>
                            <li>
                                <p class="grade_lt">商品评分：<strong id="shopDescription">5.0</strong>分</p>
                                <p><i class="g_icon_1"></i>16.33%</p>
                            </li>
                            <li>
                                <p class="grade_lt">服务态度：<strong id="shopService">5.0</strong>分</p>
                                <p><i class="g_icon_2"></i>7.45%</p>
                            </li>
                            <li>
                                <p class="grade_lt">物流速度：<strong id="deliverySpeed">5.0</strong>分</p>
                                <p><i class="g_icon_1"></i>15.64%</p>
                            </li>
                        </ul>
                    </div>

                    <div class="item_company">
                        <p>公司：${supplierShop.companyName!}</p>
                        <p>
                            地址：${supplierShop.companyState!}&nbsp;${supplierShop.companyCity!}&nbsp;${supplierShop.companyAddress!} </p>
                    </div>
                    <div class="store_wrap">
                        <div class="into store_btn01">
                            <a href="/shop/${supplierShop.id!?c}">进入店铺</a>
                            <input type="text" id="shopid" value="${supplierShop.id!?c}" style="display: none;"/>
                        </div>
                        <div class="into store_btn02"><a href="javascript:void(0);">收藏店铺</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="detail_wrap">
        <!--left begin-->
        <div class="left">
            <div class="sort">
                <h2>相关分类</h2>
                <div class="sort_list">
                    <ul>
                    </ul>
                </div>
            </div>

            <div class="sort">
                <h2>同类其他品牌</h2>
                <div class="sort_list">
                    <ul>
                    </ul>
                </div>
            </div>

            <div class="sort">
            	<input type="hidden" id="categoryId" value="${product.categoryId!?c}" />
                <h2>浏览商品的用户最后买了</h2>
                <div class="historyproduct">
                    <ul>
                    </ul>
                </div>
            </div>

        </div>
        <!--left end-->

        <!--right begin-->
        <div class="right">
            <div class="pro_tab_trigger">
                <ul>
                    <li class="current"><a href="javascript:void(0);">商品介绍</a></li>
                    <li style="display:none;"><a href="javascript:void(0);">规格参数</a></li>
                    <#if pdlList?size gt 0>
                    <li><a href="javascript:void(0);">包装清单</a></li>
                    <#else>
                    <li style="display:none;><a href="javascript:void(0);">包装清单</a></li>
                    </#if>
                    <li><a href="javascript:void(0);">商品评价<em id="commentsCount2">0</em></a></li>
                    <li><a href="javascript:void(0);">售后保障</a></li>
                </ul>
            </div>
            <!--商品介绍 begin-->
            <div class="pro_panel" style="display:block;">
                <div class="pro_referral">
                    <ul>
                        <li title='${product.name!}'>
                            商品名称：
                            <#if product.name?length gt 30>
                            ${product.name!?substring(0,30)}...
                            <#else>
                            ${product.name!}
                            </#if>
                        </li>
                        <li>商品型号：${product.marque}</li>
                        <li>上架时间：${product.createDate!?datetime}</li>
                        <li>商品毛重：${product.roughWeight!}kg</li>
                        <#list pamap as pam >
                            <li title='${pam.value!}'>${pam.name!}：
                                <#if pam.value?length gt 30>
                                ${pam.value!?substring(0,30)}...
                                <#else>
                                ${pam.value!}
                                </#if>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div class="p_detail_content">
                ${product.introduction!}
                </div>
            </div>
            <!--商品介绍 end-->

            <!--规格参数 begin-->
            <div class="pro_panel">
                <div class="detail_correction">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="p_table">
                        <#list paramap as para >
                            <tr>
                                <td class="tdTitle last_ct">${para.name!}</td>
                                <td class="last_ct">${para.value!}</td>
                            </tr>
                        </#list>
                    </table>
                </div>
            </div>
            <!--规格参数 end-->

            <!--包装清单 begin-->
            <div class="pro_panel">
                <div class="detail_correction">
                    <table width="800px" border="0" cellpadding="0" cellspacing="0" class="p_table qd_table" >
                        <tr>
                            <th class="th1">名称</th>
                            <th class="th2">数量</th>
                        </tr>
                        <#list pdlList as productDetialList >
                            <tr>
                                <td class="td1">${productDetialList.name!}</td>
                                <td class="td2">${productDetialList.num!}</td>
                            </tr>
                        </#list>
                    </table>
                </div>
            </div>
            <!--包装清单 end-->

            <!--商品评价 begin-->
            <div class="pro_panel" id="productComents">
                <div class="p_comment">
                    <div class="p_percent">
                        <div class="p_one_percent">
                            <span>商品评分：</span>
                            <div class="p_percent_star"><span class="current_rating w_1"></span></div>
                            <div class="count" id="goodsRatings">5.0</div>
                        </div>
                        <div class="p_one_percent">
                            <span>服务评分：</span>
                            <div class="p_percent_star"><span class="current_rating w_2"></span></div>
                            <div class="count" id="serviceRatings">5.0</div>
                        </div>
                        <div class="p_one_percent">
                            <span>物流评分：</span>
                            <div class="p_percent_star"><span class="current_rating w_3"></span></div>
                            <div class="count" id="logisticsRatings">5.0</div>
                        </div>
                    </div>
                    <div class="p_rate">
                        <div class="rate">
                            <strong id="praiseDegree1">100%</strong>
                            <span>好评度</span>
                        </div>
                        <div class="percent">
                            <dl>
                                <dt>好评(<span id="praiseDegree2">100</span>%)</dt>
                                <dd>
                                    <div class="p_1"></div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>中评(<span id="nomalDegree">0</span>%)</dt>
                                <dd>
                                    <div class="p_2"></div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>差评(<span id="badDegree">0</span>%)</dt>
                                <dd>
                                    <div class="p_3"></div>
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>
                <div class="p_inner">
                    <ul>
                        <li class="curr">
                            <a href="javascript:void(0);" onclick="comment(1,0);">全部评价（<em
                                    id="commentsCount3">0</em>）</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);" onclick="comment(1,1);">好评（<em id="praiseCount">0</em>）</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);" onclick="comment(1,2);">中评（<em id="nomalCount">0</em>）</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);" onclick="comment(1,3);">差评（<em id="badCount">0</em>）</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!--商品评价 end-->

            <!--售后保障 begin-->
            <div class="pro_panel">
                <p class="service"><span>我的网承诺</span></p>
                <div class="service-img"><img src="images/service.jpg" width="828" height="82" alt="service"></div>
                <p class="service"><span>售后服务</span></p>
                <div class="service-cont">
                    <p>${product.afterService!}</p>
                </div>
                <p class="service"><span>退货流程</span></p>
                <div class="service-img"><img src="images/service-step.jpg" width="829" height="325" alt="service-img">
                </div>
                <p class="service"><span>温馨提示</span></p>
                <div class="service-cont">
                    <p>亲爱的顾客，为保障您的权益，请您对配送商品查验确认合格后签收，如有问题，请及时与商家联系。如需退货，请将包装一并寄回哦。</p>
                </div>
                <p class="service"><span>特别申明</span></p>
                <div class="service-cont">
                    <p>本站商品信息均来自于我的网商家，其真实性、准确性和合法性由信息发布者（商家）负责。本站不提供任何保证，并不承担任何法律责任。因厂家会在没有</p>
                    <p>任何提前通知的情况下更改产品包装、产地或者一些附件，本站不能确保客户收到的货物与网站图片、产地、附件说明完全一致，网站商品的功能参数仅供参考，请以实物为准。若本站没有及时更新，请您谅解！</p>
                </div>
            </div>
            <!--售后保障 end-->
        </div>
        <!--right end-->
    </div>
</#if>
</div>
    <!--content end-->
    <!--help begin-->
<#include "login.ftl">
<#include "footer.ftl">
    <!--footer end-->
    <script type="text/javascript" src="/resources/js/top_ewm.js"></script>
    <script type="text/javascript" src="/resources/js/product_detail.js"></script>
</body>
</html>