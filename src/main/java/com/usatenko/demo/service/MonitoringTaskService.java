package com.usatenko.demo.service;

import com.usatenko.demo.pojo.MonitoringRequestData;
import com.usatenko.demo.scheduler.TaskScheduler;
import com.usatenko.demo.storage.MonitoringDataStorage;
import com.usatenko.demo.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MonitoringTaskService {

  @Autowired private RestService restService;
  @Autowired private MonitoringDataAnalyzerService analyzerService;
  @Autowired private TaskScheduler taskScheduler;
  @Autowired private MonitoringRequestService monitoringRequestService;

  public void submitMonitoringTask(MonitoringRequestData data) {
    log.info("Try to submit monitoring task: {}", JacksonUtils.getJson(data));
    var task = createTask(data);
    taskScheduler.submit(task);
  }

  private void handleTaskFinish(MonitoringRequestData data) {
    log.info("Try to handle finish monitoring task: {}", JacksonUtils.getJson(data));
    var task = createTask(data);
    taskScheduler.schedule(
        task, data.getMonitoringPeriodDelay(), data.getMonitoringPeriodTimeUnit());
  }

  private Runnable createTask(MonitoringRequestData data) {
    log.info("Try to create new monitoring task: {}", JacksonUtils.getJson(data));
    Runnable task =
        () -> {
          log.info("Try to run monitoring task: {}", JacksonUtils.getJson(data));
          var isTaskActive = monitoringRequestService.isExist(data.getId());
          if (isTaskActive) {
            log.info("Try to run active monitoring task: {}", JacksonUtils.getJson(data));
            var optionalResponse = restService.sendRequest(data.getUrl());
            var monitoringData =
                optionalResponse
                    .map(serverResponse -> analyzerService.analyze(data, serverResponse))
                    .orElseGet(() -> analyzerService.analyzeErrorResponse(data));
            MonitoringDataStorage.putItem(monitoringData);
            handleTaskFinish(data);
          } else {
            log.info("Try to finish non-active monitoring task: {}", JacksonUtils.getJson(data));
          }
        };
    return task;
  }
}
