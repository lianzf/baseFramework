<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" import="com.framework.constant.Constant"%>
<%@ page language="java" import="org.springframework.security.core.context.SecurityContext"%>
<%@taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%
	String menu = "";
	if (request.getRequestURI().indexOf("index.jsp") > 0) {
		menu = Constant.MENU_INDEX;
	} else {
		
		menu = request.getAttribute("menu") != null ? request.getAttribute("menu").toString() : null;
	}
%>
<div class="page-sidebar nav-collapse collapse">
	<!-- BEGIN SIDEBAR MENU -->
	<input id="path" type="hidden" value="<%=menu%>" />
	<ul class="page-sidebar-menu">
		<li>
			<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
			<div class="sidebar-toggler hidden-phone"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		</li>
		<li id="<%=Constant.MENU_INDEX %>" class="start">
			<a href="<%=request.getContextPath()%>/index.jsp"> 
				<i class="icon-home"></i> 
				<span class="title">首页</span> 
				<span class="selected"></span>
			</a>
		</li>
		<li class="last">
		<%
		System.out.println(((SecurityContext) request.getSession(true).getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName());
		%>
			<s:authorize url="AUTHMANAGE">
				<a href="#"><i class="icon-cogs"></i><span class="title">系统权限</span><span class="arrow"></span></a>
			</s:authorize>
			<ul class="sub-menu">
				<s:authorize url="/dictionary/getPage">
					<li id="<%=Constant.MENU_DICTIONARY%>"><a href="<%=request.getContextPath()%>/dictionary/getPage">数据字典</a></li>
				</s:authorize>
				<s:authorize url="/sysUser/getPage">
					<li id="<%=Constant.MENU_SYS_USER%>"><a href="<%=request.getContextPath()%>/sysUser/getPage">用户管理</a></li>
				</s:authorize>
				<s:authorize url="/sysRoles/getPage">
					<li id="<%=Constant.MENU_SYS_ROLES%>"><a href="<%=request.getContextPath()%>/sysRoles/getPage">角色管理</a></li>
				</s:authorize>
				<s:authorize url="/sysRes/getPage">
					<li id="<%=Constant.MENU_SYS_RES%>"><a href="<%=request.getContextPath()%>/sysRes/getPage">资源管理</a></li>
				</s:authorize>
			</ul>
		</li>
	</ul>
	<!-- END SIDEBAR MENU -->
</div>