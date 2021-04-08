package com.usatenko.demo.storage;

import com.usatenko.demo.pojo.MonitoringData;
import com.usatenko.demo.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MonitoringDataStorage {

    private static final Map<String, MonitoringData> STORAGE_MAP = new ConcurrentHashMap<>();

    public static void putItem(MonitoringData monitoringData) {
        log.info("Try to put item to storage: {}", JacksonUtils.getJson(monitoringData));
        STORAGE_MAP.put(monitoringData.getId(), monitoringData);
    }

    public static void removeItem(String id) {
        log.info("Try to remove item from storage by id: {}", id);
        STORAGE_MAP.remove(id);
    }

    public static List<MonitoringData> getAllItems() {
        var list = new ArrayList<>(STORAGE_MAP.values());
        log.info("Try to get all items from storage ...");
        return list;
    }


}
