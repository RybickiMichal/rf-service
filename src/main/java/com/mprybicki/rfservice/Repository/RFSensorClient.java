package com.mprybicki.rfservice.Repository;

import com.mprybicki.rfservice.model.RFSensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("sensor-service")
public interface RFSensorClient {

    @GetMapping("/rf-sensors/active")
    List<RFSensor> getActiveSensors();
}
