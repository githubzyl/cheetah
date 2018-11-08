package top.zylsite.cheetah.base.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

	private final static String CLASS_PATH = "classpath:";
	private final static String CLASS_PATH_STAR = "classpath*:";

	/**
	 * 读取Properties配置文件转换成map
	 * 
	 * @param propPath
	 * @return
	 * @create: 2017年7月3日 下午1:38:17 zhaoyl
	 * @history:
	 */
	public static Map<String, String> readProperties(String propPath) {
		InputStream is = null;
		Map<String, String> dataMap = new LinkedHashMap<>();
		try {
			// 加载配置文件
			is = getResourceAsInputStream(propPath);
			if (null == is) {
				return dataMap;
			}
			// 读取配置文件
			Properties prop = new OrderedProperties();
			prop.load(is);

			Set<String> keys = prop.stringPropertyNames();
			for(String key : keys) {
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
			Resource[] resources = null;
			if (resourcePath.startsWith(CLASS_PATH) || resourcePath.startsWith(CLASS_PATH_STAR)) {
				resources = resolver.getResources(resourcePath);
			} else {
				resources = resolver.getResources(CLASS_PATH_STAR + resourcePath);
			}
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
//读取propertis文件时按照原来的顺序读取出来
class OrderedProperties extends Properties {
	 
    private static final long serialVersionUID = -4627607243846121965L;
     
    private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();
 
    public synchronized Enumeration<Object> keys() {
        return Collections.<Object> enumeration(keys);
    }
 
    public synchronized Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }
 
    public Set<Object> keySet() {
        return keys;
    }
 
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();
        for (Object key : this.keys) {
            set.add((String) key);
        }
        return set;
    }
    
}
