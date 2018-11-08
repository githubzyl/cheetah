package top.zylsite.cheetah.web.backstage.common.shiro;

/**
 * Description: shiro相关的常量
 * @author jason
 * 2018年10月26日
 * @version 1.0
 */
public final class ShiroConstants {

	/**
	 * 登录页面的url
	 */
	public final static String LOGIN_URL = "/login";
	/**
	 * 首页的url
	 */
	public final static String SUCCESS_URL = "/index";
	/**
	 * 没权限的url
	 */
	public final static String UNAUTHORIZED_URL = "/error/403";
	
	/**
	 * shiro-filter配置文件，主要配置shiro相关的东西
	 */
	public final static String SHIRO_FILTER_FILE = "classpath:config/shiro/shiro-filter.properties";
	/**
	 * shiro-ehcache缓存配置
	 */
	public final static String EHCACHE_SHIRO_FILE = "classpath:config/shiro/ehcache-shiro.xml";
	
	/**
	 * 存放session的key
	 */
	public final static String SESSION_USER_KEY = "sessionUser";
	/**
	 * 存放权限德尔key
	 */
	public final static String SESSION_PERMISSION_KEY = "userPermission";
	/**
	 * 登录失败的错误消息的key
	 */
	public final static String FAIL_LOGIN_KEY_ATTRIBUTE = "error_message";
	/**
	 * 登录类型key
	 */
	public static final String DEFAULT_LOGIN_TYPE_PARAM = "loginType";
	
	/**
	 * session超时的配置key
	 */
	public final static String SESSION_TIMEOUT_KEY = "session.timeout";
	
	/**
	 * 默认的session超时时间（30分钟）
	 */
	public final static int DEFAULT_SESSION_TIMEOUT = 30;
	
	/**
	 *retryCount是shiro登陆上限
	 */
	public final static int LOGIN_ERROR_RETRYCOUNT = 5;
	
	/**
	 * maxRetryCount代表系统最大登陆出错次数，超过此次数需要手动解锁
	 */
	public final static int LOGIN_ERROR_MAXRETRYCOUNT = 10;
	
}
