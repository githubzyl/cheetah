package top.zylsite.cheetah.base.common;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.alibaba.fastjson.JSONObject;

/**
 * Description: 控制器基类，主要提供返回消息的方法
 * 2018年10月25日
 * @version 1.0
 */
public class BaseController extends BaseParamHandler{
	
	protected Object ajax(boolean status) {
		if (status) {
			return this.ajaxSuccess(null);
		}
		return this.ajaxParamError(null);
	}

	protected Object ajaxJson(boolean status) {
		if (status) {
			return this.ajaxJsonSuccess(null);
		}
		return this.ajaxJsonParamError(null);
	}

	protected Object ajaxSuccess(Object data) {
		return this.ajax(data, "处理成功", ResponseStatus.SUCCESS);
	}

	protected Object ajaxJsonSuccess(Object data) {
		return this.ajaxJSON(data, "处理成功", ResponseStatus.SUCCESS);
	}

	protected Object ajaxParamError(Object data) {
		return this.ajax(data, "参数错误,请检查", ResponseStatus.ParamError);
	}
	
	protected Object ajaxParamError(String msg) {
		return this.ajax(null, msg, ResponseStatus.ParamError);
	}
	
	protected Object ajaxJsonParamError(String msg) {
		return this.ajaxJSON("", msg, ResponseStatus.ParamError);
	}

	protected Object ajaxInternalError(Object data, String msg) {
		return this.ajax(data, "服务异常,请联系管理员", ResponseStatus.InternalError);
	}

	protected Object ajaxJsonInternalError(String msg) {
		return this.ajaxJSON("", msg, ResponseStatus.InternalError);
	}

	protected Object ajaxAuthenticationFailed() {
		return this.ajax("", "认证失败", ResponseStatus.AuthenticationFailed);
	}

	protected Object ajaxAuthenticationFailed(String msg) {
		return this.ajax("", msg, ResponseStatus.AuthenticationFailed);
	}

	protected Object ajaxInternalError(String msg) {
		return this.ajax(null, msg, ResponseStatus.InternalError);
	}

	protected Object ajaxUnknownError(String msg) {
		return this.ajax(null, msg, ResponseStatus.UnknownError);
	}

	protected Object ajax(Object data, String msg, int status) {
		BaseOut out = new BaseOut();
		out.setStatus(status);
		out.setMsg(msg);
		out.setData(data);
		return out;
	}

	protected String ajaxJSON(Object data, String msg, int status) {
		JSONObject json = this.ajaxJSONObject(data, msg, status);
		return json.toString();
	}

	protected JSONObject ajaxJSONObject(Object data, String msg, int status) {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("msg", msg);
		json.put("data", (null == data || "".equals(data)) ? "" : data);
		return json;
	}

	protected String ajaxJSON(BaseOut out) {
		return this.ajaxJSON(out.getData(), out.getMsg(), out.getStatus());
	}

	protected JSONObject ajaxJSONObject(BaseOut out) {
		return this.ajaxJSONObject(out.getData(), out.getMsg(), out.getStatus());
	}

	protected Object sendError(BindingResult... results) {
		return this.ajax(null, getErrors(results), ResponseStatus.ParamError);
	}

	protected String getErrors(BindingResult[] results) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < results.length; i++) {
			BindingResult result = results[i];
			if (result.hasErrors()) {
				List<FieldError> errors = result.getFieldErrors();
				for (int j = 0; j < errors.size(); j++) {
					String mm = errors.get(j).getDefaultMessage(); // 出错的信息
					str.append(",").append(mm);
				}
			}
		}
		return str.toString().replaceFirst(",", "");
	}
	
}
