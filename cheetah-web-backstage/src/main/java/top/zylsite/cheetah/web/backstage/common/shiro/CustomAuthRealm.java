package top.zylsite.cheetah.web.backstage.common.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.utils.SpringUtil;
import top.zylsite.cheetah.web.backstage.common.Constants;

public class CustomAuthRealm extends AuthorizingRealm {

	private Cache<String, AtomicInteger> passwordRetryCache;

	public CustomAuthRealm(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	// 认证.登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;// 获取用户输入的token
		String username = utoken.getUsername();
		IUserService userService = SpringUtil.getBean(IUserService.class);
		SessionUser user = userService.findByUserName(username);
		if (null == user) {// 用户名不存在
			throw new IncorrectCredentialsException();
		}
		if (Constants.YES.equals(user.getcStatus())) {//启用，禁用
			throw new DisabledAccountException();
		}
		if (Constants.YES.equals(user.getcLockStatus())) {//锁定，非锁定
			throw new LockedAccountException();
		}
		AtomicInteger atomicInteger = passwordRetryCache.get(username);
		if (null != atomicInteger) {
			if (atomicInteger.get() > ShiroConstants.LOGIN_ERROR_MAXRETRYCOUNT) {
				userService.changeLockStatus(user.getId(), Constants.YES);
				passwordRetryCache.remove(username);
				throw new LockedAccountException();
			}
		}
		// 放入shiro.调用CredentialsMatcher检验密码
		return new SimpleAuthenticationInfo(user, user.getVcPassword(), this.getClass().getName());
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		Object obj = ShiroUtil.getSessionAttribute(ShiroConstants.SESSION_PERMISSION_KEY);
		if (null != obj && obj instanceof SimpleAuthorizationInfo) {
			return (SimpleAuthorizationInfo) obj;
		}
		return new SimpleAuthorizationInfo();
	}

}
