package top.zylsite.cheetah.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: slf4j日志记录工具类
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public final class LoggerFactoryUtil {

	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

}
