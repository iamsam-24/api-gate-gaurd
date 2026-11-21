package com.coprod.gatekeeper.management.repository;


import com.coprod.gatekeeper.management.entity.OrgStatus;
import com.coprod.gatekeeper.management.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRespository extends JpaRepository<Organization,Long> {

    Optional<Organization> findBIdAndStatusNot(Long id, OrgStatus status);

    Optional<Organization> findByAllAndStatusNot(OrgStatus status);
}
