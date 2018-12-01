package top.zylsite.cheetah.web.backstage.common.shiro.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import top.zylsite.cheetah.web.backstage.common.shiro.LoginTypeToken;

/**
 * Description: 重写模块化用户验证器,根据登录界面传递的loginType参数,获取唯一匹配的realm
 * 
 * @author jason 2018年10月31日
 * @version 1.0
 */
public class CheetahModularRealmAuthenticator extends ModularRealmAuthenticator {
	
	@Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        LoginTypeToken loginTypeToken = (LoginTypeToken) authenticationToken;
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.supports(loginTypeToken)) {
            	 typeRealms.add(realm);
            }
        }
        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.get(0), loginTypeToken);
        }
        else{
            return doMultiRealmAuthentication(typeRealms, loginTypeToken);
        }
    }

}
