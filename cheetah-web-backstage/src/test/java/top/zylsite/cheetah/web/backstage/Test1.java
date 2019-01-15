package top.zylsite.cheetah.web.backstage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import top.zylsite.cheetah.base.utils.RequestUtil;

public class Test1 {

	@Test
	public void testIp() {
		String str = RequestUtil.getIpLocation("180.76.106.80");
		System.out.println(str.trim());
	}

	@Test
	public void testSubject() {
		Subject subject = SecurityUtils.getSubject();
		String url = "/login";
		subject.isPermitted(url);
	}

	@Test
	public void testLDAP() {
		String host = "ad.gildata.com"; // AD服务器IP
		int port = 389; // 端口
		String username = "zhaoyl";
		String password = "546fewgwe)*";
		String user = String.format("%s@gildata.com", username);
		boolean flag = LDAPUtil.authenticate(host, port, user, password);
		System.out.println(flag);
	}

	@Test
	public void getADUsers() {
		String name = "laiqy";
		String username = "threemanager@gildata.com";
		String password = "Hello1234";
		String host = "ad.gildata.com";
		LdapContext ctx = LDAPUtil.getLdapContext(host, 0, username, password);
		String searchFilter = "(|(sAMAccountName="+name+"*)(name="+name+"*)(displayName="+name+"*)(mail="+name+"*))";
		String searchBase = "OU=恒生聚源,DC=gildata,DC=com";
		String returnedAtts[] = { "name", "sAMAccountName", "mobile", "mail","displayName","department" };
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// searchCtls.setReturningAttributes(returnedAtts);
		List<Map<String, String>> accounts = new ArrayList<>();
		//部门
		LinkedHashMap<String, String> deptMap = new LinkedHashMap<>();
		int i = 1;
		try {
			if (ctx != null) {
				NamingEnumeration<?> answer = ctx.search(searchBase, searchFilter, searchCtls);
				Map<String, String> map = null;
				while (answer.hasMoreElements()) {
					System.out.println("============================================");
					SearchResult sr = (SearchResult) answer.next();
					Attributes Attrs = sr.getAttributes();
					if (Attrs != null) {
						map = new HashMap<>();
						for (NamingEnumeration<?> ne = Attrs.getAll(); ne.hasMore();) {
							Attribute Attr = (Attribute) ne.next();
							System.out.println(Attr.getID()+"="+Attr.get(0));
							// 读取属性值
							for (NamingEnumeration<?> e = Attr.getAll(); e.hasMore();) {
								map.put(Attr.getID(), e.next().toString());
							}
						}
					}
					System.out.println(map.toString());
					String distinguishedName = map.get("distinguishedName");
					if(StringUtils.isNotBlank(distinguishedName)) {
						String[] str = distinguishedName.split(",");
						String[] dps = null;
						for(String s : str) {
							dps = s.split("=");
							if(dps[0].equals("OU")) {
								deptMap.put("dept"+(i++), dps[1]);
							}
						}
					}
					System.out.println(deptMap);
					accounts.add(map);
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("总数：" + accounts.size());
		LDAPUtil.close(ctx);
	}
	
	@Test
	public void testIpToLong() {
		String ip = "192.168.12.36";
		long longIp = ipToLong(ip);
		System.out.println(numberToIp(longIp).equals(ip));
	}
	
	private static String numberToIp(Long number) {
        //等价上面
        String ip = "";
        for (int i = 3; i >= 0; i--) {
            ip += String.valueOf((number & 0xff));
            if (i != 0) {
                ip += ".";
            }
            number = number >> 8;
        }
 
        return ip;
    }

	public static long ipToLong(String ip) {
        String[] ipArray = ip.split("\\.");
        List ipNums = new ArrayList();
        for (int i = 0; i < 4; ++i) {
            ipNums.add(Long.valueOf(Long.parseLong(ipArray[i].trim())));
        }
        long ZhongIPNumTotal = ((Long) ipNums.get(0)).longValue() * 256L * 256L * 256L
                + ((Long) ipNums.get(1)).longValue() * 256L * 256L + ((Long) ipNums.get(2)).longValue() * 256L
                + ((Long) ipNums.get(3)).longValue();
 
        return ZhongIPNumTotal;
    }

}
