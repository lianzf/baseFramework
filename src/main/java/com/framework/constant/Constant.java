package com.framework.constant;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

public class Constant {
	
	// 资源常量
	public static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	public static final String nbsp = "&nbsp;";
	/******** menu管理 *********/
	public static final String MENU_STRING = "menu";//控制左侧菜单显示效果
	public static final String MENU_INDEX = "index";// 首页
	
	public static final String MENU_DICTIONARY = "dictionary"; // 数据字典字符串
	public static final String MENU_SYS_USER = "sysUser"; // 系统用户
	public static final String MENU_SYS_ROLES = "sysRoles"; // 系统角色
	public static final String MENU_SYS_RES = "sysRes"; // 系统资源
}
