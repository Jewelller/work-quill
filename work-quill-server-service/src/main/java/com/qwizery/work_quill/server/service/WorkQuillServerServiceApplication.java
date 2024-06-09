package com.qwizery.work_quill.server.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WorkQuillServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkQuillServerServiceApplication.class, args);
	}

}
