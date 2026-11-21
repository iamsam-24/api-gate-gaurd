package com.coprod.gatekeeper.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

    @Enumerated(EnumType.STRING)
    private OrgStatus status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;


    public void getStatus(OrgStatus orgStatus) {
    }
}
