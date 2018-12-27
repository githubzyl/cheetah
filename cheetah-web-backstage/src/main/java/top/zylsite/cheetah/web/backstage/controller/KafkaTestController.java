package top.zylsite.cheetah.web.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaOperations.OperationsCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import top.zylsite.cheetah.backstage.model.master.User;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping("/send/{total}")
	public Object send(@PathVariable Integer total) {
		return kafkaTemplate.executeInTransaction(new OperationsCallback<String, String, String>() {
			@Override
			public String doInOperations(KafkaOperations<String, String> operations) {
				User user = null;
				for (int i = 0; i < total; i++) {
					user = new User();
					user.setId(i + 1);
					user.setVcUserName("account" + (i + 1));
					user.setVcRealName("姓名" + (i + 1));
					kafkaTemplate.send("test-topic", JSONObject.toJSONString(user));
				}
				return "SUCCESS";
			}
		});
	}

}
