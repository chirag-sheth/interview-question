package com.url.shortening.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UrlShorteningControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UrlRepository urlRepo;

  /**
   * This method runs a test to verify success scenario for short Url.
  */
  @Test
  public void testShortUrlSuccess() throws Exception {
     String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/short?url="+uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     int status = mvcResult.getResponse().getStatus();
     String content = mvcResult.getResponse().getContentAsString();
     // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     assertTrue(content.length() > 0);
     // Verify getting the short Url back and matching its content
     JSONObject jsonObj = new JSONObject(content);
     String expectedShortUrl = jsonObj.getString("shortUrl");
     // Verify if the object was inserted in the database.
     Optional<UrlEntity> entity = urlRepo.findByLongUrl(uriToSend);
     assertEquals(expectedShortUrl,entity.get().getShortUrl());
  }
  
  /**
   * This method runs a test to verify success scenario for not sending short Url parameter.
  */
  @Test
  public void testNoShortUrlFailure() throws Exception {
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/short")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     int status = mvcResult.getResponse().getStatus();
    // Verify HTTP Status code
     assertEquals(HttpStatus.BAD_REQUEST.value(), status);
  }
  
  /**
   * This method runs a test to verify success scenario for not sending long Url parameter.
  */
  @Test
  public void testNoLongUrlFailure() throws Exception {
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/long")
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     int status = mvcResult.getResponse().getStatus();
    // Verify HTTP Status code
     assertEquals(HttpStatus.BAD_REQUEST.value(), status);
  }

}