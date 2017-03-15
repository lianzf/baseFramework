package com.framework.mapper;

import org.apache.ibatis.annotations.Param;

import com.framework.base.BaseMapper;
import com.framework.po.SysUsers;


public interface SysUserMapper extends BaseMapper<SysUsers> {

	void deleteRelByUsersId(@Param("usersId") Integer usersId);

	void deleteUser(@Param("usersId") String usersId);

	SysUsers getUserByUserAccount(@Param("userAccount") String userAccount);

	SysUsers getUserInfo(SysUsers sysUsers);

	SysUsers isExistsUser(@Param("userAccount") String userAccount);

	void updateUserInfo(@Param("userAccount") String loginUser);

	void updateUserPass(@Param("userAccount") String userAccount, @Param("userPassword") String userPassword);

}
