package com.cheetah.web.backstage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${test.message}")
	private String message;
	
	@GetMapping("/test")
	public Object test() {
		return message;
	}
	
}
