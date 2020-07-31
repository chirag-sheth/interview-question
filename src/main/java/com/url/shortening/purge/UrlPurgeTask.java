package com.url.shortening.purge;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortening.repository.UrlRepository;

/**
* <h1>Cron task to purge the Urls that have gone past the configured timeout period.</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@Service
@Transactional
public class UrlPurgeTask {

	@Autowired
    private UrlRepository urlRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
 
    @Scheduled(cron = "${url-purge.cronexpression}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        logger.info("UrlPurgeTask::purgeExpired() --- Purge Task started.");
        urlRepository.deleteByExpiryDateLessThanEqual(now);
        logger.info("UrlPurgeTask::purgeExpired() --- Purge Task finished.");
    }
}