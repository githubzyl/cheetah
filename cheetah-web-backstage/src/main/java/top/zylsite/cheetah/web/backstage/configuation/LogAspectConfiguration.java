package top.zylsite.cheetah.web.backstage.configuation;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import top.zylsite.cheetah.backstage.model.dto.SystemLog;
import top.zylsite.cheetah.backstage.model.master.UserViewLog;
import top.zylsite.cheetah.backstage.service.master.IUserViewLogService;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;
import top.zylsite.cheetah.web.backstage.common.shiro.ShiroUtil;

@Aspect
@Configuration
public class LogAspectConfiguration {

	@Autowired
	private IUserViewLogService userViewLogService;

	@Pointcut("@annotation(top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs)")
	public void controllerAspect() {

	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 *
	 * @param joinPoint 切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			Integer userId = null;
			if (null != ShiroUtil.getSessionUser()) {
				userId = ShiroUtil.getSessionUser().getId();
			}
			String methodDescription = getControllerMethodDescription(joinPoint);
			UserViewLog userViewLog = SystemLog.createUserViewLog(request, userId, methodDescription);
			userViewLogService.insertInfo(userViewLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(ControllerLogs.class).description();
					break;
				}
			}
		}
		return description;
	}

}
