package top.zylsite.cheetah.web.backstage.common.shiro.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import top.zylsite.cheetah.base.utils.EncdDecd;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.LoginTypeToken;

public class UserCredentialsMatcher extends SimpleCredentialsMatcher {

	private Cache<String, AtomicInteger> passwordRetryCache;

	public UserCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		char[] password = null;
		if(token instanceof LoginTypeToken) {
			LoginTypeToken loginTypeToken = (LoginTypeToken) token;
			password = loginTypeToken.getPassword();
		}
		// 获取用户名
		String username = (String) token.getPrincipal();
		// 获取登录记录次数
		AtomicInteger atomicInteger = passwordRetryCache.get(username);
		if (null == atomicInteger) {
			atomicInteger = new AtomicInteger(0);
			passwordRetryCache.put(username, atomicInteger);
		}
		int currentCount = atomicInteger.incrementAndGet();
		if (currentCount > ShiroConstants.LOGIN_ERROR_RETRYCOUNT) {// 登陆出错次数已达到上限
			throw new ExcessiveAttemptsException();
		}
		// 获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
		String inPassword = new String(password);
		inPassword = EncdDecd.MD5String(inPassword);
		// 获得数据库中的密码
		String dbPassword = (String) info.getCredentials();
		// 进行密码的比对
		boolean matches = this.equals(inPassword, dbPassword);
		if (matches) {
			passwordRetryCache.remove(username);
		}
		return matches;
	}

}
