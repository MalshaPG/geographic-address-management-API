package com.tmf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "ValidatedAddresses")
public class GeographicAddressValidation {

    @Id
    private String id;

    private String href;

    private boolean provideAlternative;

    private String state;

    private String validationResult;

    private LocalDateTime validationDate;

    private GeographicAddress validGeographicAddress;

    private GeographicAddress submittedGeographicAddress;

    private List<GeographicAddress> alternateGeographicAddress;

    @JsonProperty("@type")
    private String type = "FieldedAddress";

    @JsonProperty("@baseType")
    private String baseType = "GeographicAddressValidation";

}
