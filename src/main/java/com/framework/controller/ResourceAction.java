package com.framework.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.BaseAction;
import com.framework.po.SysResources;
import com.framework.service.DictionaryService;
import com.framework.service.ResourceService;
import com.framework.util.LogFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * <p>
 * Description: 资源模块
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
@RequestMapping("/sysRes")
public class ResourceAction extends BaseAction {

	@Autowired(required = true)
	private ResourceService resourceService;
	@Autowired(required = true)
	private DictionaryService dictionaryService;
	private SysResources sysResources;

	@RequestMapping("getList")
	@Override
	public void getList() throws Exception {
		// 获取前台传入参数信息
		String resourceName = request.getParameter("resourceName");
		String resourceDesc = request.getParameter("resourceDesc");
		String resourceString = request.getParameter("resourceString");
		// 用于添加查询条件的map
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("resourceName", StringUtils.isNotBlank(resourceName) ? resourceName : null);
		conditionMap.put("resourceDesc", StringUtils.isNotBlank(resourceDesc) ? resourceDesc : null);
		conditionMap.put("resourceString", StringUtils.isNotBlank(resourceString) ? resourceString : null);
		conditionMap.put("startPage", getStart());
		conditionMap.put("pageSize", getIDisplayLength());
		// 根据查询条件查询的的数据信息并获取数据的总量
		int count = resourceService.count(conditionMap);
		recordsTotal = count;
		// 分页显示上面查询出的数据结果
		List<SysResources> data = resourceService.loadList(conditionMap);
		List<SysResources> data1 = new ArrayList<SysResources>();

		if (data != null) {
			for (SysResources sr : data) {
				sr.setResourceType(dictionaryService.getDicValueByKey("RESOURCE_TYPE").get(sr.getResourceType()));
				data1.add(sr);
			}
		}
		recordsFiltered = recordsTotal;
		recordsDisplay = data1.size();
		this.writerToClient(data1, iDisplayLength, recordsDisplay, recordsFiltered, recordsTotal, start);
	}

	@RequestMapping("insert")
	@Override
	public void insert(Object obj) throws Exception {
		try {
			SysResources sysResources = (SysResources) obj;
			resourceService.insert(sysResources);
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
			SysResources sysResources = (SysResources) obj;
			resourceService.update(sysResources);
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
			String resourceId = request.getParameter("resourceId");
			sysResources = resourceService.getResourceById(resourceId);
			Gson g = new GsonBuilder().serializeNulls().create();
			this.writerToClient(g.toJson(sysResources));
		} catch (Exception e) {
			LogFactory.getInstance().getSysInfoLogger().error("系统错误：" + e.getMessage());
			this.writerToClient("错误<br/><hr/>" + e.getCause());
		}
	}

	@RequestMapping("getResourceOpt")
	public void getResourceOpt() throws Exception {
		List<SysResources> resList = resourceService.getResourceList();

		Gson g = new GsonBuilder().serializeNulls().create();
		this.writerToClient(g.toJson(resList));
	}

	@Override
	public String getPage() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
