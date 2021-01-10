package com.mprybicki.rfservice.rfsensor.repository;

import com.mprybicki.rfservice.common.model.RFSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFSensorRepository extends CrudRepository<RFSensor, String> {}
