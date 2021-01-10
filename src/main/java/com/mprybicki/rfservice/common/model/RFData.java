package com.mprybicki.rfservice.common.model;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@ToString(callSuper = true)
public class RFData {

    @Min(0)
    double rssi;

    @Min(0)
    Double altitude;

    @Min(0)
    double middleFrequency;
}
