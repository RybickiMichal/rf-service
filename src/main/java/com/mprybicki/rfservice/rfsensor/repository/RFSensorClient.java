package com.mprybicki.rfservice.rfsensor.repository;

import com.mprybicki.rfservice.common.model.RFSensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("sensor-service")
public interface RFSensorClient {

    @GetMapping("/rf-sensors/active")
    List<RFSensor> getActiveSensors();
}
