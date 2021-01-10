package com.mprybicki.rfservice.rfsensor.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RFSensorListenerService {

    private final RFSensorService rfSensorService;

    @KafkaListener(topics = "${kafka.rf.sensor.topic}")
    public void listen(String message) {
        rfSensorService.updateSensor(message);
    }
}
