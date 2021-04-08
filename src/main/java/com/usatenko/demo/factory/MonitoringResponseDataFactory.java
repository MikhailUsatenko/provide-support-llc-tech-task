package com.usatenko.demo.factory;

import com.usatenko.demo.pojo.MonitoringResponseData;
import com.usatenko.demo.pojo.enums.MonitoringStatus;

public class MonitoringResponseDataFactory {

    public static MonitoringResponseData create(
            MonitoringStatus status, String description) {
        return new MonitoringResponseData(status, description);
    }

    public static MonitoringResponseData create(MonitoringStatus monitoringStatus) {
        return create(monitoringStatus, null);
    }

    public static MonitoringResponseData create() {
        return create(null, null);
    }
}
