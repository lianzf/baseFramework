package com.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mapper.RolesResMapper;
import com.framework.mapper.SysRolesMapper;
import com.framework.po.RolesResVO;
import com.framework.po.SysRoles;
import com.framework.po.SysRolesVO;


@Service("rolesServcie")
public class RolesServiceImpl implements RolesService {

	@Autowired
	private SysRolesMapper sysRolesMapper;
	@Autowired
	private RolesResMapper rolesResMapper;

	@Override
	public int count(Map<String, Object> conditionMap) throws Exception {
		return sysRolesMapper.count(conditionMap);
	}

	@Override
	public List<SysRoles> loadList(Map<String, Object> conditionMap) throws Exception {
		return sysRolesMapper.getListByMap(conditionMap);
	}

	@Override
	public void insert(RolesResVO sysRoles) throws Exception {
		SysRoles roles = new SysRoles();
		roles.setRoleName(sysRoles.getRoleName());
		roles.setRoleDesc(sysRoles.getRoleDesc());
		roles.setParentId(sysRoles.getParentId());
		roles.setModule(sysRoles.getModule());
		sysRolesMapper.insert(roles);
		Integer rolesId = roles.getRolesId();
		Integer[] reses = sysRoles.getResourceId();
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> map;
		if(roles != null && reses != null && reses.length > 0) {
			for(int i = 0; i < reses.length; i++) {
				map = new HashMap<String, Integer>();
				map.put("resourcesId", reses[i]);
				map.put("rolesId", rolesId);
				list.add(map);
			}
			rolesResMapper.insertRoleRes(list);
		}

	}

	@Override
	public void update(RolesResVO sysRoles) throws Exception {
		SysRoles roles = new SysRoles();
		roles.setRoleName(sysRoles.getRoleName());
		roles.setRoleDesc(sysRoles.getRoleDesc());
		roles.setParentId(sysRoles.getParentId());
		roles.setModule(sysRoles.getModule());
		roles.setRolesId(sysRoles.getRolesId());
		sysRolesMapper.update(roles);
		rolesResMapper.deleteById(sysRoles.getRolesId());
		Integer rolesId = roles.getRolesId();
		Integer[] reses = sysRoles.getResourceId();
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> map;
		if(roles != null && reses != null && reses.length > 0) {
			for(int i = 0; i < reses.length; i++) {
				map = new HashMap<String, Integer>();
				map.put("resourcesId", reses[i]);
				map.put("rolesId", rolesId);
				list.add(map);
			}
			rolesResMapper.insertRoleRes(list);
		}
	}

	@Override
	public SysRoles getRolesById(String rolesId) throws Exception {
		return sysRolesMapper.getById(Integer.valueOf(rolesId));
	}

	@Override
	public boolean isExistsRoles(String rolesId, String rolesName) throws Exception {
		SysRoles roles = sysRolesMapper.isExistsRoles(rolesName);

		boolean bool = false;
		if(roles == null) {
			bool = true;
		}
		else if(roles != null && StringUtils.isBlank(rolesId)) {
			bool = false;
		}
		else if(roles != null && StringUtils.isNotBlank(rolesId)) {
			int id = roles.getRolesId();

			if(String.valueOf(id).equals(rolesId)) {
				bool = true;
			}
			else {
				bool = false;
			}
		}

		return bool;
	}

	@Override
	public List<SysRolesVO> getAllRoles(String usersId) throws Exception {
		List<SysRoles> urList = null;
		List<SysRolesVO> listvo = new ArrayList<SysRolesVO>();// 将转换后的对象放入此集合
		if(StringUtils.isNotBlank(usersId)) {
			urList = sysRolesMapper.getUserRoles(usersId);
			String roleArr = "";
			for(SysRoles ur : urList) {
				listvo.add(converVO(ur, usersId));
				roleArr += (ur.getRolesId() + ",");
			}
			if(StringUtils.isNotBlank(roleArr)) {
				List<SysRoles> roles = sysRolesMapper.getRoleNotExistsUser(roleArr.substring(0, roleArr.length() - 1));
				if(roles != null && roles.size() > 0) {
					for(SysRoles ur : roles) {
						listvo.add(converVO(ur, null));
					}
				}
			}
			else {
				List<SysRoles> reses = sysRolesMapper.getAllRoles();
				for(SysRoles ur : reses) {
					listvo.add(converVO(ur, null));
				}
			}
		}
		else {
			List<SysRoles> roles = sysRolesMapper.getAllRoles();
			for(SysRoles ur : roles) {
				listvo.add(converVO(ur, null));
			}
		}
		return listvo;
	}

	public SysRolesVO converVO(SysRoles ur, String usersId) {
		SysRolesVO vo = new SysRolesVO();
		vo.setRoleDesc(ur.getRoleDesc());
		vo.setRolesId(ur.getRolesId());
		vo.setRolesName(ur.getRoleName());
		if(StringUtils.isNotBlank(usersId)) {
			vo.setUsersId(Integer.valueOf(usersId));
		}
		return vo;
	}

}
