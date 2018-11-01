package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;
import top.zylsite.cheetah.base.utils.HttpUtils;

/**
 * Description: DingDing登录获取相关信息类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class DingDindInfo {
	
	/**
	 * 获取access_token
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken() {
		StringBuilder sb = new StringBuilder(LoginConstants.DINGDING_DOMAIN);
		sb.append("/sns/gettoken")
		   .append("?appid=").append(LoginConstants.DINGDING_APP_ID)
		   .append("&appsecret=").append(LoginConstants.DINGDING_APP_SECRET);
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
	 * 获取用户授权的持久授权码
	 * @param access_token
	 * @param tmp_auth_code
	 * @return
	 */
	public static ThirdResult getPersistentCode(String access_token, String tmp_auth_code) {
		StringBuilder sb = new StringBuilder(LoginConstants.DINGDING_DOMAIN);
		sb.append("/sns/get_persistent_code").append("?access_token=").append(access_token);
		String url = sb.toString();
		JSONObject param = new JSONObject();
		param.put("tmp_auth_code", tmp_auth_code);
		String result = HttpUtils.post(url, param.toJSONString());
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取持久授权码失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("persistent_code") && jsonObject.containsKey("openid")) {
			return new ThirdResult(true, ThirdResultType.JSON,  jsonObject);
		}
		return new ThirdResult(false, url, "获取持久授权码失败,错误信息:"+result);
	}
	
	/**
	 * 获取用户授权的SNS_TOKEN
	 * @param access_token
	 * @param openid
	 * @param persistent_code
	 * @return
	 */
	public static ThirdResult getSNSToken(String access_token, String openid, String persistent_code) {
		StringBuilder sb = new StringBuilder(LoginConstants.DINGDING_DOMAIN);
		sb.append("/sns/get_sns_token").append("?access_token=").append(access_token);
		String url = sb.toString();
		JSONObject param = new JSONObject();
		param.put("openid", openid);
		param.put("persistent_code", persistent_code);
		String result = HttpUtils.post(url, param.toJSONString());
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取SNS_TOKEN失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("sns_token")) {
			return new ThirdResult(true, ThirdResultType.STRING,  jsonObject.getString("sns_token"));
		}
		return new ThirdResult(false, url, "获取SNS_TOKEN失败,错误信息:"+result);
	}
	
	/**
	 * 获取用户信息
	 * @param sns_token
	 * @return
	 */
	public static ThirdResult getUserInfo(String sns_token) {
		StringBuilder sb = new StringBuilder(LoginConstants.DINGDING_DOMAIN);
		sb.append("/sns/getuserinfo")
		   .append("?sns_token=").append(sns_token);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("user_info")) {
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject.getJSONObject("user_info"));
		}
		return new ThirdResult(false, url, "获取用户信息失败,错误信息:"+result);
	}
	
}
