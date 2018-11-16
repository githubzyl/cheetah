package top.zylsite.cheetah.web.backstage.configuation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * 配置使用spring session进行分布式session管理
 * 
 * @author: zhaoyl
 * @since: 2017年8月24日 上午8:49:08
 * @history:
 */
@Configuration
@EnableSpringHttpSession
public class SpringSessionConfiguation {
	
	//默认session超时时间30分钟
	private final static int DEFAULT_SESSION_TIMEOUT = 30 * 60;
	
	@Value("${session.timeout}")
	private String sessionTimeout;

	/**
	 * 配置spring session的存储器，此处使用redis
	 * 
	 * @param redisConnectionFactory
	 * @return
	 * @create: 2017年8月24日 上午8:49:36 zhaoyl
	 * @history:
	 */
	@Bean
	public SessionRepository<?> sessionRepository(JedisConnectionFactory redisConnectionFactory) {
		RedisOperationsSessionRepository redisSessionRepository = new RedisOperationsSessionRepository(
				redisConnectionFactory);
		int time_out = DEFAULT_SESSION_TIMEOUT;
		if(StringUtils.isNotBlank(sessionTimeout)) {
			try {
				time_out = Integer.parseInt(sessionTimeout) * 60;
			}catch(Exception e) {
				time_out = DEFAULT_SESSION_TIMEOUT;
			}
		}
		// session超时时间
		redisSessionRepository.setDefaultMaxInactiveInterval(time_out);
		String redisKeyNameSpace = "cheetah";
		redisSessionRepository.setRedisKeyNamespace(redisKeyNameSpace);
		return redisSessionRepository;
	}

	/**
	 * 配置spring session默认的cookie读写器
	 * 
	 * @return
	 * @create: 2017年8月24日 上午8:50:01 zhaoyl
	 * @history:
	 */
	@Bean
	public DefaultCookieSerializer defaultCookieSerializer() {
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setCookiePath("/");// cookie的写入路径
		cookieSerializer.setUseBase64Encoding(false);
		cookieSerializer.setCookieName("SYS_CHEETAH_SESSION");
		return cookieSerializer;
	}

	/**
	 * 配置CookieHttpSessionStrategy，传入自定义的cookieSerializer
	 * 
	 * @param cookieSerializer
	 * @return
	 * @create: 2017年11月29日 上午11:06:25 zhaoyl
	 * @history:
	 */
	@Bean
	public CookieHttpSessionStrategy cookieHttpSessionStrategy(DefaultCookieSerializer cookieSerializer) {
		CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
		cookieHttpSessionStrategy.setCookieSerializer(cookieSerializer);
		return cookieHttpSessionStrategy;
	}

	/**
	 * 配置自定义的SessionRepositoryFilter，传入自定义的sessionRepository,cookieHttpSessionStrategy
	 * 
	 * @param sessionRepository
	 * @param cookieHttpSessionStrategy
	 * @return
	 * @create: 2017年11月29日 上午11:08:54 zhaoyl
	 * @history:
	 */
	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(
			SessionRepository<S> sessionRepository, CookieHttpSessionStrategy cookieHttpSessionStrategy) {
		SpringSessionRepositoryFilter<S> filter = new SpringSessionRepositoryFilter<>(sessionRepository);
		filter.setHttpSessionStrategy(cookieHttpSessionStrategy);
		return filter;
	}

}
