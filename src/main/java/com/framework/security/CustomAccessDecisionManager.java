package com.framework.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * GrantedAuthority对象通过AuthenticationManager保存到Authentication对象里，
 * 然后从AccessDecisionManager读出来，进行授权判断。
 * 
 * Spring Security提供了一些拦截器，来控制对安全对象的访问权限，例如方法调用或web请求。
 * 一个是否允许执行调用的预调用决定，是由AccessDecisionManager实现的。
 * 这个 AccessDecisionManager被AbstractSecurityInterceptor调用，它用来作最终访问控制的决定。
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

	/**
	 * 判断是否有访问该url的权限
	 * <p>
	 * Title: decide
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * @param authentication
	 * @param object
	 * @param configAttributes
	 * @throws AccessDeniedException
	 * @throws InsufficientAuthenticationException
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication,
	 * java.lang.Object, java.util.Collection)
	 */
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) {
			return;
		}

		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while(ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			// ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				// 如果是管理员，赋予最大权限
				if("ROLE_ADMIN".equals(ga.getAuthority().trim())) {
					return;
				}
				if(needRole.trim().equals(ga.getAuthority().trim())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("无权限.");
	}

	/**
	 * supports(ConfigAttribute) 方法在启动的时候被 AbstractSecurityInterceptor调用，来决定AccessDecisionManager
	 * 是否可以执行传递ConfigAttribute。
	 * <p>
	 * Title: supports
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * @param attribute
	 * @return
	 * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
	 */
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * supports(Class)方法被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型
	 * <p>
	 * Title: supports
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * @param clazz
	 * @return
	 * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}
}