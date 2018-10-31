package top.zylsite.cheetah.web.backstage.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Description: 重写UsernamePasswordToken，增加loginType
 * 
 * @author jason 2018年10月31日
 * @version 1.0
 */
public class UsernamePasswordLoginTypeToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	/**
	 * 登陆类型
	 */
	private String loginType;

	public UsernamePasswordLoginTypeToken(String username, String password, boolean rememberMe, String host,
			String loginType) {
		super(username, password, rememberMe, host);
		this.loginType = loginType;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

}
