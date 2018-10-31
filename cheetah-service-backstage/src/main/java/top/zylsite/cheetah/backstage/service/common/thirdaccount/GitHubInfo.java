package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;
import top.zylsite.cheetah.base.utils.HttpUtils;

/**
 * Description: GitHub登录获取相关信息类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class GitHubInfo {
	
	/**
	 * 获取access_token
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken(String code) {
		StringBuilder sb = new StringBuilder(LoginConstants.GITHUB_LOGIN_DOMAIN);
		sb.append("/login/oauth/access_token")
		   .append("?code=").append(code)
		   .append("&client_id=").append(LoginConstants.GITHUB_CLIENT_ID)
		   .append("&client_secret=").append(LoginConstants.GITHUB_CLIENT_SECRET);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取access_token失败,错误信息:请求异常");
		}
		String[] res = result.split("&");
		String[] param = null;
		String access_token = null;
		boolean flag = false;
		for (String s : res) {
			param = s.split("=");
			if ("access_token".equals(param[0])) {
				access_token = param[1];
				flag = true;
			}
		}
		if(flag) {
			return new ThirdResult(true, ThirdResultType.STRING,  access_token);
		}
		return new ThirdResult(false, url, "获取access_token失败,错误信息:"+result);
	}
	
	/**
	 * 获取用户信息
	 * @param access_token
	 * @return
	 */
	public static ThirdResult getUserInfo(String access_token) {
		StringBuilder sb = new StringBuilder(LoginConstants.GITHUB_API_DOMAIN);
		sb.append("/user")
		   .append("?access_token=").append(access_token);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("message")) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:"+result);
		}else {
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		}
	}
	
}
