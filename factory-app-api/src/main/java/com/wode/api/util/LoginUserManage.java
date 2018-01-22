package com.wode.api.util;

import com.wode.factory.model.UserFactory;

public class LoginUserManage {

	/** 线程内共享 ThreadLocal通常是全局的，支持泛型 */  
    private static ThreadLocal<UserFactory> threadLocal = new ThreadLocal<UserFactory>();  
    
    public static void setUser(UserFactory u){
    	threadLocal.set(u);
    }
    
    
    public static UserFactory getUser(){
    	return threadLocal.get();
    }
    
    public static void removeUser(){
    	threadLocal.remove();
    }
}
