package com.mprybicki.rfservice.rfsensor.service;

import com.google.gson.Gson;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorClient;
import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.common.model.RFSensorDTO;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;

@AllArgsConstructor
@Service
public class RFSensorService {

    private RFSensorClient rfSensorClient;

    private Gson gson;

    @Retryable( value = ConnectException.class, maxAttempts=89280, backoff = @Backoff(delay = 30000))
    public List<RFSensor> getActiveRFSensors(){
        return rfSensorClient.getActiveSensors();
    }

    public void updateSensor(String message){
        RFSensorDTO rfSensorDTO = gson.fromJson(message, RFSensorDTO.class);
        switch (rfSensorDTO.getSensorOperation()) {
            case REGISTERED:
                System.out.println("REGISTERED");
                break;
            case UNREGISTERED:
                System.out.println("UNREGISTERED");
                break;
            case UPDATED:
                System.out.println("UPDATED");
                break;
        }
    }
}
