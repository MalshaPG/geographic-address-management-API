package com.tmf.repository;

import com.tmf.model.GeographicAddress;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GeographicAddressRepository extends MongoRepository<GeographicAddress, String> {
    // CRUD methods auto-generate
}
