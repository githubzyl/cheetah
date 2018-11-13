package top.zylsite.cheetah.backstage.service.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Description: 权限类型（资源类型）
 * @author jason
 * 2018年11月12日
 * @version 1.0
 */
public enum ResourceTypeEnum {

	CATALOG("0", "目录"), MENU("1", "菜单"), BUTTON("2", "按钮");

	String code;
	String name;

	ResourceTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static ResourceTypeEnum getByName(String name) {
		for (ResourceTypeEnum loginWayEnum : ResourceTypeEnum.values()) {
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
		for (ResourceTypeEnum loginWayEnum : ResourceTypeEnum.values()) {
			if (code.equals(loginWayEnum.getCode())) {
				return loginWayEnum.getName();
			}
		}
		return null;
	}

}
