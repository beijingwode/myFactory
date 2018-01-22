/**
 * 
 */
package com.wode.common.util;

import java.util.UUID;

/**
 * 节点id生成器
 * @author haisheng
 *
 */
public class IdGenerate {
	
	
	public static String getUUID(){
		   String s = UUID.randomUUID().toString();    
		   //去掉“-”符号    
		   return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);    
    }
	
	

	
	
	/**
	 * 生成树状结构节点的Id(每个级别以字符分割)
	 * @param maxid
	 * 节点所在级别的最大Id
	 * @param pid
	 *  父节点id
	 * @param split
	 * 分割符号
	 * @return
	 */
	public static String treeId(String  maxid,String pid,final String  split){
		if(maxid==null&&pid==null){
			maxid="0";
		}else if(maxid==null&&pid!=null){
			maxid=pid+split+"0";
		}
		int index=maxid.lastIndexOf(split);
		if(index==-1){//一级节点无分隔符
			int num=Integer.parseInt(maxid);
			num++;
			return String.valueOf(num);
		}else{
			int num=Integer.parseInt(maxid.substring(index+1));
			num++;
			return maxid.substring(0,index+1)+num;
		}
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		//System.out.println(treeId("2_11","_"));
		System.out.println(getUUID());
	}
	
	
}
