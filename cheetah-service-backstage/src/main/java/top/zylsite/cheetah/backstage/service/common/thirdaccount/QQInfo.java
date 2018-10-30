package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;
import top.zylsite.cheetah.base.utils.HttpUtils;

/**
 * Description: QQ登录获取相关信息类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class QQInfo {
	
	/**
	 * 获取access_token
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken(String code) {
		StringBuilder sb = new StringBuilder(LoginConstants.QQ_DOMAIN);
		sb.append("/oauth2.0/token?grant_type=authorization_code")
		   .append("&code=").append(code)
		   .append("&client_id=").append(LoginConstants.QQ_CLIENT_ID)
		   .append("&client_secret=").append(LoginConstants.QQ_CLIENT_SECRET)
		   .append("&redirect_uri=").append(LoginConstants.CHEETAH_DOMIAN).append(LoginConstants.QQ_AUTH_REDIRECT_URI);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取access_token失败,错误信息:请求异常");
		}
		String access_token = null;
		boolean flag = false;
		String[] res = result.split("&");
		for (String s : res) {
			if ("access_token".equals(s.split("=")[0])) {
				access_token = s.split("=")[1];
				flag = true;
				break;
			}
		}
		if(!flag) {
			return new ThirdResult(false, url, "获取access_token失败,错误信息:"+result);
		}
		return new ThirdResult(true, ThirdResultType.STRING, access_token);
	}
	
	/**
	 * 获取openid
	 * @param access_token
	 * @return
	 */
	public static ThirdResult getOpneId(String access_token) {
		StringBuilder sb = new StringBuilder(LoginConstants.QQ_DOMAIN);
		sb.append("/oauth2.0/me?access_token=").append(access_token);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取openid失败,错误信息:请求异常");
		}
		String openid = null;
		try {
			int startIndex = result.indexOf("{");
			int endIndex = result.lastIndexOf("}");
			result = result.substring(startIndex, endIndex + 1);
			JSONObject jsonObject = JSONObject.parseObject(result);
			openid = jsonObject.getString("openid");
			return new ThirdResult(true, ThirdResultType.STRING, openid);
		}catch(Exception e) {
			return new ThirdResult(false, url, "获取openid失败,错误信息:"+result);
		}
	}
	
	/**
	 * 获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static ThirdResult getUserInfo(String access_token, String openid) {
		StringBuilder sb = new StringBuilder(LoginConstants.QQ_DOMAIN);
		sb.append("/user/get_user_info")
		   .append("?access_token=").append(access_token)
		   .append("&openid=").append(openid)
		   .append("&oauth_consumer_key=").append(LoginConstants.QQ_CLIENT_ID);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(0 == jsonObject.getIntValue("ret")) {
			jsonObject.remove("ret");
			jsonObject.remove("msg");
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		}else {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:"+result);
		}
	}
	
	//获取头像URL
	public static String getPictureUrl(JSONObject jsonObject) {
		String pUrl = null;
		if(jsonObject.containsKey("figureurl_qq_2")) {
			pUrl = jsonObject.getString("figureurl_qq_2");
		}else{
			pUrl = jsonObject.getString("figureurl_qq_1");
		}
		return pUrl;
	}
	
}
