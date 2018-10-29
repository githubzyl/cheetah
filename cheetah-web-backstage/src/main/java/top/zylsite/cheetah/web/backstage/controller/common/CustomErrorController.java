package top.zylsite.cheetah.web.backstage.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import top.zylsite.cheetah.base.common.BaseController;
import top.zylsite.cheetah.base.utils.RequestUtil;
import top.zylsite.cheetah.base.utils.ResponseUtil;

@Controller
@RequestMapping(value = "error")
@EnableConfigurationProperties({ ServerProperties.class })
public class CustomErrorController extends BaseController implements ErrorController {

	private ErrorAttributes errorAttributes;

	@Autowired
	private ServerProperties serverProperties;

	/**
	 * 初始化ExceptionController
	 * 
	 * @param errorAttributes
	 */
	@Autowired
	public CustomErrorController(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping("/403")
	public Object error403(HttpServletRequest request, HttpServletResponse response) {
		if (RequestUtil.isAjaxRequest(request)) {
			Object obj = this.ajaxJSONObject(null, "您无权限访问或操作,请联系管理员", HttpStatus.FORBIDDEN.value());
			ResponseUtil.writeResult(obj, request, response);
			return null;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("common/error/403");
			return modelAndView;
		}
	}

	@RequestMapping("/404")
	public Object error404(HttpServletRequest request, HttpServletResponse response) {
		if (RequestUtil.isAjaxRequest(request)) {
			Object obj = this.ajaxJSONObject(null, "请求未找到,请联系管理员", HttpStatus.NOT_FOUND.value());
			ResponseUtil.writeResult(obj, request, response);
			return null;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("common/error/404");
			return modelAndView;
		}
	}

	@RequestMapping("/500")
	public Object error500(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(getStatus(request).value());
		if (RequestUtil.isAjaxRequest(request)) {
			Object obj = this.ajaxInternalError("服务异常，请联系管理员");
			ResponseUtil.writeResult(obj, request, response);
			return null;
		} else {
			Map<String, Object> model = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addAllObjects(model);
			modelAndView.setViewName("common/error/500");
			return modelAndView;
		}
	}

	/**
	 * Determine if the stacktrace attribute should be included.
	 * 
	 * @param request
	 *            the source request
	 * @param produces
	 *            the media type produced (or {@code MediaType.ALL})
	 * @return if the stacktrace attribute should be included
	 */
	protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
		ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
		if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}

	/**
	 * 是否包含trace
	 * 
	 * @param request
	 * @return
	 */
	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	/**
	 * 获取错误编码
	 * 
	 * @param request
	 * @return
	 */
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	/**
	 * 获取错误的信息
	 * 
	 * @param request
	 * @param includeStackTrace
	 * @return
	 */
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}
