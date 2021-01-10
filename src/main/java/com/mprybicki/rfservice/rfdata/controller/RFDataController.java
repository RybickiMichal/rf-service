package com.mprybicki.rfservice.rfdata.controller;

import com.mprybicki.rfservice.common.model.RFData;
import com.mprybicki.rfservice.rfdata.service.RFDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class RFDataController {

    private RFDataService rfDataService;

    @PostMapping(value = "/RFData")
    public RFData addRFData(@Valid @RequestBody RFData rfData) {
        return rfDataService.addRFData(rfData);
    }
}
