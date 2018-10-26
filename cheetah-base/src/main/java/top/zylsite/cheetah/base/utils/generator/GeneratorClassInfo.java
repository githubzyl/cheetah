package top.zylsite.cheetah.base.utils.generator;

import top.zylsite.cheetah.base.utils.ReflectionUtilEX;

public class GeneratorClassInfo {

	private String modelName;// 实体类全类名
	private String basePackage; // 基本包
	private String bigClassName; // 大写类名
	private String smallClassName;// 首字母小写的类名
	private String mapperPackage;// mapper包名
	private String servicePackage;// service包名
	private String controllerPackage;// controller包名
	private boolean existIdProperty;// 是否存在id属性
	private String idType;// id的数据类型

	/**
	 * 生成service时，mapperPackage和servicePackage一定要设置,controllerPackage无需设置
	 * 生成controller时，servicePackage和controllerPackage一定要设置,mapperPackage无需设置
	 * @param clazz
	 * @param mapperPackage
	 * @param servicePackage
	 * @param controllerPackage
	 * @throws Exception
	 */
	public GeneratorClassInfo(Class<?> clazz, String basePackage, String mapperPackage, String servicePackage, String controllerPackage)
			throws Exception {
		this.modelName = clazz.getName();
		this.bigClassName = clazz.getSimpleName();
		this.smallClassName = this.bigClassName.substring(0, 1).toLowerCase() + this.bigClassName.substring(1);
		this.basePackage = basePackage;
		this.mapperPackage = mapperPackage;
		this.servicePackage = servicePackage;
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

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

}
