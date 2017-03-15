package com.framework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mapper.SysResourceMapper;
import com.framework.po.SysResourceVO;
import com.framework.po.SysResources;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Override
	public int count(Map<String, Object> conditionMap) throws Exception {
		return sysResourceMapper.count(conditionMap);
	}

	@Override
	public List<SysResources> loadList(Map<String, Object> conditionMap) throws Exception {
		// TODO Auto-generated method stub
		return sysResourceMapper.getListByMap(conditionMap);
	}

	@Override
	public void insert(SysResources sysResources) throws Exception {
		sysResourceMapper.insert(sysResources);
	}

	@Override
	public void update(SysResources sysResources) throws Exception {
		sysResourceMapper.update(sysResources);
	}

	@Override
	public SysResources getResourceById(String resourceId) throws Exception {
		return sysResourceMapper.getById(Integer.valueOf(resourceId));
	}

	@Override
	public List<SysResources> getResourceList() throws Exception {
		return sysResourceMapper.getResourceList();
	}

	@Override
	public List<SysResourceVO> getAllRes(String rolesId) throws Exception {
		List<SysResources> urList = null;
		List<SysResourceVO> listvo = new ArrayList<SysResourceVO>();// 将转换后的对象放入此集合
		if(StringUtils.isNotBlank(rolesId)) {
			urList = sysResourceMapper.getResByRolesId(rolesId);
			String resArr = "";
			for(SysResources ur : urList) {
				listvo.add(converVO(ur, rolesId));
				resArr += (ur.getResourceId() + ",");
			}
			if(StringUtils.isNotBlank(resArr)) {
				List<SysResources> reses = sysResourceMapper.getResNotExistsRoles(resArr.substring(0, resArr.length() - 1));
				if(reses != null && reses.size() > 0) {
					for(SysResources ur : reses) {
						listvo.add(converVO(ur, null));
					}
				}
			}
			else {
				List<SysResources> reses = sysResourceMapper.getResourceListAll();
				for(SysResources ur : reses) {
					listvo.add(converVO(ur, null));
				}
			}
		}
		else {
			List<SysResources> reses = sysResourceMapper.getResourceListAll();
			for(SysResources ur : reses) {
				listvo.add(converVO(ur, null));
			}
		}
		return listvo;
	}

	public SysResourceVO converVO(SysResources ur, String rolesId) {
		SysResourceVO vo = new SysResourceVO();
		vo.setResourceType(ur.getResourceType());
		vo.setResourceString(ur.getResourceString());
		vo.setResourceName(ur.getResourceName());
		vo.setResourceId(ur.getResourceId());
		vo.setResourceDesc(ur.getResourceDesc());
		vo.setParentId(ur.getParentId());
		if(StringUtils.isNotBlank(rolesId)) {
			vo.setRolesId(Integer.valueOf(rolesId));
		}
		return vo;
	}

	@Override
	public List<SysResourceVO> getFirstMenu(String rolesId) throws Exception {
		List<SysResourceVO> listvo = new ArrayList<SysResourceVO>();// 将转换后的对象放入此集合
		List<SysResources> reses = sysResourceMapper.getFirstMenu();
		List<SysResources> urList = sysResourceMapper.getResByRolesId(rolesId);
		int i = 0;
		for(SysResources ur : reses) {
			for(SysResources sr : urList) {
				if(ur.getResourceId().equals(sr.getResourceId())) {
					i++;
				}
			}
			if(i > 0) {
				listvo.add(converVO(ur, rolesId));
				i = 0;
			}
			else {
				listvo.add(converVO(ur, null));
			}

		}
		return listvo;
	}

}
