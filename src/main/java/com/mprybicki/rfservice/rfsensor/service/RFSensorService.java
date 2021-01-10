package com.mprybicki.rfservice.rfsensor.service;

import com.google.gson.Gson;
import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.common.model.RFSensorDTO;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorClient;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
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
    private RFSensorValidationService rfSensorValidationService;
    private Gson gson;

    @Setter
    private boolean AreSensorsDownloaded = false;

    public RFSensorService(RFSensorClient rfSensorClient, RFSensorRepository rfSensorRepository, RFSensorValidationService rfSensorValidationService, Gson gson) {
        this.rfSensorClient = rfSensorClient;
        this.rfSensorRepository = rfSensorRepository;
        this.rfSensorValidationService = rfSensorValidationService;
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
        } else {
            List<RFSensor> sensors = getActiveRFSensors();
            log.info("successfully downloaded sensors");

            setAreSensorsDownloaded(true);
            rfSensorRepository.saveAll(sensors);
        }
    }

    private void registerSensor(RFSensor rfSensorToRegister) {
        rfSensorRepository.save(rfSensorToRegister);
        log.info("RF sensor registered " + rfSensorToRegister);
    }

    private void unregisterSensor(RFSensor rfSensorToUnregister) {
        rfSensorValidationService.validateUnregisterSensor(rfSensorToUnregister.getId());
        rfSensorRepository.delete(rfSensorToUnregister);
        rfSensorRepository.findAll().forEach(rfSensor -> log.error(rfSensor.toString()));
        log.info("RF sensor unregistered " + rfSensorToUnregister);
    }

    private void updateSensor(RFSensor newRFSensor) {
        rfSensorValidationService.validateUpdateSensor(newRFSensor);
        rfSensorRepository.save(newRFSensor);
        log.info("RF sensor updated " + newRFSensor);
    }
}
