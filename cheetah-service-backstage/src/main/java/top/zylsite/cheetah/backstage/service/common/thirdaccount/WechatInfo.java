package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;
import top.zylsite.cheetah.base.utils.HttpUtils;

/**
 * Description: Wechat登录获取相关信息类
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class WechatInfo {

	/**
	 * 获取access_token和openid
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken(String code) {
		StringBuilder sb = new StringBuilder(LoginConstants.WECHAT_API_DOMAIN);
		sb.append("/sns/oauth2/access_token?grant_type=authorization_code")
		   .append("&code=").append(code)
		   .append("&appid=").append(LoginConstants.WECHAT_APP_ID)
		   .append("&secret=").append(LoginConstants.WECHAT_APP_SECRET);
		String url = sb.toString();
		String result = HttpUtils.post(url);
		if(StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取access_token失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("access_token") && jsonObject.containsKey("openid")) {
			return new ThirdResult(true, ThirdResultType.JSON,  jsonObject);
		}
		return new ThirdResult(false, url, "获取access_token失败,错误信息:"+result);
	}
	
	/**
	 * 获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static ThirdResult getUserInfo(String access_token, String openid) {
		StringBuilder sb = new StringBuilder(LoginConstants.WECHAT_API_DOMAIN);
		sb.append("/sns/userinfo")
		   .append("?access_token=").append(access_token)
		   .append("&openid=").append(openid);
		String url = sb.toString();
		String result = HttpUtils.get(url);
		if (StringUtils.isBlank(result)) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:请求异常");
		}
		JSONObject jsonObject = JSON.parseObject(result);
		if(jsonObject.containsKey("errcode")) {
			return new ThirdResult(false, url, "获取用户信息失败,错误信息:"+result);
		}else {
			return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		}
	}
	
}
