package com.usatenko.demo.factory;

import com.usatenko.demo.pojo.MonitoringData;
import com.usatenko.demo.pojo.MonitoringRequestData;
import com.usatenko.demo.pojo.MonitoringResponseData;

public class MonitoringDataFactory {



  public static MonitoringData create(MonitoringRequestData requestData, MonitoringResponseData responseData) {
    return new MonitoringData(requestData.getId(), requestData, responseData);
  }


  public static MonitoringData create(MonitoringRequestData requestData) {
    return create(requestData, null);
  }

  public static MonitoringData create() {
    return create(null, null);
  }
}
