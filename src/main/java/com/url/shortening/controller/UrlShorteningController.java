package com.url.shortening.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortening.helper.UrlHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* <h1>Rest Controller that processes incoming requests.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-29
*/
@Api(value = "UrlShorteningController")
@RestController
public class UrlShorteningController {
	
	// Url Helper class object
	@Autowired 
	UrlHelper urlHelper;
	
	/**  Returns the short Url for the long Url supplied. Returns HTTP status code of 200 when successful.
	   * Throws a 400 error response code if the request parameter is not specified.
	   * 
	   * @param @RequestParam Request parameter with the Url string.
	   * @return Short Url for the string supplied.
	   * @exception Exception.
	   * @see Exception
	*/
	@ApiOperation(value = "Shortening the Url", notes = "Converts long Url to short Url.")
	@RequestMapping(path="/short",produces={"application/json"})
	public String getShortUrl(@RequestParam(name="url",required=true) String longUrl) throws Exception {
		return urlHelper.getShortUrl(longUrl);
	}
	
	/**  Returns the long (original) Url for the short Url supplied. Returns HTTP status code of 200 when successful.
	   * Throws a 400 error response code if the request parameter is not specified.
	   * 
	   * @param @RequestParam Request parameter with the Url string.
	   * @return String	Long (original) Url for the string supplied.
	   * @exception Exception.
	   * @see Exception
	*/
	@ApiOperation(value = "Converts short Url to long(original) Url", notes = "Finds long (original) Url from short Url.")
	@GetMapping(path="/long",produces={"application/json"})
	public String getLongUrl(@RequestParam(name="url",required=true) String shortUrl) throws Exception {
		String longUrl = urlHelper.getLongUrl(shortUrl);
		if (longUrl == null) {
			return "Long Url not found !";
		}
		return longUrl;
	}
}