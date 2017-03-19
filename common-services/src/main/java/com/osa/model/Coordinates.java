package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 2830304317758738048L;

    private Double latitude;
    private Double longtitude;
}
