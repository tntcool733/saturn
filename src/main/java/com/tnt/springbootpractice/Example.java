package com.tnt.springbootpractice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Example {

		@RequestMapping("/")
		Map<String, String> home() {
			List<String> sList = Arrays.asList("a", "b", "c");
			return sList.stream().collect(Collectors.toMap(s -> "key:" + s, s -> "value:" + s ));
		}

		public static void main(String[] args) throws Exception {
			SpringApplication.run(Example.class, args);
		}

}
