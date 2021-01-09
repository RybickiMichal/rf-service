package com.mprybicki.rfservice.service;

import com.mprybicki.rfservice.Repository.RFSensorClient;
import com.mprybicki.rfservice.model.RFSensor;
import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;

@AllArgsConstructor
@Service
public class RFSensorService {

    RFSensorClient rfSensorClient;

    @Retryable( value = ConnectException.class, maxAttempts=89280, backoff = @Backoff(delay = 30000))
    public List<RFSensor> getActiveRFSensors(){
        return rfSensorClient.getActiveSensors();
    }
}
