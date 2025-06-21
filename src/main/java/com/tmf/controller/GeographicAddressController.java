package com.tmf.controller;

import com.tmf.exception.AddressNotFoundException;
import com.tmf.model.GeographicAddress;
import com.tmf.repository.GeographicAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tmf-api/geographicAddress")
public class GeographicAddressController {

    @Autowired
    private GeographicAddressRepository geographicAddressRepository;

    /**
     * GET /tmf-api/geographicAddressManagement/v4/geographicAddress
     *
     * @return List of all the GeographicAddresses, retrieved from the database
     */
    @GetMapping
    public ResponseEntity<?> getFilteredAddresses(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postcode,
            @RequestParam(required = false) String fields
    ) {
        List<GeographicAddress> results;

        // Apply filters using repository methods
        if (country != null && city != null) {
            results = geographicAddressRepository.findByCountryAndCity(country, city);
        } else if (country != null) {
            results = geographicAddressRepository.findByCountry(country);
        } else if (city != null) {
            results = geographicAddressRepository.findByCity(city);
        } else if (postcode != null) {
            results = geographicAddressRepository.findByPostcode(postcode);
        } else {
            results = geographicAddressRepository.findAll();
        }

        // If no field selection provided, return all fields
        if (fields == null || fields.isEmpty()) {
            return new ResponseEntity<List<GeographicAddress>>(results, HttpStatus.OK);
        }

        // Apply field selection
        List<Map<String, Object>> filteredResults = new ArrayList<>();
        String[] requestedFields = fields.split(",");

        for (GeographicAddress addr : results) {
            Map<String, Object> map = new HashMap<>();
            for (String field : requestedFields) {
                String trimmed = field.trim();
                if ("id".equals(trimmed)) map.put("id", addr.getId());
                else if ("name".equals(trimmed)) map.put("name", addr.getName());
                else if ("streetName".equals(trimmed)) map.put("streetName", addr.getStreetName());
                else if ("postcode".equals(trimmed)) map.put("postcode", addr.getPostcode());
                else if ("city".equals(trimmed)) map.put("city", addr.getCity());
                else if ("stateOrProvince".equals(trimmed)) map.put("stateOrProvince", addr.getStateOrProvince());
                else if ("country".equals(trimmed)) map.put("country", addr.getCountry());
                else if ("href".equals(trimmed)) map.put("href", addr.getHref());
            }
            filteredResults.add(map);
        }

        return new ResponseEntity<List<Map<String, Object>>>(filteredResults, HttpStatus.OK);
    }

    /**
     * GET /tmf-api/geographicAddressManagement/v4/geographicAddress/{id}
     *
     * @param id ID of the required GeographicAddress
     * @return Http status code 200, and the GeoGraphicAddress object
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressByIdWithFilters(
            @PathVariable String id,
            @RequestParam(required = false) String fields,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city
    ) {
        GeographicAddress address = geographicAddressRepository.findById(id).orElse(null);

        if (address == null) {
            throw new AddressNotFoundException("Address with ID " + id + " not found");
        }

        if (country != null && !country.equalsIgnoreCase(address.getCountry())) {
            return ResponseEntity.notFound().build();
        }

        if (city != null && !city.equalsIgnoreCase(address.getCity())) {
            return ResponseEntity.notFound().build();
        }

        if (fields == null || fields.isEmpty()) {
            return new ResponseEntity<GeographicAddress>(address, HttpStatus.OK);
        }

        String[] requestedFields = fields.split(",");
        Map<String, Object> map = new HashMap<>();

        for (String field : requestedFields) {
            String trimmed = field.trim();
            if ("id".equals(trimmed)) map.put("id", address.getId());
            else if ("name".equals(trimmed)) map.put("name", address.getName());
            else if ("streetName".equals(trimmed)) map.put("streetName", address.getStreetName());
            else if ("postcode".equals(trimmed)) map.put("postcode", address.getPostcode());
            else if ("city".equals(trimmed)) map.put("city", address.getCity());
            else if ("stateOrProvince".equals(trimmed)) map.put("stateOrProvince", address.getStateOrProvince());
            else if ("country".equals(trimmed)) map.put("country", address.getCountry());
            else if ("href".equals(trimmed)) map.put("href", address.getHref());
        }

        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

}

