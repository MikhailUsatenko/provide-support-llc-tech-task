package com.usatenko.demo.service;

import com.usatenko.demo.scheduler.TaskScheduler;
import com.usatenko.demo.test.factory.MonitoringRequestDataFactoryForTests;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MonitoringTaskService.class})
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
class MonitoringTaskServiceTest {

  private final MonitoringTaskService monitoringTaskService;

  @MockBean private RestService restService;
  @MockBean private MonitoringDataAnalyzerService analyzerService;
  @MockBean private MonitoringRequestService monitoringRequestService;
  @MockBean private TaskScheduler taskScheduler;

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void submitMonitoringTask() {
    // arrange
    var requestData = MonitoringRequestDataFactoryForTests.create();
    // act
    monitoringTaskService.submitMonitoringTask(requestData);
    // assert
    Mockito.verify(taskScheduler, Mockito.times(1)).submit(Mockito.any());
  }
}
