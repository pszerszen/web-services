package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class City implements Serializable {
    private static final long serialVersionUID = -8301402847341826707L;

    private Long id;
    private String name;
    private CityClass cityClass;
    private Coordinates coordinates;
    private List<Integer> stations;
    private List<Integer> pairs;
    private Country country;
}
