package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Station implements Serializable {
    private static final long serialVersionUID = -9088762482371387394L;

    private Long id;
    private String code;
    private String name;
    private String address;
    private Coordinates coordinates;
    private City city;
    private Country country;
}
