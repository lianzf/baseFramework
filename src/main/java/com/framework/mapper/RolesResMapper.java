package com.framework.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.framework.base.BaseMapper;
import com.framework.po.SysResources;
import com.framework.po.SysRolesRes;


public interface RolesResMapper extends BaseMapper<SysRolesRes> {

	int deleteById(@Param("rolesId") Integer rolesId);

	List<SysResources> getResByRolesName(@Param("roleName") String roleName);

	void insertRoleRes(List<Map<String, Integer>> list);

}
