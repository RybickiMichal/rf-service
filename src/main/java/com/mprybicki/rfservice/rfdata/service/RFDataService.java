package com.mprybicki.rfservice.rfdata.service;

import com.mprybicki.rfservice.rfdata.repository.RFDataRepository;
import com.mprybicki.rfservice.common.model.RFData;
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
