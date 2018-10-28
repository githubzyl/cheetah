package top.zylsite.cheetah.backstage.service.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Description: 第三方登录的URL获取类
 * @author jason 2018年10月28日
 * @version 1.0
 */
public class ThirdLoginUrl {

	/**
	 * 获取第三方登录的URL
	 * 
	 * @param code
	 * @return
	 */
	public static String get(int code) {
		String url = "/login";
		switch (code) {
		case LoginConstants.LOGIN_WAY_QQ:
			url = qq();
			break;
		case LoginConstants.LOGIN_WAY_WECHAT:
			url = wechat();
			break;
		case LoginConstants.LOGIN_WAY_SINA:
			url = sina();
			break;
		case LoginConstants.LOGIN_WAY_BAIDU:
			url = baidu();
			break;
		case LoginConstants.LOGIN_WAY_ALIPAY:
			url = alipay();
			break;
		case LoginConstants.LOGIN_WAY_GITHUB:
			url = github();
			break;
		default:
			
			break;
		}
		return url;
	}

	private static String qq() {
		StringBuilder sb = new StringBuilder(LoginConstants.QQ_DOMAIN);
		sb.append("/oauth2.0/authorize?response_type=code");
		sb.append("&client_id=" + LoginConstants.QQ_CLIENT_ID);
		sb.append("&redirect_uri=" + LoginConstants.QQ_AUTH_REDIRECT_URI);
		sb.append("&state=" + LoginConstants.QQ_STATE);
		sb.append("&scope=get_user_info,list_album,upload_pic,do_like");
		return sb.toString();
	}
	
	private static String wechat() {
		
		return "";
	}
	
	private static String sina() {
		StringBuilder sb = new StringBuilder(LoginConstants.SINA_DOMAIN);
		sb.append("/oauth2/authorize");
		sb.append("?client_id=");
		sb.append(LoginConstants.SINA_APP_KEY);
		sb.append("&scope=all&redirect_uri=");
		sb.append(LoginConstants.SINA_AUTH_REDIRECT_URL);
		sb.append("&state=init");
		sb.append("&display=default");
		sb.append("&forcelogin=false");
		return sb.toString();
	}
	
	private static String baidu() {
		StringBuilder sb = new StringBuilder(LoginConstants.BAIDU_DOMAIN);
		sb.append("/oauth/2.0/authorize");
		sb.append("?client_id=" + LoginConstants.BAIDU_APP_KEY);
		sb.append("&redirect_uri=" + LoginConstants.BAIDU_AUTH_REDIRECT_URI);
		sb.append("&scope=basic&display=popup&response_type=code");
		return sb.toString();
	}
	
	private static String alipay() {
		StringBuilder sb = new StringBuilder(LoginConstants.APIPAY_AUTH_DOMAIN);
		sb.append("/oauth2/publicAppAuthorize.htm");
		sb.append("?app_id=");
		sb.append(LoginConstants.APIPAY_APP_ID);
		sb.append("&scope=auth_user&redirect_uri=");
		try {
			sb.append(URLEncoder.encode(LoginConstants.ALIPAY_AUTH_REDIRECT_URL, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("&state=init");
		return sb.toString();
	}
	
	private static String github() {
		StringBuilder sb = new StringBuilder(LoginConstants.GITHUB_LOGIN_DOMAIN);
		sb.append("/login/oauth/authorize");
		sb.append("?client_id=" + LoginConstants.GITHUB_CLIENT_ID);
		return sb.toString();
	}

}
