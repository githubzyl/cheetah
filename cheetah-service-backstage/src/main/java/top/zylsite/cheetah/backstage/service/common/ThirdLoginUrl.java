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
		case LoginConstants.LOGIN_WAY_DINGDING:
			url = dingding();
			break;
		default:
			break;
		}
		return url;
	}

	private static String qq() {
		StringBuilder sb = new StringBuilder(LoginConstants.QQ_DOMAIN);
		sb.append("/oauth2.0/authorize")
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
		sb.append("/oauth2/publicAppAuthorize.htm")
		   .append("?app_id=").append(LoginConstants.ALIPAY_APP_ID)
		   .append("&redirect_uri=");
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
	
	//调出钉钉二维码扫码登录页
	private static String dingding() {
		StringBuilder sb = new StringBuilder(LoginConstants.DINGDING_DOMAIN);
		sb.append("/connect/qrconnect")
		   .append("?appid=").append(LoginConstants.DINGDING_APP_ID)
		   .append("&redirect_uri=").append(getRedirectUrl(LoginConstants.DINDGING_AUTH_REDIRECT_URI))
		   .append("&response_type=code&scope=snsapi_login&state=STATE");
		return sb.toString();
	}
	
	//调出钉钉手机号+密码登录页面
	@SuppressWarnings("unused")
	private static String dingdingPwd() {
		StringBuilder sb = new StringBuilder(LoginConstants.DINGDING_DOMAIN);
		sb.append("/connect/oauth2/sns_authorize")
		   .append("?appid=").append(LoginConstants.DINGDING_APP_ID)
		   .append("&redirect_uri=").append(getRedirectUrl(LoginConstants.DINDGING_AUTH_REDIRECT_URI))
		   .append("&response_type=code&scope=snsapi_login&state=STATE");
		return sb.toString();
	}
	
	private static String getRedirectUrl(String uri) {
		return LoginConstants.CHEETAH_DOMIAN + uri;
	}

}
