package com.url.shortening.purge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.url.shortening.entity.UrlEntity;
import com.url.shortening.repository.UrlRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UrlPurgeTaskTest {

	@Autowired
	UrlPurgeTask urlPurgeTask;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UrlRepository urlRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
    public void testPurgeTask() throws Exception{
		String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
	     // Send the request
	     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/short?url="+uriToSend)
	        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	     int status = mvcResult.getResponse().getStatus();
	     String content = mvcResult.getResponse().getContentAsString();
	     // Verify HTTP Status code
	     assertEquals(HttpStatus.OK.value(), status);
	     // Verify getting the short Url back and matching its content
	     JSONObject jsonObj = new JSONObject(content);
	     String expectedShortUrl = jsonObj.getString("shortUrl");
	     // Verify if the object was inserted in the database.
	     Optional<UrlEntity> entity = urlRepo.findByLongUrl(uriToSend);
	     assertEquals(expectedShortUrl,entity.get().getShortUrl());
	     //update the expiry date so that it gets picked up in the next Purge run.
	     entity.get().setExpiryDate(new Date());
	     urlRepo.save(entity.get());
	     // Wait for 3 seconds and check if the object was purged
	     logger.info("UrlPurgeTaskTest::testPurgeTask() --- Waiting for 3 seconds for Purge to delete the record.");
	     Thread.sleep(3000L);
	     // Verify if the object was purged.
	     entity = urlRepo.findByLongUrl(uriToSend);
	     assertEquals(!entity.isPresent(),true);
    }
}
