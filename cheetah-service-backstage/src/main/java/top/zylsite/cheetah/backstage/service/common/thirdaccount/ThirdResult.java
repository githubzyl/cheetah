package top.zylsite.cheetah.backstage.service.common.thirdaccount;

import top.zylsite.cheetah.backstage.service.common.enums.ThirdResultType;

/**
 * Description: 第三方登录返回结果
 * @author jason
 * 2018年10月30日
 * @version 1.0
 */
public class ThirdResult {

	private boolean success;
	private String error;
	private String requestUrl;
	private ThirdResultType resultType;
	private Object result;
	
	public ThirdResult(boolean success, String requestUrl, String error) {
		this.success = success;
		this.requestUrl = requestUrl;
		this.error = error;
	}
	
	public ThirdResult(boolean success, ThirdResultType resultType, Object result) {
		this.success = success;
		this.resultType = resultType;
		this.result = result;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ThirdResultType getResultType() {
		return resultType;
	}
	public void setResultType(ThirdResultType resultType) {
		this.resultType = resultType;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
}
