package com.url.shortening.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Getter;
import lombok.Setter;

/**
* <h1>Specifies the configuration for timeout property.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@Configuration
@EnableScheduling
@ConfigurationProperties("url-purge")
@ComponentScan("com.url")
public class UrlConfiguration {

	//setting default value of 30, in case it is not configured in the application.yml
	@Getter @Setter private int timeout=30;
	
}