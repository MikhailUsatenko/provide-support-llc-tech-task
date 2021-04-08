package com.usatenko.demo.service;

import com.usatenko.demo.factory.ServerResponseDataFactory;
import com.usatenko.demo.pojo.ServerResponseData;
import com.usatenko.demo.utils.OffsetDateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestService {

  private final RestTemplate restTemplate;

  public Optional<ServerResponseData> sendRequest(String monitoringUrl) {
    try {
      var url = UriComponentsBuilder.fromHttpUrl(monitoringUrl).build().encode().toUri();
      var requestEntity = new RequestEntity<>(null, null, HttpMethod.GET, url);
      var timeBeforeRequest = OffsetDateTimeUtils.currentTime();
      var responseEntity = restTemplate.exchange(requestEntity, String.class);
      var timeAfterRequest = OffsetDateTimeUtils.currentTime();
      var responseTime = Duration.between(timeBeforeRequest, timeAfterRequest);
      var serverResponse = ServerResponseDataFactory.create(responseEntity, responseTime);
      return Optional.of(serverResponse);
    } catch (Exception e) {
      log.error("Got error while try to send request : {}", e.getMessage());
      return Optional.empty();
    }
  }
}
