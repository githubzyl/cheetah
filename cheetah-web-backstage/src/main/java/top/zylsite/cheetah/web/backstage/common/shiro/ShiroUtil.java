package top.zylsite.cheetah.web.backstage.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;

public class ShiroUtil {

	public static boolean CHECK_PASSWORD = true;// 校验密码
	public static boolean CHECK_VALIDATECODE = false;// 校验验证码
	public static String VALIDATECODE_ATTR_NAME = "validateCode";// 验证码的属性名
	public static int VCODE_EFFECTIVETIME = 15;// 验证码有效时间,单位分钟
	public static boolean ENABLE_GOTO_BEFORE_LOGIN_URL = true;//开启登陆之后直接跳转到登录之前的url
	
	/**
     * {@link org.apache.shiro.session.Session Session} key used to save a request and later restore it, for example when redirecting to a
     * requested page after login, equal to {@code shiroSavedRequest}.
     */
    public static final String CUSTOM_SAVED_REQUEST_KEY = "customShiroSavedRequest";

	/**
	 * 向session中设置值
	 * 
	 * @param key
	 * @param value
	 * @create: 2018年3月14日 上午10:52:25 zhaoyl
	 * @history:
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 获取session中的值
	 * 
	 * @param key
	 * @return
	 * @create: 2018年3月14日 上午10:53:43 zhaoyl
	 * @history:
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 获取session
	 * 
	 * @return
	 * @create: 2018年3月14日 上午10:49:41 zhaoyl
	 * @history:
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

	/**
	 * 判断用户是不是已经登录
	 * 
	 * @return
	 * @create: 2018年3月14日 上午10:50:11 zhaoyl
	 * @history:
	 */
	public static boolean isAuthenticated() {
		boolean flag = getSubject().isAuthenticated();
		Object obj = getSessionAttribute(ShiroConstants.SESSION_USER_KEY);
		if(null == obj) {
			flag = false;
			getSubject().logout();
		}
		return flag;
	}
	
	/**
	 * 获取登录之前的url
	 * @return
	 */
	public static String getBeforeLoginUrl(ServletRequest servletRequest) {
		if(ENABLE_GOTO_BEFORE_LOGIN_URL) {
			CustomSavedRequest savedRequest = ShiroUtil.getSavedRequest();
			if (null != savedRequest && savedRequest.isJump()) {
				return savedRequest.getRequestUrl();
			}
		}
		return null;
	}
	
	public static SessionUser getSessionUser() {
		Object obj = ShiroUtil.getSessionAttribute(ShiroConstants.SESSION_USER_KEY);
		if (null != obj) {
			SessionUser user = (SessionUser) obj;
			return user;
		}
		return null;
	}
	
	public static void saveRequest(ServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        CustomSavedRequest savedRequest = new CustomSavedRequest(httpRequest);
        session.setAttribute(CUSTOM_SAVED_REQUEST_KEY, savedRequest);
	}
	
	public static CustomSavedRequest getSavedRequest() {
		CustomSavedRequest savedRequest = null;
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        if (session != null) {
            savedRequest = (CustomSavedRequest) session.getAttribute(CUSTOM_SAVED_REQUEST_KEY);
        }
        return savedRequest;
	}

	private static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

}