package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;
import top.zylsite.cheetah.base.utils.HttpUtils;

/**
 * Description: Baidu登录获取相关信息类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class BaiduInfo {
	
	private final static String LARGE_IMG_URL = "http://tb.himg.baidu.com/sys/portrait/item/";
	
	/**
	 * 获取access_token
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken(String code) {
		StringBuilder sb = new StringBuilder(LoginConstants.BAIDU_DOMAIN);
		sb.append("/oauth/2.0/token?grant_type=authorization_code")
		   .append("&code=").append(code)
		   .append("&client_id=").append(LoginConstants.BAIDU_APP_KEY)
		   .append("&client_secret=").append(LoginConstants.BAIDU_SECRET_KEY)
		   .append("&redirect_uri=").append(LoginConstants.CHEETAH_DOMIAN).append(LoginConstants.BAIDU_AUTH_REDIRECT_URI);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取access_token失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("access_token")) {
			return new ThirdResult(true, ThirdResultType.STRING,  jsonObject.getString("access_token"));
		}
		return new ThirdResult(false, url, "获取access_token失败,错误信息:"+result);
	}
	
	/**
	 * 获取当前登录用户的信息
	 * @param access_token
	 * @return
	 */
	public static ThirdResult getLoggedInUser(String access_token) {
		StringBuilder sb = new StringBuilder(LoginConstants.BAIDU_HTTPS_DOMAIN);
		sb.append("/rest/2.0/passport/users/getLoggedInUser")
		   .append("?access_token=").append(access_token);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("uid")) {
			if(jsonObject.containsKey("portrait")) {
				jsonObject.put("portrait", LARGE_IMG_URL + jsonObject.getString("portrait"));
			}
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		}else {
			return new ThirdResult(false, url, "获取当前登录用户信息失败,错误信息:"+result);
		}
	}
	
	/**
	 * 获取详细用户信息
	 * @param access_token
	 * @return
	 */
	public static ThirdResult getUserInfo(String access_token) {
		StringBuilder sb = new StringBuilder(LoginConstants.BAIDU_HTTPS_DOMAIN);
		sb.append("/rest/2.0/passport/users/getInfo")
		   .append("?access_token=").append(access_token);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("userid")) {
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		}else {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:"+result);
		}
	}
	
}
