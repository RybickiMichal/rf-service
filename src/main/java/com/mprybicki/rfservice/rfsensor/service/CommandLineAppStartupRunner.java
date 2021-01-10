package com.mprybicki.rfservice.rfsensor.service;

import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
import com.mprybicki.rfservice.rfsensor.service.RFSensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private RFSensorService rfSensorService;
    private RFSensorRepository rfSensorRepository;

    @Override
    public void run(String... args) {
        List<RFSensor> sensors = rfSensorService.getActiveRFSensors();
        log.info("successfully downloaded sensors");

        rfSensorService.setAreSensorsDownloaded(true);
        rfSensorRepository.saveAll(sensors);
    }
}
