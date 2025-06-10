package com.tmf.repositary;

import com.tmf.model.GeographicAddressValidation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GeographicAddressValidationRepository extends MongoRepository<GeographicAddressValidation, String> {
    // CRUD methods auto-generated
}

