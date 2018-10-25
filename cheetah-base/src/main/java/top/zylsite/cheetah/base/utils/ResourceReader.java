package top.zylsite.cheetah.base.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 读取资源配置文件
 * 
 * @author: zhaoyl
 * @since: 2017年7月3日 上午9:59:34
 * @history:
 */
public class ResourceReader {

	private static final Logger logger = LoggerFactoryUtil.getLogger(ResourceReader.class);

	/**
	 * 读取Properties配置文件转换成map
	 * 
	 * @param propPath
	 * @return
	 * @create: 2017年7月3日 下午1:38:17 zhaoyl
	 * @history:
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> readProperties(String propPath) {
		InputStream is = null;
		Map<String, String> dataMap = new HashMap<>();
		try {
			// 加载配置文件
			is = getResourceAsInputStream(propPath);
			if (null == is) {
				return dataMap;
			}
			// 读取配置文件
			Properties prop = new Properties();
			prop.load(is);

			Enumeration<String> en = (Enumeration<String>) prop.propertyNames();
			String key = null;
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				dataMap.put(key, prop.getProperty(key));
			}
			return dataMap;
		} catch (IOException e) {
			logger.error(propPath + " 文件未找到");
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataMap;
	}

	/**
	 * 将读取的资源文件转换成输入流
	 * 
	 * @param resourcePath
	 * @return
	 * @create: 2017年7月3日 下午1:36:47 zhaoyl
	 * @history:
	 */
	public static InputStream getResourceAsInputStream(String resourcePath) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resolver.getResources("classpath*:" + resourcePath);
			if (null != resources && resources.length > 0) {
				return resources[0].getInputStream();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File getResourceAsFile(String resourcePath) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resolver.getResources("classpath*:" + resourcePath);
			if (null != resources && resources.length > 0) {
				return resources[0].getFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
