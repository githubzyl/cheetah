package top.zylsite.cheetah.backstage.model.dto;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import eu.bitwalker.useragentutils.UserAgent;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;
import top.zylsite.cheetah.backstage.model.master.UserViewLog;
import top.zylsite.cheetah.base.utils.RequestUtil;
import top.zylsite.cheetah.base.utils.UserAgentUtils;

/**
 * Description: 系统日志辅助类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class SystemLog {

	public static UserLoginLog createUserLoginLog(HttpServletRequest request, SessionUser sessionUser, String loginType) {
		UserAgent userAgent = getUserAgent(request);
		UserLoginLog userLoginLog = new UserLoginLog();
		userLoginLog.setcLoginType(loginType);
		if(null != sessionUser) {
			userLoginLog.setlUserId(sessionUser.getId());
			userLoginLog.setVcUserName(sessionUser.getVcUserName());
		}
		userLoginLog.setdLoginTime(new Date());
		userLoginLog.setVcIp(RequestUtil.getClientIp(request));
		userLoginLog.setVcLocation(getLocation(userLoginLog.getVcIp()));
		userLoginLog.setVcDeviceType(getDeviceType(userAgent));
		userLoginLog.setVcBrowserType(getBrowserType(userAgent));
		return userLoginLog;
	}

	public static UserViewLog createUserViewLog(HttpServletRequest request, SessionUser sessionUser, String methodDescription) {
		UserAgent userAgent = getUserAgent(request);
		UserViewLog userViewLog = new UserViewLog();
		if(null != sessionUser) {
			userViewLog.setlUserId(sessionUser.getId());
			userViewLog.setVcUserName(sessionUser.getVcUserName());
		}
		userViewLog.setVcOperation(methodDescription);
		userViewLog.setVcUrl(request.getRequestURI());
		userViewLog.setVcParam(getRequestParam(request));
		userViewLog.setVcMethod(request.getMethod().toUpperCase());
		userViewLog.setVcIp(RequestUtil.getClientIp(request));
		userViewLog.setVcLocation(getLocation(userViewLog.getVcIp()));
		userViewLog.setVcDeviceType(getDeviceType(userAgent));
		userViewLog.setVcBrowserType(getBrowserType(userAgent));
		userViewLog.setdVisitTime(new Date());
		return userViewLog;
	}

	private static String getRequestParam(HttpServletRequest request) {
		String parameter = "";
		Enumeration<String> enu = request.getParameterNames();
		String paraName, paraValue;
		while (enu.hasMoreElements()) {
			paraName = (String) enu.nextElement();
			paraValue = request.getParameter(paraName);
			parameter += "&" + paraName + "=" + paraValue;
		}
		if (StringUtils.isNotBlank(parameter)) {
			parameter = parameter.substring(1);
		}
		return parameter.equals("") ? null : parameter;
	}

	private static UserAgent getUserAgent(HttpServletRequest request) {
		return UserAgentUtils.getUserAgent(request);
	}

	private static String getDeviceType(UserAgent userAgent) {
		return userAgent.getOperatingSystem().getDeviceType().getName() + "(系统:"
				+ userAgent.getOperatingSystem().getName() + ")";
	}

	private static String getBrowserType(UserAgent userAgent) {
		return userAgent.getBrowser().getName() + "(版本:" + userAgent.getBrowserVersion() + ")";
	}
	
	private static String getLocation(String ip) {
		if(StringUtils.isBlank(ip)) {
			return null;
		}
		return RequestUtil.getIpLocation(ip);
	}
	
}
