package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;
import top.zylsite.cheetah.base.utils.HttpUtils;

/**
 * Description: Sina登录获取相关信息类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class SinaInfo {
	
	/**
	 * 获取access_token和uid
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken(String code) {
		StringBuilder sb = new StringBuilder(LoginConstants.SINA_DOMAIN);
		sb.append("/oauth2/access_token?grant_type=authorization_code")
		   .append("&code=").append(code)
		   .append("&client_id=").append(LoginConstants.SINA_APP_KEY)
		   .append("&client_secret=").append(LoginConstants.SINA_APP_SECRET)
		   .append("&redirect_uri=").append(LoginConstants.CHEETAH_DOMIAN).append(LoginConstants.SINA_AUTH_REDIRECT_URI);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取access_token失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("access_token") && jsonObject.containsKey("uid")) {
			return new ThirdResult(true, ThirdResultType.JSON,  jsonObject);
		}
		return new ThirdResult(false, url, "获取access_token失败,错误信息:"+result);
	}
	
	/**
	 * 获取用户信息
	 * @param access_token
	 * @param uid
	 * @return
	 */
	public static ThirdResult getUserInfo(String access_token, String uid) {
		StringBuilder sb = new StringBuilder(LoginConstants.SINA_DOMAIN);
		sb.append("/2/users/show.json")
		   .append("?access_token=").append(access_token)
		   .append("&uid=").append(uid);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("error_code")) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:"+result);
		}else {
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		}
	}
	
}
