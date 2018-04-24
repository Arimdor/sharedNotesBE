package com.arimdor.sharedNotesBE.repository;

import com.arimdor.sharedNotesBE.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
