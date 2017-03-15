package com.framework.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.BaseAction;
import com.framework.constant.Constant;
import com.framework.po.SysResources;
import com.framework.po.SysRolesVO;
import com.framework.service.RolesResService;
import com.framework.service.RolesService;
import com.framework.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginAction extends BaseAction {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private RolesResService rolesResServcie;
	@Autowired(required = true)
	private RolesService rolesServcie;

	@RequestMapping("userLogin")
	public void userLogin() {
		String userAccount = request.getParameter("sysUsers.userAccount");
		String userPassword = request.getParameter("sysUsers.userPassword");
		try {
			loadResourceDefine();
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAccount, userPassword);
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(authentication);
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
			System.out.println("获取到得资源json:" + getLoginUser());
			userService.updateUserInfo(getLoginUser());
			setJsonSuccess(response, null, "登陆成功",RESULT_TYPE_CLOSE_BOX_FUNCTION);
		}
		catch(UsernameNotFoundException e) {
			setJsonFail(response, null, 1100, "用户名不存在或被禁用");
		}
		catch(AuthenticationException e) {
			setJsonFail(response, null, 1100, "用户名或密码错误");
		}
		catch(Exception e) {
			setJsonFail(response, null, 1100, "系统错误");
		}
	}
	
	public void logout() {

	}
	
	private void loadResourceDefine() throws Exception {
		// 获取权限(角色)列表
		List<SysRolesVO> roles = rolesServcie.getAllRoles(null);
		Constant.resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		// 循环所有角色，查出对应的资源
		for(SysRolesVO role : roles) {
			ConfigAttribute ca = new SecurityConfig(role.getRolesName());
			List<SysResources> sr = rolesResServcie.getResByRolesName(role.getRolesName());

			for(SysResources resource : sr) {
				String url = resource.getResourceString();
				if(Constant.resourceMap.containsKey(url)) {
					Collection<ConfigAttribute> value = Constant.resourceMap.get(url);
					value.add(ca);
					Constant.resourceMap.put(url, value);
				}
				else {
					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
					atts.add(ca);
					Constant.resourceMap.put(url, atts);
				}
			}
		}
	}

	@Override
	public void getList() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getById() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPage() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
