package top.zylsite.cheetah.web.backstage.configuation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @param obj
     * @param timeout 超时时间
     */
    public long lock(String obj, long timeout) {
        boolean locked = false;
        long expiretime = System.currentTimeMillis() + timeout;
        locked = lock(obj, Long.toString(expiretime));
        return locked ? expiretime : -1;
    }

    /**
     * @param obj
     * @param lockid lock取得的标识
     */
    public void unlock(String obj, long lockid) {
        unlock(obj, Long.toString(lockid));
    }

    /**
     * @param key
     * @param value 当前时间+超时时间
     */
    private boolean lock(String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        // 如果锁过期
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // 获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue)
                    && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    private void unlock(String key, String value) {
        try {
            String currrentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currrentValue)
                    && currrentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("解锁异常{}", e);
        }

    }
}
