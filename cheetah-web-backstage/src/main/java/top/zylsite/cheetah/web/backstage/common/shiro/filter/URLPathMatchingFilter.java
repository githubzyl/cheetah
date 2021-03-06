package top.zylsite.cheetah.web.backstage.common.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;
import top.zylsite.cheetah.web.backstage.common.shiro.config.UrlPermission;

public class URLPathMatchingFilter extends PathMatchingFilter {

	@Override
	protected boolean onPreHandle(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue)
			throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 请求的url
		String requestURI = request.getRequestURI();
		if (null == ShiroUtil.getSessionUser()) {
			WebUtils.issueRedirect(request, response, ShiroConstants.LOGIN_URL);
			return false;
		}
		if (ShiroUtil.getSessionUser().isSysadmin()) {
			return true;
		}
		ShiroUtil.setAllPermissions();
		if(!ShiroUtil.urlMap.containsKey(requestURI)) {
			return true;
		}
		if (SecurityUtils.getSubject().isPermitted(new UrlPermission(requestURI))) {
			return true;
		}else {
			WebUtils.issueRedirect(request, response, ShiroConstants.UNAUTHORIZED_URL);
			return false;
		}
	}

}
