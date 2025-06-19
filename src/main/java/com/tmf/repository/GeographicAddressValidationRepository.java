package com.tmf.repository;

import com.tmf.model.GeographicAddress;
import com.tmf.model.GeographicAddressValidation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GeographicAddressValidationRepository extends MongoRepository<GeographicAddressValidation, String> {
    // Main CRUD methods auto-generate
    List<GeographicAddressValidation> findByStatus(String status);
    List<GeographicAddressValidation> findByProvideAlternative(boolean provideAlternative);
    List<GeographicAddressValidation> findByStatusAndProvideAlternative(String address, boolean provideAlternative);
}

