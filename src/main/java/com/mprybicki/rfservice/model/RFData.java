package com.mprybicki.rfservice.model;

import javax.validation.constraints.Min;

public class RFData {

    double rssi;

    @Min(0)
    Double altitude;

    @Min(0)
    double middleFrequency;
}
