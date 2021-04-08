package com.usatenko.demo.repository;

import com.usatenko.demo.pojo.MonitoringRequestData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringRequestDataRepository extends MongoRepository<MonitoringRequestData, String> {
}
