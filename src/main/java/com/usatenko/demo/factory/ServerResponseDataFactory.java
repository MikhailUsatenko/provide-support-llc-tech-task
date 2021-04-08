package com.usatenko.demo.factory;

import com.usatenko.demo.pojo.ServerResponseData;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

public class ServerResponseDataFactory {
    public static ServerResponseData create(ResponseEntity<String> responseEntity, Duration responseTime) {
        return new ServerResponseData(responseEntity, responseTime);
    }

    public static ServerResponseData create() {
        return create(null, null);
    }
}
