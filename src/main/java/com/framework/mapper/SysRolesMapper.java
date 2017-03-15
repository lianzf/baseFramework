package com.framework.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.base.BaseMapper;
import com.framework.po.SysRoles;

public interface SysRolesMapper extends BaseMapper<SysRoles> {

	List<SysRoles> getAllRoles();

	List<SysRoles> getRoleNotExistsUser(@Param("roleArr") String roleArr);

	List<SysRoles> getUserRoles(@Param("usersId") String usersId);

	SysRoles isExistsRoles(@Param("roleName") String rolesName);

}
