package com.coprod.gatekeeper.management.dto.response;


import com.coprod.gatekeeper.management.entity.OrgStatus;
import com.coprod.gatekeeper.management.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter

@AllArgsConstructor
public  class OrganizationResponse {


    private Long id;
    private String name;
    private String slug;
    private OrgStatus status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public static OrganizationResponse from (Organization organization){
        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getSlug(),
                organization.getStatus(),
                organization.getCreated_at(),
                organization.getUpdated_at());


    }




}

