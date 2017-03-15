package com.framework.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.base.BaseMapper;
import com.framework.po.SysResources;


public interface SysResourceMapper extends BaseMapper<SysResources> {

	List<SysResources> getAllRes();

	List<SysResources> getFirstMenu();

	List<SysResources> getResByRolesId(@Param("rolesId") String rolesId);

	List<SysResources> getResNotExistsRoles(@Param("resArr") String resArr);

	List<SysResources> getResourceList();

	List<SysResources> getResourceListAll();

}
