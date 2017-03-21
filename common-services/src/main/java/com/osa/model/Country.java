package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Country implements Serializable {
    private static final long serialVersionUID = 162088827893700838L;

    private String name;
    private String code;
}
