package top.zylsite.cheetah.base.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Description: sql参数格式化类，主要针对特殊符号“%”，“_”
 * @author jason
 * 2018年10月25日
 * @version 1.0
 */
public class SqlUtil {

    public static String escape(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.replaceAll("'", "\\\\'");
    }

    public static String escapeLike(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return escape(str).replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
    }

}
