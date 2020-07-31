package com.url.shortening.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortening.config.UrlConfiguration;
import com.url.shortening.entity.UrlEntity;
import com.url.shortening.helper.UrlUtility;
import com.url.shortening.repository.UrlRepository;

/**
* <h1>Service Implementation class for database interaction.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@Service
@Transactional
public class UrlDAOService {

	@Autowired
	UrlRepository repository;
	
	@Autowired
	UrlUtility urlHelper;
	
	@Autowired
	UrlConfiguration config;
	
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
	/**
	   * This method converts the long Url to short Url, and returns the Url entity object.
	   * Creates new record, if it doesn't exist. Fetches the entity record, if already exists.
	   *
	   * @param String Long Url.
	   * @return UrlEntity Url Entity object.
	*/
	public UrlEntity convertToShortUrl(String longUrl) {
		Optional<UrlEntity> entity = repository.findByLongUrl(longUrl);
		UrlEntity urlEntity = null;
		if(entity.isPresent()) {
			urlEntity = entity.get();
		}
		else {
			int timeoutInMinutes = config.getTimeout();
			Date currentDate = new Date();
			urlEntity= new UrlEntity(); 
			urlEntity.setLongUrl(longUrl);
			urlEntity.setShortUrl(urlHelper.getShortUrl(longUrl));
			urlEntity.setCreateDate(new Date());
			urlEntity.setExpiryDate(urlHelper.addMinutes(currentDate, timeoutInMinutes));
			repository.save(urlEntity);
		}
		return urlEntity;
	}
	
	/**
	   * This method converts the long Url to short Url, and returns the Url entity object.
	   * Creates new record, if it doesn't exist. Fetches the entity record, if already exists.
	   *
	   * @param String Short Url.
	   * @return UrlEntity Url Entity object.
	*/
	public UrlEntity retrieveUrl(String shortUrl) {
		UrlEntity entity = repository.findByShortUrl(shortUrl).get();
		return entity;
	}
	
	/**
	   * This method converts the long Url to short Url, and returns the Url entity object.
	   * Creates new record, if it doesn't exist. Fetches the entity record, if already exists.
	   *
	   * @return List<UrlEntity> Url Entity objects.
	*/
	public List<UrlEntity> retrieveAllUrls() {
		List<UrlEntity> entities = repository.findAll();
		return entities;
	}
	
}
