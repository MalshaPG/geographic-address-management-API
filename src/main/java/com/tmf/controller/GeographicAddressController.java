package com.tmf.controller;

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

    @GetMapping
    public ResponseEntity<List<GeographicAddress>> getAllGeographicAddresses() {
        List<GeographicAddress> allGeographicAddresses = geographicAddressRepository.findAll();
        return new ResponseEntity<> (allGeographicAddresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeographicAddress> getGeographicAddressForID(@PathVariable String id) {
        return geographicAddressRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

