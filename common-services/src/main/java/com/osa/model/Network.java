package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Network implements Serializable {
    private static final long serialVersionUID = -4555927183604575042L;

    private List<City> cities;
    private List<Station> stations;
}
