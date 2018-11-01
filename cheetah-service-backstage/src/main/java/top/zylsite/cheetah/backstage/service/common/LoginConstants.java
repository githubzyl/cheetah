package top.zylsite.cheetah.backstage.service.common;

/**
 * Description: 登录相关的常量
 * @author jason
 * 2018年10月28日
 * @version 1.0
 */
public final class LoginConstants {
	
	/**
	 * 登录方式编码
	 */
	public final static int LOGIN_WAY_QQ = 3;
	public final static int LOGIN_WAY_WECHAT = 4;
	public final static int LOGIN_WAY_SINA = 5;
	public final static int LOGIN_WAY_BAIDU = 6;
	public final static int LOGIN_WAY_ALIPAY = 7;
	public final static int LOGIN_WAY_GITHUB = 8;
	public final static int LOGIN_WAY_DINGDING = 9;
	
	/**
	 * 网站地址
	 */
	public final static String CHEETAH_DOMIAN = "http://cheetah.zylsite.top";
	
	/**
	 * QQ登录
	 */
	public final static String QQ_STATE = "854f24ab7ce26aaf90a2";
	public final static String QQ_CLIENT_ID = "101479960";
	public final static String QQ_CLIENT_SECRET = "2ec514d9a7295b881b0f4ad8b3ec47d7";
	public final static String QQ_DOMAIN = "https://graph.qq.com";
	public final static String QQ_AUTH_REDIRECT_URI = "/login/qq/callback";
	
	/**
	 * 微信
	 */
	public final static String WECHAT_APP_ID = "";
	public final static String WECHAT_APP_SECRET = "";
	public final static String WECHAT_OPEN_DOMAIN = "https://open.weixin.qq.com";
	public final static String WECHAT_API_DOMAIN = "https://api.weixin.qq.com";
	public final static String WECHAT_AUTH_REDIRECT_URI = "/login/wechat/callback";
	
	/**
	 * 新浪微博
	 */
	public final static String SINA_APP_KEY = "2170790687";
	public final static String SINA_APP_SECRET = "4fb47b9e490164d9b8e0858be0c1903d";
	public final static String SINA_DOMAIN = "https://api.weibo.com";
	public final static String SINA_AUTH_REDIRECT_URI = "/login/sina/callback";
	
	/**
	 * 百度
	 */
	public final static String BAIDU_APP_KEY = "uB6n560W6VHuOOs4YGW6tuUH";
	public final static String BAIDU_SECRET_KEY = "6WWZdcIdxdxWrU6s2WS7ZHQs1Nae6D5k";
	public final static String BAIDU_DOMAIN = "http://openapi.baidu.com";
	public final static String BAIDU_HTTPS_DOMAIN = "https://openapi.baidu.com";
	public final static String BAIDU_AUTH_REDIRECT_URI = "/login/baidu/callback";
	
	/**
	 * 支付宝
	 */
	public final static String ALIPAY_APP_ID = "2016091500516270";
	public final static String ALIPAY_APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNHjiAJ4lf165blHYYSwfefR7qmvzU8qyWOXP/PO5i62VggZiZCpVTt/qjGGqz75KqGsrwdKAbw1wqxLlu2YVYEiWnejxhUXtP8lkXBq8JsWv7V4nspK1KRw8RLlHUyIrjKCCB8icBKYlLkbzrcwN5pcJgeOc+dOIvNpLFFGpqgKDNMgBzP4K/+x+3jgF5zAU2bSt8wn/2j1ymFoHeyWcjXBDaNGyd1KwZYs2rMQuG16ksaUsl+I15i0AAwgsDWuFrPkXX9V0PN9URxY0+nwRAYComTb5b+LXyzfSWJJnJ9ed64Qocl6bafisTFxaMuMvuADds9IxiSXViyu8gpuG3AgMBAAECggEAWzL2rMvgWhJXWlQd5iFHWqBeBJxZXX3fz84diwxju4YJpE7cwbs3Oj2iOxkijFr92/UoxjF477hXrkQo2ty08m/36b5zmyk/mjEzJlBpesgw0uSF/GuImoJF2IKo2+0m5Rsftxs53eRsGGx9PWS9EDbx4csBFzoOmxiXzcod4+l5O0GrkJMVpdTyIGMY5fnnve3n0UN3S1sOZVFOVnk4W2OVnGH1G20QuC00MXqR1/p8wBHcHXSh1PX7kAcxAYmY2wrUv6ZsakcmD+Wak8pV5rvFHlJubAKE23RmUplxCGEamqw/8GaGzdGW7zWpoSWvl8H7uZscZ5I6QHTLmKUagQKBgQDYozdroecEjvST0DcenrnkiLTPHNbhZqsUS1ahRJG2LXzuewcOMJ6q/ArGAPSha4TelBl3h74BPRhY+Tl0OxfBrH6esrZBwbhTkLU0irWqs7hZpGbSs1s0KKfjS8oz5wCKHRPx20cHDn1N/V9c63fjKY9wiBtkLZMj2iRBsSNO4QKBgQCmwkDY6UoLtQFTETHc9k0xtmCyKQrjoZyJ0vK5WiN8d9YcMdj+XMHhaS0Qmz3VO4NoQ1ZjvgqVp9U3PLTpXfnHuWhKOs4y6FG5boWgiW089y6glnSR8lNmEhelrxOfHXE00piN1j5Wx5zi7k4mgcRU9qvSGkAm2Q19iDi+WdW7lwKBgEyxU3BeK6u4dCtT/f9wD70xDFl6CzRWpWAU38FNxgvphJ3knfXPDozXl3TLZ88xgiNN6MohGwnTOChfapCUTu+4gkbqW41h6EBlLVFBfdjFsRDDG8ehyTro3a6g6R+AuvcqHiJ0D2wXHBMdDMKXdaTM1MPw20mWvM1nb4h2DlrhAoGAZOT7H0JnDpZBauToamyLYBycfK7ga8GIWMtNGGA1QEWxQfXW+G9ezuc34OhMjQspAphbWJXPCaU2F+me++NkETdYpjDWOW7eo3b/WpZLhat4hqkBdpxLYWJo9FHtaUQDDpasvoR1y0OS5KqSljHFfDKS2tgPIHIK+3zD+EeaTZkCgYAa8aL/Cyq1OZTWtXUYfH/kKIgx1XeETCgONH9I9UoPYFCfHjE0o/bmXClEK3hAgpm3o1jPup6ySy7Tkde2I6XCBq9dVEH0XmXE8ii0QpoJsTFf5u/pwSvAtSEt4p2iJKbHye4Cvv2g6Ng2ewISc0O204oFc4F9t1zeHZM6EI/5dw==";
	public final static String ALIPAY_DOMAIN = "https://openapi.alipaydev.com";
	public final static String ALIPAY_AUTH_DOMAIN = "https://openauth.alipaydev.com";
	public final static String ALIPAY_URL = "https://openapi.alipaydev.com/gateway.do";
	public final static String ALIPAY_FORMAT = "json";
	public final static String ALIPAY_CHARSET = "UTF-8";
	public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8e/1s2Qz5wfUXoKDCuvwXSUd5E934oQjChrJxAwy5353xORHZk+t6mJy8hadmUmlXh6T8oplBV0aw9OU1Gtcgs7xFwxjT7Bm55aTQaD5mHgOdd82I4aIb8oXC8eFAE8PpOf7BeiOSYoe7Mar2RrBsiS4MfOjQ93q1CAZdSExJSaDFZbCBcH/lh3JgF/7vPaBPOfYMTxGmpbaMnClSVDQN+hn66g/CauLF1dNfo2o+hJnEQTN0p5Bd81gFUTtKtzmi1QCqzeR0SN5JqhtrStvgOl8wO8g8g4ojStib1gPHR75lQWC2hLAc1+3Xm46wypW1J3mu45I3JlRnpRm4vOpoQIDAQAB";
	public final static String ALIPAY_SIGN_TYPE = "RSA2";
	public final static String ALIPAY_AUTH_REDIRECT_URI = "/login/alipay/callback";
	
	/**
	 * GITHUB
	 */
	public final static String GITHUB_CLIENT_ID = "854f24ab7ce26aaf90a2";
	public final static String GITHUB_CLIENT_SECRET = "698d6360135f0876ac786fe64987427d7de8bbbd";
	public final static String GITHUB_LOGIN_DOMAIN = "https://github.com";
	public final static String GITHUB_API_DOMAIN = "https://api.github.com";
	public final static String GITHUB_AUTH_REDIRECT_URI= "/login/github/callback";
	
	/**
	 * 钉钉
	 */
	public final static String DINGDING_DOMAIN = "https://oapi.dingtalk.com";
	public final static String DINGDING_APP_ID = "dingoao4v27twoqsa3qo3w";
	public final static String DINGDING_APP_SECRET = "865w58JraI2e8wI8TgeVUvOA679xhXGQm4LuPkFMeMQg0zDZUm8QTITiYM_7eD9w";
	public final static String DINDGING_AUTH_REDIRECT_URI = "/login/dingding/callback";
	
}
