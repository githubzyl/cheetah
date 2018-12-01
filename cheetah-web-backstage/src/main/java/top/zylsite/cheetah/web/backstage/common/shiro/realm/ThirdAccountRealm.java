package top.zylsite.cheetah.web.backstage.common.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.web.backstage.common.shiro.LoginTypeToken;

public class ThirdAccountRealm extends AbstractRealm{

	// 认证.登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		SessionUser user = super.getUserService().findByUserName(username);
		super.assertUser(user);
		// 放入shiro.调用CredentialsMatcher检验密码
		return new SimpleAuthenticationInfo(user, user.getVcPassword(), super.getName());
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		return super.getSimpleAuthorizationInfo();
	}

	
	/**
	 * 重写获取tocken的验证器
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		if (token instanceof LoginTypeToken) {
            LoginTypeToken usernamePasswordLoginType = (LoginTypeToken) token;
            return LoginWayEnum.isThirdAccount(usernamePasswordLoginType.getLoginType());
        }
        return false;
	}

}
