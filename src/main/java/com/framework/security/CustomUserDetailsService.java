package com.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.framework.po.SysRoles;
import com.framework.po.SysUsers;
import com.framework.service.UserRolesService;
import com.framework.service.UserService;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private UserRolesService userRolesServcie;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, LockedException {
		// TODO Auto-generated method stub
		String userAccount = "";
		String passWord = "";
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// 可以从数据库获取到用户信息
		SysUsers sysUsers = userService.getUserByUserAccount(username);
		List<SysRoles> roles = null;
		if(sysUsers != null) {
			Integer enabled = sysUsers.getEnabled();
			if(enabled.equals(0)) {
				throw new UsernameNotFoundException("用户已被禁用");
			}
			userAccount = sysUsers.getUserAccount();
			passWord = sysUsers.getUserPassword();
			roles = userRolesServcie.getRolesByUserAccount(username);
		}
		else {
			throw new UsernameNotFoundException("用户名不存在");
		}
		if(roles != null) {
			SysRoles role = null;
			for(int i = 0; i < roles.size(); i++) {
				role = roles.get(i);
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
		}
		User user = new User(userAccount, passWord, true, true, true, true, authorities);
		return user;
	}

}
