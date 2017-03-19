package com.osa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Transfer implements Serializable {
    private static final long serialVersionUID = -2583423887168800287L;

    private Time departure;
    private Time arrival;
    private Station station;
}
