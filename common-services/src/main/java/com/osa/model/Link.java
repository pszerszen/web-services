package com.osa.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Link implements Serializable {
    private static final long serialVersionUID = -527598814758543004L;

    private String reference;
    private String url;
}
