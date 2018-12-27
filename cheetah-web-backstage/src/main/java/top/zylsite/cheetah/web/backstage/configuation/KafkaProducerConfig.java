package top.zylsite.cheetah.web.backstage.configuation;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * <p>Description: kafka生产者配置</p>
 * @author   zhaoyl
 * @date      2018-12-26
 * @version  v1.0
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

	@Value("${kafka.bootstrap-servers}")
	private String servers;
	@Value("${kafka.producer.retries}")
	private int retries;
	@Value("${kafka.producer.batch-size}")
	private int batchSize;
	@Value("${kafka.producer.buffer-memory}")
	private int bufferMemory;
	@Value("${kafka.producer.linger}")
	private int linger;
	@Value("${kafka.producer.transactional-id-prefix}")
	private String transactionIdPrefix;//事务ID前缀
	@Value("${kafka.producer.enable-idempotence}")
	private String enableIdempotence;//设置幂等性

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}

	public ProducerFactory<String, String> producerFactory() {
		DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
		factory.setTransactionIdPrefix(transactionIdPrefix);
		return factory;
	}

	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
		return props;
	}

}
