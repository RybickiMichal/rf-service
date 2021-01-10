package com.mprybicki.rfservice.rfsensor.service;

import com.google.gson.Gson;
import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.common.model.RFSensorDTO;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorClient;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;

@Service
@Slf4j
public class RFSensorService {

    private RFSensorClient rfSensorClient;
    private RFSensorRepository rfSensorRepository;
    private Gson gson;

    @Setter
    private boolean AreSensorsDownloaded;

    public RFSensorService(RFSensorClient rfSensorClient, RFSensorRepository rfSensorRepository, Gson gson) {
        this.rfSensorClient = rfSensorClient;
        this.rfSensorRepository = rfSensorRepository;
        this.gson = gson;
    }

    @Retryable(value = ConnectException.class, maxAttempts = 89280, backoff = @Backoff(delay = 30000))
    public List<RFSensor> getActiveRFSensors() {
        return rfSensorClient.getActiveSensors();
    }

    public void updateSensor(String message) {
        RFSensorDTO rfSensorDTO = gson.fromJson(message, RFSensorDTO.class);

        if (AreSensorsDownloaded) {
            switch (rfSensorDTO.getSensorOperation()) {
                case REGISTERED:
                    registerSensor(rfSensorDTO.getRfSensor());
                    break;
                case UNREGISTERED:
                    unregisterSensor(rfSensorDTO.getRfSensor());
                    break;
                case UPDATED:
                    updateSensor(rfSensorDTO.getRfSensor());
                    break;
            }
        }
    }

    private void registerSensor(RFSensor rfSensor) {
        //TODO validation
        rfSensorRepository.save(rfSensor);
        log.info("RF sensor registered " + rfSensor);
    }

    private void unregisterSensor(RFSensor rfSensor) {
        //TODO validation
        rfSensorRepository.delete(rfSensor);
        log.info("RF sensor unregistered " + rfSensor);
    }

    private void updateSensor(RFSensor rfSensor) {
        //TODO validation
        rfSensorRepository.save(rfSensor);
        log.info("RF sensor updated " + rfSensor);
    }
}
