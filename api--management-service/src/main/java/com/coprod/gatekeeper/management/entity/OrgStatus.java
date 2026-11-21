package com.coprod.gatekeeper.management.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor

public enum OrgStatus {

    PENDING,
    ACTIVE,
    SUSPEND,
    DELETED

}
