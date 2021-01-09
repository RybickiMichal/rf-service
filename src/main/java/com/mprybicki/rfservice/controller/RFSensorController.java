package com.mprybicki.rfservice.controller;

import com.mprybicki.rfservice.model.RFSensor;
import com.mprybicki.rfservice.service.RFSensorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class RFSensorController {

    private RFSensorService rfSensorService;

    @GetMapping(value = "/rf-sensors/active")
    public List<RFSensor> getActiveCameraSensors() {
        return rfSensorService.getActiveRFSensors();
    }
}
