package com.usatenko.demo.service;

import com.usatenko.demo.factory.MonitoringDataFactory;
import com.usatenko.demo.factory.MonitoringResponseDataFactory;
import com.usatenko.demo.pojo.MonitoringData;
import com.usatenko.demo.pojo.MonitoringRequestData;
import com.usatenko.demo.pojo.MonitoringResponseData;
import com.usatenko.demo.pojo.ServerResponseData;
import com.usatenko.demo.pojo.enums.MonitoringStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MonitoringDataAnalyzerService {

  public MonitoringData analyze(
          MonitoringRequestData monitoringRequestData, ServerResponseData serverResponse) {

    var analyzedResponseDataList =
        initAnalyzedResponseDataList(monitoringRequestData, serverResponse);

    var isCritical =
        analyzedResponseDataList.stream()
            .map(MonitoringResponseData::getStatus)
            .anyMatch(MonitoringStatus.CRITICAL::equals);
    if (isCritical) {
      return getErrorData(
          analyzedResponseDataList, MonitoringStatus.CRITICAL, monitoringRequestData);
    }

    var isWarning =
        analyzedResponseDataList.stream()
            .map(MonitoringResponseData::getStatus)
            .anyMatch(status -> status == MonitoringStatus.WARNING);
    if (isWarning) {
      return getErrorData(
          analyzedResponseDataList, MonitoringStatus.WARNING, monitoringRequestData);
    }

    // If status is OK
    return getOkData(monitoringRequestData);
  }

  public MonitoringData analyzeErrorResponse(MonitoringRequestData monitoringRequestData) {
    var responseData =
        MonitoringResponseDataFactory.create(MonitoringStatus.CRITICAL, "Request failed");
    return MonitoringDataFactory.create(monitoringRequestData, responseData);
  }

  private MonitoringResponseData analyzeResponseTime(
          MonitoringRequestData monitoringRequestData, ServerResponseData serverResponseData) {
    var responseTime = serverResponseData.getResponseTime().toSeconds();
    var okResponseTime = monitoringRequestData.getOkResponseTime();
    var warningResponseTime = monitoringRequestData.getWarningResponseTime();
    if (responseTime <= okResponseTime) {
      return MonitoringResponseDataFactory.create(MonitoringStatus.OK);
    }
    if (responseTime <= warningResponseTime) {
      return MonitoringResponseDataFactory.create(
          MonitoringStatus.WARNING,
          String.format(
              "<span class='fw-bold'>Description:</span> Warning! Response time more than limit. Expected limit (in seconds): %s; Actual time (in seconds): %s",
              okResponseTime, responseTime));
    }
    return MonitoringResponseDataFactory.create(
        MonitoringStatus.CRITICAL,
        String.format(
            "<span class='fw-bold'>Description:</span> Critical! Response time more than limit. Expected limit (in seconds): %s; Actual time (in seconds): %s",
            warningResponseTime, responseTime));
  }

  private MonitoringResponseData analyzeResponseStatus(
          MonitoringRequestData monitoringRequestData, ServerResponseData serverResponseData) {
    var responseStatus = serverResponseData.getResponseEntity().getStatusCode();
    var okResponseStatus = monitoringRequestData.getOkResponseStatus();
    if (responseStatus == okResponseStatus) {
      return MonitoringResponseDataFactory.create(MonitoringStatus.OK);
    } else {
      return MonitoringResponseDataFactory.create(
          MonitoringStatus.CRITICAL,
          String.format(
              "<span class='fw-bold'>Description:</span> Critical! Response status does not equals to expected. Expected status: %s; Actual status: %s",
              okResponseStatus, responseStatus));
    }
  }

  private MonitoringResponseData analyzeResponseSize(
          MonitoringRequestData monitoringRequestData, ServerResponseData serverResponseData) {
    var responseSize = serverResponseData.getResponseEntity().getBody().getBytes().length;
    var minResponseSize = monitoringRequestData.getMinResponseSize();
    var maxResponseSize = monitoringRequestData.getMaxResponseSize();
    if (responseSize < minResponseSize) {
      return MonitoringResponseDataFactory.create(
          MonitoringStatus.CRITICAL,
          String.format(
              "<span class='fw-bold'>Description:</span> Critical! Response size is less than the min limit. Min limit: %s; Actual size: %s",
              minResponseSize, responseSize));
    }
    if (responseSize > maxResponseSize) {
      return MonitoringResponseDataFactory.create(
          MonitoringStatus.CRITICAL,
          String.format(
              "<span class='fw-bold'>Description:</span> Critical! Response size is more than the max limit. Max limit: %s; Actual size: %s",
              maxResponseSize, responseSize));
    }
    return MonitoringResponseDataFactory.create(MonitoringStatus.OK);
  }

  private MonitoringResponseData analyzeSubstringInResponse(
          MonitoringRequestData monitoringRequestData, ServerResponseData serverResponseData) {
    var substring = monitoringRequestData.getSubstringInResponse();
    var responseBody = serverResponseData.getResponseEntity().getBody();

    return Optional.ofNullable(substring)
        .map(
            string -> {
              if (responseBody.contains(string)) {
                return MonitoringResponseDataFactory.create(MonitoringStatus.OK);
              } else {
                return MonitoringResponseDataFactory.create(
                    MonitoringStatus.CRITICAL,
                    "<span class='fw-bold'>Description:</span> Critical! There is no substring in the response");
              }
            })
        .orElseGet(() -> MonitoringResponseDataFactory.create(MonitoringStatus.OK));
  }

  private List<MonitoringResponseData> initAnalyzedResponseDataList(
          MonitoringRequestData monitoringRequestData, ServerResponseData serverResponse) {
    return List.of(
        analyzeResponseTime(monitoringRequestData, serverResponse),
        analyzeResponseStatus(monitoringRequestData, serverResponse),
        analyzeResponseSize(monitoringRequestData, serverResponse),
        analyzeSubstringInResponse(monitoringRequestData, serverResponse));
  }

  private String getDescriptionOfErrors(List<MonitoringResponseData> list) {
    return list.stream()
        .map(MonitoringResponseData::getDescription)
        .filter(Objects::nonNull)
        .collect(Collectors.joining("<br>"));
  }

  private MonitoringData getErrorData(
      List<MonitoringResponseData> list,
      MonitoringStatus monitoringStatus,
      MonitoringRequestData monitoringRequestData) {
    var description = getDescriptionOfErrors(list);
    var responseData = MonitoringResponseDataFactory.create(monitoringStatus, description);
    return MonitoringDataFactory.create(monitoringRequestData, responseData);
  }

  private MonitoringData getOkData(MonitoringRequestData monitoringRequestData) {
    var responseData = MonitoringResponseDataFactory.create(MonitoringStatus.OK);
    return MonitoringDataFactory.create(monitoringRequestData, responseData);
  }
}
