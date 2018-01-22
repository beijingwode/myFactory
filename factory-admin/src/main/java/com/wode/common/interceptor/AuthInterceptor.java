package com.wode.common.interceptor;

import com.wode.common.beetl.utils.BeetlUtils;
import com.wode.common.constant.Constant;
import com.wode.common.stereotype.NoCheckLogin;
import com.wode.sys.model.SysResource;
import com.wode.sys.model.SysUser;
import com.wode.sys.utils.SysUserUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Map;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI(); //请求路径
        String ctx = request.getContextPath();
        String path = uri.replace(ctx,"");
        
        if(handler instanceof HandlerMethod) {
            //注解 排除方法  NoCheckLong
    		HandlerMethod handlerMethod = (HandlerMethod) handler;
    		Method method = handlerMethod.getMethod();
    		//fixed by zhangtong ,that when user logout do not rerecord session and cookie;
    		NoCheckLogin nocheck = method.getAnnotation(NoCheckLogin.class);
    		if (nocheck != null) {
    			return true;
    		}
        }
        //if(!ignorePath.contains(path)){
            //获得session中的登陆用户
            SysUser sessionUser = SysUserUtils.getLoginUser();

           
            if (sessionUser == null ) { // 转到登陆页面
                response.sendRedirect(ctx + "/notlogin");
                return false;
            } else {
            	
                Map<String, SysResource> allRes = BeetlUtils
                        .getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
                String perPath = path.substring(1);
                SysResource sysResource = allRes.get(perPath);
                //判断如果url不在数据库中，则默认都有权限访问
                if (sysResource == null
                        || Constant.RESOURCE_COMMON.equals(sysResource.getCommon())) {
                    return true;
                }
                
                //实时的权限验证,检测用户认证是否改变，如果认证改变则重置，否则不进行任何操作
                SysUserUtils.setUserAuth();
                
                //从缓存中的用户权限 map<url:resource>
                Map<String, SysResource> userRes = SysUserUtils.getUserResources();
                if (userRes.containsKey(perPath)) { //有权限则过
                    return true;
                } else { //没有权限跳到未有权限
                    response.sendRedirect(ctx + "/notauth");
                    return false;
                }
            }
        //}
       // return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
