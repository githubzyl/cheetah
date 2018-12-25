package top.zylsite.cheetah.web.backstage.configuation;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.zylsite.cheetah.base.common.redisson.DistributedLockTemplate;
import top.zylsite.cheetah.base.common.redisson.DistributedLockTestManager;
import top.zylsite.cheetah.base.common.redisson.SingleDistributedLockTemplate;

@Configuration
public class DistributedLockConfiguation {
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private int database;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Bean
	public RedissonClient redisson() throws IOException {
		Config config = new Config();
		config.useSingleServer()
		         .setAddress("redis://" + host + ":" + port)
		         .setPassword(password)
		         .setDatabase(database);
		return Redisson.create(config);
	}

	@Bean
	public DistributedLockTemplate distributedLockTemplate(RedissonClient redissonClient) {
		return new SingleDistributedLockTemplate(redissonClient);
	}
	
	@Bean
	public DistributedLockTestManager distributedLockTestManager() {
		return new DistributedLockTestManager();
	}

}
