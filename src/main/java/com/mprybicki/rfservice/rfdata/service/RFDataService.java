package com.mprybicki.rfservice.rfdata.service;

import com.mprybicki.rfservice.common.model.RFData;
import com.mprybicki.rfservice.common.model.RFSensor;
import com.mprybicki.rfservice.rfdata.repository.RFDataRepository;
import com.mprybicki.rfservice.rfsensor.client.RFSensorClient;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class RFDataService {

    private RFDataRepository rfDataRepository;
    private RFDataSenderService rfDataSenderService;
    private RFSensorClient rfSensorClient;
    private RFSensorRepository rfSensorRepository;

    private static boolean AreSensorsNotDownloaded = true;

    public RFData addRFData(RFData rfData) {
        if (AreSensorsNotDownloaded) {
            downloadSensors();
        }

        rfDataRepository.save(rfData);
        rfDataSenderService.send(rfData);
        log.info("Received new camera data " + rfData.toString());
        return rfData;
    }

    private void downloadSensors() {
        rfSensorRepository.deleteAll();
        List<RFSensor> sensors = rfSensorClient.getActiveSensors();
        log.info("successfully downloaded sensors");

        AreSensorsNotDownloaded = false;
        rfSensorRepository.saveAll(sensors);
    }
}
