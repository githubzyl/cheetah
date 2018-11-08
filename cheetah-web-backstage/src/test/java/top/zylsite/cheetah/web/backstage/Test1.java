package top.zylsite.cheetah.web.backstage;

import org.junit.Test;

import top.zylsite.cheetah.base.utils.RequestUtil;

public class Test1 {

	@Test
	public void testIp() {
		String str = RequestUtil.getIpLocation("180.76.106.80");
		System.out.println(str.trim());
	}
	
}
