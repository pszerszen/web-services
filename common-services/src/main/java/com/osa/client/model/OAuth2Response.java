package com.osa.client.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class OAuth2Response {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Integer expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    private String scope;
}
