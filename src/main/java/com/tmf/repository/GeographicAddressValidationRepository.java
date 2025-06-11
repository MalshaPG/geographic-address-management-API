package com.tmf.repository;

import com.tmf.model.GeographicAddressValidation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GeographicAddressValidationRepository extends MongoRepository<GeographicAddressValidation, String> {
    // CRUD methods auto-generate
}

