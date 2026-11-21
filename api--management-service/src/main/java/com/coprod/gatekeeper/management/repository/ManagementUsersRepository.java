package com.coprod.gatekeeper.management.repository;

import com.coprod.gatekeeper.management.entity.ManagementUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagementUsersRepository extends JpaRepository<ManagementUsers,Long> {

    Optional<ManagementUsers> findByUsername(String username);
}

//always remembeer to add a no args constructor in order to avoid 401 errors