package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.po.RolesResVO;
import com.framework.po.SysRoles;
import com.framework.po.SysRolesVO;


public interface RolesService {

	int count(Map<String, Object> conditionMap) throws Exception;

	List<SysRoles> loadList(Map<String, Object> conditionMap) throws Exception;

	void insert(RolesResVO sysRoles) throws Exception;

	void update(RolesResVO sysRoles) throws Exception;

	SysRoles getRolesById(String rolesId) throws Exception;

	boolean isExistsRoles(String rolesId, String rolesName) throws Exception;

	List<SysRolesVO> getAllRoles(String usersId) throws Exception;

}
