package com.usatenko.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.usatenko.demo.pojo.enums.MonitoringStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringResponseData {

    @JsonProperty
    private MonitoringStatus status;

    @JsonProperty
    private String description;

}
