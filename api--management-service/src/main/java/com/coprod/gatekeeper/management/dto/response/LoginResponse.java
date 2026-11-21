package com.coprod.gatekeeper.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class LoginResponse {

    private String token;
    private String tokenType;
    private Long expiresInSecond;
    private String role;
    private Long OrganizationId;

}
