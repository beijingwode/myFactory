package com.wode.factory.supplier.util;


 
 import com.baidu.ueditor.define.ActionMap;
 import com.baidu.ueditor.define.BaseState;
 import com.baidu.ueditor.define.State;
 import com.baidu.ueditor.hunter.FileManager;
 import com.baidu.ueditor.hunter.ImageHunter;
 import com.baidu.ueditor.upload.Uploader;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;

 
 
 
 public class ActionEnter
 {
   private HttpServletRequest request = null;
   
   private String rootPath = null;
   private String contextPath = null;
   
   private String actionType = null;
   
   private ConfigManager configManager = null;
   
   public ActionEnter(HttpServletRequest request, String rootPath)
   {
/*  28 */     this.request = request;
/*  29 */     this.rootPath = rootPath;
/*  30 */     this.actionType = request.getParameter("action");
/*  31 */     this.contextPath = request.getContextPath();
/*  32 */     this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
   }
   
 
   public String exec()
   {
/*  38 */     String callbackName = this.request.getParameter("callback");
     
/*  40 */     if (callbackName != null)
     {
/*  42 */       if (!validCallbackName(callbackName)) {
/*  43 */         return new BaseState(false, 401).toJSONString();
       }
       
/*  46 */       return callbackName + "(" + invoke() + ");";
     }
     
/*  49 */     return invoke();
   }
   
 
 
   public String invoke()
   {
/*  56 */     if ((this.actionType == null) || (!ActionMap.mapping.containsKey(this.actionType))) {
/*  57 */       return new BaseState(false, 101).toJSONString();
     }
     
/*  60 */     if ((this.configManager == null) || (!this.configManager.valid())) {
/*  61 */       return new BaseState(false, 102).toJSONString();
     }
     
/*  64 */     State state = null;
     
/*  66 */     int actionCode = ActionMap.getType(this.actionType);
     
/*  68 */     Map<String, Object> conf = null;
     
/*  70 */     switch (actionCode)
     {
     case 0: 
/*  73 */       return this.configManager.getAllConfig().toString();
     
     case 1: 
     case 2: 
     case 3: 
     case 4: 
/*  79 */       conf = this.configManager.getConfig(actionCode);
/*  80 */       state = new Uploader(this.request, conf).doExec();
/*  81 */       break;
     
     case 5: 
/*  84 */       conf = this.configManager.getConfig(actionCode);
/*  85 */       String[] list = this.request.getParameterValues((String)conf.get("fieldName"));
/*  86 */       state = new ImageHunter(conf).capture(list);
/*  87 */       break;
     
     case 6: 
     case 7: 
/*  91 */       conf = this.configManager.getConfig(actionCode);
/*  92 */       int start = getStartIndex();
/*  93 */       state = new FileManager(conf).listFile(start);
     }
     
     
 
/*  98 */     return state.toJSONString();
   }
   
 
   public int getStartIndex()
   {
/* 104 */     String start = this.request.getParameter("start");
     try
     {
/* 107 */       return Integer.parseInt(start);
     } catch (Exception e) {}
/* 109 */     return 0;
   }
   
 
 
 
 
 
   public boolean validCallbackName(String name)
   {
     if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
       return true;
     }
     
     return false;
   }
 }
