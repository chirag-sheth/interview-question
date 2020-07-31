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
class UrlShorteningControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UrlHelper urlHelper;

  
  /**
   * This method runs a test to verify success scenario for short Url.
  */
  @Test
  public void testShortUrlSuccess() throws Exception {
     String uriToSend = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/testshort.json";
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/short?url="+uriToSend)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
     int status = mvcResult.getResponse().getStatus();
     String content = mvcResult.getResponse().getContentAsString();
     String expectedShortUrl=urlHelper.getShortUrl(uriToSend);
     // Verify HTTP Status code
     assertEquals(HttpStatus.OK.value(), status);
     // Verify getting the short Url back and matching its content
     assertTrue(content.length() > 0);
     assertEquals(expectedShortUrl, content);
  }
  
  /**
   * This method runs a test to verify success scenario for not sending short Url parameter.
  */
  @Test
  public void testNoShortUrlFailure() throws Exception {
     // Send the request
     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/short")
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