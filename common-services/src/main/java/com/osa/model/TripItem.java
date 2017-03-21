package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TripItem implements Serializable {
    private static final long serialVersionUID = -3693649260148803427L;

    private Long id;
    private Time departure;
    private Time arrival;
    private double price;
    private List<Transfer> transfers;
    private Status status;
    private List<Link> links;
}
