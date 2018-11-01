package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;
import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;

/**
 * Description: Alipay登录获取相关信息类
 * 
 * @author jason 2018年10月30日
 * @version 1.0
 */
public class AlipayInfo {

	/**
	 * 获取access_token
	 * 
	 * @param code
	 * @return
	 */
	public static ThirdResult getAccessToken(String code) {
		AlipaySystemOauthTokenRequest authRequest = new AlipaySystemOauthTokenRequest();
		authRequest.setCode(code);
		authRequest.setGrantType("authorization_code");
		try {
			AlipaySystemOauthTokenResponse oauthTokenResponse = getAlipayClient().execute(authRequest);
			return new ThirdResult(true, ThirdResultType.STRING, oauthTokenResponse.getAccessToken());
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return new ThirdResult(false, "", "获取access_token失败,错误信息:" + e.getMessage());
		}
	}
	
	/**
	 * 获取用户信息
	 * @param access_token
	 * @return
	 */
	public static ThirdResult getUserInfo(String access_token) {
		AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
		try {
			AlipayUserInfoShareResponse userInfoResponse = getAlipayClient().execute(userInfoRequest, access_token);
		    if (userInfoResponse.isSuccess()) {
		    	JSONObject jsonObject = new JSONObject();
		    	jsonObject.put("nickName", userInfoResponse.getNickName());
		    	jsonObject.put("userId", userInfoResponse.getUserId());
		    	jsonObject.put("avatar", userInfoResponse.getAvatar());
		    	return new ThirdResult(true, ThirdResultType.JSON, jsonObject);
		    } else {
		    	 return new ThirdResult(false, "", "获取access_token失败,错误信息:" + JSONObject.toJSONString(userInfoResponse));
		    } 
		} catch (AlipayApiException e) {
			return new ThirdResult(false, "", "获取access_token失败,错误信息:" + e.getMessage());
		}
	}
	
	private static AlipayClient getAlipayClient() {
		return new DefaultAlipayClient(LoginConstants.ALIPAY_URL, LoginConstants.ALIPAY_APP_ID,
				LoginConstants.ALIPAY_APP_PRIVATE_KEY, LoginConstants.ALIPAY_FORMAT, LoginConstants.ALIPAY_CHARSET,
				LoginConstants.ALIPAY_PUBLIC_KEY, LoginConstants.ALIPAY_SIGN_TYPE);
	}
	
}
