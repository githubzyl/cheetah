package top.zylsite.cheetah.web.backstage;

import org.junit.Test;

import top.zylsite.cheetah.base.utils.generator.MyGeneratorTool;

public class PageAndJsGenerator {

	@Test
	public void genPageAdnJs() throws Exception {
		MyGeneratorTool.createPageAndJs(null, null, "role", "角色管理", "角色", "role", false);
		MyGeneratorTool.createPageAndJs(null, null, "permission", "权限管理", "权限", "permission", false);
		MyGeneratorTool.createPageAndJs(null, null, "job", "定时任务", "定时任务", "scheduledJob", false);
		MyGeneratorTool.createPageAndJs(null, null, "dictionaryType", "字典类型", "字典类型", "dictionaryType", false);
		MyGeneratorTool.createPageAndJs(null, null, "dataDictionary", "数据字典", "数据字典", "dataDictionary", false);
	}
	
}