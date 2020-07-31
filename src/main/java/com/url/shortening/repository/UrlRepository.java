package com.url.shortening.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortening.entity.UrlEntity;

/**
* <h1>Specifies the Url JPA Repository</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

	Optional<UrlEntity> findByLongUrl(String longUrl);
	
	Optional<UrlEntity> findByShortUrl(String shortUrl);
	
	void deleteByExpiryDateLessThanEqual(Date now);
}