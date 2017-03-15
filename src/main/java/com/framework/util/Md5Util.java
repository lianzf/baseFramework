package com.framework.util;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

public class Md5Util {

	// 十六进制下数字到字符的映射数组

	@SuppressWarnings("unused")
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static void main(String[] args) throws Exception {
	    System.out.println(encodeByMD5("123456"));
    }
	
	/**
	 * MD5 加密
	 * 
	 * @param originString
	 * 待加密的内容
	 * @return MD5加密后的串, 字母部分是大写的
	 * @throws Exception
	 */
	public static String encodeByMD5(String originString) throws Exception {
		if(!StringUtils.isEmpty(originString)) {
			// 创建具有指定算法名称的信息摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
			byte[] results = md.digest(originString.getBytes());
			// 将得到的字节数组变成字符串返回
			String resultString = MsgGenUtil.buf2hex(results);

			return resultString.toLowerCase();
		}

		return null;
	}

	public static String encodeByMD5(byte[] originbyte) throws Exception {
		if(originbyte.length > 0) {
			// 创建具有指定算法名称的信息摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] results = md.digest(originbyte);
			// 将得到的字节数组变成字符串返回
			String resultString = MsgGenUtil.buf2hex(results);
			return resultString.toUpperCase();
		}
		return null;
	}

	/**
	 * 
	 * GBK编码
	 * 
	 * @param originString
	 * @return String
	 * @throws Exception
	 */
	public static String encodeByMD5GBK(String originString) throws Exception {
		if(!StringUtils.isEmpty(originString)) {
			// 创建具有指定算法名称的信息摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
			byte[] results = md.digest(originString.getBytes("GBK"));
			// 将得到的字节数组变成字符串返回
			String resultString = MsgGenUtil.buf2hex(results);

			return resultString.toUpperCase();

		}

		return null;
	}

}
