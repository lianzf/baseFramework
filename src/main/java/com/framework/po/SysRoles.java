package com.framework.po;

import java.io.Serializable;

public class SysRoles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer rolesId;
	private String roleName;
	private String roleDesc;
	private String module;
	private Integer parentId;

	public String getModule() {
		return module;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public String getRoleName() {
		return roleName;
	}

	public Integer getRolesId() {
		return rolesId;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

}
