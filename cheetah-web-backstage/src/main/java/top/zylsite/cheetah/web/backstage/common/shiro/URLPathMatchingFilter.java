package top.zylsite.cheetah.web.backstage.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

public class URLPathMatchingFilter extends PathMatchingFilter {

	@Override
	protected boolean onPreHandle(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue)
			throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 请求的url
		String requestURI = request.getRequestURI();
		boolean hasPermission = checkPermission(requestURI);
		if (hasPermission) {
			return true;
		} else {
			WebUtils.issueRedirect(request, response, ShiroConstants.UNAUTHORIZED_URL);
			return false;
		}
	}

	private boolean checkPermission(String url) {
		if (ShiroUtil.getSessionUser().isSysadmin()) {
			return true;
		}
		ShiroUtil.setAllPermissions();
		boolean flag = true;
		String permissionCode = ShiroUtil.urlMap.get(url);
		if (StringUtils.isNotBlank(permissionCode)) {
			flag = SecurityUtils.getSubject().isPermitted(permissionCode);
		}
		return flag;
	}

}