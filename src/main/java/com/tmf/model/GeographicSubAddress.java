package com.tmf.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "geographicSubAddresses")
public class GeographicSubAddress {
    @Id
    private String id;
    private String href;
    private String name;
    private String buildingName;
    private String levelType;
    private String levelNumber;
    private String privateStreetName;
    private String privateStreetNumber;
    private String subAddressType;
    private String subUnitType;
    private String subUnitNumber;

    private String type = "GeographicSubAddress";

}
