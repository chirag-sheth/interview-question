package com.url.shortening.helper;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
* <h1>Helper class to process Url strings for shortening the Url, and returning back the original Url.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-29
*/

@Component
public class UrlHelper {
	 private HashMap<String, String> keyMap;
	 private HashMap<String, String> valueMap;
	 private char charArr[];
	 private Random myRand;
	 private int keyLength;
	 String characterStr;
	 
	 /**
	   Default constructor to initialize the variables.
	*/
	public UrlHelper() {
		keyMap = new HashMap<String, String>();
		valueMap = new HashMap<String, String>();
		myRand = new Random();
		keyLength = 8;
		characterStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; 
		charArr = characterStr.toCharArray();
	}
	
	/**
	   * This method returns the short Url for the long Url supplied.
	   *
	   * @param String Long Url.
	   * @return String Short Url for the string supplied.
	*/
	public String getShortUrl(String longUrl) {
		String shortUrl = "";
		if (valueMap.containsKey(longUrl)) {
			shortUrl = valueMap.get(longUrl);
		} else {
			shortUrl = getKey(longUrl);
		}
		return shortUrl;
	}

	/**
	   * This method returns the original(long) Url for the short  Url supplied.
	   *
	   * @param String Short Url.
	   * @return String Long Url for the string supplied.
	*/
	public String getLongUrl(String shortUrl) {
		String longUrl = "";
		String key = shortUrl;
		longUrl = keyMap.get(key);
		return longUrl;
	}
	
	/**
	   * This method generates the key for the long Url string supplied.
	   *
	   * @param String Short Url.
	   * @return String A randomly generated key corresponding to the long Url string.
	*/
	private String getKey(String longUrl) {
		String key;
		key = generateKey();
		keyMap.put(key, longUrl);
		valueMap.put(longUrl, key);
		return key;
	 }
	
	/**
	   * This method generates the key.
	   *
	   * @return String randomly generated key (length of 8 characters) corresponding to the long Url string.
	*/
	private String generateKey() {
		String key = "";
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += charArr[myRand.nextInt(characterStr.length())];
			}
			if (!keyMap.containsKey(key)) {
				flag = false;
			}
		}
		return key;
	}
}
