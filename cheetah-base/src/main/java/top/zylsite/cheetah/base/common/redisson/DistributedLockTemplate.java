package top.zylsite.cheetah.base.common.redisson;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description: 分布式锁操作模板
 * </p>
 * 
 * @author zhaoyl
 * @date 2018-12-20
 * @version v1.0
 */
public interface DistributedLockTemplate {

	long DEFAULT_WAIT_TIME = 30;
	long DEFAULT_TIMEOUT = 5;
	TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

	/**
	 * <p>Description: 使用分布式锁,使用锁默认超时时间</>
	 * @author zhaoyl
	 * @param callback 回调接口
	 * @param fairLock 是否使用公平锁
	 * @return
	 */
	<T> T lock(DistributedLockCallback<T> callback, boolean fairLock);

	/**
	 * <p>Description: 使用分布式锁,自定义锁的超时时间</>
	 * @author zhaoyl
	 * @param callback 回调接口
	 * @param leaseTime 锁超时时间,超时后自动释放锁
	 * @param timeUnit 时间单位
	 * @param fairLock 是否使用公平锁
	 * @return
	 */
	<T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit, boolean fairLock);

	/**
	 * <p>Description: 尝试分布式锁,使用锁默认等待时间、超时时间</>
	 * @author zhaoyl
	 * @param callback 回调接口
	 * @param fairLock 是否使用公平锁
	 * @return
	 */
	<T> T tryLock(DistributedLockCallback<T> callback, boolean fairLock);

	/**
	 * <p>Description: 尝试分布式锁,自定义等待时间、超时时间</>
	 * @author zhaoyl
	 * @param callback 回调接口
	 * @param waitTime 获取锁最长等待时间
	 * @param leaseTime 锁超时时间,超时后自动释放锁。
	 * @param timeUnit 时间单位
	 * @param fairLock 是否使用公平锁
	 * @return
	 */
	<T> T tryLock(DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit,
			boolean fairLock);

}
