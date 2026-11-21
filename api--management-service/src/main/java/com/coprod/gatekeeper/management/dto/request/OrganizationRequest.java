package com.coprod.gatekeeper.management.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationRequest {

    private String name;
    private String slug;

}
