package com.framework.po;

import java.io.Serializable;

public class SysResources implements Serializable {

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

	public String getModule() {
		return module;
	}

	public Integer getParentId() {
		return parentId;
	}

	public Integer getPriority() {
		return priority;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getResourceString() {
		return resourceString;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceString(String resourceString) {
		this.resourceString = resourceString;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

}
