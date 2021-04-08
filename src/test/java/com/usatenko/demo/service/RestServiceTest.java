package com.usatenko.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestService.class})
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
class RestServiceTest {

  private final RestService restService;

  @MockBean private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void sendRequestSuccess() {
    // assert
    var monitoringUrl = "https://google.com";
    var url = UriComponentsBuilder.fromHttpUrl(monitoringUrl).build().encode().toUri();
    var requestEntity = new RequestEntity<>(null, null, HttpMethod.GET, url);
    var expectedResponseEntity = ResponseEntity.ok("");
    Mockito.when(restTemplate.exchange(requestEntity, String.class))
        .thenReturn(expectedResponseEntity);
    // arrange
    var actualValueOptional = restService.sendRequest(monitoringUrl);
    // act
    Mockito.verify(restTemplate, Mockito.times(1)).exchange(requestEntity, String.class);
    assertTrue(actualValueOptional.isPresent());
    assertNotNull(actualValueOptional.get().getResponseTime());
    assertEquals(expectedResponseEntity, actualValueOptional.get().getResponseEntity());
  }

  @Test
  void sendRequestWithIncorrectUrl() {
    // assert
    var monitoringUrl = "";
    // arrange
    var actualValueOptional = restService.sendRequest(monitoringUrl);
    // act
    Mockito.verify(restTemplate, Mockito.times(0))
        .exchange(Mockito.any(), Mockito.eq(String.class));
    assertTrue(actualValueOptional.isEmpty());
  }

  @Test
  void sendRequestExecuteThrows() {
    // assert
    var monitoringUrl = "https://google.com";
    var url = UriComponentsBuilder.fromHttpUrl(monitoringUrl).build().encode().toUri();
    var requestEntity = new RequestEntity<>(null, null, HttpMethod.GET, url);
    Mockito.when(restTemplate.exchange(requestEntity, String.class)).thenThrow();
    // arrange
    var actualValueOptional = restService.sendRequest(monitoringUrl);
    // act
    Mockito.verify(restTemplate, Mockito.times(1)).exchange(requestEntity, String.class);
    assertTrue(actualValueOptional.isEmpty());
  }
}
