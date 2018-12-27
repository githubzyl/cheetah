package top.zylsite.cheetah.web.backstage.configuation;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaBatchDataListener {

	/**
	 * <p>
	 * Description: 批量消费</>
	 * 
	 * @author zhaoyl
	 * @param records
	 */
	@KafkaListener(topics = { "test-topic" })
	public void receiveMessage(List<ConsumerRecord<?, ?>> records) {
		log.info("获取到的消息数量为:" + records.size());
		for (ConsumerRecord<?, ?> record : records) {
			Optional<?> kafkaMessage = Optional.ofNullable(record.value());
			if (kafkaMessage.isPresent()) {
				Object message = record.value();
				String topic = record.topic();
				log.info("topic=" + topic + ",message={}", message);
			}
		}
	}

}
