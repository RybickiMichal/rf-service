package com.mprybicki.rfservice.Repository;

import com.mprybicki.rfservice.model.RFData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFDataRepository extends MongoRepository<RFData, String> {}
