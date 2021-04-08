package com.usatenko.demo.service;

import com.usatenko.demo.pojo.MonitoringRequestData;
import com.usatenko.demo.repository.MonitoringRequestDataRepository;
import com.usatenko.demo.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class MonitoringRequestService {

  @Autowired
  private  MonitoringRequestDataRepository monitoringRequestDataRepository;
  @Autowired
  private MonitoringTaskService monitoringTaskService;

  @PostConstruct
  private void init() {
    log.info("Try to prepare monitoring request data to monitoring task service...");
    var requestDataList = monitoringRequestDataRepository.findAll();
    requestDataList.forEach(monitoringTaskService::submitMonitoringTask);
  }

  public void save(MonitoringRequestData monitoringRequestData) {
    log.info("Try to save monitoring request data: {}", JacksonUtils.getJson(monitoringRequestData));
    monitoringRequestDataRepository.save(monitoringRequestData);
    monitoringTaskService.submitMonitoringTask(monitoringRequestData);
  }

  public void delete(String id) {
    log.info("Try to delete monitoring request data by id: {}", id);
    monitoringRequestDataRepository.deleteById(id);
  }

  public boolean isExist(String id) {
    log.info("Try to check monitoring request data if exist by id: {}", id);
    return monitoringRequestDataRepository.existsById(id);
  }
}
