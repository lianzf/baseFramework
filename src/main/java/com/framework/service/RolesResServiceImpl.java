package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mapper.RolesResMapper;
import com.framework.po.SysResources;


@Service("rolesResServcie")
public class RolesResServiceImpl implements RolesResService {

	@Autowired
	private RolesResMapper rolesResMapper;

	@Override
	public List<SysResources> getResByRolesName(String roleName) throws Exception {
		return rolesResMapper.getResByRolesName(roleName);
	}

}
