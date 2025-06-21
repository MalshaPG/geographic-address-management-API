package com.tmf.model;

import lombok.Data;

@Data
public class StreetSegment {
    private String id;
    private String href;
    private String name;
    private String startNr;
    private String endNr;
    private String type = "StreetSegment";

    private Street street;
}
