package top.zylsite.cheetah.web.backstage.common.shiro;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;
import top.zylsite.cheetah.backstage.service.master.IUserLoginLogService;
import top.zylsite.cheetah.base.utils.LoggerFactoryUtil;
import top.zylsite.cheetah.base.utils.MessageSourceUtil;
import top.zylsite.cheetah.base.utils.SpringUtil;

public class ShiroUtil {

	public static Logger logger = LoggerFactoryUtil.getLogger(ShiroUtil.class);
	
	public static boolean CHECK_PASSWORD = true;// 校验密码
	public static boolean CHECK_VALIDATECODE = false;// 校验验证码
	public static String VALIDATECODE_ATTR_NAME = "validateCode";// 验证码的属性名
	public static int VCODE_EFFECTIVETIME = 15;// 验证码有效时间,单位分钟
	public static boolean ENABLE_GOTO_BEFORE_LOGIN_URL = true;// 开启登陆之后直接跳转到登录之前的url
	public static IUserLoginLogService userLoginLogService = SpringUtil.getBean(IUserLoginLogService.class);

	/**
	 * {@link org.apache.shiro.session.Session Session} key used to save a request
	 * and later restore it, for example when redirecting to a requested page after
	 * login, equal to {@code shiroSavedRequest}.
	 */
	public static final String CUSTOM_SAVED_REQUEST_KEY = "customShiroSavedRequest";

	/**
	 * 向session中设置值
	 * 
	 * @param key
	 * @param value
	 * @create: 2018年3月14日 上午10:52:25 zhaoyl
	 * @history:
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 获取session中的值
	 * 
	 * @param key
	 * @return
	 * @create: 2018年3月14日 上午10:53:43 zhaoyl
	 * @history:
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 获取session
	 * 
	 * @return
	 * @create: 2018年3月14日 上午10:49:41 zhaoyl
	 * @history:
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

	/**
	 * 判断用户是不是已经登录
	 * 
	 * @return
	 * @create: 2018年3月14日 上午10:50:11 zhaoyl
	 * @history:
	 */
	public static boolean isAuthenticated() {
		boolean flag = getSubject().isAuthenticated();
		Object obj = getSessionAttribute(ShiroConstants.SESSION_USER_KEY);
		if (null == obj) {
			flag = false;
			getSubject().logout();
		}
		return flag;
	}

	/**
	 * 获取登录之前的url
	 * 
	 * @return
	 */
	public static String getBeforeLoginUrl(ServletRequest servletRequest) {
		if (ENABLE_GOTO_BEFORE_LOGIN_URL) {
			CustomSavedRequest savedRequest = ShiroUtil.getSavedRequest();
			if (null != savedRequest && savedRequest.isJump()) {
				return savedRequest.getRequestUrl();
			}
		}
		return null;
	}

	public static SessionUser getSessionUser() {
		Object obj = ShiroUtil.getSessionAttribute(ShiroConstants.SESSION_USER_KEY);
		if (null != obj) {
			SessionUser user = (SessionUser) obj;
			return user;
		}
		return null;
	}

	public static void saveRequest(ServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		CustomSavedRequest savedRequest = new CustomSavedRequest(httpRequest);
		session.setAttribute(CUSTOM_SAVED_REQUEST_KEY, savedRequest);
	}

	public static CustomSavedRequest getSavedRequest() {
		CustomSavedRequest savedRequest = null;
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		if (session != null) {
			savedRequest = (CustomSavedRequest) session.getAttribute(CUSTOM_SAVED_REQUEST_KEY);
		}
		return savedRequest;
	}

	public static String getErrorMessage(Exception e) {
		String messageProperty = "", error_message = "";
		if (e instanceof UnknownAccountException) {
			messageProperty = "unknown_account";
		} else if (e instanceof IncorrectCredentialsException) {
			messageProperty = "incorrect_credentials";
		} else if (e instanceof LockedAccountException) {
			messageProperty = "locked_account";
		} else if (e instanceof ExcessiveAttemptsException) {
			messageProperty = "excessive_attempts";
		} else if (e instanceof DisabledAccountException) {
			messageProperty = "disabled_account";
		} else {
			messageProperty = "server_exception";
		}
		if (!"".equals(messageProperty)) {
			error_message = MessageSourceUtil.getMessage(messageProperty);
		}
		return error_message;
	}

	public static void login(String username, String password) {
		// 验证
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// 获取当前的Subject
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);// 完成登陆
	}

	public static void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			SessionUser sessionUser = (SessionUser) subject.getPrincipal();
			if (null != sessionUser && null != sessionUser.getLoginLogId()) {
				UserLoginLog userLoginLog = new UserLoginLog();
				userLoginLog.setId(sessionUser.getLoginLogId());
				userLoginLog.setdLogoutTime(new Date());
				try {
					userLoginLogService.updateInfoByPrimaryKey(userLoginLog, true);
				}catch(Exception e) {
					logger.error(" 登出日志记录更新数据库失败:", e);
				}
			}
			subject.logout();
		}
	}

	private static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

}