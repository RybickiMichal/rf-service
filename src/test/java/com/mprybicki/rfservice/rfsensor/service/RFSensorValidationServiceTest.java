package com.mprybicki.rfservice.rfsensor.service;

import com.mprybicki.rfservice.common.exception.InvalidSensorException;
import com.mprybicki.rfservice.rfsensor.repository.RFSensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mprybicki.rfservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor;
import static com.mprybicki.rfservice.rfsensor.sampledata.RFSensorSampleData.correctActiveRFSensor2;
import static com.mprybicki.rfservice.rfsensor.sampledata.RFSensorSampleData.correctInactiveRFSensor;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RFSensorValidationServiceTest {

    @Mock
    private RFSensorRepository rfSensorRepository;

    @InjectMocks
    private RFSensorValidationService rfSensorValidationService;

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndThereIsNoSensorWithGivenId() {
        assertThatThrownBy(() -> rfSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("RF Sensor with given id doesn't exist");
    }

    @Test
    void shouldThrowInvalidSensorExceptionWhenUnregisteringAndSensorStatusIsInactive() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctInactiveRFSensor()));

        assertThatThrownBy(() -> rfSensorValidationService.validateUnregisterSensor(any()))
                .isInstanceOf(InvalidSensorException.class)
                .hasMessage("Cannot delete inactive sensor");
    }

    @Test
    void shouldPassUnregisteringValidation() {
        when(rfSensorRepository.findById(any())).thenReturn(Optional.of(correctActiveRFSensor()));

        assertDoesNotThrow(() -> rfSensorValidationService.validateUnregisterSensor(any()));
    }

}