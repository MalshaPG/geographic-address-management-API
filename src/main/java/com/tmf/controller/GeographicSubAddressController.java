package com.tmf.controller;

import com.tmf.exception.AddressNotFoundException;
import com.tmf.model.GeographicAddress;
import com.tmf.model.GeographicSubAddress;
import com.tmf.repository.GeographicAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tmf-api/geographicAddressManagement/v4/geographicAddress/{addressId}/geographicSubAddress")
public class GeographicSubAddressController {

    @Autowired
    private GeographicAddressRepository geographicAddressRepository;

    /**
     *
     * @param addressId - GeographicAddress id
     * @return list of subAddresses of the geographic address with the addressID
     */
    @GetMapping
    public ResponseEntity<ArrayList<GeographicSubAddress>> listSubAddresses(@PathVariable String addressId) {
        // Find the address by ID
        Optional<GeographicAddress> addressOptional = geographicAddressRepository.findById(addressId);

        if (addressOptional.isEmpty()) {
            throw new AddressNotFoundException("Address not found with id: " + addressId);
        }

        GeographicAddress address = addressOptional.get();

        // Get the sub-address list from the address
        List<GeographicSubAddress> subAddresses = address.getGeographicSubAddress();

        // Convert to ArrayList (if needed) and return
        return ResponseEntity.ok(new ArrayList<>(subAddresses));
    }

    /**
     *
     * @param addressId - GeographicAddress id
     * @param subAddressId -GeographicSubAddress id
     * @return corresponding SubAddress
     */
    @GetMapping("/{subAddressId}")
    public ResponseEntity<GeographicSubAddress> getSubAddress(
            @PathVariable String addressId,
            @PathVariable String subAddressId) {

        // Find the address by ID
        Optional<GeographicAddress> addressOptional = geographicAddressRepository.findById(addressId);

        if (addressOptional.isEmpty()) {
            throw new AddressNotFoundException("Address not found with id: " + addressId);
        }

        GeographicAddress address = addressOptional.get();

        // Find the specific sub-address in the list
        Optional<GeographicSubAddress> subAddressOptional = address.getGeographicSubAddress()
                .stream()
                .filter(subAddress -> subAddress.getId().equals(subAddressId))
                .findFirst();

        if (subAddressOptional.isEmpty()) {
            throw new AddressNotFoundException(
                    "Sub-address not found with id: " + subAddressId + " for address: " + addressId);
        }

        return ResponseEntity.ok(subAddressOptional.get());
    }
}