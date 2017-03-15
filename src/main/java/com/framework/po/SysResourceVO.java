package com.framework.po;

import java.io.Serializable;

public class SysResourceVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer resourceId;
	private String resourceName;
	private String resourceDesc;
	private String resourceType;
	private String resourceString;
	private Integer priority;
	private String module;
	private Integer parentId;
	private Integer rolesId;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceString() {
		return resourceString;
	}

	public void setResourceString(String resourceString) {
		this.resourceString = resourceString;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public Integer getRolesId() {
		return rolesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

}
