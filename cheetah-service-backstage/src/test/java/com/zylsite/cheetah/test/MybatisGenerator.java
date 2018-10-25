package com.zylsite.cheetah.test;

import org.junit.Test;

import top.zylsite.cheetah.base.utils.generator.MyGeneratorTool;
import top.zylsite.cheetah.base.utils.mybatis.MybatisGeneratorUtil;

public class MybatisGenerator {

	private final static String MYBATIS_GENERATOR_CONFIG = "mybatis/generator/mybatis-generator.xml";
	private final  static String MODEL_PACKAGE = "top.zylsite.cheetah.backstage.model";

	@Test
	public void genModelAndMapper() {
		System.out.println("开始生成model,mapper,mapper.xml");
		MybatisGeneratorUtil.generateCode(MYBATIS_GENERATOR_CONFIG);
		System.out.println("代码生成结束，请刷新项目之后生成service和serviceImpl...");
	}
	
	@Test
	public void genServiceAndServiceImpl() {
		System.out.println("开始生成service,serviceImpl");
		MyGeneratorTool.createServiceAndServiceImpl(MODEL_PACKAGE, false);
		System.out.println("代码生成结束,请刷新项目后查看结果...");
	}

}
