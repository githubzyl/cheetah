package top.zylsite.cheetah.web.backstage;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * <p>
 * Description: LDAP工具类
 * </p>
 * 
 * @author zhaoyl
 * @date 2018-12-04
 * @version v1.0
 */
public class LDAPUtil {

	public final static int DEFAULT_LDAP_PORT = 389;

	/**
	 * <p>
	 * Description: 获取LDAP连接</>
	 * 
	 * @author zhaoyl
	 * @param host     服务器IP或域名
	 * @param port     连接端口
	 * @param username 域账号
	 * @param password 密码
	 * @return
	 */
	public static LdapContext getLdapContext(String host, int port, String username, String password) {
		Properties env = getProperties(host, port, username, password);
		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
		} catch (NamingException e) {
			ctx = null;
		}
		return ctx;
	}

	/**
	 * <p>
	 * Description: 域账号认证</>
	 * 
	 * @author zhaoyl
	 * @param host     服务器IP或域名
	 * @param port     连接端口
	 * @param username 域账号
	 * @param password 密码
	 * @return
	 */
	public static boolean authenticate(String host, int port, String username, String password) {
		LdapContext ctx = getLdapContext(host, port, username, password);
		return (null == ctx ? false : true);
	}

	/**
	 * <p>
	 * Description: 关闭LDAP连接</>
	 * 
	 * @author zhaoyl
	 * @param ctx
	 */
	public static void close(Context ctx) {
		try {
			if (ctx != null) {
				ctx.close();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static Properties getProperties(String host, int port, String username, String password) {
		if (port <= 0) {
			port = DEFAULT_LDAP_PORT;
		}
		Properties env = new Properties();
		String ldapURL = "LDAP://%s:%d";
		ldapURL = String.format(ldapURL, host, port);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");// "none","simple","strong"
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.PROVIDER_URL, ldapURL);
		return env;
	}

}
