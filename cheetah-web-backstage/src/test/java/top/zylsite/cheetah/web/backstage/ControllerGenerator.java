package top.zylsite.cheetah.web.backstage;

import org.junit.Test;

import top.zylsite.cheetah.base.utils.generator.MyGeneratorTool;

public class ControllerGenerator {

	private final static String BASE_PACKAGE = "top.zylsite.cheetah.backstage";
	private final static String MODEL_PACKAGE = "top.zylsite.cheetah.backstage.model.master";
	private final static String SERVICE_PACKAGE = "top.zylsite.cheetah.backstage.service.master";
	private final static String CONTROLLER_PACKAGE = "top.zylsite.cheetah.web.backstage.controller.master";

	@Test
	public void genController() throws Exception {
		System.out.println("开始生成controller");
		MyGeneratorTool.createController(BASE_PACKAGE, MODEL_PACKAGE, SERVICE_PACKAGE, CONTROLLER_PACKAGE, false);
		System.out.println("代码生成结束,请刷新项目后查看结果...");
	}

}
