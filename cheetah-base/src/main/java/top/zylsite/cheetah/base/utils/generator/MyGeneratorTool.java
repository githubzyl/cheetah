package top.zylsite.cheetah.base.utils.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import top.zylsite.cheetah.base.utils.ResourceReader;

/**
 * Description: 生成简单的service和serviceImpl
 * 
 * @author jason 2018年10月25日
 * @version 1.0
 */
public class MyGeneratorTool {

	private static Configuration config = new Configuration(Configuration.VERSION_2_3_28);
	static {
		try {
			File file = ResourceReader.getResourceAsFile("codetpls");
			config.setDirectoryForTemplateLoading(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param modelPackage
	 * @param mapperPackage
	 * @param servicePackage
	 * @param overwrite
	 */
	public static void createServiceAndServiceImpl(String basePackage, String modelPackage, String mapperPackage,
			String servicePackage, boolean overwrite) {
		Set<Class<?>> list = getClassesByPackage(modelPackage);
		if (!CollectionUtils.isEmpty(list)) {
			for (Class<?> clazz : list) {
				try {
					GeneratorClassInfo info = new GeneratorClassInfo(clazz, basePackage, mapperPackage, servicePackage,
							null);
					String serviceFolder = servicePackage.replace(".", "/");
					createFile(info, "IService.ftl", "src/main/java/" + serviceFolder + "/I{1}Service.java", overwrite);
					createFile(info, "ServiceImpl.ftl", "src/main/java/" + serviceFolder + "/impl/{1}ServiceImpl.java",
							overwrite);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void createController(String basePackage, String modelPackage, String servicePackage,
			String controllerPackage, boolean overwrite) throws Exception {
		Set<Class<?>> list = getClassesByPackage(modelPackage);
		if (!CollectionUtils.isEmpty(list)) {
			for (Class<?> clazz : list) {
				try {
					GeneratorClassInfo info = new GeneratorClassInfo(clazz, basePackage, null, servicePackage,
							controllerPackage);
					// 生成controller
					String controlleFolder = controllerPackage.replace(".", "/");
					createFile(info, "Controller.ftl", "src/main/java/" + controlleFolder + "/{1}Controller.java",
							overwrite);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 生成简单页面以及js
	 * @param pageRootPath 页面存放的根路径
	 * @param jsRootPath js存放的根路径
	 * @param modelName 实体名称,首字母小写，驼峰形式
	 * @param menuName 菜单名称
	 * @param modelCNName 实体中文名称
	 * @param requestRoot 请求根路径
	 * @param overwrite 是否覆盖原文件
	 * @throws Exception 
	 */
	public static void createPageAndJs(String pageRootPath, String jsRootPath, String modelName, String menuName, String modelCNName, String requestRoot, boolean overwrite) throws Exception {
		pageRootPath = StringUtils.defaultIfBlank(pageRootPath, "src/main/resources/templates/page/");
		jsRootPath = StringUtils.defaultIfBlank(jsRootPath, "src/main/resources/static/page/");
		//1、生成列表页面
		String templateFile = "pageList-html.ftl";
		String targetFile = pageRootPath + modelName + "/" + modelName + "List.html";
		Map<String,String> model = new HashMap<>();
		model.put("menuName", menuName);
		model.put("modelName", modelName);
		model.put("htmlTitle", "#{system.name}");
		createFile(model, templateFile, targetFile, overwrite);
		System.out.println("列表页面----->" + targetFile);
		//2、生成编辑页面
		templateFile = "pageEdit-html.ftl";
		targetFile = pageRootPath + modelName + "/" + modelName + "Edit.html";
		createFile(model, templateFile, targetFile, overwrite);
		System.out.println("编辑页面----->" + targetFile);
		//3、生成js
		templateFile = "pageList-js.ftl";
		targetFile = jsRootPath + modelName + "/js/" + modelName + "List.js";
		model.put("modelCNName", modelCNName);
		model.put("requestRoot", requestRoot);
		createFile(model, templateFile, targetFile, overwrite);
		System.out.println("页面js----->" + targetFile);
	}

	// 其他java文件的创建
	private static void createFile(GeneratorClassInfo info, String templateFile, String targetFile, boolean overwrite)
			throws Exception {
		// System.out.println(MessageFormat.format("你好{0},明天去{1}", "小强","打球"));
		Template template = config.getTemplate(templateFile);
		targetFile = MessageFormat.format(targetFile, info.getBasePackage().replace(".", "/"), info.getBigClassName());
		System.out.println(templateFile);
		System.out.println(targetFile);
		File file = new File(targetFile);
		// 如果文件存在则报错，不会覆盖以前的文件
		if (file.exists()) {
			if (overwrite) {
				file.delete();
			} else {
				throw new RuntimeException(file.getName() + "已经存在！");
			}
		}
		File parentFile = file.getParentFile();
		// 创建文件目录
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		template.process(info, new FileWriter(file));
	}

	private static Set<Class<?>> getClassesByPackage(String modelPackage) {
		return PackageUtil.getClasses(modelPackage);
	}
	
	private static void createFile(Map<String,String> model, String templateFile, String targetFile, boolean overwrite) throws Exception {
		Template template = config.getTemplate(templateFile);
		System.out.println(templateFile);
		System.out.println(targetFile);
		File file = new File(targetFile);
		// 如果文件存在则报错，不会覆盖以前的文件
		if (file.exists()) {
			if (overwrite) {
				file.delete();
			} else {
				throw new RuntimeException(file.getName() + "已经存在！");
			}
		}
		File parentFile = file.getParentFile();
		// 创建文件目录
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		template.process(model, new FileWriter(file));
	}

}