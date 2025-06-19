package com.tmf.repository;

import com.tmf.model.GeographicAddress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GeographicAddressRepository extends MongoRepository<GeographicAddress, String> {
    // CRUD methods auto-generate
    List<GeographicAddress> findByCountry(String country);
    List<GeographicAddress> findByCity(String city);
    List<GeographicAddress> findByPostcode(String postcode);
    List<GeographicAddress> findByCountryAndCity(String country, String city);
}
