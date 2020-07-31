package com.url.shortening.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortening.entity.UrlEntity;
import com.url.shortening.exception.UrlNotFoundException;
import com.url.shortening.helper.UrlUtility;
import com.url.shortening.service.UrlDAOService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* <h1>Rest Controller that processes incoming requests.</h1>
*
* @author  Chirag Sheth
* @version 2.0
* @since   2020-07-30
*/

@Api(value = "UrlShorteningController")
@RestController
public class UrlShorteningController {
	
	// Url Helper class object
	@Autowired 
	UrlUtility urlHelper;
	@Autowired
	UrlDAOService urlService;
	
	/**  Returns the short Url for the long Url supplied. Returns HTTP status code of 200 when successful.
	   * Throws a 400 error response code if the request parameter is not specified.
	   * 
	   * @param @RequestParam Request parameter with the Url string.
	   * @return Short Url for the string supplied.
	   * @exception Exception.
	   * @see Exception
	*/
	@ApiOperation(value = "Shortening the Url", 
			notes = "Converts long Url to short Url from the database.")
	@PostMapping(path="/short", produces={"application/json"})
	public ResponseEntity<UrlEntity> covertToShortUrl(@RequestParam(name="url",required=true) String longUrl) throws UrlNotFoundException {
		try {
			UrlEntity urlEntity = urlService.convertToShortUrl(longUrl);
			return new ResponseEntity<UrlEntity>(urlEntity, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception ex) {
			throw new UrlNotFoundException("No Url details found for long Url - " + longUrl);
		}
	}
	
	/**  Returns the long (original) Url for the short Url supplied. Returns HTTP status code of 200 when successful.
	   * Throws a 400 error response code if the request parameter is not specified.
	   * 
	   * @param @RequestParam Request parameter with the Url string.
	   * @return String	Long (original) Url for the string supplied.
	   * @exception Exception.
	   * @see Exception
	*/
	@ApiOperation(value = "Converts short Url to long(original) Url", 
			notes = "Retrieves long (original) Url from short Url from the database.")
	@GetMapping(path="/long", produces={"application/json"})
	public ResponseEntity<UrlEntity> getLongUrl(@RequestParam(name="url",required=true) String shortUrl) {
		try {
			UrlEntity urlEntity = urlService.retrieveUrl(shortUrl);
			return new ResponseEntity<UrlEntity>(urlEntity, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception ex) {
			throw new UrlNotFoundException("No Url details found for short Url - " + shortUrl);
		}
	}
	
	/**  Returns the complete list of Urls. Returns HTTP status code of 200 when successful.
	   * 
	   * @param @RequestParam Request parameter with the Url string.
	   * @return String	Long (original) Url for the string supplied.
	   * @exception Exception.
	   * @see Exception
	*/
	@ApiOperation(value = "Lists all Urls", 
			notes = "Retrieves a complete list of Urls from the database.")
	@GetMapping(path="/urls", produces={"application/json"})
	public ResponseEntity<List<UrlEntity>> getAllUrls() {
		List<UrlEntity> entities = urlService.retrieveAllUrls();
		return new ResponseEntity<List<UrlEntity>>(entities, new HttpHeaders(), HttpStatus.OK);
	}
}