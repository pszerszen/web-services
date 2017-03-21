package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Trip implements Serializable {
    private static final long serialVersionUID = 7543478626620070263L;

    private Station from;
    private Station to;
    private List<TripItem> items;
}
