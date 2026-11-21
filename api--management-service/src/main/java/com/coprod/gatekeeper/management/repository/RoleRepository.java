package com.coprod.gatekeeper.management.repository;

import com.coprod.gatekeeper.management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
