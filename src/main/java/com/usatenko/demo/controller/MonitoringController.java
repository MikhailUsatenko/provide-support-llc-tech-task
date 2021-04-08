package com.usatenko.demo.controller;

import com.usatenko.demo.controller.api.ControllerAPI;
import com.usatenko.demo.pojo.MonitoringData;
import com.usatenko.demo.pojo.MonitoringRequestData;
import com.usatenko.demo.service.MonitoringRequestService;
import com.usatenko.demo.storage.MonitoringDataStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = ControllerAPI.MONITORING_CONTROLLER)
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringRequestService monitoringRequestService;

    @PostMapping(value = ControllerAPI.MONITORING_CONTROLLER_ADD_MONITORING_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody MonitoringRequestData monitoringRequestData) {
        monitoringRequestService.save(monitoringRequestData);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = ControllerAPI.MONITORING_CONTROLLER_DELETE_MONITORING_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        monitoringRequestService.delete(id);
        MonitoringDataStorage.removeItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = ControllerAPI.MONITORING_CONTROLLER_GET_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonitoringData>> getAllItems() {
        var storageMap = MonitoringDataStorage.getAllItems();
        return ResponseEntity.ok(storageMap);
    }
}
