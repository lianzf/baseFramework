package com.framework.util;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.framework.constant.Constant;

public class StringUtil {

	/**
	 * 
	 * @Author lianzhifei 2016年9月8日 strtoHex 方法描述: 字符串转换为16进制 逻辑描述:
	 * @param s
	 * @return
	 */
	public static String strtoHex(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return "0x" + str;// 0x表示十六进制
	}

	/**
	 * 
	 * @Author lianzhifei 2016年9月8日 deleteBOM 方法描述: 判断返回String中是否存在BOM头，若存在则去除
	 *         逻辑描述:
	 * @param value
	 * @return
	 */
	public static String deleteBOM(String value) {
		String tmpStr2 = new String(value.substring(0, 1));
		if (strtoHex(tmpStr2).equals("0xfeff")) {
			value = new String(value.getBytes(), 3, value.getBytes().length - 3, Charset.defaultCharset());
		}
		return value;
	}

	public static String listToString(List<?> list, String string) {
		return StringUtils.join(list.toArray(), string);
	}

	public static String splitString(String str, String pattern) {
		if (StringUtils.isNotBlank(pattern)) {
			try {
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(str);
				while (m.find()) {
					return m.group();
				}
			} catch (Exception e) {
			}
		} else {
			return str;
		}
		return null;
	}

	/**
	 * 
	 * replaceAccessTokenReg 方法描述: 替换url指定参数的值 逻辑描述:
	 * 
	 * @param url
	 * @param name
	 * @param accessToken
	 * @return
	 * @since Ver 1.00
	 */
	public static String replaceAccessTokenReg(String url, String name, String accessToken) {
		if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(accessToken)) {
			url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + accessToken);
		}
		return url;
	}

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	/** 下划线转驼峰 */
	public static String lineToHump(String str) {
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static Pattern humpPattern = Pattern.compile("[A-Z]");

	/** 驼峰转下划线,效率比上面高 */
	public static String humpToLine(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static String spaceBuild(int length) {
		StringBuilder space = new StringBuilder();
		for(int i = 0; i < length; i++) {
			space.append(Constant.nbsp);
		}
		return space.toString();
	}
}
