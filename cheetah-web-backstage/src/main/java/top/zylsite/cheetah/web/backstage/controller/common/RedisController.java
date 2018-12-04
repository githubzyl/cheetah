package top.zylsite.cheetah.web.backstage.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	RedisTemplate<String,String> redisTemplate;

	@GetMapping("/map")
	public Object map(String key, String value) {
		final String mapKey = "Api:all";
		redisTemplate.opsForHash().put(mapKey, key, value);
		return redisTemplate.opsForHash().get(mapKey, key);
	}
	
}
