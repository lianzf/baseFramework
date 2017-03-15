package com.framework.po;

import java.io.Serializable;

public class SysRolesRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer rolesResId;
	private Integer resourcesId;
	private Integer rolesId;

	public Integer getResourcesId() {
		return resourcesId;
	}

	public Integer getRolesId() {
		return rolesId;
	}

	public Integer getRolesResId() {
		return rolesResId;
	}

	public void setResourcesId(Integer resourcesId) {
		this.resourcesId = resourcesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

	public void setRolesResId(Integer rolesResId) {
		this.rolesResId = rolesResId;
	}

}
