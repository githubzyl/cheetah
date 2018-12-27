package top.zylsite.cheetah.web.backstage.configuation;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * <p>Description: kafka消费者配置</p>
 * @author   zhaoyl
 * @date      2018-12-26
 * @version  v1.0
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.bootstrap-servers}")
	private String servers;
	@Value("${kafka.consumer.group-id}")
	private String groupId;
	@Value("${kafka.consumer.client-id}")
	private String clientId;
	@Value("${kafka.consumer.enable-auto-commit}")
	private boolean enableAutoCommit;
	@Value("${kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;
	@Value("${kafka.consumer.auto-commit-interval}")
	private String autoCommitInterval;
	@Value("${kafka.consumer.max-poll-records}")
	private String maxPollRecords;
	@Value("${kafka.consumer.heartbeat-interval}")
	private String heartbeatInterval;
	@Value("${kafka.consumer.session-timeout}")
	private String sessionTimeout;
	@Value("${kafka.consumer.concurrency}")
	private int concurrency;

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(concurrency);
		factory.setBatchListener(true);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

	@Bean
	public KafkaBatchDataListener listener() {
		return new KafkaBatchDataListener();
	}

	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	public Map<String, Object> consumerConfigs() {
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		propsMap.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		propsMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return propsMap;
	}

}
