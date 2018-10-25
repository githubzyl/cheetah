package top.zylsite.cheetah.base.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResponseUtil {

	private final static String JSONP_CALLBACK = "jsonpcallback";// 跨域请求传递的参数

	// 获取跨域返回结果
	public static String getJsonpResult(HttpServletRequest request, Object result) {
		return getJsonpcallback(result, getJsonpParameter(request));
	}

	public static String getJsonResult(Object result) {
		return getJsonStr(result);
	}

	// 根据请求参数返回不同的结果
	public static Object getResult(Object result, HttpServletRequest request) {
		String jsonpcallback = isCrossDomain(request);
		if (null != jsonpcallback) {
			return getJsonpcallback(result, jsonpcallback);
		}
		return result;
	}

	// 根据请求参数向客户端写出不同的结果
	public static void writeResult(Object result, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("application/json; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String jsonpcallback = isCrossDomain(request);
			if (null != jsonpcallback) {
				writer.write(getJsonpcallback(result, jsonpcallback));
			} else {
				writer.write(getJsonStr(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断是否是跨域请求
	public static String isCrossDomain(HttpServletRequest request) {
		String jsonpcallback = getJsonpParameter(request);
		String httpMethod = request.getMethod();
		if ("GET".equals(httpMethod) && StringUtils.isNotBlank(jsonpcallback)) {
			return jsonpcallback;
		}
		return null;
	}

	private static String getJsonpcallback(Object result, String jsonpcallback) {
		String jsonStr = getJsonStr(result);
		jsonpcallback += "(" + jsonStr + ")";
		return jsonpcallback;
	}

	private static String getJsonpParameter(HttpServletRequest request) {
		return request.getParameter(JSONP_CALLBACK);
	}

	public static String getJsonStr(Object result) {
		return JSONObject.toJSONString(result);
	}
	
	public static String getJsonStrWithNull(Object result) {
		return JSONObject.toJSONString(result, SerializerFeature.WRITE_MAP_NULL_FEATURES);
	}

}
