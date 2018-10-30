package top.zylsite.cheetah.web.backstage.common.shiro;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.model.dto.SystemLog;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.Role;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;
import top.zylsite.cheetah.backstage.model.master.UserRole;
import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.backstage.service.master.IRoleService;
import top.zylsite.cheetah.backstage.service.master.IUserLoginLogService;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.common.BaseOut;
import top.zylsite.cheetah.base.common.ResponseStatus;
import top.zylsite.cheetah.base.utils.LoggerFactoryUtil;
import top.zylsite.cheetah.base.utils.MessageSourceUtil;
import top.zylsite.cheetah.base.utils.RequestUtil;
import top.zylsite.cheetah.base.utils.ResponseUtil;
import top.zylsite.cheetah.base.utils.SpringUtil;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private Logger logger = LoggerFactoryUtil.getLogger(CustomFormAuthenticationFilter.class);

	// 重写此方法是为了在session过期时返回数据或重定向到登录页
	@Override
	protected void redirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
		// 判断session是否已过期
		if (!checkSession(servletRequest, servletResponse)) {
			super.redirectToLogin(servletRequest, servletResponse);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		SessionUser sessionUser = (SessionUser) subject.getPrincipal();
		IUserService userService = SpringUtil.getBean(IUserService.class);
		// 设置菜单树信息
		userService.setMenuTree(sessionUser);
		Environment env = SpringUtil.getBean(Environment.class);
		int timeout = env.getProperty(ShiroConstants.SESSION_TIMEOUT_KEY, Integer.class,
				ShiroConstants.DEFAULT_SESSION_TIMEOUT);
		if (0 != timeout) {
			ShiroUtil.getSession().setTimeout(timeout * 60 * 1000);
		}
		// 记录用户登录日志
		try {
			IUserLoginLogService userLoginLogService = SpringUtil.getBean(IUserLoginLogService.class);
			UserLoginLog userLoginLog = SystemLog.createUserLoginLog((HttpServletRequest) request, sessionUser.getId(),
					String.valueOf(LoginWayEnum.AP.getCode()));
			userLoginLogService.insertInfoAndGetId(userLoginLog);
			sessionUser.setLoginLogId(userLoginLog.getId());
		} catch (Exception e) {
			logger.error("登录日志插入数据库失败:", e);
		}
		ShiroUtil.setSessionAttribute(ShiroConstants.SESSION_USER_KEY, sessionUser);
		ShiroUtil.setSessionAttribute(ShiroConstants.SESSION_PERMISSION_KEY, getPermissions(sessionUser));

		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		request.setAttribute(ShiroConstants.FAIL_LOGIN_KEY_ATTRIBUTE, ShiroUtil.getErrorMessage(e));
		return true;
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		String url = ShiroUtil.getBeforeLoginUrl(request);
		if (StringUtils.isBlank(url)) {
			url = getSuccessUrl();
		}
		WebUtils.issueRedirect(request, response, url, null, true);
	}

	private boolean checkSession(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
		if (!ShiroUtil.isAuthenticated()) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			if (RequestUtil.isAjaxRequest(request)) {
				BaseOut result = new BaseOut();
				result.setStatus(ResponseStatus.SessionTimeout);
				result.setMsg(MessageSourceUtil.getMessage("session_timeout_warn"));
				result.setData("");
				ResponseUtil.writeResult(result, request, response);
			} else {
				super.redirectToLogin(request, response);
			}
			return true;
		}
		return false;
	}

	private SimpleAuthorizationInfo getPermissions(SessionUser user) {
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

}
