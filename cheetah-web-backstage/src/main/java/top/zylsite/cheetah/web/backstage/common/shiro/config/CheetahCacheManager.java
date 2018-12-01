package top.zylsite.cheetah.web.backstage.common.shiro.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class CheetahCacheManager implements CacheManager {

	private Cache<String, AtomicInteger> passwordRetryCache;

	@SuppressWarnings("unchecked")
	@Override
	public Cache<String, AtomicInteger> getCache(String name) throws CacheException {
		// TODO Auto-generated method stub
		return passwordRetryCache;
	}

}
