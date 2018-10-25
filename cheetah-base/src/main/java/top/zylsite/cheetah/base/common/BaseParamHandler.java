package top.zylsite.cheetah.base.common;

import org.apache.commons.lang3.StringUtils;

import tk.mybatis.mapper.entity.Example.Criteria;
import top.zylsite.cheetah.base.utils.SqlUtil;

public class BaseParamHandler {

	protected final static String PERCENT_SIGN = "%";

	protected void andLeftLike(Criteria criteria, String propertyName, String propertyValue) {
		if (StringUtils.isNotBlank(propertyValue)) {
			criteria.andLike(propertyName, PERCENT_SIGN + getEscapeValue(propertyValue));
		}
	}

	protected void andRightLike(Criteria criteria, String propertyName, String propertyValue) {
		if (StringUtils.isNotBlank(propertyValue)) {
			criteria.andLike(propertyName, getEscapeValue(propertyValue) + PERCENT_SIGN);
		}
	}

	protected void andFullLike(Criteria criteria, String propertyName, String propertyValue) {
		if (StringUtils.isNotBlank(propertyValue)) {
			criteria.andLike(propertyName, PERCENT_SIGN + getEscapeValue(propertyValue) + PERCENT_SIGN);
		}
	}

	protected void orLeftLike(Criteria criteria, String propertyName, String propertyValue) {
		if (StringUtils.isNotBlank(propertyValue)) {
			criteria.orLike(propertyName, PERCENT_SIGN + getEscapeValue(propertyValue));
		}
	}

	protected void orRightLike(Criteria criteria, String propertyName, String propertyValue) {
		if (StringUtils.isNotBlank(propertyValue)) {
			criteria.orLike(propertyName, getEscapeValue(propertyValue) + PERCENT_SIGN);
		}
	}

	protected void orFullLike(Criteria criteria, String propertyName, String propertyValue) {
		if (StringUtils.isNotBlank(propertyValue)) {
			criteria.orLike(propertyName, PERCENT_SIGN + getEscapeValue(propertyValue) + PERCENT_SIGN);
		}
	}

	protected String getEscapeValue(String value) {
		return SqlUtil.escapeLike(value);
	}

}
