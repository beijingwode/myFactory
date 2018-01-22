package com.wode.factory.user.service.impl;

import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.factory.user.service.ExpressCollectingPointsService;
@Service("expressCollectingPointsService")
public class ExpressCollectingPointsServiceImpl implements ExpressCollectingPointsService {

	@Override
	public ActResult<Object> collectingPoints(Double lng, Double lat,String address, Boolean status) {
		ActResult act = new ActResult();
//		Map param= new HashMap();
//		Map paramMap=new HashMap();
//		paramMap.put("action", "near");//{"action":"near","sname":"","lng":116.509784,"lat":40.006504}
//		paramMap.put("sname", "distriBution");
//		if(StringUtils.isNullOrEmpty(address)){
//			paramMap.put("lng", lng);
//			paramMap.put("lat", lat);
//		}else{
//			paramMap.put("address", address);
//		}
//		param.put("content", JsonUtil.toJson(paramMap));
//		String response=HttpClientUtil.sendHttpRequest("post", Constant.EXPRESS_API_URL+"SimpleJsonApi/bus.do", param);
//		JSONObject obj = JSONObject.parseObject(response);
//		JSONArray ja = obj==null?null:JSONArray.parseArray(obj.getString("body"));
//		if(ja==null || ja.isEmpty()){
			act.setSuccess(false);
			act.setMsg("没有找到代收点");
			return act;
//		}else{
//			//标示为true的时候，提取距离最近的一个代收点
//			if(status==true){
//				JSONObject jo = JSONObject.parseObject(ja.get(0).toString());
//				Map<String,String> map = new HashMap<String, String>();
//				map.put("lng", jo.getString("lng"));
//				map.put("lat", jo.getString("lat"));
//				map.put("address", jo.getString("address"));
//				map.put("phone",jo.getString("phone"));//"phone", "(电话:"+jo.getString("phone")+")"
//				map.put("id", jo.getString("id"));
//				act.setSuccess(true);
//				act.setData(map);
//				return act;
//			}else{
//				//地图展示数组串
//				List<CollectingPointVo> ll = new ArrayList<CollectingPointVo>();
//				for(Object o : ja){
//					//代收点实例
//					CollectingPointVo c = JSON.parseObject(o.toString(), CollectingPointVo.class);
//					ll.add(c);
////					//地图展示数组串
////					List<String> l = new ArrayList<String>();
////					JSONObject jo = JSONObject.parseObject(o.toString());
////					l.add(jo.getString("lng"));
////					l.add(jo.getString("lat"));
////					l.add("地址:"+jo.getString("address")+"<br/>电话:"+jo.getString("phone"));
////					ll.add(JSON.toJSONString(l));
//				}
//				act.setSuccess(true);
//				act.setData(ll);
//				return act;
//			}
//		}
	}
}
