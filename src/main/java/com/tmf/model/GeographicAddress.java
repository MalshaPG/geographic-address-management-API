package com.tmf.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection = "GeographicAddresses")
public class GeographicAddress {
    private String id;
    private String href;
    private String name = "";
    private String city = "";
    private String country = "";

    private GeographicLocationReforValue geographicLocation;

    private List<GeographicSubAddress> geographicSubAddress = new ArrayList<>();

    private String locality = "";
    private String postcode = "";
    private String stateOrProvince = "";
    private String streetName = "";
    private String streetNr = "";
    private String streetNrLast = "";
    private String streetNrSuffix = "";
    private String streetNrLastSuffix = "";
    private String streetType = "";
    private String streetSuffix = "";

    private String type = "GeographicAddress";
    private String baseType = "GeographicAddress";
    private String schemaLocation = "http://localhost:8080/schemas/GeographicAddress.json";

}
