package com.url.shortening.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
* <h1>Specifies the configuration for timeout property.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@Component
@ConfigurationProperties("url-purge")
public class UrlConfiguration {

	//setting default value of 30, in case it is not configured in the application.yml
	@Getter @Setter private int timeout=30;
	
}