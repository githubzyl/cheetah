package top.zylsite.cheetah.web.backstage.configuation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.zylsite.cheetah.base.common.redisson.DistributedLock;
import top.zylsite.cheetah.base.common.redisson.DistributedLockCallback;
import top.zylsite.cheetah.base.common.redisson.DistributedLockTemplate;

@Component
@Slf4j
public class ScheduledJob {

	@Autowired
	private DistributedLockTemplate distributedLockTemplate;

	@Scheduled(cron = "0 0 20 * * ?")
	public void test() {
		distributedLockTemplate.tryLock(new DistributedLockCallback<Object>() {
			@Override
			public Object process() {
				log.info("定时任务测试:" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
				return null;
			}

			@Override
			public String getLockName() {
				// TODO Auto-generated method stub
				return "testJobLock";
			}
		}, 0L, 50L, TimeUnit.SECONDS, false);
	}
	
}
