package com.usatenko.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponseData {

    @JsonProperty
    private ResponseEntity<String> responseEntity;

    @JsonProperty
    private Duration responseTime;
}
