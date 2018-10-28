package top.zylsite.cheetah.backstage.service.common.enums;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;

/**
 * Description: 登录方式枚举类
 * @author jason
 * 2018年10月28日
 * @version 1.0
 */
public enum LoginWayEnum {

	AP(1,"账号密码"),
	SMS(2,"短信验证码"),
	QQ(LoginConstants.LOGIN_WAY_QQ,"QQ"),
	WECHAT(LoginConstants.LOGIN_WAY_WECHAT,"微信"),
	SINA(LoginConstants.LOGIN_WAY_SINA,"新浪微博"),
	BAIDU(LoginConstants.LOGIN_WAY_BAIDU,"百度"),
	ALIPAY(LoginConstants.LOGIN_WAY_ALIPAY,"支付宝"),
	GITHUB(LoginConstants.LOGIN_WAY_GITHUB,"GITHUB")
	;
	
	int code;
	String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	LoginWayEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static LoginWayEnum getByCode(int code) {
		for(LoginWayEnum loginWayEnum : LoginWayEnum.values()) {
			if(code == loginWayEnum.getCode()) {
				return loginWayEnum;
			}
		}
		return null;
	}
	
	public static LoginWayEnum getByName(String name) {
		for(LoginWayEnum loginWayEnum : LoginWayEnum.values()) {
			if(name.equals(loginWayEnum.getName())) {
				return loginWayEnum;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		for(LoginWayEnum loginWayEnum : LoginWayEnum.values()) {
			if(code == loginWayEnum.getCode()) {
				return loginWayEnum.getName();
			}
		}
		return null;
	}
	
}
