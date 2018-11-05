package top.zylsite.cheetah.base.common.enums;

public enum EnableOrDisableEnum {

	ENABLE("1","启用"),
	DISABLE("0","禁用")
	;
	
	String code;
	String name;
	
	EnableOrDisableEnum(String code, String name) {
		this.code = code;
		this.name= name;
	}
	
	public String code() {
		return this.code;
	}
	
	public String displayname() {
		return this.name;
	}
}
