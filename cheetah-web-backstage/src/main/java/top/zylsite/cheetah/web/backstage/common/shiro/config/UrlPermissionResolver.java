package top.zylsite.cheetah.web.backstage.common.shiro.config;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class UrlPermissionResolver implements PermissionResolver {

    /**
     * 经过调试发现
     * subject.isPermitted(url) 中传入的字符串
     * 和自定义 Realm 中传入的权限字符串集合都要经过这个 resolver
     * @param s
     * @return
     */
    @Override
    public Permission resolvePermission(String s) {
        if(s.startsWith("/")){
            return new UrlPermission(s);
        }
        return new WildcardPermission(s);
    }
    
}
