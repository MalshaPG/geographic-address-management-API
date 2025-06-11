package com.tmf.controller;

import com.tmf.exception.AddressNotFoundException;
import com.tmf.model.GeographicAddress;
import com.tmf.repository.GeographicAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tmf-api/geographicAddressManagement/v4/geographicAddress")
public class GeographicAddressController {

    @Autowired
    private GeographicAddressRepository geographicAddressRepository;

    /**
     * GET /tmf-api/geographicAddressManagement/v4/geographicAddress
     *
     * @return List of all the GeographicAddresses, retrieved from the database
     */
    @GetMapping
    public ResponseEntity<List<GeographicAddress>> getAllGeographicAddresses() {
        List<GeographicAddress> allGeographicAddresses = geographicAddressRepository.findAll();
        return new ResponseEntity<> (allGeographicAddresses, HttpStatus.OK);
    }

    /**
     * GET /tmf-api/geographicAddressManagement/v4/geographicAddress/{id}
     *
     * @param id ID of the required GeographicAddress
     * @return Http status code 200, and the GeoGraphicAddress object
     */
    @GetMapping("/{id}")
    public ResponseEntity<GeographicAddress> getGeographicAddressForID(@PathVariable String id) {
        //Return the result or if not found throe the custom exception "AddressNotFound"
        GeographicAddress address = geographicAddressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + id + " not found"));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}

