package com.tmf.repository;

import com.tmf.model.GeographicAddress;
import com.tmf.model.GeographicAddressValidation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GeographicAddressValidationRepository extends MongoRepository<GeographicAddressValidation, String> {
    // Main CRUD methods auto-generate
    List<GeographicAddressValidation> findByState(String state);
    List<GeographicAddressValidation> findByProvideAlternative(boolean provideAlternative);
    List<GeographicAddressValidation> findByStateAndProvideAlternative(String address, boolean provideAlternative);
}

