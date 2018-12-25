package top.zylsite.cheetah.base.common.redisson;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description: 分布式锁注解
 * </p>
 * 
 * @author zhaoyl
 * @date 2018-12-20
 * @version v1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

	/**
	 * <p>
	 * Description: 锁的名称(如果lockName是固定的,直接设置该属性)</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	String lockName() default "";

	/**
	 * <p>
	 * Description: lockName前缀</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	String lockNamePre() default "";

	/**
	 * <p>
	 * Description: lockName后缀</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	String lockNameSuffix() default "lock";

	/**
	 * <p>
	 * Description: 获得锁名时拼接前后缀用到的分隔符</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	String separator() default ".";

	/**
	 * <p>
	 * Description: 获取注解的方法参数列表的某个参数对象的某个属性值来作为lockName,因为有时候lockName是不固定的;
	 * 当param不为空时,可以通过argNum参数来设置具体是参数列表的第几个参数,不设置则默认取第一个 </>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	String param() default "";

	/**
	 * <p>
	 * Description: 将方法第argNum个参数作为锁,默认第一个参数</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	int argNum() default 0;

	/**
	 * <p>
	 * Description: 是否使用公平锁,公平锁即先来先得</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	boolean fairLock() default false;

	/**
	 * <p>
	 * Description: 是否使用尝试锁</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	boolean tryLock() default false;

	/**
	 * <p>
	 * Description: 最长等待时间,该字段只有当tryLock()返回true才有效,默认30秒</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	long waitTime() default 30L;

	/**
	 * <p>
	 * Description: 锁超时时间( 默认5秒,超时时间过后,锁自动释放,建议尽量缩简需要加锁的逻辑)</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	long leaseTime() default 5L;

	/**
	 * <p>
	 * Description: 时间单位(默认为秒)</>
	 * 
	 * @author zhaoyl
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

}
