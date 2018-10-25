package top.zylsite.cheetah.base.utils.generator;

import top.zylsite.cheetah.base.utils.ReflectionUtilEX;

public class GeneratorClassInfo {
	
	private String modelName;// 实体类全类名
	private String basePackage; // 基本包
	private String bigClassName; // 大写类名
	private String smallClassName;// 首字母小写的类名
	private String controllerPackage;//controller包名
	private boolean existIdProperty;//是否存在id属性
	private String idType;//id的数据类型

	public GeneratorClassInfo(Class<?> clazz) throws Exception {
		String pkgName = clazz.getPackage().getName();
		this.basePackage = pkgName.substring(0, pkgName.lastIndexOf("."));
		this.modelName = clazz.getName();
		this.bigClassName = clazz.getSimpleName();
		this.smallClassName = this.bigClassName.substring(0, 1).toLowerCase() + this.bigClassName.substring(1);
		this.controllerPackage = this.basePackage + ".controller";
		this.idType = ReflectionUtilEX.isExistField(clazz, "id");
		this.existIdProperty = (null == idType ? false : true);
	}
	
	public GeneratorClassInfo(Class<?> clazz, String controllerPackage) throws Exception {
		String pkgName = clazz.getPackage().getName();
		this.basePackage = pkgName.substring(0, pkgName.lastIndexOf("."));
		this.modelName = clazz.getName();
		this.bigClassName = clazz.getSimpleName();
		this.smallClassName = this.bigClassName.substring(0, 1).toLowerCase() + this.bigClassName.substring(1);
		this.controllerPackage = controllerPackage;
		this.idType = ReflectionUtilEX.isExistField(clazz, "id");
		this.existIdProperty = (null == idType ? false : true);
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getBigClassName() {
		return bigClassName;
	}

	public void setBigClassName(String bigClassName) {
		this.bigClassName = bigClassName;
	}

	public String getSmallClassName() {
		return smallClassName;
	}

	public void setSmallClassName(String smallClassName) {
		this.smallClassName = smallClassName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public boolean isExistIdProperty() {
		return existIdProperty;
	}

	public void setExistIdProperty(boolean existIdProperty) {
		this.existIdProperty = existIdProperty;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

}
