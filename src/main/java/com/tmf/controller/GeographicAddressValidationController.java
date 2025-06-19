package com.tmf.controller;

import com.tmf.model.GeographicAddressValidation;
import com.tmf.repository.GeographicAddressValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    // GET /geographicAddressValidation?status=...&provideAlternative=...
    @GetMapping
    public ResponseEntity<List<GeographicAddressValidation>> getByFilter(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) Boolean provideAlternative
    ) {
    List<GeographicAddressValidation> allResults;

    if (status != null && provideAlternative != null) {
        allResults = validationRepo.findByStatusAndProvideAlternative(status, provideAlternative);
    } else if (status != null) {
        allResults = validationRepo.findByStatus(status);
    } else if (provideAlternative != null) {
        allResults = validationRepo.findByProvideAlternative(provideAlternative);
    } else {
        allResults = validationRepo.findAll();
    }

    return new ResponseEntity<>(allResults, HttpStatus.OK);
    }

    /**
     * GET /geographicAddressValidation/{id}
     *
     * Retrieves a specific validation by its unique ID.
     *
     * @param id The validation ID
     * @return The found validation with HTTP 200 OK, or throws AddressNotFoundException
     */

//    @GetMapping("/{id}")
//    public ResponseEntity<GeographicAddressValidation> getById(@PathVariable String id) {
//        return validationRepo.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdWithFilters(
            @PathVariable String id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean provideAlternative,
            @RequestParam(required = false) String fields
    ) {
        GeographicAddressValidation result = validationRepo.findById(id).orElse(null);

        // If not found, return 404
        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        // If filter on status exists and doesn't match, return 404
        if (status != null && !status.equalsIgnoreCase(result.getStatus())) {
            return ResponseEntity.notFound().build();
        }

        // If filter on provideAlternative exists and doesn't match, return 404
        if (provideAlternative != null && provideAlternative != result.isProvideAlternative()) {
            return ResponseEntity.notFound().build();
        }

        // If no fields parameter, return the whole object
        if (fields == null || fields.isEmpty()) {
            return ResponseEntity.ok(result);
        }

        // If fields are specified, build a filtered response
        Map<String, Object> filteredResult = new HashMap<>();

        String[] requestedFields = fields.split(",");
        for (String field : requestedFields) {
            switch (field.trim()) {
                case "id":
                    filteredResult.put("id", result.getId());
                    break;
                case "status":
                    filteredResult.put("status", result.getStatus());
                    break;
                case "provideAlternative":
                    filteredResult.put("provideAlternative", result.isProvideAlternative());
                    break;
                case "validationResult":
                    filteredResult.put("validationResult", result.getValidationResult());
                    break;
                case "validationDate":
                    filteredResult.put("validationDate", result.getValidationDate());
                    break;
                case "submittedGeographicAddress":
                    filteredResult.put("submittedGeographicAddress", result.getSubmittedGeographicAddress());
                    break;
                case "alternateGeographicAddress":
                    filteredResult.put("alternateGeographicAddress", result.getAlternateGeographicAddress());
                    break;
                case "validAddress":
                    filteredResult.put("validAddress", result.getValidAddress());
                    break;
                default:
                    // Skip unknown fields
                    break;
            }
        }

        return ResponseEntity.ok(filteredResult);
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


        //Find entity by id
        GeographicAddressValidation existing = validationRepo.findById(id).orElse(null);

        if (existing == null) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }

        // Apply the updates
        if (updates.containsKey("status")) {
            existing.setStatus((String) updates.get("status"));
        }

        // Save the updated entity
        GeographicAddressValidation updated = validationRepo.save(existing);

        return new ResponseEntity<>(updated, HttpStatus.OK); // Return 200 OK with updated object
    }
}
