package com.osa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class Trip implements Serializable {
    private static final long serialVersionUID = 7543478626620070263L;

    private Station from;
    private Station to;
    private List<TripItem> items;
}
