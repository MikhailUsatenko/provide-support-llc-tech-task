package com.usatenko.demo.service;

import com.usatenko.demo.repository.MonitoringRequestDataRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MonitoringRequestService.class})
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
class MonitoringRequestServiceTest {

  private final MonitoringRequestService monitoringRequestService;

  @MockBean private MonitoringRequestDataRepository monitoringRequestDataRepository;
  @MockBean private MonitoringTaskService monitoringTaskService;

  private static final String ID_FOR_TEST = "idForTest";

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void save() {
    // arrange
    var monitoringRequest = MonitoringRequestDataFactoryForTests.create();
    // act
    monitoringRequestService.save(monitoringRequest);
    // assert
    Mockito.verify(monitoringRequestDataRepository, Mockito.times(1)).save(monitoringRequest);
    Mockito.verify(monitoringTaskService, Mockito.times(1)).submitMonitoringTask(monitoringRequest);
  }

  @Test
  void delete() {
    // arrange
    var id = ID_FOR_TEST;
    // act
    monitoringRequestService.delete(id);
    // assert
    Mockito.verify(monitoringRequestDataRepository, Mockito.times(1)).deleteById(id);
  }

  @Test
  void isExist() {
    // arrange
    var id = ID_FOR_TEST;
    var expectedValue = true;
    Mockito.when(monitoringRequestDataRepository.existsById(id)).thenReturn(expectedValue);
    // act
    var actualValue = monitoringRequestService.isExist(id);
    // assert
    assertEquals(expectedValue, actualValue);
    Mockito.verify(monitoringRequestDataRepository, Mockito.times(1)).existsById(id);
  }
}
