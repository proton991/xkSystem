package com.fudan.xk.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	public static String salt = "fudan";
	
	public static String md5(String str){
		return DigestUtils.md5Hex(str);
	}
	
	//第一次
	public static String inputToBack(String str){
		return md5(str + salt);
	}
	
	//第二次
	public static String backtToDb(String str, String dbSalt){
		return md5(str + dbSalt);
	}
	
	
	public static String inputToDb(String str, String dbSalt){
		return backtToDb(inputToBack(str), dbSalt);
	}
	
	
}
