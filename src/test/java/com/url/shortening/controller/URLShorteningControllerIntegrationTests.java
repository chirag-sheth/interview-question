package com.url.shortening.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.url.shortening.helper.UrlHelper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UrlShorteningControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UrlHelper urlHelper;

  
  /**
   * This method runs a full integration test to verify success scenario of the UrlShorteningController.
  */
  @Test
  public void testIntegrationSuccess() throws Exception {
     String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38";
     String expectedLongUrl = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
     
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/short?url="+uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     int status = mvcResult.getResponse().getStatus();
     String content = mvcResult.getResponse().getContentAsString();
     String expectedShortUrl=urlHelper.getShortUrl(expectedLongUrl);
     // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     // Verify getting the short Url back and matching its content
     assertTrue(content.length() > 0);
     assertEquals(expectedShortUrl, content);
     
     // Uri for getting the long Url
     uriToSend = "/long?url="+content;
     // Send the request
     mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     
     status = mvcResult.getResponse().getStatus();
     content = mvcResult.getResponse().getContentAsString();
  // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
  // Verify getting the long Url back and matching its content
     assertTrue(content.length() > 0);
     assertEquals(expectedLongUrl, content);
  }
  
  /**
   * This method runs a full integration test to verify failure scenario of the UrlShorteningController.
  */
  @Test
  public void testIntegrationFailure() throws Exception {
     String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38";
     String expectedLongUrl = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
     String expectedFailureContent = "Long Url not found !";
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/short?url="+uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     int status = mvcResult.getResponse().getStatus();
     String content = mvcResult.getResponse().getContentAsString();
     String expectedShortUrl=urlHelper.getShortUrl(expectedLongUrl);
     // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     // Verify getting the short Url back and matching its content
     assertTrue(content.length() > 0);
     assertEquals(expectedShortUrl, content);
     
     // Send invalid Uri for getting the long Url
     uriToSend = "/long?url=23H34b563f";
     // Send the request
     mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     status = mvcResult.getResponse().getStatus();
     content = mvcResult.getResponse().getContentAsString();
     // Verify status is still success
     assertEquals(HttpStatus.OK.value(), status);
     // Verify not getting the Url back
     assertEquals(expectedFailureContent, content);
  }

}