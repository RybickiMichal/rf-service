package com.mprybicki.rfservice.rfsensor.service;


import com.mprybicki.rfservice.common.exception.InvalidSensorException;
import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.common.model.Sensor;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mprybicki.rfservice.common.model.SensorStatus.INACTIVE;

@AllArgsConstructor
@Service
public class RFSensorValidationService {

    private RFSensorRepository rfSensorRepository;


    public void validateUnregisterSensor(String id) {
        RFSensor sensorToUnregister = rfSensorRepository.findById(id).orElseThrow(() -> new InvalidSensorException("RF Sensor with given id doesn't exist"));
        validateIsSensorActive(sensorToUnregister, "Cannot delete inactive sensor");
    }

    public void validateUpdateSensor(Sensor newSenor) {
        validateIsSensorActive(newSenor, "To unregister sensor try delete endpoint");
    }

    private void validateIsSensorActive(Sensor sensor, String errorMessage) {
        if (sensor.getSensorStatus().equals(INACTIVE)) {
            throw new InvalidSensorException(errorMessage);
        }
    }
}
