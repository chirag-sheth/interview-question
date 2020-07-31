package com.url.shortening.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.url.shortening.helper.UrlUtility;
import com.url.shortening.repository.UrlRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UrlShorteningControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UrlUtility urlHelper;
  @Autowired
  private UrlRepository urlRepo;
  
  /**
   * This method runs a full integration test to verify success scenario of the UrlShorteningController.
  */
  @Test
  public void testIntegrationSuccess() throws Exception {
     String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38";
     String expectedLongUrl = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
     JSONObject jsonObj = null;
     int status=0;
     String expectedShortUrl = "";
     String content = "";
     
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/short?url="+uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     status = mvcResult.getResponse().getStatus();
     content = mvcResult.getResponse().getContentAsString();
     // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     jsonObj = new JSONObject(content);
     expectedShortUrl = jsonObj.getString("shortUrl");
     // Verify the short Url from the database.
     Optional<UrlEntity> entity = urlRepo.findByLongUrl(expectedLongUrl);
     assertEquals(expectedShortUrl,entity.get().getShortUrl());
     
     // Uri for getting the long Url
     uriToSend = "/long?url="+expectedShortUrl;
     // Send the request
     mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     status = mvcResult.getResponse().getStatus();
     content = mvcResult.getResponse().getContentAsString();
     // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     jsonObj = new JSONObject(content);
     expectedLongUrl = jsonObj.getString("longUrl");
     // Verify the long Url from the database.
     entity = urlRepo.findByShortUrl(expectedShortUrl);
     assertEquals(expectedLongUrl,entity.get().getLongUrl());
  }
  
  /**
   * This method runs a full integration test to verify failure scenario of the UrlShorteningController.
  */
  @Test
  public void testIntegrationFailure() throws Exception {
     String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38";
     String expectedLongUrl = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
     JSONObject jsonObj = null;
     int status=0;
     
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/short?url="+uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     status = mvcResult.getResponse().getStatus();
     String content = mvcResult.getResponse().getContentAsString();
     String expectedShortUrl=urlHelper.getShortUrl(expectedLongUrl);
  // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     jsonObj = new JSONObject(content);
     expectedShortUrl = jsonObj.getString("shortUrl");
     // Verify the short Url from the database.
     Optional<UrlEntity> entity = urlRepo.findByLongUrl(expectedLongUrl);
     assertEquals(expectedShortUrl,entity.get().getShortUrl());
     
     // Send invalid Uri for getting the long Url
     uriToSend = "/long?url=23H34b563f";
     // Send the request
     mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     status = mvcResult.getResponse().getStatus();
     content = mvcResult.getResponse().getContentAsString();
     // Verify failure status
     assertEquals(HttpStatus.NOT_FOUND.value(), status);
  }

}