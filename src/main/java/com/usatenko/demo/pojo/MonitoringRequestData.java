package com.usatenko.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

@Document(collection = "monitoringRequestData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringRequestData {

  @Id @JsonProperty private String id;

  @JsonProperty private String url;

  @JsonProperty private long monitoringPeriodDelay;

  @JsonProperty private TimeUnit monitoringPeriodTimeUnit;

  @JsonProperty private int okResponseTime;

  @JsonProperty private int warningResponseTime;

  @JsonProperty private HttpStatus okResponseStatus;

  @JsonProperty private int minResponseSize;

  @JsonProperty private int maxResponseSize;

  @JsonProperty private String substringInResponse;
}
