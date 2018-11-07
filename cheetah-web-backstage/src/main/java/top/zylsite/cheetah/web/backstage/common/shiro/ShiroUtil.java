package top.zylsite.cheetah.web.backstage.common.shiro;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.model.dto.SystemLog;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.Role;
import top.zylsite.cheetah.backstage.model.master.UserBindInfo;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;
import top.zylsite.cheetah.backstage.model.master.UserRole;
import top.zylsite.cheetah.backstage.service.common.enums.GenderEnum;
import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.backstage.service.master.IRoleService;
import top.zylsite.cheetah.backstage.service.master.IUserBindInfoService;
import top.zylsite.cheetah.backstage.service.master.IUserLoginLogService;
import top.zylsite.cheetah.backstage.service.master.IUserService;
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

	public static SessionUser getSessionUser() {
		Object obj = ShiroUtil.getSessionAttribute(ShiroConstants.SESSION_USER_KEY);
		if (null != obj) {
			SessionUser user = (SessionUser) obj;
			return user;
		}
		return null;
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

	public static Subject login(String username, String password, String loginType) {
		// 验证
		UsernamePasswordLoginTypeToken token = new UsernamePasswordLoginTypeToken(username, password, false, null,
				loginType);
		// 获取当前的Subject
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);// 完成登陆
		return subject;
	}

	public static void onLoginSuccess(HttpServletRequest request, Subject subject, String loginType) {
		SessionUser sessionUser = (SessionUser) subject.getPrincipal();
		//清空密码
		sessionUser.setVcPassword(null);
		//设置头像信息
		IUserService userService = SpringUtil.getBean(IUserService.class);
		if(LoginWayEnum.isThirdAccount(loginType)) {
			IUserBindInfoService userBindInfoService = SpringUtil.getBean(IUserBindInfoService.class);
			UserBindInfo bindInfo = userBindInfoService.queryByUserId(sessionUser.getId(), loginType);
			if(null != bindInfo && StringUtils.isNotBlank(bindInfo.getVcPhoto())) {
				sessionUser.setVcPhoto(bindInfo.getVcPhoto());
			}
		}
		//设置性别
		if(StringUtils.isBlank(sessionUser.getcGender())) {
			sessionUser.setcGender(GenderEnum.MALE.getCode());
		}
		// 设置菜单树信息
		userService.setPermissionTree(sessionUser);
		Environment env = SpringUtil.getBean(Environment.class);
		int timeout = env.getProperty(ShiroConstants.SESSION_TIMEOUT_KEY, Integer.class,
				ShiroConstants.DEFAULT_SESSION_TIMEOUT);
		if (0 != timeout) {
			ShiroUtil.getSession().setTimeout(timeout * 60 * 1000);
		}
		// 记录用户登录日志
		try {
			IUserLoginLogService userLoginLogService = SpringUtil.getBean(IUserLoginLogService.class);
			UserLoginLog userLoginLog = SystemLog.createUserLoginLog(request, sessionUser, loginType);
			userLoginLogService.insertInfoAndGetId(userLoginLog);
			sessionUser.setLoginLogId(userLoginLog.getId());
		} catch (Exception e) {
			logger.error("登录日志插入数据库失败:", e);
		}
		ShiroUtil.setSessionAttribute(ShiroConstants.SESSION_USER_KEY, sessionUser);
		ShiroUtil.setSessionAttribute(ShiroConstants.SESSION_PERMISSION_KEY, getPermissions(sessionUser));
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
				} catch (Exception e) {
					logger.error(" 登出日志记录更新数据库失败:", e);
				}
			}
			subject.logout();
		}
	}
	
	private static SimpleAuthorizationInfo getPermissions(SessionUser user) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		IUserService userService = SpringUtil.getBean(IUserService.class);
		IRoleService roleService = SpringUtil.getBean(IRoleService.class);
		List<UserRole> userRoles = userService.queryRolesByUserId(user.getId());
		if (null != userRoles && userRoles.size() > 0) {
			Role role = null;
			for (UserRole userRole : userRoles) {
				role = roleService.queryInfoByPrimaryKey(userRole.getRoleId());
				info.addRole(role.getVcCode());
			}
		}
		List<Permission> list = userService.queryPermissionListByUserId(user.getId());
		if (null != list && list.size() > 0) {
			for (Permission p : list) {
				info.addStringPermission(p.getVcCode());
			}
		}
		return info;
	}

	private static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

}