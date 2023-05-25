package com.czy.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	private static Map<String,String> testMap = new ConcurrentHashMap<>();


	public static void main(String[] args) {

		testMap.put("A", "a");
		System.out.println(testMap.get("A"));
		testMap.put("A", "b");
		System.out.println(testMap.get("A"));
		testMap = new ConcurrentHashMap<>();
		testMap.put("a", "A");
		System.out.println(testMap.get("a"));
//		SpringApplication.run(DemoApplication.class, args);
	}

}
