package com.arimdor.sharedNotesBE.repository;

import com.arimdor.sharedNotesBE.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
