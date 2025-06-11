package com.tmf.controller;

import com.tmf.model.GeographicAddressValidation;
import com.tmf.repository.GeographicAddressValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tmf-api/geographicAddressManagement/v4/geographicAddressValidation")
public class GeographicAddressValidationController {

    @Autowired
    private GeographicAddressValidationRepository validationRepo;

    /**
     * POST /geographicAddressValidation
     *
     * Creates a new validation request.
     * Automatically generates ID, sets status and timestamps.
     *
     * @param validation The request body representing the address to validate
     * @return The created validation object with HTTP 201 Created status
     */
    @PostMapping
    public ResponseEntity<GeographicAddressValidation> createValidatedAddress(@RequestBody GeographicAddressValidation validation) {
        validation.setId(UUID.randomUUID().toString());
        validation.setStatus("InProgress");
        validation.setValidationResult("success");
        validation.setValidationDate(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(validationRepo.save(validation));
    }

    /**
     * GET /geographicAddressValidation
     *
     * Retrieves all previously created validations.
     *
     * @return List of all validation records with HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<GeographicAddressValidation>> getAllValidatedAddresses() {
        List<GeographicAddressValidation> allValidatedAddress = validationRepo.findAll();
        return new ResponseEntity<>(allValidatedAddress, HttpStatus.OK);
    }

    /**
     * GET /geographicAddressValidation/{id}
     *
     * Retrieves a specific validation by its unique ID.
     *
     * @param id The validation ID
     * @return The found validation with HTTP 200 OK, or throws AddressNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<GeographicAddressValidation> getValidatedAddressById(@PathVariable String id) {
        return validationRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PATCH /geographicAddressValidation/{id}
     *
     * Partially updates an existing validation's fields (e.g., status).
     *
     * @param id The ID of the validation to update
     * @param updates A map of field names and their updated values
     * @return The updated validation object with HTTP 200 OK, or throws AddressNotFoundException
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GeographicAddressValidation> updateValidatedAddress(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates) {

            return validationRepo.findById(id)
                    .map(existing -> {
                        // Apply updates from the map to the existing object
                        if (updates.containsKey("status")) {
                            existing.setStatus((String) updates.get("status"));
                        }
                        // Add other fields as needed
                        return ResponseEntity.ok(validationRepo.save(existing));
                    })
                    .orElse(ResponseEntity.notFound().build());

    }
}
