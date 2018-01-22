package com.wode.factory.user.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.TimeUtil;
import com.wode.factory.model.GradeMsgVo;
import com.wode.factory.model.SupplierEvent;
import com.wode.factory.model.SupplierPrize;
import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.model.UserSignRecord;
import com.wode.factory.model.UserWeixin;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.user.service.SupplierEventService;
import com.wode.factory.user.service.SupplierPrizeService;
import com.wode.factory.user.service.UserPrizeRecordService;
import com.wode.factory.user.service.UserSignRecordService;
import com.wode.factory.user.service.UserWeixinService;
import com.wode.factory.user.util.Constant;

@Controller
@RequestMapping("/acticity")
public class EnterpriseActicityController {
	
	
	@Autowired
	private SupplierPrizeService supplierPrizeService;
	
	@Autowired
	private UserSignRecordService userSignRecordService;
	
	@Autowired
	private UserPrizeRecordService userPrizeRecordService;
	
	@Autowired
	private SupplierEventService supplierEventService;
	
	@Autowired
	private UserWeixinService userWeixinService;

	static WxOpenService wxOpen = ServiceFactory.getWxOpenService(Constant.OUTSIDE_SERVICE_URL);
	/**
	 * 跳转企业奖品领取页面
	 * @return
	 */
	@RequestMapping("/toDrawPrizePage{acticityId}")
	@ResponseBody
	public ModelAndView toPrizeTakePage(@PathVariable Long acticityId) {
		ModelAndView model = new ModelAndView("acticity/drawPrize");
		SupplierEvent supplierEvent = supplierEventService.getById(acticityId);
		if(supplierEvent == null) {
			return null;
		}
		model.addObject("acticityId", acticityId);
		model.addObject("supplierEvent", supplierEvent);
		model.addObject("bannerImage", supplierEvent.getPcPageBanner());
		model.addObject("bgBanner", supplierEvent.getPcPageBg());
		return model;
	}
	
	/**
	 * 获取奖品信息
	 * @param acticityId
	 * @return
	 */
	@RequestMapping("/getPrizeMsg")
	@ResponseBody
	public ActResult<Object> getPrizeMsg(Long acticityId) {
		//查询活动信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("acticityId", acticityId);
		//查询抽奖奖品信息
		List<SupplierPrize> prizelist = supplierPrizeService.findPrizeListByMap(map);
		return ActResult.success(prizelist);
	}
	
	/**
	 * 获取抽奖号码
	 * @param acticityId
	 * @return
	 */
	@RequestMapping("/getPrizePhoneMsg")
	@ResponseBody
	public ActResult<Object> getPrizePhoneMsg(Long acticityId) {
		//查询活动信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("acticityId", acticityId);
		//查询抽奖奖品信息
		List<UserSignRecord> userlist = userSignRecordService.getRecordPhoneByMap(map);
		return ActResult.success(userlist);
	}
	
	/**
	 * 添加用户中奖记录并推送微信消息
	 * @param acticityId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/addPrizeRecord")
	@ResponseBody
	public ActResult<String> addPrizeRecord(Long acticityId,Long userId,Integer prizeGrade,Long prizeId,String gradeName) {
		//添加获奖用户记录
		UserSignRecord userSignRecord = userSignRecordService.getById(userId);
		
		UserPrizeRecord userPrizeRecord = new UserPrizeRecord();
		userPrizeRecord.setCreateDate(new Date());
		userPrizeRecord.setStatus(1);
		userPrizeRecord.setLuckyNumber(userSignRecord.getLuckyNumber());
		userPrizeRecord.setName(userSignRecord.getName());
		userPrizeRecord.setPhone(userSignRecord.getPhone());
		userPrizeRecord.setPrizeId(prizeId);
		userPrizeRecord.setPrizeGrade(prizeGrade);
		userPrizeRecord.setActicityId(acticityId);
		userPrizeRecord.setGradeName(gradeName);
		userPrizeRecord.setUserId(userSignRecord.getUserId());
		userPrizeRecordService.save(userPrizeRecord);
		
		userSignRecord.setPrizeId(prizeId);
		userSignRecordService.update(userSignRecord);
		//微信推送中奖用户信息
		UserWeixin userWeixin = userWeixinService.getOneModelByUserId(userSignRecord.getUserId());
		Map<String,Object> paramWX=new HashMap<String,Object>();//微信
		SupplierEvent supplierEvent = supplierEventService.getById(acticityId);//获取活动信息
		SupplierPrize supplierPrize = supplierPrizeService.getById(prizeId);
		paramWX.put("eventTitle", supplierEvent.getEventTitle());
		paramWX.put("prizeName", supplierPrize.getPrizeName());
		paramWX.put("date", TimeUtil.getStringDateShort());
		paramWX.put("first", "恭喜您中奖了");
		paramWX.put("remark", "请尽快到主席台领取相关奖品\n更多员工福利点击详情查看。");
		paramWX.put("url", "http://api.wd-w.com/index_m.html");

	    return wxOpen.templateMsgSend(userWeixin.getOpenId(), WxOpenService.TEMPLATE_TYPE_WIN_PRIZE, userSignRecord.getUserId(), paramWX, true, null);
	}
	
	/**
	 * 获取中奖的用户信息
	 * @param acticityId
	 * @return
	 */
	@RequestMapping("/getUserPrizeRecordMsg")
	@ResponseBody
	public ActResult<Object> getUserPrizeRecordMsg(Long acticityId) {
		//查询中奖信息
		UserPrizeRecord userPrizeRecord = new UserPrizeRecord();
		userPrizeRecord.setActicityId(acticityId);
		List<UserPrizeRecord> userlist = userPrizeRecordService.selectByModel(userPrizeRecord);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("acticityId", acticityId);
		List<GradeMsgVo> list = userPrizeRecordService.findGradeByGroup(map);
		if(list != null && list.size() > 0) {
			for (GradeMsgVo gradeMsgVo : list) {
				List<UserPrizeRecord> userPrizeList = new ArrayList<UserPrizeRecord>();
				for (UserPrizeRecord userPrize : userlist) {
					if(userPrize.getPrizeGrade() == gradeMsgVo.getPrizeGrade()) {
						userPrizeList.add(userPrize);
					}
				}
				gradeMsgVo.setList(userPrizeList);
			}
		}
		return ActResult.success(list);
	} 
	
	/**
	 * 获取活动签到信息
	 * @param acticityId
	 * @return
	 */
	@RequestMapping("/signAllMsg")
	@ResponseBody
	public ActResult<Object> signAllMsg(Long acticityId) {
		//查询中奖信息
		UserSignRecord userSignRecord = new UserSignRecord();
		userSignRecord.setActicityId(acticityId);
		List<UserSignRecord> userlist = userSignRecordService.selectByModel(userSignRecord);
		return ActResult.success(userlist);
	} 
	
	/**
	 * 导出数据信息
	 * @param acticityId
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/export")
	public void export(Long acticityId,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
		UserSignRecord userSignRecord = new UserSignRecord();
		userSignRecord.setActicityId(acticityId);
		List<UserSignRecord> userlist = userSignRecordService.selectByModel(userSignRecord);
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("签到用户"); 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        
        /**
         * 设置样式 start
         * */
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        /**设置样式  end
         * */
        List<String> headers= new ArrayList<String>();
        headers.add("姓名");
        headers.add("手机号");
        headers.add("签到时间");
        /**
         * 
         * 设置订单详情表头 start
         * */
        HSSFRow row = sheet.createRow((int) 0); 
        HSSFCell cellt = row.createCell(0);
        //设置值
        cellt.setCellValue("统计信息：");  
        //设置样式
        cellt.setCellStyle(style);
        row = sheet.createRow((int) 1); 
        for (int i = 0; i < headers.size(); i++) {
        	HSSFCell cell = row.createCell(i);
            //设置值
            cell.setCellValue(headers.get(i));  
            //设置样式
            cell.setCellStyle(style);
		}
        /** 设置订单详情表头 end
         * */
        int currentRow = 1;
		for (UserSignRecord p : userlist) {

			currentRow++;
			int col=0;
            row = sheet.createRow(currentRow); 
            //headers.add("招商人员");
            row.createCell(col++).setCellValue(p.getName());
            //headers.add("已导入员工商家");
            row.createCell(col++).setCellValue(p.getPhone().substring(0, 3)+"****"+p.getPhone().substring(p.getPhone().length()-5, p.getPhone().length()-1));
            //headers.add("商家数量");
            row.createCell(col++).setCellValue(TimeUtil.dateToStr(p.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
		}
		
        // 第六步，将文件存到指定位置  
        //设置相应头信息
        response.addHeader("Content-Disposition", "attachment;"+ getFileNameForSave(request));
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getFileNameForSave(HttpServletRequest request) throws UnsupportedEncodingException {

		String userAgent = request.getHeader("user-agent");
		String filename = "签到人员统计"+ TimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +".xls";
		String new_filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		String rtn = "filename=\"" + new_filename + "\"";
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
				rtn = "filename=\"" + new_filename + "\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
}
