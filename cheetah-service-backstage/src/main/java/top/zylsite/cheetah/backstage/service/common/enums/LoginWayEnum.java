package top.zylsite.cheetah.backstage.service.common.enums;

import top.zylsite.cheetah.backstage.service.common.LoginConstants;

/**
 * Description: 登录方式枚举类
 * @author jason
 * 2018年10月28日
 * @version 1.0
 */
public enum LoginWayEnum {

	AP(1,"账号密码",false),
	SMS(2,"短信验证码", false),
	QQ(LoginConstants.LOGIN_WAY_QQ,"QQ",true),
	WECHAT(LoginConstants.LOGIN_WAY_WECHAT,"微信",true),
	SINA(LoginConstants.LOGIN_WAY_SINA,"新浪微博",true),
	BAIDU(LoginConstants.LOGIN_WAY_BAIDU,"百度",true),
	ALIPAY(LoginConstants.LOGIN_WAY_ALIPAY,"支付宝",true),
	GITHUB(LoginConstants.LOGIN_WAY_GITHUB,"GitHub",true),
	DINGDING(LoginConstants.LOGIN_WAY_DINGDING,"钉钉",true)
	;
	
	int code;
	String name;
	boolean thirdAccount;
	String codeStr;

	public boolean isThirdAccount() {
		return thirdAccount;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String getCodeStr() {
		return String.valueOf(code);
	}

	LoginWayEnum(int code, String name,boolean thirdAccount) {
		this.code = code;
		this.name = name;
		this.thirdAccount = thirdAccount;
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
	
	public static boolean isThirdAccount(String code) {
		for(LoginWayEnum loginWayEnum : LoginWayEnum.values()) {
			if(code.equals(loginWayEnum.getCodeStr()) && loginWayEnum.isThirdAccount()) {
				return true;
			}
		}
		return false;
	}
	
}
