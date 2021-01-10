package com.mprybicki.rfservice.rfdata.repository;

import com.mprybicki.rfservice.common.model.RFData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFDataRepository extends MongoRepository<RFData, String> {}
