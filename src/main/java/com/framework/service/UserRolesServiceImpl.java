package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mapper.UserRolesMapper;
import com.framework.po.SysRoles;


@Service("userRolesServcie")
public class UserRolesServiceImpl implements UserRolesService {

	@Autowired
	private UserRolesMapper userRolesMapper;

	@Override
	public List<SysRoles> getRolesByUserAccount(String userAccount) {
		return userRolesMapper.getRolesByUserAccount(userAccount);
	}

}
