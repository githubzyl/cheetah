package com.cheetah.web.backstage;

import org.junit.Test;

import top.zylsite.cheetah.base.utils.generator.MyGeneratorTool;

public class ControllerGenerator {

	private final  static String CONTROLLER_PACKAGE = "com.cheetah.web.backstage.controller"; 
	private final  static String MODEL_PACKAGE = "top.zylsite.cheetah.backstage.model";
	
	@Test
	public void genController() throws Exception {
		System.out.println("开始生成controller");
		MyGeneratorTool.createController(MODEL_PACKAGE, CONTROLLER_PACKAGE, false);
		System.out.println("代码生成结束,请刷新项目后查看结果...");
	}

}
