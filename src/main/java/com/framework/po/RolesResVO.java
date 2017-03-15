package com.framework.po;

import java.io.Serializable;

public class RolesResVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer rolesId;
	private String roleName;
	private String roleDesc;
	private String module;
	private Integer parentId;
	private Integer[] resourceId;

	public Integer getRolesId() {
		return rolesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer[] getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer[] resourceId) {
		this.resourceId = resourceId;
	}

}
