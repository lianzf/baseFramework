package com.framework.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * 系统用户表
 * </p>
 * 修改记录:
 * (从这里添加，没有则删除此项)
 * 作者 lianzhifei
 * 日期 2015 2015年6月25日
 */
public class SysUsers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer usersId;
	private String userAccount;
	private String userName;
	private String userPassword;
	private String userDesc;
	private Integer enabled = 1; // 0-禁用 1-启用
	private Date createTime;
	private Date loginTime;
	private Integer isDelete = 0; // 伪删除操作 0-未删除 1-已删除

	public Date getCreateTime() {
		return createTime;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
}
