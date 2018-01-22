package com.wode.factory.supplier.util;

import com.alibaba.fastjson.JSONObject;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserIm;
import com.wode.factory.outside.service.EasemobIMService;
import com.wode.factory.outside.service.ServiceFactory;

public class EasemobIMUtils {

	static EasemobIMService os = ServiceFactory.getEasemobIMService(Constant.OUTSIDE_SERVICE_URL);
	
    private static JSONObject initUserinfos(UserFactory uf,UserIm im) {
    	JSONObject uinfos = new JSONObject();
    	uinfos.put("username", im.getOpenimId());
    	uinfos.put("password", im.getOpenimPw());
    	//uinfos.put("nickname", uf.getNickName());
        return uinfos;
    }
    
    /**
     * 更新用户昵称
     * @param username
     * @param nickname
     * @return
     */
    public static boolean updUserNickName(String username ,String nickname) {
    	return os.updUserNickName(username, nickname, EasemobIMService.APP_TYPE_SHOP, "myFactory", false, null);
    }

    public static UserIm addImUser(UserFactory uf) {
    	UserIm im = new UserIm();
    	im.setUserId(uf.getId());
    	im.setAppType(EasemobIMService.APP_TYPE_SHOP);
    	im.setAppKey(EasemobIMService.APP_KEY_SHOP);
    	im.setOpenimId(EasemobIMService.APP_TYPE_SHOP+uf.getId());
    	im.setOpenimPw("openimPw");
    	
		String json = initUserinfos(uf,im).toJSONString();
		
		if(os.addImUser(json, EasemobIMService.APP_TYPE_SHOP, "myFactory", false, null)){
			return im;
		} else {
			return null;
		}
    }

}
