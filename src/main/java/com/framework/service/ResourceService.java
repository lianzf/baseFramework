package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.po.SysResourceVO;
import com.framework.po.SysResources;

public interface ResourceService {

	int count(Map<String, Object> conditionMap) throws Exception;

	List<SysResources> loadList(Map<String, Object> conditionMap) throws Exception;

	void insert(SysResources sysResources) throws Exception;

	void update(SysResources sysResources) throws Exception;

	SysResources getResourceById(String resourceId) throws Exception;

	List<SysResources> getResourceList() throws Exception;

	List<SysResourceVO> getAllRes(String rolesId) throws Exception;

	List<SysResourceVO> getFirstMenu(String rolesId) throws Exception;

}
