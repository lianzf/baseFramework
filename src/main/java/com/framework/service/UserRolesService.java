package com.framework.service;

import java.util.List;

import com.framework.po.SysRoles;


public interface UserRolesService {

	List<SysRoles> getRolesByUserAccount(String userAccount);

}
