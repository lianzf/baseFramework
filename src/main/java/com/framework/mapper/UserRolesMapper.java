package com.framework.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.framework.base.BaseMapper;
import com.framework.po.SysRoles;
import com.framework.po.SysUsersRoles;


public interface UserRolesMapper extends BaseMapper<SysUsersRoles> {

	List<SysRoles> getRolesByUserAccount(@Param("userAccount") String userAccount);

	void insertUserRole(List<Map<String, Integer>> list);

}
