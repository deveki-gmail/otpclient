package com.example.otpclient;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
public class OtpclientApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(OtpclientApplication.class, args);
	}
	
	@GetMapping("/otp/{userId}")
	public String otpGen(@PathVariable("userId") String userId) throws Exception {
		System.out.println("OTP Gen Client: otpGen method. User Id = "+userId);
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://otpserver.azurewebsites.net/otp/"+userId;
		ResponseEntity<String> re = restTemplate.getForEntity(new URI(url), String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(re.getBody());
		JsonNode otp = root.path("otp");
		JsonNode userIdFrmJson = root.path("userId");
		String message = "The Otp for user id "+userIdFrmJson+" is '"+otp+"'";
		System.out.println(message);
		return message;
	}
}
