package top.zylsite.cheetah.web.backstage.common.shiro.realm;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.utils.SpringUtil;
import top.zylsite.cheetah.web.backstage.common.Constants;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroConstants;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

public abstract class AbstractRealm extends AuthorizingRealm{

	protected IUserService getUserService() {
		return SpringUtil.getBean(IUserService.class);
	}
	
	protected void assertUser(SessionUser user) {
		if (null == user) {// 用户名不存在
			throw new IncorrectCredentialsException();
		}
		if (Constants.YES.equals(user.getcStatus())) {// 启用，禁用
			throw new DisabledAccountException();
		}
		if (Constants.YES.equals(user.getcLockStatus())) {// 锁定，非锁定
			throw new LockedAccountException();
		}
	}
	
	protected SimpleAuthorizationInfo getSimpleAuthorizationInfo() {
		Object obj = ShiroUtil.getSessionAttribute(ShiroConstants.SESSION_PERMISSION_KEY);
		if (null != obj && obj instanceof SimpleAuthorizationInfo) {
			return (SimpleAuthorizationInfo) obj;
		}
		return new SimpleAuthorizationInfo();
	}
	
}
