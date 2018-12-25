package top.zylsite.cheetah.base.common.redisson;

import java.util.function.Supplier;

public class DistributedLockTestManager {
	
	@DistributedLock(argNum = 1, lockNameSuffix = ".lock")
	public Integer aspect(String lockName, DistributedLockTestWorker worker) {
		return worker.aspectBusiness(lockName);
	}

	@DistributedLock(lockName = "lock", lockNameSuffix = ".lock")
	public int aspect(Supplier<Integer> supplier) {
	    return supplier.get();
	}
	
	@DistributedLock(lockName = "lock", lockNameSuffix = ".lock")
	public void doSomething(DistributedLockAction action) {
	    action.action();
	}
	
}
