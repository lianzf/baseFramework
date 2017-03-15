package com.framework.po;

import java.io.Serializable;

public class SysUsersRoles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer usersRolesId;
	private Integer rolesId;
	private Integer usersId;

	public Integer getRolesId() {
		return rolesId;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public Integer getUsersRolesId() {
		return usersRolesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public void setUsersRolesId(Integer usersRolesId) {
		this.usersRolesId = usersRolesId;
	}

}
