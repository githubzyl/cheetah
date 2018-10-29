package top.zylsite.cheetah.base.common;

public class ResponseStatus {
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 参数错误
	 */
	public static final int ParamError = 1001;

	/**
	 * 系统内部错误
	 */
	public static final int InternalError = 1002;

	/**
	 * 认证失败
	 */
	public static final int AuthenticationFailed = 1003;
	
	/**
	 * session超时的状态
	 */
	public static final int SessionTimeout = 9999;

	/**
	 * 未知错误
	 */
	public static final int UnknownError = -1;

}
