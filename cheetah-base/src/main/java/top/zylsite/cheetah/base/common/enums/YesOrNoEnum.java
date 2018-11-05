package top.zylsite.cheetah.base.common.enums;

public enum YesOrNoEnum {

	YES("1","是"),
	NO("0","否")
	;
	
	String code;
	String name;
	
	YesOrNoEnum(String code, String name) {
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
