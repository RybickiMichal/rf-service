package com.mprybicki.rfservice.rfsensor.service;

import com.google.gson.Gson;
import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.common.model.RFSensorDTO;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class RFSensorService {

    private RFSensorRepository rfSensorRepository;
    private RFSensorValidationService rfSensorValidationService;
    private Gson gson;

    public void updateSensor(String message) {
        RFSensorDTO rfSensorDTO = gson.fromJson(message, RFSensorDTO.class);

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

    private void registerSensor(RFSensor rfSensorToRegister) {
        rfSensorRepository.save(rfSensorToRegister);
        log.info("RF sensor registered " + rfSensorToRegister);
    }

    private void unregisterSensor(RFSensor rfSensorToUnregister) {
        rfSensorValidationService.validateUnregisterSensor(rfSensorToUnregister.getId());
        rfSensorRepository.delete(rfSensorToUnregister);
        log.info("RF sensor unregistered " + rfSensorToUnregister);
    }

    private void updateSensor(RFSensor newRFSensor) {
        rfSensorValidationService.validateUpdateSensor(newRFSensor);
        rfSensorRepository.save(newRFSensor);
        log.info("RF sensor updated " + newRFSensor);
    }
}
