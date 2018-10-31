package top.zylsite.cheetah.web.backstage.configuation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import top.zylsite.cheetah.web.backstage.common.shiro.CustomCredentialsMatcher;
import top.zylsite.cheetah.web.backstage.common.shiro.CustomFormAuthenticationFilter;
import top.zylsite.cheetah.web.backstage.common.shiro.CustomModularRealmAuthenticator;
import top.zylsite.cheetah.web.backstage.common.shiro.CustomShiroFilterFactoryBean;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.ThirdAccountCredentialsMatcher;
import top.zylsite.cheetah.web.backstage.common.shiro.ThirdAccountRealm;
import top.zylsite.cheetah.web.backstage.common.shiro.UserRealm;

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

	// 配置自定义的密码比较器
	@Bean(name = "credentialsMatcher")
	public CustomCredentialsMatcher credentialsMatcher() {
		return new CustomCredentialsMatcher(getEhCacheManager());
	}

	// 配置第三方账号自定义的密码比较器
	@Bean(name = "thirdAccountCredentialsMatcher")
	public ThirdAccountCredentialsMatcher thirdAccountCredentialsMatcher() {
		return new ThirdAccountCredentialsMatcher();
	}

	// 配置自定义的权限登录器
	@Bean(name = "userAuthRealm")
	public UserRealm userAuthRealm() {
		UserRealm authRealm = new UserRealm(getEhCacheManager());
		authRealm.setCredentialsMatcher(credentialsMatcher());
		return authRealm;
	}

	// 配置自定义的权限登录器
	@Bean(name = "thirdAccountRealm")
	public ThirdAccountRealm thirdAccountRealm() {
		ThirdAccountRealm authRealm = new ThirdAccountRealm();
		authRealm.setCredentialsMatcher(thirdAccountCredentialsMatcher());
		return authRealm;
	}

	@Bean(name = "authenticator")
	public ModularRealmAuthenticator authenticator() {
		CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
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
		ShiroFilterFactoryBean factoryBean = new CustomShiroFilterFactoryBean();
		Map<String, Filter> filters = factoryBean.getFilters();
		filters.put("authc", new CustomFormAuthenticationFilter());
		factoryBean.setFilters(filters);
		factoryBean.setSecurityManager(securityManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		factoryBean.setLoginUrl(ShiroConstants.LOGIN_URL);
		// 登录成功后要跳转的连接
		factoryBean.setSuccessUrl(ShiroConstants.SUCCESS_URL);
		// 没有权限的跳转链接
		factoryBean.setUnauthorizedUrl(ShiroConstants.UNAUTHORIZED_URL);
		// 初始化配置信息,主要加载不需要验证的url
		loadExcludesUrl();
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
	 * 初始化配置信息，主要加载排除拦截的pattern
	 */
	private void loadExcludesUrl() {
		try {
			Element element = getElement();
			// 读取不需要验证的url
			addPatternsByType(element, "anon");
			// 读取需要验证的url
			addPatternsByType(element, "authc");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
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

	private void addPatternsByType(Element element, String type) {
		NodeList nodeList = element.getElementsByTagName(type + "s");
		if (nodeList == null) {
			return;
		}
		NodeList secondlist = nodeList.item(0).getChildNodes();
		if (null == secondlist || secondlist.getLength() <= 0) {
			return;
		}
		for (int i = 0; i < secondlist.getLength(); i++) {
			Node node = secondlist.item(i);
			if ("pattern".equals(node.getNodeName())) {
				filterChainMap.put(node.getTextContent(), type);
			}
		}
	}

	private Element getElement() throws IOException, ParserConfigurationException, SAXException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources(ShiroConstants.SESSION_FILTER_FILE);
		InputStream is = resources[0].getInputStream();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(is);
		Element element = doc.getDocumentElement();
		return element;
	}

}
