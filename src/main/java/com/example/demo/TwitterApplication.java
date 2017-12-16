package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

/**
 *
 * @author Preetham Viswanathan 
 */

@SpringBootApplication
public class TwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApplication.class, args);
	}
	
	
	/**Comment following lines if you want autowire Twitter tokens. Comment/uncomment accordingly in TwitterController.java**/
	@Bean
	public Twitter twitter() {
		return new TwitterTemplate("consmer_key",
				"consumer_secret",
				"access_token",
				"accessTokenSecret");
	}
}
