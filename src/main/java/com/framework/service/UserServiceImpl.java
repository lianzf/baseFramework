package com.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mapper.SysUserMapper;
import com.framework.mapper.UserRolesMapper;
import com.framework.po.SysUsers;
import com.framework.po.SysUsersVO;


/**
 * 
 * <p>
 * 用户服务类
 * </p>
 * @Author lianzhifei
 * @Date [2015年6月25日]
 * @Version V1.0
 * @修改记录
 * <pre>
 * 版本号   修改人  修改时间     修改内容描述
 * ----------------------------------------
 * V1.0	2015年6月25日	新建文件.
 * 
 * </pre>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private UserRolesMapper userRolesMapper;

	@Override
	public int count(Map<String, Object> conditionMap) {
		return sysUserMapper.count(conditionMap);
	}

	@Override
	public List<SysUsers> loadList(Map<String, Object> conditionMap) {
		return sysUserMapper.getListByMap(conditionMap);
	}

	@Override
	public boolean isExistsUser(String usersId, String userAccount) {
		SysUsers users = sysUserMapper.isExistsUser(userAccount);

		boolean bool = false;
		if(users == null) {
			bool = true;
		}
		else if(users != null && StringUtils.isBlank(usersId)) {
			bool = false;
		}
		else if(users != null && StringUtils.isNotBlank(usersId)) {
			int id = users.getUsersId();

			if(String.valueOf(id).equals(usersId)) {
				bool = true;
			}
			else {
				bool = false;
			}
		}

		return bool;
	}

	@Override
	public SysUsers getUserInfoByUserAccount(String userAccount) {
		return sysUserMapper.isExistsUser(userAccount);
	}

	@Override
	public void insert(SysUsersVO sysUsers) {
		SysUsers user = new SysUsers();
		user.setCreateTime(sysUsers.getCreateTime());
		user.setEnabled(sysUsers.getEnabled());
		user.setIsDelete(sysUsers.getIsDelete());
		user.setLoginTime(sysUsers.getLoginTime());
		user.setUserAccount(sysUsers.getUserAccount());
		user.setUserDesc(sysUsers.getUserDesc());
		user.setUserName(sysUsers.getUserName());
		user.setUserPassword(sysUsers.getUserPassword());
		sysUserMapper.insert(user);
		Integer usersId = user.getUsersId();
		Integer[] roles = sysUsers.getRolesId();
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> map;
		if(roles != null) {
			for(int i = 0; i < roles.length; i++) {
				map = new HashMap<String, Integer>();
				map.put("usersId", usersId);
				map.put("rolesId", roles[i]);
				list.add(map);
			}
			userRolesMapper.insertUserRole(list);
		}
	}

	@Override
	public void update(SysUsersVO sysUsers) {
		SysUsers user = new SysUsers();
		user.setUsersId(sysUsers.getUsersId());
		user.setEnabled(sysUsers.getEnabled());
		user.setUserAccount(sysUsers.getUserAccount());
		user.setUserDesc(sysUsers.getUserDesc());
		user.setUserName(sysUsers.getUserName());
		user.setUserPassword(sysUsers.getUserPassword());
		sysUserMapper.update(user);
		userRolesMapper.deleteById(sysUsers.getUsersId());
		Integer usersId = user.getUsersId();
		Integer[] roles = sysUsers.getRolesId();
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		Map<String, Integer> map;
		if(roles != null) {
			for(int i = 0; i < roles.length; i++) {
				map = new HashMap<String, Integer>();
				map.put("usersId", usersId);
				map.put("rolesId", roles[i]);
				list.add(map);
			}
			userRolesMapper.insertUserRole(list);
		}
		sysUserMapper.deleteRelByUsersId(sysUsers.getUsersId());
	}

	@Override
	public SysUsers getUserById(String usersId) {
		return sysUserMapper.getById(Integer.valueOf(usersId));
	}

	@Override
	public SysUsers getUserInfo(SysUsers sysUsers) {
		return sysUserMapper.getUserInfo(sysUsers);
	}

	@Override
	public void updateUserInfo(String loginUser) {
		sysUserMapper.updateUserInfo(loginUser);
	}

	@Override
	public void updateUserPass(String userAccount, String encodeByMD5) {
		sysUserMapper.updateUserPass(userAccount, encodeByMD5);
	}

	@Override
	public void deleteUser(String usersId) {
		sysUserMapper.deleteUser(usersId);
	}

	@Override
	public SysUsers getUserByUserAccount(String userAccount) {
		return sysUserMapper.getUserByUserAccount(userAccount);
	}

}
