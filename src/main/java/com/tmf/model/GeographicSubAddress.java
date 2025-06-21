package com.tmf.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "GeographicSubAddresses")
public class GeographicSubAddress {
    @Field("id")
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
