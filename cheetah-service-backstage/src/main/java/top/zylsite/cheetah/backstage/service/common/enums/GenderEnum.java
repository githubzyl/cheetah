package top.zylsite.cheetah.backstage.service.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Description: 性别
 * 
 * @author jason 2018年10月28日
 * @version 1.0
 */
public enum GenderEnum {

	MALE("0", "男"), FEMALE("1", "女");

	String code;
	String name;

	GenderEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static GenderEnum getByName(String name) {
		for (GenderEnum loginWayEnum : GenderEnum.values()) {
			if (name.equals(loginWayEnum.getName())) {
				return loginWayEnum;
			}
		}
		return null;
	}

	public static String getNameByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (GenderEnum loginWayEnum : GenderEnum.values()) {
			if (code.equals(loginWayEnum.getCode())) {
				return loginWayEnum.getName();
			}
		}
		return null;
	}

}
