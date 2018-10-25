package top.zylsite.cheetah.base.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestUtil {

	/**
	 * 获取客户端请求ip
	 * 
	 * @param request
	 * @return
	 * @create: 2017年7月5日 下午2:44:25 zhaoyl
	 * @history:
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取当前请求的根路径
	 * 
	 * @param request
	 * @return
	 * @create: 2017年7月4日 下午3:06:51 zhaoyl
	 * @history:
	 */
	public static String getBasePath(HttpServletRequest request, String serverIp, Integer port) {
		String path = request.getContextPath();
		if (StringUtils.isBlank(serverIp)) {
			serverIp = request.getServerName();
		}
		if (null == port || 0 == port) {
			port = request.getServerPort();
		}
		String basePath = request.getScheme() + "://" + serverIp + ":" + port + path;
		return basePath;
	}

	/**
	 * 获取请求的url
	 * 
	 * @param request
	 * @return
	 * @create: 2017年11月28日 下午3:41:49 zhaoyl
	 * @history:
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (!"".equals(contextPath)) {
			requestPath = requestPath.substring(contextPath.length());
		}
		return requestPath;
	}

	/**
	 * 判断是不是ajax请求
	 * 
	 * @param request
	 * @return
	 * @create: 2018年3月13日 下午2:04:11 zhaoyl
	 * @history:
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取本机ip
	 * 
	 * @return
	 * @create: 2018年3月19日 上午11:09:07 zhaoyl
	 * @history:
	 */
	public static InetAddress getLocalHostLANAddress() {
		try {
			InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces
					.hasMoreElements();) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							// 如果是site-local地址，就是它了
							return inetAddr;
						} else if (candidateAddress == null) {
							// site-local类型的地址未被发现，先记录候选地址
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress;
			}
			// 如果没有发现 non-loopback地址.只能用最次选的方案
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			return jdkSuppliedAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取request中的所有键值对Map
	 * 
	 * @param request
	 * @return
	 * @create: 2018年3月22日 下午1:42:38 zhaoyl
	 * @history:
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

	// 判断请求发来的客户端浏览器是不是微软的浏览器
	public static boolean isMSBrowser(HttpServletRequest request) {
		final String[] IEBrowserSignals = { "MSIE", "Trident", "Edge" };
		String userAgent = request.getHeader("User-Agent");
		for (String signal : IEBrowserSignals) {
			if (userAgent.contains(signal))
				return true;
		}
		return false;
	}

	// 判断请求发来的客户端浏览器是不是火狐浏览器
	public static boolean isFirefoxBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		return (userAgent != null && userAgent.toLowerCase().indexOf("firefox") != -1);
	}

}
