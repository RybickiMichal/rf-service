package com.mprybicki.rfservice.config;

import com.mprybicki.rfservice.Repository.RFSensorRepository;
import com.mprybicki.rfservice.model.RFSensor;
import com.mprybicki.rfservice.service.RFSensorService;
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
        log.info("successfully downloaded " + sensors.size() + " sensors");

        rfSensorRepository.saveAll(sensors);
        rfSensorRepository.findAll().forEach(rfSensor -> System.out.println(rfSensor.toString()));
    }
}
