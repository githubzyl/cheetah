package top.zylsite.cheetah.web.backstage.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import top.zylsite.cheetah.base.common.BaseController;
import top.zylsite.cheetah.base.common.BaseOut;
import top.zylsite.cheetah.base.common.ResponseStatus;
import top.zylsite.cheetah.base.utils.LoggerFactoryUtil;
import top.zylsite.cheetah.base.utils.RequestUtil;
import top.zylsite.cheetah.base.utils.ResponseUtil;

/**
 * 统一异常处理类
 * 
 * @author: zhaoyl
 * @since: 2017年6月9日 下午1:04:59
 * @history:
 */
@ControllerAdvice
public class ExceptionHandle extends BaseController {

	private final static Logger logger = LoggerFactoryUtil.getLogger(ExceptionHandle.class);

	private final static String ERROR_VIEW_NAME = "page/common/error/500";

	@ExceptionHandler(value = Exception.class)
	public Object handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
		if (RequestUtil.isAjaxRequest(request)) {
			ResponseUtil.writeResult(getAjaxMessage(e,request), request, response);
			return null;
		} else {
			return getNotAjaxView(e);
		}
	}

	/**
	 * 处理ajax请求的返回值
	 * 
	 * @return
	 * @create: 2018年3月14日 上午10:58:39 zhaoyl
	 * @history:
	 */
	private Object getAjaxMessage(Exception e,HttpServletRequest request) {
		BaseOut out = (BaseOut) getExceptionMsg(e,request);
		return this.ajaxJSONObject(out);
	}

	/**
	 * 处理非ajax请求的返回页面
	 * 
	 * @param e
	 * @return
	 * @create: 2018年3月14日 上午11:01:55 zhaoyl
	 * @history:
	 */
	private Object getNotAjaxView(Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", e.getClass().getName());
		modelAndView.addObject("message", e.getMessage());
		modelAndView.setViewName(ERROR_VIEW_NAME);
		return modelAndView;
	}

	// 返回异常信息
	private Object getExceptionMsg(Exception e,HttpServletRequest request) {
		if (e instanceof HttpRequestMethodNotSupportedException) {
			logger.error("请求方法不支持异常，访问的url为："+RequestUtil.getRequestPath(request),e);
			return this.ajax("", e.getMessage(), ResponseStatus.UnknownError);
		} else {
			logger.error("【系统异常】", e);
			return this.ajaxInternalError("服务异常，请联系管理员");
		}
	}

}
