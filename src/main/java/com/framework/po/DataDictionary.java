package com.framework.po;

import java.io.Serializable;
import java.util.Date;

public class DataDictionary implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String grouping; // 数据分类（分组），自己定义

	private String name; // 数据的key

	private String value; // 数据的value

	private String description; // 描述，主要解释分组数据

	private Date createTime;

	private Date updateTime;

	public Date getCreateTime() {
		return createTime;
	}

	public String getDescription() {
		return description;
	}

	public String getGrouping() {
		return grouping;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getValue() {
		return value;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DataDictionary [id=" + id + ", grouping=" + grouping + ", name=" + name + ", value=" + value
				+ ", description=" + description + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
