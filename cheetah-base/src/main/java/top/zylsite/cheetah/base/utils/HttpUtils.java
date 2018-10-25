/**
 * 
 */
package top.zylsite.cheetah.base.utils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.google.common.collect.Lists;

/**
 * Description: http请求工具类
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public class HttpUtils {
	
	private static Logger logger = LoggerFactoryUtil.getLogger(HttpUtils.class);

	public static final int timeout = 10;

	/**
	 * post 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url) {
		return post(url, "");
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String post(String url, String data) {
		return postData(url, data);
	}

	/**
	 * 发送http post请求
	 * 
	 * @param url
	 *            url
	 * @param instream
	 *            post数据的字节流
	 * @return
	 */
	public static String post(String url, InputStream instream) {
		try {
			HttpEntity entity = Request.Post(url).bodyStream(instream, ContentType.create("text/html", Consts.UTF_8))
					.execute().returnResponse().getEntity();
			return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
		} catch (Exception e) {
			logger.error("post请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		return httpGet(url);
	}

	public static String getClient(String url) {
		return httpClientGet(url);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	private static String postData(String url, String data) {
		try {
			HttpEntity entity = Request.Post(url).bodyString(data, ContentType.create("text/html", Consts.UTF_8))
					.execute().returnResponse().getEntity();
			return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
		} catch (Exception e) {
			logger.error("post请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送带参数的post请求
	 * 
	 * @param url
	 *            请求的url
	 * @param paramMap
	 *            请求的参数，map键值对
	 * @return
	 * @create: 2017年7月20日 上午10:02:22 zhaoyl
	 * @history:
	 */
	public static String post(String url, Map<String, String> paramMap) {
		List<NameValuePair> paramList = Lists.newArrayListWithCapacity(paramMap.size());
		for (String key : paramMap.keySet()) {
			paramList.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (NameValuePair nameValuePair : paramList) {
			builder.addTextBody(nameValuePair.getName(), nameValuePair.getValue(),
					ContentType.create("text/plain", Consts.UTF_8));
		}
		HttpEntity reqEntity = builder.build();
		return post(url, reqEntity);
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            URL
	 * @param file
	 *            需要上传的文件
	 * @return
	 */
	public static String postFile(String url, File file) {
		return postFile(url, null, file);
	}

	private static String post(String url, HttpEntity reqEntity) {
		String result = null;
		try {
			Request request = Request.Post(url);
			request.body(reqEntity);
			HttpEntity resEntity = request.execute().returnResponse().getEntity();
			result = resEntity != null ? EntityUtils.toString(resEntity, "utf-8") : result;
		} catch (Exception e) {
			logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            URL
	 * @param name
	 *            文件的post参数名称
	 * @param file
	 *            上传的文件
	 * @return
	 */
	public static String postFile(String url, String name, File file) {
		HttpEntity reqEntity = MultipartEntityBuilder.create().addBinaryBody(name, file).build();
		return post(url, reqEntity);
	}

	public static String postFile(String url, String name, InputStream ins, String fileName) {
		String result = null;
		try {
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addBinaryBody(name, ins, ContentType.DEFAULT_BINARY, fileName).build();
			result = post(url, reqEntity);
			if (ins != null) {
				ins.close();
			}
		} catch (Exception e) {
			logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
		}
		return result;

	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            URL
	 * @return 文件的二进制流，客户端使用outputStream输出为文件
	 */
	public static byte[] getFile(String url) {
		try {
			Request request = Request.Get(url);
			HttpEntity resEntity = request.execute().returnResponse().getEntity();
			return EntityUtils.toByteArray(resEntity);
		} catch (Exception e) {
			logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 */
	private static String httpGet(String url) {
		try {
			HttpEntity entity = Request.Get(url).execute().returnResponse().getEntity();
			return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
		} catch (Exception e) {
			logger.error("get请求异常，" + e.getMessage() + "\n get url:" + url);
			e.printStackTrace();
		}
		return null;
	}

	private static String httpClientGet(String url) {
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet request = new HttpGet(url);
			HttpEntity entity = httpClient.execute(request).getEntity();
			return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
		} catch (Exception e) {
			logger.error("get请求异常，" + e.getMessage() + "\n get url:" + url);
			e.printStackTrace();
		}
		return null;
	}

}
