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

	/**
	 * QQ登录
	 */
	public final static String QQ_STATE = "854f24ab7ce26aaf90a2";
	public final static String QQ_CLIENT_ID = "101479960";
	public final static String QQ_CLIENT_SECRET = "2ec514d9a7295b881b0f4ad8b3ec47d7";
	public final static String QQ_DOMAIN = "https://graph.qq.com";
	public final static String QQ_AUTH_REDIRECT_URI = "http://cheetah.zylsite.top/login/qq/callback";
	
	/**
	 * 新浪微博
	 */
	public final static String SINA_APP_KEY = "1601293518";
	public final static String SINA_APP_SECRET = "b02cd362cfc904ae4c3875d2f89d6700";
	public final static String SINA_DOMAIN = "https://api.weibo.com";
	public final static String SINA_AUTH_REDIRECT_URL = "http://cheetah.zylsite.top/login/sina/callback";
	
	/**
	 * 百度
	 */
	public final static String BAIDU_APP_KEY = "uB6n560W6VHuOOs4YGW6tuUH";
	public final static String BAIDU_SECRET_KEY = "6WWZdcIdxdxWrU6s2WS7ZHQs1Nae6D5k";
	public final static String BAIDU_DOMAIN = "http://openapi.baidu.com";
	public final static String BAIDU_HTTPS_DOMAIN = "https://openapi.baidu.com";
	public final static String BAIDU_AUTH_REDIRECT_URI = "http://cheetah.zylsite.top/login/baidu/callback";
	
	/**
	 * 支付宝
	 */
	public final static String APIPAY_APP_ID = "2016091500516270";
	public final static String APIPAY_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCqbpNCDv4JVehWa3k/VQ0ihJYp5Tjx42j8wZDjRj07ue4YCEfNeHuxtu6t7z5vHh7g4xulWRnFjETKilrELpOtM5dwaP8Uzn1nhm7wXMwZ7pgoz3HSI8euhNVaZJv8S56JLRLu6g96BaGkSYm2zzvcjGg+yk7aqYLoB/r3ve6bjxYgXMIXm0kTWXctfR7OO/u/zgPr+4fJkzhG+F/cJWocFnF6GL+jtj8H6rH313mdwk+CO4MfctDKRsaJJkn60oBQv49rZFstvPmUY+S9bEO3Dm5yWJKlHr5PpoWPOX8x+5kTfFFZp5Uyinsj1pVLikYIm90mf3Yv+DsVaFqDnr6BAgMBAAECggEBAI7JGQJNATPdD0vnuqr+nISAxDXRXwX4Wir4GGTwobh6KyljNpTEDg1zjz+Vop8Bgykx3CWJInhI7hEqHKUjyIFhKcz5iktqO9IR5LvnYu+O31fBhEBjzZU+oGicxbp3uBiA9M3ItrKqKSQ0n/XSZPp/f46re6jiP1QZ637HVzJnLjvU0bYJyAuKEha4E/nDEoJEPnhjUmk2fWnU00ck1rl67Ny32GbmBOAks8KR5cPRjOioxXqLE9I4xFi7tilZdn5JotOoavzvkn9zXWD22lLbi5+33WP8jAmTBxwqoUuXnqVv3RmJKsT0Pt5xsH9QA5FuFwBPcXchgB5Sc+o07mkCgYEA1HzlsIe5DqukfSkF2YFDVySf8e8hO/wNX+iGH5Hgf4AJeKTpHV5KcloaYbYrjZZT/O0ROIW8BbR3Qi+WoIuAJtYTGe0C69DFzZc8JSD32gvyWTKKDhyyeBgWRrxpqmp+16CAgD1U11FaPg5J4ekYaBpj24aEF6G7u3/IK2kbJUcCgYEAzVUCPMnz+Sk6jNFToJDCY4LgHA5IPAW62uT++rKUE8TNfFE7zqB6YCdNWm77xUjsfwP1FQgGyoBAyRPf/ig4V77zb5W750bVqQrFPCn9KumM+pepAdyqYP0XL0hMkLSb9MKGFfqQu6tGlQtAjra//0nsNePM/z3n8UkOJMSzgfcCgYBvJDDjx+nUsZSk3psvrDvzzbVXTzO781dvjejxhFS/e9H4Z5GDCfm4nEZmS2m2ciXWCo3SWakB9uXWGZwYtz69kYWGLwEzJQ0BPxR3eyoIw+zsGNI7FWmIBVlJgG1odLGJyWrNKMdCpEumX8TnRAu/D+ahQVfAIJlYNjsV8bEAKwKBgQCM/Pn0U5c6VpzlZDZP4hYXzh2kJgpjbGvJMcQCz+vtRwzxd7XhQz/EFjh/CAXIiTIaZNU8A67mDPpvFz6OfX9ygKMW03GadIDw+XxEu3AirwsmZkyeAq7aue3ZgYNROTJ89bSsEsielBR68qb5V3KuEEwpFKoF+LPycX9Mr+OyMQKBgQDPNXK/3xsOqH710zEhpf5YrLXlmkNiK4Ax9pKnEw+03Olvtoc6JT+8HnZ28b7u7WBFY5I/dI1LdzEyMR3Fmvc6OKRpJsJI6keKyURQHI/982oIC1oC3Lojov0T49Bm7yeZ4D79bsMpGpbtJz7/bl+GcDLzqUCss8y/INaaWMiHkQ==";
	public final static String APIPAY_DOMAIN = "https://openapi.alipaydev.com";
	public final static String APIPAY_AUTH_DOMAIN = "https://openauth.alipaydev.com";
	public final static String APIPAY_URL = APIPAY_DOMAIN + "/gateway.do";
	public final static String APIPAY_FORMAT = "json";
	public final static String APIPAY_CHARSET = "UTF-8";
	public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqm6TQg7+CVXoVmt5P1UNIoSWKeU48eNo/MGQ40Y9O7nuGAhHzXh7sbbure8+bx4e4OMbpVkZxYxEyopaxC6TrTOXcGj/FM59Z4Zu8FzMGe6YKM9x0iPHroTVWmSb/EueiS0S7uoPegWhpEmJts873IxoPspO2qmC6Af6973um48WIFzCF5tJE1l3LX0ezjv7v84D6/uHyZM4Rvhf3CVqHBZxehi/o7Y/B+qx99d5ncJPgjuDH3LQykbGiSZJ+tKAUL+Pa2RbLbz5lGPkvWxDtw5ucliSpR6+T6aFjzl/MfuZE3xRWaeVMop7I9aVS4pGCJvdJn92L/g7FWhag56+gQIDAQAB";
	public final static String APIPAY_SIGN_TYPE = "RSA2";
	public final static String ALIPAY_AUTH_REDIRECT_URL = "http://cheetah.zylsite.top/login/alipay/callback";
	
	/**
	 * GITHUB
	 */
	public final static String GITHUB_CLIENT_ID = "854f24ab7ce26aaf90a2";
	public final static String GITHUB_CLIENT_SECRET = "698d6360135f0876ac786fe64987427d7de8bbbd";
	public final static String GITHUB_LOGIN_DOMAIN = "https://github.com";
	public final static String GITHUB_API_DOMAIN = "https://api.github.com";
	
}
