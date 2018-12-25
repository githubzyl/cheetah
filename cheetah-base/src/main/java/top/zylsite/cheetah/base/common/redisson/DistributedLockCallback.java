package top.zylsite.cheetah.base.common.redisson;

/**
 * <p>Description: 分布式锁回调接口</p>
 * @author   zhaoyl
 * @date      2018-12-20
 * @version  v1.0
 */
public interface DistributedLockCallback<T> {
	
	/**
	 * <p>Description: 调用者必须在此方法中实现需要加分布式锁的业务逻辑</>
	 * @author zhaoyl
	 * @return
	 */
    public T process();

    /**
     * <p>Description: 得到分布式锁名称</>
     * @author zhaoyl
     * @return
     */
    public String getLockName();
    
}
