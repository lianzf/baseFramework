package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.po.SysUsers;
import com.framework.po.SysUsersVO;


public interface UserService {

	int count(Map<String, Object> conditionMap);

	List<SysUsers> loadList(Map<String, Object> conditionMap);

	boolean isExistsUser(String usersId, String userAccount);

	void insert(SysUsersVO sysUsers);

	void update(SysUsersVO sysUsers);

	SysUsers getUserById(String usersId);

	SysUsers getUserInfo(SysUsers sysUsers);

	SysUsers getUserInfoByUserAccount(String userAccount);

	void updateUserInfo(String loginUser);

	void updateUserPass(String userAccount, String encodeByMD5);

	void deleteUser(String usersId);

	SysUsers getUserByUserAccount(String username);

}
