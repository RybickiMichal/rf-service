package com.mprybicki.rfservice.Repository;

import com.mprybicki.rfservice.model.RFSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFSensorRepository extends CrudRepository<RFSensor, String> {}
