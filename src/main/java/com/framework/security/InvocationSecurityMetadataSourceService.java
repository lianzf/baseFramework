package com.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.framework.constant.Constant;
import com.framework.po.SysResources;
import com.framework.po.SysRolesVO;
import com.framework.service.RolesResService;
import com.framework.service.RolesService;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 此类在初始化时，应该取到所有资源及其对应角色的定义。
 */
public class InvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired(required = true)
	private RolesResService rolesResServcie;
	@Autowired(required = true)
	private RolesService rolesServcie;

	private void loadResourceDefine() throws Exception {
		// 获取权限(角色)列表
		List<SysRolesVO> roles = rolesServcie.getAllRoles(null);
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		// 循环所有角色，查出对应的资源
		for(SysRolesVO role : roles) {
			ConfigAttribute ca = new SecurityConfig(role.getRolesName());
			List<SysResources> sr = rolesResServcie.getResByRolesName(role.getRolesName());

			for(SysResources resource : sr) {
				String url = resource.getResourceString();
				if(resourceMap.containsKey(url)) {
					Collection<ConfigAttribute> value = resourceMap.get(url);
					value.add(ca);
					resourceMap.put(url, value);
				}
				else {
					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(url, atts);
				}
			}
		}
		Constant.resourceMap = resourceMap;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		try {
			if(Constant.resourceMap == null) {
				loadResourceDefine();

			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Collection<ConfigAttribute> cc = null;
		String url = ((FilterInvocation) object).getRequestUrl();
		System.out.println("real request url : " + url);

		Iterator<String> ite = Constant.resourceMap.keySet().iterator();
		PathMatcher matcher = new AntPathMatcher();
		while(ite.hasNext()) {
			String parterUrl = ite.next();
			if(matcher.match(parterUrl, url)) {
				System.out.println("parterUrl : " + parterUrl);
				cc = Constant.resourceMap.get(parterUrl);
				break;
			}
		}
		if(cc == null) {
			ConfigAttribute ca = new SecurityConfig("ROLE_NO_URL");
			cc = new ArrayList<ConfigAttribute>();
			cc.add(ca);
		}
		return cc;

	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}