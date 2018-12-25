package top.zylsite.cheetah.web.backstage.controller;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zylsite.cheetah.base.common.redisson.DistributedLockTestManager;
import top.zylsite.cheetah.base.common.redisson.DistributedLockTestWorker;

@RestController
@RequestMapping("/distributedLock")
public class DistributedLockTestController {

	private int count = 10;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private DistributedLockTestManager distributedLockTestManager;
	
	@Resource
	private RedisTemplate<String,Object> redisTemplate;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@GetMapping("/redissonLockTest")
	public String distributedLockTest() throws Exception {
		RMap<String, Integer> map = redissonClient.getMap("distributionTest");
		map.put("count", 8);
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(count);
		for (int i = 0; i < count; ++i) { // create and start threads
			new Thread(
					new DistributedLockTestWorker(startSignal, doneSignal, distributedLockTestManager, redissonClient))
							.start();
		}
		startSignal.countDown(); // let all threads proceed
		doneSignal.await();
		System.out.println("All processors done. Shutdown connection");
		return "finish";
	}

}