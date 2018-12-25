package top.zylsite.cheetah.base.common.redisson;

@FunctionalInterface
public interface DistributedLockAction {
	
    void action();
    
}
