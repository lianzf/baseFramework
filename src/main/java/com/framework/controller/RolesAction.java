package com.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.BaseAction;
import com.framework.po.RolesResVO;
import com.framework.po.SysResourceVO;
import com.framework.po.SysRoles;
import com.framework.po.SysRolesVO;
import com.framework.service.ResourceService;
import com.framework.service.RolesService;
import com.framework.util.LogFactory;
import com.framework.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * <p>
 * Description: 用户角色模块
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
@RequestMapping("/sysRoles")
public class RolesAction extends BaseAction {

	@Autowired(required = true)
	private RolesService rolesService;
	@Autowired(required = true)
	private ResourceService resourceService;
	
	@RequestMapping("getList")
	@Override
	public void getList() throws Exception {
		// 获取前台传入参数信息
		String roleName = request.getParameter("roleName");
		// 用于添加查询条件的map
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleName", StringUtils.isNotBlank(roleName) ? roleName : null);
		conditionMap.put("startPage", getStart());
		conditionMap.put("pageSize", getIDisplayLength());
		// 根据查询条件查询的的数据信息并获取数据的总量
		int count = rolesService.count(conditionMap);
		recordsTotal = count;
		// 分页显示上面查询出的数据结果
		List<SysRoles> data = rolesService.loadList(conditionMap);
		recordsFiltered = recordsTotal;
		recordsDisplay = data.size();
		this.writerToClient(data, iDisplayLength, recordsDisplay, recordsFiltered, recordsTotal, start);
	}

	@RequestMapping("insert")
	@Override
	public void insert(Object obj) throws Exception {
		try {
			RolesResVO sysRoles = (RolesResVO) obj;
			rolesService.insert(sysRoles);
			this.writerToClient("操作成功");
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	@Override
	public void delete() throws Exception {
		// TODO Auto-generated method stub

	}

	@RequestMapping("update")
	@Override
	public void update(Object obj) throws Exception {
		try {
			RolesResVO sysRoles = (RolesResVO) obj;
			rolesService.update(sysRoles);
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
			String rolesId = request.getParameter("rolesId");
			SysRoles sysRole = rolesService.getRolesById(rolesId);
			Gson g = new GsonBuilder().serializeNulls().create();
			this.writerToClient(g.toJson(sysRole));
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	/**
	 * 
	 * isExistsRole 方法描述: 根据角色名称查看角色是否已存在
	 * 
	 * @throws Exception
	 * @since Ver 1.00
	 */
	@RequestMapping("isExistsRole")
	public void isExistsRole() throws Exception {
		String rolesId = request.getParameter("rolesId");
		String rolesName = request.getParameter("rolesName");
		boolean bool = rolesService.isExistsRoles(rolesId, rolesName);
		this.writerToClient(bool + "");
	}

	@RequestMapping("getRoleByUserId")
	public void getRoleByUserId() throws Exception {
		try {
			String usersId = request.getParameter("usersId");
			List<SysRolesVO> roleList = rolesService.getAllRoles(usersId);
			Gson g = new GsonBuilder().serializeNulls().create();
			this.writerToClient(g.toJson(roleList));
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	@RequestMapping("getResByRoleId")
	public void getResByRoleId() throws Exception {
		try {
			String rolesId = request.getParameter("rolesId");
			List<SysResourceVO> resList0 = resourceService.getFirstMenu(rolesId);
			List<SysResourceVO> resList = resourceService.getAllRes(rolesId);
			StringBuilder sb = new StringBuilder();
			for (SysResourceVO vo : resList0) {
				if (vo.getRolesId() != null && vo.getRolesId().toString().equals(rolesId)) {
					sb.append("<ul class='nav'><li class='active'><input name='sysRoles.resourceId' " + "value='"
							+ vo.getResourceId() + "' type='checkbox' onclick=checkSel('first',this); checked/>"
							+ StringUtil.spaceBuild(2) + vo.getResourceDesc() + "");
				} else {
					sb.append("<ul class='nav'><li class='active'><input name='sysRoles.resourceId' " + "value='"
							+ vo.getResourceId() + "' type='checkbox' onclick=checkSel('first',this); />"
							+ StringUtil.spaceBuild(2) + vo.getResourceDesc() + "");
				}
				for (SysResourceVO vo1 : resList) {
					if (vo1.getParentId().equals(vo.getResourceId())) {
						sb.append("<ul class='nav nav-second-level'>");
						if (vo1.getRolesId() != null && vo1.getRolesId().toString().equals(rolesId)) {
							sb.append("<li>" + StringUtil.spaceBuild(6) + "<input name='sysRoles.resourceId'"
									+ "  value='" + vo1.getResourceId() + "' "
									+ "type='checkbox' onclick=checkSel('second',this); checked>"
									+ StringUtil.spaceBuild(2) + vo1.getResourceDesc() + "");
							for (SysResourceVO vo2 : resList) {
								if (vo2.getParentId().equals(vo1.getResourceId())) {
									sb.append("<ul class='nav nav-third-level'>");
									if (vo2.getRolesId() != null && vo2.getRolesId().toString().equals(rolesId)) {
										sb.append("<li>" + StringUtil.spaceBuild(12)
												+ "<input name='sysRoles.resourceId'" + " value='" + vo2.getResourceId()
												+ "'" + " type='checkbox' onclick=checkSel('third',this); checked>"
												+ StringUtil.spaceBuild(2) + vo2.getResourceDesc() + "</li>");
									} else {
										sb.append("<li>" + StringUtil.spaceBuild(12)
												+ "<input name='sysRoles.resourceId'" + " value='" + vo2.getResourceId()
												+ "'" + " type='checkbox' onclick=checkSel('third',this);>"
												+ StringUtil.spaceBuild(2) + vo2.getResourceDesc() + "</li>");
									}
									sb.append("</ul>");
								}
							}
							sb.append("</li>");
						} else {
							sb.append("<li>" + StringUtil.spaceBuild(6) + "<input name='sysRoles.resourceId'"
									+ " value='" + vo1.getResourceId() + "'"
									+ " type='checkbox' onclick=checkSel('second',this);>" + StringUtil.spaceBuild(2)
									+ vo1.getResourceDesc() + "");
							for (SysResourceVO vo2 : resList) {
								if (vo2.getParentId().equals(vo1.getResourceId())) {
									sb.append("<ul class='nav nav-third-level'>");
									sb.append("<li>" + StringUtil.spaceBuild(12) + "<input name='sysRoles.resourceId' "
											+ "value='" + vo2.getResourceId()
											+ "' type='checkbox' onclick=checkSel('third',this);>"
											+ StringUtil.spaceBuild(2) + vo2.getResourceDesc() + "</li>");
									sb.append("</ul>");
								}
							}
							sb.append("</li>");
							sb.append("</li>");
						}
						sb.append("</ul>");
					}
				}
				sb.append("</li></ul>");
			}
			this.writerToClient(sb.toString());
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	@Override
	public String getPage() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
