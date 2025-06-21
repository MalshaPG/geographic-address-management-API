package com.tmf.model;

import java.util.List;

public class Street {
    private String id;
    private String href;
    private String name;
    private String type = "Street";

    private Area area;
    private List<StreetSegment> segments;
}
