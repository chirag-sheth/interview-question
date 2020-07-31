package com.url.shortening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* <h1>Spring boot Application for Shortening Urls</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@SpringBootApplication
public class UrlShorteningApplication {
	
	/**
	   * This is the main method which makes use of addNum method.
	   * @param args Unused.
	   * @return Nothing.
	*/
	public static void main(String[] args) {
		SpringApplication.run(UrlShorteningApplication.class, args);
	}
}
