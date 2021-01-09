package com.mprybicki.rfservice.service;

import com.mprybicki.rfservice.Repository.RFDataRepository;
import com.mprybicki.rfservice.model.RFData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RFDataService {

    private RFDataRepository rfDataRepository;
    private RFDataSenderService rfDataSenderService;

    public RFData addRFData(RFData rfData){
        //TODO walidacja
        rfDataRepository.save(rfData);
        rfDataSenderService.send(rfData);
        return rfData;
    }
}
