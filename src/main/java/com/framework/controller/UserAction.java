package com.framework.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.BaseAction;
import com.framework.po.SysRoles;
import com.framework.po.SysUsers;
import com.framework.po.SysUsersVO;
import com.framework.service.UserRolesService;
import com.framework.service.UserService;
import com.framework.util.LogFactory;
import com.framework.util.Md5Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * <p>
 * Description: 用户管理模块
 * </p>
 * 
 * @Author lianzhifei
 * @Date [2015年6月25日]
 * @Version V1.0
 * @修改记录
 * 
 *       <pre>
 * 版本号   修改人  修改时间     修改内容描述
 * ----------------------------------------
 * V1.0	2015年6月25日	新建文件.
 * 
 *       </pre>
 */
@Controller
@RequestMapping("/sysUser")
public class UserAction extends BaseAction {

	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private UserRolesService userRolesServcie;

	@RequestMapping("getList")
	@Override
	public void getList() throws Exception {
		// 获取前台传入参数信息
		String userAccount = request.getParameter("userAccount");
		String userName = request.getParameter("userName");
		// 用于添加查询条件的map
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("userAccount", StringUtils.isNotBlank(userAccount) ? userAccount : null);
		conditionMap.put("userName", StringUtils.isNotBlank(userName) ? userName : null);
		conditionMap.put("startPage", getStart());
		conditionMap.put("pageSize", getIDisplayLength());
		// 根据查询条件查询的的数据信息并获取数据的总量
		int count = userService.count(conditionMap);
		recordsTotal = count;
		// 分页显示上面查询出的数据结果
		List<SysUsers> data = userService.loadList(conditionMap);
		recordsFiltered = recordsTotal;
		recordsDisplay = data.size();
		this.writerToClient(data, iDisplayLength, recordsDisplay, recordsFiltered, recordsTotal, start);
	}

	@RequestMapping("insert")
	@Override
	public void insert(Object obj) throws Exception {
		try {
			SysUsersVO sysUsers = (SysUsersVO) obj;
			sysUsers.setCreateTime(new Date());
			sysUsers.setUserPassword(Md5Util.encodeByMD5(sysUsers.getUserPassword()));
			userService.insert(sysUsers);
			this.writerToClient("操作成功");
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}
	
	@RequestMapping("delete")
	@Override
	public void delete() throws Exception {
		String usersId = request.getParameter("usersId");
		try {
			userService.deleteUser(usersId);
			this.writerToClient("操作成功");
		} catch (Exception e) {
			this.writerToClient("删除失败");
		}
	}

	@RequestMapping("update")
	@Override
	public void update(Object obj) throws Exception {
		try {
			SysUsersVO sysUsers = (SysUsersVO) obj;
			String confirmPassword = request.getParameter("sysUsers.confirmPassword");
			if (StringUtils.isBlank(sysUsers.getUserPassword())) {
				sysUsers.setUserPassword(confirmPassword);
			}
			if (!confirmPassword.equals(sysUsers.getUserPassword())
					&& !StringUtils.isBlank(sysUsers.getUserPassword())) {
				sysUsers.setUserPassword(Md5Util.encodeByMD5(sysUsers.getUserPassword()));
			}
			userService.update(sysUsers);
			this.writerToClient("操作成功");
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	@RequestMapping("getById")
	@Override
	public void getById() throws Exception {
		try {
			String usersId = request.getParameter("usersId");
			SysUsers sysUser = userService.getUserById(usersId);
			Gson g = new GsonBuilder().serializeNulls().create();
			this.writerToClient(g.toJson(sysUser));
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	/**
	 * 
	 * isExistsUser 方法描述: 根据用户账号查看用户是否已存在 逻辑描述:
	 * 添加时直接判断用户是否已存在，修改是判断是否有usersId如果有则为修改
	 * 
	 * @throws Exception
	 * @since Ver 1.00
	 */
	@RequestMapping("isExistsUser")
	public void isExistsUser() throws Exception {
		String usersId = request.getParameter("usersId");
		String userAccount = request.getParameter("userAccount");
		boolean bool = userService.isExistsUser(usersId, userAccount);
		this.writerToClient(bool + "");
	}

	@RequestMapping("getUserInfo")
	public void getUserInfo() throws Exception {
		String userAccount = getLoginUser();
		SysUsers user = userService.getUserInfoByUserAccount(userAccount);
		List<SysRoles> roles = userRolesServcie.getRolesByUserAccount(userAccount);
		Map<String, Object> userInfoMap = new HashMap<String, Object>();
		userInfoMap.put("usersId", user.getUsersId());
		userInfoMap.put("userAccount", user.getUserAccount());
		userInfoMap.put("userDesc", user.getUserDesc());
		userInfoMap.put("userName", user.getUserName());
		userInfoMap.put("roles", roles);
		Gson g = new GsonBuilder().serializeNulls().create();
		this.writerToClient(g.toJson(userInfoMap));
	}

	@RequestMapping("getUserPass")
	public void getUserPass() throws Exception {
		String oldPassword = request.getParameter("oldPassword");
		String userAccount = getLoginUser();
		SysUsers users = userService.getUserInfoByUserAccount(userAccount);
		String userPassword = users.getUserPassword();
		if (!userPassword.equals(Md5Util.encodeByMD5(oldPassword))) {
			this.writerToClient("true");
		} else {
			this.writerToClient("false");
		}
	}

	@RequestMapping("updateUserPass")
	public void updateUserPass() throws Exception {
		String newPassword = request.getParameter("newPassword");
		String confirmNewPassword = request.getParameter("confirmNewPassword");
		String userAccount = getLoginUser();
		if (!newPassword.equals(confirmNewPassword)) {
			this.writerToClient("两次输入密码不一致");
		} else {
			userService.updateUserPass(userAccount, Md5Util.encodeByMD5(confirmNewPassword));
			this.writerToClient("操作成功");
		}
	}

	@Override
	public String getPage() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
