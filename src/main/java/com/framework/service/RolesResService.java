package com.framework.service;

import java.util.List;

import com.framework.po.SysResources;


public interface RolesResService {

	List<SysResources> getResByRolesName(String roleName) throws Exception;

}
