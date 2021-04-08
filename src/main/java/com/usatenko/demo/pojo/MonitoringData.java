package com.usatenko.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringData {

    @JsonProperty
    private String id;

    @JsonProperty
    private MonitoringRequestData monitoringRequestData;

    @JsonProperty
    private MonitoringResponseData monitoringResponseData;
}
