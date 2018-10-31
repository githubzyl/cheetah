package top.zylsite.cheetah.web.backstage.common.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import top.zylsite.cheetah.base.common.BaseOut;
import top.zylsite.cheetah.base.common.ResponseStatus;
import top.zylsite.cheetah.base.utils.MessageSourceUtil;
import top.zylsite.cheetah.base.utils.RequestUtil;
import top.zylsite.cheetah.base.utils.ResponseUtil;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private String loginTypeParamName = ShiroConstants.DEFAULT_LOGIN_TYPE_PARAM;

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
		String loginType = null;
		if (token instanceof UsernamePasswordLoginTypeToken) {
			UsernamePasswordLoginTypeToken loginTypeToken = (UsernamePasswordLoginTypeToken) token;
			loginType = loginTypeToken.getLoginType();
		}
		ShiroUtil.onLoginSuccess((HttpServletRequest) request, subject, loginType);
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		request.setAttribute(ShiroConstants.FAIL_LOGIN_KEY_ATTRIBUTE, ShiroUtil.getErrorMessage(e));
		return true;
	}

	/**
	 * 重写该方法,为了将loginType参数保存到token中
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String loginType = getLoginType(request);
		return createToken(username, password, request, response, loginType);
	}

	private AuthenticationToken createToken(String username, String password, ServletRequest request,
			ServletResponse response, String loginType) {
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return createToken(username, password, rememberMe, host, loginType);
	}

	private AuthenticationToken createToken(String username, String password, boolean rememberMe, String host,
			String loginType) {
		return new UsernamePasswordLoginTypeToken(username, password, rememberMe, host, loginType);
	}

	private String getLoginType(ServletRequest request) {
		return WebUtils.getCleanParam(request, getLoginTypeParamName());
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

	public String getLoginTypeParamName() {
		return loginTypeParamName;
	}

	public void setLoginTypeParamName(String loginTypeParamName) {
		this.loginTypeParamName = loginTypeParamName;
	}

}
