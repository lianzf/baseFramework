package com.framework.po;

import java.io.Serializable;

public class SysRolesVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer usersId;
	private Integer rolesId;
	private String roleDesc;
	private String rolesName;

	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public Integer getRolesId() {
		return rolesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

	public String getRolesName() {
		return rolesName;
	}

	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
