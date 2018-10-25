package top.zylsite.cheetah.base.utils.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
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

	public static void createServiceAndServiceImpl(String modelPackage, boolean overwrite) {
		Set<Class<?>> list = getClassesByPackage(modelPackage);
		if (!CollectionUtils.isEmpty(list)) {
			for (Class<?> clazz : list) {
				try {
					GeneratorClassInfo info = new GeneratorClassInfo(clazz);
					createFile(info, "IService.ftl", "src/main/java/{0}/service/I{1}Service.java", overwrite);
					createFile(info, "ServiceImpl.ftl", "src/main/java/{0}/service/impl/{1}ServiceImpl.java",
							overwrite);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void createController(String modelPackage, String controllerPackage, boolean overwrite)
			throws Exception {
		Set<Class<?>> list = getClassesByPackage(modelPackage);
		if (!CollectionUtils.isEmpty(list)) {
			for (Class<?> clazz : list) {
				try {
					if (StringUtils.isBlank(controllerPackage)) {
						GeneratorClassInfo info = new GeneratorClassInfo(clazz);
						createFile(info, "Controller.ftl", "src/main/java/{0}/controller/{1}Controller.java",
								overwrite);
					} else {
						GeneratorClassInfo info = new GeneratorClassInfo(clazz, controllerPackage);
						// 生成controller
						String controlleFolder = controllerPackage.replace(".", "/");
						createFile(info, "Controller.ftl", "src/main/java/" + controlleFolder + "/{1}Controller.java",
								overwrite);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
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

}