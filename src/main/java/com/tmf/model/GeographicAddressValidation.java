package com.tmf.model;

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
    private String status;
    private String validationResult;
    private LocalDateTime validationDate;
    private GeographicAddress validAddress;
    private GeographicAddress submittedGeographicAddress;
    private List<GeographicAddress> alternateGeographicAddress;
}
