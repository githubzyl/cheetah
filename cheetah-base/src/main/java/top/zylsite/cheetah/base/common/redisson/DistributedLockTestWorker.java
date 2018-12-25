package top.zylsite.cheetah.base.common.redisson;

import java.util.concurrent.CountDownLatch;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

public class DistributedLockTestWorker implements Runnable {

	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	private final DistributedLockTestManager distributedLockManager;
	private RedissonClient redissonClient;

	public DistributedLockTestWorker(CountDownLatch startSignal, CountDownLatch doneSignal,
			DistributedLockTestManager distributedLockManager, RedissonClient redissonClient) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
		this.distributedLockManager = distributedLockManager;
		this.redissonClient = redissonClient;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + " start");
			startSignal.await();
			Integer count = distributedLockManager.aspect(() -> aspect());
			System.out.println(Thread.currentThread().getName() + ": count = " + count);
			doneSignal.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int aspect(String lockName) {
		return distributedLockManager.aspect(lockName, this);
	}

	private int aspect() {
	    RMap<String, Integer> map = redissonClient.getMap("distributionTest");
	    Integer count1 = map.get("count");
	    if (count1 > 0) {
	        count1 = count1 - 1;
	        map.put("count", count1);
	    }
	    return count1;
	}
	
	public int aspectBusiness(String lockName) {
		RMap<String, Integer> map = redissonClient.getMap("distributionTest");
		Integer count = map.get("count");
		if (count > 0) {
			count = count - 1;
			map.put("count", count);
		}
		return count;
	}

}
