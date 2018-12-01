package top.zylsite.cheetah.web.backstage.common.shiro.realm;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.service.common.enums.LoginWayEnum;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.web.backstage.common.Constants;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.LoginTypeToken;

public class UserRealm extends AbstractRealm {

	private Cache<String, AtomicInteger> passwordRetryCache;

	/**
	 * 支持的登录类型
	 */
	private String supportedLoginType;

	public UserRealm(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
		this.supportedLoginType = LoginWayEnum.AP.getCodeStr();
	}

	// 认证.登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		IUserService userService = super.getUserService();
		SessionUser user = userService.findUser(username);
		super.assertUser(user);
		AtomicInteger atomicInteger = passwordRetryCache.get(username);
		if (null != atomicInteger) {
			if (atomicInteger.get() > ShiroConstants.LOGIN_ERROR_MAXRETRYCOUNT) {
				userService.changeLockStatus(user.getId(), Constants.YES);
				passwordRetryCache.remove(username);
				throw new LockedAccountException();
			}
		}
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
            return this.supportedLoginType.equals(usernamePasswordLoginType.getLoginType());
        }
        return false;
	}

}
