package com.wode.factory.user.web.controller.unionpay;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zoln on 2015/8/31.
 */
public class UnionPayConfig {

	public static final String REQUEST_URL = "https://gateway.95516.com/gateway/api/frontTransReq.do";

	public static final String QUERY_URL = "https://gateway.95516.com/gateway/api/queryTrans.do";

	public static final String REDIRECT_URL = "http://www.wd-w.com/unionpay/front";

	public static final String NOTIFY_URL = "http://www.wd-w.com/unionpay/back";

	public static final String PARTNER = "898111448161629";

	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

	protected static Map<String, String> post(Map<String, String> submitFromData,String requestUrl) {
		String resultString = "";
		//发送
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, "UTF-8");
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, "UTF-8")) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			System.out.println("打印返回报文：" + resultString);
		}
		return resData;
	}

	public static Map<String, String> signData(Map<String, ?> contentData) {
		Map.Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Map.Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(submitFromData, "UTF-8");
		return submitFromData;
	}
}
