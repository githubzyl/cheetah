package top.zylsite.cheetah.web.backstage.configuation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import top.zylsite.cheetah.base.utils.ResourceReader;
import top.zylsite.cheetah.web.backstage.common.shiro.config.CheetahFormAuthenticationFilter;
import top.zylsite.cheetah.web.backstage.common.shiro.config.CheetahModularRealmAuthenticator;
import top.zylsite.cheetah.web.backstage.common.shiro.config.CheetahShiroFilterFactoryBean;
import top.zylsite.cheetah.web.backstage.common.shiro.config.UrlPermissionResolver;
import top.zylsite.cheetah.web.backstage.common.shiro.credentials.UserCredentialsMatcher;
import top.zylsite.cheetah.web.backstage.common.shiro.filter.URLPathMatchingFilter;
import top.zylsite.cheetah.web.backstage.common.shiro.realm.ThirdAccountRealm;
import top.zylsite.cheetah.web.backstage.common.shiro.realm.UserRealm;
import top.zylsite.cheetah.web.backstage.common.shiro.credentials.ThirdAccountCredentialsMatcher;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;

/**
 * Description: shiro配置
 * 
 * @author jason 2018年10月26日
 * @version 1.0
 */
@Configuration
public class ShiroConfiguration {

	private Map<String, String> filterChainMap = new LinkedHashMap<String, String>();

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 * 
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 * 
	 */
	@Bean
	public EhCacheManager getEhCacheManager() {
		EhCacheManager ehcacheManager = new EhCacheManager();
		ehcacheManager.setCacheManagerConfigFile(ShiroConstants.EHCACHE_SHIRO_FILE);
		return ehcacheManager;
	}

	// cookie对象;
	@Bean
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	// cookie管理对象;
	@Bean
	public CookieRememberMeManager cookieRememberMeManager() {
		CookieRememberMeManager manager = new CookieRememberMeManager();
		manager.setCookie(rememberMeCookie());
		return manager;
	}

	// 配置自定义的密码比较器
	@Bean(name = "credentialsMatcher")
	public UserCredentialsMatcher credentialsMatcher() {
		return new UserCredentialsMatcher(getEhCacheManager());
	}

	// 配置第三方账号自定义的密码比较器
	@Bean(name = "thirdAccountCredentialsMatcher")
	public ThirdAccountCredentialsMatcher thirdAccountCredentialsMatcher() {
		return new ThirdAccountCredentialsMatcher();
	}

	@Bean("permissionResolver")
	public UrlPermissionResolver permissionResolver() {
		return new UrlPermissionResolver();
	}
	
	// 配置自定义的权限登录器
	@Bean(name = "userAuthRealm")
	public UserRealm userAuthRealm() {
		UserRealm authRealm = new UserRealm(getEhCacheManager());
		authRealm.setCredentialsMatcher(credentialsMatcher());
		authRealm.setPermissionResolver(permissionResolver());
		return authRealm;
	}

	// 配置自定义的权限登录器
	@Bean(name = "thirdAccountRealm")
	public ThirdAccountRealm thirdAccountRealm() {
		ThirdAccountRealm authRealm = new ThirdAccountRealm();
		authRealm.setCredentialsMatcher(thirdAccountCredentialsMatcher());
		authRealm.setPermissionResolver(permissionResolver());
		return authRealm;
	}

	@Bean(name = "authenticator")
	public ModularRealmAuthenticator authenticator() {
		CheetahModularRealmAuthenticator authenticator = new CheetahModularRealmAuthenticator();
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return authenticator;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setAuthenticator(authenticator());
		List<Realm> realms = new ArrayList<>();
		// 添加多个Realm
		realms.add(userAuthRealm());
		realms.add(thirdAccountRealm());
		securityManager.setRealms(realms);
		securityManager.setCacheManager(getEhCacheManager());
		securityManager.setRememberMeManager(cookieRememberMeManager());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new CheetahShiroFilterFactoryBean();
		Map<String, Filter> filters = factoryBean.getFilters();
		filters.put("authc", new CheetahFormAuthenticationFilter());
		filters.put("requestUrl", new URLPathMatchingFilter());
		factoryBean.setFilters(filters);
		factoryBean.setSecurityManager(securityManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		factoryBean.setLoginUrl(ShiroConstants.LOGIN_URL);
		// 登录成功后要跳转的连接
		factoryBean.setSuccessUrl(ShiroConstants.SUCCESS_URL);
		// 没有权限的跳转链接
		factoryBean.setUnauthorizedUrl(ShiroConstants.UNAUTHORIZED_URL);
		// 加载配置信息
		loadShiroConfiguation();
		// 配置访问权限
		loadShiroFilterChain(factoryBean);
		return factoryBean;
	}

	/**
	 * 加载ShiroFilter权限控制规则
	 */
	private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
		/**
		 * anon：它对应的过滤器里面是空的,什么都没做,可以理解为不拦截 authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器
		 * org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		 */
		factoryBean.setFilterChainDefinitionMap(filterChainMap);
	}

	/**
	 * 加载配置信息
	 */
	private void loadShiroConfiguation() {
		filterChainMap = ResourceReader.readProperties(ShiroConstants.SHIRO_FILTER_FILE);
	}

	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 * 
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}
