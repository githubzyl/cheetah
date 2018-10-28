package top.zylsite.cheetah.backstage.service.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Description: 第三方登录的URL获取类
 * 
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
		sb.append("/oauth2.0/authorize?")
		   .append("?client_id=").append(LoginConstants.QQ_CLIENT_ID)
		   .append("&redirect_uri=").append(getRedirectUrl(LoginConstants.QQ_AUTH_REDIRECT_URI))
		   .append("&state=" + LoginConstants.QQ_STATE)
		   .append("&response_type=code&scope=get_user_info,list_album,upload_pic,do_like");
		return sb.toString();
	}

	private static String wechat() {
		StringBuilder sb = new StringBuilder(LoginConstants.WECHAT_OPEN_DOMAIN);
		sb.append("/connect/qrconnect")
		   .append("?appid=").append(LoginConstants.WECHAT_APP_ID)
		   .append("&redirect_uri=").append(getRedirectUrl(LoginConstants.WECHAT_AUTH_REDIRECT_URI))
		   .append("&response_type=code&scope=SCOPE&state=STATE#wechat_redirect");
		return sb.toString();
	}

	private static String sina() {
		StringBuilder sb = new StringBuilder(LoginConstants.SINA_DOMAIN);
		sb.append("/oauth2/authorize")
		   .append("?client_id=").append(LoginConstants.SINA_APP_KEY)
		   .append("&redirect_uri=").append(getRedirectUrl(LoginConstants.SINA_AUTH_REDIRECT_URI))
		   .append("&scope=all&state=init&display=default&forcelogin=false");
		return sb.toString();
	}

	private static String baidu() {
		StringBuilder sb = new StringBuilder(LoginConstants.BAIDU_DOMAIN);
		sb.append("/oauth/2.0/authorize")
		   .append("?client_id=").append(LoginConstants.BAIDU_APP_KEY)
		   .append("&redirect_uri=").append(getRedirectUrl(LoginConstants.BAIDU_AUTH_REDIRECT_URI))
		   .append("&scope=basic&display=popup&response_type=code");
		return sb.toString();
	}

	private static String alipay() {
		StringBuilder sb = new StringBuilder(LoginConstants.ALIPAY_AUTH_DOMAIN);
		sb.append("/oauth2/publicAppAuthorize.htm");
		sb.append("?app_id=").append(LoginConstants.ALIPAY_APP_ID);
		sb.append("&redirect_uri=");
		try {
			sb.append(URLEncoder.encode(getRedirectUrl(LoginConstants.ALIPAY_AUTH_REDIRECT_URI), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("&state=init&scope=auth_user");
		return sb.toString();
	}

	private static String github() {
		StringBuilder sb = new StringBuilder(LoginConstants.GITHUB_LOGIN_DOMAIN);
		sb.append("/login/oauth/authorize")
		   .append("?client_id=").append(LoginConstants.GITHUB_CLIENT_ID);
		return sb.toString();
	}

	private static String getRedirectUrl(String uri) {
		return LoginConstants.CHEETAH_DOMIAN + uri;
	}

}
